package com.opzoon.license.creator;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.opzoon.license.common.FileUtil;
import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.custom.RsaException;

public class VDILicenseCreator {

	private static Logger log = Logger.getLogger(VDILicenseCreator.class);
	// RSA最大加密明文大小
	private static final int MAX_ENCRYPT_BLOCK = 117;
	// RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final String SPL_LI = "#-#-#-";

	// 分段加密
	private static String encryptByKey(String data, Key key) throws BasicException {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, key);
		} catch (Exception e) {
			throw new RsaException(e);
		} 

		int length = data.length();
		int offset = 0;
		byte[] result = null;
		int i = 0;
		while (length - offset > 0) {
			byte[] cache;
			try {
				if (length - offset > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data.getBytes(), offset, length - offset);
				}
			} catch (Exception e) {
				throw new RsaException(e);
			}
			result = VDILicenseCreator.concat(result, cache);
			i++;
			offset = i * MAX_ENCRYPT_BLOCK;
		}
		
		return Base64.encodeBase64String(result);
	}
	
	// 分段解密
	private static String decryptByKey(String data, Key key) throws BasicException {
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			throw new RsaException(e);
		}
		
		byte[] data64 = Base64.decodeBase64(data);
		int length = data64.length;
		int offset = 0;
		byte[] result = null;
		int i = 0;
		while (length - offset > 0) {
			byte[] cache;
			try {
				if (length - offset > MAX_DECRYPT_BLOCK) {
					cache = cipher.doFinal(data64, offset, MAX_DECRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data64, offset, length - offset);
				}
			} catch (Exception e) {
				throw new RsaException(e);
			}
			result = VDILicenseCreator.concat(result, cache);
			i++;
			offset = i * MAX_DECRYPT_BLOCK;
		}
		
		return new String(result);
	}

	private static PrivateKey newPrivateKey(String key) throws BasicException {
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
				Base64.decodeBase64(key));
		PrivateKey priKey;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			priKey = keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new RsaException(e);
		}
		return priKey;
	}
	
	private static PublicKey newPublicKey(String key) throws BasicException {
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
				Base64.decodeBase64(key));
		PublicKey pubKey;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			pubKey = keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new RsaException(e);
		}
		return pubKey;
	}

	public static void createRSAKey(String privateKeyPath, String publicKeyPath) throws BasicException {
		Date start = new Date();
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new RsaException(e);
		}
		SecureRandom random = new SecureRandom();
		kpg.initialize(2048, random); // 指定密匙长度
		KeyPair kp = kpg.genKeyPair(); // 生成密匙对
		PublicKey public_key = kp.getPublic(); // 获得公匙
		PrivateKey private_key = kp.getPrivate(); // 获得私匙

		Date genD = new Date();
		log.info("gen key time is : " + (genD.getTime() - start.getTime()));

		String publicKeyStr = Base64
				.encodeBase64String(public_key.getEncoded());
		// 创建公钥
		FileUtil.createFile(publicKeyPath, publicKeyStr.getBytes());

		String privateKeyStr = Base64.encodeBase64String(private_key
				.getEncoded());
		// 创建私钥
		FileUtil.createFile(privateKeyPath, privateKeyStr.getBytes());
		
	}

	public static void createLicense(String licensePath, String privateKeyPath,
			String fingerContent, int maxConnect, int registerType,
			int usageType, int validdayAmount) {
		
		String privateKeyStr = FileUtil.readFile(privateKeyPath);
		
		if (privateKeyStr == null || privateKeyStr.isEmpty()) {
			throw new RsaException("privateKeyStr error");
		}
		if (fingerContent == null || fingerContent.isEmpty()) {
			throw new RsaException("fingerContent is empty");
		}
		
		PrivateKey pKey = VDILicenseCreator.newPrivateKey(privateKeyStr);
		
		String finger = VDILicenseCreator.decryptByKey(fingerContent, pKey);
		// 指纹 + 并发数 + 有限时间 + 单机=1/集群=2 + 正式=1/试用=2
		String license = finger + SPL_LI + maxConnect + SPL_LI
				+ validdayAmount + SPL_LI + registerType + SPL_LI
				+ usageType;
		String licenseCipher = encryptByKey(license, pKey);
		// 创建license
		FileUtil.createFile(licensePath, licenseCipher.getBytes());
	}
	
	public static void createLicense(String licensePath, String privateKeyPath, 
			int maxConnect, int validdayAmount) {
		
		String privateKeyStr = FileUtil.readFile(privateKeyPath);
		
		if (privateKeyStr == null || privateKeyStr.isEmpty()) {
			throw new RsaException("privateKeyStr error");
		}
		
		PrivateKey pKey = VDILicenseCreator.newPrivateKey(privateKeyStr);
		// UUID + 并发数 + 有限时间 + Ukey=0/单机=1/集群=2 + Ukey=0/正式=1/试用=2
		String license = UUID.randomUUID().toString() + SPL_LI + maxConnect + SPL_LI + validdayAmount + SPL_LI 
				+ 0 + SPL_LI + 0;
		String licenseCipher = encryptByKey(license, pKey);
		// 创建license
		FileUtil.createFile(licensePath, licenseCipher.getBytes());
	}
	
	public static void createLicense(String licensePath, String privateKeyPath,
			String license) {
		
		String privateKeyStr = FileUtil.readFile(privateKeyPath);
		
		if (privateKeyStr == null || privateKeyStr.isEmpty()) {
			throw new RsaException("privateKeyStr error");
		}
		
		PrivateKey pKey = VDILicenseCreator.newPrivateKey(privateKeyStr);
		
		String licenseCipher = encryptByKey(license, pKey);
		// 创建license
		FileUtil.createFile(licensePath, licenseCipher.getBytes());
	}

	// 合并数组
	private static byte[] concat(byte[] first, byte[] second) {
		if(first == null && second == null) {
			return new byte[0];
		}
		if(first == null) {
			return second;
		}
		if(second == null) {
			return first;
		}
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	
	/*public static void main(String[] args) {
		String license = FileUtil.readFile("E://test.license");
		System.out.println("License : " + license);
		String pubKey = FileUtil.readFile("E://keypair//public.key");
		System.out.println("UKey : " + pubKey);
		String result = decryptByKey(license, newPublicKey(pubKey));
		System.out.println("Result : " + result);
	}*/
	
}

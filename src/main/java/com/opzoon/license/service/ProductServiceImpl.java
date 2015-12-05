package com.opzoon.license.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.opzoon.license.common.Constants;
import com.opzoon.license.common.FileUtil;
import com.opzoon.license.creator.SuiteLicenseCreator;
import com.opzoon.license.creator.VDILicenseCreator;
import com.opzoon.license.dao.ProductDao;
import com.opzoon.license.dao.SystemDao;
import com.opzoon.license.domain.ClusterUKey;
import com.opzoon.license.domain.License;
import com.opzoon.license.domain.PageModel;
import com.opzoon.license.domain.Product;
import com.opzoon.license.domain.SuiteLicense;
import com.opzoon.license.domain.UsageType;
import com.opzoon.license.domain.User;
import com.opzoon.license.domain.VDILicense;
import com.opzoon.license.domain.VDIUKey;
import com.opzoon.license.domain.Version;
import com.opzoon.license.domain.request.ClusterUKeyRequest;
import com.opzoon.license.domain.request.LicenseRequest;
import com.opzoon.license.domain.request.ProductRequest;
import com.opzoon.license.domain.request.UserRequest;
import com.opzoon.license.domain.request.VDIUKeyRequest;
import com.opzoon.license.domain.request.VersionRequest;
import com.opzoon.license.domain.response.BasicResponse;
import com.opzoon.license.domain.response.ClusterUKeyResponse;
import com.opzoon.license.domain.response.LicenseResponse;
import com.opzoon.license.domain.response.ProductResponse;
import com.opzoon.license.domain.response.VDIUKeyResponse;
import com.opzoon.license.domain.response.VersionResponse;
import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.ExceptionCode;
import com.opzoon.license.exception.custom.UploadFileException;

@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	private static Logger log = Logger.getLogger(ProductServiceImpl.class); 
	
	@Autowired
	private SystemDao systemDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public BasicResponse listProducts(ProductRequest productReq) throws BasicException {
		log.info("<=license=>service listProducts productReq=" + productReq);
		List<Product> tmpList = productDao.findProducts(productReq);
		List<Product> productList = new ArrayList<Product>();
		// 根据权限过滤list
		User loginUser = new User();
		loginUser.setUserId(productReq.getLoginUserId());
		PageModel page = new PageModel();
		int[] authority = getAuthority(systemDao.findUsers(new UserRequest(loginUser, page)).get(0));
		for(Product product : tmpList) {
			int result = Arrays.binarySearch(authority, product.getProductId());
			if(result >= 0) {
				productList.add(product);
			}
		}
		PageModel pageModel = productReq.getPage();
		pageModel.setAmount(productDao.getProductAmount(productReq));
		return new ProductResponse(ExceptionCode.Success.getErrorCode(), productList, pageModel);
	}

	@Override
	public BasicResponse createVersion(Version version) throws BasicException {
		log.info("<=license=>service createVersion version=" + version);
		// 拿到product的name，生成私钥公钥的相对路径，在数据库存储相对路径
		String keyName = version.getProduct().getProductName() + version.getVersionNo();
		log.info("<=license=>service createVersion keyName=" + keyName);
		String privateKeyPath = Constants.KEY_PATH + keyName + Constants.PRIVATE_KEY_SUFFIX;
		String publicKeyPath = Constants.KEY_PATH + keyName + Constants.PUBLIC_KEY_SUFFIX;
		version.setPrivateKey(privateKeyPath);
		version.setPublicKey(publicKeyPath);
		// 生成密钥对
		if(version.getProduct().getProductId() == 1) {
			SuiteLicenseCreator.createRSAKey(
					Constants.ROOT_PATH + privateKeyPath, 
					Constants.ROOT_PATH + publicKeyPath
					);
		}else if(version.getProduct().getProductId() == 2) {
			VDILicenseCreator.createRSAKey(
					Constants.ROOT_PATH + privateKeyPath, 
					Constants.ROOT_PATH + publicKeyPath
					);
		}
		version.setCreateDate(new Date());
		productDao.addVersion(version);
		
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse listVersions(VersionRequest versionReq) throws BasicException {
		log.info("<=license=>service listVersions versionReq=" + versionReq);
		List<Version> tmpList = productDao.findVersions(versionReq);
		List<Version> versionList = new ArrayList<Version>();
		// 根据权限过滤list
		User loginUser = new User();
		loginUser.setUserId(versionReq.getLoginUserId());
		PageModel page = new PageModel();
		int[] authority = getAuthority(systemDao.findUsers(new UserRequest(loginUser, page)).get(0));
		for(Version version : tmpList) {
			int result = Arrays.binarySearch(authority, version.getProduct().getProductId());
			if(result >= 0) {
				versionList.add(version);
			}
		}
		PageModel pageModel = versionReq.getPage();
		pageModel.setAmount(productDao.getVersionAmount(versionReq));
		return new VersionResponse(ExceptionCode.Success.getErrorCode(), versionList, pageModel);
	}

	@Override
	public BasicResponse deleteVersions(Integer[] ids) throws BasicException {
		log.info("<=license=>service deleteVersions ids=" + ids);
		productDao.deleteVersions(ids);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse createLicense(License license) throws BasicException {
		log.info("<=license=>service createLicense license=" + license);
		// 拿到license的name，生成license的相对路径，在数据库存储相对路径 
		String licensePath = Constants.LICENSE_PATH + license.getLicenseName() + Constants.LICENSE_SUFFIX;
		license.setLicenseURL(licensePath);
		// 生成license
		if(license instanceof SuiteLicense) {
			SuiteLicenseCreator.createLicense(
					Constants.ROOT_PATH + licensePath, 
					Constants.ROOT_PATH + license.getVersion().getPrivateKey(), 
					Constants.ROOT_PATH + license.getVersion().getPublicKey(), 
					license.getRegisterInfo(), 
					((SuiteLicense)license).getValiddayAmount(), 
					((SuiteLicense)license).getNuclearAmout(), 
					((SuiteLicense)license).getCpuAmount(), 
					((SuiteLicense)license).getHostAmount()
					);
		}else if(license instanceof VDILicense) {
			if(((VDILicense)license).getUsageTypeCode() == UsageType.TRIAL.getCode()) { // 如果是试用版，只可使用7天
				license.setValiddayAmount(7);
			}
			VDILicenseCreator.createLicense(
					Constants.ROOT_PATH + licensePath, 
					Constants.ROOT_PATH + license.getVersion().getPrivateKey(), 
					license.getRegisterInfo(), 
					((VDILicense)license).getMaxConnectAmount(), 
					((VDILicense)license).getRegisterTypeCode(), 
					((VDILicense)license).getUsageTypeCode(), 
					license.getValiddayAmount()
					);
		}
		license.setCreateDate(new Date());
		productDao.addLicense(license);
		
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse listLicenses(LicenseRequest licenseReq) throws BasicException {
		log.info("<=license=>service listLicenses licenseReq=" + licenseReq);
		List<License> tmpList = productDao.findLicenses(licenseReq);
		List<License> licenseList = new ArrayList<License>();
		// 根据权限过滤list
		User loginUser = new User();
		loginUser.setUserId(licenseReq.getLoginUserId());
		PageModel page = new PageModel();
		int[] authority = getAuthority(systemDao.findUsers(new UserRequest(loginUser, page)).get(0));
		for(License license : tmpList) {
			int result = Arrays.binarySearch(authority, license.getVersion().getProduct().getProductId());
			if(result >= 0) {
				licenseList.add(license);
			}
		}
		PageModel pageModel = licenseReq.getPage();
		pageModel.setAmount(productDao.getLicenseAmount(licenseReq));
		return new LicenseResponse(ExceptionCode.Success.getErrorCode(), licenseList, pageModel);
	}

	@Override
	public BasicResponse deleteLicenses(Integer[] ids) throws BasicException {
		log.info("<=license=>service deleteLicenses ids=" + ids);
		productDao.deleteLicenses(ids);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}
	
	@Override
	public BasicResponse listClusterUKeys(ClusterUKeyRequest clusterUKeyReq)
			throws BasicException {
		log.info("<=license=>service listClusterUKeys clusterUKeyReq=" + clusterUKeyReq);
		List<ClusterUKey> clusterUKeyList = productDao.findClusterUKeys(clusterUKeyReq);
		PageModel pageModel = clusterUKeyReq.getPage();
		pageModel.setAmount(productDao.getClusterUKeyAmount(clusterUKeyReq));
		return new ClusterUKeyResponse(ExceptionCode.Success.getErrorCode(), clusterUKeyList, pageModel);
	}

	@Override
	public BasicResponse createClusterUKey(ClusterUKey clusterUKey)
			throws BasicException {
		log.info("<=license=>service createClusterUKey clusterUKey=" + clusterUKey);
		clusterUKey.setGuid(UUID.randomUUID().toString());
		String licensePath = Constants.ULICENSE_PATH + clusterUKey.getLicenseName() + Constants.LICENSE_SUFFIX;
		clusterUKey.setLicenseURL(licensePath);
		
		// 创建json对象
		JsonObject obj = new JsonObject();
		obj.addProperty("version", clusterUKey.getVersion());
		obj.addProperty("id", clusterUKey.getGuid());
		JsonArray list = new JsonArray();
		// 
		Integer suiteCount = clusterUKey.getSuiteCount();
		Integer suiteTerm = clusterUKey.getSuiteTerm();
		if(suiteCount != null && suiteTerm != null) {
			JsonObject suite = new JsonObject();
			suite.addProperty("name", "SUITE");
			suite.addProperty("count", suiteCount);
			suite.addProperty("term", suiteTerm);
			list.add(suite);
		}
		//
		Integer vdiCount = clusterUKey.getVdiCount();
		Integer vdiTerm = clusterUKey.getVdiTerm();
		if(vdiCount != null && vdiTerm != null) {
			JsonObject vdi = new JsonObject();
			vdi.addProperty("name", "VDI");
			vdi.addProperty("count", vdiCount);
			vdi.addProperty("term", vdiTerm);
			list.add(vdi);
		}
		//
		Integer fwCount = clusterUKey.getFwCount();
		Integer fwTerm = clusterUKey.getFwTerm();
		if(fwCount != null && fwTerm != null) {
			JsonObject fw = new JsonObject();
			fw.addProperty("name", "FW");
			fw.addProperty("count", fwCount);
			fw.addProperty("term", fwTerm);
			list.add(fw);
		}
		obj.add("productlist", list);
		
		VDILicenseCreator.createLicense(
				Constants.ROOT_PATH + licensePath, 
				Constants.ROOT_PATH + Constants.UKEY_PATH + clusterUKey.getUkeyName(), 
				obj.toString()
				);
		
		productDao.addClusterUKey(clusterUKey);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse deleteClusterUKeys(Integer[] ids) throws BasicException {
		log.info("<=license=>service deleteClusterUKeys ids=" + ids);
		productDao.deleteClusterUKeys(ids);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse listVDIUKeys(VDIUKeyRequest vdiUKeyReq)
			throws BasicException {
		log.info("<=license=>service listVDIUKeys vdiUKeyReq=" + vdiUKeyReq);
		List<VDIUKey> vdiUKeyList = productDao.findVDIUKeys(vdiUKeyReq);
		PageModel pageModel = vdiUKeyReq.getPage();
		pageModel.setAmount(productDao.getVDIUKeyAmount(vdiUKeyReq));
		return new VDIUKeyResponse(ExceptionCode.Success.getErrorCode(), vdiUKeyList, pageModel);
	}

	@Override
	public BasicResponse createVDIUKey(VDIUKey vdiUKey) throws BasicException {
		log.info("<=license=>service createVDIUKey vdiUKey=" + vdiUKey);
		String licensePath = Constants.ULICENSE_PATH + vdiUKey.getLicenseName() + Constants.LICENSE_SUFFIX;
		vdiUKey.setLicenseURL(licensePath);
		VDILicenseCreator.createLicense(
				Constants.ROOT_PATH + licensePath, 
				Constants.ROOT_PATH + Constants.UKEY_PATH + vdiUKey.getUkeyName(), 
				vdiUKey.getMaxConnectAmount(), 
				vdiUKey.getValiddayAmount()
				);
		productDao.addVDIUKey(vdiUKey);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}

	@Override
	public BasicResponse deleteVDIUKeys(Integer[] ids) throws BasicException {
		log.info("<=license=>service deleteVDIUKeys ids=" + ids);
		productDao.deleteVDIUKeys(ids);
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}
	
	@Override
	public BasicResponse uploadUKey(MultipartFile file) throws BasicException {
		if(file == null) {
			throw new UploadFileException("upload file is null");
		}
		try {
			FileUtil.createFile(
					Constants.ROOT_PATH + Constants.UKEY_PATH + file.getOriginalFilename(), 
					file.getBytes()
					);
		} catch (IOException e) {
			throw new UploadFileException(e);
		}
		return new BasicResponse(ExceptionCode.Success.getErrorCode());
	}
	
	private int[] getAuthority(User user) {
		Set<Product> products = user.getProducts();
		int[] authority = new int[products.size()];
		int i = 0;
		for(Product product : user.getProducts()) {
			authority[i] = product.getProductId();
			i++;
		}
		Arrays.sort(authority);
		return authority;
	}
	
}

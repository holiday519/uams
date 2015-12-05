package com.opzoon.license.creator;

import org.apache.log4j.Logger;

import com.opzoon.license.common.Constants;
import com.opzoon.license.common.ShellUtil;
import com.opzoon.license.exception.BasicException;

public class SuiteLicenseCreator {
	
	private static Logger log = Logger.getLogger(SuiteLicenseCreator.class);

	public static void createRSAKey(String privateKeyPath, String publicKeyPath) throws BasicException {
		String shell = "python " + Constants.GEN_KEY_PYTHON + " " + publicKeyPath + " " + privateKeyPath;
		log.info("createRSAKey shell=" + shell);
		ShellUtil.executeShell(shell);
	}
	
	public static void createLicense(String licensePath, String privateKeyPath, String publicKeyPath, 
			String fingerContent, int days, int cores, int cpus, int nodes) throws BasicException {
		String shell = "python " + Constants.GEN_LICENSE_PYTHON + " -d" + days + " -c" + cores + " -p" + cpus
                + " -n" + nodes + " -k" + privateKeyPath + " -g" + publicKeyPath + " -u" + fingerContent + " -o" + licensePath;
		log.info("createLicense shell=" + shell);
		ShellUtil.executeShell(shell);
	}
}

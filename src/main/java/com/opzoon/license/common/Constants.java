package com.opzoon.license.common;

import java.io.File;

public class Constants {

	public static final String ROOT_PATH = System.getProperty("uams.root");
	
	public static final String KEY_PATH = "key" + File.separatorChar;
	
	public static final String LICENSE_PATH = "license" + File.separatorChar;
	
	public static final String UKEY_PATH = "ukey" + File.separatorChar;
	
	public static final String ULICENSE_PATH = "ulicense" + File.separatorChar;
	
	public static final String PRIVATE_KEY_SUFFIX = "_private.key";
	
	public static final String PUBLIC_KEY_SUFFIX = "_public.key";
	
	public static final String LICENSE_SUFFIX = ".license";
	
	public static final String GEN_KEY_PYTHON = ROOT_PATH + "WEB-INF/classes/m2crypto_gen_key.py";
	
	public static final String GEN_LICENSE_PYTHON = ROOT_PATH + "WEB-INF/classes/license_creator.py";
	
}

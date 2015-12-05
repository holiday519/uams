package com.opzoon.license.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.opzoon.license.common.Constants;
import com.opzoon.license.common.FileUtil;
import com.opzoon.license.common.ShellUtil;
import com.opzoon.license.domain.ClusterUKey;
import com.opzoon.license.domain.SuiteLicense;
import com.opzoon.license.domain.User;
import com.opzoon.license.domain.VDILicense;
import com.opzoon.license.domain.VDIUKey;
import com.opzoon.license.domain.Version;
import com.opzoon.license.domain.request.ClusterUKeyRequest;
import com.opzoon.license.domain.request.LicenseRequest;
import com.opzoon.license.domain.request.LoginRequest;
import com.opzoon.license.domain.request.PasswordRequest;
import com.opzoon.license.domain.request.IdList;
import com.opzoon.license.domain.request.ProductRequest;
import com.opzoon.license.domain.request.UserRequest;
import com.opzoon.license.domain.request.VDIUKeyRequest;
import com.opzoon.license.domain.request.VersionRequest;
import com.opzoon.license.domain.response.BasicResponse;
import com.opzoon.license.exception.ExceptionManager;
import com.opzoon.license.exception.custom.ShellExecuteException;
import com.opzoon.license.service.ProductService;
import com.opzoon.license.service.SystemService;

@Controller
@RequestMapping("/*")
public class WsController {
	
	private static Logger log = Logger.getLogger(WsController.class);
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="loginUser", method=RequestMethod.POST)
	public @ResponseBody BasicResponse loginUser(@RequestBody @Valid LoginRequest login) {
		log.info("<=license=>controller, loginUser login = " + login);
		return systemService.loginUser(login);
	}
	
	@RequestMapping(value="logoutUser", method=RequestMethod.POST)
	public @ResponseBody BasicResponse logoutUser(HttpServletRequest request, HttpServletResponse response) {
		log.info("<=license=>controller, logoutUser");
		return systemService.logoutUser(request);
	}
	
	@RequestMapping(value="createUser", method=RequestMethod.POST)
	public @ResponseBody BasicResponse createUser(@RequestBody @Valid User user) {
		log.info("<=license=>controller, createUser user = " + user);
		return systemService.createUser(user);
	}
	
	@RequestMapping(value="listUsers", method=RequestMethod.POST)
	public @ResponseBody BasicResponse listUsers(@RequestBody UserRequest userReq) {
		log.info("<=license=>controller, listUsers userReq = " + userReq);
		return systemService.listUsers(userReq);
	}
	
	@RequestMapping(value="updateUser", method=RequestMethod.POST)
	public @ResponseBody BasicResponse updateUser(@RequestBody User user) {
		log.info("<=license=>controller, updateUser user = " + user);
		return systemService.updateUser(user);
	}
	
	@RequestMapping(value="deleteUsers", method=RequestMethod.POST)
	public @ResponseBody BasicResponse deleteUsers(@RequestBody IdList list) {
		log.info("<=license=>controller, deleteUsers ids = " + list);
		return systemService.deleteUsers(list.getIds());
	}
	
	@RequestMapping(value="modifyPassword", method=RequestMethod.POST)
	public @ResponseBody BasicResponse modifyPassword(@RequestBody PasswordRequest passwordReq) {
		log.info("<=license=>controller, modifyPassword passwordReq = " + passwordReq);
		return systemService.modifyPassword(passwordReq);
	}
	
	@RequestMapping(value="listProducts", method=RequestMethod.POST)
	public @ResponseBody BasicResponse listProducts(@RequestBody ProductRequest productReq) {
		log.info("<=license=>controller, listProducts productReq = " + productReq);
		return productService.listProducts(productReq);
	}
	
	@RequestMapping(value="listVersions", method=RequestMethod.POST)
	public @ResponseBody BasicResponse listVersions(@RequestBody VersionRequest versionReq) {
		log.info("<=license=>controller, listVersions versionReq = " + versionReq);
		return productService.listVersions(versionReq);
	}
	
	@RequestMapping(value="createVersion", method=RequestMethod.POST)
	public @ResponseBody BasicResponse createVersion(@RequestBody Version version) {
		log.info("<=license=>controller, createVersion version = " + version);
		return productService.createVersion(version);
	}
	
	@RequestMapping(value="deleteVersions", method=RequestMethod.POST)
	public @ResponseBody BasicResponse deleteVersions(@RequestBody IdList list) {
		log.info("<=license=>controller, deleteVersions ids = " + list);
		return productService.deleteVersions(list.getIds());
	}
	
	@RequestMapping(value="createSuiteLicense", method=RequestMethod.POST)
	public @ResponseBody BasicResponse createSuiteLicense(@RequestBody SuiteLicense license) {
		log.info("<=license=>controller, createSuiteLicense SuiteLicense = " + license);
		return productService.createLicense(license);
	}
	
	@RequestMapping(value="createVDILicense", method=RequestMethod.POST)
	public @ResponseBody BasicResponse createVDILicense(@RequestBody VDILicense license) {
		log.info("<=license=>controller, createVDILicense VDILicense = " + license);
		return productService.createLicense(license);
	}
	
	@RequestMapping(value="listLicenses", method=RequestMethod.POST)
	public @ResponseBody BasicResponse listLicenses(@RequestBody LicenseRequest licenseReq) {
		log.info("<=license=>controller, listLicenses licenseReq = " + licenseReq);
		return productService.listLicenses(licenseReq);
	}
	
	@RequestMapping(value="deleteLicenses", method=RequestMethod.POST)
	public @ResponseBody BasicResponse deleteLicenses(@RequestBody IdList list) {
		log.info("<=license=>controller, deleteLicenses ids = " + list);
		return productService.deleteLicenses(list.getIds());
	}
	
	@RequestMapping(value="createClusterUKey", method=RequestMethod.POST)
	public @ResponseBody BasicResponse createClusterUKey(@RequestBody ClusterUKey clusterUKey) {
		log.info("<=license=>controller, createClusterUKey clusterUKey = " + clusterUKey);
		return productService.createClusterUKey(clusterUKey);
	}
	
	@RequestMapping(value="listClusterUKeys", method=RequestMethod.POST)
	public @ResponseBody BasicResponse listClusterUKeys(@RequestBody ClusterUKeyRequest clusterUKeyReq) {
		log.info("<=license=>controller, listClusterUKeys clusterUKeyReq = " + clusterUKeyReq);
		return productService.listClusterUKeys(clusterUKeyReq);
	}
	
	@RequestMapping(value="deleteClusterUKeys", method=RequestMethod.POST)
	public @ResponseBody BasicResponse deleteClusterUKeys(@RequestBody IdList list) {
		log.info("<=license=>controller, deleteClusterUKeys ids = " + list);
		return productService.deleteClusterUKeys(list.getIds());
	}
	
	@RequestMapping(value="createVDIUKey", method=RequestMethod.POST)
	public @ResponseBody BasicResponse createVDIUKey(@RequestBody VDIUKey vdiUKey) {
		log.info("<=license=>controller, createVDIUKey vdiUKey = " + vdiUKey);
		return productService.createVDIUKey(vdiUKey);
	}
	
	@RequestMapping(value="listVDIUKeys", method=RequestMethod.POST)
	public @ResponseBody BasicResponse listVDIUKeys(@RequestBody VDIUKeyRequest vdiUKeyReq) {
		log.info("<=license=>controller, listVDIUKeys vdiUKeyReq = " + vdiUKeyReq);
		return productService.listVDIUKeys(vdiUKeyReq);
	}
	
	@RequestMapping(value="deleteVDIUKeys", method=RequestMethod.POST)
	public @ResponseBody BasicResponse deleteVDIUKeys(@RequestBody IdList list) {
		log.info("<=license=>controller, deleteVDIUKeys ids = " + list);
		return productService.deleteVDIUKeys(list.getIds());
	}
	
	@RequestMapping(value="uploadUKey", method=RequestMethod.POST)
	public @ResponseBody BasicResponse uploadUKey(@RequestParam MultipartFile file) {
		log.info("<=license=>controller, uploadUKey file = " + file.getName());
		return productService.uploadUKey(file);
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody BasicResponse handleException(Exception exception) {
		log.info("<=license=>handleException exception=" + exception);
		return new BasicResponse(ExceptionManager.convertToCode(exception));
	}
	
	@PostConstruct
	public void initSystem() {
		log.info("<=license=>initSystem " + Constants.ROOT_PATH);
		// 创建key和license的存储目录
		FileUtil.createDir(Constants.ROOT_PATH + Constants.KEY_PATH);
		FileUtil.createDir(Constants.ROOT_PATH + Constants.LICENSE_PATH);
		FileUtil.createDir(Constants.ROOT_PATH + Constants.UKEY_PATH);
		FileUtil.createDir(Constants.ROOT_PATH + Constants.ULICENSE_PATH);
		// 给shell脚本赋予执行权限
//		if(ShellUtil.hasExecuteAuthority(Constants.GEN_KEY_PYTHON)) {
//			ShellUtil.addExecuteAuthority(Constants.GEN_KEY_PYTHON);
//			ShellUtil.changeDosToUnix(Constants.GEN_KEY_PYTHON);
//		}
//		if(ShellUtil.hasExecuteAuthority(Constants.GEN_LICENSE_PYTHON)) {
//			ShellUtil.addExecuteAuthority(Constants.GEN_LICENSE_PYTHON);
//			ShellUtil.changeDosToUnix(Constants.GEN_LICENSE_PYTHON);
//		}
	}
	
}

package com.opzoon.license.service;

import org.springframework.web.multipart.MultipartFile;

import com.opzoon.license.domain.ClusterUKey;
import com.opzoon.license.domain.License;
import com.opzoon.license.domain.VDIUKey;
import com.opzoon.license.domain.Version;
import com.opzoon.license.domain.request.ClusterUKeyRequest;
import com.opzoon.license.domain.request.LicenseRequest;
import com.opzoon.license.domain.request.ProductRequest;
import com.opzoon.license.domain.request.VDIUKeyRequest;
import com.opzoon.license.domain.request.VersionRequest;
import com.opzoon.license.domain.response.BasicResponse;
import com.opzoon.license.exception.BasicException;

public interface ProductService {
	
	public BasicResponse listProducts(ProductRequest productReq) throws BasicException;

	public BasicResponse createVersion(Version version) throws BasicException;
	
	public BasicResponse listVersions(VersionRequest versionReq) throws BasicException;
	
	public BasicResponse deleteVersions(Integer[] ids) throws BasicException;
	
	public BasicResponse createLicense(License license) throws BasicException;
	
	public BasicResponse listLicenses(LicenseRequest licenseReq) throws BasicException;
	
	public BasicResponse deleteLicenses(Integer[] ids) throws BasicException;
	
	public BasicResponse listClusterUKeys(ClusterUKeyRequest clusterUKeyReq) throws BasicException;
	
	public BasicResponse createClusterUKey(ClusterUKey clusterUKey) throws BasicException;
	
	public BasicResponse deleteClusterUKeys(Integer[] ids) throws BasicException;
	
	public BasicResponse listVDIUKeys(VDIUKeyRequest vdiUKeyReq) throws BasicException;
	
	public BasicResponse createVDIUKey(VDIUKey vdiUKey) throws BasicException;
	
	public BasicResponse deleteVDIUKeys(Integer[] ids) throws BasicException;
	
	public BasicResponse uploadUKey(MultipartFile file) throws BasicException;
	
}

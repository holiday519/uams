package com.opzoon.license.dao;

import java.util.List;

import com.opzoon.license.domain.ClusterUKey;
import com.opzoon.license.domain.License;
import com.opzoon.license.domain.Product;
import com.opzoon.license.domain.VDIUKey;
import com.opzoon.license.domain.Version;
import com.opzoon.license.domain.request.ClusterUKeyRequest;
import com.opzoon.license.domain.request.LicenseRequest;
import com.opzoon.license.domain.request.ProductRequest;
import com.opzoon.license.domain.request.VDIUKeyRequest;
import com.opzoon.license.domain.request.VersionRequest;
import com.opzoon.license.exception.BasicException;

public interface ProductDao {

	public List<Product> findProducts(ProductRequest productReq) throws BasicException;
	
	public int getProductAmount(ProductRequest productReq) throws BasicException;
	
	public void addVersion(Version version) throws BasicException;
	
	public List<Version> findVersions(VersionRequest versionReq) throws BasicException;
	
	public void deleteVersions(Integer[] ids) throws BasicException;
	
	public int getVersionAmount(VersionRequest versionReq) throws BasicException;
	
	public void addLicense(License license) throws BasicException;
	
	public List<License> findLicenses(LicenseRequest licenseReq) throws BasicException;
	
	public void deleteLicenses(Integer[] ids) throws BasicException;
	
	public int getLicenseAmount(LicenseRequest licenseReq) throws BasicException;
	
	public List<ClusterUKey> findClusterUKeys(ClusterUKeyRequest clusterUKeyReq) throws BasicException;
	
	public int getClusterUKeyAmount(ClusterUKeyRequest clusterUKeyReq) throws BasicException;
	
	public void addClusterUKey(ClusterUKey clusterUKey) throws BasicException;
	
	public void deleteClusterUKeys(Integer[] ids) throws BasicException;
	
	public List<VDIUKey> findVDIUKeys(VDIUKeyRequest vdiUKeyReq) throws BasicException;
	
	public int getVDIUKeyAmount(VDIUKeyRequest vdiUKeyReq) throws BasicException;
	
	public void addVDIUKey(VDIUKey vdiUKey) throws BasicException;
	
	public void deleteVDIUKeys(Integer[] ids) throws BasicException;
}

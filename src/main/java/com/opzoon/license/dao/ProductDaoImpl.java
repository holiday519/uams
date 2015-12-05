package com.opzoon.license.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.opzoon.license.common.HqlUtil;
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
import com.opzoon.license.exception.custom.ResourceConflictException;

@Repository("productDao")
public class ProductDaoImpl implements ProductDao {
	
	private static Logger log = Logger.getLogger(ProductDaoImpl.class); 
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findProducts(final ProductRequest productReq) throws BasicException {
		log.info("<=license=>Dao findProducts productReq=" + productReq);
		final String hql = HqlUtil.createSelHql(productReq.getProduct()) + " " 
						+ HqlUtil.createOrderHql(productReq.getPage());
		log.info("<=license=>Dao findProducts hql is " + hql);
		
		return hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			
			@Override
			public List<Product> doInHibernate(Session session) throws HibernateException, SQLException {
				if(productReq.getPage().getPageSize() == -1) {
					return session.createQuery(hql).list();
				}
				return session.createQuery(hql).setFirstResult(productReq.getPage().getPageNo()-1)
						.setMaxResults(productReq.getPage().getPageSize()).list();
			}
			
		});
	}
	
	@Override
	public int getProductAmount(ProductRequest productReq) throws BasicException {
		log.info("<=license=>Dao getProductAmount productReq=" + productReq);
		String hql = HqlUtil.createCountHql(productReq.getProduct());
		log.info("<=license=>Dao getProductAmount hql is " + hql);
		
		return ((Long)hibernateTemplate.find(hql).get(0)).intValue();
	}
	
	@Override
	public void addVersion(Version version) throws BasicException {
		log.info("<=license=>Dao addVersion version=" + version);
		if(isConflictedByVersion(version.getVersionNo(), version.getProduct().getProductName())) {
			throw new ResourceConflictException();
		}
		hibernateTemplate.save(version);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Version> findVersions(final VersionRequest versionReq) throws BasicException {
		log.info("<=license=>Dao findVersions versionReq=" + versionReq);
		final String hql = HqlUtil.createSelHql(versionReq.getVersion()) + " " 
						+ HqlUtil.createOrderHql(versionReq.getPage());
		log.info("<=license=>Dao findVersions hql is " + hql);
		
		return hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			
			@Override
			public List<Version> doInHibernate(Session session) throws HibernateException, SQLException {
				if(versionReq.getPage().getPageSize() == -1) {
					return session.createQuery(hql).list();
				}
				return session.createQuery(hql).setFirstResult(versionReq.getPage().getPageNo()-1)
						.setMaxResults(versionReq.getPage().getPageSize()).list();
			}
			
		});
	}

	@Override
	public void deleteVersions(final Integer[] ids) throws BasicException {
		log.info("<=license=>Dao deleteVersions ids=" + ids);
		final String hql = "delete Version where id in (:ids)";
		
		hibernateTemplate.execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
			
		});
	}

	@Override
	public int getVersionAmount(VersionRequest versionReq) throws BasicException {
		log.info("<=license=>Dao getVersionAmount versionReq=" + versionReq);
		String hql = HqlUtil.createCountHql(versionReq.getVersion());
		log.info("<=license=>Dao getVersionAmount hql is " + hql);
		
		return ((Long)hibernateTemplate.find(hql).get(0)).intValue();
	}

	@Override
	public void addLicense(License license) throws BasicException {
		log.info("<=license=>Dao addLicense license=" + license);
		if(isConflictedByLicense(license.getLicenseName())) {
			throw new ResourceConflictException();
		}
		hibernateTemplate.save(license);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<License> findLicenses(final LicenseRequest licenseReq) throws BasicException {
		log.info("<=license=>Dao findLicenses licenseReq=" + licenseReq);
		final String hql = HqlUtil.createSelHql(licenseReq.getLicense()) + " " 
						+ HqlUtil.createOrderHql(licenseReq.getPage());
		log.info("<=license=>Dao findLicenses hql is " + hql);
		
		return hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			
			@Override
			public List<License> doInHibernate(Session session) throws HibernateException, SQLException {
				if(licenseReq.getPage().getPageSize() == -1) {
					return session.createQuery(hql).list();
				}
				return session.createQuery(hql).setFirstResult(licenseReq.getPage().getPageNo()-1)
						.setMaxResults(licenseReq.getPage().getPageSize()).list();
			}
			
		});
	}

	@Override
	public void deleteLicenses(final Integer[] ids) throws BasicException {
		log.info("<=license=>Dao deleteLicenses ids=" + ids);
		final String hql = "delete License where id in (:ids)";
		
		hibernateTemplate.execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
			
		});
	}

	@Override
	public int getLicenseAmount(LicenseRequest licenseReq) throws BasicException {
		log.info("<=license=>Dao getLicenseAmount licenseReq=" + licenseReq);
		String hql = HqlUtil.createCountHql(licenseReq.getLicense());
		log.info("<=license=>Dao getLicenseAmount hql is " + hql);
		
		return ((Long)hibernateTemplate.find(hql).get(0)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClusterUKey> findClusterUKeys(final ClusterUKeyRequest clusterUKeyReq)
			throws BasicException {
		log.info("<=license=>Dao findClusterUKeys clusterUKeyReq=" + clusterUKeyReq);
		final String hql = HqlUtil.createSelHql(clusterUKeyReq.getClusterUKey()) + " " 
						+ HqlUtil.createOrderHql(clusterUKeyReq.getPage());
		log.info("<=license=>Dao findClusterUKeys hql is " + hql);
		
		return hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			
			@Override
			public List<ClusterUKey> doInHibernate(Session session) throws HibernateException, SQLException {
				if(clusterUKeyReq.getPage().getPageSize() == -1) {
					return session.createQuery(hql).list();
				}
				return session.createQuery(hql).setFirstResult(clusterUKeyReq.getPage().getPageNo()-1)
						.setMaxResults(clusterUKeyReq.getPage().getPageSize()).list();
			}
			
		});
	}

	@Override
	public int getClusterUKeyAmount(ClusterUKeyRequest clusterUKeyReq)
			throws BasicException {
		log.info("<=license=>Dao getClusterUKeyAmount clusterUKeyReq=" + clusterUKeyReq);
		String hql = HqlUtil.createCountHql(clusterUKeyReq.getClusterUKey());
		log.info("<=license=>Dao getClusterUKeyAmount hql is " + hql);
		
		return ((Long)hibernateTemplate.find(hql).get(0)).intValue();
	}

	@Override
	public void addClusterUKey(ClusterUKey clusterUKey) throws BasicException {
		log.info("<=license=>Dao addClusterUKey clusterUKey=" + clusterUKey);
		if(isConflictedByClusterUKey(clusterUKey.getLicenseName())) {
			throw new ResourceConflictException();
		}
		hibernateTemplate.save(clusterUKey);
	}

	@Override
	public void deleteClusterUKeys(final Integer[] ids) throws BasicException {
		log.info("<=license=>Dao deleteClusterUKeys ids=" + ids);
		final String hql = "delete ClusterUKey where id in (:ids)";
		
		hibernateTemplate.execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
			
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VDIUKey> findVDIUKeys(final VDIUKeyRequest vdiUKeyReq)
			throws BasicException {
		log.info("<=license=>Dao findVDIUKeys vdiUKeyReq=" + vdiUKeyReq);
		final String hql = HqlUtil.createSelHql(vdiUKeyReq.getVdiUKey()) + " " 
						+ HqlUtil.createOrderHql(vdiUKeyReq.getPage());
		log.info("<=license=>Dao findVDIUKeys hql is " + hql);
		
		return hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			
			@Override
			public List<VDIUKey> doInHibernate(Session session) throws HibernateException, SQLException {
				if(vdiUKeyReq.getPage().getPageSize() == -1) {
					return session.createQuery(hql).list();
				}
				return session.createQuery(hql).setFirstResult(vdiUKeyReq.getPage().getPageNo()-1)
						.setMaxResults(vdiUKeyReq.getPage().getPageSize()).list();
			}
			
		});
	}

	@Override
	public int getVDIUKeyAmount(VDIUKeyRequest vdiUKeyReq)
			throws BasicException {
		log.info("<=license=>Dao getVDIUKeyAmount vdiUKeyReq=" + vdiUKeyReq);
		String hql = HqlUtil.createCountHql(vdiUKeyReq.getVdiUKey());
		log.info("<=license=>Dao getVDIUKeyAmount hql is " + hql);
		
		return ((Long)hibernateTemplate.find(hql).get(0)).intValue();
	}

	@Override
	public void addVDIUKey(VDIUKey vdiUKey) throws BasicException {
		log.info("<=license=>Dao addVDIUKey vdiUKey=" + vdiUKey);
		if(isConflictedByVDIUKey(vdiUKey.getLicenseName())) {
			throw new ResourceConflictException();
		}
		hibernateTemplate.save(vdiUKey);
	}

	@Override
	public void deleteVDIUKeys(final Integer[] ids) throws BasicException {
		log.info("<=license=>Dao deleteVDIUKeys ids=" + ids);
		final String hql = "delete VDIUKey where id in (:ids)";
		
		hibernateTemplate.execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setParameterList("ids", ids);
				return query.executeUpdate();
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	private boolean isConflictedByVersion(String versionNo, String productName) {
		log.info("<=license=>Dao isConflictedByVersion");
		String hql = "from Version version where version.versionNo='" + versionNo
				+ "' and version.product.productName='" + productName + "'";
		List<Version> versions = hibernateTemplate.find(hql);
		
		return versions.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isConflictedByLicense(String licenseName) {
		log.info("<=license=>Dao isConflictedByLicense");
		String hql = "from License license where license.licenseName='" + licenseName + "'";
		List<License> licenses = hibernateTemplate.find(hql);
		
		return licenses.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isConflictedByClusterUKey(String licenseName) {
		log.info("<=license=>Dao isConflictedByClusterUKey");
		String hql = "from ClusterUKey clusterUKey where clusterUKey.licenseName='" + licenseName + "'";
		List<ClusterUKey> clusterUKeys = hibernateTemplate.find(hql);
		
		return clusterUKeys.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	private boolean isConflictedByVDIUKey(String licenseName) {
		log.info("<=license=>Dao isConflictedByVDIUKey");
		String hql = "from VDIUKey vdiUKey where vdiUKey.licenseName='" + licenseName + "'";
		List<VDIUKey> vdiUKeys = hibernateTemplate.find(hql);
		
		return vdiUKeys.size() > 0;
	}
}

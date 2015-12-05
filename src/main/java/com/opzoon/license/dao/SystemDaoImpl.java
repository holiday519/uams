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
import com.opzoon.license.domain.User;
import com.opzoon.license.domain.request.PasswordRequest;
import com.opzoon.license.domain.request.UserRequest;
import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.custom.ResourceConflictException;
import com.opzoon.license.exception.custom.ResourceNotFoundException;

@Repository("systemDao")
public class SystemDaoImpl implements SystemDao {
	
	private static Logger log = Logger.getLogger(SystemDaoImpl.class); 
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Override
	public void addUser(User user) throws BasicException {
		log.info("<=license=>Dao addUser user=" + user);
		if(userIsExisted(user.getUserName())) {
			throw new ResourceConflictException();
		}
		hibernateTemplate.save(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsers(final UserRequest userReq) throws BasicException {
		log.info("<=license=>Dao findUsers userRequest=" + userReq);
		final String hql = HqlUtil.createSelHql(userReq.getUser()) + " " 
						+ HqlUtil.createOrderHql(userReq.getPage()); 
		log.info("<=license=>Dao findUsers hql is " + hql);
		
		return hibernateTemplate.executeFind(new HibernateCallback<Object>() {
			
			@Override
			public List<User> doInHibernate(Session session) throws HibernateException, SQLException {
				if(userReq.getPage().getPageSize() == -1) {
					return session.createQuery(hql).list();
				}
				return session.createQuery(hql).setFirstResult((userReq.getPage().getPageNo()-1)*userReq.getPage().getPageSize())
						.setMaxResults(userReq.getPage().getPageSize()).list();
			}
			
		});
	}

	@Override
	public void updateUser(User user) throws BasicException {
		log.info("<=license=>Dao updateUser user=" + user);
		if(!userIsExisted(user.getUserId())) {
			throw new ResourceNotFoundException(); 
		}
		hibernateTemplate.update(user);
	}

	@Override
	public void deleteUsers(final Integer[] ids) throws BasicException {
		log.info("<=license=>Dao deleteUsers ids=" + ids);
		final String hql = "delete User where id in (:ids)";
		
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
	public int getUserAmount(UserRequest userReq) throws BasicException {
		log.info("<=license=>Dao getUserAmount userRequest=" + userReq);
		String hql = HqlUtil.createCountHql(userReq.getUser());
		log.info("<=license=>Dao getUserAmount hql is " + hql);
		
		return ((Long)hibernateTemplate.find(hql).get(0)).intValue();
	}

	@Override
	public void modifyPassword(PasswordRequest passwordReq) throws BasicException {
		log.info("<=license=>Dao modifyPassword passwordReq=" + passwordReq);
		if(!userIsExisted(passwordReq.getUserId())) {
			throw new ResourceNotFoundException();
		}
		final String hql = "update User user set user.password='" + passwordReq.getPassword() + "' where user.userId=" + passwordReq.getUserId();
		
		hibernateTemplate.execute(new HibernateCallback<Object>() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql).executeUpdate();
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	private boolean userIsExisted(String userName) {
		log.info("<=license=>Dao userIsExisted");
		if(userName == null) {
			return false;
		}
		String hql = "from User user where user.userName='" + userName + "'";
		List<User> users = hibernateTemplate.find(hql);
		
		return users.size() > 0;
	}
	
	@SuppressWarnings("unchecked")
	private boolean userIsExisted(Integer userId) {
		log.info("<=license=>Dao userIsExisted");
		if(userId == null) {
			return false;
		}
		String hql = "from User user where user.userId='" + userId + "'";
		List<User> users = hibernateTemplate.find(hql);
		
		return users.size() > 0;
	}
	
}

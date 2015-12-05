package com.opzoon.license.common;

import java.lang.reflect.Field;

import com.opzoon.license.domain.PageModel;
import com.opzoon.license.exception.BasicException;
import com.opzoon.license.exception.custom.HqlUtilReflectException;

public class HqlUtil {

	public static String createSelHql(Object obj) throws BasicException {
		
		return "from " + obj.getClass().getSimpleName() + " o where 1=1" + getConditionHql(obj, "o");
	}
	
	public static String createOrderHql(PageModel pageModel) {
		if(pageModel.getSortkey() == null || "".equals(pageModel.getSortkey())) {
			return "";
		}
		StringBuilder hql = new StringBuilder();
		hql.append("order by o." + pageModel.getSortkey());
		
		if(pageModel.getAscend() == 0) {
			hql.append(" desc");
		}
		return hql.toString();
	}
	
	public static String createCountHql(Object obj) throws BasicException {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from " + obj.getClass().getSimpleName() + " o where 1=1");
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object tmpObj;
			try {
				tmpObj = field.get(obj);
			} catch (Exception e) {
				throw new HqlUtilReflectException(e);
			} 
			if(tmpObj != null) {
				hql.append(" and o." + field.getName() + "='" + tmpObj + "'");
			}
		}
		return hql.toString();
	}
	
	private static String getConditionHql(Object obj, String parentName) throws BasicException {
		StringBuilder hql = new StringBuilder();
		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object tmpObj;
			try {
				tmpObj = field.get(obj);
			} catch (Exception e) {
				throw new HqlUtilReflectException(e);
			} 
			if(tmpObj != null) {
				String fieldName = field.getName();
				Object fieldObj = tmpObj;
				if(isBasicType(fieldObj)) {
					hql.append(" and " + parentName + "." + fieldName + "='" + fieldObj + "'");
				}else {
					// 迭代生成hql
					hql.append(getConditionHql(fieldObj, parentName + "." + fieldName));
				}
			}
		}
		
		return hql.toString();
	}
	
	private static Boolean isBasicType(Object obj) {
		if(obj instanceof Boolean || obj instanceof Character || obj instanceof Byte 
				|| obj instanceof Short || obj instanceof Integer || obj instanceof Long 
				|| obj instanceof Double || obj instanceof Float || obj instanceof String) {
			return true;
		}
		return false;
	}
	
}

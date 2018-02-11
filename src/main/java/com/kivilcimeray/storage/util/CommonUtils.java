package com.kivilcimeray.storage.util;

import org.springframework.util.StringUtils;

public class CommonUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T objToTargetType(String object, String className) {
		try {
			if(StringUtils.isEmpty(object) || StringUtils.isEmpty(className)) {
				return null;
			}
			Class<?> type = Class.forName("java.lang." + className);
			Object targetValue = null;
			
			if(type.equals(String.class)) {
				targetValue = object.toString();
			} else if(type.equals(Boolean.class)) {
				targetValue = object.equals("true") ? true : object.equals("false") ? false : null;
			} else if (type.equals(Double.class)) {
				targetValue = Double.valueOf(object.toString());
			} else if (type.equals(Integer.class)) {
				targetValue = Integer.valueOf(object.toString());
			}
			return (T) targetValue;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

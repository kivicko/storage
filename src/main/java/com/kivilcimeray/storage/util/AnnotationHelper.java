package com.kivilcimeray.storage.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

public class AnnotationHelper {

	@SuppressWarnings("unchecked")
	public static void changeAnnotationValue(Annotation annotation, String key, Object newValue) {
		try {
			Object handler = Proxy.getInvocationHandler(annotation);
			Field f = handler.getClass().getDeclaredField("memberValues");
			f.setAccessible(true);
			Map<String, Object> memberValues = (Map<String, Object>) f.get(handler);
			Object oldValue = memberValues.get(key);
			if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
				throw new Exception();
			}
			memberValues.put(key, newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

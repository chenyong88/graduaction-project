package co.cy.util;

import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class TableEntityMapperUtil {
	
	public static String mapperToEntity(String name) {
		StringBuffer result = new StringBuffer();
		result.append(Character.toUpperCase(name.charAt(0)));
		for (int i = 1; i < name.toCharArray().length; i++) {
			char ch = name.charAt(i);
			if (ch == '_') {
				if (i+1 < name.toCharArray().length && Character.isLowerCase(name.charAt(i+1))) {
					result.append(Character.toUpperCase(name.charAt(i+1)));
					i++;
					continue;
				}
			}
			result.append(ch);
		}
		return result.toString();
	}
	
	public static String mapperToProperty(String name) {
		StringBuffer result = new StringBuffer();
		result.append(Character.toLowerCase(name.charAt(0)));
		for (int i = 1; i < name.toCharArray().length; i++) {
			char ch = name.charAt(i);
			if (ch == '_') {
				if (i+1 < name.toCharArray().length && Character.isLowerCase(name.charAt(i+1))) {
					result.append(Character.toUpperCase(name.charAt(i+1)));
					i++;
					continue;
				}
			}
			result.append(ch);
		}
		return result.toString();
	}
	
	public static String mapperToDB(String name) {
		StringBuffer result = new StringBuffer();
		result.append(Character.toLowerCase(name.charAt(0)));
		for (int i = 1; i < name.toCharArray().length; i++) {
			char ch = name.charAt(i);
			if (Character.isUpperCase(ch)) {
				result.append("_");
				result.append(Character.toLowerCase(ch));
			} else {
				result.append(ch);
			}
		}
		return result.toString();
	}
	
	public static Map<String, Object> beanToMap(Object source, String... ignoreProperties) {
		Assert.notNull(source, "Source must not be null");

		Class<?> beanClass = source.getClass();
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		Map<String, Object> result = new HashMap<String, Object>();
		
		PropertyDescriptor propertyDescriptor = null;
		for (Field field : beanClass.getDeclaredFields()) {
			
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			
			try {
				propertyDescriptor = new PropertyDescriptor(field.getName(), beanClass);
				Method readMethod = propertyDescriptor.getReadMethod();
				
				if (readMethod == null 
					|| (ignoreList != null && ignoreList.contains(propertyDescriptor.getName()))	
					|| field.getAnnotation(Transient.class) != null 
					|| readMethod.getAnnotation(Transient.class) != null) {
					continue;
				} 
				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
					readMethod.setAccessible(true);
				}
				Object value = readMethod.invoke(source);
				//不复制null值
				if (value == null) {
					continue;
				}
				result.put(field.getName(), value);
			} catch (Throwable ex) {
				throw new FatalBeanException(
						"Could not copy property '" + propertyDescriptor.getName() + "' from source to map", ex);
			}
			
		}
		return result;
	}
}

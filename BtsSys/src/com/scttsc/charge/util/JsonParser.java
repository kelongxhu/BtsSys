package com.scttsc.charge.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;


/**
 *
 * @describe json解析器,主要针对Message的body相应解析
 */
public class JsonParser {
	private final static Logger LOG = Logger.getLogger(JsonParser.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	static {
		// config init ...
	}
	
	/**
	 * 将对象转化为String
	 * @param obj
	 * @return String
	 */
	public static String toString(Object obj) {
		String result = null;
		try {
			result = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return result;
	}
	
	/**
	 * 将对象转json写入文件
	 * @param obj
	 * @return String
	 */
	public static void toFile(File file, Object obj) {
		try {
			mapper.writeValue(file, obj);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 将json字符串转化为相应的对象实体
	 * @param jsonStr class
	 * @return Message
	 */
	public static Object jsonStrToObj(String jsonStr, Class<?> clazz) {
		Object obj = null;
		try {
			obj = mapper.readValue(jsonStr, clazz);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return obj;
	}
}
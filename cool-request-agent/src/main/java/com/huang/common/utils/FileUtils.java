package com.huang.common.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class FileUtils {

	public static byte[] getClassResourceBytes(Class<?> clazz, String classPath) {
		try(InputStream stream = clazz.getResourceAsStream(classPath)) {
			assert stream != null;
			int len = stream.available();
			byte[] bytes = new byte[len];
			int writIndex = 0;
			int count;
			while ((count = stream.read(bytes, writIndex, len - writIndex)) > 0) {
				writIndex += count;
			}
			return bytes;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

package com.huang.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class FileUtils {

	public static byte[] getClassResourceBytes(Class<?> clazz, String classPath) {
		URL resource = clazz.getResource(classPath);
		try(InputStream stream = resource.openStream()) {
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

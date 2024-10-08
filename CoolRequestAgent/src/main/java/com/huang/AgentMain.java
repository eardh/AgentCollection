package com.huang;

import com.huang.net.MessageClient;

import java.io.*;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class AgentMain {

	public static void agentmain(String agentArgs, Instrumentation inst) {
		try {
			List<ClassDefinition> classDefinitionList = new ArrayList<>();
			for (Class aClass : inst.getAllLoadedClasses()) {
				if (aClass.getName().contains("LicenseDialogWrapper")) {
					classDefinitionList.add(new ClassDefinition(aClass, fileToByte("/breakClasses/LicenseDialogWrapper.class")));
				}
			}
			if (classDefinitionList.isEmpty()) {
				throw new Exception();
			}
			inst.redefineClasses(classDefinitionList.toArray(new ClassDefinition[0]));
			MessageClient.sendMessage(1);
		} catch (Throwable e) {
			MessageClient.sendMessage(0);
		}
	}

	private static byte[] fileToByte(String classPath) {
		URL resource = AgentMain.class.getResource(classPath);
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

package com.huang.transformer;

import com.huang.AgentMain;
import com.huang.common.constant.AgentContext;
import com.huang.common.utils.FileUtils;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class DefaultClassFileTransformer implements ClassFileTransformer {

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
		if (!AgentContext.RELOAD_CLASS_MAP.containsKey(className.replaceAll("/", "."))) {
			return classfileBuffer;
		}
		return FileUtils.getClassResourceBytes(AgentMain.class, "/lib/" + AgentContext.RELOAD_CLASS_MAP.get(className.replaceAll("/", ".")));
	}

}

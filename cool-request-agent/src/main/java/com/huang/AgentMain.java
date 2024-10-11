package com.huang;

import com.huang.common.constant.AgentContext;
import com.huang.net.MessageClient;
import com.huang.transformer.DefaultClassFileTransformer;
import java.lang.instrument.*;
import java.util.*;


/**
 * @Author lei.huang
 * @Description TODO
 **/
public class AgentMain {

	public static void agentmain(String agentArgs, Instrumentation inst) {
		doAgentmain(agentArgs, inst);
	}

	private synchronized static void doAgentmain(String agentArgs, Instrumentation inst) {
		Map<String, String> paramMap = parseArgs(agentArgs);
		int port = Integer.parseInt(paramMap.getOrDefault("port", "9527"));
		try {
			Class.forName("com.huang.common.constant.AgentContext");
			if (AgentContext.ATTACHED) {
				MessageClient.sendMessage(port, 1);
				return;
			}
			inst.addTransformer(new DefaultClassFileTransformer(), true);
			inst.retransformClasses(reloadLoadedClass(inst).toArray(new Class[0]));
			MessageClient.sendMessage(port, 1);
			AgentContext.ATTACHED = true;
		} catch (Throwable e) {
			MessageClient.sendMessage(port, 0);
		}
	}

	private static Map<String, String> parseArgs(String agentArgs) {
		Map<String, String> map = new HashMap<>();
		String[] paramList = agentArgs.trim().split(";");
		for (String param : paramList) {
			String[] tuples = param.trim().split("=");
			map.put(tuples[0], tuples[1]);
		}
		return map;
	}

	private static List<Class<?>> reloadLoadedClass(Instrumentation inst) throws UnmodifiableClassException {
		List<Class<?>> classList = new ArrayList<>();
		for (Class<?> aClass : inst.getAllLoadedClasses()) {
			String className = aClass.getName();
			if (!AgentContext.RELOAD_CLASS_MAP.containsKey(className)) {
				continue;
			}
			classList.add(aClass);
		}
		return classList;
	}

}

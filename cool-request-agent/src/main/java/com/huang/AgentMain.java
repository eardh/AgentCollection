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
		main(agentArgs, inst);
	}

	private synchronized static void main(String agentArgs, Instrumentation inst) {
		try {
			Class.forName("com.huang.common.constant.AgentContext");
			if (AgentContext.ATTACHED) {
				MessageClient.sendMessage(1);
				return;
			}
			inst.addTransformer(new DefaultClassFileTransformer(), true);
			inst.retransformClasses(reloadLoadedClass(inst).toArray(new Class[0]));
			MessageClient.sendMessage(1);
		} catch (Throwable e) {
			MessageClient.sendMessage(e.toString());
			MessageClient.sendMessage(0);
		} finally {
			AgentContext.ATTACHED = true;
		}
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

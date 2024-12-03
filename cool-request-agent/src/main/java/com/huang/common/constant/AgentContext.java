package com.huang.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author lei.huang
 * @Description TODO
 **/
public class AgentContext {

	public static volatile boolean ATTACHED = false;

	public static Map<String, String> RELOAD_CLASS_MAP = new HashMap<>();

	static {
		// CoolRequest plugin
		RELOAD_CLASS_MAP.put("com.cool.request.vip.LicenseUtils", "LicenseUtils.class");
		RELOAD_CLASS_MAP.put("com.cool.request.vip.FeatureManager", "FeatureManager.class");

		// SequenceDiagram plugin
		RELOAD_CLASS_MAP.put("vanstudio.sequence.c.b", "b.class");
	}

}

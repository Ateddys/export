package com.xiaohan.cn.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 校验时判断集合中的数据是否存在重复
 *
 * @author teddy
 * @since 2012/12/01
 *
 */
public class ListUniqUtils {

	/**
	 * ThrealLocal维护Map校验是否重复，InherTableThreadLocal解决了子线程的问题，但是对于线程池未解决，使用TransmittableThreadLocal
	 */
	public static final ThreadLocal<Map<String,Set<Object>>> UNIQ_VALIDATE=new TransmittableThreadLocal<>();
	
	/**
	 * 手动调用校验的过程中，如需要开启重复校验时调用该方法
	 */
	public static void start() {
		UNIQ_VALIDATE.set(new HashMap<>());
		isUniq("uniqValid", true);
	}
	/**
	 * 判断是否开启重复校验
	 * @return
	 */
	public static boolean hasStart() {
		return (UNIQ_VALIDATE.get()!=null)&&(!UNIQ_VALIDATE.get().keySet().isEmpty());
	}
	
	/**
	 * 判断该数据是否重复
	 * @param k
	 * @param v
	 * @return
	 */
	public static boolean isUniq(String k,Object v) {
		boolean isUniq=false;
		if(UNIQ_VALIDATE.get().keySet().contains(k)) {
			if(UNIQ_VALIDATE.get().get(k).contains(v)) {
				isUniq=true;
			}else {
				UNIQ_VALIDATE.get().get(k).add(v);
			}
		}else {
			Set<Object> set = new HashSet<>();
			set.add(v);
			UNIQ_VALIDATE.get().put(k, set);
		}
		return isUniq;
	}
	
	/**
	 * 校验接受调用，释放资源
	 */
	public static void end() {
		UNIQ_VALIDATE.remove();
	}
}

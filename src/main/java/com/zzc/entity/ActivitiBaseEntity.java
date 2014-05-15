package com.zzc.entity;

import java.io.Serializable;
import java.util.Map;

import javax.resource.spi.CommException;

/**
 * 调用流程需要构建的基类
 * @author zhengzhichao
 *
 */
public abstract class ActivitiBaseEntity implements Serializable {
	/**
	 * 流程定义的id
	 */
	protected String id;
	
	/**
	 * 订单id
	 */
	protected String orderId;
	
	/**
	 * 处理参数
	 * 此方法由子类实现，启动流程前校验参数并将参数转化为Map作为流程绑定参数
	 * @return
	 */
	public abstract Map<String, Object> processParam() throws CommException;

	public String getId() {
		return id;
	}

	public String getOrderId() {
		return orderId;
	}
}

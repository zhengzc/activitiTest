package com.zzc.entity;

import java.util.List;
import java.util.Map;


/**
 * 调用审批流程需要传入的参数类
 * 与流程绑定
 * @author zhengzhichao
 *
 */
public class ApproveTestEntity extends ActivitiBaseEntity {

	private Boolean deptFlag;
	private Boolean deptRet;
	private List<List<String>> deptApprovers;
	private String outParam;
	
	public ApproveTestEntity(String processId,String orderId){
		this.id = processId;
		this.orderId = orderId;
	}
	
	@Override
	public Map<String, Object> processParam() {
		return null;
	}

	public Boolean getDeptFlag() {
		return deptFlag;
	}

	public void setDeptFlag(Boolean deptFlag) {
		this.deptFlag = deptFlag;
	}

	public Boolean getDeptRet() {
		return deptRet;
	}

	public void setDeptRet(Boolean deptRet) {
		this.deptRet = deptRet;
	}

	public List<List<String>> getDeptApprovers() {
		return deptApprovers;
	}

	public void setDeptApprovers(List<List<String>> deptApprovers) {
		this.deptApprovers = deptApprovers;
	}

	public String getOutParam() {
		return outParam;
	}

	public void setOutParam(String outParam) {
		this.outParam = outParam;
	}

	
}

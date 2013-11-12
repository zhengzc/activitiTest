package com.zzc.activiti.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service(value="res")
public class Res {
	private Map<String, String> res =  new HashMap<String, String>();
	public Res(){
		res.put("bussinessTripForm", 
				"<table>\n" +
				"  <tr>\n" + 
				"    <td>申请人：</td>\n" + 
				"    <td><input type=\"text\" id=\"user\" name=\"user\" /></td>\n" + 
				"    <td>出差费用：</td>\n" + 
				"    <td><input type=\"text\" id=\"fee\" name=\"fee\" /></td>\n" + 
				"  </tr>\n" + 
				"  <tr>\n" + 
				"    <td>邮箱：</td>\n" + 
				"    <td><input type=\"text\" id=\"email\" name=\"email\" /></td>\n" + 
				"    <td>审批人1：</td>\n" + 
				"    <td><input type=\"text\" id=\"approver1\" name=\"approver1\" /></td>\n" + 
				"  </tr>\n" + 
				"  <tr>\n" + 
				"    <td>审批人2：</td>\n" + 
				"    <td><input type=\"text\" id=\"approver2\" name=\"approver2\" /></td>\n" + 
				"    <td>审批人3：</td>\n" + 
				"    <td><input type=\"text\" id=\"approver3\" name=\"approver3\" /></td>\n" + 
				"  </tr>\n" + 
				"</table>");
	}
	
	public String getRes(String resName){
		return this.res.get(resName);
	}
}

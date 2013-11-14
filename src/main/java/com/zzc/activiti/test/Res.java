package com.zzc.activiti.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service(value="res")
public class Res {
	//表单资源
	private Map<String, String> formRes =  new HashMap<String, String>();
	//展示资源
	private Map<String, String> showRes = new HashMap<>();
	
	public Res(){
		formRes.put("bussinessTripForm","/page/template/formRes1.html");
		
		formRes.put("bussinessTripAdvice","/page/template/formRes2.html");
		
		showRes.put("bussinessTripAdvice","/page/template/showRes.html");
	}
	
	public String getFormRes(String resName){
		return this.formRes.get(resName);
	}
	
	public String getShowRes(String resName){
		return this.showRes.get(resName);
	}
}

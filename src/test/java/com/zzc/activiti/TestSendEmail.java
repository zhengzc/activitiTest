package com.zzc.activiti;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestSendEmail extends BaseActTest {

	/**
	 * 发送邮件测试
	 */
	@Test
	public void testSendEmail(){
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("htmlText", "<b>processed and shipped<input type='button' value='test'></input></b>");
		
		this.runtimeService.startProcessInstanceByKey("sendEmail",variables);
	}
}

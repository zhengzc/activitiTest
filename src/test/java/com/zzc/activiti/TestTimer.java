package com.zzc.activiti;

import org.junit.Test;

public class TestTimer extends BaseActTest {

	/**
	 * testTimer
	 * 边界定时器测试
	 */
	@Test
	public void testTimer(){
		runtimeService.startProcessInstanceByKey("testTimer");
	}

}

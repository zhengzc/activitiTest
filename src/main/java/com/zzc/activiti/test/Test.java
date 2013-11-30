package com.zzc.activiti.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value="test")
public class Test {
	private final Logger LOG = LoggerFactory.getLogger(Test.class);
	
	public void printMessage(){
		LOG.info("-----------------hello activiti------------------------>");
	}
	
	public void testQueryProcess(FutureTask<String> futureTask){
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		executorService.execute(futureTask);
	}
}

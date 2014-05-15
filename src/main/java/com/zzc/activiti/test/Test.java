package com.zzc.activiti.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value="test")
public class Test {
	private final Logger logger = LoggerFactory.getLogger(Test.class);
	
	public void printMessage(){
		logger.info("-----------------hello activiti------------------------>");
	}
	
	public void test2(DelegateExecution execution){
		logger.info("----->test2 begin");
		Map<String,Object> variables = execution.getVariables();
		Iterator<Entry<String, Object>> it = variables.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Object> temp = it.next();
			logger.info("----->"+temp.getKey()+":"+temp.getValue());
		}
		logger.info("----->test2 end");
	}
	
	public void testQueryProcess(FutureTask<String> futureTask){
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		executorService.execute(futureTask);
	}
}

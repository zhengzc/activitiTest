package com.zzc.activiti;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestQueryProcess {
	private final Logger LOG = LoggerFactory.getLogger(ActivitiTest.class);
	
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private FormService formService;
	@Resource
	private RepositoryService repositoryService;
	
	@Test
	public void testQuery(){
		//创建future
		FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "hello world";
			}
		});
		
		//准备流程参数
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("futureTask", futureTask);
		
		//启动流程
		runtimeService.startProcessInstanceByKey("testQueryProcess",variables);
		
		//获取结果
		try {
			String ret = futureTask.get();
			
			System.out.println(ret);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

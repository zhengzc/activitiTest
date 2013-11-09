package com.zzc.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class ActivitiTest {
	
	private final Logger LOG = LoggerFactory.getLogger(ActivitiTest.class);
	
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private FormService formService;
	@Resource
	private RepositoryService repositoryService;
	
	/**
	 * test1.bpmn
	 */
	@Test
	public void test() {
		runtimeService.startProcessInstanceByKey("process1");
	}
	
	/**
	 * testUserTask.bpmn
	 */
	@Test
	public void testUserTask(){
		//设置流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("candidateUsers", "kermit,gonzo");
		
		//启动流程
		runtimeService.startProcessInstanceByKey("testUserTask", variables);
		
		//查询我的候选任务
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("kermit").list();
		Task task = tasks.get(0);
		
		//签收任务
		taskService.claim(task.getId(), "kermit");
		
		//查询我的任务
		List<Task> myTasks = taskService.createTaskQuery().taskAssignee("kermit").list();
		Task myTask = myTasks.get(0);
		
		//完成任务
		taskService.complete(task.getId());
	}
	
	/**
	 * testBusinessTripFlow.bpmn
	 * 启动一个差旅申请
	 */
	@Test
	public void testTripStart(){
		//设置流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("fee", 5000);
		
//		repositoryService.getpro
//		StartFormData formData = formService.getStartFormData();
		runtimeService.startProcessInstanceByKey("testBusinessTripFlow", variables);
	}
	
	/**
	 * testBusinessTripFlow.bpmn
	 * 组长审批
	 */
	@Test
	public void testTripUserTask1(){
		//查询我的候选任务
		List<Task> tasks = taskService.createTaskQuery()
				.taskAssignee("gonzo")
				.list();
		
		Task task = tasks.get(0);
		
		//获取流程变量
		Map<String,Object> variables = task.getProcessVariables();
		LOG.debug("--------------------->"+variables.toString());
		
		taskService.complete(task.getId());
	}
	
	/**
	 * testBusinessTripFlow.bpmn
	 * 主管审批
	 */
	@Test
	public void testTripUserTask2(){
		//查询我的候选任务  附加上查询流程变量
		List<Task> tasks = taskService.createTaskQuery()
				.includeProcessVariables()
				.taskAssignee("fozzie")
				.list();
//		List<Task> tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
		Task task = tasks.get(0);
		
		//获取流程变量
		Map<String,Object> variables = task.getProcessVariables();
		LOG.debug("--------------------->"+variables.toString());
		
		taskService.complete(task.getId());
	}
	
	/**
	 * testBusinessTripFlow.bpmn
	 * 财务审批审批
	 */
	@Test
	public void testTripUserTask3(){
		//查询我的候选任务
		List<Task> tasks = taskService.createTaskQuery().taskAssignee("kermit").list();
		Task task = tasks.get(0);
		
		Map<String,Object> variables = new HashMap<String, Object>();
		variables.put("advice", "祝旅途愉快！！");
		
		taskService.complete(task.getId(), variables);
	}
	
}

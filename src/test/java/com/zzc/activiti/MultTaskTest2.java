package com.zzc.activiti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zzc.entity.ApproveTestEntity;

public class MultTaskTest2 extends BaseActTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 启动多实例
	 */
	@Test
	public void start(){
		Map<String, Object> variables = new HashMap<String, Object>();
		
		List<String> users = new ArrayList<>();
		users.add("Kermit,zhangsan2");
		users.add("zhengzc,zhengzc333");
		
//		variables.put("candidateUsersList", users);
		
//		List<String> users2 = new ArrayList<>();
//		users.add("Kermit,zhangsan2");
//		users.add("zhengzc,zhengzc333");
//		
//		variables.put("candidateUsersList", users);
		List<List<String>> deptApprovers = new ArrayList<>();
		deptApprovers.add(users);
		
		
		ApproveTestEntity approveTestEntity = new ApproveTestEntity("b2gApproverTest", "12345678");
		approveTestEntity.setDeptFlag(true);
		approveTestEntity.setDeptApprovers(deptApprovers);
		
		variables.put("inParam", approveTestEntity);
		variables.put("outParam", new ArrayList<>());
		variables.put("testOut", "");
		
		this.runtimeService.startProcessInstanceByKey(approveTestEntity.getId(),variables);
	}
	
	/**
	 * 签收任务
	 */
	@Test
	public void claimTask(){
		//查询我的候选任务
		List<Task> tasks = taskService.createTaskQuery()
				.includeProcessVariables()
				.taskCandidateUser("Kermit")
				.list();
		
		Task task = tasks.get(0);
//						int a = 5/0;
		
		taskService.claim(task.getId(), "Kermit");
	}
	
	/**
	 * 审批任务
	 */
	@Test
	public void completeTask(){
		//查询我的候选任务
		List<Task> tasks = taskService.createTaskQuery()
				.includeProcessVariables()
				.taskAssignee("Kermit")
				.list();
		
		Task task = tasks.get(0);
//				int a = 5/0;
		//获取流程变量
		Map<String,Object> variables = task.getProcessVariables();
		variables.put("flag", "complete task");
		
		taskService.complete(task.getId(),variables);
	}
	
	/**
	 * 拒绝任务
	 */
	@Test
	public void refuseTask(){
		//查询我的候选任务
		List<Task> tasks = taskService.createTaskQuery()
				.includeProcessVariables()
				.taskCandidateUser("Kermit")
				.list();
		
		Task task = tasks.get(0);
//		int a = 5/0;
		//获取流程变量
		Map<String,Object> variables = task.getProcessVariables();
		variables.put("flag", "refuseTask");
		
//		ProcessCustomService.endProcess(task.getId(), variables);
		//多实例使用边界事件来处理 用户任务 被拒绝
		this.runtimeService.messageEventReceived("refuseMessage", task.getExecutionId(),variables);
	}
}

package com.zzc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.zzc.activiti.test.Res;

@Controller
@RequestMapping(value="/")
public class MainController {
	
	private final Logger LOG = LoggerFactory.getLogger(MainController.class);
	
//	@Resource
//	private ProcessEngine processEngine;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private TaskService taskService;
	@Resource
	private FormService formService;
	@Resource
	private RepositoryService repositoryService;
	
	@Resource
	private Res res;
	
	/**
	 * 打开申请页面
	 * @param req
	 * @return
	 */
	@RequestMapping(value="openRequestPage",method=RequestMethod.GET)
	public ModelAndView openRequestPage(WebRequest req){
		ModelAndView  mav = new ModelAndView();
		
		//获取参数
		String processKey = req.getParameter("processKey");//流程KEY
		
		//查询流程id
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey(processKey)
						.latestVersion()
						.singleResult();
		
		//获取表单资源
		String formKey = formService.getStartFormKey(processDefinition.getId());//表单资源编号
		
//		mav.getModel().put("resForm",res.getRes("bussinessTripForm"));
		mav.getModel().put("resForm",res.getRes(formKey));
		mav.getModel().put("processId", processDefinition.getId());
		mav.getModel().put("actionUrl", "/submitOrder");
		
		mav.setViewName("page/requestPage.html"); 
		return mav;
	}
	
	/**
	 * 提交订单
	 * @return
	 */
	@RequestMapping(value="submitOrder",method=RequestMethod.GET)
	public ModelAndView submitOrder(WebRequest req){
		ModelAndView mav = new ModelAndView();
		
		//获取参数
		String processId = req.getParameter("processId");
		String user = req.getParameter("user");
		String fee = req.getParameter("fee");
		String email = req.getParameter("email");
		String approver1 = req.getParameter("approver1");
		String approver2 = req.getParameter("approver2");
		String approver3 = req.getParameter("approver3");
		
		//作为流程变量设置
		Map<String,Object> variables = new HashMap<>();
		variables.put("user", user);
		variables.put("fee", fee);
		variables.put("email", email);
		variables.put("approver1", approver1);
		variables.put("approver2", approver2);
		variables.put("approver3", approver3);
		
		//启动流程
		ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId,variables);
		
		mav.setViewName("page/main.html");
		return mav;
	}
	
	/**
	 * 查询订单列表
	 * @return
	 */
	@RequestMapping(value="queryOrder",method=RequestMethod.GET)
	public ModelAndView queryOrder(@RequestParam(value="userName")String userName){
		ModelAndView mav = new ModelAndView();
		
		//获取任务列表
		List<Task> tasks = taskService.createTaskQuery()
							.taskAssignee(userName)
							.list();
		
		//组织返回值
		List<String> lists = new ArrayList<>();
		Iterator<Task> it = tasks.iterator();
		while(it.hasNext()){
			Task task = it.next();
			
			lists.add(task.getId());
		}
		
		mav.getModel().put("tasks", lists);
		mav.setViewName("page/list.html");
		return mav;
	}
	
	/**
	 * 打开审批页面
	 * @return
	 */
	@RequestMapping(value="openApprovePage",method=RequestMethod.GET)
	public ModelAndView openApprovePage(@RequestParam(value="taskId")String taskId){
		ModelAndView mav = new ModelAndView();
		
		Task task = taskService.createTaskQuery()
					.includeProcessVariables()
					.taskId(taskId)
					.singleResult();
		
		Map<String,Object> variables = task.getProcessVariables();
		
		mav.getModel().put("variables", variables);
		mav.setViewName("page/task.html");
		return mav;
	}
	
	/**
	 * 审批订单
	 * @return
	 */
	public ModelAndView approveOrder(){
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}

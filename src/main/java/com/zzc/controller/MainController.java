package com.zzc.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
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
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import com.zzc.activiti.core.ProcessCustomService;
import com.zzc.activiti.test.Res;

@Controller
@RequestMapping(value="/")
public class MainController {
	
	private final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Resource
	private FreeMarkerConfig freemarkerConfig;
	
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
	
	@RequestMapping(value="main",method=RequestMethod.GET)
	public ModelAndView main(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("page/main.html");
		return mav;
	}
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
		
		mav.getModel().put("formRes",res.getFormRes(formKey));
		mav.getModel().put("processId", processDefinition.getId());
		mav.getModel().put("actionUrl", "submitOrder");
		
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
		
		mav.setViewName("page/test.html");
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
		
		//获取任务
		Task task = taskService.createTaskQuery()
					.includeProcessVariables()
					.taskId(taskId)
					.singleResult();
		
		//获取表单资源
		String formKey = formService.getTaskFormKey(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		String formRes = res.getFormRes(formKey);
		//获取展示资源
		String showRes = res.getShowRes(formKey);
		
		//生成图片资源
		String imgPath = this.genProcessImg(task.getProcessInstanceId());
		
//		freemarkerConfig.getConfiguration().getTemplate(name);
		
		Map<String,Object> variables = task.getProcessVariables();
		mav.getModel().put("variables", variables);
		mav.getModel().put("taskId", taskId);
		mav.getModel().put("formRes", formRes);
		mav.getModel().put("showRes", showRes);
		mav.getModel().put("processImg", imgPath);
		mav.setViewName("page/task.html");
		return mav;
	}
	
	/**
	 * 审批订单
	 * @return
	 */
	@RequestMapping(value="approveOrder",method=RequestMethod.GET)
	public ModelAndView approveOrder(WebRequest req){
		ModelAndView mav = new ModelAndView();
		
		String taskId = req.getParameter("taskId");
		String advice = req.getParameter("advice");
		String isOk = req.getParameter("isOk") == null ? "yes" : req.getParameter("isOk");
		
		//begin 00001 zhengzhichao  添加审批人审批意见，是否审批通过等字段
		//审批意见
		List<Map<String,String>> approverAdvices = new ArrayList<>();
		Map<String,String> approverAdvice = new HashMap<>();
		approverAdvice.put("advice",advice);
		approverAdvice.put("isOk", isOk);
		approverAdvices.add(approverAdvice);
		//end 00001 zhengzhichao
		
		//查询任务
		Task task = taskService.createTaskQuery()
					.includeProcessVariables()
					.taskId(taskId)
					.singleResult();
		
		//获取流程变量并添加审批意见
		Map<String,Object> variables = task.getProcessVariables();
		variables.put("approverAdvices", approverAdvices);
		variables.put("isOk", isOk);
		
		if("yes".equals(isOk)){
			//完成任务
			taskService.complete(taskId, variables);
		}else{
			try {
				ProcessCustomService.endProcess(taskId,variables);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		mav.setViewName("page/test.html");
		return mav;
	}
	
	/**
	 * 流程iｄ
	 * @param processInstanceId
	 * @return
	 */
	private String genProcessImg(String processInstanceId){
		//生成图片
		String rootPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		String imgWebPath = "resources"+File.separator+"test"+File.separator+"png"+File.separator+processInstanceId+".png";
		String imgPath = rootPath+File.separator+".."+File.separator+".."+File.separator+imgWebPath;
		File img = new File(imgPath);;
		
		BufferedOutputStream bufferedOutputStream = null;
		InputStream inputStream = null;
		
		try {
			inputStream = ProcessCustomService.getImageStreamByProcessInstanceId(processInstanceId);
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(img));
			
			byte[] tempbytes = new byte[1000];
			int byteRead = 0;
			while ((byteRead = inputStream.read(tempbytes)) != -1){  
				bufferedOutputStream.write(tempbytes, 0, byteRead);  
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bufferedOutputStream != null){
					bufferedOutputStream.close();
				}
				
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return imgWebPath;
	}
}

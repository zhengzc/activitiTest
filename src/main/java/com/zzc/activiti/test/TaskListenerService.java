package com.zzc.activiti.test;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zzc.activiti.core.BaseService;
import com.zzc.entity.EMailEntity;

@Service("taskListenerService")
public class TaskListenerService extends BaseService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 针对任务监听器中的 create事件
	 * @param delegateTask
	 */
	public void create(DelegateTask delegateTask){
		logger.debug("----->taskListenerService create begin");
		
		EMailEntity eMailEntity = EMailEntity.create("zhichao.zheng@travelzen.com","system@travelzen.com","测试",null,"hello nonhtml");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("email", eMailEntity);
		this.runtimeService.startProcessInstanceByKey("sendEmail",variables);
		
		logger.debug("----->taskListenerService create end");
	}
}

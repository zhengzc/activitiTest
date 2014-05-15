package com.zzc.activiti.core;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

public class BaseService {
	@Resource
	public RuntimeService runtimeService;
	@Resource
	public TaskService taskService;
	@Resource
	public FormService formService;
	@Resource
	public RepositoryService repositoryService;
}

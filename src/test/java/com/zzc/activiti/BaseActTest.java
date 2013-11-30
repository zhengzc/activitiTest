package com.zzc.activiti;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class BaseActTest {
		@Resource
		public RuntimeService runtimeService;
		@Resource
		public TaskService taskService;
		@Resource
		public FormService formService;
		@Resource
		public RepositoryService repositoryService;
}

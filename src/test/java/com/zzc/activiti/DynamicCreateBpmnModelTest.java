package com.zzc.activiti;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;

public class DynamicCreateBpmnModelTest {
	public void test(){
		BpmnModel model = new BpmnModel();
		
		Process process = new Process();
		process.setId("testDynamicModel");
		
		model.addProcess(process);
		
		
		StartEvent startEvent = new StartEvent();//开始节点
		process.addFlowElement(startEvent);
		
		UserTask userTask = new UserTask();
		userTask.setId("task1");
		userTask.setAssignee("zhengzhichao");
		userTask.setName("first dynamic task");
		process.addFlowElement(userTask);
		
		EndEvent endEvent = new EndEvent();
		process.addFlowElement(endEvent);
		
		SequenceFlow sequenceFlow1 = new SequenceFlow();
		sequenceFlow1.setSourceRef("start");
		sequenceFlow1.setTargetRef("task1");
		
		SequenceFlow sequenceFlow2 = new SequenceFlow();
		sequenceFlow2.setSourceRef("task1");
		sequenceFlow2.setTargetRef("end");
	}
}

package com.join.learn.activiti.c;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import com.join.learn.activiti.util.ActivitiUtils;

public class ProcessInstanceTest {
	
	private ProcessEngine engine=ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void init(){
		ActivitiUtils.dropCreateEngine();
	}
	
	@Test
	public void startProcess(){
		String processDefinitionKey="helloworld";
		ProcessInstance pi=engine.getRuntimeService()
			.startProcessInstanceByKey(processDefinitionKey);//processDefinitionKey即流程配置bpmn文件的process节点的ID
		System.out.println("流程实例ID："+pi.getId());
		System.out.println("流程定义ID："+pi.getProcessDefinitionId());
	}
	
}

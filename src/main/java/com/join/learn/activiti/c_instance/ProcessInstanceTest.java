package com.join.learn.activiti.c_instance;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
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
	
	@Test
	public void completeTask(){
		String taskId="1001";
		engine.getTaskService()
			.complete(taskId);
		System.out.println("完成任务,taskId="+taskId);
	}
	
	/**
	 * 判断流程是否结束
	 */
	@Test
	public void isProcessEnd(){
		String processInstanceId="101";
		ProcessInstance pi=engine.getRuntimeService()
			.createProcessInstanceQuery()
			.processInstanceId(processInstanceId)
			.singleResult();
		if(pi==null){
			System.out.println("流程已结束");
		}else{
			System.out.println("流程未结束");
			pi.getProcessDefinitionId();
			System.out.println("pi.getProcessDefinitionId()"+pi.getProcessDefinitionId());
			System.out.println(pi.getBusinessKey());
			System.out.println(pi.getId());
		}
	}
	
	/**
	 * 历史任务查询
	 */
	@Test
	public void findHisTask(){
		String taskAssigne="张三";
		List<HistoricTaskInstance> list=engine.getHistoryService()
					.createHistoricTaskInstanceQuery()
					.taskAssignee(taskAssigne)
					.list();
		for(HistoricTaskInstance hisTask:list){
			System.out.println(hisTask.getId()+","+hisTask.getName()+","+hisTask.getExecutionId());
			System.out.println(hisTask.getStartTime()+","+hisTask.getEndTime());
		}
	}
	
	/**
	 * 查询历史流程
	 */
	@Test
	public void findHisProcessInstance(){
		String processInstanceId="101";
		HistoricProcessInstance hpi=engine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		System.out.println(hpi);
	}
	
}

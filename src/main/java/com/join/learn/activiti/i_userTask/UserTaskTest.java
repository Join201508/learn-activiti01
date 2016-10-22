package com.join.learn.activiti.i_userTask;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

import junit.framework.Assert;

public class UserTaskTest extends BaseActivitiTest {
	@Test
	public void deploy(){
		super.deployByInStream("UserTask节点示例流程3", 
				"UserTaskProcess.bpmn", 
				"UserTaskProcess.png");
	}
	
	@Test
	public void start(){
		Map<String,Object> vars=new HashMap<>();
		vars.put("userId", "曹操");
		
		String processKey="UserTaskProcess";
		
		//启动流程是把变量从开始节点带入，进入usertask1任务
		ProcessInstance pi=engine.getRuntimeService()
				.startProcessInstanceByKey(processKey,vars);
		super.printProcessInstance(pi);
		
		//查询task1
		Task task1=engine.getTaskService()
				.createTaskQuery()
				.processInstanceId(pi.getProcessInstanceId())
				.singleResult();
		System.out.println("task1.getAssignee="+task1.getAssignee());
		
		//期望值是曹操：
		Assert.assertEquals("曹操", task1.getAssignee());
		
	}
	
	//完成usertask1，进入usertask2
	@Test
	public void completeTask1(){
		String task1_id="6805";
		String executionId="6801";
		
		//完成当前任务：usertask1
		engine.getTaskService().complete(task1_id);
		
		//查询下一步任务:usertask2
		Task task2=engine.getTaskService()
				.createTaskQuery()
				.executionId(executionId)
				.singleResult();
		System.out.println("task2.getAssignee="+task2.getAssignee());
		
		//期望值是张三：在com.join.learn.activiti.i_userTask.TaskLsnImpl中设置的
		Assert.assertEquals("张三", task2.getAssignee());
	}
}

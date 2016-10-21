package com.join.learn.activiti.g_gateway;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

public class ExclusiveGateWayTest extends BaseActivitiTest{

	@Test
	public void deploy(){
		super.deploy("费用报销-排他网关", 
				"com/join/learn/activiti/g_gateway/ExclusiveGateWay.bpmn", 
				"com/join/learn/activiti/g_gateway/ExclusiveGateWay.png");
	}
	
	@Test
	public void start(){
		super.startProcessByKey("exlusiveGateWayProcess");
	}
	
	@Test
	public void complete(){
		String taskId="4704";
		String procInstId="4701";//没有并行网关时，proc_inst_id和execution_id一致
		Map<String,Object> vars=new HashMap<>();
		//vars.put("message", "重要");
		vars.put("money", "1000");
		engine.getTaskService().complete(taskId,vars);
		System.out.println("任务"+taskId+"已经完成，该流程实例还有如下task未完成：");
		printTaskListByProcInstId(procInstId);
	}
}

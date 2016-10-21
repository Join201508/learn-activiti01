package com.join.learn.activiti.g_gateway;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

public class ParallelGateWayTest extends BaseActivitiTest{

	@Test
	public void deploy(){
		super.deployByInStream("并行网关-测试", "ParallelGateWay.bpmn", "ParallelGateWay.png");
	}
	
	@Test 
	public void start(){
		super.startProcessByKey("parallelGateWayProcess");
	}
	
	@Test
	public void complete(){
		String taskId="5202";
		String procInstId="5202";//没有并行网关时，proc_inst_id和execution_id一致
		engine.getTaskService().complete(taskId);
		System.out.println("任务"+taskId+"已经完成，该流程实例还有如下task未完成：");
		printTaskListByProcInstId(procInstId);
	}
}

package com.join.learn.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class MyBusinessProcessTest {
	
	@Rule
	public ActivitiRule activitiRule=new ActivitiRule("activiti.cfg.default.xml");

	@Test
	public void testSimpleProcess() {
		activitiRule.getRuntimeService().startProcessInstanceByKey("simpleProcess");
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
		Assert.assertEquals("My Task", task.getName());
		activitiRule.getTaskService().complete(task.getId());
		Assert.assertEquals(0, activitiRule.getRuntimeService().createProcessInstanceQuery().count());
	}

	@Test
	public void ruleUsageExample() {
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		runtimeService.startProcessInstanceByKey("ruleUsage");
		TaskService taskService = activitiRule.getTaskService();
		Task task = taskService.createTaskQuery().singleResult();

		Assert.assertEquals("My Task", task.getName());
		taskService.complete(task.getId());
		Assert.assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}
	
	
}

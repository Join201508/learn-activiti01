package com.join.learn.activiti;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class BaseActivitiTest {
	protected ProcessEngine engine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 部署流程：
	 * 1.act_re_deployment 流程部署信息
	 * 2.act_re_procdef 流程定义表
	 * 		ID_            流程定义的key+版本+随机数，如：helloworld:2:104
	 * 		NAME_          对应bpmn定义中<process>的name
	 * 		KEY_           对应bpmn定义中<process>的id
	 *  	VARSION_:      版本号，统一个流程部署可以部署多次，产生
	 *  	DEPLOYMENT_ID_ 关联act_re_deployment.id_
	 *  	RESOURCE_NAME_ 流程定义bpmn文件名
	 *  	DRM_RESOURCE_NAME_ 流程定义的流程图文件名
	 * 3.act_ge_bytearray 资源文件表
	 * 
	 * 注意：部署多次会产生多个版本
	 */
	public void deploy(String defName,String bpmnPath,String pngPath){
		RepositoryService service=engine.getRepositoryService();
		Deployment deployment=
				service.createDeployment()//创建一个部署对象
					.name(defName)//指定流程：act_re_deployment.name_
					.addClasspathResource(bpmnPath)//加载资源文件:"diagrams/helloworld.bpmn"
					.addClasspathResource(pngPath)//"diagrams/helloworld.png"
					.deploy();//完成部署
		
		printProcessDefinition(service, deployment);
		
	}
	
	public void deployByInStream(String defName,String bpmnFileName,String pngFileName){
		
		InputStream bpmnInStream=this.getClass().getResourceAsStream(bpmnFileName);
		InputStream pngInStream=this.getClass().getResourceAsStream(pngFileName);
		
		RepositoryService service=engine.getRepositoryService();
		Deployment deployment=
				service.createDeployment()//创建一个部署对象
					.name(defName)//指定流程：act_re_deployment.name_
					.addInputStream(bpmnFileName, bpmnInStream)
					.addInputStream(pngFileName, pngInStream)
					.deploy();//完成部署
		
		printProcessDefinition(service, deployment);
		
	}

	/**
	 * 启动一个流程实例
	 * act_ru_task、act_ru_execution、act_re_procdef
	 */
	public ProcessInstance startProcessByKey(String processDefinitionKey){
		ProcessInstance pi=engine.getRuntimeService()
			.startProcessInstanceByKey(processDefinitionKey);//processDefinitionKey即流程配置bpmn文件的process节点的ID
		printProcessInstance(pi);
		return pi;
	}

	/**
	 * 启动一个流程实例
	 * act_ru_task、act_ru_execution、act_re_procdef
	 */
	public void startProcessById(String processDefinitionId){
		ProcessInstance pi=engine.getRuntimeService()
			.startProcessInstanceById(processDefinitionId);
		printProcessInstance(pi);
	}
	
	public void printTaskListByProcInstId(String processInstanceId){
		List<Task> taskList=engine.getTaskService()
				.createTaskQuery()
				.processInstanceId(processInstanceId)
				.list();
		if(taskList.size()==0){
			System.out.println("当前流程实例没有任务，processInstanceId="+processInstanceId);
		}else{
			printTask(taskList);
		}
			
	}
	
	public void printTaskListByProcDefId(String processDefinitionId){
		List<Task> taskList=engine.getTaskService()
				.createTaskQuery()
				.processDefinitionId(processDefinitionId)
				.list();
		if(taskList.size()==0){
			System.out.println("当前流程定义没有任务，processDefinitionId="+processDefinitionId);
		}else{
			printTask(taskList);
		}
			
	}
	
	public void printTaskListByExecId(String executionId){
		List<Task> taskList=engine.getTaskService()
				.createTaskQuery()
				.executionId(executionId)
				.list();
		if(taskList.size()==0){
			System.out.println("当前流程Execution没有任务：executionId="+executionId);
		}else{
			printTask(taskList);
		}
			
	}

	private void printTask(List<Task> taskList) {
		for(Task t:taskList){
			System.out.println("  taskid:"+t.getId()
						+"  executionId:"+t.getExecutionId()
						+"  name:"+t.getName()
						+"  proDefId:"+t.getProcessDefinitionId()
						+"  assignee:"+t.getAssignee());
		}
	}
	
	private void printProcessInstance(ProcessInstance pi) {
		System.out.println();
		System.out.println("#####流程实例启动成功########################");
		System.out.println("流程定义ID："+pi.getProcessDefinitionId());
		System.out.println("流程实例ID："+pi.getProcessInstanceId());
		System.out.println("流程execution_ID："+pi.getId());//helloworld:2:204
		
		System.out.println("##task列表如下：");
		printTaskListByProcInstId(pi.getProcessInstanceId());
		System.out.println("#################################");
	}
	
	private void printProcessDefinition(RepositoryService service, Deployment deployment) {
		//definition与deployment一对一关系
		ProcessDefinition definition=
				service.createProcessDefinitionQuery()
					.deploymentId(deployment.getId())
					.singleResult();
		
		System.out.println("#####流程部署完成#####################");
		System.out.println("流程部署主键："+deployment.getId());//自动生成
		System.out.println("流程部署名称："+deployment.getName());//部署时设置的名称
		System.out.println("流程定义Key："+definition.getKey());//parallelGateWayProcess
		System.out.println("流程定义版本号："+definition.getVersion());//1
		System.out.println("流程定义ID："+definition.getId());//parallelGateWayProcess:1:4904
		System.out.println("#######################################");
	}
	
	
}

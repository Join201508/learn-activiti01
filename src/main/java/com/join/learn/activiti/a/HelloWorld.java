
package com.join.learn.activiti.a;

import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    // 从默认配置文件activiti.cfg.xml创建ProcessEngine
    private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 初始化： 1.创建23张表； 2.往act_ge_property表里插入数据：next.dbid、schema.history、schema.version
     */
    @Test
    public void dropCreateEngine() {
        // 从默认配置文件activiti.cfg.xml创建ProcessEngineConfiguration
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        config.setDatabaseSchemaUpdate("drop-create");// 删除所有表，然后创建，单元测试清空数据时使用
        config.setHistory(HistoryLevel.FULL.getKey());
        config.setHistoryLevel(HistoryLevel.FULL);
        ProcessEngine engine = config.buildProcessEngine();// 构建

        log.info("\n===========");
        System.out.println("数据库：" + config.getDatabaseType());
        System.out.println("url：" + config.getJdbcUrl());
        System.out.println("用户名：" + config.getJdbcUsername());
        System.out.println("密码：" + config.getJdbcPassword());
        System.out.println("通过activiti.cfg.xml配置创建流程引擎:" + engine);
        System.out.println("引擎名称:" + engine.getName());
        log.info("\n===========");
        Map<String, String> properties = engine.getManagementService().getProperties();
        properties.forEach((k, v) -> {
            System.out.println(k + "=" + v);
        });
        log.info("\n===========");
    }

    /**
     * 部署流程： 1.act_re_deployment 流程部署信息 2.act_re_procdef 流程定义表 ID_
     * 流程定义的key+版本+随机数，如：helloworld:2:104 NAME_ 对应bpmn定义中<process>的name KEY_ 对应bpmn定义中<process>的id
     * VARSION_: 版本号，统一个流程部署可以部署多次，产生 DEPLOYMENT_ID_ 关联act_re_deployment.id_ RESOURCE_NAME_
     * 流程定义bpmn文件名 DRM_RESOURCE_NAME_ 流程定义的流程图文件名 3.act_ge_bytearray 资源文件表
     * 
     * 注意：部署多次会产生多个版本
     */
    @Test
    public void deploymentProcessDefinition() {
        RepositoryService service = engine.getRepositoryService();
        Deployment deployment = service.createDeployment()// 创建一个部署对象
            .name("helloworld流程入门")// 指定流程：act_re_deployment.name_
            .addClasspathResource("diagrams/helloworld.bpmn")// 加载资源文件
            .addClasspathResource("diagrams/helloworld.png")
            .deploy();// 完成部署

        System.out.println("流程ID：" + deployment.getId());
        System.out.println("流程名称：" + deployment.getName());
    }

    /**
     * 启动一个流程实例 act_ru_task、act_ru_execution、act_re_procdef
     */
    @Test
    public void startProcess() {
        String processDefinitionKey = "helloworld";
        ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);// processDefinitionKey即流程配置bpmn文件的process节点的ID
        System.out.println("流程实例ID：" + pi.getId());
        System.out.println("流程定义ID：" + pi.getProcessDefinitionId());
    }

    /**
     * 正在执行的任务查询：act_ru_task表
     */
    @Test
    public void findTask() {
        String assign = "李四";
        List<Task> list = engine.getTaskService()// 查询任务的service
            .createTaskQuery()// 创建一个任务的查询对象

            /* 查询条件： */
            .taskAssignee(assign)// 根据任务处理人查询
            // .taskCandidateGroup(candidateGroup);//根据组任务的办理人查询
            // .processDefinitionId(processDefinitionId);//根据流程定义ID查询
            // .processInstanceId(processInstanceId);//根据流程实例ID查询
            // .executionId(executionId);//根据执行对象id查询
            // .singleResult();//返回单个结果集

            /* 排序： */
            // .orderByDueDate().desc()//按照due_date降序
            .orderByTaskCreateTime()
            .asc()// 按照创建时间升序

            /* 结果集处理： */
            // .count();//结果集数量
            // .listPage(firstResult, maxResults);//分页查询
            .list();

        if (list != null && list.size() > 0) {
            for (Task t : list) {
                System.out.println("任务ID：" + t.getId());
                System.out.println("任务名称：" + t.getName());
                System.out.println("创建时间：" + t.getCreateTime());
                System.out.println("处理人：" + t.getAssignee());
                System.out.println("流程实例ID：" + t.getProcessInstanceId());
                System.out.println("执行对象ID：" + t.getExecutionId());
                System.out.println("流程定义ID：" + t.getProcessDefinitionId());
                System.out.println("###################################");
            }
        } else {
            System.out.println("没有查询到数据");
        }
    }

    @Test
    public void completeProcessTask() {
        String taskId = "402";
        engine.getTaskService()// 执行任务的service
            .complete(taskId);
        System.out.println("任务完成：taskId=" + taskId);
    }
}

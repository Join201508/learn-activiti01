
package com.join.learn.activiti.d_var;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class ProcessVariablesTest {

    // 从默认配置文件activiti.cfg.xml创建ProcessEngine
    private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploymentProcessDefinition_inputStream() {
        InputStream bpmnInStream = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
        InputStream pngInStream = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
        Deployment deployment = engine.getRepositoryService()
            .createDeployment()
            .name("流程变量定义")
            .addInputStream("processVariables.bpmn", bpmnInStream)
            .addInputStream("processVariables.png", pngInStream)
            .deploy();
        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
    }

    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "processVariables";
        ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
        System.out.println("流程实例ID：" + pi.getId());
        System.out.println("getProcessDefinitionId:" + pi.getProcessDefinitionId());
        System.out.println("getProcessInstanceId:" + pi.getProcessInstanceId());
    }


    @Test
    public void setVariableForTask() {
        TaskService taskService = engine.getTaskService();
        String taskId = "1304";
        taskService.setVariable(taskId, "请假日期", new Date());
        taskService.setVariable(taskId, "请假天数", 8);// 同一个变量多次设值(值发生变化时)版本号会增加:act_ru_variable.REV_字段+1
        taskService.setVariable(taskId, "请假原因", "回家有事1");// act_ru_variable.TASK_ID_为空
        /*
         * 1. taskService.setVariableLocal时act_ru_variable.TASK_ID_有值 2.
         * local变量，只在当前任务中生效，当前任务complete了，local变量消失
         */
        taskService.setVariableLocal(taskId, "local变量", "local variable");

        /**
         * 也可以设置javaBean变量(实现序列化接口Serializable的javaBean)
         * 会存放到act_ge_bytearray二进制表在，通过act_ru_variable.BYTEARRAY_ID_关联起来
         */
        Personal p = new Personal();
        p.setPersonalId(10010);
        p.setPersonalName("personal宝宝");
        taskService.setVariable(taskId, "personal", p);

    }

    @Test
    public void getVariableForTask() {
        TaskService taskService = engine.getTaskService();
        String taskId = "1304";
        Integer days = (Integer) taskService.getVariable(taskId, "请假天数");
        System.out.println("请假天数：" + days);
        System.out.println(taskService.getVariableLocal(taskId, "local变量"));
        System.out.println(taskService.getVariable(taskId, "local变量"));
        System.out.println(taskService.getVariables(taskId));
        System.out.println(taskService.getVariablesLocal(taskId));
        Personal p = (Personal) taskService.getVariable(taskId, "personal");
        System.out.println(p.toString());// Personal [personalId=10010, personalName=personal宝宝]
    }

    @Test
    public void setAndGetVariableForRuntime() {
        RuntimeService runtimeService = engine.getRuntimeService();
        String executionId = "401";
        runtimeService.setVariable(executionId, "holiday date", new Date());
        runtimeService.setVariable(executionId, "请假天数", 3);
        runtimeService.setVariable(executionId, "holiday reason", "家事");
        runtimeService.setVariableLocal(executionId, "runtime variable local", "local test");

        System.out.println("get variable ... ");
        System.out.println(runtimeService.getVariables(executionId));
        System.out.println(runtimeService.getVariablesLocal(executionId));
    }

    /**
     * 带变量完成任务
     */
    @Test
    public void completeForVariables() {
        TaskService taskService = engine.getTaskService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("complete变量1", "complete_var_1");
        variables.put("complete变量2", "complete_var_2");
        taskService.complete("1104", variables);
    }

    /**
     * 查询历史流程变量：act_hi_varinst
     */
    @Test
    public void queryHisVariable() {
        List<HistoricVariableInstance> list =
            engine.getHistoryService().createHistoricVariableInstanceQuery().variableName("请假天数").list();
        for (HistoricVariableInstance hisVar : list) {
            System.out.println(hisVar.getClass().getName());
            HistoricVariableInstanceEntity var = (HistoricVariableInstanceEntity) hisVar;
            System.out.println(var.getId() + "，" + var.getProcessInstanceId() + "，" + var.getTaskId() + "，" + var.getExecutionId()
                + "，" + var.getVariableName() + "，" + var.getValue());
        }
    }
}


package com.join.learn.activiti;

import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class LeaveProcessTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.default.xml");

    @Test
    public void deploy() {
        // activitiRule.getIdentityService().setAuthenticatedUserId("张三");
        activitiRule.getRepositoryService()
            .createDeployment()
            .addClasspathResource("diagrams/LeaveProcess.bpmn")
            .name("请假申请")
            .category("人事")
            .deploy();

        // TaskFormData data=activitiRule.getFormService().getTaskFormData("");
    }

    @Test
    public void start() {
        activitiRule.getIdentityService().setAuthenticatedUserId("张三");
        ProcessInstance porcessInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("leaveProcess");
        System.out.println(porcessInstance.getActivityId());
        System.out.println(porcessInstance.getProcessDefinitionId());
    }
}

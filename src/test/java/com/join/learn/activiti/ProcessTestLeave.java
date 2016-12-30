
package com.join.learn.activiti;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestLeave {

    private String filename =
        "D:\\Code\\learn-activiti01\\src\\main\\java\\com\\join\\learn\\activiti\\exercise\\leave\\Leave.bpmn";

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void startProcess() throws Exception {
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        repositoryService.createDeployment().addInputStream("leave.bpmn20.xml", new FileInputStream(filename)).deploy();
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String, Object> variableMap = new HashMap<String, Object>();
        variableMap.put("name", "Activiti");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave", variableMap);
        Assert.assertNotNull(processInstance.getId());
        System.out.println("id " + processInstance.getId() + " " + processInstance.getProcessDefinitionId());
    }
}

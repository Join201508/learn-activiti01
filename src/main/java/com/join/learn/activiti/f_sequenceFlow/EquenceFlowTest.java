
package com.join.learn.activiti.f_sequenceFlow;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

/**
 * 顺序连接线
 * 
 * @author Join
 *
 */
public class EquenceFlowTest extends BaseActivitiTest {

    @Test
    public void deploy() {
        super.deployByResourcePath("sequence连线测试", "diagrams/sequenceFlow.bpmn", "diagrams/sequenceFlow.png");
    }

    @Test
    public void start() {
        super.startProcessByKey("sequenceFlow");
    }

    @Test
    public void complete() {
        String taskId = "2103";
        engine.getTaskService().complete(taskId);
    }

    @Test
    public void completeForVars() {
        String taskId = "2304";
        Map<String, Object> vars = new HashMap<>();
        // vars.put("message", "重要");
        vars.put("message", "不重要");
        engine.getTaskService().complete(taskId, vars);
    }

}

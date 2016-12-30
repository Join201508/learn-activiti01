/* 
 * All rights Reserved, Designed By 农金圈
 * 2016年12月14日 下午1:40:17
 */
package com.join.learn.activiti.exercise.leave;

import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

/** 
 *
 * @author: Join 
 */
public class Leave extends BaseActivitiTest {

    @Test
    public void deploy() {
        super.deploy("请假流程Leave", "Leave.bpmn", "Leave.png");
    }

    @Test
    public void start() {
        String processKey = "leave";

        // 启动流程是把变量从开始节点带入，进入usertask1任务
        ProcessInstance pi = engine.getRuntimeService().startProcessInstanceByKey(processKey);
        super.printProcessInstance(pi);

    }

    // 完成usertask1，进入usertask2
    @Test
    public void completeTask1() {
        String task1_id = "7502";
        String executionId = "5001";

        // 完成当前任务：usertask1
        engine.getTaskService().complete(task1_id);

    }
}

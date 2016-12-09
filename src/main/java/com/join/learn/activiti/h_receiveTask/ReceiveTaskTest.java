
package com.join.learn.activiti.h_receiveTask;

import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

public class ReceiveTaskTest extends BaseActivitiTest {

    @Test
    public void deploy() {
        super.deploy("receiveTast学习示例", "ReceiveTaskTest.bpmn", "ReceiveTaskTest.png");

    }

    @Test
    public void startProcess() {
        String processDefinitionKey = "processReceiveTask";
        /** 1.启动流程 */
        ProcessInstance pi = super.startProcessByKey(processDefinitionKey);

        /* 当前流程停留在第一个节点receiveTask1 */
        // 查询执行对象：根据流程实例ID(pi.getProcessInstanceId())和当前活动节点ID(receiveTask1)
        Execution exec1 = engine.getRuntimeService()
            .createExecutionQuery()
            .processInstanceId(pi.getProcessInstanceId())
            .activityId("receivetask1")// 当前活动节点，对应bpmn流程定义中节点的Id属性值
            .singleResult();
        System.out.println("exec1.getId=" + exec1.getId());

        /** 2.设置流程变量来模拟要接受的消息 */
        engine.getRuntimeService().setVariable(exec1.getId(), "当日销售额", 2000);

        /** 3.处理完毕后，流程继续往下走-此处打断点跟踪 */
        // complete()是TaskService中的服务(只有UserTask节点才有)，其他节点都是调用RuntimeService.signal(xxx)
        // 非UserTask不会在act_ru_task中产生记录，非UserTask节点的所有记录都在act_hi_actinst中
        // 非UserTask的当前节点记录在act_ru_execution.act_id_中（对应bpmn中定义的节点ID）
        engine.getRuntimeService().signal(exec1.getId());

        /* 执行signal后，流程走到第二个节点receiveTask2 */
        // 查询执行对象：根据流程实例ID(pi.getProcessInstanceId())和当前活动节点ID(receiveTask2)
        // 由于没有并列网关，exec1和exec2应该是同一个对象
        Execution exec2 = engine.getRuntimeService()
            .createExecutionQuery()
            .processInstanceId(pi.getProcessInstanceId())
            .activityId("receivetask2")// 当前活动节点，对应bpmn流程定义中节点的Id属性值
            .singleResult();
        System.out.println("exec2.getId=" + exec2.getId());

        /** 4.从变量中获【当日销售额】 */
        Integer i = (Integer) engine.getRuntimeService().getVariable(exec2.getId(), "当日销售额");
        System.out.println("模拟统计数据，当日销售额为：" + i);

        /** 5.继续向后执行 */
        engine.getRuntimeService().signal(exec2.getId());

    }
}

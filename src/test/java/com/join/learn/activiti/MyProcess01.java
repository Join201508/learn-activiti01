/* 
 * All rights Reserved, Designed By 农金圈
 * 2017年1月16日 下午9:03:22
 */
package com.join.learn.activiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/** 
 *
 * @author: Join 
 */
public class MyProcess01 {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.default.xml");

    @Test
    public void deploy() {
        activitiRule.getRepositoryService()
            .createDeployment()
            .addClasspathResource("com/join/learn/activiti/MyProcess01.bpmn")
            .name("测试002")
            .deploy();
    }
    
    @Test
    public void start() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", "李四");
        variables.put("age", 18);
        activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess", "CRD0010", variables);
    }

    @Test
    public void queryProcessDef(){
        List<ProcessDefinition> list =
            activitiRule.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey("myProcess").list();
        list.forEach(System.out::println);

    }

    @Test
    public void claim() {
        activitiRule.getTaskService().claim("60002", "小红");
    }

    @Test
    public void complete() {
        String taskId="60002";
        activitiRule.getTaskService().addComment(taskId, null, "统一不同意案例集法拉盛减法222");
        // activitiRule.getTaskService().complete(taskId);
    }

    @Test
    public void querytask() {
        String taskId = "60002";
        List<Comment> commentList = activitiRule.getTaskService().getTaskComments(taskId);

        System.out.println(commentList.size());
        commentList.forEach(comment -> {
            System.out.println(comment.getType() + "," + comment.getFullMessage());
        });

        List<Event> eventList = activitiRule.getTaskService().getTaskEvents(taskId);
        eventList.forEach(event -> {
            System.out.println(event.getAction() + "," + event.getMessage() + "," + event.getMessageParts());
        });
    }



}

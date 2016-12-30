/* 
 * All rights Reserved, Designed By 农金圈
 * 2016年12月20日 下午3:35:01
 */
package com.join.learn.activiti.exercise.audit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

import junit.framework.Assert;

/** 
 *
 * @author: Join 
 */
public class LoanAudit extends BaseActivitiTest {

    @Test
    public void deploy() {
        deploy("审批流程", "loanAudit.bpmn", "loanAudit.bpmn");
    }


    public void testUser() throws Exception {
        IdentityService identityService = engine.getIdentityService();
        
        String userId1 = "custManager_user";// 客户经理
        String userId2 = "leader_user";// 部门总监
        String userId3 = "inner_user";// 内审人员
        String userId4 = "ini_user";// 初审人员
        String userId5 = "re_user";// 复审人员
        String userId6 = "final_user";// 贷审会秘书

        String userId = "部门总监";

        User user1 = identityService.newUser(userId1);
        User user2 = identityService.newUser(userId2);
        User user3 = identityService.newUser(userId3);
        User user4 = identityService.newUser(userId4);
        User user5 = identityService.newUser(userId5);
        User user6 = identityService.newUser(userId6);

        // user.setEmail("henryyan@qq.com");
        user1.setFirstName("张三（客户经理）");
        user2.setFirstName("李四（部门总监）");
        user3.setFirstName("王五（内审员）");
        user4.setFirstName("赵六（初审员）");
        user5.setFirstName("小明（复审员）");
        user6.setFirstName("小榄（贷审会秘书）");

        identityService.saveUser(user1);
        identityService.saveUser(user2);
        identityService.saveUser(user3);
        identityService.saveUser(user4);
        identityService.saveUser(user5);
        identityService.saveUser(user6);

        User userInDb = identityService.createUserQuery().userId(userId).singleResult();
        Assert.assertNotNull(userInDb);

        identityService.deleteUser(userId);

        userInDb = identityService.createUserQuery().userId(userId).singleResult();
        Assert.assertNull(userInDb);

    }

    @Test
    public void start() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentUserId", -1);
        startProcessByKey("loanAudit", map);
    }

    @Test
    public void complete() {
        Map<String, Object> map = new HashMap<>();
        map.put("investManagerId", -1);
        engine.getTaskService().complete("5005", map);
    }

    @Test
    public void test() {
        String insId = "5001";
        String defId = engine.getHistoryService()
            .createHistoricProcessInstanceQuery()
            .processInstanceId(insId)
            .singleResult()
            .getProcessDefinitionId();

        RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) (engine.getRepositoryService());
        ProcessDefinitionEntity def =
            (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(defId);

        // 执行实例
        ExecutionEntity execution =
            (ExecutionEntity) engine.getRuntimeService().createProcessInstanceQuery().processInstanceId(insId).singleResult();
        // 当前实例的执行到哪个节点
        String activitiId = execution.getActivityId();
        System.out.println("activitiId=" + activitiId);
        // 获得当前任务的所有节点
        List<ActivityImpl> activitiList = def.getActivities();
        System.out.println("find=" + def.findActivity("APPLY"));
        String id = null;
        for (ActivityImpl activityImpl : activitiList) {
            if ("userTask".equals(activityImpl.getProperty("type"))) {
                id = activityImpl.getId();
                if (activitiId.equals(id)) {
                    System.out.println("===" +
                        activityImpl.getId() + ",\t" + activityImpl.getProperty("name") + ",\t" + activityImpl.getProperty("type"));
                } else {
                    System.out.println(
                        activityImpl.getId() + ",\t" + activityImpl.getProperty("name") + ",\t" + activityImpl.getProperty("type"));
                }
            }
        }

    }
}

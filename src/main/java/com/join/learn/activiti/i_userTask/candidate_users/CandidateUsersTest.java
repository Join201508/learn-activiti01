
package com.join.learn.activiti.i_userTask.candidate_users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

/**
 * 组任务
 * 
 * @author Administrator
 *
 */
public class CandidateUsersTest extends BaseActivitiTest {

    /**
     * 注意：UserTask节点的MainConfig中配置 Candidate users
     */
    @Test
    public void deploy() {
        String processDefName = "userTask候选人示例5";
        String processFileNamePrefix = "CandidateUsersProcess";
        super.deploy(processDefName, processFileNamePrefix);
    }

    @Test
    public void start() {
        /*
         * 指定任务候选人员，也可以使用监听器实现implements TaskListener， 在监听器中设置候选人员：
         * delegateTask.addCandidateUser(userId1); delegateTask.addCandidateUser(userId2);
         */
        Map<String, Object> vars = new HashMap<>();
        vars.put("userIds", "周大,周中,周小");// 英文逗号！！
        super.startProcessByKey("candidateUsersProcess", vars);
    }

    @Test
    public void queryCandidateUsersByTaskId() {
        String taskId = "30005";
        queryCandidateUsersByTaskId(taskId);

    }

    private void queryCandidateUsersByTaskId(String taskId) {
        List<IdentityLink> idttlList = engine.getTaskService().getIdentityLinksForTask(taskId);
        for (IdentityLink idttl : idttlList) {
            System.out.println(idttl.getGroupId() + "," + idttl.getUserId() + "," + idttl.getType());
        }
    }

    /**
     * 认领任务
     */
    @Test
    public void claim() {
        String taskId = "30005";
        String userId = "周中";

        // 认领前
        Task taskBefore = engine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

        /* 认领任务 */
        engine.getTaskService().claim(taskId, userId);

        // 认领后
        Task taskAfter = engine.getTaskService().createTaskQuery().taskId(taskId).singleResult();

        System.out.println("认领前，任务认领人：" + taskBefore.getAssignee());
        System.out.println("认领后，任务认领人（重新查询）：" + taskAfter.getAssignee());
    }

    // 向组任务中添加成员
    @Test
    public void addCandidateUser() {
        String taskId = "30005";
        String userId = "候选人1";
        queryCandidateUsersByTaskId(taskId);
        engine.getTaskService().addCandidateUser(taskId, userId);
        System.out.println("#####添加后");
        queryCandidateUsersByTaskId(taskId);
    }

    // 向组任务中删除成员
    @Test
    public void removeCandidateUser() {
        String taskId = "30005";
        String userId = "候选人1";
        queryCandidateUsersByTaskId();
        engine.getTaskService().deleteCandidateUser(taskId, userId);
        System.out.println("#####删除后");
        queryCandidateUsersByTaskId();
    }

    @Test
    public void complete() {
        String taskId = "2505";
        engine.getTaskService().complete(taskId);
    }

}

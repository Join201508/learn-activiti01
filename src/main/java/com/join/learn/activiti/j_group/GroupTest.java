package com.join.learn.activiti.j_group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

/**
 * 组任务
 * @author Administrator
 *
 */
public class GroupTest extends BaseActivitiTest {
	@Test
	public void deploy(){
		super.deployByInStream("userTask组任任务4", "GroupProcess.bpmn", "GroupProcess.png");
	}
	
	@Test
	public void start(){
		/*指定任务候选人员，也可以使用监听器实现implements TaskListener，
		 * 在监听器中设置候选人员：
		 * delegateTask.addCandidateUser(userId1);
		 * delegateTask.addCandidateUser(userId2);
		 * */
		Map<String,Object> vars=new HashMap<>();
		vars.put("userIds", "周大,周中,周小");//英文逗号！！
		super.startProcessByKey("groupProcess",vars);
	}
	
	@Test
	public void queryGroupUserByTaskId(){
		String taskId="2505";
		queryGroupUserByTaskId(taskId);
		
	}

	private void queryGroupUserByTaskId(String taskId) {
		List<IdentityLink> idttlList=engine.getTaskService()
			.getIdentityLinksForTask(taskId);
		for(IdentityLink idttl:idttlList){
			System.out.println(idttl.getGroupId()+","+idttl.getUserId()+","+idttl.getType());
		}
	}
	
	/**
	 * 认领任务
	 */
	@Test
	public void claim(){
		String taskId="2505";
		String userId="周中";
		
		//认领前
		Task taskBefore=engine.getTaskService()
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();
		
		
		/*认领任务*/
		engine.getTaskService()	
			.claim(taskId, userId);
		
		//认领后
		Task taskAfter=engine.getTaskService()
				.createTaskQuery()
				.taskId(taskId)
				.singleResult();

		System.out.println("认领前，任务认领人："+taskBefore.getAssignee());
		System.out.println("认领后，任务认领人（重新查询）："+taskAfter.getAssignee());
	}
	
	@Test
	public void complete(){
		String taskId="8405";
		engine.getTaskService().complete(taskId);
	}
	
	//向组任务中添加成员
	@Test
	public void addUser(){
		String taskId = "2505";
		String userId = "候选人1";
		queryGroupUserByTaskId(taskId);
		engine.getTaskService().addCandidateUser(taskId, userId);
		System.out.println("#####添加后");
		queryGroupUserByTaskId(taskId);
	}

	//向组任务中删除成员
	@Test
	public void removeUser(){
		String taskId = "2505";
		String userId = "候选人1";
		queryGroupUserByTaskId();
		engine.getTaskService().deleteCandidateUser(taskId, userId);
		System.out.println("#####删除后");
		queryGroupUserByTaskId();
	}

}

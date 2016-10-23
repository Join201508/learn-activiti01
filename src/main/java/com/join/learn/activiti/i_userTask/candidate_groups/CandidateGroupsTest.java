package com.join.learn.activiti.i_userTask.candidate_groups;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

public class CandidateGroupsTest extends BaseActivitiTest{
	
	@Test
	public void deploy(){
		/**
		 * 注意：UserTask节点的MainConfig中配置 Candidate groups
		 */
		super.deploy("usertask候选组示例", "CandidateGroupsProcess");
	}
	
	@Test
	public void addIdentitys(){
		IdentityService idtService=engine.getIdentityService();
		
		//新建用户:act_id_user
		idtService.saveUser(new UserEntity("王宝强总"));
		idtService.saveUser(new UserEntity("刘德华经理"));
		idtService.saveUser(new UserEntity("李冰冰经理"));
		idtService.saveUser(new UserEntity("员工01"));
		idtService.saveUser(new UserEntity("员工02"));
		idtService.saveUser(new UserEntity("员工03"));
		//新建组（角色或岗位）:act_id_group
		idtService.saveGroup(new GroupEntity("总经理"));
		idtService.saveGroup(new GroupEntity("部门经理"));
		idtService.saveGroup(new GroupEntity("普通员工"));
		//建立用户&组关系：act_id_membership
		idtService.createMembership("王宝强总", "总经理");
		idtService.createMembership("刘德华经理", "部门经理");
		idtService.createMembership("李冰冰经理", "部门经理");
		idtService.createMembership("员工01", "普通员工");
		idtService.createMembership("员工02", "普通员工");
		idtService.createMembership("员工03", "普通员工");
		
		System.out.println("新建用户&组信息成功");
	}
	
	@Test
	public void start(){
		super.startProcessByKey("candidateGroupsProcess");
	}
	
	/**
	 * 查询任务与组(或用户)的关系列表
	 */
	@Test
	public void queryGroupUsers(){
		String taskId="50004";
		List<IdentityLink> list=engine.getTaskService()
							.getIdentityLinksForTask(taskId);
		for(IdentityLink idLink:list){
			System.out.println("groupId:"+idLink.getGroupId()+" , userId:"+idLink.getUserId()+" , taskId:"+idLink.getTaskId()+" , type:"+idLink.getType());
		}
	}
	
	/**
	 * userTask设置了【候选组】的，通过【候选组】查询任务：
	 * activiti自动关联如下三张表查询出来：
	 * act_ru_identitylink 任务与候选人(候选组)关系表
	 * act_id_user、
	 * act_id_group、
	 * act_id_membership
	 */
	@Test
	public void queryTaskByCandidateGroup(){
		//String groupId="总经理";
		String groupId="部门经理";
		//String groupId="普通员工";//"普通员工"查不到任务，因为userTask的候选组中没有配置“普通员工"组
		List<Task> list=engine.getTaskService()
						.createTaskQuery()
						.taskCandidateGroup(groupId)
						.list();
		if(list.size()==0){
			System.out.printf("根据CandidateGroup=[%s]查不到任务",groupId);
		}
		printTask(list);
	}

	
	/**
	 * userTask设置了【候选组】的，通过【候选人】也可以查询到任务：
	 * activiti自动关联如下三张表查询出来：
	 * act_ru_identitylink 任务与候选人(候选组)关系表
	 * act_id_user、
	 * act_id_group、
	 * act_id_membership
	 */
	@Test
	public void queryTaskByCandidateUser(){
		//String userId="王宝强总";
		String userId="刘德华经理";
		//String userId="员工01";//"员工01"查不到任务，应为该人不属于该任务候选组中的人
		List<Task> list=engine.getTaskService()
						.createTaskQuery()
						.taskCandidateUser(userId)
						.list();
		if(list.size()==0){
			System.out.printf("根据CandidateUser=[%s]查不到任务",userId);
		}
		printTask(list);
	}
	
	/**
	 * userTask设置了【候选组】的，通过assignee查询不到数据，
	 * 除非指定了assignee人
	 */
	@Test 
	public void queryTaskByAssignee(){
		String userId="刘德华经理";
		List<Task> list=engine.getTaskService()
						.createTaskQuery()
						.taskAssignee(userId)
						.list();
		if(list.size()==0){
			System.out.printf("根据taskAssignee=[%s]查不到任务",userId);
		}
		printTask(list);
	}
	
	@Test
	public void complete(){
		String taskId="50004";
		engine.getTaskService().complete(taskId);
		System.out.printf("任务{}完成", taskId);
	}
	
	private void printTask(List<Task> list) {
		if(list.size()>0){
			System.out.println("任务如下：");
			for(Task t:list){
				System.out.println(
						 "taskId="+t.getId()
						+" , name="+t.getName()
						+" , DefinitionKey="+t.getTaskDefinitionKey()
						+" , assignee="+t.getAssignee());
			}
		}
	}
	
	
}

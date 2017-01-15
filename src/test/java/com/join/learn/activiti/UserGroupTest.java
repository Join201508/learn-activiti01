package com.join.learn.activiti;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class UserGroupTest {
	
	@Rule
	public ActivitiRule activitiRule=new ActivitiRule("activiti.cfg.default.xml");

	@Test
	public void testGroup(){
			IdentityService identityService = activitiRule.getIdentityService();
			// 创建一个组对象
			Group group = identityService.newGroup("deptLeader");
			group.setName("部门领导");
			group.setType("assignment");
			// 保存组
			identityService.saveGroup(group);
			// 验证组是否已保存成功，首先需要创建组查询对象
			List<Group> groupList = identityService.createGroupQuery().groupId("deptLeader").list();
			Assert.assertEquals(1, groupList.size());
			// 删除组
			identityService.deleteGroup("deptLeader");
			// 验证是否删除成功
			groupList = identityService.createGroupQuery().groupId("deptLeader").list();
			Assert.assertEquals(0, groupList.size());			
	}
}

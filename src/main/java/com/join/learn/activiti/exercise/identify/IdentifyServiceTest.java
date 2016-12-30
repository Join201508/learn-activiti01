/* 
 * All rights Reserved, Designed By 农金圈
 * 2016年12月19日 下午1:57:08
 */
package com.join.learn.activiti.exercise.identify;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import junit.framework.Assert;

/**
 * 用户与组
 * 
 * @author: Join
 */
public class IdentifyServiceTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    public void testUser() throws Exception {
        IdentityService identityService = activitiRule.getIdentityService();
        String userId = "henryyan";

        User user = identityService.newUser("henryyan");
        user.setEmail("henryyan@qq.com");
        user.setFirstName("Henry");
        user.setLastName("Yan");
        identityService.saveUser(user);

        User userInDb = identityService.createUserQuery().userId(userId).singleResult();
        Assert.assertNotNull(userInDb);

        identityService.deleteUser(userId);

        userInDb = identityService.createUserQuery().userId(userId).singleResult();
        Assert.assertNull(userInDb);

    }
    
}

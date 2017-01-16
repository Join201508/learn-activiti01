/* 
 * All rights Reserved, Designed By 农金圈
 * 2017年1月12日 上午10:22:27
 */
package com.join.learn.activiti.exercise.listener;

import org.junit.Test;

import com.join.learn.activiti.BaseActivitiTest;

/** 
 *
 * @author: Join 
 */
public class ListenerTest extends BaseActivitiTest {

    @Test
    public void deploy() {
        super.deploy("监听器测试", "ListenerProcess");
    }

}

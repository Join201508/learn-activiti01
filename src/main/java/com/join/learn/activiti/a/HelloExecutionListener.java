/* 
 * All rights Reserved, Designed By 农金圈
 * 2017年1月6日 下午1:49:36
 */
package com.join.learn.activiti.a;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;


/** 
 *
 * @author: Join 
 */
public class HelloExecutionListener implements ExecutionListener {

    /**
     * TODO
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * TODO
     * </p>
     * 
     * @param execution
     * @throws Exception
     */
    @Override
    public void notify(DelegateExecution execution) throws Exception {
        System.out.println(execution.getEventName());
        System.out.println(execution.getCurrentActivityId());
        System.out.println(execution.getCurrentActivityName());
        System.out.println(execution.getVariable("test_key"));
    }

}

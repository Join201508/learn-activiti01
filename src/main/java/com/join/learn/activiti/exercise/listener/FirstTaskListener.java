/* 
 * All rights Reserved, Designed By 农金圈
 * 2017年1月12日 上午11:09:08
 */
package com.join.learn.activiti.exercise.listener;

import java.util.Map;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.persistence.entity.VariableInstance;


/** 
 *
 * @author: Join 
 */
public class FirstTaskListener implements TaskListener {

    /**
     * TODO
     */
    private static final long serialVersionUID = 1L;

    /**
     * <p>
     * TODO
     * </p>
     * 
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("delegateTask.getEventName():" + delegateTask.getEventName());
        System.out.println(delegateTask.getExecution().getProcessBusinessKey());

        Map<String, VariableInstance> varMap = delegateTask.getVariableInstancesLocal();
        varMap.forEach((k, v) -> {
            System.out.println("k=" + k + ",v=" + v);
        });

        Set<String> varSet = delegateTask.getVariableNamesLocal();
        varSet.forEach(System.out::println);


    }

}

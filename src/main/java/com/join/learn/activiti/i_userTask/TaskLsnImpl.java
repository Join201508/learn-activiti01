
package com.join.learn.activiti.i_userTask;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 将该类配置到UserTask节点的Listener中
 * 
 * @author Administrator
 *
 */
public class TaskLsnImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        // 可以通过这种方式指派人
        // 也可以做其他额外操作
        delegateTask.setAssignee("张三");
    }

}

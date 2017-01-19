/* 
 * All rights Reserved, Designed By 农金圈
 * 2017年1月17日 下午3:46:17
 */
package com.join.learn.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.junit.Test;

/** 
 *
 * @author: Join 
 */
public class Test02 {
    
    
    @Test
    public void test(){
        ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);// 删除所有表，然后创建，单元测试清空数据时使用
        //config.setHistory(HistoryLevel.FULL.getKey());
        config.setHistoryLevel(HistoryLevel.FULL);
        ProcessEngine engine = config.buildProcessEngine();// 构建
        
        engine.getRuntimeService();
    }
}

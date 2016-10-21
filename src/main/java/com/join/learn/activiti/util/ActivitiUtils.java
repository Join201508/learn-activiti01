package com.join.learn.activiti.util;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public class ActivitiUtils {
	public static void dropCreateEngine(){
		//从默认配置文件activiti.cfg.xml创建ProcessEngineConfiguration
		ProcessEngineConfiguration config=ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
		config.setDatabaseSchemaUpdate("drop-create");//删除所有表，然后创建，单元测试清空数据时使用
		ProcessEngine engine=config.buildProcessEngine();//构建
		
		System.out.println("数据库："+config.getDatabaseType());
		System.out.println("url："+config.getJdbcUrl());
		System.out.println("用户名："+config.getJdbcUsername());
		System.out.println("密码："+config.getJdbcPassword());
		System.out.println("通过activiti.cfg.xml配置创建流程引擎:"+engine);
		System.out.println("引擎名称:"+engine.getName());
	}
	
}

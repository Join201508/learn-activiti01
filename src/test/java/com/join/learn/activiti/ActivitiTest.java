package com.join.learn.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class ActivitiTest {
	
	@Test
	public void createActiviti(){
		ProcessEngineConfiguration configuration=ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
		//数据库配置
		configuration.setJdbcDriver("com.mysql.jdbc.Driver");
		configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti_test?useUnicode=true&characterEncoding=utf8");
		configuration.setJdbcUsername("root");
		configuration.setJdbcPassword("123456");
		
		//DB_SCHEMA_UPDATE_TRUE：如果没有activiti相关表，则会自动创建 
		configuration.setDatabaseSchemaUpdate("drop-create");//(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);//("drop-create");//;
		
		ProcessEngine engine=configuration.buildProcessEngine();
		System.out.println("通过代码创建流程引擎:"+engine);
		System.out.println(configuration.getDatabaseType());
		System.out.println(configuration.getJdbcPassword());
	}
	
	public void createActiviti_2(){
		ProcessEngineConfiguration config=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti-cfg.xml");
		ProcessEngine engine=config.buildProcessEngine();
		System.out.println("通过activiti-cfg.xml配置创建流程引擎:"+engine);
	}
	
}

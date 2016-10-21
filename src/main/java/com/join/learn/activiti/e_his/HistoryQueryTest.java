package com.join.learn.activiti.e_his;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

public class HistoryQueryTest {

	private ProcessEngine engine=ProcessEngines.getDefaultProcessEngine();
	
	/**
	 * 查询历史流程
	 */
	@Test
	public void findHisProcessInstance(){
		String processInstanceId="101";
		HistoricProcessInstance hpi=engine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		System.out.println(hpi);
		
		/**
		 * 正在运行数据：engine.getRuntimeService()
		 * 历史数据查询：engine.getHistoryService()
		 */
		
		/*获取各种历史查询服务对象:*/
		engine.getHistoryService().createHistoricActivityInstanceQuery();//act_hi_actinst
		engine.getHistoryService().createHistoricDetailQuery();
		engine.getHistoryService().createHistoricProcessInstanceQuery();
		engine.getHistoryService().createHistoricTaskInstanceQuery();
		engine.getHistoryService().createHistoricVariableInstanceQuery();
		engine.getHistoryService().createNativeHistoricActivityInstanceQuery();
		engine.getHistoryService().createNativeHistoricProcessInstanceQuery();
		engine.getHistoryService().createNativeHistoricTaskInstanceQuery();
	} 
}

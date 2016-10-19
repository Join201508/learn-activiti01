/*初始化*/
SELECT * FROM `act_ge_property`; #主键生成策略等属性

/*流程部署*/
SELECT * FROM act_re_deployment; #部署对象表：部署一次增加一条记录
SELECT * FROM act_re_procdef;    #流程定义表：版本信息等，当多次部署的流程的key相同时，在此表中保存对应的版本信息(version_字段)
SELECT * FROM act_ge_bytearray;  #资源文件表

/*流程实例：act_ru开头的表*/
SELECT * FROM act_ru_execution; #正在执行的对象
SELECT * FROM act_ru_task;      #正在执行的任务表（只有节点是UserTask时，该表中才有数据）
SELECT * FROM act_hi_procinst;  #流程实例历史表，流程没有结束时end_time_为空
SELECT * FROM act_hi_taskinst;  #任务历史表（只有节点是UserTask时，该表中才有数据）
SELECT * FROM act_hi_actinst;   #所有活动节点的历史表

/*流程变量*/
SELECT * FROM act_ru_variable; #taskService.setVariableLocal时TASK_ID_有值，taskService.setVariable该值为空
SELECT * FROM act_hi_varinst ; #流程变量历史
SELECT * FROM act_ge_bytearray WHERE id_=1501;

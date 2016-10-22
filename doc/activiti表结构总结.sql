/*初始化*/
SELECT * FROM `act_ge_property`; #主键生成策略等属性

/*流程部署*/
SELECT * FROM act_re_deployment ORDER BY DEPLOY_TIME_ DESC; #部署对象表：部署一次增加一条记录
SELECT * FROM act_re_procdef ORDER BY DEPLOYMENT_ID_ DESC,VERSION_ DESC;    #流程定义表：版本信息等，当多次部署的流程的key相同时，在此表中保存对应的版本信息(version_字段)
SELECT * FROM act_ge_bytearray ORDER BY task_id_ DESC;  #资源文件表

/*流程实例：act_ru开头的表*/
SELECT * FROM act_ru_execution ORDER BY PROC_INST_ID_ DESC, ID_ ASC ; #正在执行的对象
SELECT * FROM act_ru_task ORDER BY EXECUTION_ID_ DESC;      #正在执行的任务表（只有节点是UserTask时，该表中才有数据）
SELECT * FROM act_ru_identitylink ORDER BY task_id_ DESC; #任务与处理人候选组关联表，任务可以指定给一组人处理

SELECT * FROM act_hi_procinst ORDER BY proc_inst_id_ DESC ;  #流程实例历史表，流程没有结束时end_time_为空
SELECT * FROM act_hi_taskinst ORDER BY EXECUTION_ID_ DESC;  #任务历史表（只有节点是UserTask时，该表中才有数据）
SELECT * FROM act_hi_actinst ORDER BY proc_inst_id_ DESC;   #所有活动节点的历史表
SELECT * FROM act_hi_identitylink ORDER BY task_id_ DESC; #任务与处理人候选组关联表，任务可以指定给一组人处理


/*流程变量*/
SELECT * FROM act_ru_variable; #taskService.setVariableLocal时TASK_ID_有值，taskService.setVariable该值为空
SELECT * FROM act_hi_varinst ; #流程变量历史
SELECT * FROM act_ge_bytearray WHERE id_=1501;

/*用户、角色管理*/
SELECT * FROM act_id_group;      #角色组表
SELECT * FROM act_id_user;       #用户表：
SELECT * FROM act_id_membership; #用户角色表


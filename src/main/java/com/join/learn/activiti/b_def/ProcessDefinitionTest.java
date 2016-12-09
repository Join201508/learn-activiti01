
package com.join.learn.activiti.b_def;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class ProcessDefinitionTest {

    // 从默认配置文件activiti.cfg.xml创建ProcessEngine
    private ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploymentProcessDefinition() {
        RepositoryService service = engine.getRepositoryService();
        Deployment deployment = service.createDeployment()// 创建一个部署对象
            .name("流程入门")// 指定流程
            .addClasspathResource("diagrams/helloworld.bpmn")// 加载资源文件
            .addClasspathResource("diagrams/helloworld.png")
            .deploy();// 完成部署

        System.out.println("流程ID：" + deployment.getId());
        System.out.println("流程名称：" + deployment.getName());
    }

    @Test
    public void deploymentProcessDefinitionFromZip() {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
        ZipInputStream zipInput = new ZipInputStream(in);
        RepositoryService service = engine.getRepositoryService();
        Deployment deployment = service.createDeployment()// 创建一个部署对象
            .name("流程入门zip")// 指定流程
            .addZipInputStream(zipInput)
            .deploy();// 完成部署

        System.out.println("流程ID：" + deployment.getId());
        System.out.println("流程名称：" + deployment.getName());
    }

    /**
     * 流程查询对象使用：ProcessDefinitionQuery
     */
    @Test
    public void findProcessDefinition() {
        List<ProcessDefinition> list = engine.getRepositoryService()
            .createProcessDefinitionQuery()// 返回ProcessDefinitionQuery

            /* 指定条件查询 */
            // .processDefinitionKey(processDefinitionKey)//通过流程key查询
            // .processDefinitionId(processDefinitionId)
            // .processDefinitionName(processDefinitionName)
            // .processDefinitionNameLike("")

            /* 指定排序方式 */
            .orderByProcessDefinitionVersion()
            .desc()// 按版本降序排列
            // .orderByProcessDefinitionKey().asc()

            /* 结果集处理 */
            .list();// 返回满足条件的所有数据
        // .singleResult()///返回唯一结果集(如果结果不唯一，抛异常)
        // .count()//统计数量
        // .listPage(firstResult, maxResults);//分页查询

        if (list.size() > 0) {
            for (ProcessDefinition pd : list) {
                // 对应act_re_procdef.deployment_id_ （即关联act_re_deployment.id_）
                System.out.println("部署对象ID：" + pd.getDeploymentId());// 如：601
                // 对应act_re_procdef.id_，生成规则：流程定义的key+版本+随机数
                System.out.println("流程定义ID：" + pd.getId());// 如：helloworld:3:604
                // 对应act_re_procdef.name_，即bpmn中process元素的name
                System.out.println("流程定义名称：" + pd.getName());// 如：helloworldProcess
                // 对应act_re_procdef.key_，即bpmn中process元素的id
                System.out.println("流程定义key:" + pd.getKey());// 如：helloworld
                // 对应act_re_procdef.version_
                System.out.println("流程定义版本：" + pd.getVersion());// 如：3
                // 对应act_re_procdef.resource_name_
                System.out.println("bpmn资源名：" + pd.getResourceName());// 如：helloworld.bpmn
                // 对应act_re_procdef.dgrm_resource_name_
                System.out.println("png资源名：" + pd.getDiagramResourceName());// 如：helloworld.png
                System.out.println("#######################################");
            }
        } else {
            System.out.println("没有数据");
        }
    }

    @Test
    public void startProcessIntance() {
        engine.getRuntimeService().startProcessInstanceByKey("helloworld");// 启动版本号最高的流程
    }


    /**
     * 删除流程
     */
    @Test
    public void deleteProcessDefinition() {
        String deploymentId = "601";
        engine.getRepositoryService().deleteDeployment(deploymentId);// 只能删除没有启动的流程，否则异常
        // .deleteDeployment(deploymentId,true);//级联删除：不管流程是否启动都可以删除
    }

    /**
     * 查看流资源文件
     * 
     * @throws IOException
     */
    @Test
    public void viewResource() throws IOException {
        String deploymentId = "601";

        List<String> list = engine.getRepositoryService().getDeploymentResourceNames(deploymentId);// 获取该流程的所有资源文件名

        for (String resourceName : list) {
            System.out.println(resourceName);
            InputStream in = engine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
            File file = new File("D://" + resourceName);
            FileOutputStream os = new FileOutputStream(file);
            int i = in.read();
            while (i != -1) {
                os.write(i);
                i = in.read();
            }
            os.flush();
            os.close();
            // while(in.read(b))
        }
        System.out.println("完成");

        // .getProcessDiagram(processDefinitionId)
    }
}

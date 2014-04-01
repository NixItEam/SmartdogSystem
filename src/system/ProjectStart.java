package system;

import java.util.List;

import config.ProConfig;
import dao.ConfigDao;
import service.ProjectService;
import thread.ConentThread;
import util.html.ToHtml;
import util.word.DicTyc;

public class ProjectStart {
  
  List<String> fileList = null; // 项目路径List
  DicTyc dictyc = new DicTyc(); // 同义词对象
  ProConfig proconfig = new ProConfig(); // 配置对象
  ConfigDao configdao = new ConfigDao(); // 配置dao
  ProjectService proservic = new ProjectService(); // 项目Service
  ToHtml html = new ToHtml();
  
  public ProjectStart() {
    
    projectStat();
  }
  
  /**
   * 项目运行开始
   */
  public void projectStat() {
    
    try {
      
      if (projectInit() > 0) {
        
        proservic.getUrl(configdao.getSoure()); // 分割源url
        configdao.deleRepurl(); // 二级地址去重
        
        ConentThread conThread = new ConentThread(configdao.getUrl());
        
        for (int i = 0; i < 5; i++) {
           
          new Thread(conThread).start();
        }
        
      }
      else {
       
        System.out.println("检测未通过,请检查配置!");
      }
    } 
    catch (Exception e) {
      
      System.out.println("projectStart类projectStat方法出现异常!");
      e.printStackTrace();
    }
  }
  
  /**
   * 程序运行监测运行环境
   */
  public int projectInit() {
    
    int i = 0; // 统计任务个数  
    
    try {
      
      System.out.println("^^^^^^Nixi军团智能狗自动站群管理系统 beta V1.0 运行环境检测 ^^^^^^^");
      
      fileList = proconfig.getConfigFile();
      
      for (i = 0; i < fileList.size(); i++) {
        
        System.out.println("您项目配置文件的地址为: " + fileList.get(i));
      }
      System.out.println("共计" + i + "个项目,如不符合实际数量请检查配置文件路径与命名格式.");
      System.out.println("~~检测已经完成,祝您在互联网淘金成功,技术问题请联系作者 fate' QQ:50660783^^^^^^^");
      
      configdao.deletePro();
      
      for ( i = 0; i < fileList.size(); i++) {
        
        // 插入数据库配置
        configdao.addProject(proconfig.getProconfig(fileList.get(i)));
      }
      
      System.out.println("配置数据插入成功!服务开始运行.");
      
      return i;
    }
    catch (Exception e) {
      
      System.out.println("projectStart类projectInit方法出现异常!");
      e.printStackTrace();
    }
    return 0;
  }
}

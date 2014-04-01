package config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import util.common.Common;

import bean.ProjectInfo;
import bean.UrlInfo;

/**
 * 项目配置类
 * @author fate
 */
public class ProConfig {


  /**
   * 读取配置文件
   * @param fileUrl 配置文件路径
   * @return
   */
  public ProjectInfo getProconfig(String fileUrl) {
    
    try {
    
      ProjectInfo projectinfo = new ProjectInfo(); // 生成任务实体类
      List<UrlInfo> urlList = new ArrayList<UrlInfo>(); // 生成采集源集合
      
      //读取项目配置文件
      File file = new File(fileUrl);
      InputStream in = new FileInputStream(file);
      
      Properties prts = new Properties();
      prts.load(in);
      
      projectinfo.setProjectName(prts.getProperty("projectName"));
      projectinfo.setDocumentRoot(prts.getProperty("documentRoot"));
      
      int pr_i = 1;
     
      do {

      	UrlInfo urlinfo = new UrlInfo(); // 生成采集源实体类
        urlinfo.setIndexStart(prts.getProperty("indexStart" + pr_i)); // 采集源地址
        urlinfo.setIndexRex(prts.getProperty("indexRex" + pr_i)); // 分析二级链接正则
        urlinfo.setConRex(prts.getProperty("conRex" + pr_i)); // 获取内容正则
        urlinfo.setTitleRex(prts.getProperty("titleRex" + pr_i));
        pr_i++;
        
        urlList.add(urlinfo);
        
      } while (prts.getProperty("indexStart" + pr_i) != null);
      
      projectinfo.setSoureList(urlList);
      
      return projectinfo;
      
    } 
    catch (Exception e) {
      
      System.out.println("proConfig类getProconfig方法出现异常!");
      e.printStackTrace();
    }
    
    return null;
  }
  
  /**
   * 获取所有项目配置文件地址
   * @return
   */
  public List<String> getConfigFile() {
    
    try {
      
      List<String> profileList = new ArrayList<String>(); // 存放项目配置文件路径
      
      File folder = new File(Common.localPath); // 获取当前环境运行的目录
      // 列出当前目录所有文件
      for (File file:folder.listFiles()) {
        
        if (isProject(file.toString())) {
          
          profileList.add(file.toString());
        }
      }
      
      return profileList;
    } 
    catch (Exception e) {
      
      System.out.println("proConfig类getConfigFile方法出现异常!");
      e.printStackTrace();
    }
    
    return null;
    
  }
  
  /**
   * 判断是否是项目配置文件
   * @param file 需要判断的文件的路径
   * @return
   */
  public boolean isProject(String file) {
    
    try {

      if ("_project.properties".equals(file.substring(file.length()-19))) {
        
        return true;
      }
    } 
    catch (Exception e) {
      
      System.out.println("proConfig类isProject方法出现异常!");
      e.printStackTrace();
    }
    
    return false;
  }
  
}

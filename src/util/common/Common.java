package util.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * 
 * @author fate
 * 用于加载同义词词典 关键字 配置文件值
 */
public class Common {

  // 系统运行路径
  public static String localPath = System.getProperty("user.dir");
  
  /**
   * 获取自定义的关键字
   * @return
   */
  public List<String> getKeyword() { 
    
    try {
      
      BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(Common.localPath + "/keyword.txt"), "utf-8"));
     
    } 
    catch (Exception e) {
      
      System.out.println("proConfig类getKeyword方法出现异常!");
      e.printStackTrace();
    }
    return null;
  }
}

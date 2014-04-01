package util.word;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import util.common.Common;

/**
 * 同义词加载类 
 */
public class DicTyc {

  public static final HashMap<String, String> tycMap = new HashMap<String, String>(); // 用来存放同义词Map
  
  /**
   * 构造方法, 第一次进入时加载同义词词典
   */
  public DicTyc() {
     
     int i = getSynonym(); 
     
     if (i > 0) {
       
      System.out.println("恭喜:伪原创词典加载成功! 词量为: " + i);
    }
  }
  
  /**
   * 读取同义词词库
   * @return
   */
  public int getSynonym() {
    
    try {
      
      BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(Common.localPath + "/src/tongyici/dlc/tycdlc.txt"), "utf-8"));
      
      String str = "";
      
      while ((str = rd.readLine()) != null) {

        tycMap.put(str.split("-")[0], str.split("-")[1]);
      }
      
      return tycMap.size();
    } 
    catch (Exception e) {
      
      System.out.println("dicTyc类getSynonym方法出现异常!");
      e.printStackTrace();
    }
    
    return 0;
  }
}

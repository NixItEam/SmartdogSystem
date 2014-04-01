package util.html;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 采集HTML类
 * @author fate
 *
 */
public class ToHtml {
  
  /**
   * 采集html首页
   * @param url 采集地址
   * @return
   */
  public String saveHtml(String url) {
    
    try {
      // 采集地址
      URL u1 = new URL(url);
      InputStreamReader isr = new InputStreamReader(u1.openStream(),"gbk");
      BufferedReader bufred = new BufferedReader(isr);
      
      String htmlVal = "";
      
      while ((url = bufred.readLine()) != null) {
       
         htmlVal += url;
      }
      bufred.close();
      return htmlVal;
    } 
    catch (Exception e) {
     
      System.out.println("toHtml类saveHtml方法出现异常!");
      e.printStackTrace();
    }
    
    return null;
  }
  
  /**
   * 过滤html标签,仅用于中文文章采集!
   * @param htmlValue 需要过滤的html标签
   * @return
   */
  public String getDeltag(String htmlValue) {
    
    try {
      
      Pattern pat = Pattern.compile("[^A-Za-z<>=\\s/&:]");  
      Matcher mat = pat.matcher(htmlValue); 
      htmlValue = "";
      while (mat.find()) {

        htmlValue += mat.group();
      }
      return htmlValue;
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类getDeltag方法出现异常!");
      e.printStackTrace();
    }
     
    return null;
  }
  
  
  /**
   * 提取界面文章内容
   * @param patt 正则 
   * @param htmlValue 采集到的源码
   */
  public String getPatternhtmlContent(String patt, String htmlValue) {
    
    try {
      
      Pattern pat = Pattern.compile(patt,Pattern.DOTALL);  
      Matcher mat = pat.matcher(htmlValue);
      
      htmlValue = "";
      
      while (mat.find()) {

        htmlValue += mat.group();
      }
      
      return htmlValue;
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类getPatternhtmlContent方法出现异常!");
      e.printStackTrace();
    }
    return null;
  }
  
  
  /**
   * 提取采集二级目录
   * @param patt 正则 
   * @param htmlValue 采集到的源码
   */
  public List<String> getPatternhtmlContent(String patt, String htmlValue, String type) {
    
    try {
      
      Pattern pat = Pattern.compile(patt,Pattern.DOTALL);  
      Matcher mat = pat.matcher(htmlValue);
      
      List<String> urlList = new ArrayList<String>();
      
      while (mat.find()) {

        urlList.add(mat.group());
      }
      
      return urlList;
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类getPatternhtmlContent-List<String>方法出现异常!");
      e.printStackTrace();
    }
    return null;
  }
  
  /**
   * 正则表达式提取图片地址
   * @param patt
   * @param htmlValue
   * @return
   */
  public List<String> getPatternhtmlImage(String patt, String htmlValue) {
    
    try {
      
      System.out.println("进入提取方法 提取到的正则为: " + patt);
      System.out.println("获取到的源码为: " + htmlValue);
      // 创建正则表达式编译实例
      Pattern pat = Pattern.compile(patt);  
      Matcher mat = pat.matcher(htmlValue);  
      
      List<String> imgList = new ArrayList<String>();
      
      int i = 0;
      
      while (mat.find()) {
      
        imgList.add(mat.group());
        i++;
      }
      System.out.println("一共有 " + i + "张图片.");
      
      return imgList;
    } 
    catch (Exception e) {
      
       System.out.println("toHtml类getPatternhtml方法出现异常!");
       e.printStackTrace();
      
    }
    
    return null;
  } 
  
  
  /**
   * 分割采集源
   * @param url 采集源地址
   */
  public List<String> spliUrls(String url) {
    
    try {
      
      String rex = "\\{\\d*-\\d*\\}";
      
      Pattern pat = Pattern.compile(rex);  // 分割采集源正则
      
      Matcher mat = pat.matcher(url);
      
      String urlval = "";
          
      while (mat.find()) {

        urlval += mat.group();
      }
      
      urlval = urlval.replace("{", "");
      urlval = urlval.replace("}", "");
     
      String[] uarry = urlval.split("-");
      
      System.out.println(uarry[0] + "/" + uarry[1]);

      List<String> soureList = new ArrayList<String>(); // 存放采集地址集合
      
      for (int i =  Integer.parseInt(uarry[0]); i <= Integer.parseInt(uarry[1]); i++) {
        
        soureList.add(url.replaceAll(rex, i+""));
       
      }
      
      return soureList;
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类spliUrls方法出现异常!");
      e.printStackTrace();
    }
    
    return null;
  }
}

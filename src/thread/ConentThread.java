package thread;

import java.sql.SQLException;
import java.util.List;

import dao.ConfigDao;

import util.html.ToHtml;

import bean.UrlInfo;

/**
 * 多线程采集内容并入库
 * @author fate
 */
public class ConentThread implements Runnable{

  ConfigDao configdao = new ConfigDao(); // 配置dao
  
  ToHtml tohtml = new ToHtml();
  
  List<UrlInfo> urlList = null;
  
  int size = 0;
  
  public ConentThread(List<UrlInfo> list) {
    
    this.urlList = list;
    this.size = list.size();
    System.out.println("List SIze " + list.size());
  }
  
  public void run() {
    
    while (size > 0) {
      
      insertCont();
    }
  }
  
  /**
   * 插入内容方法
   */
  public synchronized void insertCont() {
    
    if (size > 0) {
      
      size--;

      try {
        
        // 获取标题
        urlList.get(size).setTitle(tohtml.getDeltag(tohtml.getPatternhtmlContent(urlList.get(size).getTitleRex(),tohtml.saveHtml(urlList.get(size).getUrl())))); 
        // 获取内容
        urlList.get(size).setContent(tohtml.getDeltag(tohtml.getPatternhtmlContent(urlList.get(size).getConRex(),tohtml.saveHtml(urlList.get(size).getUrl())))); 
        // 插入到内容表
        configdao.addContent(urlList.get(size));
      } 
      catch (SQLException e) {

        System.out.println("ConentThread类insertCont方法出现异常!");
        e.printStackTrace();
      }
    }
  }
}

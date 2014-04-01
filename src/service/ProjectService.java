package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.html.ToHtml;
import util.word.DicTyc;
import config.ProConfig;
import dao.ConfigDao;

import bean.UrlInfo;

/**
 * 项目Service
 * @author fate
 *
 */
public class ProjectService {

  List<String> fileList = null; // 项目路径List
  DicTyc dictyc = new DicTyc(); // 同义词对象
  ProConfig proconfig = new ProConfig(); // 配置对象
  ConfigDao configdao = new ConfigDao(); // 配置dao
  ToHtml html = new ToHtml();
  
  /**
   * 获取二级采集地址
   * @param urlList 采集源列表
   * @throws SQLException 
   */
  public void getUrl(List<UrlInfo> urlList) throws SQLException {
    
    List<String> soureList = new ArrayList<String>();
    
    for (UrlInfo urlinfo : urlList) {
     
      soureList = html.spliUrls(urlinfo.getIndexStart());
      
      for (int i = 0; i < soureList.size(); i++) {
        
        urlinfo.setUrlList(html.getPatternhtmlContent(urlinfo.getIndexRex(),html.saveHtml(soureList.get(i)), ""));
        configdao.addUrl(urlinfo);
      }
    }
  }
}

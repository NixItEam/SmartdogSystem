package bean;

import java.util.List;

/**
 * 采集源实体类
 * @author fate
 *
 */
public class UrlInfo {

  private int id; // 采集源编号
  
  private String indexStart; // 采集地址
  
  private String indexRex; // 分析地址正则
  
  private String conRex; // 抓取内容正则

  private int Project_Id; // 项目ID
  
  private String titleRex; // 采集标题正则
  
	private List<String> urlList; // 二级地址集合
	
	private String url; // 采集二级地址
	
	private String source_Id; // 采集源id

	private String htmlVal; // html 源码
	
	private String title; // 获取到的标题
	
	private String content; // 获取到的内容
	
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getHtmlVal() {
    return htmlVal;
  }

  public void setHtmlVal(String htmlVal) {
    this.htmlVal = htmlVal;
  }

  public String getSource_Id() {
    return source_Id;
  }

  public void setSource_Id(String source_Id) {
    this.source_Id = source_Id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getTitleRex() {
		return titleRex;
	}

	public void setTitleRex(String titleRex) {
		this.titleRex = titleRex;
	}
	
  public List<String> getUrlList() {
    return urlList;
  }

  public void setUrlList(List<String> urlList) {
    this.urlList = urlList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getProject_Id() {
    return Project_Id;
  }

  public void setProject_Id(int project_Id) {
    Project_Id = project_Id;
  }

  public String getIndexStart() {
    return indexStart;
  }

  public void setIndexStart(String indexStart) {
    this.indexStart = indexStart;
  }

  public String getIndexRex() {
    return indexRex;
  }

  public void setIndexRex(String indexRex) {
    this.indexRex = indexRex;
  }

  public String getConRex() {
    return conRex;
  }

  public void setConRex(String conRex) {
    this.conRex = conRex;
  }
}

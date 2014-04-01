package bean;

import java.util.List;



/**
 * 任务实体类
 * @author fate
 *
 */
public class ProjectInfo {

  private String projectName; // 采集项目名称
  
  private String documentRoot; // 发布的网站目录根路径
	
	private List<UrlInfo> soureList; // 采集源地址
	
	private String startTime; // 采集开始时间

  public String getDocumentRoot() {
    return documentRoot;
  }

  public void setDocumentRoot(String documentRoot) {
    this.documentRoot = documentRoot;
  }

  public List<UrlInfo> getSoureList() {
    return soureList;
  }

  public void setSoureList(List<UrlInfo> soureList) {
    this.soureList = soureList;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
  
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(String projectName) {
    this.projectName = projectName;
  }

}

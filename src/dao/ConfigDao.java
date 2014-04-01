package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bean.ProjectInfo;
import bean.UrlInfo;
import config.DataBase;

/**
 * 读取配置Dao层处理
 * @author fate
 *
 */
public class ConfigDao {
  
  DataBase db = new DataBase(); // 数据库连接公共类对象

  /**
   * 把配置文件插入数据库
   * @param proInfo 项目配置文件实体类
   * @return
   * @throws SQLException
   */
	public int addProject(ProjectInfo proInfo) throws SQLException {
    
      // 插入项目配置表
      String sqlPro = " insert into project_info (projectname, documentroot, projecttype) values(?, ?, '1')"; 
      // 插入采集源表
      String sqlSoure = " insert into project_soure (project_id, indexstart, indexrex, conrex, titleRex) values ((select id from project_info where projectname = ?), ?, ?, ?, ?)";
      
      PreparedStatement ps = null;
      Connection conn = db.getConn();
      try {
        
        conn.setAutoCommit(false); // 设置事物
        
        ps = conn.prepareStatement(sqlPro);
        ps.setString(1, proInfo.getProjectName()); // 项目名称
        ps.setString(2, proInfo.getDocumentRoot()); // 项目路径
        
        int addPro = ps.executeUpdate(); // 获取增加项目成功行数
        
        ps.clearParameters();
        ps = conn.prepareStatement(sqlSoure);
        ps.setString(1, proInfo.getProjectName()); // 项目名称
        
        if (addPro > 0) {
          
          for (int i = 0; i < proInfo.getSoureList().size(); i++) {
            
            ps.setString(2, proInfo.getSoureList().get(i).getIndexStart()); // 采集源
            ps.setString(3, proInfo.getSoureList().get(i).getIndexRex()); // 分析二级连接正则
            ps.setString(4, proInfo.getSoureList().get(i).getConRex()); // 采集内容正则
            ps.setString(5, proInfo.getSoureList().get(i).getTitleRex()); // 采集内容标题正则
            
            addPro += ps.executeUpdate();
          }
          
        }
        conn.commit(); 
        return addPro;
      } 
      catch (Exception e) {
        
        conn.rollback(); 
        System.out.println("configDao类addProject方法出现异常!");
        e.printStackTrace();
      }
      finally {
        
        try {
            
          ps.close();
          db.dbColse(conn, null);
          
        } 
        catch (SQLException e) {
  
          e.printStackTrace();
        }
      }
      return 0;
    }
    
    /**
     * 删除项目配置数据库与采集源数据库
     * @throws SQLException 
     */
		public void deletePro() throws SQLException {
    	
    	// 删除项目配置表
      String delPro = " delete from project_info"; 
      // 删除采集源表
      String delsoure = " delete from project_soure"; 
      // 删除二级地址表
      String delurl = " delete from project_url"; 
      
      PreparedStatement ps = null;
      Connection conn = db.getConn();
    
      try {
    		
    		conn.setAutoCommit(false); // 设置事物
    		
    	  ps = conn.prepareStatement(delsoure); // 删除采集源表
        ps.executeUpdate();
        ps.clearParameters();
        ps = conn.prepareStatement(delPro); // 删除项目配置表
        ps.executeUpdate();
        ps = conn.prepareStatement(delurl); // 删除项目配置表
        ps.executeUpdate();
       
        conn.commit();
        System.out.println("采集数据库已经清空,正等待重新插入配置!");
    		
  		} 
    	catch (Exception e) {
    		
    		conn.rollback();
    		System.out.println("configDao类deletePro方法出现异常!");
    		e.printStackTrace();
  		}
    	 finally {
         
         try {
          
           ps.close();
           db.dbColse(conn, null);
         } 
         catch (SQLException e) {
  
           e.printStackTrace();
         }
       }
    }
  	
  	/**
  	 * 获取项目配置中的采集源
  	 * @return
  	 * @throws SQLException 
  	 */
  	public List<UrlInfo> getSoure() throws SQLException {
  	  
  	  String sqlSoure = " select id, project_id, indexstart, indexrex, conrex, titlerex from project_soure";
  	  Connection conn = db.getConn();
  	  ResultSet rs = null;
  	  Statement stat = conn.createStatement();
  	  try {
        
  	    rs = stat.executeQuery(sqlSoure);
        
  	    List<UrlInfo> urlList = new ArrayList<UrlInfo>();
  	    
  	    while (rs.next()) {
  	      
  	      UrlInfo urlinfo = new UrlInfo();
  	      
  	      urlinfo.setId(rs.getInt("ID")); //  采集源编号
  	      urlinfo.setProject_Id(rs.getInt("PROJECT_ID")); // 项目编号 
  	      urlinfo.setIndexStart(rs.getString("INDEXSTART")); // 采集源地址
  	      urlinfo.setIndexRex(rs.getString("INDEXREX")); // 采集源二级目录分析地址正则
  	      urlinfo.setConRex(rs.getString("CONREX")); // 内容采集正则
  	      urlinfo.setTitleRex(rs.getString("TITLEREX")); // 内容标题正则
  	      
  	      urlList.add(urlinfo);
  	    }
  	    
  	    return urlList;
      } 
  	  catch (Exception e) {
  	    
  	    System.out.println("configDao类getSoure方法出现异常!");
        e.printStackTrace();
  	  }
  	  finally {
  	    
  	    rs.close();
  	    db.dbColse(conn, stat);
  	  }
  	  
  	  return null;
  	}
  	
  	/**
  	 * 二级链接采集地址去重
  	 * @throws SQLException 
  	 */
  	public void deleRepurl() throws SQLException {
  	  
  	  String dele_urlsql = " delete project_url from project_url , (select id from project_url group by url having count(*)>1 ) as prourl where project_url.id = prourl.id";
  	  
      PreparedStatement ps = null;
      Connection conn = db.getConn();
      
      try {
        
        ps = conn.prepareStatement(dele_urlsql); // 删除采集源表
        
        int count = 0;
        
        System.out.println("二级采集地址开始去重.......");
        
        while ((count = ps.executeUpdate()) > 0) {
        	
        	System.out.println("去除 " + count + "条重复二级采集地址.");
					
				}
        
      } 
      catch (Exception e) {
        
        System.out.println("configDao类deleRepurl方法出现异常!");
        e.printStackTrace();
      }
      finally {
        
        ps.close();
        db.dbColse(conn, null);
      }
  	}
  	
  	/**
  	 * 增加二级采集地址
  	 * @param urlinfo 采集地址实体类
  	 * @return
  	 * @throws SQLException 
  	 */
  	public int addUrl(UrlInfo urlinfo) throws SQLException {
  	  
  	  String addUrl_sql = " insert into project_url (project_id, source_id, url, titlerex, conrex) values(?, ?, ?, ?, ?)";
  	  
  	  PreparedStatement ps = null;
  	  Connection conn = db.getConn();
  	  
  	  try {
        
  	    ps = conn.prepareStatement(addUrl_sql);
  	    ps.setInt(1, urlinfo.getProject_Id()); // 项目ID
  	    ps.setInt(2, urlinfo.getId()); // 采集源ID
  	    ps.setString(4, urlinfo.getTitleRex()); // 采集内容标题正则
  	    ps.setString(5, urlinfo.getConRex()); // 采集内容正则
  	    
  	    for (String str:urlinfo.getUrlList()) {
  	      
  	      System.out.println("正在插入二级地址:" + str);
  	      
  	      ps.setString(3, str);
  	      ps.executeUpdate();
  	    }
  	    
      } catch (Exception e) {
        
        System.out.println("configDao类addUrl方法出现异常!");
        e.printStackTrace();
      }
  	  finally {
  	    
  	    ps.close();
  	    db.dbColse(conn, null);
  	  }
      return 0;
  	}
  	
  	/**
  	 * 获取URL
  	 * @return
  	 * @throws SQLException
  	 */
  	public List<UrlInfo> getUrl() throws SQLException {
  	  
  	  String sqlSoure = " select id, project_id, source_id, url, conrex, titlerex from project_url";
      Connection conn = db.getConn();
      ResultSet rs = null;
      Statement stat = conn.createStatement();
      try {
        
        rs = stat.executeQuery(sqlSoure);
        
        List<UrlInfo> urlList = new ArrayList<UrlInfo>();
        
        while (rs.next()) {
          
          UrlInfo urlinfo = new UrlInfo();
          
          urlinfo.setId(rs.getInt("ID")); //  采集源编号
          urlinfo.setProject_Id(rs.getInt("PROJECT_ID")); // 项目编号 
          urlinfo.setIndexStart(rs.getString("SOURCE_ID")); // 采集源地址
          urlinfo.setUrl(rs.getString("URL")); // 采集源二级目录分析地址正则
          urlinfo.setConRex(rs.getString("CONREX")); // 内容采集正则
          urlinfo.setTitleRex(rs.getString("TITLEREX")); // 内容标题正则
          
          urlList.add(urlinfo);
        }
        
        return urlList;
      } 
      catch (Exception e) {
        
        System.out.println("configDao类getSoure方法出现异常!");
        e.printStackTrace();
      }
      finally {
        
        rs.close();
        db.dbColse(conn, stat);
      }
      return null;
  	}

  	/**
  	 * 插入采集内容
  	 * @param urlInfo
  	 * @return
  	 * @throws SQLException 
  	 */
  	public int addContent(UrlInfo urlInfo) throws SQLException {
  	  
  	  String addContent_sql = " insert into project_content (title, content, project_id, soure_id, createdate) values(?, ?, ?, ?, null)";
      
      PreparedStatement ps = null;
      Connection conn = db.getConn();
      
      try {
        
        ps = conn.prepareStatement(addContent_sql);
        ps.setString(1, urlInfo.getTitle()); // 插入标题
        ps.setString(2, urlInfo.getContent()); // 插入内容
        ps.setInt(3, urlInfo.getProject_Id()); // 项目id
        ps.setString(4, urlInfo.getSource_Id()); // 采集源id
        System.out.println("采集一条内容, 标题为:" + urlInfo.getTitle());
        return ps.executeUpdate();
      } 
      catch (Exception e) {
        
        System.out.println("configDao类addContent方法出现异常!");
        e.printStackTrace();
      }
      finally {
        
        ps.close();
        db.dbColse(conn, null);
      }
      
      return 0;
  	}
  	
}

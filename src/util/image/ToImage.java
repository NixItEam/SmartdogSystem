package util.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import util.common.Common;

/**
 * 对图片文件操作
 * @author fate
 *
 */
public class ToImage {
  
  public String downImage(String imageUrl) {
   
    BufferedImage image = null;
    
    URL imgUrl = null; 
    
    try {
    
      imgUrl = new URL(imageUrl);
      
      image = ImageIO.read(imgUrl);
      
      ImageIO.write(image, "jpg", new File(Common.localPath + "/" + System.currentTimeMillis() + ".jpg"));
      
      System.out.println("保存");
    } 
    catch (Exception e) {
      
      e.printStackTrace();
    }
    
    return null;
    
  }
  
  /**
   * 重命名图片文件
   * @param fileSet
   * @param i
   */
  public void reNameImg(Set<String> fileSet, int i) {
   
   File file;

   java.util.Calendar c = java.util.Calendar.getInstance();    
   java.text.SimpleDateFormat f = new java.text.SimpleDateFormat("yyyyMMdd");    
   String time = f.format(c.getTime());
   
   for (String imgFile : fileSet) {
     
     file = new File(imgFile);
     file.renameTo(new File(time + "" + i++ + ".jpg"));
   }
 }
  
  /**
   * 列出所有图片文件 
   */
  public Set<String> searchImage() {
    
    Set<String> imgSet = new HashSet<String>();
    
    File folder = new File(Common.localPath);
    
    for (File file : folder.listFiles()) {
      
      if (isImage(file.getAbsolutePath())) {
        
        imgSet.add(file.getAbsolutePath());
        
        System.out.println("图片路径: "+ file.getAbsolutePath());
      }
    }
    
    return imgSet;
  }
  
  /**
   * 判断是否是jpg文件
   * @param file
   * @return
   */
  public boolean isImage(String file) {
    
    if (("jpg").equals(file.substring(file.length()-3))) {
      
      return true;
    }
    
    return false;
  }
}

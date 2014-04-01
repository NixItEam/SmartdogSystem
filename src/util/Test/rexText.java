package util.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 文本去重
 * @author fate
 *
 */
public class rexText {
  
  public void rexBigtext() {
    
    try {
      
      long time = System.currentTimeMillis();
      
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("f:/tongyici/1.txt"),"utf-8"));
      FileWriter fw = new FileWriter("f:/tongyici/x.txt");
      String str = "";
      int o = 0;
      Set<String> uniqe = new LinkedHashSet<String>();
      while ((str=br.readLine())!=null) {
        o++;
        uniqe.add(str);
      }
      for (String s:uniqe) {
        fw.write(s+"\n");
      }
      br.close();
      fw.flush();
      fw.close();
      System.out.println("共" + o + "条数据");
      System.out.println(System.currentTimeMillis()-time);
    } 
    catch (Exception e) {
      
      e.printStackTrace();
    }
  }
}

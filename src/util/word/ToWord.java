package util.word;

import java.io.StringReader;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

/**
 * 分词伪原创类
 * @author fate
 *
 */
public class ToWord {

  /**
   * 分词
   * @param value
   * @return
   */
  public String solitWord(String value) {
    
    Analyzer anal = new PaodingAnalyzer();
    
    try {
      
      StringReader strreader = new StringReader(value);
      
      TokenStream ts = anal.tokenStream(value, strreader);
      TermAttribute termAtt = (TermAttribute)ts.getAttribute(TermAttribute.class);
      
      value = "";
      
      while (ts.incrementToken()) {
        
        value += reSynonym(termAtt.term());
      }
      
      return value;
    } 
    catch (Exception e) {
      
      System.out.println("toWord类solitWord方法出现异常!");
      e.printStackTrace();
    }
    
    return null;
  }

  /**
   * 因为庖丁解牛不支持保留标点符号,所以标点进行替换
   * @param htmlValue
   * @return
   */
  public String  replWord(String htmlValue) {
  
    try {
      
      htmlValue = htmlValue.replaceAll("\\.", "点");
      htmlValue = htmlValue.replaceAll("。", "句号");
      htmlValue = htmlValue.replaceAll("；", "分号");
      htmlValue = htmlValue.replaceAll("！", "感叹号");
      htmlValue = htmlValue.replaceAll("？", "问号");
      htmlValue = htmlValue.replaceAll("：", "冒号");
      htmlValue = htmlValue.replaceAll("“", "左引号");
      htmlValue = htmlValue.replaceAll("”", "右引号");
      htmlValue = htmlValue.replaceAll("，", "逗号");
      
      return htmlValue;
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类getDeltag方法出现异常!");
      e.printStackTrace();
    }
    
   return htmlValue;
  }
  
  /**
   * 将文字替换成标点
   * @param htmlValue
   * @return
   */
  public String  replWordzi(String htmlValue) {
  
    try {
      
      htmlValue = htmlValue.replaceAll("点", ".");
      htmlValue = htmlValue.replaceAll("句号", "。");
      htmlValue = htmlValue.replaceAll("分号", "；");
      htmlValue = htmlValue.replaceAll("感叹号", "！");
      htmlValue = htmlValue.replaceAll("问号", "？");
      htmlValue = htmlValue.replaceAll("冒号", "：");
      htmlValue = htmlValue.replaceAll("左引号", "“");
      htmlValue = htmlValue.replaceAll("右引号", "”");
      htmlValue = htmlValue.replaceAll("逗号", "，");
      
      return htmlValue;
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类getDeltag方法出现异常!");
      e.printStackTrace();
    }
    
   return htmlValue;
  }
  
  /**
   * 同义词替换
   * @param key
   * @return
   */
  public String reSynonym(String key) {
    
    try {
      
      if (DicTyc.tycMap.get(key) != null) {
        
        System.out.println("将 " + key + "替换为: " + DicTyc.tycMap.get(key));
        
        key = DicTyc.tycMap.get(key);
      }
    } 
    catch (Exception e) {
      
      System.out.println("toHtml类reSynonym方法出现异常!");
      e.printStackTrace();
    }
    
    return key;
  }
}

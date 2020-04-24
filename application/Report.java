package application;
import java.util.TreeSet;

public class Report {

  private String title;
  private TreeSet <Milk> content;
  
  
  public Report(String title) {
    super();
    this.title = title;
  }
  
  
  @Override
  public String toString() {
    
    StringBuilder sb = new StringBuilder();
    sb.append ("Report for ");
    sb.append (this.title);
    sb.append("\n");
    for (Milk m: content) {
      sb.append (m);
      sb.append ("\n");
    }
    
    return sb.toString();
  }
  
  
  public void addMilk (Milk milk) {
    content.add (milk);
  }
  
  
  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }
  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }
  /**
   * @return the content
   */
  public TreeSet<Milk> getContent() {
    return content;
  }
  /**
   * @param content the content to set
   */
  public void setContent(TreeSet<Milk> content) {
    this.content = content;
  }

  
  
}

package application;

/**
 * @author Zhonggang(John) Li
 * This is a class used to help generate report and display it in javafx 
 *
 */
public class FarmIDReport {
  /**
   * @return the monthStr
   */
  public String getMonthStr() {
    return monthStr;
  }
  /**
   * @param monthStr the monthStr to set
   */
  public void setMonthStr(String monthStr) {
    this.monthStr = monthStr;
  }
 /**
   * @return the weightStr
   */
  public String getWeightStr() {
    return weightStr;
  }
 /**
   * @param weightStr the weightStr to set
   */
  public void setWeightStr(String weightStr) {
    this.weightStr = weightStr;
  }
  /**
   * @return the percentageStr
   */
  public String getPercentageStr() {
    return percentageStr;
  }

  /**
   * @param percentageStr the percentageStr to set
   */
  public void setPercentageStr(String percentageStr) {
    this.percentageStr = percentageStr;
  }


  private String monthStr;
  private String weightStr;
  private String percentageStr;

  public FarmIDReport(String monthStr, String weightStr, String percentageStr) {
    super();
    this.monthStr = monthStr;
    this.weightStr = weightStr;
    this.percentageStr = percentageStr;
  }

}

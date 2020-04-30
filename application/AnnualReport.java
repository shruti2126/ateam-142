package application;

/**
 * @author Zhonggang(John) Li
 * This is a class used to help generate report and display it in javafx 
 *
 */
public class AnnualReport {
  /**
   * @return the monthStr
   */
  public String getFarmIDStr() {
    return FarmIDStr;
  }
  /**
   * @param monthStr the monthStr to set
   */
  public void setFarmIDStr(String FarmIDStr) {
    this.FarmIDStr = FarmIDStr;
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


  private String FarmIDStr;
  private String weightStr;
  private String percentageStr;

  public AnnualReport(String FarmIDStr, String weightStr, String percentageStr) {
    super();
    this.FarmIDStr = FarmIDStr;
    this.weightStr = weightStr;
    this.percentageStr = percentageStr;
  }

}

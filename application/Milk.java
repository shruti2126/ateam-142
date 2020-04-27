package application;
import java.util.Date;

/**
 * @author Zhonggang (John) Li
 * this class is the Milk, used to store the milk information for each record 
 *
 */
public class Milk implements Comparable<Milk> {
  /**
   *Since milk implements comparable, it should be compared.
   *In this case, Milk can be stored in TreeSet and comes with the heaviest one
   */
  @Override
  public int compareTo(Milk o) {
    return o.weight -this.weight;
  }
  
  /**
   * To string method, can be used to printout for user view
   */
  @Override
  public String toString() {
    return "Milk [date=: " + date + ", farmID: " + farmID + ", weight: " + weight + "]";
  }


  private Date date; // produced date
  private String farmID; // farmID, 
  private int weight; // the milk weight
  
  
  
  /**
   * Construction method to generate milk instance
   * @param date
   * @param farmID
   * @param weight
   */
  public Milk(Date date, String farmID, int weight) {
    super();
    this.date = date;
    this.farmID = farmID;
    this.weight = weight;
  }

  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((date == null) ? 0 : date.hashCode());
    result = prime * result + ((farmID == null) ? 0 : farmID.hashCode());
    result = prime * result + weight;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Milk other = (Milk) obj;
    if (date == null) {
      if (other.date != null)
        return false;
    } else if (!date.equals(other.date))
      return false;
    if (farmID == null) {
      if (other.farmID != null)
        return false;
    } else if (!farmID.equals(other.farmID))
      return false;
    if (weight != other.weight)
      return false;
    return true;
  }


  
  
  /**
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @return the farmID
   */
  public String getFarmID() {
    return farmID;
  }
  /**
   * @param farmID the farmID to set
   */
  public void setFarmID(String farmID) {
    this.farmID = farmID;
  }

  /**
   * @return the weight
   */
  public int getWeight() {
    return weight;
  }

  /**
   * @param weight the weight to set
   */
  public void setWeight(int weight) {
    this.weight = weight;
  }


  
}

package application;
import java.util.TreeSet;
public class Farm {
  private String farmID;
  private TreeSet<Milk> farmProduct;

  public Farm(String farmID, TreeSet<Milk> farmProduct) {
    super();
    this.farmID = farmID;
    this.farmProduct = farmProduct;
  }  
    
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((farmID == null) ? 0 : farmID.hashCode());
    result = prime * result + ((farmProduct == null) ? 0 : farmProduct.hashCode());
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
    Farm other = (Farm) obj;
    if (farmID == null) {
      if (other.farmID != null)
        return false;
    } else if (!farmID.equals(other.farmID))
      return false;
    if (farmProduct == null) {
      if (other.farmProduct != null)
        return false;
    } else if (!farmProduct.equals(other.farmProduct))
      return false;
    return true;
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
   * @return the farmProduct
   */
  public TreeSet<Milk> getFarmProduct() {
    return farmProduct;
  }
  /**
   * @param farmProduct the farmProduct to set
   */
  public void setFarmProduct(TreeSet<Milk> farmProduct) {
    this.farmProduct = farmProduct;
  }

  
  
}

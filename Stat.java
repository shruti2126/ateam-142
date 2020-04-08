import java.io.File;

public interface Stat {
  void readSingleFile();
  void readMultipleFile();
  void showByFarmID();
  void showByMonth();
  void showByFullYear();
  
  File exportReport();
  
}

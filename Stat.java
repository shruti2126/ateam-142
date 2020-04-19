import java.io.File;

// this is the interface for the main class

public interface Stat {
  void readSingleFile();
  void readMultipleFile();
  void showByFarmID();
  void showByMonth();
  void showByFullYear();
  
  File exportReport();
  
}

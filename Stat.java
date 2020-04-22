import java.io.File;

// this is the interface for the main class

public interface Stat {
  void readSingleFile(File file);
  void readMultipleFile(File[] files);
  Report showByFarmID();
  Report showByMonth();
  Report showByFullYear();
  Report showByDate();
  File exportReport(Report report);
  
}

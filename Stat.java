import java.io.File;
import java.util.Date;

// this is the interface for the main class

public interface Stat {
  void readSingleFile(File file);
  void readMultipleFile(File[] files);
  Report showByFarmID(String id);
  Report showByMonth(int momth);
  Report showByFullYear();
  Report showByDate(Date start, Date end);
  File exportReport(Report report);
  void clearData();
}

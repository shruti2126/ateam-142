package application;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;

// this is the interface for the main class

public interface Stat {
  void readSingleFile(String filename);
  void readMultipleFile(String[] filenames);
  Report showByFarmID(String id);
  Report showByMonth(int momth);
  Report showByFullYear();
  Report showByDate(LocalDate start, LocalDate end);
  File exportReport(Report report);
  void clearData();
}

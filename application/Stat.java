package application;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;

// this is the interface for the main class

/**
 * @author Zhonggang (John) Li
 *
 */
public interface Stat {
  void readSingleFile(String filename); // readFile and put records into data containers
  void readMultipleFile(String[] filenames); // readmultiple files at the same time
  Report showByFarmID(String id); // generate report based on farmID
  Report showByMonth(int momth); // generate report based on month
  Report showByFullYear(); // generate report for full year
  Report showByDate(LocalDate start, LocalDate end); // specify the date range to generate report
  void clearData(); // clear all data and re-do the analysis
}

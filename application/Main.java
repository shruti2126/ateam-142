package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author Zhonggang (John) Li,  Shruti Sharma, Samuel Bahr
 *
 *this is the main method to show UI and do the milk statistical analysis
 */

public class Main  extends Application implements Stat {  
  private List <Milk> storage; // list of all milk records in the csv files  
  private TreeSet<Milk> reportTreeSet; // this is the milk stored in the treeSet for report purpose  
  private List <Farm> farms;  //list of farms   
  private Map<Integer,List<Milk>> monthMap;//list of farmObj    
  private Map<String, Farm> farmMap; // farmMap, with name as string, and Farm as its value  
  private Report report; // report to display and export  
  File singleFile; // single file to be loaded
  List<File> selectedFiles; // multiple files to be loaded
  // set up as global variable to get input from the selection in javafx
  private int month;
  private String id;
  private Date start;
  private Date end;
  String monthString;

 public static void main(String[] args) {
    launch(args);
  }

 @Override
 public void start(Stage primaryStage) throws Exception {
 
    Button b1=  new Button("Load Single File");  // b1 is loading single file 
    Button b2 = new Button("Load Multi Files");  //b2 is loading multiple files    
    Button b4 = new Button("Show Report");   // b4 is show report in screen
    Button b5 = new Button("Save Report");   // b5 is to save report as csv   
    Button b6 = new Button("Clear Data");   // b6 is used to clear data and re-do the analysis
    
    // set up style for all buttons
    b1.setLayoutX(100);
    b1.setLayoutY(100);
    b1.setPrefWidth(100);
    b1.setPrefHeight(50);    
    b2.setLayoutX(400);
    b2.setLayoutY(100);
    b2.setPrefWidth(100);
    b2.setPrefHeight(50);
    b4.setLayoutX(50);
    b4.setLayoutY(500);
    b4.setPrefWidth(100);
    b4.setPrefHeight(50);    
    b5.setLayoutX(250);
    b5.setLayoutY(500);
    b5.setPrefWidth(100);
    b5.setPrefHeight(50);    
    b6.setLayoutX(450);
    b6.setLayoutY(500);
    b6.setPrefWidth(100);
    b6.setPrefHeight(50);
    b1.setStyle("-fx-background-color:greenyellow;"+
                "-fx-background-radium:20;"
        );    
    b2.setStyle("-fx-background-color:greenyellow;"+
        "-fx-background-radium:20;"
        );
    b4.setStyle("-fx-text-fill:red;"+
        "-fx-background-color:lightblue;"+
        "-fx-background-radium:25;"
        );    
    b5.setStyle("-fx-text-fill:red;"+
        "-fx-background-color:lightblue;"+
        "-fx-background-radium:25;"
        );
    
    
     
     b1.setOnAction (new EventHandler<ActionEvent>() {
        // set up b1 action
        @Override
        public void handle(ActionEvent event) {
          // use filechooser to select files to be loaded
          FileChooser fc1 = new FileChooser();
          fc1.setTitle("Load Single File");        
          singleFile = fc1.showOpenDialog(primaryStage);
          // changing button style after loading file
          if (singleFile !=null) {
            b1.setText("file uploaded");
            b1.setStyle("-fx-background-color:yellow;"+
                "-fx-background-radium:30;"
        );
          }
          readSingleFile(singleFile.toString());
        }        
      });
      
     
    b2.setOnAction (new EventHandler<ActionEvent>() { // similar to b1
        @Override
        public void handle(ActionEvent event) {         
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Load Multiple Files");
          fileChooser.getExtensionFilters().addAll(new ExtensionFilter("csv Files", "*.csv"));
          selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);          
          if (selectedFiles != null || selectedFiles.size() != 0) {              
            String[] filenames = new String[selectedFiles.size()]; 
            for (int i = 0; i < selectedFiles.size();i++) {
                filenames[i] = selectedFiles.get(i).toString();
            }
            readMultipleFile(filenames);            
            b2.setText("files uploaded");
            b2.setStyle("-fx-background-color:yellow;"+
                "-fx-background-radium:30;");
          }        
        }        
      });
    
         //now set up all text fields
         Label labelTitle = new Label("Milk Weights Analyzer");
         labelTitle.setLayoutX (150);
         labelTitle.setLayoutY (10);
         labelTitle.setFont (new Font("Arial", 30));         
         
         Label labelPartOne = new Label("I : Upload File");
         labelPartOne.setTextFill(Color.web("#0076a3"));        
         labelPartOne.setFont(new Font("Arial", 20));
         labelPartOne.setLayoutX (50);
         labelPartOne.setLayoutY (60);
         
         
         Label labelPartTwo = new Label("II: Select Analyze");   
         labelPartTwo.setTextFill(Color.web("#0076a3"));
         labelPartTwo.setFont(new Font("Arial", 20));
         labelPartTwo.setLayoutX (50);
         labelPartTwo.setLayoutY (175);
         
         Label labelPartThree = new Label("III: Output Results");
         labelPartThree.setTextFill(Color.web("#0076a3"));
         labelPartThree.setFont(new Font("Arial", 20));
         labelPartThree.setLayoutX (50);
         labelPartThree.setLayoutY (455);
         
         
         Label labelAll = new Label("1. Annual Analyze");
         labelAll.setLayoutX (100);
         labelAll.setLayoutY (210);
         labelAll.setFont (new Font("Arial", 20));
         
         CheckBox annulCheck = new CheckBox ("Annual Analyze");
         annulCheck.setLayoutX (100);
         annulCheck.setLayoutY (245);        
         

         Label labelFarmID = new Label("4. Farm Analyze ");
         labelFarmID.setLayoutX (100);
         labelFarmID.setLayoutY (365);
         labelFarmID.setFont (new Font("Arial", 20));
         
         // this is the farmID analysis panel
         CheckBox farmCheck = new CheckBox ("4. Farm Analyze");
         farmCheck.setLayoutX (100);
         farmCheck.setLayoutY (370);   
         farmCheck.setStyle(
             "-fx-font-size: 20;" );         
         TextField textFarmID = new TextField();          
         textFarmID.setLayoutX (150);
         textFarmID.setLayoutY (400);         
         id = textFarmID.getText();    
         Tooltip tip = new Tooltip ("This is the farmID");
         tip.setFont (Font.font(25));      
         textFarmID.setTooltip (tip);         
         textFarmID.setPromptText("Input farmID here: ");
         textFarmID.setFocusTraversable (false);         
         ZoneId defaultZoneId = ZoneId.systemDefault();
         DatePicker startPicker = new DatePicker();         
         DatePicker endPicker = new DatePicker();
        
         // this is the date range analysis panel
         Label labelRange = new Label("3. Date-range Analyze: ");
         labelRange.setLayoutX (100);
         labelRange.setLayoutY (285);
         labelRange.setFont (new Font("Arial", 20));      
         CheckBox dateCheck = new CheckBox ("3. Date Range Analyze");
         dateCheck.setLayoutX (100);
         dateCheck.setLayoutY (290);          
         dateCheck.setStyle(
              "-fx-font-size: 20;"
         );        
         
         GridPane gpDate = new GridPane();
         gpDate.add(startPicker,0,0);
         gpDate.add(endPicker,1,0);
         gpDate.setHgap(10);
         gpDate.setVgap(10);
         gpDate.setLayoutX (150);
         gpDate.setLayoutY (330);
         
         // this is the date month analysis panel
         Label labelMonth = new Label("2. Month Analyze: ");
         labelMonth.setLayoutX (300);
         labelMonth.setLayoutY (210);
         labelMonth.setFont (new Font("Arial", 20));         
         ComboBox <String> comboBoxMonth = new ComboBox<> ();
         comboBoxMonth.getItems().add("Janunary");
         comboBoxMonth.getItems().add("Febrary");
         comboBoxMonth.getItems().add("March");
         comboBoxMonth.getItems().add("April");
         comboBoxMonth.getItems().add("May");
         comboBoxMonth.getItems().add("June");
         comboBoxMonth.getItems().add("July");
         comboBoxMonth.getItems().add("August");
         comboBoxMonth.getItems().add("September");
         comboBoxMonth.getItems().add("Octember");
         comboBoxMonth.getItems().add("November");
         comboBoxMonth.getItems().add("December");         
         comboBoxMonth.setLayoutX (300);
         comboBoxMonth.setLayoutY (240);         
         CheckBox monthCheck = new CheckBox ("Month Analyze");
         monthCheck.setLayoutX (420);
         monthCheck.setLayoutY (245); 
            
         // if selected all, trigger event to uncheck the others:
         annulCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
              if (arg2) {
                System.out.println("selected annual");
                monthCheck.setSelected(false);
                dateCheck.setSelected(false);
                farmCheck.setSelected(false);
              }
          }                 
         });
         
         // if selected month, trigger event to uncheck the others:
         monthCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
              if (arg2) {
                System.out.println("selected month");
                annulCheck.setSelected(false);
                dateCheck.setSelected(false);
                farmCheck.setSelected(false);                
                monthSelect(comboBoxMonth);
                }
          }

          /**
           * @param comboBoxMonth report month selected
           */
          private void monthSelect(ComboBox<String> comboBoxMonth) {
            try {
            monthString = (String) comboBoxMonth.getValue();
            switch(monthString) {
              case "Janunary":
                month = 1;
                break;
              case "Febrary":
                month = 2;
                break;
              case "March":
                month = 3;
                break;
              case "April":
                month = 4;
                break;
              case "May":
                month = 5;
                break;
              case "June":
                month = 6;
                break;
              case "July":
                month = 7;
                break;
              case "August":
                month = 8;
                break;
              case "September":
                month = 9;
                break;
              case "Octember":
                month = 10;
                break;
              case "November":
                month = 11;
                break;
              case "December":
                month = 12;
                break;
              default:
                month = 0;
            }
            }
        catch (Exception e) {System.out.println("no month selected");}
          }     
         });
         
         // date select, trigger event
         dateCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
           @Override
           public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
               if (arg2) {
                 System.out.println("selected date");
                 monthCheck.setSelected(false);
                 annulCheck.setSelected(false);
                 farmCheck.setSelected(false);
                 try {
                   start = Date.from(startPicker.getValue().atStartOfDay(defaultZoneId).toInstant());;
                   end = Date.from(endPicker.getValue().atStartOfDay(defaultZoneId).toInstant());
                 }
                 catch (Exception e) {System.out.println("no date is selected"); };
               }
           } 
          });
         
         // date select, trigger event
         farmCheck.selectedProperty().addListener(new ChangeListener<Boolean>() {
           @Override
           public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
               if (arg2) {
                 System.out.println("selected farm");
                 monthCheck.setSelected(false);
                 annulCheck.setSelected(false);
                 dateCheck.setSelected(false);
               }
           }     
          });

       b4.setOnAction (new EventHandler<ActionEvent>() {         
         @Override
         public void handle(ActionEvent event) {
           // put event triered by button1 here
           // show report in screen
           if (singleFile == null && selectedFiles == null) {
             showNoFileSelectionMessage();
           }
           
           else {             
             if  (!annulCheck.isSelected() && !monthCheck.isSelected() && !dateCheck.isSelected() && !farmCheck.isSelected()) {
                     warningWithNoSelection(); // pop out warning message when none is selected
             }
             
             else {
               // to generate report
                generateReportFromSelected(annulCheck, farmCheck, textFarmID, startPicker, endPicker,
                  dateCheck, monthCheck);
              // following is the displaying piechart and the summary table
                ArrayList<ArrayList<Milk>> myReportList= null;     // initiate report ready to display        
                // show scene of the piechart
                displayPiechart();                
                // show scene of the percentage
                displayTable (annulCheck, farmCheck, dateCheck, monthCheck);     
          }   
        }              
       }

        private void displayTable(CheckBox annulCheck, CheckBox farmCheck, CheckBox dateCheck,
            CheckBox monthCheck) {
          ArrayList<ArrayList<Milk>> myReportList;
          if (annulCheck.isSelected() || monthCheck.isSelected()) {
             TreeSet <Milk> storedReportSet = new TreeSet<>(); 
             if (annulCheck.isSelected()) {myReportList = report.getAnnual();}
             else {myReportList = report.getMonthReport();}           
             int totalWeight = 0;
             for (ArrayList<Milk> farmList: myReportList) {
                 int weight = 0;
                 String farmName = null;
                 for (Milk currentMilk : farmList) {
                   farmName = currentMilk.getFarmID();
                   weight += currentMilk.getWeight();
                 }
                 if (farmName != null) {
                   Milk sumMilk = new Milk (new Date(),farmName, weight);
                   totalWeight += weight;
                   storedReportSet.add(sumMilk);
                 }
             } 
             
             Label weightlabel = new Label("total milk weight: " + String.valueOf(totalWeight));
             weightlabel.setFont(new Font("Arial", 20));
     
             TableView<AnnualReport> table = new TableView();

//                 TableColumn<FarmIDReport, String> reporTableColumnMonth = new TableColumn<>("Month");
             TableColumn<AnnualReport, String> reporTableColumnWeight = new TableColumn<>("Milk Weight");
             TableColumn<AnnualReport, String> reporTableColumnPercent = new TableColumn<>("Percentage(%)");
             TableColumn<AnnualReport, String> reporTableColumnFarmID = new TableColumn<>("Farm ID");
             
             reporTableColumnFarmID.setCellValueFactory(new PropertyValueFactory<AnnualReport,String>("FarmIDStr"));
             reporTableColumnWeight.setCellValueFactory(new PropertyValueFactory<AnnualReport,String>("weightStr"));
             reporTableColumnPercent.setCellValueFactory(new PropertyValueFactory<AnnualReport,String>("percentageStr"));
             
             
             ObservableList<AnnualReport> data = 
                 FXCollections.observableArrayList();
             
             ArrayList <AnnualReport> annualList = new ArrayList<>();
             for (Milk currentMilk: storedReportSet) {
                 float percent = (currentMilk.getWeight() * 100.0f) / totalWeight;
                 annualList.add (new AnnualReport(currentMilk.getFarmID(),String.valueOf(currentMilk.getWeight()),String.valueOf(percent)));
             }

             data.addAll(annualList);
             
             table.setItems(data);
             table.getColumns().addAll(reporTableColumnFarmID, reporTableColumnWeight, reporTableColumnPercent);
             Group groupOut = new Group();                 
             VBox vbox = new VBox();
             vbox.getChildren().addAll(weightlabel, table);                 
             Scene sceneOut = new Scene(vbox); 
             
             Stage stage = new Stage();          
             stage.setTitle("Report Summary");
             stage.setWidth(300);
             stage.setHeight(500);                 
             stage.setScene(sceneOut);
             stage.show();           
          
           }
           
           else if (farmCheck.isSelected()) {
             TreeSet <Milk> storedReportSet = new TreeSet<>(); 
             myReportList = report.getReportList();                 
             int totalWeight = 0;
             String farmName = null;
             for (ArrayList<Milk> farmList: myReportList) {
                 int weight = 0;
                 farmName = null;
                 Date produceDate = null;
                 for (Milk currentMilk : farmList) {                        
                   farmName = currentMilk.getFarmID();
                   weight += currentMilk.getWeight();
                   produceDate = currentMilk.getDate();
                 }
                 
                 if (farmName != null) {
                   Milk sumMilk = new Milk (produceDate,farmName, weight);
                   totalWeight += weight;
                   storedReportSet.add(sumMilk);
                 }
               }
             
             Label weightlabel = new Label("total milk weight: " + String.valueOf(totalWeight));
             weightlabel.setFont(new Font("Arial", 20));
     
             TableView<FarmIDReport> table = new TableView();

             TableColumn<FarmIDReport, String> reporTableColumnMonth = new TableColumn<>("Month");
             TableColumn<FarmIDReport, String> reporTableColumnWeight = new TableColumn<>("Milk Weight");
             TableColumn<FarmIDReport, String> reporTableColumnPercent = new TableColumn<>("Percentage(%)");                 
             
             reporTableColumnMonth.setCellValueFactory(new PropertyValueFactory<FarmIDReport,String>("monthStr"));
             reporTableColumnWeight.setCellValueFactory(new PropertyValueFactory<FarmIDReport,String>("weightStr"));
             reporTableColumnPercent.setCellValueFactory(new PropertyValueFactory<FarmIDReport,String>("percentageStr"));
             
             
             ObservableList<FarmIDReport> data = 
                 FXCollections.observableArrayList();
             
             ArrayList <FarmIDReport> annualList = new ArrayList<>();
             for (Milk currentMilk: storedReportSet) {
                 float percent = (currentMilk.getWeight() * 100.0f) / totalWeight;
                 annualList.add (new FarmIDReport(String.valueOf(currentMilk.getDate().getMonth()+1),
                     String.valueOf(currentMilk.getWeight()),String.valueOf(percent)));
             }

             data.addAll(annualList);
             
             table.setItems(data);
             table.getColumns().addAll(reporTableColumnMonth, reporTableColumnWeight, reporTableColumnPercent);
             Group groupOut = new Group();                 
             VBox vbox = new VBox();
             vbox.getChildren().addAll(weightlabel, table);                 
             Scene sceneOut = new Scene(vbox);                 
             Stage stage = new Stage();          
             stage.setTitle("Report Summary");
             stage.setWidth(300);
             stage.setHeight(500);                 
             stage.setScene(sceneOut);
             stage.show();    
             
           }                  
           
           else if (dateCheck.isSelected()) {
             TreeSet <Milk> storedReportSet = new TreeSet<>(); 
             myReportList = report.getRangeReport();              

             int totalWeight = 0;
             for (ArrayList<Milk> farmList: myReportList) {
                 int weight = 0;
                 String farmName = null;
                 for (Milk currentMilk : farmList) {
                   farmName = currentMilk.getFarmID();
                   weight += currentMilk.getWeight();
                 }
                 if (farmName != null) {
                   Milk sumMilk = new Milk (new Date(),farmName, weight);
                   totalWeight += weight;
                   storedReportSet.add(sumMilk);
                 }
             } 
             
             Label weightlabel = new Label("total milk weight: " + String.valueOf(totalWeight));
             weightlabel.setFont(new Font("Arial", 20));
     
             TableView<AnnualReport> table = new TableView();

//                 TableColumn<FarmIDReport, String> reporTableColumnMonth = new TableColumn<>("Month");
             TableColumn<AnnualReport, String> reporTableColumnWeight = new TableColumn<>("Milk Weight");
             TableColumn<AnnualReport, String> reporTableColumnPercent = new TableColumn<>("Percentage(%)");
             TableColumn<AnnualReport, String> reporTableColumnFarmID = new TableColumn<>("Farm ID");
             
             reporTableColumnFarmID.setCellValueFactory(new PropertyValueFactory<AnnualReport,String>("FarmIDStr"));
             reporTableColumnWeight.setCellValueFactory(new PropertyValueFactory<AnnualReport,String>("weightStr"));
             reporTableColumnPercent.setCellValueFactory(new PropertyValueFactory<AnnualReport,String>("percentageStr"));
             
             
             ObservableList<AnnualReport> data = 
                 FXCollections.observableArrayList();
             
             ArrayList <AnnualReport> annualList = new ArrayList<>();
             for (Milk currentMilk: storedReportSet) {
                 float percent = (currentMilk.getWeight() * 100.0f) / totalWeight;
                 annualList.add (new AnnualReport(currentMilk.getFarmID(),String.valueOf(currentMilk.getWeight()),String.valueOf(percent)));
             }

             data.addAll(annualList);
             
             table.setItems(data);
             table.getColumns().addAll(reporTableColumnFarmID, reporTableColumnWeight, reporTableColumnPercent);
             Group groupOut = new Group();                 
             VBox vbox = new VBox();
             vbox.getChildren().addAll(weightlabel, table);                 
             Scene sceneOut = new Scene(vbox); 
             
             Stage stage = new Stage();          
             stage.setTitle("Report Summary");
             stage.setWidth(300);
             stage.setHeight(500);                 
             stage.setScene(sceneOut);
             stage.show();    
           }
        }

        private void generateReportFromSelected(CheckBox annulCheck, CheckBox farmCheck,
            TextField textFarmID, DatePicker startPicker, DatePicker endPicker, CheckBox dateCheck,
            CheckBox monthCheck) {
          if (annulCheck.isSelected()) {
             System.out.println("annul report to be generated");
             report = showByFullYear();
          }
          else if (monthCheck.isSelected()) {
            System.out.println("month report to be generated");
            report = showByMonth(month);
          }              
          else if (dateCheck.isSelected()) {
            System.out.println("date range report to be generated");                
            report = showByDate(startPicker.getValue(),endPicker.getValue());
          }
          else if (farmCheck.isSelected()) {
            System.out.println("farm report to be generated");
            id = textFarmID.getText();             
            report = showByFarmID(id);
          }
        }

        private void showNoFileSelectionMessage() {
          Text warning = new Text ("No datafile Selected");   
           warning.setFont(Font.font("Helvetica",40));
           warning.setFill (Paint.valueOf("Red")); 
           TextFlow warningtextflow = new TextFlow();
           warningtextflow.getChildren().addAll(warning);
           warningtextflow.setTextAlignment (TextAlignment.CENTER);               
           // put report in sceneOut
           Group groupOutwarning = new Group();
           groupOutwarning.getChildren().add(warningtextflow);
           Scene sceneOutwarning = new Scene(groupOutwarning); 
           Stage stagewarning = new Stage();             
           stagewarning.setScene(sceneOutwarning);
           stagewarning.setTitle("warning");
           stagewarning.setHeight(600);
           stagewarning.setWidth(600);
           stagewarning.show();
        }

        /**
         * if no selection, show this error message
         */
        private void warningWithNoSelection() {
          Text warning = new Text ("No Analyze Method Selected");   
           warning.setFont(Font.font("Helvetica",40));
           warning.setFill (Paint.valueOf("Red")); 
           TextFlow warningtextflow = new TextFlow();
           warningtextflow.getChildren().addAll(warning);
           warningtextflow.setTextAlignment (TextAlignment.CENTER);               
           // put report in sceneOut
           Group groupOutwarning = new Group();
           groupOutwarning.getChildren().add(warningtextflow);
           Scene sceneOutwarning = new Scene(groupOutwarning); 
           Stage stagewarning = new Stage();             
           stagewarning.setScene(sceneOutwarning);
           stagewarning.setTitle("warning");
           stagewarning.setHeight(600);
           stagewarning.setWidth(600);
           stagewarning.show();
        }

        /**
         * display pie chart
         */
        private void displayPiechart() {
          Text text = new Text (report.getTitle());   
          text.setFont(Font.font("Helvetica",20));
          text.setFill (Paint.valueOf("blue")); 
          TextFlow textflow = new TextFlow();
          textflow.getChildren().addAll(text);
          textflow.setTextAlignment (TextAlignment.CENTER);        
          // put report in sceneOut
          Group groupOut = new Group();
          groupOut.getChildren().addAll (report.createPieChart());
          groupOut.getChildren().add(textflow);
          Scene sceneOut = new Scene(groupOut); 
          Stage stage = new Stage();             
          stage.setScene(sceneOut);
          stage.setTitle("Report");
          stage.setHeight(600);
          stage.setWidth(600);
          stage.show();
        }        
       }); 
       
       b5.setOnAction (new EventHandler<ActionEvent>() {         
         @Override
         public void handle(ActionEvent event) {
           // put event triered by button1 here
           // show report in screen
           if (singleFile == null && selectedFiles == null) {
             fileNotFoundMessage();
           }
           
           else {             
             if (!annulCheck.isSelected() && !monthCheck.isSelected() && !dateCheck.isSelected() && !farmCheck.isSelected()) {
               noSelectMessage();
             }
             else {
               // to generate report and save it
               generateReportFromSelection(annulCheck, farmCheck, startPicker, endPicker, dateCheck,
                  monthCheck);               
               
               FileChooser fileChooser = new FileChooser();             
               //Set extension filter
               FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("csv files (*.csv)", "*.csv");
               fileChooser.getExtensionFilters().add(extFilter);
               
               //Show save file dialog
               File file = fileChooser.showSaveDialog(primaryStage);
               ArrayList<ArrayList<Milk>> myReportList= null;             
               TreeSet <Milk> storedReportSet = new TreeSet<>();
                 // write data from report to csv             
                if (annulCheck.isSelected() || monthCheck.isSelected()) {
                   generateAnnualReportCSV(annulCheck, file, storedReportSet);
                }
                
                else if (farmCheck.isSelected()) {
                  generateFarmidCSV(file, storedReportSet);
                  }                  
                
                else if (dateCheck.isSelected()) {
                  generateDateReportCSV(file, storedReportSet);
                  }        
             }
          }           
         }

        private void generateReportFromSelection(CheckBox annulCheck, CheckBox farmCheck,
            DatePicker startPicker, DatePicker endPicker, CheckBox dateCheck, CheckBox monthCheck) {
          if (annulCheck.isSelected()) {
              System.out.println("annul report to be generated");
              report = showByFullYear();
           }
           else if (monthCheck.isSelected()) {
             System.out.println("month report to be generated");
             report = showByMonth(month);
           }
           
           else if (dateCheck.isSelected()) {
             System.out.println("date range report to be generated");
           
             report = showByDate(startPicker.getValue(),endPicker.getValue());
           }
           else if (farmCheck.isSelected()) {
             System.out.println("farm report to be generated");
             report = showByFarmID(id);
           }
        }

        private void noSelectMessage() {
          Text warning = new Text ("No Analyze Method Selected");   
           warning.setFont(Font.font("Helvetica",40));
           warning.setFill (Paint.valueOf("Red")); 
           TextFlow warningtextflow = new TextFlow();
           warningtextflow.getChildren().addAll(warning);
           warningtextflow.setTextAlignment (TextAlignment.CENTER);               
           // put report in sceneOut
           Group groupOutwarning = new Group();
           groupOutwarning.getChildren().add(warningtextflow);
           Scene sceneOutwarning = new Scene(groupOutwarning); 
           Stage stagewarning = new Stage();             
           stagewarning.setScene(sceneOutwarning);
           stagewarning.setTitle("warning");
           stagewarning.setHeight(600);
           stagewarning.setWidth(600);
           stagewarning.show();
        }

        private void fileNotFoundMessage() {
          Text warning = new Text ("No datafile Selected");   
           warning.setFont(Font.font("Helvetica",40));
           warning.setFill (Paint.valueOf("Red")); 
           TextFlow warningtextflow = new TextFlow();
           warningtextflow.getChildren().addAll(warning);
           warningtextflow.setTextAlignment (TextAlignment.CENTER);               
           // put report in sceneOut
           Group groupOutwarning = new Group();
           groupOutwarning.getChildren().add(warningtextflow);
           Scene sceneOutwarning = new Scene(groupOutwarning); 
           Stage stagewarning = new Stage();             
           stagewarning.setScene(sceneOutwarning);
           stagewarning.setTitle("warning");
           stagewarning.setHeight(600);
           stagewarning.setWidth(600);
           stagewarning.show();
        }

        /**
         * @param annulCheck
         * @param file  generate month report and annual report
         * @param storedReportSet
         */
        private void generateAnnualReportCSV(CheckBox annulCheck, File file,
          TreeSet<Milk> storedReportSet) {
          ArrayList<ArrayList<Milk>> myReportList;
          if (annulCheck.isSelected()) {myReportList = report.getAnnual();}
           else {myReportList = report.getMonthReport();}                     
           
           int totalWeight = 0;
           for (ArrayList<Milk> farmList: myReportList) {
               int weight = 0;
               String farmName = null;
               for (Milk currentMilk : farmList) {
                 farmName = currentMilk.getFarmID();
                 weight += currentMilk.getWeight();
               }
               if (farmName != null) {
                 Milk sumMilk = new Milk (new Date(),farmName, weight);
                 totalWeight += weight;
                 storedReportSet.add(sumMilk);
               }
           }                   
           try {
             FileWriter csvWriter;
             csvWriter = new FileWriter(file);                  
             csvWriter.append(report.getTitle());
             csvWriter.append("\n");
             csvWriter.append("Total milk weight: " + String.valueOf(totalWeight));
             csvWriter.append("\n");
             csvWriter.append("FarmID");
             csvWriter.append(",");
             csvWriter.append("Milk Weight");
             csvWriter.append(",");
             csvWriter.append("Percent (%)");
             csvWriter.append("\n");                  
             for (Milk currentMilk : storedReportSet) {
                 csvWriter.append(currentMilk.getFarmID());
                 csvWriter.append(",");
                 csvWriter.append(Integer.toString(currentMilk.getWeight()));
                 csvWriter.append(",");
                 float percent = (currentMilk.getWeight() * 100.0f) / totalWeight;
                 csvWriter.append(String.valueOf(percent));
                 csvWriter.append("\n");
                 }
              csvWriter.close();
             }              
        catch (Exception ex) { }
        }

        /**
         * @param file
         * @param storedReportSet to the generate csv file
         */
        private void generateDateReportCSV(File file, TreeSet<Milk> storedReportSet) {
          ArrayList<ArrayList<Milk>> myReportList;
          myReportList = report.getRangeReport();
          HashSet<String> farmNames = new HashSet<>();
          int totalWeight = 0;                  
          for (ArrayList<Milk> farmList: myReportList) {
              for (Milk currentMilk : farmList) {                        
                String farmName = currentMilk.getFarmID();
                totalWeight += currentMilk.getWeight();
                farmNames.add(farmName);
              }
            }
          
          for (String farmName : farmNames) {
            Milk generatedMilk = new Milk (new Date(), farmName, 0);
            for (ArrayList<Milk> farmList: myReportList) {
              for (Milk currentMilk : farmList) {
                  if (currentMilk.getFarmID().equals(farmName)) {
                      int preWeight = generatedMilk.getWeight();
                      int currentWeight = preWeight + currentMilk.getWeight();
                      generatedMilk.setWeight(currentWeight);
                  }
                }
              }
            storedReportSet.add (generatedMilk);
          }
          try {
            FileWriter csvWriter;
            csvWriter = new FileWriter(file);                  
            csvWriter.append(report.getTitle());
            csvWriter.append("\n"); 
            csvWriter.append("Total Weight: " + String.valueOf(totalWeight));
            csvWriter.append("\n");    
            csvWriter.append("FarmID");
            csvWriter.append(",");
            csvWriter.append("Milk Weight");
            csvWriter.append(",");
            csvWriter.append("Percent (%)");
            csvWriter.append("\n");                  
            for (Milk currentMilk : storedReportSet) {
                csvWriter.append(String.valueOf (currentMilk.getFarmID()));
                csvWriter.append(",");
                csvWriter.append(Integer.toString(currentMilk.getWeight()));
                csvWriter.append(",");
                float percent = (currentMilk.getWeight() * 100.0f) / totalWeight;
                csvWriter.append(String.valueOf(percent));
                csvWriter.append("\n");
                }
             csvWriter.close();
            }              
        catch (Exception ex) { }
        }

        /**
         * @param file
         * @param storedReportSet generate farmID report csv vile
         */
        private void generateFarmidCSV(File file, TreeSet<Milk> storedReportSet) {
          ArrayList<ArrayList<Milk>> myReportList;
          myReportList = report.getReportList();
          int totalWeight = 0;
          String farmName = null;
          for (ArrayList<Milk> farmList: myReportList) {
              int weight = 0;
              farmName = null;
              Date produceDate = null;
              for (Milk currentMilk : farmList) {                        
                farmName = currentMilk.getFarmID();
                weight += currentMilk.getWeight();
                produceDate = currentMilk.getDate();
              }
              
              if (farmName != null) {
                Milk sumMilk = new Milk (produceDate,farmName, weight);
                totalWeight += weight;
                storedReportSet.add(sumMilk);
              }
            }
          try {
            FileWriter csvWriter;
            csvWriter = new FileWriter(file);                  
            csvWriter.append(report.getTitle());
            csvWriter.append("\n");   
            if (farmName != null) {
              csvWriter.append(farmName);
              csvWriter.append("\n"); 
            }
            csvWriter.append("Total Milk Weight: " + Integer.toString(totalWeight));  
            csvWriter.append("\n");  
            csvWriter.append("Month");
            csvWriter.append(",");
            csvWriter.append("Milk Weight");
            csvWriter.append(",");
            csvWriter.append("Percent (%)");
            csvWriter.append("\n");                  
            for (Milk currentMilk : storedReportSet) {
                csvWriter.append(String.valueOf (currentMilk.getDate().getMonth()+1));
                csvWriter.append(",");
                csvWriter.append(Integer.toString(currentMilk.getWeight()));
                csvWriter.append(",");
                float percent = (currentMilk.getWeight() * 100.0f) / totalWeight;
                csvWriter.append(String.valueOf(percent));
                csvWriter.append("\n");
                }
             csvWriter.close();
            }              
        catch (Exception ex) { }
        }        
       }); 
       
       b6.setOnAction (new EventHandler<ActionEvent>() {         
         @Override
         public void handle(ActionEvent event) {
           clearData();
           b1.setText("Load Single File");
           b1.setStyle("-fx-background-color:greenyellow;"+
               "-fx-background-radium:20;");
           b2.setText("Load Multi Files");
           b2.setStyle("-fx-background-color:greenyellow;"+
               "-fx-background-radium:20;");
           monthCheck.setSelected(false);
           annulCheck.setSelected(false);
           farmCheck.setSelected(false);
           dateCheck.setSelected(false);    
           textFarmID.clear();
         }
       }); 

        // add at least one node (borderPane most, or button or layout) to scene  
        Group group = new Group();
        group.getChildren().addAll (labelPartOne,labelPartTwo,labelPartThree,b1,b2,b4,b5,b6, textFarmID,labelTitle,labelMonth,
            comboBoxMonth,labelAll,annulCheck,farmCheck,gpDate,monthCheck,dateCheck);
        
        Scene scene = new Scene(group);       
 
        // set scene to stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Milk Statistics");
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);
        primaryStage.show();
    
  }

 @Override
 public void readSingleFile(String filename) {  //method used to read single file
     // to initiate all lists and maps
     storage = new ArrayList<>();
     monthMap = new TreeMap<>();
     farmMap = new TreeMap<>();	 
     farms = new ArrayList<>();
	 
	 
	 // start reading file
     BufferedReader br = null;
     String line = "";
     String cvsSplitBy = ",";
     
     try {
       br = new BufferedReader(new FileReader(filename));
       br.readLine();
       while ((line = br.readLine()) != null) {
           String[] milkStrings = line.split(cvsSplitBy);
           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           
           Date createDate = null;
           String createFarmId = null;
           Integer createWeight = 0;
           
           // this try is used to track exception for date, if date is missing or in wrong format, pass this record
           try {
             createDate = format.parse(milkStrings[0]);
             createFarmId= milkStrings[1];
             
             if (!createFarmId.startsWith("Farm")) { // if Farm ID in a different style, using UnknownFarm instead
               createFarmId = "UnknownFarm";
             }
             
             // this is used to find the correct weight, if in wrong format or missing, put 0 instead
             try {
               createWeight = Integer.parseInt(milkStrings[2]);
             }
             catch (Exception e) {
               createWeight = 0;
            }
             // generate milk instance and put into storage
             Milk createMilk= new Milk (createDate,createFarmId,createWeight);
             storage.add (createMilk);
             
             // to generate farmMap and push data into farmMap
             if (!farmMap.containsKey(createFarmId)) {
                 Farm createFarm = new Farm (createFarmId, new TreeSet<>());
                 TreeSet<Milk> currentSet = createFarm.getFarmProduct();
                 currentSet.add(createMilk);
                 createFarm.setFarmProduct(currentSet);
                 
                 // put the newly generated farm into map
                 farmMap.put (createFarmId,createFarm);
                 // add newly generated farm into farms list
                 farms.add (createFarm);
             }
             // if this farm is already in farmMap
             else {
               Farm existingFarm = farmMap.get(createFarmId); 
               TreeSet<Milk> existingSet = existingFarm.getFarmProduct();
               existingSet.add(createMilk);
               existingFarm.setFarmProduct(existingSet);            
               
               // reset the existing farm in the farmMap
               farmMap.put (createFarmId,existingFarm);
               // reset the exiting farm in the farms list
               for (int i = 0; i < farms.size(); i++) {
                   if (farms.get(i).getFarmID().equals(existingFarm.getFarmID()) ){
                     farms.get(i).setFarmProduct(existingSet);;
                   }
               }                                      
             }
             
             // add milk to month map
             Integer monthInt = createDate.getMonth() + 1;
             
             // for the first time, put the month (1,2,3 ..) as key, and put a new list with milk to the map
             if (!monthMap.containsKey(monthInt)) {
               List<Milk> monthMilkList = new ArrayList<>();
               monthMilkList.add (createMilk);
               monthMap.put (monthInt,monthMilkList);             
             }
             // if the month map with the key (month), add milk to existing list and put back to map
             else {
               List<Milk> existinMonthMilkList = monthMap.get(monthInt);
               existinMonthMilkList.add (createMilk);
               monthMap.put (monthInt,existinMonthMilkList); 
             }
//
//             System.out.println(createMilk);
//             System.out.println("-----------done with one instance---------------------------");
           }
           catch (Exception e) {
             System.out.println("One invalid date record, passed");
          }
       }
     } catch (FileNotFoundException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     } 
     
     
//     System.out.println("-----------after reading file ---------------------------");
     
     // the following printout is used to track all information in map and lists after reading files
//     if (monthMap != null) {
//       for (Integer key: monthMap.keySet()) {
//           System.out.println("month " + key + " : " + monthMap.get(key));
//       }
//     }
//     
//     if (farmMap != null) {
//       for (String key: farmMap.keySet()) {
//           System.out.println("farm infor: " + key + " : " + farmMap.get(key).getFarmID());
//       }
//     }
//     
//     if (farms != null) {
//       for (Farm farm: farms) {
//           System.out.println("farm is " + farm.getFarmID() + " " + farm.getFarmProduct());
//       }
//     }
}

 @Override
 public void readMultipleFile(String[] filenames) {
   
     // to store information in following containers
     storage = new ArrayList<>();     
     monthMap = new TreeMap<>();
     farmMap = new TreeMap<>();	 
     farms = new ArrayList<>();
     
     // the information can be referred to the readSingle file method
	 for(int i = 0; i < filenames.length; i++) {	 
		// start reading file
	     BufferedReader br = null;
	     String line = "";
	     String cvsSplitBy = ",";
	     
	     try {
	       br = new BufferedReader(new FileReader(filenames[i]));
	       br.readLine();
	       while ((line = br.readLine()) != null) {
	           String[] milkStrings = line.split(cvsSplitBy);
	           SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	           
	           Date createDate = null;
	           String createFarmId = null;
	           Integer createWeight = 0;
	           
	           try {
	             createDate = format.parse(milkStrings[0]);
	             createFarmId= milkStrings[1];
	               if (!createFarmId.startsWith("Farm")) {
	                 createFarmId = "UnknownFarm";
	               }
	               
	               try {
	                 createWeight = Integer.parseInt(milkStrings[2]);
	               }
	               catch (Exception e) {
	                 createWeight = 0;
	              }
	               
	               // generate milk instance and put into storage
	               Milk createMilk= new Milk (createDate,createFarmId,createWeight);
	               storage.add (createMilk);
	               
	               // to generate farmMap and push data into farmMap
	               if (!farmMap.containsKey(createFarmId)) {
	                   Farm createFarm = new Farm (createFarmId, new TreeSet<>());
	                   TreeSet<Milk> currentSet = createFarm.getFarmProduct();
	                   currentSet.add(createMilk);
	                   createFarm.setFarmProduct(currentSet);
	                   
	                   // put the newly generated farm into map
	                   farmMap.put (createFarmId,createFarm);
	                   // add newly generated farm into farms list
	                   farms.add (createFarm);
	               }
	               // if this farm is already in farmMap
	               else {
	                 Farm existingFarm = farmMap.get(createFarmId); 
	                 TreeSet<Milk> existingSet = existingFarm.getFarmProduct();
	                 existingSet.add(createMilk);
	                 existingFarm.setFarmProduct(existingSet);            
	                 
	                 // reset the existing farm in the farmMap
	                 farmMap.put (createFarmId,existingFarm);
	                 // reset the exiting farm in the farms list
	                 for (int x = 0; x < farms.size(); x++) {
	                     if (farms.get(x).getFarmID().equals(existingFarm.getFarmID()) ){
	                       farms.get(x).setFarmProduct(existingSet);;
	                     }
	                 }                                      
	               }
	               
	               // add milk to month map
	               Integer monthInt = createDate.getMonth() + 1;
	               
	               // for the first time, put the month (1,2,3 ..) as key, and put a new list with milk to the map
	               if (!monthMap.containsKey(monthInt)) {
	                 List<Milk> monthMilkList = new ArrayList<>();
	                 monthMilkList.add (createMilk);
	                 monthMap.put (monthInt,monthMilkList);             
	               }
	               // if the month map with the key (month), add milk to existing list and put back to map
	               else {
	                 List<Milk> existinMonthMilkList = monthMap.get(monthInt);
	                 existinMonthMilkList.add (createMilk);
	                 monthMap.put (monthInt,existinMonthMilkList); 
	               }
//	               
//	               
//	               System.out.println(createMilk);
//	               System.out.println("-----------done with one instance---------------------------");
	             
	           }
	           catch (Exception e) {
	             System.out.println("One invalid date record, passed");
              }	          
	       }       
	       
	     } catch (FileNotFoundException e) {
	       e.printStackTrace();
	     } catch (IOException e) {
	       e.printStackTrace();
	     } 
	}
	 
//	 System.out.println("-------------done with reading---------");
//	 
//     if (monthMap != null) {
//       for (Integer key: monthMap.keySet()) {
//           System.out.println("month " + key + " : " + monthMap.get(key));
//       }
//     }
//     
//     if (farmMap != null) {
//       for (String key: farmMap.keySet()) {
//           System.out.println("farm infor: " + key + " : " + farmMap.get(key).getFarmID());
//       }
//     }
//     
//     if (farms != null) {
//       for (Farm farm: farms) {
//           System.out.println("farm is " + farm.getFarmID() + " " + farm.getFarmProduct());
//       }
//     }		 
  }

@Override
 public Report showByFarmID(String id) {
    // generate farmId report
    Report farmReport = new Report("FarmId Report");
    
    for(int i = 0; i < farms.size(); i++) {
       	if(farms.get(i).getFarmID().contentEquals(id)) {
				farmReport.farmReport(farms.get(i));
			}
    }

    return farmReport;
  }

 @Override
 public Report showByMonth(int month) {
    // TODO Auto-generated method stub
    Report monthReport = new Report("Month Report");
    monthReport.monthReport(storage, month);
 
    return monthReport;
  }

 @Override
 public Report showByFullYear() {
    // TODO Auto-generated method stub
    Report annulReport = new Report("Annul Report");
    annulReport.annualReport(storage);
    System.out.println("generate here annulReport" + annulReport.getTitle());
    return annulReport;
  }
 
 @Override
 public Report showByDate(LocalDate start, LocalDate end) {
    // TODO Auto-generated method stub
    Report dateReport = new Report("Dates Report");
    dateReport.dateRangeReport(start, end, storage);
    // for loop to add milk
    // report.addMilk ();
    
    return dateReport;
  }

 @Override
 public void clearData() {
    // reset all data to null
    storage = null;
    farms = null;
    monthMap = null;
    farmMap = null;
    report = null;
    month = 0;
    id = null;
    start = null;
    end = null;
    monthString = null;
    singleFile = null;
    selectedFiles = null;
    }

}

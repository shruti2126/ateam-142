package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main  extends Application implements Stat {
  
  private List <Milk> storage;
  
  
  private List <Farm> farms;  //list of farms
  
  
 // private List<Farm> farmObj; //list of farmObj
  private Map<Integer,List<Milk>> monthMap;
  
  // farmMap, with name as string, and Farm as its value
  private Map<String, Farm> farmMap;
  
  
  // do we need this ?  
//  private Hashtable <String, Set<Milk>> farmProducts; //to store milk specified by farmId
  

  // report to export
  private Report report;

  private int month;
  private String id;
  private Date start;
  private Date end;
  String monthString;
  
  
  // files to be loaded
  File singleFile;
  List<File> selectedFiles;
  

  
  public static void main(String[] args) {

    launch(args);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
 
    Button b1=  new Button("Load Single File");   
    Button b2 = new Button("Load Multi Files");      
    Button b4 = new Button("Show Report");   
    Button b5 = new Button("Save Report");      
    Button b6 = new Button("Clear Data");
    
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
    
    // use css
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
        
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
          FileChooser fc1 = new FileChooser();
          fc1.setTitle("Load Single File");        
          singleFile = fc1.showOpenDialog(primaryStage);
          if (singleFile !=null) {

            b1.setText("file uploaded");
            b1.setStyle("-fx-background-color:yellow;"+
                "-fx-background-radium:30;"
        );
          }
          readSingleFile(singleFile.toString());
        }        
      });
      
      b2.setOnAction (new EventHandler<ActionEvent>() {
  
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
          
          FileChooser fileChooser = new FileChooser();
          fileChooser.setTitle("Load Multiple Files");
          fileChooser.getExtensionFilters().addAll(new ExtensionFilter("csv Files", "*.csv"));
          selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
          
          if (selectedFiles != null || selectedFiles.size() != 0) {
              
//            System.out.println(selectedFiles);
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

         //text field
    
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
         
         CheckBox farmCheck = new CheckBox ("4. Farm Analyze");
         farmCheck.setLayoutX (100);
         farmCheck.setLayoutY (370);   
         farmCheck.setStyle(
             "-fx-font-size: 20;"
        );
         
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
               catch (Exception e) {System.out.println("no month selected");  }
                }
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

             // to generate report
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
             else {
               System.out.println("no analyze method is selected");
             }
             
             System.out.println("current report is " + report.getTitle());
             
             
             if (report != null) {
               // save report as csv
               Text text = new Text (report.getTitle());   
               text.setFont(Font.font("Helvetica",30));
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
             
             else {
            
               Text text = new Text ("Report is null ");
               text.setFont(Font.font("Helvetica",30));
               text.setFill (Paint.valueOf("blue"));               
               TextFlow textflow = new TextFlow();
               
               textflow.getChildren().addAll(text);
               textflow.setTextAlignment (TextAlignment.CENTER);
               Scene sceneOut = new Scene(textflow);
               
               Stage stage = new Stage();             
               stage.setScene(sceneOut);
               stage.setTitle("Report");
               stage.setHeight(600);
               stage.setWidth(600);
               stage.show();
             }
         }        
       }); 
       
       
       b5.setOnAction (new EventHandler<ActionEvent>() {         
         @Override
         public void handle(ActionEvent event) {
           // put event triered by button1 here
           // show report in screen

             // to generate report
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
             else {
               System.out.println("no analyze method is selected");
             }
           
             
             System.out.println("----ss---------------------------");
             System.out.println("current report is " + report.getTitle());
             System.out.println("----ss---------------------------");
            
             FileChooser fileChooser = new FileChooser();
             
             //Set extension filter
             FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("csv files (*.csv)", "*.csv");
             fileChooser.getExtensionFilters().add(extFilter);
             
             //Show save file dialog
             File file = fileChooser.showSaveDialog(primaryStage);
             ArrayList<ArrayList<Milk>> myReportList= null;
             
             
             if (report != null) {
               // write data from report to csv
               System.out.println("report will be saved");
               
               if (annulCheck.isSelected()) {
                 myReportList = report.getAnnual();
              }
              else if (monthCheck.isSelected()) {
                myReportList = report.getMonthReport();
              }
              
              else if (dateCheck.isSelected()) {
                myReportList = report.getRangeReport();
              }
              else if (farmCheck.isSelected()) {
                myReportList = report.getReportList();
              }
              else {
                myReportList = null;
              }
               
              if (myReportList != null) {
                try {
                  FileWriter csvWriter;
                  csvWriter = new FileWriter(file);
                  
                  csvWriter.append(report.getTitle());
                  csvWriter.append("\n");
                  
                  csvWriter.append("FarmID");
                  csvWriter.append(",");
                  csvWriter.append("Production Date");
                  csvWriter.append(",");
                  csvWriter.append("Milk Weight");
                  csvWriter.append("\n");
                  
                  
                  for (ArrayList<Milk> innerList: myReportList) {
                      for (Milk currentMilk: innerList) {
                        csvWriter.append(currentMilk.getFarmID());
                        csvWriter.append(",");
                        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                        csvWriter.append(formatter.format(currentMilk.getDate()));
                        csvWriter.append(",");
                        csvWriter.append(Integer.toString(currentMilk.getWeight()));
                        csvWriter.append("\n");
                      }
                  }
                  csvWriter.close();
                  
              } catch (IOException ex) {
                }
              }

             }
             
             else {
               
               Text text = new Text ("Report is null ");
               text.setFont(Font.font("Helvetica",30));
               text.setFill (Paint.valueOf("blue"));               
               TextFlow textflow = new TextFlow();
               
               textflow.getChildren().addAll(text);
               textflow.setTextAlignment (TextAlignment.CENTER);
               Scene sceneOut = new Scene(textflow);
               
               Stage stage = new Stage();             
               stage.setScene(sceneOut);
               stage.setTitle("Report");
               stage.setHeight(600);
               stage.setWidth(600);
               stage.show();
             }
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
 public void readSingleFile(String filename) {
     
     // to store all milk in storage
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
           
           try {
             createDate = format.parse(milkStrings[0]);
           }
           catch (Exception e) {
             System.out.println("pass this invalid date record");
             continue;
          }

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
//             monthMap.put (monthInt,existinMonthMilkList); 
           }
       
           
           System.out.println(createMilk);
           System.out.println("-----------done with one instance---------------------------");
       }

     } catch (FileNotFoundException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     } 
     
     
     System.out.println("-----------after reading file ---------------------------");
     
     if (monthMap != null) {
       for (Integer key: monthMap.keySet()) {
           System.out.println("month " + key + " : " + monthMap.get(key));
       }
     }
     
     if (farmMap != null) {
       for (String key: farmMap.keySet()) {
           System.out.println("farm infor: " + key + " : " + farmMap.get(key).getFarmID());
       }
     }
     
     if (farms != null) {
       for (Farm farm: farms) {
           System.out.println("farm is " + farm.getFarmID() + " " + farm.getFarmProduct());
       }
     }
}

 @Override
 public void readMultipleFile(String[] filenames) {
     // to store all milk in storage
     storage = new ArrayList<>();
     
     monthMap = new TreeMap<>();
     farmMap = new TreeMap<>();
	 
     farms = new ArrayList<>();
		 
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
	           }
	           catch (Exception e) {
	             System.out.println("pass this invalid date record");
	             continue;
              }

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
//	             monthMap.put (monthInt,existinMonthMilkList); 
	           }
	           
	           
	           System.out.println(createMilk);
	           System.out.println("-----------done with one instance---------------------------");

	       }
	       
	       
	     } catch (FileNotFoundException e) {
	       e.printStackTrace();
	     } catch (IOException e) {
	       e.printStackTrace();
	     } 
	}
	 
     if (monthMap != null) {
       for (Integer key: monthMap.keySet()) {
           System.out.println("month " + key + " : " + monthMap.get(key));
       }
     }
     
     if (farmMap != null) {
       for (String key: farmMap.keySet()) {
           System.out.println("farm infor: " + key + " : " + farmMap.get(key).getFarmID());
       }
     }
     
     if (farms != null) {
       for (Farm farm: farms) {
           System.out.println("farm is " + farm.getFarmID() + " " + farm.getFarmProduct());
       }
     }
		 
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
 public File exportReport(Report report) {
    // TODO Auto-generated method stub
    // need to deal with situation when report is null..
    // used to save report as file
    return null;
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

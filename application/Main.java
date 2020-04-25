package application;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.stage.Stage;

public class Main  extends Application implements Stat {
  /**
   * @return the report
   */
  public Report getReport() {
    return report;
  }

  /**
   * @param report the report to set
   */
  public void setReport(Report report) {
    this.report = report;
  }
  
  private List <Milk> storage;
  private List <Farm> farms;  
  private Map<Integer,List<Milk>> monthMap;
  private Map<String, Farm> farmMap;
  private Report report;

  private int month;
  private String id;
  private Date start;
  private Date end;
  
  File singleFile;
  
  
  String monthString;
  
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
    
    
    
      Label labelUploadFile = new Label();
      b1.setOnAction (new EventHandler<ActionEvent>() {
        
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
          FileChooser fc1 = new FileChooser();
          fc1.setTitle("Load Single File");        
          singleFile = fc1.showOpenDialog(primaryStage);
          if (singleFile !=null) {
            labelUploadFile.setText(singleFile.toString());
            labelUploadFile.setLayoutX (80);
            labelUploadFile.setLayoutY (155);
            labelUploadFile.setFont (new Font("Arial", 10));
            b1.setText("uploaded");
            b1.setStyle("-fx-background-color:yellow;"+
                "-fx-background-radium:30;"
        );
          }
        }        
      });
      
      b2.setOnAction (new EventHandler<ActionEvent>() {
  
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
          FileChooser fc2 = new FileChooser();
          fc2.setTitle("Load Multiple Files");
          fc2.showOpenDialog(primaryStage);
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
         
       // to generate report
       if (annulCheck.isSelected()) {
          report = showByFullYear();
       }
       else if (monthCheck.isSelected()) {
         report = showByMonth(month);
       }
       
       else if (dateCheck.isSelected()) {
         report = showByDate(start,end);
       }
       else if (farmCheck.isSelected()) {
         report = showByFarmID(id);
       }
       else {
         System.out.println("no analyze method is selected");
       }
        
       b4.setOnAction (new EventHandler<ActionEvent>() {         
         @Override
         public void handle(ActionEvent event) {
           // put event triered by button1 here
           // show report in screen

             if (report != null) {
               // save report as csv
               Text text = new Text (report.getTitle());
               
               TextFlow textflow = new TextFlow();


               
               Text reportText = new Text (report.toString());
               textflow.getChildren().addAll(text,reportText);
               textflow.setTextAlignment (TextAlignment.CENTER);
               
               
               // put report in sceneOut
               Group groupOut = new Group();
               groupOut.getChildren().addAll (textflow);
               
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
           
//             report = new Report ("report");
             
             if (report != null) {
               //ObjectMapper mapper = new ObjectMapper();
               
               String json;
               
//             Milk m1 = new Milk (new Date(), "1", 25);
//             Milk m2 = new Milk (new Date(), "2", 45);
//             report.addMilk(m1);
//             report.addMilk(m2);
               
              try {
               // json = mapper.writeValueAsString(report);               
               // mapper.writeValue (new File ("d://report.txt"), report);
              } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }

             }
             
             else {
               Text text = new Text ("Report is null, can't export report");
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
         }
       }); 
       
       
       
        // add at least one node (borderPane most, or button or layout) to scene  
        Group group = new Group();
        group.getChildren().addAll (labelPartOne,labelPartTwo,labelPartThree,b1,b2,b4,b5,b6, textFarmID,labelTitle,labelMonth,
            comboBoxMonth,labelAll,annulCheck,labelUploadFile,farmCheck,gpDate,monthCheck,dateCheck);
        
        Scene scene = new Scene(group);       
 
        // set scene to stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Milk Statistics");
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);
        primaryStage.show();
    
  }
  
  

  @Override
 public void readSingleFile(File file) {
     monthMap = new TreeMap<>();
     farmMap = new TreeMap<>();
     BufferedReader fileReader = null;
      
     //Delimiter used in CSV file
     final String DELIMITER = ",";
     try
     {
         String line = "";
         //Create the file reader
         fileReader = new BufferedReader(new FileReader(singleFile));
          
         //Read the file line by line
         while ((line = fileReader.readLine()) != null) 
         {
             //Get all tokens available in line
             String[] tokens = line.split(DELIMITER);
             for(String token : tokens)
             {
            	 String date = tokens[0];
            	 Scanner dateScanner = new Scanner(date);
            	 dateScanner.useDelimiter("-");
            	 int[] dateArr = new int[3];
            	 int i = 0;
            	 while(dateScanner.hasNext()) {
            		dateArr[i] = dateScanner.nextInt();
            		i++;
            	 }
            	 @SuppressWarnings("deprecation")
				 Date milkDate = new Date(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]),Integer.parseInt(dateArr[2]));
            	 String farmId = tokens[1];
            	 String weight = tokens[2];
            	 Milk milk = new Milk(milkDate, farmId, Integer.parseInt(weight));
            	 storage.add(milk); //insert milk in Milk list     	 
            	 int hashCode = farmId.hashCode();
            	 farmProducts.get(hashCode).add(milk);
            	 List<Milk> list = monthMap.get(Integer.parseInt(dateArr[1])); //get list of current month
            	 list.add(milk);
            	 monthMap.put(dateArr[1], list); //insert data in monthMap
             }
             //add farm to farm list
             for(String farmid : farmProducts.keySet()) {
            	 Set<Milk> milks = new HashSet<Milk>();
            	 milks.addAll(farmProducts.get(farmid)); //get the set of milk for farmid
            	 Farm farm = new Farm(farmid, milks);
            	 farms.add(farm); 
             }
             
         }
     } 
     catch (Exception e) {
         e.printStackTrace();
     } 
     finally
     {
         try {
             fileReader.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
  }

  @Override
  public void readMultipleFile(File[] files) {
    // TODO Auto-generated method stub
    storage = new ArrayList<>();
    farms = new ArrayList<>();
    monthMap = new HashMap<>();
    farmMap = new HashMap<>();    
    // put mutiple files to dataframe
  }

  @Override
  public Report showByFarmID(String id) {
    // TODO Auto-generated method stub
    Report farmReport = new Report("FarmId Report");
    for(int i = 0; i < farms.size(); i++) {
    	if(farms.get(i).getFarmID().contentEquals(id)) {
				farmReport.farmReport(farms.get(i));
			}
    }
    // for loop to add milk
    // report.addMilk ();
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
    
    return annulReport;
  }
  
  @Override
  public Report showByDate(Date start, Date end) {
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
  }

}

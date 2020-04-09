import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MilkStat extends Application implements Stat {
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
  
  File singleFile = null;
  
  public static void main(String[] args) {
    launch(args);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // show a website link
//    HostServices host = getHostServices();
//    host.showDocument ("www.google.com");
    
    Button b1= new Button("Load Single File");   
    Button b2 = new Button("Load Multi Files");      
    Button b3 = new Button("Load All Files");
    Button b4 = new Button("Show Report");   
    Button b5 = new Button("Save Report");      
    
    b1.setLayoutX(100);
    b1.setLayoutY(100);
    b1.setPrefWidth(100);
    b1.setPrefHeight(50);
    
    
    b2.setLayoutX(250);
    b2.setLayoutY(100);
    b2.setPrefWidth(100);
    b2.setPrefHeight(50);
        
    
    b3.setLayoutX(400);
    b3.setLayoutY(100);
    b3.setPrefWidth(100);
    b3.setPrefHeight(50);
    
    b4.setLayoutX(50);
    b4.setLayoutY(500);
    b4.setPrefWidth(100);
    b4.setPrefHeight(50);
    
    b5.setLayoutX(450);
    b5.setLayoutY(500);
    b5.setPrefWidth(100);
    b5.setPrefHeight(50);
    
    // use css
    b1.setStyle("-fx-background-color:greenyellow;"+
                "-fx-background-radium:20;"
        );
    
    b2.setStyle("-fx-background-color:greenyellow;"+
        "-fx-background-radium:20;"
        );
    
    b3.setStyle("-fx-background-color:greenyellow;"+
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
      
      b3.setOnAction (new EventHandler<ActionEvent>() {
  
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
          FileChooser fc3 = new FileChooser();
          fc3.setTitle("Load All Files");
          fc3.showOpenDialog(primaryStage);
        }        
      });    
      
      
      b4.setOnAction (new EventHandler<ActionEvent>() {
        
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
          // show report in screen
            if (report != null) {
              // save report as csv
              System.out.println(report);
            }
        }        
      }); 
      
      
      
      b5.setOnAction (new EventHandler<ActionEvent>() {
        
        @Override
        public void handle(ActionEvent event) {
          // put event triered by button1 here
            if (report != null) {
              // save report as csv
              System.out.println(report);
            }
        }        
      }); 
      
    
         //text field
    
         Label labelTitle = new Label("Milk Weights Analyzer");
         labelTitle.setLayoutX (150);
         labelTitle.setLayoutY (25);
         labelTitle.setFont (new Font("Arial", 35));
         
         Label labelAll = new Label("Full Year");
         labelAll.setLayoutX (235);
         labelAll.setLayoutY (195);
         labelAll.setFont (new Font("Arial", 20));
         
         CheckBox buttonAll = new CheckBox ("Annual Analyze");
         buttonAll.setLayoutX (220);
         buttonAll.setLayoutY (225);        


         Label labelID = new Label("Input farmID: ");
         labelID.setLayoutX (220);
         labelID.setLayoutY (405);
         labelID.setFont (new Font("Arial", 20));
         
         
         TextField text = new TextField();        
         
         text.setLayoutX (210);
         text.setLayoutY (435);
         
         Tooltip tip = new Tooltip ("This is the farmID");
         tip.setFont (Font.font(25));      
         text.setTooltip (tip);
         
         text.setPromptText("Input farmID here: ");
         text.setFocusTraversable (false);
         
         Button buttonID = new Button("Farm Analyze Selection");
         buttonID.setLayoutX (365);
         buttonID.setLayoutY (435);
         
         
         // limit input to 12 digits         
         text.textProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
               if (arg2.length() > 12)
                 text.setText (arg1);   
               if (arg2.length() > 0)
                 buttonID.setStyle("-fx-background-color:red;"+
                     "-fx-background-radium:20;"
             );
           }        
         });
         
         
         // if selected all, trigger event, put summary all here:
         buttonAll.selectedProperty().addListener(new ChangeListener<Boolean>() {

          @Override
          public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
              if (arg2) {
                System.out.println("selected all");
                // method to summary annual
              }
          }

       
         });
         
         DatePicker startPicker = new DatePicker();
         DatePicker endPicker = new DatePicker();
         
         Label labelRange = new Label("Select date range: ");
         labelRange.setLayoutX (210);
         labelRange.setLayoutY (260);
         labelRange.setFont (new Font("Arial", 20));
         
         GridPane gp = new GridPane();
         gp.add(startPicker,0,0);
         gp.add(endPicker,1,0);
         gp.setHgap(10);
         gp.setVgap(10);
         gp.setLayoutX (150);
         gp.setLayoutY (290);
         
         Label labelMonth = new Label("Select month: ");
         labelMonth.setLayoutX (220);
         labelMonth.setLayoutY (330);
         labelMonth.setFont (new Font("Arial", 20));
         
         ComboBox comboBox = new ComboBox();
         comboBox.getItems().add("Janunary");
         comboBox.getItems().add("Febrary");
         comboBox.getItems().add("March");
         comboBox.getItems().add("April");
         comboBox.getItems().add("May");
         comboBox.getItems().add("June");
         comboBox.getItems().add("July");
         comboBox.getItems().add("August");
         comboBox.getItems().add("September");
         comboBox.getItems().add("Octember");
         comboBox.getItems().add("November");
         comboBox.getItems().add("December");
         
         comboBox.setLayoutX (225);
         comboBox.setLayoutY (360);
         
   
        // add at least one node (borderPane most, or button or layout) to scene  
        Group group = new Group();
        group.getChildren().addAll (b1,b2,b3,b4,b5,labelID,text,labelTitle,labelMonth,
            comboBox,labelAll,buttonAll,labelRange,labelUploadFile,buttonID,gp);
        
        Scene scene = new Scene(group);
        
    
        // set scene to stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Milk Statistics");
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);
        primaryStage.show();
    
  }
  
  

  @Override
  public void readSingleFile() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void readMultipleFile() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void showByFarmID() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void showByMonth() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void showByFullYear() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public File exportReport() {
    // TODO Auto-generated method stub
    return null;
  }

}

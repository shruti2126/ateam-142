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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MilkStat extends Application implements Stat {
  private List <Milk> storage;
  private List <Farm> farms;  
  private Map<Integer,List<Milk>> monthMap;
  private Map<String, Farm> farmMap;
  
  public static void main(String[] args) {
    launch(args);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    // show a website link
//    HostServices host = getHostServices();
//    host.showDocument ("www.google.com");
    
    // add button or image to root panel, currently not used
    BorderPane root = new BorderPane();
    
    Button b1= new Button("Load Single File");   
    Button b2 = new Button("Load Multiple Files");      
    Button b3 = new Button("Load All");
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
    
    
    b1.setOnAction (new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // put event triered by button1 here
          System.out.println("click");
      }        
    });
    
    
    
         //text field
    
         Label labelTitle = new Label("Milk Weights Analyzer");
         labelTitle.setLayoutX (150);
         labelTitle.setLayoutY (25);
         labelTitle.setFont (new Font("Arial", 30));
         
         
         Label labelID = new Label("Input farmID: ");
         labelID.setLayoutX (225);
         labelID.setLayoutY (400);
          
         TextField text = new TextField();
         
         
         text.setLayoutX (200);
         text.setLayoutY (420);
         
         Tooltip tip = new Tooltip ("This is the farmID");
         tip.setFont (Font.font(25));      
         text.setTooltip (tip);
         
         text.setPromptText("Input farmID here: ");
         text.setFocusTraversable (false);
         
         // limit input to 12 digits
         
         text.textProperty().addListener(new ChangeListener<String>() {

           @Override
           public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
               if (arg2.length() > 12)
                 text.setText (arg1);                
           }        
         });
         
    
    
    // add at least one node (borderPane most, or button or layout) to scene  
    Group group = new Group();
    group.getChildren().addAll (b1,b2,b3,b4,b5,labelID,text,labelTitle);
    
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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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
    // use css
    b1.setStyle("-fx-background-color:greenyellow;"+
                "-fx-background-radium:20;"
        );
    
    b2.setStyle("-fx-background-color:yellowgreen;"+
        "-fx-background-radium:20;"
);
    
    b3.setStyle("-fx-background-color:gray;"+
        "-fx-background-radium:20;"
);
    
    b1.setOnAction (new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        // put event triered by button1 here
          System.out.println("click");
      }        
    });
    
    // add at least one node (borderPane most, or button or layout) to scene  
    Group group = new Group();
    group.getChildren().addAll (b1,b2,b3);
    
    Scene scene = new Scene(group);
    
    // set scene to stage
    primaryStage.setScene(scene);
    primaryStage.setTitle("scene show");
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

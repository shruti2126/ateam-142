import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.application.Application;
import javafx.application.HostServices;
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
    
    Button button = new Button("continue");
    button.setCursor (Cursor.MOVE);
    button.setPrefWidth (200);
    button.setPrefHeight (200);
    
    // add at least one node (borderPane most, or button or layout) to scene  
    Group group = new Group();
    group.getChildren().add (button);
    
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

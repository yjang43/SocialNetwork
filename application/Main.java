package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {
  double origCanvasTransX;
  double origCanvasTransY;

  
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
  
  
  void createCenterUser(Pane canvas, Pane interactionPane, Profile centerUser) throws FileNotFoundException {

    /*
     * make this into a module
     */
    // size gets bigger when there are too many users
    if (centerUser.getFriends().size() / (canvas.getPrefHeight() * canvas.getPrefWidth()) > .000001) {
      System.out.println("size upgrade");
      canvas.setPrefSize(canvas.getPrefWidth() + 64, canvas.getPrefHeight() + 64);
    }

    // index array
    int indexX = (int) canvas.getPrefWidth() / 32;
    int indexY = (int) canvas.getPrefHeight() / 32;

    int[][] Array = new int[indexY][indexX];

    for (int i = indexY / 2; i <= indexY / 2 + 2; i++) {
      for (int j = indexX / 2; j <= indexX / 2 + 2; j++) {
        Array[i][j] = 1;
      }
    }

    /*
     * clean this mass
     */
    try {
      ProfileGUI centerUserGUI =
          new ProfileGUI(centerUser, canvas.getPrefWidth() / 2, canvas.getPrefHeight() / 2);
      centerUserGUI.setImageSize(64, 64);
      centerUserGUI.setTranslateX(-32);
      centerUserGUI.setTranslateY(-32);
      int numOfFriends = centerUser.getFriends().size();
      for (int friendIndex = 0; friendIndex < numOfFriends; friendIndex++) {
        int randNumFrom = (indexX * indexY - 9 - friendIndex);
        Random rnd = new Random();
        int position = rnd.nextInt(randNumFrom);
        int i = 0;
        int j = 0;
        for (i = 0; i < indexY && position != 0; i++) {
          for (j = 0; j < indexX && position != 0; j++) {
            if (Array[i][j] == 0) {
              position--;
              if (position == 0)
                break;
            }
          }
          if (position == 0)
            break;
        }

        Array[i][j] = 1;
        ProfileGUI friend =
            new ProfileGUI(centerUser.getFriends().get(friendIndex), i * 32, j * 32);
        setFriendUI(friend, interactionPane);
        friend.setTranslateX(-16);
        friend.setTranslateY(-16);
        Line line = new Line(centerUserGUI.getLayoutX(), centerUserGUI.getLayoutX(),
            friend.getLayoutX(), friend.getLayoutY());
        canvas.getChildren().addAll(line, friend);
      }
      canvas.getChildren().add(centerUserGUI);
    } catch (Exception e) {
      System.out.println(e);
    }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

  public void setFriendUI(ProfileGUI friend, Pane interactionPane) {
    Pane friendPane = new Pane();
    Label testLabel = new Label("hello test test");
    friendPane.getChildren().add(testLabel);
    friendPane.setVisible(false);
    friend.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      System.out.println("debug");
      if(friendPane.isVisible() == false)
        friendPane.setVisible(true);
      else
        friendPane.setVisible(false);
      });
    
    interactionPane.getChildren().add(friendPane);
  }
  
  
////////////////////////////////////////////////////////////////////////////////////////////////////
  
  @Override
  public void start(Stage primaryStage) throws Exception {

    HBox root = new HBox();
    Pane canvasPane = new Pane();
    Pane canvas = new Pane();

    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
      origCanvasTransX = e.getX();
      origCanvasTransY = e.getY();
    });
    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
      canvas.setTranslateX(e.getSceneX() - origCanvasTransX);
      canvas.setTranslateY(e.getSceneY() - origCanvasTransY);

    });
    canvasPane.getChildren().add(canvas);
    canvasPane.setPrefSize(500, 500);
    canvas.setPrefSize(480, 480);
    // translation added later
    canvasPane.setBackground(
        new Background(new BackgroundFill(Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    canvas.setBackground(
        new Background(new BackgroundFill(Color.LIGHTSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

    VBox status = new VBox();
    Pane centerUserPane = new Pane();
    centerUserPane.setPrefSize(300, 200);
    centerUserPane.setBackground(
        new Background(new BackgroundFill(Color.HOTPINK, CornerRadii.EMPTY, Insets.EMPTY)));

    Pane interactionPane = new Pane();
    
    
    createCenterUser(canvas, interactionPane, new Profile("center"));
    
    
    interactionPane.setPrefSize(300, 300);
    interactionPane.setBackground(
        new Background(new BackgroundFill(Color.FORESTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

    status.getChildren().addAll(centerUserPane, interactionPane);


    root.getChildren().addAll(canvasPane, status);


    // primaryStage.setTitle(APP_TITLE);
    Scene mainScene = new Scene(root, 800, 500);
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}


class ProfileGUI extends ImageView {
  private Profile profile;
  Circle profileCircle;

  public ProfileGUI(Profile profile, double x, double y) throws FileNotFoundException {
    super(new Image(new FileInputStream("application/profileIcon.png"), 32, 32, true, true));
    this.setLayoutX(x);
    this.setLayoutY(y);
    this.profile = profile;
    this.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
      DropShadow shadow = new DropShadow();
      this.setEffect(shadow);
    });
    this.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
      this.setEffect(null);
    });
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public void setImageSize(double x, double y) throws FileNotFoundException {
    this.setImage(new Image(new FileInputStream("application/profileIcon.png"), x, y, true, true));
  }
}



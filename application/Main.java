package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class Main extends Application {
  ProfileManager pm = new ProfileManager();
  
  LinkedList<ProfileGUI> profileGUI = new LinkedList<ProfileGUI>();
  
  double origCanvasTransX = 0;
  double origCanvasTransY;
  
  Profile centerUser = null;
  Profile curFriend = null;
  
  String textField = "type down user name...";
  String labelText = "console";
  
  Pane canvas;
  Pane canvasPane;
  Pane centerUserPane;
  Pane userFindPane;
  Pane console;
  Pane friendPane;
  Pane linkPane;
  
////////////////////////////////////////////////////////////////////////////////////////////////////
  private Pane createCanvasPane(Pane canvas) throws FileNotFoundException{
    /*
     * 480 x 480 pane center aligned
     */
    Pane canvasPane = new Pane();
    GridPane.setMargin(canvasPane, new Insets(10, 10, 10, 10));
    canvasPane.setPrefSize(480, 480);
    canvasPane.setBackground(
        new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
    Rectangle clip = new Rectangle(480, 480);
    canvasPane.setClip(clip);
    setCenterUser(canvas);

    // create UW-Madison logo
    ImageView uwMadisonLogo =
        new ImageView(new Image(new FileInputStream("application/madisonLogo.png")));
    uwMadisonLogo.setFitHeight(80);
    uwMadisonLogo.setPreserveRatio(true);
    uwMadisonLogo.setLayoutX(10);
    uwMadisonLogo.setLayoutY(390);
    
    canvasPane.getChildren().addAll(canvas, uwMadisonLogo);
    return canvasPane;
  }
  
  private Pane createCanvas() {
    Pane canvas = new Pane();
    // make it movable
    
    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        origCanvasTransX = - canvas.getTranslateX() + e.getSceneX();
        origCanvasTransY = - canvas.getTranslateY() + e.getSceneY();
      }
    });
    
    canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        canvas.setTranslateX(canvas.getTranslateX() + e.getX() - origCanvasTransX);
        canvas.setTranslateY(canvas.getTranslateY() + e.getY() - origCanvasTransY);
      }
      
    });
    
    return canvas;
  }
  
  private void setCenterUser(Pane canvas) throws FileNotFoundException{
    canvas.getChildren().clear();
    double height = 480;
    double width = 480;
    for(int i = 0; i < centerUser.getListOfUsersFriends().size(); i++) {
      profileGUI.add(new ProfileGUI(centerUser.getListOfUsersFriends().get(i)));
    }
    
    // set size of a canvas depending on the number of friend
    while(((double)profileGUI.size()) / height > 0.01 &&
        ((double)profileGUI.size()) / width > 0.01) {
      height += 64;
      width += 64;
      
      System.out.println("getting bigger... size: "+ profileGUI.size());
      
    }
    
    canvas.setPrefSize(width, height);
    
    // temporary gui part
    
    ProfileGUI centerBucky = new ProfileGUI(centerUser);
    centerBucky.setImageSize(128);
    centerBucky.setLayoutX(width/2 - 64);
    centerBucky.setLayoutY(height/2 - 64);
    
    for(int i = 0; i < centerUser.getListOfUsersFriends().size(); i++) {
      addFriendEventHandler(profileGUI.get(i), canvas, centerUser.getListOfUsersFriends().get(i), centerUser);
      profileGUI.get(i).setFitHeight(64);
      profileGUI.get(i).setPreserveRatio(true);
      profileGUI.get(i).positionX = profileGUI.get(i).hashCode() % width - 32;
      profileGUI.get(i).setLayoutX(profileGUI.get(i).positionX);
      profileGUI.get(i).positionY = profileGUI.get(i).hashCode()/width % height - 32;
      profileGUI.get(i).setLayoutY(profileGUI.get(i).positionY);
      Line line = new Line(centerBucky.getLayoutX()+64, centerBucky.getLayoutY()+64, profileGUI.get(i).getLayoutX()+32, profileGUI.get(i).getLayoutY()+32);
      line.setStrokeWidth(3);
      canvas.getChildren().addAll(line, profileGUI.get(i));
    }
    
    // create instance for each friend and add to the canvas in a location
    
    
    canvas.getChildren().add(centerBucky);
  }
  
  private void addFriendEventHandler(ImageView friend, Pane canvas, Profile friendProfile, Profile centerUserProfile){
    friend.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent arg0) {
        Group mutualFriendIndicators = mutualFriend(friendProfile, centerUserProfile);
        canvas.getChildren().add(mutualFriendIndicators);
        friend.setCursor(Cursor.HAND);
      }
    });
    friend.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        if(e.getClickCount() == 2) {
          return;
        }
        canvas.getChildren().remove(canvas.getChildren().size() - 1);
        friend.setCursor(Cursor.DEFAULT);
      }
    });
    friend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        if(e.getTarget() instanceof ProfileGUI) {
          ProfileGUI friendProfileGUI = (ProfileGUI) e.getTarget(); 
          curFriend = friendProfileGUI.getProfile();
          System.out.println("friend change");
        }
        try {
          setFriendPane(friendPane);          
        }catch (FileNotFoundException f) {
          
        }
        if(e.getClickCount() == 2) {
          try {
            centerUser = friendProfile;
            profileGUI.clear();
            System.out.println(centerUser.user_Name);
            setCenterUser(canvas);            
            setCenterUserPane(centerUserPane);
          } catch(FileNotFoundException f) {
            System.out.println("this code is unreachable");
          }
        }
      }
    });
  }
  
  private ProfileGUI findProfileGUI(Profile friend) {
    for(int i = 0; i < profileGUI.size(); i++) {
      if(profileGUI.get(i).getProfile().equals(friend)) {
        return profileGUI.get(i);
      }
    }
    return null;
  }
  
  private Group mutualFriend(Profile friend, Profile centerUser) {
    Group lineGroup = new Group();
    List<Profile> mutualFriendList = pm.getMutualFriends(friend, centerUser);
    if(mutualFriendList == null) {
      return lineGroup;
    }
    ProfileGUI friendGUI = findProfileGUI(friend);
    for (int i = 0; i < mutualFriendList.size(); i++) {
      ProfileGUI mutualFriendGUI = findProfileGUI(mutualFriendList.get(i));
      Line line = new Line(friendGUI.positionX + 32, friendGUI.positionY + 32, mutualFriendGUI.positionX + 32,
          mutualFriendGUI.positionY + 32);
      line.setStrokeWidth(3);
      lineGroup.getChildren().add(line);
    }
    return lineGroup;
  }
  

////////////////////////////////////////////////////////////////////////////////////////////////////
  
  private Pane createCenterUserPane() throws FileNotFoundException {
    Pane centerUserPane = new Pane();
    GridPane.setMargin(centerUserPane, new Insets(10, 10, 10, 10));
    centerUserPane.setBorder(new Border(new BorderStroke(Color.WHITE, 
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    centerUserPane.setPrefSize(280, 180);
    
    setCenterUserPane(centerUserPane);
    
    return centerUserPane;
  }
  
  private void setCenterUserPane(Pane centerUserPane) throws FileNotFoundException{
    centerUserPane.getChildren().clear();
    Image centerUserImage = centerUser.user_Picture;
    String userName = centerUser.user_Name;

    String userBio = "user need to fill \nout their bio!";
    ImageView centerUserImageView = new ImageView(centerUserImage);
    centerUserImageView.setFitWidth(100);
    centerUserImageView.setPreserveRatio(true);
    centerUserImageView.setLayoutX(10);
    centerUserImageView.setLayoutY(10);
    
    Label userNameLabel = new Label(userName);
    userNameLabel.setStyle("-fx-font-size: 15pt;"
        + "-fx-text-fill: black;");
    userNameLabel.setLayoutX(150);
    userNameLabel.setLayoutY(10);
    
    Text userBioText = new Text(userBio);
//    userBioText.setStyle("-fx-font-size: 15pt;"
//        + "-fx-text-fill: white;");
    userBioText.setLayoutX(150);
    userBioText.setLayoutY(50);
    
    HBox links = createSnsLinkPane();
    links.setLayoutX(100);
    links.setLayoutY(130);
    
    centerUserPane.getChildren().addAll(centerUserImageView, userNameLabel, userBioText, links);
  }
  
////////////////////////////////////////////////////////////////////////////////////////////////////
  private Pane createUserFindPane() {
    Pane userFindPane = new Pane();
    GridPane.setMargin(userFindPane, new Insets(0, 10, 0, 10));
    userFindPane.setPrefSize(280, 30);
    TextField userNameTextField = new TextField("type user name here...");
    userNameTextField.setPrefSize(150, 30);
    userNameTextField.setLayoutX(0);
    Button findButton = new Button("Find");
    findButton.setOnAction(e->{
      textField = userNameTextField.getText();
      setConsole(console);
      try { 
        setFriendPane(friendPane);        
      } catch(FileNotFoundException f) {
        
      }
    });
    findButton.setPrefSize(50,30);
    findButton.setLayoutX(175);
    userFindPane.getChildren().addAll(userNameTextField, findButton);
    
    return userFindPane;
  }
  

////////////////////////////////////////////////////////////////////////////////////////////////////
  private Pane createConsole() {
    Pane console = new Pane();
    GridPane.setMargin(console, new Insets(10, 10, 10, 10));
    console.setBorder(new Border(new BorderStroke(Color.WHITE, 
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    console.setPrefSize(280, 80);
    
    setConsole(console);
    
    return console;
  }
  
  private String setConsole(Pane console) {
    console.getChildren().clear();
    String userName = textField;
    String friendPathStr = "";
    Label consoleLabel = new Label();
    consoleLabel.setStyle("-fx-font-size: 15pt;"
        + "-fx-text-fill: black;");
    consoleLabel.setLayoutX(10);
    consoleLabel.setLayoutY(10);
    console.getChildren().add(consoleLabel);
    //get profile of userName
    try {
      System.out.println(centerUser.user_Name);
      System.out.println(pm.findProfile(userName).user_Name);
      List<String> friendPath = pm.getShortestPath(centerUser, pm.findProfile(userName));
      friendPathStr = "To reach out to the user";
      for(int i = 0; i < friendPath.size(); i++) {
        friendPathStr = friendPathStr.concat(friendPath.get(i) + "->");
      }
    } catch(Exception e) {
      friendPathStr = "invalid input";
      System.out.println("error");
    }
    
    consoleLabel.setText(friendPathStr);
    return friendPathStr;
    
  }
  
////////////////////////////////////////////////////////////////////////////////////////////////////
  private Pane createFriendPane() throws FileNotFoundException {
    Pane friendPane = new Pane();
    GridPane.setMargin(friendPane, new Insets(0, 10, 0, 10));
    friendPane.setBorder(new Border(new BorderStroke(Color.WHITE, 
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    friendPane.setPrefSize(280, 130);
    
    setFriendPane(friendPane);
    
    return friendPane;
  }
  
  private void setFriendPane(Pane friendPane) throws FileNotFoundException {
    friendPane.getChildren().clear();
    if(curFriend == null) {
      return;
    }
    ImageView friendImage =
        new ImageView(curFriend.user_Picture);
    friendImage.setFitWidth(50);
    friendImage.setPreserveRatio(true);
    friendImage.setLayoutX(10);
    friendImage.setLayoutY(10);
    
    Label name = new Label(curFriend.user_Name);
    name.setStyle("-fx-font-size: 15pt;"
        + "-fx-text-fill: black;");
    name.setLayoutX(100);
    name.setLayoutY(2);
    
    Button addButton = new Button("ADD");
    addButton.setPrefWidth(100);
    addButton.setLayoutX(10);
    addButton.setLayoutY(80);
    addButton.setOnAction(e -> {
//      pm.getGraph().addFriend(profileA, profileB);
      try {
        setCenterUser(canvas);
      } catch(FileNotFoundException f) {
        
      }
    });
    
    // remove after A2 and ADD button to toggle back and forth to remove
    Button removeButton = new Button("REMOVE");
    removeButton.setPrefWidth(100);
    removeButton.setLayoutX(120);
    removeButton.setLayoutY(80);
    removeButton.setOnAction(e -> {
//    pm.getGraph().removeFriend(profileA, profileB);
    try {
      setCenterUser(canvas);
    } catch(FileNotFoundException f) {
      
    }
    });
    
    HBox links = createSnsLinkPane();
    links.setLayoutX(100);
    links.setLayoutY(30);
    
    friendPane.getChildren().addAll(friendImage, name, addButton, removeButton, links);
  }
  
  
////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * Misc methods
   */
  
  private HBox createSnsLinkPane() throws FileNotFoundException {
    HBox snsLinkPane = new HBox();
    snsLinkPane.setBackground(
        new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, new Insets(-5))));

    // linkedin, fb, insta, youtube
    snsLinkPane.setSpacing(5);
    ImageView linkedIn =
        new ImageView(new Image(new FileInputStream("application/linkedIn.png"), 30, 30, true, true));
    addLinkEventHandler(linkedIn, "https://linkedin.com");
    ImageView facebook =
        new ImageView(new Image(new FileInputStream("application/facebook.png"), 30, 30, true, true));
    addLinkEventHandler(facebook, "https://fb.com");
    ImageView instagram =
        new ImageView(new Image(new FileInputStream("application/instagram.png"), 30, 30, true, true));
    addLinkEventHandler(instagram, "https://instagram.com");
    
    snsLinkPane.getChildren().addAll(linkedIn, facebook, instagram);
    
    return snsLinkPane;
  }
  
  private Pane createLinkPane() throws FileNotFoundException{
    Pane linkPane = new Pane();
    GridPane.setMargin(linkPane, new Insets(5,0,5,0));
    linkPane.setPrefSize(280, 30);
    Button load = new Button("Load");
    load.setLayoutX(10);
    
    load.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        String path = "";
        TextInputDialog popup = new TextInputDialog("path...");
        popup.setHeaderText("write down path for load");
        Optional<String> result = popup.showAndWait();
        if (result.isPresent()) {
          path = result.get();
        }
        FileControl.writeLog(path);
      }  
    });
    Button save = new Button("Save");
    save.setLayoutX(60);
    save.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Auto-generated method stub
        String path = "";
        TextInputDialog popup = new TextInputDialog("path...");
        popup.setHeaderText("write down path for save");
        Optional<String> result = popup.showAndWait();
        if (result.isPresent()) {
          path= result.get();
        }
        FileControl.writeLog(path);
      }  
    });
    
    ImageView gitHub =  
        new ImageView(new Image(new FileInputStream("application/GitHub.png"), 30, 30, true, true));
    addLinkEventHandler(gitHub, "https://github.com");
    gitHub.setLayoutX(260);
    linkPane.getChildren().addAll(load, save, gitHub);
    return linkPane;
  }

  private static void openURL(String url) {

    try {
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(new URI(url));
      }
    } catch (URISyntaxException e) {

    } catch (IOException e) {

    }
  }
  
  private void addLinkEventHandler(ImageView link, String url){
    link.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent arg0) {
        link.setCursor(Cursor.HAND);
      }
    });
    link.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent arg0) {
        link.setCursor(Cursor.DEFAULT);
      }
    });
    link.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent arg0) {
        openURL(url);
      }
    });
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  
//  private Dialog<R> createPopupWindow(String title, String Function) {
//    Pane popup = new Pane();
//    
//    return popup;
//  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  @Override
  public void start(Stage primaryStage) throws Exception {

    /*
     * root
     *  -> canvasPane
     *      -> canvas
     *  
     *    -> userPane
     *        -> profileImage
     *        -> profileBio 
     *    -> userFindPane
     *        -> userFindTextBox
     *        -> userFindButton
     *    -> friendPane
     *        -> firendImage
     *        -> friendName
     *        -> friendButton
     *        -> unfriendButton
     */
    centerUser = new Profile("default");
    Profile a = new Profile("a");
    Profile b = new Profile("b");
    pm.getGraph().addFriend(centerUser, a);
    pm.getGraph().addFriend(centerUser, b);
//    b.list_of_user_friends.add(centerUser);
//    a.list_of_user_friends.add(centerUser);
//    centerUser.list_of_user_friends.add(a);
//    centerUser.list_of_user_friends.add(b);
    pm.getGraph().addFriend(a, b);
    try {
      for(int i= 0; i < pm.getGraph().getVertexList().size(); i++) {
        System.out.println(pm.getGraph().getVertexList().get(i).getUserName());              
      }
      System.out.println(pm.findProfile("a"));
    } catch(Exception e) {
      System.out.println(e);
    }
//    b.list_of_user_friends.add(a);
//    a.list_of_user_friends.add(b);
    
    canvas = createCanvas();
    canvasPane = createCanvasPane(canvas);
    centerUserPane = createCenterUserPane();
    userFindPane = createUserFindPane();
    console = createConsole();
    linkPane = createLinkPane();
    

    // create test profile
    Profile test = new Profile("john");
    Profile testFriend = new Profile("mark");
    friendPane = createFriendPane();

    updateScene(primaryStage);
  }

  public static void main(String[] args) {
    launch(args);
  }
  
  
  
  private void updateScene(Stage primaryStage) {
    GridPane root = new GridPane();
    root.setPrefSize(800, 500);
    root.setBackground(
        new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
    root.add(canvasPane, 0, 0, 1, 5);
    root.add(centerUserPane, 1, 0, 1, 1);
    root.add(userFindPane, 1, 1, 1, 1);
    root.add(console, 1, 2, 1, 1);
    root.add(friendPane, 1, 3, 1, 1);
    root.add(linkPane, 1, 4, 1, 1);
    Scene mainScene = new Scene(root, 800, 500);
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }
}



class ProfileGUI extends ImageView {
  private Profile profile;
  double positionX;
  double positionY;

  public ProfileGUI(Profile profile) throws FileNotFoundException {
    super(profile.user_Picture);
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

  public void setImageSize(double height) throws FileNotFoundException {
    super.setFitHeight(height);
    super.setPreserveRatio(true);
  }
}



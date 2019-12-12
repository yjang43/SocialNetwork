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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.stage.WindowEvent;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Runs a GUI program that implements Social Network graph
 * 
 * @author yjang
 * @author yongseong
 */
public class Main extends Application {
  
  // global variable that needs to be created here
  // profile manager that will be used through out the program
  private ProfileManager pm = new ProfileManager();
  
  // profileGUI that contains profileGUI instances were created for each center user
  private LinkedList<ProfileGUI> profileGUI = new LinkedList<ProfileGUI>();

  // center user and friend user will be updated here
  public static Profile centerUser = null;
  private Profile curFriend = null;

  // text for some UI will is stored here
  private String textField = "type down user name...";
  private String consoleOutput = "";

  // main GUI components instances are declared here
  private Pane canvas;
  private Pane canvasPane;
  private Pane centerUserPane;
  private Pane userFindPane;
  private Pane console;
  private Pane friendPane;
  private Pane linkPane;

  // variables needed to be declare to save position of mouse
  private double origCanvasTransX = 0;
  private double origCanvasTransY = 0;
  

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * Method that make main canvas pane and set it background and set it's shape and size. In
   * addition, it create UW-Madison Logo
   * 
   * @param canvas white canvas that contains centerUser and friendship
   * @return return Pane it create canvasPane and return Pane
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private Pane createCanvasPane(Pane canvas) throws FileNotFoundException {
    
    // create canvasPane and set attributes
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

    // insert components to canvasPane
    canvasPane.getChildren().addAll(canvas, uwMadisonLogo);
    return canvasPane;
  }

  /**
   * Method that makes main movable canvas
   *  
   * @return return canvas 
   */  
  private Pane createCanvas() {
    
    // create canvas and set attributes
    Pane canvas = new Pane();
    // make canvas movable when button is pressed
    canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
      /**
       * get the value of the property translate and the value of the property scene
       * @param e
       */
      @Override
      public void handle(MouseEvent e) {
        origCanvasTransX = -canvas.getTranslateX() + e.getSceneX();
        origCanvasTransY = -canvas.getTranslateY() + e.getSceneY();
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

  /**
   * set main user in the center and set other user randomly for the friends
   * 
   * @param canvas canvas
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private void setCenterUser(Pane canvas) throws FileNotFoundException {
    
    double height = 480;
    double width = 480;
    
    // set error status if centerUser is null
    if (centerUser == null) {
      Label centerUserError = new Label("need to set center user");
      centerUserError.setLayoutX(width / 2 - 50);
      centerUserError.setLayoutY(height / 2);
      canvas.getChildren().add(centerUserError);
      return;
    }
    
    // clear canvas before add update components
    canvas.getChildren().clear();
    profileGUI.clear();
    
    
    // set size of a canvas depending on the number of friend
    for (int i = 0; i < centerUser.getListOfUsersFriends().size(); i++) {
      profileGUI.add(new ProfileGUI(centerUser.getListOfUsersFriends().get(i)));
    }
    while (((double) profileGUI.size()) / height > 0.01
        && ((double) profileGUI.size()) / width > 0.01) {
      height += 64;
      width += 64;
    }
    canvas.setPrefSize(width, height);
    
    
    // set centerUser image and friend images on canvas
    ProfileGUI centerBucky = new ProfileGUI(centerUser);
    centerBucky.setImageSize(128);
    centerBucky.setLayoutX(width / 2 - 64);
    centerBucky.setLayoutY(height / 2 - 64);
    for (int i = 0; i < centerUser.getListOfUsersFriends().size(); i++) {
      addFriendEventHandler(profileGUI.get(i), canvas, centerUser.getListOfUsersFriends().get(i),
          centerUser);
      profileGUI.get(i).setFitHeight(64);
      profileGUI.get(i).setPreserveRatio(true);
      profileGUI.get(i).positionX = profileGUI.get(i).hashCode() % width - 32;
      profileGUI.get(i).setLayoutX(profileGUI.get(i).positionX);
      profileGUI.get(i).positionY = profileGUI.get(i).hashCode() / width % height - 32;
      profileGUI.get(i).setLayoutY(profileGUI.get(i).positionY);
      Line line = new Line(centerBucky.getLayoutX() + 64, centerBucky.getLayoutY() + 64,
          profileGUI.get(i).getLayoutX() + 32, profileGUI.get(i).getLayoutY() + 32);
      line.setStrokeWidth(3);
      canvas.getChildren().addAll(line, profileGUI.get(i));
    }

    // create UI components in centerUserPane
    Label graphStatus = new Label("# of users:" + pm.getGraph().order() + "\r# of size: "
        + pm.getGraph().size() + "\r# of connected components");
    graphStatus.setLayoutX(300);
    
    // insert components to canvas
    canvas.getChildren().addAll(centerBucky, graphStatus);
  }

  /**
   * Add friends and handle the event related with friends
   * 
   * @param friend friend profileGUI 
   * @param canvas canvas 
   * @param friendProfile friend profile
   * @param centerUserProfile center user profile
   */
  private void addFriendEventHandler(ImageView friend, Pane canvas, Profile friendProfile,
      Profile centerUserProfile) {
    
    // set event handlers
    friend.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
      /**
       * create mutual friend relationship
       * @param e mouse event
       */
      @Override
      public void handle(MouseEvent e) {
        Group mutualFriendIndicators = mutualFriend(friendProfile, centerUserProfile);
        canvas.getChildren().add(mutualFriendIndicators);
        friend.setCursor(Cursor.HAND);
      }
    });
    friend.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
      /**
       * destroys mutual friend relationship
       * @param e mouse event
       */
      @Override
      public void handle(MouseEvent e) {
        if (e.getClickCount() == 2) {
          return;
        }
        canvas.getChildren().remove(canvas.getChildren().size() - 1);
        friend.setCursor(Cursor.DEFAULT);
      }
    });
    friend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      /**
       * when mouse is clicked once it sets current friend the center user is looking at to the user
       * clicked.
       * when mouse is double clicked it centers friend user to center user
       * 
       * @param e mouse event
       */
      @Override
      public void handle(MouseEvent e) {
        if (e.getTarget() instanceof ProfileGUI) {
          ProfileGUI friendProfileGUI = (ProfileGUI) e.getTarget();
          curFriend = friendProfileGUI.getProfile();
          System.out.println("friend change");
        }
        try {
          setFriendPane(friendPane);
        } catch (FileNotFoundException f) {

        }
        if (e.getClickCount() == 2) {
          try {
            centerUser = friendProfile;
            profileGUI.clear();
            System.out.println(centerUser.user_Name);
            setCenterUser(canvas);
            FileControl.setLog("s " + centerUser.getUserName());
            setCenterUserPane(centerUserPane);
          } catch (FileNotFoundException f) {
            System.out.println("this code is unreachable");
          }
        }
      }
    });
  }

  /**
   * build the mutual friendship with centerUser and create lines accordingly 
   * @param friend friend whose friends with centerUser we are finding
   * @param centerUser centerUser whose friends with curFriend we are finding
   * @return return group of lines
   */
  private Group mutualFriend(Profile friend, Profile centerUser) {
    
    // create an instance of group that holds all the lines
    Group lineGroup = new Group();
    
    // create the number of line accordingly
    List<Profile> mutualFriendList = pm.getMutualFriends(friend, centerUser);
    if (mutualFriendList == null) {
      return lineGroup;
    }
    ProfileGUI friendGUI = findProfileGUI(friend);
    for (int i = 0; i < mutualFriendList.size(); i++) {
      ProfileGUI mutualFriendGUI = findProfileGUI(mutualFriendList.get(i));
      Line line = new Line(friendGUI.positionX + 32, friendGUI.positionY + 32,
          mutualFriendGUI.positionX + 32, mutualFriendGUI.positionY + 32);
      line.getStrokeDashArray().addAll(20.0, 20.0, 20.0, 20.0);
      line.setStroke(Color.GRAY);
      line.setStrokeWidth(3);
      lineGroup.getChildren().add(line);
    }
    
    return lineGroup;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * create centerUser pane and set margin, border, and size of the Pane
   * 
   * @return Pane return centerUserPane
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private Pane createCenterUserPane() throws FileNotFoundException {
    
    // create centerUserPane and set its attribute
    Pane centerUserPane = new Pane();
    GridPane.setMargin(centerUserPane, new Insets(10, 10, 10, 10));
    centerUserPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY, new BorderWidths(3))));
    centerUserPane.setPrefSize(280, 180);
    setCenterUserPane(centerUserPane);

    return centerUserPane;
  }

  /**
   * set centerUserPane in the program whenever there is a change in centerUserPane
   * 
   * @param centerUserPane
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private void setCenterUserPane(Pane centerUserPane) throws FileNotFoundException {
    centerUserPane.getChildren().clear();

    // if centerUser is null give an error message
    if (centerUser == null) {
      Label centerUserError = new Label("need to set center user");
      centerUserError.setLayoutX(centerUserPane.getPrefWidth() / 2 - 50);
      centerUserError.setLayoutY(centerUserPane.getPrefHeight() / 2);
      centerUserPane.getChildren().add(centerUserError);
      return;
    }

    // create components in this pane and set their attributes
    Image centerUserImage = centerUser.user_Picture;
    String userName = centerUser.user_Name;
    String userBio = "user need to fill \nout their bio!";
    ImageView centerUserImageView = new ImageView(centerUserImage);
    centerUserImageView.setFitWidth(100);
    centerUserImageView.setPreserveRatio(true);
    centerUserImageView.setLayoutX(10);
    centerUserImageView.setLayoutY(10);
    Label userNameLabel = new Label(userName);
    userNameLabel.setStyle("-fx-font-size: 15pt;" + "-fx-text-fill: white;");
    userNameLabel.setLayoutX(150);
    userNameLabel.setLayoutY(10);
    Text userBioText = new Text(userBio + "\rYou have "
       + centerUser.list_of_user_friends.size() + " friends");
    userBioText.setFill(Color.WHITE);
    userBioText.setLayoutX(150);
    userBioText.setLayoutY(50);
    HBox links = createSnsLinkPane();
    links.setLayoutX(100);
    links.setLayoutY(130);

    // insert components to canvas
    centerUserPane.getChildren().addAll(centerUserImageView, userNameLabel, userBioText, links);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * create user find pane and set margin, border, and size of the Pane add name text field and
   * button to user find pane
   * 
   * @return userFindPane that include text field and button
   */
  private Pane createUserFindPane() {
    
    // create userFindPane and set its attributes
    Pane userFindPane = new Pane();
    GridPane.setMargin(userFindPane, new Insets(0, 10, 0, 10));
    userFindPane.setPrefSize(280, 30);
    
    // create other UI components that will be inserted in the userFindPane
    TextField userNameTextField = new TextField("type user name here...");
    userNameTextField.setPrefSize(150, 30);
    userNameTextField.setLayoutX(0);
    Button findButton = new Button("Find");
    findButton.setPrefSize(50, 30);
    findButton.setLayoutX(175);
    findButton.setOnAction(e -> {
      // set an console text accordingly to the situation
      textField = userNameTextField.getText();
      try {
        curFriend = pm.findProfile(textField);
        List<String> friendPath = pm.getShortestPath(centerUser, pm.findProfile(textField));
        String friendPathStr = "Path: ";
        int i;
        for (i = 0; i < friendPath.size() - 1; i++) {
          friendPathStr = friendPathStr.concat(friendPath.get(i) + "->");
        }
        friendPathStr = friendPathStr.concat(friendPath.get(i));
        consoleOutput = friendPathStr;
      } catch (Exception f) {
        consoleOutput = textField + " is not your friend";
        try {
          pm.findProfile(textField);
        } catch (Exception k) {
          consoleOutput = "user " + textField + " does not exist";
        }
      }
      setConsole(console);
      try {
        curFriend = pm.findProfile(textField);
        setFriendPane(friendPane);
      } catch (Exception f) {
      }
    });
    
    // insert UI components to userFindPane
    userFindPane.getChildren().addAll(userNameTextField, findButton);

    return userFindPane;
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * create console and set margin, border, and size of the console
   * 
   * @return console that set essential elements
   */
  private Pane createConsole() {
    
    // create console and set its attribute
    Pane console = new Pane();
    GridPane.setMargin(console, new Insets(10, 10, 10, 10));
    console.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY, new BorderWidths(3))));
    console.setPrefSize(280, 80);
    setConsole(console);

    return console;
  }

  /**
   * set the console and sett style and layout size
   * show the path how to reach out the friends from the centerUser
   * 
   * @param console console
   */
  private void setConsole(Pane console) {
    
    // create before adding any updates to console
    console.getChildren().clear();
    
    // create label to output status and set its attribute
    Label consoleLabel = new Label();
    consoleLabel.setStyle("-fx-font-size: 13pt;" + "-fx-text-fill: black;");
    consoleLabel.setLayoutX(10);
    consoleLabel.setLayoutY(10);
    console.getChildren().add(consoleLabel);
    consoleLabel.setText(consoleOutput);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * create friendPane and set margin, border, and size of the friend pane
   * 
   * @return friendPane that set essential element
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private Pane createFriendPane() throws FileNotFoundException {
    
    // create friendPane and set its attributes
    Pane friendPane = new Pane();
    GridPane.setMargin(friendPane, new Insets(0, 10, 0, 10));
    friendPane.setBorder(new Border(new BorderStroke(Color.WHITE, BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY, new BorderWidths(3))));
    friendPane.setPrefSize(280, 130);
    setFriendPane(friendPane);

    return friendPane;
  }

  /**
   * set friendPane whenever there is an update to components in friendPane
   * 
   * @param friendPane friendPane
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private void setFriendPane(Pane friendPane) throws FileNotFoundException {
    friendPane.getChildren().clear();
    
    // if curFriend is null clear pane
    if (curFriend == null) {
      return;
    }
    
    // update friendPane attributes
    ImageView friendImage = new ImageView(curFriend.user_Picture);
    friendImage.setFitWidth(50);
    friendImage.setPreserveRatio(true);
    friendImage.setLayoutX(10);
    friendImage.setLayoutY(10);
    Label name = new Label(curFriend.user_Name);
    name.setStyle("-fx-font-size: 15pt;" + "-fx-text-fill: black;");
    name.setLayoutX(100);
    name.setLayoutY(2);
    HBox links = createSnsLinkPane();
    links.setLayoutX(100);
    links.setLayoutY(30);
    Button addButton = new Button("ADD");
    addButton.setPrefWidth(100);
    addButton.setLayoutX(10);
    addButton.setLayoutY(80);
    addButton.setOnAction(e -> {
      // add friendship when button is clicked
      pm.getGraph().addFriend(centerUser, curFriend);
      FileControl.setLog("a " + centerUser.getUserName() + " " + curFriend.getUserName());
      try {
        consoleOutput =
            centerUser.getUserName() + " is now a friend with " + curFriend.getUserName();
        setConsole(console);
        setCenterUser(canvas);
        setCenterUserPane(centerUserPane);
      } catch (FileNotFoundException f) {
      }
    });
    Button removeButton = new Button("REMOVE");
    removeButton.setPrefWidth(100);
    removeButton.setLayoutX(120);
    removeButton.setLayoutY(80);
    removeButton.setOnAction(e -> {
      // remove friendship if friendship is already there and delete frienduser if there is no
      // friendship
      boolean isFriend = false;
      for (int i = 0; i < centerUser.list_of_user_friends.size(); i++) {
        try {
          if (pm.findProfile(textField) == centerUser.list_of_user_friends.get(i)) {
            isFriend = true;
            break;
          }
        } catch (Exception f) {
          System.out.println("user must be found here");
        }
      }
      if (isFriend) {
        // if user is friend then delete friendship
        pm.getGraph().deleteFriend(centerUser, curFriend);
        FileControl.setLog("r " + centerUser.getUserName() + " " + curFriend.getUserName());
        try {
          consoleOutput =
              centerUser.getUserName() + " is no mo friend with " + curFriend.getUserName();
          setConsole(console);
          setCenterUser(canvas);
          setCenterUserPane(centerUserPane);
        } catch (FileNotFoundException f) {

        }
      } else {
        // if not delete friend's account
        pm.getGraph().deleteUser(curFriend);
        FileControl.setLog("r " + curFriend.getUserName());
        try {
          consoleOutput = curFriend.getUserName() + " deleted an account";
          curFriend = null;
          setConsole(console);
          setCenterUser(canvas);
          setCenterUserPane(centerUserPane);
          setFriendPane(friendPane);
        } catch (FileNotFoundException f) {
        }
      }
    });

    // insert all components
    friendPane.getChildren().addAll(friendImage, name, addButton, removeButton, links);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * create create Link pane and set margin, border, and size of the friend pane
   * 
   * @return friendPane that set essential element
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private Pane createLinkPane() throws FileNotFoundException {
    
    // create linkPane instance and set its attributes
    Pane linkPane = new Pane();
    GridPane.setMargin(linkPane, new Insets(5, 0, 5, 0));
    linkPane.setPrefSize(280, 30);
    
    // create UI components in linkPane
    ImageView gitHub =
        new ImageView(new Image(new FileInputStream("application/GitHub.png"), 30, 30, true, true));
    addLinkEventHandler(gitHub, "https://github.com/yjang43/SocialNetwork");
    gitHub.setLayoutX(260);
    Button load = new Button("Load");
    load.setLayoutX(10);
    load.setOnAction(new EventHandler<ActionEvent>() {
      /**
       * when button is clicked, user is given a statement to load a file
       * @param e mouse event
       */
      @Override
      public void handle(ActionEvent e) {
        String path = "";
        TextInputDialog popup = new TextInputDialog("path...");
        popup.setHeaderText("write down path for load");
        Optional<String> result = popup.showAndWait();
        if (result.isPresent()) {
          path = result.get();
        }
        FileControl.setProfile_manager(pm);
        try {
          FileControl.constructGraph(path);
          consoleOutput = "succesfully loaded the file " + path;
        } catch (FileNotFoundException f) {
          consoleOutput = "file load error: " + path;
        }
        try {
          setConsole(console);
          setCenterUser(canvas);
          setCenterUserPane(centerUserPane);
        } catch (FileNotFoundException f) {

        }
      }
    });
    Button save = new Button("Save");
    save.setLayoutX(60);
    save.setOnAction(new EventHandler<ActionEvent>() {
      /**
       * when button is clicked, user is given a statement to save a file
       * @param e mouse event
       */
      @Override
      public void handle(ActionEvent arg0) {
        // TODO Auto-generated method stub
        String path = "";
        TextInputDialog popup = new TextInputDialog("path...");
        popup.setHeaderText("write down path for save");
        Optional<String> result = popup.showAndWait();
        if (result.isPresent()) {
          path = result.get();
        }
        FileControl.setProfile_manager(pm);
        try {
          FileControl.writeLog(path);
          consoleOutput = "succesfully saved the file" + path;
        } catch (IOException f) {
          consoleOutput = "file save error: " + path;
        }
        try {
          setConsole(console);
          setCenterUser(canvas);
          setCenterUserPane(centerUserPane);
        } catch (FileNotFoundException f) {

        }
      }
    });
    Button signUp = new Button("Add User");
    signUp.setLayoutX(110);
    signUp.setOnAction(new EventHandler<ActionEvent>() {
      /**
       * when button is clicked, user is given a statement to sign up
       * @param e mouse event
       */
      @Override
      public void handle(ActionEvent arg0) {
        String userName = "";
        TextInputDialog popup = new TextInputDialog("userName");
        popup.setHeaderText("what is your user name");
        Optional<String> result = popup.showAndWait();
        if (result.isPresent()) {
          try {
            userName = result.get();
            Profile userProfile = pm.findProfile(userName);
            Alert popup2 = new Alert(AlertType.NONE, "logining in as... " + userName);
            popup2.getDialogPane().setPrefSize(200, 100);
            popup2.getDialogPane().getButtonTypes().add(ButtonType.OK);
            popup2.show();
            if (centerUser == null) {
              centerUser = userProfile;
              FileControl.setLog("s " + userName);
            }
          } catch (Exception e) {
            userName = result.get();
            Alert popup2 = new Alert(AlertType.NONE, "Welcome, " + userName);
            popup2.getDialogPane().setPrefSize(200, 100);
            popup2.getDialogPane().getButtonTypes().add(ButtonType.OK);
            popup2.show();
            Profile userProfile = new Profile(userName);
            pm.getGraph().addUser(userProfile);
            FileControl.setLog("a " + userName);
            if (centerUser == null) {
              centerUser = userProfile;
              FileControl.setLog("s " + userName);
              try {
                setCenterUser(canvas);
                setCenterUserPane(centerUserPane);
              } catch (Exception k) {
              }
            }
          }
          consoleOutput = "user " + userName + " is successfully added";
          setConsole(console);
        }
        try {
          setCenterUser(canvas);
          setCenterUserPane(centerUserPane);
        } catch (Exception e) {
        }
      }
    });
    
    // insert all comoponents
    linkPane.getChildren().addAll(load, save, signUp, gitHub);
    
    return linkPane;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * Misc methods
   */

  /**
   * find profileGUI if the number of profile equals with input friends
   * 
   * @param friend friend being searched
   * @return return the number of profileGUI if the number of profile equals with the size of input
   *         friends
   */
  private ProfileGUI findProfileGUI(Profile friend) {
    for (int i = 0; i < profileGUI.size(); i++) {
      if (profileGUI.get(i).getProfile().equals(friend)) {
        return profileGUI.get(i);
      }
    }
    return null;
  }
  
  /**
   * create snsLinkPane that will be used in centerUserPane and friendPane
   * 
   * @return snsLinkPane HBox that contains linkedIn, facebook, and instagram
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message.
   */
  private HBox createSnsLinkPane() throws FileNotFoundException {
    
    // create snsLinkPane
    HBox snsLinkPane = new HBox();
    snsLinkPane.setBackground(
        new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, new Insets(-5))));

    // linkedin, fb, insta
    snsLinkPane.setSpacing(5);
    ImageView linkedIn = new ImageView(
        new Image(new FileInputStream("application/linkedIn.png"), 30, 30, true, true));
    addLinkEventHandler(linkedIn, "https://linkedin.com");
    ImageView facebook = new ImageView(
        new Image(new FileInputStream("application/facebook.png"), 30, 30, true, true));
    addLinkEventHandler(facebook, "https://fb.com");
    ImageView instagram = new ImageView(
        new Image(new FileInputStream("application/instagram.png"), 30, 30, true, true));
    addLinkEventHandler(instagram, "https://instagram.com");

    // insert components
    snsLinkPane.getChildren().addAll(linkedIn, facebook, instagram);

    return snsLinkPane;
  }

  /**
   * open a browser when URL is given
   * 
   * @param url the url to be opened with the associated application
   */
  private static void openURL(String url) {
    try {
      if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
        Desktop.getDesktop().browse(new URI(url));
      }
    } catch (URISyntaxException e) {
    } catch (IOException e) {
    }
  }

  /**
   * when link is clicked direct the user to the web accordingly
   * @param link link that user clicked
   * @param url url of the link
   */
  private void addLinkEventHandler(ImageView link, String url) {
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
  
  /**
   * set up all the GUI for the program
   * 
   * @param primaryStage stage that all components will be set
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // layout info
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
     *    -> linkPane
     *        -> load
     *        -> save
     *        -> add user
     */
    
    // create instances for each pane
    canvas = createCanvas();
    canvasPane = createCanvasPane(canvas);
    centerUserPane = createCenterUserPane();
    userFindPane = createUserFindPane();
    console = createConsole();
    linkPane = createLinkPane();
    friendPane = createFriendPane();
    updateScene(primaryStage);

    // before close, ask to save or no
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent arg0) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("save");
        alert.setHeaderText("Do you want to exit the Social Network?");
        alert.setContentText("save or discard any changes?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
          String path = "";
          TextInputDialog popup = new TextInputDialog("path...");
          popup.setHeaderText("write down path for save");
          Optional<String> result2 = popup.showAndWait();
          if (result.isPresent()) {
            path = result2.get();
          }
          FileControl.setProfile_manager(pm);
          try {
            FileControl.writeLog(path);
          } catch (Exception e) {

          }
          try {
            setCenterUser(canvas);
            setCenterUserPane(centerUserPane);
          } catch (FileNotFoundException f) {
          }
        }
      }
    });
  }

  /**
   * launce the program
   * @param args n/a
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * set scenes before launching the program
   * @param primaryStage stage where all components will be shown
   */
  private void updateScene(Stage primaryStage) {
    
    // instanciate root, set attributes, and control layout for each pane
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


/**
 * class that will hold data about profile GUI and profile
 * @author jang
 *
 */
class ProfileGUI extends ImageView {
  
  // fields declared here
  private Profile profile;
  public double positionX;
  public double positionY;

  /**
   * constructor for profileGUI. It sets eventHandler for imageView
   * @param profile
   * @throws FileNotFoundException
   */
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

  /**
   * get profile of the instance
   * @return
   */
  public Profile getProfile() {
    return profile;
  }

  /**
   * set image size of the image 
   * @param height height of the image
   * @throws FileNotFoundException Constructs a FileNotFoundException with null as its error detail
   *                               message. 
   */
  public void setImageSize(double height) throws FileNotFoundException {
    super.setFitHeight(height);
    super.setPreserveRatio(true);
  }
}

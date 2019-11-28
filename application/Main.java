package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
  double origCanvasTransX;
  double origCanvasTransY;

  
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
    
    return canvas;
  }
  
  private void setCenterUser(Pane canvas, Profile centerUser) throws FileNotFoundException{
    // set size of a canvas depending on the number of friend
    canvas.setPrefSize(500, 500);
    ImageView friendlyBucky = new ImageView(new Image(new FileInputStream("application/friendlyBucky.png")));
    friendlyBucky.setFitHeight(64);
    friendlyBucky.setPreserveRatio(true);
    friendlyBucky.setLayoutX(250);
    friendlyBucky.setLayoutY(250);
    // create instance for each friend and add to the canvas in a location
    
    
    canvas.getChildren().add(friendlyBucky);
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  
  private Pane createCenterUserPane(Image centerUserImage, String userName, String userBio) throws FileNotFoundException {
    
    Pane centerUserPane = new Pane();
    GridPane.setMargin(centerUserPane, new Insets(10, 10, 10, 10));
    centerUserPane.setBorder(new Border(new BorderStroke(Color.WHITE, 
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    centerUserPane.setPrefSize(280, 180);
    ImageView centerUserImageView = new ImageView(centerUserImage);
    centerUserImageView.setFitWidth(100);
    centerUserImageView.setPreserveRatio(true);
    centerUserImageView.setLayoutX(10);
    centerUserImageView.setLayoutY(10);
    
    Label userNameLabel = new Label(userName);
    userNameLabel.setLayoutX(150);
    userNameLabel.setLayoutY(10);
    
    Text userBioText = new Text(userBio);
    userBioText.setLayoutX(150);
    userBioText.setLayoutY(50);
    
    HBox links = createSnsLinkPane();
    links.setLayoutX(100);
    links.setLayoutY(130);
    
    centerUserPane.getChildren().addAll(centerUserImageView, userNameLabel, userBioText, links);
    return centerUserPane;
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
    findButton.setPrefSize(50,30);
    findButton.setLayoutX(175);
    
    // event handler
    findButton.setOnAction(e -> {
      findUser(userNameTextField.getText());
//      setFriendPane();
      });
    
    
    userFindPane.getChildren().addAll(userNameTextField, findButton);
    
    return userFindPane;
  }
  
  private Profile findUser(String userNanme) {
    return null;
  }

////////////////////////////////////////////////////////////////////////////////////////////////////
  private Pane createConosole() {
    Pane console = new Pane();
    GridPane.setMargin(console, new Insets(10, 10, 10, 10));
    console.setBorder(new Border(new BorderStroke(Color.WHITE, 
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    console.setPrefSize(280, 80);
    return console;
  }
  
  private void setConsole(TextField userNameTextField) {
    String userName = userNameTextField.getText();
    
    // back end need to be done
    /*
     * find -> textfield -> string -> string compare -> is user here? -> ProfileManager.method List -> Profile.getName -> Systemout.println
     *                                                           -> error -> user is not found in our data
     */
    
  }
  
////////////////////////////////////////////////////////////////////////////////////////////////////
  private Pane createFriendPane(Profile centerUser, Profile friend) throws FileNotFoundException {
    Pane friendPane = new Pane();
    GridPane.setMargin(friendPane, new Insets(0, 10, 0, 10));
    friendPane.setBorder(new Border(new BorderStroke(Color.WHITE, 
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3))));
    friendPane.setPrefSize(280, 130);
    
    ImageView friendImage =
        new ImageView(new Image(new FileInputStream("application/bucky.png")));
    friendImage.setFitWidth(50);
    friendImage.setPreserveRatio(true);
    friendImage.setLayoutX(10);
    friendImage.setLayoutY(10);
    
    Button addButton = new Button("ADD");
    addButton.setPrefWidth(50);
    addButton.setLayoutX(10);
    addButton.setLayoutY(80);
    
    HBox links = createSnsLinkPane();
    links.setLayoutX(60);
    links.setLayoutY(10);
    
    friendPane.getChildren().addAll(friendImage, addButton, links);
    return friendPane;
  }
////////////////////////////////////////////////////////////////////////////////////////////////////
  /*
   * Misc methods
   */
  
  private HBox createSnsLinkPane() throws FileNotFoundException {
    HBox snsLinkPane = new HBox();
    
    // linkedin, fb, insta, youtube
    snsLinkPane.setSpacing(5);
    ImageView linkedIn =
        new ImageView(new Image(new FileInputStream("application/linkedIn.png"), 30, 30, true, true));
    ImageView facebook =
        new ImageView(new Image(new FileInputStream("application/facebook.png"), 30, 30, true, true));
    ImageView instagram =
        new ImageView(new Image(new FileInputStream("application/instagram.png"), 30, 30, true, true));
    ImageView youtube =
        new ImageView(new Image(new FileInputStream("application/youtube.png"), 30, 30, true, true));
    
    snsLinkPane.getChildren().addAll(linkedIn, facebook, instagram, youtube);
    
    return snsLinkPane;
  }
  
  private Pane createLinkPane() throws FileNotFoundException{
    Pane linkPane = new Pane();
    GridPane.setMargin(linkPane, new Insets(5,0,5,0));
    linkPane.setPrefSize(280, 30);
    ImageView gitHub =
        new ImageView(new Image(new FileInputStream("application/GitHub.png"), 30, 30, true, true));
    gitHub.setLayoutX(260);
    linkPane.getChildren().add(gitHub);
    return linkPane;
  }
  
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
    
    
    GridPane root = new GridPane();
    root.setPrefSize(800, 500);
    root.setBackground(
        new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
    Pane canvas = createCanvas();
    Pane canvasPane = createCanvasPane(canvas);
    Pane centerUserPane = createCenterUserPane(new Image(new FileInputStream("application/bucky.png")), "john", "john is my friend");
    Pane userFindPane = createUserFindPane();
    Pane console = createConosole();
    Pane linkPane = createLinkPane();
    
    // create test profile
    Profile test = new Profile("john");
    test.testInit();
    Profile testFriend = new Profile("mark");
    testFriend.testInit();

    Pane friendPane = createFriendPane(test, testFriend);
    
    root.add(canvasPane, 0, 0, 1, 5);
    root.add(centerUserPane, 1, 0, 1, 1);
    root.add(userFindPane, 1, 1, 1, 1);
    root.add(console, 1, 2, 1, 1);
    root.add(friendPane, 1, 3, 1, 1);
    root.add(linkPane, 1, 4, 1, 1);
    
    setCenterUser(canvas, test);
    
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



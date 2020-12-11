package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 */

public class Main extends Application {
    private static Stage primaryStage;
    static int viewWidth = 1250;
    static int viewHeight = 800;
    public static final Model model = new Model(16, 25);
    public static final InteractionModel iModel = new InteractionModel();
    public static final View view = new View();
    public static final MainMenuView menuView = new MainMenuView();
    public static final GameController GAME_CONTROLLER = new GameController();
    public static final SplashScreenView splashView = new SplashScreenView();
    public static final MainMenuController menuController = new MainMenuController();
    public static Scene gameScene;
    public static Scene mainMenu;
    public static Scene splashScreen;


    @Override
    public void start(Stage primaryStage) {
        try {
            this.primaryStage = primaryStage;
            gameScene = new Scene(view, viewWidth, viewHeight);
            mainMenu = new Scene(menuView);
            splashScreen = new Scene(splashView);
            //mainMenu.getStylesheets().add(app/stylesheet.css)
            primaryStage.setScene(splashScreen);
            primaryStage.setResizable(false);
            view.requestFocus();
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setScene(Scene scene){
        primaryStage.setScene(scene);
    }
}

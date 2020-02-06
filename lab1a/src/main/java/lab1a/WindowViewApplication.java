package lab1a;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowViewApplication extends Application {


    ScrollBar sc = new ScrollBar();

    final Image[] images = new Image[5];
    final ImageView[] pics = new ImageView[5];
    final VBox vb = new VBox();
    DropShadow shadow = new DropShadow();
    private int scrollValue;

    void increaseScrollValue() { sc.increment(); }
    void decreaseScrollValue() { sc.decrement(); }

    @Override
    public void start(Stage stage) {
        /*Group root = new Group();
        Scene scene = new Scene(root, 300, 300);
        scene.setFill(Color.BLACK);
        stage.setScene(scene);
        stage.setTitle("Scrollbar");
        root.getChildren().addAll(vb, sc);

        shadow.setColor(Color.GREY);
        shadow.setOffsetX(2);
        shadow.setOffsetY(2);

        vb.setLayoutX(5);
        vb.setSpacing(10);

        sc.setMinWidth(scene.getWidth());
        sc.setMinHeight(scene.getHeight());

        sc.setMin(0);
        sc.setMax(100);
        scrollValue = 50;
        sc.setUnitIncrement(1);
        */
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /*public static void main(String[] args) {
        launch(args);
    }*/
}

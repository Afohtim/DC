package org.afohtim.lab6a;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Vector;

public class MainWindow extends Application {

    private final int SIZE = 40;
    private Vector<Vector<SquareControl>> rectEvents;
    private GameLife gameLife;

    GridPane createGrid(int size, Vector<Vector<SquareControl>> squareControls) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < size; ++i)
        {
            for (int j = 0;j < size; ++j) {
                gridPane.add(squareControls.get(i).get(j).getSquare(), i, j);
            }
        }
        gridPane.setLayoutX(50);
        gridPane.setLayoutY(50);
        return gridPane;
    }

    @Override
    public void start(Stage stage) {

        rectEvents = new Vector<>();
        for(int i = 0; i < SIZE; ++i) {
            rectEvents.add(new Vector<>());
            for(int j = 0; j < SIZE; ++j) {
                rectEvents.lastElement().add(new SquareControl(15));
            }
        }


        gameLife = new GameLife(rectEvents);

        GridPane gridPane = createGrid(SIZE, rectEvents);

        Button startButton = JavaFXButtonFactory.createButton("Start", 680, 50, 20, 80);
        Button stopButton = JavaFXButtonFactory.createButton("Stop", 680, 80, 20, 80);


        startButton.setOnAction(event -> {
            gameLife.start();
            startButton.setDisable(true);
            stopButton.setDisable(false);
        });
        stopButton.setOnAction(event -> {
            gameLife.stop();
            startButton.setDisable(false);
            stopButton.setDisable(true);
        });


        Button hangarButton = JavaFXButtonFactory.createButton("Hangar", 680, 110, 20, 80,
                () -> {
                if (startButton.isDisabled())
                    gameLife.stop();
                startButton.setDisable(false);
                stopButton.setDisable(true);
                for (int i = 0;i < SIZE; ++i)
                    for (int j = 0;j < SIZE; ++j)
                        if (i%3 != 0 && j%3 != 0)
                            rectEvents.get(i).get(j).setAlive(true);
                        else
                            rectEvents.get(i).get(j).setAlive(false);
        });


        Button clearButton = JavaFXButtonFactory.createButton("Clear", 680, 140, 20, 80,
                () -> {
                if (startButton.isDisabled())
                    gameLife.stop();
                startButton.setDisable(false);
                stopButton.setDisable(true);
                for (int i = 0;i < SIZE; ++i)
                    for (int j = 0;j < SIZE; ++j)

                        rectEvents.get(i).get(j).setAlive(false);
        });

        Group group = new Group();
        group.getChildren().add(gridPane);
        group.getChildren().add(startButton);
        group.getChildren().add(hangarButton);
        group.getChildren().add(clearButton);
        group.getChildren().add(stopButton);
        Scene scene = new Scene(group, 770, 700);
        scene.setFill(Color.GRAY);
        stage.setScene(scene);

        stage.setTitle("Task1 a");

        stage.show();
    }
}

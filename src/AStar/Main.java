package AStar;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import javafx.event.ActionEvent.*;

import java.awt.*;

public class Main extends Application implements EventHandler {
    Scene mainScene;
    Group mainGroup;
    double side = 50;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("A* PathFinding");
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(1000);
        Label hello = new Label("what up");
        mainGroup = new Group();
        Canvas canvas = new Canvas(500,800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        boolean[][] grid = new boolean[18][14];
        mainGroup.getChildren().add(canvas);
        mainScene = new Scene(mainGroup);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Override
    public void handle(Event event) {
    }

    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.setLineWidth(5);
        for(double i = 0; i < 900; i += side) {
            for (double j = 0; j < 700; j += side) {
                gc.fillRect(i, j, side, side);
            }
        }
    }

}

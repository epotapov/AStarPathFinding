package AStar;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
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
        mainGroup.getChildren().add(canvas);
        mainScene = new Scene(mainGroup);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void drawShapes(GraphicsContext gc) {
        /*gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(5);
        gc.strokeLine(40, 10, 10, 40);
        gc.fillOval(10, 60, 30, 30);
        gc.strokeOval(60, 60, 30, 30);
        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
        gc.fillPolygon(new double[]{10, 40, 10, 40},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolygon(new double[]{60, 90, 60, 90},
                new double[]{210, 210, 240, 240}, 4);
        gc.strokePolyline(new double[]{110, 140, 110, 140},
                new double[]{210, 210, 240, 240}, 4);*/
        gc.setFill(Color.GRAY);
        gc.setLineWidth(5);
        for(double i = 0; i < 50; i += side) {
            for (double j = 0; j < 100; j += side) {
                gc.fillRect(i, j, side, side);
            }
        }
    }
}

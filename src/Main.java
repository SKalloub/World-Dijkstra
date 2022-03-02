import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends Application {
    HashMap<String,Circle> circles = new HashMap<>();
    ArrayList<Line> lines  = new ArrayList<>();
    static String[] colors = {"SADDLEBROWN","ROYALBLUE","ORANGE","GREEN","NAVY"};
    static  int countcolors = 0;
    static int turn = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Graph graph = new Graph();
        ReadData(graph);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(1280);
        anchorPane.setPrefHeight(720);
        anchorPane.setStyle("-fx-background-color:White;");
        anchorPane.getChildren().add(MapPane.getPane());
        ComboBoxCustom comboBoxCustom = new ComboBoxCustom("Travel From");
        comboBoxCustom.set(941,217);


        ImageView logo = new ImageView(new Image(new FileInputStream("src/Images/icon.png")));
        logo.setFitWidth(200);
        logo.setFitHeight(200);
        logo.setLayoutY(1);
        logo.setLayoutX(997);

        anchorPane.getChildren().add(logo);

        ComboBoxCustom comboBoxCustom2 = new ComboBoxCustom("Travel To");

        comboBoxCustom2.set(941,282);




        for(Vertex v: graph.Vertices.values()) {
        comboBoxCustom.AddCountry(v.getCountry().getCountryName());
        comboBoxCustom2.AddCountry(v.getCountry().getCountryName());
        }
        JFXButton calculate = new JFXButton("Travel");

        TextArea resultsPane = new TextArea();
        resultsPane.setPrefWidth(366);
        resultsPane.setPrefHeight(237);
        resultsPane.setLayoutX(915);
        resultsPane.setLayoutY(485);

        resultsPane.setStyle("-fx-border-color: #7B241C;-fx-border-width: 5 0 0 0");
        anchorPane.getChildren().add(resultsPane);
        calculate.setLayoutX(1034);
        calculate.setLayoutY(355);
        calculate.setPrefWidth(126);
        calculate.setPrefHeight(60);
        calculate.setStyle("-fx-background-color: #7B241C;");
        calculate.setTextFill(Color.WHITE);
        calculate.setFont(Font.font(20));
        calculate.setRipplerFill(Paint.valueOf("Blue"));
        calculate.setOnAction(event -> {
            resultsPane.clear();
            removeLines(MapPane.group);

            if (comboBoxCustom.comboBox.getValue()!=null && !comboBoxCustom.comboBox.getValue().isEmpty() && comboBoxCustom2.comboBox.getValue()!=null && !comboBoxCustom2.comboBox.getValue().isEmpty()) {
                graph.findShortestPath(comboBoxCustom.comboBox.getValue(),comboBoxCustom2.comboBox.getValue());
                graph.fill(resultsPane,comboBoxCustom2.comboBox.getValue());
                drawLines(MapPane.group,graph.Vertices.get(comboBoxCustom2.comboBox.getValue()));
            }
        });
        anchorPane.getStylesheets().add("st.css");
        anchorPane.getChildren().addAll(comboBoxCustom.comboBox, comboBoxCustom2.comboBox,calculate);
        insertCircles(graph,MapPane.group, comboBoxCustom.comboBox, comboBoxCustom2.comboBox);

        JFXDecorator jfxDecorator = new JFXDecorator(primaryStage, anchorPane);
        jfxDecorator.setCustomMaximize(true);
        jfxDecorator.getStylesheets().add("st.css");
        primaryStage.setScene(new Scene(jfxDecorator));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(new FileInputStream("src/Images/icon.png")));
        primaryStage.show();
    }

    private void removeLines(Group group) {
        countcolors = 0;
            for (Line line: lines)
        group.getChildren().remove(line);
    }
    private void drawLines(Group group, Vertex vertex) {
        if (vertex.getPrev()==null)
            return;
        drawLines(group,vertex.getPrev());
        Line line = new Line(vertex.getPrev().getCountry().getX(),vertex.getPrev().getCountry().getY(),vertex.getCountry().getX(),vertex.getCountry().getY());
        line.setStrokeWidth(4);
        line.setStroke(Paint.valueOf(colors[countcolors++%6]));
        group.getChildren().add(line);
        lines.add(line);
    }
    private void insertCircles(Graph graph, Group group, ComboBox <String>comboBox1, ComboBox <String>comboBox2) {
        Circle circle = null;
        for (Vertex v: graph.Vertices.values()) {
            circle = new Circle(v.getCountry().getX(),v.getCountry().getY(),3);
            circle.setFill(Color.ROYALBLUE);

            group.getChildren().add(circle);
            circles.put(v.getCountry().getCountryName(),circle);

            circle.setOnMouseClicked(event -> {
                if (comboBox1.getValue()==null || comboBox1.getValue().isEmpty())
                {
                    comboBox1.setValue(v.getCountry().getCountryName());
                }
                else if (comboBox2.getValue()==null || comboBox2.getValue().isEmpty()) {
                    comboBox2.setValue(v.getCountry().getCountryName());
                }
                if (turn == 0) {
                    comboBox1.setValue(v.getCountry().getCountryName());
                    turn = 1;
                }
                else if (turn ==1 ) {
                    comboBox2.setValue(v.getCountry().getCountryName());
                    turn = 0;
                }
            });


        }
    }

    private void ReadData(Graph graph) throws FileNotFoundException {
        File file = new File("src/files/Countries.txt");
        File file2 = new File("src/files/longlat.txt");
        Scanner scanner2 = new Scanner(file2);
        Scanner scanner = new Scanner(file);
        Scanner scanner1 = new Scanner(new File("src/files/xs.txt"));
        while (scanner.hasNextLine()) {
            String count = scanner.nextLine().trim();
            Country country = new Country(count);

            Vertex vertex = new Vertex(country);
            String []longlat = scanner2.nextLine().trim().split(",");
            double latitude = Double.parseDouble(longlat[0]);
            double longitude = Double.parseDouble(longlat[1]);
            if (latitude<0)
                latitude = 360+latitude;

            if (longitude<0)
                longitude = 360+longitude;
            country.setLatitude(latitude);
            country.setLongitude(longitude);
            String line = scanner1.nextLine();
            String[] tokens = line.split(",");
            country.setX(Double.parseDouble(tokens[0]));
            country.setY(Double.parseDouble(tokens[1]));
            graph.insertVertex(vertex);
        }

        file = new File("src/files/Edges.txt");
        scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String Line = scanner.nextLine();
            String tokens[] = Line.split("-");
            graph.addAdjacent(tokens[0],tokens[1],findDistance(graph,tokens[0],tokens[1]));
        }
    }

    private double findDistance(Graph graph, String token, String token1) {

        Country c1 = graph.Vertices.get(token).getCountry();
        Country c2 = graph.Vertices.get(token1).getCountry();
        return distancefinder.fromLongAndLat(c1.getLongitude(),c1.getLatitude(),c2.getLongitude(),c2.getLatitude());
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

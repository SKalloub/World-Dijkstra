import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MapPane {
public static Group group = new Group();
    public static ScrollPane getPane() throws FileNotFoundException {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(916);
        scrollPane.setPrefHeight(720);
        Image image = new Image(new FileInputStream("src/Images/WorldMap.png"));
        ImageView imageView = new ImageView(new Image(new FileInputStream("src/Images/WorldMap.png")));
        imageView.setFitWidth(1085.2);
        imageView.setFitHeight(751.6);


         group = new Group(imageView);
        group.setOnScroll(
                e -> {
                    if (e.isShortcutDown() && e.getDeltaY() != 0) {

                            if (e.getDeltaY() < 0) {
                                group.setScaleX(Math.max(group.getScaleX() - 0.1, 0.4));
                            } else {
                                group.setScaleX(Math.min(group.getScaleX() + 0.1, 5.0));
                            }
                        group.setScaleY(group.getScaleX());

                            scrollPane.setContent(group);
                        e.consume(); // prevents ScrollEvent from reaching ScrollPane

                    }
                });

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setContent(group);
        scrollPane.setStyle("-fx-border-width: 3 3 0 0;-fx-border-color: #7B241C;");
        return scrollPane;
    }




}

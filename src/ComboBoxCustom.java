import com.jfoenix.controls.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ComboBoxCustom {
ComboBox<String> comboBox = new ComboBox<>();

public ComboBoxCustom(String prompetText) {
    comboBox.setPromptText(prompetText);
    comboBox.setPrefWidth(313);
    comboBox.setPrefHeight(44);
    comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
        @Override public ListCell<String> call(ListView<String> p) {
            return new ListCell<String>() {
                private final HBox hBox;
                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    hBox = new HBox();
                    hBox.setPrefWidth(207);
                    hBox.setPrefHeight(35);
                    hBox.setSpacing(10);
                    hBox.setPadding(new Insets(0,0,0,8));
                }

                @Override protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setGraphic(null);
                    } else {
                        try {
                            File file = new File("src/Images/icons/"+item+".png");
                                    if (!file.exists())
                                        file = new File("src/Images/icons/noflag.png");
                            ImageView image = new ImageView(new Image(new FileInputStream(file)));
                            image.setFitHeight(33);
                            image.setFitWidth(33);
                            Label st = new Label(item);
                            st.setPadding(new Insets(5,0,0,0));
                            st.setFont(Font.font("System", FontWeight.BOLD,16));
                            hBox.getChildren().clear();
                            hBox.getChildren().addAll(image,st);
                            setGraphic(hBox);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            };
        }
    });
}
public void AddCountry(String country) {
    comboBox.getItems().add(country);
}
public void set(double x, double y){
    comboBox.setLayoutX(x);
    comboBox.setLayoutY(y);
}
}

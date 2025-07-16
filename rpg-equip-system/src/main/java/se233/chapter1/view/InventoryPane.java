package se233.chapter1.view;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import se233.chapter1.Launcher;
import se233.chapter1.model.item.BasedEquipment;

import java.util.ArrayList;

import static se233.chapter1.controller.AllCustomHandler.onDragDetected;

public class InventoryPane extends ScrollPane {
    private ArrayList<BasedEquipment> equipmentArray;

    public InventoryPane() {}

    private Pane getDetailPane() {
        Pane inventoryInfoPane = new HBox(10);
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(25, 25, 25, 25));

        if (equipmentArray != null) {
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];

            for (int i = 0; i < equipmentArray.size(); i++) {
                imageViewList[i] = new ImageView();
                imageViewList[i].setImage(
                        new Image(Launcher.class.getResource(equipmentArray.get(i).getImgpath()).toString())
                );

                int finalI = i;
                BasedEquipment equipment = equipmentArray.get(finalI);

                // Start dragging
                imageViewList[i].setOnDragDetected((MouseEvent event) -> {
                    onDragDetected(event, equipment, imageViewList[finalI]);
                });

                // Handle drop done (success/fail)
                imageViewList[i].setOnDragDone((DragEvent event) -> {
                    ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();



                    Launcher.setAllEquipments(allEquipments);
                    Launcher.refreshPane();
                });
            }

            inventoryInfoPane.getChildren().addAll(imageViewList);
        }

        return inventoryInfoPane;
    }

    public void drawPane(ArrayList<BasedEquipment> equipmentArray) {
        this.equipmentArray = equipmentArray;
        Pane inventoryInfo = getDetailPane();
        this.setStyle("-fx-background-color: #4D2691;");
        this.setContent(inventoryInfo);
    }
}

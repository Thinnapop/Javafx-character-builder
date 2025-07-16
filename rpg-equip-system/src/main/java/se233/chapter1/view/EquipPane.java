package se233.chapter1.view;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

import static se233.chapter1.controller.AllCustomHandler.*;

public class EquipPane extends ScrollPane {
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    private Pane getDetailsPane() {
        VBox equipmentInfoPane = new VBox(10);
        equipmentInfoPane.setAlignment(Pos.CENTER);
        equipmentInfoPane.setPadding(new Insets(25));

        // === WEAPON SLOT ===
        Label weaponLbl = new Label("Weapon:");
        StackPane weaponImgGroup = new StackPane();
        ImageView weaponBg = new ImageView(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        weaponImgGroup.getChildren().add(weaponBg);

        if (equippedWeapon != null) {
            weaponLbl.setText("Weapon:\n" + equippedWeapon.getName());
            ImageView weaponImg = new ImageView(new Image(Launcher.class.getResource(equippedWeapon.getImgpath()).toString()));

            // Enable drag for equipped weapon
            weaponImg.setOnDragDetected((MouseEvent event) -> {
                onDragDetected(event, equippedWeapon, weaponImg);
            });
            weaponImg.setOnDragDone((DragEvent event) -> {
                onEquipDragAway(event, "Weapon");
            });

            weaponImgGroup.getChildren().add(weaponImg);
        }

        weaponImgGroup.setOnDragOver(e -> onDragOver(e, "Weapon"));
        weaponImgGroup.setOnDragDropped(e -> onDragDropped(e, weaponLbl, weaponImgGroup));

        // === ARMOR SLOT ===
        Label armorLbl = new Label("Armor:");
        StackPane armorImgGroup = new StackPane();
        ImageView armorBg = new ImageView(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        armorImgGroup.getChildren().add(armorBg);

        if (equippedArmor != null) {
            armorLbl.setText("Armor:\n" + equippedArmor.getName());
            ImageView armorImg = new ImageView(new Image(Launcher.class.getResource(equippedArmor.getImgpath()).toString()));

            // Enable drag for equipped armor
            armorImg.setOnDragDetected((MouseEvent event) -> {
                onDragDetected(event, equippedArmor, armorImg);
            });
            armorImg.setOnDragDone((DragEvent event) -> {
                onEquipDragAway(event, "Armor");
            });

            armorImgGroup.getChildren().add(armorImg);
        }

        Button unequipBtn = new Button();
        unequipBtn.setText("Unequip");
        unequipBtn.setOnAction((ActionEvent event) -> {
            ArrayList<BasedEquipment> inventory = Launcher.getAllEquipments();
            BasedCharacter character = Launcher.getMainCharacter();
            if (Launcher.getEquippedWeapon() != null) {
                inventory.add(equippedWeapon);
                character.unequipWeapon();
                Launcher.setEquippedWeapon(null);

            }
            if (Launcher.getEquippedArmor() != null) {
                inventory.add(equippedArmor);
                character.unequipArmor();
                Launcher.setEquippedArmor(null);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(inventory);
            Launcher.refreshPane();
        });

        armorImgGroup.setOnDragOver(e -> onDragOver(e, "Armor"));
        armorImgGroup.setOnDragDropped(e -> onDragDropped(e, armorLbl, armorImgGroup));

        equipmentInfoPane.getChildren().addAll(weaponLbl, weaponImgGroup, armorLbl, armorImgGroup,unequipBtn);
        return equipmentInfoPane;

    }

    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipmentInfo = getDetailsPane();
        this.setStyle("-fx-background-color: #4D2691;");
        this.setContent(equipmentInfo);
    }
}
package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;
import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;

import java.util.ArrayList;

public class AllCustomHandler {

    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.setEquippedWeapon(null);
            Launcher.setEquippedArmor(null);
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(equipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard dragboard = event.getDragboard();
        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment equipment = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            if (equipment.getClass().getSimpleName().equals(type)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard dragboard = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();

        if (dragboard.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment newEquip = (BasedEquipment) dragboard.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();

            if (newEquip instanceof Weapon) {
                Weapon newWeapon = (Weapon) newEquip;
                boolean canEquip = character instanceof se233.chapter1.model.character.BattleMageCharacter
                        || character.getType() == newWeapon.getDamageType();

                if (!canEquip) {
                    // Return to inventory if type mismatched
                    //allEquipments.add(newWeapon);
                    Launcher.setAllEquipments(allEquipments);
                    Launcher.refreshPane();
                    event.setDropCompleted(false);
                    return;
                }

                // Unequip current and return to inventory
                Weapon oldWeapon = Launcher.getEquippedWeapon();
                if (oldWeapon != null) {
                    allEquipments.add(oldWeapon);
                }

                Launcher.setEquippedWeapon(newWeapon);
                character.equipWeapon(newWeapon);

            } else if (newEquip instanceof Armor) {
                if (character instanceof se233.chapter1.model.character.BattleMageCharacter) {
                    //allEquipments.add(newEquip);
                    Launcher.setAllEquipments(allEquipments);
                    Launcher.refreshPane();
                    event.setDropCompleted(false);
                    return;
                }

                // Unequip current and return to inventory
                Armor oldArmor = Launcher.getEquippedArmor();
                if (oldArmor != null) {
                    allEquipments.add(oldArmor);
                }

                Launcher.setEquippedArmor((Armor) newEquip);
                character.equipArmor((Armor) newEquip);
            }

            // ✅ Remove from inventory and equip
            allEquipments.removeIf(e -> e.getName().equals(newEquip.getName()));
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();

            // Update equip image
            if (imgGroup.getChildren().size() > 1) {
                imgGroup.getChildren().remove(1);
            }
            ImageView imgView = new ImageView();
            imgView.setImage(new Image(Launcher.class.getResource(newEquip.getImgpath()).toString()));
            imgGroup.getChildren().add(imgView);
            lbl.setText(newEquip.getClass().getSimpleName() + ":\n" + newEquip.getName());

            dragCompleted = true;
        }

        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDragAway(DragEvent event, String type) {
        if (event.isDropCompleted()) {
            if (type.equals("Weapon")) {
                Launcher.setEquippedWeapon(null);
            } else if (type.equals("Armor")) {
                Launcher.setEquippedArmor(null);
            }
            Launcher.refreshPane();
        }
    }

    // COMPLETELY REMOVE the duplicate removal logic
    public static void onEquipDone(DragEvent event) {
        // Only refresh the pane, don't modify inventory
        // The inventory modification is handled in onDragDropped
        Launcher.refreshPane();
    }
}
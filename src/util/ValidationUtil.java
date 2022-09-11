package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.regex.Pattern;

public class ValidationUtil {
    public static Object validate(HashMap<TextField,Pattern>map, Button btn){
        for (TextField key:map.keySet()) {
            Pattern pattern = map.get(key);
            if (!pattern.matcher(key.getText()).matches()){
                addError(key,btn);
                return key;
            }
            removeError(key,btn);
        }
        return true;
    }

    public static void removeError(TextField txtField,Button btn) {
        txtField.setStyle("-fx-border-color: green");
        btn.setDisable(false);
    }

    public static void addError(TextField txtField,Button btn) {
        txtField.setStyle("-fx-border-color: red");
        btn.setDisable(true);
    }
}

package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.AdditionalCost;
import model.Buyer;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class AdditionalCostFormController {

    public AnchorPane additionalCostContext;
    public JFXTextField txtSearch;
    public TextField txtDescription;
    public TextField txtCost;
    public TextField txtDate;
    public TableView tblAdditionalCost;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colCost;
    public TableColumn colId;
    public TextField txtId;
    public Button btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        loadDateAndTime();

        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colDate.setCellValueFactory(new PropertyValueFactory("date"));
        colCost.setCellValueFactory(new PropertyValueFactory("cost"));

        try {
            loadAllAddtionalCost();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(A-)[0-9]{3,5}$");
        Pattern descPattern = Pattern.compile("^[A-z0-9]{1,45}$");
        Pattern costPattern = Pattern.compile("^[1-9][0-9 .]+$");

        map.put(txtId,idPattern);
        map.put(txtDescription,descPattern);
        map.put(txtCost,costPattern);
    }

    private void loadAllAddtionalCost() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.additionalcost");

        ObservableList<AdditionalCost> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new AdditionalCost(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getDouble(4)
                    )
            );
        }
        tblAdditionalCost.setItems(obList);
    }

    private void loadDateAndTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            txtDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        AdditionalCost ad = new AdditionalCost(
                txtId.getText(),txtDescription.getText(),txtDate.getText(),
                Double.parseDouble(txtCost.getText())
        );

        try {
            if (CrudUtil.execute("INSERT INTO pujithapalletsuppliers.additionalcost VALUES (?,?,?,?)",ad.getId(),ad.getDescription(),ad.getDate(),ad.getCost())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        additionalCostContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AdditionalCostForm.fxml"));
        additionalCostContext.getChildren().add(parent);

        txtId.clear();
        txtDescription.clear();
        txtCost.clear();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        AdditionalCost ad = new AdditionalCost(
                txtId.getText(),txtDescription.getText(),txtDate.getText(),
                Double.parseDouble(txtCost.getText())
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE pujithapalletsuppliers.additionalcost SET description= ? , date=? , cost=? WHERE id=?",ad.getDescription(),ad.getDate(),ad.getCost(),ad.getId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        additionalCostContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AdditionalCostForm.fxml"));
        additionalCostContext.getChildren().add(parent);

        txtId.clear();
        txtDescription.clear();
        txtCost.clear();
        txtSearch.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (CrudUtil.execute("DELETE FROM pujithapalletsuppliers.additionalcost WHERE id=?",txtId.getText())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        additionalCostContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AdditionalCostForm.fxml"));
        additionalCostContext.getChildren().add(parent);

        txtSearch.clear();
    }

    private void search() throws ClassNotFoundException ,SQLException{

        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.additionalcost WHERE id=?",txtSearch.getText());

        if (result.next()){
            txtId.setText(result.getString(1));
            txtDescription.setText(result.getString(2));
            txtDate.setText(result.getString(3));
            txtCost.setText(String.valueOf(result.getDouble(4)));
        }else{
            new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
        }
        //txtSearch.clear();
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);
        //validate();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = ValidationUtil.validate(map,btnSave);
            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {

            }

        }
    }
}

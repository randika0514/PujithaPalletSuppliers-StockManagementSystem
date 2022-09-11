package controller;

import com.jfoenix.controls.JFXTextField;
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
import model.Buyer;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class BuyerFormController{
    public AnchorPane buyersContext;
    public TextField txtBuyerId;
    public TextField txtBuyerName;
    public TextField txtBuyerNic;
    public TextField txtBuyerAddress;
    public TextField txtBuyerContact;
    public TextField txtBuyerEmail;
    public TableView tblBuyer;
    public TableColumn colBuyerId;
    public TableColumn colBuyerName;
    public TableColumn colBuyerNic;
    public TableColumn colBuyerAddress;
    public TableColumn colBuyerContact;
    public JFXTextField txtSearch;
    public TableColumn colBuyerEmail;
    public Button btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){


        colBuyerId.setCellValueFactory(new PropertyValueFactory("BuyerId"));
        colBuyerName.setCellValueFactory(new PropertyValueFactory("BuyerName"));
        colBuyerNic.setCellValueFactory(new PropertyValueFactory("BuyerNic"));
        colBuyerAddress.setCellValueFactory(new PropertyValueFactory("BuyerAddress"));
        colBuyerContact.setCellValueFactory(new PropertyValueFactory("BuyerContact"));
        colBuyerEmail.setCellValueFactory(new PropertyValueFactory("BuyerEmail"));


        try {
            loadAllBuyers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(B-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,45}$");
        Pattern nicPattern = Pattern.compile("^[0-9V]{10,12}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,}$");
        Pattern emailPattern = Pattern.compile("^[A-z0-9@./]{5,}$");

        map.put(txtBuyerId,idPattern);
        map.put(txtBuyerName,namePattern);
        map.put(txtBuyerNic,nicPattern);
        map.put(txtBuyerAddress,addressPattern);
        map.put(txtBuyerContact,contactPattern);
        map.put(txtBuyerEmail,emailPattern);
    }

    private void loadAllBuyers() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.buyer");

        ObservableList<Buyer> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Buyer(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getString(5),
                            result.getString(6)
                    )
            );
        }
        tblBuyer.setItems(obList);
    }


    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {

        Buyer b = new Buyer(
                txtBuyerId.getText(),txtBuyerName.getText(),txtBuyerNic.getText(),
                txtBuyerAddress.getText(),txtBuyerContact.getText(),txtBuyerEmail.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO pujithapalletsuppliers.buyer VALUES (?,?,?,?,?,?)",b.getBuyerId(),b.getBuyerName(),b.getBuyerNic(),b.getBuyerAddress(),b.getBuyerContact(),b.getBuyerEmail())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        buyersContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerForm.fxml"));
        buyersContext.getChildren().add(parent);

        txtBuyerName.clear();
        txtBuyerId.clear();
        txtBuyerNic.clear();
        txtBuyerAddress.clear();
        txtBuyerContact.clear();
        txtBuyerEmail.clear();


    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {

        Buyer b =new Buyer(
                txtBuyerId.getText(),txtBuyerName.getText(),txtBuyerNic.getText(),
                txtBuyerAddress.getText(),txtBuyerContact.getText(),txtBuyerEmail.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE pujithapalletsuppliers.buyer SET name= ? , nic=? , address=? , contact=? , email=? WHERE bid=?",b.getBuyerName(),b.getBuyerNic(),b.getBuyerAddress(),b.getBuyerContact(),b.getBuyerEmail(),b.getBuyerId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        buyersContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerForm.fxml"));
        buyersContext.getChildren().add(parent);

        txtBuyerName.clear();
        txtBuyerId.clear();
        txtBuyerNic.clear();
        txtBuyerAddress.clear();
        txtBuyerContact.clear();
        txtBuyerEmail.clear();
        txtSearch.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (CrudUtil.execute("DELETE FROM pujithapalletsuppliers.buyer WHERE bId=?",txtBuyerId.getText())){
               /* new Alert(Alert.AlertType.CONFIRMATION,"Deleted..").showAndWait();*/
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        buyersContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerForm.fxml"));
        buyersContext.getChildren().add(parent);

        txtSearch.clear();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void search() throws ClassNotFoundException ,SQLException{

        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.buyer WHERE bId=?",txtSearch.getText());

        if (result.next()){
            txtBuyerId.setText(result.getString(1));
            txtBuyerName.setText(result.getString(2));
            txtBuyerNic.setText(result.getString(3));
            txtBuyerAddress.setText(result.getString(4));
            txtBuyerContact.setText(result.getString(5));
            txtBuyerEmail.setText(result.getString(6));
        }else{
            new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
        }

    }



    public void textFields_Key_Released(KeyEvent keyEvent) {
       // Object validate = validate();
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

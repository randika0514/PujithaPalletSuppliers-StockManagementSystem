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
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane supplierContext;
    public TextField txtSupplierId;
    public TextField txtSupplierName;
    public TextField txtSupplierAddress;
    public TextField txtSupplierContact;
    public TableView tblSupplier;
    public TableColumn colSupplierId;
    public TableColumn colSupplierName;
    public TableColumn colSupplierAddress;
    public TableColumn colSupplierContact;
    public JFXTextField txtSearch;
    public Button btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colSupplierId.setCellValueFactory(new PropertyValueFactory("SupId"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory("SupName"));
        colSupplierAddress.setCellValueFactory(new PropertyValueFactory("SupAddress"));
        colSupplierContact.setCellValueFactory(new PropertyValueFactory("SupContact"));

        try {
            loadAllSuppliers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(S-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z]{2,45}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 .,/]{4,30}$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,}$");



        map.put(txtSupplierId,idPattern);
        map.put(txtSupplierName,namePattern);
        map.put(txtSupplierAddress,addressPattern);
        map.put(txtSupplierContact,contactPattern);
    }

    private void loadAllSuppliers() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.supplier");

        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Supplier(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4)
                    )
            );
        }
        tblSupplier.setItems(obList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        Supplier s = new Supplier(
                txtSupplierId.getText(),txtSupplierName.getText(),txtSupplierAddress.getText(),
                txtSupplierContact.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO pujithapalletsuppliers.supplier VALUES (?,?,?,?)",s.getSupId(),s.getSupName(),s.getSupAddress(),s.getSupContact())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        supplierContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        supplierContext.getChildren().add(parent);

        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtSupplierContact.clear();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Supplier s = new Supplier(
                txtSupplierId.getText(),txtSupplierName.getText(),txtSupplierAddress.getText(),
                txtSupplierContact.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE pujithapalletsuppliers.supplier SET name= ? , address=? , contact=? WHERE supId=?",s.getSupName(),s.getSupAddress(),s.getSupContact(),s.getSupId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        supplierContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        supplierContext.getChildren().add(parent);

        txtSupplierId.clear();
        txtSupplierName.clear();
        txtSupplierAddress.clear();
        txtSupplierContact.clear();
        txtSearch.clear();

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (CrudUtil.execute("DELETE FROM pujithapalletsuppliers.supplier WHERE supId=?",txtSupplierId.getText())){
                /* new Alert(Alert.AlertType.CONFIRMATION,"Deleted..").showAndWait();*/
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        supplierContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        supplierContext.getChildren().add(parent);

        txtSearch.clear();
    }

    private void search() throws ClassNotFoundException ,SQLException{

        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.supplier WHERE supId=?",txtSearch.getText());

        if (result.next()){
            txtSupplierId.setText(result.getString(1));
            txtSupplierName.setText(result.getString(2));
            txtSupplierAddress.setText(result.getString(3));
            txtSupplierContact.setText(result.getString(4));

        }else{
            new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
        }

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

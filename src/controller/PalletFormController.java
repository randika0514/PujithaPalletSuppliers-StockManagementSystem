package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
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
import model.Material;
import model.MaterialDetails;
import model.Pallet;
import model.SupplierOrder;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.SupplierOrderCartTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class PalletFormController {

    public AnchorPane palletContext;
    public TextField txtPalletId;
    public TextField txtPalletSize;
    public TextField txtPalletRate;
    public TextField txtPalletQtyOnHand;
    public TextField txtPalletDesc;
    public TableView tblPallet;
    public TableColumn colPalletId;
    public TableColumn colPalletSize;
    public TableColumn colPalletRate;
    public TableColumn colQtyOnHand;
    public TableColumn colPalletDesc;
    public JFXTextField txtSearch;
    public Button btnSave;
    public ComboBox<String> cmbPalletId;
    public TextField txtSetPalletSize;
    public TextField txtSetQtyOnHand;
    public TextField txtQty;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colPalletId.setCellValueFactory(new PropertyValueFactory("palletId"));
        colPalletSize.setCellValueFactory(new PropertyValueFactory("palletSize"));
        colPalletRate.setCellValueFactory(new PropertyValueFactory("palletRate"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("palletQtyOnHand"));
        colPalletDesc.setCellValueFactory(new PropertyValueFactory("palletDesc"));

        try {
            loadAllPallet();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        setPalletIds();

        cmbPalletId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setPalletDetails(newValue);
        });

        Pattern idPattern = Pattern.compile("^(PL-)[0-9]{3,5}$");
        Pattern sizePattern = Pattern.compile("^[A-z0-9]{2,45}$");
        Pattern ratePattern = Pattern.compile("^[1-9][0-9 .]+$");
        Pattern qtyOnHandPattern = Pattern.compile("^[0-9]+$");
        Pattern descPattern = Pattern.compile("^[A-z0-9 _(),./+&@%]{1,45}$");


        map.put(txtPalletId,idPattern);
        map.put(txtPalletSize,sizePattern);
        map.put(txtPalletRate,ratePattern);
        map.put(txtPalletQtyOnHand,qtyOnHandPattern);
        map.put(txtPalletDesc,descPattern);

    }

    private void loadAllPallet() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.pallet");

        ObservableList<Pallet> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Pallet(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getInt(4),
                            result.getString(5)
                    )
            );
        }
        tblPallet.setItems(obList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        Pallet p = new Pallet(
                txtPalletId.getText(),txtPalletSize.getText(),Double.parseDouble(txtPalletRate.getText()),
                Integer.parseInt(txtPalletQtyOnHand.getText()),txtPalletDesc.getText()
        );

        try {
            if (CrudUtil.execute("INSERT INTO pujithapalletsuppliers.pallet VALUES (?,?,?,?,?)",p.getPalletId(),p.getPalletSize(),p.getPalletRate(),p.getPalletQtyOnHand(),p.getPalletDesc())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        palletContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PalletForm.fxml"));
        palletContext.getChildren().add(parent);

        txtPalletId.clear();
        txtPalletSize.clear();
        txtPalletRate.clear();
        txtPalletQtyOnHand.clear();
        txtPalletDesc.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Pallet p = new Pallet(
                txtPalletId.getText(),txtPalletSize.getText(),Double.parseDouble(txtPalletRate.getText()),
                Integer.parseInt(txtPalletQtyOnHand.getText()),txtPalletDesc.getText()
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE pujithapalletsuppliers.pallet SET size= ? , rate=? , qtyOnHand=? , description=? WHERE palletId=?",p.getPalletSize(),p.getPalletRate(),p.getPalletQtyOnHand(),p.getPalletDesc(),p.getPalletId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        palletContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PalletForm.fxml"));
        palletContext.getChildren().add(parent);

        txtPalletId.clear();
        txtPalletSize.clear();
        txtPalletRate.clear();
        txtPalletQtyOnHand.clear();
        txtPalletDesc.clear();
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
            if (CrudUtil.execute("DELETE FROM pujithapalletsuppliers.pallet WHERE palletId=?",txtPalletId.getText())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        palletContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PalletForm.fxml"));
        palletContext.getChildren().add(parent);

        txtSearch.clear();
    }

    private void search() throws ClassNotFoundException ,SQLException{

        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.pallet WHERE palletId=?",txtSearch.getText());

        if (result.next()){
            txtPalletId.setText(result.getString(1));
            txtPalletSize.setText(result.getString(2));
            txtPalletRate.setText(String.valueOf(result.getDouble(3)));
            txtPalletQtyOnHand.setText(String.valueOf(result.getInt(4)));
            txtPalletDesc.setText(result.getString(5));

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


//=====================================================================================================================
    public void cmbPalletId(ActionEvent actionEvent) {

    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {

        updateQty(cmbPalletId.getValue(), Integer.parseInt(txtQty.getText()));

    }


    private boolean updateQty(String palletId, int qty) throws SQLException, ClassNotFoundException {
        //return CrudUtil.execute("UPDATE pujithapalletsuppliers.pallet SET qtyOnHand=qtyOnHand+? WHERE palletId=?",qty,palletId);

        return CrudUtil.execute("UPDATE pujithapalletsuppliers.pallet SET qtyOnHand=qtyOnHand+? WHERE palletId=?",qty,palletId);
        /*if (isUpdated){
            new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again..").show();
        }*/
    }
    private void setPalletIds() {
        try {
            cmbPalletId.setItems(FXCollections.observableList(PalletCrudController.getPalletIds()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setPalletDetails(String selectedPalletId) {
        try {
            Pallet p = PalletCrudController.getPallet(selectedPalletId);
            if (p!=null){
                txtSetPalletSize.setText(p.getPalletSize());
                txtSetQtyOnHand.setText(String.valueOf(p.getPalletQtyOnHand()));

            }else{
                new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

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
import model.Material;
import model.Pallet;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class MaterialFormController {
    public AnchorPane MaterialContext;
    public TextField txtMatId;
    public TextField txtMatItem;
    public TextField txtMatQtyOnHand;
    public TableView tblMaterial;
    public TableColumn colMatId;
    public TableColumn colMatItem;
    public TableColumn colMatQtyOnHand;
    public JFXTextField txtSearch;
    public TextField txtMatRate;
    public TableColumn colMatRate;
    public Button btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colMatId.setCellValueFactory(new PropertyValueFactory("matId"));
        colMatItem.setCellValueFactory(new PropertyValueFactory("matItem"));
        colMatRate.setCellValueFactory(new PropertyValueFactory("matRate"));
        colMatQtyOnHand.setCellValueFactory(new PropertyValueFactory("matQtyOnHand"));


        try {
            loadAllMaterial();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(M-)[0-9]{3,5}$");
        Pattern itemPattern = Pattern.compile("^[A-z ]{2,45}$");
        Pattern ratePattern = Pattern.compile("^[0-9.]{2,}$");
        Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,10}$");


        map.put(txtMatId,idPattern);
        map.put(txtMatItem,itemPattern);
        map.put(txtMatRate,ratePattern);
        map.put(txtMatQtyOnHand,qtyOnHandPattern);
    }

    private void loadAllMaterial() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.material");

        ObservableList<Material> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Material(
                            result.getString(1),
                            result.getString(2),
                            result.getDouble(3),
                            result.getInt(4)
                    )
            );
        }
        tblMaterial.setItems(obList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        Material m = new Material(
                txtMatId.getText(),txtMatItem.getText(),Double.parseDouble(txtMatRate.getText()),Integer.parseInt(txtMatQtyOnHand.getText())
        );

        try {
            if (CrudUtil.execute("INSERT INTO pujithapalletsuppliers.material VALUES (?,?,?,?)",m.getMatId(),m.getMatItem(),m.getMatRate(),m.getMatQtyOnHand())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        MaterialContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MaterialForm.fxml"));
        MaterialContext.getChildren().add(parent);

        txtMatId.clear();
        txtMatItem.clear();
        txtMatRate.clear();
        txtMatQtyOnHand.clear();

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Material m = new Material(
                txtMatId.getText(),txtMatItem.getText(),Double.parseDouble(txtMatRate.getText()),Integer.parseInt(txtMatQtyOnHand.getText())
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE pujithapalletsuppliers.material SET item= ? ,rate=? , qtyOnHand=?  WHERE mId=?",m.getMatItem(),m.getMatRate(),m.getMatQtyOnHand(),m.getMatId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        MaterialContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MaterialForm.fxml"));
        MaterialContext.getChildren().add(parent);

        txtMatId.clear();
        txtMatItem.clear();
        txtMatRate.clear();
        txtMatQtyOnHand.clear();
        txtSearch.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (CrudUtil.execute("DELETE FROM pujithapalletsuppliers.material WHERE mId=?",txtMatId.getText())){
                /* new Alert(Alert.AlertType.CONFIRMATION,"Deleted..").showAndWait();*/
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        MaterialContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MaterialForm.fxml"));
        MaterialContext.getChildren().add(parent);

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

        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.material WHERE mId=?",txtSearch.getText());

        if (result.next()){
            txtMatId.setText(result.getString(1));
            txtMatItem.setText(result.getString(2));
            txtMatRate.setText(String.valueOf(result.getDouble(3)));
            txtMatQtyOnHand.setText(String.valueOf(result.getInt(4)));

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

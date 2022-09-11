package controller;

import db.DBConnection;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.*;
import view.tm.BuyerOrderCartTM;
import view.tm.SupplierOrderCartTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SupplierOrderFormController {

    public AnchorPane supplierOrderContext;
    public ComboBox cmbOrderType;
    public ComboBox<String> cmbSupplierId;
    public TextField txtSupplierName;
    public TextField txtSupplierAddress;
    public TextField txtSupplierContact;
    public ComboBox<String> cmbMaterialId;
    public TextField txtMaterialType;
    public TextField txtMaterialQtyOnHand;
    public TextField txtMaterialRate;
    public TextField txtQty;
    public TableView tblSupplierOrder;
    public TableColumn colMaterialId;
    public TableColumn colType;
    public TableColumn colRate;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public TableColumn colOption;
    public Label lblTotal;
    public Label lblDate;
    public Button btnAddToCart;


    public void initialize(){

        loadDate();

        colMaterialId.setCellValueFactory(new PropertyValueFactory("materialId"));
        colType.setCellValueFactory(new PropertyValueFactory("type"));
        colRate.setCellValueFactory(new PropertyValueFactory("rate"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory("totalCost"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));


        cmbOrderType.getItems().add("Buyer Order");
        setSupplierIds();
        setMaterialIds();

        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setSupplierDetails(newValue);
        });

        cmbMaterialId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setMaterialDetails(newValue);
        });


    }

    private void setMaterialDetails(String selectedMaterialId) {
        try {
            Material m = MaterialCrudController.getMaterial(selectedMaterialId);
            if (m!=null){
                txtMaterialType.setText(m.getMatItem());
                txtMaterialQtyOnHand.setText(String.valueOf(m.getMatQtyOnHand()));
                txtMaterialRate.setText(String.valueOf(m.getMatRate()));

            }else{
                new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSupplierDetails(String selectedSupplierId) {
        try {
            Supplier s = SupplierCrudController.getSupplier(selectedSupplierId);
            if (s!=null){
                txtSupplierName.setText(s.getSupName());
                txtSupplierAddress.setText(s.getSupAddress());
                txtSupplierContact.setText(s.getSupContact());
            }else {
                new Alert(Alert.AlertType.WARNING, "Empty Result..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setMaterialIds() {
        try {
            cmbMaterialId.setItems(FXCollections.observableList(MaterialCrudController.getMaterialIds()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSupplierIds() {
        try {
            ObservableList<String> sIdObList = FXCollections.observableList(
                    SupplierCrudController.getSupplierIds()
            );
            cmbSupplierId.setItems(sIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDate() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnTakeOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        SupplierOrder supOrder = new SupplierOrder(
                SupplierOrderCrudController.getOrderId(1),
                cmbSupplierId.getValue(),
                txtMaterialType.getText(),
                lblDate.getText()
        );
        ArrayList<MaterialDetails> matDetails = new ArrayList<>();
        for (SupplierOrderCartTM tm:tmList
        ) {
            matDetails.add(
                    new MaterialDetails(
                            tm.getMaterialId(),
                            SupplierOrderCrudController.getOrderId(1),
                            tm.getQty(),
                            tm.getRate()
                    )
            );

        }
        Connection connection = null;
        try {
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSave = new SupplierOrderCrudController().saveOrder(supOrder);
            if (isOrderSave){
                boolean isDetailsSaved = new SupplierOrderCrudController().saveOrderDetails(matDetails);
                if (isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Added!").show();

                }else {
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }else {
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error!").show();
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public void cmbOrderTypeOnAction(ActionEvent actionEvent) throws IOException {
        if (cmbOrderType.getValue().equals("Buyer Order")){
            supplierOrderContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerOrderForm.fxml"));
            supplierOrderContext.getChildren().add(parent);
        }
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) {
    }

    ObservableList<SupplierOrderCartTM> tmList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double rate = Double.parseDouble(txtMaterialRate.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalCost = rate*qty;

        SupplierOrderCartTM isExists = isExists(cmbMaterialId.getValue());

        if (isExists!=null){
            for (SupplierOrderCartTM temp:tmList
            ) {
                if (temp.equals(isExists)){
                    temp.setQty((temp.getQty()+qty));
                    temp.setTotalCost(temp.getTotalCost()+totalCost);
                }
            }
        }else {
            Button btn =new Button("Delete");

            SupplierOrderCartTM tm = new SupplierOrderCartTM(
                    cmbMaterialId.getValue(),
                    txtMaterialType.getText(),
                    rate,
                    qty,
                    totalCost,
                    btn
            );

            btn.setOnAction(e->{
                tmList.remove(tm);
                calculateTotal();

            });

            tmList.add(tm);
            tblSupplierOrder.setItems(tmList);
        }
        tblSupplierOrder.refresh();
        calculateTotal();
    }

    private SupplierOrderCartTM isExists(String materialId){
        for (SupplierOrderCartTM tm:tmList
        ) {
            if (tm.getMaterialId().equals(materialId)){
                return tm;
            }

        }
        return null;
    }

    private void calculateTotal(){
        double total=0;
        for (SupplierOrderCartTM tm:tmList
        ) {
            total+=tm.getTotalCost();
        }
        lblTotal.setText(String.valueOf(total));
    }
}

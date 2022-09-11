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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Buyer;
import model.Order;
import model.OrderDetails;
import model.Pallet;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.BuyerOrderCartTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class BuyerOrderFormController{
    public ComboBox cmbOrderType;
    public AnchorPane buyerOrderContext;
    public ComboBox<String> cmbBuyerId;
    public TextField txtBuyerName;
    public TextField txtBuyerNic;
    public TextField txtBuyerContact;
    public ComboBox<String> cmbPalletId;
    public TextField txtPalletSize;
    public TextField txtPalletRate;
    public TextField txtPalletQty;
    public TextField txtPalletQtyOnHand;
    public TableView tblBuyerOrder;
    public TableColumn colPalletId;
    public TableColumn colSize;
    public TableColumn colRate;
    public TableColumn colQty;
    public TableColumn colTotalCost;
    public TableColumn colOption;
    public Label lblTotal;
    public Label lblDate;
    public Label lblOrderId;
    public Button btnAddToCart;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();


    public void cmbBtnOrderTypeOnAction(ActionEvent actionEvent) throws IOException {
        if (cmbOrderType.getValue().equals("Supplier Order")){
            buyerOrderContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierOrderForm.fxml"));
            buyerOrderContext.getChildren().add(parent);
        }
    }
    public void initialize(){

        loadDate();

        colPalletId.setCellValueFactory(new PropertyValueFactory("palletId"));
        colSize.setCellValueFactory(new PropertyValueFactory("size"));
        colRate.setCellValueFactory(new PropertyValueFactory("rate"));
        colQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colTotalCost.setCellValueFactory(new PropertyValueFactory("totalCost"));
        colOption.setCellValueFactory(new PropertyValueFactory("btn"));


        cmbOrderType.getItems().add("Supplier Order");
        setBuyerIds();
        setPalletIds();

        cmbBuyerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setBuyerDetails(newValue);
        });

        cmbPalletId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setPalletDetails(newValue);
        });


        try {
            lblOrderId.setText(OrderCrudController.getOrderId(1));
        } catch (SQLException e) {
            e.printStackTrace();
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

    private void setPalletDetails(String selectedPalletId) {
        try {
            Pallet p = PalletCrudController.getPallet(selectedPalletId);
            if (p!=null){
                txtPalletSize.setText(p.getPalletSize());
                txtPalletQtyOnHand.setText(String.valueOf(p.getPalletQtyOnHand()));
                txtPalletRate.setText(String.valueOf(p.getPalletRate()));

            }else{
                new Alert(Alert.AlertType.WARNING,"Empty Result..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    private void setBuyerDetails(String selectedBuyerId) {
        try {
            Buyer b = BuyerCrudController.getBuyer(selectedBuyerId);
            if (b!=null){
                txtBuyerName.setText(b.getBuyerName());
                txtBuyerNic.setText(b.getBuyerNic());
                txtBuyerContact.setText(b.getBuyerContact());
            }else {
                new Alert(Alert.AlertType.WARNING, "Empty Result..").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<BuyerOrderCartTM> tmList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double rate = Double.parseDouble(txtPalletRate.getText());
        int qty = Integer.parseInt(txtPalletQty.getText());
        double totalCost = rate*qty;

        BuyerOrderCartTM isExists = isExists(cmbPalletId.getValue());

        if (isExists!=null){
            for (BuyerOrderCartTM temp:tmList
                 ) {
                if (temp.equals(isExists)){
                    temp.setQty((temp.getQty()+qty));
                    temp.setTotalCost(temp.getTotalCost()+totalCost);
                }
            }
        }else {
            Button btn =new Button("Delete");

            BuyerOrderCartTM tm = new BuyerOrderCartTM(
                    cmbPalletId.getValue(),
                    txtPalletSize.getText(),
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
            tblBuyerOrder.setItems(tmList);
        }
        tblBuyerOrder.refresh();
        calculateTotal();
    }

    public void btnRemoveOnAction(ActionEvent actionEvent) throws IOException {
        buyerOrderContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerOrderForm.fxml"));
        buyerOrderContext.getChildren().add(parent);
    }

    private void setBuyerIds(){
        try {
            ObservableList<String> bIdObList = FXCollections.observableList(
                    BuyerCrudController.getBuyerIds()
            );
            cmbBuyerId.setItems(bIdObList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private BuyerOrderCartTM isExists(String palletId){
        for (BuyerOrderCartTM tm:tmList
             ) {
            if (tm.getPalletId().equals(palletId)){
                return tm;
            }

        }
        return null;
    }

    private void calculateTotal(){
        double total=0;
        for (BuyerOrderCartTM tm:tmList
             ) {
            total+=tm.getTotalCost();
        }
        lblTotal.setText(String.valueOf(total));
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Order order = new Order(
               // OrderCrudController.getOrderId(1),
                lblOrderId.getText(),
                cmbBuyerId.getValue(),
                lblDate.getText()
        );
        ArrayList<OrderDetails> details = new ArrayList<>();
        for (BuyerOrderCartTM tm:tmList
             ) {
            details.add(
                    new OrderDetails(
                           // OrderCrudController.getOrderId(1),
                            lblOrderId.getText(),
                            tm.getPalletId(),
                            tm.getQty(),
                            tm.getRate()
                    )
            );
            
        }
        Connection connection = null;
        try {
            connection=DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSave = new OrderCrudController().saveOrder(order);
            if (isOrderSave){
                boolean isDetailsSaved = new OrderCrudController().saveOrderDetails(details);
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

    public void btnPrintBillOnAction(ActionEvent actionEvent) {
        String orderID = lblOrderId.getText();
        /*String date = txtDate.getText();*/
        String buyerId = cmbBuyerId.getValue();
        String buyerName = txtBuyerName.getText();
        String total = lblTotal.getText();

        HashMap paramMap = new HashMap();
        paramMap.put("OrderId", orderID);
        paramMap.put("BuyerId", buyerId);
        paramMap.put("BuyerName", buyerName);
        paramMap.put("Total", total);

        ObservableList<BuyerOrderCartTM> tableRecords = tblBuyerOrder.getItems();

        try {
            JasperReport compiledReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/BuyerOrderInvoice.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport,paramMap, new JRBeanCollectionDataSource(tableRecords));
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}

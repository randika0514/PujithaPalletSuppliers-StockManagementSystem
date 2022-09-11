package controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Buyer;
import model.Order;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyerReportsFormController {
    public AnchorPane buyerReportContext;
    public TableView tblBuyerReport;
    public TableColumn colOrderId;
    public TableColumn colBuyerId;
    public TableColumn colOrderDate;
    public JFXComboBox cmbOption;


    private void loadAllOrders() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.orders");

        ObservableList<Order> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Order(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3)
                    )
            );
        }
        tblBuyerReport.setItems(obList);
    }

    public void cmbOptionOnAction(ActionEvent actionEvent) throws IOException {
        if (cmbOption.getValue().equals("Suppliers' Report")){
            buyerReportContext.getChildren().clear();
            Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierReportsForm.fxml"));
            buyerReportContext.getChildren().add(parent);
        }
    }

    public void initialize(){
        cmbOption.getItems().add("Suppliers' Report");

        colOrderId.setCellValueFactory(new PropertyValueFactory("orderId"));
        colBuyerId.setCellValueFactory(new PropertyValueFactory("buyerId"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory("date"));

        try {
            loadAllOrders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}

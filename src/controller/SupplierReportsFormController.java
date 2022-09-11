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
import model.Order;
import model.SupplierOrder;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierReportsFormController {
    public TableColumn colOrderId;
    public TableColumn colSupplierId;
    public TableColumn colItem;
    public TableColumn colOrderDate;
    public AnchorPane supplierReportContext;
    public JFXComboBox cmbOption;
    public TableView tblSupplierReport;

    public void cmbOptionOnAction(ActionEvent actionEvent) throws IOException {
            if (cmbOption.getValue().equals("Buyers' Report")){
                supplierReportContext.getChildren().clear();
                Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerReportsForm.fxml"));
                supplierReportContext.getChildren().add(parent);
            }
    }

    public void initialize(){
        cmbOption.getItems().add("Buyers' Report");

        colOrderId.setCellValueFactory(new PropertyValueFactory("supOrderId"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory("supId"));
        colItem.setCellValueFactory(new PropertyValueFactory("item"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory("date"));

        try {
            loadAllSupplierOrders();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadAllSupplierOrders() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.supplierorder");

        ObservableList<SupplierOrder> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new SupplierOrder(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4)
                    )
            );
        }
        tblSupplierReport.setItems(obList);
    }
}

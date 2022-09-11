package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane mainContext;
    public AnchorPane dashboardContext;
    public Label lblPalletsCount;
    public Label lblOrdersCount;
    public Label lblBuyersCount;
    public Label lblTime;
    public Label lblDate;

    public void initialize(){
        loadDateAndTime();

        try {
            setPalletLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setOrderLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            setBuyerLabel();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mainContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"))));
    }

    public void btnBuyersOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mainContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnPalletOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PalletForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnOrderOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerOrderForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnMaterialOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MaterialForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnAdditionalCostOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/AdditionalCostForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    public void btnReportsOnAction(ActionEvent actionEvent) throws IOException {
        dashboardContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/BuyerReportsForm.fxml"));
        dashboardContext.getChildren().add(parent);
    }

    private void setPalletLabel() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(palletId) FROM pujithapalletsuppliers.pallet");

        if (resultSet.next()){
            lblPalletsCount.setText(String.valueOf(resultSet.getInt(1)));
        }
    }

    private void setOrderLabel() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(orderId) FROM pujithapalletsuppliers.orders");

        if (resultSet.next()){
            lblOrdersCount.setText(String.valueOf(resultSet.getInt(1)));
        }
    }

    private void setBuyerLabel() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(bId) FROM pujithapalletsuppliers.buyer");

        if (resultSet.next()){
            lblBuyersCount.setText(String.valueOf(resultSet.getInt(1)));
        }
    }
}

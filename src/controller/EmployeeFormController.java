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
import model.Employee;
import model.Supplier;
import util.CrudUtil;
import util.ValidationUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class EmployeeFormController {

    public AnchorPane employeeContext;
    public TextField txtEmpId;
    public TextField txtEmpName;
    public TextField txtEmpAddress;
    public TextField txtEmpContact;
    public TextField txtEmpSalary;
    public TableView tblEmployee;
    public TableColumn colEmpId;
    public TableColumn colEmpName;
    public TableColumn colEmpAddress;
    public TableColumn colEmpContact;
    public TableColumn colEmpSalary;
    public JFXTextField txtSearch;
    public Button btnSave;
    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colEmpId.setCellValueFactory(new PropertyValueFactory("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory("empName"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory("empAddress"));
        colEmpContact.setCellValueFactory(new PropertyValueFactory("empContact"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory("empSalary"));

        try {
            loadAllSuppliers();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Pattern idPattern = Pattern.compile("^(E-)[0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,45}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,}$");
        Pattern salaryPattern = Pattern.compile("^[0-9.]{5,}$");

        map.put(txtEmpId,idPattern);
        map.put(txtEmpName,namePattern);
        map.put(txtEmpAddress,addressPattern);
        map.put(txtEmpContact,contactPattern);
        map.put(txtEmpSalary,salaryPattern);
    }

    private void loadAllSuppliers() throws ClassNotFoundException, SQLException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.employee");

        ObservableList<Employee> obList = FXCollections.observableArrayList();

        while (result.next()){
            obList.add(
                    new Employee(
                            result.getString(1),
                            result.getString(2),
                            result.getString(3),
                            result.getString(4),
                            result.getDouble(5)
                    )
            );
        }
        tblEmployee.setItems(obList);
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) throws IOException {
        Employee emp = new Employee(
                txtEmpId.getText(),txtEmpName.getText(),txtEmpAddress.getText(),
                txtEmpContact.getText(),Double.parseDouble(txtEmpSalary.getText())
        );

        try {
            boolean isUpdated = CrudUtil.execute("UPDATE pujithapalletsuppliers.employee SET name= ? , address=? , contact=? , salary=? WHERE eId=?",emp.getEmpName(),emp.getEmpAddress(),emp.getEmpContact(),emp.getEmpSalary(),emp.getEmpId());
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated..").show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        employeeContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);

        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpSalary.clear();
        txtSearch.clear();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (CrudUtil.execute("DELETE FROM pujithapalletsuppliers.employee WHERE eId=?",txtEmpId.getText())){
                /* new Alert(Alert.AlertType.CONFIRMATION,"Deleted..").showAndWait();*/
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure?", ButtonType.YES,ButtonType.NO);
                alert.showAndWait();
            }else{
                new Alert(Alert.AlertType.WARNING,"Try Again..").show();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        employeeContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);

        txtSearch.clear();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) throws IOException {
        Employee emp = new Employee(
                txtEmpId.getText(),txtEmpName.getText(),txtEmpAddress.getText(),
                txtEmpContact.getText(),Double.parseDouble(txtEmpSalary.getText())
        );

        try {
            if (CrudUtil.execute("INSERT INTO pujithapalletsuppliers.employee VALUES (?,?,?,?,?)",emp.getEmpId(),emp.getEmpName(),emp.getEmpAddress(),emp.getEmpContact(),emp.getEmpSalary())){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved...").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        employeeContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        employeeContext.getChildren().add(parent);

        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpSalary.clear();

    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            search();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void search() throws ClassNotFoundException ,SQLException{

        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.employee WHERE eId=?",txtSearch.getText());

        if (result.next()){
            txtEmpId.setText(result.getString(1));
            txtEmpName.setText(result.getString(2));
            txtEmpAddress.setText(result.getString(3));
            txtEmpContact.setText(result.getString(4));
            txtEmpSalary.setText(String.valueOf(result.getDouble(5)));

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

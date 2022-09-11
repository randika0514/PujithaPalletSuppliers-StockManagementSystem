package controller;

import model.Buyer;
import model.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierCrudController {
    public static ArrayList<String> getSupplierIds() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT supId FROM pujithapalletsuppliers.supplier");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Supplier getSupplier(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.supplier WHERE supId=?", id);
        if (result.next()){
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
}

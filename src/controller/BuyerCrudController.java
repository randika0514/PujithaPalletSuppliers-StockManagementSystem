package controller;

import model.Buyer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuyerCrudController {
    public static ArrayList<String> getBuyerIds() throws SQLException, ClassNotFoundException {
        ResultSet result=CrudUtil.execute("SELECT bid FROM pujithapalletsuppliers.buyer");
        ArrayList<String> ids = new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Buyer getBuyer(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.buyer WHERE bId=?", id);
        if (result.next()){
            return new Buyer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        return null;
    }
}

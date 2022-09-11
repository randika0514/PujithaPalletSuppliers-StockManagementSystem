package controller;

import model.Pallet;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PalletCrudController {
    public static ArrayList<String> getPalletIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT palletId FROM pujithapalletsuppliers.pallet");
        ArrayList<String> palletIdList = new ArrayList<>();
        while (result.next()){
            palletIdList.add(result.getString(1));
        }
        return palletIdList;
    }

    public static Pallet getPallet(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.pallet WHERE palletId=?",id);
        if (result.next()){
            return new Pallet(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4),
                    result.getString(5)
            );
        }
        return null;
    }
    private boolean updateQty(String palletId, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE pujithapalletsuppliers.pallet SET qtyOnHand=qtyOnHand+? WHERE palletId=?",qty,palletId);
    }
}

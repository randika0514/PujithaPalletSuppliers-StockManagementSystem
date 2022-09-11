package controller;

import model.Material;
import model.Pallet;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaterialCrudController {
    public static ArrayList<String> getMaterialIds() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT mId FROM pujithapalletsuppliers.material");
        ArrayList<String> materialIdList = new ArrayList<>();
        while (result.next()){
            materialIdList.add(result.getString(1));
        }
        return materialIdList;
    }

    public static Material getMaterial(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM pujithapalletsuppliers.material WHERE mId=?",id);
        if (result.next()){
            return new Material(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return null;
    }
}

package controller;

import model.MaterialDetails;
import model.SupplierOrder;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderCrudController {
    public boolean saveOrder(SupplierOrder order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO pujithapalletsuppliers.supplierorder VALUES(?,?,?,?)",
                order.getSupOrderId(),order.getSupId(),order.getItem(),order.getDate());
    }



    public boolean saveOrderDetails(ArrayList<MaterialDetails> matDetails) throws SQLException, ClassNotFoundException {
        for (MaterialDetails det:matDetails
        ) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO pujithapalletsuppliers.materialdetail VALUES(?,?,?,?)",
                    det.getMaterialId(),det.getSupOrderId(),det.getQty(),det.getRate());
            if (isDetailsSaved){
                updateQty(det.getMaterialId(), det.getQty());
            }
        }
        return true;
    }

    private boolean updateQty(String materialId, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE pujithapalletsuppliers.material SET qtyOnHand=qtyOnHand+? WHERE mId=?",qty,materialId);
    }

    public static String getOrderId(int column) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT supOrderId FROM pujithapalletsuppliers.supplierorder ORDER BY supOrderId DESC LIMIT 1");

        if (resultSet.next()) {
            String id = resultSet.getString(column);
            String[] splitted = id.split("(SO-)");
            return String.format("SO-%03d", (Integer.valueOf(splitted[1]) + 1));
        }
        return "SO-001";


    }
}

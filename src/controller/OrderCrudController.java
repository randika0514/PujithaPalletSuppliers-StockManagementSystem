package controller;

import model.Order;
import model.OrderDetails;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {
    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
       return CrudUtil.execute("INSERT INTO pujithapalletsuppliers.orders VALUES(?,?,?)",
                order.getOrderId(),order.getBuyerId(),order.getDate());
    }


    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details
        ) {
            boolean isDetailsSaved = CrudUtil.execute("INSERT INTO pujithapalletsuppliers.orderdetail VALUES(?,?,?,?)",
                    det.getOrderId(),det.getPalletId(),det.getQty(),det.getRate());
            if (isDetailsSaved){
                updateQty(det.getPalletId(), det.getQty());
            }
        }
        return true;
    }

    private boolean updateQty(String palletId, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE pujithapalletsuppliers.pallet SET qtyOnHand=qtyOnHand-? WHERE palletId=?",qty,palletId);
    }

    public static String getOrderId(int column) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT orderId FROM pujithapalletsuppliers.orders ORDER BY orderId DESC LIMIT 1");

        if (set.next()) {
            String id = set.getString(column);
            String[] splitted = id.split("(OD-)");
            return String.format("OD-%03d", (Integer.valueOf(splitted[1]) + 1));
        }
        return "OD-001";


    }
}

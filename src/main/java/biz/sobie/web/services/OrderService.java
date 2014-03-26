package biz.sobie.web.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import biz.sobie.web.beans.Order;
import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;

public class OrderService extends JdbcDaoSupport {
    String query = null;


    /**
     * Buyer checkouts an item from his/her shopping cart and creates a new order
     */
    public void createNewOrder(final SobieProfile sobieProfile, final Order order) {
    	
    	/**
		 * Adds a new order a buyers account
		 */
		query = "INSERT INTO ORDERS (ORDER_NO, BUYER_ACC_NO, SELLER_ACC_NO, SUPPLIER_ACC_NO, SHIPPER_ACC_NO, PROD_NO, TRAN_TYPE, ORDER_DATE, ORDER_TIME, PROD_QTY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, order.getOrderNo()); 
                       statement.setString(2, order.getBuyerAccNo());
                       statement.setString(3, order.getSellerAccNo());
                       statement.setString(4, order.getSupplierAccNo());
                       statement.setString(5, order.getShipperAccNo());
                       statement.setString(6, order.getProdId());
                       statement.setString(7, order.getTranType());
                       statement.setInt(8, order.getOrderDate());
                       statement.setInt(9, order.getOrderTime());
                       statement.setInt(10, order.getProdQty());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
        	 ex.printStackTrace();
        }
    }


	@SuppressWarnings("unchecked")
	public List<Order> retrieveOrderList(SobieProfile sobieProfile) {
		
		List<Order> orderList;
		
		query = "SELECT A.ORDER_NO, " +
					"A.BUYER_ACC_NO, " +
					"A.SELLER_ACC_NO, " +
					"A.SUPPLIER_ACC_NO, " +
					"A.SHIPPER_ACC_NO, " +
					"A.PROD_NO, " +
					"A.TRAN_TYPE, " +
					"A.ORDER_DATE, " +
					"A.ORDER_TIME, " +
					"A.PROD_QTY, " + 
					"B.IMG_ID, " +
					"B.IMG_FILENAME, " +
					"B.IMG_FILE, " +
					"C.PROD_ID, " +
					"C.PROD_NAME, " +
					"C.PROD_PRICE, " +
					"C.PROD_DESC, " +
					"C.PROD_TYPE " +
			"FROM ORDERS A, " +
				"IMAGE B, " +
				"PRODUCT C ";
		
		if(sobieProfile.getAccount().getPpId().startsWith("BU")) {
			query = query + "WHERE A.BUYER_ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' ";
			
		} else if(sobieProfile.getAccount().getPpId().startsWith("SE")) {
			query = query + "WHERE A.SELLER_ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' ";
			
		} else if(sobieProfile.getAccount().getPpId().startsWith("SU")) {
			query = query + "WHERE A.SUPPLIER_ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' ";
			
		} else if(sobieProfile.getAccount().getPpId().startsWith("SH")) {
			query = query + "WHERE A.SHIPPER_ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' ";
		}
		
		query = query + "AND A.PROD_NO = C.PROD_ID " +
						"AND B.IMG_ID = C.IMG_ID ";	
		
		try {
			orderList = (List<Order>)getJdbcTemplate().query(query, new OrderMapper());
		} catch (Exception e) {
			orderList = null;
		}
		return orderList;
	}
	
	@SuppressWarnings("rawtypes")
	//TODO: Changes was made to the Product Bean. Need to update to reflect	
	public static final class OrderMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Order order = new Order();
			order.setOrderNo(rs.getString("ORDER_NO"));
			order.setBuyerAccNo(rs.getString("BUYER_ACC_NO"));
			order.setSellerAccNo("SELLER_ACC_NO");
			order.setSupplierAccNo(rs.getString("SUPPLIER_ACC_NO"));
			order.setProdId(rs.getString("PROD_NO"));
			order.setTranType(rs.getString("TRAN_TYPE"));
			order.setOrderDate(rs.getInt("ORDER_DATE"));
			order.setOrderTime(rs.getInt("ORDER_TIME"));
			order.setProdQty(rs.getInt("PROD_QTY"));
			
			Product product = new Product();
			
			product.setProdId(rs.getString("PROD_ID"));
			product.setProdName(rs.getString("C.PROD_NAME"));
			//product.setProdPrice(rs.getDouble("C.PROD_PRICE"));
			product.setProdDesc(rs.getString("C.PROD_DESC"));
			//product.setProdType(rs.getString("C.PROD_TYPE"));
			SobieImage image = new SobieImage();
			image.setImage(rs.getBlob("B.IMG_FILE"));
			image.setImgFilename(rs.getString("B.IMG_FILENAME"));
			image.setImgId(rs.getString("B.IMG_ID"));
			product.getImageCatalog().add(image);
			order.setProduct(product);
			return order;
		}
	}
}
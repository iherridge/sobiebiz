package biz.sobie.web.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import biz.sobie.web.beans.Product;
import biz.sobie.web.beans.SearchResultsBean;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.userdashboard.inventory.MerchantDetails;
import biz.sobie.web.userdashboard.inventory.ProductHistory;
import biz.sobie.web.utils.SobieUtils;

public class ProductService extends JdbcDaoSupport {
    String query = null;

    /**
     * Activate a product in a users Inventory Products
     */
    public void activateProduct(final String prodId, final String storeId){
    	
    	query = "UPDATE store_inventory " +
    			"SET PROD_STATUS = '1' " +
    			"WHERE STORE_NO = ? " +
    			"AND PROD_ID = ?;";
    	
    	getJdbcTemplate().update(query, new Object[] {storeId, prodId});
    }

    /**
     * De-activate a product in a users Inventory Products
     */
    public void deactivateProduct(final String prodId, final String storeId){
    	
    	query = "UPDATE store_inventory " +
    			"SET PROD_STATUS = '0' " +
    			"WHERE STORE_NO = ? " +
    			"AND PROD_ID = ?;";
    	
    	getJdbcTemplate().update(query, new Object[] {storeId, prodId});
    }
    
    /**
     * De-activate a product in a users Inventory Products
     */
    public void requestForApprovalProduct(final String prodId, final String storeId){
    	
    	query = "UPDATE store_inventory " +
    			"SET PROD_STATUS = '2' " +
    			"WHERE STORE_NO = ? " +
    			"AND PROD_ID = ?;";
    	
    	getJdbcTemplate().update(query, new Object[] {storeId, prodId});
    }
    
    /** Seller ads a product from a supplier to his/her shop
         *//**
     */
    public void addProductToSellerShop(final Product product, final String accNo) {
    	final String ownerProdId = product.getOwnerProdId();
    	/**
    	 * Retrieve StoreNo that belongs to accNo
    	 */
		query = "SELECT STORE_NO FROM store WHERE ACC_NO = '" + accNo + "'";
        final String accNoStoreNo = (String)getJdbcTemplate().query(query, new StoreMapper()).get(0);
        
        /**
    	 * Retrieve StoreNo that belongs to prodId where the OWNED_ITEM indicator is set to Y
    	 */
        query = "SELECT STORE_NO FROM store_inventory WHERE PROD_ID = '" + ownerProdId + "' AND OWNED_ITEM = 'Y'";
        final String prodIdStoreNo = (String)getJdbcTemplate().query(query, new StoreInventoryMapper()).get(0);
        
        SobieUtils sobieUtils = new SobieUtils();
		final String prodId = sobieUtils.createProductId();
        /**
		 * Insert new Product record into PRODUCT table
		 */
		
		query = "INSERT INTO PRODUCT(PROD_ID, " + 
									"PROD_NAME, " +
									"PROD_SKU_PART_NO, " +
									"PROD_CATEGORY, " +
									"PROD_RETAIL_PRICE, " +
									"PROD_SALE_PRICE, " +
									"PROD_WHOLESALE_PRICE, " +
									"TAX_CLASS_ID, " +
									"PROD_ADD_TO_CART, " +
									"PROD_QTY_IMED_AVAIL, " +
									"PROD_MIN_PURCHASE_QTY, " +
									"PROD_MAX_PURCHASE_QTY, " +
									"PROD_FREE_SHIPPING, " +
									"PROD_SHIP_SEPERATE, " +
									"PROD_SHIPPING_TYPE_ID, " +
									"PROD_WEIGHT, " +
									"PROD_HEIGHT, " +
									"PROD_WIDTH, " +
									"PROD_LENGHT, " +
									"PROD_SHIPPING_ENABLED, " +
									"PROD_DESC, " +
									"PROD_FEATURES, " +
									"PROD_SPECIFICATION, " +
									"PROD_SHIPPING_INFO, " +
									"PROD_YOUTUBE_URL, " +
									"PROD_COMMENTS_ENABLED, " +
									"PROD_QNA_ENABLED, " +
									"PROD_BRAND, " +
									"PROD_ENABLED) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, prodId); 
                       statement.setString(2, product.getProdName()); 
                       statement.setString(3, product.getProdSkuPartNo()); 
                       statement.setString(4, product.getProdCategory()); 
                       statement.setDouble(5, product.getProdRetailPrice()); 
                       statement.setDouble(6, product.getProdSalePrice()); 
                       statement.setDouble(7, product.getProdWholesalePrice()); 
                       statement.setString(8, product.getTaxClassId()); 
                       statement.setBoolean(9, product.isProdAddToCart()); 
                       statement.setInt(10, product.getProdQtyImedAvail()); 
                       statement.setInt(11, product.getProdMinPurchaseQty()); 
                       statement.setInt(12, product.getProdMaxPurchaseQty()); 
                       statement.setBoolean(13, product.isProdFreeShipping()); 
                       statement.setBoolean(14, product.isProdShipSeperate()); 
                       statement.setString(15, product.getProdShippingTypeId()); 
                       statement.setDouble(16, product.getProdWeight()); 
                       statement.setDouble(17, product.getProdHeight()); 
                       statement.setDouble(18, product.getProdWidth()); 
                       statement.setDouble(19, product.getProdLenght()); 
                       statement.setBoolean(20, product.isProdShippingEnabled()); 
                       statement.setString(21, product.getProdDesc()); 
                       statement.setString(22, product.getProdFeatures()); 
                       statement.setString(23, product.getProdSpecification()); 
                       statement.setString(24, product.getProdShippingInfo()); 
                       statement.setString(25, product.getProdYouTubeUrl()); 
                       statement.setBoolean(26, product.isProdCommentsEnabled()); 
                       statement.setBoolean(27, product.isProdQnaEnabled()); 
                       statement.setString(28, product.getProdBrand());
                       statement.setBoolean(29, product.isProdEnabled()); 
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        /**
		 * Insert PROD_NO and STORE_NO into STORE_INVENTORY table with OWNED_ITEM indicator set to N and OWNER_STORE_NO set to the prodIdStoreNo
		 */
		
		query = "insert into store_inventory (STORE_NO, PROD_ID, SERV_ID, OWNED_ITEM, OWNER_STORE_NO, OWNER_PROD_ID, PROD_STATUS) values (?, ?, ?, ?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, accNoStoreNo); 
                       statement.setString(2, prodId);
                       statement.setString(3, "0");
                       statement.setString(4, "N");
                       statement.setString(5, prodIdStoreNo);
                       statement.setString(6, ownerProdId);
                       statement.setString(7, "0");
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
        	 ex.printStackTrace();
        }
        
        
    }

    /**
     * This method must be invoked when a user adds a new product to the his/her shopping cart
     */
    public void addProductToShoppingCart(final String prodId, final String accNo, final String sellerStoreNo, final String accNoShoppingCartNo) {
        
        /**
    	 * Retrieve OwnerStoreNumber that belongs to prodId where the OWNED_ITEM indicator is set to Y
    	 */
        query = "SELECT * FROM store_inventory WHERE PROD_ID = '" + prodId + "' AND OWNED_ITEM = 'Y'";
        final String ownerStoreNo = (String)getJdbcTemplate().query(query, new OwnerStoreInventoryMapper()).get(0);
        
        /**
		 * Insert PROD_ID and SELLER_STORE_NO and OWNER_STORE_NO into SHOPCART_INVENTORY table
		 */
		query = "insert into shopcart_inventory (SHOPCART_NO, PROD_ID, SELLER_STORE_NO, OWNER_STORE_NO) values (?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, accNoShoppingCartNo); 
                       statement.setString(2, prodId);
                       statement.setString(3, sellerStoreNo);
                       statement.setString(4, ownerStoreNo);
                       return statement;
					}
                });
            }
        } catch (DuplicateKeyException ex) {
        	 ex.printStackTrace();
        	 //TODO: Send a error message to the user, explaining that he/she has already added this product to his/her shopping cart
        } catch (Exception ex) {
        	 ex.printStackTrace();
        }
    }  
    
	public class OwnerStoreInventoryMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String storeNo = rs.getString("OWNER_STORE_NO");
			return storeNo;
		}
	}
    
    /**
     * THis method must be called when a users wants to add a new product to a shop. 
     * Can be used by sellers and suppliers, but this product will always as a "supplier product",
     * even when it is a seller adding it.
     * Shippers will also pick the products up from the owner of this product.
     * 
     * @param product
     * @param accNo
     */
    public void saveNewProduct(final Product product, final String accNo) {
        

    	/**
    	 * Retrieve StoreNo
    	 */
    	query = "SELECT * FROM store WHERE ACC_NO = '" + accNo + "'";
        final String storeNo = (String)getJdbcTemplate().query(query, new StoreMapper()).get(0);
    	
    	SobieUtils sobieUtils = new SobieUtils();
		product.setProdId(sobieUtils.createProductId());
       
		/**
		 * Insert new Product record into PRODUCT table
		 */
		
		query = "INSERT INTO PRODUCT(PROD_ID, " + 
									"PROD_NAME, " +
									"PROD_SKU_PART_NO, " +
									"PROD_CATEGORY, " +
									"PROD_RETAIL_PRICE, " +
									"PROD_SALE_PRICE, " +
									"PROD_WHOLESALE_PRICE, " +
									"TAX_CLASS_ID, " +
									"PROD_ADD_TO_CART, " +
									"PROD_QTY_IMED_AVAIL, " +
									"PROD_MIN_PURCHASE_QTY, " +
									"PROD_MAX_PURCHASE_QTY, " +
									"PROD_FREE_SHIPPING, " +
									"PROD_SHIP_SEPERATE, " +
									"PROD_SHIPPING_TYPE_ID, " +
									"PROD_WEIGHT, " +
									"PROD_HEIGHT, " +
									"PROD_WIDTH, " +
									"PROD_LENGHT, " +
									"PROD_SHIPPING_ENABLED, " +
									"PROD_DESC, " +
									"PROD_FEATURES, " +
									"PROD_SPECIFICATION, " +
									"PROD_SHIPPING_INFO, " +
									"PROD_YOUTUBE_URL, " +
									"PROD_COMMENTS_ENABLED, " +
									"PROD_QNA_ENABLED, " +
									"PROD_BRAND, " +
									"PROD_ENABLED) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, product.getProdId()); 
                       statement.setString(2, product.getProdName()); 
                       statement.setString(3, product.getProdSkuPartNo()); 
                       statement.setString(4, product.getProdCategory()); 
                       statement.setDouble(5, product.getProdRetailPrice()); 
                       statement.setDouble(6, product.getProdSalePrice()); 
                       statement.setDouble(7, product.getProdWholesalePrice()); 
                       statement.setString(8, product.getTaxClassId()); 
                       statement.setBoolean(9, product.isProdAddToCart()); 
                       statement.setInt(10, product.getProdQtyImedAvail()); 
                       statement.setInt(11, product.getProdMinPurchaseQty()); 
                       statement.setInt(12, product.getProdMaxPurchaseQty()); 
                       statement.setBoolean(13, product.isProdFreeShipping()); 
                       statement.setBoolean(14, product.isProdShipSeperate()); 
                       statement.setString(15, product.getProdShippingTypeId()); 
                       statement.setDouble(16, product.getProdWeight()); 
                       statement.setDouble(17, product.getProdHeight()); 
                       statement.setDouble(18, product.getProdWidth()); 
                       statement.setDouble(19, product.getProdLenght()); 
                       statement.setBoolean(20, product.isProdShippingEnabled()); 
                       statement.setString(21, product.getProdDesc()); 
                       statement.setString(22, product.getProdFeatures()); 
                       statement.setString(23, product.getProdSpecification()); 
                       statement.setString(24, product.getProdShippingInfo()); 
                       statement.setString(25, product.getProdYouTubeUrl()); 
                       statement.setBoolean(26, product.isProdCommentsEnabled()); 
                       statement.setBoolean(27, product.isProdQnaEnabled()); 
                       statement.setString(28, product.getProdBrand());
                       statement.setBoolean(29, product.isProdEnabled());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		/**
		 * Insert PROD_NO and STORE_NO into STORE_INVENTORY table
		 */
		
		query = "insert into store_inventory (STORE_NO, PROD_ID, SERV_ID, OWNED_ITEM, OWNER_STORE_NO, PROD_STATUS, OWNER_PROD_ID, PROD_FEATURED) values (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, storeNo); 
                       statement.setString(2, product.getProdId());
                       statement.setString(3, "0");
                       statement.setString(4, "Y");
                       statement.setString(5, storeNo);
                       statement.setString(6, "0");
                       statement.setString(7, product.getProdId());
                       statement.setBoolean(8, product.isProdFeatured());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        for(int x = 0; x < product.getImageCatalog().size(); x++){
        	
        	final int count = x;
        	/**
             * Insert a record for each product owned image to the 
             */
    		
    		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, ACC_NO, PROD_ID, PRIMARY_IMG) values (?, ?, ?, ?, ?)";

            try {
                synchronized(this) {
                    getJdbcTemplate().update(new PreparedStatementCreator() {
    					
    					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                           PreparedStatement statement = con.prepareStatement(query);
                           statement.setString(1, product.getImageCatalog().get(count).getImgId());
                           statement.setString(2, "PRODUCT_CATALOG");
                           statement.setString(3, accNo);
                           statement.setString(4, product.getProdId());
                           statement.setString(5, product.getImageCatalog().get(count).getPrimaryImage());
                           return statement;
    					}
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        	
	        /**
	         * Insert image that is related to the Product that was inserted into the PRODUCT table
	         */
			
			query = "insert into image (IMG_ID, IMG_FILENAME, IMG_TYPE, IMG_NOTES, IMG_FILE) values (?, ?, ?, ?, ?)";
	
	        try {
	            synchronized(this) {
	                getJdbcTemplate().update(new PreparedStatementCreator() {
						
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                       PreparedStatement statement = con.prepareStatement(query);
	                       statement.setString(1, product.getImageCatalog().get(count).getImgId()); 
	                       statement.setString(2, product.getImageCatalog().get(count).getImgFilename());
	                       statement.setString(3, product.getImageCatalog().get(count).getImgNotes());
	                       statement.setString(4, product.getImageCatalog().get(count).getImgType());
	                       statement.setBytes(5, product.getImageCatalog().get(count).getImageInBytes());
	                       return statement;
						}
	                });
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
        }
    }
    
	public class StoreMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String storeNo = rs.getString("STORE_NO");
			return storeNo;
		}
	}
	
	public class StoreInventoryMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String storeNo = rs.getString("STORE_NO");
			return storeNo;
		}
	}
	
	/**
	 * This method is called when a user opens the shopping cart window
	 * @param sobieProfile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Product> retrieveShoppingCart(final SobieProfile sobieProfile) {
        
		query = "SELECT A.PROD_ID, " +
					"A.PROD_NAME, " +
					"A.PROD_SKU_PART_NO, " +
					"A.PROD_CATEGORY, " +
					"A.PROD_RETAIL_PRICE, " +
					"A.PROD_SALE_PRICE, " +
					"A.PROD_WHOLESALE_PRICE, " +
					"A.TAX_CLASS_ID, " +
					"A.PROD_ADD_TO_CART, " +
					"A.PROD_QTY_IMED_AVAIL, " +
					"A.PROD_MIN_PURCHASE_QTY, " +
					"A.PROD_MAX_PURCHASE_QTY, " +
					"A.PROD_FREE_SHIPPING, " +
					"A.PROD_SHIP_SEPERATE, " +
					"A.PROD_SHIPPING_TYPE_ID, " +
					"A.PROD_WEIGHT, " +
					"A.PROD_HEIGHT, " +
					"A.PROD_WIDTH, " +
					"A.PROD_LENGHT, " +
					"A.PROD_SHIPPING_ENABLED, " +
					"A.PROD_DESC, " +
					"A.PROD_FEATURES, " +
					"A.PROD_SPECIFICATION, " +
					"A.PROD_SHIPPING_INFO, " +
					"A.PROD_YOUTUBE_URL, " +
					"A.PROD_COMMENTS_ENABLED, " +
					"A.PROD_QNA_ENABLED, " +
					"A.PROD_BRAND, " +
					"A.PROD_ENABLED, " +
					"B.SELLER_STORE_NO, " +
					"B.OWNER_STORE_NO, " +
					"(SELECT ACC_NO FROM STORE WHERE STORE_NO = B.SELLER_STORE_NO) AS SELLER_ACC_NO, " +
					"(SELECT ACC_NO FROM STORE WHERE STORE_NO = B.OWNER_STORE_NO) AS OWNER_ACC_NO " + 
					 "FROM product A, " +
					 	  "shopcart_inventory B, " +
					 	  "shopping_cart C " +
					 "WHERE A.PROD_ID = B.PROD_ID " +
					 "AND B.SHOPCART_NO = C.SHOPCART_NO " +
					 "AND C.ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "'";		
		
		List<Product> products = getJdbcTemplate().query(query, new RowMappers.ShoppingCartListMapper());
		
		return products;
	}
	
	public String getShoppingCartNotificationInfo(String shoppingCartNo) {
        
		query = "SELECT COUNT(PROD_ID) AS TOTAL_ITEMS " +
				"FROM SHOPCART_INVENTORY " +
				"WHERE SHOPCART_NO = '" + shoppingCartNo + "'";
		
		List<String> totalShoppingCartItems = getJdbcTemplate().query(query, new ShoppingCartNotificationMapper());
		
		return totalShoppingCartItems.get(0);
	}
	
	public class ShoppingCartNotificationMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String totalShoppingCartItems = rs.getString("TOTAL_ITEMS");
			return totalShoppingCartItems;
		}
	}

	/**
	 * This method is invoked when a user views the inventory associated to the account
	 */
	@SuppressWarnings("unchecked")
	public List<Product> retrieveStoreProducts(String accNo) {
        
		try{
			query = "SELECT A.PROD_ID, " +
						"A.PROD_NAME, " +
						"A.PROD_SKU_PART_NO, " +
						"A.PROD_CATEGORY, " +
						"A.PROD_RETAIL_PRICE, " +
						"A.PROD_SALE_PRICE, " +
						"A.PROD_WHOLESALE_PRICE, " +
						"A.TAX_CLASS_ID, " +
						"A.PROD_ADD_TO_CART, " +
						"A.PROD_QTY_IMED_AVAIL, " +
						"A.PROD_MIN_PURCHASE_QTY, " +
						"A.PROD_MAX_PURCHASE_QTY, " +
						"A.PROD_FREE_SHIPPING, " +
						"A.PROD_SHIP_SEPERATE, " +
						"A.PROD_SHIPPING_TYPE_ID, " +
						"A.PROD_WEIGHT, " +
						"A.PROD_HEIGHT, " +
						"A.PROD_WIDTH, " +
						"A.PROD_LENGHT, " +
						"A.PROD_SHIPPING_ENABLED, " +
						"A.PROD_DESC, " +
						"A.PROD_FEATURES, " +
						"A.PROD_SPECIFICATION, " +
						"A.PROD_SHIPPING_INFO, " +
						"A.PROD_YOUTUBE_URL, " +
						"A.PROD_COMMENTS_ENABLED, " +
						"A.PROD_QNA_ENABLED, " +
						"A.PROD_BRAND, " +
						"A.PROD_ENABLED, " +
						"B.STORE_NO, " +
						"B.PROD_STATUS, " +
						"D.ACC_NO AS OWNER_ACC_NO, " +
						"B.OWNER_PROD_ID, " +
						"B.PROD_FEATURED, " +
						"IFNULL((SELECT COUNT(OWNER_PROD_ID) " +
						"FROM LIKES " +
						"WHERE OWNER_PROD_ID = B.OWNER_PROD_ID " +
						"GROUP BY OWNER_PROD_ID),0) AS OWNER_PROD_ID_COUNT " +
					 "FROM PRODUCT A, " +
					 	  "STORE_INVENTORY B, " +
					 	  "STORE C, " +
					 	  "STORE D " +
					 "WHERE A.PROD_ID = B.PROD_ID " +
					 "AND B.STORE_NO = C.STORE_NO " + 
					 "AND C.ACC_NO = '" + accNo + "' " +
					 "AND B.OWNED_ITEM = 'Y' " +
					 "AND D.STORE_NO = B.OWNER_STORE_NO " +
					 "AND B.DELETED <> '1' ";
			
			List<Product> products = getJdbcTemplate().query(query, new RowMappers.ProductMapper());
			
			query = "SELECT A.IMG_ID, " +
						"A.IMG_FILENAME, " +
						"A.IMG_TYPE, " +
						"A.IMG_NOTES, " +
						"A.IMG_FILE, " +
						"B.IMG_ALBUM, " +
						"B.PROD_ID," +
						"B.SERV_ID," +
						"B.PRIMARY_IMG " +
					"FROM IMAGE A, " +
					"IMAGE_CATALOG B " +
					"WHERE B.ACC_NO = '" + accNo + "' " +
					"AND B.IMG_ALBUM = 'PRODUCT_CATALOG' " +
					"AND A.IMG_ID = B.IMG_ID ";
			
			List<SobieImage> sobieImages = getJdbcTemplate().query(query, new RowMappers.SobieImageMapper());
			
			for(int x = 0; x < products.size(); x++){
				ArrayList<SobieImage> productImages = (ArrayList<SobieImage>) products.get(x).getImageCatalog();
				for(int y = 0; y < sobieImages.size(); y++){
					if(sobieImages.get(y).getProdId().equals(products.get(x).getProdId())){
						productImages.add(sobieImages.get(y));
					}
				}
			}
			
			return products;

		} catch (Exception e) {

			logger.debug("Error selecting");
			 e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * This method is invoked when a user views the inventory network products user dashboard
	 */
	@SuppressWarnings("unchecked")
	public List<Product> retrieveInventoryNetworkProducts(String accNo) {
        
		try{
			query = "SELECT A.PROD_ID, " +
						"A.PROD_NAME, " +
						"A.PROD_SKU_PART_NO, " +
						"A.PROD_CATEGORY, " +
						"A.PROD_RETAIL_PRICE, " +
						"A.PROD_SALE_PRICE, " +
						"A.PROD_WHOLESALE_PRICE, " +
						"A.TAX_CLASS_ID, " +
						"A.PROD_ADD_TO_CART, " +
						"A.PROD_QTY_IMED_AVAIL, " +
						"A.PROD_MIN_PURCHASE_QTY, " +
						"A.PROD_MAX_PURCHASE_QTY, " +
						"A.PROD_FREE_SHIPPING, " +
						"A.PROD_SHIP_SEPERATE, " +
						"A.PROD_SHIPPING_TYPE_ID, " +
						"A.PROD_WEIGHT, " +
						"A.PROD_HEIGHT, " +
						"A.PROD_WIDTH, " +
						"A.PROD_LENGHT, " +
						"A.PROD_SHIPPING_ENABLED, " +
						"A.PROD_DESC, " +
						"A.PROD_FEATURES, " +
						"A.PROD_SPECIFICATION, " +
						"A.PROD_SHIPPING_INFO, " +
						"A.PROD_YOUTUBE_URL, " +
						"A.PROD_COMMENTS_ENABLED, " +
						"A.PROD_QNA_ENABLED, " +
						"A.PROD_BRAND, " +
						"A.PROD_ENABLED, " +
						"B.STORE_NO, " +
						"B.PROD_STATUS, " +
						"D.ACC_NO AS OWNER_ACC_NO, " +
						"B.OWNER_PROD_ID, " +
						"B.PROD_FEATURED, " +
						"IFNULL((SELECT COUNT(OWNER_PROD_ID) " +
						"FROM LIKES " +
						"WHERE OWNER_PROD_ID = B.OWNER_PROD_ID " +
						"GROUP BY OWNER_PROD_ID),0) AS OWNER_PROD_ID_COUNT " +
					 "FROM PRODUCT A, " +
					 	  "STORE_INVENTORY B, " +
					 	  "STORE C, " +
					 	  "STORE D " +
					 "WHERE A.PROD_ID = B.PROD_ID " +
					 "AND B.STORE_NO = C.STORE_NO " + 
					 "AND C.ACC_NO = '" + accNo + "' " +
					 "AND B.OWNED_ITEM = 'N' " +
					 "AND D.STORE_NO = B.OWNER_STORE_NO " +
					 "AND B.DELETED <> '1' ";
			
			List<Product> products = getJdbcTemplate().query(query, new RowMappers.ProductMapper());
			
			if(products.size() != 0){
				String productQuery = "'" + products.get(0).getOwnerProdId() + "'";
				for(int x = 1; x < products.size(); x++){
					productQuery = productQuery + ", " + "'" + products.get(x).getOwnerProdId() + "'";
				}
			
			
				query = "SELECT A.IMG_ID, " +
							"A.IMG_FILENAME, " +
							"A.IMG_TYPE, " +
							"A.IMG_NOTES, " +
							"A.IMG_FILE, " +
							"B.IMG_ALBUM, " +
							"B.PROD_ID," +
							"B.SERV_ID," +
							"B.PRIMARY_IMG " +
						"FROM IMAGE A, " +
						"IMAGE_CATALOG B " +
						"WHERE B.PROD_ID IN(" + productQuery + ") " +
						"AND A.IMG_ID = B.IMG_ID ";
				
				List<SobieImage> sobieImages = getJdbcTemplate().query(query, new RowMappers.SobieImageMapper());
				
				for(int x = 0; x < products.size(); x++){
					ArrayList<SobieImage> productImages = (ArrayList<SobieImage>) products.get(x).getImageCatalog();
					for(int y = 0; y < sobieImages.size(); y++){
						if(sobieImages.get(y).getProdId().equals(products.get(x).getOwnerProdId())){
							productImages.add(sobieImages.get(y));
						}
					}
				}
			}
			return products;

		} catch (Exception e) {

			logger.debug("Error selecting");
			 e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * THis method must be called when searching for a product using the TextBoxSearchController
	 * @param searchString
	 * @return
	 */
	public List<SearchResultsBean> productSearch(String searchString) {
		query = "SELECT PROD_ID, " +
				    "PROD_NAME " +
		 "FROM PRODUCT " +
		 "WHERE (PROD_NAME LIKE '" + searchString + "%' " +
		 "OR PROD_ID LIKE '" + searchString + "%')";
		List<SearchResultsBean> products = (List<SearchResultsBean>)getJdbcTemplate().query(query, new SearchProductMapper());
		
		return products;
	}
	
	/**
	 * THis method must be called when a user has entered some text in the search box, and hits the Go button
	 * @param searchString
	 * @return
	 */
	public List<Product> searchProduct(String searchString) {
		
		query = "SELECT A.PROD_ID, " +
				"A.PROD_NAME, " +
				"A.PROD_SKU_PART_NO, " +
				"A.PROD_CATEGORY, " +
				"A.PROD_RETAIL_PRICE, " +
				"A.PROD_SALE_PRICE, " +
				"A.PROD_WHOLESALE_PRICE, " +
				"A.TAX_CLASS_ID, " +
				"A.PROD_ADD_TO_CART, " +
				"A.PROD_QTY_IMED_AVAIL, " +
				"A.PROD_MIN_PURCHASE_QTY, " +
				"A.PROD_MAX_PURCHASE_QTY, " +
				"A.PROD_FREE_SHIPPING, " +
				"A.PROD_SHIP_SEPERATE, " +
				"A.PROD_SHIPPING_TYPE_ID, " +
				"A.PROD_WEIGHT, " +
				"A.PROD_HEIGHT, " +
				"A.PROD_WIDTH, " +
				"A.PROD_LENGHT, " +
				"A.PROD_SHIPPING_ENABLED, " +
				"A.PROD_DESC, " +
				"A.PROD_FEATURES, " +
				"A.PROD_SPECIFICATION, " +
				"A.PROD_SHIPPING_INFO, " +
				"A.PROD_YOUTUBE_URL, " +
				"A.PROD_COMMENTS_ENABLED, " +
				"A.PROD_QNA_ENABLED, " +
				"A.PROD_BRAND, " +
				"A.PROD_ENABLED, " +
				"B.STORE_NO, " +
				"B.PROD_STATUS, " +
				"D.ACC_NO AS OWNER_ACC_NO, " +
				"B.OWNER_PROD_ID, " +
				"B.PROD_FEATURED, " +
				"IFNULL((SELECT COUNT(OWNER_PROD_ID) " +
				"FROM LIKES " +
				"WHERE OWNER_PROD_ID = B.OWNER_PROD_ID " +
				"GROUP BY OWNER_PROD_ID),0) AS OWNER_PROD_ID_COUNT " +
			 "FROM PRODUCT A, " +
			 	  "STORE_INVENTORY B, " +
			 	  "STORE C, " +
			 	  "STORE D " +
			 "WHERE A.PROD_ID = B.PROD_ID " +
			 "AND B.STORE_NO = C.STORE_NO " + 
			 "AND B.OWNED_ITEM = 'Y' " +
			 "AND D.STORE_NO = B.OWNER_STORE_NO " +
			 "AND B.DELETED <> '1' " +
			 "AND (A.PROD_NAME LIKE '%" + searchString + "%' " +
			 "OR A.PROD_ID LIKE '%" + searchString + "%') ";
	
		@SuppressWarnings("unchecked")
		List<Product> products = getJdbcTemplate().query(query, new RowMappers.ProductMapper());
		
		for(int x = 0; x < products.size(); x++){
		
			query = "SELECT A.IMG_ID, " +
					"A.IMG_FILENAME, " +
					"A.IMG_TYPE, " +
					"A.IMG_NOTES, " +
					"A.IMG_FILE, " +
					"B.IMG_ALBUM, " +
					"B.PROD_ID," +
					"B.SERV_ID," +
					"B.PRIMARY_IMG " +
				"FROM IMAGE A, " +
				"IMAGE_CATALOG B " +
				"WHERE B.PROD_ID = '" + products.get(x).getProdId() + "' " +
				"AND B.IMG_ALBUM = 'PRODUCT_CATALOG' " +
				"AND A.IMG_ID = B.IMG_ID " +
				"AND B.PRIMARY_IMG = 'Y'";
		
			@SuppressWarnings("unchecked")
			SobieImage productImage = (SobieImage) getJdbcTemplate().query(query, new RowMappers.SobieImageMapper()).get(0);
			products.get(x).setProductImage(productImage);
		}
		
		return products;
		
	}
	
	public class SearchProductMapper implements ParameterizedRowMapper<SearchResultsBean> {
		
		public SearchResultsBean mapRow(ResultSet rs, int arg1) throws SQLException {
			SearchResultsBean searchResultsBean = new SearchResultsBean();
			searchResultsBean.setId(rs.getString("PROD_ID")); 
			searchResultsBean.setLabel(rs.getString("PROD_NAME"));
			return searchResultsBean;
		}
	}

	/**
	 * This method must be invoked when all the top selling products must be displayed to a user.
	 * TODO: Must be able to track sales somehow
	 * @param sobieProfile
	 * @return
	 */
	public List<Product> retrieveTopSellingProductList(SobieProfile sobieProfile) {
		
		query = "SELECT A.PROD_ID, " +
						"A.PROD_NAME, " +
						"A.PROD_SKU_PART_NO, " +
						"A.PROD_CATEGORY, " +
						"A.PROD_RETAIL_PRICE, " +
						"A.PROD_SALE_PRICE, " +
						"A.PROD_WHOLESALE_PRICE, " +
						"A.TAX_CLASS_ID, " +
						"A.PROD_ADD_TO_CART, " +
						"A.PROD_QTY_IMED_AVAIL, " +
						"A.PROD_MIN_PURCHASE_QTY, " +
						"A.PROD_MAX_PURCHASE_QTY, " +
						"A.PROD_FREE_SHIPPING, " +
						"A.PROD_SHIP_SEPERATE, " +
						"A.PROD_SHIPPING_TYPE_ID, " +
						"A.PROD_WEIGHT, " +
						"A.PROD_HEIGHT, " +
						"A.PROD_WIDTH, " +
						"A.PROD_LENGHT, " +
						"A.PROD_SHIPPING_ENABLED, " +
						"A.PROD_DESC, " +
						"A.PROD_FEATURES, " +
						"A.PROD_SPECIFICATION, " +
						"A.PROD_SHIPPING_INFO, " +
						"A.PROD_YOUTUBE_URL, " +
						"A.PROD_COMMENTS_ENABLED, " +
						"A.PROD_QNA_ENABLED, " +
						"A.PROD_BRAND, " +
						"A.PROD_ENABLED, " +
						"B.STORE_NO AS SELLER_STORE_NO, " +
						"B.OWNER_STORE_NO, " +
						"B.OWNED_ITEM, " +
						"C.IMG_ID, " +
						"C.IMG_FILENAME, " +
						"C.IMG_TYPE, " +
						"C.IMG_NOTES, " +
						"C.IMG_FILE, " +
						"D.PRIMARY_IMG, " +
						"B.OWNER_PROD_ID, " +
						"IFNULL((SELECT COUNT(OWNER_PROD_ID) " +
						"FROM LIKES " +
						"WHERE OWNER_PROD_ID = B.OWNER_PROD_ID " +
						"GROUP BY OWNER_PROD_ID),0) AS OWNER_PROD_ID_COUNT " +
					"FROM PRODUCT A, " +
					"STORE_INVENTORY B, " +
					"IMAGE C, " +
					"IMAGE_CATALOG D " +
					"WHERE A.PROD_ID = B.PROD_ID " +
					"AND A.PROD_ID = D.PROD_ID " +
					"AND D.IMG_ALBUM = 'PRODUCT_CATALOG' " +
					"AND D.PRIMARY_IMG = 'Y' " +
					"AND D.IMG_ID = C.IMG_ID " +
					"AND B.PROD_STATUS <> '0' " +
					"ORDER BY RAND() " +
					"LIMIT 10";
		
		@SuppressWarnings("unchecked")
		List<Product> products = getJdbcTemplate().query(query, new RowMappers.ProductListMapper());
		
		return products;
	}

	public List<String> getProductCategories() {
		
		query = "SELECT PROD_CATEGORY " +
				"FROM PRODUCT_CATEGORY ";
		
		List<String> productCategory = (List<String>)getJdbcTemplate().query(query, new ProductCategoryMapper());
		return productCategory;
	}
	
	public class ProductCategoryMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String storeNo = rs.getString("PROD_CATEGORY");
			return storeNo;
		}
	}

	/**
	 * This method is invoked to remove a product from either a suppliers store inventory or a sellers network product inventory
	 * @param prodId
	 * @param storeNo
	 */
	public void deleteProduct(final String prodId, final String storeNo) {
		
		query = "UPDATE store_inventory " +
    			"SET DELETED = '1' " +
    			"WHERE STORE_NO = ? " +
    			"AND PROD_ID = ?;";
    	
    	getJdbcTemplate().update(query, new Object[] {storeNo, prodId});
		
	}

	public List<MerchantDetails> retrieveProductMerchants(final String prodId) {
		query = "SELECT A.STORE_NO," +
						"B.ACC_NO " +
				"FROM STORE_INVENTORY A," +
					 "STORE B " +
				"WHERE A.OWNER_PROD_ID = '" + prodId + "' " +
				"AND A.DELETED <> '1' " +
				"AND A.PROD_ID <> '" + prodId + "' " +
				"AND A.STORE_NO = B.STORE_NO";
	
		@SuppressWarnings("unchecked")
		List<MerchantDetails> merchantStoreNos = (List<MerchantDetails>) getJdbcTemplate().query(query, new RowMappers.MerchantStoreListMapper());
		return merchantStoreNos;
	}

	public Product retrieveSupplierProduct(String prodId) {
		try{
			query = "SELECT A.PROD_ID, " +
						"A.PROD_NAME, " +
						"A.PROD_SKU_PART_NO, " +
						"A.PROD_CATEGORY, " +
						"A.PROD_RETAIL_PRICE, " +
						"A.PROD_SALE_PRICE, " +
						"A.PROD_WHOLESALE_PRICE, " +
						"A.TAX_CLASS_ID, " +
						"A.PROD_ADD_TO_CART, " +
						"A.PROD_QTY_IMED_AVAIL, " +
						"A.PROD_MIN_PURCHASE_QTY, " +
						"A.PROD_MAX_PURCHASE_QTY, " +
						"A.PROD_FREE_SHIPPING, " +
						"A.PROD_SHIP_SEPERATE, " +
						"A.PROD_SHIPPING_TYPE_ID, " +
						"A.PROD_WEIGHT, " +
						"A.PROD_HEIGHT, " +
						"A.PROD_WIDTH, " +
						"A.PROD_LENGHT, " +
						"A.PROD_SHIPPING_ENABLED, " +
						"A.PROD_DESC, " +
						"A.PROD_FEATURES, " +
						"A.PROD_SPECIFICATION, " +
						"A.PROD_SHIPPING_INFO, " +
						"A.PROD_YOUTUBE_URL, " +
						"A.PROD_COMMENTS_ENABLED, " +
						"A.PROD_QNA_ENABLED, " +
						"A.PROD_BRAND, " +
						"A.PROD_ENABLED, " +
						"B.STORE_NO, " +
						"B.PROD_STATUS, " +
						"C.ACC_NO AS OWNER_ACC_NO, " +
						"B.OWNER_PROD_ID " +
						"B.PROD_FEATURED, " +
						"E.OWNER_PROD_ID_COUNT " +
					 "FROM PRODUCT A, " +
				 	 "STORE_INVENTORY B, " +
				 	 "STORE C, " +
				 	 "(SELECT COUNT(OWNER_PROD_ID) AS OWNER_PROD_ID_COUNT " +
					 "FROM LIKES " +
					 "GROUP BY OWNER_PROD_ID) E " +
					 "WHERE A.PROD_ID = '" + prodId + "' " +
					 "AND A.PROD_ID = B.PROD_ID " +
					 "AND B.STORE_NO = C.STORE_NO " +
					 "AND B.OWNER_PROD_ID = E.OWNER_PROD_ID_COUNT";
			
			@SuppressWarnings("unchecked")
			List<Product> products = getJdbcTemplate().query(query, new RowMappers.ProductMapper());
			
			return products.get(0);

		} catch (Exception e) {

			logger.debug("Error selecting");
			 e.printStackTrace();
		}
		return null;
	}

	public void addProductHistoryRecord(final ProductHistory history) {
		final SobieUtils utils = new SobieUtils();
		
		query = "INSERT INTO PRODUCT_HISTORY(PROD_HIST_ID, " + 
				"PROD_ID, " +
				"DATETIME, " +
				"FIELD_NAME, " +
				"FIELD_VALUE_FROM, " +
				"FIELD_VALUE_TO) " +
				"VALUES(?, ?, ?, ?, ?, ?);";
		
		try {
			synchronized(this) {
				getJdbcTemplate().update(new PreparedStatementCreator() {	
		
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					   PreparedStatement statement = con.prepareStatement(query);
					   statement.setString(1, utils.createProdHistoryId()); 
					   statement.setString(2, history.getProdId()); 
					   statement.setDate(3, history.getDateTime()); 
					   statement.setString(4, history.getFieldName()); 
					   statement.setString(5, history.getFieldValueFrom()); 
					   statement.setString(6, history.getFieldValueTo()); 
					   return statement;
					}
				});
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This method is called after a seller has edited a product from their Inventory Network Product Dashboard
	 * @param selectedProduct
	 */
	public void updateProduct(final Product selectedProduct, final String accNo) {
		
		query = "UPDATE PRODUCT " +
				"SET PROD_NAME = ?, " +
					"PROD_SKU_PART_NO = ?, " +
					"PROD_CATEGORY = ?, " +
					"PROD_RETAIL_PRICE = ?, " +
					"PROD_SALE_PRICE = ?, " +
					"PROD_WHOLESALE_PRICE = ?, " +
					"TAX_CLASS_ID = ?, " +
					"PROD_ADD_TO_CART = ?, " +
					"PROD_QTY_IMED_AVAIL = ?, " +
					"PROD_MIN_PURCHASE_QTY = ?, " +
					"PROD_MAX_PURCHASE_QTY = ?, " +
					"PROD_FREE_SHIPPING = ?, " +
					"PROD_SHIP_SEPERATE = ?, " +
					"PROD_SHIPPING_TYPE_ID = ?, " +
					"PROD_WEIGHT = ?, " +
					"PROD_HEIGHT = ?, " +
					"PROD_WIDTH = ?, " +
					"PROD_LENGHT = ?, " +
					"PROD_SHIPPING_ENABLED = ?, " +
					"PROD_DESC = ?, " +
					"PROD_FEATURES = ?, " +
					"PROD_SPECIFICATION = ?, " +
					"PROD_SHIPPING_INFO = ?, " +
					"PROD_YOUTUBE_URL = ?, " +
					"PROD_COMMENTS_ENABLED = ?, " +
					"PROD_QNA_ENABLED = ?, " +
					"PROD_BRAND = ?, " +
					"PROD_ENABLED = ? " +
				"WHERE PROD_ID = '" + selectedProduct.getProdId() + "';";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, selectedProduct.getProdName()); 
                       statement.setString(2, selectedProduct.getProdSkuPartNo()); 
                       statement.setString(3, selectedProduct.getProdCategory()); 
                       statement.setDouble(4, selectedProduct.getProdRetailPrice()); 
                       statement.setDouble(5, selectedProduct.getProdSalePrice()); 
                       statement.setDouble(6, selectedProduct.getProdWholesalePrice()); 
                       statement.setString(7, selectedProduct.getTaxClassId()); 
                       statement.setBoolean(8, selectedProduct.isProdAddToCart()); 
                       statement.setInt(9, selectedProduct.getProdQtyImedAvail()); 
                       statement.setInt(10, selectedProduct.getProdMinPurchaseQty()); 
                       statement.setInt(11, selectedProduct.getProdMaxPurchaseQty()); 
                       statement.setBoolean(12, selectedProduct.isProdFreeShipping()); 
                       statement.setBoolean(13, selectedProduct.isProdShipSeperate()); 
                       statement.setString(14, selectedProduct.getProdShippingTypeId()); 
                       statement.setDouble(15, selectedProduct.getProdWeight()); 
                       statement.setDouble(16, selectedProduct.getProdHeight()); 
                       statement.setDouble(17, selectedProduct.getProdWidth()); 
                       statement.setDouble(18, selectedProduct.getProdLenght()); 
                       statement.setBoolean(19, selectedProduct.isProdShippingEnabled()); 
                       statement.setString(20, selectedProduct.getProdDesc()); 
                       statement.setString(21, selectedProduct.getProdFeatures()); 
                       statement.setString(22, selectedProduct.getProdSpecification()); 
                       statement.setString(23, selectedProduct.getProdShippingInfo()); 
                       statement.setString(24, selectedProduct.getProdYouTubeUrl()); 
                       statement.setBoolean(25, selectedProduct.isProdCommentsEnabled()); 
                       statement.setBoolean(26, selectedProduct.isProdQnaEnabled()); 
                       statement.setString(27, selectedProduct.getProdBrand());
                       statement.setBoolean(28, selectedProduct.isProdEnabled());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        query = "UPDATE STORE_INVENTORY " +
				"SET PROD_FEATURED = ? " +
				"WHERE PROD_ID = '" + selectedProduct.getProdId() + "' " +
				"AND STORE_NO = '" + selectedProduct.getSellerStoreNo() + "'";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setBoolean(1, selectedProduct.isProdFeatured()); 
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        for(int x = 0; x < selectedProduct.getImageCatalog().size(); x++){
        	
        	if(selectedProduct.getImageCatalog().get(x).isUpdateImage()){
	        	final int count = x;
	        	/**
	             * Insert a record for each product owned image to the 
	             */
	    		
	    		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, ACC_NO, PROD_ID, PRIMARY_IMG) values (?, ?, ?, ?, ?)";
	
	            try {
	                synchronized(this) {
	                    getJdbcTemplate().update(new PreparedStatementCreator() {
	    					
	    					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                           PreparedStatement statement = con.prepareStatement(query);
	                           statement.setString(1, selectedProduct.getImageCatalog().get(count).getImgId());
	                           statement.setString(2, "PRODUCT_CATALOG");
	                           statement.setString(3, accNo);
	                           statement.setString(4, selectedProduct.getProdId());
	                           statement.setString(5, selectedProduct.getImageCatalog().get(count).getPrimaryImage());
	                           return statement;
	    					}
	                    });
	                }
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        	
		        /**
		         * Insert image that is related to the Product that was inserted into the PRODUCT table
		         */
				
				query = "insert into image (IMG_ID, IMG_FILENAME, IMG_TYPE, IMG_NOTES, IMG_FILE) values (?, ?, ?, ?, ?)";
		
		        try {
		            synchronized(this) {
		                getJdbcTemplate().update(new PreparedStatementCreator() {
							
							public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		                       PreparedStatement statement = con.prepareStatement(query);
		                       statement.setString(1, selectedProduct.getImageCatalog().get(count).getImgId()); 
		                       statement.setString(2, selectedProduct.getImageCatalog().get(count).getImgFilename());
		                       statement.setString(3, selectedProduct.getImageCatalog().get(count).getImgNotes());
		                       statement.setString(4, selectedProduct.getImageCatalog().get(count).getImgType());
		                       statement.setBytes(5, selectedProduct.getImageCatalog().get(count).getImageInBytes());
		                       return statement;
							}
		                });
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
	        }
        }
		
	}

	public List<ProductHistory> getProductHistory(String prodId) {
		
		try{
			query = "SELECT PROD_HIST_ID, " + 
						"PROD_ID, " +
						"DATETIME, " +
						"FIELD_NAME, " +
						"FIELD_VALUE_FROM, " +
						"FIELD_VALUE_TO " +
				 "FROM PRODUCT_HISTORY " +
				 "WHERE PROD_ID = '" + prodId + "';";
		
			@SuppressWarnings("unchecked")
			List<ProductHistory> productHistories = getJdbcTemplate().query(query, new RowMappers.ProductHistoryMapper());
			
			return productHistories;
		
		} catch (Exception e) {
		
			logger.debug("Error selecting");
			 e.printStackTrace();
		}
		return null;
	}

	public String getProductStatus(String msgRpProdId) {
		
		query = "SELECT PROD_STATUS FROM STORE_INVENTORY WHERE PROD_ID = '" + msgRpProdId + "'";
        final String prodStatus = (String)getJdbcTemplate().query(query, new ProductStatusMapper()).get(0);
        return prodStatus;
	}
	
	public class ProductStatusMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String prodStatus = rs.getString("PROD_STATUS");
			return prodStatus;
		}
	}

	public List<Product> retrieveStoreFeaturedProducts(String storeNo) {
		
		query = "SELECT A.PROD_ID, " +
				"A.PROD_NAME, " +
				"A.PROD_SKU_PART_NO, " +
				"A.PROD_CATEGORY, " +
				"A.PROD_RETAIL_PRICE, " +
				"A.PROD_SALE_PRICE, " +
				"A.PROD_WHOLESALE_PRICE, " +
				"A.TAX_CLASS_ID, " +
				"A.PROD_ADD_TO_CART, " +
				"A.PROD_QTY_IMED_AVAIL, " +
				"A.PROD_MIN_PURCHASE_QTY, " +
				"A.PROD_MAX_PURCHASE_QTY, " +
				"A.PROD_FREE_SHIPPING, " +
				"A.PROD_SHIP_SEPERATE, " +
				"A.PROD_SHIPPING_TYPE_ID, " +
				"A.PROD_WEIGHT, " +
				"A.PROD_HEIGHT, " +
				"A.PROD_WIDTH, " +
				"A.PROD_LENGHT, " +
				"A.PROD_SHIPPING_ENABLED, " +
				"A.PROD_DESC, " +
				"A.PROD_FEATURES, " +
				"A.PROD_SPECIFICATION, " +
				"A.PROD_SHIPPING_INFO, " +
				"A.PROD_YOUTUBE_URL, " +
				"A.PROD_COMMENTS_ENABLED, " +
				"A.PROD_QNA_ENABLED, " +
				"A.PROD_BRAND, " +
				"A.PROD_ENABLED, " +
				"B.STORE_NO, " +
				"B.PROD_STATUS, " +
				"D.ACC_NO AS OWNER_ACC_NO, " +
				"B.OWNER_PROD_ID, " +
				"B.PROD_FEATURED, " +
				"IFNULL((SELECT COUNT(OWNER_PROD_ID) " +
				"FROM LIKES " +
				"WHERE OWNER_PROD_ID = B.OWNER_PROD_ID " +
				"GROUP BY OWNER_PROD_ID),0) AS OWNER_PROD_ID_COUNT " +
			 "FROM PRODUCT A, " +
		 	 "STORE_INVENTORY B, " +
		 	 "STORE C, " +
		 	 "STORE D " +
			 "WHERE A.PROD_ID = B.PROD_ID " +
			 "AND B.STORE_NO = C.STORE_NO " + 
			 "AND C.STORE_NO = '" + storeNo + "' " +
			 "AND B.PROD_FEATURED = '1' " +
			 "AND B.OWNED_ITEM = 'Y' " +
			 "AND D.STORE_NO = B.OWNER_STORE_NO " +
			 "AND B.DELETED <> '1' " +
			 "ORDER BY RAND() " +
			 "LIMIT 5";
	
	@SuppressWarnings("unchecked")
	List<Product> featuredProducts = getJdbcTemplate().query(query, new RowMappers.ProductMapper());
	
	query = "SELECT A.IMG_ID, " +
				"A.IMG_FILENAME, " +
				"A.IMG_TYPE, " +
				"A.IMG_NOTES, " +
				"A.IMG_FILE, " +
				"B.IMG_ALBUM, " +
				"B.PROD_ID," +
				"B.SERV_ID," +
				"B.PRIMARY_IMG " +
			"FROM IMAGE A, " +
			"IMAGE_CATALOG B, " +
			"STORE C, " +
			"STORE_INVENTORY D " +
			"WHERE B.ACC_NO = C.ACC_NO " +
			"AND C.STORE_NO = '" + storeNo + "' " +
			"AND B.IMG_ALBUM = 'PRODUCT_CATALOG' " +
			"AND A.IMG_ID = B.IMG_ID " +
			"AND B.PRIMARY_IMG = 'Y' " +
			"AND C.STORE_NO = D.STORE_NO " +
			"AND B.PROD_ID = D.PROD_ID " +
			"AND D.PROD_FEATURED = '1'";
	
	@SuppressWarnings("unchecked")
	List<SobieImage> sobieImages = getJdbcTemplate().query(query, new RowMappers.SobieImageMapper());
	
	for(int x = 0; x < featuredProducts.size(); x++){
		ArrayList<SobieImage> productImages = (ArrayList<SobieImage>) featuredProducts.get(x).getImageCatalog();
		for(int y = 0; y < sobieImages.size(); y++){
			if(sobieImages.get(y).getProdId().equals(featuredProducts.get(x).getProdId())){
				productImages.add(sobieImages.get(y));
			}
		}
	}
		return featuredProducts;
	}
	
	public List<SobieImage> retrieveImageCatalog(String prodId){
		query = "SELECT A.IMG_ID, " +
				"A.IMG_FILENAME, " +
				"A.IMG_TYPE, " +
				"A.IMG_NOTES, " +
				"A.IMG_FILE, " +
				"B.IMG_ALBUM, " +
				"B.PROD_ID," +
				"B.SERV_ID," +
				"B.PRIMARY_IMG " +
			"FROM IMAGE A, " +
			"IMAGE_CATALOG B " +
			"WHERE B.PROD_ID = '" + prodId + "' " +
			"AND B.IMG_ALBUM = 'PRODUCT_CATALOG' " +
			"AND A.IMG_ID = B.IMG_ID ";
	
	@SuppressWarnings("unchecked")
	List<SobieImage> imageCatalog = getJdbcTemplate().query(query, new RowMappers.SobieImageMapper());
	return imageCatalog;
	}
}
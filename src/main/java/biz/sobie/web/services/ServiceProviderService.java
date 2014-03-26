package biz.sobie.web.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import biz.sobie.web.beans.Service;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.beans.Store;
import biz.sobie.web.utils.SobieUtils;

public class ServiceProviderService extends JdbcDaoSupport {

	 String query = null;
	 
		/**
		 * This method is invoked to remove a product from either a suppliers store inventory or a sellers network product inventory
		 * @param prodId
		 * @param storeNo
		 */
		public void deleteService(final String servId, final String storeNo) {
			
			query = "UPDATE store_inventory " +
	    			"SET DELETED = '1' " +
	    			"WHERE STORE_NO = ? " +
	    			"AND SERV_ID = ?;";
	    	
	    	getJdbcTemplate().update(query, new Object[] {storeNo, servId});
			
		}
	 
	 public List<Service> retrieveSellerServices(String storeNo){
		 
		 query = "SELECT A.SERV_ID, " +
						"A.SERV_NAME, " +
						"A.SERV_REF, " +
						"A.SERV_CATEGORY, " +
						"A.SERV_ENABLED, " +
						"A.SERV_FEATURED, " +
						"A.SERV_FIXED_PRICE, " +
						"A.SERV_FIXED_RATE_TYPE, " +
						"A.SERV_FIXED_RATE_UNIT, " +
						"A.SERV_TAX_CLASS_ID, " +
						"A.SERV_QUATATION_REQ_ENABLED, " +
						"A.SERV_OH_DAYS_MON, " +
						"A.SERV_OH_DAYS_TUE, " +
						"A.SERV_OH_DAYS_WED, " +
						"A.SERV_OH_DAYS_THU, " +
						"A.SERV_OH_DAYS_FRI, " +
						"A.SERV_OH_DAYS_SAT, " +
						"A.SERV_OH_DAYS_SUN, " +
						"A.SERV_TIME_START, " +
						"A.SERV_TIME_END, " +
						"A.SERV_YOUTUBE_URL, " +
						"A.SERV_ALT_TEXT, " +
						"A.SERV_DESC, " +
						"A.SERV_FEATURES, " +
						"A.SERV_SPECIFICATION, " +
						"A.SERV_COMMENTS_ENABLED, " +
						"A.SERV_QNA_ENABLED " +
				 "FROM SERVICE A, " +
				 	  "STORE_INVENTORY B " +
				 "WHERE A.SERV_ID = B.SERV_ID " +
				 "AND B.STORE_NO = '" + storeNo + "' " +
				 "AND B.OWNED_ITEM = 'Y' " +
				 "AND B.DELETED <> '1'";
		
			@SuppressWarnings("unchecked")
			List<Service> services = getJdbcTemplate().query(query, new RowMappers.ServiceProviderListMapper());
			
			query = "SELECT A.IMG_ID, " +
						"A.IMG_FILENAME, " +
						"A.IMG_TYPE, " +
						"A.IMG_NOTES, " +
						"A.IMG_FILE, " +
						"B.IMG_ALBUM, " +
						"B.PROD_ID," +
						"B.SERV_ID, " +
						"B.PRIMARY_IMG " +
					"FROM IMAGE A, " +
					"IMAGE_CATALOG B " +
					"WHERE B.STORE_NO = '" + storeNo + "' " +
					"AND B.IMG_ALBUM = 'SERVICE_CATALOG' " +
					"AND A.IMG_ID = B.IMG_ID ";
			
			@SuppressWarnings("unchecked")
			List<SobieImage> sobieImages = getJdbcTemplate().query(query, new RowMappers.SobieImageMapper());
			
			for(int x = 0; x < services.size(); x++){
				ArrayList<SobieImage> serviceImages = (ArrayList<SobieImage>) services.get(x).getImageCatalog();
				for(int y = 0; y < sobieImages.size(); y++){
					if(sobieImages.get(y).getServId().equals(services.get(x).getServId())){
						serviceImages.add(sobieImages.get(y));
					}
				}
			}
		 return services;
	 }
	 
	 public void addServiceToSellerShop(final Service service, final String storeNo, final String accNo) {
	    	
        SobieUtils sobieUtils = new SobieUtils();
		final String servId = sobieUtils.createServiceId();
        /**
		 * Insert new Product record into PRODUCT table
		 */
		
		query = "INSERT INTO SERVICE(SERV_ID, " +
									"SERV_NAME, " +
									"SERV_REF, " +
									"SERV_CATEGORY, " +
									"SERV_ENABLED, " +
									"SERV_FEATURED, " +
									"SERV_FIXED_PRICE, " +
									"SERV_FIXED_RATE_TYPE, " +
									"SERV_FIXED_RATE_UNIT, " +
									"SERV_TAX_CLASS_ID, " +
									"SERV_QUATATION_REQ_ENABLED, " +
									"SERV_OH_DAYS_MON, " +
									"SERV_OH_DAYS_TUE, " +
									"SERV_OH_DAYS_WED, " +
									"SERV_OH_DAYS_THU, " +
									"SERV_OH_DAYS_FRI, " +
									"SERV_OH_DAYS_SAT, " +
									"SERV_OH_DAYS_SUN, " +
									"SERV_TIME_START, " +
									"SERV_TIME_END, " +
									"SERV_YOUTUBE_URL, " +
									"SERV_ALT_TEXT, " +
									"SERV_DESC, " +
									"SERV_FEATURES, " +
									"SERV_SPECIFICATION, " +
									"SERV_COMMENTS_ENABLED, " +
									"SERV_QNA_ENABLED) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, servId); 
                       statement.setString(2, service.getServName()); 
                       statement.setString(3, service.getServRef()); 
                       statement.setString(4, service.getServCategory()); 
                       statement.setBoolean(5, service.isServEnabled()); 
                       statement.setBoolean(6, service.isServFeatured()); 
                       statement.setDouble(7, service.getServFixedPrice()); 
                       statement.setString(8, service.getServFixedRateType()); 
                       statement.setDouble(9, service.getServFixedRateUnit()); 
                       statement.setString(10, service.getServTaxClassId()); 
                       statement.setBoolean(11, service.isServQuatationReqEnabled()); 
                       statement.setBoolean(12, service.isServOhDaysMon()); 
                       statement.setBoolean(13, service.isServOhDaysTue()); 
                       statement.setBoolean(14, service.isServOhDaysWed()); 
                       statement.setBoolean(15, service.isServOhDaysThu()); 
                       statement.setBoolean(16, service.isServOhDaysFri()); 
                       statement.setBoolean(17, service.isServOhDaysSat()); 
                       statement.setBoolean(18, service.isServOhDaysSun());
                       statement.setTime(19, service.getServStartTime());
                       statement.setTime(20, service.getServEndTime());
                       statement.setString(21, service.getServYoutubeUrl()); 
                       statement.setString(22, service.getServAltText()); 
                       statement.setString(23, service.getServDesc()); 
                       statement.setString(24, service.getServFeatures()); 
                       statement.setString(25, service.getServSpecification());
                       statement.setBoolean(26, service.isServCommentsEnabled()); 
                       statement.setBoolean(27, service.isServQnaEnabled());
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
		
		query = "insert into store_inventory (STORE_NO, PROD_ID, SERV_ID, OWNED_ITEM, PROD_STATUS) values (?, ?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, storeNo); 
                       statement.setString(2, "0");
                       statement.setString(3, servId);
                       statement.setString(4, "Y");
                       statement.setString(5, "0");
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
        	 ex.printStackTrace();
        }
        
        for(int x = 0; x < service.getImageCatalog().size(); x++){
        	
        	final int count = x;
        	/**
             * Insert a record for each product owned image to the 
             */
    		
    		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, ACC_NO, SERV_ID, STORE_NO, PRIMARY_IMG) values (?, ?, ?, ?, ?, ?)";

            try {
                synchronized(this) {
                    getJdbcTemplate().update(new PreparedStatementCreator() {
    					
    					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                           PreparedStatement statement = con.prepareStatement(query);
                           statement.setString(1, service.getImageCatalog().get(count).getImgId());
                           statement.setString(2, "SERVICE_CATALOG");
                           statement.setString(3, accNo);
                           statement.setString(4, service.getServId());
                           statement.setString(5, storeNo);
                           statement.setString(6, service.getImageCatalog().get(count).getPrimaryImage());
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
	                       statement.setString(1, service.getImageCatalog().get(count).getImgId()); 
	                       statement.setString(2, service.getImageCatalog().get(count).getImgFilename());
	                       statement.setString(3, service.getImageCatalog().get(count).getImgNotes());
	                       statement.setString(4, service.getImageCatalog().get(count).getImgType());
	                       statement.setBytes(5, service.getImageCatalog().get(count).getImageInBytes());
	                       return statement;
						}
	                });
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
        }
        
    }

	public void updateServiceInSellerShop(final Service selectedService, final String storeNo, final String accNo) {

		query = "UPDATE SERVICE " +
				"SET SERV_NAME = ? , " +
				"SERV_REF = ? , " +
				"SERV_CATEGORY = ? , " +
				"SERV_ENABLED = ? , " +
				"SERV_FEATURED = ? , " +
				"SERV_FIXED_PRICE = ? , " +
				"SERV_FIXED_RATE_TYPE = ? , " +
				"SERV_FIXED_RATE_UNIT = ? , " +
				"SERV_TAX_CLASS_ID = ? , " +
				"SERV_QUATATION_REQ_ENABLED = ? , " +
				"SERV_OH_DAYS_MON = ? , " +
				"SERV_OH_DAYS_TUE = ? , " +
				"SERV_OH_DAYS_WED = ? , " +
				"SERV_OH_DAYS_THU = ? , " +
				"SERV_OH_DAYS_FRI = ? , " +
				"SERV_OH_DAYS_SAT = ? , " +
				"SERV_OH_DAYS_SUN = ? , " +
				"SERV_TIME_START = ? , " +
				"SERV_TIME_END = ? , " +
				"SERV_YOUTUBE_URL = ? , " +
				"SERV_ALT_TEXT = ? , " +
				"SERV_DESC = ? , " +
				"SERV_FEATURES = ? , " +
				"SERV_SPECIFICATION = ? , " +
				"SERV_COMMENTS_ENABLED = ? , " +
				"SERV_QNA_ENABLED = ? " +
				"WHERE SERV_ID = '" + selectedService.getServId() + "';";
			
		try {
			 synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
					   statement.setString(1, selectedService.getServName()); 
					   statement.setString(2, selectedService.getServRef()); 
					   statement.setString(3, selectedService.getServCategory()); 
					   statement.setBoolean(4, selectedService.isServEnabled()); 
					   statement.setBoolean(5, selectedService.isServFeatured()); 
					   statement.setDouble(6, selectedService.getServFixedPrice()); 
					   statement.setString(7, selectedService.getServFixedRateType()); 
					   statement.setDouble(8, selectedService.getServFixedRateUnit()); 
					   statement.setString(9, selectedService.getServTaxClassId()); 
					   statement.setBoolean(10, selectedService.isServQuatationReqEnabled()); 
					   statement.setBoolean(11, selectedService.isServOhDaysMon()); 
					   statement.setBoolean(12, selectedService.isServOhDaysTue()); 
					   statement.setBoolean(13, selectedService.isServOhDaysWed()); 
					   statement.setBoolean(14, selectedService.isServOhDaysThu()); 
					   statement.setBoolean(15, selectedService.isServOhDaysFri()); 
					   statement.setBoolean(16, selectedService.isServOhDaysSat()); 
					   statement.setBoolean(17, selectedService.isServOhDaysSun());
					   statement.setTime(18, selectedService.getServStartTime());
					   statement.setTime(19, selectedService.getServEndTime());
					   statement.setString(20, selectedService.getServYoutubeUrl()); 
					   statement.setString(21, selectedService.getServAltText()); 
					   statement.setString(22, selectedService.getServDesc()); 
					   statement.setString(23, selectedService.getServFeatures()); 
					   statement.setString(24, selectedService.getServSpecification());
					   statement.setBoolean(25, selectedService.isServCommentsEnabled()); 
					   statement.setBoolean(26, selectedService.isServQnaEnabled());
					   return statement;
					}
                });
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		for(int x = 0; x < selectedService.getImageCatalog().size(); x++){
        	
        	if(selectedService.getImageCatalog().get(x).isUpdateImage()){
				final int count = x;
	        	/**
	             * Insert a record for each product owned image to the 
	             */
	    		
	    		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, ACC_NO, SERV_ID, STORE_NO, PRIMARY_IMG) values (?, ?, ?, ?, ?, ?)";
	
	            try {
	                synchronized(this) {
	                    getJdbcTemplate().update(new PreparedStatementCreator() {
	    					
	    					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                           PreparedStatement statement = con.prepareStatement(query);
	                           statement.setString(1, selectedService.getImageCatalog().get(count).getImgId());
	                           statement.setString(2, "SERVICE_CATALOG");
	                           statement.setString(3, accNo);
	                           statement.setString(4, selectedService.getServId());
	                           statement.setString(5, storeNo);
	                           statement.setString(6, selectedService.getImageCatalog().get(count).getPrimaryImage());
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
		                       statement.setString(1, selectedService.getImageCatalog().get(count).getImgId()); 
		                       statement.setString(2, selectedService.getImageCatalog().get(count).getImgFilename());
		                       statement.setString(3, selectedService.getImageCatalog().get(count).getImgNotes());
		                       statement.setString(4, selectedService.getImageCatalog().get(count).getImgType());
		                       statement.setBytes(5, selectedService.getImageCatalog().get(count).getImageInBytes());
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

	public List<Service> retrieveTopSellingServiceList(SobieProfile sobieProfile) {
		
		query = "SELECT A.SERV_ID, " +
				"A.SERV_NAME, " +
				"A.SERV_REF, " +
				"A.SERV_CATEGORY, " +
				"A.SERV_ENABLED, " +
				"A.SERV_FEATURED, " +
				"A.SERV_FIXED_PRICE, " +
				"A.SERV_FIXED_RATE_TYPE, " +
				"A.SERV_FIXED_RATE_UNIT, " +
				"A.SERV_TAX_CLASS_ID, " +
				"A.SERV_QUATATION_REQ_ENABLED, " +
				"A.SERV_OH_DAYS_MON, " +
				"A.SERV_OH_DAYS_TUE, " +
				"A.SERV_OH_DAYS_WED, " +
				"A.SERV_OH_DAYS_THU, " +
				"A.SERV_OH_DAYS_FRI, " +
				"A.SERV_OH_DAYS_SAT, " +
				"A.SERV_OH_DAYS_SUN, " +
				"A.SERV_TIME_START, " +
				"A.SERV_TIME_END, " +
				"A.SERV_YOUTUBE_URL, " +
				"A.SERV_ALT_TEXT, " +
				"A.SERV_DESC, " +
				"A.SERV_FEATURES, " +
				"A.SERV_SPECIFICATION, " +
				"A.SERV_COMMENTS_ENABLED, " +
				"A.SERV_QNA_ENABLED " +
		 "FROM SERVICE A, " +
		 	  "STORE_INVENTORY B " +
		 "WHERE A.SERV_ID = B.SERV_ID " +
		 "AND B.DELETED <> '1' " +
		 "AND A.SERV_ENABLED = '1'";
		
		@SuppressWarnings("unchecked")
		List<Service> services = getJdbcTemplate().query(query, new RowMappers.ServiceProviderListMapper());
		
		String tempServIds = "'" + services.get(0).getServId() + "'";
		for(int x = 1; x < services.size(); x++){
			if(x+1 < services.size()){
				tempServIds = tempServIds + ", ";
			}
			tempServIds = tempServIds + "'" + services.get(x).getServId() + "'";
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
			"WHERE B.SERV_ID IN(" + tempServIds + ") " +
			"AND B.IMG_ALBUM = 'SERVICE_CATALOG' " +
			"AND A.IMG_ID = B.IMG_ID ";
	
		@SuppressWarnings("unchecked")
		List<SobieImage> sobieImages = getJdbcTemplate().query(query, new RowMappers.SobieImageMapper());
		
		for(int x = 0; x < services.size(); x++){
			ArrayList<SobieImage> serviceImages = (ArrayList<SobieImage>) services.get(x).getImageCatalog();
			for(int y = 0; y < sobieImages.size(); y++){
				if(sobieImages.get(y).getServId().equals(services.get(x).getServId())){
					serviceImages.add(sobieImages.get(y));
				}
			}
		}
		
		return services;
		
	}
	
}

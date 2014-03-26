package biz.sobie.web.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.beans.Store;
import biz.sobie.web.store.StoreContentPage;

public class StoreService extends JdbcDaoSupport {
    
	String query = null;

	public List<Store> retrieveTopSellingStoreList(SobieProfile sobieProfile) {

			query = "SELECT B.ACC_NO, " +
								"A.IMG_FILE, " +
								"B.STORE_NAME, " +
								"B.STORE_NO " +
							"FROM IMAGE A, " +
								"STORE B, " +
								"ACCOUNT C, " +
								"IMAGE_CATALOG D " +
							"WHERE A.IMG_ID = D.IMG_ID " +
							"AND D.IMG_ALBUM = 'STORE_LOGO_CATALOG' " +
							"AND D.PRIMARY_IMG = 'Y' "+
							"AND B.ACC_NO = C.ACC_NO " +
							"AND D.ACC_NO = B.ACC_NO " +
							"ORDER BY RAND() " +
							"LIMIT 10";

			@SuppressWarnings("unchecked")
			List<Store> stores = getJdbcTemplate().query(query, new RowMappers.StoreMapper());
			return stores;
	}	
	
	/**
	 * Used to retrieve a Seller or Suppliers Store Settings information that is to be displayed under the Store Settings Dashboard
	 * @param sobieProfile
	 * @return
	 */
	public Store retrieveStoreSettings(SobieProfile sobieProfile) {

		query = "SELECT STORE_NO, " +
					"ACC_NO, " +
					"STORE_NAME, " +
					"STORE_DESC, " +
					"STORE_TEL, " +
					"STORE_FAX, " +
					"STORE_ADMIN_EMAIL, " +
					"STORE_SALES_DEP_EMAIL, " +
					"STORE_DEP_TEL, " +
					"STORE_CUST_SERV_EMAIL, " +
					"STORE_CUST_SERV_TEL, " +
					"STORE_CUST_COMMENT_STATUS, " +
					"STORE_INC_TAX_IN_PROD_PRICE, " +
					"STORE_ENABLE_ADDR_VALID, " +
					"STORE_ENABLE_FRIENDLY_URLS, " +
					"STORE_FACEBOOK_URL, " +
					"STORE_TWITTER_URL, " +
					"STORE_SKYPE_NAME, " +
					"STORE_GOOGLE_URL, " +
					"STORE_STUMBLE_UPON_URL, " +
					"STORE_ABOUT_US, " +
					"STORE_LATITUDE, " +
					"STORE_LONGITUDE, " +
					"STORE_COUNTRY, " +
					"STORE_CITY_TOWN, " +
					"STORE_STREET, " +
					"STORE_LOCATION_ENABLED " +
				"FROM STORE " +
				"WHERE ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' ";

		@SuppressWarnings("unchecked")
		Store store = (Store) getJdbcTemplate().query(query, new RowMappers.StoreSettingsMapper()).get(0);
		
		/**
		 * If a user has added an image, then two entries would be returned, the row with the highest img-id number is the correct one to return,
		 * hence the descendant order and only returning the first row.
		 */
		query = "SELECT A.IMG_ID, " +
					   "B.IMG_FILE " +
				"FROM IMAGE_CATALOG A," +
				 	 "IMAGE B " +
				 "WHERE ((A.ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' " +
				 "AND A.IMG_ALBUM = 'STORE_LOGO_CATALOG' " +
				 "AND A.PRIMARY_IMG = 'Y') " +
				 "OR A.IMG_ALBUM = 'DEFAULT_STORE_LOGO') " +
				 "AND A.IMG_ID = B.IMG_ID " +
				 "ORDER BY A.IMG_ID DESC " +
				 "LIMIT 1";

		@SuppressWarnings("unchecked")
		SobieImage storeLogo = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
		store.setStoreLogo(storeLogo);
		
		/**
		 * If a user has added an image, then two entries would be returned, the row with the highest img-id number is the correct one to return,
		 * hence the descendant order and only returning the first row.
		 */
		query = "SELECT A.IMG_ID, " +
				   "B.IMG_FILE " +
			"FROM IMAGE_CATALOG A," +
			 	 "IMAGE B " +
			 "WHERE ((A.ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' " +
			 "AND A.IMG_ALBUM = 'STORE_INDEX_MAIN_HEADER_CATALOG' " +
			 "AND A.PRIMARY_IMG = 'Y') " +
			 "OR A.IMG_ALBUM = 'DEFAULT_STORE_HEADER') " +
			 "AND A.IMG_ID = B.IMG_ID " +
			 "ORDER BY A.IMG_ID DESC " +
			 "LIMIT 1";

		@SuppressWarnings("unchecked")
		SobieImage storeIndexMainHeader = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
		store.setStoreIndexMainHeaderImage(storeIndexMainHeader);
		
		return store;
	}
	
	/**
	 * Used to save/upload a seller or suppliers store settings when the Save/Update button is clicked on the Store Settings Dashboard
	 * @param storeSettings
	 */
	public void updateStoreSettings(final Store storeSettings) throws Exception{
		/**
         * Creates a new CUSTOMER record
         */
    	query = "UPDATE STORE " +
    			"SET STORE_NAME = ?, " +
					"STORE_DESC = ?, " +
					"STORE_TEL = ?, " +
					"STORE_FAX = ?, " +
					"STORE_ADMIN_EMAIL = ?, " +
					"STORE_SALES_DEP_EMAIL = ?, " +
					"STORE_DEP_TEL = ?, " +
					"STORE_CUST_SERV_EMAIL = ?, " +
					"STORE_CUST_SERV_TEL = ?, " +
					"STORE_CUST_COMMENT_STATUS = ?, " +
					"STORE_INC_TAX_IN_PROD_PRICE = ?, " +
					"STORE_ENABLE_ADDR_VALID = ?, " +
					"STORE_ENABLE_FRIENDLY_URLS = ?, " +
					"STORE_FACEBOOK_URL = ?, " +
					"STORE_TWITTER_URL = ?, " +
					"STORE_SKYPE_NAME = ?, " +
					"STORE_GOOGLE_URL = ?, " +
					"STORE_STUMBLE_UPON_URL = ?, " +
					"STORE_ABOUT_US = ?, " +
					"STORE_LATITUDE = ?, " +
					"STORE_LONGITUDE = ?, " +
					"STORE_COUNTRY = ?, " +
					"STORE_CITY_TOWN = ?, " +
					"STORE_STREET = ?, " +
					"STORE_LOCATION_ENABLED = ? " +
        		"WHERE ACC_NO = '" + storeSettings.getStoreAccNo() + "' " +
        		"AND STORE_NO = '" + storeSettings.getStoreNo() + "'";
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, storeSettings.getStoreName());
                       statement.setString(2, storeSettings.getStoreDesc());
                       statement.setString(3, storeSettings.getStoreTel());
                       statement.setString(4, storeSettings.getStoreFax());
                       statement.setString(5, storeSettings.getStoreAdminEmail());
                       statement.setString(6, storeSettings.getStoreSalesDepEmail());
                       statement.setString(7, storeSettings.getStoreDepTel());
                       statement.setString(8, storeSettings.getStoreCustServEmail());
                       statement.setString(9, storeSettings.getStoreCustServTel());
                       statement.setString(10, storeSettings.getStoreCustCommentStatus());
                       statement.setBoolean(11, storeSettings.isStoreIncludeTaxInProductPrice());
                       statement.setBoolean(12, storeSettings.isStoreEnableAddressValidation());
                       statement.setBoolean(13, storeSettings.isStoreEnableFriendlyUrls());
                       statement.setString(14, storeSettings.getStoreFacebookUrl());
                       statement.setString(15, storeSettings.getStoreTwitterUrl());
                       statement.setString(16, storeSettings.getStoreSkypeName());
                       statement.setString(17, storeSettings.getStoreGoogleUrl());
                       statement.setString(18, storeSettings.getStoreStumbleUponUrl());
                       statement.setString(19, storeSettings.getStoreAboutUs());
                       statement.setDouble(20, storeSettings.getLatitude());
                       statement.setDouble(21, storeSettings.getLongitude());
                       statement.setString(22, storeSettings.getCountry());
                       statement.setString(23, storeSettings.getCityTown());
                       statement.setString(24, storeSettings.getStreet());
                       statement.setBoolean(25, storeSettings.isLocationEnabled());
                       return statement;
					}
                });
            }
	}
	

	public Store retrieveStoreMainLayout(String storeNo) {

		query = "SELECT STORE_NO, " +
					"ACC_NO, " +
					"STORE_NAME, " +
					"STORE_DESC, " +
					"STORE_TEL, " +
					"STORE_FAX, " +
					"STORE_ADMIN_EMAIL, " +
					"STORE_SALES_DEP_EMAIL, " +
					"STORE_DEP_TEL, " +
					"STORE_CUST_SERV_EMAIL, " +
					"STORE_CUST_SERV_TEL, " +
					"STORE_CUST_COMMENT_STATUS, " +
					"STORE_INC_TAX_IN_PROD_PRICE, " +
					"STORE_ENABLE_ADDR_VALID, " +
					"STORE_ENABLE_FRIENDLY_URLS, " +
					"STORE_FACEBOOK_URL, " +
					"STORE_TWITTER_URL, " +
					"STORE_SKYPE_NAME, " +
					"STORE_GOOGLE_URL, " +
					"STORE_STUMBLE_UPON_URL, " +
					"STORE_ABOUT_US, " +
					"STORE_LATITUDE, " +
					"STORE_LONGITUDE, " +
					"STORE_COUNTRY, " +
					"STORE_CITY_TOWN, " +
					"STORE_STREET, " +
					"STORE_LOCATION_ENABLED " +
				"FROM STORE A " +
				"WHERE STORE_NO = '" + storeNo + "'";

		@SuppressWarnings("unchecked")
		Store store = (Store) getJdbcTemplate().query(query, new RowMappers.StoreSettingsMapper()).get(0);
		
		/**
		 * If a user has added an image, then two entries would be returned, the row with the highest img-id number is the correct one to return,
		 * hence the descendant order and only returning the first row.
		 */
		query = "SELECT A.IMG_ID, " +
					   "B.IMG_FILE " +
				"FROM IMAGE_CATALOG A," +
				 	 "IMAGE B " +
				 "WHERE ((A.STORE_NO = '" + storeNo + "' " +
				 "AND A.IMG_ALBUM = 'STORE_LOGO_CATALOG' " +
				 "AND A.PRIMARY_IMG = 'Y') " +
				 "OR A.IMG_ALBUM = 'DEFAULT_STORE_LOGO') " +
				 "AND A.IMG_ID = B.IMG_ID " +
				 "ORDER BY A.IMG_ID DESC " +
				 "LIMIT 1";

		@SuppressWarnings("unchecked")
		SobieImage storeLogo = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
		store.setStoreLogo(storeLogo);
		
		/**
		 * If a user has added an image, then two entries would be returned, the row with the highest img-id number is the correct one to return,
		 * hence the descendant order and only returning the first row.
		 */
		query = "SELECT A.IMG_ID, " +
				   "B.IMG_FILE " +
			"FROM IMAGE_CATALOG A," +
			 	 "IMAGE B " +
			 "WHERE ((A.STORE_NO = '" + storeNo + "' " +
			 "AND A.IMG_ALBUM = 'STORE_INDEX_MAIN_HEADER_CATALOG' " +
			 "AND A.PRIMARY_IMG = 'Y') " +
			 "OR A.IMG_ALBUM = 'DEFAULT_STORE_HEADER') " +
			 "AND A.IMG_ID = B.IMG_ID " +
			 "ORDER BY A.IMG_ID DESC " +
			 "LIMIT 1";

		@SuppressWarnings("unchecked")
		SobieImage storeIndexMainHeader = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
		store.setStoreIndexMainHeaderImage(storeIndexMainHeader);
		
		return store;
	}
	
	/**
	 * THis method must be called when a user has entered some text in the search box, and hits the Go button where if a product exists for the
	 * searched text, then the store owner must also be displayed
	 * @param searchString
	 * @return
	 */
	public List<Store> searchProductStoreOwner(String searchString) {
		
		query = "SELECT DISTINCT B.ACC_NO, " +
					"A.IMG_FILE, " +
					"B.STORE_NAME, " +
					"B.STORE_NO " +
				"FROM IMAGE A, " +
					"STORE B, " +
					"ACCOUNT C, " +
					"IMAGE_CATALOG D, " +
					"PRODUCT E, " +
					"STORE_INVENTORY F " +
				"WHERE A.IMG_ID = D.IMG_ID " +
				"AND D.IMG_ALBUM = 'PersonalProfilePics' "+
				"AND B.ACC_NO = C.ACC_NO " +
				"AND D.ACC_NO = B.ACC_NO " +
				"AND E.PROD_ID = F.PROD_ID " +
				"AND F.STORE_NO = B.STORE_NO " +
				"AND (E.PROD_NAME LIKE '" + searchString + "%' " +
				"OR E.PROD_TYPE LIKE '" + searchString + "%' " +
				"OR E.PROD_ID LIKE '" + searchString + "%')";

		@SuppressWarnings("unchecked")
		List<Store> stores = getJdbcTemplate().query(query, new RowMappers.StoreMapper());
		
		return stores;
	}

	public void insertStoreImage(final SobieImage image, final SobieProfile sobieProfile) {
		/**
         * Insert a record for each product owned image to the 
         */
		
		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, ACC_NO, STORE_NO, PRIMARY_IMG) values (?, ?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, image.getImgId());
                       statement.setString(2, image.getImageAlbum());
                       statement.setString(3, sobieProfile.getAccount().getAccNo());
                       statement.setString(4, sobieProfile.getAccount().getStoreNo());
                       statement.setString(5, image.getPrimaryImage());
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
                       statement.setString(1, image.getImgId()); 
                       statement.setString(2, image.getImgFilename());
                       statement.setString(3, image.getImgType());
                       statement.setString(4, image.getImgNotes());
                       statement.setBytes(5, image.getImageInBytes());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void insertStoreContentHeader(final SobieImage image, final SobieProfile sobieProfile) {
		/**
         * Insert a record for each product owned image to the 
         */
		
		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, ACC_NO, STORE_NO, PRIMARY_IMG, STORE_CONTENT_PAGE_ID) values (?, ?, ?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, image.getImgId());
                       statement.setString(2, image.getImageAlbum());
                       statement.setString(3, sobieProfile.getAccount().getAccNo());
                       statement.setString(4, sobieProfile.getAccount().getStoreNo());
                       statement.setString(5, image.getPrimaryImage());
                       statement.setString(6, image.getStoreContentPageId());
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
                       statement.setString(1, image.getImgId()); 
                       statement.setString(2, image.getImgFilename());
                       statement.setString(3, image.getImgType());
                       statement.setString(4, image.getImgNotes());
                       statement.setBytes(5, image.getImageInBytes());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	public void updateStoreImage(final SobieImage storeImage, final SobieProfile sobieProfile) {
		
		query = "UPDATE IMAGE " +
    			"SET IMG_FILENAME = ?, " +
    			"IMG_TYPE = ?, " +
    			"IMG_FILE = ? " +
        		"WHERE IMG_ID = '" + storeImage.getImgId() + "'";
        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, storeImage.getImgFilename());
                       statement.setString(2, storeImage.getImgType());
                       statement.setBytes(3, storeImage.getImageInBytes());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	
	/**
	 * Used to add a new content page to a store
	 * @param storeSettings
	 */
	public void addNewContentPage(final StoreContentPage storeContentPage) throws Exception{
		/**
         * Creates a new STORE_CONTENT_PAGE record
         */
    	query = "INSERT INTO STORE_CONTENT_PAGE(PAGE_ID, PAGE_NAME, PAGE_TITTLE, PAGE_PARENT_ID, PAGE_ENABLED, PAGE_CONTENT, STORE_NO) " +
        		"VALUES(?, ?, ?, ?, ?, ?, ?)";
        synchronized(this) {
            getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                   PreparedStatement statement = con.prepareStatement(query);
                   statement.setString(1, storeContentPage.getPageId());
                   statement.setString(2, storeContentPage.getPageName());
                   statement.setString(3, storeContentPage.getPageTittle());
                   statement.setString(4, storeContentPage.getPageParentId());
                   statement.setBoolean(5, storeContentPage.isPageEnabled());
                   statement.setString(6, storeContentPage.getPageContent());
                   statement.setString(7, storeContentPage.getStoreNo());
                   return statement;
				}
            });
        }
	}
	
	/**
	 * Used to update a content page to a store
	 * @param storeSettings
	 */
	public void updateContentPage(final StoreContentPage storeContentPage) throws Exception{
		/**
         * Updates a STORE_CONTENT_PAGE record
         */
    	query = "UPDATE STORE_CONTENT_PAGE " +
    			"SET PAGE_NAME = ?, " +
					"PAGE_TITTLE = ?, " +
					"PAGE_PARENT_ID = ?, " +
					"PAGE_ENABLED = ?, " +
					"PAGE_CONTENT = ?, " +
					"STORE_NO = ? " +
        		"WHERE PAGE_ID = '" + storeContentPage.getPageId() + "'";
        synchronized(this) {
            getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                   PreparedStatement statement = con.prepareStatement(query);
                   statement.setString(1, storeContentPage.getPageName());
                   statement.setString(2, storeContentPage.getPageTittle());
                   statement.setString(3, storeContentPage.getPageParentId());
                   statement.setBoolean(4, storeContentPage.isPageEnabled());
                   statement.setString(5, storeContentPage.getPageContent());
                   statement.setString(6, storeContentPage.getStoreNo());
                   return statement;
				}
            });
        }
	}
	
	public List<StoreContentPage> retrieveStoreContentPages(String storeNo) {

		query = "SELECT PAGE_ID, " +
					"PAGE_NAME, " +
					"PAGE_TITTLE, " +
					"PAGE_PARENT_ID, " +
					"PAGE_ENABLED, " +
					"PAGE_CONTENT, " +
					"STORE_NO " +
				"FROM STORE_CONTENT_PAGE " +
				"WHERE STORE_NO = '" + storeNo + "' " +
				"AND PAGE_DELETED <> '1' ";

		@SuppressWarnings("unchecked")
		List<StoreContentPage> storeContentPages = (List<StoreContentPage>) getJdbcTemplate().query(query, new RowMappers.StoreContentPageMapper());
		
		for(int x = 0; x < storeContentPages.size(); x++){
			
			query = "SELECT A.IMG_ID, " +
					   "B.IMG_FILE " +
				"FROM IMAGE_CATALOG A," +
				 	 "IMAGE B " +
				 "WHERE ((A.STORE_CONTENT_PAGE_ID IN('" + storeContentPages.get(x).getPageId() + "') " +
				 "AND A.IMG_ALBUM = 'STORE_CONTENT_PAGE_HEADER_CATALOG' " +
				 "AND A.PRIMARY_IMG = 'Y') " +
				 "OR A.IMG_ALBUM = 'DEFAULT_STORE_HEADER') " +
				 "AND A.IMG_ID = B.IMG_ID " +
				 "ORDER BY A.IMG_ID " +
				 "DESC LIMIT 1";

			@SuppressWarnings("unchecked")
			SobieImage storeContentPageHeaders = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
			storeContentPages.get(x).setStoreContentPageHeaderImage(storeContentPageHeaders);
		}
		return storeContentPages;
	}

	public SobieImage retrieveDefaultHeaderImage() {
		
		query = "SELECT A.IMG_ID, " +
				   "B.IMG_FILE " +
			"FROM IMAGE_CATALOG A," +
			 	 "IMAGE B " +
			 "WHERE A.IMG_ALBUM = 'DEFAULT_STORE_HEADER' " +
			 "AND A.IMG_ID = B.IMG_ID " +
			 "ORDER BY A.IMG_ID " +
			 "DESC LIMIT 1";

		@SuppressWarnings("unchecked")
		SobieImage storeContentPageHeader = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
		return storeContentPageHeader;
	}
	
	public void deleteContentPage(final String contentPageId){
	
		query = "UPDATE STORE_CONTENT_PAGE " +
				"SET PAGE_DELETED = '1' " +
				"WHERE PAGE_ID = ?";
		
		getJdbcTemplate().update(query, new Object[] {contentPageId});
		
	}
}
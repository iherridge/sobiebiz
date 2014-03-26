package biz.sobie.web.services;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import biz.sobie.web.beans.Account;
import biz.sobie.web.beans.Customer;
import biz.sobie.web.beans.DeliveryDetails;
import biz.sobie.web.beans.IpAddress;
import biz.sobie.web.beans.MultipleSobieProfiles;
import biz.sobie.web.beans.PaymentDetails;
import biz.sobie.web.beans.ProfilePicture;
import biz.sobie.web.beans.SearchResultsBean;
import biz.sobie.web.beans.SobieImage;
import biz.sobie.web.beans.SobieProfile;
import biz.sobie.web.utils.SobieUtils;

import com.mysql.jdbc.Blob;

public class CustomerAccountService extends JdbcDaoSupport {
    String query = null;


    /**
     * The createNewCustomerAccount method must only be called when registering a new customer, and not for existing customers that are adding extra
     * accounts to their customer profile
     */
	public SobieProfile createNewCustomerAccount(final SobieProfile sobieProfile) {
    	
    	final Customer customer = sobieProfile.getCustomer();
    	/**
         * Creates a new CUSTOMER record
         */
    	query = "UPDATE CUSTOMER " +
    			"SET CUST_FIRST_NAME = ?, " +
    			"CUST_LAST_NAME = ?, " +
    			"CUST_EMAIL = ?, " +
    			"CUST_DOB = ?, " +
    			"CUST_ID_NO = ?, " +
    			"CUST_PASSWORD = ?, " +
    			"CUST_GENDER = ? " +
        		"WHERE CUST_NUMBER = '" + sobieProfile.getCustomer().getCustNo() + "'";
        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, customer.getCustFirstName());
                       statement.setString(2, customer.getCustLastName());
                       statement.setString(3, customer.getCustEmail());
                       statement.setInt(4, customer.getCustDOB());
                       statement.setString(5, customer.getCustIdNo());
                       statement.setString(6, customer.getCustPassword());
                       statement.setString(7, customer.getCustGender());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return sobieProfile;
        }
        
        /**
         * Update ACCOUNT record with new sobie profile code
         */
        query = "UPDATE ACCOUNT  " +
        		"SET PP_ID = ?, " +
        		"ACC_NAME = ? " +
				"WHERE ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "' " +
				"AND CUST_NO = '" + sobieProfile.getCustomer().getCustNo() + "' ";
		try {
		    synchronized(this) {
		        getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		               PreparedStatement statement = con.prepareStatement(query);
		               statement.setString(1, customer.getCustProductType());
		               statement.setString(2, customer.getCustFirstName() + " " + customer.getCustLastName());
		               return statement;
					}
		        });
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    return sobieProfile;
		}
        
		if(customer.getCustProductType().startsWith("S")) {
	        
			SobieUtils sobieUtils = new SobieUtils();
			final String storeNo = sobieUtils.createStoreNo();
			sobieProfile.getAccount().setStoreNo(storeNo);
			final String accNo = sobieProfile.getAccount().getAccNo();
			query = "insert into store (STORE_NO, ACC_NO, STORE_NAME) " +
					"values (?, ?, ?)";
			
			try {
			    synchronized(this) {
			        getJdbcTemplate().update(new PreparedStatementCreator() {
						
						public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
			               PreparedStatement statement = con.prepareStatement(query);
			               statement.setString(1, storeNo); 
			               statement.setString(2, accNo);
			               statement.setString(3, "Store Name");
			               return statement;
						}
			        });
			    }
			} catch (Exception ex) {
			    ex.printStackTrace();
			    return sobieProfile;
			}
		}
		sobieProfile.getAccount().setPpId(sobieProfile.getCustomer().getCustProductType());
		
        return sobieProfile;
    }


	public SobieProfile getCustomerAccountDetails(String cust_email) throws IndexOutOfBoundsException{
		
		SobieProfile sobieProfile = new SobieProfile();
		
		/**
		 * Get customer details from db
		 */
		query = "SELECT * " +
					 "FROM customer " +
					 "WHERE CUST_EMAIL = '" + cust_email + "'";
        List<Customer> customer = (List<Customer>)getJdbcTemplate().query(query, new CustmerMapper());
		sobieProfile.setCustomer(customer.get(0));
		
		/**
    	 * Retrieve StoreNo that belongs to accNo
    	 */
		query = "SELECT A.PP_ID " +
				"FROM ACCOUNT A, " +
					"CUSTOMER B " +
				"WHERE B.CUST_NUMBER = A.CUST_NO " +
				"AND B.CUST_EMAIL = '" + cust_email + "'";
        final String ppId = (String)getJdbcTemplate().query(query, new PpIdMapper()).get(0);
		
		/**
		 * Get account details from db
		 */
		if(ppId.startsWith("BU")){
			query = "SELECT A.ACC_NO, " +
				     "A.PP_ID, " +
				     "A.ACC_NAME, " +
				     "B.SHOPCART_NO, " +
				     "'' AS STORE_NO " +
			  "FROM ACCOUNT A, " +
			  	   "SHOPPING_CART B " +
			  "WHERE A.CUST_NO = '" + sobieProfile.getCustomer().getCustNo() + "' " +
			  "AND A.ACC_NO = B.ACC_NO ";
		} else { //SELLERS & SUPPLIERS
			query = "SELECT A.ACC_NO, " +
					     "A.PP_ID, " +
					     "A.ACC_NAME, " +
					     "B.SHOPCART_NO, " +
					     "C.STORE_NO " +
				  "FROM ACCOUNT A, " +
				  	   "SHOPPING_CART B, " +
				  	   "STORE C " +
				  "WHERE A.CUST_NO = '" + sobieProfile.getCustomer().getCustNo() + "' " +
				  "AND A.ACC_NO = B.ACC_NO " +
				  "AND A.ACC_NO = C.ACC_NO ";
		}
        Account account = (Account)getJdbcTemplate().query(query, new AccountMapper()).get(0);
		sobieProfile.setAccount(account);

		/**
		 * If a user has added an image, then two entries would be returned, the row with the highest img-id number is the correct one to return,
		 * hence the descendant order and only returning the first row.
		 */
		query = "SELECT A.IMG_ID, " +
					   "B.IMG_FILE " +
				"FROM IMAGE_CATALOG A," +
				 	 "IMAGE B " +
				 "WHERE ((A.CUST_NO = '" + sobieProfile.getCustomer().getCustNo() + "' " +
				 "AND A.IMG_ALBUM = 'PROFILE_PICTURE_CATALOG' " +
				 "AND A.PRIMARY_IMG = 'Y') " +
				 "OR A.IMG_ALBUM = 'DEFAULT_MALE_PROFILE_PICTURE') " +
				 "AND A.IMG_ID = B.IMG_ID " +
				 "ORDER BY A.IMG_ID DESC " +
				 "LIMIT 1";

		@SuppressWarnings("unchecked")
		SobieImage profileImage = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
		sobieProfile.getCustomer().setProfileImage(profileImage);

        /**
    	 * Retrieve PaymentDetails
    	 */
        PaymentDetails paymentDetails = new PaymentDetails();
			query = "SELECT ACC_NO, " +
						"PAYMENT_OPTION, " +
						"ACCOUNT_HOLDER_NAME, " +
						"PAYMENT_TYPE, " +
						"CARD_NO, " +
						"CARD_CSV, " +
						"CARD_EXPIRY_DATE " +
				   	"FROM PAYMENT_DETAILS " +
				   	"WHERE ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "'";
    	try {
    		paymentDetails = (PaymentDetails)getJdbcTemplate().query(query, new PaymentDetailsMapper()).get(0);
    		paymentDetails.setPaymentDetailsEntered(true); 
        } catch (Exception e) {
        	//User have not entered payment details yet
        	paymentDetails.setPaymentDetailsEntered(false);
		}
		sobieProfile.setPaymentDetails(paymentDetails);
		
        /**
    	 * Retrieve DeliveryDetails
    	 */
		DeliveryDetails deliveryDetails = new DeliveryDetails();
			query = "SELECT ACC_NO, " +
				   		"DELIVERY_OPTION, " +
				   		"ADDRESS_LINE_1, " +
				   		"ADDRESS_LINE_2, " +
				   		"SUBURB, " +
				   		"CITY, " +
				   		"COUNTRY, " +
				   		"CODE " + 
				   	"FROM DELIVERY_DETAILS " +
				   	"WHERE ACC_NO = '" + sobieProfile.getAccount().getAccNo() + "'";
    	try {
    		deliveryDetails = (DeliveryDetails)getJdbcTemplate().query(query, new DeliveryDetailsMapper()).get(0);
    		deliveryDetails.setDeliveryDetailsEntered(true); 
        } catch (Exception e) {
        	//User has not entered delivery details yet
        	deliveryDetails.setDeliveryDetailsEntered(false);
		}
        sobieProfile.setDeliveryDetails(deliveryDetails);
        
        query = "SELECT * " +
				 "FROM IP_ADDRESS " +
				 "WHERE CUST_NO = '" + sobieProfile.getCustomer().getCustNo() + "'";
        List<IpAddress> ipAddresses = (List<IpAddress>)getJdbcTemplate().query(query, new IpAddressMapper());
        sobieProfile.setIpAdresses(ipAddresses);
        
		return sobieProfile;
	}
	
	public class DeliveryDetailsMapper implements ParameterizedRowMapper<DeliveryDetails> {
		
		public DeliveryDetails mapRow(ResultSet rs, int arg1) throws SQLException{
			DeliveryDetails deliveryDetails = new DeliveryDetails();
			deliveryDetails.setDeliveryOption(rs.getInt("DELIVERY_OPTION"));
			deliveryDetails.setAddressLine1(rs.getString("ADDRESS_LINE_1"));
			deliveryDetails.setAddressLine2(rs.getString("ADDRESS_LINE_2"));
			deliveryDetails.setSuburb(rs.getString("SUBURB"));
			deliveryDetails.setCity(rs.getString("CITY"));
			deliveryDetails.setCountry(rs.getString("COUNTRY"));
			deliveryDetails.setCode(rs.getInt("CODE"));
			return deliveryDetails; 
		}
	}

	public class PaymentDetailsMapper implements ParameterizedRowMapper<PaymentDetails> {
		
		public PaymentDetails mapRow(ResultSet rs, int arg1) throws SQLException{
			PaymentDetails paymentDetails = new PaymentDetails();
			paymentDetails.setPaymentOption(String.valueOf(rs.getInt("PAYMENT_OPTION")));
			paymentDetails.setPaymentType(rs.getString("PAYMENT_TYPE"));
			paymentDetails.setAccountHolderName(rs.getString("ACCOUNT_HOLDER_NAME"));
			paymentDetails.setCardNumber(rs.getString("CARD_NO"));
			paymentDetails.setCsv(rs.getString("CARD_CSV"));
			paymentDetails.setCardExpiryMonth(rs.getString("CARD_EXPIRY_DATE").substring(0,1));
			paymentDetails.setCardExpiryYear(rs.getString("CARD_EXPIRY_DATE").substring(2,3));
			
			return paymentDetails; 
		}
	}
	
	public class CustmerMapper implements ParameterizedRowMapper<Customer> {
		
		public Customer mapRow(ResultSet rs, int arg1) throws SQLException {
			Customer customer = new Customer(rs.getInt("CUST_DOB"), rs.getString("CUST_EMAIL"), rs.getString("CUST_FIRST_NAME"), rs.getString("CUST_GENDER"),
					rs.getString("CUST_ID_NO"), rs.getString("CUST_LAST_NAME"), rs.getString("CUST_NUMBER"));
			return customer;
		}
	}
	
	public class IpAddressMapper implements ParameterizedRowMapper<IpAddress> {
		
		public IpAddress mapRow(ResultSet rs, int arg1) throws SQLException {
			IpAddress ipAddress = new IpAddress();
				ipAddress.setCustNo(rs.getString("CUST_NO"));
				ipAddress.setDefaultIp(rs.getString("DEFAULT_IP"));
				ipAddress.setIpAddress(rs.getString("IP_ADDRESS"));
			return ipAddress;
		}
	}
	
	public class AccountMapper implements ParameterizedRowMapper<Account> {
		
		public Account mapRow(ResultSet rs, int arg1) throws SQLException {
			Account account = new Account(rs.getString("ACC_NO"), 
										rs.getString("PP_ID"),
										rs.getString("ACC_NAME"),
										rs.getString("SHOPCART_NO"),
										rs.getString("STORE_NO"));
			return account;
		}
	}
	
	public class ProfilePictureMapper implements ParameterizedRowMapper<ProfilePicture> {
		
		public ProfilePicture mapRow(ResultSet rs, int arg1) throws SQLException{
			ProfilePicture profilePicture;
			try {
				profilePicture = new ProfilePicture(rs.getString("IMG_ID"), rs.getString("CUST_NO"), rs.getString("ACC_NO"), 
																   rs.getString("IMG_ALBUM"), (Blob) rs.getBlob("IMG_FILE"), rs.getString("IMG_FILENAME"), 
																   rs.getString("IMG_TYPE"), rs.getString("IMG_NOTES"));
				return profilePicture;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} 
		}
	}
	
	public void updatePersonalProfilePicture(final SobieImage profilePicture, final String custNo, final String accNo, final String oldImgId) {
		
        /**
         * Insert image that is related a Customers Personal Profile
         */
		
		query = "insert into image (IMG_ID, IMG_FILENAME, IMG_TYPE, IMG_NOTES, IMG_FILE) values (?, ?, ?, ?, ?)";

        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, profilePicture.getImgId()); 
                       statement.setString(2, profilePicture.getImgFilename());
                       statement.setString(3, profilePicture.getImgType());
                       statement.setString(4, profilePicture.getImgNotes());
                       statement.setBytes(5, profilePicture.getImageInBytes());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		/**
		 * Link Personal Profile Picture to the Customers IMAGE_CATALOG
		 */
		
		query = "insert into image_catalog (IMG_ID, IMG_ALBUM, CUST_NO, ACC_NO, PRIMARY_IMG) values (?, ?, ?, ?, ?)";
	
	    try {
	        synchronized(this) {
	            getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
	                   PreparedStatement statement = con.prepareStatement(query);
	                   statement.setString(1, profilePicture.getImgId()); 
	                   statement.setString(2, profilePicture.getImageAlbum());
	                   statement.setString(3, custNo);
	                   statement.setString(4, accNo);
	                   statement.setString(5, profilePicture.getPrimaryImage());
	                   return statement;
					}
	            });
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    
	    if(oldImgId != null){

	    	query = "UPDATE IMAGE_CATALOG " +
	    			"SET PRIMARY_IMG = ? " +
	    			"WHERE IMG_ID = '" + oldImgId + "'";
	    	
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, "N");
                       return statement;
					}
                });
            }
	    }
	    
	}

	public List<Customer> customerSearch(String searchString) throws IOException{
		
		/**
		 * Get customer details from db
		 */
		query = "SELECT * " +
					 "FROM CUSTOMER " +
					 "WHERE (CUST_FIRST_NAME LIKE '%" + searchString + "%' " +
					 "OR CUST_LAST_NAME LIKE '%" + searchString + "%' " +
					 "OR CUST_EMAIL LIKE '%" + searchString + "%')";
        List<Customer> customers = (List<Customer>)getJdbcTemplate().query(query, new CustmerMapper());
		
		for(int x = 0; x < customers.size(); x++){
			/**
			 * If a user has added an image, then two entries would be returned, the row with the highest img-id number is the correct one to return,
			 * hence the descendant order and only returning the first row.
			 */
			query = "SELECT A.IMG_ID, " +
						   "B.IMG_FILE " +
					"FROM IMAGE_CATALOG A," +
					 	 "IMAGE B " +
					 "WHERE ((A.CUST_NO = '" + customers.get(x).getCustNo() + "' " +
					 "AND A.IMG_ALBUM = 'PROFILE_PICTURE_CATALOG' " +
					 "AND A.PRIMARY_IMG = 'Y') " +
					 "OR A.IMG_ALBUM = 'DEFAULT_MALE_PROFILE_PICTURE') " +
					 "AND A.IMG_ID = B.IMG_ID " +
					 "ORDER BY A.IMG_ID DESC " +
					 "LIMIT 1";

			@SuppressWarnings("unchecked")
			SobieImage profileImage = (SobieImage) getJdbcTemplate().query(query, new RowMappers.ImageMapper()).get(0);
			customers.get(x).setProfileImage(profileImage);
			
		}
		return customers;
	}
	
	public class SearchCustmerMapper implements ParameterizedRowMapper<SearchResultsBean> {
		
		public SearchResultsBean mapRow(ResultSet rs, int arg1) throws SQLException {
			String custFullName = rs.getString("CUST_FIRST_NAME") + " " + rs.getString("CUST_LAST_NAME");
			try {
				SearchResultsBean searchResultsBean = new SearchResultsBean(rs.getString("CUST_NUMBER"), 
						                                                    "", 
						                                                    (Blob) rs.getBlob("IMG_FILE"), 
						                                                    rs.getString("IMG_TYPE"), 
						                                                    custFullName);
				return searchResultsBean;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			
			
		}
	}
	
	/**
	 * Update payment details to PAYMENT_DETAIL table
     */
    public void updatePaymentDetails(final SobieProfile sobieProfile) {
        
    	final PaymentDetails paymentDetails = sobieProfile.getPaymentDetails();
    	if(paymentDetails.isPaymentDetailsEntered()) {
    		/**
    		 * Update payment details
    		 */
    		query = "UPDATE INTO PAYMENT_DETAILS (ACC_NO, PAYMENT_OPTION, ACCOUNT_HOLDER_NAME, PAYMENT_TYPE, CARD_NO, CARD_CSV, CARD_EXPIRY_DATE) values (?, ?, ?, ?, ?, ?, ?)";
    	} else {
    		/**
    		 * Insert new payment details
    		 */
    		
    		query = "INSERT INTO PAYMENT_DETAILS (ACC_NO, PAYMENT_OPTION, ACCOUNT_HOLDER_NAME, PAYMENT_TYPE, CARD_NO, CARD_CSV, CARD_EXPIRY_DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	}
    	try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, sobieProfile.getAccount().getAccNo());
                       statement.setInt(2, Integer.valueOf(paymentDetails.getPaymentOption()));
                       statement.setString(3, paymentDetails.getAccountHolderName());
                       statement.setString(4, paymentDetails.getPaymentType());
                       statement.setString(5, paymentDetails.getCardNumber());
                       statement.setString(6, paymentDetails.getCsv());
                       statement.setString(7, paymentDetails.getCardExpiryMonth() + paymentDetails.getCardExpiryYear());
                       return statement;
					}
                });
            }
            paymentDetails.setPaymentDetailsEntered(true);
        } catch (Exception ex) {
        	//TODO: Must generate a error message to the UI to ask the user to resubmit
            ex.printStackTrace();
            paymentDetails.setPaymentDetailsEntered(false);
        }
        sobieProfile.setPaymentDetails(paymentDetails);
    }
    
	/**
	 * Update payment details to PAYMENT_DETAIL table
     */
    public void updateDeliveryDetails(final SobieProfile sobieProfile) {
        
    	final DeliveryDetails deliveryDetails = sobieProfile.getDeliveryDetails();
    	if(deliveryDetails.isDeliveryDetailsEntered()) {
    		/**
    		 * Update payment details
    		 */
    		query = "UPDATE INTO DELIVERY_DETAILS (ACC_NO, DELIVERY_OPTION, ADDRESS_LINE_1, ADDRESS_LINE_2, SUBURB, CITY, COUNTRY, CODE) values (?, ?, ?, ?, ?, ?, ?, ?)";
    	} else {
    		/**
    		 * Insert new payment details
    		 */
    		
    		query = "INSERT INTO DELIVERY_DETAILS (ACC_NO, DELIVERY_OPTION, ADDRESS_LINE_1, ADDRESS_LINE_2, SUBURB, CITY, COUNTRY, CODE) values (?, ?, ?, ?, ?, ?, ?, ?)";
    	}
    	try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, sobieProfile.getAccount().getAccNo()); 
                       statement.setInt(2, deliveryDetails.getDeliveryOption());
                       statement.setString(3, deliveryDetails.getAddressLine1());
                       statement.setString(4, deliveryDetails.getAddressLine2());
                       statement.setString(5, deliveryDetails.getSuburb());
                       statement.setString(6, deliveryDetails.getCity());
                       statement.setString(7, deliveryDetails.getCountry());
                       statement.setInt(8, deliveryDetails.getCode());
                       return statement;
					}
                });
            }
            deliveryDetails.setDeliveryDetailsEntered(true);
        } catch (Exception ex) {
            ex.printStackTrace();
            //TODO: Must generate a error message to the UI to ask the user to resubmit
            deliveryDetails.setDeliveryDetailsEntered(true);
        }
        sobieProfile.setDeliveryDetails(deliveryDetails);
    }

	public MultipleSobieProfiles checkMultipleProfilesExist(String remoteAddr) {
		
		
		/**
		 * Checks if IP address exist in database
		 */
		query = "SELECT * " +
					 "FROM IP_ADDRESS " +
					 "WHERE IP_ADDRESS= '" + remoteAddr + "'";
		
		//TODO: Process to code that checks that only 
        List<IpAddress> ipAddresses = (List<IpAddress>)getJdbcTemplate().query(query, new IpAddressMapper());
        ArrayList<SobieProfile> sobieProfiles = new ArrayList<SobieProfile>(ipAddresses.size());
        if(ipAddresses.size() > 0){
        	for(int x = 0; x < ipAddresses.size();x++){
        		SobieProfile sobieProfile = new SobieProfile();        		
	        	/**
				 * Get customer details from db
				 */
				query = "SELECT * " +
						"FROM CUSTOMER " +
						"WHERE CUST_NUMBER = '" + ipAddresses.get(x).getCustNo() + "' ";
		        Customer customer = (Customer)getJdbcTemplate().query(query, new CustmerMapper()).get(0);
				sobieProfile.setCustomer(customer);
				/**
				 * Get account details from db
				 */
				query = "SELECT a.ACC_NO, " +
						     "a.PP_ID, " +
						     "a.ACC_NAME, " +
						     "b.SHOPCART_NO, " +
						     "'' AS STORE_NO " +
					  "FROM ACCOUNT a, " +
					  	   "SHOPPING_CART b " +
					  "WHERE a.CUST_NO = '" + ipAddresses.get(x).getCustNo() + "' " +
					  "AND a.ACC_NO = b.ACC_NO ";
		        Account account = (Account)getJdbcTemplate().query(query, new AccountMapper()).get(0);
				sobieProfile.setAccount(account);
				sobieProfiles.add(x, sobieProfile);
        	
        	}
        }
        MultipleSobieProfiles multipleSobieProfiles = new MultipleSobieProfiles();
        multipleSobieProfiles.setSobieProfiles(sobieProfiles);
		return multipleSobieProfiles;
	}


	public SobieProfile createAnonymousBuyer(String remoteAddr) {
		SobieProfile sobieProfile = new SobieProfile();

		/**
		 * Ip address does not exist
		 * Creating anonymousBuyer Account to give the user the ability to add products to their wishlist and shopping cart
		 */
		SobieUtils sobieUtils = new SobieUtils();
		final Customer customer = new Customer();
		customer.setCustNo(sobieUtils.createCustomerNumber());
		
		final IpAddress ipAddress = new IpAddress();
		ipAddress.setIpAddress(remoteAddr);
		ipAddress.setCustNo(customer.getCustNo());
		ipAddress.setDefaultIp("Y");

    	/**
         * Creates a new CUSTOMER record for anonymousBuyer
         */
    	query = "insert into customer (CUST_NUMBER) " +
        		"values (?)";
        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, customer.getCustNo()); 
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return sobieProfile;
        }
        sobieProfile.setCustomer(customer);
        
		/**
         * Creates a new IP Address record for anonymousBuyer
         */
    	query = "insert into IP_ADDRESS (IP_ADDRESS, CUST_NO, DEFAULT_IP) " +
        		"values (?, ?, ?)";
        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, ipAddress.getIpAddress());
                       statement.setString(2, ipAddress.getCustNo());
                       statement.setString(3, ipAddress.getDefaultIp());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return sobieProfile;
        }
        ArrayList<IpAddress> ipAddressArrayList = new ArrayList<IpAddress>(1);
        ipAddressArrayList.add(0, ipAddress);
        sobieProfile.setIpAdresses(ipAddressArrayList);
        
        /**
         * Creates ACCOUNT record
         */
        
        query = "insert into account (ACC_NO, CUST_NO, PP_ID, ACC_NAME) " +
		"values (?, ?, ?, ?)";
		final String accNo = sobieUtils.createAccNo();
		try {
		    synchronized(this) {
		        getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		               PreparedStatement statement = con.prepareStatement(query);
		               statement.setString(1, accNo); 
		               statement.setString(2, customer.getCustNo());
		               statement.setString(3, "BU0");
		               statement.setString(4, "anonymousBuyer");
		               return statement;
					}
		        });
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    return sobieProfile;
		}
        
        /**
         * Creates shopping cart that is associated to the anonymousBuyer profile
         */
        query = "INSERT INTO shopping_cart (SHOPCART_NO, ACC_NO) " +
        		"VALUES (?, ?)";
        
        final String shoppingCartNo = sobieUtils.createShoppingCartNo();
		try {
		    synchronized(this) {
		        getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		               PreparedStatement statement = con.prepareStatement(query);
		               statement.setString(1, shoppingCartNo); 
		               statement.setString(2, accNo);
		               return statement;
					}
		        });
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    return sobieProfile;
		}
        
		Account account = new Account(accNo, "BU0", "anonymousBuyer", shoppingCartNo, "");
		sobieProfile.setAccount(account);
        
		return sobieProfile;
	}


	public String getProductOwnerCustNo(String ownerAccNo) {
		
		/**
    	 * Retrieve StoreNo that belongs to accNo
    	 */
		String sql = "SELECT CUST_NO FROM ACCOUNT WHERE ACC_NO = '" + ownerAccNo + "'";
        final String ownerCustNo = (String)getJdbcTemplate().query(sql, new CustNoMapper()).get(0);
		
		return ownerCustNo;
	}
	
	public class CustNoMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String storeNo = rs.getString("CUST_NO");
			return storeNo;
		}
	}
	
	public class PpIdMapper implements ParameterizedRowMapper<String> {
		
		public String mapRow(ResultSet rs, int arg1) throws SQLException {
			String ppId = rs.getString("PP_ID");
			return ppId;
		}
	}
}
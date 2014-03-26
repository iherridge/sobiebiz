package biz.sobie.web.services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import biz.sobie.web.beans.MessageBean;
import biz.sobie.web.beans.SobieProfile;

public class MessageService extends JdbcDaoSupport {
	
	String query = null;
	
	public String createNewMessage(final SobieProfile sobieProfile, final MessageBean msgBean) {
        
        /**
         * Creates MESSAGES record
         */
        query = "insert into messages (MSG_ID, MSG_SUBJECT, MSG_CONTENT, MSG_DATE, MSG_TIME) " +
				"values (?, ?, ?, ?, ?)";
        
		try {
		    synchronized(this) {
		        getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		               PreparedStatement statement = con.prepareStatement(query);
		               statement.setString(1, msgBean.getMsgId()); 
		               statement.setString(2, msgBean.getMsgSubject());
		               statement.setString(3, msgBean.getMsgContent());
		               statement.setString(4, msgBean.getMsgDate());
		               statement.setString(5, msgBean.getMsgTime());
		               return statement;
					}
		        });
		    }
		} catch (Exception ex) {
		    ex.printStackTrace();
		    return "Unsuccessful";
		}
		
		/**
         * Creates a new MESSAGE_BOX record
         */
    	query = "INSERT INTO msg_inbox (MSGBX_ID, MSG_ID, MSGBX_TO_CUST_NOS, MSGBX_TO_ACC_NOS, MSGBX_FROM_CUST_NO, MSGBX_FROM_ACC_NO, MSGBX_CC_CUST_NOS, MSGBX_CC_ACC_NOS, MSGBX_BCC_CUST_NOS, MSGBX_BCC_ACC_NOS, MSGBX_TYPE, MSGBX_RP_STORE_NO, MSGBX_RP_PROD_ID, MSGBX_RP_ACC_NO) " +
        		"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            synchronized(this) {
                getJdbcTemplate().update(new PreparedStatementCreator() {
					
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                       PreparedStatement statement = con.prepareStatement(query);
                       statement.setString(1, msgBean.getMsgbxId());
                       statement.setString(2, msgBean.getMsgId());
                       statement.setString(3, msgBean.getToCustNos());
                       statement.setString(4, msgBean.getToAccNos());
                       statement.setString(5, msgBean.getFromCustNo());
                       statement.setString(6, msgBean.getFromAccNo());
                       statement.setString(7, msgBean.getCcCustNos());
                       statement.setString(8, msgBean.getCcAccNos());
                       statement.setString(9, msgBean.getBccCustNos());
                       statement.setString(10, msgBean.getBssAccNos());
                       statement.setString(11, msgBean.getMsgType());
                       statement.setString(12, msgBean.getMsgRpStoreNo());
                       statement.setString(13, msgBean.getMsgRpProdId());
                       statement.setString(14, msgBean.getMsgRpAccNo());
                       return statement;
					}
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Unsuccessful";
        }

        return "Successful";
	}
	
	public List<MessageBean> getMessages(SobieProfile sobieProfile) throws IOException{
		
		query = "SELECT A.MSGBX_ID, " +
				             "A.MSGBX_TO_CUST_NOS, " +
				    	     "A.MSGBX_TO_ACC_NOS, " +
			   		         "A.MSGBX_FROM_CUST_NO, " +
					         "A.MSGBX_FROM_ACC_NO, " +
					         "A.MSG_ID, " +
					         "A.MSGBX_CC_CUST_NOS, " +
					         "A.MSGBX_CC_ACC_NOS, " +
					         "A.MSGBX_BCC_CUST_NOS, " +
					         "A.MSGBX_BCC_ACC_NOS, " +
					         "A.MSGBX_TYPE, " +
					         "B.MSG_SUBJECT, " +
					         "B.MSG_CONTENT, " +
					         "B.MSG_DATE, " +
					         "B.MSG_TIME, " +
					         "A.MSGBX_RP_PROD_ID, " +
					         "A.MSGBX_RP_STORE_NO, " +
					         "A.MSGBX_RP_ACC_NO " +
					"FROM MSG_INBOX A, " +
			         	  "MESSAGES B " +
					"WHERE A.MSG_ID = B.MSG_ID " +
					"AND ((A.MSGBX_TO_CUST_NOS LIKE '%" + sobieProfile.getCustomer().getCustNo() + "%' " +
					"OR A.MSGBX_CC_CUST_NOS LIKE '%" + sobieProfile.getCustomer().getCustNo() + "%' " +
					"OR A.MSGBX_BCC_CUST_NOS LIKE '%" + sobieProfile.getCustomer().getCustNo() + "%') " +
					"OR (A.MSGBX_TO_ACC_NOS LIKE '%" + sobieProfile.getAccount().getAccNo() + "%' " +
					"OR A.MSGBX_CC_ACC_NOS LIKE '%" + sobieProfile.getAccount().getAccNo() + "%' " +
					"OR A.MSGBX_BCC_ACC_NOS LIKE '%" + sobieProfile.getAccount().getAccNo() + "%')); ";
        
		List<MessageBean> messages = (List<MessageBean>)getJdbcTemplate().query(query, new MessageMapper());
		
		for(int x = 0; x < messages.size(); x++) {
			String fromCustNo = messages.get(x).getFromCustNo();
			query = "SELECT CUST_FIRST_NAME, " +
			 			 "CUST_LAST_NAME " +
				  "FROM CUSTOMER " +
				  "WHERE CUST_NUMBER = '" + fromCustNo + "'; ";
			String custFullName = (String)getJdbcTemplate().query(query, new CustFullNameMapper()).get(0);
			messages.get(x).setFromCustFullName(custFullName);
		}

		return messages;
	}
	
	public class MessageMapper implements ParameterizedRowMapper<MessageBean> {
		
		public MessageBean mapRow(ResultSet rs, int arg1) throws SQLException {
			MessageBean messages = new MessageBean();
			messages.setMsgbxId(rs.getString("A.MSGBX_ID"));
			messages.setToCustNos(rs.getString("A.MSGBX_TO_CUST_NOS"));
			messages.setToAccNos(rs.getString("A.MSGBX_TO_ACC_NOS"));
			messages.setFromCustNo(rs.getString("A.MSGBX_FROM_CUST_NO"));
			messages.setFromAccNo(rs.getString("A.MSGBX_FROM_ACC_NO"));
			messages.setMsgId(rs.getString("A.MSG_ID"));
			messages.setCcCustNos(rs.getString("A.MSGBX_CC_CUST_NOS"));
			messages.setCcAccNos(rs.getString("A.MSGBX_CC_ACC_NOS"));
			messages.setBccCustNos(rs.getString("A.MSGBX_BCC_CUST_NOS"));
			messages.setBssAccNos(rs.getString("A.MSGBX_BCC_ACC_NOS"));
			messages.setMsgType(rs.getString("A.MSGBX_TYPE"));
			messages.setMsgSubject(rs.getString("B.MSG_SUBJECT"));
			messages.setMsgContent(rs.getString("B.MSG_CONTENT"));
			messages.setMsgDate(rs.getString("B.MSG_DATE"));
			messages.setMsgTime(rs.getString("B.MSG_TIME"));
			messages.setMsgRpProdId(rs.getString("A.MSGBX_RP_PROD_ID"));
			messages.setMsgRpStoreNo(rs.getString("A.MSGBX_RP_STORE_NO"));
			messages.setMsgRpAccNo(rs.getString("A.MSGBX_RP_ACC_NO"));
			return messages;
		}
	}
	
	
		
		public class CustFullNameMapper implements ParameterizedRowMapper<String> {
		
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				String fromCustFirstName = rs.getString("CUST_FIRST_NAME");
				String fromCustLastName = rs.getString("CUST_LAST_NAME");
				return fromCustFirstName + " " + fromCustLastName;
			}
		}
	
}

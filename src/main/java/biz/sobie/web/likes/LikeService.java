package biz.sobie.web.likes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class LikeService extends JdbcDaoSupport{
	
	String query = null;
	
	public void like(final String prodId, final String ownerProdId, final String servId, final String accNo) throws DuplicateKeyException{
    	
        /**
		 * Insert new Product record into PRODUCT table
		 */
		
		query = "INSERT INTO LIKES(PROD_ID, " +
									"OWNER_PROD_ID, " +
									"SERV_ID, " +
									"ACC_NO) " +
				"VALUES(?, ?, ?, ?);";

        synchronized(this) {
            getJdbcTemplate().update(new PreparedStatementCreator() {
				
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                   PreparedStatement statement = con.prepareStatement(query);
                   statement.setString(1, prodId); 
                   statement.setString(2, ownerProdId); 
                   statement.setString(3, servId); 
                   statement.setString(4, accNo); 
                   return statement;
				}
            });
        }
	}
	
}

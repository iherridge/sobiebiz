package biz.sobie.web.security;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class MyUserDetailsService implements UserDetailsService{

	private JdbcTemplate  jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings({ "deprecation" })
	public UserDetails loadUserByUsername(String cust_email)
			throws UsernameNotFoundException, DataAccessException {
		
		String sql = "SELECT * FROM CUSTOMER where CUST_EMAIL = '" + cust_email + "'";
        List<User> user = (List<User>)getJdbcTemplate().query(sql, new UserMapper());
					
		return user.get(0);
	}
    
	private GrantedAuthority[] getAuthorities(boolean isAdmin) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
        authList.add(new GrantedAuthorityImpl("ROLE_USER"));
        if (isAdmin) {
            authList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
        }
        return authList.toArray(new GrantedAuthority[] {});
    }
    
	public class UserMapper implements ParameterizedRowMapper<User> {
		
		@SuppressWarnings("deprecation")
		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User(rs.getString("CUST_EMAIL"), rs.getString("CUST_PASSWORD"), true, true, true, true, getAuthorities(false));
			return user;
		}
	}

}

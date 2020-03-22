/**
 * 
 */
package databaseAccess;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

import exception.ConfigException;
import service.ConfigService;

/**
 * @author Tristen Kreutz, Jesse Goerzen
 *
 */
public class ConnectionPool {
	
	private static ConnectionPool pool = null;
	private static DataSource dataSource = null;
	
	private ConnectionPool() throws ConfigException, SQLException {
		if( "com.mysql.cj.jdbc.Driver".equals( ConfigService.fetchFromConfig("sqldriverClassName:") ) ) {
			String url = ConfigService.fetchFromConfig("sqlurl:");
			String username = ConfigService.fetchContents( ConfigService.fetchFromConfig("sqlusernamepath:") );
			String password = ConfigService.fetchContents( ConfigService.fetchFromConfig("sqlpasswordpath:") );
			
			MysqlDataSource ds = new MysqlDataSource();
			
			ds.setUrl(url);
			ds.setUser(username);
			ds.setPassword(password);
			
			dataSource = ds;
		}
	}
	
	public static synchronized ConnectionPool getInstance() {
		
		if (pool == null) {
			try {
				pool = new ConnectionPool();
			} catch (ConfigException e) {
				pool = null;
				e.printStackTrace();
			} catch (SQLException e) {
				pool = null;
				e.printStackTrace();
			}
		}
		
		return pool;
	}
	
	public Connection getConnection() {
		
		try {
			
			return dataSource.getConnection();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void closeConnection(Connection c) {
		
		try {
			
			c.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

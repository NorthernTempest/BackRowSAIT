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
 * Class Description:	Creates and supplies a connection to the database
 * 						using appropriate login credentials from the config
 * 						file.
 * 
 * @author Tristen Kreutz, Jesse Goerzen
 *
 */
public class ConnectionPool {
	
	private static ConnectionPool pool = null;
	private static DataSource dataSource = null;
	
	/**
	 * Private constructor for the connection pool. Fetches values from the config file
	 * and then establishes a connection with the database to be used by the 
	 * database access classes.
	 * @throws ConfigException if the config file cannot be found/opened
	 * @throws SQLException if there is an error connecting to the database
	 */
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
	
	/**
	 * Public get method that will check to see if the connection pool has been initialized.
	 * If not, it will initialize it with a new ConnectionPool, and then return the connection
	 * to the calling method.
	 * @return ConnectionPool for database connections
	 */
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
	
	/**
	 * Method that returns a connection object to the calling method
	 * to establish a connection with the database.
	 * @return Connection
	 */
	public Connection getConnection() {
		
		try {
			
			return dataSource.getConnection();
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Terminates the connection that is passed into the method to free up resources.
	 * @param c Connection to terminate
	 */
	public void closeConnection(Connection c) {
		
		try {
			
			c.close();
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

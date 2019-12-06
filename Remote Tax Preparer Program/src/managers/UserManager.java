package managers;

import java.util.ArrayList;
import java.util.Properties;

import domain.User;

public class UserManager {

	public boolean insert(User user) {
		return false;
	}
	
	public boolean update(User user) {
		return false;
	}
	
	public boolean delete(String email) {
		return false;
	}
	
	public User get(String email) {
		return null;
	}
	
	public ArrayList<User> getAll() {
		return null;
	}
	
	public boolean authenticate(User user, String password) {
		return false;
	}
	
	public boolean login(String email, String password, String ip) {
		return false;
	}
	
	public Properties getAccountInfo(String email) {
		return null;
	}
}

package DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.catalina.User;

import com.mysql.cj.Session;
import com.mysql.cj.callback.UsernameCallback;

import UserPojo.UserPojo;

public class Manager {
	Connection connect = null;
	public Connection establishConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		String jdbcURL = "jdbc:mysql://localhost:3306/demo";
		String user = "root";
		String password = "root";
		connect = DriverManager.getConnection(jdbcURL, user, password);
		return connect;
	} //end of establishConnection();

	public void closeConnection(Connection connect) {
		try {
			connect.close();
			System.out.println("Connection To DataBase Closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} //end of closeConnection()

	public boolean addUser(UserPojo user) {
		try {
			connect = establishConnection();
			Statement statement = connect.createStatement();
			user.setBalanace(Integer.parseInt(user.getStartAmt())); //New User Balance will be the StartAmount Itself
			String insertQuery = "INSERT INTO BANKSERVLET VALUES(default, '"+user.getFirstName()+"', '"
					+user.getLastName()+"', "+user.getAge()+", '"+user.getStartAmt()+"', "+user.getBalanace()+", '"+user.getUserName()+"','"+user.getPassword()+"')";
			
			System.out.println(insertQuery);
			statement.executeUpdate(insertQuery);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //end of else;
		finally {
			closeConnection(connect);
		} //end of finally Block;
		return false;
	} //end of addUser();
	public boolean loginUser(UserPojo user) {
		HashMap<String, String> hashUser;
		try {
			ResultSet rs;
			hashUser = new HashMap<String, String>();
			connect = establishConnection(); //Open the Connection
			Statement statement = connect.createStatement();
			String checkUser = "SELECT * FROM BANKSERVLET WHERE USERNAME = '"+user.getUserName()+"' and PASSWORD = '"+user.getPassword()+"'";
			//			String checkUser = "SELECT * FROM BANKSERVLET WHERE USERNAME = 'Kalpit' AND PASSWORD = 'kalpit'";
			System.out.println(checkUser);
			rs = statement.executeQuery(checkUser);
			while(rs.next()) {
				hashUser.put(rs.getString("userName"), rs.getString("password"));
				if(hashUser.containsKey(user.getUserName())) {
					if(hashUser.get(user.getUserName()).contains(user.getPassword())) {
						System.out.println("User Authenticated");
						System.out.println("RS : "+rs.getInt(1));
						user.setId(rs.getInt(1));
						user.setFirstName(rs.getString("firstName"));
						user.setLastName(rs.getString("lastName"));
						user.setAge(rs.getInt(4));
						user.setBalanace(rs.getInt(6));
						user.setUserName(rs.getString("userName"));
						return true;
					} //end of inner if condition
				} //end of outer if;
				else {
					user = null;
					return false;
				} //end of outer else;
			} //end of while
			//end of Try
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			closeConnection(connect);
		}
		return false;
	} //end of loginUser;

	public UserPojo findUser(int userId) {
		try {
			connect = establishConnection();
			Statement statement = connect.createStatement();
			String findUser = "SELECT * FROM BANKSERVLET WHERE USERID = "+userId;
			ResultSet rs = statement.executeQuery(findUser);
			UserPojo user = new UserPojo();
			while(rs.next()) {
				user.setId(rs.getInt(1));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setAge(rs.getInt(4));
				user.setBalanace(rs.getInt(6));
				user.setUserName(rs.getString("userName"));
			} //end of while loop
			return user;
		} catch (ClassNotFoundException |SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //end of try-catch block
		finally{
			closeConnection(connect);
		}
		UserPojo user;
		return user=null;
	} //end of findUser;

	public int showBalance(UserPojo user) {
		System.out.println(user);
		try {
			connect = establishConnection();
			Statement statement = connect.createStatement();
			String showBal = "SELECT BALANCE FROM BANKSERVLET WHERE USERID = "+user.getId();
			System.out.println(showBal);
			ResultSet rs = statement.executeQuery(showBal);
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //end of try-Catch 
		finally{
			closeConnection(connect);
		}
		return 0;
	} //end of showBalance method;

	public boolean depositMoney(UserPojo user, int amount) {
		UserPojo userTemp = findUser(user.getId());
		int newBalance = userTemp.getBalanace() + amount; //New Balance
		System.out.println("New Balance is : "+newBalance);
		try {
			connect = establishConnection();
			connect.setAutoCommit(false);
			Statement statement = connect.createStatement();
			String updateBalance = "UPDATE BANKSERVLET SET BALANCE = "+newBalance+" WHERE USERID = "+user.getId();
			System.out.println(updateBalance);
			statement.executeUpdate(updateBalance);
			connect.commit();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //end of try-catch block
		return false;
	} //end of deposit Money method
	public boolean withdrawMoney(UserPojo user, int amount) {
		UserPojo userTemp = findUser(user.getId());
		int newBalance = userTemp.getBalanace() - amount; //New Balance
		System.out.println("New Balance is : "+newBalance);
		try {
			connect = establishConnection();
			connect.setAutoCommit(false);
			Statement statement = connect.createStatement();
			String updateBalance = "UPDATE BANKSERVLET SET BALANCE = "+newBalance+" WHERE USERID = "+user.getId();
			System.out.println(updateBalance);
			statement.executeUpdate(updateBalance);
			connect.commit();
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //end of try-catch block
		return false;
	} //end of deposit Money method
	
	public boolean transferMoney(UserPojo sender, int userId2, int amount) {
		if(withdrawMoney(sender, amount)) {
			try {
				connect = establishConnection();
				UserPojo receiver = findUser(userId2);
				if(depositMoney(receiver, amount)) {
					return true;
				} //end of if;
			} catch (ClassNotFoundException |SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //end of try catch
			finally {
				closeConnection(connect);
			}
		} //end of outer if;	
		return false;
	} //end of transferMoney method
} //end of Manager Class

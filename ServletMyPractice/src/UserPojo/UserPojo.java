package UserPojo;

public class UserPojo {
	private int age, id, balanace;
	private String firstName, lastName, startAmt, userName, password;


	public UserPojo() {

	}
	public UserPojo(int age, int id, int balanace, String firstName, String lastName, String startAmt, String userName,
			String password) {
		super();
		this.age = age;
		this.id = id;
		this.balanace = balanace;
		this.firstName = firstName;
		this.lastName = lastName;
		this.startAmt = startAmt;
		this.userName = userName;
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBalanace() {
		return balanace;
	}
	public void setBalanace(int balanace) {
		this.balanace = balanace;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStartAmt() {
		return startAmt;
	}
	public void setStartAmt(String startAmt) {
		this.startAmt = startAmt;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

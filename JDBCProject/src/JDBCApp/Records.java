package JDBCApp;

public class Records {

	private int id;
	private String name;
	private long phoneno;
	private String email;

	public Records(String name,long phoneno,String email){

		this.name=name;
		this.phoneno=phoneno;
		this.email=email;

	}


	public Records(){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(long phoneno) {
		this.phoneno = phoneno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

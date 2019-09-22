package JDBCApp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLController implements App_Vars{

	private Connection con;
	private Statement st;

	//FXMLBrowse Fields
	public TextArea displayTable;
	public Button displayTab;
	public Label browseLabel;
	public Button browseRecord;
	public Button searchRecord;
	public Button addRecord;
	public Button modifyRecord;
	public Button backButton;

	//FXMLAdd Fields
	public TextField addName;
	public TextField addPhoneno;
	public TextField addEmail;
	public Button addNewRecord;
	public Label addLabel;

	//FXMLSearch Fields
	public TextField searchBox;
	public Button searchName;
	public Button searchNumber;
	public Button searchAddress;
	public Label searchResult;

	//FXMLModify Fields
	public Label modifyRow;
	public TextField modifyBox;

	//FXMLExit Fields
	public Button exitButton;

public FXMLController() {

		try {
			Class.forName(driver);

			con=DriverManager.getConnection(url,user,pwd);

			st=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);


		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	@FXML
	public void Browse(ActionEvent event) throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("FXMLBrowse.fxml"));

        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

	}

	@FXML
	public void Search(ActionEvent event) throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("FXMLSearch.fxml"));

        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

	}

	@FXML
	public void Add(ActionEvent event) throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("FXMLAdd.fxml"));

        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

	}

	@FXML
	public void Modify(ActionEvent event) throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("FXMLModify.fxml"));

        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

	}

	public boolean addRecord(){
		boolean check=true;

		String name=addName.getText();
		long number=Long.parseLong(addPhoneno.getText());
		String email=addEmail.getText();

		Records r=new Records(name,number,email);

		String sql="insert into phonebook(name,phoneno,email) values('"+r.getName()+"',"+r.getPhoneno()+",'"+r.getEmail()+"')";

		try {
			int result=st.executeUpdate(sql);
			if(result!=1)
				{check=false;}
			else{
				addLabel.setText("Your Record Has Been Added");
			}
		}
		catch (Exception e) {

			System.out.println("not working");
			e.printStackTrace();
		}
		return check;
	}


	public boolean displayTable() {
		String s=new String();
		boolean check=true;
		ArrayList<Records> arraylist=new ArrayList<Records>();

			String sql="select name,phoneno,email from phonebook";

			try {
			ResultSet rs=st.executeQuery(sql);

			while(rs.next()){
				Records r=new Records(rs.getString("name"),rs.getLong("phoneno"),rs.getString("email"));
				arraylist.add(r);
			}

			for(Records r : arraylist){
				s += String.format("%15s%20s%20s\n", r.getName(),r.getPhoneno(),r.getEmail());
			}

			browseLabel.setText(s);
		}
			catch (Exception e) {
			check=false;
			e.printStackTrace();
		}

		return check;
	}


	public void displaySearchTable(ArrayList<Records> list){

		String s=new String();

		for(Records r : list){
			s += String.format("%15s%20s%20s\n", r.getName(),r.getPhoneno(),r.getEmail());
		}

		searchResult.setText(s);
	}

	@FXML
	public ArrayList<Records> searchMethodName(String find){

		ArrayList<Records> searchlist=new ArrayList<Records>();
		String sql="select name,phoneno,email from phonebook where name like '%"+find+"%' or phoneno like '%"+find+"%' or email like '%"+find+"%'";

			try {
				ResultSet rs = st.executeQuery(sql);
					while(rs.next()){
						Records r=new Records(rs.getString("name"),rs.getLong("phoneno"),rs.getString("email"));
						searchlist.add(r);
				}

			}
			catch (Exception e) {
					e.printStackTrace();
			}

		return searchlist;
		}

	public void searchName(ActionEvent event){
		String find=searchBox.getText();

		ArrayList<Records> list=searchMethodName(find);
		displaySearchTable(list);
	}



	public void modifySearch(ActionEvent event){

		String si=modifyBox.getText();

		ArrayList<Records> list=searchMethodName(si);

		String s=new String();

		for(Records r : list){

			s += String.format("%15s%20s%20s\n", r.getName(),r.getPhoneno(),r.getEmail());

		}
		System.out.println(s);
		modifyRow.setText(s);
	}

	@FXML
	public void goBack(ActionEvent event) throws IOException{

		Parent root = FXMLLoader.load(getClass().getResource("FXMLDoc.fxml"));
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

	}

	public void exitAction(ActionEvent event) throws IOException{

		((Node)(event.getSource())).getScene().getWindow().hide();

	}




}

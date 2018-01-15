import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserHome extends JFrame implements ActionListener
{
	
	private JLabel title, searchLabel;
	private JTextField searchBox;
	private JButton searchButton, showAllButton,logout;
	private JPanel panel;

	public UserHome()
	{
		super("Job Search");
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		panel.setLayout(null);
		
		title = new JLabel("Search Jobs");
		title.setBounds(400, 100, 500, 100);
		title.setFont(new Font("Cambria", Font.BOLD, 40));
		panel.add(title);

		searchLabel = new JLabel("Enter Job Query:");
		searchLabel.setBounds(300, 200, 400, 20);
		searchLabel.setFont(new Font("Cambria", Font.PLAIN, 20));
		panel.add(searchLabel);

		searchBox = new JTextField();
		searchBox.setBounds(300, 230, 400, 40);
		panel.add(searchBox);

		searchButton = new JButton("Search");
		searchButton.setBounds(400, 350, 200, 50);
		searchButton.setBackground(Color.WHITE);
		searchButton.addActionListener(this);
		panel.add(searchButton);

		showAllButton = new JButton("Show All");
		showAllButton.setBounds(850, 600, 100, 30);
		showAllButton.setBackground(Color.WHITE);
		showAllButton.addActionListener(this);
		panel.add(showAllButton);
		
		logout = new JButton("Logout");
		logout.setBounds(50, 600, 100, 30);
		logout.setBackground(Color.WHITE);
		logout.addActionListener(this);
		panel.add(logout);

		this.add(panel);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String elementText = ae.getActionCommand();
		
		if(elementText.equals(searchButton.getText()))
		{
			String s = searchBox.getText();
			//String[] words=s1.split(",");
			//String fullQuery="";
			//for(int i=0;i<words.length;i++){
			//	fullQuery = fullQuery+""+"+"+words[i];
			//}
			//System.out.println(fullQuery);
			if((searchBox.getText() == null) || (searchBox.getText().equals(""))){
				JOptionPane.showMessageDialog(this,"Invalid Query");
			}else{
        		try
				{
					String query = "SELECT title,link FROM `description` WHERE `title` LIKE '%"+s+"%' OR `JobRequirements` LIKE '%"+s+"%' OR `JobResponsibilities` LIKE '%"+s+"%';";     
        			String fullQueryCounter = "SELECT COUNT(*) as total FROM `description` WHERE `title` LIKE '%"+s+"%' OR `JobRequirements` LIKE '%"+s+"%' OR `JobResponsibilities` LIKE '%"+s+"%';";
        			Connection con=null;
        			Statement st = null;
					ResultSet rs = null;
					ResultSet rsCounter = null;
					//System.out.println(query);
					Class.forName("com.mysql.jdbc.Driver");
					//System.out.println("driver loaded");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
					//System.out.println("connection done");
					st = con.createStatement();
					//System.out.println("statement created");
					rs = st.executeQuery(query);
					//System.out.println("results received");
					//System.out.println("============");
					//System.out.println("====ghgh==sd==");
					st = con.createStatement();
					//System.out.println("====ghgh=====");
					
					rsCounter = st.executeQuery(fullQueryCounter);
					//System.out.println("=====44444=");

					JobNameShow j = new JobNameShow(rs,rsCounter,query,fullQueryCounter);
					this.setVisible(false);
					j.setVisible(true);	
				}
				catch(Exception ex){
					System.out.println("Exception : " +ex.getMessage());
        		}
			}
		}
		else if(elementText.equals(showAllButton.getText()))
		{
			String fullQuery = "SELECT `title`, `link` FROM `description`;";
			String fullQueryCounter = "SELECT COUNT(*) AS total FROM `description`;";
			try{
				Connection con=null;
        		Statement st = null;
 				ResultSet rs = null;
 				ResultSet rsCounter = null;
				Class.forName("com.mysql.jdbc.Driver");
				//System.out.println("driver loaded");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
				//System.out.println("connection done");
				st = con.createStatement();
				//System.out.println("statement created");
				rs = st.executeQuery(fullQuery);
				//System.out.println("results received");
				st = con.createStatement();
				rsCounter = st.executeQuery(fullQueryCounter);
				JobNameShow j = new JobNameShow(rs,rsCounter,fullQuery,fullQueryCounter);
				this.setVisible(false);
				j.setVisible(true);
			}catch(Exception ex){
				System.out.println("Exception : " +ex.getMessage());
			}
		}else if(elementText.equals(logout.getText())){
			Login lo = new Login();
			this.setVisible(false);
			lo.setVisible(true);
		}
		else{}
	}

	public static void main(String[] args){
		UserHome a = new UserHome();
		a.setVisible(true);
	}
}
	

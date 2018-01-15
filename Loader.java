import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Loader extends JFrame implements ActionListener
{
	private JLabel nameLabel, passLabel,appName;
	private JTextField userNameTF;
	private JPasswordField passPF;
	private JButton buttonLogin,createUser;
	private JPanel panel;
	private JRadioButton radioAdmin, radioUser;
	private ButtonGroup bg;
	private boolean flag;
	
	public Loader()
	{
		super("Loader");
		
		this.setSize(1000, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		appName = new JLabel("Local IT Job Finder");
		appName.setBounds(300, 20, 400, 50);
		appName.setFont(new Font("Cambria", Font.BOLD, 40));
		panel.add(appName);
		
		buttonLogin = new JButton("Show Existing Data");
		buttonLogin.setBounds(30,120,450,450);
		buttonLogin.setBackground(Color.WHITE);
		buttonLogin.addActionListener(this);
		panel.add(buttonLogin);

		createUser = new JButton("Refresh Data");
		createUser.setBounds(500,120,450,450);
		createUser.setBackground(Color.WHITE);
		createUser.addActionListener(this);
		panel.add(createUser);
	
		this.add(panel);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String buttonClicked = ae.getActionCommand();
		
		if(buttonClicked.equals(buttonLogin.getText()))
		{
			Login lo = new Login();
			this.setVisible(false);
			lo.setVisible(true);
		}
		else if(buttonClicked.equals(createUser.getText()))
		{
			//this.setVisible(false);
			//Loading lsa = new Loading();
			//lsa.setVisible(true);
			JOptionPane.showMessageDialog(this,"Loading Data... . You will be redirected After loading");
			try
			{
				String query = "DELETE FROM `description`;";     
        		Connection con=null;
        		Statement st = null;
				ResultSet rs = null;
				ResultSet rsCounter = null;

				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
				st = con.createStatement();
				int rs1 = st.executeUpdate(query);

				MainScrapperJobComBD obj1 = new MainScrapperJobComBD();
    			obj1.scrapperLink("http://job.com.bd/jobs/?c=10");
    			obj1.scrapperPost(); 
			
				MainScrapperChakri obj = new MainScrapperChakri();
  				obj.scrapperLink("http://www.chakri.com/job?catId=25");
  				obj.scrapperPost();
			}
			catch(Exception ex){
				System.out.println("Exception : " +ex.getMessage());
        	}


        	//lsa.setVisible(false);
    		this.setVisible(false);

    		Login lo = new Login();
			lo.setVisible(true);
		}
	}
}

				
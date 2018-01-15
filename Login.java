import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener
{
	private JLabel nameLabel, passLabel,appName;
	private JTextField userNameTF;
	private JPasswordField passPF;
	private JButton buttonLogin,createUser,refresh;
	private JPanel panel;
	private JRadioButton radioAdmin, radioUser;
	private ButtonGroup bg;
	private boolean flag;
	
	public Login()
	{
		super("Login Window");
		
		this.setSize(1000, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		appName = new JLabel("Local IT Job Finder");
		appName.setBounds(300, 20, 400, 50);
		appName.setFont(new Font("Cambria", Font.BOLD, 40));
		panel.add(appName);

		nameLabel = new JLabel("User Name	: ");
		nameLabel.setBounds(300, 220, 150, 25);
		nameLabel.setFont(new Font("Cambria", Font.ITALIC, 20));
		panel.add(nameLabel);
		
		userNameTF = new JTextField();
		userNameTF.setBounds(450, 220, 250, 25);
		panel.add(userNameTF);
		
		passLabel = new JLabel("Password	: ");
		passLabel.setBounds(300, 280, 150, 25);
		passLabel.setFont(new Font("Cambria", Font.ITALIC, 20));
		panel.add(passLabel);
		
		passPF = new JPasswordField();
		passPF.setBounds(450, 280, 250, 25);
		panel.add(passPF);
		
		buttonLogin = new JButton("Login");
		buttonLogin.setBounds(620,350,80,40);
		buttonLogin.setBackground(Color.WHITE);
		buttonLogin.addActionListener(this);
		panel.add(buttonLogin);

		createUser = new JButton("Create new account");
		createUser.setBounds(750,550,200,40);
		createUser.setBackground(Color.WHITE);
		createUser.addActionListener(this);
		panel.add(createUser);

		refresh = new JButton("Reset Preference");
		refresh.setBounds(20,550,200,40);
		refresh.setBackground(Color.WHITE);
		refresh.addActionListener(this);
		panel.add(refresh);
	
		this.add(panel);
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String buttonClicked = ae.getActionCommand();
		
		if(buttonClicked.equals(buttonLogin.getText()))
		{
			flag=true;
			check();
		}
		else if(buttonClicked.equals(createUser.getText()))
		{
			CreateNewUser m = new CreateNewUser();
			this.setVisible(false);
			m.setVisible(true);
		}
		else if(buttonClicked.equals(refresh.getText()))
		{
			Loader l = new Loader();
			this.setVisible(false);
			l.setVisible(true);
		}
	}
	
	public void check()
	{
        String query = "SELECT `Approval`, `Type`, `Name`, `Password`, `Email` FROM `user`;";     
        Connection con=null;
        Statement st = null;
		ResultSet rs = null;
		//System.out.println(query);
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
			//System.out.println("connection done");
			st = con.createStatement();
			//System.out.println("statement created");
			rs = st.executeQuery(query);
			//System.out.println("results received");
					
			while(rs.next())
			{
                String userId = rs.getString("Name");
                String password = rs.getString("Password");
				String type = rs.getString("Type");
				String approval = rs.getString("Approval");
				
				
				if(userId.equals(userNameTF.getText()))
				{
					flag=false;
					if(password.equals(passPF.getText()))
					{
						if(type.equals("normal"))
						{
							
							if(approval.equals("1")){
								UserHome ush = new UserHome();
								this.setVisible(false);
								ush.setVisible(true);
							}else{
								JOptionPane.showMessageDialog(this,"Unapproved");
							};
						}
						else if(type.equals("admin"))
						{
							AdminHome adh = new AdminHome();
							this.setVisible(false);
							adh.setVisible(true);
						}
					}
					else
					{
						JOptionPane.showMessageDialog(this,"Invalid pass"); 
					}
				}			
			}
			if(flag){
				JOptionPane.showMessageDialog(this,"Invalid name");
			}
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
        finally
		{
            try
			{
                if(rs!=null)
					rs.close();

                if(st!=null)
					st.close();

                if(con!=null)
					con.close();
            }
            catch(Exception ex){}
        }
    }
}

				
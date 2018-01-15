import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

class AdminHome extends JFrame implements ActionListener
{
	private JLabel label,action_tag,updateEmail_tag,deleteEmail_tag,update,delete,info;
	private JTextField updateEmail,deleteEmail,action;
	private JButton logoutButton,updateButton,deleteButton;
	private JPanel panel;
	private JTable myTable;
	private JScrollPane tableScrollPane;

	private String [][]row;
	
	public AdminHome()
	{
		super("Admin");
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		label = new JLabel("Account Management");
		label.setBounds(20, 20, 600, 50);
		label.setFont(new Font("Cambria",Font.BOLD, 40));
		panel.add(label);

		update = new JLabel("Update Status:");
		update.setBounds(680, 100, 200, 50);
		update.setFont(new Font("Cambria", Font.ITALIC+Font.BOLD, 25));
		panel.add(update);

		updateEmail_tag = new JLabel("Email:");
		updateEmail_tag.setBounds(700, 170, 80, 30);
		updateEmail_tag.setFont(new Font("Cambria", Font.ITALIC, 15));
		panel.add(updateEmail_tag);

		updateEmail = new JTextField();
		updateEmail.setBounds(800,170,120,30);
		panel.add(updateEmail);

		action_tag = new JLabel("Approval:");
		action_tag.setBounds(700, 220, 80, 30);
		action_tag.setFont(new Font("Cambria", Font.ITALIC, 15));
		panel.add(action_tag);

		action = new JTextField();
		action.setBounds(800,220,120,30);
		panel.add(action);

		info = new JLabel("(1 for Account Enabling, 0 for disabling)");
		info.setBounds(700, 250, 270, 30);
		info.setFont(new Font("Cambria",Font.PLAIN, 13));
		panel.add(info);

		updateButton = new JButton("Update");
		updateButton.setBackground(Color.WHITE);
		updateButton.setBounds(820,300,100,40);
		updateButton.addActionListener(this);
		panel.add(updateButton);

		delete = new JLabel("Delete:");
		delete.setBounds(680, 360, 200, 50);
		delete.setFont(new Font("Cambria", Font.ITALIC+Font.BOLD, 25));
		panel.add(delete);

		deleteEmail_tag = new JLabel("Email:");
		deleteEmail_tag.setBounds(700, 430, 80, 30);
		deleteEmail_tag.setFont(new Font("Cambria", Font.ITALIC, 15));
		panel.add(deleteEmail_tag);

		deleteEmail = new JTextField();
		deleteEmail.setBounds(800,430,120,30);
		panel.add(deleteEmail);

		deleteButton = new JButton("Delete");
		deleteButton.setBackground(Color.WHITE);
		deleteButton.setBounds(820,480,100,40);
		deleteButton.addActionListener(this);
		panel.add(deleteButton);

		logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.WHITE);
		logoutButton.setBounds(855,600,120,50);
		logoutButton.addActionListener(this);
		panel.add(logoutButton);

		String queryCount = "SELECT COUNT(*) as dataCount FROM `user`;"; 
		String query =  "SELECT * FROM `user`;";   
       	Connection con=null;
       	Statement st = null;
       	ResultSet rsCount = null;
		ResultSet rs = null;
		String []col = {"Name", "Password", "E-mail","Type","Action"};
        
        try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("driver loaded");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
			//System.out.println("connection done");
			st = con.createStatement();
			//System.out.println("statement created");
			rsCount = st.executeQuery(queryCount);
			//System.out.println("results received");
			rsCount.next();
			int count = rsCount.getInt("dataCount");
    	    String [][]row = new String[count][5];
			
			st = con.createStatement();
			//System.out.println("statement created");
			rs = st.executeQuery(query);
			//System.out.println("results received");
			rs.next();

			for(int i=0;i<count;i++)
			{
				String type = rs.getString("Type");
				String name = rs.getString("Name");
				String password = rs.getString("Password");
				String email = rs.getString("Email");
				String approval = rs.getString("Approval");
				row[i][0] = name;
				row[i][1] = password;
				row[i][2] = email;
				row[i][3] = type;
				row[i][4] = approval;
				rs.next();
			}
			myTable = new JTable(row,col);
			tableScrollPane = new JScrollPane(myTable);
			tableScrollPane.setBounds(20,100,650,500);
			panel.add(tableScrollPane);
		}catch(Exception ex){
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
		
		this.add(panel);

	}
	public void actionPerformed(ActionEvent ae)
	{
		String buttonClicked = ae.getActionCommand();
		
		if(buttonClicked.equals(logoutButton.getText())){
			Login l = new Login();
			l.setVisible(true);
			this.setVisible(false);
		}
		else if(buttonClicked.equals(updateButton.getText())){
			String account = updateEmail.getText();
			String approve = action.getText();
	       			Connection con=null;
	       			Statement st = null;
					ResultSet rs = null;
					String query = "UPDATE user SET Approval='"+approve+"' WHERE Email='"+account+"';";
					//System.out.println(query);
					try{
						Class.forName("com.mysql.jdbc.Driver");
						//System.out.println("driver loaded");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
						//System.out.println("connection done");
						st = con.createStatement();
						//System.out.println("statement created");
						int rs1 = st.executeUpdate(query);
						//System.out.println("results received");
						JOptionPane.showMessageDialog(this,"Updated");
					}catch(Exception ex){
						JOptionPane.showMessageDialog(this,"Exception : " +ex.getMessage());
	        		}finally{
	            		try{
	                		if(rs!=null)
								rs.close();

	                		if(st!=null)
								st.close();

	                		if(con!=null)
								con.close();
	            		}catch(Exception ex){}
	        		}
		}
		else if(buttonClicked.equals(deleteButton.getText())){
			String account = deleteEmail.getText();
			String query = "DELETE FROM user WHERE Email='"+account+"';";
			System.out.println(query);
			Connection con=null;
       		Statement st = null;
			ResultSet rs = null;
			try{
				Class.forName("com.mysql.jdbc.Driver");
				//System.out.println("driver loaded");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
				//System.out.println("connection done");
				st = con.createStatement();
				//System.out.println("statement created");
				int rs1 = st.executeUpdate(query);
				//System.out.println("results received");
				JOptionPane.showMessageDialog(this,"Deleted");
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,"Exception : " +ex.getMessage());
        	}finally{
            	try{
                	if(rs!=null)
						rs.close();

                	if(st!=null)
						st.close();

                	if(con!=null)
						con.close();
            	}catch(Exception ex){}
        	}		
		}
	}
}
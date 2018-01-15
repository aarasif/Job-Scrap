import java.lang.*;
import java.util.*;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class JobNameShow extends JFrame implements ActionListener{
	private JLabel intro;
	private JButton button[],back,logout;
	private JPanel panel;
	private int y_axis,numberOfRecords;
	private JScrollPane pane;
	private ArrayList jLinks = new ArrayList();
	private String dataQuery,dataQueryCounter;
	public JobNameShow(ResultSet rs,ResultSet rs1,String dataQuery, String dataQueryCounter){
		super("Local IT Job Finder");
		this.setSize(1000,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.dataQuery=dataQuery;
		this.dataQueryCounter=dataQueryCounter;

        try
		{
			panel = new JPanel();
			panel.setLayout(null);
			panel.setPreferredSize( new Dimension(1000, 5000) );

			rs1.next();
			numberOfRecords = rs1.getInt("total");
			button = new JButton[numberOfRecords];;
			intro = new JLabel(numberOfRecords+" Data Found !!");
			intro.setBounds(450,0,500,50);
			intro.setFont(new Font("Times New Roman", Font.BOLD, 20));
			panel.add(intro);
			
			y_axis = 55;
			int i =0;
			while(rs.next()){	
				button[i] = new JButton(rs.getString("title"));
				jLinks.add(rs.getString("link"));
				button[i].setBounds(50,y_axis,900,60);
				button[i].setBackground(Color.WHITE);
				button[i].setFont(new Font("Times New Roman", Font.BOLD, 18));
				button[i].addActionListener(this);
				panel.add(button[i]);
				y_axis+=65;
				i++;
			}
			pane = new JScrollPane(panel);
			pane.setBounds(0,0,1000,3000);
			pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  
       		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       		
       		back = new JButton("Back");
			back.setBounds(850, 20, 100, 30);
			back.setBackground(Color.WHITE);
			back.addActionListener(this);
			panel.add(back);
			
			logout = new JButton("Logout");
			logout.setBounds(50, 20, 100, 30);
			logout.setBackground(Color.WHITE);
			logout.addActionListener(this);
			panel.add(logout);

			this.add(pane);
		}
        catch(Exception ex)
		{
			System.out.println("Exception : " +ex.getMessage());
        }
		
	}
	 public void actionPerformed(ActionEvent ae){
		String elementText = ae.getActionCommand();
		for(int i=0;i<numberOfRecords;i++){
			if(elementText.equals(button[i].getText())){
				String fullQuery = "SELECT * FROM `description` WHERE Link='"+jLinks.get(i)+"';";
				try{
					Connection con=null;
        			Statement st = null;
 					ResultSet rs = null;
					Class.forName("com.mysql.jdbc.Driver");
					System.out.println("driver loaded");
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
					System.out.println("connection done");
					st = con.createStatement();
					System.out.println("statement created");
					rs = st.executeQuery(fullQuery);
					System.out.println("results received");

					PostUI p = new PostUI(rs,dataQuery,dataQueryCounter);
					this.setVisible(false);
					p.setVisible(true);
				}catch(Exception ex){
					System.out.println("Exception : " +ex.getMessage());
				}
			}
		}
		if(elementText.equals(back.getText())){
			UserHome us = new UserHome();
			this.setVisible(false);
			us.setVisible(true);
		}
		else if(elementText.equals(logout.getText())){
			Login lo = new Login();
			this.setVisible(false);
			lo.setVisible(true);
		}
	}
}
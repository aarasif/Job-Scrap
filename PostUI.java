import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class PostUI extends JFrame implements ActionListener
{
	
	private JTextField link,jobResponsibilities,jobRequirements,jobVacancies,jobSalary,jobDeadline;
	private JLabel title,link_tag,jobResponsibilities_tag,jobRequirements_tag,jobVacancies_tag,jobSalary_tag,jobDeadline_tag;
	private JButton back,logout;
	private JPanel panel;
	private String dataQuery,dataQueryCounter;

	public PostUI(ResultSet rs,String dataQuery, String dataQueryCounter)
	{
		super("Job Details");
		this.setSize(1000, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.dataQuery=dataQuery;
		this.dataQueryCounter=dataQueryCounter;
		
		panel = new JPanel();
		panel.setLayout(null);
		try{
			rs.next();
			String titles = rs.getString("title");
			String links = rs.getString("link");
			String jobResponsibilitiess = rs.getString("JobResponsibilities");
			String jobRequirementss = rs.getString("JobRequirements");
			String jobVacanciess = rs.getString("Vacancy");
			String jobSalarys = rs.getString("Salary");
			String jobDeadlines = rs.getString("Deadline");
			
			title = new JLabel(titles);
			title.setBounds(10, 10, 500, 50);
			title.setFont(new Font("Cambria", Font.BOLD, 25));
			panel.add(title);

			jobRequirements_tag = new JLabel("Job Requirements: ");
			jobRequirements_tag.setBounds(10, 70, 200, 200);
			jobRequirements_tag.setFont(new Font("Cambria", Font.BOLD, 15));
			panel.add(jobRequirements_tag);

			jobRequirements = new JTextField(jobRequirementss);
			jobRequirements.setEditable(false);
			jobRequirements.setBounds(250, 70, 500, 200);
			jobRequirements.setFont(new Font("Cambria", Font.ITALIC, 15));
			panel.add(jobRequirements);

			jobResponsibilities_tag = new JLabel("Job Responsibilities: ");
			jobResponsibilities_tag.setBounds(10, 270, 200, 200);
			jobResponsibilities_tag.setFont(new Font("Cambria", Font.BOLD, 15));
			panel.add(jobResponsibilities_tag);
		
		
			jobResponsibilities = new JTextField(jobResponsibilitiess);
			jobResponsibilities.setEditable(false);
			jobResponsibilities.setBounds(250, 270, 500, 200);
			jobResponsibilities.setFont(new Font("Cambria", Font.ITALIC, 15));
			panel.add(jobResponsibilities);

			jobVacancies_tag = new JLabel("Job Vacancies: ");
			jobVacancies_tag.setBounds(10, 470, 200, 20);
			jobVacancies_tag.setFont(new Font("Cambria", Font.BOLD, 15));
			panel.add(jobVacancies_tag);
		
		
			jobVacancies = new JTextField(jobVacanciess);
			jobVacancies.setEditable(false);
			jobVacancies.setBounds(250, 470, 500, 20);
			jobVacancies.setFont(new Font("Cambria", Font.ITALIC, 15));
			panel.add(jobVacancies);
		
			jobSalary_tag = new JLabel("Job Salary: ");
			jobSalary_tag.setBounds(10, 500, 200, 20);
			jobSalary_tag.setFont(new Font("Cambria", Font.BOLD, 15));
			panel.add(jobSalary_tag);
		
		
			jobSalary = new JTextField(jobSalarys);
			jobSalary.setEditable(false);
			jobSalary.setBounds(250, 500, 500, 20);
			jobSalary.setFont(new Font("Cambria", Font.ITALIC, 15));
			panel.add(jobSalary);

			jobDeadline_tag = new JLabel("Deadline: ");
			jobDeadline_tag.setBounds(10, 530, 200, 20);
			jobDeadline_tag.setFont(new Font("Cambria", Font.BOLD, 15));
			panel.add(jobDeadline_tag);
		
		
			jobDeadline = new JTextField(jobDeadlines);
			jobDeadline.setEditable(false);
			jobDeadline.setBounds(250, 530, 500, 20);
			jobDeadline.setFont(new Font("Cambria", Font.ITALIC, 15));
			panel.add(jobDeadline);

			link_tag = new JLabel("Link: ");
			link_tag.setBounds(10, 560, 200, 20);
			link_tag.setFont(new Font("Cambria", Font.BOLD, 15));
			panel.add(link_tag);
		
		
			link = new JTextField(links);
			link.setEditable(false);
			link.setBounds(250, 560, 500, 20);
			link.setFont(new Font("Cambria", Font.ITALIC, 15));
			panel.add(link);

			back = new JButton("Back");
			back.setBounds(850, 600, 100, 30);
			back.setBackground(Color.WHITE);
			back.addActionListener(this);
			panel.add(back);
			
			logout = new JButton("Logout");
			logout.setBounds(50, 600, 100, 30);
			logout.setBackground(Color.WHITE);
			logout.addActionListener(this);
			panel.add(logout);

			this.add(panel);
		}catch(Exception ex){
			System.out.println("Exception : " +ex.getMessage());
		}
		
		
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		String elementText = ae.getActionCommand();
	 	if(elementText.equals(back.getText())){
	 		try{
				Connection con=null;
        		Statement st = null;
 				ResultSet rs = null;
 				ResultSet rsCounter = null;
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("driver loaded");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/local it job finder","root","");
				System.out.println("connection done");
				st = con.createStatement();
				System.out.println("statement created");
				rs = st.executeQuery(dataQuery);
				System.out.println("results received");
				st = con.createStatement();
				rsCounter = st.executeQuery(dataQueryCounter);
				JobNameShow j = new JobNameShow(rs,rsCounter,dataQuery,dataQueryCounter);
				this.setVisible(false);
				j.setVisible(true);
			}catch(Exception ex){
				System.out.println("Exception : " +ex.getMessage());
			}
		}
		else if(elementText.equals(logout.getText())){
			Login lo = new Login();
			this.setVisible(false);
			lo.setVisible(true);
		}	
	}
}
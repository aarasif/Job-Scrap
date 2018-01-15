import java.lang.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Loading extends JFrame
{
	private JLabel imgLabel,text;
	private ImageIcon image;
	private JPanel panel;
	public Loading()
	{
		super("Loading");
		
		this.setSize(1000, 650);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		
		text = new JLabel("Loading...!");
		text.setBounds(300,100,400,200);
		text.setFont(new Font("Times New Roman", Font.BOLD+Font.ITALIC, 60));
		panel.add(text);

		image = new ImageIcon("./loader.gif");
		imgLabel = new JLabel(image);
		imgLabel.setBounds(400,200,200,200);
		panel.add(imgLabel);
		
		this.add(panel);

	}
}

				
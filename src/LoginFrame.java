import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private JButton loginButton;
	private JTextField usernameArea;
	private JPasswordField passwordArea;
	
	public LoginFrame() {
		setTitle("Login");
		setSize(600, 400);
		//setLayout(new GridLayout(5, 2, 15, 15));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(100, 100);
        
        JPanel outsidePanel = new JPanel();
        outsidePanel.setBorder(new EmptyBorder(15,15,15,15));
        
        JPanel insidePanel = new JPanel(new BorderLayout());
        
        JPanel infoPanel = new JPanel(new GridLayout(2,2,10,10));
        infoPanel.add(new JLabel("User Name:"));
        usernameArea = new JTextField(15);
        infoPanel.add(usernameArea);
        infoPanel.add(new JLabel("Password:"));
        passwordArea = new JPasswordField(15);
        infoPanel.add(passwordArea);
        
        insidePanel.add(infoPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);
        buttonPanel.add(loginButton);
        
        insidePanel.add(buttonPanel, BorderLayout.SOUTH);
        
        outsidePanel.add(insidePanel);
        
        add(outsidePanel);
        
        setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String query = "SELECT * FROM users WHERE user_name='" + usernameArea.getText() + "'";
		try {
			ResultSet rs = DatabaseFactory.executeQuery(query);
			if(!rs.isBeforeFirst()) {
				JOptionPane.showMessageDialog(this, "Error in username or password");
			}
			else {
				rs.next();		// move to first entry in result set
				String p = rs.getString("password");
				String i = new String(passwordArea.getPassword());
				if(p.equals(i)) {
					new TablesFrame();
					this.dispose();
				}
				else {
					JOptionPane.showMessageDialog(this, "Error in username or password");
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}

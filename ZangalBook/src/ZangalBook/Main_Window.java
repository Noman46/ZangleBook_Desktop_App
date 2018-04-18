package ZangalBook;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Statement;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.List;

import javax.swing.SwingConstants;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Main_Window extends JFrame {

	private JPanel contentPane;
	private CardLayout card;
	private JTextField txtAnimal;
	private JLabel adminImage;
	String ImgPath;
	private JTextField text_admin_name;
	private JTextField textUserName;
	private JTextField textPassword;
	private JTextField text_username;
	private JTextField text_password;
	private String userName;
	private String passWord;
	private JLabel lbl_image;
	private JLabel lbl_title;
	int pos=0;

	
	
	//Creating connection method ---------------------------+++++++++++++++++++++++++++++++	
public Connection getConnection () 
	
	{
		
		Connection con = null;
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/zangolbook","root","");
			//JOptionPane.showMessageDialog(null,"conected");
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null," not conected");
			Logger.getLogger(Main_Window.class.getName()).log(Level.SEVERE,null,e);
			return null;
		}
		
	}

// End Of Connection method--------------------------+++++++++++++++++++++++++++


//Image resize method--------------------++++++++++++++++++++++++++++



	public ImageIcon ResizeImage(String imagePath, byte [] pic) {
		
		ImageIcon myImage=null;
		if(imagePath !=null)
		{
			myImage = new ImageIcon(imagePath);
			
		}else {
			myImage = new ImageIcon(pic);
		}
		
		Image img =myImage.getImage();
		Image img2=img.getScaledInstance(adminImage.getWidth(), adminImage.getHeight(), Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(img2);
		return image;
	}

	

//End of Image resizing------------------++++++++++++++++++++++++++++++
	
	
// Method for taking the out put of resultset-----------------=======-----------
	
	public java.util.List<Item>  getItemsList(){
		
		try {
			
			Connection con =getConnection();
			Statement st = (Statement) con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM animalimage");
			java.util.List<Item> list = new ArrayList<Item>();
			Item item;
			while(rs.next()) {
				
				item= new Item(rs.getString("name"),rs.getBytes("image"));
				list.add(item);
			}
			return list;
			
			
		}
		catch(Exception e){
			
			e.printStackTrace();
			return null;
		}
		
	}
	
	//-----------------------------------+++++++++++++++++++----------------------
	
	public void showItem(int index)
	{
		
		ImageIcon icon = new ImageIcon(getItemsList().get(index).getImage());
		Image image = icon.getImage().getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
		lbl_image.setIcon(new ImageIcon(image));
		
	
		String animal_name = txtAnimal.getText();
		String animal_name_db= getItemsList().get(index).getName();
		
		
		if(animal_name.equalsIgnoreCase(animal_name_db)) {
			JOptionPane.showMessageDialog(null,"Wow the Answer is correct, try the next one");
			
		}
		else {
			
			JOptionPane.showMessageDialog(null,"OOps the answer is not correct,try again");
		}
		
		
		
	}
	
	
	
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Window frame = new Main_Window();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main_Window() {
		//showItem(pos);
		getConnection ();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		card= new CardLayout();
		contentPane.setLayout(card);
		
		JPanel first = new JPanel();
		first.setBackground(new Color(0, 0, 128));
		contentPane.add(first, "first");
		first.setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("Zangal Book");
		lblNewLabel.setBackground(new Color(102, 205, 170));
		lblNewLabel.setForeground(new Color(0, 0, 128));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(32, 11, 341, 34);
		first.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Know Animal");
		lblNewLabel_1.setBackground(new Color(255, 192, 203));
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel_1.setBounds(32, 76, 103, 22);
		first.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("ANIMAL");
		btnNewButton.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
				
				
				Connection con= getConnection();
				try {
					Statement st = (Statement) con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM `animalimage`");
					if(rs.next()) {
						byte [] img= rs.getBytes("image");
						ImageIcon emage =new ImageIcon(img);
						Image im = emage.getImage();
						Image myImg = im.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon newImage= new ImageIcon(myImg);
						lbl_image.setIcon(newImage);
						lbl_title.setText("ANIMAL");
						card.show(contentPane, "last");
					}else {
						JOptionPane.showMessageDialog(null," Image not found");
					}
						
					}
				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				   
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		btnNewButton.setBounds(284, 77, 89, 23);
		first.add(btnNewButton);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(contentPane, "loginPanel");
			}
		});
		btnAdmin.setBounds(284, 217, 89, 23);
		first.add(btnAdmin);
		
		
		
		JPanel last = new JPanel();
		last.setBackground(new Color(250, 235, 215));
		contentPane.add(last, "last");
		last.setLayout(null);
		
		lbl_title = new JLabel("");
		lbl_title.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title.setBounds(137, 11, 136, 26);
		last.add(lbl_title);
		
		JLabel lblNewLabel_3 = new JLabel("Look At The Image and Let Us Know The Name");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(10, 48, 404, 26);
		last.add(lblNewLabel_3);
		
		lbl_image = new JLabel("              image");
		lbl_image.setBackground(new Color(255, 192, 203));
		lbl_image.setOpaque(true);
		lbl_image.setBounds(20, 85, 175, 144);
		last.add(lbl_image);
		
		txtAnimal = new JTextField();
		txtAnimal.setBounds(234, 85, 162, 26);
		last.add(txtAnimal);
		txtAnimal.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Your Answer Is Correct");
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSubmit.setBounds(234, 206, 89, 23);
		last.add(btnSubmit);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
		

			public void actionPerformed(ActionEvent e) {
				

				
				
				
				
				
				pos++;
				
				if(pos>=getItemsList().size()) {
					
					pos=getItemsList().size()-1;
					
				}
				showItem(pos);
				
				
			
				
				
				
				/*Connection con= getConnection();
				try {
					Statement st = (Statement) con.createStatement();
					ResultSet rs = st.executeQuery("SELECT * FROM `animalimage`");
					while(rs.next()) {
						byte [] img= rs.getBytes("image");
						ImageIcon emage =new ImageIcon(img);
						Image im = emage.getImage();
						Image myImg = im.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon newImage= new ImageIcon(myImg);
						lbl_image.setIcon(newImage);
						//lbl_title.setText("ANIMAL");
						//card.show(contentPane, "last");
					}
						
					}
				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNext.setBounds(326, 116, 68, 23);
		last.add(btnNext);
		
		JButton btnNewButton_4 = new JButton("Back");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 
			    card.show(contentPane, "first");
			}
			
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_4.setBounds(325, 146, 68, 23);
		last.add(btnNewButton_4);
		
		JPanel admin = new JPanel();
		admin.setBackground(new Color(0, 0, 128));
		contentPane.add(admin, "admin");
		admin.setLayout(null);
		
		adminImage = new JLabel("   Image");
		adminImage.setOpaque(true);
		adminImage.setBackground(new Color(255, 192, 203));
		adminImage.setBounds(10, 11, 175, 144);
		admin.add(adminImage);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                  
				if(/*text_admin_name.getText()==null &&*/ ImgPath !=null) { /* Make a check input method for this text_admin_name*/
					
					Connection con =getConnection();
					try {
						PreparedStatement ps=con.prepareStatement("INSERT INTO animalimage(name,image)" 
					+"values(?,?)");
						
						ps.setString(1, text_admin_name.getText());
					
						
						 InputStream img = new FileInputStream(new File(ImgPath));
						 ps.setBlob(2,img);
						 ps.executeUpdate();
						 JOptionPane.showMessageDialog(null, "Data Inserted");						
		
					} catch (SQLException | FileNotFoundException e1) {
						 // TODO Auto-generated catch block
						 //e1.printStackTrace();
						 JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					
					
				}else {
					

					JOptionPane.showMessageDialog(null, "One or more Data Field Are Empty");
					
				}
				
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsert.setBounds(325, 217, 89, 23);
		admin.add(btnInsert);
		
		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser file = new JFileChooser();
				   file.setCurrentDirectory(new File(System.getProperty("user.home")));
				   
				   FileNameExtensionFilter filter = new  FileNameExtensionFilter("*.images","jpg","png");
				   file.addChoosableFileFilter(filter);
				   int result=file.showSaveDialog(null);
				   
				   if(result == JFileChooser.APPROVE_OPTION) {
					   File selectedFile = file.getSelectedFile();
					   String path = selectedFile.getAbsolutePath();
					   adminImage.setIcon(ResizeImage(path,null));
					   ImgPath=path;
				   }else {
					   
					   System.out.println("File not selected");
				   }
				
			}
		});
		btnChoose.setBounds(46, 166, 89, 23);
		admin.add(btnChoose);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBackground(new Color(255, 192, 203));
		lblName.setOpaque(true);
		lblName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(207, 11, 46, 14);
		admin.add(lblName);
		
		text_admin_name = new JTextField();
		text_admin_name.setBounds(207, 36, 131, 20);
		admin.add(text_admin_name);
		text_admin_name.setColumns(10);
		
		JPanel loginPanel = new JPanel();
		loginPanel.setBackground(new Color(0, 0, 128));
		contentPane.add(loginPanel, "loginPanel");
		loginPanel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("User Name");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBackground(Color.DARK_GRAY);
		lblNewLabel_4.setOpaque(true);
		lblNewLabel_4.setBounds(28, 24, 89, 17);
		loginPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Password");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setForeground(Color.WHITE);
		lblNewLabel_5.setBackground(Color.DARK_GRAY);
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBounds(28, 66, 89, 17);
		loginPanel.add(lblNewLabel_5);
		
		textUserName = new JTextField();
		textUserName.setBounds(133, 21, 141, 20);
		loginPanel.add(textUserName);
		textUserName.setColumns(10);
		
		textPassword = new JTextField();
		textPassword.setBounds(133, 63, 141, 20);
		loginPanel.add(textPassword);
		textPassword.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	//calling the connection method
			
				String query ="SELECT * FROM `loginformation`";	
			
				
				Connection con = getConnection();
				try {
					Statement st=(Statement) con.createStatement();
					ResultSet rs = st.executeQuery(query);
					 while (rs.next()) {
					        String userName = rs.getString("username");
					        String passWord = rs.getString("password");
					        
					        if((textUserName.getText().equals(userName)) && (textPassword.getText().equals(passWord)))
					        {
					        	card.show(contentPane, "admin");
					        }
					        else
					        {
					        	
					        	JOptionPane.showMessageDialog(null,"Provided informations are wrong");
					        }
						 
					 
					 }
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
						
				
				
				//card.show(contentPane, "admin");
			}
		});
		btnLogin.setBackground(Color.GREEN);
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLogin.setBounds(28, 128, 107, 28);
		loginPanel.add(btnLogin);
		
		JButton btnNewButton_2 = new JButton("Change password");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setBackground(Color.GREEN);
		btnNewButton_2.setBounds(159, 128, 141, 28);
		loginPanel.add(btnNewButton_2);
		
		JLabel lblNewLabel_6 = new JLabel("Creat A new Account");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setForeground(Color.WHITE);
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_6.setBackground(Color.DARK_GRAY);
		lblNewLabel_6.setBounds(28, 191, 246, 28);
		loginPanel.add(lblNewLabel_6);
		
		JButton btnCreat = new JButton("Creat");
		btnCreat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(contentPane, "registration");
			}
		});
		btnCreat.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreat.setBackground(Color.GREEN);
		btnCreat.setBounds(299, 191, 95, 28);
		loginPanel.add(btnCreat);
		
		JPanel registration = new JPanel();
		contentPane.add(registration, "registration");
		registration.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("Give user name");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setBackground(Color.DARK_GRAY);
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_7.setForeground(Color.WHITE);
		lblNewLabel_7.setOpaque(true);
		lblNewLabel_7.setBounds(29, 30, 186, 24);
		registration.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("New label");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBackground(Color.DARK_GRAY);
		lblNewLabel_8.setForeground(Color.WHITE);
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_8.setOpaque(true);
		lblNewLabel_8.setBounds(29, 80, 186, 24);
		registration.add(lblNewLabel_8);
		
		text_username = new JTextField();
		text_username.setBounds(225, 30, 145, 24);
		registration.add(text_username);
		text_username.setColumns(10);
		
		text_password = new JTextField();
		text_password.setBounds(225, 80, 145, 27);
		registration.add(text_password);
		text_password.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Checking the right format of crating account
				/*String userNm = text_username.getText();
				String pass = text_password.getText();
				
				if(userNm.length()>8)
				{
					JOptionPane.showMessageDialog(null,"User Nmae must be less than 8 Character");
				}
				else if(pass.length()>8)
				{
					{
						JOptionPane.showMessageDialog(null,"Password must be greater than 3 Character");
					}
					
				}*/
				
				
				
			//caling the connection method
				Connection con = getConnection();
				
				try {
					PreparedStatement ps = con.prepareStatement("INSERT INTO loginformation(username,password)" +"values(?,?)");
					ps.setString(1, text_username.getText());
					ps.setString(2, text_password.getText());
					ps.executeUpdate();
					//JOptionPane.showMessageDialog(null,"A new Account Is Created");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setBounds(296, 177, 89, 23);
		registration.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Back");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(contentPane, "loginPanel");
			}
		});
		btnNewButton_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_3.setBounds(29, 178, 89, 23);
		registration.add(btnNewButton_3);
		//showItem(pos);
	}
}

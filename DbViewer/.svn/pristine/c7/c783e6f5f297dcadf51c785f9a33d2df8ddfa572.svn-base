/*
 * ************************************************************************************************************************************************
 * 
 *     PSW - DBViewer
 * __________________
 * The MIT License (MIT)
 * Copyright (c) 2016 Jelena Jankovic RA 139/2013, Nikola Kukavica RA 98/2013, Viktor Sanca RA 1/2013, Marko Bender 213/2012
 * 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT  
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO 
 * EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER 
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR  
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 *************************************************************************************************************************************************/
 
 package dialogs;

import gui.MainFrame;
import interpreter.JSONInterpreter;
import interpreter.SQLInterpreter;
import model.DBSchema;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import security.MD5Hashing;
import sql.DbConnection;
import application.Application;

@SuppressWarnings("serial")
/**
 * Login dialog consists of GUI as well as user authentication for getting access to the application.
 * Based on user choice data is retrieved either from JSON or SQL. 
 * Passwords are stored as MD5 hash before check is performed.
 * @author Viktor
 * @author Marko
 * @author Jelena
 */
public class LoginDialog extends JFrame {
	/**
	 * Main panel for displaying components.
	 */
	private JPanel panel;
	/**
	 * Locale of application
	 */
	private Locale l = Application.getLocale(); 
	/**
	 * Application resource bundle
	 */
	private ResourceBundle r = Application.getResourceBundle(); 
	/**
	 * Possible interpreter options
	 */
	private static final String[] INTERPRETER_OPTIONS = {"JSON", "SQL"};
	/**
	 * Combo box index for JSON interpreter.
	 */
	private static final int JSON_INTERPRETER_INDEX = 0;
	/**
	 * Combo box index for SQL interpreter.
	 */
	private static final int SQL_INTERPRETER_INDEX = 1;
	/**
	 * Possible languages.
	 */
	private static String[] languages = {"Srpski", "English", "Српски"};
	
	/**
	 * Path to the JSON file containing the {@link DBSchema} description.
	 */
	private static final String PATH_TO_JSON = "PSW_MPS_2016.pdm.json";
	
	/**
	 * Interpreter label.
	 */
	private JLabel interpreterLabel;
	/**
	 * Language label.
	 */
	private JLabel languageLabel;
	/**
	 * Username label.
	 */
	private JLabel usernameLabel;
	/**
	 * Password label.
	 */
	private JLabel passwordLabel;

	/**
	 * Combo box for selecting the interpreter.
	 */
	private JComboBox<String> interpreter;
	/**
	 * Combo box for selecting the language.
	 */
	private JComboBox<String> language;
	/**
	 * Username entry field.
	 */
	private JTextField username;
	/**
	 * Password entry field.
	 */
	private JPasswordField password;
	
	/**
	 * Button for logging in.
	 */
	private JButton loginButton;
	/**
	 * Button for exiting the login dialog and application.
	 */
	private JButton exitButton;
	
	/**
	 * LoginDialog instance for singleton pattern
	 */
	private static LoginDialog instance;
	
	/**
	 * Returns an instance of a login dialog.
	 * 
	 * @return {@link LoginDialog} instance of login dialog
	 */
	public static LoginDialog getInstance() {
		if(instance==null){
			instance = new LoginDialog();
		}
		
		return instance;
	}
	
	/**
	 * Default constructor of a login dialog. Sets up GUI and actions.
	 */
	private LoginDialog(){
		
		setupFrame();
		
		setExitAction();
		
		setLoginAction();
	}
	
	/**
	 * Sets up GUI layout for login dialog.
	 */
	private void setupFrame() {
		
		selectLocale(Application.applicationProperties.getProperty("Language"));
		
		System.out.println(Application.applicationProperties.getProperty("Language"));
		
		setTitle(r.getString("LoginTitle").toString());
		setSize(new Dimension(500,250));
		
		setIconImage(new ImageIcon("images/icons/icon_256.png").getImage());
		
		//interpreterLabel  = new JLabel("Interpreter");
		interpreterLabel  = new JLabel(r.getString("Interpreter")+":");
		languageLabel  = new JLabel(r.getString("Language")+":");
		usernameLabel  = new JLabel(r.getString("Username")+":");
		passwordLabel  = new JLabel(r.getString("Password")+":");
		
		interpreter = new JComboBox<String>();
		for(String io : INTERPRETER_OPTIONS)
			interpreter.addItem(io);
		
		language = new JComboBox<String>();
		for(String l : languages)
			language.addItem(l);
		
		// sets the action listener on language selection combo box to change of display language
		setLanguageAction();
		
		username = new JTextField(10);
		//gets last used username
		username.setText(Application.applicationProperties.getProperty("LastUsername"));//username.setText("admin");//username.setText(Application.applicationProperties.getProperty("DBUsername"));//
		password = new JPasswordField(10);
		//gets last used password
		//String md5Password = MD5HashingExample.getMD5(Application.applicationProperties.getProperty("LastPassword")); 
		password.setText("admin");//password.setText("admin");//password.setText(Application.applicationProperties.getProperty("DBPassword"));//
		
		loginButton = new JButton("Login");
		//loginButton.setIcon(new ImageIcon(new ImageIcon("images/icons/login.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		
		exitButton = new JButton("Exit");
		//exitButton.setIcon(new ImageIcon(new ImageIcon("images/icons/exit.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		
		GridLayout layout = new GridLayout(5,2);
		layout.setHgap(10);
		layout.setVgap(10);
		
		panel = new JPanel(layout);
		panel.setBorder(new EmptyBorder(20, 20, 20, 20));
		add(panel);
		
		panel.add(interpreterLabel);
		panel.add(interpreter);
		
		panel.add(languageLabel);
		panel.add(language);
		
		panel.add(usernameLabel);
		panel.add(username);
		
		panel.add(passwordLabel, password);
		panel.add(password);
		
		panel.add(loginButton);
		panel.add(exitButton);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Sets up the exit button event to close the application.
	 */
	private void setExitAction() {
		//dodata akcija da se na klik exit dugmeta prozori i aplikacija zatvore.
		exitButton.addActionListener(new ActionListener() {
					
		@Override
		public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	/**
	 * Sets up the login button event and login and authentication procedures.
	 */
	private void setLoginAction() {
		loginButton.addActionListener(new ActionListener() {

			@SuppressWarnings("static-access")
			@Override
			public void actionPerformed(ActionEvent e) {
		
				// sets the cursor to wait to show that some process is taking place
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				boolean successfulLogin = false;
				
				// gets the entered username and password
				String uname = new String(username.getText());
				String pass = new String(password.getPassword());
				
				//Perform MD5 hash before comparing values.
				if(!uname.equals(Application.applicationProperties.getProperty("LastUsername"))) {
					pass = MD5Hashing.getMD5(pass); 
				} else {
					pass = Application.applicationProperties.getProperty("LastPassword");
				}
				
				// login logic - checking whether user with entered username and password exists in the database
				Connection c = DbConnection.getInstance().getConnection();
				try {
					PreparedStatement ps = c.prepareStatement("SELECT REG_IDENTIFIKATOR FROM REGISTROVANI_KORISNICI WHERE REG_USER_NAME = ? AND REG_LOZINKA = ?");
					ps.setString(1, uname);
					ps.setString(2, pass);
					
					ResultSet users = ps.executeQuery();
					
					// for getting the users from the database
					if(users.next()){
						successfulLogin = true;
					}
					
					// for testing purposes independet from the database
					if(uname.equals("admin") && pass.equals(Application.applicationProperties.getProperty("LastPassword"))){
						successfulLogin = true;
					}
						
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				// sets the cursor back to normal
				panel.setCursor(Cursor.getDefaultCursor());
				
				if(successfulLogin) {
					// if login is successful then close the login dialog and show the application GUI
					dispose();
					MainFrame mainFrame = MainFrame.getInstance();
					if(interpreter.getSelectedIndex() == SQL_INTERPRETER_INDEX) {
						// sql
						mainFrame.setInterpreter(new SQLInterpreter());
					} else {
						// json
						mainFrame.setInterpreter(new JSONInterpreter(PATH_TO_JSON));
					}
					Application.interpreter = interpreter.getSelectedIndex();
					mainFrame.initialize();
					mainFrame.setVisible(true);
					
					// sets the language setting for the application
					MainFrame.getInstance().getLocale().setDefault(l);
					Application.setLocale(l);
					Application.updateLanguage(l);
					
					// set properties for personalization or easier login (e.g. "remember" me option)
					Application.applicationProperties.setProperty("LastUsername", username.getText());
					Application.applicationProperties.setProperty("LastPassword", new String(pass));
				} else {
					// if login is unsuccessful show a message and get back to the login dialog
					JOptionPane.showMessageDialog(MainFrame.getInstance(),r.getString("LoginErrorText"), r.getString("LoginError"),JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * Sets the language picker action to the combo box.
	 */
	private void setLanguageAction() {
		language.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedLanguage = (String)language.getSelectedItem(); 
				
				selectLocale(selectedLanguage);
				
				changeLanguage();
			}
		});
	}
	
	/**
	 * Method for selecting the locale according to the combo-box selected value.
	 */
	public void selectLocale(String selectedLanguage) {
		if(selectedLanguage.equals("English")) {
			ResourceBundle.clearCache();
			String lang = "en"; 
			String country = "US"; 
			l = new Locale(lang, country);
		}else if(selectedLanguage.equals("Srpski")) {
			ResourceBundle.clearCache();
			String lang = "sr"; 
			String country = "RS"; 
			l = new Locale(lang, country); 
		}else if(selectedLanguage.equals("Српски")) {
			ResourceBundle.clearCache();
			String lang = "sr"; 
			String country = "RS"; 
			String option = "Cyrl";
			l = new Locale(lang, country, option); 
		}
		
		Application.setLocale(l);
		r = ResourceBundle.getBundle("MessageResources.lang", l);
		
		Application.applicationProperties.setProperty("Language",selectedLanguage);
		
		if(language != null) {
			language.setSelectedItem(selectedLanguage);
		}
	}
	
	/**
	 * Method for changing the dialog language.
	 */
	public void changeLanguage() {
		
		r = ResourceBundle.getBundle("MessageResources.lang", l);
		
		interpreterLabel.setText(r.getString("Interpreter")+":");
		languageLabel.setText(r.getString("Language")+":");
		usernameLabel.setText(r.getString("Username")+":");
		passwordLabel.setText(r.getString("Password")+":");
		
		loginButton.setText(r.getString("Login"));
		exitButton.setText(r.getString("Exit"));
		
		setTitle(r.getString("LoginTitle").toString());
		
	}
	
}

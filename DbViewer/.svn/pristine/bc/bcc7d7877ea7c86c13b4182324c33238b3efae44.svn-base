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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import application.Application;
import gui.MainFrame;
import sql.DbConnection;

@SuppressWarnings("serial")
/**
 * Dialog which acts as a splash screen while the application is preparing it's resources.
 * 
 * Connection is set up with the database and if it is established, it proceeds to the login dialog.
 * If the connection is not made, the application terminates since such problem cannot be handled from the application.
 * 
 * @author Viktor
 *
 */
public class OpeningDialog extends JFrame{
	/**
	 * Instance of {@link OpeningDialog}, as a basis for the singleton pattern
	 */
	private static OpeningDialog instance;
	/**
	 * Progress bar for improving the UX and making users aware that some process is taking place
	 */
	private JProgressBar progressBar;
	
	/**
	 * Returns an instance of {@link OpeningDialog}, as a basis for the singleton pattern.
	 * 
	 * @return
	 */
	public static OpeningDialog getInstance() {
		if(instance == null) {
			instance = new OpeningDialog();
		}
		return instance;
	}
	
	/**
	 * Default constructor. Initializes the GUI and tries establishing the connection with the database.
	 */
	private OpeningDialog() {
		init();
		
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		Thread t1 = new Thread(new Runnable() {
		     public void run() {
		    	 int counter = 0;
		         while(counter <= progressBar.getMaximum()){
		        	 try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		        	 counter ++;
		        	 progressBar.setValue(counter);
		          
		         }
		     }
		});  
		t1.start();
		
		boolean success = initConnection();
		
		progressBar.setValue(progressBar.getMaximum());
		
		System.out.println("KONEKCIJA INICIJALIZOVANA!");
		
		setCursor(Cursor.getDefaultCursor());
		
		if(success){
			LoginDialog login = LoginDialog.getInstance();
			login.setVisible(true);
			
			setVisible(false);
			dispose();
		} else {
			JOptionPane.showMessageDialog(this,"Došlo je do greške pri povezivanju na bazu podataka.", "Greška",JOptionPane.ERROR_MESSAGE);
			setVisible(false);
			System.exit(-1);
		}
		
	}
	
	/**
	 * Helper method for setting up the GUI.
	 */
	private void init() {
		setUndecorated(true);
		setTitle("DBViewer");
		setSize(new Dimension(500,400));
		getContentPane().setSize(new Dimension(500,400));
		setIconImage(new ImageIcon("images/icons/icon_256.png").getImage());
		
		getContentPane().setLayout(new BorderLayout());
		
		JLabel background = new JLabel("", new ImageIcon(new ImageIcon("images/icon_512.png").getImage().getScaledInstance(600, 500, java.awt.Image.SCALE_SMOOTH)),JLabel.CENTER);
		background.setSize(new Dimension(600, 500));

		getContentPane().add(background, BorderLayout.CENTER);
		
		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(500, 15));
		progressBar.setForeground(Color.GREEN);
		progressBar.setMinimum(0);
		progressBar.setMaximum(10);
		
		getContentPane().add(progressBar, BorderLayout.SOUTH);
		
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		setVisible(true);
	}
	
	/**
	 * Helper method for establishing connection with the database.
	 * 
	 * @return Returns {@link Boolean} as an indication whether the connection was set up properly
	 */
	public boolean initConnection(){
		String url = Application.applicationProperties.getProperty("DBUrl");//"jdbc:jtds:sqlserver://147.91.175.155:1433/psw-2016-tim5-1";
		String username = Application.applicationProperties.getProperty("DBUsername");//"psw-2016-tim5-1";
		String password = Application.applicationProperties.getProperty("DBPassword");//"tim5-1223248";
		
		boolean success = false;
		
		try {
			DbConnection.getInstance().setConnection(DriverManager.getConnection(url, username, password));
			success = true;
		} catch (SQLException e1) {

			url = Application.applicationProperties.getProperty("DBAlternativeUrl");//"jdbc:jtds:sqlserver://192.168.77.230:1433/psw-2016-tim5-1";
			
			// login credentials are wrong
			if(e1.getErrorCode()==18456)
				return false;
			
			try {
				DbConnection.getInstance().setConnection(DriverManager.getConnection(url, username,password));
				success = true;
			} catch (SQLException e2) {
				// login credentials are wrong
				if(e1.getErrorCode()==18456)
					return false;
				JOptionPane.showMessageDialog(MainFrame.getInstance(),"Došlo je do greške.", "Greška",JOptionPane.ERROR_MESSAGE);
				//e2.printStackTrace();
			}					
		}
		
		return success;
	}
}

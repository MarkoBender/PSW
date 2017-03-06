/*************************************************************************************************************************************************
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
 
 package application;

import gui.MainFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.UIManager;

import model.DBSchema;
import dialogs.LoginDialog;
import dialogs.OpeningDialog;

/**
 * 
 * Main class for running the application.
 * 
 * @author Viktor
 * @author Jelena
 * @author Marko
 * @author Nikola
 *
 */
public class Application {
	
	/**
	 * Global locale to be used for the application.
	 */
	private static Locale locale = new Locale("en","US");
	/**
	 * Resource bundle with localization strings for the static fields in the application (main GUI).
	 */
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources.lang", locale);
	/**
	 * Resource bundle with localization strings for the dynamic fields (tables, columns, attributes).
	 */
	private static ResourceBundle dynamicBundle = ResourceBundle.getBundle("DynamicLocalization.PSW_MPS_2016", locale);
	/**
	 * Global {@link DBSchema} instance.
	 */
	private static DBSchema schema;
	/**
	 * Properties file which contains application settings.
	 */
	public static Properties applicationProperties = new Properties();
	/**
	 * Global test iteration for checking whether an infinite recursion happens when interpreting data. 
	 * If it is greater than 1, an infinite recursion happened and stack overflow would follow if not handled.
	 */
	public static int testIteration = 0;
	/**
	 * Interpreter choice: 0 - JSON, 1 - SQL
	 */
	public static int interpreter = 0;
	/**
	 * If JUnit test is conducted, set to true, otherwise set to false.
	 */
	public static boolean test = false;
	
	/**
	 * Reference to the singleton DBErrorHandler object.
	 */
	public static DBErrorHandler dbeh=null;
	
	/**
	 * Main method, an entry point when the application starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    } catch (Exception e) {
	    }
		
		try {
			applicationProperties.load(new FileInputStream("resources/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dbeh=DBErrorHandler.getInstance();
		OpeningDialog od = OpeningDialog.getInstance();
		
	}
/*
		private static ResourceBundle createResourceBundle() {
		return ResourceBundle.getBundle("gui.MessageResources.MessageResources", locale, new Control() {
			@Override
			public List<Locale> getCandidateLocales(String baseName, Locale locale) {
				if (baseName == null)
					throw new NullPointerException();
				if (locale.getLanguage().equals("sr") && locale.getCountry().equals("RS")) {
					if (locale.getScript().equals("Latn"))
						return Arrays.asList(new Locale("sr", "RS", "latin"));
					else
						return Arrays.asList(new Locale("sr", "RS", "cyrilic"));
				}
				return super.getCandidateLocales(baseName, locale);
			}

		});
	}
	*/
	

	
	/**
	 * Method for getting singleton {@link DBErrorHandlerObject} object.
	 */
	public static DBErrorHandler getDbeh() {
		return dbeh;
	}

	/**
	 * Method for updating language dynamically in the application.
	 * 
	 * @param locale Selected locale for displaying language
	 */
	public static void updateLanguage(Locale locale){
		Application.setResourceBundle(ResourceBundle.getBundle("MessageResources/lang", Application.getLocale()));
		Application.setDynamicBundle(ResourceBundle.getBundle("DynamicLocalization.PSW_MPS_2016", Application.getLocale()));
		MainFrame.getInstance().updateLanguage();
		LoginDialog.getInstance().changeLanguage();
	}
	
	/**
	 * Gets the applications {@link Locale}.
	 * 
	 * @return {@link Locale}
	 */
	public static Locale getLocale() {
		return locale;
	}

	public static void setLocale(Locale locale) {
		Application.locale = locale;
	}

	/**
	 * Gets the applications {@link ResourceBundle}.
	 * 
	 * @return {@link ResourceBundle}
	 */
	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	/**
	 * Sets the applications {@link ResourceBundle}.
	 * 
	 * @param resourceBundle
	 */
	public static void setResourceBundle(ResourceBundle resourceBundle) {
		Application.resourceBundle = resourceBundle;
	}

	/**
	 * Gets the applications {@link DBSchema}.
	 * 
	 * @return {@link DBSchema}
	 */
	public static DBSchema getSchema() {
		return schema;
	}

	/**
	 * Sets the applications {@link DBSchema}.
	 * 
	 * @param schema
	 */
	public static void setSchema(DBSchema schema) {
		Application.schema = schema;
	}
	
	/**
	 * Gets the applications {@link ResourceBundle}.
	 * 
	 * @return {@link ResourceBundle}
	 */
	public static ResourceBundle getDynamicBundle() {
		return dynamicBundle;
	}
	
	/**
	 * Sets the applications {@link ResourceBundle}.
	 * 
	 * @param dynamicBundle
	 */
	public static void setDynamicBundle(ResourceBundle dynamicBundle) {
		Application.dynamicBundle = dynamicBundle;
	}
	
}

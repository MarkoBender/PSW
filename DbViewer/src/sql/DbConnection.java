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
 
 package sql;

import java.sql.Connection;

/**
 * Setting connection to the database
 * @author Nikola
 * @author Marko
 *
 */

public class DbConnection {

	/**
	 * Singleton reference to {@link DbConnection} class.
	 */
	private static DbConnection instance = null;
	/**
	 * {@link Connection} reference.
	 */
	private static Connection connection;

	/**
	 * Private constructor that tries to establish the connection.
	 */
	private DbConnection() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the instance of {@link DbConnection} and instantiates it if it is not already instantiated.
	 * @return DbConnection
	 */
	public static DbConnection getInstance(){
		if(instance == null)
			instance = new DbConnection();
		return instance;
	}
	
	/** 
	 * Gets the {@link Connection}
	 * @return Connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Sets the {link Connection}
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		DbConnection.connection = connection;
	}
}

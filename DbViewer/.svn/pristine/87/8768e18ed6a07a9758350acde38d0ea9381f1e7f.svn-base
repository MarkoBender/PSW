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
 
 package model;

/**
 * Class representing column constraints reflecting on Table behavior. Such constraints represent fields that are:
 * Mandatory, Primary Key, Foreign Key.
 * 
 * @author Viktor
 *
 */
public class TableConstraint {
	/**
	 * Is primary key.
	 */
	private Boolean primaryKey = false;
	/**
	 * Is foreign key.
	 */
	private Boolean foreignKey = false;
	/**
	 * Is mandatory field.
	 */
	private Boolean mandatory = false;
	
	/**
	 * Is primary key.
	 * 
	 * @return
	 */
	public Boolean getPrimaryKey() {
		return primaryKey;
	}
	
	/**
	 * Sets if primary key.
	 * 
	 * @param primaryKey
	 */
	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	/**
	 * Is foreign key.
	 * 
	 * @return
	 */
	public Boolean getForeignKey() {
		return foreignKey;
	}
	
	/**
	 * Sets if foreign key.
	 * 
	 * @param foreignKey
	 */
	public void setForeignKey(Boolean foreignKey) {
		this.foreignKey = foreignKey;
	}
	
	/**
	 * Is mandatory field.
	 * 
	 * @return
	 */
	public Boolean getMandatory() {
		return mandatory;
	}
	
	/**
	 * Sets if mandatory field.
	 * 
	 * @param mandatory
	 */
	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	@Override
	public String toString() {
		return "TableConstraint [primaryKey=" + primaryKey + ", foreignKey=" + foreignKey + ", mandatory=" + mandatory
				+ "]";
	}
}

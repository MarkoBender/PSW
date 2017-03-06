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

import java.sql.Types;

/**
 * Column is the representation of a column in a relational table. 
 * 
 * @author Viktor
 * @author Jelena
 * @author Nikola
 */
public class Column {
	
	/**
	 * Column unique identifier.
	 */
	private String code; 
	/**
	 * Actual column name.
	 */
	private String name; 
	/**
	 * {@link DataConstraint} for this column.
	 */
	private DataConstraint type;
	/**
	 * {@link TableConstraint} for this column.
	 */
	private TableConstraint constraint;
	/**
	 * {@link ValueConstraint} for this column.
	 */
	private ValueConstraint valueConstraints;
	
	/**
	 * Default constructor.
	 */
	public Column() {
		
	}

	/**
	 * Returns column name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets column name.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns column code.
	 * @return code.
	 */
	public String getCode() {
		return code;
	}
	/**
	 * Sets column code.
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Returns {@link DataConstraint} value.
	 * @return type
	 */
	public DataConstraint getType() {
		return type;
	}
	/**
	 * Sets {@link DataConstraint} value.
	 * @param type
	 */
	public void setType(DataConstraint type) {
		this.type = type;
	}

	/**
	 * Returns {@link TableConstraint} value.
	 * @return constraint
	 */
	public TableConstraint getConstraint() {
		return constraint;
	}
	/**
	 * Sets {@link TableConstraint} value.
	 * @param constraint
	 */
	public void setConstraint(TableConstraint constraint) {
		this.constraint = constraint;
	}
	/**
	 * Returns {@link ValueConstraint} value.
	 * @return valueConstraints
	 */
	public ValueConstraint getValueConstraints() {
		return valueConstraints;
	}
	/**
	 * Sets {@link ValueConstraint} value.
	 * @param valueConstraints
	 */
	public void setValueConstraints(ValueConstraint valueConstraints) {
		this.valueConstraints = valueConstraints;
	}

	@Override
	public String toString() {
		return name+" ["+code + "] ";
	}
	
	/**
	 * Maps meta-schema type names to SQL type names
	 * @return {@link int} - {@link java.sql.Types} value
	 */
	public int getSqlType() {
		int sqlType = -1;
	
		switch(type.getType()) {
			case VARCHAR:
				sqlType = Types.VARCHAR;
				break;
			case BLOB:
				sqlType = Types.BLOB;
				break;
			case CHAR:
				sqlType = Types.CHAR;
				break;
			case INTEGER:
				sqlType = Types.INTEGER;
				break;
			case NUMERIC:
				sqlType = Types.NUMERIC;
				break;
			case TIMESTAMP:
				sqlType = Types.TIMESTAMP;
				break;
			default:
				break;
		}
		
		return sqlType;
	}
}

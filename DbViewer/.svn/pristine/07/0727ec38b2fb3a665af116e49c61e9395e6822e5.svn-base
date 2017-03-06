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

import org.codehaus.jackson.annotate.JsonCreator;

/**
 * Maps application-specific data types to sql-specific data types.
 * @author Jelena
 *
 */
public enum DataType {
	
	VARCHAR("varchar"),
	CHAR("char"),
	INTEGER("int"),
	NUMERIC("numeric"),
	TIMESTAMP("datetime"),
	BLOB("image");

	/**
	 * String representation of enum type.
	 */
    private String value;

    /**
     * Constructor.
     * @param value
     */
	DataType(final String value) {
        this.value = value;
    }

	/**
	 * Returns string representation of enum type.
	 * @return value
	 */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
    /**
     * Enables automatic JSON parsing to enum {@link DataType}.
     * @param value
     * @return
     */
    @JsonCreator
    public static DataType forValue(String value) { 
    	if(BLOB.getValue().equals(value)) {
    		return BLOB;
    	}
    	if(CHAR.getValue().equals(value)) {
    		return CHAR;
    	}                                                                                                                                                                           
    	if(VARCHAR.getValue().equals(value)) {
    		return VARCHAR;
    	}
    	if(INTEGER.getValue().equals(value)) {
    		return INTEGER;
    	}
    	if(NUMERIC.getValue().equals(value)) {
    		return NUMERIC;
    	}
		return TIMESTAMP;    	
    }
    
    
      /**
      * Enables automatic SQL parsing to enum {@link DataType}.
      * @param sqlType
      * @return
      */
    public static DataType sqlToEnum(String sqlType) {
    	if("varchar".equals(sqlType)) {
    		return VARCHAR; 
    	}
    	else if("char".equals(sqlType)) {
    		return CHAR; 
    	}
    	else if("blob".equals(sqlType)) {
    		return BLOB; 
    	}
    	else if("integer".equals(sqlType)) {
    		return INTEGER; 
    	}
    	else if("numeric".equals(sqlType)) {
    		return NUMERIC; 
    	}else {
    		return TIMESTAMP; 
    	}
    }
}

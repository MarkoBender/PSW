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

import java.util.List;

/**
 * This class represents one foreign key constraint in {@link Table}. Foreign key constraint records such connection between tables. 
 * 
 * @author Viktor
 * @author Jelena
 */
public class ForeignKeyConstraint {
	/**
	 * Unique identifier for constraint.
	 */
	private String code;
	/**
	 * Name of constraint, contains semantic value describing this constraint.
	 */
	private String name;
	/**
	 * Unique identifier for referenced table.
	 */
	private String referencedTableCode;
	/**
	 * Unique identifier for referring columns.
	 */
	private List<String> refeeringColumnCodes;
	/**
	 * Unique identifier for referenced columns.
	 */
	private List<String> referencedColumnCodes;
	
	/**
	 * Default constructor.
	 */
	public ForeignKeyConstraint(){
		
	}
/**
 * Gets code.
 * @return
 */
	public String getCode() {
		return code;
	}
/**
 * Sets code.
 * @param code
 */
	public void setCode(String code) {
		this.code = code;
	}
/**
 * Gets name.
 * @return
 */
	public String getName() {
		return name;
	}
/**
 * Sets name.
 * @param name
 */
	public void setName(String name) {
		this.name = name;
	}
/**
 * Gets referenced table.
 * @return
 */
	public String getReferencedTableCode() {
		return referencedTableCode;
	}
/**
 * Sets referenced table.
 * @param referencedTableCode
 */
	public void setReferencedTableCode(String referencedTableCode) {
		this.referencedTableCode = referencedTableCode;
	}
/**
 * Gets referring column.
 * @return
 */
	public List<String> getRefeeringColumnCodes() {
		return refeeringColumnCodes;
	}
/**
 * Sets referring column.
 * @param refeeringColumnCodes
 */
	public void setRefeeringColumnCodes(List<String> refeeringColumnCodes) {
		this.refeeringColumnCodes = refeeringColumnCodes;
	}
/**
 * Gets referenced column.
 * @return
 */
	public List<String> getReferencedColumnCodes() {
		return referencedColumnCodes;
	}
/**
 * Sets referenced column.
 * @param referencedColumnCodes
 */
	public void setReferencedColumnCodes(List<String> referencedColumnCodes) {
		this.referencedColumnCodes = referencedColumnCodes;
	}

	@Override
	public String toString() {
		return "ForeignKey [code=" + code + ", name=" + name + ", referencedTableCode=" + referencedTableCode
				+ ", refeeringColumnCodes=" + refeeringColumnCodes + ", referencedColumnCodes=" + referencedColumnCodes
				+ "]";
	}
}

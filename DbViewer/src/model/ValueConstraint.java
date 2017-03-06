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
 * Represents a value constraint of a table column in relational data model. Value constraints are domain constraints 
 * and provide information about the permitted values: minimum, maximum and list of values in the domain, as well as the default value.
 * 
 * If a value is not defined, there is no assigned value constraint for the specific parameter.
 * 
 * @author Viktor
 *
 */
public class ValueConstraint {
	/**
	 * Minimum value allowed in domain
	 */
	private String minValue;
	/**
	 * Maximum value allowed in domain
	 */
	private String maxValue;
	/**
	 * Default value
	 */
	private String defaultValue;
	/**
	 * List of allowed values that represent the domain
	 */
	private List<Value> listOfValues;
	
	/**
	 * Default Constructor.
	 */
	public ValueConstraint() {
		super();
	}
	
	/**
	 * Gets the minimal valid value.
	 * 
	 * @return
	 */
	public String getMinValue() {
		return minValue;
	}
	
	/**
	 * Sets the minimal valid value.
	 * 
	 * @param minValue
	 */
	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}
	
	/**
	 * Gets the maximum valid value.
	 * 
	 * @return
	 */
	public String getMaxValue() {
		return maxValue;
	}
	
	/**
	 * Sets the maximum valid value.
	 * 
	 * @param maxValue
	 */
	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}
	
	/**
	 * Gets the default value.
	 * 
	 * @return
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * Sets the default value.
	 * 
	 * @param defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	/**
	 * Gets the list of values.
	 * 
	 * @return
	 */
	public List<Value> getListOfValues() {
		return listOfValues;
	}
	
	/**
	 * Sets the list of values.
	 * 
	 * @param listOfValues
	 */
	public void setListOfValues(List<Value> listOfValues) {
		this.listOfValues = listOfValues;
	}
	
}

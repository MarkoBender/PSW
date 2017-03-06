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
 * Represents packages in the tree structure. Packages may contain none, one or more {@link Table} or {@link Package} instances.
 * 
 * @author Jelena
 * @author Viktor
 */
public class Package {
	/**
	 * Unique package identifier.
	 */
	private String packageCode;
	/**
	 * Package name.
	 */
	private String packageName;
	/**
	 * Collection of sub packages, which this package contains.
	 */
	private List<Package> subPackages; 
	
	/**
	 * Default constructor.
	 */
	public Package() {
		
	}
	
	/**
	 * Returns package identifier.
	 * @return packageCode
	 */
	public String getPackageCode() {
		return packageCode;
	}
	/**
	 * Sets package identifier.
	 * @param packageCode
	 */
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	/**
	 * Returns package name.
	 * @return packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * Sets package name.
	 * @param packageName
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	/**
	 * Returns collection of subpackages.
	 * @return subPackages
	 */
	public List<Package> getSubPackages() {
		return subPackages;
	}
	/**
	 * Sets collection of subpackages.
	 * @param subPackages
	 */
	public void setSubPackages(List<Package> subPackages) {
		this.subPackages = subPackages;
	}

	@Override
	public String toString() {
		return packageName;
	}
}

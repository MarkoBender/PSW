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
 
 package gui.menus;

import javax.swing.AbstractAction;
import javax.swing.JMenuBar;

/**
 * 
 * Represents applications main menu bar, contains {@link MenuFile} ,
 * {@link MenuEdit} , {@link MenuRecords} , {@link MenuHelp} and
 * {@link MenuLanguages}
 * 
 * @author Marko
 * 
 * @see AbstractAction
 */
public class MainMenuBar extends JMenuBar{

	/**
	 * {@link MenuFile} reference.
	 */
	private MenuFile menuFile;
	/**
	 * {@link MenuHelp} reference.
	 */
	private MenuHelp menuHelp;
	/**
	 * {@link MenuLanguages} reference.
	 */
	private MenuLanguages menuLanguages;
	
	/**
	 * Constructor of the {@link MainMenuBar} class.
	 * Creates a new instance of each menu it contains.
	 */
	public MainMenuBar() {
		
		menuFile = new MenuFile();
		add(menuFile);
		
		menuHelp = new MenuHelp();
		add(menuHelp);
		
		menuLanguages = new MenuLanguages();
		add(menuLanguages);
		
	}
	
	/**
	 * Returns the {@link MenuFile} object of the menu bar.
	 * @return {@link MenuFile}
	 */
	public MenuFile getMenuFile() {
		return menuFile;
	}

	/**
	 * Sets the {@link MenuFile} object of the menu bar.
	 * @param menuFile
	 */
	public void setMenuFile(MenuFile menuFile) {
		this.menuFile = menuFile;
	}

	/**
	 * Returns the {@link MenuHelp} object of the menu bar.
	 * @return {@link MenuHelp}
	 */
	public MenuHelp getMenuHelp() {
		return menuHelp;
	}

	/**
	 * Sets the {@link MenuHelp} object of the menu bar.
	 * @param menuHelp
	 */
	public void setMenuHelp(MenuHelp menuHelp) {
		this.menuHelp = menuHelp;
	}

	/**
	 * Returns the {@link MenuLanguages} object of the menu bar.
	 * @return {@link MenuLanguages}
	 */
	public MenuLanguages getMenuLanguages() {
		return menuLanguages;
	}

	/**
	 * Sets the {@link MenuLanguages} object of the menu bar.
	 * @param menuLanguages
	 */
	public void setMenuLanguages(MenuLanguages menuLanguages) {
		this.menuLanguages = menuLanguages;
	}
	
	/**
	 * Changes the language of menus by going 
	 * through each menu.
	 */
	public void changeLanguage() {
		menuFile.changeLanguage();
		menuHelp.changeLanguage();
		menuLanguages.changeLanguage();
	}
	
}

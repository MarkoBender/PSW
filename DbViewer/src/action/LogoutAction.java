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
 
package action;

import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import application.Application;
import dialogs.LoginDialog;
import gui.MainFrame;
import tree.SchemaTree;

/**
 * An Action that extends {@link AbstractAction},
 * when invoked,logs the user out of the application.
 * 
 * @author Viktor
 * @author Marko
 * 
 * @see AbstractAction
 *
 */
@SuppressWarnings("serial")
public class LogoutAction extends AbstractAction{


	/**
	 * {@link LogoutAction} public constructor
	 * Initializes the object with a localised name
	 * and a small icon.
	 */
	public LogoutAction() {
		putValue(NAME, Application.getResourceBundle().getString("Logout"));
		putValue(SMALL_ICON, new ImageIcon(new ImageIcon("images/icons/logout.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
	}

	/**
	 * Disposes the {@link MainFrame} singleton object,
	 * then opens a new {@link LoginDialog} with the currently
	 * selected language.
	 * @param arg0
	 * 
	 * @see MainFrame
	 * @see LoginDialog
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		MainFrame.getInstance().dispose();
		MainFrame.getInstance().destroy();
		Application.testIteration = 0;
		
		LoginDialog.getInstance().selectLocale(Application.applicationProperties.getProperty("Language"));
		LoginDialog.getInstance().changeLanguage();
		LoginDialog.getInstance().setVisible(true);
	}
}

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
 
 package application;

import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.Action;

import action.ExitAction;
import action.LogoutAction;
import action.help.*;
import action.language.*;
import action.tree.*;

/**
 * Used for keeping instances of actions that are not record-related and their localization.
 * 
 * @author Marko
 *
 */
public class ActionManager {
	/**
	 * Map of {@link AbstractAction} where key is {@link AbstractAction} name and value is specific {@link AbstractAction}.
	 */
	public static HashMap<String,AbstractAction> map = new HashMap<String,AbstractAction>();
	/**
	 * Reference to the singleton object of {@link ActionManager}.
	 */
	private static ActionManager instance = null;
	/**
	 * {@link ExitAction} reference.
	 */
	private ExitAction exitAction;
	/**
	 * {@link LogoutAction} reference.
	 */
	private LogoutAction logoutAction;
	
	/**
	 * {@link AboutAction} reference.
	 */
	private AboutAction aboutAction;
	
	/**
	 * {@link EnglishAction} reference.
	 */
	private EnglishAction englishAction;
	/**
	 * {@link SerbianCyrillicAction} reference.
	 */
	private SerbianCyrillicAction serbianCyrillicAction;
	/**
	 * {@link SerbianLatinAction} reference.
	 */
	private SerbianLatinAction serbianLatinAction;
	
	/**
	 * {@link CollapseTreeAction} reference.
	 */
	private CollapseTreeAction collapseTreeAction;
	/**
	 * {@link ExpandTreeAction} reference.
	 */
	private ExpandTreeAction expandTreeAction;
	/**
	 * {@link TreeMoveDownAction} reference.
	 */
	private TreeMoveDownAction treeMoveDownAction;
	/**
	 * {@link TreeMoveUpAction} reference.
	 */
	private TreeMoveUpAction treeMoveUpAction;
	
	/**
	 * Static method used to get the instance of {@link @ActionManager}.
	 * @return {@link @ActionManager}
	 */
	public static ActionManager getInstance(){
		if(instance==null)
			instance = new ActionManager();
		return instance;
	}
	
	/**
	 * Private constructor that instanciates managers actions.
	 */
	private ActionManager(){
		this.exitAction = new ExitAction();
		this.logoutAction = new LogoutAction();
		
		this.aboutAction = new AboutAction();
		
		this.englishAction = new EnglishAction();
		this.serbianCyrillicAction = new SerbianCyrillicAction();
		this.serbianLatinAction = new SerbianLatinAction();
		
		this.collapseTreeAction = new CollapseTreeAction();
		this.expandTreeAction = new ExpandTreeAction();
		this.treeMoveDownAction = new TreeMoveDownAction();
		this.treeMoveUpAction = new TreeMoveUpAction();
	}
	
	/**
	 * Gets the manages {@link AboutAction}.
	 * 
	 * @return {@link AboutAction}
	 */
	public AboutAction getAboutAction(){
		return aboutAction;
	}
	
	/**
	 * Gets the manages {@link CollapseTreeAction}.
	 * 
	 * @return {@link CollapseTreeAction}
	 */
	public CollapseTreeAction getCollapseTreeAction() {
		return collapseTreeAction;
	}
	
	/**
	 * Gets the manages {@link ExpandTreeAction}.
	 * 
	 * @return {@link ExpandTreeAction}
	 */
	public ExpandTreeAction getExpandTreeAction() {
		return expandTreeAction;
	}

	/**
	 * Gets the manages {@link TreeMoveDownAction}.
	 * 
	 * @return {@link TreeMoveDownAction}
	 */
	public TreeMoveDownAction getTreeMoveDownAction() {
		return treeMoveDownAction;
	}

	/**
	 * Gets the manages {@link TreeMoveUpAction}.
	 * 
	 * @return {@link TreeMoveUpAction}
	 */
	public TreeMoveUpAction getTreeMoveUpAction() {
		return treeMoveUpAction;
	}

	/**
	 * Gets the manages {@link EnglishAction}.
	 * 
	 * @return {@link EnglishAction}
	 */
	public EnglishAction getEnglishAction() {
		return englishAction;
	}

	/**
	 * Gets the manages {@link SerbianCyrillicAction}.
	 * 
	 * @return {@link SerbianCyrillicAction}
	 */
	public SerbianCyrillicAction getSerbianCyrillicAction() {
		return serbianCyrillicAction;
	}

	/**
	 * Gets the manages {@link SerbianLatinAction}.
	 * 
	 * @return {@link SerbianLatinAction}
	 */
	public SerbianLatinAction getSerbianLatinAction() {
		return serbianLatinAction;
	}

	/**
	 * Gets the manages {@link ExitAction}.
	 * 
	 * @return {@link ExitAction}
	 */
	public ExitAction getExitAction() {
		return exitAction;
	}

	/**
	 * Gets the manages {@link LogoutAction}.
	 * 
	 * @return {@link LogoutAction}
	 */
	public LogoutAction getLogoutAction() {
		return logoutAction;
	}

	/**
	 * Updates language of actions.
	 */
	public static void updateActions(){
		
		map.put("About", ActionManager.getInstance().getAboutAction());
		
		map.put("English", ActionManager.getInstance().getEnglishAction());
		map.put("SerbianC", ActionManager.getInstance().getSerbianCyrillicAction());
		map.put("SerbianL", ActionManager.getInstance().getSerbianLatinAction());
		
		map.put("CollapseTree", ActionManager.getInstance().getCollapseTreeAction());
		map.put("ExpandTree", ActionManager.getInstance().getExpandTreeAction());
		
		map.put("Logout", ActionManager.getInstance().getLogoutAction());
		map.put("Exit", ActionManager.getInstance().getExitAction());
		
		map.put("TreeUp", ActionManager.getInstance().getTreeMoveUpAction());
		map.put("TreeDown", ActionManager.getInstance().getTreeMoveDownAction());
		
		for(String key : map.keySet()){
			AbstractAction action = map.get(key);
			if(Application.getResourceBundle().containsKey(key))
				action.putValue(Action.NAME, Application.getResourceBundle().getString(key));
			if(Application.getResourceBundle().containsKey(key+"Desc"))
				action.putValue(Action.SHORT_DESCRIPTION, Application.getResourceBundle().getString(key + "Desc"));
		}	
	}
	
	public static void disableCRUDActions() {
		/*
		AddRecordDialogAction.getInstance().setEnabled(false);
		DeleteRecordDialogAction.getInstance().setEnabled(false);
		FirstRecordAction.getInstance().setEnabled(false);
		LastRecordAction.getInstance().setEnabled(false);
		ModifyRecordDialogAction.getInstance().setEnabled(false);
		NextRecordAction.getInstance().setEnabled(false);
		PreviousRecordAction.getInstance().setEnabled(false);
		RefreshTableAction.getInstance().setEnabled(false);
		*/
	}
	
	public static void enableCRUDActions() {
		/*
		//AddRecordDialogAction.getInstance().setEnabled(true);
		DeleteRecordDialogAction.getInstance().setEnabled(true);
		FirstRecordAction.getInstance().setEnabled(true);
		LastRecordAction.getInstance().setEnabled(true);
		ModifyRecordDialogAction.getInstance().setEnabled(true);
		NextRecordAction.getInstance().setEnabled(true);
		PreviousRecordAction.getInstance().setEnabled(true);
		RefreshTableAction.getInstance().setEnabled(true);
		*/
	}
}

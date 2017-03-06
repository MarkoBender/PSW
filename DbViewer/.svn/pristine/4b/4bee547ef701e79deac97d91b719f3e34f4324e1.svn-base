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
 
 package gui.validation;

import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import model.DataConstraint;
import model.DataType;
import application.Application;
/**
 * Used for validating {@link JComponent} representing SQL INTEGER type.
 * @author Jelena
 *
 */
public class IntegerVerifier extends AbstractInputVerifier {
	/**
	 * Instance of {@link DataConstraint}. Presents 
	 * the data constraint of the column assigned to input 
	 * field that is being validated.
	 */
	final DataConstraint constraint; 
	/**
	 * Presents a name of current column.
	 */
	final String columnName; 
	/**
	 * Maps Column names to error labels that contain validation errors.
	 */
	final Map<String, JLabel> errorLabelMap; 
	/**
	 * Sets all attributes of object.
	 * @param constraint
	 * @param columnName
	 * @param mandatory
	 * @param errorLabelMap
	 */
	public IntegerVerifier(DataConstraint constraint, String columnName, Boolean mandatory, Map<String, JLabel> errorLabelMap) {
		if(constraint.getType() != DataType.INTEGER) {
			throw new IllegalArgumentException("NumericVerifier cannot be used for dataType other than Integer");
		}
		this.constraint = constraint;
		this.columnName = columnName; 
		this.mandatory = mandatory; 
		this.errorLabelMap = errorLabelMap; 
	}

	@Override
	public boolean verify(JComponent input) {
		String text = null;

        if (input instanceof JTextComponent) {
          text = ((JTextComponent) input).getText();
          text = text.trim(); 
          if(text.length() == 0 && !mandatory) {
        	  return true; 
          }
        }else if (input instanceof JComboBox) {
        	return ((JComboBox) input).getSelectedIndex() != -1 || !mandatory;
        }

        try {
           Integer.parseInt(text);
        } catch (NumberFormatException e) {
           return false;
        }

        int length = constraint.getLength(); 
        if(text.length() > (Integer.MAX_VALUE + "").toString().length()) {
        	return false; 
        }
        
        
        
        return true;
     }
	
	@Override
    public boolean shouldYieldFocus(JComponent input) {
	   super.shouldYieldFocus(input); 
       boolean valid = verify(input);
       String columnName = input.getName();
	   // get error label 
	   JLabel errorLabel = errorLabelMap.get(columnName);
	   if (!valid && mandatory) {
		   String error = Application.getResourceBundle().getString("errorInteger"); 
    	   errorLabel.setVisible(true);
    	   errorLabel.setText(error);
    	   
       } else {
    	   errorLabel.setVisible(false);
       }
       	    
	return true;
	}
}
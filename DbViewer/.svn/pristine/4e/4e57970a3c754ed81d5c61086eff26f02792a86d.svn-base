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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import application.Application;

import com.toedter.calendar.JDateChooser;
/**
 * Verifies if user input of type {@link JDateChooser} is in the 
 * correct format and not empty.
 * @author Jelena
 *
 */
public class DateTimeVerifier extends AbstractInputVerifier {
	/**
	 * String that presents a format for date input fields.
	 */
	public String format = "yyyy-MM-dd";
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
	 * @param columnName
	 * @param mandatory
	 * @param errorLabelMap
	 */
	public DateTimeVerifier(String columnName, Boolean mandatory, Map<String, JLabel> errorLabelMap) {
		this.columnName = columnName; 
		this.mandatory = mandatory; 
		this.errorLabelMap = errorLabelMap; 
	}
	
	/**
	 * Sets component that should be validated.
	 * @param componentToSet
	 */
	@Override
	public void setComponent(JComponent componentToSet) {
		super.setComponent(componentToSet);
		if (component instanceof JDateChooser) {
		
			JDateChooser choser = (JDateChooser) component;
			
			JTextComponent txt = (JTextComponent)choser.getDateEditor().getUiComponent();
			txt.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					boolean valid = verify(component);
					String columnName = component.getName();
	           
	         	   // get error label 
	         	   JLabel errorLabel = errorLabelMap.get(columnName);
	         	   if (!valid && mandatory) {
	         		   String error = Application.getResourceBundle().getString("errorDateTime"); 
	             	   errorLabel.setVisible(true);
	             	   errorLabel.setText(error);
	             	   
	                } else {
	             	   errorLabel.setVisible(false);
	                }
	         	     
	         	  triggerCallbacks(valid);
		        	
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
				
			//Detects a change of date. This is necessary since key events do not
			//mark the end of input.
			choser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			    	public void propertyChange(java.beans.PropertyChangeEvent evt) {
				        // If the 'date' property was changed...
				        if ("date".equals(evt.getPropertyName())) {
				        	
				        	
				            JDateChooser aDateChooser = (JDateChooser) evt.getSource();
				            boolean isDateSelectedByUser = true;
				            // Get the otherwise unaccessible JDateChooser's 'dateSelected' field.
					            try {
					            	
					            	if(choser.getDate() == null) {
					            		isDateSelectedByUser = false; 
					            	}
					            	
					            	String s = ((JTextComponent)choser.getDateEditor().getUiComponent()).getText();
					            	if (s.equals("")) {
					            		isDateSelectedByUser = false; 
					            	}
					                
					                String columnName = component.getName();
					               // get error label 
					         	   JLabel errorLabel = errorLabelMap.get(columnName);
					         	   if (!isDateSelectedByUser && mandatory) {
					         		   String error = Application.getResourceBundle().getString("errorDateTime"); 
					             	   errorLabel.setVisible(true);
					             	   errorLabel.setText(error);
					             	   
					                } else {
					             	   errorLabel.setVisible(false);
					                }
					         	     
					         	  triggerCallbacks(isDateSelectedByUser);
						        	
					                
					                
					            } catch (Exception ignoreOrNot) {
					            	
					            }
				       }
		
			    	}
			});
		}
	}

	@Override
	public boolean verify(JComponent input) {
		
		String text = null;

        if (input instanceof JTextComponent) {
          text = ((JTextComponent) input).getText();
          
        } else if (input instanceof JComboBox) {
        	return ((JComboBox) input).getSelectedIndex() != -1;
        } else if (input instanceof JDateChooser) {
        	JDateChooser choser = (JDateChooser) input;
        	if(!mandatory) {
        		return true; 
        	}
        	
        	if(choser.getDate() == null) {
        		return false; 
        	}
        	
        	String s = ((JTextComponent)choser.getDateEditor().getUiComponent()).getText();
        	if (s.equals("")) {
        		return false; 
        	}
        	
        	return true;
        }
        
        if (text == null) {
        	return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(text);
            if (!sdf.format(date).equals(text)) {
                //throw new ParseException(text + " is not a valid format for " + format, 0);
            	return false;
            }
        } catch (ParseException ex) {
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
		   String error = Application.getResourceBundle().getString("errorDateTime"); 
    	   errorLabel.setVisible(true);
    	   errorLabel.setText(error);
    	   
       } else {
    	   errorLabel.setVisible(false);
       }
	     
	return true;
	}
	
}
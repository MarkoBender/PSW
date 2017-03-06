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

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * Presents base class for all verifier classes. 
 * Contains build-in behavior for modifying input fields when user input is invalid.
 * Subclasses should override {@link #verify(JComponent)} method in order to apply validation specific logic.
 * This base class acts as an observer, enables clients to register callbacks
 * in order to receive a notification when validation has changed.
 * @author Jelena
 *
 */
public abstract class AbstractInputVerifier extends InputVerifier {
/**
 * List of registered listeners that need to be notified when validation 
 * status changes.
 */
	protected List<VerifierCallback> callbacks = new ArrayList<>();
	/**
	 * Instance of component {@link JComponent}.
	 */
	protected JComponent component; 
	/**
	 * Use to restore original component style after a 
	 * validation error was corrected.
	 * 
	 */
	private Color originalColor;
	/**
	 * Tells if a input field is mandatory or not.
	 */
	protected boolean mandatory; 
	/**
	 * Maximum number of characters that can be found inside a field {@link JTextComponent}.
	 */
	protected int textLengthLimit;
	
	/**
	 * Adds reference to the client that should be notified when validation has changed.
	 * @param callback
	 */
	public void addVerifierCallback(VerifierCallback callback) {
		this.callbacks.add(callback);
	}
	
	/**
	 * Removes reference to the client.
	 * @param callback
	 */
	public void removeVerifierCallback(VerifierCallback callback) {
		this.callbacks.remove(callback);
	}
	
	/**
	 * Notifies all registered clients that validation has changed.
	 * @param isValid
	 */
	protected void triggerCallbacks(boolean isValid) {
		for(VerifierCallback callback : callbacks) {
			callback.validationChanged(isValid);
		}
	}
	/**
	 * Applies appropriate style to input field if user input is invalid.
	 */
	 @Override
     public boolean shouldYieldFocus(JComponent input) {
		 
		 boolean valid = verify(input);
        if(this.component instanceof JTextComponent) {
        	
        	JTextComponent text = (JTextComponent) this.component;
        	((JTextComponent) input).setText(text.getText().trim());
        
        	
        }
        triggerCallbacks(valid);
        
        if(originalColor == null) {
        	originalColor = input.getBackground();
        }
        
        if (!valid && mandatory) {
        	input.setBackground(Color.RED);
           /*
        	if(input instanceof JTextField || input instanceof JTextArea) {
        		JOptionPane.showMessageDialog(null, "Input data has to bi alpha numeric string.");
        	} else {
          	   JOptionPane.showMessageDialog(null, "Input data has to be selected");
            }	*/
        } else {
        	input.setBackground(originalColor);
        }
        
        return true;
	}

	 /**
	  * Returns component under validation.
	  * @return
	  */
	public JComponent getComponent() {
		
		return component;
	}
/**
 * Sets the component, this is required in order to listen for key events.
 * @param component - JComponent for validation
 */
	public void setComponent(JComponent component) {
		this.component = component;
		AbstractInputVerifier self = this;
	
		this.component.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				boolean isValid = verify(self.component);
				shouldYieldFocus(self.component); 
				if(self.component instanceof JTextComponent) {
					JTextComponent jText = (JTextComponent) self.component;
					
					if(textLengthLimit != 0 && jText.getText().length() > textLengthLimit) {
						String content = jText.getText();
						// calculate length based on total count
						// end index is max length -1
						int endIndex = textLengthLimit;
						content = content.substring(0, endIndex);
						jText.setText(content);
					}
				}
				// javljamo dijalogu da  proveri celu formu
				// tako sto ce svima pozvati isComponentValid
				// a isComponentValid poziva verify
	        	triggerCallbacks(isValid);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
	}
/**
 * Return true if component is valid, otherwise false. 
 * @return {@link JComponent}
 */
	public boolean isComponentValid() {
		if(component == null) {
			return false;
		}
		
		return this.verify(component);
		
	}
	 
}
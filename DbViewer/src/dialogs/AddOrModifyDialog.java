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
 
 package dialogs;

import gui.MainFrame;
import gui.panel.SchemaTab;
import gui.validation.AbstractInputVerifier;
import gui.validation.DateTimeVerifier;
import gui.validation.ExactTextLengthVerifier;
import gui.validation.IntegerVerifier;
import gui.validation.MaxTextLengthVerifier;
import gui.validation.NumericVerifier;
import gui.validation.VerifierCallback;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Column;
import model.ForeignKeyConstraint;
import model.Table;
import security.MD5Hashing;
import sql.ComboBoxItem;
import sql.RecordsManagement;
import sql.TypesOfActions;

import com.toedter.calendar.JDateChooser;

/**
 * A dialog that extends {@link JDialog} , used for adding a new record or modifying an existing one.
 * Enables add or modify operations underlying SQL data.
 * @author Jelena
 * @author Marko
 *
 */
public class AddOrModifyDialog extends JDialog {
	
	/**
	 * {@link SchemaTab} to which this dialog is created.
	 */
	private SchemaTab tab=null;
	//par kljuc i vrednost
	/**
	 * Map which contains {@link Column} code as key and {@link JComponent} as its value. Every {@link Column} has it's own {@link JComponent}
	 */
	private HashMap<String,JComponent> inputs = new HashMap<String,JComponent>();
	//par kljuc vrednost(tj samo kljucevi)
	/**
	 * Map which contains {@link Column} code as key and {@link Object} as its value. Represents conditions under which are some {@link JComponent} fields filled.
	 */
	private HashMap<String, Object> hm=null;
	
	/**
	 * Gets the Dialogs hm {@link HashMap}.
	 * 
	 * @return {@link HashMap}
	 */
	public HashMap<String, Object> getHm() {
		return hm;
	}

	/**
	 * Sets the Dialogs hm {@link HashMap}.
	 * 
	 * @param hm
	 */
	public void setHm(HashMap<String, Object> hm) {
		this.hm = hm;
	}
	/**
	 * Values returned from {@link RecordsManagement}. All values except images. Used for setting specific value to non image field.
	 */
	private ArrayList<String> recordValues=null;
	/**
	 * Values returned from {@link RecordsManagement}. Only image values. Used for setting specific value to image field.
	 */
	private ArrayList<byte[]> recordImages=null;
	private JButton addButton;
	/**
	 * Maps Column names to error labels that contain validation errors.
	 */
	private Map<String, JLabel> errorLabelMap;
	
	/**
	 * Constructor for {@link AddOrModifyDialog}. Depending on table columns, he dinamically creates different types of fields: {@link JTextField}, {@link JComboBox}, {@link JLabel}, {@link JFileChooser} or {@link JDateChooser}
	 * If {@link TypesOfActions} is ADD, he doesn't set values to those fields
	 * If {@link TypesOfActions} is MODIFY, he set values to those fields depending on map of Primary Keys
	 * @param tab in which tab we call this dialog (from tab we get table, selected row..)
	 * @param hm map of Primary Keys for getting and setting values to different field types
	 * @param toa type of possible action (ADD or MODIFY)
	 */
	public AddOrModifyDialog(SchemaTab tab, HashMap<String, Object> hm, TypesOfActions toa) {
		//podesavanje ownera tako da ne izadje iz fokusa
		super(MainFrame.getInstance());
		this.errorLabelMap = new HashMap<String, JLabel>(); 
		this.tab=tab;
		this.hm=hm;
		Table table = tab.getTable();
		setTitle(table.getName());
		this.setModal(true);
		
		
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);

		scrollPane.setHorizontalScrollBarPolicy(
				   JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				scrollPane.setVerticalScrollBarPolicy(
				   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
		
		int numberOfColumns = table.getColumns().size() + 1; // Za sve input fieldove + 1 za button
		int padding = 40;
		setSize(new Dimension(700,numberOfColumns*55 + 2*padding));
		
		GridLayout layout = new GridLayout(numberOfColumns,2);
		layout.setHgap(10);
		layout.setVgap(10);
		panel.setLayout(layout);
		panel.setBorder(new EmptyBorder(padding,padding,padding,padding));
		
		//ukoliko imamo hm-tj to nam govori dal je modify ili add
		if(hm!=null){
			RecordsManagement rm=new RecordsManagement(table,TypesOfActions.SEARCH_SPECIFIC_RECORD,null,hm);
			recordValues=rm.getRecordValues();
			recordImages=rm.getRecordImages();
		}

		for(int i=0; i<table.getColumns().size();i++){
			Column column=table.getColumns().get(i);
			String star="";
			if(column.getConstraint().getPrimaryKey() || column.getConstraint().getMandatory())
				star="*";
			JLabel label = new JLabel(column.getName()+star);
			
			JLabel errorLabel = new JLabel("DEFAULT ERROR TEXT"); 
			errorLabel.setForeground(Color.RED);
			errorLabelMap.put(column.getName(), errorLabel); 
			errorLabel.setVisible(false);
			JComponent input = null;
			
			int columnType = column.getSqlType();
			
			 // Construct new verifier based on new column type.		 
			AbstractInputVerifier verifier = getInputVerifier(column); 
			
			switch(columnType){
				case Types.VARCHAR:
				case Types.CHAR:
				case Types.NUMERIC:
				case Types.INTEGER:
					if(column.getConstraint().getForeignKey()){
						if(column.getConstraint().getPrimaryKey() && table.getForeignKeys().contains(column.getCode()) && table.getPrimaryKeys().size()==1){
							input=new JTextField();
							if(hm!=null){
								((JTextField)input).setText(recordValues.get(i));
								if(column.getConstraint().getPrimaryKey()){
									((JTextField)input).setEditable(false);
								}
							}
						}
								
						else
							input=getForeignKeyCodes(column,i);
					}
					else{
						input=new JTextField();
						
						if(hm!=null){
							((JTextField)input).setText(recordValues.get(i));
							if(column.getConstraint().getPrimaryKey()){
								((JTextField)input).setEditable(false);
							}
						}
					}
					break;
				case Types.TIMESTAMP:
					JDateChooser dc=new JDateChooser();
					dc.setDateFormatString("yyyy-MM-dd");
					if(column.getConstraint().getMandatory()) {
						dc.setDate(new Date());
					}
					
					input = dc;
					if(hm!=null){
						if(!recordValues.get(i).equals("")){
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
							try {
								dc.setDate(dateFormat.parse(recordValues.get(i)));
			
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
						}
					}
					break;
				case Types.BLOB:
					input = new JPanel(new GridLayout(1,2));
					JLabel labela=new JLabel();
					JButton button = new JButton("Choose");
					input.add(labela);
					input.add(button);
					((JPanel)input).addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if(SwingUtilities.isRightMouseButton(e)){
								for (Component c : ((JComponent)e.getSource()).getComponents()) {
								    if (c instanceof JLabel) { 
								       ((JLabel)c).setText("");
								       ((JLabel)c).setIcon(null);
								       ((JLabel)c).revalidate();
								    }
								}
							}
						}
					});
					((AbstractButton) button).addActionListener(new ActionListener(){
	
						@Override
						public void actionPerformed(ActionEvent arg0) {
							JFileChooser chooser = new JFileChooser();
							FileFilter imageFilter = new FileNameExtensionFilter(
								    "Image files", ImageIO.getReaderFileSuffixes());
							chooser.addChoosableFileFilter(imageFilter);
							chooser.setAcceptAllFileFilterUsed(false);
							chooser.setMultiSelectionEnabled(false);
							int returnVal = chooser.showOpenDialog(chooser);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								String path=chooser.getSelectedFile().getAbsolutePath();
							    labela.setIcon(ResizeImage(path,labela));
							    labela.setText(path);
							}
						}	
					});
					if(hm!=null){
						if(!recordValues.get(i).equals("")){						
							byte[] imageBytes=recordImages.get(0);
							ImageIcon image = new ImageIcon(imageBytes);
		                    Image im = image.getImage();
		                    //Image myImg = im.getScaledInstance(labela.getWidth(), labela.getHeight(),Image.SCALE_SMOOTH);
		                    Image myImg = im.getScaledInstance(76, 42,Image.SCALE_SMOOTH);
		                    ImageIcon newImage = new ImageIcon(myImg);
		                    labela.setIcon(newImage);
		                    labela.setText(recordValues.get(i));
		                    recordImages.remove(0);
						}
					}
					break;
			}
			panel.add(label);
			input.setName(column.getName());
			input.setInputVerifier(verifier);
			
			if(verifier != null) {
				verifier.setComponent(input);
			}
			
			
			panel.add(input);
			panel.add(errorLabel); 
			if(hm!=null){
				if(!column.getConstraint().getPrimaryKey()){
					inputs.put(column.getCode(), input);
				}
			}
			else
				inputs.put(column.getCode(), input);
		}
		addButton=new JButton();
		addButton.setText("Accept");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!allFieldsValid()) {
					addButton.setEnabled(false);
					return;
				}
				
				RecordsManagement rm=null;
				
				if(inputs.containsKey("REG_LOZINKA")) {
					JTextField text = (JTextField) inputs.get("REG_LOZINKA"); 
					String password = text.getText(); 
					String md5Password = MD5Hashing.getMD5(password); 
					text.setText(md5Password);
					inputs.put("REG_LOZINKA", text); 
				}		
				
				if(hm==null){					
					rm=new RecordsManagement(table,TypesOfActions.ADD_RECORD,inputs,null);
				}
				else{
					rm=new RecordsManagement(table,TypesOfActions.MODIFY_RECORD,inputs,hm);
				}
				((SchemaTab) MainFrame.getInstance().getMainContentPane().getSelectedComponent()).refreshTable();
				if(rm.uspesno)
					dispose();
			}

	

		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		panel.add(addButton);
		panel.add(cancelButton);

//		scrollPane.add(panel); 
//		panel.revalidate();
//		panel.repaint();
		//add(panel);
		add(scrollPane);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
	}

	/**
	 * Constructs {@link InputVerifier} and sets verifier for specified input based on column type.
	 * @param column
	 * @return
	 */
	private AbstractInputVerifier getInputVerifier(Column column) {
		AbstractInputVerifier inputVerifier = null;
		
		
		switch(column.getType().getType()) {
		case VARCHAR:
			inputVerifier = new MaxTextLengthVerifier(column.getType(), column.getName(), column.getConstraint().getMandatory(), errorLabelMap);
			break;
		case BLOB:
			break;
		case CHAR:
			inputVerifier = new ExactTextLengthVerifier(column.getType(), column.getName(), column.getConstraint().getMandatory(), errorLabelMap);
			break;
		case INTEGER:
			inputVerifier = new IntegerVerifier(column.getType(), column.getName(), column.getConstraint().getMandatory(), errorLabelMap); 
			break;
		case NUMERIC:
			inputVerifier = new NumericVerifier(column.getType(), column.getName(), column.getConstraint().getMandatory(), errorLabelMap);
			break;
		case TIMESTAMP:
			inputVerifier = new DateTimeVerifier(column.getName(), column.getConstraint().getMandatory(), errorLabelMap);
			break;
		default:
			break;
	}
	
		
		if(inputVerifier != null) {
			inputVerifier.addVerifierCallback(verificationCallback);
			verifiers.add(inputVerifier);
		}
		
		return inputVerifier;
	}
	/**
	 * List of all verifiers present on the dialog.
	 * On each validation change, accept button status is set.
	 * If all fields are valid, accept button is enabled, disable otherwise.
	 */
	private List<AbstractInputVerifier> verifiers = new ArrayList<>();
	
	/**
	 * Instance of anonymous private class that will implement the callback interface.
	 */
	private VerifierCallback verificationCallback = new VerifierCallback() {

		@Override
		public void validationChanged(boolean isValid) {
			addButton.setEnabled(allFieldsValid());
		}
		
	};
	/**
	 * Checks if any of the fields are invalid, if so, it returns false.
	 * @return
	 */
	private boolean allFieldsValid() {
		// check all components
		// if any of them is invalid, return false
		for(AbstractInputVerifier verifier : verifiers) {
			if(!verifier.isComponentValid()) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Method for setting up and returning {@link JComboBox} model to the array of {@link ComboBoxItem} depending on Foreign Key
	 * @param column to which column model are we doing method
	 * @param k - row position of {@link JComboBox} in {@link GridView} 
	 * @return
	 */
	public JComboBox getForeignKeyCodes(Column column, int k){
		//napravim listu vrednosti koje moze combo box da prima i stavim prvi da je prazan
		ArrayList<ComboBoxItem> cbi=new ArrayList<ComboBoxItem>();
		cbi.add(new ComboBoxItem("",""));
		//prolazim kroz sva ogranicenja date tabele
		for(ForeignKeyConstraint fkc : tab.getTable().getReferences()){
			//prolazim kroz sve kolone koje referenciraju vrednosti iz drugih tabela
			HashMap<String,Object>template=new HashMap<String,Object>();
			for(int i=0; i<fkc.getRefeeringColumnCodes().size(); i++){
				//uzimam kod date kolone i poredim ga kolonom koju hocu da napunim
				String refeeringColumnCode=fkc.getRefeeringColumnCodes().get(i);
				if(column.getCode().equals(refeeringColumnCode)){
					//ukoliko jeste isto, uzimam na istom mestu u nizu kod kolone koja je referencirana
					//kao i naziv, zatim pravim upit izvrsavam ga i punim listu kodovima
					String referencedColumnCode=fkc.getReferencedColumnCodes().get(i);
					String referencedTableCode=fkc.getReferencedTableCode();
					template.put(referencedColumnCode, referencedTableCode);
					RecordsManagement rm=new RecordsManagement(tab.getTable(),TypesOfActions.GET_VALUES_BY_FK,null,template);
					cbi.addAll(rm.getCbi());
					break;
				}
			}
		}
		int selectedIndex=-1;
		if(column.getConstraint().getMandatory()){
			if(!cbi.isEmpty())
				cbi.remove(0);
		}
		
		for(int g=0;g<cbi.size();g++){
			if(hm!=null){
				if(((ComboBoxItem)cbi.get(g)).getValue().equals(recordValues.get(k))){
					selectedIndex=g;
					break;
				}
			}
		}
		JComboBox cb=new JComboBox(cbi.toArray());
		if(hm!=null){
			cb.setSelectedIndex(selectedIndex);
			if(column.getConstraint().getPrimaryKey()){
				cb.setEditable(false);
				cb.setEnabled(false);
			}
		}
		return cb;
	}
	
	/**
	 * Gets the Dialogs inputs {@link HashMap}.
	 * 
	 * @return {@link HashMap}
	 */
	public HashMap<String,JComponent> getInputs(){
		return inputs;
	}
	
	/**
	 * Method for resizing image size depending on size of the label
	 * @param imgPath path to the image which we want to resize
	 * @param label component where we want to put image
	 * @return compressed image
	 */
	public ImageIcon ResizeImage(String imgPath,JLabel label){
        ImageIcon MyImage = new ImageIcon(imgPath);
        Image img = MyImage.getImage();
        Image newImage = img.getScaledInstance(label.getWidth(), label.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
	
	/**
	 * Method for setting (@link JComboBox) selected item to the parents value depending on your Foreign Key and parent's Primary Key
	 * @param parentPK
	 */
	public void SetujDetetuRoditelja(HashMap<String,Object> parentPK){
		for(String m: inputs.keySet()){
			if(inputs.get(m) instanceof JComboBox){
				JComboBox jcb=(JComboBox)inputs.get(m);
				for(String key:parentPK.keySet()){
					int brojac=-1;
					for(int k=0; k<jcb.getItemCount(); k++){
						if(((ComboBoxItem)jcb.getItemAt(k)).getValue().equals(parentPK.get(key))){
							brojac=k;
							break;
						}
					}
					if(brojac!=-1){
						jcb.setSelectedIndex(brojac);
					}
				}	
			}
		}
	}
}
package com.loquatic.xls.gui.wizards.pages;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class SelectJarFileWizardPage extends WizardPage {

	private Composite composite ;
		
	private Text selectFileNameTxt ;
	private Combo choosenFilesCbo ;
	
	
	public SelectJarFileWizardPage() {
		this( Messages.SelectJarFileWizardPage_Title ) ;
	}
	public SelectJarFileWizardPage(String pageName) {
		super(pageName);
		setTitle( Messages.SelectJarFileWizardPage_Title ) ;
		setDescription( Messages.SelectJarFileWizardPage_Description ) ;
	}

	@Override
	public void createControl(Composite parent) {
		
		setPageComplete( false ) ;
		composite = new Composite( parent, SWT.NULL ) ; 
		
		FormLayout layout = new FormLayout() ;
		
		composite.setLayout( layout ) ;
		
		FormData formData = new FormData() ;
		formData.top = new FormAttachment( 0, 10 ) ;
		formData.left = new FormAttachment( 0, 10 ) ;
		formData.width = 150 ;
		
		Label selectFileLbl = new Label( composite, SWT.NONE ) ;
		selectFileLbl.setText( Messages.SelectJarFileWizardPage_Label_Select_File ) ;
		selectFileLbl.setLayoutData( formData ) ;
		
		selectFileNameTxt = new Text( composite, SWT.BORDER ) ;
		
		formData = new FormData() ;
		formData.top = new FormAttachment( 0, 10 ) ;
		formData.left = new FormAttachment( selectFileLbl, 10 ) ;
		formData.width = 250 ;
		
		selectFileNameTxt.setLayoutData( formData ) ;
	
		Button chooseFileBtn = new Button( composite, SWT.PUSH ) ;
		chooseFileBtn.setText( Messages.SelectJarFileWizardPage_Button_Select_File_Label ) ;
		formData = new FormData() ;
		formData.top = new FormAttachment( 0, 10 ) ;
		formData.left = new FormAttachment( selectFileNameTxt, 10 ) ;
		chooseFileBtn.setLayoutData( formData ) ;
		chooseFileBtn.addSelectionListener( 
			new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					String[] extensionFilters = { "*.jar" };
					FileDialog fileDialog = new FileDialog( composite.getShell(), SWT.OPEN ) ;
					fileDialog.setFilterExtensions( extensionFilters ) ;
					String selectedJar = fileDialog.open() ;
					
					if( selectedJar != null  ) {
						selectFileNameTxt.setText( selectedJar ) ;
						File selectedJarFile = new File( selectedJar ) ;
						if( selectedJarFile.exists() && 
								selectedJarFile.canRead() ) {
							setPageComplete( true ) ;
						} else {
							setErrorMessage( Messages.SelectJarFileWizardPage_CannotReadFile_Message ) ;
						}
					} else {
						setErrorMessage( Messages.SelectJarFileWizardPage_InvalidFile_Message ) ;
					}
					
					
					
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// TODO Auto-generated method stub
					
				}
				
			} ) ;
		
		
		setControl( composite ) ;
	}

}

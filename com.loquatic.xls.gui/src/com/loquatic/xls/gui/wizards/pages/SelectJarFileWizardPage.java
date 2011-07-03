/*
 * Copyright (c) 2011, Loquatic Software, LLC
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL LOQUATIC SOFTWARE LLC BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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

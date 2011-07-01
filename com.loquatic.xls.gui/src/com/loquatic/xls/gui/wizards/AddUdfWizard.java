package com.loquatic.xls.gui.wizards;

import org.eclipse.jface.wizard.Wizard;

import com.loquatic.xls.gui.wizards.pages.SelectJarFileWizardPage;

public class AddUdfWizard extends Wizard {
	
	
	private SelectJarFileWizardPage selectJarFile  = new SelectJarFileWizardPage() ;
	
	public AddUdfWizard() {
		super();
		
	}
	
	public void addPages() {
		addPage( selectJarFile  ) ;
	}

	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		return true;
	}

}

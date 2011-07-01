package com.loquatic.xls.gui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import com.loquatic.xls.gui.wizards.AddUdfWizard;

public class OpenAddUdfWizard extends AbstractHandler implements IHandler {

	public final static String ID = "com.loquatic.xls.gui.command.openAddUdfWizard" ;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AddUdfWizard addUdfWizard = new AddUdfWizard() ;
		
		WizardDialog dialog = new WizardDialog(HandlerUtil
				.getActiveShell(event), addUdfWizard ) ;
		dialog.open();

		
		return null;
	}

}

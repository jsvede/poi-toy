package com.loquatic.xls.gui.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.loquatic.xls.gui.views.SheetNameView;
import com.loquatic.xls.util.WorkbookUtil;
import com.loquatic.xls.util.WorkbookUtilFactory;

public class OpenFileHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
		String[] extensionFilters = { "*.xls", "*.xlsx" };
		fileDialog.setFilterExtensions(extensionFilters);
		String selectedFileName = fileDialog.open();

		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(SheetNameView.ID);
			IWorkbenchPart activePart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().getActivePart();
			
			if( activePart instanceof SheetNameView ) {
				WorkbookUtil util = WorkbookUtilFactory.getInstance( selectedFileName ) ; 
				((SheetNameView)activePart).setWorkbookUtil( util ) ;
			}
		} catch (PartInitException pie) {
			pie.printStackTrace();
		}

		return null;
	}

}

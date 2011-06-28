package com.loquatic.xls.gui.commands;

import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import com.loquatic.xls.gui.editors.SheetEditor;
import com.loquatic.xls.gui.editors.SheetEditorInput;
import com.loquatic.xls.gui.views.SheetNameView;

public class OpenSheetEditorHandler extends AbstractHandler {

	public final static String ID = "com.loquatic.xls.gui.command.openSheetEditor" ;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbenchPage page = window.getActivePage();
		IViewPart viewPart = page.findView(SheetNameView.ID);
		if( viewPart instanceof SheetNameView ) {
			SheetNameView view = (SheetNameView) viewPart ;
			// Get the selection
			ISelection selection = view.getSite().getSelectionProvider()
			.getSelection();
			if (selection != null && selection instanceof IStructuredSelection) {
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				// If we had a selection lets open the editor
			if (obj != null) {
				if( obj instanceof String ) {
//					Entry person = (Entry) obj;
					SheetEditorInput sheetEditorInput = new SheetEditorInput() ;
					sheetEditorInput.setUtil( view.getWorkbookUtil() ) ;
					String sheetName = (String)obj ;
					Sheet s = view.getWorkbookUtil().getSheetFromName( sheetName ) ;
					sheetEditorInput.setSheet( s ) ;
					try {
						page.openEditor(sheetEditorInput, SheetEditor.ID);

					} catch (PartInitException e) {
						throw new RuntimeException(e);
					}
				}
			}
			}
		}
		return null;
	}

}

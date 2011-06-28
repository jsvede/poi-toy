package com.loquatic.xls.gui.editors;

import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.loquatic.xls.util.WorkbookUtil;

public class SheetEditorInput implements IEditorInput {
	
	private WorkbookUtil workbookUtil ;
	
	private Sheet selectedSheet ;
	
//	private String name = "Sheet Editor" ;

	@Override
	public Object getAdapter(Class adapter) {
		System.out.println( "SheetEditorInput.getAdapter()" ) ;
		return null;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
//		System.out.println( "SheetEditorInput.getImageDescriptor() called" ) ;
		return null;
	}

	@Override
	public String getName() {
//		System.out.println( "SheetEditorInput.getName() called" ) ;
		return selectedSheet.getSheetName() ;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
//		System.out.println( "SheetEditorInput.getToolTipText() called" ) ;
		return selectedSheet.getSheetName() ;
	}

	public void setSheet( Sheet s ) {
		selectedSheet = s;
	}
	
	public Sheet getSelectedSheet() {
		return selectedSheet ;
	}
	
	public WorkbookUtil getUtil() {
		return workbookUtil ;
	}
	
	public void setUtil( WorkbookUtil ut ) {
		workbookUtil = ut ;
	}
}

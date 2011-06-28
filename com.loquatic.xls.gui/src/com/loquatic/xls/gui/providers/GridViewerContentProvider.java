package com.loquatic.xls.gui.providers;

import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.loquatic.xls.util.WorkbookUtil;

public class GridViewerContentProvider implements IStructuredContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if( inputElement instanceof Sheet ) {
			Sheet s = (Sheet)inputElement ;
			return WorkbookUtil.getRows(s) ; 
		}
		Object[] array = new Object[] { "you suck" } ;
		return array ;
	}

}

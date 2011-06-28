package com.loquatic.xls.gui.editors;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.nebula.jface.gridviewer.GridTableViewer;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import com.loquatic.org.eclipse.nebula.grid.widget.layout.GridWidgetLayout;
import com.loquatic.xls.gui.providers.GridViewerContentProvider;
import com.loquatic.xls.gui.providers.GridViewerLabelProvider;
import com.loquatic.xls.util.WorkbookUtil;


public class SheetEditor extends EditorPart {
	
	public final static String ID = "com.loquatic.xls.gui.editors.sheetEditor" ;

	private GridTableViewer viewer;
	
	private WorkbookUtil wbUtil ;
	
	private SheetEditorInput myInput ;
	
	public SheetEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		setSite(site);
		setInput(input);
		if( input instanceof SheetEditorInput ) {
			myInput = (SheetEditorInput)input ;
		}
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer = new GridTableViewer( parent, 
                SWT.BORDER | 
                SWT.FULL_SELECTION | 
                SWT.H_SCROLL| 
                SWT.V_SCROLL ) ;
		
		

        GridWidgetLayout gridWidgetLayout = new GridWidgetLayout() ;
        viewer.getGrid().setLayout( gridWidgetLayout ) ;

		String[] data = WorkbookUtil.getColumnHeaders( myInput.getSelectedSheet() ) ;
		
        if( data != null && data.length > 0 ) {
        	
    		for( int x=0; x<data.length ; x++ ) {
    			gridWidgetLayout.addColumnData(new ColumnWeightData(10,50, false)); 
                GridColumn aGridColumn = new GridColumn( viewer.getGrid(), SWT.NONE ) ;
                aGridColumn.setText( data[x] ) ;  
    		}        	
        }

        
        viewer.getGrid().setHeaderVisible( true ) ;
        viewer.getGrid().setLinesVisible( true ) ;   

        viewer.setContentProvider( new GridViewerContentProvider() ) ;
		viewer.setLabelProvider( new GridViewerLabelProvider( myInput.getUtil() ) ) ;
		
		viewer.getGrid().setSelectionEnabled( true ) ;
		viewer.getGrid().setCellSelectionEnabled( true ) ;
        
		if( myInput != null ) {
//			System.out.println( "myInput is not null" ) ;
			if( myInput.getSelectedSheet() == null ) System.out.println( "AHA!!!" ) ;
			viewer.setInput( myInput.getSelectedSheet() ) ;
		}
		
		viewer.refresh() ;
		
		getSite().setSelectionProvider(viewer ) ;

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}

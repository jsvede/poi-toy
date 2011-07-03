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

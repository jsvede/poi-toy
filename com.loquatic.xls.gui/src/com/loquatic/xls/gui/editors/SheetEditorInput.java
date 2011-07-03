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

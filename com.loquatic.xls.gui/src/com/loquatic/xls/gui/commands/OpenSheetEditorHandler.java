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

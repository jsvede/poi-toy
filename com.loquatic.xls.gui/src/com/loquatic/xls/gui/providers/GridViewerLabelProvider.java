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
package com.loquatic.xls.gui.providers;

import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.loquatic.xls.util.WorkbookUtil;

public class GridViewerLabelProvider extends LabelProvider implements
		ITableLabelProvider {

	
	private WorkbookUtil util ;
	
	public GridViewerLabelProvider( WorkbookUtil ut ) {
		super() ;
		util = ut ;
	}
	
	public GridViewerLabelProvider() {
		super() ;
	}
	
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
	//		System.out.println( "GridViewerLabelProvider.getColumnImage() called" ) ;
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		//System.out.println( "GridViewerLabelProvider.getColumnText() received " + element.getClass().getName() ) ; 
		if( element instanceof Row ) {
//			System.out.println( "have a Row instance" ) ;
			Row row = (Row)element ;
			Cell cell = row.getCell(columnIndex ) ;
			if( cell != null ) {
				//System.out.println( "getColumnText() !=  null" ) ;
				return getCellValue( cell ) ;
			} else {
//				System.out.println( "getColumnText() ==  null" ) ;
			}
		}
 		return "";
	}
	
	private String getCellValue( Cell cell ) {
//		//System.out.println( "GridViewerLabelProvider.getCellValue() called" ) ;
		if( cell != null ) {
			switch (cell.getCellType() ) {
			case Cell.CELL_TYPE_BLANK:
				return "" ;
			case Cell.CELL_TYPE_ERROR:
				return "Error: " + cell.getErrorCellValue() ;
			case Cell.CELL_TYPE_FORMULA:
				// need to evaluate the cell and return the results ;
				double result = -1.0 ;
				try {
					result = util.evaluate( cell ) ;
				} catch( NotImplementedException nie ) {
					System.out.println(" failed to evaluate due to: " + 
							                            nie.getMessage() ) ;
				}
				return Double.toString( result ) ;
			case Cell.CELL_TYPE_NUMERIC:
				return Double.toString(cell.getNumericCellValue() ) ;
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue() ;
			default:
				return "-" ;
			}
		}
		return "x" ;
	}

}

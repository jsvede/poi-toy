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

package com.loquatic.xls.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkbookUtil {
	
	private static final String[] LETTERS = { "A","B", "C", "D", "E", "F", "G", "H",
		                                      "I", "J", "K", "L", "M", "N", "O", "P",
		                                      "Q", "R", "S", "T", "U", "V", "W", "X",
		                                      "Y", "Z" } ;
	

	private String fileName;

	private Workbook workbook;

	public WorkbookUtil(String fName) {
		fileName = fName;
		File inputFileName = new File(fileName);

		try {
			FileInputStream fis = new FileInputStream(inputFileName);
			workbook = WorkbookFactory.create(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WorkbookUtil(Workbook w) {
		workbook = w;
	}

	public static Row[] getRows(Sheet s) {

		LinkedList<Row> rowsList = new LinkedList<Row>();
		for (int x = 0; x < s.getLastRowNum(); x++) {
			if (s.getRow(x) != null) {
				rowsList.add(s.getRow(x));
			}
		}
		return rowsList.toArray(new Row[rowsList.size()]);
	}

	public static int getMaxRowSize(Sheet s) {
		int maxRowSize = 0;

		for (int x = 0; x < s.getLastRowNum(); x++) {
			if (s.getRow(x) != null) {
				int maxRowSzNow = s.getRow(x).getLastCellNum();
				if (maxRowSzNow > maxRowSize) {
					maxRowSize = maxRowSzNow;
				}
			}
		}
		return maxRowSize;
	}
	
	public static String[] getColumnHeaders(Sheet s ) {
		String[] headers = getColumnHeaders( getMaxRowSize( s ) ) ;
		return headers ;
		
	}

	public static String[] getColumnHeaders( int size ) {
		String[] headers = new String[ size ] ;
		
 		if( size <= LETTERS.length ) {
 			 for( int c = 0; c<headers.length; c++ ) {
 				 headers[c] = LETTERS[c] ;
 			 }
 		} else {
 			int headerIterations = size / LETTERS.length ;
 			// if we're here this should always be true
 			if( headerIterations > 1 ) {
 				for( int a=0; a<headerIterations; a++ ) {
 					if( a == 0 ) {
 						for( int b=0; b<LETTERS.length; b++ ) {
 							headers[b] = LETTERS[b] ;
 						}
 					} else if( a > 0 ) {
 						for( int b=0; b<LETTERS.length; b++ ) {
 							headers[(a*LETTERS.length)+b] = LETTERS[a-1] + LETTERS[b] ;
 						}
 						
 					}
 				}
 				
 			}
 		}
 		
		return headers ;
		
	}
	
	public double evaluate(Cell cell ) {
		FormulaEvaluator fe = null ;
		double result = 0 ;
		
		if( fileName != null ) {
			if( fileName.endsWith( "xls") ) {
				fe = new HSSFFormulaEvaluator( (HSSFWorkbook) workbook ) ;
			} else if( fileName.endsWith( "xlsx") ) {
				fe = new XSSFFormulaEvaluator( (XSSFWorkbook)workbook ) ;
			}
			
		}
		
		if( fe != null ) {
			result = fe.evaluateFormulaCell( cell ) ;
		}
		
		return result ;
	}

	public Map<Integer, String> getSheetsForWorkbook() {

		HashMap<Integer, String> sheetData = new HashMap<Integer, String>();

		int sheetCount = workbook.getNumberOfSheets();
		for (int x = 0; x < sheetCount; x++) {
			Integer idx = new Integer(x);
			Sheet aSheet = workbook.getSheetAt(x);
			sheetData.put(idx, aSheet.getSheetName());
		}

		return sheetData;
	}

	public String[] getSheetNamesAsArray() {
		int sheetCount = workbook.getNumberOfSheets();

		String[] sheetNames = new String[sheetCount];
		for (int x = 0; x < sheetCount; x++) {
			Integer idx = new Integer(x);
			Sheet aSheet = workbook.getSheetAt(x);
			sheetNames[x] = aSheet.getSheetName();
		}

		return sheetNames;

	}

	public String getValueOfCell(String cellStr) {
		// ( cellStr ) ;
		CellReference cellRef = new CellReference(cellStr);
		String sheetName = cellRef.getSheetName();
		Sheet sheet = workbook.getSheet(sheetName);
		int colIdx = cellRef.getCol();
		int rowIdx = cellRef.getRow();

		Row row = sheet.getRow(rowIdx);
		Cell cell = row.getCell(colIdx);

		String cellValue = "";

		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			cellValue = Double.toString(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.toString();
			break;
		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		}

		return cellValue;
	}

	public Cell getCell(String cellStr) {
		CellReference cellRef = getCellReference(cellStr);
		String sheetName = getSheetName(cellRef);

		Sheet sheet = getSheetFromName(sheetName);
		int colIdx = cellRef.getCol();
		int rowIdx = cellRef.getRow();

		Row row = sheet.getRow(rowIdx);
		Cell cell = row.getCell(colIdx);

		return cell;
	}

	public CellReference getCellReference(String cellStr) {
		return new CellReference(cellStr);
	}

	public String getSheetName(CellReference cellRef) {
		return cellRef.getSheetName();
	}

	public Sheet getSheetFromName(String sheetName) {
		return workbook.getSheet(sheetName);
	}

	private int recursionCount = 0;

	public CellInfo buildCellTree(String sheetName, String cellId,
			CellInfo parentCell) {
		CellInfo aCell = new CellInfo(sheetName, cellId, parentCell);

		try {
			Cell cell = getCell(sheetName + "!" + cellId);

			if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				String cellFormula = cell.getCellFormula();
				String[] childCells = parseCellFormula(cellFormula);
				// System.out.println( "\t\tkids: " + childCells ) ;
				for (String kidCellId : childCells) {
					aCell.addChild(buildCellTree(sheetName, kidCellId, aCell));
					// recursionCount++ ;
					// System.out.println( "recursionCount: " + recursionCount )
					// ;
				}
			}
		} catch (NullPointerException e) {
			// System.out.println( "PUKE: " + sheetName + "!" + cellId + ": " +
			// e.getMessage() ) ;
		} catch (IllegalArgumentException e) {
			// System.out.println( "PUKE: " + sheetName + "!" + cellId + ": " +
			// e.getMessage() ) ;
			// this happens when we get a reference that isn't cell, so ignore
			// it.
			// e.printStackTrace();
		}

		return aCell;
	}

	public String[] parseCellFormula(String cellFormula) {
		LinkedHashSet<String> token = new LinkedHashSet<String>();
		Pattern pattern = Pattern.compile("\\b[\\$?[A-Z]]*[0-9][0-9][0-9]?\\b");
		Matcher matcher = pattern.matcher(cellFormula);

		while (matcher.find()) {
			token.add(matcher.group());
		}

		return token.toArray(new String[token.size()]);
	}

	/**
	 * Returns an array of CellInfo objects based on the sheet name passed in.
	 * This method iterates over the rows and columns based on the
	 * 'LastRow/CellNum() method of the right object.
	 * 
	 * @param sheetName
	 * @return
	 */
	public CellInfo[] getAllFormulaCellsForSheet(String sheetName) {
		Sheet sheet = getSheetFromName(sheetName);

		LinkedList<CellInfo> cellInfos = new LinkedList<CellInfo>();
		for (int x = 0; x < sheet.getLastRowNum(); x++) {
			Row row = sheet.getRow(x);
			for (int y = 0; y < row.getLastCellNum(); y++) {
				Cell cell = row.getCell(y);
				if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					CellInfo cellInfo = buildCellTree(sheetName,
							(cell.getColumnIndex() + "" + cell.getRowIndex()),
							null);
					cellInfos.add(cellInfo);
				}
			}
		}

		return cellInfos.toArray(new CellInfo[cellInfos.size()]);
	}

}

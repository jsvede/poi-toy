package com.loquatic.xls.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.loquatic.xls.util.WorkbookUtilFactory.FileType;

public class WorkbookUtilTests {

	private static final String COMPONENTS_MODEL_XLS = "/resources/ComponentsModel_v2_May_26_2011_modified_LH2_Storage_Calculations.xls" ;
	
	WorkbookUtil util ;
	@Before
	public void setUp() {
//		util = WorkbookUtilFactory.getInstance( FileType.RESOURCE, COMPONENTS_MODEL_XLS ) ;
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testColumnHeaders() {
		
		String[] headers = util.getColumnHeaders( 104 ) ;
		int x = 0 ;
		for( String s : headers ) {
			System.out.println( "header[" + x + "] = " + s ) ;
			x++ ;
		}
		
	}
}

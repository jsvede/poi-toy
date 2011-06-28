package com.loquatic.xls.util;

import static org.junit.Assert.*;

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

		// test a couple of random entries
		assertEquals( headers[103], "CZ" ) ;
		assertEquals( headers[39], "AN" ) ;
		
		// now test that the first 52 entries start with
		for( int x=0; x<51; x++ ) { 
			assert( headers[x].startsWith("A") ) ; 
 		}
		
		// the next 26 should start with B
		for( int x=0; x<26; x++ ) {
			assert( headers[x+51].startsWith("B") ) ;
		}
	}
}

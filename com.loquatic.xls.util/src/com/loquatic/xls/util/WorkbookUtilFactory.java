package com.loquatic.xls.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Keeps a list of WorkbookUtil so that we don't end up with multiple 
 * objects pointing to the same file.
 * @author jsvede
 *
 */
public class WorkbookUtilFactory {

  
  private static HashMap<String, WorkbookUtil> utils ;

  public static enum FileType { RESOURCE, FILENAME } ;

  private static WorkbookUtilFactory factory ;
  
  private WorkbookUtilFactory() {}
  
  
  public static WorkbookUtil getInstance( String fileName ) {
    
    if( utils == null ) {
      utils = new HashMap<String, WorkbookUtil>() ;
    }
    
    if( utils.containsKey(  fileName ) ) {
      return utils.get( fileName ) ; 
    } else {
      WorkbookUtil util = new WorkbookUtil( fileName ) ;
      utils.put( fileName, util ) ;
      return util ;
    }
  }
  
  public static WorkbookUtil getInstance( Workbook w ) {
	  WorkbookUtil util = new WorkbookUtil( w ) ;
	  return util ;
  }
  
  public static Workbook getInstance( FileType type, String fileName ) throws Exception {
	
	Workbook workbook = null ;
	  
	if( factory == null ) factory = new WorkbookUtilFactory()  ;
	
  	if( type == FileType.RESOURCE ) {
	       InputStream is = factory.getClass().getResourceAsStream( fileName );
	        try {
	            workbook = WorkbookFactory.create(is) ;
	        } catch( InvalidFormatException e ) {
	            // TODO Auto-generated catch block
	            e.printStackTrace() ;
	        }
	} else {
     File wbFile = new File( fileName ) ;
     if( wbFile.exists() && wbFile.canRead() ) {
         workbook = WorkbookFactory.create( new FileInputStream( wbFile ) ) ;
     } else {
         throw new FileNotFoundException( "The file " + fileName + " is " + 
                 "either unreadable or does not exist.  Please double " + 
                 "check the file name." ) ;
     }
	}
  	
  	return workbook ;

  }
  
  public static void releaseWorkbook( WorkbookUtil util ) {
    if( utils != null ) {
      Iterator<String> keysIt = utils.keySet().iterator() ;
      while( keysIt.hasNext() ) {
        String key = keysIt.next() ;
        WorkbookUtil utilVal = utils.get( key ) ;
        if( utilVal == util ) {
          utils.remove( key ) ;
          utilVal = null ;
        }
      }
    }
  }
 
}

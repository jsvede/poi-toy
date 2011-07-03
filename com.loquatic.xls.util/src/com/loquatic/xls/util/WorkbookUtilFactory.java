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

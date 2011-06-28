package com.loquatic.xls.util;

public class CellData {
  
  private String cellId ;
  private double numericValue ;
  private String cellFormula ;
  
  public CellData( String id, double num, String formula ) {
    numericValue = num ;
    cellFormula = formula ;
    cellId = id ;
  }

  public double getNumericValue() {
    return numericValue ;
  }

  public void setNumericValue( double numericValue ) {
    this.numericValue = numericValue ;
  }

  public String getCellFormula() {
    return cellFormula ;
  }

  public void setCellFormula( String cellFormula ) {
    this.cellFormula = cellFormula ;
  }
  
  public String getCellId() {
    return cellId ;
  }

  public void setCellId( String cellId ) {
    this.cellId = cellId ;
  }

  @Override
  public String toString() {
    return "CellData [cellId=" + cellId + ", numericValue=" + numericValue
        + ", cellFormula=" + cellFormula + "]" ;
  }
  
}

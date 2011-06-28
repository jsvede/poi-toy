package com.loquatic.xls.util;

import java.util.LinkedList;

public class CellInfo {

	private String sheetName;
	private String cellId;
	private CellInfo parentCell;
	private LinkedList<CellInfo> children;

	// public CellInfo( String cellId, CellInfo parentCell,
	// LinkedList<CellInfo> children ) {
	// super() ;
	// this.cellId = cellId ;
	// this.parentCell = parentCell ;
	// this.children = children ;
	// }

	public CellInfo(String sheetNm, String cellId, CellInfo parentCell) {
		super();
		sheetName = sheetNm;
		this.cellId = cellId;
		this.parentCell = parentCell;
		children = new LinkedList<CellInfo>();
	}

	public String getCellId() {
		return cellId;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public void setCellId(String cellId) {
		this.cellId = cellId;
	}

	public CellInfo getParentCell() {
		return parentCell;
	}

	public void setParentCell(CellInfo parentCell) {
		this.parentCell = parentCell;
	}

	public LinkedList<CellInfo> getChildren() {
		return children;
	}

	public CellInfo[] getChildrenAsArray() {
		if (children != null) {
			return children.toArray(new CellInfo[children.size()]);
		} else {
			return new CellInfo[0];
		}
	}

	public void setChildren(LinkedList<CellInfo> children) {
		this.children = children;
	}

	public void addChild(CellInfo childCell) {
		children.add(childCell);
	}

	@Override
	public String toString() {
		return "CellInfo (sheetName=" + sheetName + ", cellId=" + cellId
				+ ", parentCell="
				+ (parentCell != null ? parentCell.getCellId() : "")
				+ ", children=[" + enumerateChildren() + "])";
	}

	private String enumerateChildren() {
		StringBuilder sb = new StringBuilder();
		int x = 0;
		for (CellInfo c : children) {
			if (x > 0) {
				sb.append(", ");
			}
			sb.append(c.getCellId());
			x++;
		}

		return sb.toString();
	}
}

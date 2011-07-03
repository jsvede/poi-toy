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

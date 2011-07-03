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
package com.loquatic.org.eclipse.nebula.grid.widget.layout;


import java.util.ArrayList ;
import java.util.List ;

import org.eclipse.core.runtime.Assert ;
import org.eclipse.jface.util.Util ;
import org.eclipse.jface.viewers.ColumnLayoutData ;
import org.eclipse.jface.viewers.ColumnPixelData ;
import org.eclipse.jface.viewers.ColumnWeightData ;
import org.eclipse.nebula.widgets.grid.Grid ;
import org.eclipse.nebula.widgets.grid.GridColumn ;
import org.eclipse.swt.SWT ;
import org.eclipse.swt.graphics.Point ;
import org.eclipse.swt.widgets.Composite ;
import org.eclipse.swt.widgets.Item ;
import org.eclipse.swt.widgets.Layout ;
/**
 * The nebula project doesn't have the concept of a TableLayout for the Grid
 * widget so this our solution:  copy the JFace TableLayout and modify it to
 * be able to handle the Grid widget it.
 * 
 * @author jsvede
 *
 */
public class GridWidgetLayout extends Layout {

  /**
   * The number of extra pixels taken as horizontal trim by the table column.
   * To ensure there are N pixels available for the content of the column,
   * assign N+COLUMN_TRIM for the column width.
   * 
   * @since 3.1
   */
  private static int COLUMN_TRIM;

  static {
    if (Util.isWindows()) {
      COLUMN_TRIM = 4;
    } else if (Util.isMac()) {
      COLUMN_TRIM = 24;
    } else {
      COLUMN_TRIM = 3;
    }
  }

  /**
   * The list of column layout data (element type:
   * <code>ColumnLayoutData</code>).
   */
  private List columns = new ArrayList();

  /**
   * Indicates whether <code>layout</code> has yet to be called.
   */
  private boolean firstTime = true;

  /**
   * Creates a new table layout.
   */
  public GridWidgetLayout() {
  }

  /**
   * Adds a new column of data to this table layout.
   * 
   * @param data
   *            the column layout data
   */
  public void addColumnData(ColumnLayoutData data) {
    columns.add(data);
  }

  /*
   * (non-Javadoc) Method declared on Layout.
   */
  public Point computeSize(Composite c, int wHint, int hHint, boolean flush) {
    if (wHint != SWT.DEFAULT && hHint != SWT.DEFAULT) {
      return new Point(wHint, hHint);
    }

    Grid grid = (Grid) c;
    // To avoid recursions.
    grid.setLayout(null);
    // Use native layout algorithm
    Point result = grid.computeSize(wHint, hHint, flush);
    grid.setLayout(this);

    int width = 0;
    int size = columns.size();
    for (int i = 0; i < size; ++i) {
      ColumnLayoutData layoutData = (ColumnLayoutData) columns.get(i);
      if (layoutData instanceof ColumnPixelData) {
        ColumnPixelData col = (ColumnPixelData) layoutData;
        width += col.width;
        if (col.addTrim) {
          width += COLUMN_TRIM;
        }
      } else if (layoutData instanceof ColumnWeightData) {
        ColumnWeightData col = (ColumnWeightData) layoutData;
        width += col.minimumWidth;
      } else {
        Assert.isTrue(false, "Unknown column layout data");//$NON-NLS-1$
      }
    }
    if (width > result.x) {
      result.x = width;
    }
    return result;
  }

  /*
   * (non-Javadoc) Method declared on Layout.
   */
  public void layout(Composite c, boolean flush) {
    // Only do initial layout. Trying to maintain proportions when resizing
    // is too hard,
    // causes lots of widget flicker, causes scroll bars to appear and
    // occasionally stick around (on Windows),
    // requires hooking column resizing as well, and may not be what the
    // user wants anyway.
    if (!firstTime) {
      return;
    }

    int width = c.getClientArea().width;

    // XXX: Layout is being called with an invalid value the first time
    // it is being called on Linux. This method resets the
    // Layout to null so we make sure we run it only when
    // the value is OK.
    if (width <= 1) {
      return;
    }

    Item[] tableColumns = getColumns(c);
    int size = Math.min(columns.size(), tableColumns.length);
    int[] widths = new int[size];
    int fixedWidth = 0;
    int numberOfWeightColumns = 0;
    int totalWeight = 0;

    // First calc space occupied by fixed columns
    for (int i = 0; i < size; i++) {
      ColumnLayoutData col = (ColumnLayoutData) columns.get(i);
      if (col instanceof ColumnPixelData) {
        ColumnPixelData cpd = (ColumnPixelData) col;
        int pixels = cpd.width;
        if (cpd.addTrim) {
          pixels += COLUMN_TRIM;
        }
        widths[i] = pixels;
        fixedWidth += pixels;
      } else if (col instanceof ColumnWeightData) {
        ColumnWeightData cw = (ColumnWeightData) col;
        numberOfWeightColumns++;
        // first time, use the weight specified by the column data,
        // otherwise use the actual width as the weight
        // int weight = firstTime ? cw.weight :
        // tableColumns[i].getWidth();
        int weight = cw.weight;
        totalWeight += weight;
      } else {
        Assert.isTrue(false, "Unknown column layout data");//$NON-NLS-1$
      }
    }

    // Do we have columns that have a weight
    if (numberOfWeightColumns > 0) {
      // Now distribute the rest to the columns with weight.
      int rest = width - fixedWidth;
      int totalDistributed = 0;
      for (int i = 0; i < size; ++i) {
        ColumnLayoutData col = (ColumnLayoutData) columns.get(i);
        if (col instanceof ColumnWeightData) {
          ColumnWeightData cw = (ColumnWeightData) col;
          // calculate weight as above
          // int weight = firstTime ? cw.weight :
          // tableColumns[i].getWidth();
          int weight = cw.weight;
          int pixels = totalWeight == 0 ? 0 : weight * rest
              / totalWeight;
          if (pixels < cw.minimumWidth) {
            pixels = cw.minimumWidth;
          }
          totalDistributed += pixels;
          widths[i] = pixels;
        }
      }

      // Distribute any remaining pixels to columns with weight.
      int diff = rest - totalDistributed;
      for (int i = 0; diff > 0; ++i) {
        if (i == size) {
          i = 0;
        }
        ColumnLayoutData col = (ColumnLayoutData) columns.get(i);
        if (col instanceof ColumnWeightData) {
          ++widths[i];
          --diff;
        }
      }
    }

    firstTime = false;

    for (int i = 0; i < size; i++) {
      setWidth(tableColumns[i], widths[i]);
    }
  }

  /**
   * Set the width of the item.
   * 
   * @param item
   * @param width
   */
  private void setWidth(Item item, int width) {
    if (item instanceof GridColumn) {
      ((GridColumn) item).setWidth(width);
    } 

  }

  /**
   * Return the columns for the receiver.
   * 
   * @param composite
   * @return Item[]
   */
  private Item[] getColumns(Composite composite) {
    return ((Grid) composite).getColumns();
  }
}

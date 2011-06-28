package com.loquatic.xls.gui;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.loquatic.xls.gui.views.SheetNameView;

public class Perspective implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		layout.addView( SheetNameView.ID, IPageLayout.LEFT,
				0.25f, IPageLayout.ID_EDITOR_AREA);
	
	}
}

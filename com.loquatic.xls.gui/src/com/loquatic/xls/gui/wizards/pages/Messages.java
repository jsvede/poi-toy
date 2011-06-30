package com.loquatic.xls.gui.wizards.pages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "com.loquatic.xls.gui.wizards.pages.messages"; //$NON-NLS-1$
	public static String SelectJarFileWizardPage_Title ;
	public static String SelectJarFileWizardPage_Description ;
	public static String SelectJarFileWizardPage_Label_Select_File ;
	public static String SelectJarFileWizardPage_Button_Select_File_Label ;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}

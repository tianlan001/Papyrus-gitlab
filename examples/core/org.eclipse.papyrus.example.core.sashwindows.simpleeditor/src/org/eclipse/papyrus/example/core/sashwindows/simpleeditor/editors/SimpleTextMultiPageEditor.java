/*****************************************************************************
 * Copyright (c) 2009 CEA LIST & LIFL 
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.example.core.sashwindows.simpleeditor.editors;


import java.io.StringWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IComponentModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.ISashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.simple.SimpleSashWindowsContentProvider;
import org.eclipse.papyrus.infra.core.sasheditor.multipage.editor.MultiPageEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.EditorActionBarContributor;
import org.eclipse.ui.part.FileEditorInput;

/**
 * An example showing how to create a multi-page editor using a ContentProvider.
 * This example is similar to the one provided by Eclipse wizard on MultiPage.
 * 
 * This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class SimpleTextMultiPageEditor extends MultiPageEditor implements IResourceChangeListener {

	/** The text editor used in page 0. */
	private TextEditor editor;

	/** The font chosen in page 1. */
	private Font font;

	/** The text widget used in page 2. */
	private StyledText text;

	/**
	 * Creates a multi-page editor example.
	 */
	public SimpleTextMultiPageEditor() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Create and initialize the pageProvider.
	 */
	protected ISashWindowsContentProvider createPageProvider() {
		SimpleSashWindowsContentProvider pageProvider = new SimpleSashWindowsContentProvider();
		// Adding requested pages
		pageProvider.addPage(new Page0());
		pageProvider.addPage(new Page1());
		pageProvider.addPage(new Page2());
		pageProvider.addPage(new Page0());


		return pageProvider;
	}

	/**
	 * Description of the first page
	 * 
	 * @author dumoulin
	 */
	public class Page0 implements IEditorModel {

		public IEditorPart createIEditorPart() throws PartInitException {
			editor = new TextEditor();
			return editor;
		}

		public Image getTabIcon() {
			return null;
		}

		public String getTabTitle() {
			return editor.getTitle();
		}

		public Object getRawModel() {
			return this;
		}

		public EditorActionBarContributor getActionBarContributor() {
			return null;
		}

		@Override
		public void dispose() {
			// Pass
		}
	}

	/**
	 * Describe the page1
	 * 
	 * @author dumoulin
	 */
	public class Page1 implements IComponentModel {

		public Composite createPartControl(Composite parent) {
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout();
			composite.setLayout(layout);
			layout.numColumns = 2;

			Button fontButton = new Button(composite, SWT.NONE);
			GridData gd = new GridData(GridData.BEGINNING);
			gd.horizontalSpan = 2;
			fontButton.setLayoutData(gd);
			fontButton.setText("Change Font...");

			fontButton.addSelectionListener(new SelectionAdapter() {

				public void widgetSelected(SelectionEvent event) {
					setFont();
				}
			});

			return composite;
		}

		public Image getTabIcon() {
			return null;
		}

		public String getTabTitle() {
			return "Properties";
		}

		public Object getRawModel() {
			return this;
		}

		@Override
		public void dispose() {
			// Pass
		}

	}

	public class Page2 implements IComponentModel {

		public Composite createPartControl(Composite parent) {
			Composite composite = new Composite(getContainer(), SWT.NONE);
			FillLayout layout = new FillLayout();
			composite.setLayout(layout);
			text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
			text.setEditable(false);

			return composite;
		}

		public Image getTabIcon() {
			return null;
		}

		public String getTabTitle() {
			return "Preview";
		}

		public Object getRawModel() {
			return this;
		}

		@Override
		public void dispose() {
			// Pass
		}

	}

	/**
	 * The <code>MultiPageEditorPart</code> implementation of this <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}

	/**
	 * Saves the multi-page editor's document.
	 */
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	public void doSaveAs() {
		IEditorPart editor = getEditor(0);
		editor.doSaveAs();
		setPageText(0, editor.getTitle());
		setInput(editor.getEditorInput());
	}

	/*
	 * (non-Javadoc)
	 * Method declared on IEditorPart
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		IDE.gotoMarker(getEditor(0), marker);
	}

	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	public void init(IEditorSite site, IEditorInput editorInput)
			throws PartInitException {
		if(!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}

	/*
	 * (non-Javadoc)
	 * Method declared on IEditorPart.
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * Calculates the contents of page 2 when the it is activated.
	 */
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
		if(newPageIndex == 2) {
			sortWords();
		}
	}

	/**
	 * Closes all project files on project close.
	 */
	public void resourceChanged(final IResourceChangeEvent event) {
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE) {
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					for(int i = 0; i < pages.length; i++) {
						if(((FileEditorInput)editor.getEditorInput()).getFile().getProject().equals(event.getResource())) {
							IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
							pages[i].closeEditor(editorPart, true);
						}
					}
				}
			});
		}
	}

	/**
	 * Sets the font related data to be applied to the text in page 2.
	 */
	void setFont() {
		FontDialog fontDialog = new FontDialog(getSite().getShell());
		fontDialog.setFontList(text.getFont().getFontData());
		FontData fontData = fontDialog.open();
		if(fontData != null) {
			if(font != null)
				font.dispose();
			font = new Font(text.getDisplay(), fontData);
			text.setFont(font);
		}
	}

	/**
	 * Sorts the words in page 0, and shows them in page 2.
	 */
	void sortWords() {

		String editorText =
				editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();

		StringTokenizer tokenizer =
				new StringTokenizer(editorText, " \t\n\r\f!@#\u0024%^&*()-_=+`~[]{};:'\",.<>/?|\\");
		ArrayList editorWords = new ArrayList();
		while(tokenizer.hasMoreTokens()) {
			editorWords.add(tokenizer.nextToken());
		}

		Collections.sort(editorWords, Collator.getInstance());
		StringWriter displayText = new StringWriter();
		for(int i = 0; i < editorWords.size(); i++) {
			displayText.write(((String)editorWords.get(i)));
			displayText.write(System.getProperty("line.separator"));
		}
		text.setText(displayText.toString());
	}
}

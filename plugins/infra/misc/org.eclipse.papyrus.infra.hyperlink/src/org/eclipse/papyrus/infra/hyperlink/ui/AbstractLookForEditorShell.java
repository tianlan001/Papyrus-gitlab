/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.ui;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.dialogs.PatternFilter;


/**
 * Abstract superclass of the dialog used to search for a notation editor in the
 * currently open model(s).
 */
public class AbstractLookForEditorShell extends TrayDialog {

	/** The tab folder. */
	private TabFolder tabFolder = null;

	/** The diagram listcomposite. */
	private Composite diagramListcomposite = null;

	/** The tree viewcomposite. */
	private Composite treeViewcomposite = null;

	private Composite afterTreeViewComposite = null;

	/** The modeltree. */
	private final Tree modeltree = null;

	/** The new diagrambutton. */
	private Button newDiagrambutton = null;

	/** The remove diagrambutton. */
	private Button removeDiagrambutton = null;

	/** The diagram listtree. */
	private final Tree diagramListtree = null;

	/** The diagramfiltered tree. */
	private FilteredTree diagramfilteredTree = null;

	/** The mode filtered tree. */
	private FilteredTree modeFilteredTree = null;

	/** The c label. */
	private CLabel cLabel = null;

	/**
	 * @since 2.0
	 */
	public AbstractLookForEditorShell(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * @since 2.0
	 */
	public AbstractLookForEditorShell(IShellProvider parentShell) {
		super(parentShell);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		// Tabs on the top
		tabFolder = new TabFolder(parent, SWT.TOP);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(tabFolder);

		createDiagramListcomposite();
		createTreeViewcomposite();

		TabItem tabItem = new TabItem(tabFolder, SWT.None);
		tabItem.setControl(diagramListcomposite);
		tabItem.setText(Messages.AbstractLookForEditorShell_EditorsList);

		TabItem tabItem1 = new TabItem(tabFolder, SWT.None);
		tabItem1.setText(Messages.AbstractLookForEditorShell_TreeView);
		tabItem1.setControl(treeViewcomposite);

		contentsCreated();

		return tabFolder;
	}

	/**
	 * Hook that subclasses may override to handle the creation of the dialog contents.
	 * 
	 * @since 2.0
	 */
	protected void contentsCreated() {
		// Pass
	}

	/**
	 * This method initializes diagramListcomposite.
	 */
	private void createDiagramListcomposite() {
		GridLayout gridLayout2 = new GridLayout();
		diagramListcomposite = new Composite(tabFolder, SWT.BORDER);
		createDiagramfilteredTree();
		diagramListcomposite.setLayout(gridLayout2);
	}

	/**
	 * This method initializes treeViewcomposite.
	 */
	private void createTreeViewcomposite() {
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData5 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;

		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		treeViewcomposite = new Composite(tabFolder, SWT.NONE);

		createModelFilteredTree();

		treeViewcomposite.setLayout(gridLayout);
		newDiagrambutton = new Button(treeViewcomposite, SWT.NONE);
		newDiagrambutton.setText(Messages.AbstractLookForEditorShell_New);
		newDiagrambutton.setLayoutData(gridData4);
		removeDiagrambutton = new Button(treeViewcomposite, SWT.NONE);
		removeDiagrambutton.setText(Messages.AbstractLookForEditorShell_Remove);
		removeDiagrambutton.setLayoutData(gridData5);
		cLabel = new CLabel(treeViewcomposite, SWT.NONE);
		cLabel.setText("   "); //$NON-NLS-1$

		afterTreeViewComposite = new Composite(treeViewcomposite, SWT.NONE);
		afterTreeViewComposite.setLayout(new FillLayout());
		afterTreeViewComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

	/**
	 * Gets the modeltree.
	 *
	 * @return the modeltree
	 */
	// @unused
	protected Tree getModeltree() {
		return modeltree;
	}

	/**
	 * Gets the new diagrambutton.
	 *
	 * @return the newDiagrambutton
	 */
	protected Button getNewDiagrambutton() {
		return newDiagrambutton;
	}

	/**
	 * Sets the new diagrambutton.
	 *
	 * @param newDiagrambutton
	 *            the newDiagrambutton to set
	 */
	// @unused
	protected void setNewDiagrambutton(Button newDiagrambutton) {
		this.newDiagrambutton = newDiagrambutton;
	}

	/**
	 * Gets the remove diagrambutton.
	 *
	 * @return the removeDiagrambutton
	 */
	protected Button getRemoveDiagrambutton() {
		return removeDiagrambutton;
	}

	/**
	 * Sets the remove diagrambutton.
	 *
	 * @param removeDiagrambutton
	 *            the removeDiagrambutton to set
	 */
	// @unused
	protected void setRemoveDiagrambutton(Button removeDiagrambutton) {
		this.removeDiagrambutton = removeDiagrambutton;
	}

	/**
	 * Gets the diagram listtree.
	 *
	 * @return the diagramListtree
	 */
	// @unused
	protected Tree getDiagramListtree() {
		return diagramListtree;
	}

	/**
	 * This method initializes diagramfilteredTree.
	 */
	private void createDiagramfilteredTree() {
		GridData gridData6 = new GridData();
		gridData6.horizontalAlignment = GridData.FILL;
		gridData6.grabExcessHorizontalSpace = true;
		gridData6.grabExcessVerticalSpace = true;
		gridData6.verticalAlignment = GridData.FILL;
		diagramfilteredTree = new FilteredTree(diagramListcomposite, SWT.BORDER, new PatternFilter(), true);
		diagramfilteredTree.setLayoutData(gridData6);
	}

	/**
	 * This method initializes filteredTree.
	 */
	private void createModelFilteredTree() {
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.grabExcessVerticalSpace = true;
		gridData3.verticalSpan = 3;
		gridData3.verticalAlignment = GridData.FILL;
		modeFilteredTree = new FilteredTree(treeViewcomposite, SWT.BORDER, new PatternFilter(), true);
		modeFilteredTree.setLayoutData(gridData3);
	}

	/**
	 * Gets the diagramfiltered tree.
	 *
	 * @return the diagramfilteredTree
	 */
	protected FilteredTree getDiagramfilteredTree() {
		return diagramfilteredTree;
	}

	/**
	 * Gets the mode filtered tree.
	 *
	 * @return the modeFilteredTree
	 */
	protected FilteredTree getModeFilteredTree() {
		return modeFilteredTree;
	}

	/**
	 * @since 2.0
	 */
	protected Composite getAfterTreeViewComposite() {
		return afterTreeViewComposite;
	}
}

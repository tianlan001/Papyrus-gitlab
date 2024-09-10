/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - add flat button.
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 *
 * @author Juan Cadavid
 *
 */
public class FlattenableRestrictedFilteredContentProvider extends AbstractFilteredContentProvider implements IStaticContentProvider, IRestrictedContentProvider, IFlattenableContentProvider {

	/** true if the display should be flat */
	private boolean isFlat = false;

	private ReferenceSelector selector;

	protected IRestrictedContentProvider provider;

	protected HierarchicToFlatContentProvider flatProvider;

	/** the flat button. */
	private ToolItem flatButton;

	/** The dialog settings key for this class. */
	protected static final String DIALOG_SETTINGS_KEY = FlattenableRestrictedFilteredContentProvider.class.getName();

	/** The dialogs settings key for the flat value */
	protected static final String FLAT_SETTINGS_KEY = "flat"; //$NON-NLS-1$

	/** The default profile icon path. */
	protected static final String ICONS_FLAT_VIEW = "/icons/flatView.gif";//$NON-NLS-1$

	/** The default profile icon path. */
	protected static final String ICONS_TREE_VIEW = "/icons/treeView.gif";//$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 * @param provider
	 *            the encapsulated content provider
	 * @param selector
	 *            the reference selector (we need it to refresh it)
	 */
	public FlattenableRestrictedFilteredContentProvider(IRestrictedContentProvider provider, ReferenceSelector selector) {
		this.provider = provider;
		flatProvider = new HierarchicToFlatContentProvider(provider);
		this.selector = selector;
	}

	/**
	 * Add 2 checkboxes to the dialog
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.AbstractFilteredContentProvider#createAfter(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createAfter(final Composite parent) {

		super.createAfter(parent);
		Composite checkboxSection = new Composite(parent, SWT.NONE);
		checkboxSection.setLayout(new FillLayout(SWT.VERTICAL));
		final Button onlyCurrentContainersCheckbox = new Button(checkboxSection, SWT.CHECK);
		onlyCurrentContainersCheckbox.setText(Messages.FlattenableRestrictedFilteredContentProvider_AllPossibleContentsMessage);
		onlyCurrentContainersCheckbox.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				setRestriction(!onlyCurrentContainersCheckbox.getSelection());
				viewer.refresh();
				selector.refresh();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
	}

	/**
	 * {@inheritDoc}<br>
	 * Extended to add flat check boxes to the dialog.
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IGraphicalContentProvider#createViewerToolbar(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createViewerToolbar(final Composite parent) {
		createFlatSwitchButton(parent);
		if (provider instanceof IGraphicalContentProvider) {
			((IGraphicalContentProvider) provider).createViewerToolbar(parent);
		}
	}

	/**
	 * create the flat checkBox.
	 * 
	 * @param parent
	 *            The parent {@link Composite}.
	 */
	protected void createFlatSwitchButton(final Composite parent) {
		// Create the checkBox button
		ToolBar Toolbar = new ToolBar(parent, SWT.NONE);
		flatButton = new ToolItem(Toolbar, SWT.CHECK);
		refreshFlatButton();
		flatButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				setFlat(!isFlat);
				getDialogSettings().put(FLAT_SETTINGS_KEY, isFlat);
				refreshFlatButton();
				viewer.refresh();
				selector.refresh();
			}
		});
	}

	/**
	 * Refresh the button icon and tooltip according to isFlat.
	 */
	protected void refreshFlatButton() {
		flatButton.setImage(Activator.getDefault().getImage(isFlat ? ICONS_TREE_VIEW : ICONS_FLAT_VIEW));
		flatButton.setToolTipText(isFlat ? Messages.FlattenableFilteredContentProvider_flatButtonAsFlatTooltip : Messages.FlattenableFilteredContentProvider_flatButtonAsTreeTooltip);
	}

	/**
	 * Returns the dialog settings. Returned object can't be null.
	 *
	 * @return dialog settings for this dialog
	 */
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS_KEY);
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS_KEY);
		}
		return settings;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (isFlat) {
			return flatProvider.getElements(inputElement);
		}
		return provider.getElements(inputElement);
	}

	@Override
	public void dispose() {
		flatProvider.dispose();
		provider.dispose();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		super.inputChanged(viewer, oldInput, newInput);
		flatProvider.inputChanged(viewer, oldInput, newInput);
		provider.inputChanged(viewer, oldInput, newInput);
	}

	@Override
	public boolean isValidValue(Object element) {
		return provider.isValidValue(element);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (isFlat) {
			return new Object[0];
		}
		return provider.getChildren(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if (isFlat) {
			return null;
		}
		return provider.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (isFlat) {
			return false;
		}
		return provider.hasChildren(element);
	}

	@Override
	public void setRestriction(boolean isRestricted) {
		provider.setRestriction(isRestricted);
	}

	@Override
	public void setFlat(boolean flat) {
		this.isFlat = flat;

	}

	@Override
	public Object[] getElements() {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider#setIgnoreInheritedElements(boolean)
	 *
	 * @param ignoreInheritedElements
	 */
	@Override
	public void setIgnoreInheritedElements(boolean ignoreInheritedElements) {
		provider.setIgnoreInheritedElements(ignoreInheritedElements);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider#isIgnoringInheritedElements()
	 *
	 * @return
	 */
	@Override
	public boolean isIgnoringInheritedElements() {
		return provider.isIgnoringInheritedElements();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IRestrictedContentProvider#isRestricted()
	 *
	 * @return
	 */
	@Override
	public boolean isRestricted() {
		return provider.isRestricted();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.providers.IFlattenableContentProvider#isFlat()
	 */
	@Override
	public boolean isFlat() {
		return isFlat;
	}

}

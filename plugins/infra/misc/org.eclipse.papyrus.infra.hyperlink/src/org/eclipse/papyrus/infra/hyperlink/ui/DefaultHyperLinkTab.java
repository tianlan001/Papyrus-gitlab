/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.helper.AbstractHyperLinkHelper;
import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;

/**
 * this is the tab in charge to display the hyperlink for the property defaut
 *
 */
public class DefaultHyperLinkTab extends AbstractHyperLinkTab {


	/**
	 *
	 * Constructor.
	 *
	 */
	public DefaultHyperLinkTab() {
		super();
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param tabId
	 * @param helper
	 */
	public DefaultHyperLinkTab(final String tabId, final AbstractHyperLinkHelper helper) {
		super(tabId, helper);
	}

	protected TableViewer availableHyperLinkViewer;

	protected Button defaultHRight;

	protected Button defaultHleft;

	protected Button defaultHup;

	protected Button defaultHdown;

	protected TableViewer defaultHyperLinkViewer;

	protected List<HyperLinkObject> defaultHyperLinkObject = new ArrayList<HyperLinkObject>();

	protected List<HyperLinkObject> availableHyperLinkObject = new ArrayList<HyperLinkObject>();

	protected Composite defaultHyperlinkComposite;


	/**
	 *
	 * @return get the list of hyperlink that are to be as default hyperlinks
	 */
	public List<HyperLinkObject> getDefaultHyperLinkObject() {
		return defaultHyperLinkObject;
	}

	@Override
	public void init(final TabFolder tabFolder, List<HyperLinkObject> hyperlinkObjects, EObject element) {
		super.init(tabFolder, hyperlinkObjects, element);

		TabItem tbtmDefaultsHyperlinks = new TabItem(tabFolder, SWT.NONE);
		tbtmDefaultsHyperlinks.setText(Messages.DefaultHyperLinkTab_DefaultHyperlinks);
		defaultHyperlinkComposite = new Composite(tabFolder, SWT.NONE);

		defaultHyperlinkComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		defaultHyperlinkComposite.setLayout(new GridLayout(4, false));

		Composite availableHyperlinks = new Composite(defaultHyperlinkComposite, SWT.NONE);
		availableHyperlinks.setLayout(new GridLayout(1, true));
		availableHyperlinks.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite addRemoveButtonsComposite = new Composite(defaultHyperlinkComposite, SWT.NONE);
		addRemoveButtonsComposite.setLayout(new GridLayout(1, true));
		addRemoveButtonsComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));

		Composite defaultHyperlinks = new Composite(defaultHyperlinkComposite, SWT.NONE);
		defaultHyperlinks.setLayout(new GridLayout(1, true));
		defaultHyperlinks.setLayoutData(new GridData(GridData.FILL_BOTH));

		Composite upDownButtonsComposite = new Composite(defaultHyperlinkComposite, SWT.NONE);
		upDownButtonsComposite.setLayout(new GridLayout(1, true));
		upDownButtonsComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));

		tbtmDefaultsHyperlinks.setControl(defaultHyperlinkComposite);

		Label lblHyperlinks = new Label(availableHyperlinks, SWT.NONE);
		lblHyperlinks.setText(Messages.DefaultHyperLinkTab_HyperLinks);

		Label lblDefaultHyperlinksby = new Label(defaultHyperlinks, SWT.NONE);
		lblDefaultHyperlinksby.setText(Messages.DefaultHyperLinkTab_DefaultHyperLinks);

		Table availableHyperLink = new Table(availableHyperlinks, SWT.BORDER | SWT.FULL_SELECTION);
		availableHyperLink.setLayoutData(new GridData(GridData.FILL_BOTH));


		defaultHRight = new Button(addRemoveButtonsComposite, SWT.PUSH);
		defaultHRight.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				handleRightButton();
			}
		});
		defaultHRight.setToolTipText("Set default hyperlink");

		defaultHleft = new Button(addRemoveButtonsComposite, SWT.PUSH);
		defaultHleft.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				handleLeftButton();
			}
		});
		defaultHleft.setToolTipText("Remove default hyperlink");


		Table defaultHyperLink = new Table(defaultHyperlinks, SWT.BORDER | SWT.FULL_SELECTION);
		defaultHyperLink.setLayoutData(new GridData(GridData.FILL_BOTH));


		defaultHup = new Button(upDownButtonsComposite, SWT.PUSH);
		defaultHup.setToolTipText("Move default hyperlink up");

		defaultHup.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// move element bottom to top
				if (defaultHyperLinkViewer.getSelection() != null) {
					// normally this viewer contains only hyperlinkObject
					if (defaultHyperLinkViewer.getSelection() instanceof IStructuredSelection) {

						HyperLinkObject hyperlinkObjectToMove = (HyperLinkObject) ((IStructuredSelection) defaultHyperLinkViewer.getSelection()).getFirstElement();
						int index = defaultHyperLinkObject.indexOf(hyperlinkObjectToMove);
						if (index > 0) {
							defaultHyperLinkObject.remove(hyperlinkObjectToMove);
							defaultHyperLinkObject.add(index - 1, hyperlinkObjectToMove);
							refresh();
							updateButtonEnablement(null);
						}
					}
				}
			}
		});

		defaultHdown = new Button(upDownButtonsComposite, SWT.PUSH);
		defaultHdown.setToolTipText("Move default hyperlink down");

		defaultHdown.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// move element top to bottom
				if (defaultHyperLinkViewer.getSelection() != null) {
					// normally this viewer contains only hyperlinkObject
					if (defaultHyperLinkViewer.getSelection() instanceof IStructuredSelection) {

						HyperLinkObject hyperlinkObjectToMove = (HyperLinkObject) ((IStructuredSelection) defaultHyperLinkViewer.getSelection()).getFirstElement();
						int index = defaultHyperLinkObject.indexOf(hyperlinkObjectToMove);
						if (index < defaultHyperLinkObject.size() - 1) {

							defaultHyperLinkObject.remove(hyperlinkObjectToMove);
							defaultHyperLinkObject.add(index + 1, hyperlinkObjectToMove);
							refresh();
							updateButtonEnablement(null);
						}
					}
				}
			}
		});


		// associate image to buttons
		defaultHdown.setImage(Activator.getDefault().getIcon(Activator.IMG_ARROW_DOWN));
		defaultHup.setImage(Activator.getDefault().getIcon(Activator.IMG_ARROW_UP));
		defaultHleft.setImage(Activator.getDefault().getIcon(Activator.IMG_ARROW_LEFT));
		defaultHRight.setImage(Activator.getDefault().getIcon(Activator.IMG_ARROW_RIGHT));

		EObject contextElement = EMFHelper.getEObject(element);

		ILabelProvider provider = null;
		if (contextElement != null) {
			try {
				provider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, contextElement).getLabelProvider();
			} catch (ServiceException ex) {
				Activator.log.error(ex);
			}
		}

		if (provider == null) {
			provider = new LabelProvider();
		}


		// init tableviewer
		availableHyperLinkViewer = new TableViewer(availableHyperLink);
		availableHyperLinkViewer.setLabelProvider(provider);
		availableHyperLinkViewer.setContentProvider(CollectionContentProvider.instance);

		availableHyperLinkViewer.setInput(hyperlinkObjects);
		availableHyperLinkViewer.addSelectionChangedListener(this::updateButtonEnablement);
		availableHyperLinkViewer.addDoubleClickListener(event -> handleRightButton());

		defaultHyperLinkViewer = new TableViewer(defaultHyperLink);
		defaultHyperLinkViewer.setLabelProvider(provider);
		defaultHyperLinkViewer.setContentProvider(CollectionContentProvider.instance);
		defaultHyperLinkViewer.addSelectionChangedListener(this::updateButtonEnablement);
		defaultHyperLinkViewer.addDoubleClickListener(event -> handleLeftButton());

		updateButtonEnablement(null);
	}

	private void handleRightButton() {
		// move element left to right
		if (availableHyperLinkViewer.getSelection() != null) {
			// normally this viewer contains only hyperlinkObject
			if (availableHyperLinkViewer.getSelection() instanceof IStructuredSelection) {
				HyperLinkObject hyperlinkObjectToMove = (HyperLinkObject) ((IStructuredSelection) availableHyperLinkViewer.getSelection()).getFirstElement();
				hyperlinkObjectToMove.setIsDefault(true);
				availableHyperLinkObject.remove(hyperlinkObjectToMove);
				defaultHyperLinkObject.add(hyperlinkObjectToMove);
				refresh();
			}
		}
	}

	private void handleLeftButton() {
		// move element right to left
		if (defaultHyperLinkViewer.getSelection() != null) {
			// normally this viewer contains only hyperlinkObject
			if (defaultHyperLinkViewer.getSelection() instanceof IStructuredSelection) {

				HyperLinkObject hyperlinkObjectToMove = (HyperLinkObject) ((IStructuredSelection) defaultHyperLinkViewer.getSelection()).getFirstElement();
				hyperlinkObjectToMove.setIsDefault(false);
				defaultHyperLinkObject.remove(hyperlinkObjectToMove);
				availableHyperLinkObject.add(hyperlinkObjectToMove);
				refresh();
			}
		}
	}

	private void updateButtonEnablement(SelectionChangedEvent event) {
		defaultHRight.setEnabled(!availableHyperLinkViewer.getSelection().isEmpty());
		defaultHleft.setEnabled(!defaultHyperLinkViewer.getSelection().isEmpty());

		int index = defaultHyperLinkViewer.getTable().getSelectionIndex();
		defaultHup.setEnabled(index > 0);
		defaultHdown.setEnabled((index >= 0) && (index < (defaultHyperLinkObject.size() - 1)));
	}

	/**
	 *
	 * @return the composite that manage all widgets in the this tab
	 */
	protected Composite getMainComposite() {
		return defaultHyperlinkComposite;
	}

	/**
	 * used to refresh table
	 */
	protected void refresh() {
		availableHyperLinkViewer.setInput(availableHyperLinkObject);
		defaultHyperLinkViewer.setInput(defaultHyperLinkObject);
	}



	/**
	 * input of this tab
	 * --> fill all available hyperlinks
	 * --> fill all default hyperlinks
	 *
	 * @param hyperLinkObjectList
	 *            the list of hyperlinks
	 */
	@Override
	public void setInput(List<HyperLinkObject> hyperLinkObjectList) {
		defaultHyperLinkObject.clear();
		availableHyperLinkObject.clear();
		// filter between default and not default
		Iterator<HyperLinkObject> iterator = hyperLinkObjectList.iterator();
		while (iterator.hasNext()) {
			HyperLinkObject hyperlinkObject = iterator.next();
			if (hyperlinkObject.getIsDefault()) {
				defaultHyperLinkObject.add(hyperlinkObject);
			} else {
				availableHyperLinkObject.add(hyperlinkObject);
			}

		}
		refresh();
	}
}

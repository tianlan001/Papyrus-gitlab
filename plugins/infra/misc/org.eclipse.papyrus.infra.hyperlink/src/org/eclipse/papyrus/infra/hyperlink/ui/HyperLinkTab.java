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
 *  Christian W. Damus (CEA LIST) - Consolidate all hyperlink helper contributions into one tab
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.ui;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.helper.AbstractHyperLinkHelper;
import org.eclipse.papyrus.infra.hyperlink.helper.CompositeHyperlinkHelper;
import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.providers.CollectionContentProvider;
import org.eclipse.papyrus.infra.widgets.util.PopupButtonMenu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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
 * this class is GUI that represent a tab for each kind of hyperlink
 * for example a tab for diagram, for web links...
 *
 */

// TODO change the methods to get images after the refactoring
public class HyperLinkTab extends AbstractHyperLinkTab {

	/**
	 * The Class DiagramContentProvider.
	 */
	protected TabItem hyperlinksTab;

	protected Table hyperLinkListTable;

	protected Button newHyperLinkbutton;

	protected Button modifyHyperLinkButton;

	protected Button removeHyperLinkButton;

	protected Button upHyperLinkButton;

	protected Button downHyperLinkButton;

	protected TableViewer tableViewer;

	/**
	 *
	 * Constructor.
	 *
	 */
	public HyperLinkTab() {
		// nothing to do
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param tabId
	 * @param helpers
	 */
	public HyperLinkTab(String tabId, Collection<? extends AbstractHyperLinkHelper> helpers) {
		super(tabId, (helpers.size() == 1) ? helpers.iterator().next() : new CompositeHyperlinkHelper(helpers));
	}

	/**
	 * get TableVeiver
	 *
	 * @return
	 */
	public TableViewer getTableViewer() {
		return tableViewer;
	}

	/**
	 * set list of hyperlinks to display in the tab
	 *
	 * @param hyperlinkObjects
	 */
	public void setHyperlinkObjects(List<HyperLinkObject> hyperlinkObjects) {
		getHyperlinkObjects().clear();
		getHyperlinkObjects().addAll(hyperlinkObjects);
	}


	@Override
	public void init(TabFolder tabFolder, List<HyperLinkObject> hyperlinkObjects, EObject element/* , IHyperLinkShell parent */) {
		super.init(tabFolder, hyperlinkObjects, element /* , parent */);

		hyperlinksTab = new TabItem(tabFolder, SWT.NONE);
		hyperlinksTab.setText(Messages.HyperLinkTab_title);

		Composite diagramComposite = new Composite(tabFolder, SWT.NONE);
		hyperlinksTab.setControl(diagramComposite);
		GridData gridData4 = new GridData();
		gridData4.horizontalAlignment = GridData.FILL;
		gridData4.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.horizontalAlignment = GridData.FILL;
		gridData3.verticalAlignment = GridData.CENTER;
		GridData gridData2 = new GridData();
		gridData2.horizontalAlignment = GridData.FILL;
		gridData2.verticalAlignment = GridData.CENTER;
		GridData gridData1 = new GridData();
		gridData1.verticalAlignment = GridData.FILL;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = true;
		gridData1.verticalSpan = 6;
		gridData1.horizontalAlignment = GridData.FILL;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;

		diagramComposite.setLayout(gridLayout);
		CLabel listLabel = new CLabel(diagramComposite, SWT.SHADOW_NONE);
		listLabel.setText(Messages.HyperLinkTab_Listof);
		listLabel.setEnabled(false);

		new Label(diagramComposite, SWT.NONE);
		hyperLinkListTable = new Table(diagramComposite, SWT.BORDER | SWT.MULTI);
		tableViewer = new TableViewer(hyperLinkListTable);

		newHyperLinkbutton = new Button(diagramComposite, SWT.PUSH);
		newHyperLinkbutton.setText(""); //$NON-NLS-1$
		newHyperLinkbutton.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Add_16x16.gif")); //$NON-NLS-1$
		newHyperLinkbutton.setLayoutData(gridData4);
		newHyperLinkbutton.setToolTipText("New hyperlink");

		hyperLinkListTable.setHeaderVisible(false);
		hyperLinkListTable.setToolTipText(Messages.HyperLinkTab_SetOf);
		hyperLinkListTable.setLayoutData(gridData1);
		hyperLinkListTable.setLinesVisible(false);

		modifyHyperLinkButton = new Button(diagramComposite, SWT.PUSH);
		modifyHyperLinkButton.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Edit_16x16.gif")); //$NON-NLS-1$
		modifyHyperLinkButton.setToolTipText("Edit hyperlink");

		removeHyperLinkButton = new Button(diagramComposite, SWT.PUSH);
		removeHyperLinkButton.setText(""); //$NON-NLS-1$
		removeHyperLinkButton.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/Delete_16x16.gif")); //$NON-NLS-1$
		removeHyperLinkButton.setToolTipText("Remove hyperlink");

		upHyperLinkButton = new Button(diagramComposite, SWT.PUSH);
		upHyperLinkButton.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/ArrowUp_16x16.gif")); //$NON-NLS-1$
		upHyperLinkButton.setToolTipText("Move hyperlink up");
		upHyperLinkButton.setLayoutData(gridData2);

		downHyperLinkButton = new Button(diagramComposite, SWT.PUSH);
		downHyperLinkButton.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("/icons/ArrowDown_16x16.gif")); //$NON-NLS-1$
		downHyperLinkButton.setToolTipText("Move hyperlink down");
		downHyperLinkButton.setLayoutData(gridData3);

		addListeners();

		tableViewer.setContentProvider(CollectionContentProvider.instance);

		setHyperlinkObjects(getHyperLinkHelper().getFilteredObject(hyperlinkObjects));

		updateButtonEnablement(null);

		ILabelProvider provider = null;
		if (element != null) {
			try {
				provider = ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, element).getLabelProvider();
			} catch (ServiceException ex) {
				Activator.log.error(ex);
			}
		}

		if (provider == null) {
			provider = new LabelProvider();
		}

		tableViewer.setLabelProvider(provider);
		getTableViewer().setInput(getHyperlinkObjects());
	}

	private void updateButtonEnablement(SelectionChangedEvent event) {
		IStructuredSelection selection = (event == null)
				? (IStructuredSelection) tableViewer.getSelection()
				: (IStructuredSelection) event.getSelection();

		if (selection == null) {
			// Disable everything
			getRemoveHyperLinkButton().setEnabled(false);
			getModifyHyperLinkButton().setEnabled(false);
			getUpHyperLinkButton().setEnabled(false);
			getDownHyperLinkButton().setEnabled(false);
		} else {
			int index = getTableViewer().getTable().getSelectionIndex();
			getUpHyperLinkButton().setEnabled(index > 0);
			getDownHyperLinkButton().setEnabled((index >= 0) && (index < (getHyperlinkObjects().size() - 1)));
			getRemoveHyperLinkButton().setEnabled(true);
			getModifyHyperLinkButton().setEnabled(!((List<?>) selection.toList()).stream().anyMatch(HyperLinkSpecificObject.class::isInstance));
		}
	}

	protected void addListeners() {
		getTableViewer().addSelectionChangedListener(this::updateButtonEnablement);

		getRemoveHyperLinkButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (HyperLinkTab.this.getTableViewer().getTable().getSelection().length != 0) {
					Iterator<?> iterator = ((IStructuredSelection) HyperLinkTab.this.getTableViewer().getSelection()).iterator();
					while (iterator.hasNext()) {
						Object object = iterator.next();
						HyperLinkTab.this.getHyperlinkObjects().remove(object);
						HyperLinkTab.this.getTableViewer().setInput(HyperLinkTab.this.getHyperlinkObjects());
					}
				}
			}
		});

		getUpHyperLinkButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (HyperLinkTab.this.getTableViewer().getTable().getSelection().length != 0) {
					Object elt = ((IStructuredSelection) HyperLinkTab.this.getTableViewer().getSelection()).getFirstElement();
					if (HyperLinkTab.this.getHyperlinkObjects().indexOf(elt) == 0) {
						return;
					}
					Iterator<?> iterator = ((IStructuredSelection) HyperLinkTab.this.getTableViewer().getSelection()).iterator();
					while (iterator.hasNext()) {
						HyperLinkObject currentHyperLinkDoc = (HyperLinkObject) iterator.next();
						int index = HyperLinkTab.this.getHyperlinkObjects().indexOf(currentHyperLinkDoc);
						HyperLinkTab.this.getHyperlinkObjects().remove(currentHyperLinkDoc);
						HyperLinkTab.this.getHyperlinkObjects().add(index - 1, currentHyperLinkDoc);
						HyperLinkTab.this.getTableViewer().setInput(HyperLinkTab.this.getHyperlinkObjects());
					}
					updateButtonEnablement(null);
				}
			}
		});

		getDownHyperLinkButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (HyperLinkTab.this.getTableViewer().getTable().getSelection().length != 0) {
					Object[] block = ((IStructuredSelection) HyperLinkTab.this.getTableViewer().getSelection()).toArray();
					if ((HyperLinkTab.this.getHyperlinkObjects().indexOf(block[block.length - 1])) == HyperLinkTab.this.getHyperlinkObjects().size() - 1) {
						return;
					}
					for (int i = block.length - 1; i >= 0; i--) {
						HyperLinkObject currentobject = (HyperLinkObject) block[i];
						int index = HyperLinkTab.this.getHyperlinkObjects().indexOf(currentobject);
						HyperLinkTab.this.getHyperlinkObjects().remove(currentobject);
						HyperLinkTab.this.getHyperlinkObjects().add(index + 1, currentobject);
						HyperLinkTab.this.getTableViewer().setInput(HyperLinkTab.this.getHyperlinkObjects());
					}
					updateButtonEnablement(null);
				}
			}
		});

		final Runnable addHandler = new Runnable() {

			@Override
			public void run() {
				HyperLinkTab.this.getHyperLinkHelper().executeNewMousePressed(hyperlinksTab.getControl().getShell(),
						HyperLinkTab.this.getHyperlinkObjects(), HyperLinkTab.this.getElement());
				HyperLinkTab.this.setInput(HyperLinkTab.this.getHyperlinkObjects());
			}
		};
		if (!isCompositeHelper()) {
			getNewHyperLinkbutton().addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					addHandler.run();
				}
			});
		} else {
			addNewHyperLinkMenuActions(addHandler);
		}

		getModifyHyperLinkButton().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (HyperLinkTab.this.getTableViewer().getTable().getSelection().length != 0) {
					HyperLinkObject hyperLinkObject = (HyperLinkObject) ((IStructuredSelection) HyperLinkTab.this.getTableViewer().getSelection()).getFirstElement();
					HyperLinkTab.this.getHyperLinkHelper().executeEditMousePressed(hyperlinksTab.getControl().getShell(),
							HyperLinkTab.this.getHyperlinkObjects(), hyperLinkObject, HyperLinkTab.this.getElement());
					HyperLinkTab.this.setInput(HyperLinkTab.this.getHyperlinkObjects());
				}
			}
		});
	}

	private void addNewHyperLinkMenuActions(final Runnable addNewHyperlinkHandler) {
		PopupButtonMenu menu = new PopupButtonMenu(getNewHyperLinkbutton());

		for (final AbstractHyperLinkHelper helper : ((CompositeHyperlinkHelper) getHyperLinkHelper()).getHelpers()) {
			IAction action = new Action(helper.getNameofManagedHyperLink()) {

				@Override
				public void run() {
					((CompositeHyperlinkHelper) getHyperLinkHelper()).setActiveHelper(helper);
					addNewHyperlinkHandler.run();
				}
			};
			action.setToolTipText(NLS.bind(Messages.HyperLinkTab_addTooltip, helper.getNameofManagedHyperLink()));

			menu.addAction(action);
		}
	}

	private Button getNewHyperLinkbutton() {
		return newHyperLinkbutton;
	}

	protected boolean isCompositeHelper() {
		return getHyperLinkHelper() instanceof CompositeHyperlinkHelper;
	}

	private Button getModifyHyperLinkButton() {
		return modifyHyperLinkButton;
	}

	private Button getRemoveHyperLinkButton() {
		return removeHyperLinkButton;
	}

	private Button getUpHyperLinkButton() {
		return upHyperLinkButton;
	}

	private Button getDownHyperLinkButton() {
		return downHyperLinkButton;
	}

	/**
	 * set as input a list of hyperlinkObject to display
	 *
	 * @param hyperlinkObjects
	 *            the lis of hyperlinkobjects
	 */
	@Override
	public void setInput(List<HyperLinkObject> hyperlinkObjects) {
		if (!getTableViewer().getControl().isDisposed()) {
			setHyperlinkObjects(this.getHyperLinkHelper().getFilteredObject(hyperlinkObjects));
			getTableViewer().setInput(this.getHyperlinkObjects());
		} else {
			Activator.log.warn("This hyperlink tab has been disposed");
		}
	}
}

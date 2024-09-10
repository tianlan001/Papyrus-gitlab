/*****************************************************************************
 * Copyright (c) 2016, 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Patrick Tessier (CEA list) -
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.ui.architecture.widgets.ArchitectureContextComposite;
import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.papyrus.uml.diagram.wizards.utils.SettingsHelper;
import org.eclipse.papyrus.uml.diagram.wizards.wizards.CreateModelWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.ui.PlatformUI;

/**
 * @author melaasar
 *
 */
public class SelectArchitectureContextPage extends WizardPage {

	/** The Constant PAGE_ID. */
	public static final String PAGE_ID = "SelectArchitectureContextPage"; //$NON-NLS-1$

	private SettingsHelper settingsHelper;

	private final boolean allowSeveralContexts;

	private String[] selectedContexts;

	private String[] selectedViewpoints;

	public SelectArchitectureContextPage() {
		this(false);
	}

	public SelectArchitectureContextPage(boolean allowSeveralContexts) {
		super(PAGE_ID);
		setTitle("Select Architecture Context"); //$NON-NLS-1$
		setDescription("Select the architecture context(s) and viewpoints to apply to the Papyrus model"); //$NON-NLS-1$
		this.allowSeveralContexts = allowSeveralContexts;
	}

	@Override
	public void setWizard(IWizard newWizard) {
		super.setWizard(newWizard);
		settingsHelper = new SettingsHelper(getDialogSettings());

		List<String> contextIds = asList(settingsHelper.getArchitectureContexts());
		if (!allowSeveralContexts) {
			String defaultContextId = ArchitectureDomainManager.getInstance().getDefaultArchitectureContextId();
			if (defaultContextId != null) {
				contextIds = asList(defaultContextId);
			} else if (!contextIds.isEmpty()) {
				contextIds = asList(contextIds.get(0));
			} else {
				contextIds = Collections.emptyList();
			}
		}

		List<String> viewpoints = new ArrayList<>();
		for (Iterator<String> i = contextIds.iterator(); i.hasNext();) {
			String contextId = i.next();
			if (isVisibleContext(contextId)) {
				MergedArchitectureContext context = ArchitectureDomainManager.getInstance().getArchitectureContextById(contextId);
				for (MergedArchitectureViewpoint viewpoint : context.getViewpoints()) {
					viewpoints.add(viewpoint.getId());
				}
			} else {
				i.remove();
			}
		}

		selectedContexts = contextIds.toArray(new String[0]);
		selectedViewpoints = viewpoints.toArray(new String[0]);
	}

	private <T> List<T> asList(T... elements) {
		List<T> list = new ArrayList<>();
		for (T element : elements) {
			list.add(element);
		}
		return list;
	}

	private boolean isVisibleContext(String contextId) {
		if (contextId != null) {
			for (MergedArchitectureContext context : ArchitectureDomainManager.getInstance().getVisibleArchitectureContexts()) {

				if (context.getId() == null) {
					org.eclipse.papyrus.uml.diagram.wizards.Activator.log.info(" context has a null id" + context); //$NON-NLS-1$
				}
				if (contextId.equals(context.getId())) {
					return true;
				}

			}

		} else {
			org.eclipse.papyrus.uml.diagram.wizards.Activator.log.info(" a context has not been loaded"); //$NON-NLS-1$
		}
		return false;

	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent) {
		Composite comp = createComposite(parent, 1, 1, GridData.FILL_BOTH, 0, 0);
		((GridData) comp.getLayoutData()).widthHint = 350;
		createVerticalSpacer(comp, 1);
		setControl(comp);

		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		MergedArchitectureContext[] contexts;
		// if (allowSeveralContexts || manager.getDefaultArchitectureContext() == null)
		contexts = manager.getVisibleArchitectureContexts().toArray(new MergedArchitectureContext[0]);
		// else
		// contexts = new ArchitectureContext[] { manager.getDefaultArchitectureContext() };

		final ArchitectureContextComposite acc = new ArchitectureContextComposite(comp, 1, 1, GridData.FILL_BOTH, 0, 0);
		acc.setAllowSeveralContexts(allowSeveralContexts);
		acc.setSelectedContexts(selectedContexts);
		acc.setSelectedViewpoints(selectedViewpoints);
		acc.setInput(contexts);
		acc.setUpdater(new ArchitectureContextComposite.Updater() {
			@Override
			public void update() {
				selectedContexts = acc.getSelectedContexts();
				selectedViewpoints = acc.getSelectedViewpoints();
				updateButtons();
			}
		});
		updateButtons();
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.eclipse.papyrus.uml.diagram.wizards.Category"); //$NON-NLS-1$
	}

	/**
	 * Gets the selected architecture contexts.
	 *
	 * @return the architecture contexts
	 */
	public String[] getSelectedContexts() {
		return selectedContexts;
	}

	/**
	 * Respond to completion of the wizard. Includes saving settings for the next
	 * invocation of the wizard.
	 *
	 * @since 2.0
	 */
	public void performFinish() {
		settingsHelper.saveArchitectureContexts(selectedContexts);
	}

	public String[] getSelectContexts() {
		return selectedContexts;
	}

	public String[] getSelectViewpoints() {
		return selectedViewpoints;
	}

	private void updateButtons() {
		setPageComplete(selectedContexts.length != 0 && validatePage());
	}

	/**
	 * Validate page.
	 *
	 * @return true, if successful
	 */
	protected boolean validatePage() {
		setMessage(null);
		setErrorMessage(null);
		String[] categories = selectedContexts;
		if (categories == null || categories.length == 0) {
			setErrorMessage(Messages.SelectArchitectureContextPage_select_one_category);
			return false;
		}
		if (!validateFileExtension(categories)) {
			return false;
		}

		return true;
	}

	/**
	 * Validate file extension.
	 *
	 * @param categories
	 *            the categories
	 * @return true, if successful
	 */
	protected boolean validateFileExtension(String... contexts) {
		IStatus status = ((CreateModelWizard) getWizard()).architectureContextChanged(contexts);
		switch (status.getSeverity()) {
		case Status.WARNING:
			setMessage(status.getMessage(), IMessageProvider.WARNING);
			break;
		case IStatus.INFO:
			setMessage(status.getMessage(), IMessageProvider.INFORMATION);
			break;
		}
		return true;
	}

	private static Composite createComposite(Composite parent, int columns, int hspan, int fill, int marginwidth, int marginheight) {
		Composite g = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(columns, false);
		layout.marginWidth = marginwidth;
		layout.marginHeight = marginheight;
		g.setLayout(layout);
		g.setFont(parent.getFont());
		GridData gd = new GridData(fill);
		gd.horizontalSpan = hspan;
		g.setLayoutData(gd);
		return g;
	}

	private static void createVerticalSpacer(Composite parent, int numlines) {
		Label lbl = new Label(parent, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		Layout layout = parent.getLayout();
		if (layout instanceof GridLayout) {
			gd.horizontalSpan = ((GridLayout) parent.getLayout()).numColumns;
		}
		gd.heightHint = numlines;
		lbl.setLayoutData(gd);
	}

}

/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - move from oep.customization.palette
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Comparator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.messages.Messages;
import org.eclipse.pde.core.plugin.IPluginBase;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;


/**
 * This dialog allows user to browse the available plugins and select some
 */
public class BundleExplorerDialog extends FilteredItemsSelectionDialog {

	/**
	 * the empty string constant.
	 */
	protected static final String EMPTY = "";//$NON-NLS-1$ s

	/** dialogs settings */
	protected static final String DIALOG_SETTINGS = "org.eclipse.papyrus.infra.widgets.toolbox.BundleExplorerDialog";//$NON-NLS-1$

	/** the plugin model base. */
	protected IPluginModelBase[] fModels;

	/**
	 * Creates a new BundleExplorerDialog.
	 *
	 * @param shell
	 *            the parent shell for the dialog
	 * @param multi
	 *            <code>true</code> if multi selection is allowed
	 */
	public BundleExplorerDialog(final Shell shell, final boolean multi, final IPluginModelBase[] models) {
		super(shell, multi);
		setTitle(Messages.BundleExplorerDialog_PlugInSelectionTitle);
		setMessage(Messages.BundleExplorerDialog_DialogMessage);
		fModels = models;
		setListLabelProvider(new LabelProvider());
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected Control createExtendedContentArea(Composite parent) {
		return null;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected ItemsFilter createFilter() {
		return new PluginSearchItemsFilter();
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected void fillContentProvider(final AbstractContentProvider contentProvider, final ItemsFilter itemsFilter, final IProgressMonitor progressMonitor) throws CoreException {
		for (int i = 0; i < fModels.length; i++) {
			contentProvider.add(fModels[i], itemsFilter);
			progressMonitor.worked(1);
		}
		progressMonitor.done();
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);

		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}

		return settings;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	public String getElementName(final Object item) {
		if (item instanceof IPluginModelBase) {
			IPluginModelBase model = (IPluginModelBase) item;
			return model.getPluginBase().getId();
		}
		return null;
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected Comparator<?> getItemsComparator() {
		return new PluginSearchComparator();
	}

	/**
	 * @{inheritDoc
	 */
	@Override
	protected IStatus validateItem(Object item) {
		return new Status(IStatus.OK, Activator.PLUGIN_ID, 0, EMPTY, null); // $NON-NLS-1$
	}

	private class PluginSearchItemsFilter extends ItemsFilter {

		/**
		 * The dot constant.
		 */
		private static final String DOT = "."; //$NON-NLS-1$
		/**
		 * The interrogation point constant.
		 */
		private static final String INTERROGATION = "?"; //$NON-NLS-1$
		/**
		 * The asterisk constant.
		 */
		private static final String ASTERISK = "*"; //$NON-NLS-1$

		@Override
		public boolean isConsistentItem(final Object item) {
			return true;
		}

		@Override
		public boolean matchItem(final Object item) {
			String id = null;
			if (item instanceof IPluginModelBase) {
				IPluginModelBase model = (IPluginModelBase) item;
				id = model.getPluginBase().getId();
			}

			return (matches(id));
		}

		@Override
		protected boolean matches(final String text) {
			String pattern = patternMatcher.getPattern();
			if (pattern.indexOf(ASTERISK) != 0 && pattern.indexOf(INTERROGATION) != 0 && pattern.indexOf(DOT) != 0) {// $NON-NLS-1$
				pattern = ASTERISK + pattern;
				patternMatcher.setPattern(pattern);
			}
			return patternMatcher.matches(text);
		}
	}

	private class PluginSearchComparator implements Comparator {

		@Override
		public int compare(final Object o1, final Object o2) {
			int id1 = getId(o1);
			int id2 = getId(o2);

			return id1 != id2 ? id1 - id2 : compareSimilarObjects(o1, o2);
		}

		private int getId(final Object element) {
			return element instanceof IPluginModelBase ? 100 : 0;
		}

		private int compareSimilarObjects(final Object o1, final Object o2) {
			if (o1 instanceof IPluginModelBase && o2 instanceof IPluginModelBase) {
				IPluginModelBase ipmb1 = (IPluginModelBase) o1;
				IPluginModelBase ipmb2 = (IPluginModelBase) o2;
				return comparePlugins(ipmb1.getPluginBase(), ipmb2.getPluginBase());
			}
			return 0;
		}

		private int comparePlugins(final IPluginBase ipmb1, final IPluginBase ipmb2) {
			return ipmb1.getId().compareTo(ipmb2.getId());
		}

	}

}

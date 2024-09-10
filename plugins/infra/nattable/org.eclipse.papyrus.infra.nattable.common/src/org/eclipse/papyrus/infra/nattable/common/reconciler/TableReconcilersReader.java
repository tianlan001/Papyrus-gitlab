/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.reconciler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.papyrus.infra.nattable.common.Activator;

/**
 * A reader of table reconcilers from the extensions
 * @since 3.0
 */
public class TableReconcilersReader extends RegistryReader {

	private static volatile TableReconcilersReader ourInstance = null;

	private static final String EXT_PT = "tableReconciler"; //$NON-NLS-1$

	private static final String TAG_TABLE_RECONCILER = "tableReconciler"; //$NON-NLS-1$

	private List<TableReconciler> myReconcilers;

	public static final TableReconcilersReader getInstance() {
		if (ourInstance == null) {
			synchronized (TableReconcilersReader.class) {
				if (ourInstance == null) {
					ourInstance = new TableReconcilersReader();
				}
			}
		}
		return ourInstance;
	}

	TableReconcilersReader() {
		super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_PT);
	}

	/**
	 * @return unmodifiable map of loaded reconcilers
	 */
	public synchronized List<TableReconciler> load() {
		if (myReconcilers == null) {
			myReconcilers = new ArrayList<TableReconciler>();
			readRegistry();
		}
		return myReconcilers;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.emf.ecore.plugin.RegistryReader#readElement(org.eclipse.core.runtime.IConfigurationElement, boolean)
	 */
	@Override
	protected boolean readElement(IConfigurationElement element, boolean add) {
		if (!TAG_TABLE_RECONCILER.equals(element.getName())) {
			return false;
		}

		String className = element.getAttribute(TableReconciler.ATTR_RECONCILER_CLASS);
		String sourceVersion = element.getAttribute(TableReconciler.ATTR_SOURCE_VERSION);
		String targetVersion = element.getAttribute(TableReconciler.ATTR_TARGET_VERSION);

		if (!checkNotEmpty(className)) {
			logMissingAttribute(element, TableReconciler.ATTR_RECONCILER_CLASS);
			return false;
		}
		if (!checkNotEmpty(sourceVersion)) {
			logMissingAttribute(element, TableReconciler.ATTR_SOURCE_VERSION);
			return false;
		}

		if (!checkNotEmpty(targetVersion)) {
			logMissingAttribute(element, TableReconciler.ATTR_TARGET_VERSION);
			return false;
		}
		if (!TableVersioningUtils.isCurrentPapyrusVersion(targetVersion)) {
			Activator.log.debug("Reconciler for outdated version is still registered but will never be executed: " + className); //$NON-NLS-1$
			return false;
		}


		if (add) {
			addTableReconciler(element);
		} else {
			removeTableReconciler(element);
		}

		return true;
	}

	private static boolean checkNotEmpty(String attr) {
		return (attr != null) && (attr.length() != 0);
	}

	protected void addTableReconciler(IConfigurationElement element) {
		TableReconciler reconciler = createReconciler(element);
		if (reconciler != null) {
			synchronized (myReconcilers) {
				myReconcilers.add(reconciler);
			}
		}
	}

	/**
	 * Unregisters reconciler defined by given extension element.
	 * <p/>
	 * Only table type and fully qualified class name will be used to find the one instance to remove.
	 */
	protected void removeTableReconciler(IConfigurationElement element) {
		String fqn = element.getAttribute(TableReconciler.ATTR_RECONCILER_CLASS);
		if (fqn == null) {
			// we already have skipped this config at the time of addition
			return;
		}

		synchronized (myReconcilers) {
			for (Iterator<TableReconciler> it = myReconcilers.iterator(); it.hasNext();) {
				TableReconciler next = it.next();
				if (fqn.equals(next.getClassFqn())) {
					it.remove();
					break;
				}
			}
		}
	}

	/**
	 * Instantiates the reconciler defined by given extension
	 *
	 * @param element
	 * @return configured reconciler instance or <code>null</code> if something bad happens (error is logged in this case)
	 */
	private TableReconciler createReconciler(IConfigurationElement element) {
		try {
			Object reconcilerObject = element.createExecutableExtension(TableReconciler.ATTR_RECONCILER_CLASS);
			if (reconcilerObject instanceof TableReconciler) {
				return (TableReconciler) reconcilerObject;
			} else {
				Activator.log.error("Table reconciler extension does not extend mandatory TableReconciler base class: " + element.getAttribute(TableReconciler.ATTR_RECONCILER_CLASS), null); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			Activator.log.error(e);
		}
		return null;
	}

}

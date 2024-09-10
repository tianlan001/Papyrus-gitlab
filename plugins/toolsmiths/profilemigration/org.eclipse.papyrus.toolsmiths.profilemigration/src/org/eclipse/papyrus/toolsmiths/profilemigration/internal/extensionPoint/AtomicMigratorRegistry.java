/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.extensionPoint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.Activator;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.IAtomicMigratorManager;
import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;

/**
 * This is a registry for the extension point org.eclipse.papyrus.profile.migration.AtomicMigrators
 */
public class AtomicMigratorRegistry {

	public static final AtomicMigratorRegistry INSTANCE = new AtomicMigratorRegistry();

	private static final String EXTPT_ID = Activator.PLUGIN_ID + ".AtomicMigrators"; //$NON-NLS-1$

	private static final String ATOMIC_MIGRATOR_CLASS_PROPERTY = "class"; //$NON-NLS-1$

	private static final String ERASED_MIGRATOR_ELEMENT_NAME = "erasedMigrator"; //$NON-NLS-1$

	private static final String ERASED_MIGRATOR_CLASS_PROPERTY = "class"; //$NON-NLS-1$

	/**
	 * The list of descriptor corresponding to every AtomicMigrator registered
	 */
	private List<Descriptor> registry = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 */
	public AtomicMigratorRegistry() {
		for (IConfigurationElement config : Platform.getExtensionRegistry().getConfigurationElementsFor(EXTPT_ID)) {
			registry.add(new Descriptor(config));
		}
	}

	/**
	 * Get the registry
	 * 
	 * @return the registry
	 */
	public List<Descriptor> getRegistry() {
		return registry;
	}

	/**
	 * Descriptor for AtomicMigrator extension point
	 */
	public class Descriptor {

		/**
		 * An instance of the IAtomicMigratorManager
		 */
		private IAtomicMigratorManager instance;

		/**
		 * The list of AtomicMigrator erased by this migrator
		 */
		private List<String> erasedMigrators = new ArrayList<>();

		/**
		 * Constructor.
		 *
		 * @param config
		 *            the configuration corresponding to the AtomicMigrator extension point
		 */
		public Descriptor(IConfigurationElement config) {
			initInstance(config);
			initReplacements(config);
		}

		/**
		 * Initialize the instance according to the configuration
		 * 
		 * @param config
		 *            the configuration corresponding to the extension point
		 * @return the new instance
		 */
		private IAtomicMigratorManager initInstance(IConfigurationElement config) {
			if (instance == null) {
				try {
					instance = (IAtomicMigratorManager) config.createExecutableExtension(ATOMIC_MIGRATOR_CLASS_PROPERTY);
				} catch (ClassCastException e) {
					Activator.log.error("Atomic migrator does not implement IAtomicMigratorManager interface.", e); //$NON-NLS-1$
				} catch (Exception e) {
					Activator.log.error("Could not instantiate storage provider.", e); //$NON-NLS-1$
				}

				if (instance == null) {
					instance = new NullAtomicMigratorManager();
				}
			}

			return instance;
		}

		/**
		 * Initialize the list of erasedMigrator
		 * 
		 * @param config
		 *            the configuration corresponding to the extension point
		 */
		private void initReplacements(IConfigurationElement config) {
			IConfigurationElement[] test = config.getChildren(ERASED_MIGRATOR_ELEMENT_NAME);
			for (IConfigurationElement replacementConfig : test) {
				String className = replacementConfig.getAttribute(ERASED_MIGRATOR_CLASS_PROPERTY);
				if (className != null) {
					erasedMigrators.add(className);
				}
			}
		}

		/**
		 * Get the instance
		 * 
		 * @return the instance
		 */
		public IAtomicMigratorManager getInstance() {
			return instance;
		}

		/**
		 * Get the list of erased migrators
		 * 
		 * @return the erasedMigrators
		 */
		public List<String> getErasedMigrators() {
			return erasedMigrators;
		}

	}

	/**
	 * Default IAtomicMigratorManager in case of it is not possible to instantiate the manager
	 */
	public class NullAtomicMigratorManager implements IAtomicMigratorManager {

		/**
		 * @see org.eclipse.papyrus.toolsmiths.profilemigration.migrators.IAtomicMigratorManager#instantiate(org.eclipse.emf.edit.tree.TreeNode)
		 *
		 * @param treeNode
		 * @return
		 */
		@Override
		public IAtomicMigrator instantiate(TreeNode treeNode) {
			return null;
		}


	}

}

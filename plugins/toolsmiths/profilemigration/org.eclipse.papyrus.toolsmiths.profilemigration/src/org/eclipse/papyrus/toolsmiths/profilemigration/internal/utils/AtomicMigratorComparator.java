/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils;

import java.util.Comparator;

import org.eclipse.papyrus.toolsmiths.profilemigration.migrators.atomic.IAtomicMigrator;

/**
 * A comparator for IAtomitMigrators, compare according to the priority
 */
public class AtomicMigratorComparator implements Comparator<IAtomicMigrator> {

	/**
	 * Compare according to the priority
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 *
	 */
	@Override
	public int compare(IAtomicMigrator o1, IAtomicMigrator o2) {
		if (o1.getPriority() == o2.getPriority()) {
			// if it is the same priority we sort by name (to be sure that every migrator of the same type are grouped)
			if (o1.getClass().getName().compareTo(o2.getClass().getName()) > 0) {
				return 1;
			} else if (o1.getClass().getName().compareTo(o2.getClass().getName()) < 0) {
				return -1;
			} else {
				return 0;
			}
		}
		return o1.getPriority() > o2.getPriority() ? -1 : 1;
	}

}

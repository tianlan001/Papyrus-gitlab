/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.common.internal.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * interface to use to define the table comand creation in the architecture file
 * This class is in an internal package, because this API needs enhancement
 *
 * @since 5.5
 */
public interface ITableCreationCommand {


	/**
	 *
	 * @param modelSet
	 * @param owner
	 * @param element
	 * @param prototype
	 * @param name
	 * @param openTable
	 * @return
	 */
	Table createTable(ModelSet modelSet, EObject owner, EObject element, TableViewPrototype prototype, String name, boolean openTable);
}

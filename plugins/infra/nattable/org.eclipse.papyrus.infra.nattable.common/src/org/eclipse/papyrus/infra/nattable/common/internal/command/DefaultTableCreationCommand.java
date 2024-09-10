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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.common.internal.command;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.nattable.common.handlers.PolicyDefinedTableHandler;
import org.eclipse.papyrus.infra.nattable.common.helper.TableViewPrototype;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 * Default command creation for table
 * This class is in an internal package, because this API needs enhancement
 *
 * @since 5.5
 */
public final class DefaultTableCreationCommand implements ITableCreationCommand {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.common.internal.command.ITableCreationCommand#createTable(org.eclipse.papyrus.infra.core.resource.ModelSet, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject,
	 *      org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype, java.lang.String, boolean)
	 *
	 * @param modelSet
	 * @param owner
	 * @param element
	 * @param prototype
	 * @param name
	 * @param openTable
	 * @return
	 */
	@Override
	public Table createTable(final ModelSet modelSet, final EObject owner, final EObject element, final TableViewPrototype prototype, final String name, boolean openTable) {
		PolicyDefinedTableHandler handler = new PolicyDefinedTableHandler(prototype.getTableConfiguration(), owner, name);
		handler.execute(prototype);
		return null;
	}

}

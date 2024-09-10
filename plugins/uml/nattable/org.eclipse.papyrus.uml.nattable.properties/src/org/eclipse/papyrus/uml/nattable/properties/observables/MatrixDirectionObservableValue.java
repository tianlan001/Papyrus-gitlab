/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.properties.observables;

import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecelleditor.NattablecelleditorPackage;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;


/**
 * Observable for the direction of the relationships to create and display feature
 */
public class MatrixDirectionObservableValue extends AbstractMatrixRelationshipCellEditorConfigurationObservableValue {

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            The current editing domain.
	 * @param table
	 *            The managed table.
	 */
	public MatrixDirectionObservableValue(final Table table) {
		super(TableEditingDomainUtils.getTableEditingDomain(table), table, NattablecelleditorPackage.eINSTANCE.getGenericRelationshipMatrixCellEditorConfiguration_Direction());
	}

}

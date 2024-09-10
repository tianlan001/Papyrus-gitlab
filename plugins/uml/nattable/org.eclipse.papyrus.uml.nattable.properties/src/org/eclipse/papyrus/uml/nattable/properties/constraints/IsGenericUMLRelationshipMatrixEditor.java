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
package org.eclipse.papyrus.uml.nattable.properties.constraints;

import org.eclipse.papyrus.infra.constraints.constraints.Constraint;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.properties.constraints.IsEObjectInFlatTableConstraint;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.uml.nattable.properties.utils.MatrixHelper;

/**
 * The constraint which allow to determinate if the Matrix tab can be displayed
 * We only show the matrix tab when the selected Table is opened in the current editor.
 * It is to avoid trouble with GlazedList during refresh when the user edit the row sources fields
 * 
 * We forbid to display the Matrix tab for closed table
 */
public class IsGenericUMLRelationshipMatrixEditor extends IsEObjectInFlatTableConstraint {

	/**
	 * @see org.eclipse.papyrus.infra.nattable.properties.constraints.IsEObjectInFlatTableConstraint#checkMoreTableConstraint(org.eclipse.papyrus.infra.nattable.model.nattable.Table)
	 *
	 * @param table
	 * @return
	 */
	@Override
	protected boolean checkMoreTableConstraint(Table table) {
		return TableHelper.isMatrixTreeTable(table) && null != MatrixHelper.getMatrixTableWidgetModelManagerFromCurrentEditor();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.constraints.constraints.AbstractConstraint#equivalent(org.eclipse.papyrus.infra.constraints.constraints.Constraint)
	 */
	@Override
	protected boolean equivalent(final Constraint constraint) {
		return constraint == this || constraint instanceof IsGenericUMLRelationshipMatrixEditor;
	}
}

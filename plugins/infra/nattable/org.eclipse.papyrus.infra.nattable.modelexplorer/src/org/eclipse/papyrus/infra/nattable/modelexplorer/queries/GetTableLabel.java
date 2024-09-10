/**
 *  Copyright (c) 2016 CEA LIST, and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *    Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *    Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 516471
 */
package org.eclipse.papyrus.infra.nattable.modelexplorer.queries;

import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalization;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.provider.NattableEditPlugin;
import org.eclipse.papyrus.infra.ui.editorsfactory.AbstractGetEditorIconQuery;

/**
 * Return the label of the corresponding table.
 * @since 3.0
 */
public class GetTableLabel extends AbstractGetEditorIconQuery implements IJavaQuery2<Table, String> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2#evaluate(org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2, org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager)
	 */
	public String evaluate(final Table source, final IParameterValueList2 parameterValues, final IFacetManager facetManager) throws DerivedTypedElementException {
		final String label = LabelInternationalization.getInstance().getTableLabel(source);
		
		/*
		bug 516471 : we must not display the EClass name
		return label == null || label.length() == 0 ? NattableEditPlugin.INSTANCE.getString("_UI_Table_type") : //$NON-NLS-1$
				NattableEditPlugin.INSTANCE.getString("_UI_Table_type") + " " + label; //$NON-NLS-1$ //$NON-NLS-2$
		*/
 
		return label == null || label.length() == 0 ? NattableEditPlugin.INSTANCE.getString("_UI_Table_type") : label; //$NON-NLS-1$
	
	}
}

/**
 *  Copyright (c) 2011 Atos.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Atos - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.nattable.modelexplorer.queries;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetReference;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.ParameterValue;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.viewpoints.policy.NotationUtils;

public class DisplayTablesRef implements IJavaQuery2<EObject, Boolean> {

	/**
	 * Return true if the element is a Table Container and the the Ereference is diagrams
	 */

	@Override
	public Boolean evaluate(EObject source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		ParameterValue parameterValue = parameterValues.getParameterValueByName("eStructuralFeature");
		EStructuralFeature eStructuralFeature = (EStructuralFeature) parameterValue.getValue();
		if ((eStructuralFeature instanceof FacetReference) && ("tables".equals((eStructuralFeature).getName()))) {

			Iterator<EObject> roots = NotationUtils.getNotationRoots(source);
			if (roots == null) {
				return false;
			}

			while (roots.hasNext()) {
				EObject root = roots.next();
				if (root instanceof Table) {
					if (EcoreUtil.equals(((Table) root).getOwner(), source)) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}
}
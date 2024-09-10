/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.modelexplorer.queries;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.views.modelexplorer.queries.GetVisibleReferencesQuery;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @author Camille Letavernier
 *
 */
public class GetVisibleUMLReferencesQuery extends GetVisibleReferencesQuery {

	private static final Set<EReference> excludedReferences = getExcludedReferences();

	private static final Set<EReference> getExcludedReferences() {
		Set<EReference> result = new HashSet<>();
		result.add(UMLPackage.Literals.MULTIPLICITY_ELEMENT__LOWER_VALUE);
		result.add(UMLPackage.Literals.MULTIPLICITY_ELEMENT__UPPER_VALUE);
		result.add(UMLPackage.Literals.PACKAGE__PROFILE_APPLICATION);
		result.add(UMLPackage.Literals.BEHAVIOR__POSTCONDITION);
		result.add(UMLPackage.Literals.BEHAVIOR__PRECONDITION);
		result.add(UMLPackage.Literals.OPERATION__BODY_CONDITION);
		return result;
	}

	/**
	 * @see org.eclipse.papyrus.views.modelexplorer.queries.GetVisibleReferencesQuery#evaluate(org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2, org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager)
	 *
	 * @param source
	 * @param parameterValues
	 * @param facetManager
	 * @return
	 * @throws DerivedTypedElementException
	 */
	@Override
	public List<EReference> evaluate(EObject source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		List<EReference> result = new ArrayList<EReference>(super.evaluate(source, parameterValues, facetManager));
		if (source instanceof PackageImport) {
			result.add(UMLPackage.Literals.PACKAGE_IMPORT__IMPORTED_PACKAGE);
		} else if (source instanceof ElementImport) {
			result.add(UMLPackage.Literals.ELEMENT_IMPORT__IMPORTED_ELEMENT);
		}
		result.removeAll(excludedReferences);
		return result;
	}
}

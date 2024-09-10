/*****************************************************************************
 * Copyright (c) 2016, 2021 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bugs 573987, 573986
 *
 *****************************************************************************/

package org.eclipse.papyrus.views.properties.toolsmiths.query;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.properties.contexts.Annotatable;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextsSwitch;
import org.eclipse.papyrus.infra.properties.ui.UiPackage;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.ToggleAnnotationsAction;

/**
 * @author Camille Letavernier
 *
 */
public class GetVisibleFeaturesQuery implements IJavaQuery2<EObject, List<EReference>> {

	static Set<EReference> allExcludedReferences = new HashSet<>();

	static {
		allExcludedReferences.add(ConstraintsPackage.Literals.DISPLAY_UNIT__CONSTRAINTS);
		allExcludedReferences.add(EcorePackage.Literals.EMODEL_ELEMENT__EANNOTATIONS);

		allExcludedReferences.add(ContextsPackage.Literals.CONTEXT__DEPENDENCIES);
		allExcludedReferences.add(ContextsPackage.Literals.DATA_CONTEXT_ROOT__MODEL_ELEMENT_FACTORY);
		allExcludedReferences.add(ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__PACKAGE);
		allExcludedReferences.add(ContextsPackage.Literals.DATA_CONTEXT_ELEMENT__SUPERTYPES);
		allExcludedReferences.add(ContextsPackage.Literals.PROPERTY__CONTEXT_ELEMENT);
		allExcludedReferences.add(ContextsPackage.Literals.ABSTRACT_SECTION__TAB);
		allExcludedReferences.add(ContextsPackage.Literals.SECTION__OWNER);
		allExcludedReferences.add(ContextsPackage.Literals.SECTION__VIEWS);
		allExcludedReferences.add(ContextsPackage.Literals.TAB__AFTER_TAB);
		allExcludedReferences.add(ContextsPackage.Literals.TAB__SECTIONS);
		allExcludedReferences.add(ContextsPackage.Literals.TAB__ALL_SECTIONS);
		allExcludedReferences.add(ContextsPackage.Literals.VIEW__CONTEXT);
		allExcludedReferences.add(ContextsPackage.Literals.VIEW__DATACONTEXTS);
		allExcludedReferences.add(ContextsPackage.Literals.ANNOTATION__ELEMENT);
		allExcludedReferences.add(ContextsPackage.Literals.ANNOTATION__REFERENCES);

		allExcludedReferences.add(UiPackage.Literals.COMPOSITE_WIDGET__WIDGET_TYPE);
		allExcludedReferences.add(UiPackage.Literals.UI_COMPONENT__ATTRIBUTES);
		allExcludedReferences.add(UiPackage.Literals.LAYOUT__LAYOUT_TYPE);
		allExcludedReferences.add(UiPackage.Literals.PROPERTY_EDITOR__PROPERTY);
		allExcludedReferences.add(UiPackage.Literals.PROPERTY_EDITOR__WIDGET_TYPE);
		allExcludedReferences.add(UiPackage.Literals.PROPERTY_EDITOR__UNRESOLVED_PROPERTY);
		allExcludedReferences.add(UiPackage.Literals.STANDARD_WIDGET__WIDGET_TYPE);
	}

	/**
	 * @see org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2#evaluate(org.eclipse.emf.ecore.EObject, org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2, org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager)
	 *
	 * @param source
	 * @param parameterValues
	 * @param facetManager
	 * @return
	 * @throws DerivedTypedElementException
	 */
	@Override
	public List<EReference> evaluate(EObject source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		if (source.eClass().getEPackage() == ContextsPackage.eINSTANCE) {
			return new ContextsSwitch<List<EReference>>() {
				@Override
				public List<EReference> caseContext(Context object) {
					return filter(object.eClass(), filterContextReferences(object));
				}

				@Override
				public List<EReference> caseAnnotatable(Annotatable object) {
					return filter(object.eClass(), filterAnnotationReferences(object));
				}

				@Override
				public java.util.List<EReference> defaultCase(EObject object) {
					return filter(object.eClass(), ref -> true);
				};

			}.doSwitch(source);
		} else if (source.eClass().getEPackage() == UiPackage.eINSTANCE) {
			return filter(source.eClass(), ref -> true);
		}

		return Collections.emptyList();
	}

	protected Predicate<EReference> filterContextReferences(Context object) {
		return ref -> {
			if (ref == ContextsPackage.Literals.CONTEXT__PROTOTYPE) { // Show the Prototype reference only if it is set
				return object.getPrototype() != null;
			} else if (ref == ContextsPackage.Literals.ANNOTATABLE__ANNOTATIONS) {
				return ToggleAnnotationsAction.showAnnotations;
			}
			return true;
		};
	}

	protected Predicate<EReference> filterAnnotationReferences(Annotatable object) {
		return ref -> {
			if (ref == ContextsPackage.Literals.ANNOTATABLE__ANNOTATIONS) {
				return ToggleAnnotationsAction.showAnnotations;
			}
			return true;
		};
	}

	private List<EReference> filter(EClass eClass, Predicate<EReference> filter) {
		return eClass.getEAllReferences().stream()
				.filter(ref -> !allExcludedReferences.contains(ref))
				.filter(filter)
				.collect(Collectors.toList());
	}

}

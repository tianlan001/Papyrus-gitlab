/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.trace;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.customization.properties.generation.layout.ILayoutGenerator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;

/**
 * Protocol for a helper that infers the source model element of a properties element
 * for properties elements that do not have explicit source annotations.
 */
public interface SourceTraceHelper {

	int DEFAULT_MULTIPLICITY = (int) ConstraintsPackage.Literals.DISPLAY_UNIT__ELEMENT_MULTIPLICITY.getDefaultValue();;

	EObject getSourceElement(EObject propertiesElement);

	List<? extends EObject> getNestedPackages(EObject sourcePackage);

	List<? extends EObject> getClasses(EObject sourcePackage);

	List<? extends EObject> getProperties(EObject sourceClass);

	boolean isPropertyRedefinition(EObject sourceProperty);

	List<? extends EObject> getSuperclasses(EObject sourceClass);

	ConstraintDescriptor createInstanceOfConstraint(EObject sourceClass);

	boolean isInstanceOfConstraint(ConstraintDescriptor constraint);

	EObject resolveInstanceOfConstraintClass(ConstraintDescriptor constraint);

	/**
	 * Extract the class name referenced by an <em>Instance Of</em> constraint.
	 *
	 * @param instanceOfConstraint
	 *            a constraint recognized as an <em>Instance Of</em> by the
	 *            {@link #isInstanceOfConstraint(ConstraintDescriptor)} API
	 *
	 * @return referenced class name
	 */
	String getClassName(ConstraintDescriptor instanceOfConstraint);

	/**
	 * Query the property of an <em>Instance Of</em> constraint that specifies the class name.
	 *
	 * @param instanceOfConstraint
	 *            a constraint recognized as an <em>Instance Of</em> by the
	 *            {@link #isInstanceOfConstraint(ConstraintDescriptor)} API
	 *
	 * @return the class name property
	 */
	ValueProperty getClassNameProperty(ConstraintDescriptor instanceOfConstraint);

	/**
	 * Given the source class that the owner of a constraint traces to, resolve the
	 * class or classes that is/are valid source(s) according to the semantics of
	 * the constraint. This accounts, for example, for metaclass extensions of UML
	 * stereotypes (in which case the use must choose one to reference in the
	 * constraint from multiple quick fixes offered).
	 *
	 * @param instanceOfConstraint
	 *            a constraint recognized as an <em>Instance Of</em> by the
	 *            {@link #isInstanceOfConstraint(ConstraintDescriptor)} API
	 * @param sourceClass
	 *            the traced source class of the constraint's owner
	 * @return the resolved source class according to the semantics of the constraint
	 */
	default Collection<? extends EObject> getValidConstraintSourceClasses(ConstraintDescriptor instanceOfConstraint, EObject sourceClass) {
		return List.of(sourceClass);
	}

	/**
	 * Create a dummy generator for the given source model class that is used to satisfies the requirements
	 * of an {@link ILayoutGenerator} that is used to generate the properties editor layout for the class.
	 *
	 * @param sourceClass
	 *            a class in the source model
	 * @return a generator that is minimally configured to support the {@link ILayoutGenerator}
	 */
	IGenerator createGenerator(EObject sourceClass);

	/**
	 * Get the {@linkplain NameKind#SIMPLE simple name} of a source model element.
	 *
	 * @param sourceElement
	 *            a source model element
	 * @return its simple name
	 */
	default String getName(EObject sourceElement) {
		return getName(sourceElement, NameKind.SIMPLE);
	}

	String getName(EObject sourceElement, NameKind kind);

	int getMultiplicity(EObject sourceProperty);

	default boolean isViewOf(View view, DataContextElement element) {
		PropertiesCache cache = PropertiesCache.getInstance(view);
		return view.getDatacontexts().contains(element)
				|| view.getSections().stream().anyMatch(cache.getSections(element)::contains);
	}

	default boolean isSectionFor(Section section, DataContextElement element) {
		boolean result = false;

		CompositeWidget widget = section.getWidget();
		if (widget != null && !widget.eIsProxy()) {
			result = Iterators2.stream(Iterators2.filter(widget.eAllContents(), PropertyEditor.class))
					.map(pe -> pe.getProperty())
					.filter(Objects::nonNull)
					.map(Property::getContextElement)
					.anyMatch(Predicate.isEqual(element));
		}

		return result;
	}

}

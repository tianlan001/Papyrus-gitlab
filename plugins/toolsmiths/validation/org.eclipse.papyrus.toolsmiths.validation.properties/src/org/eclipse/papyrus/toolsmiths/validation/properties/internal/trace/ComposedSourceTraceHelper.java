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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;

/**
 * A source trace helper that is composed of other helpers to which it delegates
 * in turn until it gets a result.
 */
public class ComposedSourceTraceHelper implements SourceTraceHelper {

	private final List<SourceTraceHelper> delegates;

	public ComposedSourceTraceHelper() {
		super();

		delegates = List.of(new ExplicitSourceTraceHelper(),
				new ImplicitUMLSourceTraceHelper(),
				new ImplicitEcoreSourceTraceHelper());
	}

	@Override
	public EObject getSourceElement(EObject propertiesElement) {
		return iterate(propertiesElement, null, SourceTraceHelper::getSourceElement);
	}

	@Override
	public List<? extends EObject> getNestedPackages(EObject sourcePackage) {
		return iterate(sourcePackage, List.of(), SourceTraceHelper::getNestedPackages);
	}

	@Override
	public List<? extends EObject> getClasses(EObject sourcePackage) {
		return iterate(sourcePackage, List.of(), SourceTraceHelper::getClasses);
	}

	@Override
	public List<? extends EObject> getProperties(EObject sourceClass) {
		return iterate(sourceClass, List.of(), SourceTraceHelper::getProperties);
	}

	@Override
	public boolean isPropertyRedefinition(EObject sourceProperty) {
		return iterate(sourceProperty, false, SourceTraceHelper::isPropertyRedefinition);
	}

	@Override
	public List<? extends EObject> getSuperclasses(EObject sourceClass) {
		return iterate(sourceClass, List.of(), SourceTraceHelper::getSuperclasses);
	}

	@Override
	public ConstraintDescriptor createInstanceOfConstraint(EObject sourceClass) {
		return iterate(sourceClass, null, SourceTraceHelper::createInstanceOfConstraint);
	}

	@Override
	public boolean isInstanceOfConstraint(ConstraintDescriptor constraint) {
		return iterate(constraint, false, SourceTraceHelper::isInstanceOfConstraint);
	}

	@Override
	public EObject resolveInstanceOfConstraintClass(ConstraintDescriptor constraint) {
		return iterate(constraint, null, SourceTraceHelper::resolveInstanceOfConstraintClass);
	}

	@Override
	public String getClassName(ConstraintDescriptor instanceOfConstraint) {
		return iterate(instanceOfConstraint, null, SourceTraceHelper::getClassName);
	}

	@Override
	public ValueProperty getClassNameProperty(ConstraintDescriptor instanceOfConstraint) {
		return iterate(instanceOfConstraint, null, SourceTraceHelper::getClassNameProperty);
	}

	@Override
	public Collection<? extends EObject> getValidConstraintSourceClasses(ConstraintDescriptor instanceOfConstraint, EObject sourceClass) {
		return iterate(instanceOfConstraint, List.of(sourceClass), (h, c) -> h.getValidConstraintSourceClasses(c, sourceClass));
	}

	@Override
	public IGenerator createGenerator(EObject sourceClass) {
		return iterate(sourceClass, null, SourceTraceHelper::createGenerator);
	}

	@Override
	public String getName(EObject sourceElement, NameKind kind) {
		return iterate(sourceElement, null, (h, e) -> h.getName(e, kind));
	}

	@Override
	public int getMultiplicity(EObject sourceProperty) {
		return iterate(sourceProperty, DEFAULT_MULTIPLICITY, SourceTraceHelper::getMultiplicity);
	}

	@Override
	public boolean isViewOf(View view, DataContextElement element) {
		return iterate(view, false, (helper, v) -> helper.isViewOf(v, element));
	}

	@Override
	public boolean isSectionFor(Section section, DataContextElement element) {
		return iterate(section, false, (helper, s) -> helper.isSectionFor(s, element));
	}

	private <T, R> R iterate(T input, R defaultResult, BiFunction<SourceTraceHelper, ? super T, ? extends R> function) {
		R result = null;

		for (Iterator<SourceTraceHelper> iter = delegates.iterator(); (result == null || Objects.equals(result, defaultResult)) && iter.hasNext();) {
			result = function.apply(iter.next(), input);
		}

		return result != null ? result : defaultResult;
	}

}

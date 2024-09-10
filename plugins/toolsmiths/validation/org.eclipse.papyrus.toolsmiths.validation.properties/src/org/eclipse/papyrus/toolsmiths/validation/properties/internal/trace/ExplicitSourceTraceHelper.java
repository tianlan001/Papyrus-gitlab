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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.properties.contexts.Annotatable;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextAnnotations;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextsSwitch;

/**
 * Source trace helper that uses the explicit source reference in the annotation.
 */
class ExplicitSourceTraceHelper extends ComposedSwitch<EObject> implements SourceTraceHelper {

	ExplicitSourceTraceHelper() {
		super();

		addSwitch(createContextsSwitch());
	}

	@Override
	public EObject getSourceElement(EObject propertiesElement) {
		return doSwitch(propertiesElement);
	}

	@Override
	public List<? extends EObject> getNestedPackages(EObject sourcePackage) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public List<? extends EObject> getClasses(EObject sourcePackage) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public List<? extends EObject> getProperties(EObject sourceClass) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public boolean isPropertyRedefinition(EObject sourceProperty) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return false;
	}

	@Override
	public List<? extends EObject> getSuperclasses(EObject sourceClass) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public ConstraintDescriptor createInstanceOfConstraint(EObject sourceClass) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public boolean isInstanceOfConstraint(ConstraintDescriptor constraint) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return false;
	}

	@Override
	public String getClassName(ConstraintDescriptor instanceOfConstraint) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public ValueProperty getClassNameProperty(ConstraintDescriptor instanceOfConstraint) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public EObject resolveInstanceOfConstraintClass(ConstraintDescriptor constraint) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public IGenerator createGenerator(EObject sourceClass) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public String getName(EObject sourceElement, NameKind kind) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return null;
	}

	@Override
	public int getMultiplicity(EObject sourceProperty) {
		// Explicit tracing doesn't really help with this; we need metamodel-specific handling
		return DEFAULT_MULTIPLICITY;
	}

	private Switch<EObject> createContextsSwitch() {
		return new ContextsSwitch<>() {
			public EObject caseAnnotatable(Annotatable object) {
				return ContextAnnotations.getSourceModel(object);
			}
		};
	}

}

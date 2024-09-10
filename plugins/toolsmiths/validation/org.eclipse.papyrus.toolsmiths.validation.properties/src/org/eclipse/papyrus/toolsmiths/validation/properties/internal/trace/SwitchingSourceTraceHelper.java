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

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ComposedSwitch;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.papyrus.infra.constraints.CompositeConstraint;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.ConstraintsFactory;
import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.constraints.ReferenceProperty;
import org.eclipse.papyrus.infra.constraints.SimpleConstraint;
import org.eclipse.papyrus.infra.constraints.ValueProperty;
import org.eclipse.papyrus.infra.constraints.environment.ConstraintType;
import org.eclipse.papyrus.infra.constraints.util.ConstraintsSwitch;
import org.eclipse.papyrus.infra.properties.contexts.AbstractSection;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.DataContextPackage;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextContentTreeIterator;
import org.eclipse.papyrus.infra.properties.contexts.util.ContextsSwitch;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.infra.properties.ui.PropertyEditor;
import org.eclipse.papyrus.infra.properties.ui.UiPackage;
import org.eclipse.papyrus.infra.properties.ui.Widget;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.properties.ui.util.UiSwitch;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.infra.tools.util.RecursionGuard;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.util.PropertiesCache;

import com.google.common.collect.Iterables;

/**
 * Abstract switch for common cases of inferring source traces from constraints and other
 * relationships in the properties model.
 */
abstract class SwitchingSourceTraceHelper extends ComposedSwitch<EObject> implements SourceTraceHelper {

	SwitchingSourceTraceHelper() {
		super();

		addSwitch(createContextsSwitch());
		addSwitch(createConstraintsSwitch());
	}

	protected final EObject composedSwitch(EObject object) {
		return super.doSwitch(object);
	}

	@Override
	public final EObject getSourceElement(EObject propertiesElement) {
		return composedSwitch(propertiesElement);
	}

	protected Switch<EObject> createContextsSwitch() {
		return new ContextsDelegate();
	}

	protected Switch<EObject> createConstraintsSwitch() {
		return new ConstraintsDelegate();
	}

	protected ValueProperty getProperty(SimpleConstraint constraint, String propertyName) {
		return (ValueProperty) constraint.getProperties(propertyName, false, ConstraintsPackage.Literals.VALUE_PROPERTY);
	}

	protected String getValue(SimpleConstraint constraint, String propertyName) {
		ValueProperty result = getProperty(constraint, propertyName);
		return result != null ? result.getValue() : null;
	}

	protected void setValue(SimpleConstraint constraint, String propertyName, String value) {
		ValueProperty property = ConstraintsFactory.eINSTANCE.createValueProperty();
		property.setName(propertyName);
		property.setValue(value);
		constraint.getProperties().add(property);
	}

	protected EObject getReference(SimpleConstraint constraint, String propertyName) {
		ReferenceProperty result = (ReferenceProperty) constraint.getProperties(propertyName, false, ConstraintsPackage.Literals.REFERENCE_PROPERTY);
		return result != null ? result.getValue() : null;
	}

	protected void setReference(SimpleConstraint constraint, String propertyName, EObject reference) {
		ReferenceProperty property = ConstraintsFactory.eINSTANCE.createReferenceProperty();
		property.setName(propertyName);
		property.setValue(reference);
		constraint.getProperties().add(property);
	}

	protected Optional<ConstraintType> getConstraintType(String className) {
		return Optional.ofNullable(Iterables.getFirst(PropertiesRuntime.getConfigurationManager().getContexts(), null))
				.map(context -> PropertiesCache.getInstance(context).getConstraintTypes(context))
				.flatMap(constraintTypes -> constraintTypes.stream()
						.filter(type -> className.equals(type.getConstraintClass()))
						.findAny());
	}

	protected Optional<ConstraintType> getConstraintType(ConstraintDescriptor constraint) {
		ConstraintType result = null;

		if (constraint instanceof SimpleConstraint) {
			SimpleConstraint simple = (SimpleConstraint) constraint;
			result = simple.getConstraintType();
		}

		return Optional.ofNullable(result);
	}

	protected Optional<SimpleConstraint> asSimpleConstraint(ConstraintDescriptor constraint) {
		return Optional.ofNullable(constraint).filter(SimpleConstraint.class::isInstance).map(SimpleConstraint.class::cast);
	}

	protected abstract EObject getOwningPackage(EObject sourceElement);

	protected abstract EObject getClass(EObject package_, DataContextElement contextElement);

	protected abstract EObject getProperty(EObject class_, Property contextProperty);

	protected EObject drillDown(Iterable<? extends EObject> nested) {
		EObject result = null;
		for (Iterator<? extends EObject> iter = nested.iterator(); result == null && iter.hasNext();) {
			result = composedSwitch(iter.next());
		}
		return result;
	}

	protected EObject drillDown(Iterable<? extends EObject> nested, Function<? super EObject, ? extends EObject> resultFunction) {
		EObject result = null;
		for (Iterator<? extends EObject> iter = nested.iterator(); result == null && iter.hasNext();) {
			EObject next = composedSwitch(iter.next());
			if (next != null) {
				result = resultFunction.apply(next);
			}
		}
		return result;
	}

	//
	// Nested types
	//

	protected class ContextsDelegate extends ContextsSwitch<EObject> {

		/**
		 * A set of objects that are currently engaged in recursion up
		 * the tree to try to infer a trace for themselves. This is used
		 * to cut off unbounded recursion back down the tree again through
		 * these objects.
		 */
		private final RecursionGuard<EObject> upwardRecursionGuard = new RecursionGuard<>();

		@Override
		public EObject caseAbstractSection(AbstractSection object) {
			return drillDown(object.getConstraints());
		}

		@Override
		public EObject caseSection(Section object) {
			EObject result = caseAbstractSection(object);

			if (result == null) {
				// Check the view(s) referencing the section
				result = drillDown(object.getViews());
			}

			return result;
		}

		@Override
		public EObject caseView(View object) {
			return drillDown(object.getConstraints());
		}

		@Override
		public EObject caseDataContextPackage(DataContextPackage object) {
			// Find the source class of any context-element that provides one, then get its package
			return drillDown(object.getElements(), SwitchingSourceTraceHelper.this::getOwningPackage);
		}

		@Override
		public EObject caseDataContextElement(DataContextElement object) {
			EObject result = null;
			PropertiesCache cache = PropertiesCache.getInstance(object);

			if (!object.getProperties().isEmpty()) {
				// Find any property that has an editor in some section, that we can trace via an instance-of constraint,
				// and get that constraint class if it has the same name as the data-context element
				for (Property property : object.getProperties()) {
					result = cache.getViews(object).stream().filter(view -> hasEditor(view, property))
							.map(SwitchingSourceTraceHelper.this::getSourceElement)
							.filter(Objects::nonNull)
							.filter(sourceClass -> Objects.equals(getName(sourceClass), object.getName()))
							.findAny().orElse(null);

					if (result != null) {
						break;
					}
				}
			}

			if (result == null) {
				// Didn't get a result from properties? Maybe there are none or they do not resolve.
				// See if we can trace the containing package by any means and look up the class by name
				EObject container = object.eContainer();
				try (RecursionGuard<EObject>.Gate gate = upwardRecursionGuard.guardIfAny(DataContextPackage.class::isInstance, container)) {
					if (gate.isOpen() && !(container instanceof Context)) {
						EObject sourcePackage = composedSwitch(container);
						if (sourcePackage != null) {
							result = SwitchingSourceTraceHelper.this.getClass(sourcePackage, object);
						}
					}
				}
			}

			return result;
		}

		private boolean hasEditor(View view, Property property) {
			boolean result = false;

			Iterator<PropertyEditor> editors = Iterators2.filter(new ContextContentTreeIterator(view.getSections()), PropertyEditor.class);
			while (!result && editors.hasNext()) {
				result = editors.next().getProperty() == property;
			}

			return result;
		}

		@Override
		public EObject caseProperty(Property object) {
			DataContextElement owner = object.getContextElement();
			if (owner == null) {
				// No use looking up a property that isn't in the data context (which
				// then should actually be an instance of the UnknownProperty class)
				return null;
			}

			// Look for any section that has a widget referencing this property
			EObject result = null;

			PropertiesCache cache = PropertiesCache.getInstance(object);
			for (Iterator<PropertyEditor> iter = cache.<PropertyEditor> getReferencers(object, UiPackage.Literals.PROPERTY_EDITOR__PROPERTY).iterator(); result == null && iter.hasNext();) {
				Widget rootWidget = getRootWidget(iter.next());
				Section section = getSection(rootWidget);
				if (section != null) {
					EObject sourceClass = composedSwitch(section);
					// Check that we're not finding an inherited property
					if (sourceClass != null && Objects.equals(getName(sourceClass), owner.getName())) {
						result = getProperty(sourceClass, object);
					}
				}
			}

			if (result == null) {
				// Maybe we can trace the containing context and find a matching property?
				try (RecursionGuard<EObject>.Gate gate = upwardRecursionGuard.guard(owner)) {
					if (gate.isOpen()) {
						EObject sourceClass = composedSwitch(owner);
						if (sourceClass != null) {
							result = getProperty(sourceClass, object);
						}
					}
				}

			}

			return result;
		}

		Widget getRootWidget(Widget widget) {
			Widget result = widget;

			while (result != null && result.eContainer() instanceof Widget) {
				result = (Widget) result.eContainer();
			}

			return result;
		}

		Section getSection(Widget widget) {
			PropertiesCache cache = PropertiesCache.getInstance(widget);
			EList<Section> sections = cache.getReferencers(widget, ContextsPackage.Literals.SECTION__WIDGET);
			return sections.isEmpty() ? null : sections.get(0);
		}

	}

	protected class ConstraintsDelegate extends ConstraintsSwitch<EObject> {

		@Override
		public EObject caseCompositeConstraint(CompositeConstraint object) {
			return drillDown(object.getConstraints());
		}

		@Override
		public EObject caseSimpleConstraint(SimpleConstraint object) {
			EObject result = null;
			ConstraintType type = object.getConstraintType();

			if (type.getConstraintClass() != null) {
				result = constraintSwitch(object, type.getConstraintClass());
			}

			return result;
		}

		protected EObject constraintSwitch(SimpleConstraint constraint, String constraintClass) {
			return null;
		}

	}

	protected class UIDelegate extends UiSwitch<EObject> {

		@Override
		public EObject caseCompositeWidget(CompositeWidget object) {
			return drillDown(object.getWidgets());
		}

		@Override
		public EObject casePropertyEditor(PropertyEditor object) {

			return super.casePropertyEditor(object);
		}

	}

}

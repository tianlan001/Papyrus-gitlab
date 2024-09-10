/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 417409
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.constraints.runtime;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.papyrus.infra.constraints.Activator;
import org.eclipse.papyrus.infra.constraints.ConstraintDescriptor;
import org.eclipse.papyrus.infra.constraints.DisplayUnit;
import org.eclipse.papyrus.infra.constraints.constraints.Constraint;

/**
 * The default, generic implementation for ConstraintEngine
 *
 * @author Camille Letavernier
 * @param <E>
 *            The type of DisplayUnit managed by this Constraint Engine
 */
public abstract class DefaultConstraintEngine<E extends DisplayUnit> implements ConstraintEngine<E> {

	// Pattern for names of methods that convert an object to a collection
	private static final Pattern CONVERTER_PATTERN = Pattern.compile("(?:to|as)(?:Collection|List|Set)"); //$NON-NLS-1$

	private static final Method NO_CONVERTER = Object.class.getMethods()[0];

	private final ListenerList<ConstraintEngineListener> listeners = new ListenerList<>(ListenerList.IDENTITY);

	/**
	 * The constraints instantiated by this Engine
	 */
	protected final Set<Constraint> constraints = new LinkedHashSet<>();

	private final Class<? extends E> displayUnitType;

	private final Map<Class<?>, Method> collectionConverters = new HashMap<>();

	/**
	 * @since 2.0
	 */
	protected DefaultConstraintEngine(Class<? extends E> displayUnitType) {
		super();

		this.displayUnitType = displayUnitType;
	}

	@Override
	public abstract void refresh();

	@Override
	public synchronized void addConstraint(ConstraintDescriptor descriptor) {
		Constraint constraint = ConstraintFactory.getInstance().createFromModel(descriptor);
		if (constraint != null) {
			constraints.add(constraint);
		}
	}

	/**
	 * @since 2.0
	 */
	@Override
	public synchronized Set<E> getDisplayUnits(final Object selection) {
		Collection<?> collection = asCollection(selection);

		Set<Constraint> matchedConstraints = match(collection);

		return getDisplayUnits(matchedConstraints);
	}

	private Set<Constraint> match(final Collection<?> selection) {
		Set<Constraint> matchedConstraints = new LinkedHashSet<>();

		if (selection.isEmpty()) {
			return matchedConstraints;
		}

		for (Constraint c : constraints) {
			try {
				if (c.match(selection)) {
					matchedConstraints.add(c);
				}
			} catch (Throwable ex) {
				String errorMessage = String.format("An error occurred when executing the matching constraint %s. This constraint will be ignored", c.getDescriptor().getName());
				Activator.log.error(errorMessage, ex);
			}

		}

		// String logValue;
		//
		// logValue = "Filtered Constraints : "; //$NON-NLS-1$
		// for(Constraint constraint : matchedConstraints) {
		// logValue += constraint.getDescriptor().getName() + ", ";
		// }
		// Activator.log.warn(logValue);

		resolveConstraintConflicts(matchedConstraints);

		// logValue = "Filtered Constraints : "; //$NON-NLS-1$
		// for(Constraint constraint : matchedConstraints) {
		// logValue += constraint.getDescriptor().getName() + ", ";
		// }
		//
		// Activator.log.warn(logValue);

		return matchedConstraints;
	}

	private void resolveConstraintConflicts(final Set<Constraint> matchedConstraints) {
		Set<Constraint> constraintsSet = new HashSet<>(matchedConstraints);
		for (Constraint c : constraintsSet) {
			for (Constraint c2 : constraintsSet) {
				if (c == c2) {
					continue;
				}

				if (c.getDescriptor().getOverriddenConstraints().contains(c2.getDescriptor())) {
					matchedConstraints.remove(c2);
					continue;
				}

				if (c2.getDescriptor().isOverrideable() && c.overrides(c2)) {
					matchedConstraints.remove(c2);
					continue;
				}
			}
		}
	}

	private Set<E> getDisplayUnits(final Set<Constraint> matchedConstraints) {
		Set<E> displayUnits = new LinkedHashSet<>();
		for (Constraint c : matchedConstraints) {
			displayUnits.add(displayUnitType.cast(c.getDescriptor().getDisplay()));
		}
		return displayUnits;
	}

	@Override
	public void addConstraintEngineListener(ConstraintEngineListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeConstraintEngineListener(ConstraintEngineListener listener) {
		listeners.remove(listener);
	}

	protected void fireConstraintsChanged() {
		if (!listeners.isEmpty()) {
			Object[] toNotify = listeners.getListeners();
			ConstraintsChangedEvent event = new ConstraintsChangedEvent(this);
			for (int i = 0; i < toNotify.length; i++) {
				try {
					((ConstraintEngineListener) toNotify[i]).constraintsChanged(event);
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in constraints-changed listener.", e); //$NON-NLS-1$
				}
			}
		}
	}

	private Collection<?> asCollection(Object object) {
		Collection<?> result;

		if (object == null) {
			result = Collections.EMPTY_LIST;
		} else if (object instanceof Collection<?>) {
			result = (Collection<?>) object;
		} else if (object instanceof Iterable<?>) {
			result = StreamSupport.stream(((Iterable<?>) object).spliterator(), false).collect(Collectors.toList());
		} else {
			Method converter = getToCollectionMethod(object);
			if (converter != null) {
				try {
					result = (Collection<?>) converter.invoke(object);
				} catch (Exception e) {
					// Converter not valid. Fine. Don't try it again
					rejectConverter(object);
					result = Collections.EMPTY_LIST;
				}
			} else {
				result = Collections.singletonList(object);
			}
		}

		return result;
	}

	private Method getToCollectionMethod(Object object) {
		Class<?> key = object.getClass();
		Method result = collectionConverters.computeIfAbsent(key, owner -> {
			for (Method next : owner.getMethods()) {
				if (((next.getModifiers() & Modifier.STATIC) == 0)
						&& Collection.class.isAssignableFrom(next.getReturnType())
						&& (next.getParameterCount() == 0)
						&& CONVERTER_PATTERN.matcher(next.getName()).matches()) {
					return next;
				}
			}
			return NO_CONVERTER;
		});

		return (result == NO_CONVERTER) ? null : result;
	}

	private void rejectConverter(Object object) {
		collectionConverters.put(object.getClass(), NO_CONVERTER);
	}
}

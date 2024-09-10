/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.util;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.AbstractObservableValue;
import org.eclipse.core.databinding.observable.value.ValueDiff;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.GMFObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.style.StylePackage;
import org.eclipse.papyrus.infra.services.edit.ui.databinding.PapyrusObservableValue;

/**
 * Represents the observable value of a diagram's owner in the case where the diagram does not yet have the viewpoints-compatible style for holding the value itself
 *
 * @author Laurent Wouters
 */
public class LegacyOwnerObservable extends AbstractObservableValue {

	private EditingDomain domain;
	private Diagram diagram;
	private EStructuralFeature feature;
	private IChangeListener styleListener;
	private IChangeListener valueListener;
	private PapyrusObservableValue styleObservable;
	private PapyrusObservableValue valueObservable;

	/**
	 * Initializes the observables
	 *
	 * @param diagram
	 *            The diagram to be observed
	 * @param domain
	 *            The editing domain
	 */
	public LegacyOwnerObservable(Diagram diagram, EStructuralFeature feature, EditingDomain domain) {
		super(Realm.getDefault());
		this.domain = domain;
		this.diagram = diagram;
		this.feature = feature;
		buildStyleListener();
		this.styleObservable = new GMFObservableValue(diagram, NotationPackage.Literals.VIEW__STYLES, domain);
		this.styleObservable.addChangeListener(styleListener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#isStale()
	 */
	@Override
	public boolean isStale() {
		return (valueListener != null);
	}

	/**
	 * Builds the listener for the styles property of the diagram
	 */
	private void buildStyleListener() {
		this.styleListener = new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				if (valueListener != null) {
					return;
				}
				Style style = LegacyOwnerObservable.this.diagram.getStyle(StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE);
				if (style != null) {
					buildValueListener(style);
				}
			}
		};
	}

	/**
	 * Builds the listener for the EObjectValue property of the style
	 *
	 * @param style
	 *            The style to be observed
	 */
	private void buildValueListener(Style style) {
		this.valueListener = new IChangeListener() {
			@Override
			public void handleChange(ChangeEvent event) {
				fireValueChange(new ValueDiff() {
					@Override
					public Object getOldValue() {
						return null;
					}

					@Override
					public Object getNewValue() {
						return DiagramUtils.getOwner(diagram);
					}
				});
			}
		};
		valueObservable = new GMFObservableValue(style, feature, domain);
		valueObservable.addChangeListener(valueListener);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.databinding.observable.value.IObservableValue#getValueType()
	 */
	@Override
	public Object getValueType() {
		return EcorePackage.Literals.EOBJECT;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		return DiagramUtils.getOwner(diagram);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.databinding.observable.value.AbstractObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(Object value) {
		Command emfCommand = getCommand((EObject) value);
		domain.getCommandStack().execute(emfCommand);
	}

	private Command getCommand(final EObject newValue) {
		final EObject oldValue = DiagramUtils.getOwner(diagram);

		return new AbstractCommand("Change diagram's owner") {
			private boolean createStyle = true;

			@Override
			public void execute() {
				Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE);
				createStyle = (style == null);
				DiagramUtils.setOwner(diagram, newValue);
			}

			@Override
			public void redo() {
				DiagramUtils.setOwner(diagram, newValue);
			}

			@Override
			public void undo() {
				if (createStyle) {
					Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_DIAGRAM_STYLE);
					diagram.getStyles().remove(style);
				} else {
					DiagramUtils.setOwner(diagram, oldValue);
				}
			}

			@Override
			public boolean prepare() {
				return true;
			}
		};
	}
}

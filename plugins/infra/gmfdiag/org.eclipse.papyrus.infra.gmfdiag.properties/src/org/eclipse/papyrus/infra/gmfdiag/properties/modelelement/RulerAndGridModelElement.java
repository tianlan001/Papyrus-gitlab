/*****************************************************************************
 * Copyright (c) 2013, 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 417409
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 491816
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.draw2d.Graphics;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomBooleanStyleWithStoreObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomDoubleStyleWithStoreObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomIntStyleWithStoreObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.RulersUnitStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.LineStyleLabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.UnitsLabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.properties.util.RulersAndGridPropertyConstants;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.StaticContentProvider;

/**
 *
 * @author Vincent Lorenzo
 *         This class provides the ModelElement for the property view
 */
@SuppressWarnings("restriction")
// suppress the warning for WorkspaceViewerProperties
public class RulerAndGridModelElement extends AbstractModelElement {



	/**
	 * the diagram for which we are editing preferences
	 */
	protected Diagram diagram;

	/**
	 * the edited preference store
	 */
	protected IPreferenceStore store;

	/**
	 * The editing domain.
	 * @since 3.0
	 */
	protected EditingDomain domain;

	/**
	 *
	 * Constructor.
	 *
	 * @param view
	 *            the diagram for which we are editing preferences
	 * @param domain
	 *            the editing domain
	 * @param context
	 *            the data context
	 * @param preferenceStore
	 *            the edited preference store
	 */
	public RulerAndGridModelElement(final Diagram view, final EditingDomain domain, final DataContextElement context, final IPreferenceStore preferenceStore) {
		this.diagram = view;
		this.domain = domain;
		this.store = preferenceStore;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#dispose()
	 *
	 */
	@Override
	public void dispose() {
		super.dispose();
		this.diagram = null;
		this.store = null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#doGetObservable(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	protected IObservable doGetObservable(final String propertyPath) {
		IObservable observable = null;

		if (RulersAndGridPropertyConstants.GRID_IS_DISPLAYING_GRID.equals(propertyPath)) {
			observable = new CustomBooleanStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.VIEWGRID, this.store);
		}
		if (RulersAndGridPropertyConstants.RULERS_IS_DISPLAYING_RULER.equals(propertyPath)) {
			observable = new CustomBooleanStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.VIEWRULERS, this.store);
		}
		if (RulersAndGridPropertyConstants.GRID_IS_IN_FRONT.equals(propertyPath)) {
			observable = new CustomBooleanStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.GRIDORDER, this.store);
		}
		if (RulersAndGridPropertyConstants.GRID_COLOR.equals(propertyPath)) {
			observable = new CustomIntStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.GRIDLINECOLOR, this.store);
		}
		if (RulersAndGridPropertyConstants.GRID_STYLE.equals(propertyPath)) {
			observable = new CustomIntStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.GRIDLINESTYLE, this.store);
		}
		if (RulersAndGridPropertyConstants.GRID_SPACING.equals(propertyPath)) {
			observable = new CustomDoubleStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.GRIDSPACING, this.store);
		}
		if (RulersAndGridPropertyConstants.GRID_IS_SNAP_TO_GRID.equals(propertyPath)) {
			observable = new CustomBooleanStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.SNAPTOGRID, this.store);
		}
		if (RulersAndGridPropertyConstants.GRID_IS_SNAP_TO_SHAPE.equals(propertyPath)) {
			observable = new CustomBooleanStyleWithStoreObservableValue(this.diagram, this.domain, WorkspaceViewerProperties.SNAPTOGEOMETRY, this.store);
		}
		if (RulersAndGridPropertyConstants.RULERS_UNITS.equals(propertyPath)) {
			observable = new RulersUnitStyleObservableValue(this.diagram, this.domain, this.store);
		}
		return observable;
	}



	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#getContentProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		if (RulersAndGridPropertyConstants.GRID_STYLE.equals(propertyPath)) {
			return new StaticContentProvider(new Object[] { Graphics.LINE_DASH, Graphics.LINE_DASHDOT, Graphics.LINE_DASHDOTDOT, Graphics.LINE_DOT, Graphics.LINE_SOLID, Graphics.LINE_CUSTOM });
		}
		if (RulersAndGridPropertyConstants.RULERS_UNITS.equals(propertyPath)) {
			return new StaticContentProvider(new Object[] { RulerProvider.UNIT_INCHES, RulerProvider.UNIT_CENTIMETERS, RulerProvider.UNIT_PIXELS });
		}
		return super.getContentProvider(propertyPath);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#getLabelProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		if (RulersAndGridPropertyConstants.GRID_STYLE.equals(propertyPath)) {
			return new LineStyleLabelProvider();
		}
		if (RulersAndGridPropertyConstants.RULERS_UNITS.equals(propertyPath)) {
			return new UnitsLabelProvider();
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#isMandatory(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public boolean isMandatory(final String propertyPath) {
		if (RulersAndGridPropertyConstants.GRID_STYLE.equals(propertyPath) || RulersAndGridPropertyConstants.RULERS_UNITS.equals(propertyPath)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.AbstractModelElement#forceRefresh(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public boolean forceRefresh(final String propertyPath) {
		return true;
	}
}

/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation - bug 465297
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 515661, 521754
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.decoration.ConnectionDecorationRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.types.NotationTypesMap;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.LineStyleEnum;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PortPositionEnum;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PositionEnum;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.ConnectionDecorationStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.CustomChildFloatingLabelBooleanStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.CustomIntStyleCompartmentObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.CustomStringStyleCompartmentObservableValue;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.StaticContentProvider;

/**
 * An extends of AdvanceStyleModelElement to manage custom style properties.
 */
public class AdvanceStyleModelElement extends CustomStyleModelElement {

	/** The view. */
	View view;

	/**
	 * Instantiates a new custom style model element.
	 *
	 * @param source
	 *            the source
	 * @param context
	 *            the context
	 */
	public AdvanceStyleModelElement(final View source, final DataContextElement context) {
		this(source, null, context);
	}

	/**
	 * Instantiates a new custom style model element.
	 *
	 * @param source
	 *            the source
	 * @param domain
	 *            the domain
	 * @param element
	 *            the element
	 */
	public AdvanceStyleModelElement(final View source, final EditingDomain domain, final DataContextElement element) {
		super(source, domain, element);
		this.view = source;
	}

	/**
	 * Do get observable.
	 *
	 * @param propertyPath
	 *            the property path
	 * @return the i observable
	 * @see org.eclipse.papyrus.infra.properties.modelelement.EMFModelElement#doGetObservable(java.lang.String)
	 */
	@Override
	public IObservable doGetObservable(final String propertyPath) {
		IObservable observable = null;
		Property property = findProperty(propertyPath);
		if (null != property) {

			// For compartments child line position and line length ratio properties.
			if (propertyPath.equals(NamedStyleProperties.LINE_POSITION) || propertyPath.equals(NamedStyleProperties.LINE_LENGTH_RATIO)) {
				// CustomStringObservableValue to set all compartment of the Shape
				observable = new CustomStringStyleCompartmentObservableValue(view, domain, propertyPath);
			} else

			// For compartments child line length property
			if (propertyPath.equals(NamedStyleProperties.LINE_LENGTH)) {
				observable = new CustomIntStyleCompartmentObservableValue(view, domain, propertyPath);
			} else

			// For floating label boolean: needs to set the child to set label visible
			if (propertyPath.equals(NamedStyleProperties.DISPLAY_FLOATING_LABEL) && !"FloatingLabel".equals(NotationTypesMap.instance.getHumanReadableType((View) source))) {// $NON-NLS-1$
				// CustomStringObservableValue to set all compartment of the Shape
				observable = new CustomChildFloatingLabelBooleanStyleObservableValue(view, domain, propertyPath);
			} else

			// case of connector decorations
			if (propertyPath.equals(ConnectionEditPart.TARGET_DECORATION)
					|| propertyPath.equals(ConnectionEditPart.SOURCE_DECORATION)) {
				observable = new ConnectionDecorationStyleObservableValue(view, domain, propertyPath);
			} else {
				observable = super.doGetObservable(propertyPath);
			}
		}
		return observable;
	}

	/**
	 * Gets the content provider.
	 *
	 * @param propertyPath
	 *            the property path
	 * @return the content provider
	 * @see org.eclipse.papyrus.infra.properties.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 */
	@Override
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		IStaticContentProvider contentProvider = null;

		// Case for alignments
		if (propertyPath.equals(NamedStyleProperties.TEXT_ALIGNMENT)
				|| propertyPath.equals(NamedStyleProperties.LINE_POSITION)) {

			String[] textAlignments = new String[] {
					PositionEnum.LEFT.toString(),
					PositionEnum.CENTER.toString(),
					PositionEnum.RIGHT.toString() };

			contentProvider = new StaticContentProvider(textAlignments);
		} else

		// Case for border style
		if (propertyPath.equals(NamedStyleProperties.BORDER_STYLE)) {

			String[] borderStyles = new String[] {
					LineStyleEnum.DASH.toString(), LineStyleEnum.DASH_DOT.toString(),
					LineStyleEnum.DASH_DOT_DOT.toString(), LineStyleEnum.DOT.toString(),
					LineStyleEnum.SOLID.toString(), LineStyleEnum.CUSTOM.toString() };

			contentProvider = new StaticContentProvider(borderStyles);
		} else

		// Case for position
		if (propertyPath.equals(NamedStyleProperties.POSITION)) {

			String[] positions = new String[] {
					PositionEnum.EAST.toString(), PositionEnum.WEST.toString(),
					PositionEnum.NORTH.toString(), PositionEnum.SOUTH.toString(),
					PositionEnum.AUTO.toString() };

			contentProvider = new StaticContentProvider(positions);

		} else

		// Case for port position
		if (propertyPath.equals(NamedStyleProperties.PORT_POSITION)) {
			String[] portPositions = new String[] {
					PortPositionEnum.INSIDE.toString(),
					PortPositionEnum.ONLINE.toString(),
					PortPositionEnum.OUTSIDE.toString() };

			contentProvider = new StaticContentProvider(portPositions);
		} else

		// case connector decorations
		if (propertyPath.equals(ConnectionEditPart.TARGET_DECORATION)
				|| propertyPath.equals(ConnectionEditPart.SOURCE_DECORATION)) {
			Map<String, Class<? extends RotatableDecoration>> availableDecoration = ConnectionDecorationRegistry.getInstance().getAvailableDecoration();
			List<String> decorations = new ArrayList<String>(availableDecoration.keySet());
			decorations.addAll(Arrays.asList(ConnectionEditPart.DECORATION_VALUES));
			contentProvider = new StaticContentProvider(decorations.toArray());
		}

		return null != contentProvider ? contentProvider : super.getContentProvider(propertyPath);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.properties.modelelement.CustomStyleModelElement#getLabelProvider(java.lang.String)
	 */
	@Override
	public ILabelProvider getLabelProvider(final String propertyPath) {
		ILabelProvider labelProvider = null;

		// case connector decorations
		if (propertyPath.equals(ConnectionEditPart.TARGET_DECORATION)
				|| propertyPath.equals(ConnectionEditPart.SOURCE_DECORATION)) {
			Map<String, Class<? extends RotatableDecoration>> availableDecoration = ConnectionDecorationRegistry.getInstance().getAvailableDecoration();
			List<String> decorations = new ArrayList<String>(availableDecoration.keySet());
			decorations.addAll(Arrays.asList(ConnectionEditPart.DECORATION_VALUES));

			labelProvider = new LabelProvider() {
				@Override
				public String getText(final Object element) {
					if (element instanceof String) {
						String label = ConnectionDecorationRegistry.getInstance().getLabel((String) element);
						return null != label ? label : (String) element;
					}
					return null;
				}
			};
		} else {
			labelProvider = super.getLabelProvider(propertyPath);
		}

		return labelProvider;
	}

}

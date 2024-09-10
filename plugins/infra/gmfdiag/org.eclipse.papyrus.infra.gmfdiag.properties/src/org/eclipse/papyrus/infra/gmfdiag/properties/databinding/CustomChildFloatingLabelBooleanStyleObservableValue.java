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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.properties.databinding;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomBooleanStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStyleValueCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.types.NotationTypesMap;

/**
 * A custom boolean observable value for child floating label.
 */
public class CustomChildFloatingLabelBooleanStyleObservableValue extends CustomBooleanStyleObservableValue {

	/** The Constant FLOATING_LABEL. */
	private static final String FLOATING_LABEL = "FloatingLabel"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param source
	 *            the source
	 * @param domain
	 *            the domain
	 * @param styleName
	 *            the style name
	 */
	public CustomChildFloatingLabelBooleanStyleObservableValue(final View source, final EditingDomain domain, final String styleName) {
		super(source, domain, styleName);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AbstractCustomStyleObservableValue#doGetValue()
	 */
	@Override
	protected Object doGetValue() {
		Object value = null;
		if (source instanceof View) {
			@SuppressWarnings("unchecked")
			List<View> floatingLabels = (List<View>) source.getChildren().stream().filter(child -> isFloatingLabel(child)).collect(Collectors.toList());

			for (View child : floatingLabels) {
				NamedStyle valueStyle = child.getNamedStyle(styleClass, styleName);
				if (null != valueStyle) {
					value = valueStyle.eGet(styleFeature);
				}
			}
		}
		return value;
	}

	/**
	 * @param child
	 * @return <code>true</code> if the object is a floating label view
	 */
	private boolean isFloatingLabel(Object child) {
		return child instanceof DecorationNode && FLOATING_LABEL.equals(NotationTypesMap.instance.getHumanReadableType((View) child));
	}

	/**
	 * Do set value.
	 *
	 * @param value
	 *            the value
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AbstractCustomStyleObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		if (value instanceof Boolean && source instanceof Shape) {
			CompoundCommand cc = new CompoundCommand();
			for (Object child : source.getChildren()) {
				if (isFloatingLabel(child)) {
					Command command = new CustomStyleValueCommand((View) child, value, styleClass, styleFeature, styleName);
					if (null != command) {
						cc.append(command);
					}
				}
			}
			domain.getCommandStack().execute(cc);
			doGetValue(); // Refresh the lastValue
		}
	}
}

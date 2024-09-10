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

import java.util.Iterator;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStringStyleObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStyleValueCommand;

/**
 * A custom String observable value for Compartment Style.
 */
public class CustomStringStyleCompartmentObservableValue extends CustomStringStyleObservableValue {

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
	public CustomStringStyleCompartmentObservableValue(final View source, final EditingDomain domain, final String styleName) {
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
		boolean allCompSameValue = true;
		if (source instanceof View) {
			Iterator<?> iterator = source.getChildren().iterator();
			while (allCompSameValue == true && iterator.hasNext()) {
				Object child = iterator.next();
				if (child instanceof BasicCompartment) {
					NamedStyle valueStyle = ((View) child).getNamedStyle(styleClass, styleName);
					if (valueStyle != null) {
						if (value == null) {
							value = valueStyle.eGet(styleFeature);
						} else if (!value.equals(valueStyle.eGet(styleFeature))) {
							allCompSameValue = false;
						}
					}
				}
			}
		}
		return allCompSameValue ? value : getDefaultValue();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AbstractCustomStyleObservableValue#doSetValue(java.lang.Object)
	 */
	@Override
	protected void doSetValue(final Object value) {
		if (value instanceof String && source instanceof Shape) {
			CompoundCommand cc = new CompoundCommand();
			for (Object child : source.getChildren()) {
				if (child instanceof BasicCompartment) {
					Command command = new CustomStyleValueCommand((View) child, value, styleClass, styleFeature,
							styleName);
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

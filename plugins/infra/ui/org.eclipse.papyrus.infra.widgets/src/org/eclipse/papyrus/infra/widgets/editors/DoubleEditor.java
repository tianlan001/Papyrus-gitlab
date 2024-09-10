/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Thibault Le Ouay t.leouay@sherpa-eng.com - Add binding implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.editors;


import org.eclipse.core.databinding.conversion.IConverter;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.validator.RealValidator;
import org.eclipse.swt.widgets.Composite;


/**
 * An editor representing a float value as a text box
 *
 * @author Camille Letavernier
 */
public class DoubleEditor extends StringEditor {

	private IConverter targetToModelConverter;

	/**
	 *
	 * Constructs an Editor for a Double value. The widget is a Text field.
	 *
	 * @param parent
	 *            The Composite in which the editor is created
	 * @param style
	 *            The Text's style
	 */
	public DoubleEditor(Composite parent, int style) {
		super(parent, style);

		targetValidator = new RealValidator();
		targetToModelConverter = new IConverter() {

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Object getToType() {
				return Double.class;
			}

			@Override
			public Double convert(Object fromObject) {
				if (fromObject instanceof String) {
					String newString = ((String) fromObject)
							.replaceAll(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						return Double.parseDouble(newString);
					} catch (NumberFormatException ex) {
						Activator.log.error(ex);
						return null;
					}
				}
				return null;
			}

		};

		IConverter doubleToString = new IConverter() {

			@Override
			public Object getFromType() {
				return Double.class;
			}

			@Override
			public Object getToType() {
				return String.class;
			}

			@Override
			public Object convert(Object fromObject) {
				if (fromObject instanceof Double) {
					return Double.toString((Double) fromObject);
				}
				return ""; //$NON-NLS-1$
			}
		};
		setValidateOnDelay(true);
		setConverters(targetToModelConverter, doubleToString);
		setTargetAfterGetValidator(targetValidator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getEditableType() {
		return Double.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double getValue() {
		try {
			return (Double) targetToModelConverter.convert(super.getValue());
		} catch (Exception ex) {
			Activator.log.error(ex);
			return null;
		}
	}


}

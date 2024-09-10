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
import org.eclipse.core.databinding.conversion.text.StringToNumberConverter;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.validator.IntegerValidator;
import org.eclipse.swt.widgets.Composite;

/**
 * A Property Editor representing an Integer value as a text box.
 *
 * @author Camille Letavernier
 *
 */
public class IntegerEditor extends StringEditor {

	/**
	 * The IConverter for converting data from the widget to the model
	 */
	private IConverter targetToModelConverter;


	/**
	 * Constructs an editor for Integer values. The widget is a Text field.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The Text's style
	 */
	public IntegerEditor(Composite parent, int style) {
		this(parent, style, null);
	}

	/**
	 * Constructs an editor for Integer values. The widget is a Text field.
	 *
	 * @param parent
	 *            The Composite in which this editor is created
	 * @param style
	 *            The Text's style
	 * @param label
	 *            The editor's label
	 */
	public IntegerEditor(Composite parent, int style, String label) {
		super(parent, style, label);

		targetValidator = new IntegerValidator();

		targetToModelConverter = new IConverter() {

			@Override
			public Object getToType() {
				return Integer.class;
			}

			@Override
			public Object getFromType() {
				return String.class;
			}

			@Override
			public Integer convert(Object fromObject) {
				if (fromObject instanceof String) {
					String newString = ((String) fromObject).replaceAll(" ", ""); //$NON-NLS-1$ //$NON-NLS-2$
					try {
						Integer i = StringToNumberConverter.toInteger(false).convert(newString);
						return i;
					} catch (Exception ex) {

					}

				}

				return null;
			}
		};

		IConverter integerToString = new IConverter() {

			@Override
			public Object getToType() {
				return String.class;
			}

			@Override
			public Object getFromType() {
				return Integer.class;
			}

			@Override
			public Object convert(Object fromObject) {

				if (fromObject instanceof Integer) {
					return Integer.toString((Integer) fromObject);
				}
				errorBinding = true;
				return ""; //$NON-NLS-1$
			}

		};
		setValidateOnDelay(true);
		setConverters(targetToModelConverter, integerToString);
		setTargetAfterGetValidator(targetValidator);

	}





	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getEditableType() {
		return Integer.class;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer getValue() {
		try {

			return (Integer) targetToModelConverter.convert(super.getValue());
		} catch (Exception ex) {
			Activator.log.error(ex);
			return null;
		}
	}

}

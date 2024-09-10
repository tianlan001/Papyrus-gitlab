/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.widgets.editors;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter;
import org.eclipse.papyrus.infra.widgets.validator.AbstractValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;

/**
 * @author Vincent Lorenzo
 *
 */
public class CompletionStyledTextStringEditor extends StyledTextStringEditor implements ISetPapyrusConverter {

	/**
	 * This wrapper provides a text field with completion
	 */
	private StringEditorWithCompletionWrapper wrapper;

	/**
	 * the parser to use
	 */
	protected IPapyrusConverter parser;

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 * @param heighHint
	 * @param widthHint
	 */
	public CompletionStyledTextStringEditor(Composite parent, int style, int heighHint, int widthHint) {
		super(parent, style, heighHint, widthHint);
		createReferenceTargetValidator();
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 * @param label
	 * @param heighHint
	 * @param widthHint
	 */
	public CompletionStyledTextStringEditor(Composite parent, int style, String label, int heighHint, int widthHint) {
		super(parent, style, label, heighHint, widthHint);
		createReferenceTargetValidator();
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 * @param label
	 */
	public CompletionStyledTextStringEditor(Composite parent, int style, String label) {
		super(parent, style, label);
		createReferenceTargetValidator();
	}



	/**
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#setValue(java.lang.Object)
	 *
	 * @param value
	 */
	@Override
	public void setValue(Object value) {
		if (parser != null && value instanceof Collection<?>) {
			String val = parser.canonicalToEditValue(value, 0);
			super.setValue(val);
		} else {
			super.setValue(value);
		}
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param parent
	 * @param style
	 */
	public CompletionStyledTextStringEditor(Composite parent, int style) {
		super(parent, style);
		createReferenceTargetValidator();
	}

	protected void notifyChange() {

		text.notifyListeners(SWT.FocusOut, new Event());

		// added to update the status when we use the completion
		if (targetValidator != null) {
			IStatus status = targetValidator.validate(text.getText());
			updateStatus(status);
		}
		commit();
		changeColorField();
	}

	/**
	 * create the validator for the text field
	 */
	protected void createReferenceTargetValidator() {
		targetValidator = new AbstractValidator() {

			@Override
			public IStatus validate(Object value) {
				if (parser == null) {
					return Status.OK_STATUS;
				}
				if (value instanceof String) {
					return parser.isValidEditString((String) value);
				}
				// not possible
				return new Status(IStatus.ERROR, org.eclipse.papyrus.infra.widgets.Activator.PLUGIN_ID, "Impossible case"); //$NON-NLS-1$
			}
		};
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.StyledTextStringEditor#createStyledText(org.eclipse.swt.widgets.Composite, java.lang.String, int)
	 *
	 * @param parent
	 * @param value
	 * @param style
	 * @return
	 */
	@Override
	public StyledText createStyledText(Composite parent, String value, int style) {
		this.wrapper = new StringEditorWithCompletionWrapper(parent, style);
		return wrapper.getTextWidget();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.util.ISetPapyrusConverter#setPapyrusConverter(org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter)
	 *
	 * @param parser
	 */
	public void setPapyrusConverter(IPapyrusConverter parser) {
		this.parser = parser;
		this.wrapper.setPapyrusConverter(parser);
	}


}

/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) Remi.Schnekenburger@cea.fr - Initial API and implementation
 *  Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * Default implementation for the {@link IDirectEditorConfiguration} interface.
 */
public class DefaultDirectEditorConfiguration implements IDirectEditorConfiguration {

	/** Default sourceViewer configuration */
	protected SourceViewerConfiguration sourceViewerConfiguration;

	/** language of the edited body */
	protected String language;

	/** To check if the configuration is available for each type. */
	protected boolean superType;

	/** objectToEdit */
	protected Object objectToEdit;

	/** input validator */
	protected IInputValidator validator;

	/**
	 * Returns the language of the edited body
	 *
	 * @return the language of the edited body
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language of the edited body
	 *
	 * @param language
	 *            the language of the edited body
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * {@inheritDoc}
	 */
	public Point getPreferedSize() {
		return new Point(SWT.DEFAULT, SWT.DEFAULT);
	}

	/**
	 * {@inheritDoc}
	 */
	public SourceViewerConfiguration getSourceViewerConfiguration() {
		if (sourceViewerConfiguration == null) {
			sourceViewerConfiguration = new SourceViewerConfiguration();
		}
		return sourceViewerConfiguration;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getStyle() {
		return SWT.BORDER | SWT.SINGLE;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getTextToEdit(Object objectToEdit) {
		return ""; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 */
	public Object postEditAction(Object objectToEdit, String newText) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object preEditAction(Object objectToEdit) {
		setObjectToEdit(objectToEdit);
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Composite createExtendedDialogArea(Composite parent) {
		return null;
	}

	/**
	 * Returns the object to edit
	 *
	 * @return the object to edit
	 */
	public Object getObjectToEdit() {
		return objectToEdit;
	}

	/**
	 * Sets the object to edit
	 *
	 * @param objectToEdit
	 *            the object to edit
	 */
	public void setObjectToEdit(Object objectToEdit) {
		this.objectToEdit = objectToEdit;
	}

	/**
	 * {@inheritDoc}
	 */
	public IInputValidator getInputValidator() {
		if (validator == null) {
			validator = new IInputValidator() {

				public String isValid(String newText) {
					// always valid
					return null;
				}
			};
		}
		return validator;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setInputValidator(IInputValidator validator) {
		this.validator = validator;
	}

	/**
	 * {@inheritDoc}
	 */
	public Selection getTextSelection(String value, Object editedObject) {
		return new Selection(0, value.length());
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSuperType() {
		return superType;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setSuperType(boolean superType) {
		this.superType = superType;
	}
}

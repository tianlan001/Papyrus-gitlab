/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.properties.xtext.widget;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.widgets.editors.AbstractReferenceDialog;
import org.eclipse.papyrus.infra.widgets.editors.StyledTextReferenceDialog;
import org.eclipse.papyrus.uml.properties.widgets.ExtendedMultiplicityDialog;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

/**
 * This allow to define the multiplicity editors (with 2 modes) with XText editors for lower and upper ValueSpecification
 */
public class MultiplicityXTextValueEditor extends ExtendedMultiplicityDialog implements SelectionListener {

	/**
	 * The default lower value direct editor configuration class name.
	 */
	private final static String DEFAULT_LOWER_VALUE_DIRECT_EDITOR_CONFIGURATION = "org.eclipse.papyrus.uml.textedit.valuespecification.xtext.ui.contribution.MultiplicityLowerValueSpecificationXtextDirectEditorConfiguration";

	/**
	 * The default upper value direct editor configuration class name.
	 */
	private final static String DEFAULT_UPPER_VALUE_DIRECT_EDITOR_CONFIGURATION = "org.eclipse.papyrus.uml.textedit.valuespecification.xtext.ui.contribution.MultiplicityUpperValueSpecificationXtextDirectEditorConfiguration";


	/**
	 * The lower value direct editor configuration class name edited.
	 */
	private String lowerValueDirectEditorConfiguration;

	/**
	 * The upper value direct editor configuration class name edited.
	 */
	private String upperValueDirectEditorConfiguration;


	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style.
	 */
	public MultiplicityXTextValueEditor(final Composite parent, final int style) {
		this(parent, style, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            The parent composite.
	 * @param style
	 *            The style.
	 * @param preferenceStore
	 *            The preference store.
	 */
	public MultiplicityXTextValueEditor(final Composite parent, final int style, final IPreferenceStore preferenceStore) {
		super(parent, style, preferenceStore);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultiplicityDialog#createLowerValueSpecificationEditor(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected AbstractReferenceDialog createLowerValueSpecificationEditor(final Composite parent, final int style) {
		final AbstractReferenceDialog lowerValueSpecificationEditor = new UMLXtextReferenceValueEditor(parent, style);
		if (null == lowerValueDirectEditorConfiguration) {
			lowerValueDirectEditorConfiguration = DEFAULT_LOWER_VALUE_DIRECT_EDITOR_CONFIGURATION;
		}
		((UMLXtextReferenceValueEditor) lowerValueSpecificationEditor).setDirectEditorConfiguration(lowerValueDirectEditorConfiguration);
		return lowerValueSpecificationEditor;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultiplicityDialog#createLowerValueSpecificationEditor(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected AbstractReferenceDialog createUpperValueSpecificationEditor(final Composite parent, final int style) {
		final AbstractReferenceDialog upperValueSpecificationEditor = super.createUpperValueSpecificationEditor(parent, style);
		if (null == upperValueDirectEditorConfiguration) {
			upperValueDirectEditorConfiguration = DEFAULT_UPPER_VALUE_DIRECT_EDITOR_CONFIGURATION;
		}
		((UMLXtextReferenceValueEditor) upperValueSpecificationEditor).setDirectEditorConfiguration(upperValueDirectEditorConfiguration);
		return upperValueSpecificationEditor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultiplicityDialog#createValueSpecificationEditor(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected StyledTextReferenceDialog createValueSpecificationEditor(final Composite parent, final int style) {
		return new UMLXtextReferenceValueEditor(parent, style);
	}

	/**
	 * Sets the lower value direct editor configuration class name.
	 * 
	 * @param lowerValueDirectEditorConfiguration
	 *            The lower value direct editor configuration class name.
	 */
	public void setLowerValueDirectEditorConfiguration(final String lowerValueDirectEditorConfiguration) {
		this.lowerValueDirectEditorConfiguration = lowerValueDirectEditorConfiguration;
		if (null != lowerValueEditor) {
			((UMLXtextReferenceValueEditor) lowerValueEditor).setDirectEditorConfiguration(lowerValueDirectEditorConfiguration);
		}
	}

	/**
	 * Sets the upper value direct editor configuration class name.
	 * 
	 * @param upperValueDirectEditorConfiguration
	 *            The upper valuedirect editor configuration class name.
	 */
	public void setUpperValueDirectEditorConfiguration(final String upperValueDirectEditorConfiguration) {
		this.upperValueDirectEditorConfiguration = upperValueDirectEditorConfiguration;
		if (null != upperValueEditor) {
			((UMLXtextReferenceValueEditor) upperValueEditor).setDirectEditorConfiguration(upperValueDirectEditorConfiguration);
		}
	}

}

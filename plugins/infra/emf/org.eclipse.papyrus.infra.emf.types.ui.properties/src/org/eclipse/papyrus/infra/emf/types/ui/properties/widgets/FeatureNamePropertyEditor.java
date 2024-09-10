/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * The Stereotype qualify name property Editor.
 */
public class FeatureNamePropertyEditor extends AbstractPropertyEditor {

	/** The stereotype qualify name value editor. */
	private FeatureNameValueEditor editor;

	private AdapterFactoryEditingDomain domain;

	/**
	 * The key for source uml element put into the map options of the reslurce set.
	 */
	private static final String SOURCE_ECLASS = "sourceEClass";//$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public FeatureNamePropertyEditor(final Composite parent, final int style) {
		editor = new FeatureNameValueEditor(parent, style);
		setEditor(editor);
	}

	/**
	 * Set the Uml Element to apply stereotype to the editor. Used to filter applicable stereotypes.
	 */
	protected void setObjectToApply() {
		if (null != domain) {
			// Get the semantic uml object selected
			Object eClassSourceElement = domain.getResourceSet().getLoadOptions().get(SOURCE_ECLASS);
			if (eClassSourceElement instanceof EClass) {
				editor.setSourceEClass((EClass) eClassSourceElement);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setProperty(java.lang.String)
	 */
	@Override
	public void setProperty(final String path) {
		super.setProperty(path);
		setDomain();
		setObjectToApply();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setInput(org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource)
	 */
	@Override
	public void setInput(final DataSource input) {
		super.setInput(input);
		setDomain();
		setObjectToApply();
	}

	/**
	 * Get the Editing domain according to the input and the property path.
	 */
	private void setDomain() {
		if (null != propertyPath && null != input) {
			// Get the semantic uml object selected
			ModelElement modelElement = input.getModelElement(propertyPath);
			EMFHelper.resolveEditingDomain(input.getSelection().getFirstElement());

			if (modelElement instanceof EMFModelElement) {
				domain = (AdapterFactoryEditingDomain) ((EMFModelElement) modelElement).getDomain();
			}
		}
	}


}

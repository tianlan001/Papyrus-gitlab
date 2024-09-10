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

package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.emf.types.advices.values.FeatureToSet;
import org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdviceFactory;
import org.eclipse.papyrus.infra.emf.types.advices.values.SetValuesAdvicePackage;
import org.eclipse.papyrus.infra.widgets.editors.MultipleReferenceEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * A {@link MultipleReferenceEditor} for {@link StereotypeToApply}.
 */
public class FeaturesToSetMultipleEditor extends AbstractCustomMultipleEditor {

	/**
	 * Constructor.
	 */
	public FeaturesToSetMultipleEditor(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultipleValueEditor#addAction()
	 */
	@Override
	protected void addAction() {
		// The feature
		EReference feature = SetValuesAdvicePackage.eINSTANCE.getSetValuesAdviceConfiguration_FeaturesToSet();
		// The container
		EObject container = (EObject) getContextElement();

		FeatureToSet newObject = SetValuesAdviceFactory.eINSTANCE.createFeatureToSet();

		// Execute the add
		addNewObject(feature, container, newObject);
		// Refresh
		getViewer().refresh();
		// Select new object
		getViewer().setSelection(new StructuredSelection(newObject), true);
	}

}

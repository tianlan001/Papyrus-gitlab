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
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.properties.ui.modelelement;

import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintEngine;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.creation.CreationContext;
import org.eclipse.papyrus.infra.properties.ui.creation.EcorePropertyEditorFactory;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.swt.widgets.Control;

/**
 * A ReferenceFactory used to instantiate EObjects.
 * @since 2.0
 */
public class EObjectDirectEditingValueFactory extends EcorePropertyEditorFactory {

	/**
	 * Constructor.
	 *
	 * @param referenceIn
	 *            The {@link EReference}.
	 */
	public EObjectDirectEditingValueFactory(final EReference referenceIn) {
		super(referenceIn);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.creation.PropertyEditorFactory#createObject(org.eclipse.swt.widgets.Control, java.lang.Object, java.lang.Object)
	 */
	@Override
	protected Object createObject(final Control widget, final Object context, final Object source) {
		if (source == null) {
			return null;
		}

		IStructuredSelection selection = new StructuredSelection(source);

		ConstraintEngine<View> constraintEngine = PropertiesRuntime.getConstraintEngine();
		Set<View> views = constraintEngine.getDisplayUnits(selection);
		if (!views.isEmpty()) {
			CreationContext creationContext = getCreationContext(context);
			creationContext.pushCreatedElement(source);
			creationContext.popCreatedElement(source);
		}

		return source;
	}

}

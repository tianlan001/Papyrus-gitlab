/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.expressions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.advanced.controls.propertiesadvancedcontrols.impl.ExtEditableReferenceDescriptionImpl;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQuery;
import org.eclipse.sirius.business.api.dialect.description.IInterpretedExpressionQueryProvider;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;

/**
 * An {@link IInterpretedExpressionQueryProvider} for Ext editable reference
 * description expressions.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@SuppressWarnings({ "removal", "deprecation" })
public class AdvancedControlsExpressionQueryProvider implements IInterpretedExpressionQueryProvider {

	@Override
	public Option<IInterpretedExpressionQuery> getExpressionQueryFor(EObject context,
			EStructuralFeature expressionAttribute) {
		if (isInsidePapyrusReferenceDescription(context)) {
			IInterpretedExpressionQuery value = new ExtEditableReferenceInterpretedExpressionQuery(context,
					expressionAttribute);
			return Options.newSome(value);
		} else {
			return Options.newNone();
		}
	}

	/**
	 * Tests whether a model element is part of a Ext Editable Reference
	 * description.
	 * 
	 * @param eObject the element to test.
	 * @return <code>true</code> if the element is part of a Ext Editable Reference
	 *         description, <code>false</code> otherwise.
	 */
	public boolean isInsidePapyrusReferenceDescription(EObject eObject) {
		EObject current = eObject;
		if (!(current instanceof ExtEditableReferenceDescriptionImpl)) {
			eObject.eContainer();
			while (current != null && !(current instanceof ExtEditableReferenceDescriptionImpl)) {
				current = current.eContainer();
			}
		}
		return current != null;
	}
}

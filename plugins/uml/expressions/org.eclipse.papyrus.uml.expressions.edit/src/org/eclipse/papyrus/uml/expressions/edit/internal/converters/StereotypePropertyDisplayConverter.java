/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.converters;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.emf.ui.converters.IDisplayConverter;
import org.eclipse.papyrus.infra.emf.expressions.IExpression;
import org.eclipse.papyrus.uml.expressions.edit.internal.utils.ProfileUtils;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This converter is used to convert a Stereotype Property into String (using its name) and vice versa
 */
public class StereotypePropertyDisplayConverter implements IDisplayConverter {

	/**
	 *
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.converters.IDisplayConverter#semanticToDisplayValue(java.lang.Object, org.eclipse.emf.ecore.EObject)
	 *
	 * @param canonicalValue
	 * @param editedObject
	 * @return
	 */
	@Override
	public Object semanticToDisplayValue(final Object canonicalValue, final EObject editedObject) {
		if (canonicalValue instanceof String && ((String) canonicalValue).length() > 0) {
			if (editedObject instanceof IExpression<?, ?>) {
				final IExpression<?, ?> exp = (IExpression<?, ?>) editedObject;
				// there is not yet common class between Expression with the field stereotypeQualifiedName
				final EStructuralFeature f = exp.eClass().getEStructuralFeature("stereotypeQualifiedName");

				if (null != f && f instanceof EAttribute && false == f.isMany()) {
					final Object value = exp.eGet(f);
					if (value instanceof String) {
						final Stereotype foundStereotype = ProfileUtils.findStereotype(exp, (String) value);
						if (null != foundStereotype) {
							return foundStereotype.getAttribute((String) canonicalValue, null);
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.converters.IDisplayConverter#displayToSemanticValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToSemanticValue(final Object displayValue, final EObject editedObject) {
		if (displayValue instanceof Property) {
			return ((Property) displayValue).getName();
		}
		return ""; //$NON-NLS-1$
	}

}

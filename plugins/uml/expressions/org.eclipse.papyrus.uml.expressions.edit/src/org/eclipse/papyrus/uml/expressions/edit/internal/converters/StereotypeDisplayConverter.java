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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.emf.ui.converters.IDisplayConverter;
import org.eclipse.papyrus.uml.expressions.edit.internal.utils.ProfileUtils;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This converter is used to convert a Stereotype into String (using its qualified name) and vice versa
 */
public class StereotypeDisplayConverter implements IDisplayConverter {

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
			final String stereotypeQualifiedName = (String) canonicalValue;
			final Stereotype ste = ProfileUtils.findStereotype(editedObject, stereotypeQualifiedName);
			if (null != ste) {
				return ste;
			}
		}
		// required to avoid to lost the value when we can't resolve it
		return canonicalValue;
	}

	/**
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.converters.IDisplayConverter#displayToSemanticValue(java.lang.Object)
	 *
	 * @param displayValue
	 * @return
	 */
	@Override
	public Object displayToSemanticValue(final Object displayValue, final EObject editedObject) {
		if (displayValue instanceof Stereotype) {
			return ((Stereotype) displayValue).getQualifiedName();
		}
		// required to avoid to lost the value when we can't resolve it
		return displayValue;
	}

}

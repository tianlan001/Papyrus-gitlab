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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.converter;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;

/**
 * The display converter for the single UML reference.
 */
public class SingleUMLReferenceDisplayConverter extends DisplayConverter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#displayToCanonicalValue(java.lang.Object)
	 */
	@Override
	public Object displayToCanonicalValue(final Object displayValue) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.data.convert.DisplayConverter#canonicalToDisplayValue(java.lang.Object)
	 */
	@Override
	public Object canonicalToDisplayValue(final Object canonicalValue) {
		Object result = null;
		// TODO : we should use the table label provider to do the conversion!
		final ILabelProvider provider = new UMLLabelProvider();
		if (canonicalValue instanceof DynamicEObjectImpl) {
			final EStructuralFeature feature = ((DynamicEObjectImpl) canonicalValue).eClass().getEStructuralFeature("base_Class"); //$NON-NLS-1$
			result = provider.getText(((DynamicEObjectImpl) canonicalValue).eGet(feature));
		} else {
			result = provider.getText(canonicalValue);
		}
		return result;
	}
}

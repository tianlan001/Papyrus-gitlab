/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and Others.
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
 *    Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.manager.axis;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.papyrus.uml.nattable.manager.axis.AbstractStereotypedElementUMLSynchronizedOnFeatureAxisManager;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Element;

/**
 * Axis Manager for dynamic Allocate.
 */
public class AllocateAxisManager extends AbstractStereotypedElementUMLSynchronizedOnFeatureAxisManager<DynamicEObjectImpl> {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.axis.AbstractAxisManager#canCreateAxisElement(java.lang.String)
	 */
	@Override
	public boolean canCreateAxisElement(final String elementId) {
		return UMLElementTypes.ABSTRACTION.getId().equals(elementId);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.AbstractStereotypedElementUMLSynchronizedOnFeatureAxisManager#isInstanceOfRequiredStereotypeApplication(java.lang.Object)
	 */
	@Override
	protected boolean isInstanceOfRequiredStereotypeApplication(final Object object) {
		return object instanceof DynamicEObjectImpl && ((DynamicEObjectImpl) object).eClass().getName().equals("Allocate"); //$NON-NLS-1$
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.AbstractStereotypedElementUMLSynchronizedOnFeatureAxisManager#getStereotypeApplication(org.eclipse.uml2.uml.Element)
	 */
	@Override
	protected DynamicEObjectImpl getStereotypeApplication(final Element el) {
		for (EObject stereotypeApplication : el.getStereotypeApplications()) {

			if (stereotypeApplication instanceof DynamicEObjectImpl && ((DynamicEObjectImpl) stereotypeApplication).eClass().getName().equals("Allocate")) { //$NON-NLS-1$
				return (DynamicEObjectImpl) stereotypeApplication;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.AbstractStereotypedElementUMLSynchronizedOnFeatureAxisManager#getStereotypeApplicationBasePropertyName()
	 */
	@Override
	protected String getStereotypeApplicationBasePropertyName() {
		return "base_Class"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.manager.axis.AbstractStereotypedElementUMLSynchronizedOnFeatureAxisManager#isAllowedAsBaseElement(org.eclipse.uml2.uml.Element)
	 */
	@Override
	protected boolean isAllowedAsBaseElement(final Element element) {
		return element instanceof Abstraction;
	}


}

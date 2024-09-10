/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and Others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	 Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import org.eclipse.papyrus.uml.service.types.utils.ClassifierUtils;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Property;

/**
 * Association Directed edit helper advice.
 * 
 * @since 3.0
 */
public class AssociationDirectedEditHelperAdvice extends AssociationEditHelperAdvice {

	/**
	 * The source end must be navigable for directed association.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AssociationEditHelperAdvice#addSourceInModel(org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Association)
	 */
	@Override
	protected void addSourceInModel(final Property sourceEnd, Classifier owner, Classifier targetType, Association association) throws UnsupportedOperationException {
		ClassifierUtils.addOwnedAttribute(owner, sourceEnd);
		sourceEnd.setIsNavigable(true);
		sourceEnd.setLower(0);
		sourceEnd.setUpper(1);
	}

	/**
	 * <pre>
	 * {@inheritDoc}
	 *
	 * The currently created {@link Association} in the default case (aggregation = none), and directed
	 * (meaning navigable in one direction only) which means the target end is owned by the association itself.
	 *
	 * Moreover this end name should not be set in that case, this latter rule is not followed here for now.
	 * </pre>
	 * 
	 * @see org.eclipse.papyrus.uml.service.types.helper.advice.AssociationEditHelperAdvice#addTargetInModel(org.eclipse.uml2.uml.Property, org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Classifier, org.eclipse.uml2.uml.Association)
	 */
	@Override
	protected void addTargetInModel(Property targetEnd, Classifier owner, Classifier sourceType, Association association) {
		association.getOwnedEnds().add(targetEnd);
	}

}

/**
 * Copyright (c) 2017 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.expressions.umlexpressions.custom;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsStereotypedWithExpressionImpl;
import org.eclipse.papyrus.uml.expressions.umlexpressions.utils.UMLExpressionsUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * 
 * Override the default implementation of the generated class
 *
 */
public class CustomIsStereotypedWithExpression extends IsStereotypedWithExpressionImpl {
	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsStereotypedWithExpressionImpl#evaluate(org.eclipse.emf.ecore.EObject)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public Boolean evaluate(final EObject context) {
		boolean result = false;
		if (null != this.stereotypeQualifiedName && !this.stereotypeQualifiedName.isEmpty() && context instanceof Element) {
			final Element el = (Element) context;
			final Iterator<Stereotype> iter = el.getAppliedStereotypes().iterator();
			while (iter.hasNext() && !result) {
				final Stereotype current = iter.next();
				if (this.stereotypeQualifiedName.equals(current.getQualifiedName())) {
					if (null != this.profileURI && !this.profileURI.isEmpty()) {
						// we use the uri of the top profile, because often the subprofiles don't have defined uri
						result = profileURI.equals(UMLExpressionsUtils.getTopProfileURI(current));
					} else {
						result = true;
					}
				}
			}
		}
		return Boolean.valueOf(result);
	}
}

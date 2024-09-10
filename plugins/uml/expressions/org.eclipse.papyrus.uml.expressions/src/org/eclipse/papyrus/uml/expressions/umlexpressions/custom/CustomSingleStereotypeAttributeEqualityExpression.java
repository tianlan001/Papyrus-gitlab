/**
 * Copyright (c) 2019 CEA LIST.
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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.SingleStereotypeAttributeEqualityExpressionImpl;
import org.eclipse.papyrus.uml.expressions.umlexpressions.utils.UMLExpressionsUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Custom implementation for {@link SingleStereotypeAttributeEqualityExpressionImpl}
 */
public class CustomSingleStereotypeAttributeEqualityExpression extends SingleStereotypeAttributeEqualityExpressionImpl {

	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.SingleStereotypeAttributeEqualityExpressionImpl#evaluate(org.eclipse.emf.ecore.EObject)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public Boolean evaluate(final EObject context) {
		boolean result = false;
		if (context instanceof Element) {
			if (null != this.propertyName && !this.propertyName.isEmpty()) {
				final Stereotype stereotype = getStereotype(context);
				if (null != stereotype) {
					final EObject steApp = ((Element) context).getStereotypeApplication(stereotype);
					final EClass steAppEClass = steApp.eClass();
					final EStructuralFeature feature = steAppEClass.getEStructuralFeature(this.propertyName);
					if (feature instanceof EAttribute && !feature.isMany()) {
						final Object value = steApp.eGet(feature);
						if (expectedValue == value) {
							result = true;
						} else if (expectedValue != null) {
							return result = this.expectedValue.equals(value.toString());
						}

					}
				}
			}
		}
		return Boolean.valueOf(result);
	}

	/**
	 *
	 * @param context
	 *            the context used to make the evaluation
	 * @return
	 *         the Stereotype matching the configuration of the expression
	 */
	private Stereotype getStereotype(final EObject context) {
		if (null != this.stereotypeQualifiedName
				&& !this.stereotypeQualifiedName.isEmpty()
				&& context instanceof Element) {
			final Element el = (Element) context;
			final Iterator<Stereotype> iter = el.getAppliedStereotypes().iterator();
			while (iter.hasNext()) {
				final Stereotype current = iter.next();
				if (this.stereotypeQualifiedName.equals(current.getQualifiedName())) {
					if (null != this.profileURI
							&& !this.profileURI.isEmpty()) {
						// we use the uri of the top profile, because often the subprofiles don't have defined uri
						if (profileURI.equals(UMLExpressionsUtils.getTopProfileURI(current))) {
							return current;
						}
					} else {
						return current;
					}
				}
			}
		}
		return null;
	}
}

/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.umlexpressions.custom;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfStereotypeExpressionImpl;
import org.eclipse.papyrus.uml.expressions.umlexpressions.utils.UMLExpressionsUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * @author as247872
 *
 */
public class CustomIsKindOfStereotypeExpression extends IsKindOfStereotypeExpressionImpl {

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.IExpression#evaluate(java.lang.Object)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public Boolean evaluate(EObject context) {
		boolean result = false;
		if (null != this.stereotypeQualifiedName && !this.stereotypeQualifiedName.isEmpty() && context instanceof Element) {
			final Element el = (Element) context;
			final Iterator<Stereotype> iter = el.getAppliedStereotypes().iterator();
			while (iter.hasNext() && !result) {
				// 1. first case : the current stereotype is the expected one
				final Stereotype current = iter.next();
				if (this.stereotypeQualifiedName.equals(current.getQualifiedName())) {
					if (null != this.profileURI && !this.profileURI.isEmpty()) {
						// we use the uri of the top profile, because often the subprofiles don't have defined uri
						result = profileURI.equals(UMLExpressionsUtils.getTopProfileURI(current));
					} else {
						result = true;
					}
				}

				// 2. second case : the current stereotype is a subtype of the expected one
				if (!result) {
					// we get the list of the stereotypes parent
					final List<Stereotype> parents = current.allParents()
							.stream()
							.filter(Stereotype.class::isInstance).map(Stereotype.class::cast)
							.filter(s -> s.getQualifiedName().equals(this.stereotypeQualifiedName))
							.collect(Collectors.toList());

					if (parents.size() > 0) {
						if (null != this.profileURI && !this.profileURI.isEmpty()) {
							Iterator<Stereotype> iterParents = parents.iterator();
							while (!result && iterParents.hasNext()) {
								result = profileURI.equals(UMLExpressionsUtils.getTopProfileURI(iterParents.next()));
							}
						} else {
							result = true;
						}
					}
				}
			}
		}
		return Boolean.valueOf(result);

	}

}

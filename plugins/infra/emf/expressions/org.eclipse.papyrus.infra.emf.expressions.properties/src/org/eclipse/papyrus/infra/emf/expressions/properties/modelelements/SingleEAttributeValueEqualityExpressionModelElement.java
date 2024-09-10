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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.expressions.properties.modelelements;

import java.util.Set;
import java.util.TreeSet;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.emf.ui.comparators.ENamedElementComparator;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression;
import org.eclipse.papyrus.infra.emf.expressions.properties.provider.SingleEAttributeContentProvider;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * ModelElement to {@link SingleEAttributeValueEqualityExpression}
 *
 */
public class SingleEAttributeValueEqualityExpressionModelElement extends EMFModelElement {

	/**
	 * Constructor.
	 *
	 * @param source
	 * @param domain
	 */
	public SingleEAttributeValueEqualityExpressionModelElement(final EObject source, final EditingDomain domain) {
		super(source, domain);
	}

	/**
	 * Constructor.
	 *
	 * @param source
	 */
	public SingleEAttributeValueEqualityExpressionModelElement(final EObject source) {
		super(source);
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 *
	 * @param propertyPath
	 * @return
	 */
	@Override
	public IStaticContentProvider getContentProvider(final String propertyPath) {
		if (propertyPath.equals("eAttribute")) { //$NON-NLS-1$
			final EditingDomain domain = getDomain();
			final ResourceSet set = domain.getResourceSet();
			final Set<EPackage> packages = new TreeSet<EPackage>(new ENamedElementComparator());

			for (final Resource tmp : set.getResources()) {
				for (EObject current : tmp.getContents()) {
					final EPackage p = current.eClass().getEPackage();
					packages.add(p);
				}
			}

			return new SingleEAttributeContentProvider(packages);
		}
		return super.getContentProvider(propertyPath);
	}

}

/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.decoratormodel.properties.elements;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElement;
import org.eclipse.papyrus.uml.properties.modelelement.UMLModelElementFactory;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;

/**
 * Properties model element factory for profile applications.
 */
public class PackageModelElementFactory extends UMLModelElementFactory {

	public PackageModelElementFactory() {
		super();
	}

	@Override
	protected UMLModelElement doCreateFromSource(Object source, DataContextElement context) {
		Element umlSource = UMLUtil.resolveUMLElement(source);
		if (umlSource instanceof Package) {
			return createPackageElement((Package) umlSource, context);
		}

		return super.doCreateFromSource(source, context);
	}

	protected UMLModelElement createPackageElement(Package package_, DataContextElement context) {
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(package_);
		return new PackageModelElement(package_, domain);
	}
}

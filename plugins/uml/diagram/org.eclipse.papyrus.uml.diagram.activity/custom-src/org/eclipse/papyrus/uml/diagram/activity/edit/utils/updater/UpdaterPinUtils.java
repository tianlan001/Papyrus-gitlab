/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Jérémie TATIBOUET (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater;

import java.util.Iterator;

import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;

/**
 *
 * Automated pin derivation for AcceptEventAction and AcceptCallAction
 *
 */
public class UpdaterPinUtils {

	private final static String UML_PRIMITIVE_TYPES_URI = "http://www.omg.org/spec/PrimitiveTypes/20131001";

	/**
	 * This method test if the UML primitive type package is already imported in the model
	 *
	 * @param entryPoint
	 * @return return true if the UML primitive type package is already imported in the model, false otherwise
	 */
	public static boolean isPrimitiveTypeLibraryImported(Element entryPoint) {
		boolean imported = false;
		if (entryPoint != null) {
			Model model = entryPoint.getModel();
			Iterator<PackageImport> packageImportIterator = model.getPackageImports().iterator();
			while (!imported && packageImportIterator.hasNext()) {
				PackageImport packageImport = packageImportIterator.next();
				if (packageImport.getImportedPackage().isModelLibrary()
						&& UML_PRIMITIVE_TYPES_URI.equals(packageImport.getImportedPackage().getURI())) {
					imported = true;
				}
			}
		}
		return imported;
	}

	/**
	 * Get the UML primitive type package
	 *
	 * @param element
	 * @return the UML primitive type package
	 */
	public static Package getPrimitiveTypesPackage(Element element) {
		Package primitiveTypesPackage = null;
		Model model = element.getModel();
		Iterator<PackageImport> packageImportIterator = model.getPackageImports().iterator();
		while (primitiveTypesPackage == null && packageImportIterator.hasNext()) {
			PackageImport packageImport = packageImportIterator.next();
			if (packageImport.getImportedPackage().isModelLibrary()
					&& packageImport.getImportedPackage().getURI().equals(UML_PRIMITIVE_TYPES_URI)) {
				primitiveTypesPackage = packageImport.getImportedPackage();
			}
		}
		return primitiveTypesPackage;
	}

}
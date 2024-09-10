/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.service;

import java.util.Iterator;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Package;

/**
 * Helper for access to the <em>EMF Generator Model</em> for an UML profile.
 */
class GenModelHelper {

	private static final GenModelHelper INSTANCE;

	static {
		GenModelHelper instance;

		try {
			instance = new Default();
		} catch (Exception | LinkageError e) {
			// The Ecore codegen dependency is optional, so if it isn't installed, then
			// obviously there is no genmodel for the profile.
			instance = new GenModelHelper();
		}

		INSTANCE = instance;
	}

	private GenModelHelper() {
		super();
	}

	/**
	 * Query whether an UML package has a generator model.
	 *
	 * @param package_
	 *            an UML package
	 * @param resourceSet
	 *            the resource context in which it is loaded
	 * @return whether the package has a generator model that imports it
	 */
	public boolean hasGeneratorModel(Package package_, ResourceSet resourceSet) {
		EObject generatorModel = getGeneratorModel(package_, resourceSet);

		return generatorModel != null && isForeignModelOf(package_, generatorModel, resourceSet);
	}

	EObject getGeneratorModel(Package package_, ResourceSet resourceSet) {
		return null;
	}

	boolean isForeignModelOf(Package package_, EObject generatorModel, ResourceSet context) {
		return false;
	}

	public static GenModelHelper getInstance() {
		return INSTANCE;
	}

	//
	// Nested types
	//

	private static final class Default extends GenModelHelper {

		private static final String UML_IMPORTER_ID = "org.eclipse.uml2.uml.ecore.importer"; //$NON-NLS-1$
		private static final String GENMODEL_EXTENSION = "genmodel"; //$NON-NLS-1$

		@Override
		GenModel getGeneratorModel(Package package_, ResourceSet resourceSet) {
			URI genmodelURI = package_.eResource().getURI().trimFileExtension().appendFileExtension(GENMODEL_EXTENSION);
			return UML2Util.load(resourceSet, genmodelURI, GenModelPackage.Literals.GEN_MODEL);
		}

		@Override
		boolean isForeignModelOf(Package package_, EObject generatorModel, ResourceSet context) {
			boolean result = false;

			GenModel genmodel = (GenModel) generatorModel;
			if (UML_IMPORTER_ID.equals(genmodel.getImporterID())) {
				URIConverter converter = context.getURIConverter();
				URI genmodelURI = converter.normalize(EcoreUtil.getURI(genmodel).trimFragment());
				URI sourceURI = converter.normalize(EcoreUtil.getURI(package_).trimFragment());

				for (Iterator<String> foreignModel = genmodel.getForeignModel().iterator(); !result && foreignModel.hasNext();) {
					URI foreignModelURI = URI.createURI(foreignModel.next()).resolve(genmodelURI);
					result = foreignModelURI.equals(sourceURI);
				}
			}

			return result;
		}

	}

}

/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.resource.UML302UMLResource;

/**
 * This class is used to load the "/org.eclipse.uml2.uml/model/UML.ecore"
 * resource in order to use its documentation, which is not present in the
 * "/org.eclipse.uml2.uml/src/org/eclipse/uml2/uml/internal/impl/uml.ecore"
 * resource.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public final class UMLDocumentationResource {

	/**
	 * URI of the UML model.
	 */
	private static final String UML_MODEL_URI = "platform:/plugin/org.eclipse.uml2.uml/model/UML.ecore"; //$NON-NLS-1$

	/**
	 * The singleton instance.
	 */
	private static UMLDocumentationResource instance;

	/**
	 * The EPackage of the "UML.ecore" resource.
	 */
	private static EPackage uml2EPackage;

	/**
	 * The constructor used to initialize the singleton.
	 */
	private UMLDocumentationResource() {
		URI uri = URI.createURI(UML_MODEL_URI, true);
		Resource resource = UML302UMLResource.Factory.INSTANCE.createResource(uri);
		try {
			resource.load(null);
		} catch (IOException exception) {
			throw new WrappedException(exception);
		}
		uml2EPackage = (EPackage) resource.getContents().get(0);
	}

	/**
	 * Gets the singleton instance for this class.
	 * 
	 * @return the singleton instance
	 */
	public static UMLDocumentationResource getInstance() {
		if (instance == null) {
			instance = new UMLDocumentationResource();
		}
		return instance;
	}

	/**
	 * Gets the EPackage of the "UML.ecore" resource.
	 * 
	 * @return the EPackage of the "UML.ecore" resource
	 */
	public EPackage getUml2EPackage() {
		return uml2EPackage;
	}
}
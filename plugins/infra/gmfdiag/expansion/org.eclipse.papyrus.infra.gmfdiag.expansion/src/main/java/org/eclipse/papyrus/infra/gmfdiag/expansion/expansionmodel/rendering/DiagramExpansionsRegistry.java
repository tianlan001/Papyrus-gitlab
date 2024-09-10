/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.papyrus.infra.gmfdiag.common.Activator;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.DiagramExpansion;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.UseContext;
import org.osgi.framework.Bundle;

/**
 * This class is used to load all extension point call org.eclipse.papyrus.infra.gmfdiag.diagramexpansion
 * It gives the set of all Diagram expansion that has to be used
 * #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_060
 *
 */
public class DiagramExpansionsRegistry {

	protected final static String EXPANSION_MODEL_EXTENSION_ID = "org.eclipse.papyrus.infra.gmfdiag.common.diagramExpansion"; //$NON-NLS-1$
	protected final String MODEL_ID = "model"; //$NON-NLS-1$
	protected ArrayList<DiagramExpansion> diagramExpansions = new ArrayList<DiagramExpansion>();
	protected HashMap<String, UseContext> usages = new HashMap<String, UseContext>();
	public HashMap<String, ChildrenListRepresentation> mapChildreen = new HashMap<String, ChildrenListRepresentation>();

	/**
	 *
	 * Constructor.
	 *
	 */
	public DiagramExpansionsRegistry() {
		init();
	}

	/**
	 * this method load the extension points
	 */
	public void init() {
		// Obtain a new resource set
		ResourceSet resourceSet = new ResourceSetImpl();

		// Reading data from plugins
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(EXPANSION_MODEL_EXTENSION_ID);
		for (int i = 0; i < configElements.length; i++) {
			DiagramExpansion diagramExpansion = initializeOneModel(resourceSet, configElements[i]);
			if(diagramExpansion!=null){
				installExpansionModel(diagramExpansion);
			}
		}

	}

	protected void installExpansionModel(DiagramExpansion diagramExpansion) {
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(diagramExpansion);
		if (diagnostic.getSeverity() == Diagnostic.OK) {
			// load only valid models
			diagramExpansions.add(diagramExpansion);
			for (UseContext usage : diagramExpansion.getUsages()) {
				if ((usages.get(usage.getDiagramType())) == null) {
					usages.put(usage.getDiagramType(), usage);
					ChildrenListRepresentation childrenListRepresentation = new ChildrenListRepresentation(usage);
					mapChildreen.put(usage.getDiagramType(), childrenListRepresentation);
					Activator.log.trace(Activator.EXPANSION_TRACE,childrenListRepresentation.toString());
				} else {
					// there is two ewtension oon the smae diagram!
					// do not load --error
					Activator.log.warn("Several expansions has been defined for the same diagram");
				}
			}
		} else {
			Activator.log.warn("Expansion model not loaded");
			for (Iterator<Diagnostic> i = diagnostic.getChildren().iterator(); i.hasNext();) {
				Diagnostic childDiagnostic = i.next();
				switch (childDiagnostic.getSeverity()) {
				case Diagnostic.ERROR:
				case Diagnostic.WARNING:
					Activator.log.warn("\t" + childDiagnostic.getMessage());
				}
			}
		}
	}

	/**
	 *
	 * @return the set of DiagramExpansion
	 */
	public ArrayList<DiagramExpansion> getDiagramExpansions() {
		return diagramExpansions;
	}

	/**
	 * get the usecontext associate to a diagram type
	 *
	 * @param diagramType
	 *            the id of a diagram of the id of a view prototype
	 * @return a useConstext or null if not usage exist.
	 */
	public UseContext getUsage(String diagramType) {
		return usages.get(diagramType);
	}


	/**
	 * Load one model
	 *
	 * @param element
	 *            the extension point
	 */
	private DiagramExpansion initializeOneModel(ResourceSet resourceSet, IConfigurationElement element) {
		return createExtension(resourceSet, element, element.getAttribute(MODEL_ID));

	}

	/**
	 * Load a resource instanceof DiagramExpansion
	 *
	 * @param resourceSet
	 *            the resource set in which to load the menu model
	 * @param element
	 *            the extension point
	 * @param classAttribute
	 *            the name of the resource to load
	 * @return null or the Diagram Expansion model
	 * @throws Exception
	 *             if the resource is not loaded
	 */
	private static DiagramExpansion createExtension(final ResourceSet resourceSet, final IConfigurationElement element, final String classAttribute) {
		URL url=null;
		try {
			Bundle extensionBundle = Platform.getBundle(element.getDeclaringExtension().getNamespaceIdentifier());
			url = extensionBundle.getResource(classAttribute);

			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
			if (url != null) {
				URI uri = URI.createURI(url.toURI().toASCIIString());

				// Get the resource
				Resource resource = resourceSet.getResource(uri, true);
				if (resource.getContents().get(0) instanceof DiagramExpansion) {
					return (DiagramExpansion) resource.getContents().get(0);
				}
			}
			return null;
		} catch (Exception e) {
			Activator.log.error("Unable to load an extension for "+EXPANSION_MODEL_EXTENSION_ID +" with the url"+url, e); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * this method is used to load a model expansion at runtime.
	 * see #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_0100
	 *
	 * @param resourceURI
	 *            the URI of the model expansion.
	 */
	public void loadExpansion(URI resourceURI) {
		// Obtain a new resource set
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(resourceURI, true);
		if (resource.getContents().get(0) instanceof DiagramExpansion) {
			DiagramExpansion diagramExpansion = (DiagramExpansion) resource.getContents().get(0);
			installExpansionModel(diagramExpansion);
		}
	}

	/**
	 * this method is used to clear the registry about all expansion.
	 */
	public void clear() {
		diagramExpansions.clear();
		usages.clear();
		mapChildreen.clear();
	}


}
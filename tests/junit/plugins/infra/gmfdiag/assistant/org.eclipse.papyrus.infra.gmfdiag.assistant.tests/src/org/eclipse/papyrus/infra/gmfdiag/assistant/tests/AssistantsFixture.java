/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.assistant.tests;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.IModelingAssistantModelProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.DefaultModelingAssistantModelProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.ModelingAssistantModelRegistry;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * A JUnit test rule that sets up modeled diagram assistants and their supporting element types for the duration of a test.
 */
@SuppressWarnings("restriction")
public class AssistantsFixture extends TestWatcher {

	private final ResourceSet resourceSet = new ResourceSetImpl();

	private final String assistantsModelPath;

	private IModelingAssistantModelProvider assistantModelProvider;

	private String elementTypesID;

	public AssistantsFixture(String assistantsModelPath) {
		super();

		this.assistantsModelPath = assistantsModelPath;
	}

	@Override
	protected void starting(Description description) {
		URI assistantsModelURI = getModelURI(assistantsModelPath, description);

		// Load the corresponding element types, if they are a custom set
		URI elementTypesModelURI = getModelURI(assistantsModelPath.replaceFirst("\\.assistants$", ".elementtypesconfigurations"), description);
		if (elementTypesModelURI != null) {
			ElementTypeSetConfiguration elementTypes = UMLUtil.load(resourceSet, elementTypesModelURI, ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);

			try {
				ElementTypeSetConfigurationRegistry.getInstance().loadElementTypeSetConfiguration(TypeContext.getDefaultContext().getId(), (ElementTypeSetConfiguration) elementTypes);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			elementTypesID = JUnitUtils.getTestClass(description).getName();

			try {
				String contextId = TypeContext.getDefaultContext().getId();

				if (!ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations().containsKey(contextId)) {
					ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations().put(contextId, new HashMap<String, ElementTypeSetConfiguration>());
				}
				ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations().get(contextId).put(elementTypesID, elementTypes);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// And then the assistants that reference them
		assistantModelProvider = registerAssistants(assistantsModelURI);
	}

	protected URI getModelURI(String path, Description testDescription) {
		Bundle testBundle = FrameworkUtil.getBundle(JUnitUtils.getTestClass(testDescription));
		URL url = testBundle.getEntry(path);
		return (url == null) ? null : URI.createURI(url.toExternalForm(), true);
	}

	protected IModelingAssistantModelProvider registerAssistants(URI assistantsModelURI) {
		IModelingAssistantModelProvider result = new DefaultModelingAssistantModelProvider(assistantsModelURI);
		ModelingAssistantModelRegistry.getInstance().loadModels(result);
		return result;
	}

	@Override
	protected void finished(Description description) {
		ModelingAssistantModelRegistry.getInstance().unloadModels(assistantModelProvider);
		assistantModelProvider = null;

		if (elementTypesID != null) {
			try {
				String contextId = TypeContext.getDefaultContext().getId();

				ElementTypeSetConfigurationRegistry.getInstance().unload(contextId, elementTypesID);
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		EMFHelper.unload(resourceSet);
	}
}

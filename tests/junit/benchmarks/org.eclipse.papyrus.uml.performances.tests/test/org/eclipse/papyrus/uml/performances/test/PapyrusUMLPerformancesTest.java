/*****************************************************************************
 * Copyright (c) 2019, 2023 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Pauline DEVILLE (CEA LIST) - bug 562218
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.performances.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.commands.CreateModelInModelSetCommand;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class PapyrusUMLPerformancesTest extends AbstractUMLPerformancesTest {

	protected ServicesRegistry servicesRegistry;

	protected ModelSet modelSet;

	@Test
	public void create_50000_Elements() {
		createModelAndClasses(50000);
	}

	@Override
	protected void createResources(final long size) {
		servicesRegistry = createServicesRegistry();
		Assert.assertNotNull(servicesRegistry);

		try {
			this.modelSet = servicesRegistry.getService(ModelSet.class);
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
		Assert.assertNotNull(this.modelSet);

		createBaseURI(size);
		final URI diURI = umlURI.trimFileExtension().appendFileExtension(DiModel.MODEL_FILE_EXTENSION);

		final RecordingCommand command = new CreateModelInModelSetCommand(this.modelSet, diURI);

		modelSet.getTransactionalEditingDomain().getCommandStack().execute(command);
		save();
		final Resource umlResource = modelSet.getResource(umlURI, true);

		// create the architecture context
		final ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils(modelSet);

		final String contextId = "org.eclipse.papyrus.infra.services.edit.TypeContext";
		final List<String> viewpointsIds = new ArrayList<>();
		// we assume we are in pure UML
		viewpointsIds.add("org.eclipse.papyrus.uml.analysis");
		viewpointsIds.add("org.eclipse.papyrus.uml.design");

		final String[] stringsViewPointIds = viewpointsIds.toArray(new String[viewpointsIds.size()]);

		modelSet.getTransactionalEditingDomain().getCommandStack().execute(helper.createNewModel(contextId, stringsViewPointIds));
		save();

		Assert.assertNotNull(umlResource);
		rootModel = (Model) umlResource.getContents().get(0);

		try {
			initServicesRegistry(servicesRegistry);// we need to have a root in the model to init it!?!?
		} catch (ServiceException e) {
			System.err.println(e.getMessage());
		}
	}

	protected ServicesRegistry createServicesRegistry() {
		ServicesRegistry result = null;

		try {
			result = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
		} catch (ServiceException e) {
			// couldn't create the registry? Fatal problem
		}

		try {
			// have to create the model set and populate it with the DI model before initializing other services that actually need the DI model, such as the SashModel Manager service
			result.startServicesByClassKeys(ModelSet.class);
		} catch (ServiceException ex) {
			// Ignore this exception: some services may not have been loaded, which is probably normal at this point
		}

		return result;
	}

	protected void initServicesRegistry(ServicesRegistry registry) throws ServiceException {
		try {
			registry.startRegistry();
		} catch (ServiceException ex) {
			// Ignore this exception: some services may not have been loaded, which is probably normal at this point
		}

		registry.getService(IPageManager.class);
	}

	@Override
	protected void createUMLClass(final org.eclipse.uml2.uml.Package owner, final long index) {
		final CreateElementRequest createElementRequest = new CreateElementRequest(owner, UMLElementTypes.CLASS);
		final StringBuilder builder = new StringBuilder("Class_");
		builder.append(index);
		createElementRequest.setLabel(builder.toString());
		final CreateElementCommand creationElementCommand = new CreateElementCommand(createElementRequest);

		try {
			creationElementCommand.execute(new NullProgressMonitor(), null);
		} catch (final ExecutionException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	protected void save() {
		try {
			modelSet.save(new NullProgressMonitor());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void dispose() throws Exception {
		this.modelSet.unload();
		this.modelSet = null;
		this.servicesRegistry.disposeRegistry();
		this.servicesRegistry = null;
		super.dispose();
	}

}

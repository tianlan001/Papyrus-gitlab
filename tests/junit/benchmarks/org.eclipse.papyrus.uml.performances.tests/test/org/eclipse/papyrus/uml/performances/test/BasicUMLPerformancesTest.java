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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.performances.test;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.WorkspaceEditingDomainFactory;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Test;

/**
 *
 */
public class BasicUMLPerformancesTest extends AbstractUMLPerformancesTest {

	private Resource resource;

	private TransactionalEditingDomain domain;

	@Test
	public void create_50000_Elements() {
		createModelAndClasses(50000);
	}

	@Override
	protected void createResources(final long size) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		createBaseURI(size);
		resource = resourceSet.createResource(umlURI);

		rootModel = UMLFactory.eINSTANCE.createPackage();
		rootModel.setName("RootPackage");
		resource.getContents().add(rootModel);
		save();
		domain = WorkspaceEditingDomainFactory.INSTANCE.createEditingDomain(resource.getResourceSet());
	}

	@Override
	protected void createUMLClass(final org.eclipse.uml2.uml.Package owner, final long index) {
		final Class createdClass = UMLFactory.eINSTANCE.createClass();
		final StringBuilder builder = new StringBuilder("Class_");
		builder.append(index);
		createdClass.setName(builder.toString());
		final AbstractTransactionalCommand createCommand = new AbstractTransactionalCommand(domain, "Create class", Collections.singletonList(resource)) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				owner.getPackagedElements().add(createdClass);
				return CommandResult.newOKCommandResult();
			}
		};
		try {
			createCommand.execute(new NullProgressMonitor(), null);
		} catch (final ExecutionException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	protected void save() {
		try {
			resource.save(null);
		} catch (final IOException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void dispose() throws Exception {
		resource.unload();
		resource = null;
		domain = null;
		super.dispose();
	}
}

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

package org.eclipse.papyrus.uml.service.types.tests.creation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterables;

/**
 * Regressions tests for bug 465899: creation of a connector on a port inherited
 * from a class in a read-only resource.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=465899
 */
@PluginResource("resource/bug465899/model.di")
public class ConnectorReadOnlyTestBug465899 extends AbstractPapyrusTest {

	@Rule
	public final ModelSetFixture model = new ModelSetFixture();

	public ConnectorReadOnlyTestBug465899() {
		super();
	}

	@Test
	public void createConnectorSourceReadOnly() throws Exception {
		try {
			Class composite = (Class) model.getModel().getOwnedType("Composite");
			Port port = Iterables.filter(composite.getAllAttributes(), Port.class).iterator().next();
			Property part = composite.getOwnedAttribute("part", null);
	
			IClientContext context = TypeContext.getContext(model.getResourceSet());
			IElementEditService service = ElementEditServiceUtils.getCommandProvider(UMLElementTypes.PROPERTY_PART, context);
			ICommand command = service.getEditCommand(new CreateRelationshipRequest(composite, port, part, UMLElementTypes.CONNECTOR));
	
			assertThat("No command provided", command, notNullValue());
			assertThat("Command not executable", command.canExecute(), is(true));
		} catch (ServiceException e) {
			System.out.println(e);
		}
	}

	@Test
	public void createConnectorTargetReadOnly() throws Exception {
		try {
			Class composite = (Class) model.getModel().getOwnedType("Composite");
			Port port = Iterables.filter(composite.getAllAttributes(), Port.class).iterator().next();
			Property part = composite.getOwnedAttribute("part", null);
	
			IClientContext context = TypeContext.getContext(model.getResourceSet());
			IElementEditService service = ElementEditServiceUtils.getCommandProvider(UMLElementTypes.PORT, context);
			ICommand command = service.getEditCommand(new CreateRelationshipRequest(composite, part, port, UMLElementTypes.CONNECTOR));
	
			assertThat("No command provided", command, notNullValue());
			assertThat("Command not executable", command.canExecute(), is(true));
		} catch (ServiceException e) {
			System.out.println(e);
		}
	}

	// Association between classes, the classes owns both member ends, so they are altered.
	@Test
	public void createAssociationWritable() throws Exception {
		try {
			Class class1 = (Class) model.getModel().getOwnedMember("Class1");
			Class class2 = (Class) model.getModel().getOwnedMember("Class2");
	
			IClientContext context = TypeContext.getContext(model.getResourceSet());
			IElementEditService service = ElementEditServiceUtils.getCommandProvider(UMLElementTypes.CLASS, context);
			ICommand command = service.getEditCommand(new CreateRelationshipRequest(model.getModel(), class1, class2, UMLElementTypes.ASSOCIATION));
	
			assertThat("No command provided", command, notNullValue());
			assertThat("Command is executable", command.canExecute(), is(false));
		} catch (ServiceException e) {
			System.out.println(e);
		}
	}

	// Association between classes, the classes owns both member ends, so they are altered.
	@Test
	public void createAssociationReadOnly() throws Exception {
		try {
			Class class1 = (Class) model.getModel().getImportedMember("LibraryClass1");
			Class class2 = (Class) model.getModel().getImportedMember("LibraryClass2");
	
			IClientContext context = TypeContext.getContext(model.getResourceSet());
			IElementEditService service = ElementEditServiceUtils.getCommandProvider(UMLElementTypes.CLASS, context);
			ICommand command = service.getEditCommand(new CreateRelationshipRequest(model.getModel(), class1, class2, UMLElementTypes.ASSOCIATION));
	
			assertThat("No command provided", command, notNullValue());
			assertThat("Command is executable", command.canExecute(), is(false));
		} catch (ServiceException e) {
			System.out.println(e);
		}
	}

	// Association between usecase and actor owns both member ends, so does not alter the
	// end types.
	@Test
	public void createAssociationReadOnly_usecase() throws Exception {
		try {
			UseCase usecase = (UseCase) model.getModel().getImportedMember("UseCase1");
			Actor actor = (Actor) model.getModel().getImportedMember("Actor1");
	
			IClientContext context = TypeContext.getContext(model.getResourceSet());
			IElementEditService service = ElementEditServiceUtils.getCommandProvider(UMLElementTypes.USE_CASE, context);
			ICommand command = service.getEditCommand(new CreateRelationshipRequest(model.getModel(), usecase, actor, UMLElementTypes.ASSOCIATION));
	
			assertThat("No command provided", command, notNullValue());
			assertThat("Command is not executable", command.canExecute(), is(true));
		} catch (ServiceException e) {
			System.out.println(e);
		}
	}
}

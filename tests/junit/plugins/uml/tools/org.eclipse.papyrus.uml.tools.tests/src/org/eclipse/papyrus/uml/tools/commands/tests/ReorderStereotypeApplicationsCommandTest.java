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

package org.eclipse.papyrus.uml.tools.commands.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.junit.matchers.CommandMatchers;
import org.eclipse.papyrus.uml.tools.commands.ReorderStereotypeApplicationsCommand;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the {@link ReorderStereotypeApplicationsCommand} class.
 */
public class ReorderStereotypeApplicationsCommandTest {

	private EditingDomain domain;
	private Class fixture;
	private EList<EObject> resourceContents;
	private EList<EObject> originalContents;

	private Stereotype s1;
	private Stereotype s2;
	private Stereotype s3;
	private Stereotype s4;

	private boolean skipUndoRedo;

	@Test
	public void reorderSingleStereotype() {
		execute(List.of(s1, s3, s2, s4));
	}

	@Test
	public void reorderMultipleStereotypes() {
		execute(List.of(s2, s1, s4, s3));
	}

	@Test
	public void attemptReorderOfTooFewStereotypes() {
		assertNotExecutable(List.of(s1, s3, s4));
	}

	@Test
	public void attemptReorderOfTooManyStereotypes() {
		fixture.unapplyStereotype(s2);

		assertNotExecutable(List.of(s1, s3, s2, s4));
	}

	@Test
	public void attemptReorderOfStereotypesAcrossResources() {
		Resource resource = new ResourceImpl(URI.createURI("test://second.uml"));
		domain.getResourceSet().getResources().add(resource);
		resource.getContents().add(fixture.getStereotypeApplication(s2));

		assertNotExecutable(List.of(s1, s3, s2, s4));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		domain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE), new BasicCommandStack());

		Resource resource = new ResourceImpl(URI.createURI("test://model.uml"));
		domain.getResourceSet().getResources().add(resource);
		resourceContents = resource.getContents();

		Model model = UMLFactory.eINSTANCE.createModel();
		resource.getContents().add(model);
		fixture = model.createOwnedClass("Fixture", false);
		Class class2 = model.createOwnedClass("Class2", false);

		Profile standard = UML2Util.load(domain.getResourceSet(), URI.createURI(UMLResource.STANDARD_PROFILE_URI), UMLPackage.Literals.PROFILE);
		s1 = standard.getOwnedStereotype("Auxiliary");
		s2 = standard.getOwnedStereotype("Focus");
		s3 = standard.getOwnedStereotype("Type");
		s4 = standard.getOwnedStereotype("Utility");
		model.applyProfile(standard);

		// Mix up the ordering of stereotype applications between these two elements
		fixture.applyStereotype(s1);
		class2.applyStereotype(s1);
		fixture.applyStereotype(s2);
		class2.applyStereotype(s2);
		fixture.applyStereotype(s3);
		class2.applyStereotype(s3);
		fixture.applyStereotype(s4);
		class2.applyStereotype(s4);
	}

	@After
	public void undoRedo() {
		try {
			if (skipUndoRedo) {
				return;
			}

			List<EObject> contentsAfterExecute = new BasicEList.FastCompare<>(resourceContents);
			List<Stereotype> orderAfterExecute = getStereotypesAppliedInOrder(fixture);

			assertThat("Command is not undoable", domain.getCommandStack().canUndo());
			domain.getCommandStack().undo();

			assertThat("Undo did not restore stereotype application order", resourceContents, is(originalContents));
			assertThat("Applied stereotypes not recalculated in order after undo", getStereotypesAppliedInOrder(fixture),
					is(List.of(s1, s2, s3, s4)));

			assertThat("Command is not redoable", domain.getCommandStack().canRedo());
			domain.getCommandStack().redo();

			assertThat("Redo did not correctly set stereotype application order", resourceContents, is(contentsAfterExecute));
			assertThat("Applied stereotypes not recalculated in order after redo", getStereotypesAppliedInOrder(fixture),
					is(orderAfterExecute));
		} finally {
			EMFHelper.unload(domain.getResourceSet());
		}
	}

	List<Stereotype> getStereotypesAppliedInOrder(Element element) {
		return element.getStereotypeApplications().stream().map(UMLUtil::getStereotype).collect(Collectors.toList());
	}

	void execute(List<Stereotype> order) {
		skipUndoRedo = true; // In case of assertion failure here

		ReorderStereotypeApplicationsCommand command = new ReorderStereotypeApplicationsCommand(fixture, order);
		assertThat(command, CommandMatchers.EMF.canExecute());

		originalContents = new BasicEList.FastCompare<>(resourceContents);
		domain.getCommandStack().execute(command);

		assertThat("Stereotype applications incorrectly reordered", getStereotypesAppliedInOrder(fixture), is(order));
		skipUndoRedo = false;
	}

	void assertNotExecutable(List<Stereotype> order) {
		skipUndoRedo = true;

		ReorderStereotypeApplicationsCommand command = new ReorderStereotypeApplicationsCommand(fixture, order);
		assertThat(command, not(CommandMatchers.EMF.canExecute()));
	}

}

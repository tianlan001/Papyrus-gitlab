/*****************************************************************************
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
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

package org.eclipse.papyrus.uml.service.types.tests.registry;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.matchers.CommandMatchers;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the unified {@code StereotypeMatcherAdviceConfiguration}.
 *
 * @see <a href="http://eclip.se/568853">bug 568853</a>
 */
@PluginResource("resource/bug568853/bug568853.di")
public class StereotypeMatcherAdviceTest {

	@Rule
	public final ModelSetFixture model = new ServiceRegistryModelSetFixture();

	@Test
	public void matchExistingElement() {
		Type suite = model.getModel().getOwnedType("MainTests");
		IElementType[] types = getTypesOf(suite);

		assertThat(Arrays.asList(types), hasItem(elementTypeIDThat(qualified("TestSuite"))));
	}

	@Test
	public void createNewElement() {
		org.eclipse.uml2.uml.Class suite = (org.eclipse.uml2.uml.Class) model.getModel().getOwnedType("MainTests");

		IElementType testSuiteType = getType("TestSuite");
		IElementType testCaseType = getType("TestCase");

		CreateElementRequest request = new CreateElementRequest(suite, testCaseType);
		ICommand command = edit(testSuiteType).getEditCommand(request);

		model.execute(command);
		Operation operation = (Operation) command.getCommandResult().getReturnValue();

		assertThat(suite.getOwnedOperations().size(), is(2));
		assertThat(suite.getOwnedOperations(), hasItem(operation));

		assertThat("Stereotype not applied to new element", operation.isStereotypeApplied(operation.getApplicableStereotype("UnitTest::TestCase")));
	}

	@Test
	public void stereotypeNotApplicableByProfileURI() {
		applyImpostorProfile("UnitTest"); // Same profile name as referenced by the advice

		IElementType testSuiteType = getType("TestSuite");

		// The stereotype is applicable by qualified name, but the wrong profile URI is applied, so a test case cannot be created
		CreateElementRequest request = new CreateElementRequest(model.getModel(), testSuiteType);
		ICommand command = edit(UMLElementTypes.PACKAGE).getEditCommand(request);
		if (command != null) {
			assertThat(command, not(CommandMatchers.GMF.canExecute()));
		}
	}

	@Test
	public void stereotypeNotApplicableByQualifiedName() {
		applyImpostorProfile("Impostor"); // Different profile name as referenced by the advice

		IElementType testSuiteType = getType("TestSuite");
		IElementType testCaseType = getType("TestCase");

		org.eclipse.uml2.uml.Class suite = (org.eclipse.uml2.uml.Class) model.getModel().getOwnedType("MainTests");

		// The advice does not reference a Profile URI, but the stereotype is not applicable by qualified name
		CreateElementRequest request = new CreateElementRequest(suite, testCaseType);
		ICommand command = edit(testSuiteType).getEditCommand(request);
		if (command != null) {
			assertThat(command, not(CommandMatchers.GMF.canExecute()));
		}
	}

	//
	// Test framework
	//

	IElementType[] getTypesOf(EObject object) {
		try {
			return ElementTypeRegistry.getInstance().getAllTypesMatching(object, TypeContext.getContext(model.getResourceSet()));
		} catch (ServiceException e) {
			throw new AssertionError(e);
		}
	}

	IElementType getType(String name) {
		try {
			Matcher<IElementType> matcher = elementTypeIDThat(qualified(name));
			return Stream.of(ElementTypeRegistry.getInstance().getSpecializationTypes(TypeContext.getContext(model.getResourceSet())))
					.filter(matcher::matches)
					.findAny()
					.orElseThrow(() -> new AssertionError("No such element type: " + name));
		} catch (ServiceException e) {
			throw new AssertionError(e);
		}
	}

	IElementEditService edit(IElementType type) {
		try {
			return ElementEditServiceUtils.getCommandProvider(type, TypeContext.getContext(model.getResourceSet()));
		} catch (ServiceException e) {
			throw new AssertionError(e);
		}
	}

	Profile applyImpostorProfile(String name) {
		EcoreUtil.resolveAll(model.getResourceSet());
		org.eclipse.uml2.uml.Package uml = (org.eclipse.uml2.uml.Package) UMLUtil.findNamedElements(model.getResourceSet(), "UML").iterator().next();
		org.eclipse.uml2.uml.Class class_ = (org.eclipse.uml2.uml.Class) uml.getOwnedType("Class");
		org.eclipse.uml2.uml.Class operation = (org.eclipse.uml2.uml.Class) uml.getOwnedType("Operation");

		Profile result = UMLFactory.eINSTANCE.createProfile();
		result.setName(name);
		result.setURI("http://www.eclipse.org/Papyrus/2020/test/Impostor");
		result.createMetaclassReference(class_);
		result.createMetaclassReference(operation);
		result.createOwnedStereotype("TestSuite", false).createExtension(class_, false);
		result.createOwnedStereotype("TestCase", false).createExtension(operation, false);
		result.define();

		model.execute(new RecordingCommand(model.getEditingDomain()) {

			@Override
			protected void doExecute() {
				model.getModel().getAllAppliedProfiles().forEach(profile -> model.getModel().unapplyProfile(profile));
				model.getModel().applyProfile(result);
			}
		});

		return result;
	}

	private Matcher<IElementType> elementTypeIDThat(Matcher<String> idMatcher) {
		return new FeatureMatcher<>(idMatcher, "id", "id") {

			@Override
			protected String featureValueOf(IElementType actual) {
				return actual.getId();
			}
		};
	}

	static Matcher<String> qualified(String name) {
		return is("org.eclipse.papyrus.uml.service.types.tests.UnitTest." + name); //$NON-NLS-1$
	}

}

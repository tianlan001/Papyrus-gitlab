/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.tests.deletion;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.IdentityCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.infra.types.core.registries.ElementTypeSetConfigurationRegistry;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionOperatorKind;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.hamcrest.CustomTypeSafeMatcher;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Iterators;

/**
 * @author damus
 *
 */
public class InteractionDeletionTest {

	@Rule
	public final ModelSetFixture model = new ModelSetFixture();

	/**
	 * Constructor.
	 *
	 */
	public InteractionDeletionTest() {
		super();
	}

	/**
	 * Verify that the deletion of a combined fragment does not delete the
	 * interaction fragments that its operand owns, but transfers them up to the next
	 * fragment container.
	 */
	@Test
	@PluginResource("resource/interactions/bug533682.uml")
	public void deleteCombinedFragment() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);

		// We have at least four message ends and one execution specification
		InteractionFragment[] fragments = operand.getFragments().toArray(new InteractionFragment[0]);
		assumeThat("Lost fragments on opening editor", fragments.length, greaterThan(4));
		ExecutionSpecification exec = (ExecutionSpecification) operand.getFragment("exec");
		assumeThat("No execution specification", exec, notNullValue());

		delete(cfrag);

		assertThat("Interaction fragments not retained", interaction.getFragments(), hasItems(fragments));
	}

	/**
	 * A variant in which the contents of a combined fragment that is deleted end up in
	 * a nesting fragment, not the interaction.
	 */
	@Test
	@PluginResource("resource/interactions/bug533682.uml")
	public void deleteCombinedFragmentNested() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);

		// We have at least four message ends and one execution specification
		InteractionFragment[] fragments = operand.getFragments().toArray(new InteractionFragment[0]);
		assumeThat("Lost fragments on opening editor", fragments.length, greaterThan(4));
		ExecutionSpecification exec = (ExecutionSpecification) operand.getFragment("exec");
		assumeThat("No execution specification", exec, notNullValue());

		// Insert a layer of nesting
		CombinedFragment newCFrag = insertContainingCombinedFragment(cfrag);
		InteractionOperand intermediate = newCFrag.getOperands().get(0);

		delete(cfrag);

		assertThat("Interaction fragments not retained by parent operand", intermediate.getFragments(), hasItems(fragments));
	}

	/**
	 * A variant in which the content of a combined fragment that is deleted is just another
	 * combined fragment, not more primitive fragments.
	 */
	@Test
	@PluginResource("resource/interactions/bug533682.uml")
	public void deleteCombinedFragmentDeeplyNested() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		// Insert two layers of nesting
		CombinedFragment toDelete = insertContainingCombinedFragment(cfrag);
		CombinedFragment newCFrag = insertContainingCombinedFragment(toDelete);
		InteractionOperand intermediate = newCFrag.getOperands().get(0);

		delete(toDelete);

		assertThat("Interaction fragments not retained by grandparent operand", intermediate.getFragments(), hasItem(cfrag));
	}

	/**
	 * Verify that the deletion of an interaction fragment inside of an operand is not impeded.
	 */
	@Test
	@PluginResource("resource/interactions/bug533682.uml")
	public void deleteInteractionFragmentInOperand() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand operand = cfrag.getOperands().get(0);

		ExecutionSpecification exec = findElement("exec", ExecutionSpecification.class);

		delete(exec);

		assertThat("Execution specification not deleted", exec, isDeleted());
		assertThat("Operand still has the execution specification", operand.getFragments(), not(hasItem(exec)));
		assertThat("Interaction has the execution specification", interaction.getFragments(), not(hasItem(exec)));
	}

	/**
	 * Verify that the deletion of the interaction is not impeded.
	 */
	@Test
	@PluginResource("resource/interactions/bug533682.uml")
	public void deleteInteraction() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		ExecutionSpecification exec = findElement("exec", ExecutionSpecification.class);

		delete(interaction);

		assertThat("Execution specification not deleted", exec, isDeleted());
		assertThat("Interaction not deleted", interaction, isDeleted());
	}

	@Test
	@PluginResource("resource/interactions/bug533682.uml")
	public void deleteInteractionWithNesting() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		ExecutionSpecification exec = findElement("exec", ExecutionSpecification.class);

		insertContainingCombinedFragment(cfrag);

		delete(interaction);

		assertThat("Execution specification not deleted", exec, isDeleted());
		assertThat("Interaction not deleted", interaction, isDeleted());
	}

	/**
	 * Verify that the deletion of the last operand of a combined fragment results in the
	 * combined fragment also being deleted.
	 */
	@Test
	@PluginResource("resource/interactions/bug533683.uml")
	public void deleteOperandsOfCombinedFragment() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand alt1 = cfrag.getOperand("alt1");
		InteractionOperand alt2 = cfrag.getOperand("alt2");

		// We have at least four message ends and one execution specification
		InteractionFragment[] fragments = alt1.getFragments().toArray(new InteractionFragment[0]);
		assumeThat("Lost fragments on opening editor", fragments.length, greaterThan(4));
		ExecutionSpecification exec = (ExecutionSpecification) alt1.getFragment("exec");
		assumeThat("No execution specification", exec, notNullValue());

		delete(alt2);

		assertThat(cfrag.getOperands(), is(Collections.singletonList(alt1)));

		assertThat("Interaction fragments were moved or deleted", alt1.getFragments(), hasItems(fragments));

		delete(alt1);

		assertThat("Combined fragment not deleted", cfrag, isDeleted());

		assertThat("Interaction fragments not retained", interaction.getFragments(), hasItems(fragments));
	}

	/**
	 * Verify that the simultaneous deletion of all operands of a combined fragment results in the
	 * combined fragment also being deleted.
	 */
	@Test
	@PluginResource("resource/interactions/bug533683.uml")
	public void deleteAllOperandsOfCombinedFragment() {
		CombinedFragment cfrag = findElement("cfrag", CombinedFragment.class);
		Interaction interaction = cfrag.getEnclosingInteraction();
		assumeThat("No interaction", interaction, notNullValue());

		InteractionOperand alt1 = cfrag.getOperand("alt1");
		InteractionOperand alt2 = cfrag.getOperand("alt2");

		// We have at least four message ends and one execution specification
		InteractionFragment[] fragments = alt1.getFragments().toArray(new InteractionFragment[0]);
		assumeThat("Lost fragments on opening editor", fragments.length, greaterThan(4));
		ExecutionSpecification exec = (ExecutionSpecification) alt1.getFragment("exec");
		assumeThat("No execution specification", exec, notNullValue());

		delete(Arrays.asList(alt1, alt2));
		assertThat(Arrays.asList(alt1, alt2), everyItem(isDeleted()));

		assertThat("Combined fragment not deleted", cfrag, isDeleted());
		assertThat("Interaction fragments not retained", interaction.getFragments(), hasItems(fragments));
	}

	//
	// Test framework
	//

	@BeforeClass
	public static void ensureModeledEditHelperAdvice() {
		ElementTypeSetConfigurationRegistry.getInstance().getElementTypeSetConfigurations();
	}

	<T extends NamedElement> T findElement(String name, Class<T> type) {
		Iterator<? extends EObject> eObjects = Iterators.filter(model.getResourceSet().getAllContents(), EObject.class);
		return type.cast(UML2Util.findEObject(eObjects, eObject -> type.isInstance(eObject) && name.equals(((NamedElement) eObject).getName())));
	}

	void delete(EObject object) {
		DestroyElementRequest request = new DestroyElementRequest(model.getEditingDomain(), object, false);
		model.execute(command(object.eContainer(), request));
	}

	void delete(Collection<? extends EObject> objects) {
		DestroyElementRequest request = new DestroyElementRequest(model.getEditingDomain(), false);
		Map<String, Object> parameters = new HashMap<>();

		@SuppressWarnings("unchecked")
		ICommand command = objects.stream().reduce((ICommand) IdentityCommand.INSTANCE,
				(cmd, object) -> {
					request.setElementToDestroy(object);
					request.addParameters(parameters);
					ICommand delete = command(object.eContainer(), request);
					parameters.clear();
					parameters.putAll(request.getParameters());
					return cmd.compose(delete);
				},
				CompositeCommand::compose);

		model.execute(command.reduce());
	}

	ICommand command(EObject object, IEditCommandRequest request) {
		IElementEditService edit = ElementEditServiceUtils.getCommandProvider(object);
		assertThat("No edit command provider", edit, notNullValue());
		ICommand result = edit.getEditCommand(request);
		assertThat("No command provided for " + request.getClass().getSimpleName(), result, notNullValue());
		return result;
	}

	/**
	 * Sneak a combined fragment into the model as parent of the given combined fragment,
	 * to add a level of nesting.
	 * 
	 * @param cfrag
	 *            the combined fragment to nested into another new one
	 * @return the new nesting combined fragment
	 */
	CombinedFragment insertContainingCombinedFragment(CombinedFragment cfrag) {
		CombinedFragment[] result = { null };

		try {
			GMFUnsafe.write(model.getEditingDomain(), () -> {
				EList<InteractionFragment> fragments = (cfrag.getEnclosingInteraction() == null)
						? cfrag.getEnclosingOperand().getFragments()
						: cfrag.getEnclosingInteraction().getFragments();

				int index = fragments.indexOf(cfrag);
				CombinedFragment newCFrag = UMLFactory.eINSTANCE.createCombinedFragment();
				newCFrag.setInteractionOperator(InteractionOperatorKind.OPT_LITERAL);
				InteractionOperand newOperand = newCFrag.createOperand(null);
				fragments.add(index, newCFrag);
				newOperand.getFragments().add(cfrag);

				result[0] = newCFrag;
			});
		} catch (InterruptedException e) {
			fail("Combined fragment insertion transaction interrupted");
		} catch (RollbackException e) {
			e.printStackTrace();
			fail("Combined fragment insertion transaction rolled back");
		}

		return result[0];
	}

	static Matcher<EObject> isDeleted() {
		return new CustomTypeSafeMatcher<EObject>("is deleted") {
			@Override
			protected boolean matchesSafely(EObject item) {
				return item.eResource() == null;
			}
		};
	}
}

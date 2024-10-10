/*****************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.SemanticCandidatesServices;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Include;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link SemanticCandidatesServices} service.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class SemanticCandidatesServicesTest extends AbstractServicesTest {


	private SemanticCandidatesServices semanticCandidatesServices;

	/**
	 * This method set up the test environment
	 */
	@Before
	public void setUp() {
		this.semanticCandidatesServices = new SemanticCandidatesServices();
	}

	/**
	 * Test {@link SemanticCandidatesServices#getAbstractionCandidates(EObject)}.
	 */
	@Test
	public void testGetAbstractionCandidates() {
		Model model = this.create(Model.class);
		assertTrue(this.semanticCandidatesServices.getAbstractionCandidates(model).isEmpty());

		// test GetAbstraction from {@link Model} context and contained in {@link Model}
		Abstraction a1 = this.create(Abstraction.class);
		model.getPackagedElements().add(a1);
		Collection<Abstraction> abstractions = this.semanticCandidatesServices.getAbstractionCandidates(model);
		assertEquals(1, abstractions.size());
		assertTrue(abstractions.contains(a1));

		// test GetAbstraction from {@link Model} context but contained by sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Abstraction a2 = this.create(Abstraction.class);
		pack.getPackagedElements().add(a2);
		abstractions = this.semanticCandidatesServices.getAbstractionCandidates(model);
		assertEquals(2, abstractions.size());
		assertTrue(abstractions.contains(a1));
		assertTrue(abstractions.contains(a2));

		// test GetAbstraction from a non {@link Package}
		abstractions = this.semanticCandidatesServices.getAbstractionCandidates(this.create(Comment.class));
		assertEquals(0, abstractions.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getConnectorCandidates(EObject)}.
	 */
	@Test
	public void testGetConnectorCandidates() {
		Model model = this.create(Model.class);
		Class class1 = this.create(Class.class);
		model.getPackagedElements().add(class1);
		assertTrue(this.semanticCandidatesServices.getConnectorCandidates(model).isEmpty());

		// test GetConnector from {@link Model} context and contained by a {@link Class} in {@link Model}
		Connector c1 = this.create(Connector.class);
		class1.getOwnedConnectors().add(c1);
		Collection<Connector> connectors = this.semanticCandidatesServices.getConnectorCandidates(model);
		assertEquals(1, connectors.size());
		assertTrue(connectors.contains(c1));

		// test GetConnector from {@link Model} context but contained by a {@link Class} in sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Class class2 = this.create(Class.class);
		pack.getPackagedElements().add(class2);
		Connector c2 = this.create(Connector.class);
		class2.getOwnedConnectors().add(c2);
		connectors = this.semanticCandidatesServices.getConnectorCandidates(model);
		assertEquals(2, connectors.size());
		assertTrue(connectors.contains(c1));
		assertTrue(connectors.contains(c2));

		// test GetConnector from a non {@link Package}
		connectors = this.semanticCandidatesServices.getConnectorCandidates(this.create(Comment.class));
		assertEquals(0, connectors.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getConstraintCandidates(EObject)}.
	 */
	@Test
	public void testGetConstraintCandidates() {

		Model model = this.create(Model.class);
		Constraint constraint = this.create(Constraint.class);
		Constraint constraint2 = this.create(Constraint.class);
		Constraint constraint3 = this.create(Constraint.class);
		assertTrue(this.semanticCandidatesServices.getConstraintCandidates(model).isEmpty());
		model.getOwnedRules().addAll(List.of(constraint, constraint2));
		model.getPackagedElements().add(constraint3);
		Collection<EObject> constraintCandidates = this.semanticCandidatesServices.getConstraintCandidates(model);
		assertEquals(2, constraintCandidates.size());
		assertTrue(constraintCandidates.containsAll(List.of(constraint, constraint2)));
	}

	/**
	 * Test {@link SemanticCandidatesServices#getGeneralizationCandidates(EObject)}.
	 */
	@Test
	public void testGetGeneralizationCandidates() {
		Model model = this.create(Model.class);
		Class class1 = this.create(Class.class);
		model.getPackagedElements().add(class1);
		assertTrue(this.semanticCandidatesServices.getGeneralizationCandidates(model).isEmpty());

		// test GetGeneralization from {@link Model} context and contained by a {@link Class} in {@link Model}
		Generalization g1 = this.create(Generalization.class);
		class1.getGeneralizations().add(g1);
		Collection<Generalization> generalizations = this.semanticCandidatesServices.getGeneralizationCandidates(model);
		assertEquals(1, generalizations.size());
		assertTrue(generalizations.contains(g1));

		// test GetGeneralization from {@link Model} context but contained by a {@link Class} in sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Class class2 = this.create(Class.class);
		pack.getPackagedElements().add(class2);
		Generalization g2 = this.create(Generalization.class);
		class2.getGeneralizations().add(g2);
		generalizations = this.semanticCandidatesServices.getGeneralizationCandidates(model);
		assertEquals(2, generalizations.size());
		assertTrue(generalizations.contains(g1));
		assertTrue(generalizations.contains(g2));

		// test GetGeneralization from a non {@link Package}
		generalizations = this.semanticCandidatesServices.getGeneralizationCandidates(this.create(Comment.class));
		assertEquals(0, generalizations.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getInterfaceRealizationCandidates(EObject)}.
	 */
	@Test
	public void testGetInterfaceRealizationCandidates() {
		Model model = this.create(Model.class);
		Component component1 = this.create(Component.class);
		model.getPackagedElements().add(component1);
		assertTrue(this.semanticCandidatesServices.getInterfaceRealizationCandidates(model).isEmpty());

		// test getInterfaceRealizationCandidates from Model context and contained by a Component in Model
		InterfaceRealization i1 = this.create(InterfaceRealization.class);
		component1.getInterfaceRealizations().add(i1);
		Collection<InterfaceRealization> interfaceRealizations = this.semanticCandidatesServices.getInterfaceRealizationCandidates(model);
		assertEquals(1, interfaceRealizations.size());
		assertTrue(interfaceRealizations.contains(i1));

		// test getInterfaceRealizationCandidates from Model context but contained by a Component in a sub-Package of Model
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Component component2 = this.create(Component.class);
		pack.getPackagedElements().add(component2);
		InterfaceRealization i2 = this.create(InterfaceRealization.class);
		component2.getInterfaceRealizations().add(i2);
		interfaceRealizations = this.semanticCandidatesServices.getInterfaceRealizationCandidates(model);
		assertEquals(2, interfaceRealizations.size());
		assertTrue(interfaceRealizations.contains(i1));
		assertTrue(interfaceRealizations.contains(i2));

		// test getInterfaceRealizationCandidates from a non Namespace element
		interfaceRealizations = this.semanticCandidatesServices.getInterfaceRealizationCandidates(this.create(Comment.class));
		assertEquals(0, interfaceRealizations.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getComponentRealizationCandidates(EObject)}.
	 */
	@Test
	public void testGetComponentRealizationCandidates() {
		Model model = this.create(Model.class);
		Component component1 = this.create(Component.class);
		model.getPackagedElements().add(component1);
		assertTrue(this.semanticCandidatesServices.getComponentRealizationCandidates(model).isEmpty());

		// test getComponentRealizationCandidates from Model context and contained by a Component in Model
		ComponentRealization c1 = this.create(ComponentRealization.class);
		component1.getRealizations().add(c1);
		Collection<ComponentRealization> componentRealizations = this.semanticCandidatesServices.getComponentRealizationCandidates(model);
		assertEquals(1, componentRealizations.size());
		assertTrue(componentRealizations.contains(c1));

		// test getComponentRealizationCandidates from Model context but contained by a Component in a sub-Package of Model
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Component component2 = this.create(Component.class);
		pack.getPackagedElements().add(component2);
		ComponentRealization c2 = this.create(ComponentRealization.class);
		component2.getRealizations().add(c2);
		componentRealizations = this.semanticCandidatesServices.getComponentRealizationCandidates(model);
		assertEquals(2, componentRealizations.size());
		assertTrue(componentRealizations.contains(c1));
		assertTrue(componentRealizations.contains(c2));

		// test getComponentRealizationCandidates from a non Namespace element
		componentRealizations = this.semanticCandidatesServices.getComponentRealizationCandidates(this.create(Comment.class));
		assertEquals(0, componentRealizations.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getManifestationCandidates(EObject)}.
	 */
	@Test
	public void testGetManifestationCandidates() {
		Model model = this.create(Model.class);
		assertTrue(this.semanticCandidatesServices.getManifestationCandidates(model).isEmpty());

		// test GetManifestation from {@link Model} context and contained in {@link Model}
		Manifestation m1 = this.create(Manifestation.class);
		model.getPackagedElements().add(m1);
		Collection<Manifestation> manifestations = this.semanticCandidatesServices.getManifestationCandidates(model);
		assertEquals(1, manifestations.size());
		assertTrue(manifestations.contains(m1));

		// test GetManifestation from {@link Model} context but contained by sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Manifestation m2 = this.create(Manifestation.class);
		pack.getPackagedElements().add(m2);
		manifestations = this.semanticCandidatesServices.getManifestationCandidates(model);
		assertEquals(2, manifestations.size());
		assertTrue(manifestations.contains(m1));
		assertTrue(manifestations.contains(m2));

		// test GetManifestation from a non {@link Package}
		manifestations = this.semanticCandidatesServices.getManifestationCandidates(this.create(Comment.class));
		assertEquals(0, manifestations.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getRealizationCandidates(EObject)}.
	 */
	@Test
	public void testGetRealizationCandidates() {
		Model model = this.create(Model.class);
		assertTrue(this.semanticCandidatesServices.getRealizationCandidates(model).isEmpty());

		// test GetRealization from {@link Model} context and contained in {@link Model}
		Realization r1 = this.create(Realization.class);
		model.getPackagedElements().add(r1);
		Collection<Realization> realizations = this.semanticCandidatesServices.getRealizationCandidates(model);
		assertEquals(1, realizations.size());
		assertTrue(realizations.contains(r1));

		// test GetRealization from {@link Model} context but contained by sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Realization r2 = this.create(Realization.class);
		pack.getPackagedElements().add(r2);
		realizations = this.semanticCandidatesServices.getRealizationCandidates(model);
		assertEquals(2, realizations.size());
		assertTrue(realizations.contains(r1));
		assertTrue(realizations.contains(r2));


		// Test GetRealization from a {@link CollaborationUse}

		Collaboration collaboration = this.create(Collaboration.class);
		CollaborationUse collaborationUse = this.create(CollaborationUse.class);
		collaboration.getCollaborationUses().add(collaborationUse);
		model.getPackagedElements().add(collaboration);
		Realization r3 = this.create(Realization.class);
		collaborationUse.getRoleBindings().add(r3);


		realizations = this.semanticCandidatesServices.getRealizationCandidates(model);
		assertEquals(3, realizations.size());
		assertTrue(realizations.contains(r1));
		assertTrue(realizations.contains(r2));
		assertTrue(realizations.contains(r3));

		// test GetRealization from a non {@link Package}
		realizations = this.semanticCandidatesServices.getRealizationCandidates(this.create(Comment.class));
		assertEquals(0, realizations.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getSubstitutionCandidates(EObject)}.
	 */
	@Test
	public void testGetSubstitutionCandidates() {
		Model model = this.create(Model.class);
		Class class1 = this.create(Class.class);
		model.getPackagedElements().add(class1);
		assertTrue(this.semanticCandidatesServices.getSubstitutionCandidates(model).isEmpty());

		// test GetSubstitution from {@link Model} context and contained by a {@link Class} in {@link Model}
		Substitution s1 = this.create(Substitution.class);
		class1.getSubstitutions().add(s1);
		Collection<Substitution> substitutions = this.semanticCandidatesServices.getSubstitutionCandidates(model);
		assertEquals(1, substitutions.size());
		assertTrue(substitutions.contains(s1));

		// test GetSubstitution from {@link Model} context but contained by a {@link Class} in sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Class class2 = this.create(Class.class);
		pack.getPackagedElements().add(class2);
		Substitution s2 = this.create(Substitution.class);
		class2.getSubstitutions().add(s2);
		substitutions = this.semanticCandidatesServices.getSubstitutionCandidates(model);
		assertEquals(2, substitutions.size());
		assertTrue(substitutions.contains(s1));
		assertTrue(substitutions.contains(s2));

		// test GetSubstitution from a non {@link Package}
		substitutions = this.semanticCandidatesServices.getSubstitutionCandidates(this.create(Comment.class));
		assertEquals(0, substitutions.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getUsageCandidates(EObject)}.
	 */
	@Test
	public void testGetUsageCandidates() {
		Model model = this.create(Model.class);
		assertTrue(this.semanticCandidatesServices.getUsageCandidates(model).isEmpty());

		// test GetUsages from {@link Model} context and contained in {@link Model}
		Usage u1 = this.create(Usage.class);
		model.getPackagedElements().add(u1);
		Collection<Usage> usages = this.semanticCandidatesServices.getUsageCandidates(model);
		assertEquals(1, usages.size());
		assertTrue(usages.contains(u1));

		// test GetUsages from {@link Model} context but contained by sub-{@link Package}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		Usage u2 = this.create(Usage.class);
		pack.getPackagedElements().add(u2);
		usages = this.semanticCandidatesServices.getUsageCandidates(model);
		assertEquals(2, usages.size());
		assertTrue(usages.contains(u1));
		assertTrue(usages.contains(u2));

		// test GetUsages from a non {@link Package}
		usages = this.semanticCandidatesServices.getUsageCandidates(this.create(Comment.class));
		assertEquals(0, usages.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getIncludeCandidates(EObject)}.
	 */
	@Test
	public void testGetIncludeCandidates() {
		Model model = this.create(Model.class);
		assertTrue(this.semanticCandidatesServices.getIncludeCandidates(model).isEmpty());

		// test getIncludeCandidates from {@link Model} context and contained in {@link Model}
		UseCase useCase = this.create(UseCase.class);
		model.getPackagedElements().add(useCase);
		Include i1 = this.create(Include.class);
		useCase.getIncludes().add(i1);
		Collection<Include> includes = this.semanticCandidatesServices.getIncludeCandidates(model);
		assertEquals(1, includes.size());
		assertTrue(includes.contains(i1));

		// test getIncludeCandidates from {@link Model} context but contained by sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		UseCase useCase2 = this.create(UseCase.class);
		pack.getPackagedElements().add(useCase2);
		Include i2 = this.create(Include.class);
		useCase2.getIncludes().add(i2);
		includes = this.semanticCandidatesServices.getIncludeCandidates(model);
		assertEquals(2, includes.size());
		assertTrue(includes.contains(i1));
		assertTrue(includes.contains(i2));


		// Test getIncludeCandidates from {@link Model} context but contained by sub-subjects of {@link Model}

		Component component = this.create(Component.class);
		model.getPackagedElements().add(component);
		UseCase useCase3 = this.create(UseCase.class);
		component.getOwnedUseCases().add(useCase3);
		Include i3 = this.create(Include.class);
		useCase3.getIncludes().add(i3);


		includes = this.semanticCandidatesServices.getIncludeCandidates(model);
		assertEquals(3, includes.size());
		assertTrue(includes.contains(i1));
		assertTrue(includes.contains(i2));
		assertTrue(includes.contains(i3));

		// test getIncludeCandidates from a non {@link Package}
		includes = this.semanticCandidatesServices.getIncludeCandidates(this.create(Comment.class));
		assertEquals(0, includes.size());
	}

	/**
	 * Test {@link SemanticCandidatesServices#getExtendCandidates(EObject)}.
	 */
	@Test
	public void testGetExtendCandidates() {
		Model model = this.create(Model.class);
		assertTrue(this.semanticCandidatesServices.getExtendCandidates(model).isEmpty());

		// test getExtendCandidates from {@link Model} context and contained in {@link Model}
		UseCase useCase = this.create(UseCase.class);
		model.getPackagedElements().add(useCase);
		Extend e1 = this.create(Extend.class);
		useCase.getExtends().add(e1);
		Collection<Extend> extendsList = this.semanticCandidatesServices.getExtendCandidates(model);
		assertEquals(1, extendsList.size());
		assertTrue(extendsList.contains(e1));

		// test getExtendCandidates from {@link Model} context but contained by sub-{@link Package} of {@link Model}
		Package pack = this.create(Package.class);
		model.getPackagedElements().add(pack);
		UseCase useCase2 = this.create(UseCase.class);
		pack.getPackagedElements().add(useCase2);
		Extend e2 = this.create(Extend.class);
		useCase2.getExtends().add(e2);
		extendsList = this.semanticCandidatesServices.getExtendCandidates(model);
		assertEquals(2, extendsList.size());
		assertTrue(extendsList.contains(e1));
		assertTrue(extendsList.contains(e2));


		// Test getExtendCandidates from {@link Model} context but contained by sub-subjects of {@link Model}

		Component component = this.create(Component.class);
		model.getPackagedElements().add(component);
		UseCase useCase3 = this.create(UseCase.class);
		component.getOwnedUseCases().add(useCase3);
		Extend e3 = this.create(Extend.class);
		useCase3.getExtends().add(e3);


		extendsList = this.semanticCandidatesServices.getExtendCandidates(model);
		assertEquals(3, extendsList.size());
		assertTrue(extendsList.contains(e1));
		assertTrue(extendsList.contains(e2));
		assertTrue(extendsList.contains(e3));

		// test getExtendCandidates from a non {@link Package}
		extendsList = this.semanticCandidatesServices.getExtendCandidates(this.create(Comment.class));
		assertEquals(0, extendsList.size());
	}

}

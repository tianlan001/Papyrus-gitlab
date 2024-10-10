/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.services;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.communication.services.CommunicationDiagramServices;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Observation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link ComponentStructureDiagramServices} service.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
public class CommunicationDiagramServicesTest extends AbstractServicesTest {

	private CommunicationDiagramServices communicationDiagramServices;

	@Before
	public void setUp() {
		this.communicationDiagramServices = new CommunicationDiagramServices();
	}

	/**
	 * Test {@link CommunicationDiagramServices#canCreateObservationCD(org.eclipse.emf.ecore.EObject, EClass, String)} in {@link Interaction} contained in {@link Package}.
	 */
	@Test
	public void testCanCreateObservationInInteraction() {
		Model model = create(Model.class);
		Interaction interaction = create(Interaction.class);
		model.getPackagedElements().add(interaction);

		EClass durationObservationClass = UMLPackage.eINSTANCE.getDurationObservation();
		assertTrue(communicationDiagramServices.canCreateObservationCOD(interaction, durationObservationClass, UMLPackage.eINSTANCE.getPackage_PackagedElement().getName()));
	}

	/**
	 * Test {@link CommunicationDiagramServices#canCreateObservationCD(org.eclipse.emf.ecore.EObject, EClass, String)} in orphan {@link Interaction}.
	 */
	@Test
	public void testCannotCreateObservationInInteraction() {
		Interaction interaction = create(Interaction.class);

		EClass durationObservationClass = UMLPackage.eINSTANCE.getDurationObservation();
		assertFalse(communicationDiagramServices.canCreateObservationCOD(interaction, durationObservationClass, UMLPackage.eINSTANCE.getPackage_PackagedElement().getName()));
	}

	/**
	 * Test {@link CommunicationDiagramServices#getObservationCandidates(org.eclipse.emf.ecore.EObject)} from null context.
	 */
	@Test
	public void testGetEmptyObservationCandidatesFromNullContext() {
		Collection<Observation> observationCandidates = communicationDiagramServices.getObservationCandidates(null);
		assertTrue(observationCandidates.isEmpty());
	}

	/**
	 * Test {@link CommunicationDiagramServices#getObservationCandidates(org.eclipse.emf.ecore.EObject)} from Package with no Observation.
	 */
	@Test
	public void testGetEmptyObservationCandidates() {
		Package pack = create(Package.class);
		Collection<Observation> observationCandidates = communicationDiagramServices.getObservationCandidates(pack);
		assertTrue(observationCandidates.isEmpty());
	}

	/**
	 * Test {@link CommunicationDiagramServices#getObservationCandidates(org.eclipse.emf.ecore.EObject)} from Package with Observation.
	 */
	@Test
	public void testGetObservationCandidatesFromPackageWithObservation() {
		Package pack = create(Package.class);
		TimeObservation timeObservation = create(TimeObservation.class);
		pack.getPackagedElements().add(timeObservation);
		Collection<Observation> observationCandidates = communicationDiagramServices.getObservationCandidates(pack);
		assertFalse(observationCandidates.isEmpty());
		assertEquals(1, observationCandidates.size());
		assertEquals(timeObservation, observationCandidates.stream().findFirst().get());
	}

	/**
	 * Test {@link CommunicationDiagramServices#getObservationCandidates(org.eclipse.emf.ecore.EObject)} from Interface in Package with Observation.
	 */
	@Test
	public void testGetObservationCandidatesFromInterfaceWithPackageWithObservation() {
		Package pack = create(Package.class);
		Interface interfac = create(Interface.class);
		TimeObservation timeObservation = create(TimeObservation.class);
		pack.getPackagedElements().add(timeObservation);
		pack.getPackagedElements().add(interfac);
		Collection<Observation> observationCandidates = communicationDiagramServices.getObservationCandidates(interfac);
		assertFalse(observationCandidates.isEmpty());
		assertEquals(1, observationCandidates.size());
		assertEquals(timeObservation, observationCandidates.stream().findFirst().get());
	}


}

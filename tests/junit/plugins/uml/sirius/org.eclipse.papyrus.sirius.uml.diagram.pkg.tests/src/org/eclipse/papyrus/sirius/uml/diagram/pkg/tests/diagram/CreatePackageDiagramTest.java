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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.diagram;

import static org.junit.Assert.assertEquals;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDiagramCreationTests;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

/**
 * Check Package diagram creation.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/createDiagram/createDiagram.di")
public class CreatePackageDiagramTest extends AbstractDiagramCreationTests {

	private static final String PACKAGE1 = "Package1"; //$NON-NLS-1$

	private static final String DIAGRAM_TYPE = "org.eclipse.papyrus.sirius.uml.diagram.pkg"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "newName"; //$NON-NLS-1$

	@Test
	public void createPackageDiagramTestFromRootModel() throws Exception {
		this.checkDiagramCreationFromSiriusDiagramPrototype(this.rootModel, DIAGRAM_NAME, DIAGRAM_TYPE);
		assertEquals(1, this.getAllPackages());
	}

	@Test
	public void createPackageDiagramTestFromSubPackage() {
		Package package1 = (Package) this.rootModel.getMember(PACKAGE1);
		this.checkDiagramCreationFromSiriusDiagramPrototype(package1, DIAGRAM_NAME, DIAGRAM_TYPE);
		assertEquals(1, this.getAllPackages());
	}

	private int getAllPackages() {
		Stream<EObject> rootContentStream = StreamSupport
				.stream(Spliterators.spliteratorUnknownSize(this.rootModel.eAllContents(), Spliterator.NONNULL), false);
		int interactionNumber = rootContentStream.filter(Package.class::isInstance)//
				.collect(Collectors.toList())//
				.size();
		return interactionNumber;
	}

}

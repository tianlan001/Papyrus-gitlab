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
package org.eclipse.papyrus.sirius.uml.diagram.common.services.tests;

import static org.junit.Assert.assertEquals;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.NamedElementQualifiedNameProvider;
import org.eclipse.papyrus.uml.domain.services.labels.ElementLabelProvider;
import org.eclipse.papyrus.uml.domain.services.labels.KeywordLabelProvider;
import org.eclipse.papyrus.uml.domain.services.labels.UMLCharacters;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

/**
 * Tests related to the NamedElementQualifiedNameProvider.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class NamedElementQualifiedNameProviderTest extends AbstractServicesTest {

	private static final String CLASS_NAME = "myClass"; //$NON-NLS-1$

	private static final String PACKAGE_NAME = "myPackage"; //$NON-NLS-1$

	@Test
	public void testQualifiedNameProvider() {
		ElementLabelProvider elementLabelProvider = this.buildLabelProviderNoPrefix();
		Class clazz = this.create(Class.class);
		clazz.setName(CLASS_NAME);
		Package package1 = this.create(Package.class);
		package1.setName(PACKAGE_NAME);
		package1.getPackagedElements().add(clazz);

		assertEquals(PACKAGE_NAME + "::" + CLASS_NAME, elementLabelProvider.getLabel(clazz)); //$NON-NLS-1$
	}

	@Test
	public void testQualifiedNameProviderWithParentWithNoName() {
		ElementLabelProvider elementLabelProvider = this.buildLabelProviderNoPrefix();
		Class clazz = this.create(Class.class);
		clazz.setName(CLASS_NAME);
		Package package1 = this.create(Package.class);
		package1.getPackagedElements().add(clazz);

		assertEquals(CLASS_NAME, elementLabelProvider.getLabel(clazz));
	}


	@Test
	public void testQualifiedNameProviderWithNoParent() {
		ElementLabelProvider elementLabelProvider = this.buildLabelProviderNoPrefix();
		Class clazz = this.create(Class.class);
		clazz.setName(CLASS_NAME);

		assertEquals(CLASS_NAME, elementLabelProvider.getLabel(clazz));
	}

	@Test
	public void testQualifiedNameProviderWithNoName() {
		ElementLabelProvider elementLabelProvider = this.buildLabelProviderNoPrefix();
		Class clazz = this.create(Class.class);

		assertEquals(UMLCharacters.EMPTY, elementLabelProvider.getLabel(clazz));
	}

	private ElementLabelProvider buildLabelProviderNoPrefix() {
		return ElementLabelProvider.builder()//
				.withKeywordLabelProvider(new KeywordLabelProvider())//
				.withNameProvider(new NamedElementQualifiedNameProvider()).build();
	}
}

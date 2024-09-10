/*****************************************************************************
 * Copyright (c) 2018 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.compatibility.cdo.tests;

import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.papyrus.infra.gmfdiag.style.StyleFactory;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * This test classes allows to check that the CDO version of metamodel is used by the Eclipse Instance
 */
@SuppressWarnings("nls")
public class CDOInstallationTest {

	private static final String PAPYRUS_NOTATION_CDO_FACTORY_CLASS_NAME = "org.eclipse.papyrus.infra.gmfdiag.css.cdo.factory.CSSNotationFactory";

	private static final String PAPYRUS_DIAGRAM_STYLE_CDO_FACTORY_CLASS_NAME = "org.eclipse.papyrus.infra.gmfdiag.style.cdo.impl.StyleFactoryImpl";

	private static final String PAPYRUS_UML_CDO_FACTORY_CLASS_NAME = "org.eclipse.uml2.uml.cdo.internal.impl.UMLFactoryImpl";

	@Test
	public void checkNotationFactory() {
		final NotationFactory notationFactory = NotationFactory.eINSTANCE;
		final String name = notationFactory.getClass().getName();
		Assert.assertEquals("The tests result is wrong, because the CDO version of GMF Notation is not installed (or used?!).", PAPYRUS_NOTATION_CDO_FACTORY_CLASS_NAME, name);
	}

	@Test
	public void checkStyleFactory() {
		final StyleFactory notationFactory = StyleFactory.eINSTANCE;
		final String name = notationFactory.getClass().getName();
		Assert.assertEquals("The tests result is wrong, because the CDO version of the Diagram Style is not installed (or used?!).", PAPYRUS_DIAGRAM_STYLE_CDO_FACTORY_CLASS_NAME, name);
	}

	@Test
	public void checkUMLFactory() {
		final UMLFactory umlFactory = UMLFactory.eINSTANCE;
		final String name = umlFactory.getClass().getName();
		Assert.assertEquals("The tests result is wrong, because the CDO version of the UML Metamodel is not installed (or used?!).", PAPYRUS_UML_CDO_FACTORY_CLASS_NAME, name);
	}
}

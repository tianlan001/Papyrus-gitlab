/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   Celine Janssens (ALL4TEC) - Update tests due to CSS engine modification (Bug 491334)
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.css.tests.tests;

import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.WorkspaceCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.CSSHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.preferences.ThemePreferences;
import org.eclipse.papyrus.infra.gmfdiag.css.tests.Activator;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Camille Letavernier
 *
 *
 */
public class CSSNamedElementsTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	private CSSDiagram diagram;

	@BeforeClass
	public static void initCSSTheme() {
		IPreferenceStore cssThemePreferences = org.eclipse.papyrus.infra.gmfdiag.css.Activator.getDefault().getPreferenceStore();
		cssThemePreferences.setValue(ThemePreferences.CURRENT_THEME, "org.eclipse.papyrus.css.papyrus_theme");
		WorkspaceCSSEngine.instance.reset();
	}

	@Before
	public void init() throws Exception {
		ResourceSet resourceSet = houseKeeper.createResourceSet();
		CSSHelper.installCSSSupport(resourceSet);

		URI modelURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/model/namedElementsTest/model.notation", true);

		Diagram diagram = (Diagram) EMFHelper.loadEMFModel(resourceSet, modelURI);
		Assert.assertNotNull("Cannot find the model", diagram);
		Assert.assertTrue("CSS are not activated on this resource", diagram instanceof CSSDiagram);
		this.diagram = (CSSDiagram) diagram;
	}

	@Test
	public void testNamedReference() {
		Shape namedType = findShape("namedType");
		Assert.assertEquals("The color should be red", 255, namedType.getFontColor());
		Assert.assertTrue("The property should be bold", namedType.isBold());
	}

	@Test
	public void testUnnamedReference() {
		Shape namedType = findShape("unnamedType");
		Assert.assertNotEquals("The color should not be red", 255, namedType.getFontColor());
		Assert.assertTrue("The property should be bold", namedType.isBold());
	}

	@Test
	public void testEmptyNameReference() {
		Shape namedType = findShape("emptyNameType");
		Assert.assertNotEquals("The color should not be red", 255, namedType.getFontColor());
		Assert.assertTrue("The property should be bold", namedType.isBold());
	}

	@Test
	public void testUntypedReference() {
		Shape namedType = findShape("untyped");
		Assert.assertNotEquals("The color should not be red", 255, namedType.getFontColor());
		Assert.assertTrue("The property should be bold", namedType.isBold());
	}



	protected Shape findShape(String elementName) {
		Iterator<EObject> allElements = diagram.eAllContents();
		while (allElements.hasNext()) {
			EObject viewObject = allElements.next();
			if (!(viewObject instanceof Shape)) {
				continue;
			}

			Shape view = (Shape) viewObject;
			if (view.getElement() instanceof NamedElement) {
				NamedElement element = (NamedElement) view.getElement();
				if (elementName.equals(element.getName())) {
					return view;
				}
			}
		}

		Assert.fail("Cannot find the view associated to " + elementName);
		return null;
	}
}

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
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.css.tests.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.IntValueStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.engine.WorkspaceCSSEngine;
import org.eclipse.papyrus.infra.gmfdiag.css.preferences.ThemePreferences;
import org.eclipse.papyrus.infra.gmfdiag.css.tests.Activator;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Camille Letavernier
 *
 */
public class CSSNamedStyleTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	protected Diagram diagram;

	private String previousTheme;

	@Before
	public void init() throws Exception {
		IPreferenceStore cssThemePreferences = org.eclipse.papyrus.infra.gmfdiag.css.Activator.getDefault().getPreferenceStore();
		previousTheme = cssThemePreferences.getString(ThemePreferences.CURRENT_THEME);
		cssThemePreferences.setValue(ThemePreferences.CURRENT_THEME, "org.eclipse.papyrus.infra.gmfdiag.css.tests.namedStylesTest");
		WorkspaceCSSEngine.instance.reset();

		ResourceSet resourceSet = houseKeeper.createResourceSet();

		URI modelURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/model/namedStylesTest/model.notation", true);

		diagram = (Diagram) EMFHelper.loadEMFModel(resourceSet, modelURI);
		Assert.assertNotNull("Cannot find the model", diagram);
	}

	@After
	public void dispose() throws Exception {
		IPreferenceStore cssThemePreferences = org.eclipse.papyrus.infra.gmfdiag.css.Activator.getDefault().getPreferenceStore();
		cssThemePreferences.setValue(ThemePreferences.CURRENT_THEME, previousTheme);
		WorkspaceCSSEngine.instance.reset();
	}

	// Bug 478789: CSS NamedStyles are returned even if the CSS Support is not enabled
	// Stylesheet is applied via a Test Theme
	@Test
	public void testStylesDisabled() {
		Shape class1Shape = (Shape) diagram.getChildren().get(0);
		IntValueStyle intStyle = (IntValueStyle) class1Shape.getNamedStyle(NotationPackage.eINSTANCE.getIntValueStyle(), "testNamedStyle");
		Assert.assertNull("Dynamic IntValueStyle should not be returned, since CSS are not enabled", intStyle);
	}

}


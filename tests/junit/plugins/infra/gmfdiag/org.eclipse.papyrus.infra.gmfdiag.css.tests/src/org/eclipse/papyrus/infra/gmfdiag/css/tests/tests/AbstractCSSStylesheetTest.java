/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 422257
 *  Micka�l ADAM (ALL4TEC) mickael.adam@all4tec.net
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.tests.tests;

import java.util.Iterator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.GradientStyle;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 *
 * An abstract Class to test CSSStylesheet. The Papyrus model tested is "../../model/stylesheetTest/".
 * In the daughter's class you must implement initCSSTheme() with the @BeforeClass tag and init() with the @Before tag.
 *
 * @author Mickael ADAM
 *
 */
public abstract class AbstractCSSStylesheetTest extends AbstractPapyrusTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	protected CSSDiagram diagram;

	@Test
	public void testClassStyle() {
		Shape classView = findShape("Class1");
		if(classView == null) {
			return;
		}
		Assert.assertEquals("Invalid color", rgb(255, 0, 0), classView.getFillColor()); //Red = #FF0000
		Assert.assertNull("Invalid gradient", classView.getGradient());
		Assert.assertTrue("The element icon should be displayed", AppearanceHelper.showElementIcon(classView));
	}

	@Test
	public void testInterfaceStyle() {
		Shape interfaceView = findShape("Interface1");
		Assert.assertNotNull("The interface should have a gradient", interfaceView.getGradient());
		Assert.assertEquals("The fill color should be yellow", rgb(255, 255, 0), interfaceView.getFillColor()); //Yellow = #FFFF00
		Assert.assertEquals("The gradient should be red", rgb(255, 0, 0), interfaceView.getGradient().getGradientColor1()); //Red = #FF0000
		Assert.assertEquals("The gradient should be vertical", GradientStyle.VERTICAL, interfaceView.getGradient().getGradientStyle());
		Assert.assertTrue("The element icon should be displayed", AppearanceHelper.showElementIcon(interfaceView));
	}

	@Test
	public void testCSSClassStyle() {
		Shape interfaceView = findShape("Interface2");
		Assert.assertNotNull("The interface should have a gradient", interfaceView.getGradient());
		Assert.assertEquals("The fill color should be light green (#90EE90)", rgb(144, 238, 144), interfaceView.getFillColor()); //Lightgreen = #90EE90
		//Case insensitive color name
		Assert.assertEquals(rgb(0, 191, 255), interfaceView.getGradient().getGradientColor1()); //DeepSkyBlue = #00BFFF
		Assert.assertEquals(GradientStyle.HORIZONTAL, interfaceView.getGradient().getGradientStyle());
		Assert.assertFalse(AppearanceHelper.showElementIcon(interfaceView));
		//TODO: Font names should be case-insensitive
		Assert.assertEquals("KaiTi", interfaceView.getFontName());
	}

	@Test
	public void testPackage() {
		Shape packageView = findShape("Package1");
		Assert.assertNotNull(packageView.getGradient());
		//Inherited style (From *)
		Assert.assertEquals(rgb(255, 0, 0), packageView.getGradient().getGradientColor1()); //Red = #FF0000
	}

	@Test
	public void testPriorities() {
		//Tests the priorities between styles:
		//.myClass : Low priority
		//.myClass.myOtherClass : Highest priority
		//[isAbstract=true] : Medium priority
		//Styles should also be merged when they aren't conflicting (ie. [isAbstract] + .myClass -> fontName = Tunga)
		Shape class3 = findShape("Class3");
		Shape class5 = findShape("Class5");
		testBlueStyle(class3); //isAbstract
		testBlueStyle(class5); //isAbstract
		Shape class4 = findShape("Class4");
		testWhiteStyle(class4); //.myClass.myOtherClass (Overrides isAbstract)
		testFontName(class4); //.myClass (Doesn't conflict with isAbstract)
		Shape class6 = findShape("Class6");
		testWhiteStyle(class6); //.myClass
		testFontName(class6); //.myClass
		Shape class7 = findShape("Class7");
		testBlueStyle(class7); //isAbstract
		testFontName(class7); //.myClass (Doesn't conflict with isAbstract)
	}

	private void testBlueStyle(Shape shape) {
		Assert.assertEquals(rgb(195, 205, 255), shape.getFillColor()); //rgb syntax
		Assert.assertTrue(shape.isBold());
		Assert.assertNotNull(shape.getGradient());
	}

	private void testWhiteStyle(Shape shape) {
		Assert.assertEquals(rgb(255, 255, 255), shape.getFillColor()); //White = #FFFFFF (Hexa syntax)
		Assert.assertFalse(shape.isBold());
		Assert.assertNull(shape.getGradient());
	}

	private void testFontName(Shape shape) {
		Assert.assertEquals("Tunga", shape.getFontName());
	}

	@After
	public void dispose() {
		Iterator<Resource> iterator = diagram.eResource().getResourceSet().getResources().iterator();
		while(iterator.hasNext()) {
			iterator.next().unload();
			iterator.remove();
		}
	}

	protected int rgb(int red, int green, int blue) {
		return red | green << 8 | blue << 16;
	}

	protected Shape findShape(String elementName) {
		for(Object viewObject : diagram.getChildren()) {
			View view = (View)viewObject;
			if(view instanceof Shape && view.getElement() instanceof NamedElement) {
				NamedElement element = (NamedElement)view.getElement();
				if(elementName.equals(element.getName())) {
					return (Shape)view;
				}
			}
		}
		Assert.fail("Cannot find the view associated to " + elementName);
		return null;
	}
}

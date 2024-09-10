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

package org.eclipse.papyrus.uml.tools.tests.tests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.infra.widgets.util.INameResolutionHelper;
import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.util.UMLReferenceConverter;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionHelper;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Vincent Lorenzo
 * This class tests the UMLReferenceConverter
 */
@PluginResource("/resources/uml/UMLReferenceConverterTest.di")
public class UMLReferenceConverterTest {

	@Rule
	public final ModelSetFixture fixture = new ModelSetFixture();

	private INameResolutionHelper helper;

	private UMLReferenceConverter singleValueParser;

	private UMLReferenceConverter multiValueParser;

	public static final String MODEL_TO_DISPLAY = "model";

	public static final String MODEL_TO_EDIT = "model";
	
	public static final String CLASS1_NAME_TO_DISPLAY = "Class1";

	public static final String CLASS1_NAME_TO_EDIT = "Class1";


	public static final String CLASS2_NAME_TO_DISPLAY = "Clas,s2";

	public static final String CLASS2_NAME_TO_EDIT = "'Clas,s2'";

	public static final String CLASS3_NAME_TO_DISPLAY = "Cla,,ss3";
	
	public static final String CLASS4_NAME_TO_DISPLAY = "Class4";

	public static final String CLASS3_NAME_TO_EDIT = "'Cla,,ss3'";

	public static final String CLASSE2_CLASS3_TO_DISPLAY = CLASS2_NAME_TO_DISPLAY + "," + CLASS3_NAME_TO_DISPLAY;

	public static final String CLASSE2_CLASS3_TO_EDIT = CLASS2_NAME_TO_EDIT + "," + CLASS3_NAME_TO_EDIT;

	private Class class2 = null;

	private Class class3 = null;
	
	private Class class1 = null;
	
	private Class class4 = null;

	private Package root = null;


	@Before
	public void initField() {
		helper = new NameResolutionHelper(fixture.getModel(), UMLPackage.eINSTANCE.getClass_());
		singleValueParser = new UMLReferenceConverter(helper, false);
		multiValueParser = new UMLReferenceConverter(helper, true);
		root = fixture.getModel();
		class2 = (Class) root.getMember(CLASS1_NAME_TO_DISPLAY);
		class2 = (Class) root.getMember(CLASS2_NAME_TO_DISPLAY);
		class3 = (Class) root.getMember(CLASS3_NAME_TO_DISPLAY);
	}

	private void testConverter(final String strToParse, final String strToEdit, final String strToDisplay, final Object objectToString, Object objectToFound, boolean isMany) {
		IPapyrusConverter converter = null;
		if(isMany){
			converter = multiValueParser;
		}else{
			converter = singleValueParser;
		}
		// test parsed value
		Object parsedValue = converter.editToCanonicalValue(strToParse, 0);
		Assert.assertEquals(objectToFound, parsedValue);

		// test the string to edit
		String str = converter.canonicalToEditValue(objectToString, 0);
		Assert.assertEquals(strToEdit, str);


		// test the string to display
		str = converter.canonicalToDisplayValue(objectToString, 0);
		Assert.assertEquals(str, strToDisplay);
	}


	@Test
	public void testClass2() {
		testConverter(CLASS2_NAME_TO_EDIT, CLASS2_NAME_TO_EDIT, CLASS2_NAME_TO_DISPLAY, class2, class2, false);
	}

	@Test
	public void testClass3() {
		testConverter(CLASS3_NAME_TO_EDIT, CLASS3_NAME_TO_EDIT, CLASS3_NAME_TO_DISPLAY, class3, class3, false);
	}

	@Test
	public void testList() {
		List<Object> list = new ArrayList<Object>();
		list.add(class2);
		list.add(class3);
		testConverter(CLASSE2_CLASS3_TO_EDIT, CLASSE2_CLASS3_TO_EDIT, CLASSE2_CLASS3_TO_DISPLAY, list, list, true);
	}


	@Test
	public void testMatchingElementstSingleValue1() {
		List<?> elements = singleValueParser.getMatchingElements(CLASS2_NAME_TO_DISPLAY);
		Assert.assertEquals(1, elements.size());
	}


	@Test
	public void testMatchingElementstSingleValue2() {
		List<?> elements = singleValueParser.getMatchingElements(CLASS2_NAME_TO_EDIT);
		Assert.assertEquals(1, elements.size());
	}

	@Test
	public void testEmptyString() {
		testConverter(IPapyrusConverter.EMPTY_STRING, IPapyrusConverter.EMPTY_STRING, IPapyrusConverter.UNDEFINED_VALUE, null, null, false);
	}

	@Test
	public void testNull() {
		testConverter(IPapyrusConverter.UNDEFINED_VALUE, IPapyrusConverter.EMPTY_STRING, IPapyrusConverter.UNDEFINED_VALUE, null, null, false);
	}


	@Test
	public void testEditToDisplaySingleString_1() {
		String res = singleValueParser.editToDisplayValue(CLASS2_NAME_TO_EDIT);
		Assert.assertEquals(CLASS2_NAME_TO_DISPLAY, res);
	}

	@Test
	public void testDisplayToEditString_1() {
		String res = singleValueParser.displayToEditValue(CLASS2_NAME_TO_DISPLAY);
		Assert.assertEquals(CLASS2_NAME_TO_EDIT, res);
	}



}

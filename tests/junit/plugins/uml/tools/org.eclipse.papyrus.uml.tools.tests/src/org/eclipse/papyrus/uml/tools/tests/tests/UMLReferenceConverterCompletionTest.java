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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUe (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 481835
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.tests.tests;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.papyrus.infra.widgets.util.IPapyrusConverter;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.util.ReferenceContentAssistProcessor;
import org.eclipse.papyrus.uml.tools.util.UMLReferenceConverter;
import org.eclipse.papyrus.uml.tools.utils.NameResolutionHelper;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class the suggestion of the content assist
 */
@PluginResource("/resources/uml/UMLReferenceConverterCompletionTest.di")
public class UMLReferenceConverterCompletionTest {

	@Rule
	public final ModelSetFixture fixture = new ModelSetFixture();

	private Package root;

	private Class class1;

	private Property property;

	

	private static String PROPERTY1_NAME_TO_DISPLAY = "Attribute1"; //$NON-NLS-1$

	private static final String PACKAGE1_NAME_TO_DISPLAY = "Package1"; //$NON-NLS-1$

	public static final String CLASS1_SHORTEST_QN_TO_DISPLAY = "model::Class1"; //$NON-NLS-1$

	public static final String CLASS1_QUALIFIED_NAME_TO_DISPLAY = "model::Class1"; //$NON-NLS-1$

	public static final String NESTED_CLASS1_NAME_TO_DISPLAY = "Class1"; //$NON-NLS-1$

	public static final String CLASS4_NAME_TO_DISPLAY = "Class4"; //$NON-NLS-1$

	public static final String CLASS2_NAME_TO_DISPLAY = "Clas,s2"; //$NON-NLS-1$

	public static final String CLASS3_NAME_TO_DISPLAY = "Cla,,ss3"; //$NON-NLS-1$

	private static final String CLASS_PARTIAL_NAME_WITHOUT_QUOTE_WITH_COMMA = "Clas,"; //$NON-NLS-1$

	private static final String CLASS_PARTIAL_NAME_WITHOUT_QUOTE_WITHOUT_COMMA = "Clas"; //$NON-NLS-1$

	private static final String CLASS_PARTIAL_NAME_WITH_QUOTE_WITH_COMMA = "'Clas,"; //$NON-NLS-1$


	public static final String CLASS5_NAME_TO_DISPLAY = "Class5"; //$NON-NLS-1$
	public static final String CLASS6_NAME_TO_DISPLAY = "Class6"; //$NON-NLS-1$

	public static final String CLASS1_NAME_TO_DISPLAY = "Class1"; //$NON-NLS-1$

	public static final String CONSTRAINT1_NAME_TO_DISPLAY = "Constraint1"; //$NON-NLS-1$
	public static final String CONSTRAINT2_NAME_TO_DISPLAY = "Constraint2"; //$NON-NLS-1$
	public static final String CONSTRAINT3_NAME_TO_DISPLAY = "Constraint3"; //$NON-NLS-1$
	public static final String CONSTRAINT4_NAME_TO_DISPLAY = "Constraint4"; //$NON-NLS-1$
	public static final String CONSTRAINT5_NAME_TO_DISPLAY = "Constraint5"; //$NON-NLS-1$
	public static final String CONSTRAINT6_NAME_TO_DISPLAY = "Constraint6"; //$NON-NLS-1$

	@Before
	public void initField() {
		root = fixture.getModel();
		class1 = (Class) root.getMember(CLASS1_NAME_TO_DISPLAY);
		property = (Property) class1.getMember(PROPERTY1_NAME_TO_DISPLAY);
	}

	@Test
	public void testCompletionToFindClass_1() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter converter = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) converter.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest(IPapyrusConverter.EMPTY_STRING);
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
	}

	public void testCompletionToFindClass_2() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter converter = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) converter.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("Cl"); //$NON-NLS-1$
		Assert.assertEquals(8, values.size());
		Assert.assertTrue(!values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
	}


	@Test
	public void testCompletionToFindClass_3() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("C"); //$NON-NLS-1$
		Assert.assertEquals(8, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(!values.contains(IPapyrusConverter.MORE_ELEMENTS));
		// Bug 481835: The values contains 2 times 'Class1' for 2 different objects
		Assert.assertTrue(values.contains(NESTED_CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS2_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS3_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS4_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS5_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS6_NAME_TO_DISPLAY));
	}

	@Test
	public void testCompletionToFindClass_4() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest(NESTED_CLASS1_NAME_TO_DISPLAY);
		Assert.assertEquals(3, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(!values.contains(IPapyrusConverter.MORE_ELEMENTS));
		// Bug 481835: The values contains 2 times 'Class1' for 2 different objects
		Assert.assertTrue(values.contains(NESTED_CLASS1_NAME_TO_DISPLAY));
	}

	@Test
	public void testCompletionToFindClass_5() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("Clas,"); //$NON-NLS-1$
		// the comma is ignored
		Assert.assertEquals(7, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(!values.contains(IPapyrusConverter.MORE_ELEMENTS));
		// Bug 481835: The values contains 2 times 'Class1' for 2 different objects
		Assert.assertTrue(values.contains(NESTED_CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS2_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS4_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS5_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS6_NAME_TO_DISPLAY));
	}

	@Test
	public void testCompletionToFindClass_6() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("'Clas,"); //$NON-NLS-1$
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(values.contains(CLASS2_NAME_TO_DISPLAY));
	}

	@Test
	public void testCompletionToFindClass_7() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("'Clas"); //$NON-NLS-1$
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(values.contains(CLASS2_NAME_TO_DISPLAY));
	}


	@Test
	public void testCompletionToFindClass_8() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, true);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		StringBuilder multiValueString = new StringBuilder();
		multiValueString.append(CLASS1_NAME_TO_DISPLAY);
		multiValueString.append(IPapyrusConverter.STRING_SEPARATOR);
		multiValueString.append(CLASS4_NAME_TO_DISPLAY);
		multiValueString.append(IPapyrusConverter.STRING_SEPARATOR);
		multiValueString.append("Cl"); //$NON-NLS-1$
		List<String> values = completionproposal.suggest(multiValueString.toString());
		Assert.assertEquals(8, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(!values.contains(IPapyrusConverter.MORE_ELEMENTS));
		// Bug 481835: The values contains 2 times 'Class1' for 2 different objects
		Assert.assertTrue(values.contains(NESTED_CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS2_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS3_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS4_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS5_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CLASS6_NAME_TO_DISPLAY));
	}

	@Test
	public void testCompletionToFindConstraint_1() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getConstraint());
		UMLReferenceConverter converter = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) converter.getCompletionProcessor(null);
		// we don't provide values when the string is empty
		List<String> values = completionproposal.suggest(IPapyrusConverter.EMPTY_STRING);
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
	}

	@Test
	public void testCompletionToFindConstraint_2() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getConstraint());
		UMLReferenceConverter converter = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) converter.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("Cons"); //$NON-NLS-1$
		Assert.assertEquals(7, values.size());
		Assert.assertTrue(!values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		Assert.assertTrue(values.contains(CONSTRAINT1_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CONSTRAINT2_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CONSTRAINT3_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CONSTRAINT4_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CONSTRAINT5_NAME_TO_DISPLAY));
		Assert.assertTrue(values.contains(CONSTRAINT6_NAME_TO_DISPLAY));
	}


	@Test
	public void testCompletionToFindPackage_1() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getPackage());
		UMLReferenceConverter converter = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) converter.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest(IPapyrusConverter.EMPTY_STRING);
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
	}

	@Test
	public void testCompletionToFindPackage_2() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getPackage());
		UMLReferenceConverter converter = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) converter.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest("Pack"); //$NON-NLS-1$
		Assert.assertEquals(17, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
		values.remove(IPapyrusConverter.MORE_ELEMENTS);
		values.remove(IPapyrusConverter.UNDEFINED_VALUE);
		Assert.assertEquals(15, new HashSet<String>(values).size());
	}


	@Test
	public void testCompletionSingleValueConverterEmptyString() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter singleValueParser = new UMLReferenceConverter(helper, false);
		ReferenceContentAssistProcessor proposal = (ReferenceContentAssistProcessor) singleValueParser.getCompletionProcessor(null);
		List<String> values = proposal.suggest(IPapyrusConverter.EMPTY_STRING);
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
	}

	@Test
	public void testCompletionMultiValueConverterEmptyString() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, true);
		ReferenceContentAssistProcessor completionproposal = (ReferenceContentAssistProcessor) multiValueParser.getCompletionProcessor(null);
		List<String> values = completionproposal.suggest(IPapyrusConverter.EMPTY_STRING);
		Assert.assertEquals(2, values.size());
		Assert.assertTrue(values.contains(IPapyrusConverter.MORE_ELEMENTS));
		Assert.assertTrue(values.contains(IPapyrusConverter.UNDEFINED_VALUE));
	}

	@Test
	public void testSplitString_1() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, true);
		StringBuilder builder = new StringBuilder();
		builder.append(CLASS1_NAME_TO_DISPLAY);
		builder.append(IPapyrusConverter.STRING_SEPARATOR);
		builder.append(CLASS_PARTIAL_NAME_WITHOUT_QUOTE_WITH_COMMA);
		Map<List<Integer>, String> mapRes = multiValueParser.getSubStringsWithTheirPositions(builder.toString());
		Assert.assertEquals(2, mapRes.size());
		Assert.assertTrue(mapRes.values().contains(CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(mapRes.values().contains(CLASS_PARTIAL_NAME_WITHOUT_QUOTE_WITHOUT_COMMA));
		for (Entry<List<Integer>, String> current : mapRes.entrySet()) {
			if (current.getValue().equals(CLASS1_NAME_TO_DISPLAY)) {
				int start = current.getKey().get(0);
				int end = current.getKey().get(1);
				Assert.assertEquals(0, start);
				Assert.assertEquals(6, end);
			} else if (current.getValue().equals(CLASS_PARTIAL_NAME_WITHOUT_QUOTE_WITHOUT_COMMA)) {
				int start = current.getKey().get(0);
				int end = current.getKey().get(1);
				Assert.assertEquals(7, start);
				Assert.assertEquals(11, end);
			} else {
				Assert.assertTrue(false);
			}

		}
		List<String> res = multiValueParser.splitFullStringToSubElementString(builder.toString());
		Assert.assertEquals(2, res.size());
		Assert.assertTrue(res.get(0).equals(CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(res.get(1).equals(CLASS_PARTIAL_NAME_WITHOUT_QUOTE_WITHOUT_COMMA));
	}

	@Test
	public void testSplitString_2() {
		NameResolutionHelper helper = new NameResolutionHelper(property, UMLPackage.eINSTANCE.getClass_());
		UMLReferenceConverter multiValueParser = new UMLReferenceConverter(helper, true);
		StringBuilder builder = new StringBuilder();
		builder.append(CLASS1_NAME_TO_DISPLAY);
		builder.append(IPapyrusConverter.STRING_SEPARATOR);
		builder.append(CLASS_PARTIAL_NAME_WITH_QUOTE_WITH_COMMA);
		Map<List<Integer>, String> mapRes = multiValueParser.getSubStringsWithTheirPositions(builder.toString());
		Assert.assertEquals(2, mapRes.size());
		Assert.assertTrue(mapRes.values().contains(CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(mapRes.values().contains(CLASS_PARTIAL_NAME_WITH_QUOTE_WITH_COMMA));
		for (Entry<List<Integer>, String> current : mapRes.entrySet()) {
			if (current.getValue().equals(CLASS1_NAME_TO_DISPLAY)) {
				int start = current.getKey().get(0);
				int end = current.getKey().get(1);
				Assert.assertEquals(0, start);
				Assert.assertEquals(6, end);
			} else if (current.getValue().equals(CLASS_PARTIAL_NAME_WITH_QUOTE_WITH_COMMA)) {
				int start = current.getKey().get(0);
				int end = current.getKey().get(1);
				Assert.assertEquals(7, start);
				Assert.assertEquals(13, end);
			} else {
				Assert.assertTrue(false);
			}

		}
		List<String> res = multiValueParser.splitFullStringToSubElementString(builder.toString());
		Assert.assertEquals(2, res.size());
		Assert.assertTrue(res.get(0).equals(CLASS1_NAME_TO_DISPLAY));
		Assert.assertTrue(res.get(1).equals(CLASS_PARTIAL_NAME_WITH_QUOTE_WITH_COMMA));
	}

}

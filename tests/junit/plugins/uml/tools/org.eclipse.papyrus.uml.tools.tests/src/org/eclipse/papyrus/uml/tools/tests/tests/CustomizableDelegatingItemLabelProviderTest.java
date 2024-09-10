/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.eclipse.papyrus.uml.tools.providers.CustomizableDelegatingItemLabelProvider.ST_LEFT;
import static org.eclipse.papyrus.uml.tools.providers.CustomizableDelegatingItemLabelProvider.ST_RIGHT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelStylersEnum;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelTypesEnum;
import org.eclipse.papyrus.uml.tools.providers.CustomizableDelegatingItemLabelProvider;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.TemplateParameter;
import org.eclipse.uml2.uml.TemplateSignature;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



/**
 * Class to test {@link CustomizableDelegatingItemLabelProvider}.
 */
@SuppressWarnings("nls")
@PluginResource("/resources/CustomizableLabelProvider/model.di")
public class CustomizableDelegatingItemLabelProviderTest {

	/** The space character. */
	public static final char SPACE = ' ';

	/** The colon separator. */
	public static final String COLON_SEPARATOR = ":";

	/** The dash separator. */
	public static final String DASH_SEPARATOR = "-";

	/** The fixture. */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/** the list of types */
	public static List<String> types = Arrays.asList(
			LabelTypesEnum.COLON_SEPARATOR.toString(),
			LabelTypesEnum.METACLASS.toString(),
			LabelTypesEnum.DASH_SEPARATOR.toString(),
			LabelTypesEnum.LABEL.toString(),
			LabelTypesEnum.STEREOTYPE.toString(),
			LabelTypesEnum.COLON_SEPARATOR.toString(),
			LabelTypesEnum.QUALIFIED_NAME.toString());

	/** the list of style */
	public static List<Styler> styles = Arrays.asList(
			LabelStylersEnum.DEFAULT.getStyler(),
			LabelStylersEnum.BLACK.getStyler(),
			LabelStylersEnum.BLUE.getStyler(),
			LabelStylersEnum.DEFAULT.getStyler(),
			LabelStylersEnum.GOLD.getStyler(),
			LabelStylersEnum.GREY.getStyler());

	/** the label provider */
	private final CustomizableDelegatingItemLabelProvider labelProvider = new CustomizableDelegatingItemLabelProvider(types, styles);

	/** Profile that has been applied. */
	private Profile profile = null;

	/** Profile Name. */
	private static final String PROFILE_NAME = "ProfileTest";

	/** The model */
	private Package model;

	/** The expected returned styled label for the model. */
	private static final StyledString MODEL_STYLED_LABEL = new StyledString()
			.append("<Model>", styles.get(1))
			.append(SPACE)
			.append(DASH_SEPARATOR, styles.get(2))
			.append(SPACE)
			.append("RootElement", styles.get(3))
			.append(SPACE)
			.append(COLON_SEPARATOR, styles.get(5))
			.append(SPACE)
			.append("RootElement", LabelStylersEnum.DEFAULT.getStyler());// $NON-NLS-1$

	/** The simple class. */
	private NamedElement simpleClass = null;

	/** The simple class name. */
	private static final String SIMPLE_CLASS_NAME = "SimpleClass";

	/** The expected returned styled label for the class. */
	private static final StyledString SIMPLE_CLASS_STYLED_LABEL = new StyledString()
			.append("<Class>", styles.get(1))
			.append(SPACE)
			.append(DASH_SEPARATOR, styles.get(2))
			.append(SPACE)
			.append("SimpleClass", styles.get(3))
			.append(SPACE)
			.append(COLON_SEPARATOR, styles.get(5))
			.append(SPACE)
			.append("RootElement::SimpleClass", LabelStylersEnum.DEFAULT.getStyler());

	/** The stereotyped class. */
	private NamedElement stereotypedClass = null;

	/** The stereotyped class name. */
	private static final String STEREOTYPED_CLASS_NAME = "StereotypedClass";

	/** The expected returned styled label for the stereotyped class. */
	private static final StyledString STEREOTYPED_CLASS_STYLED_LABEL = new StyledString()
			.append("<Class>", styles.get(1))
			.append(SPACE)
			.append(DASH_SEPARATOR, styles.get(2))
			.append(SPACE)
			.append("StereotypedClass", styles.get(3))
			.append(SPACE)
			.append(ST_LEFT + "Stereotype1" + ST_RIGHT, styles.get(4))
			.append(SPACE)
			.append(COLON_SEPARATOR, styles.get(5))
			.append(SPACE)
			.append("RootElement::StereotypedClass", LabelStylersEnum.DEFAULT.getStyler());

	/** The empty comment */
	private Comment emptyCommment = null;

	/** The expected returned styled label for the empty comment. */
	private static final StyledString EMPTY_COMMENT_STYLED_LABEL = new StyledString()
			.append("<Comment>", styles.get(1))
			.append(SPACE)
			.append(DASH_SEPARATOR, styles.get(2))
			.append(SPACE)
			.append("<Empty Comment>", styles.get(3));

	/** the comment */
	private Comment commment = null;

	/** The expected returned styled label for the comment. */
	private static final StyledString BODY_COMMENT_STYLED_LABEL = new StyledString()
			.append("<Comment>", styles.get(1))
			.append(SPACE)
			.append(DASH_SEPARATOR, styles.get(2))
			.append(SPACE)
			.append("body comment", styles.get(3));

	/** the list of style to type template test. */
	static List<String> types4templateParameterTest = new ArrayList<String>();
	static {
		// initialize label provider
		types4templateParameterTest.add(LabelTypesEnum.LABEL.toString());
	}

	/** The template signature. */
	private TemplateSignature templateSignature = null;

	/** The expected returned styled label for the comment. */
	private static final StyledString TEMPLATE_SIGNATURE_STYLED_LABEL = new StyledString()
			.append("<Template Signature>");

	/** The template parameters. */
	private TemplateParameter templateParameter = null;
	/** The expected returned styled label for the comment. */
	private static final StyledString TEMPLATE_PARAMETER_STYLED_LABEL = new StyledString()
			.append("<UNDEFINED>");

	/**
	 * Initialization of the test cases.
	 */
	@Before
	public void init() {

		// Gets all needed Element from loaded papyrus model

		model = editorFixture.getModel();
		assertNotNull("The model cannot be null", model);

		profile = model.getAppliedProfile(PROFILE_NAME);
		assertEquals("Profile is not the one Expected", PROFILE_NAME, profile.getName());

		simpleClass = model.getMember(SIMPLE_CLASS_NAME);
		assertTrue("The element is not a Class", simpleClass instanceof Class);
		assertEquals("Element is not the one expected", SIMPLE_CLASS_NAME, simpleClass.getName());

		stereotypedClass = model.getMember(STEREOTYPED_CLASS_NAME);
		assertTrue("The element is not a Class", stereotypedClass instanceof Class);
		assertEquals("Element is not the one expected", STEREOTYPED_CLASS_NAME, stereotypedClass.getName());

		for (Iterator<Comment> iterator = model.getOwnedComments().iterator(); iterator.hasNext();) {
			Comment comment = iterator.next();
			if (comment.getBody() == null) {
				emptyCommment = comment;
			} else {
				commment = comment;
			}
		}

		templateSignature = model.getOwnedTemplateSignature();
		assertNotNull("The template signature must exist", templateSignature);

		templateParameter = templateSignature.getParameters().get(0);
		assertNotNull("The template parameters must exist", templateParameter);
	}

	/**
	 * Test the return text of {@link CustomizableDelegatingItemLabelProvider}.
	 */
	@Test
	public void returnedTextTest() {

		assertEquals("model return label is incorrect", MODEL_STYLED_LABEL.getString(), labelProvider.getText(model));

		assertEquals("simple class return label is incorrect", SIMPLE_CLASS_STYLED_LABEL.getString(), labelProvider.getText(simpleClass));

		assertEquals("stereotyped Class return label is incorrect", STEREOTYPED_CLASS_STYLED_LABEL.getString(), labelProvider.getText(stereotypedClass));

		assertEquals("empty comment return label is incorrect", EMPTY_COMMENT_STYLED_LABEL.getString(), labelProvider.getText(emptyCommment));

		assertEquals("comment return label is incorrect", BODY_COMMENT_STYLED_LABEL.getString(), labelProvider.getText(commment));

		// Set label provider to display only the name
		labelProvider.setStylesList(types4templateParameterTest, Collections.<Styler> emptyList());

		assertEquals("template signature return label is incorrect", TEMPLATE_SIGNATURE_STYLED_LABEL.getString(), labelProvider.getText(templateSignature));

		assertEquals("template parameter return label is incorrect", TEMPLATE_PARAMETER_STYLED_LABEL.getString(), labelProvider.getText(templateParameter));
	}

	/**
	 * Test the returned styled text of {@link CustomizableDelegatingItemLabelProvider}.
	 */
	@Test
	public void returnedStyledTextTest() {

		checkStyledString(model, MODEL_STYLED_LABEL);

		checkStyledString(simpleClass, SIMPLE_CLASS_STYLED_LABEL);

		checkStyledString(stereotypedClass, STEREOTYPED_CLASS_STYLED_LABEL);

		checkStyledString(emptyCommment, EMPTY_COMMENT_STYLED_LABEL);

		checkStyledString(commment, BODY_COMMENT_STYLED_LABEL);

		// Set label provider to display only the name
		labelProvider.setStylesList(types4templateParameterTest, Collections.<Styler> emptyList());

		checkStyledString(templateSignature, TEMPLATE_SIGNATURE_STYLED_LABEL);

		checkStyledString(templateParameter, TEMPLATE_PARAMETER_STYLED_LABEL);
	}

	/**
	 * Check the return style string of the object compare to a style string
	 * 
	 * @param object
	 *            The object.
	 * @param objectStyledLabel
	 *            The styled string to compare with.
	 */
	protected void checkStyledString(final Object object, final StyledString objectStyledLabel) {
		StyledString styledText = labelProvider.getStyledText(object);
		StyleRange[] styleRanges = styledText.getStyleRanges();
		StyleRange[] styleRangesRef = objectStyledLabel.getStyleRanges();

		for (int i = 0; i < styleRanges.length; i++) {
			assertTrue("\n" + styleRanges[i].toString()
					+ "\n not similar to expected:\n "
					+ styleRangesRef[i] + "\n in "
					+ styledText.toString(), styleRanges[i].similarTo(styleRangesRef[i]));
		}
	}

}

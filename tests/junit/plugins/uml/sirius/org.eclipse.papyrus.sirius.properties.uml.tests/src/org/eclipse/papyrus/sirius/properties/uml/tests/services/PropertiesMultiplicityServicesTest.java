/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.sirius.properties.common.utils.SiriusInterpreterHelper;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesMultiplicityServices;
import org.eclipse.papyrus.sirius.properties.uml.tests.services.mock.MockPropertiesMultiplicityServices;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesMultiplicityServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesMultiplicityServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * The 0..* multiplicity.
	 */
	private static final String ANY = "0..*"; //$NON-NLS-1$
	/**
	 * "5..4" multiplicity.
	 */
	private static final String INVALID_POSITIVE = "5..4"; //$NON-NLS-1$
	/**
	 * "-1" value, which is the equivalent of the upper bound's integer value for "0..*".
	 */
	private static final String STAR_INTEGER_VALUE = "-1"; //$NON-NLS-1$
	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesMultiplicityServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesMultiplicityServices();
	}

	@Test
	public void testSetMultiplicityCorrectValues() {
		MultiplicityElement multiplicity = create(Property.class);
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices();

		assertTrue(mockPS.setMultiplicity(multiplicity, "1..1", null)); //$NON-NLS-1$
		assertEquals(1, multiplicity.lowerBound());
		assertEquals(1, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, "5..5", null)); //$NON-NLS-1$
		assertEquals(5, multiplicity.lowerBound());
		assertEquals(5, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, ANY, null)); // $NON-NLS-1$
		assertEquals(0, multiplicity.lowerBound());
		assertEquals(-1, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, "1", null)); //$NON-NLS-1$
		assertEquals(1, multiplicity.lowerBound());
		assertEquals(1, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, "5", null)); //$NON-NLS-1$
		assertEquals(5, multiplicity.lowerBound());
		assertEquals(5, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, "*", null)); //$NON-NLS-1$
		assertEquals(0, multiplicity.lowerBound());
		assertEquals(-1, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, "0..1", null)); //$NON-NLS-1$
		assertEquals(0, multiplicity.lowerBound());
		assertEquals(1, multiplicity.upperBound());
		assertTrue(mockPS.setMultiplicity(multiplicity, "1..*", null)); //$NON-NLS-1$
		assertEquals(1, multiplicity.lowerBound());
		assertEquals(-1, multiplicity.upperBound());
	}

	@Test
	public void testSetMultiplicityIncorrectValues() {
		MultiplicityElement multiplicity = create(Property.class);
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices();

		assertFalse(mockPS.setMultiplicity(multiplicity, "0..0", null)); //$NON-NLS-1$
		assertNull(multiplicity.getLowerValue());
		assertNull(multiplicity.getUpperValue());
		assertFalse(mockPS.setMultiplicity(multiplicity, "0..-1", null)); //$NON-NLS-1$
		assertNull(multiplicity.getLowerValue());
		assertNull(multiplicity.getUpperValue());
		assertFalse(mockPS.setMultiplicity(multiplicity, "-2..0", null)); //$NON-NLS-1$
		assertNull(multiplicity.getLowerValue());
		assertNull(multiplicity.getUpperValue());
		assertFalse(mockPS.setMultiplicity(multiplicity, INVALID_POSITIVE, null));
		assertNull(multiplicity.getLowerValue());
		assertNull(multiplicity.getUpperValue());
		assertFalse(mockPS.setMultiplicity(multiplicity, "1..-1", null)); //$NON-NLS-1$
		assertEquals(1, multiplicity.lowerBound());
		assertEquals(1, multiplicity.upperBound());
		assertFalse(mockPS.setMultiplicity(multiplicity, "1..-25", null)); //$NON-NLS-1$
		assertEquals(1, multiplicity.lowerBound());
		assertEquals(1, multiplicity.upperBound());
	}

	@Test
	public void testSetMultiplicityNullValue() {
		MultiplicityElement multiplicity = create(Property.class);
		assertFalse(propertiesService.setMultiplicity(multiplicity, null, null));
		assertNull(multiplicity.getLowerValue());
		assertNull(multiplicity.getUpperValue());

		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices();
		assertFalse(mockPS.setMultiplicity(multiplicity, null, null));
		assertNull(multiplicity.getLowerValue());
		assertNull(multiplicity.getUpperValue());
	}

	@Test
	public void testSetIncorrectMultiplicityUpperValueInitWithLiteralNull() {
		MultiplicityElement multiplicity = create(Property.class);
		LiteralNull literalNull = create(LiteralNull.class);
		multiplicity.setUpperValue(literalNull);
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices();

		assertFalse(mockPS.setMultiplicity(multiplicity, "-1..0", null)); //$NON-NLS-1$
		assertNull(multiplicity.getLowerValue());
		assertNotNull(multiplicity.getUpperValue());
	}

	@Test
	public void testSetIncorrectMultiplicityUpperValueInitWithLiteralInteger() {
		MultiplicityElement multiplicity = create(Property.class);
		LiteralInteger literalInteger = create(LiteralInteger.class);
		literalInteger.setValue(-1);
		multiplicity.setUpperValue(literalInteger);
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices();

		assertFalse(mockPS.setMultiplicity(multiplicity, "-1..0", null)); //$NON-NLS-1$
		assertNull(multiplicity.getLowerValue());
		assertNotNull(multiplicity.getUpperValue());
	}

	@Test
	public void testMultiplicityValueCorrectValues() {
		MultiplicityElement multiplicity = create(Property.class);
		LiteralInteger lowerValue = create(LiteralInteger.class);
		LiteralUnlimitedNatural upperValue = create(LiteralUnlimitedNatural.class);
		multiplicity.setLowerValue(lowerValue);
		multiplicity.setUpperValue(upperValue);
		lowerValue.setValue(4);
		upperValue.setValue(5);
		assertEquals("4..5", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$
		lowerValue.setValue(0);
		upperValue.setValue(-1);
		assertEquals(ANY, propertiesService.getMultiplicity(multiplicity));
		lowerValue.setValue(2);
		upperValue.setValue(2);
		assertEquals("2", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$
	}

	@Test
	public void testMultiplicityValueIncorrectValues() {
		MultiplicityElement multiplicity = create(Property.class);
		LiteralInteger lowerValue = create(LiteralInteger.class);
		LiteralUnlimitedNatural upperValue = create(LiteralUnlimitedNatural.class);
		multiplicity.setLowerValue(lowerValue);
		multiplicity.setUpperValue(upperValue);
		lowerValue.setValue(5);
		upperValue.setValue(4);
		assertEquals(INVALID_POSITIVE, propertiesService.getMultiplicity(multiplicity));

		lowerValue.setValue(0);
		upperValue.setValue(0);
		assertEquals("0", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$

		lowerValue.setValue(-12);
		upperValue.setValue(-5);
		assertEquals("-12..*", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$

		lowerValue.setValue(2);
		multiplicity.setUpperValue(create(LiteralBoolean.class));
		assertEquals("2..1", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$

		LiteralString lowerString = create(LiteralString.class);
		LiteralString upperString = create(LiteralString.class);
		multiplicity.setLowerValue(lowerString);
		multiplicity.setUpperValue(upperString);
		lowerString.setValue("5"); //$NON-NLS-1$
		upperString.setValue("4"); //$NON-NLS-1$
		assertEquals(INVALID_POSITIVE, propertiesService.getMultiplicity(multiplicity));

		lowerString.setValue(STAR_INTEGER_VALUE);
		upperString.setValue(STAR_INTEGER_VALUE);
		assertEquals(STAR_INTEGER_VALUE, propertiesService.getMultiplicity(multiplicity));
	}

	@Test
	public void testMultiplicityValueDefaultValues() {
		MultiplicityElement multiplicity = create(Property.class);
		assertEquals("1", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$
		ValueSpecification lowerValue = create(LiteralInteger.class);
		ValueSpecification upperValue = create(LiteralUnlimitedNatural.class);
		multiplicity.setLowerValue(lowerValue);
		multiplicity.setUpperValue(upperValue);
		assertEquals("0", propertiesService.getMultiplicity(multiplicity)); //$NON-NLS-1$
	}

	@Test
	public void testValidateMultiplicityField() {
		MultiplicityElement multiplicity = create(Property.class);
		LiteralInteger lowerValue = create(LiteralInteger.class);
		LiteralUnlimitedNatural upperValue = create(LiteralUnlimitedNatural.class);
		multiplicity.setLowerValue(lowerValue);
		multiplicity.setUpperValue(upperValue);
		lowerValue.setValue(0);
		upperValue.setValue(5);

		assertTrue(propertiesService.validateMultiplicityField(multiplicity, null));

		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices("", null); //$NON-NLS-1$
		assertTrue(mockPS.validateMultiplicityField(multiplicity, null));
	}

	@Test
	public void testValidateMultiplicityFieldIncorrectValue() {
		MultiplicityElement multiplicity = create(Property.class);
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices(SiriusInterpreterHelper.MULTIPLICITY_VALUE, INVALID_POSITIVE);

		assertFalse(mockPS.validateMultiplicityField(multiplicity, null));
	}

	@Test
	public void testGetMultiplicityInputStringValue() {
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices(SiriusInterpreterHelper.MULTIPLICITY_VALUE, INVALID_POSITIVE);
		assertEquals(INVALID_POSITIVE, mockPS.getMultiplicityAsString(null));
	}

	@Test
	public void testGetMultiplicityInputStringValueWrongTypeValue() {
		MockPropertiesMultiplicityServices mockPS = new MockPropertiesMultiplicityServices(SiriusInterpreterHelper.MULTIPLICITY_VALUE, Integer.valueOf(5));
		assertTrue(mockPS.getMultiplicityAsString(null).isEmpty());
	}

	@Test
	public void testGetMultiplicityInputStringValueNoDescriptor() {
		assertTrue(propertiesService.getMultiplicityAsString(null).isEmpty());
	}
}

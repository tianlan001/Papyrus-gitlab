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
import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.sirius.properties.common.utils.SiriusInterpreterHelper;
import org.eclipse.papyrus.sirius.properties.uml.services.PropertiesValueSpecificationServices;
import org.eclipse.papyrus.sirius.properties.uml.tests.services.mock.MockPropertiesValueSpecificationServices;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for {@link PropertiesValueSpecificationServices} service class.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesValueSpecificationServicesTest extends AbstractPropertiesServicesTest {

	/**
	 * Wildcard string.
	 */
	private static final String STAR = "*"; //$NON-NLS-1$

	/**
	 * An arbitrary string to initialize tests with a wrong value.
	 */
	private static final String A_STRING = "a"; //$NON-NLS-1$

	/**
	 * Integer as String.
	 */
	private static final String INTEGER_AS_STRING = "15"; //$NON-NLS-1$

	/**
	 * Double as String.
	 */
	private static final String DOUBLE_AS_STRING = "1.0"; //$NON-NLS-1$

	/**
	 * The instance of PropertiesServices being tested.
	 */
	private PropertiesValueSpecificationServices propertiesService;

	@Before
	public void setUp() {
		this.propertiesService = new PropertiesValueSpecificationServices();
	}

	@Test
	public void testGetLiteralIntegerValue() {
		LiteralInteger literalInteger = create(LiteralInteger.class);
		int value = 1;
		literalInteger.setValue(value);
		assertEquals(Integer.toString(value), propertiesService.getLiteralIntegerValue(literalInteger, null));
	}

	@Test
	public void testGetLiteralRealValue() {
		LiteralReal literalReal = create(LiteralReal.class);
		double value = 1.0;
		literalReal.setValue(value);
		assertEquals(Double.toString(value), propertiesService.getLiteralRealValue(literalReal, null));
	}

	@Test
	public void testGetLiteralUnlimitedNaturalValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);
		int value = 1;
		literalUnlimitedNatural.setValue(value);
		assertEquals(Integer.toString(value), propertiesService.getLiteralUnlimitedNaturalValue(literalUnlimitedNatural, null));
		value = -1;
		literalUnlimitedNatural.setValue(value);
		assertEquals(STAR, propertiesService.getLiteralUnlimitedNaturalValue(literalUnlimitedNatural, null));
	}

	@Test
	public void testGetDefaultLiteralIntegerValue() {
		LiteralInteger literalInteger = create(LiteralInteger.class);
		int value = 0;
		assertEquals(Integer.toString(value), propertiesService.getLiteralIntegerValue(literalInteger, null));
	}

	@Test
	public void testGetDefaultLiteralRealValue() {
		LiteralReal literalReal = create(LiteralReal.class);
		double value = 0.0;
		assertEquals(Double.toString(value), propertiesService.getLiteralRealValue(literalReal, null));
	}

	@Test
	public void testGetDefaultLiteralUnlimitedNaturalValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);
		int value = 0;
		assertEquals(Integer.toString(value), propertiesService.getLiteralUnlimitedNaturalValue(literalUnlimitedNatural, null));
	}

	@Test
	public void testSetLiteralIntegerValue() {
		LiteralInteger literalInteger = create(LiteralInteger.class);
		literalInteger.setValue(1);
		assertFalse(propertiesService.setLiteralIntegerValue(literalInteger, Integer.valueOf(2), null));
		assertEquals(1, literalInteger.getValue());
	}

	@Test
	public void testSetLiteralIntegerValueWithStringInteger() {
		LiteralInteger literalInteger = create(LiteralInteger.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertTrue(mockPS.setLiteralIntegerValue(literalInteger, INTEGER_AS_STRING, null));
		assertEquals(15, literalInteger.getValue());
	}

	@Test
	public void testSetLiteralIntegerValueWithInteger() {
		LiteralInteger literalInteger = create(LiteralInteger.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertTrue(mockPS.setLiteralIntegerValue(literalInteger, Integer.valueOf(1), null));
		assertEquals(1, literalInteger.getValue());
	}

	@Test
	public void testSetLiteralIntegerValueWithString() {
		LiteralInteger literalInteger = create(LiteralInteger.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertFalse(mockPS.setLiteralIntegerValue(literalInteger, A_STRING, null));
		assertEquals(0, literalInteger.getValue());
	}

	@Test
	public void testSetLiteralIntegerValueWithNull() {
		LiteralInteger literalInteger = create(LiteralInteger.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertFalse(mockPS.setLiteralIntegerValue(literalInteger, null, null));
		assertEquals(0, literalInteger.getValue());
	}

	@Test
	public void testSetLiteralRealValue() {
		LiteralReal literalReal = create(LiteralReal.class);
		literalReal.setValue(1.0);
		assertFalse(propertiesService.setLiteralRealValue(literalReal, Double.valueOf(2.0), null));
		assertEquals(1.0, literalReal.getValue(), 0);
	}

	@Test
	public void testSetLiteralRealValueWithStringDouble() {
		LiteralReal literalReal = create(LiteralReal.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertTrue(mockPS.setLiteralRealValue(literalReal, DOUBLE_AS_STRING, null));
		assertEquals(1, literalReal.getValue(), 0);
	}

	@Test
	public void testSetLiteralRealValueWithDouble() {
		LiteralReal literalReal = create(LiteralReal.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertTrue(mockPS.setLiteralRealValue(literalReal, Double.valueOf(1.0), null));
		assertEquals(1, literalReal.getValue(), 0);
	}

	@Test
	public void testSetLiteralRealValueWithString() {
		LiteralReal literalReal = create(LiteralReal.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertFalse(mockPS.setLiteralRealValue(literalReal, A_STRING, null));
		assertEquals(0, literalReal.getValue(), 0);
	}

	@Test
	public void testSetLiteralRealValueWithNull() {
		LiteralReal literalReal = create(LiteralReal.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertFalse(mockPS.setLiteralRealValue(literalReal, null, null));
		assertEquals(0, literalReal.getValue(), 0);
	}

	@Test
	public void testSetLiteralUnlimitedNaturalValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);
		literalUnlimitedNatural.setValue(1);
		assertFalse(propertiesService.setLiteralUnlimitedNaturalValue(literalUnlimitedNatural, Integer.valueOf(2), null));
		assertEquals(1, literalUnlimitedNatural.getValue());
	}

	@Test
	public void testSetLiteralUnlimitedNaturalValueWithIntegerValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertTrue(mockPS.setLiteralUnlimitedNaturalValue(literalUnlimitedNatural, INTEGER_AS_STRING, null));
		assertEquals(15, literalUnlimitedNatural.getValue());
		assertTrue(mockPS.setLiteralUnlimitedNaturalValue(literalUnlimitedNatural, STAR, null));
		assertEquals(-1, literalUnlimitedNatural.getValue());
	}

	@Test
	public void testSetLiteralUnlimitedNaturalValueWithInteger() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertTrue(mockPS.setLiteralUnlimitedNaturalValue(literalUnlimitedNatural, Integer.valueOf(1), null));
		assertEquals(1, literalUnlimitedNatural.getValue());
	}

	@Test
	public void testSetLiteralUnlimitedNaturalValueWithString() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertFalse(mockPS.setLiteralUnlimitedNaturalValue(literalUnlimitedNatural, A_STRING, null));
		assertEquals(0, literalUnlimitedNatural.getValue());
	}

	@Test
	public void testSetLiteralUnlimitedNaturalValueWithNull() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices();
		assertFalse(mockPS.setLiteralUnlimitedNaturalValue(literalUnlimitedNatural, null, null));
		assertEquals(0, literalUnlimitedNatural.getValue());
	}

	@Test
	public void testValidateLiteralIntegerField() {
		LiteralInteger literalInteger = create(LiteralInteger.class);
		literalInteger.setValue(1);
		assertTrue(propertiesService.validateLiteralIntegerField(literalInteger, null));
	}

	@Test
	public void testValidateLiteralIntegerFieldCorrectStringValue() {
		LiteralInteger literalInteger = create(LiteralInteger.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.INTEGER_VALUE, INTEGER_AS_STRING);
		assertTrue(mockPS.validateLiteralIntegerField(literalInteger, null));
	}

	@Test
	public void testValidateLiteralIntegerFieldWrongStringValue() {
		LiteralInteger literalInteger = create(LiteralInteger.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.INTEGER_VALUE, A_STRING);
		assertFalse(mockPS.validateLiteralIntegerField(literalInteger, null));
	}

	@Test
	public void testValidateLiteralRealField() {
		LiteralReal literalReal = create(LiteralReal.class);
		literalReal.setValue(1.0);
		assertTrue(propertiesService.validateLiteralRealField(literalReal, null));
	}

	@Test
	public void testValidateLiteralRealFieldCorrectStringValue() {
		LiteralReal literalReal = create(LiteralReal.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.REAL_VALUE, DOUBLE_AS_STRING);
		assertTrue(mockPS.validateLiteralRealField(literalReal, null));
	}

	@Test
	public void testValidateLiteralRealFieldWrongStringValue() {
		LiteralReal literalReal = create(LiteralReal.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.REAL_VALUE, A_STRING);
		assertFalse(mockPS.validateLiteralRealField(literalReal, null));
	}

	@Test
	public void testValidateLiteralUnlimitedNaturalField() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);
		literalUnlimitedNatural.setValue(1);
		assertTrue(propertiesService.validateLiteralUnlimitedNaturalField(literalUnlimitedNatural, null));
	}

	@Test
	public void testValidateLiteralUnlimitedNaturalFieldCorrectIntegerStringValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, INTEGER_AS_STRING);
		assertTrue(mockPS.validateLiteralUnlimitedNaturalField(literalUnlimitedNatural, null));
	}

	@Test
	public void testValidateLiteralUnlimitedNaturalFieldCorrectWildcardStringValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, STAR);
		assertTrue(mockPS.validateLiteralUnlimitedNaturalField(literalUnlimitedNatural, null));
	}

	@Test
	public void testValidateLiteralUnlimitedNaturalFieldWrongStringValue() {
		LiteralUnlimitedNatural literalUnlimitedNatural = create(LiteralUnlimitedNatural.class);

		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, A_STRING);
		assertFalse(mockPS.validateLiteralUnlimitedNaturalField(literalUnlimitedNatural, null));
	}

	@Test
	public void testGetLiteralIntegerInputStringValue() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.INTEGER_VALUE, INTEGER_AS_STRING);
		assertEquals(INTEGER_AS_STRING, mockPS.getLiteralIntegerInputStringValue(null));
	}

	@Test
	public void testGetLiteralIntegerInputStringValueArbitraryInput() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.INTEGER_VALUE, A_STRING);
		assertEquals(A_STRING, mockPS.getLiteralIntegerInputStringValue(null));
	}

	@Test
	public void testGetLiteralIntegerInputStringValueIntegerValue() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.INTEGER_VALUE, Integer.valueOf(INTEGER_AS_STRING));
		assertTrue(mockPS.getLiteralIntegerInputStringValue(null).isEmpty());
	}

	@Test
	public void testGetLiteralRealInputStringValue() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.REAL_VALUE, DOUBLE_AS_STRING);
		assertEquals(DOUBLE_AS_STRING, mockPS.getLiteralRealInputStringValue(null));
	}

	@Test
	public void testGetLiteralRealInputStringValueArbitraryInput() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.REAL_VALUE, A_STRING);
		assertEquals(A_STRING, mockPS.getLiteralRealInputStringValue(null));
	}

	@Test
	public void testGetLiteralRealInputStringValueRealValue() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.REAL_VALUE, Double.valueOf(DOUBLE_AS_STRING));
		assertTrue(mockPS.getLiteralRealInputStringValue(null).isEmpty());
	}

	@Test
	public void testGetLiteralUnlimitedNaturalInputStringValue() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, INTEGER_AS_STRING);
		assertEquals(INTEGER_AS_STRING, mockPS.getLiteralUnlimitedNaturalInputStringValue(null));
	}

	@Test
	public void testGetLiteralUnlimitedNaturalInputStringValueArbitraryInput() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, A_STRING);
		assertEquals(A_STRING, mockPS.getLiteralUnlimitedNaturalInputStringValue(null));
	}

	@Test
	public void testGetLiteralUnlimitedNaturalInputStringValueIntegerValue() {
		PropertiesValueSpecificationServices mockPS = new MockPropertiesValueSpecificationServices(SiriusInterpreterHelper.UNLIMITED_NATURAL_VALUE, Integer.valueOf(INTEGER_AS_STRING));
		assertTrue(mockPS.getLiteralUnlimitedNaturalInputStringValue(null).isEmpty());
	}

}

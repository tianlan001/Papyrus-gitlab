package org.eclipse.papyrus.uml.diagram.common.tests.parser;

import static org.junit.Assert.assertEquals;

import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.tools.utils.ValueSpecificationUtil;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Assert;
import org.junit.Test;

public class ValueSpecificationUtilTestCase extends AbstractPapyrusTest {

	private UMLFactory factory;

	@Test
	public void testLiteralInteger() {
		Assert.assertEquals("1", ValueSpecificationUtil.getSpecificationValue(createLiteralInteger(1)));
	}

	@Test
	public void testLiteralIntegerNegative() {
		Assert.assertEquals("-1", ValueSpecificationUtil.getSpecificationValue(createLiteralInteger(-1)));
	}

	@Test
	public void testLiteralUnlimitedNaturalInfinitive() {
		assertEquals("*", ValueSpecificationUtil.getSpecificationValue(createLiteralUnlimitedNatural(-1)));
	}

	@Test
	public void testLiteralUnlimitedNatural() {
		assertEquals("100", ValueSpecificationUtil.getSpecificationValue(createLiteralUnlimitedNatural(100)));
	}

	@Test
	public void testLiteralString() {
		assertEquals("EXACT", ValueSpecificationUtil.getSpecificationValue(createLiteralString("EXACT")));
	}

	@Test
	public void testLiteralBoolean() {
		Assert.assertEquals("true", ValueSpecificationUtil.getSpecificationValue(createLiteralBoolean(true)));
	}

	@Test
	public void testLiteralBooleanFalse() {
		Assert.assertEquals("false", ValueSpecificationUtil.getSpecificationValue(createLiteralBoolean(false)));
	}

	@Test
	public void testLiteralNull() {
		assertEquals("", ValueSpecificationUtil.getSpecificationValue(createLiteralNull()));
	}

	@Test
	public void testLiteralReal() {
		assertEquals("0.5", ValueSpecificationUtil.getSpecificationValue(createLiteralReal(0.5)));
	}

	private UMLFactory getFactory() {
		if (factory == null) {
			factory = UMLFactory.eINSTANCE;
		}
		return factory;
	}

	private LiteralInteger createLiteralInteger(int value) {
		LiteralInteger result = getFactory().createLiteralInteger();
		result.setValue(value);
		return result;
	}

	private LiteralUnlimitedNatural createLiteralUnlimitedNatural(int value) {
		LiteralUnlimitedNatural result = getFactory().createLiteralUnlimitedNatural();
		result.setValue(value);
		return result;
	}

	private LiteralString createLiteralString(String value) {
		LiteralString result = getFactory().createLiteralString();
		result.setValue(value);
		return result;
	}

	private LiteralBoolean createLiteralBoolean(boolean value) {
		LiteralBoolean result = getFactory().createLiteralBoolean();
		result.setValue(value);
		return result;
	}

	private LiteralNull createLiteralNull() {
		return getFactory().createLiteralNull();
	}

	private LiteralReal createLiteralReal(double value) {
		LiteralReal result = getFactory().createLiteralReal();
		result.setValue(value);
		return result;
	}
}

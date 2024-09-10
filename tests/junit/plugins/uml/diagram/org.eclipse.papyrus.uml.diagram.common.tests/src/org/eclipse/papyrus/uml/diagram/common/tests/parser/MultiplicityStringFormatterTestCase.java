package org.eclipse.papyrus.uml.diagram.common.tests.parser;

import static org.junit.Assert.assertEquals;

import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.tools.utils.MultiplicityElementUtil;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.ValueSpecification;
import org.junit.Assert;
import org.junit.Test;

public class MultiplicityStringFormatterTestCase extends AbstractPapyrusTest {

	private UMLFactory factory;

	@Test
	public void testMultiplicityLiteralLowerUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(1), createLiteralUnlimitedNatural(5));
		Assert.assertEquals("1..5", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityIntValues() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), 1, 5);
		assertEquals("1..5", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityInfinityIntRange() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), 1, -1);
		assertEquals("1..*", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityInfinityLiteralRange() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(1), createLiteralUnlimitedNatural(-1));
		assertEquals("1..*", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityInfinityLiteral() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(0), createLiteralUnlimitedNatural(-1));
		assertEquals("*", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityInfinity() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), 0, -1);
		assertEquals("*", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityEqualsLiteralLowerUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(1), createLiteralUnlimitedNatural(1));
		assertEquals("1", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityLiteralLowerEqualsUpperWithBrackets() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(2), createLiteralUnlimitedNatural(2));
		assertEquals("[2]", getMultiplicityWithBrackets(element).trim());
	}

	@Test
	public void testMultiplicityLowerEqualsUpperWithBrackets() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), 2, 2);
		assertEquals("[2]", getMultiplicityWithBrackets(element).trim());
	}

	@Test
	public void testMultiplicityStringLiteralEqualsWithBrackets() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("EXACT"), createLiteralString("EXACT"));
		assertEquals("[EXACT]", getMultiplicityWithBrackets(element).trim());
	}

	@Test
	public void testMultiplicityLiteralLowerIsNull() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), null, createLiteralString("UPPER"));
		assertEquals("1..UPPER", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityLiteralUpperIsNull() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("LOWER"), null);
		assertEquals("LOWER..1", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityLiteralLowerIntUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("LOWER"), 20);
		assertEquals("LOWER..20", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityStringLiteralLowerIntLiteralUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("LOWER"), createLiteralUnlimitedNatural(20));
		assertEquals("LOWER..20", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityIntLiteralLowerStringLiteralUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(10), createLiteralString("UPPER"));
		assertEquals("10..UPPER", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityIntLowerStringLiteralUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), 10, createLiteralString("UPPER"));
		assertEquals("10..UPPER", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityStringLiteralLowerLiteralInfinity() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("LOWER"), createLiteralUnlimitedNatural(-1));
		assertEquals("LOWER..*", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityStringLiteralLowerInfinity() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("LOWER"), -1);
		assertEquals("LOWER..*", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityIntLowerStringUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(1), createLiteralString("1"));
		assertEquals("1", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityInfinityIntLowerStringUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralInteger(-1), createLiteralString("1"));
		assertEquals("-1..1", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityInfinityLowerUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), -1, -1);
		assertEquals("-1", getMultiplicityWithoutBrackets(element));
	}
	
	@Test
	public void testMultiplicityIntLowerIntUpperInfinity() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), -2, -1);
		assertEquals("-2..*", getMultiplicityWithoutBrackets(element));
	}
	
	@Test
	public void testMultiplicityInfinityIntLowerIntUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), -1, 1);
		assertEquals("-1..1", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityDefault() {
		assertEquals("1", getMultiplicityWithoutBrackets(createProperty()));
	}

	@Test
	public void testMultiplicityStringLowerUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("LOWER"), createLiteralString("UPPER"));
		assertEquals("LOWER..UPPER", getMultiplicityWithoutBrackets(element));
	}

	@Test
	public void testMultiplicityStringInfinityLowerUpper() {
		MultiplicityElement element = setupMultiplicityElement(createProperty(), createLiteralString("*"), createLiteralString("UPPER"));
		assertEquals("", getMultiplicityWithoutBrackets(element));
	}

	private Property createProperty() {
		return getFactory().createProperty();
	}

	private UMLFactory getFactory() {
		if (factory == null) {
			factory = UMLFactory.eINSTANCE;
		}
		return factory;
	}

	private String getMultiplicityWithoutBrackets(MultiplicityElement element) {
		return MultiplicityElementUtil.formatMultiplicityNoBrackets(element);
	}

	private String getMultiplicityWithBrackets(MultiplicityElement element) {
		return MultiplicityElementUtil.getMultiplicityAsString(element);
	}

	private MultiplicityElement setupMultiplicityElement(MultiplicityElement element, ValueSpecification lower, ValueSpecification upper) {
		element.setLowerValue(lower);
		element.setUpperValue(upper);
		return element;
	}

	private MultiplicityElement setupMultiplicityElement(MultiplicityElement element, int lower, int upper) {
		element.setLower(lower);
		element.setUpper(upper);
		return element;
	}

	private MultiplicityElement setupMultiplicityElement(MultiplicityElement element, ValueSpecification lower, int upper) {
		element.setLowerValue(lower);
		element.setUpper(upper);
		return element;
	}

	private MultiplicityElement setupMultiplicityElement(MultiplicityElement element, int lower, ValueSpecification upper) {
		element.setLower(lower);
		element.setUpperValue(upper);
		return element;
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
}

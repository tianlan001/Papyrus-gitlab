/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.tests.tests;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EOperationAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;

/**
 * This class allows to define the methods to check the table data.
 */
public class StereotypeDisplayTableTestsUtils {

	/**
	 * The class name.
	 */
	private static final String CLASS1_NAME = "Class1";

	/**
	 * The interface name.
	 */
	private static final String INTERFACE1_NAME = "Interface1";

	/**
	 * The port name.
	 */
	private static final String PORT1_NAME = "Port1";

	/**
	 * The association name.
	 */
	private static final String ASSOCIATION1_NAME = "Association1";

	/**
	 * The time observation name.
	 */
	private static final String TIME_OBSERVATION1_NAME = "TimeObservation1";

	/**
	 * The human stereotype name.
	 */
	private static final String HUMAN_STEREOTYPE = "Human";

	/**
	 * The animal stereotype name.
	 */
	private static final String ANIMAL_STEREOTYPE = "Animal";

	/**
	 * The non available value.
	 */
	private static final String NA = "N/A";

	/**
	 * Check the columns.
	 * 
	 * @param columnElements
	 *            The column elements.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkColumns(final List<?> columnElements) throws Exception {
		int indexColumn = 0;
		final IAxis firstColumn = (IAxis) columnElements.get(indexColumn);
		final Object firstColumnRepresentedElement = firstColumn.getElement();
		Assert.assertEquals("stereotype_display_properties:/nameDepth", firstColumnRepresentedElement);
		indexColumn++;

		final IAxis secondColumn = (IAxis) columnElements.get(indexColumn);
		final Object secondColumnRepresentedElement = secondColumn.getElement();
		Assert.assertEquals("stereotype_display_properties:/isDisplayed", secondColumnRepresentedElement);
		indexColumn++;

		final IAxis thirdColumn = (IAxis) columnElements.get(indexColumn);
		final Object thirdColumnRepresentedElement = thirdColumn.getElement();
		Assert.assertEquals("stereotype_display_properties:/isDisplayedInBraces", thirdColumnRepresentedElement);
		indexColumn++;

		final IAxis fourthColumn = (IAxis) columnElements.get(indexColumn);
		final Object fourthColumnRepresentedElement = fourthColumn.getElement();
		Assert.assertEquals("stereotype_display_properties:/isDisplayedInComment", fourthColumnRepresentedElement);
		indexColumn++;

		final IAxis fifthColumn = (IAxis) columnElements.get(indexColumn);
		final Object fifthColumnRepresentedElement = fifthColumn.getElement();
		Assert.assertEquals("stereotype_display_properties:/isDisplayedInCompartment", fifthColumnRepresentedElement);
	}

	/**
	 * Check the rows concerning the Class object.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @param firstPosition
	 *            The position of the first object to inspect.
	 * @param rowsNumberToCheck
	 *            The rows number to check.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkClassRows(final List<?> rowElements, final int firstPosition, final int rowsNumberToCheck) throws Exception {
		// Check the generic rows
		checkGenericRows(rowElements, firstPosition, rowsNumberToCheck, HUMAN_STEREOTYPE);

		// Check the specific row
		int indexRow = firstPosition + 2;
		final IAxis thirdRow = (IAxis) rowElements.get(indexRow);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(CLASS1_NAME, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName());
	}

	/**
	 * Check the rows for the interface object.
	 * 
	 * @param rowElements
	 *            The rows elements.
	 * @param firstPosition
	 *            The position of the first object to inspect.
	 * @param rowsNumberToCheck
	 *            The rows number to check.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkInterfaceRows(final List<?> rowElements, final int firstPosition, final int rowsNumberToCheck) throws Exception {
		// Check the generic rows
		checkGenericRows(rowElements, firstPosition, rowsNumberToCheck, ANIMAL_STEREOTYPE);

		// Check the specific row
		int indexRow = firstPosition + 2;
		final IAxis thirdRow = (IAxis) rowElements.get(indexRow);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getInterface(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(INTERFACE1_NAME, ((org.eclipse.uml2.uml.Interface) thirdRowRepresentedElement).getName());
	}

	/**
	 * Check the rows concerning the Class object.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @param firstPosition
	 *            The position of the first object to inspect.
	 * @param rowsNumberToCheck
	 *            The rows number to check.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkPortRows(final List<?> rowElements, final int firstPosition, final int rowsNumberToCheck) throws Exception {
		// Check the generic rows
		checkGenericRows(rowElements, firstPosition, rowsNumberToCheck, HUMAN_STEREOTYPE);

		// Check the specific row
		int indexRow = firstPosition + 2;
		final IAxis thirdRow = (IAxis) rowElements.get(indexRow);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getPort(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(PORT1_NAME, ((org.eclipse.uml2.uml.Port) thirdRowRepresentedElement).getName());
	}

	/**
	 * Check the rows concerning the Class object.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @param firstPosition
	 *            The position of the first object to inspect.
	 * @param rowsNumberToCheck
	 *            The rows number to check.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkAssociationRows(final List<?> rowElements, final int firstPosition, final int rowsNumberToCheck) throws Exception {
		// Check the generic rows
		checkGenericRows(rowElements, firstPosition, rowsNumberToCheck, ANIMAL_STEREOTYPE);

		// Check the specific row
		int indexRow = firstPosition + 2;
		final IAxis thirdRow = (IAxis) rowElements.get(indexRow);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getAssociation(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(ASSOCIATION1_NAME, ((org.eclipse.uml2.uml.Association) thirdRowRepresentedElement).getName());
	}

	/**
	 * Check the rows concerning the Class object.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @param firstPosition
	 *            The position of the first object to inspect.
	 * @param rowsNumberToCheck
	 *            The rows number to check.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkTimeObservationRows(final List<?> rowElements, final int firstPosition, final int rowsNumberToCheck) throws Exception {
		// Check the generic rows
		checkGenericRows(rowElements, firstPosition, rowsNumberToCheck, ANIMAL_STEREOTYPE);

		// Check the specific row
		int indexRow = firstPosition + 2;
		final IAxis thirdRow = (IAxis) rowElements.get(indexRow);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getTimeObservation(), ((EObject) thirdRowRepresentedElement).eClass());
		Assert.assertEquals(TIME_OBSERVATION1_NAME, ((org.eclipse.uml2.uml.TimeObservation) thirdRowRepresentedElement).getName());
	}

	/**
	 * Check the generic rows for the stereotype display table.
	 * 
	 * @param rowElements
	 *            The row elements.
	 * @param firstPosition
	 *            The position of the first object to inspect.
	 * @param rowsNumberToCheck
	 *            The rows number to check.
	 * @param stereotypeNameApplied
	 *            The name of the stereotype applied.
	 * @throws Exception
	 *             The exception.
	 */
	private static void checkGenericRows(final List<?> rowElements, final int firstPosition, final int rowsNumberToCheck, final String stereotypeNameApplied) throws Exception {
		int indexRow = firstPosition;
		final IAxis firstRow = (IAxis) rowElements.get(indexRow);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row is not a view", firstRowRepresentedElement instanceof View);
		indexRow++;

		final IAxis secondRow = (IAxis) rowElements.get(indexRow);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertTrue(secondRowRepresentedElement instanceof TreeFillingConfiguration);
		final IAxis secondAxis = ((TreeFillingConfiguration) secondRowRepresentedElement).getAxisUsedAsAxisProvider();
		Assert.assertTrue(secondAxis instanceof EStructuralFeatureAxis);
		Assert.assertEquals(NotationPackage.eINSTANCE.getView_Element(), secondAxis.getElement());
		indexRow++;

		// The third row is specific, don't check it here
		indexRow++;

		final IAxis fourthRow = (IAxis) rowElements.get(indexRow);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertTrue(fourthRowRepresentedElement instanceof TreeFillingConfiguration);
		final IAxis fourthAxis = ((TreeFillingConfiguration) fourthRowRepresentedElement).getAxisUsedAsAxisProvider();
		Assert.assertTrue(fourthAxis instanceof EOperationAxis);
		Assert.assertEquals(UMLPackage.eINSTANCE.getElement__GetAppliedStereotypes(), fourthAxis.getElement());
		indexRow++;

		final IAxis fifthRow = (IAxis) rowElements.get(indexRow);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals(UMLPackage.eINSTANCE.getStereotype(), ((EObject) fifthRowRepresentedElement).eClass());
		Assert.assertEquals(stereotypeNameApplied, ((org.eclipse.uml2.uml.Stereotype) fifthRowRepresentedElement).getName());
		indexRow++;

		final IAxis sixthRow = (IAxis) rowElements.get(indexRow);
		final Object sixthRowRepresentedElement = sixthRow.getElement();
		Assert.assertTrue(sixthRowRepresentedElement instanceof TreeFillingConfiguration);
		final IAxis sixthAxis = ((TreeFillingConfiguration) sixthRowRepresentedElement).getAxisUsedAsAxisProvider();
		Assert.assertTrue(sixthAxis instanceof EOperationAxis);
		Assert.assertEquals(UMLPackage.eINSTANCE.getClassifier__GetAllAttributes(), sixthAxis.getElement());
		indexRow++;

		for (; indexRow < firstPosition + rowsNumberToCheck; indexRow++) {
			final IAxis lastRow = (IAxis) rowElements.get(indexRow);
			final Object lastRowRepresentedElement = lastRow.getElement();
			final StringBuilder errorMessage = new StringBuilder("The object at row ");
			errorMessage.append(indexRow);
			errorMessage.append(" is not a property");
			Assert.assertEquals(errorMessage.toString(), UMLPackage.eINSTANCE.getProperty(), ((EObject) lastRowRepresentedElement).eClass());
		}
	}

	/**
	 * Check the initial data in the table.
	 * 
	 * @param manager
	 *            The tree table model manager.
	 * @param firstPosition
	 *            The first position which one to check.
	 * @param rowsNumberToCheck
	 *            The number of rows to check.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkInitialTableData(final ITreeNattableModelManager manager, final int firstPosition, final int rowsNumberToCheck) throws Exception {
		checkInitialTableData(manager, firstPosition, rowsNumberToCheck, false, false, false);
	}
	
	/**
	 * Check the initial data in the table.
	 * 
	 * @param manager
	 *            The tree table model manager.
	 * @param firstPosition
	 *            The first position which one to check.
	 * @param rowsNumberToCheck
	 *            The number of rows to check.
	 * @param isBracesNA
	 *            Boolean to determinate if the column 'In Braces' fill be filled by 'N/A' value.
	 * @param isCommentNA
	 *            Boolean to determinate if the column 'In Comment' fill be filled by 'N/A' value.
	 * @param isCompartmentNA
	 *            Boolean to determinate if the column 'In Compartment' fill be filled by 'N/A' value.
	 * @throws Exception
	 *             The exception.
	 */
	public static void checkInitialTableData(final ITreeNattableModelManager manager, final int firstPosition, final int rowsNumberToCheck, final boolean isBracesNA, final boolean isCommentNA, final boolean isCompartmentNA) throws Exception {
		// Check name depth
		int columnIndex = 0;
		for (int rowIndex = firstPosition; rowIndex < firstPosition + rowsNumberToCheck; rowIndex++) {
			final Object depthValue = manager.getDataValue(columnIndex, rowIndex);
			// The depth value is initialized only for the Stereotype object
			if ((firstPosition + 4) == rowIndex) {
				final StringBuilder errorMessage = new StringBuilder("The initial value of depth will be 'none' for row ");
				errorMessage.append(rowIndex);
				Assert.assertEquals(errorMessage.toString() + rowIndex, "none", depthValue);
			} else {
				final StringBuilder errorMessage = new StringBuilder("The depth value won't be initialized for row ");
				errorMessage.append(rowIndex);
				Assert.assertTrue(errorMessage.toString(), depthValue instanceof String);
				Assert.assertTrue(errorMessage.toString(), ((String) depthValue).isEmpty());
			}
		}

		// Check Visible
		columnIndex++;
		for (int rowIndex = firstPosition; rowIndex < firstPosition + rowsNumberToCheck; rowIndex++) {
			final Object visibleValue = manager.getDataValue(columnIndex, rowIndex);
			if ((firstPosition + 4) == rowIndex) {
				final StringBuilder errorMessage = new StringBuilder("The visible display value will be true by intiialisation for row ");
				errorMessage.append(rowIndex);
				Assert.assertEquals(errorMessage.toString(), true, visibleValue);
			} else {
				final StringBuilder errorMessage = new StringBuilder("The visible display value won't be initialized for row ");
				errorMessage.append(rowIndex);
				Assert.assertTrue(errorMessage.toString(), visibleValue instanceof String);
				Assert.assertTrue(errorMessage.toString(), ((String) visibleValue).isEmpty());
			}
		}

		// Check Braces
		checkInitialBracesColumn(manager, firstPosition, rowsNumberToCheck, isBracesNA);

		// Check Comment
		checkInitialCommentColumn(manager, firstPosition, rowsNumberToCheck, isCommentNA);

		// Check Compartment
		checkInitialCompartmentColumn(manager, firstPosition, rowsNumberToCheck, isCompartmentNA);
	}

	/**
	 * Check the initial data in the 'In Braces' column.
	 * 
	 * @param manager
	 *            The tree table model manager.
	 * @param firstPosition
	 *            The first position which one to check.
	 * @param rowsNumberToCheck
	 *            The number of rows to check.
	 * @param isBracesNA
	 *            Boolean to determinate if the column 'In Braces' fill be filled by 'N/A' value.
	 * @throws Exception
	 *             The exception.
	 */
	private static void checkInitialBracesColumn(final ITreeNattableModelManager manager, final int firstPosition, final int rowsNumberToCheck, final boolean isBracesNA) throws Exception {
		// Check Braces
		int columnIndex = 2;
		for (int rowIndex = firstPosition; rowIndex < firstPosition + rowsNumberToCheck; rowIndex++) {
			final Object braceValue = manager.getDataValue(columnIndex, rowIndex);
			if ((firstPosition + 4) == rowIndex || (firstPosition + 6) <= rowIndex) {
				Object valueToCheck = true;
				if (isBracesNA){
					valueToCheck = NA;
				}else if ((firstPosition + 4) == rowIndex) {
					valueToCheck = false;
				}
				
				final StringBuilder errorMessage = new StringBuilder("The braces display value will be initiazed by '");
				errorMessage.append(valueToCheck);
				errorMessage.append("' value for row ");
				errorMessage.append(rowIndex);
				Assert.assertEquals(errorMessage.toString(), valueToCheck, braceValue);
			} else {
				final StringBuilder errorMessage = new StringBuilder("The braces display value won't be initialized for row ");
				errorMessage.append(rowIndex);
				Assert.assertTrue(errorMessage.toString(), braceValue instanceof String);
				Assert.assertTrue(errorMessage.toString(), ((String) braceValue).isEmpty());
			}
		}
	}

	/**
	 * Check the initial data in the 'In Comment' column.
	 * 
	 * @param manager
	 *            The tree table model manager.
	 * @param firstPosition
	 *            The first position which one to check.
	 * @param rowsNumberToCheck
	 *            The number of rows to check.
	 * @param isCommentNA
	 *            Boolean to determinate if the column 'In Comment' fill be filled by 'N/A' value.
	 * @throws Exception
	 *             The exception.
	 */
	private static void checkInitialCommentColumn(final ITreeNattableModelManager manager, final int firstPosition, final int rowsNumberToCheck, final boolean isCommentNA) throws Exception {
		// Check Comment
		int columnIndex = 3;
		for (int rowIndex = firstPosition; rowIndex < firstPosition + rowsNumberToCheck; rowIndex++) {
			final Object commentValue = manager.getDataValue(columnIndex, rowIndex);
			if ((firstPosition + 4) == rowIndex || (firstPosition + 6) <= rowIndex) {
				Object valueToCheck = true;
				if (isCommentNA){
					valueToCheck = NA;
				}else if ((firstPosition + 4) == rowIndex) {
					valueToCheck = false;
				}
				
				final StringBuilder errorMessage = new StringBuilder("The comment display value will be initiazed by '");
				errorMessage.append(valueToCheck);
				errorMessage.append("' value for row ");
				errorMessage.append(rowIndex);
				Assert.assertEquals(errorMessage.toString(), valueToCheck, commentValue);
			} else {
				final StringBuilder errorMessage = new StringBuilder("The comment display value won't be initialized for row ");
				errorMessage.append(rowIndex);
				Assert.assertTrue(errorMessage.toString(), commentValue instanceof String);
				Assert.assertTrue(errorMessage.toString(), ((String) commentValue).isEmpty());
			}
		}
	}

	/**
	 * Check the initial data in the 'In Compartment' column.
	 * 
	 * @param manager
	 *            The tree table model manager.
	 * @param firstPosition
	 *            The first position which one to check.
	 * @param rowsNumberToCheck
	 *            The number of rows to check.
	 * @param isCompartmentNA
	 *            Boolean to determinate if the column 'In Compartment' fill be filled by 'N/A' value.
	 * @throws Exception
	 *             The exception.
	 */
	private static void checkInitialCompartmentColumn(final ITreeNattableModelManager manager, final int firstPosition, final int rowsNumberToCheck, final boolean isCompartmentNA) throws Exception {
		// Check Compartment
		int columnIndex = 4;
		for (int rowIndex = firstPosition; rowIndex < firstPosition + rowsNumberToCheck; rowIndex++) {
			final Object compartmentValue = manager.getDataValue(columnIndex, rowIndex);
			if ((firstPosition + 4) == rowIndex || (firstPosition + 6) <= rowIndex) {
				Object valueToCheck = true;
				if (isCompartmentNA){
					valueToCheck = NA;
				}else if ((firstPosition + 4) == rowIndex) {
					valueToCheck = false;
				}
				
				final StringBuilder errorMessage = new StringBuilder("The compartment display value will be initiazed by '");
				errorMessage.append(valueToCheck);
				errorMessage.append("' value for row ");
				errorMessage.append(rowIndex);
				Assert.assertEquals(errorMessage.toString(), valueToCheck, compartmentValue);
			} else {
				final StringBuilder errorMessage = new StringBuilder("The compartment display value won't be initialized for row ");
				errorMessage.append(rowIndex);
				Assert.assertTrue(errorMessage.toString(), compartmentValue instanceof String);
				Assert.assertTrue(errorMessage.toString(), ((String) compartmentValue).isEmpty());
			}
		}
	}


	/**
	 * Try to modify some values in the human stereotyped object table value.
	 * 
	 * @param helper
	 *            The Stereotype display helper.
	 * @param manager
	 *            The tree nattable model manager.
	 * @param class1View
	 *            The view of the class1.
	 * @param firstPosition
	 *            The first row position.
	 * @throws Exception
	 *             The exception.
	 */
	public static void modifyHumanStereotypedValues(final StereotypeDisplayUtil helper, final ITreeNattableModelManager manager, final View class1View, final Element class1, final int firstPosition) throws Exception {
		final Stereotype humanStereotype = class1.getAppliedStereotype("RootElement::Body::Human");
		final View label = helper.getStereotypeLabel(class1View, humanStereotype);
		Assert.assertTrue("Label have to be a decoration node", label instanceof DecorationNode);

		// Modify the depth value
		Assert.assertEquals("The initial depth is not correct", "Human", helper.getStereotypeName((DecorationNode) label));
		manager.setDataValue(0, firstPosition + 4, "-1");
		Assert.assertEquals("The depth was not modified", "Body::Human", helper.getStereotypeName((DecorationNode) label));
		manager.setDataValue(0, firstPosition + 4, "full");
		Assert.assertEquals("The depth was not modified", "RootElement::Body::Human", helper.getStereotypeName((DecorationNode) label));

		// Modify the visible value
		Assert.assertEquals("The initial 'Visible' value is not correct", true, label.isVisible());
		manager.setDataValue(1, firstPosition + 4, false);
		Assert.assertEquals("The 'Visible' value was not modified", false, label.isVisible());

		modifyBooleanValues(helper, manager, class1View, humanStereotype, firstPosition);
	}

	/**
	 * Try to modify some values in the animal stereotyped object table value.
	 * 
	 * @param helper
	 *            The Stereotype display helper.
	 * @param manager
	 *            The tree nattable model manager.
	 * @param interface1View
	 *            The view of the interface1.
	 * @param firstPosition
	 *            The first row position.
	 * @throws Exception
	 *             The exception.
	 */
	public static void modifyAnimalStereotypedValues(final StereotypeDisplayUtil helper, final ITreeNattableModelManager manager, final View interface1View, final Element interface1, final int firstPosition) throws Exception {
		final Stereotype animalStereotype = interface1.getAppliedStereotype("RootElement::Body::Animal");
		final View label = helper.getStereotypeLabel(interface1View, animalStereotype);
		Assert.assertTrue("Label have to be a decoration node", label instanceof DecorationNode);

		// Modify the depth value
		Assert.assertEquals("The initial depth is not correct", "Animal", helper.getStereotypeName((DecorationNode) label));
		manager.setDataValue(0, firstPosition + 4, "-1");
		Assert.assertEquals("The depth was not modified", "Body::Animal", helper.getStereotypeName((DecorationNode) label));
		manager.setDataValue(0, firstPosition + 4, "full");
		Assert.assertEquals("The depth was not modified", "RootElement::Body::Animal", helper.getStereotypeName((DecorationNode) label));

		// Modify the visible value
		Assert.assertEquals("The initial 'Visible' value is not correct", true, label.isVisible());
		manager.setDataValue(1, firstPosition + 4, false);
		Assert.assertEquals("The 'Visible' value was not modified", false, label.isVisible());

		modifyBooleanValues(helper, manager, interface1View, animalStereotype, firstPosition);
	}

	/**
	 * Check the boolean values ('In Braces', 'In comment' and 'In Compartment') in the table.
	 * 
	 * @param helper
	 *            The Stereotype display helper.
	 * @param manager
	 *            The tree nattable model manager.
	 * @param view
	 *            The view.
	 * @param stereotype
	 *            The applied stereotype.
	 * @param firstPosition
	 *            The first row position.
	 */
	private static void modifyBooleanValues(final StereotypeDisplayUtil helper, final ITreeNattableModelManager manager, final View view, final Stereotype stereotype, final int firstPosition) {
		// Modify the in braces values
		final Object braceValue = manager.getDataValue(2, firstPosition + 4);
		if(!braceValue.equals(NA)){
			final View brace = helper.getStereotypeBraceCompartment(view, stereotype);
			Assert.assertEquals("The initial 'In Braces' value is not correct", false, brace.isVisible());
			manager.setDataValue(2, firstPosition + 4, true);
			Assert.assertEquals("The 'In Braces' value was not modified", true, brace.isVisible());
			int rowPosition = 6;
			for (Property property : stereotype.getAllAttributes()) {
				if (StereotypeUtil.isValidStereotypeProperty(property)) {
					StringBuilder errorMessage = new StringBuilder("The initial 'In Braces' value is not correct for property '");
					errorMessage.append(property.getName());
					errorMessage.append("'");
					View braceProperty = helper.getStereotypePropertyInBrace(view, stereotype, property);
					Assert.assertEquals(errorMessage.toString(), true, braceProperty.isVisible());
					manager.setDataValue(2, firstPosition + rowPosition, false);
					errorMessage = new StringBuilder("The 'In Braces' value was not modified on property '");
					errorMessage.append(property.getName());
					errorMessage.append("'");
					Assert.assertEquals(errorMessage.toString(), false, braceProperty.isVisible());
					rowPosition++;
				}
			}
		}

		// Modify the in comment values
		final Object commentValue = manager.getDataValue(3, firstPosition + 4);
		if(!commentValue.equals(NA)){
			final View comment = helper.getStereotypeComment(view);
			Assert.assertEquals("The initial 'In Comment' value is not correct", false, comment.isVisible());
			manager.setDataValue(3, firstPosition + 4, true);
			Assert.assertEquals("The 'In Comment' value was not modified", true, comment.isVisible());
			int rowPosition = 6;
			for (Property property : stereotype.getAllAttributes()) {
				if (StereotypeUtil.isValidStereotypeProperty(property)) {
					StringBuilder errorMessage = new StringBuilder("The initial 'In Comment' value is not correct for property '");
					errorMessage.append(property.getName());
					errorMessage.append("'");
					View commentProperty = helper.getStereotypePropertyInComment(view, stereotype, property);
					Assert.assertEquals(errorMessage.toString(), true, commentProperty.isVisible());
					manager.setDataValue(3, firstPosition + rowPosition, false);
					errorMessage = new StringBuilder("The 'In Comment' value was not modified on property '");
					errorMessage.append(property.getName());
					errorMessage.append("'");
					Assert.assertEquals(errorMessage.toString(), false, commentProperty.isVisible());
					rowPosition++;
				}
			}
		}

		// Modify the in compartment values
		final Object compartmentValue = manager.getDataValue(4, firstPosition + 4);
		if(!compartmentValue.equals(NA)){
			final View compartment = helper.getStereotypeCompartment(view, stereotype);
			Assert.assertEquals("The initial 'In Compartment' value is not correct", false, compartment.isVisible());
			manager.setDataValue(4, firstPosition + 4, true);
			Assert.assertEquals("The 'In Compartment' value was not modified", true, compartment.isVisible());
			int rowPosition = 6;
			for (Property property : stereotype.getAllAttributes()) {
				if (StereotypeUtil.isValidStereotypeProperty(property)) {
					StringBuilder errorMessage = new StringBuilder("The initial 'In Comment' value is not correct for property '");
					errorMessage.append(property.getName());
					errorMessage.append("'");
					View compartmentProperty = helper.getStereotypePropertyInCompartment(view, stereotype, property);
					Assert.assertEquals(errorMessage.toString(), true, compartmentProperty.isVisible());
					manager.setDataValue(4, firstPosition + rowPosition, false);
					errorMessage = new StringBuilder("The 'In Compartment' value was not modified on property '");
					errorMessage.append(property.getName());
					errorMessage.append("'");
					Assert.assertEquals(errorMessage.toString(), false, compartmentProperty.isVisible());
					rowPosition++;
				}
			}
		}
	}
}

/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 522721
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.matrix.tests.tests;

import java.util.List;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * This class checks an existing matrix after opening it. The table is already preconfigured with the required local columns header configuration.
 *
 */
@PluginResource("resources/updateTableContentsTests/updateTableContentsAfterExpressionChangeWithExistingLocalHeader.di")
public class UpdateTableContentsAfterExpressionChangeWithLocalHeaderPreconfiguredTests extends AbstractTableTest {

	/**
	 * the number of classes in the tested model
	 */
	private static final int NB_CLASSES = 3;

	/**
	 * the number of interfaces in the tested model
	 */
	private static final int NB_INTERFACE = 3;

	/**
	 * the number of columns in the tested matrix
	 */
	private static final int NB_COLUMNS = NB_CLASSES + NB_INTERFACE + 1; // +1 for applied profile

	/**
	 * the number of rows in the tested matrix
	 */
	private static final int NB_ROWS = NB_CLASSES + NB_INTERFACE + 1 + 2; // +1 for the applied profile +1 for the context package and +1 for the TreeFillingConfiguration


	private static final String STEREOTYPE_QUALIFIED_NAME = "StandardProfile::Metaclass";

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.matrix.tests.tests.AbstractTableTest#getSourcePath()
	 *
	 * @return
	 */
	protected String getSourcePath() {
		return "resources/updateTableContentsTests/"; //$NON-NLS-1$
	}

	/**
	 * This JUnit tests check the opening of the matrix just after opening it
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void testOpeningMatrix() {
		initTest();
		final List<Object> rowElementsList = this.manager.getRowElementsList();
		final List<Object> columnElementsList = this.manager.getColumnElementsList();
		Assert.assertEquals("The number of rows is not the expected one.", NB_ROWS, rowElementsList.size()); //$NON-NLS-1$
		Assert.assertEquals("The number of columns is not the expected one.", NB_COLUMNS, columnElementsList.size()); //$NON-NLS-1$
	}


	/**
	 * This tests apply a filter expression on column and play undo/redo
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void settingAColumnExpressionTest() throws Exception {
		initTest();
		RecordingCommand rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				final IsTypeOfExpression expression = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
				expression.setUmlEClass(UMLPackage.eINSTANCE.getClass_());

				AbstractHeaderAxisConfiguration ahac = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(manager.getTable());
				TreeFillingConfiguration conf = (TreeFillingConfiguration) ahac.getOwnedAxisConfigurations().get(0);
				conf.setFilterRule(expression);
			}
		};
		this.fixture.execute(rc);
		final List<Object> columnElementsList = this.manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Class", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
		}

		this.fixture.undo();
		Assert.assertEquals("The number of columns is not the expected one.", NB_COLUMNS, columnElementsList.size()); //$NON-NLS-1$

		this.fixture.redo();
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Class", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
		}

	}

	/**
	 * This tests apply a filter expression on column, then reedit the filter rule and play undo/redo
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void settingAColumnExpressionThenEditingItTest() throws Exception {
		initTest();
		RecordingCommand rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				final IsTypeOfExpression expression = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
				expression.setUmlEClass(UMLPackage.eINSTANCE.getClass_());

				AbstractHeaderAxisConfiguration ahac = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(manager.getTable());
				TreeFillingConfiguration conf = (TreeFillingConfiguration) ahac.getOwnedAxisConfigurations().get(0);
				conf.setFilterRule(expression);
			}
		};

		// we filter on UML::Class
		this.fixture.execute(rc);
		final List<Object> columnElementsList = this.manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Class", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
		}

		// we filter on UML::Interface
		rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				AbstractHeaderAxisConfiguration ahac = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(manager.getTable());
				TreeFillingConfiguration conf = (TreeFillingConfiguration) ahac.getOwnedAxisConfigurations().get(0);
				((IsTypeOfExpression) conf.getFilterRule()).setUmlEClass(UMLPackage.eINSTANCE.getInterface());
			}
		};
		this.fixture.execute(rc);
		Assert.assertEquals("The number of columns is not the expected one.", NB_INTERFACE, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Interface", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Interface); //$NON-NLS-1$
		}


		this.fixture.undo();// we filter on UML::Class
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Class", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
		}


		this.fixture.redo();// we filter on UML::Interface
		Assert.assertEquals("The number of columns is not the expected one.", NB_INTERFACE, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Interface", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Interface); //$NON-NLS-1$
		}

	}


	/**
	 * This tests apply a filter expression on column, then replace the filter rule rule and play undo/redo
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void settingAColumnExpressionThenReplacingItTest() throws Exception {
		initTest();
		RecordingCommand rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				final IsTypeOfExpression expression = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
				expression.setUmlEClass(UMLPackage.eINSTANCE.getClass_());

				AbstractHeaderAxisConfiguration ahac = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(manager.getTable());
				TreeFillingConfiguration conf = (TreeFillingConfiguration) ahac.getOwnedAxisConfigurations().get(0);
				conf.setFilterRule(expression);
			}
		};

		// we filter on UML::Class
		this.fixture.execute(rc);
		final List<Object> columnElementsList = this.manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Class", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
		}

		// all columns must be removed
		rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				AbstractHeaderAxisConfiguration ahac = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(manager.getTable());
				TreeFillingConfiguration conf = (TreeFillingConfiguration) ahac.getOwnedAxisConfigurations().get(0);
				final LiteralFalseExpression expression = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
				conf.setFilterRule(expression);
			}
		};
		this.fixture.execute(rc);
		Assert.assertEquals("The number of columns is not the expected one.", 0, columnElementsList.size()); //$NON-NLS-1$


		this.fixture.undo();// we filter on UML::Class
		Assert.assertEquals("The number of columns is not the expected one.", NB_CLASSES, columnElementsList.size()); //$NON-NLS-1$
		for (Object current : columnElementsList) {
			Assert.assertTrue("One of the displayed column is not a UML Class", AxisUtils.getRepresentedElement(current) instanceof org.eclipse.uml2.uml.Class); //$NON-NLS-1$
		}


		this.fixture.redo();// // all columns must be removed
		Assert.assertEquals("The number of columns is not the expected one.", 0, columnElementsList.size()); //$NON-NLS-1$
	}


	/**
	 * This test applies an applied stereotype expression on column, then applies stereotypes on semantic element and play undo/redo.
	 * 
	 * @throws Exception
	 */
	@Test
	@ActiveTable("MatrixOfRelationships")
	public void settingAColumnExpressionRequiringToListenAllChangesTest() throws Exception {
		initTest();
		RecordingCommand rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				final HasAppliedStereotypesExpression expression = UMLExpressionsFactory.eINSTANCE.createHasAppliedStereotypesExpression();

				AbstractHeaderAxisConfiguration ahac = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(manager.getTable());
				TreeFillingConfiguration conf = (TreeFillingConfiguration) ahac.getOwnedAxisConfigurations().get(0);
				conf.setFilterRule(expression);
			}
		};

		// Filter on stereotype elements
		this.fixture.execute(rc);
		final List<Object> columnElementsList = this.manager.getColumnElementsList();

		// We have no columns as there is no stereotype element in the column sources
		Assert.assertEquals("The number of columns is not the expected one.", 0, columnElementsList.size()); //$NON-NLS-1$

		// Apply stereotype for Class1
		NamedElement class1 = this.fixture.getModel().getMember("Class1");
		Stereotype applicableStereotype = class1.getApplicableStereotype(STEREOTYPE_QUALIFIED_NAME);
		Assert.assertNotNull("The stereotype required for the test is not found", applicableStereotype);
		rc = new RecordingCommand(this.fixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				class1.applyStereotype(applicableStereotype);
			}
		};
		this.fixture.execute(rc);
		Assert.assertNotNull("The stereotype required for the JUnit has not been applied", class1.getAppliedStereotype(STEREOTYPE_QUALIFIED_NAME));

		// Now we have only one column of stereotype Class1 element
		Assert.assertEquals("The number of columns is not the expected one.", 1, columnElementsList.size()); //$NON-NLS-1$

		// Check UNDO: all columns must be removed
		this.fixture.undo();
		Assert.assertEquals("The number of columns is not the expected one.", 0, columnElementsList.size()); //$NON-NLS-1$

		// Check REDO: only one column must be displayed
		this.fixture.redo();
		Assert.assertEquals("The number of columns is not the expected one.", 1, columnElementsList.size()); //$NON-NLS-1$
	}
}

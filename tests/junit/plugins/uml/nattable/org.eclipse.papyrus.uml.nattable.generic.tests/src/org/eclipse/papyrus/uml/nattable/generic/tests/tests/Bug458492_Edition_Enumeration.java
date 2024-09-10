/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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

package org.eclipse.papyrus.uml.nattable.generic.tests.tests;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.junit.utils.EditorUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.ProjectUtils;
import org.eclipse.papyrus.uml.nattable.dataprovider.UMLStereotypeSingleEnumerationComboBoxDataProvider;
import org.eclipse.papyrus.uml.nattable.generic.tests.Activator;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * This test checks properties typed with Enumeration:
 * <ul>
 * <li>property owned by a stereotype and typed with an UML Enumeration declared in the same profile</li>
 * <li>property owned by a stereotype and typed with an UML Enumeration declared in an external library</li>
 * <li>property owned by a stereotype and typed with an UML Enumerationn when it is a static profile (SysML FlowPort::direction)</li>
 * </ul>
 *
 * We check the available literals AND the set action for each of them
 */
public class Bug458492_Edition_Enumeration extends AbstractGenericTableTest {


	private static final String CLASS_NAME = "Class1"; //$NON-NLS-1$

	private static final String TASK_COLOR_PROPERTY_QN = "profile::Task::color"; //$NON-NLS-1$

	private static final String TASK_COLOR_PROPERTY_NAME = "color"; //$NON-NLS-1$

	private static final String TASK_PRIORITY_PROPERTY_QN = "profile::Task::p"; //$NON-NLS-1$

	private static final String TASK_STEREOTYPE_QN = "profile::Task"; //$NON-NLS-1$

	private static final String TASK_PRIORIY_PROPERTY_NAME = "p"; //$NON-NLS-1$

	public static final String PASTE_FOLDER_PATH = "/resources/UMLEnumerationEdition/"; //$NON-NLS-1$

	private static final String PORT_NAME = "flowport1"; //$NON-NLS-1$

	//TODO #487932: Either use the standard profile or move the test to SysML 1.4 
	private static final String FLOW_PORT_STEREOTYPE_QN = "SysMLCopy::PortAndFlows::FlowPort"; //$NON-NLS-1$

	private static final String DIRECTION_QN = "SysMLCopy::PortAndFlows::FlowPort::direction"; //$NON-NLS-1$

	private static final String DIRECTION_PROPERTY_NAME = "direction"; //$NON-NLS-1$

	@Before
	public void initModel() throws Exception {
		initModel("table", getClass().getSimpleName(), getBundle()); //$NON-NLS-1$
	}

	/**
	 * Inits this.editor
	 * Fails or throws an exception if an error occurs
	 *
	 * @param bundle
	 *            the source bundle where the model is store
	 * @param projectName
	 *            the project that will created at runtime to execute test
	 * @param modelName
	 *            the model that will be copied and test executed on.
	 */
	protected void initModel(String projectName, String modelName, Bundle bundle) throws Exception {
		project = ProjectUtils.createProject(projectName);
		final IFile project1 = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), "library"); //$NON-NLS-1$
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(project1);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		final IFile project2 = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), "model.profile"); //$NON-NLS-1$
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(project2);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		this.diModelFile = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), modelName);
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(diModelFile);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		Assert.assertNotNull(editor);
	}


	@Test
	public void availableColorTest() throws Exception {

		StringBuilder builder = new StringBuilder(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX);
		builder.append(TASK_COLOR_PROPERTY_QN); //$NON-NLS-1$
		String wantedProperty = builder.toString();
		loadGenericTable();
		List<IAxis> iAxis = getTable().getCurrentColumnAxisProvider().getAxis();
		IAxis colorAxis = null;
		Object representedElement = null;

		for (IAxis current : iAxis) {
			representedElement = AxisUtils.getRepresentedElement(current);
			if (wantedProperty.equals(representedElement)) {
				colorAxis = current;
				break;
			}
		}
		Assert.assertNotNull(colorAxis);
		Assert.assertNotNull(representedElement);

		UMLStereotypeSingleEnumerationComboBoxDataProvider provider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(colorAxis, getTableManager());
		List<?> values = provider.getValues(iAxis.indexOf(colorAxis), 0);
		boolean findBlack = false;
		boolean findRed = false;
		boolean findBlue = false;
		for (Object current : values) {
			Assert.assertTrue(current instanceof EEnumLiteral);
			EEnumLiteral lit = (EEnumLiteral) current;
			if (lit.getLiteral().equals("black")) { //$NON-NLS-1$
				findBlack = true;
			}
			if (lit.getLiteral().equals("red")) { //$NON-NLS-1$
				findRed = true;
			}
			if (lit.getLiteral().equals("blue")) { //$NON-NLS-1$
				findBlue = true;
			}
		}


		Assert.assertTrue(findBlack);
		Assert.assertTrue(findRed);
		Assert.assertTrue(findBlue);
	}

	@Test
	public void availablePriorityTest() throws Exception {
		StringBuilder builder = new StringBuilder(UMLTableUtils.PROPERTY_OF_STEREOTYPE_PREFIX);
		builder.append(TASK_PRIORITY_PROPERTY_QN); //$NON-NLS-1$
		String wantedProperty = builder.toString();
		loadGenericTable(); //$NON-NLS-1$ //$NON-NLS-2$
		List<IAxis> iAxis = getTable().getCurrentColumnAxisProvider().getAxis();
		IAxis priorityAxis = null;
		Object representedElement = null;

		for (IAxis current : iAxis) {
			representedElement = AxisUtils.getRepresentedElement(current);
			if (wantedProperty.equals(representedElement)) {
				priorityAxis = current;
				break;
			}
		}
		Assert.assertNotNull(priorityAxis);
		Assert.assertNotNull(representedElement);

		UMLStereotypeSingleEnumerationComboBoxDataProvider provider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(priorityAxis, getTableManager());
		List<?> values = provider.getValues(iAxis.indexOf(priorityAxis), 0);
		boolean findHight = false;
		boolean findMedium = false;
		boolean findLow = false;
		for (Object current : values) {
			Assert.assertTrue(current instanceof EEnumLiteral);
			EEnumLiteral lit = (EEnumLiteral) current;
			if (lit.getLiteral().equals("hight")) { //$NON-NLS-1$
				findHight = true;
			}
			if (lit.getLiteral().equals("medium")) { //$NON-NLS-1$
				findMedium = true;
			}
			if (lit.getLiteral().equals("low")) { //$NON-NLS-1$
				findLow = true;
			}
		}


		Assert.assertTrue(findHight);
		Assert.assertTrue(findMedium);
		Assert.assertTrue(findLow);
	}




	@Test
	public void testSetPriorityOnStereotypedClass() throws Exception {
		loadGenericTable(); //$NON-NLS-1$ //$NON-NLS-2$
		List<Object> elements = getTableManager().getRowElementsList();
		Assert.assertEquals(2, elements.size());
		// we look for the class to edit
		Class clazz = null;
		Object classObject = null;
		for (Object object : elements) {
			Object tmp = AxisUtils.getRepresentedElement(object);
			if (tmp instanceof Class && ((Class) tmp).getName().equals(CLASS_NAME)) {
				clazz = (Class) tmp;
				classObject = object;
			}
		}

		Stereotype appliedStereotype = clazz.getAppliedStereotype(TASK_STEREOTYPE_QN);
		Assert.assertNotNull(appliedStereotype);
		// we look for the column to edit
		Object priorityColumn = null;
		List<Object> columns = getTableManager().getColumnElementsList();
		List<Object> rows = getTableManager().getRowElementsList();
		for (Object current : columns) {
			Object representedElement = AxisUtils.getRepresentedElement(current);
			if (representedElement instanceof String && ((String) representedElement).contains(TASK_PRIORITY_PROPERTY_QN)) {
				priorityColumn = current;
				break;
			}
		}

		Assert.assertNotNull(priorityColumn);
		Assert.assertNotNull(clazz);
		Stereotype ste = clazz.getApplicableStereotype(TASK_STEREOTYPE_QN);
		Assert.assertNotNull(ste);


		EObject stereotypeDef = ste.getProfile().getDefinition(ste);
		Assert.assertNotNull(stereotypeDef);
		EStructuralFeature priority = ((EClass) stereotypeDef).getEStructuralFeature(TASK_PRIORIY_PROPERTY_NAME);
		Assert.assertNotNull(priority);

		UMLStereotypeSingleEnumerationComboBoxDataProvider contentProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(priorityColumn, getTableManager());
		Object values = contentProvider.getValues(columns.indexOf(priorityColumn), rows.indexOf(classObject));
		Assert.assertTrue(values instanceof Collection<?>);
		Collection<?> coll = (Collection<?>) values;
		Assert.assertEquals(3, coll.size());
		boolean findHight = false;
		boolean findMedium = false;
		boolean findLow = false;
		for (Object current : coll) {
			Assert.assertTrue(current instanceof EEnumLiteral);
			EEnumLiteral lit = (EEnumLiteral) current;
			if (lit.getLiteral().equals("hight")) { //$NON-NLS-1$
				findHight = true;
			}
			if (lit.getLiteral().equals("medium")) { //$NON-NLS-1$
				findMedium = true;
			}
			if (lit.getLiteral().equals("low")) { //$NON-NLS-1$
				findLow = true;
			}
		}
		// we check that values are wanted values
		Assert.assertTrue(findHight);
		Assert.assertTrue(findMedium);
		Assert.assertTrue(findLow);
		Iterator<?> iter = coll.iterator();

		// we check the set Value command
		while (iter.hasNext()) {
			Object newValue = iter.next();
			Assert.assertTrue(newValue instanceof EEnumLiteral);
			CellManagerFactory.INSTANCE.setCellValue(getTransactionalEditingDomain(), priorityColumn, clazz, newValue, getTableManager());
			Stereotype stee = clazz.getAppliedStereotype("profile::Task"); //$NON-NLS-1$
			Assert.assertNotNull(stee);
			Object value = clazz.getValue(stee, TASK_PRIORIY_PROPERTY_NAME);
			Assert.assertTrue(value instanceof EnumerationLiteral);
			Assert.assertEquals(((EEnumLiteral) newValue).getLiteral(), ((EnumerationLiteral) value).getName());
		}


	}


	@Test
	public void testSetColorOnStereotypedClass() throws Exception {
		loadGenericTable(); //$NON-NLS-1$ //$NON-NLS-2$
		List<Object> elements = getTableManager().getRowElementsList();
		// we look for the class to edit
		Class clazz = null;
		Object classObject = null;
		for (Object object : elements) {
			Object representedObject = AxisUtils.getRepresentedElement(object);
			if (representedObject instanceof Class && ((Class) representedObject).getName().equals(CLASS_NAME)) {
				clazz = (Class) representedObject;
				classObject = object;
			}
		}

		Stereotype appliedStereotype = clazz.getAppliedStereotype(TASK_STEREOTYPE_QN);
		Assert.assertNotNull(appliedStereotype);
		// we look for the column to edit
		Object priorityColumn = null;
		List<Object> columns = getTableManager().getColumnElementsList();
		List<Object> rows = getTableManager().getRowElementsList();
		for (Object current : columns) {
			Object representedElement = AxisUtils.getRepresentedElement(current);
			if (representedElement instanceof String && ((String) representedElement).contains(TASK_COLOR_PROPERTY_QN)) {
				priorityColumn = current;
				break;
			}
		}

		Assert.assertNotNull(priorityColumn);
		Assert.assertNotNull(clazz);
		Stereotype ste = clazz.getApplicableStereotype(TASK_STEREOTYPE_QN);
		Assert.assertNotNull(ste);


		EObject stereotypeDef = ste.getProfile().getDefinition(ste);
		Assert.assertNotNull(stereotypeDef);
		EStructuralFeature priority = ((EClass) stereotypeDef).getEStructuralFeature(TASK_COLOR_PROPERTY_NAME);
		Assert.assertNotNull(priority);

		UMLStereotypeSingleEnumerationComboBoxDataProvider contentProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(priorityColumn, getTableManager());
		Object values = contentProvider.getValues(columns.indexOf(priorityColumn), rows.indexOf(classObject));
		Assert.assertTrue(values instanceof Collection<?>);
		Collection<?> coll = (Collection<?>) values;
		Assert.assertEquals(3, coll.size());
		boolean findBlack = false;
		boolean findRed = false;
		boolean findBlue = false;
		for (Object current : coll) {
			Assert.assertTrue(current instanceof EEnumLiteral);
			EEnumLiteral lit = (EEnumLiteral) current;
			if (lit.getLiteral().equals("black")) { //$NON-NLS-1$
				findBlack = true;
			}
			if (lit.getLiteral().equals("red")) { //$NON-NLS-1$
				findRed = true;
			}
			if (lit.getLiteral().equals("blue")) { //$NON-NLS-1$
				findBlue = true;
			}
		}


		Assert.assertTrue(findBlack);
		Assert.assertTrue(findRed);
		Assert.assertTrue(findBlue);
		Iterator<?> iter = coll.iterator();

		// we check the set Value command
		while (iter.hasNext()) {
			Object newValue = iter.next();
			Assert.assertTrue(newValue instanceof EEnumLiteral);
			CellManagerFactory.INSTANCE.setCellValue(getTransactionalEditingDomain(), priorityColumn, clazz, newValue, getTableManager());
			Stereotype stee = clazz.getAppliedStereotype("profile::Task"); //$NON-NLS-1$
			Assert.assertNotNull(stee);
			Object value = clazz.getValue(stee, TASK_COLOR_PROPERTY_NAME);
			Assert.assertNotNull("The stereotype property is null", value);
			Assert.assertTrue(value instanceof EnumerationLiteral);
			Assert.assertEquals(((EEnumLiteral) newValue).getLiteral(), ((EnumerationLiteral) value).getName());
		}
	}

	@Test
	public void testSetFlowPortDirection() throws Exception {
		loadGenericTable(); //$NON-NLS-1$ //$NON-NLS-2$
		List<Object> elements = getTableManager().getRowElementsList();
		// we look for the port to edit
		Port port = null;
		Object portObject = null;
		for (Object object : elements) {
			Object representedObject = AxisUtils.getRepresentedElement(object);
			if (representedObject instanceof Port && ((Port) representedObject).getName().equals(PORT_NAME)) {
				port = (Port) representedObject;
				portObject = object;
			}
		}

		Stereotype appliedStereotype = port.getAppliedStereotype(FLOW_PORT_STEREOTYPE_QN);
		Assert.assertNotNull(appliedStereotype);
		// we look for the column to edit
		Object directionColumn = null;
		List<Object> columns = getTableManager().getColumnElementsList();
		List<Object> rows = getTableManager().getRowElementsList();

		for (Object current : columns) {
			Object representedElement = AxisUtils.getRepresentedElement(current);
			if (representedElement instanceof String && ((String) representedElement).contains(DIRECTION_QN)) {
				directionColumn = current;
				break;
			}
		}

		Assert.assertNotNull(directionColumn);
		Assert.assertNotNull(port);
		Stereotype ste = port.getApplicableStereotype(FLOW_PORT_STEREOTYPE_QN);
		Assert.assertNotNull(ste);


		EObject stereotypeDef = ste.getProfile().getDefinition(ste);
		Assert.assertNotNull(stereotypeDef);
		EStructuralFeature priority = ((EClass) stereotypeDef).getEStructuralFeature(DIRECTION_PROPERTY_NAME);
		Assert.assertNotNull(priority);

		UMLStereotypeSingleEnumerationComboBoxDataProvider contentProvider = new UMLStereotypeSingleEnumerationComboBoxDataProvider(directionColumn, getTableManager());
		Object values = contentProvider.getValues(columns.indexOf(directionColumn), rows.indexOf(portObject));
		Assert.assertTrue(values instanceof Collection<?>);
		Collection<?> coll = (Collection<?>) values;
		Assert.assertEquals(3, coll.size());
		boolean findIn = false;
		boolean findout = false;
		boolean findInOut = false;
		for (Object current : coll) {
			Assert.assertTrue(current instanceof Enumerator);
			Enumerator lit = (Enumerator) current;
			if (lit.getLiteral().equals("in")) { //$NON-NLS-1$
				findIn = true;
			}
			if (lit.getLiteral().equals("out")) { //$NON-NLS-1$
				findout = true;
			}
			if (lit.getLiteral().equals("inout")) { //$NON-NLS-1$
				findInOut = true;
			}
		}


		Assert.assertTrue(findIn);
		Assert.assertTrue(findout);
		Assert.assertTrue(findInOut);
		Iterator<?> iter = coll.iterator();

		// we check the set Value command
		while (iter.hasNext()) {
			Object newValue = iter.next();
			Assert.assertTrue(newValue instanceof Enumerator);
			CellManagerFactory.INSTANCE.setCellValue(getTransactionalEditingDomain(), directionColumn, port, newValue, getTableManager());
			Stereotype stee = port.getAppliedStereotype(FLOW_PORT_STEREOTYPE_QN);
			Assert.assertNotNull(stee);
			Object value = port.getValue(stee, DIRECTION_PROPERTY_NAME);
			Assert.assertTrue(((EnumerationLiteral)value).getName().equals(newValue.toString()));
		}
	}

	@Override
	protected String getSourcePath() {
		return PASTE_FOLDER_PATH;
	}

	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}
}
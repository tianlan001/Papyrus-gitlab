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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.Problem;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableproblem.StringResolutionProblem;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This test verifies than the problem created by a paste is destroyed after a set value
 *
 */
@PluginResource("resources/bugs/bug468914/EditInvalidPastedText_V1_V3_V1.di")
public class EditInvalidPastedText_V1_V3_V1 {

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * the nattable editor
	 */
	private NatTableEditor editor;

	/**
	 * the tree table manager
	 */
	private ITreeNattableModelManager manager;

	/** the UML elements required by the tests */
	private Package root;

	private Class class1;

	private Class class2;

	private Property property1;

	private Property property2;


	private static final String CLASS_1 = "Class1"; //$NON-NLS-1$
	private static final String CLASS_2 = "Class2"; //$NON-NLS-1$

	private static final String PROPERTY_1 = "Property1"; //$NON-NLS-1$
	private static final String PROPERTY_2 = "Property2"; //$NON-NLS-1$

	private IAxis prop1Axis;

	private IAxis prop2Axis;

	private IAxis featureTypeAxis;

	/**
	 * load the table editor
	 */
	@Before
	public void init() {
		// TODO : a java annotation similar to diagram could be used to activate the table before the JUnit test
		IPageManager pageManager = fixture.getPageManager();
		List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		this.editor = (NatTableEditor) part;
		INattableModelManager m = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(m instanceof ITreeNattableModelManager);
		this.manager = (ITreeNattableModelManager) m;


		// we init the field of the class
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();


		EObject context = this.manager.getTable().getContext();
		Assert.assertTrue(context instanceof Package);
		this.root = (Package) context;
		this.class1 = (Class) this.root.getMember(CLASS_1);
		this.class2 = (Class) this.root.getMember(CLASS_2);
		Assert.assertNotNull(class1);
		Assert.assertNotNull(class2);


		this.property1 = (Property) this.class1.getMember(PROPERTY_1);
		this.property2 = (Property) this.class1.getMember(PROPERTY_2);

		Assert.assertNotNull(property1);
		Assert.assertNotNull(property2);

		Assert.assertNull(property1.getType());
		Assert.assertNull(property2.getType());

		List<Object> rows = manager.getRowElementsList();
		List<Object> columns = manager.getColumnElementsList();

		for (Object current : columns) {
			if (current instanceof EStructuralFeatureAxis) {
				if (((EStructuralFeatureAxis) current).getElement() == UMLPackage.eINSTANCE.getTypedElement_Type()) {
					featureTypeAxis = (EStructuralFeatureAxis) current;
				}
			}
			if (featureTypeAxis != null) {
				break;
			}
		}

		Assert.assertNotNull(featureTypeAxis);

		for (Object current : rows) {
			Object representedElement = AxisUtils.getRepresentedElement(current);
			if (AxisUtils.getRepresentedElement(current) instanceof Property) {
				Property property = (Property) representedElement;
				if (property1 == property) {
					prop1Axis = (IAxis) current;
				}
				if (property2 == property) {
					prop2Axis = (IAxis) current;
				}
			}
		}

		Assert.assertNotNull(prop1Axis);
		Assert.assertNotNull(prop2Axis);

	}

	/**
	 * this test check than the StringResolutionProblem and its associated Cell are destroyed by a cell edition + Undo/Redo
	 */
	@Test
	public void test() {
		TransactionalEditingDomain contextEditingDomain = TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable());

		// we check the returned value for property1/type
		Object cellValue1 = CellManagerFactory.INSTANCE.getCrossValue(featureTypeAxis, prop1Axis, manager);
		Assert.assertTrue(cellValue1 instanceof List<?>);
		List<?> pb1 = (List<?>) cellValue1;

		// we check the cell value saved in the table model
		Cell cell1 = manager.getCell(featureTypeAxis, prop1Axis);
		Assert.assertNotNull(cell1);
		Assert.assertEquals(pb1, cell1.getProblems());

		Problem problem = (Problem) pb1.get(0);
		Assert.assertTrue(problem instanceof StringResolutionProblem);
		StringResolutionProblem stringProblem1 = (StringResolutionProblem) problem;
		Assert.assertEquals(CLASS_2, stringProblem1.getValueAsString());


		// we set the value
		CellManagerFactory.INSTANCE.setCellValue(contextEditingDomain, featureTypeAxis, prop1Axis, class2, manager);
		fixture.flushDisplayEvents();

		// we check the set value
		Assert.assertEquals(class2, property1.getType());

		// we check the value returned by the cell manager
		cellValue1 = CellManagerFactory.INSTANCE.getCrossValue(featureTypeAxis, prop1Axis, manager);
		Assert.assertEquals(class2, cellValue1);

		// we check the cell value saved in the table model
		cell1 = manager.getCell(featureTypeAxis, prop1Axis);
		Assert.assertNull(cell1);

		// we check the undo
		contextEditingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// we check the set value
		Assert.assertNull(property1.getType());

		cellValue1 = CellManagerFactory.INSTANCE.getCrossValue(featureTypeAxis, prop1Axis, manager);
		Assert.assertTrue(cellValue1 instanceof List<?>);
		pb1 = (List<?>) cellValue1;

		// we check the cell value saved in the table model
		cell1 = manager.getCell(featureTypeAxis, prop1Axis);
		Assert.assertNotNull(cell1);
		Assert.assertEquals(pb1, cell1.getProblems());

		problem = (Problem) pb1.get(0);
		Assert.assertTrue(problem instanceof StringResolutionProblem);
		stringProblem1 = (StringResolutionProblem) problem;
		Assert.assertEquals(CLASS_2, stringProblem1.getValueAsString());

		// we check the redo
		contextEditingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();

		// we check the set value
		Assert.assertEquals(class2, property1.getType());

		// we check the value returned by the cell manager
		cellValue1 = CellManagerFactory.INSTANCE.getCrossValue(featureTypeAxis, prop1Axis, manager);
		Assert.assertEquals(class2, cellValue1);

		// we check the cell value saved in the table model
		cell1 = manager.getCell(featureTypeAxis, prop1Axis);
		Assert.assertNull(cell1);
	}


}

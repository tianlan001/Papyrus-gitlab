/*****************************************************************************
 * Copyright (c) 2015, 2020 CEA LIST and others.
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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 486733
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.sort;

import org.eclipse.emf.common.util.URI;
import org.eclipse.nebula.widgets.nattable.selection.command.ClearAllSelectionsCommand;
import org.eclipse.nebula.widgets.nattable.sort.command.SortColumnCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.utils.rules.ActiveTable;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractTableTest;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * Abstract test class to reopen filtered table
 */
public abstract class AbstractSortTable extends AbstractTableTest {

	/**
	 * the resource path
	 */
	private static final String RESOURCES_PATH = "resources/sort/";//$NON-NLS-1$

	public static final String NAME_ASC = "NAME_ASC";//$NON-NLS-1$

	public static final String NAME_DESC = "NAME_DESC";//$NON-NLS-1$

	public static final String TYPE_ASC = "TYPE_ASC"; //$NON-NLS-1$

	public static final String TYPE_DESC = "TYPE_DESC";//$NON-NLS-1$

	public static final String NAME_ASC_TYPE_ASC = "NAME_ASC_TYPE_ASC";//$NON-NLS-1$
	public static final String NAME_ASC_TYPE_DESC = "NAME_ASC_TYPE_DESC";//$NON-NLS-1$
	public static final String NAME_DESC_TYPE_ASC = "NAME_DESC_TYPE_ASC";//$NON-NLS-1$
	public static final String NAME_DESC_TYPE_DESC = "NAME_DESC_TYPE_DESC";//$NON-NLS-1$

	public static final String TYPE_ASC_NAME_ASC = "TYPE_ASC_NAME_ASC";//$NON-NLS-1$
	public static final String TYPE_DESC_NAME_ASC = "TYPE_DESC_NAME_ASC";//$NON-NLS-1$
	public static final String TYPE_ASC_NAME_DESC = "TYPE_ASC_NAME_DESC";//$NON-NLS-1$
	public static final String TYPE_DESC_NAME_DESC = "TYPE_DESC_NAME_DESC";//$NON-NLS-1$


	/**
	 * load the table editor
	 */
	@Before
	public void init() {
		// these tests works only when the sorted columns are visible (without scrollbar)!
		if (Display.getDefault() != null) {
			Shell shell = Display.getDefault().getActiveShell();
			if (shell != null) {
				shell.setMaximized(true);
			}
		}
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_NAME_ASC() {
		startTest();

		fixture.flushDisplayEvents();
		manager.selectAll();
		((NattableModelManager) manager).copyToClipboard();
		fixture.flushDisplayEvents();

		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), "sort");
			if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_ASC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);

		manager.selectAll();
		((NattableModelManager) manager).copyToClipboard();
		fixture.flushDisplayEvents();

		endTest(getResultFile_Name_ASC_SORT());
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_TYPE_ASC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));
		boolean foundName = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), "sort");
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_ASC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the type column to check the test", foundName);

		endTest(getResultFile_Type_ASC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Type_ASC_SORT() {
		return createSortFileNameResult(TYPE_ASC);
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_TYPE_DESC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));

		fixture.flushDisplayEvents();
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_DESC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the type column to check the test", foundType);

		endTest(getResultFile_Type_DESC_SORT());
	}

	// ---sorting the 2 columns, name first
	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_NAME_ASC_TYPE_ASC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_ASC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_ASC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);



		endTest(getResultFile_Name_ASC_Type_ASC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Name_ASC_Type_ASC_SORT() {
		return createSortFileNameResult(NAME_ASC_TYPE_ASC);
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_NAME_ASC_TYPE_DESC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_DESC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_ASC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);



		endTest(getResultFile_Name_ASC_Type_DESC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Name_ASC_Type_DESC_SORT() {
		return createSortFileNameResult(NAME_ASC_TYPE_DESC);
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_NAME_DESC_TYPE_ASC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_ASC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_DESC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);


		endTest(getResultFile_Name_DESC_Type_ASC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Name_DESC_Type_ASC_SORT() {
		return createSortFileNameResult(NAME_DESC_TYPE_ASC);
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_NAME_DESC_TYPE_DESC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_DESC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_DESC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);


		endTest(getResultFile_Name_DESC_Type_DESC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Name_DESC_Type_DESC_SORT() {
		return createSortFileNameResult(NAME_DESC_TYPE_DESC);
	}

	// ---sorting the 2 columns, type first
	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_TYPE_ASC_NAME_ASC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_ASC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_ASC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);

		endTest(getResultFile_Type_ASC_Name_ASC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Type_ASC_Name_ASC_SORT() {
		return createSortFileNameResult(TYPE_ASC_NAME_ASC);
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_TYPE_ASC_NAME_DESC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_ASC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_DESC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);


		endTest(getResultFile_Type_ASC_Name_DESC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Type_ASC_Name_DESC_SORT() {
		return createSortFileNameResult(TYPE_ASC_NAME_DESC);
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_TYPE_DESC_NAME_ASC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_DESC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_ASC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);


		endTest(getResultFile_Type_DESC_Name_ASC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Type_DESC_Name_ASC_SORT() {
		return createSortFileNameResult(TYPE_DESC_NAME_ASC);
	}


	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_TYPE_DESC_NAME_DESC() {
		startTest();
		// we do the sort
		natTable.doCommand(new ClearAllSelectionsCommand());
		fixture.flushDisplayEvents();

		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getTypeColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), true)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		boolean foundType = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getTypedElement_Type().equals(AxisUtils.getRepresentedElement(current))) {
				foundType = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_DESC", style.getStringValue());
			} else if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("1_DESC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);
		Assert.assertTrue("We don't found the type column to check the test", foundType);


		endTest(getResultFile_Type_DESC_Name_DESC_SORT());
	}

	/**
	 * @return
	 */
	private String getResultFile_Type_DESC_Name_DESC_SORT() {
		return createSortFileNameResult(TYPE_DESC_NAME_DESC);
	}

	/**
	 * @return
	 */
	private String getResultFile_Type_DESC_SORT() {
		return createSortFileNameResult(TYPE_DESC);
	}

	/**
	 * this method initialize some field for the test + expand all the table + check the initial state of the table
	 */
	@Override
	protected void startTest() {
		super.startTest();
		manager.selectAll();
		manager.copyToClipboard();
		String clipboard = getClipboardContent();
		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertNotNull(clipboard);
		String str = getWantedString(getInitialStateFileName());
		// we check than the contents of the clipboard (so the displayed table) is the same than the wanted result
		Assert.assertEquals(str, clipboard);
	}

	/**
	 *
	 * @param resultFileName
	 *            the name of the result file to use to compare the displayed state and the wanted state
	 */
	protected void endTest(String resultFileName) {
		compareCurrentDisplayToWantedDisplay(resultFileName);
	}

	/**
	 *
	 * @return
	 *         the name column index
	 */
	protected int getNameColumnIndex() {
		return 2;
	}

	/**
	 *
	 * @return
	 *         the type column index
	 */
	protected int getTypeColumnIndex() {
		return 3;
	}

	@Test
	@ActiveTable("ClassTreeTable")
	public void test_sort_NAME_DESC() {
		startTest();
		// we do the sort
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));
		Assert.assertTrue(natTable.doCommand(new SortColumnCommand(natTable, getNameColumnIndex(), false)));

		fixture.flushDisplayEvents();
		boolean foundName = false;
		for (final Object current : manager.getColumnElementsList()) {
			Assert.assertTrue(current instanceof IAxis);
			IAxis axis = (IAxis) current;
			final StringValueStyle style = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(AxisUtils.getRepresentedElement(current))) {
				foundName = true;
				Assert.assertNotNull(style);
				Assert.assertEquals("0_DESC", style.getStringValue());
			} else {
				Assert.assertNull(style);
			}
		}
		Assert.assertTrue("We don't found the name column to check the test", foundName);

		endTest(getResultFile_Name_DESC_SORT());
	}

	/**
	 *
	 * @return
	 *         the name of the file which contains the wanted contents of the clipboard after the copy to clipboard
	 */
	private String getInitialStateFileName() {
		URI uri = manager.getTable().eResource().getURI();
		uri = uri.trimFileExtension();
		StringBuffer buffer = new StringBuffer(uri.lastSegment());
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}

	/**
	 * @return
	 */
	@Override
	protected String getSourcePath() {
		return RESOURCES_PATH;
	}


	/**
	 * @see org.eclipse.papyrus.uml.nattable.clazz.config.tests.sort.AbstractSortTable#getResultFile_Name_ASC_SORT()
	 *
	 * @return
	 */

	protected String getResultFile_Name_ASC_SORT() {
		return createSortFileNameResult(NAME_ASC);
	}

	protected String getResultFile_Name_DESC_SORT() {
		return createSortFileNameResult(NAME_DESC);
	};

	protected String createSortFileNameResult(String fileNameSuffix) {
		URI uri = fixture.getActiveTableManager().getTable().eResource().getURI().trimFileExtension();
		String lastSegment = uri.lastSegment();
		StringBuffer buffer = new StringBuffer(lastSegment);
		buffer.append(FileUtils.UNDERSCORE);
		buffer.append(fileNameSuffix);
		buffer.append(FileUtils.DOT_STRING);
		buffer.append(FileUtils.TEXT_EXTENSION);
		return buffer.toString();
	}


}

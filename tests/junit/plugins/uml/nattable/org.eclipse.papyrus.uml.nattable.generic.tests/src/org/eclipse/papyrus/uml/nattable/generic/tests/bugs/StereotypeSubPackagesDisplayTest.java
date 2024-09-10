/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.nattable.generic.tests.bugs;

import java.util.List;

import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.FeatureIdAxis;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the stereotype properties available in sub packages in the table.
 */
@PluginResource("resources/bugs/bug488082/488082.di")
public class StereotypeSubPackagesDisplayTest extends AbstractPapyrusTest {

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTable0"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * Constructor.
	 */
	public StereotypeSubPackagesDisplayTest() {
		super();
	}

	/**
	 * This allow to initialize the tests.
	 */
	@Before
	public void init() {
		final Model model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to test the stereotype inherited properties displayed in the table
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testStereotypePropeties() throws Exception {
		// Open the table and get the manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		final INattableModelManager currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(currentManager instanceof INattableModelManager);

		checkTableContent(currentManager);
	}

	/**
	 * This allows to test the table cell editors and values.
	 * 
	 * @param currentManager
	 *            The current nattable model manager.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkTableContent(final INattableModelManager currentManager) throws Exception {
		// check the rows and columns number
		Assert.assertEquals("The rows number is not the expected one", 1, currentManager.getRowCount()); //$NON-NLS-1$
		Assert.assertEquals("The columns number is not the expected one", 6, currentManager.getColumnCount()); //$NON-NLS-1$

		final List<Object> columns = currentManager.getColumnElementsList();

		// Check the first column
		Assert.assertTrue("The first column must be a feature id axis", columns.get(0) instanceof FeatureIdAxis); //$NON-NLS-1$
		Assert.assertEquals("The first columns must be the first attribute", "property_of_stereotype:/RootElement::Package1::Profile1::Stereotype1::Attribute1_1", ((FeatureIdAxis) columns.get(0)).getElement()); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the second column
		Assert.assertTrue("The second column must be a feature id axis", columns.get(1) instanceof FeatureIdAxis); //$NON-NLS-1$
		Assert.assertEquals("The second columns must be the first attribute", "property_of_stereotype:/RootElement::Package1::Profile1::Stereotype1::Attribute1_2", ((FeatureIdAxis) columns.get(1)).getElement()); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the third column
		Assert.assertTrue("The third column must be a feature id axis", columns.get(2) instanceof FeatureIdAxis); //$NON-NLS-1$
		Assert.assertEquals("The third columns must be the first attribute", "property_of_stereotype:/RootElement::Package1::Stereotype2::Attribute2_1", ((FeatureIdAxis) columns.get(2)).getElement()); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the fourth column
		Assert.assertTrue("The fourth column must be a feature id axis", columns.get(3) instanceof FeatureIdAxis); //$NON-NLS-1$
		Assert.assertEquals("The fourth columns must be the first attribute", "property_of_stereotype:/RootElement::Package1::Stereotype2::Attribute2_2", ((FeatureIdAxis) columns.get(3)).getElement()); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the fifth column
		Assert.assertTrue("The fifth column must be a feature id axis", columns.get(4) instanceof FeatureIdAxis); //$NON-NLS-1$
		Assert.assertEquals("The fifth columns must be the first attribute", "property_of_stereotype:/RootElement::Package1::Package1_1::Stereotype3::Attribute3_1", ((FeatureIdAxis) columns.get(4)).getElement()); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the sixth column
		Assert.assertTrue("The sixth column must be a feature id axis", columns.get(5) instanceof FeatureIdAxis); //$NON-NLS-1$
		Assert.assertEquals("The sixth columns must be the first attribute", "property_of_stereotype:/RootElement::Package1::Package1_1::Stereotype3::Attribute3_2", ((FeatureIdAxis) columns.get(5)).getElement()); //$NON-NLS-1$ //$NON-NLS-2$

		// Check the values
		Assert.assertEquals("The first value is not the expected one", true, currentManager.getDataValue(0, 0)); //$NON-NLS-1$
		Assert.assertEquals("The second value is not the expected one", "Test1", currentManager.getDataValue(1, 0)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The third value is not the expected one", 2, currentManager.getDataValue(2, 0));//$NON-NLS-1$
		Assert.assertEquals("The fourth value is not the expected one", "Test2", currentManager.getDataValue(3, 0)); //$NON-NLS-1$ //$NON-NLS-2$
		Assert.assertEquals("The fifth value is not the expected one", 3.14, currentManager.getDataValue(4, 0)); //$NON-NLS-1$
		Assert.assertEquals("The sixth value is not the expected one", "Test3", currentManager.getDataValue(5, 0)); //$NON-NLS-1$ //$NON-NLS-2$

	}

}

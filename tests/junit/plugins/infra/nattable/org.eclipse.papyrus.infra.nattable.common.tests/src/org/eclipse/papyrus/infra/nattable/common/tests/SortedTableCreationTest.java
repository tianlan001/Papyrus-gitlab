/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.common.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.StringValueStyle;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the creation of a sorted table using the architecture framework.
 * The kind of the created table is provided by this plugin and it is not available in the Papyrus user installation
 *
 */
@PluginResource("resources/api/model.di")
public class SortedTableCreationTest extends AbstractTableHelperAPITest {

	private Model model;

	/**
	 * This allow to initialize the tests.
	 */
	@Before
	public void init() {
		this.model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

	}

	/**
	 * This test allows to check the sort named style is applied to the required columns, as done in the table configuration
	 *
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@Test
	public void createSortedTable() throws NotFoundException, ServiceException {
		createTableEditor(this.model, this.model, "SortedTreeTableForTestType", true); //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.common.tests.AbstractTableHelperAPITest#checkTableCreation(org.eclipse.papyrus.infra.nattable.model.nattable.Table, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, java.lang.String, java.lang.String,
	 *      java.lang.String, int, boolean)
	 *
	 * @param createdTable
	 * @param context
	 * @param owner
	 * @param tableType
	 * @param tableName
	 * @param tableDescription
	 * @param nbPageBeforeCreation
	 * @param mustBeOpen
	 * @throws NotFoundException
	 * @throws ServiceException
	 */
	@Override
	protected void checkTableCreation(Table createdTable, EObject context, EObject owner, String tableType, String tableName, String tableDescription, int nbPageBeforeCreation, boolean mustBeOpen) throws NotFoundException, ServiceException {
		super.checkTableCreation(createdTable, context, owner, tableType, tableName, tableDescription, nbPageBeforeCreation, mustBeOpen);
		AbstractAxisProvider provider = createdTable.getCurrentColumnAxisProvider();
		Assert.assertNotNull(provider);
		Assert.assertEquals(createdTable, provider.eContainer());
		for (IAxis axis : provider.getAxis()) {
			final StringValueStyle valueStyle = (StringValueStyle) axis.getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), NamedStyleConstants.SORT);
			Assert.assertNotNull(valueStyle);
			final Object current = AxisUtils.getRepresentedElement(axis);
			if (current == UMLPackage.eINSTANCE.getNamedElement_Name()) {
				Assert.assertEquals("0_DESC", valueStyle.getStringValue()); //$NON-NLS-1$
			} else if (current == UMLPackage.eINSTANCE.getNamedElement_Visibility()) {
				Assert.assertEquals("1_DESC", valueStyle.getStringValue()); //$NON-NLS-1$
			} else {
				Assert.assertTrue("We don't found expected column", false); //$NON-NLS-1$
			}
		}
	}

}

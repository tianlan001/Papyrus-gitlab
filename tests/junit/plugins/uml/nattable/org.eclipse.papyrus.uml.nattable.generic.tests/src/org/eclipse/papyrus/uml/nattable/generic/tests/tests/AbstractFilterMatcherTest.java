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

package org.eclipse.papyrus.uml.nattable.generic.tests.tests;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.style.ConfigAttribute;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.filter.FilterPreferences;
import org.eclipse.papyrus.infra.nattable.layer.FilterRowDataLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.generic.tests.Activator;
import org.junit.AfterClass;
import org.junit.Assert;
import org.osgi.framework.Bundle;

/**
 * Abstract Class used to test filter
 *
 */
public abstract class AbstractFilterMatcherTest extends AbstractGenericTableTest {

	/**
	 * the path where the Papyrus model to test are stored
	 */
	public static final String PATH = "/resources/filter/"; //$NON-NLS-1$


	public void initModel() throws Exception {
		initModel("table", getClass().getSimpleName(), getBundle()); //$NON-NLS-1$

		// this test work only for false
		Assert.assertTrue(!FilterPreferences.displayInconsistentValueWithFilter(null));
//		openGenericTableForTest();
		loadGenericTable(); //$NON-NLS-1$ //$NON-NLS-2$
		flushDisplayEvents();
		//this test work only for false
		Assert.assertTrue(!FilterPreferences.displayInconsistentValueWithFilter(null));
	}

	public void initModelWithAdditionalModels(Collection<String> otherModelToLoad) throws Exception {
		initModelWithAdditionalModels("table", getClass().getSimpleName(), getBundle(), otherModelToLoad); //$NON-NLS-1$
//		openGenericTableForTest();
		loadGenericTable(); //$NON-NLS-1$ //$NON-NLS-2$
		flushDisplayEvents();
		//this test work only for false
		Assert.assertTrue(!FilterPreferences.displayInconsistentValueWithFilter(null));
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.tests.AbstractGenericTableTest#getSourcePath()
	 *
	 * @return
	 *         the folder where the model are stored
	 */
	@Override
	protected String getSourcePath() {
		return PATH;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.uml.nattable.generic.tests.tests.AbstractGenericTableTest#getBundle()
	 *
	 * @return
	 *         the curernt bundler
	 */
	@Override
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}

	/**
	 * Close all the opened editors
	 */
	@AfterClass
	public static void endOfTest() {
		GenericUtils.closeAllEditors();
	}

	/**
	 * 
	 * @param tableManager
	 *            the table manager
	 * @return
	 *         the nattable widget to use
	 */
	protected NatTable getNatTable(INattableModelManager tableManager) {
		NatTable natTable = (NatTable) tableManager.getAdapter(NatTable.class);
		return natTable;
	}

	/**
	 * Check filter without data validator.
	 * 
	 * @param matchOn
	 *            the filter value : the object on which we do the math
	 * @param columnPosition
	 *            the position of the filtered column
	 * @param rowPosition
	 *            the row position of the filter
	 * @param nbElementsInTheTable
	 *            the initial number of elements in the table
	 * @param nbMatchingElement
	 *            the number of elements matching the filter
	 * @throws Exception
	 */
	protected void checkFilter(final Object matchOn, final int columnPosition, final int rowPosition, final int nbElementsInTheTable, final int nbMatchingElement) throws Exception {
		checkFilter(matchOn, columnPosition, rowPosition, nbElementsInTheTable, nbMatchingElement, null);
	}
	
	/**
	 * Check filter with data validator.
	 * 
	 * @param matchOn
	 *            the filter value : the object on which we do the math
	 * @param columnPosition
	 *            the position of the filtered column
	 * @param rowPosition
	 *            the row position of the filter
	 * @param nbElementsInTheTable
	 *            the initial number of elements in the table
	 * @param nbMatchingElement
	 *            the number of elements matching the filter
	 * @throws Exception
	 */
	protected void checkFilterWithDataValidator(final Object matchOn, final int columnPosition, final int rowPosition, final int nbElementsInTheTable, final int nbMatchingElement) throws Exception {
		checkFilter(matchOn, columnPosition, rowPosition, nbElementsInTheTable, nbMatchingElement, EditConfigAttributes.DATA_VALIDATOR);
	}
	
	/**
	 * 
	 * @param matchOn
	 *            the filter value : the object on which we do the math
	 * @param columnPosition
	 *            the position of the filtered column
	 * @param rowPosition
	 *            the row position of the filter
	 * @param nbElementsInTheTable
	 *            the initial number of elements in the table
	 * @param nbMatchingElement
	 *            the number of elements matching the filter
	 * @param configAttribute The config attribute data validator.
	 * @throws Exception
	 */
	protected void checkFilter(final Object matchOn, final int columnPosition, final int rowPosition, final int nbElementsInTheTable, final int nbMatchingElement, final ConfigAttribute<IDataValidator> configAttribute) throws Exception {
		INattableModelManager manager = getTableManager();
		List<Object> elements = manager.getRowElementsList();
		Assert.assertEquals(nbElementsInTheTable, elements.size());
		checkUnicityOfElements(elements);

		NatTable natTable = getNatTable(manager);
		
		if (null != configAttribute) {
			final IDataValidator dataValidator = natTable.getConfigRegistry().getConfigAttribute(EditConfigAttributes.DATA_VALIDATOR, DisplayMode.NORMAL, FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX + "0");
			Assert.assertTrue("Validator doesn't manage the value to set", dataValidator.validate(columnPosition, rowPosition, matchOn)); ////$NON-NLS-1$
		}

		// 1. we apply the filter
		natTable.getLayer().doCommand(new UpdateDataCommand(natTable.getLayer(), columnPosition, rowPosition, matchOn));
		flushDisplayEvents();

		// 2. we check the result
		checkUnicityOfElements(elements);
		int newSize = elements.size();
		Assert.assertEquals(nbMatchingElement, newSize);

		// 3. we undo the filter
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(manager.getTable());
		domain.getCommandStack().undo();
		flushDisplayEvents();

		// 4. we check the undo result
		checkUnicityOfElements(elements);
		newSize = elements.size();
		Assert.assertEquals(nbElementsInTheTable, newSize);

		// 5. we reapply the filter
		domain.getCommandStack().redo();
		flushDisplayEvents();

		// 6. we check the result
		newSize = elements.size();
		Assert.assertEquals(nbMatchingElement, newSize);
	}

	/**
	 * 
	 * @param rowElements
	 * 
	 *            This method check than the row elements list doesn't contain duplicated elements
	 */
	protected void checkUnicityOfElements(List<?> rowElements) {
		int initialSize = rowElements.size();
		Set<Object> set = new HashSet<Object>();
		for (Object current : rowElements) {
			set.add(current);
		}
		Assert.assertEquals("Some objects seems duplicated in the row elements list", initialSize, set.size()); //$NON-NLS-1$
	}
}

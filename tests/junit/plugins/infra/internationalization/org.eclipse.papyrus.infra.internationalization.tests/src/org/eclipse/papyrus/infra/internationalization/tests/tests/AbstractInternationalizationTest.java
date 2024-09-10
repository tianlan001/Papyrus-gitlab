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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.internationalization.tests.tests;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.views.modelexplorer.DecoratingLabelProviderWTooltips;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

/**
 * This allows to define the abstract class for the internationalization tests.
 */
@SuppressWarnings({ "nls", "restriction" })
public abstract class AbstractInternationalizationTest extends AbstractPapyrusTest {

	/** The papyrus fixture for the project. */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/** The label provider. */
	protected DecoratingLabelProviderWTooltips labelProvider;

	/** The existing diagram. */
	protected Diagram diagram;

	/** The existing table. */
	protected Table table;

	/**
	 * Constructor.
	 */
	public AbstractInternationalizationTest() {
		super();
	}

	/**
	 * Initialize the model.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Before
	public void initTest() throws Exception {

		labelProvider = (DecoratingLabelProviderWTooltips) fixture.getModelExplorerView().getCommonViewer()
				.getLabelProvider();

		for (Object object : fixture.getPageManager().allPages()) {
			if (object instanceof Table) {
				table = (Table) object;
			} else if (object instanceof Diagram) {
				diagram = (Diagram) object;
			}
		}
	}

	/**
	 * This allows to test the labels when the internationalization is not used.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	public void checkNoLabels() throws Exception {
		Assert.assertEquals("The diagram label is not the expected one.", "ClassDiagram",
				labelProvider.getText(diagram));

		Assert.assertEquals("The table label is not the expected one.", "GenericTable0",
				labelProvider.getText(table));
	}

	/**
	 * This allows to test the french labels of all initial existing objects.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	public void checkFrenchLabels() throws Exception {
		Assert.assertEquals("The diagram label is not the expected one.", "MonDiagrammeDeClasse",
				labelProvider.getText(diagram));

		Assert.assertEquals("The table label is not the expected one.", "MaTableGenerique",
				labelProvider.getText(table));
	}

	/**
	 * This allows to test the english labels of all initial existing objects.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	public void checkEnglishLabels() throws Exception {
		Assert.assertEquals("The diagram label is not the expected one.", "MyClassDiagram",
				labelProvider.getText(diagram));

		Assert.assertEquals("The table label is not the expected one.", "MyGenericTable",
				labelProvider.getText(table));
	}
}

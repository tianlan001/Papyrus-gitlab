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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.bugs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EStructuralFeatureAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.LocalTableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.IntListValueStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This allows to test the display of elements when the hidden categories are changed.
 */
@PluginResource("resources/bugs/bug467723/ChangeCategoryNotExpanded.di") //$NON-NLS-1$
public class ChangeCategoryNotExpandedTest extends AbstractPapyrusTest {
	/**
	 * The class name in the model.
	 */
	private static final String CLASS_NAME = "Class1"; //$NON-NLS-1$

	/**
	 * The comment name in the model.
	 */
	private static final String COMMENT_BODY = "Comment"; //$NON-NLS-1$

	/**
	 * The table name.
	 */
	private static final String TABLE_NAME = "GenericTreeTable0"; //$NON-NLS-1$

	/**
	 * The papyrus fixture.
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	/**
	 * The current model.
	 */
	public Model model = null;

	/**
	 * The nattable model manager.
	 */
	public INattableModelManager currentManager = null;

	/**
	 * Constructor.
	 */
	public ChangeCategoryNotExpandedTest() {
		super();
	}

	/**
	 * Initialize.
	 */
	@Before
	public void init() {
		// Get the model
		model = (Model) fixture.getModel();
		Assert.assertNotNull("RootModel is null", model); //$NON-NLS-1$

		// Get the table and open it
		final Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the table and its manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The table manage must be a tree table manager", currentManager instanceof TreeNattableModelManager); //$NON-NLS-1$
	}

	/**
	 * This allows to test the deletion of the class row element.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	@Test
	public void testClassDestroyRowElement() throws Exception {
		// Get the editing domain
		final TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Hide all categories
		hideAllCategories(editingDomain);

		// Check the initial content after the hide all categories command
		checkInitialContent();

		// Change ownedComment depth0 category
		changeFirstCategory(editingDomain);

		// Check the modified table after the categories change
		checkAfterModifiedCategories();

		// Check the undo
		undo(editingDomain);
		checkInitialContent();

		// Check the redo
		redo(editingDomain);
		checkAfterModifiedCategories();
	}

	/**
	 * This allows to hide categories by the named styles.
	 * 
	 * @param editingDomain
	 *            the table editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void hideAllCategories(final TransactionalEditingDomain editingDomain) {
		final Table table = currentManager.getTable();
		final int maxDepth = FillingConfigurationUtils.getMaxDepthForTree(table);

		// 1. obtain the list of feature to hide
		final List<Integer> toHide = new ArrayList<Integer>();
		int start = 0;
		if (!FillingConfigurationUtils.hasTreeFillingConfigurationForDepth(table, 0)) {
			start = 1;
		}
		for (int i = start; i <= maxDepth; i++) {
			toHide.add(Integer.valueOf(i));
		}

		IntListValueStyle values = StyleUtils.getHiddenDepthsValueStyle(table);
		Command cmd = null;
		if (values == null) {
			values = NattablestyleFactory.eINSTANCE.createIntListValueStyle();
			values.setName(NamedStyleConstants.HIDDEN_CATEGORY_FOR_DEPTH);
			values.eSet(NattablestylePackage.eINSTANCE.getIntListValueStyle_IntListValue(), toHide);
			cmd = AddCommand.create(editingDomain, table, NattablestylePackage.eINSTANCE.getStyledElement_Styles(), Collections.singleton(values));
		} else {
			toHide.removeAll(values.getIntListValue());
			Assert.assertTrue(!toHide.isEmpty());

			// here we don't manage the order of the hidden category, because it is not important + to manage it we will receive more than 1 notification in UpdateTableContentListener AND we don't want that
			cmd = AddCommand.create(editingDomain, values, NattablestylePackage.eINSTANCE.getIntListValueStyle_IntListValue(), toHide);
		}
		editingDomain.getCommandStack().execute(cmd);

		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to modify the packagedElements axis by the ownedComment axis.
	 * 
	 * @param editingDomain
	 *            The table editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void changeFirstCategory(final TransactionalEditingDomain editingDomain) throws Exception {
		RecordingCommand command = new RecordingCommand(editingDomain) {

			@Override
			protected void doExecute() {

				final Table table = currentManager.getTable();

				final List<TreeFillingConfiguration> createdFillingConfigurations = new ArrayList<TreeFillingConfiguration>();

				// Create the filling configurations (copy of current with the first tree filling axis change)
				for (final TreeFillingConfiguration fillingConfiguration : FillingConfigurationUtils.getAllTreeFillingConfiguration(table)) {
					final TreeFillingConfiguration createdFillingConfiguration = EcoreUtil.copy(fillingConfiguration);

					if (0 == createdFillingConfiguration.getDepth()) {
						final IAxis axis = createdFillingConfiguration.getAxisUsedAsAxisProvider();
						// If the axis is the packagedElement, change it to ownedComment
						if (axis instanceof EStructuralFeatureAxis && ((EStructuralFeatureAxis) axis).getElement().equals(UMLPackage.eINSTANCE.getPackage_PackagedElement())) {
							final EStructuralFeatureAxis ownedCommentAxis = NattableaxisFactory.eINSTANCE.createEStructuralFeatureAxis();
							ownedCommentAxis.setElement(UMLPackage.eINSTANCE.getElement_OwnedComment());

							createdFillingConfiguration.setAxisUsedAsAxisProvider(ownedCommentAxis);
						}
					}

					createdFillingConfigurations.add(createdFillingConfiguration);
				}

				final LocalTableHeaderAxisConfiguration local = (LocalTableHeaderAxisConfiguration) HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTable(table);
				final List<AxisManagerConfiguration> axisManagerConfigurations = local.getAxisManagerConfigurations();
				final AxisManagerConfiguration wantedAxisManagerConfiguration = axisManagerConfigurations.get(0);
				local.getOwnedAxisConfigurations().clear();
				local.getOwnedAxisConfigurations().addAll(createdFillingConfigurations);
				wantedAxisManagerConfiguration.getLocalSpecificConfigurations().clear();
				wantedAxisManagerConfiguration.getLocalSpecificConfigurations().addAll(createdFillingConfigurations);
			}
		};
		editingDomain.getCommandStack().execute(command);

		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to undo the previous command.
	 * 
	 * @param editingDomain
	 *            The current editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void undo(final TransactionalEditingDomain editingDomain) throws Exception {
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to redo the previous command.
	 * 
	 * @param editingDomain
	 *            The current editing domain.
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void redo(final TransactionalEditingDomain editingDomain) throws Exception {
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();
	}

	/**
	 * This allows to check the initial content of the table and especially the first row displayed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkInitialContent() throws Exception {
		final Object firstDisplayElementAxis = ((TreeNattableModelManager) currentManager).getRowHeaderLayerStack().getDataValueByPosition(1, 0);
		Assert.assertTrue("The first row must be an EObjectTreeItemAxis", firstDisplayElementAxis instanceof EObjectTreeItemAxis);
		Assert.assertTrue("The first object in table must be the class", ((EObjectTreeItemAxis) firstDisplayElementAxis).getElement() instanceof org.eclipse.uml2.uml.Class);
		Assert.assertEquals("The value of the first cell in table must the class", CLASS_NAME, ((org.eclipse.uml2.uml.Class) ((EObjectTreeItemAxis) firstDisplayElementAxis).getElement()).getName());
	}

	/**
	 * This allows to check the content of the table after the modified categories and especially the first row displayed.
	 * 
	 * @throws Exception
	 *             The caught exception.
	 */
	protected void checkAfterModifiedCategories() throws Exception {
		final Object firstDisplayElementAxis = ((TreeNattableModelManager) currentManager).getRowHeaderLayerStack().getDataValueByPosition(1, 0);
		Assert.assertTrue("The first row must be an EObjectTreeItemAxis", firstDisplayElementAxis instanceof EObjectTreeItemAxis);
		Assert.assertTrue("The first object in table must be the comment", ((EObjectTreeItemAxis) firstDisplayElementAxis).getElement() instanceof Comment);
		Assert.assertEquals("The value of the first cell in table must the comment", COMMENT_BODY, ((Comment) ((EObjectTreeItemAxis) firstDisplayElementAxis).getElement()).getBody());
	}
}

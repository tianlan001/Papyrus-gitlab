/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.UnexecutableCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.internal.treeproxy.EObjectTreeElement;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.TableUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ShowView;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.papyrus.views.modelexplorer.CustomCommonViewer;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerPageBookView;
import org.eclipse.papyrus.views.modelexplorer.ModelExplorerView;
import org.eclipse.papyrus.views.modelexplorer.NavigatorUtils;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.navigator.dnd.NavigatorDnDService;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to test the sort in the Hierarchical NatTable
 */
@SuppressWarnings("restriction")
@PluginResource("resources/bugs/bug471903/MoveElementsTreeTableTest.di") //$NON-NLS-1$
@ShowView(value = "org.eclipse.papyrus.views.modelexplorer.modelexplorer") //$NON-NLS-1$
public class MoveElementsTreeTableTest extends AbstractPapyrusTest {

	/**
	 * The first class name in the model.
	 */
	private static final String FIRST_CLASS = "Class1"; //$NON-NLS-1$

	/**
	 * The second class name in the model.
	 */
	private static final String SECOND_CLASS = "Class2"; //$NON-NLS-1$

	/**
	 * The third class name in the model.
	 */
	private static final String THIRD_CLASS = "Class3"; //$NON-NLS-1$

	/**
	 * The fourth class name in the model.
	 */
	private static final String FOURTH_CLASS = "Class4"; //$NON-NLS-1$

	/**
	 * The first package name in the model.
	 */
	private static final String FIRST_PACKAGE = "Package1"; //$NON-NLS-1$

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
	 * The model explorer part.
	 */
	public IViewPart modelExplorerPart = null;

	/**
	 * The model explorer view.
	 */
	public ModelExplorerView modelExplorerView = null;
	
	/**
	 * The nattable model manager.
	 */
	public INattableModelManager currentManager = null;

	/**
	 * Constructor.
	 */
	public MoveElementsTreeTableTest() {
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
		Table mainTable = TableUtils.getNotationFirstTable(fixture.getModelSet(), TABLE_NAME);
		fixture.getPageManager().openPage(mainTable);
		fixture.flushDisplayEvents();

		// Get the model explorer part and view
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		modelExplorerPart = activeWorkbenchWindow.getActivePage().findView(ModelExplorerPageBookView.VIEW_ID); // $NON-NLS-1$
		Assert.assertNotNull("The ModelExplorer view is not open", modelExplorerPart); //$NON-NLS-1$
		Assert.assertTrue("The ModelExplorer is not a common navigator", modelExplorerPart instanceof ModelExplorerPageBookView); //$NON-NLS-1$
		modelExplorerView = (ModelExplorerView) ((ModelExplorerPageBookView) modelExplorerPart).getActiveView();
		Assert.assertNotNull("The model explorer view cannot be null", modelExplorerView); //$NON-NLS-1$
		
		// Get the table and its manager
		final IPageManager pageManager = fixture.getPageManager();
		final List<Object> pages = pageManager.allPages();
		pageManager.openPage(pages.get(0));
		final IEditorPart part = fixture.getEditor().getActiveEditor();
		Assert.assertTrue(part instanceof NatTableEditor);
		final NatTableEditor editor = (NatTableEditor) part;
		currentManager = (INattableModelManager) editor.getAdapter(INattableModelManager.class);
		Assert.assertTrue("The table manage must be a tree table manager", currentManager instanceof TreeNattableModelManager); //$NON-NLS-1$

		// Expand the table
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
	}

	/**
	 * This allows to test the move in the same parent.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testSimpleMoveSameParent() throws Exception {
		// Check the initial table content
		checkInitialTable(currentManager.getRowElementsList());

		// Get the editing domain
		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Get the first class
		final EObject firstClass = model.getPackagedElement(FIRST_CLASS);

		// Create the move command
		Command moveCommand = MoveCommand.create(editingDomain, model, UMLPackage.eINSTANCE.getPackage_PackagedElement(), firstClass, 4);

		// Execute the command
		editingDomain.getCommandStack().execute(moveCommand);
		fixture.flushDisplayEvents();

		// Check the table content
		checkSimpleMoveSameParentTable(currentManager.getRowElementsList());

		// Undo the command
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check the table content
		checkInitialTable(currentManager.getRowElementsList());

		// Redo the command
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();

		// Check the table content
		checkSimpleMoveSameParentTable(currentManager.getRowElementsList());
	}
	
	/**
	 * This allows to test the move in the same parent.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testMultipleMoveSameParent() throws Exception {
		// Check the initial table content
		checkInitialTable(currentManager.getRowElementsList());

		// Get the editing domain
		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Get the first class
		final EObject firstClass = model.getPackagedElement(FIRST_CLASS);
		final EObject secondClass = model.getPackagedElement(SECOND_CLASS);

		// Create the move command
		final CompoundCommand multipleMoveCommand = new CompoundCommand();
		multipleMoveCommand.append(MoveCommand.create(editingDomain, model, UMLPackage.eINSTANCE.getPackage_PackagedElement(), firstClass, 4));
		multipleMoveCommand.append(MoveCommand.create(editingDomain, model, UMLPackage.eINSTANCE.getPackage_PackagedElement(), secondClass, 4));

		// Execute the command
		editingDomain.getCommandStack().execute(multipleMoveCommand);
		fixture.flushDisplayEvents();

		// Check the table content
		checkMultipleMoveSameParentTable(currentManager.getRowElementsList());

		// Undo the command
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check the table content
		checkInitialTable(currentManager.getRowElementsList());

		// Redo the command
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();

		// Check the table content
		checkMultipleMoveSameParentTable(currentManager.getRowElementsList());
	}

	/**
	 * This allows to test the move in a sub element of its current parent.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testSimpleMoveSubElementParent() throws Exception {
		// Check the initial table content
		checkInitialTable(currentManager.getRowElementsList());

		// Get the editing domain
		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Get the first class
		final EObject firstClass = model.getPackagedElement(FIRST_CLASS);
		final EObject firstPackage = model.getPackagedElement(FIRST_PACKAGE);

		// Get the move command
		final Command moveCommand = getDropElementsCommand(Arrays.asList(firstClass), firstPackage);

		// Execute the command
		editingDomain.getCommandStack().execute(moveCommand);
		fixture.flushDisplayEvents();
		// Expand the table
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();

		// Check the table content
		checkSimpleMoveSubElementsParentTable(currentManager.getRowElementsList());

		// Undo the command
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check the table content
		checkInitialTable(currentManager.getRowElementsList());

		// Redo the command
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();

		// Check the table content
		checkSimpleMoveSubElementsParentTable(currentManager.getRowElementsList());
	}
	
	/**
	 * This allows to test the move in a sub element of its current parent.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testMultipleMoveSubElementParent() throws Exception {
		// Check the initial table content
		checkInitialTable(currentManager.getRowElementsList());

		// Get the editing domain
		TransactionalEditingDomain editingDomain = fixture.getEditingDomain();
		Assert.assertNotNull("The editing domain cannot be null", editingDomain); //$NON-NLS-1$

		// Get the first class
		final EObject firstClass = model.getPackagedElement(FIRST_CLASS);
		final EObject secondClass = model.getPackagedElement(SECOND_CLASS);
		final EObject firstPackage = model.getPackagedElement(FIRST_PACKAGE);

		// Get the move command
		final List<EObject> classes = new ArrayList<EObject>(2);
		classes.add(firstClass);
		classes.add(secondClass);
		final Command moveCommand = getDropElementsCommand(classes, firstPackage);

		// Execute the command
		editingDomain.getCommandStack().execute(moveCommand);
		fixture.flushDisplayEvents();
		// Expand the table
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();

		// Check the table content
		checkMultipleMoveSubElementsParentTable(currentManager.getRowElementsList());

		// Undo the command
		editingDomain.getCommandStack().undo();
		fixture.flushDisplayEvents();

		// check the table content
		checkInitialTable(currentManager.getRowElementsList());

		// Redo the command
		editingDomain.getCommandStack().redo();
		fixture.flushDisplayEvents();
		((TreeNattableModelManager) currentManager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		fixture.flushDisplayEvents();

		// Check the table content
		checkMultipleMoveSubElementsParentTable(currentManager.getRowElementsList());
	}
	
	/**
	 * Get the drop elements command from sources Elements to a target element.
	 * 
	 * @param sourceElements
	 *            The source elements to drag and drop.
	 * @param targetElement
	 *            The target element where drop the source elements.
	 * @return The command corresponding to the drag and drop.
	 * @throws Exception
	 *             The exception.
	 */
	public Command getDropElementsCommand(final List<EObject> sourceElements, final EObject targetElement) throws Exception {
		Command command = UnexecutableCommand.INSTANCE;

		// Create the target element and select and reveal the first class
		Object target = findSemanticModelElementItem(targetElement);
		selectAndReveal(sourceElements);
		fixture.flushDisplayEvents();

		// Get the DnD service
		final CommonViewer viewer = modelExplorerView.getCommonViewer();
		NavigatorDnDService dndService = (NavigatorDnDService) viewer.getNavigatorContentService().getDnDService();
		Assert.assertNotNull("Impossible to find dnd service", dndService); //$NON-NLS-1$

		// Manage the drop gtom the papyris drop assistant
		CommonDropAdapter commonDropAdapter = ((CustomCommonViewer) modelExplorerView.getCommonViewer()).getDropAdapter();
		CommonDropAdapterAssistant[] commonDropAdapterAssistants = dndService.findCommonDropAdapterAssistants(target, (IStructuredSelection) viewer.getSelection());
		for (CommonDropAdapterAssistant assistant : commonDropAdapterAssistants) {
			// try to adapt to a Papyrus drop assistant. If not, send a warning
			if (assistant instanceof org.eclipse.papyrus.views.modelexplorer.dnd.CommonDropAdapterAssistant) {
				org.eclipse.papyrus.views.modelexplorer.dnd.CommonDropAdapterAssistant papyrusAssistant = (org.eclipse.papyrus.views.modelexplorer.dnd.CommonDropAdapterAssistant) assistant;
				Field currentOperationField = ViewerDropAdapter.class.getDeclaredField("currentOperation"); //$NON-NLS-1$
				Assert.assertNotNull(currentOperationField);
				currentOperationField.setAccessible(true);
				currentOperationField.set(commonDropAdapter, DND.DROP_MOVE);
				Field currentLocationField = ViewerDropAdapter.class.getDeclaredField("currentLocation"); //$NON-NLS-1$
				Assert.assertNotNull(currentLocationField);
				currentLocationField.setAccessible(true);
				currentLocationField.set(commonDropAdapter, ViewerDropAdapter.LOCATION_ON);
				LocalSelectionTransfer.getTransfer().setSelection(modelExplorerView.getCommonViewer().getSelection());
				command = papyrusAssistant.getDrop(target);
			}
		}

		return command;
	}

	/**
	 * Retrieves the Model Element Item for the given EObject
	 *
	 * @param objectToFind
	 *            object represented by the searched item
	 * @return the {@link ModelElementItem} that corresponds to the diagram
	 * @throws Exception
	 *             exception thrown in case of issue
	 */
	protected EObjectTreeElement findSemanticModelElementItem(final EObject objectToFind) throws Exception {
		selectAndReveal(Arrays.asList(objectToFind));
		IStructuredSelection selection = (IStructuredSelection) modelExplorerPart.getSite().getSelectionProvider().getSelection();
		Assert.assertEquals("One and only one object should be selected", 1, selection.size()); //$NON-NLS-1$
		Object selectedElement = selection.getFirstElement();
		Assert.assertTrue("Selection should be a model item element", selectedElement instanceof EObjectTreeElement); //$NON-NLS-1$
		Assert.assertTrue("Selection should be linked to a EObject", ((EObjectTreeElement) selectedElement).getEObject() instanceof EObject); //$NON-NLS-1$
		Assert.assertTrue("Selection should be linked to the Object: " + objectToFind, ((EObjectTreeElement) selectedElement).getEObject().equals(objectToFind)); //$NON-NLS-1$
		return (EObjectTreeElement) selectedElement;
	}

	/**
	 * Selects and reveal the specified list of elements
	 *
	 * @param newElements
	 *            the list of objects to select
	 * @throws Exception
	 *             exception thrown in case element could not be selected
	 */
	protected static void selectAndReveal(final List<EObject> newElements) throws Exception {
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					// Retrieve model explorer
					ModelExplorerView modelExplorerView = null;

					ModelExplorerPageBookView bookViewPart = (ModelExplorerPageBookView) NavigatorUtils.findViewPart(ModelExplorerPageBookView.VIEW_ID);
					if (bookViewPart != null) {
						modelExplorerView = (ModelExplorerView) bookViewPart.getActiveView();
					}

					// Set selection on new element in the model explorer
					if ((modelExplorerView != null) && (newElements != null)) {
						List<EObject> semanticElementList = new ArrayList<EObject>();
						semanticElementList.addAll(newElements);
						modelExplorerView.revealSemanticElement(semanticElementList);
					} else {
						throw new Exception("Impossible to find the model explorer required to select: " + newElements); //$NON-NLS-1$
					}
				} catch (Exception ex) {
					Activator.log.error(ex);
				}
			}
		});
	}

	/**
	 * Check the initial table content display.
	 * 
	 * @param rowElements
	 *            the row elements
	 * @throws Exception
	 *             The exception
	 */
	private void checkInitialTable(final List<?> rowElements) throws Exception {
		Assert.assertEquals("The initial table does not contains the correct number of rows", 6, rowElements.size()); //$NON-NLS-1$

		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the first class", FIRST_CLASS, ((org.eclipse.uml2.uml.Class) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals("The second element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the second element is not corresponding to the second class", SECOND_CLASS, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals("The third element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the third element is not corresponding to the third class", THIRD_CLASS, ((org.eclipse.uml2.uml.Class) fourthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals("The fourth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fourth element is not corresponding to the fourth class", FOURTH_CLASS, ((org.eclipse.uml2.uml.Class) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis sixthRow = (IAxis) rowElements.get(5);
		final Object sixthColumnRepresentedElement = sixthRow.getElement();
		Assert.assertEquals("The fifth element must be a package", UMLPackage.eINSTANCE.getPackage(), ((EObject) sixthColumnRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fifth element is not corresponding to the first package", FIRST_PACKAGE, ((org.eclipse.uml2.uml.Package) sixthColumnRepresentedElement).getName()); //$NON-NLS-1$
	}

	/**
	 * Check the initial table content display.
	 * 
	 * @param rowElements
	 *            the row elements
	 * @throws Exception
	 *             The exception
	 */
	private void checkSimpleMoveSameParentTable(final List<?> rowElements) throws Exception {
		Assert.assertEquals("The initial table does not contains the correct number of rows", 6, rowElements.size()); //$NON-NLS-1$

		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the second class", SECOND_CLASS, ((org.eclipse.uml2.uml.Class) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals("The second element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the second element is not corresponding to the third class", THIRD_CLASS, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals("The third element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the third element is not corresponding to the fourth class", FOURTH_CLASS, ((org.eclipse.uml2.uml.Class) fourthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals("The fourth element must be a package", UMLPackage.eINSTANCE.getPackage(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fourth element is not corresponding to the first package", FIRST_PACKAGE, ((org.eclipse.uml2.uml.Package) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis sixthRow = (IAxis) rowElements.get(5);
		final Object sixthColumnRepresentedElement = sixthRow.getElement();
		Assert.assertEquals("The fifth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) sixthColumnRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fifth element is not corresponding to the first class", FIRST_CLASS, ((org.eclipse.uml2.uml.Class) sixthColumnRepresentedElement).getName()); //$NON-NLS-1$
	}
	
	/**
	 * Check the initial table content display.
	 * 
	 * @param rowElements
	 *            the row elements
	 * @throws Exception
	 *             The exception
	 */
	private void checkMultipleMoveSameParentTable(final List<?> rowElements) throws Exception {
		Assert.assertEquals("The initial table does not contains the correct number of rows", 6, rowElements.size()); //$NON-NLS-1$

		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the third class", THIRD_CLASS, ((org.eclipse.uml2.uml.Class) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals("The second element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the second element is not corresponding to the fourth class", FOURTH_CLASS, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals("The third element must be a package", UMLPackage.eINSTANCE.getPackage(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the third element is not corresponding to the first package", FIRST_PACKAGE, ((org.eclipse.uml2.uml.Package) fourthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals("The fourth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fourth element is not corresponding to the first class", FIRST_CLASS, ((org.eclipse.uml2.uml.Class) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis sixthRow = (IAxis) rowElements.get(5);
		final Object sixthColumnRepresentedElement = sixthRow.getElement();
		Assert.assertEquals("The fifth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) sixthColumnRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fifth element is not corresponding to the second class", SECOND_CLASS, ((org.eclipse.uml2.uml.Class) sixthColumnRepresentedElement).getName()); //$NON-NLS-1$
	}

	/**
	 * Check the initial table content display.
	 * 
	 * @param rowElements
	 *            the row elements
	 * @throws Exception
	 *             The exception
	 */
	private void checkSimpleMoveSubElementsParentTable(final List<?> rowElements) throws Exception {
		Assert.assertEquals("The initial table does not contains the correct number of rows", 7, rowElements.size()); //$NON-NLS-1$

		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the second class", SECOND_CLASS, ((org.eclipse.uml2.uml.Class) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals("The second element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the second element is not corresponding to the third class", THIRD_CLASS, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals("The third element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the third element is not corresponding to the fourth class", FOURTH_CLASS, ((org.eclipse.uml2.uml.Class) fourthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fifthRow = (IAxis) rowElements.get(4);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals("The fourth element must be a package", UMLPackage.eINSTANCE.getPackage(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fourth element is not corresponding to the first package", FIRST_PACKAGE, ((org.eclipse.uml2.uml.Package) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis sixthRow = (IAxis) rowElements.get(5);
		final Object sixthRowRepresentedElement = sixthRow.getElement();
		Assert.assertTrue("The sixth row must be a tree filling configuration", sixthRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis seventhRow = (IAxis) rowElements.get(6);
		final Object seventhColumnRepresentedElement = seventhRow.getElement();
		Assert.assertEquals("The fifth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) seventhColumnRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fifth element is not corresponding to the first class", FIRST_CLASS, ((org.eclipse.uml2.uml.Class) seventhColumnRepresentedElement).getName()); //$NON-NLS-1$
	}

	/**
	 * Check the initial table content display.
	 * 
	 * @param rowElements
	 *            the row elements
	 * @throws Exception
	 *             The exception
	 */
	private void checkMultipleMoveSubElementsParentTable(final List<?> rowElements) throws Exception {
		Assert.assertEquals("The initial table does not contains the correct number of rows", 7, rowElements.size()); //$NON-NLS-1$

		final IAxis firstRow = (IAxis) rowElements.get(0);
		final Object firstRowRepresentedElement = firstRow.getElement();
		Assert.assertTrue("The first row must be a tree filling configuration", firstRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis secondRow = (IAxis) rowElements.get(1);
		final Object secondRowRepresentedElement = secondRow.getElement();
		Assert.assertEquals("The first element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) secondRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the first element is not corresponding to the third class", THIRD_CLASS, ((org.eclipse.uml2.uml.Class) secondRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis thirdRow = (IAxis) rowElements.get(2);
		final Object thirdRowRepresentedElement = thirdRow.getElement();
		Assert.assertEquals("The second element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) thirdRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the second element is not corresponding to the fourth class", FOURTH_CLASS, ((org.eclipse.uml2.uml.Class) thirdRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis fourthRow = (IAxis) rowElements.get(3);
		final Object fourthRowRepresentedElement = fourthRow.getElement();
		Assert.assertEquals("The third element must be a package", UMLPackage.eINSTANCE.getPackage(), ((EObject) fourthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the third element is not corresponding to the first package", FIRST_PACKAGE, ((org.eclipse.uml2.uml.Package) fourthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis sixthRow = (IAxis) rowElements.get(4);
		final Object sixthRowRepresentedElement = sixthRow.getElement();
		Assert.assertTrue("The sixth row must be a tree filling configuration", sixthRowRepresentedElement instanceof TreeFillingConfiguration); //$NON-NLS-1$
		
		final IAxis fifthRow = (IAxis) rowElements.get(5);
		final Object fifthRowRepresentedElement = fifthRow.getElement();
		Assert.assertEquals("The fourth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) fifthRowRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fourth element is not corresponding to the first class", FIRST_CLASS, ((org.eclipse.uml2.uml.Class) fifthRowRepresentedElement).getName()); //$NON-NLS-1$

		final IAxis seventhRow = (IAxis) rowElements.get(6);
		final Object seventhColumnRepresentedElement = seventhRow.getElement();
		Assert.assertEquals("The fifth element must be a class", UMLPackage.eINSTANCE.getClass_(), ((EObject) seventhColumnRepresentedElement).eClass()); //$NON-NLS-1$
		Assert.assertEquals("The name of the fifth element is not corresponding to the second class", SECOND_CLASS, ((org.eclipse.uml2.uml.Class) seventhColumnRepresentedElement).getName()); //$NON-NLS-1$
	}
	
}

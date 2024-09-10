/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.stereotype.display.tests.tests;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.common.modelresource.PapyrusNattableModel;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.nattable.stereotype.display.tests.Activator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to manage the tests of stereotype display table (creation, display and modification).
 */
@PluginResource("resources/StereotypeDisplayTableTests.di")
public class StereotypeDisplayTableTest extends AbstractPapyrusTest {

	/**
	 * The class diagram name.
	 */
	private static final String CLASS_DIAGRAM_NAME = "ClassDiagram";

	/**
	 * The component diagram name.
	 */
	private static final String COMPONENT_DIAGRAM_NAME = "ComponentDiagram";

	/**
	 * The class name.
	 */
	private static final String CLASS1_NAME = "Class1";

	/**
	 * The interface name.
	 */
	private static final String INTERFACE1_NAME = "Interface1";

	/**
	 * The component name.
	 */
	private static final String COMPONENT1_NAME = "Component1";

	/**
	 * The port name.
	 */
	private static final String PORT1_NAME = "Port1";

	/**
	 * The association name.
	 */
	private static final String ASSOCIATION1_NAME = "Association1";

	/**
	 * The time observation name.
	 */
	private static final String TIME_OBSERVATION1_NAME = "TimeObservation1";

	/**
	 * The URI of the table configuration.
	 */
	private static final String TABLE_CONFIRURATION = "platform:/plugin/org.eclipse.papyrus.uml.nattable.stereotype.display/config/stereotype.nattableconfiguration";

	/**
	 * The number of column in the stereotype display table.
	 */
	private static final int COLUMN_NUMBER = 5;

	/**
	 * The number of rows for the object with the 'human' stereotype applied.
	 */
	private static final int NUMBER_ROWS_HUMAN_STEREOTYPE = 12;

	/**
	 * The number of rows for the object with the 'animal' stereotype applied.
	 */
	private static final int NUMBER_ROWS_ANIMAL_STEREOTYPE = 8;


	/**
	 * The papyrus editor fixture.
	 */
	@Rule
	public final PapyrusEditorFixture editorFixture = new PapyrusEditorFixture();

	/**
	 * The class existing in the model.
	 */
	private org.eclipse.uml2.uml.Class class1 = null;

	/**
	 * The interface existing in the model.
	 */
	private org.eclipse.uml2.uml.Interface interface1 = null;

	/**
	 * The port existing in the model.
	 */
	private org.eclipse.uml2.uml.Port port1 = null;

	/**
	 * The association existing in the model.
	 */
	private org.eclipse.uml2.uml.Association association1 = null;

	/**
	 * The time observation existing in the model.
	 */
	private org.eclipse.uml2.uml.TimeObservation timeObservation1 = null;

	/**
	 * The singleton instance.
	 */
	private StereotypeDisplayUtil helper = StereotypeDisplayUtil.getInstance();


	/**
	 * Constructor.
	 */
	public StereotypeDisplayTableTest() {
		super();
	}

	@Before
	public void manageDiagram() {
		// get all semantic element that will handled
		final Model model = (Model) editorFixture.getModel();
		Assert.assertNotNull("RootModel is null", model);

		// Get the class and the interface in the model
		class1 = (org.eclipse.uml2.uml.Class) model.getPackagedElement(CLASS1_NAME);
		StringBuilder error = new StringBuilder(CLASS1_NAME);
		error.append(" doesn't exist");
		Assert.assertNotNull(error.toString(), class1);
		interface1 = (org.eclipse.uml2.uml.Interface) model.getPackagedElement(INTERFACE1_NAME);
		error = new StringBuilder(INTERFACE1_NAME);
		error.append(" doesn't exist");
		Assert.assertNotNull(error.toString(), interface1);
		org.eclipse.uml2.uml.Component component1 = (org.eclipse.uml2.uml.Component) model.getPackagedElement(COMPONENT1_NAME);
		error = new StringBuilder(COMPONENT1_NAME);
		error.append(" doesn't exist");
		Assert.assertNotNull(error.toString(), component1);
		port1 = component1.getOwnedPort(PORT1_NAME, null);
		error = new StringBuilder(PORT1_NAME);
		error.append(" doesn't exist");
		Assert.assertNotNull(error.toString(), port1);
		association1 = (org.eclipse.uml2.uml.Association) model.getPackagedElement(ASSOCIATION1_NAME);
		error = new StringBuilder(ASSOCIATION1_NAME);
		error.append(" doesn't exist");
		Assert.assertNotNull(error.toString(), association1);
		timeObservation1 = (org.eclipse.uml2.uml.TimeObservation) model.getPackagedElement(TIME_OBSERVATION1_NAME);
		error = new StringBuilder(TIME_OBSERVATION1_NAME);
		error.append(" doesn't exist");
		Assert.assertNotNull(error.toString(), timeObservation1);
	}

	/**
	 * This allow to test the stereotype display for the class object.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testCreateStereotypedClassTable() throws Exception {
		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());

		// Get the class view
		final View class1View = DiagramUtils.findShape(mainDiagram, CLASS1_NAME);
		Assert.assertNotNull("Class view not present", class1View);

		// Select the class in the diagram
		final EditPart classEP = editorFixture.findEditPart(class1);
		editorFixture.select(classEP);

		final ISelection selection = editorFixture.getActiveDiagramEditor().getSite().getSelectionProvider().getSelection();
		final Object editorSelection = ((IStructuredSelection) selection).getFirstElement();

		final StringBuilder error = new StringBuilder("The object selected is not the ");
		error.append(CLASS1_NAME);
		Assert.assertEquals(error.toString(), classEP, editorSelection);

		// Create the table
		final ITreeNattableModelManager manager = createTable(class1View);

		// Check the columns
		final List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not correct", COLUMN_NUMBER, columnElements.size());
		StereotypeDisplayTableTestsUtils.checkColumns(columnElements);

		// Check the rows elements
		final List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals("The number of rows is not correct", NUMBER_ROWS_HUMAN_STEREOTYPE, rowElements.size());
		StereotypeDisplayTableTestsUtils.checkClassRows(rowElements, 0, NUMBER_ROWS_HUMAN_STEREOTYPE);

		// Check the initial data in the table
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, 0, NUMBER_ROWS_HUMAN_STEREOTYPE);

		// Try to modify some values and check its result
		StereotypeDisplayTableTestsUtils.modifyHumanStereotypedValues(helper, manager, class1View, class1, 0);

		// Reselect the main diagram page
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.deselect(classEP);
		editorFixture.flushDisplayEvents();
	}

	/**
	 * This allow to test the stereotype display for the interface object.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testCreateStereotypedInterfaceTable() throws Exception {
		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());

		// Get the interface view
		final View interface1View = DiagramUtils.findShape(mainDiagram, INTERFACE1_NAME);
		Assert.assertNotNull("Interface view not present", interface1View);

		// Select the class in the diagram
		final EditPart interfaceEP = editorFixture.findEditPart(interface1);
		editorFixture.select(interfaceEP);

		final ISelection selection = editorFixture.getActiveDiagramEditor().getSite().getSelectionProvider().getSelection();
		final Object editorSelection = ((IStructuredSelection) selection).getFirstElement();

		final StringBuilder error = new StringBuilder("The object selected is not the ");
		error.append(INTERFACE1_NAME);
		Assert.assertEquals(error.toString(), interfaceEP, editorSelection);

		// Create the table
		final ITreeNattableModelManager manager = createTable(interface1View);

		// Check the columns
		final List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not correct", COLUMN_NUMBER, columnElements.size());
		StereotypeDisplayTableTestsUtils.checkColumns(columnElements);

		// Check the rows elements
		final List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals("The number of rows is not correct", NUMBER_ROWS_ANIMAL_STEREOTYPE, rowElements.size());
		StereotypeDisplayTableTestsUtils.checkInterfaceRows(rowElements, 0, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Check the initial data in the table
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, 0, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Try to modify some values and check its result
		StereotypeDisplayTableTestsUtils.modifyAnimalStereotypedValues(helper, manager, interface1View, interface1, 0);

		// Reselect the main diagram page
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.deselect(interfaceEP);
		editorFixture.flushDisplayEvents();
	}

	/**
	 * This allow to test the stereotype display for the multiple object.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testCreateStereotypedClassAndInterfaceTable() throws Exception {
		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());

		// Get the interface view
		final View class1View = DiagramUtils.findShape(mainDiagram, CLASS1_NAME);
		Assert.assertNotNull("Class view not present", class1View);
		final View interface1View = DiagramUtils.findShape(mainDiagram, INTERFACE1_NAME);
		Assert.assertNotNull("Interface view not present", interface1View);

		// Select the class in the diagram
		final EditPart classEP = editorFixture.findEditPart(class1);
		editorFixture.select(classEP);
		final EditPart interfaceEP = editorFixture.findEditPart(interface1);
		editorFixture.select(interfaceEP);

		// Create the table
		final ITreeNattableModelManager manager = createTable(class1View);

		// Check the columns
		final List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not correct", COLUMN_NUMBER, columnElements.size());
		StereotypeDisplayTableTestsUtils.checkColumns(columnElements);

		// Check the rows elements
		final List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals("The number of rows is not correct", NUMBER_ROWS_HUMAN_STEREOTYPE + NUMBER_ROWS_ANIMAL_STEREOTYPE, rowElements.size());
		StereotypeDisplayTableTestsUtils.checkClassRows(rowElements, 0, NUMBER_ROWS_HUMAN_STEREOTYPE);
		StereotypeDisplayTableTestsUtils.checkInterfaceRows(rowElements, NUMBER_ROWS_HUMAN_STEREOTYPE, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Check the initial data in the table
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, 0, NUMBER_ROWS_HUMAN_STEREOTYPE);
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, NUMBER_ROWS_HUMAN_STEREOTYPE, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Try to modify some values and check its result
		StereotypeDisplayTableTestsUtils.modifyHumanStereotypedValues(helper, manager, class1View, class1, 0);
		StereotypeDisplayTableTestsUtils.modifyAnimalStereotypedValues(helper, manager, interface1View, interface1, NUMBER_ROWS_HUMAN_STEREOTYPE);

		// Reselect the main diagram page
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.deselect(classEP);
		editorFixture.deselect(interfaceEP);
		editorFixture.flushDisplayEvents();
	}

	/**
	 * This allow to test the stereotype display for the interface object.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testCreateStereotypedPortTable() throws Exception {
		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), COMPONENT_DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("Current opened diagram is not the expected one", COMPONENT_DIAGRAM_NAME, mainDiagram.getName());

		// Get the interface view
		final View component1View = DiagramUtils.findShape(mainDiagram, COMPONENT1_NAME);
		Assert.assertNotNull("Component view not present", component1View);
		final View port1View = DiagramUtils.findShape(component1View, PORT1_NAME);
		Assert.assertNotNull("Port view not present", port1View);

		// Select the class in the diagram
		final EditPart portEP = editorFixture.findEditPart(port1);
		editorFixture.select(portEP);

		final ISelection selection = editorFixture.getActiveDiagramEditor().getSite().getSelectionProvider().getSelection();
		final Object editorSelection = ((IStructuredSelection) selection).getFirstElement();
		final StringBuilder error = new StringBuilder("The object selected is not the ");
		error.append(PORT1_NAME);
		Assert.assertEquals(error.toString(), portEP, editorSelection);

		// Create the table
		final ITreeNattableModelManager manager = createTable(port1View);

		// Check the columns
		final List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not correct", COLUMN_NUMBER, columnElements.size());
		StereotypeDisplayTableTestsUtils.checkColumns(columnElements);

		// Check the rows elements
		final List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals("The number of rows is not correct", NUMBER_ROWS_HUMAN_STEREOTYPE, rowElements.size());
		StereotypeDisplayTableTestsUtils.checkPortRows(rowElements, 0, NUMBER_ROWS_HUMAN_STEREOTYPE);

		// Check the initial data in the table
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, 0, NUMBER_ROWS_HUMAN_STEREOTYPE, false, false, true);

		// Try to modify some values and check its result
		StereotypeDisplayTableTestsUtils.modifyHumanStereotypedValues(helper, manager, port1View, port1, 0);

		// Reselect the main diagram page
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.deselect(portEP);
		editorFixture.flushDisplayEvents();
	}

	/**
	 * This allow to test the stereotype display for the association object.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testCreateStereotypedAssociationTable() throws Exception {
		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());

		// Get the interface view
		final View associationView = DiagramUtils.findEdge(mainDiagram, ASSOCIATION1_NAME);
		Assert.assertNotNull("Association view not present", associationView);

		// Select the class in the diagram
		final EditPart associationEP = editorFixture.findEditPart(association1);
		editorFixture.select(associationEP);

		final ISelection selection = editorFixture.getActiveDiagramEditor().getSite().getSelectionProvider().getSelection();
		final Object editorSelection = ((IStructuredSelection) selection).getFirstElement();
		final StringBuilder error = new StringBuilder("The object selected is not the ");
		error.append(ASSOCIATION1_NAME);
		Assert.assertEquals(error.toString(), associationEP, editorSelection);

		// Create the table
		final ITreeNattableModelManager manager = createTable(associationView);

		// Check the columns
		final List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not correct", COLUMN_NUMBER, columnElements.size());
		StereotypeDisplayTableTestsUtils.checkColumns(columnElements);

		// Check the rows elements
		final List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals("The number of rows is not correct", NUMBER_ROWS_ANIMAL_STEREOTYPE, rowElements.size());
		StereotypeDisplayTableTestsUtils.checkAssociationRows(rowElements, 0, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Check the initial data in the table
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, 0, NUMBER_ROWS_ANIMAL_STEREOTYPE, true, false, true);

		// Try to modify some values and check its result
		StereotypeDisplayTableTestsUtils.modifyAnimalStereotypedValues(helper, manager, associationView, association1, 0);

		// Reselect the main diagram page
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.deselect(associationEP);
		editorFixture.flushDisplayEvents();
	}

	/**
	 * This allow to test the stereotype display for the time observation object.
	 * 
	 * @throws Exception
	 *             The exception
	 */
	@Test
	public void testCreateStereotypedTimeObservationTable() throws Exception {
		// Open the diagram
		final Diagram mainDiagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), CLASS_DIAGRAM_NAME);
		editorFixture.getPageManager().openPage(mainDiagram);
		Assert.assertEquals("Current opened diagram is not the expected one", CLASS_DIAGRAM_NAME, mainDiagram.getName());

		// Get the interface view
		final View timeObservationView = DiagramUtils.findShape(mainDiagram, TIME_OBSERVATION1_NAME);
		Assert.assertNotNull("Time Observation view not present", timeObservationView);

		// Select the class in the diagram
		final EditPart timeObservationEP = editorFixture.findEditPart(timeObservation1);
		editorFixture.select(timeObservationEP);

		final ISelection selection = editorFixture.getActiveDiagramEditor().getSite().getSelectionProvider().getSelection();
		final Object editorSelection = ((IStructuredSelection) selection).getFirstElement();
		final StringBuilder error = new StringBuilder("The object selected is not the ");
		error.append(TIME_OBSERVATION1_NAME);
		Assert.assertEquals(error.toString(), timeObservationEP, editorSelection);

		// Create the table
		final ITreeNattableModelManager manager = createTable(timeObservationView);

		// Check the columns
		final List<?> columnElements = manager.getColumnElementsList();
		Assert.assertEquals("The number of columns is not correct", COLUMN_NUMBER, columnElements.size());
		StereotypeDisplayTableTestsUtils.checkColumns(columnElements);

		// Check the rows elements
		final List<?> rowElements = manager.getRowElementsList();
		Assert.assertEquals("The number of rows is not correct", NUMBER_ROWS_ANIMAL_STEREOTYPE, rowElements.size());
		StereotypeDisplayTableTestsUtils.checkTimeObservationRows(rowElements, 0, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Check the initial data in the table
		StereotypeDisplayTableTestsUtils.checkInitialTableData(manager, 0, NUMBER_ROWS_ANIMAL_STEREOTYPE);

		// Try to modify some values and check its result
		StereotypeDisplayTableTestsUtils.modifyAnimalStereotypedValues(helper, manager, timeObservationView, association1, 0);

		// Reselect the main diagram page
		editorFixture.getPageManager().selectPage(mainDiagram);
		editorFixture.deselect(timeObservationEP);
		editorFixture.flushDisplayEvents();
	}

	/**
	 * This allow to create the table and the nattable manager.
	 * 
	 * @param context
	 *            The context of the table.
	 * @return The created nattable manager.
	 * @throws Exception
	 *             The exception.
	 */
	public ITreeNattableModelManager createTable(final View context) throws Exception {
		ITreeNattableModelManager manager = null;

		// Create the table from the selected class in the diagram
		createEditorTable(context);
		editorFixture.flushDisplayEvents();

		// The new editor is the table
		final IEditorPart timeObservationTableEditor = editorFixture.getEditor().getActiveEditor();
		Assert.assertTrue(timeObservationTableEditor instanceof NatTableEditor);
		manager = (ITreeNattableModelManager) timeObservationTableEditor.getAdapter(INattableModelManager.class);

		// expand the table
		manager.doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		editorFixture.flushDisplayEvents();

		return manager;
	}

	/**
	 * Create a model identifying the editor. This model will be saved with the sash.
	 * 
	 * @param view
	 *            The view context of the table.
	 * @throws Exception
	 *             The exception.
	 */
	protected void createEditorTable(final View view) throws Exception {
		final ServicesRegistry serviceRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(view);
		final ModelSet modelSet = ServiceUtils.getInstance().getModelSet(serviceRegistry);
		final TableConfiguration configuration = getDefaultTableEditorConfiguration(modelSet);
		Assert.assertNotNull(configuration);

		final TransactionalEditingDomain domain = ServiceUtils.getInstance().getTransactionalEditingDomain(serviceRegistry);
		domain.getCommandStack().execute(new RecordingCommand(domain) {
			@Override
			protected void doExecute() {
				Table editorModel;
				try {
					editorModel = TableHelper.createTable(configuration, view, configuration.getName(), description);
					// Save the model in the associated resource
					final PapyrusNattableModel model = (PapyrusNattableModel) modelSet.getModelChecked(PapyrusNattableModel.MODEL_ID);
					model.addPapyrusTable(editorModel);

					// Get the manager allowing to add/open new editor.
					final IPageManager pageMngr = ServiceUtils.getInstance().getService(IPageManager.class, serviceRegistry);
					pageMngr.openPage(editorModel);
				} catch (Exception e) {
					Activator.log.error(e);
				}
			}
		});
	}

	/**
	 * Get the configuration to use for the new table
	 *
	 * @param resourceSet
	 *            The resource set.
	 * @return The configuration to use for the new table
	 */
	protected TableConfiguration getDefaultTableEditorConfiguration(final ResourceSet resourceSet) {
		final Resource resource = resourceSet.getResource(URI.createURI(TABLE_CONFIRURATION), true);
		TableConfiguration tableConfiguration = null;
		if (resource.getContents().get(0) instanceof TableConfiguration) {
			tableConfiguration = (TableConfiguration) resource.getContents().get(0);
		}
		return tableConfiguration;
	}
}

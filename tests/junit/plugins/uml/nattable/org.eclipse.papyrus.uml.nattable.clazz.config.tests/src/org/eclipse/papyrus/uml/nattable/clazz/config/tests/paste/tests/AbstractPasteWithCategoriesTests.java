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

package org.eclipse.papyrus.uml.nattable.clazz.config.tests.paste.tests;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.commands.core.command.EditingDomainUndoContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.commands.OpenDiagramCommand;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.CheckedOperationHistory;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.handler.PasteInTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.AbstractNattableWidgetManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.ITreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.parsers.CSVParser;
import org.eclipse.papyrus.infra.nattable.parsers.CellIterator;
import org.eclipse.papyrus.infra.nattable.parsers.RowIterator;
import org.eclipse.papyrus.infra.nattable.tree.CollapseAndExpandActionsEnum;
import org.eclipse.papyrus.infra.nattable.utils.AbstractPasteInsertInTableHandler;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.FillingConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.PasteInsertUtil;
import org.eclipse.papyrus.infra.nattable.utils.PasteSeverityCode;
import org.eclipse.papyrus.infra.nattable.utils.StyleUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableClipboardUtils;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.infra.ui.util.EclipseCommandUtils;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.utils.EditorUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.Activator;
import org.eclipse.papyrus.uml.nattable.clazz.config.tests.tests.AbstractOpenTableTest;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

import ca.odell.glazedlists.TreeList;
import ca.odell.glazedlists.TreeList.Node;

/**
 * This class allows us to test the paste in the table. We test :
 * <ul>
 * <li>the consistency of the tested model. It is a test for the developper which create the tests</li>
 * <li>we load a papyrus model called by the test class name : className.di, we open it in Papyrus and we check the content of the table : (table must be empty when the test starts</li>
 * <li>we load a file className.txt and we try to paste it, we check the command status of the paste</li>
 * <li>if status if OK :
 * <ul>
 * <li>we check the new contents of the UML Model</li>
 * <li>the table data structure (the created ITreeItemAxis)</li>
 * <li>the displayed contents of the table, copying the table contents to the clipboard (TODO : activate this part of the tests)</li>
 * <li>the undo/redo</li>
 * <li>the re-open of the table, to check it correct initialization< (could be done in another class tests, but it is easier to do it here</li>
 * </ul>
 * </li>
 * </ul>
 *
 */
@InvalidTest("Bug 494265")
public abstract class AbstractPasteWithCategoriesTests extends AbstractOpenTableTest {

	public static final String PASTE_FOLDER_PATH = "/resources/paste_tests/"; //$NON-NLS-1$

	public static final String OPERATION_BASE_NAME = "Operation"; //$NON-NLS-1$

	public static final String PARAMETER_BASE_NAME = "Param"; //$NON-NLS-1$

	public static final String PROPERTY_BASE_NAME = "Property"; //$NON-NLS-1$

	public static final String CLASS_BASE_NAME = "Class"; //$NON-NLS-1$

	public static final String NESTED_CLASS_BASE_NAME = "NestedClass"; //$NON-NLS-1$

	public static final String CLASS_ELEMENT_ID = "org.eclipse.papyrus.uml.Class"; //$NON-NLS-1$
	public static final String OPERATION_ELEMENT_ID = "org.eclipse.papyrus.uml.Operation"; //$NON-NLS-1$
	public static final String PARAMETER_ELEMENT_ID = "org.eclipse.papyrus.uml.Parameter"; //$NON-NLS-1$
	public static final String PROPERTY_ELEMENT_ID = "org.eclipse.papyrus.uml.Property"; //$NON-NLS-1$

	// TODO : create fields and constructors to get hidden, visibility, and number of categories by depth easily, instead of calculate them each time


	@Before
	public void initModel() throws Exception {
		initModel("classTreeTable", getClass().getSimpleName(), getBundle()); //$NON-NLS-1$
	};

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void checkModelForTestConsistency() throws Exception {
		String className = getClass().getSimpleName();
		className = className.replaceFirst("PasteWithCategories_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 6);
		for (int depth = 0; depth < 3; depth++) {
			final String current = result[depth];
			// filled by DnD
			if (current.equals("Empty")) { //$NON-NLS-1$
				// no configuration
				Assert.assertTrue("We must not have filling configuration for depth==0", FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(getTable(), 0).size() == 0); //$NON-NLS-1$
				// we can't hide a depth for which we don't have category

				Assert.assertTrue("The depth 0 can't be hidden", StyleUtils.isHiddenDepth(getTable(), 0) == false);//$NON-NLS-1$
			} else {
				Assert.assertEquals(2, current.length());
				final char visibility = current.charAt(0);
				final char nbCategoriesForTheDepth = current.charAt(1);
				switch (visibility) {
				case 'H':
					Assert.assertTrue(NLS.bind("The depth {0} must be hidden", depth), true == StyleUtils.isHiddenDepth(getTable(), depth));//$NON-NLS-1$
					break;
				case 'V':
					Assert.assertTrue(NLS.bind("The depth {0} must be visible", depth), false == StyleUtils.isHiddenDepth(getTable(), depth));//$NON-NLS-1$
					break;
				default:
					throw new Exception("Not supported case"); //$NON-NLS-1$
				}
				// we check that we have the wanted number of filling categories
				final List<TreeFillingConfiguration> confs = FillingConfigurationUtils.getAllTreeFillingConfigurationForDepth(getTable(), depth);
				final int nbConfig = confs.size();
				final int wantedConfig = Integer.parseInt(String.valueOf(nbCategoriesForTheDepth));
				Assert.assertEquals(wantedConfig, nbConfig);

				if (depth == 0) {
					Assert.assertTrue(confs.size() == 1);
					for (final TreeFillingConfiguration tmp : confs) {
						final PasteEObjectConfiguration pasteConf = tmp.getPasteConfiguration();
						Assert.assertNotNull("We don't have paste configuration for a TreeFillingConfiguration, depth=" + tmp.getDepth(), pasteConf); //$NON-NLS-1$
						if (depth == 0) {
							Assert.assertTrue(confs.size() == 1);
							Assert.assertTrue(CLASS_ELEMENT_ID.equals(pasteConf.getPastedElementId()));
						}
					}
				}
				if (depth == 1) {
					boolean nestedClass = false;
					boolean operation = false;
					boolean property = false;

					for (final TreeFillingConfiguration tmp : confs) {
						final PasteEObjectConfiguration pasteConf = tmp.getPasteConfiguration();
						Assert.assertNotNull("We don't have paste configuration for a TreeFillingConfiguration, depth=" + tmp.getDepth(), pasteConf); //$NON-NLS-1$
						if (depth == 1) {
							if (PROPERTY_ELEMENT_ID.equals(pasteConf.getPastedElementId())) {
								property = true;
							} else if (OPERATION_ELEMENT_ID.equals(pasteConf.getPastedElementId())) {
								operation = true;
							} else if (CLASS_ELEMENT_ID.equals(pasteConf.getPastedElementId())) {
								nestedClass = true;
							}
						}
					}
					if (confs.size() == 3) {
						Assert.assertTrue(property);
						Assert.assertTrue(operation);
						Assert.assertTrue(nestedClass);
					} else if (confs.size() == 1) {
						Assert.assertTrue(!property);
						Assert.assertTrue(operation);
						Assert.assertTrue(!nestedClass);
					}
					if (depth == 2) {
						for (final TreeFillingConfiguration tmp : confs) {
							final PasteEObjectConfiguration pasteConf = tmp.getPasteConfiguration();
							Assert.assertNotNull("We don't have paste configuration for a TreeFillingConfiguration, depth=" + tmp.getDepth(), pasteConf); //$NON-NLS-1$

							Assert.assertTrue(confs.size() == 1);
							Assert.assertTrue(PARAMETER_ELEMENT_ID.equals(pasteConf.getPastedElementId()));
						}
					}
				}
			}
		}
	}

	/**
	 *
	 * @throws Exception
	 *
	 *             This method tests
	 *             <ul>
	 *             <li>the open of the table</li>
	 *             <li>the paste of the table (must be enabled)</li>
	 *             <li>the new contents of the semantic model</li>
	 *             <li>the expand (indirectly)</li>
	 *             <li>the final contents of the table (compared to the pasted string)</li>
	 *             </ul>
	 */
	@Test
	public void testPaste() throws Exception {
		testOpenExistingTable("classTreeTable", " openTest"); //$NON-NLS-1$ //$NON-NLS-2$
		final IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		final INattableModelManager manager = tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(manager instanceof ITreeNattableModelManager);

		final List<?> rowElements = manager.getRowElementsList();
		final int size = rowElements.size();
		Assert.assertEquals(0, size);

		// fill the clipboard
		final String fileName = getPasteFileName();
		final String str = FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName);
		fillClipboard("Fill the clipboard to enable the handler"); //$NON-NLS-1$

		final Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put(AbstractPasteInsertInTableHandler.OPEN_DIALOG_ON_FAIL_BOOLEAN_PARAMETER, Boolean.FALSE);
		parameters.put(AbstractPasteInsertInTableHandler.OPEN__PROGRESS_MONITOR_DIALOG, Boolean.FALSE);
		// This parameters allows to set the text to paste instead of copy/paste it programmatically (this may be overwrite by other copy)
		parameters.put(PasteInTableHandler.TEXT_TO_PASTE, str);
		flushDisplayEvents();
		final Object res = PasteInsertUtil.paste(manager, ((TreeNattableModelManager)manager).getSelectionInTable(), parameters);
		Assert.assertTrue(res instanceof IStatus);
		final IStatus iStatus = (IStatus) res;
		validateReturnedStatus(iStatus);
		if (iStatus.isOK()) {
			verifyModelContents();
			checkTableDataStructure();

			// TODO : activate me checkCopyToClipboard(str);

			// Check the undo and the redo
			checkUndo_Redo(manager);

			// we close the table, we re-open it and we check that is contains is correct!
			testClose_Open();
		}
	}


	protected void testClose_Open() throws Exception {
		final Command cmd = EclipseCommandUtils.getCommandService().getCommand("org.eclipse.ui.file.save"); //$NON-NLS-1$
		cmd.executeWithChecks(new ExecutionEvent());

		this.editor.getEditorSite().getPage().closeEditor(editor, false);

		editor = EditorUtils.openPapyrusEditor(diModelFile);

		final IPageManager pageManager = editor.getServicesRegistry().getService(IPageManager.class);
		Assert.assertEquals(1, pageManager.allPages().size());
		IEditorPart tableEditor = editor.getActiveEditor();
		// the editor has been saved, so the table is already opened when we re open the model
		// Assert.assertNull(tableEditor);
		final Resource notationResource = NotationUtils.getNotationModel(editor.getServicesRegistry().getService(ModelSet.class)).getResource();
		final Table table = (Table) notationResource.getContents().get(0);
		final TransactionalEditingDomain editingDomain = editor.getServicesRegistry().getService(TransactionalEditingDomain.class);
		editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(new OpenDiagramCommand(editingDomain, table)));
		// to refresh the table content
		// while(!Display.getDefault().isDisposed() && Display.getDefault().readAndDispatch());
		tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);


		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		final INattableModelManager manager = tableEditor.getAdapter(INattableModelManager.class);
		Assert.assertTrue(manager instanceof ITreeNattableModelManager);


		checkTableDataStructure();

	}

	/**
	 * This allows to check the undo and the redo.
	 *
	 * @param manager
	 *            the nattable model manager.
	 * @throws Exception
	 *             The exception.
	 */
	protected void checkUndo_Redo(final INattableModelManager manager) throws Exception {

		((ITreeNattableModelManager) manager).doCollapseExpandAction(CollapseAndExpandActionsEnum.COLLAPSE_ALL, null);

		// Execute the undo and check the table content
		final EditingDomainUndoContext undoContext = new EditingDomainUndoContext(getTransactionalEditingDomain());
		getTransactionalEditingDomain().getCommandStack().undo();
		CheckedOperationHistory.getInstance().undo(undoContext, null, null);

		flushDisplayEvents();

		final List<?> rowElements = manager.getRowElementsList();
		final int size = rowElements.size();
		Assert.assertEquals(0, size);

		// Execute the redo and check the table content
		CheckedOperationHistory.getInstance().redo(undoContext, null, null);
		getTransactionalEditingDomain().getCommandStack().redo();
		flushDisplayEvents();

		verifyModelContents();
		checkTableDataStructure();
	}

	protected void verifyModel_1_1_1() throws ServiceException {
		final EObject context = getTable().getContext();
		Assert.assertTrue(context instanceof Model);
		final Model pack = (Model) context;
		final List<NamedElement> members = pack.getMembers();
		Assert.assertEquals(2, members.size());
		for (int i = 0; i < members.size(); i++) {
			final NamedElement tmp = members.get(i);
			Assert.assertTrue(tmp instanceof Class);
			final Class clazz = (Class) tmp;
			final StringBuilder className = new StringBuilder(CLASS_BASE_NAME);
			className.append(i);
			Assert.assertEquals(className.toString(), clazz.getName());
			Assert.assertEquals(3, clazz.getOwnedOperations().size());
			for (int j = 0; j < clazz.getOwnedOperations().size(); j++) {
				final Operation prop = clazz.getOwnedOperations().get(j);
				final StringBuilder propName = new StringBuilder(OPERATION_BASE_NAME);
				propName.append(j);
				Assert.assertEquals(propName.toString(), prop.getName());
			}
		}
	}


	protected void verifyModel_1_3_1() throws ServiceException {
		final EObject context = getTable().getContext();
		Assert.assertTrue(context instanceof Model);
		final Model pack = (Model) context;
		final List<NamedElement> members = pack.getMembers();
		Assert.assertEquals(2, members.size());
		for (int i = 0; i < members.size(); i++) {
			final NamedElement tmp = members.get(i);
			Assert.assertTrue(tmp instanceof Class);
			final Class clazz = (Class) tmp;
			final StringBuilder className = new StringBuilder(CLASS_BASE_NAME);
			className.append(i);
			Assert.assertEquals(className.toString(), clazz.getName());
			Assert.assertEquals(3, clazz.getOwnedAttributes().size());
			for (int j = 0; j < clazz.getOwnedAttributes().size(); j++) {
				final Property prop = clazz.getOwnedAttributes().get(j);
				final StringBuilder propName = new StringBuilder(PROPERTY_BASE_NAME);
				propName.append(j);
				Assert.assertEquals(propName.toString(), prop.getName());
			}
			final List<Operation> operations = clazz.getOperations();
			Assert.assertEquals(3, operations.size());
			for (int k = 0; k < operations.size(); k++) {
				final Operation op = operations.get(k);
				final StringBuilder operationName = new StringBuilder(OPERATION_BASE_NAME);
				operationName.append(k);
				Assert.assertEquals(operationName.toString(), op.getName());

				final List<Parameter> params = op.getOwnedParameters();
				Assert.assertEquals(3, params.size());

				for (int l = 0; l < params.size(); l++) {
					final Parameter param = params.get(l);
					final StringBuilder paramName = new StringBuilder(PARAMETER_BASE_NAME);
					paramName.append(l);
					Assert.assertEquals(paramName.toString(), param.getName());
				}
			}

			final List<Classifier> nestedClasses = clazz.getNestedClassifiers();
			Assert.assertEquals(3, nestedClasses.size());
			for (int l = 0; l < nestedClasses.size(); l++) {
				final Classifier classifier = nestedClasses.get(l);
				final StringBuilder nestedClassName = new StringBuilder(NESTED_CLASS_BASE_NAME);
				nestedClassName.append(l);
				Assert.assertEquals(nestedClassName.toString(), classifier.getName());
			}
		}
	}

	/**
	 * This allows to check the returned status of the paste action.
	 *
	 * @param status
	 *            The status got.
	 */
	protected void validateReturnedStatus(final IStatus status) {
		String className = getClass().getSimpleName();
		className = className.replaceFirst("PasteWithCategories_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 6);
		final String depth1 = result[1];
		if (depth1.startsWith("H") && depth1.endsWith("3")) { //$NON-NLS-1$ //$NON-NLS-2$
			Assert.assertTrue(!status.isOK());
			final int errorCode = status.getCode();
			Assert.assertEquals(PasteSeverityCode.PASTE_ERROR__MORE_THAN_ONE_CATEGORY_FOR_A_HIDDEN_DEPTH, errorCode);
		} else {
			Assert.assertTrue(status.isOK());
		}
	}

	/**
	 * This method verifies than the contents of the model is conform to the wanted result
	 *
	 * @throws Exception
	 */
	protected final void verifyModelContents() throws Exception {
		String className = getClass().getSimpleName();
		className = className.replaceFirst("PasteWithCategories_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 6);
		final String depth1 = result[1];
		if (depth1.endsWith("3")) { //$NON-NLS-1$
			verifyModel_1_3_1();
		} else if (depth1.endsWith("1")) { //$NON-NLS-1$
			verifyModel_1_1_1();
		} else {
			throw new Exception("We have an error in the tests"); //$NON-NLS-1$
		}
	}

	/**
	 *
	 * @param newClipBoardContents
	 */
	protected void fillClipboard(final String newClipBoardContents) {

		// its seems that the clipboard must be filled with the same way than we read it!
		final java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

		// if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
		final StringSelection s = new StringSelection(newClipBoardContents);
		clipboard.setContents(s, s);
		// }
	}

	protected void checkRootClasses(final ITreeItemAxis root, final EObject parent) throws Exception {
		final Object tmp = AxisUtils.getRepresentedElement(root);
		Assert.assertTrue(tmp instanceof Class);
		final Class clazz = (Class) tmp;
		Assert.assertEquals(parent, clazz.eContainer());
		checkChildrenClasses(root);
	}

	protected void checkSynchronizedRoot(final TreeList<?> treeList) throws Exception {
		final List<?> roots = treeList.getRoots();
		Assert.assertEquals(1, roots.size());
		for (int i = 0; i < roots.size(); i++) {
			// we check the first level
			Object tmp = roots.get(i);
			if (tmp instanceof TreeList.Node<?>) {
				final Node<?> node = (Node<?>) tmp;
				tmp = node.getElement();
			}
			Assert.assertTrue(tmp instanceof EObjectTreeItemAxis);
			final EObjectTreeItemAxis root = (EObjectTreeItemAxis) tmp;
			tmp = AxisUtils.getRepresentedElement(root);
			Assert.assertTrue(tmp instanceof TreeFillingConfiguration);
			final TreeFillingConfiguration fillingConf = (TreeFillingConfiguration) tmp;
			tmp = AxisUtils.getRepresentedElement(fillingConf.getAxisUsedAsAxisProvider());
			Assert.assertEquals(UMLPackage.eINSTANCE.getPackage_PackagedElement(), tmp);

			// we check the children : 2 classes
			final List<ITreeItemAxis> classes = root.getChildren();
			Assert.assertEquals(2, classes.size());
			for (final Object current : classes) {
				Assert.assertTrue(current instanceof ITreeItemAxis);
				final ITreeItemAxis axis = (ITreeItemAxis) current;
				checkRootClasses(axis, getTable().getContext());
			}
		}
	}

	protected void checkDnDRoot(final TreeList<?> treeList) throws Exception {
		final List<?> roots = treeList.getRoots();
		Assert.assertEquals(2, roots.size());
		for (int i = 0; i < roots.size(); i++) {
			Object tmp = roots.get(i);
			if (tmp instanceof TreeList.Node<?>) {
				final Node<?> node = (Node<?>) tmp;
				tmp = node.getElement();
			}
			Assert.assertTrue(tmp instanceof ITreeItemAxis);
			final ITreeItemAxis axis = (ITreeItemAxis) tmp;
			checkRootClasses(axis, getTable().getContext());
		}
	}

	public void checkTableDataStructure() throws Exception {
		final INattableModelManager manager = this.editor.getAdapter(INattableModelManager.class);
		flushDisplayEvents();
		// boolean tmp = true;
		// while (tmp) {
		// try {
		// tmp = Display.getDefault().readAndDispatch();
		// } catch (Exception e) {
		// Activator.log.error(e);
		// }
		// }
		// Display.getDefault().asyncExec(new Runnable() {

		// @Override
		// public void run() {
		
		((ITreeNattableModelManager) manager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
		// }

		flushDisplayEvents();
		// });
		// tmp = true;
		// while (tmp) {
		// try {
		// tmp = Display.getDefault().readAndDispatch();
		// } catch (Exception e) {
		// Activator.log.error(e);
		// }
		// }
		final List<?> elements = manager.getRowElementsList();
		Assert.assertTrue(elements instanceof TreeList<?>);
		String className = getClass().getSimpleName();
		className = className.replaceFirst("PasteWithCategories_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 6);
		final String first = result[0];
		if ("Empty".equals(first)) { //$NON-NLS-1$
			checkDnDRoot((TreeList<?>) elements);
		} else {
			checkSynchronizedRoot((TreeList<?>) elements);
		}
	}



	protected void checkChildrenClasses(final ITreeItemAxis axisRepresentingClass) throws Exception {
		final List<ITreeItemAxis> axis = axisRepresentingClass.getChildren();
		// Assert.assertEquals(3, axis.size());
		ITreeItemAxis propertyAxis = null;
		ITreeItemAxis operationAxis = null;
		ITreeItemAxis nestedClassAxis = null;
		final Class clazz = (Class) AxisUtils.getRepresentedElement(axisRepresentingClass);
		for (final ITreeItemAxis current : axis) {
			Object tmp = AxisUtils.getRepresentedElement(current);
			Assert.assertTrue(tmp instanceof TreeFillingConfiguration);
			tmp = AxisUtils.getRepresentedElement(((TreeFillingConfiguration) tmp).getAxisUsedAsAxisProvider());
			if (tmp == UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute()) {
				// property = true;
				propertyAxis = current;
				checkPropertyAxis(propertyAxis, clazz);
			}
			if (tmp == UMLPackage.eINSTANCE.getClass_OwnedOperation()) {
				// operation = true;
				operationAxis = current;
				checkOperationAxis(operationAxis, clazz);
			}
			if (tmp == UMLPackage.eINSTANCE.getClass_NestedClassifier()) {
				nestedClassAxis = current;
				checkNestedClassAxis(nestedClassAxis, clazz);
				// nestedClass = true;
			}
		}

		String className = getClass().getSimpleName();
		className = className.replaceFirst("PasteWithCategories_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 6);
		final String depth1 = result[1];
		if (depth1.endsWith("3")) { //$NON-NLS-1$
			Assert.assertNotNull(propertyAxis);
			Assert.assertNotNull(nestedClassAxis);
			Assert.assertNotNull(operationAxis);

		} else if (depth1.endsWith("1")) { //$NON-NLS-1$
			Assert.assertTrue(propertyAxis == null);
			Assert.assertTrue(nestedClassAxis == null);
			Assert.assertNotNull(operationAxis);
		} else {
			throw new Exception("We have an error in the tests"); //$NON-NLS-1$
		}
	}

	public void checkPropertyAxis(final ITreeItemAxis propertyAxis, final Class parent) {
		final List<ITreeItemAxis> axis = propertyAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		final Set<Object> properties = new HashSet<Object>();
		for (final ITreeItemAxis tmp : axis) {
			Assert.assertEquals(0, tmp.getChildren().size());
			final Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Property);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			properties.add(representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, properties.size());
	}

	public void checkNestedClassAxis(final ITreeItemAxis nestedClassAxis, final Class parent) {
		final List<ITreeItemAxis> axis = nestedClassAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		final Set<Object> nestedClasses = new HashSet<Object>();
		for (final ITreeItemAxis tmp : axis) {
			Assert.assertEquals(0, tmp.getChildren().size());
			final Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Class);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			nestedClasses.add(representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, nestedClasses.size());

	}

	public void checkOperationAxis(final ITreeItemAxis operationAxis, final Class parent) {
		final List<ITreeItemAxis> axis = operationAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		final Set<Object> operation = new HashSet<Object>();
		for (final ITreeItemAxis tmp : axis) {
			Assert.assertEquals(1, tmp.getChildren().size());
			final ITreeItemAxis parameterAxis = tmp.getChildren().get(0);
			final Object rep = AxisUtils.getRepresentedElement(parameterAxis);
			Assert.assertTrue(rep instanceof TreeFillingConfiguration);
			Assert.assertEquals(UMLPackage.eINSTANCE.getBehavioralFeature_OwnedParameter(), AxisUtils.getRepresentedElement(((TreeFillingConfiguration) rep).getAxisUsedAsAxisProvider()));
			final Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Operation);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			operation.add(representedElement);
			checkParameterAxis(parameterAxis, (Operation) representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, operation.size());
	}


	public void checkParameterAxis(final ITreeItemAxis parameterAxis, final Operation parent) {
		final List<ITreeItemAxis> axis = parameterAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		final Set<Object> parameters = new HashSet<Object>();
		for (final ITreeItemAxis tmp : axis) {
			Assert.assertEquals(0, tmp.getChildren().size());
			final Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Parameter);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			parameters.add(representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, parameters.size());
	}

	// public void verifyTableContents_1_1_1(List<?> elements) {
	// Assert.assertEquals(1, elements.size());
	// }

	/**
	 * This method verify the general appearance of the table, currently it doesn't work, because the tree header are not included in the clipboard
	 *
	 * @param pastedString
	 * @throws Exception
	 */
	protected void checkCopyToClipboard(final String pastedString) throws Exception {

		if (true) {// TODO, fixme
			return;
		}
		final INattableModelManager manager = this.editor.getAdapter(INattableModelManager.class);

		boolean tmp = true;
		while (tmp) {
			try {
				tmp = Display.getDefault().readAndDispatch();
			} catch (final Exception e) {
				Activator.log.error(e);
			}
		}
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				
				((ITreeNattableModelManager) manager).doCollapseExpandAction(CollapseAndExpandActionsEnum.EXPAND_ALL, null);
			}
		});
		while (tmp) {
			try {
				tmp = Display.getDefault().readAndDispatch();
			} catch (final Exception e) {
				Activator.log.error(e);
			}
		}
		final List<?> elements = manager.getRowElementsList();
		Assert.assertTrue(" the list managing the rows in not a TreeList", elements instanceof TreeList<?>); //$NON-NLS-1$
		Assert.assertEquals(2, elements.size());
		String className = getClass().getSimpleName();
		className = className.replaceFirst("PasteWithCategories_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		final String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 6);
		final String depth1 = result[1];
		// if (depth1.endsWith("3")) { //$NON-NLS-1$
		// // verifyTableContents_1_3_1(elements);
		// } else if (depth1.endsWith("1")) { //$NON-NLS-1$
		// verifyTableContents_1_1_1(elements);
		// } else {
		// throw new Exception("We have an error in the tests"); //$NON-NLS-1$
		// }

		// TODO N
		// Assert.assertEquals(11, elements.size());

		manager.selectAll();
		((AbstractNattableWidgetManager) manager).copyToClipboard();

		final String clipboardContents = TableClipboardUtils.getClipboardContentsAsString();
		final String fileName = getPasteFileName();

		// String pastedContents = FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID,get filePath, fileNameWithExtension)
		int i = 0;
		i++;

		final StringReader clipboardReader = new StringReader(clipboardContents);
		final StringReader fileReader = new StringReader(pastedString);
		final CSVPasteHelper helperClipboard = new CSVPasteHelper();
		final CSVPasteHelper helperfile = new CSVPasteHelper();
		final CSVParser clipboardParser = helperClipboard.createParser(clipboardReader);
		final CSVParser fileParser = helperfile.createParser(fileReader);
		final RowIterator clipboardRowIter = clipboardParser.parse();
		final RowIterator fileRowIter = fileParser.parse();
		// doesn't work because tree header are not in the clipboard
		while (clipboardRowIter.hasNext()) {
			Assert.assertEquals(fileRowIter.hasNext(), clipboardRowIter.hasNext());
			final CellIterator fileCellIterator = fileRowIter.next();
			final CellIterator clipboardCellIterator = clipboardRowIter.next();
			while (clipboardCellIterator.hasNext()) {
				Assert.assertEquals(fileCellIterator.hasNext(), clipboardCellIterator.hasNext());
				final String origin = fileCellIterator.next();
				final String current = clipboardCellIterator.next();

				// contains and not equals, because due to label provider, it could be different
				Assert.assertTrue(current.contains(origin));
			}
			Assert.assertEquals(fileCellIterator.hasNext(), clipboardCellIterator.hasNext());
		}
		Assert.assertEquals(fileRowIter.hasNext(), clipboardRowIter.hasNext());
	}

	/**
	 *
	 * @return
	 * 		the name of the paste file to use
	 */
	protected String getPasteFileName() {
		final StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append(FileUtils.DOT_STRING);
		builder.append(FileUtils.TEXT_EXTENSION);
		return builder.toString();
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

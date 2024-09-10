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

package org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.paste.without.service.edit;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.handler.PasteInTableHandler;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.EObjectTreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.IPasteConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TreeFillingConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.AxisConfigurationUtils;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.PasteInsertUtil;
import org.eclipse.papyrus.infra.tools.util.FileUtils;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.utils.EditorUtils;
import org.eclipse.papyrus.junit.utils.GenericUtils;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.ProjectUtils;
import org.eclipse.papyrus.uml.nattable.stereotyped.elements.tests.Activator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractPasteTests extends AbstractOpenTableTest {

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


	@Before
	public void initModel() throws Exception {
		initModel("requirementTable", getClass().getSimpleName(), getBundle()); //$NON-NLS-1$
	};

	/**
	 * Inits this.editor
	 * Fails or throws an exception if an error occurs
	 *
	 * @param bundle
	 *            the source bundle where the model is store
	 * @param projectName
	 *            the project that will created at runtime to execute test
	 * @param modelName
	 *            the model that will be copied and test executed on.
	 */
	protected void initModel(String projectName, String modelName, Bundle bundle) throws Exception {
		project = ProjectUtils.createProject(projectName);
		final IFile project1 = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), "library"); //$NON-NLS-1$
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(project1);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		final IFile porject2 = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), "model.profile"); //$NON-NLS-1$
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(porject2);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		this.diModelFile = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), modelName);
		Display.getDefault().syncExec(new Runnable() {

			public void run() {
				try {
					editor = EditorUtils.openPapyrusEditor(diModelFile);
				} catch (Exception ex) {
					Activator.log.error(ex);
					Assert.fail(ex.getMessage());
				}
			}
		});

		Assert.assertNotNull(editor);
	}


	protected INattableModelManager getTableManager() {
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		return manager;
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void checkModelForTestConsistency() throws Exception {
		Assert.assertTrue(!getTable().isInvertAxis());
		Assert.assertEquals("PapyrusDynamicRequirementTable", getTable().getTableConfiguration().getType());
		Assert.assertEquals(6, getTable().getCurrentColumnAxisProvider().getAxis().size());
		final IPasteConfiguration conf = (IPasteConfiguration) AxisConfigurationUtils.getIAxisConfigurationUsedInTable(getTable(), NattableaxisconfigurationPackage.eINSTANCE.getPasteEObjectConfiguration(), false);
		Assert.assertTrue(conf.isDetachedMode());
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
		IEditorPart tableEditor = editor.getActiveEditor();
		Assert.assertTrue(tableEditor instanceof NatTableEditor);
		INattableModelManager manager = (INattableModelManager) tableEditor.getAdapter(INattableModelManager.class);
		// Assert.assertTrue(manager instanceof ITreeNattableModelManager);

		List<?> rowElements = manager.getRowElementsList();
		int size = rowElements.size();
		Assert.assertEquals(1, size);

		// fill the clipboard
		String fileName = getPasteFileName();
		String str = FileUtils.getStringFromPlatformFile(Activator.PLUGIN_ID, getSourcePath(), fileName);
		fillClipboard("Fill the clipboard to enable the handler"); //$NON-NLS-1$

		Map<Object, Object> parameters = new HashMap<Object, Object>();
		parameters.put(PasteInTableHandler.OPEN_DIALOG_ON_FAIL_BOOLEAN_PARAMETER, Boolean.FALSE);
		parameters.put(PasteInTableHandler.OPEN__PROGRESS_MONITOR_DIALOG, Boolean.FALSE);
		// This parameters allows to set the text to paste instead of copy/paste it programmatically (this may be overwrite by other copy)
		parameters.put(PasteInTableHandler.TEXT_TO_PASTE, str);
		flushDisplayEvents();
		Object res = PasteInsertUtil.paste(manager, ((NattableModelManager)manager).getSelectionInTable(), parameters);
		Assert.assertTrue(res instanceof IStatus);
		IStatus iStatus = (IStatus) res;
		validateReturnedStatus(iStatus);
		if (iStatus.isOK()) {
			verifyModelContents();
			checkTableDataStructure();
			checkCopyToClipboard(str);

			// Check the undo and the redo
			checkUndo_Redo(manager);
		}
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
		// Execute the undo and test the table
		getTransactionalEditingDomain().getCommandStack().undo();
		flushDisplayEvents();

		final List<?> rowElements = manager.getRowElementsList();
		final int size = rowElements.size();
		Assert.assertEquals(1, size);

		// Execute the redo and test the table content
		getTransactionalEditingDomain().getCommandStack().redo();
		flushDisplayEvents();

		verifyModelContents();
		checkTableDataStructure();
	}

	/**
	 * This allows to check the returned status of the command.
	 * 
	 * @param status
	 *            The status to check.
	 */
	protected void validateReturnedStatus(IStatus status) {
		Assert.assertTrue(status.isOK());
	}

	/**
	 * This method verifies than the contents of the model is conform to the wanted result
	 *
	 * @throws Exception
	 */
	protected final void verifyModelContents() throws Exception {
		// useful in detached mode to apply stereotype ?
		flushDisplayEvents();

		Package context = (Package) getTable().getContext();
		List<NamedElement> members = context.getOwnedMembers();
		Collection<Class> requirements = new HashSet<Class>();
		for (NamedElement member : members) {
			if (member instanceof Class) {
				checkClass((Class) member);
				requirements.add((Class) member);
			}
		}


		Assert.assertEquals(4, requirements.size());
	}

	public static final String REQUIREMENT1_NAME = "Requirement1"; //$NON-NLS-1$
	public static final String REQUIREMENT2_NAME = "Requirement2"; //$NON-NLS-1$
	public static final String REQUIREMENT3_NAME = "Requirement3"; //$NON-NLS-1$

	private boolean checkClassColor = false;

	@InvalidTest
	@Test
	public void testPasteCheckingColor() throws Exception {
		this.checkClassColor = true;
		testPaste();
	}

	protected void checkClass(Class clazz) throws Exception {
		String name = clazz.getName();

		Stereotype reqSte = clazz.getAppliedStereotype("SysML::Requirements::Requirement"); //$NON-NLS-1$
		Stereotype taskSte = clazz.getAppliedStereotype("profile::Task"); //$NON-NLS-1$
		Assert.assertNotNull(reqSte);
		Assert.assertNotNull(taskSte);
		String id = (String) clazz.getValue(reqSte, "id"); //$NON-NLS-1$
		String text = (String) clazz.getValue(reqSte, "text"); //$NON-NLS-1$
		Object priority = clazz.getValue(taskSte, "p"); //$NON-NLS-1$

		VisibilityKind visibility = clazz.getVisibility();
		Assert.assertNotNull(NLS.bind("priority is null for {0} ", clazz.getName()), priority);

		Assert.assertTrue(NLS.bind("Priority must be an instance of EnumerationLiteral, but it is an instance of {0}", priority.getClass().getName()), priority instanceof EnumerationLiteral);
		EnumerationLiteral priority2 = (EnumerationLiteral) priority;
		Object color = clazz.getValue(taskSte, "color"); //$NON-NLS-1$
		EnumerationLiteral color2 = null;
		if (this.checkClassColor) {
			Assert.assertTrue(NLS.bind("Color must be an instance of EnumerationLiteral, but it is an instance of {0}", color.getClass().getName()), color instanceof EnumerationLiteral);
			Assert.assertNotNull(NLS.bind("color is null for {0} ", clazz.getName()), color);
			color2 = (EnumerationLiteral) color;
		}
		if ("RequirementTest".equals(name)) { //$NON-NLS-1$
			// this is the initial requirement, available in the table
			Assert.assertTrue(id == null || id.isEmpty()); // $NON-NLS-1$
			Assert.assertTrue(text == null || text.isEmpty()); // $NON-NLS-1$
			Assert.assertEquals("medium", priority2.getName()); //$NON-NLS-1$
			if (this.checkClassColor) {
				Assert.assertEquals("red", color2.getName()); //$NON-NLS-1$
			}
			Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, visibility);
		} else if (REQUIREMENT1_NAME.equals(name)) {
			Assert.assertEquals("id1", id); //$NON-NLS-1$
			Assert.assertEquals("text1", text); //$NON-NLS-1$
			Assert.assertEquals("hight", priority2.getName()); //$NON-NLS-1$
			if (this.checkClassColor) {
				Assert.assertEquals("black", color2.getName()); //$NON-NLS-1$
			}
			Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, visibility);
		} else if (REQUIREMENT2_NAME.equals(name)) {
			Assert.assertEquals("id2", id); //$NON-NLS-1$
			Assert.assertEquals("text2", text); //$NON-NLS-1$
			Assert.assertEquals("medium", priority2.getName()); //$NON-NLS-1$
			if (this.checkClassColor) {
				Assert.assertEquals("red", color2.getName()); //$NON-NLS-1$
			}
		} else if (REQUIREMENT3_NAME.equals(name)) {
			Assert.assertEquals("id3", id); //$NON-NLS-1$
			Assert.assertEquals("text3", text); //$NON-NLS-1$
			Assert.assertEquals("low", priority2.getName()); //$NON-NLS-1$
			if (this.checkClassColor) {
				Assert.assertEquals("blue", color2.getName()); //$NON-NLS-1$
			}
		} else {
			throw new Exception("There is an error in the test"); //$NON-NLS-1$
		}
	}

	/**
	 *
	 * @param newClipBoardContents
	 */
	protected void fillClipboard(String newClipBoardContents) {

		// its seems that the clipboard must be filled with the same way than we read it!
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection s = new StringSelection(newClipBoardContents);
		clipboard.setContents(s, s);
	}

	protected void checkRootClasses(ITreeItemAxis root, EObject parent) {
		Object tmp = AxisUtils.getRepresentedElement(root);
		Assert.assertTrue(tmp instanceof Class);
		Class clazz = (Class) tmp;
		Assert.assertEquals(parent, clazz.eContainer());

	}

	protected void checkSynchronizedRoot(List<?> elements) throws ServiceException {
		Assert.assertEquals(1, elements.size());
		for (int i = 0; i < elements.size(); i++) {
			// we check the first level
			Object tmp = elements.get(0);
			Assert.assertTrue(tmp instanceof EObjectTreeItemAxis);
			EObjectTreeItemAxis root = (EObjectTreeItemAxis) tmp;
			tmp = AxisUtils.getRepresentedElement(root);
			Assert.assertTrue(tmp instanceof TreeFillingConfiguration);
			TreeFillingConfiguration fillingConf = (TreeFillingConfiguration) tmp;
			tmp = AxisUtils.getRepresentedElement(fillingConf.getAxisUsedAsAxisProvider());
			Assert.assertEquals(UMLPackage.eINSTANCE.getPackage_PackagedElement(), tmp);




			// we check the children : 2 classes
			List<ITreeItemAxis> classes = root.getChildren();
			Assert.assertEquals(2, classes.size());
			for (Object current : classes) {
				Assert.assertTrue(current instanceof ITreeItemAxis);
				ITreeItemAxis axis = (ITreeItemAxis) current;
				checkRootClasses(axis, getTable().getContext());
			}
		}
	}

	protected void checkDnDRoot(List<?> elements) throws ServiceException {
		Assert.assertEquals(2, elements.size());
		for (int i = 0; i < elements.size(); i++) {
			Object tmp = elements.get(i);
			Assert.assertTrue(tmp instanceof ITreeItemAxis);
			ITreeItemAxis axis = (ITreeItemAxis) tmp;
			checkRootClasses(axis, getTable().getContext());
		}
	}

	public void checkTableDataStructure() throws ServiceException {
		flushDisplayEvents();
		final INattableModelManager manager = (INattableModelManager) this.editor.getAdapter(INattableModelManager.class);
		List<Object> rows = manager.getRowElementsList();
		Assert.assertEquals(4, rows.size());
	}



	protected void checkChildrenClasses(ITreeItemAxis axisRepresentingClass) throws Exception {
		List<ITreeItemAxis> axis = axisRepresentingClass.getChildren();
		Assert.assertEquals(3, axis.size());
		ITreeItemAxis propertyAxis = null;
		ITreeItemAxis operationAxis = null;
		ITreeItemAxis nestedClassAxis = null;
		Class clazz = (Class) AxisUtils.getRepresentedElement(axisRepresentingClass);
		for (ITreeItemAxis current : axis) {
			Object tmp = AxisUtils.getRepresentedElement(current);
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
		String[] result = className.split("_"); //$NON-NLS-1$
		Assert.assertTrue(result.length == 5);
		String depth1 = result[1];
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

	public void checkPropertyAxis(ITreeItemAxis propertyAxis, Class parent) {
		List<ITreeItemAxis> axis = propertyAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		Set<Object> properties = new HashSet<Object>();
		for (ITreeItemAxis tmp : axis) {
			Assert.assertEquals(0, tmp.getChildren());
			Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Property);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			properties.add(representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, properties.size());
	}

	public void checkNestedClassAxis(ITreeItemAxis nestedClassAxis, Class parent) {
		List<ITreeItemAxis> axis = nestedClassAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		Set<Object> properties = new HashSet<Object>();
		for (ITreeItemAxis tmp : axis) {
			Assert.assertEquals(0, tmp.getChildren());
			Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Class);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			properties.add(representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, properties.size());

	}

	public void checkOperationAxis(ITreeItemAxis operationAxis, Class parent) {
		List<ITreeItemAxis> axis = operationAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		Set<Object> properties = new HashSet<Object>();
		for (ITreeItemAxis tmp : axis) {
			Assert.assertEquals(1, tmp.getChildren());
			ITreeItemAxis parameterAxis = tmp.getChildren().get(0);
			Assert.assertEquals(UMLPackage.eINSTANCE.getBehavioralFeature_OwnedParameter(), AxisUtils.getRepresentedElement(parameterAxis.getElement()));
			Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Operation);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			properties.add(representedElement);
			checkParameterAxis(parameterAxis, (Operation) representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, properties.size());
	}


	public void checkParameterAxis(ITreeItemAxis parameterAxis, Operation parent) {
		List<ITreeItemAxis> axis = parameterAxis.getChildren();
		Assert.assertEquals(3, axis.size());
		Set<Object> parameters = new HashSet<Object>();
		for (ITreeItemAxis tmp : axis) {
			Assert.assertEquals(0, tmp.getChildren());
			Object representedElement = AxisUtils.getRepresentedElement(tmp);
			Assert.assertTrue(representedElement instanceof Parameter);
			Assert.assertTrue(((EObject) representedElement).eContainer() == parent);
			parameters.add(representedElement);
		}
		// if not a property is marked several time has children of it parent itreeitemaxis
		Assert.assertEquals(3, parameters.size());
	}

	public void verifyTableContents_1_1_1(List<?> elements) {
		Assert.assertEquals(1, elements.size());
	}

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
	}

	/**
	 *
	 * @return
	 * 		the name of the paste file to use
	 */
	protected String getPasteFileName() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
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

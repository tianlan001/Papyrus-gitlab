package org.eclipse.papyrus.uml.diagram.activity.tests.canonical;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.emf.core.util.PackageUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.activity.tests.IActivityDiagramTestsConstants;
import org.eclipse.uml2.common.edit.command.SubsetAddCommand;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.AddVariableValueAction;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.edit.providers.ActivityItemProvider;
import org.eclipse.uml2.uml.edit.providers.UMLItemProviderAdapterFactory;
import org.junit.Assert;
import org.junit.Test;


public class TestItemProviderCommand extends AbstractPapyrusTestCase {

	@Override
	protected String getProjectName() {
		return IActivityDiagramTestsConstants.PROJECT_NAME;
	}

	@Override
	protected String getFileName() {
		return IActivityDiagramTestsConstants.FILE_NAME;
	}

	public DiagramUpdater getDiagramUpdater() {
		return UMLDiagramUpdater.INSTANCE;
	}

	@Test
	public void testItemProviderCommandUndo() {
		UMLItemProviderAdapterFactory itemProviderFactory = new UMLItemProviderAdapterFactory();
		testSubsetAddCommandForItemProvider((ActivityItemProvider) itemProviderFactory.createActivityAdapter());
	}

	private void testSubsetAddCommandForItemProvider(ActivityItemProvider itemProvider) {
		Activity owner = (Activity) getActivityCompartmentEditPart().resolveSemanticElement();
		AddVariableValueAction newElement = UMLFactory.eINSTANCE.createAddVariableValueAction();
		EReference containmentFeature = PackageUtil.findFeature(owner.eClass(), newElement.eClass());
		org.eclipse.emf.common.command.Command createCommand = getCreateCommand(itemProvider, owner, newElement, containmentFeature);
		Assert.assertTrue("Expected SubsetAddCommand not " + createCommand.getClass().getName(), createCommand instanceof SubsetAddCommand);
		SubsetAddCommand subsetAddCommand = (SubsetAddCommand) createCommand;
		assertEquals(CREATION + TEST_THE_UNDO, 0, getRootSemanticModel().getOwnedElements().size());
		executeOnUIThread(EMFtoGEFCommandWrapper.wrap(subsetAddCommand));
		Assert.assertTrue(subsetAddCommand.getCommandList().size() == 2);
		assertEquals(CREATION + TEST_THE_UNDO, 1, getRootSemanticModel().getOwnedElements().size());
		checkFeatures(owner, newElement, containmentFeature, UMLPackage.Literals.ACTIVITY__NODE, true);
		undoOnUIThread();
		checkFeatures(owner, newElement, containmentFeature, UMLPackage.Literals.ACTIVITY__NODE, false);
		assertEquals(CREATION + TEST_THE_UNDO, 0, getRootSemanticModel().getOwnedElements().size());
	}

	private void checkFeatures(EObject owner, EObject newElement, EStructuralFeature feature, EStructuralFeature supersetFeatures, boolean contains) {
		Assert.assertTrue(supersetFeatures.isMany());
		@SuppressWarnings("unchecked")
		EList<EObject> values = (EList<EObject>) owner.eGet(supersetFeatures);
		Assert.assertTrue(values.contains(newElement) == contains);
	}

	private org.eclipse.emf.common.command.Command getCreateCommand(ActivityItemProvider itemProvider, EObject owner, EObject newElement, EStructuralFeature feature) {
		// setup diagramEditor
		getDiagramEditPart();
		EditingDomain domain = diagramEditor.getEditingDomain();
		CommandParameter commandParameter = new CommandParameter(owner, feature, Collections.singleton(newElement), -1);
		org.eclipse.emf.common.command.Command createCommand = itemProvider.createCommand(owner, domain, AddCommand.class, commandParameter);
		Assert.assertNotNull("AddVariableValueAction can't be created.", createCommand);
		Assert.assertTrue("Expected executable creation command.", createCommand.canExecute());
		return createCommand;
	}
}

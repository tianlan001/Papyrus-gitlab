/*****************************************************************************
 * Copyright (c) 2009, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 434993
 *  Christian W. Damus (CEA) - bug 436047
 *  Christian W. Damus - bug 473183
 *  Christian W. Damus - bug 464647
 *  Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 530155
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.DiResourceSet;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Activator;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.diagram.common.commands.CreateUMLModelCommand;
import org.eclipse.papyrus.uml.diagram.common.part.UmlGmfDiagramEditor;
import org.eclipse.papyrus.uml.diagram.profile.CreateProfileModelCommand;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NameElementNamingStrategyPreferenceInitializer;
import org.eclipse.papyrus.uml.tools.utils.internal.preferences.NamedElementIndexNamingStrategyEnum;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * The Class AbstractPapyrusTestCase.
 */
public abstract class AbstractPapyrusTestCase extends AbstractPapyrusTest {

	@ClassRule
	public static final SharedTestSuiteState suiteState = new SharedTestSuiteState();

	@Rule
	public final HouseKeeper houseKeeper = suiteState.testState();

	protected boolean operationFailed = false;

	/** The Constant CREATION. */
	protected static final String CREATION = "CREATION:"; //$NON-NLS-1$

	/** The Constant CONTAINER_CREATION. */
	protected static final String CONTAINER_CREATION = "CONTAINER CREATION: "; //$NON-NLS-1$

	/** The Constant DROP. */
	protected static final String DROP = "DROP: "; //$NON-NLS-1$

	/** The Constant DESTROY_DELETION. */
	protected static final String DESTROY_DELETION = "DESTROY DELETION: "; //$NON-NLS-1$

	/** The Constant COMMAND_NULL. */
	protected static final String COMMAND_NULL = " command null"; //$NON-NLS-1$

	/** The Constant VIEW_DELETION. */
	protected static final String VIEW_DELETION = "VIEW DELETION: "; //$NON-NLS-1$

	/** The Constant TEST_THE_REDO. */
	protected static final String TEST_THE_REDO = "test the redo"; //$NON-NLS-1$

	/** The Constant TEST_THE_UNDO. */
	protected static final String TEST_THE_UNDO = "test the undo"; //$NON-NLS-1$

	/** The Constant TEST_THE_EXECUTION. */
	protected static final String TEST_THE_EXECUTION = "test the execution"; //$NON-NLS-1$

	/** The Constant TEST_IF_THE_COMMAND_CAN_BE_EXECUTED. */
	protected static final String TEST_IF_THE_COMMAND_CAN_BE_EXECUTED = "test if the command can be executed"; //$NON-NLS-1$

	/** The Constant TEST_IF_THE_COMMAND_IS_CREATED. */
	protected static final String TEST_IF_THE_COMMAND_IS_CREATED = "test if the command is created"; //$NON-NLS-1$

	/** The Constant INITIALIZATION_TEST. */
	protected static final String INITIALIZATION_TEST = "Intitial State"; //$NON-NLS-1$

	/** The Constant CHANGE_CONTAINER. */
	protected static final String CHANGE_CONTAINER = "CHANGE CONTAINER"; //$NON-NLS-1$

	/** The papyrus editor. */
	protected IMultiDiagramEditor papyrusEditor;

	/** The di resource set. */
	protected ModelSet diResourceSet;

	/** The project. */
	protected IProject project;

	/** The file. */
	protected IFile file;

	/** The diagram editor. */
	protected UmlGmfDiagramEditor diagramEditor = null;

	/** The diagram edit part. */
	protected DiagramEditPart diagramEditPart;



	/**
	 * @see junit.framework.TestCase#setUp()
	 *
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Set the preference to "true" for the dialog to display existing link instead of create a new one.
		final IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		final String alwaysCreateLinkPreferenceName = PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.RESTORE_LINK_ELEMENT);
		store.setValue(alwaysCreateLinkPreferenceName, true);
		store.setDefault(alwaysCreateLinkPreferenceName, true);

		// we set the default naming strategy, to preserve a previous test behavior
		org.eclipse.papyrus.uml.tools.utils.Activator.getDefault().getPreferenceStore().setValue(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION, NamedElementIndexNamingStrategyEnum.UNIQUE_INDEX_INITIALIZATION.getName());
		suiteState.setupTest();
	}

	void doSetUp() throws Exception {
		projectCreation();

	}

	/**
	 * Gets the root view.
	 *
	 * @return the root view
	 */
	protected View getRootView() {
		return getDiagramEditPart().getDiagramView();
	}

	/**
	 * Gets the root semantic model.
	 *
	 * @return the root semantic model
	 */
	protected Element getRootSemanticModel() {
		return (Element) getRootView().getElement();
	}

	/**
	 * @see junit.framework.TestCase#tearDown()
	 *
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		suiteState.teardownTest();

		// we reset the naming strategy to its initial value
		org.eclipse.papyrus.uml.tools.utils.Activator.getDefault().getPreferenceStore().setToDefault(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION);
	}

	void doTearDown() throws Exception {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// if the diagram is a Profile we dont save it because we dont need to define it
				if (diagramEditPart == null || !diagramEditPart.getDiagramView().getType().equals("PapyrusUMLProfileDiagram")) { //$NON-NLS-1$
					papyrusEditor.doSave(new NullProgressMonitor());
				}

				// diResourceSet.save( new NullProgressMonitor());
				if (diagramEditor != null) {
					diagramEditor.close(true);
				}
				papyrusEditor = null;
				diagramEditPart = null;
				diagramEditor = null;
			}
		};
		Display.getDefault().syncExec(runnable);
	}

	/**
	 * Gets the diagram edit part.
	 *
	 * @return the diagram edit part
	 */
	protected DiagramEditPart getDiagramEditPart() {
		if (diagramEditPart == null) {
			diagramEditor = (UmlGmfDiagramEditor) papyrusEditor.getActiveEditor();
			diagramEditPart = papyrusEditor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("Cannot find the diagram editor", diagramEditor); //$NON-NLS-1$
			Assert.assertNotNull("Cannot find the Diagram edit part", diagramEditPart); //$NON-NLS-1$
			StringValueStyle style = (StringValueStyle) diagramEditPart.getNotationView().getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), DiagramVersioningUtils.COMPATIBILITY_VERSION);
			Assert.assertNotNull("A version must be associated with every diagram", style); //$NON-NLS-1$
			Assert.assertTrue("The created diagram has not a good version", DiagramVersioningUtils.isOfCurrentPapyrusVersion((Diagram) diagramEditPart.getNotationView())); //$NON-NLS-1$
		}
		return diagramEditPart;
	}

	protected abstract ICreationCommand getDiagramCommandCreation();

	/**
	 * Returns the Project
	 */
	protected abstract String getProjectName();

	/**
	 * Returns the File
	 */
	protected abstract String getFileName();

	protected String[] getRequiredProfiles() {
		return new String[0];
	}

	/**
	 * Project creation.
	 */
	protected void projectCreation() throws Exception {
		// assert the intro is not visible
		Runnable closeIntroRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					IIntroPart introPart = PlatformUI.getWorkbench().getIntroManager().getIntro();
					if (introPart != null) {
						PlatformUI.getWorkbench().getIntroManager().closeIntro(introPart);
					}
				} catch (Exception ex) {
					ex.printStackTrace(System.out);
					Assert.fail(ex.getMessage());
				}
			}
		};
		Display.getDefault().syncExec(closeIntroRunnable);
		/*
		 * final String timestamp = Long.toString(System.currentTimeMillis());
		 *
		 * project = root.getProject("DiagramTestProject_" + timestamp); file =
		 * project.getFile("DiagramTest_" + timestamp + ".di"); //$NON-NLS-2$
		 */
		project = houseKeeper.createProject(getProjectName());
		file = project.getFile(getFileName());
		this.diResourceSet = houseKeeper.cleanUpLater(new DiResourceSet());
		// at this point, no resources have been created

		if (file.exists()) {
			file.delete(true, new NullProgressMonitor());
		}
		if (!file.exists()) {
			// Don't create a zero-byte file. Create an empty XMI document
			Resource diResource = diResourceSet.createResource(URI.createPlatformResourceURI(file.getFullPath().toString(), true));
			diResource.save(null);
			diResource.unload();
			diResourceSet.createsModels(file);
			if (!file.getName().endsWith(".profile.di")) { //$NON-NLS-1$

				new CreateUMLModelCommand().createModel(this.diResourceSet);

				ServicesRegistry registry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
				try {
					registry.add(ModelSet.class, Integer.MAX_VALUE, diResourceSet); // High priority to override all contributions
					registry.startRegistry();
				} catch (ServiceException ex) {
					// Ignore exceptions
				}

				// Apply the required profiles
				ArrayList<IFile> modifiedFiles = new ArrayList<IFile>();
				modifiedFiles.add(file);
				ICommand commandProfiles = new AbstractTransactionalCommand(diResourceSet.getTransactionalEditingDomain(), "Apply profiles", modifiedFiles) { //$NON-NLS-1$

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						UmlModel resModel = UmlUtils.getUmlModel(diResourceSet);
						EObject obj;
						try {
							obj = resModel.lookupRoot();
						} catch (NotFoundException e) {
							return CommandResult.newErrorCommandResult(e);
						}
						if (obj instanceof Model) {
							Model model = (Model) obj;
							for (String uri : getRequiredProfiles()) {
								EPackage definition = EPackage.Registry.INSTANCE.getEPackage(uri);
								if (definition != null) {
									Profile profile = UMLUtil.getProfile(definition, model);
									model.applyProfile(profile);
								}
							}
						}
						return CommandResult.newOKCommandResult();
					}
				};
				commandProfiles.execute(new NullProgressMonitor(), null);

			} else {
				new CreateProfileModelCommand().createModel(this.diResourceSet);
				ServicesRegistry registry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
				try {
					registry.add(ModelSet.class, Integer.MAX_VALUE, diResourceSet); // High priority to override all contributions
					registry.startRegistry();
				} catch (ServiceException ex) {
					// Ignore exceptions
				}
			}
			ICreationCommand command = getDiagramCommandCreation();
			command.createDiagram(diResourceSet, null, "DiagramToTest"); //$NON-NLS-1$
			diResourceSet.save(new NullProgressMonitor());
		}

		papyrusEditor = houseKeeper.openPapyrusEditor(file);
		Assert.assertNotNull("Failed to open the editor", papyrusEditor); //$NON-NLS-1$
	}

	/**
	 * Call {@link AbstractPapyrusTestCase#execute(Command) execute} on the UI
	 * thread.
	 */
	protected void executeOnUIThread(final Command command) {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				execute(command);
			}
		});
	}

	/** Call {@link AbstractPapyrusTestCase#undo() undo} on the UI thread. */
	protected void undoOnUIThread() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				undo();
			}
		});
	}

	/** Call {@link AbstractPapyrusTestCase#redo() redo} on the UI thread. */
	protected void redoOnUIThread() {
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				redo();
			}
		});
	}

	protected void assertLastOperationSuccessful() {
		Assert.assertFalse("The operation failed. Look at the log, or put a breakpoint on ExecutionException or DefaultOperationHistory#notifyNotOK to find the cause.", this.operationFailed); //$NON-NLS-1$
	}

	/**
	 * Reset the "operation failed" state. Call this before executing each
	 * operation for which you want to test whether if failed with {@link AbstractPapyrusTestCase#assertLastOperationSuccessful()}.
	 */
	protected void resetLastOperationFailedState() {
		this.operationFailed = false;
	}

	/** Execute the given command in the diagram editor. */
	protected void execute(final Command command) {
		resetLastOperationFailedState();
		getCommandStack().execute(GEFtoEMFCommandWrapper.wrap(command));
		assertLastOperationSuccessful();
	}

	/** Undo the last command done in the diagram editor. */
	protected void undo() {
		resetLastOperationFailedState();
		final CommandStack commandStack = getCommandStack();
		Assert.assertTrue("We should be able to undo", commandStack.canUndo()); //$NON-NLS-1$
		commandStack.undo();
		assertLastOperationSuccessful();
	}

	/** Redo the last command undone in the diagram editor. */
	protected void redo() {
		resetLastOperationFailedState();
		final CommandStack commandStack = getCommandStack();
		Assert.assertTrue("We should be able to redo", commandStack.canRedo()); //$NON-NLS-1$
		commandStack.redo();
		assertLastOperationSuccessful();
	}

	/** The command stack to use to execute commands on the diagram. */
	protected CommandStack getCommandStack() {
		// not "diagramEditor.getDiagramEditDomain().getDiagramCommandStack()"
		// because it messes up undo contexts
		return getEditingDomain().getCommandStack();
	}

	/**
	 * Create a node, if necessary. It will not be necessary if the node we are trying implicitly
	 * exists because it's the behavior frame of a behavior diagram.
	 */
	public GraphicalEditPart createNodeOptionally(IElementType type, final IGraphicalEditPart containerEditPart) {

		GraphicalEditPart result = null;

		if (UMLPackage.Literals.BEHAVIOR.isSuperTypeOf(type.getEClass()) && (containerEditPart instanceof DiagramEditPart)) {
			// Look for the existing behavior frame
			result = (GraphicalEditPart) containerEditPart.getChildBySemanticHint(((IHintedType) type).getSemanticHint());
		}

		if (result == null) {
			// CREATION
			final Command[] command = { null };
			final CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, containerEditPart.getDiagramPreferencesHint());
			Display.getDefault().syncExec(new Runnable() {

				@Override
				public void run() {
					command[0] = containerEditPart.getCommand(requestcreation);
				}
			});
			assertNotNull("the command must not be null", command[0]);
			assertTrue("the command must be executable", command[0].canExecute()); //$NON-NLS-1$
			// execute the creation
			executeOnUIThread(command[0]);

			result = (GraphicalEditPart) containerEditPart.getChildren().get((containerEditPart.getChildren().size() - 1));
		}

		Assert.assertNotNull("The editpart must be created", result); //$NON-NLS-1$
		return result;
	}

	protected TransactionalEditingDomain getEditingDomain() {
		return diagramEditor.getEditingDomain();
	}
}

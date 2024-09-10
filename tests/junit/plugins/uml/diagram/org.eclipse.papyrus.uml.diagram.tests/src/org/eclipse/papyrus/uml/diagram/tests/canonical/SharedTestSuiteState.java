/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.tests.canonical;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.ICreationCommand;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.uml.architecture.UMLArchitectureContextIds;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.utils.CustomUMLUtil;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

/**
 * <p>
 * Shared state for the tests in a generated test suite ("test context"). Manages
 * a single diagram editor to be shared by all of the test cases, with clean-up
 * after each test to allow it to be safely reused by the next.
 * </p>
 * <p>
 * This behavior is factored out as a test rule so as to make as little impact as possible
 * on the structure of the base test framework, which is shared with the non-generated tests.
 * </p>
 */
@StateNotShareable
public class SharedTestSuiteState implements TestRule {
	private final HouseKeeper houseKeeper = new HouseKeeper();

	private boolean canShareState;

	private AbstractPapyrusTestCase testCase;

	private ModelSet modelSet;
	private IProject project;
	private IFile file;
	private IMultiDiagramEditor papyrusEditor;
	private View diagram;
	private List<EObject> preserve;
	private DiagramEditPart diagramEditPart;

	public SharedTestSuiteState() {
		super();
	}

	@Override
	public Statement apply(Statement base, Description description) {
		Statement result;

		if (description.isSuite()) {
			result = applyToTestContext(base, description);
		} else {
			fail("GeneratedTestSuiteStateRule may only be used with @ClassRule");
			result = null; // Unreachable
		}

		return result;
	}

	public HouseKeeper testState() {
		return new HouseKeeper() {

			@Override
			public Statement apply(final Statement base, FrameworkMethod method, final Object target) {
				Statement result;

				if (canShareState) {
					result = applyToTestCase(base, method, target);
				} else {
					result = new Statement() {

						@Override
						public void evaluate() throws Throwable {
							testCase = (AbstractPapyrusTestCase) target;
							try {
								base.evaluate();
							} finally {
								testCase = null;
							}
						}
					};
				}

				return super.apply(result, method, target);
			}
		};
	}

	public IProject getProject() {
		return project;
	}

	protected Statement applyToTestContext(final Statement base, Description description) {
		canShareState = JUnitUtils.getAnnotation(description, StateNotShareable.class) == null;

		return !canShareState
				? base
						: houseKeeper.apply(base, method("apply", Statement.class, Description.class), this); //$NON-NLS-1$
	}

	private FrameworkMethod method(String name, Class<?>... parameterType) {
		try {
			return new FrameworkMethod(SharedTestSuiteState.class.getMethod(name, parameterType));
		} catch (Exception e) {
			fail(e.getMessage());
			return null; // Unreachable
		}
	}

	protected void createProject() throws Exception {
		// Ensure that the intro is not visible
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

		// Make sure it is nullified for the next test context
		houseKeeper.setField("project", houseKeeper.createProject(testCase.getProjectName())); //$NON-NLS-1$
		houseKeeper.setField("file", project.getFile(testCase.getFileName())); //$NON-NLS-1$

		@SuppressWarnings("deprecation")
		ModelSet _modelSet = houseKeeper.cleanUpLater(new org.eclipse.papyrus.infra.core.utils.DiResourceSet());
		modelSet = _modelSet;
		// at this point, no resources have been created

		if (file.exists()) {
			file.delete(true, null);
		}
		if (!file.exists()) {
			URI fileURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			modelSet.createModels(fileURI);

			final String contextId;
			if (fileURI.lastSegment().matches(".*\\.profile\\.(di|uml)"))
				contextId = UMLArchitectureContextIds.Profile;
			else
				contextId = UMLArchitectureContextIds.UML;

			final List<String> viewpointIds = new ArrayList<String>();
			MergedArchitectureContext context = ArchitectureDomainManager.getInstance().getArchitectureContextById(contextId);
			for (MergedArchitectureViewpoint viewpoint : context.getViewpoints()) {
				viewpointIds.add(viewpoint.getId());
			}

			ArchitectureDescriptionUtils utils = new ArchitectureDescriptionUtils(modelSet);
			Command c = utils.createNewModel(contextId, viewpointIds.toArray(new String[0]));

			TransactionalEditingDomain ted = modelSet.getTransactionalEditingDomain();
			ted.getCommandStack().execute(c);

			ServicesRegistry registry = new ExtensionServicesRegistry(org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);
			try {
				registry.add(ModelSet.class, Integer.MAX_VALUE, modelSet); // High priority to override all contributions
				registry.startRegistry();
			} catch (ServiceException ex) {
				// Ignore exceptions
			}
		}
	}

	protected void createDiagramEditor() throws Exception {
		// If we're not the first test in the suite, there will already be a diagram
		if (diagram == null) {
			// First, create the diagram
			ICreationCommand command = testCase.getDiagramCommandCreation();
			command.createDiagram(modelSet, null, "DiagramToTest"); //$NON-NLS-1$

			// Must save the model so that the editor may open it
			modelSet.save(new NullProgressMonitor());

			// Forget this resource set; we'll get the editor's to replace it
			modelSet.unload();

			// Then, open the Papyrus editor
			papyrusEditor = houseKeeper.openPapyrusEditor(file);
			Assert.assertNotNull("Failed to open the editor", papyrusEditor); //$NON-NLS-1$

			// Get the new resource set from this editor
			modelSet = papyrusEditor.getServicesRegistry().getService(ModelSet.class);

			// And get the diagram
			diagram = getDiagramEditPart().getDiagramView();

			// Whatever the model and diagram are initialized with, don't destroy any of it
			ImmutableList.Builder<EObject> preserve = ImmutableList.builder();
			preserve.addAll(diagram.eResource().getAllContents());

			UmlModel uml = (UmlModel) modelSet.getModel(UmlModel.MODEL_ID);
			preserve.addAll(uml.getResource().getAllContents());

			houseKeeper.setField("preserve", preserve.build());
		}
	}

	/**
	 * Obtains the root model element of the test suite, that should be preserved when cleaning up
	 * each individual test case for reuse by the next. For example, in the case of behavior diagrams,
	 * this would be the behavior, itself.
	 * 
	 * @param testModel
	 *            the root package of the test model, which is shared by all test cases anyways
	 * 
	 * @return the root model element to share amongst test cases, or {@code null} if none
	 *         (just preserve the test model root package, then)
	 */
	protected EObject getRootModelElement(Package testModel) {
		return (EObject) EcoreUtil.getObjectByType(testModel.eContents(), UMLPackage.Literals.BEHAVIOR);
	}

	/**
	 * Gets the diagram edit part.
	 *
	 * @return the diagram edit part
	 */
	protected DiagramEditPart getDiagramEditPart() {
		if (diagramEditPart == null) {
			IEditorPart diagramEditor = papyrusEditor.getActiveEditor();
			diagramEditPart = papyrusEditor.getAdapter(DiagramEditPart.class);
			Assert.assertNotNull("Cannot find the diagram editor", diagramEditor); //$NON-NLS-1$
			Assert.assertNotNull("Cannot find the Diagram edit part", diagramEditPart); //$NON-NLS-1$
			StringValueStyle style = (StringValueStyle) diagramEditPart.getNotationView().getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), DiagramVersioningUtils.COMPATIBILITY_VERSION);
			Assert.assertNotNull("A version must be associated with every diagram", style); //$NON-NLS-1$
			Assert.assertTrue("The created diagram has not a good version", DiagramVersioningUtils.isOfCurrentPapyrusVersion((Diagram) diagramEditPart.getNotationView())); //$NON-NLS-1$
		}
		return diagramEditPart;
	}

	protected Statement applyToTestCase(final Statement base, FrameworkMethod method, final Object testCase) {
		return !canShareState ? base : new Statement() {

			@Override
			public void evaluate() throws Throwable {
				SharedTestSuiteState.this.testCase = (AbstractPapyrusTestCase) testCase;

				if (project == null) {
					createProject();
				}
				createDiagramEditor();
				setTestState();
				try {
					base.evaluate();
				} finally {
					cleanup();
					SharedTestSuiteState.this.testCase = null;
				}
			}
		};
	}

	protected void cleanup() throws Exception {
		// Delete any model content previously created
		final TransactionalEditingDomain domain = modelSet.getTransactionalEditingDomain();
		domain.getCommandStack().execute(new RecordingCommand(domain) {

			@Override
			protected void doExecute() {
				// Destroy any other diagrams that were created, CSS stylesheets, etc.
				List<EObject> toDestroy = Lists.newArrayList();
				if( diagram.eResource()!=null){
					Iterators.addAll(toDestroy, diagram.eResource().getAllContents());
				}

				UmlModel uml = (UmlModel) modelSet.getModel(UmlModel.MODEL_ID);
				if( uml.getResource()!=null){
				Iterators.addAll(toDestroy, uml.getResource().getAllContents());
				}

				toDestroy.removeAll(preserve);

				CustomUMLUtil.destroyAll(toDestroy);
			}
		});
		domain.getCommandStack().flush();
	}

	private void setTestState() {
		testCase.project = project;
		testCase.file = file;
		testCase.papyrusEditor = papyrusEditor;
		testCase.diResourceSet = modelSet;
	}

	public void setupTest() throws Exception {
		if (!canShareState) {
			testCase.doSetUp();
		}
	}

	void teardownTest() throws Exception {
		if (!canShareState) {
			testCase.doTearDown();
		}
	}
}

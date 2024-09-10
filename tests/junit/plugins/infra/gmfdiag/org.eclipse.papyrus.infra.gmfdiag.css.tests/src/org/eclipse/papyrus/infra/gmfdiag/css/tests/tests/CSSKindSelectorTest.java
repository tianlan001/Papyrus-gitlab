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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.css.tests.tests;

import java.util.Iterator;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.FigureUtils;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.EmbeddedStyleSheet;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.AssociationTargetNameEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ClassFloatingNameEditPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * The Class CSSKindSelectorTest defined to test CSS selector kind for Compartment, FloatingLabel and one specific external label.
 */
@PluginResource("/resources/model/kindSelectorTest/model.di")
public class CSSKindSelectorTest extends AbstractPapyrusTest {

	/** The diagram. */
	private Diagram diagram = null;


	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();


	@Before
	public void init() {
		diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "ClassDiagram");
	}


	/**
	 * Test kind selector for compartment with:
	 * Compartment[kind=attributes]{visible:false;}
	 *
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws NotDefinedException
	 *             the not defined exception
	 * @throws NotEnabledException
	 *             the not enabled exception
	 * @throws NotHandledException
	 *             the not handled exception
	 */
	@Test
	public void testKindSelectorForCompartment() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		EditPart classEditPart = getEditPart("Class1");
		Assert.assertTrue(classEditPart instanceof GraphicalEditPart);
		Iterator<ResizableCompartmentFigure> compartments = FigureUtils.findChildFigureInstances(((GraphicalEditPart) classEditPart).getFigure(), ResizableCompartmentFigure.class).iterator();

		boolean visible = false;

		while (false == visible && compartments.hasNext()) {
			ResizableCompartmentFigure compartment = (ResizableCompartmentFigure) compartments.next();
			if ("attributes".equals(compartment.getCompartmentTitle())) {
				visible = true;
			}
		}

		Assert.assertTrue("attributes must be visible by default", visible);

		setEmbeddedStyleSheet("Compartment[kind=attributes]", "visible", String.valueOf(false), false);
		classEditPart.refresh();

		for (ResizableCompartmentFigure compartment : FigureUtils.findChildFigureInstances(((GraphicalEditPart) classEditPart).getFigure(), ResizableCompartmentFigure.class)) {
			Assert.assertFalse("Attributes Compartment must not be visible with Compartment[kind=attributes]{visible:false;}", "attributes".equals(compartment.getCompartmentTitle()));
		}
	}

	/**
	 * Test kind selector for label with:
	 * 
	 * Label[kind=FloatingLabel]{visible:true;}
	 * Label[kind=TargetRole]{visible:false;}
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	@Test
	public void testKindSelectorForLabel() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		EditPart classEditPart = getEditPart("Class1");
		Assert.assertTrue(classEditPart instanceof GraphicalEditPart);

		String classFloatingNameVisualID = ClassFloatingNameEditPart.VISUAL_ID;
		IFigure floatingLabelFigure = findChildFigurebyVisualID(classEditPart, classFloatingNameVisualID);

		// Test the default visibility of floatingLabel
		Assert.assertNotNull("Flaoting label must exist", floatingLabelFigure);
		Assert.assertFalse("Floating label must be hidden by default", floatingLabelFigure.isVisible());

		// Set the visibility to true with CSS selector
		setEmbeddedStyleSheet("Label[kind=FloatingLabel]", "visible", String.valueOf(true), false);
		classEditPart.refresh();

		// Test the visibility of floatingLabel with CSS selector Label[kind=FloatingLabel]
		Assert.assertTrue("FloatingLabel must be visible with CSS selector Label[kind=FloatingLabel]", floatingLabelFigure.isVisible());


		// test the association Target Name
		EditPart associationEditPart = getEditPart("associationName");
		Assert.assertTrue(associationEditPart instanceof ConnectionEditPart);

		String associationTargetNameVisualID = AssociationTargetNameEditPart.VISUAL_ID;
		IFigure associationTargetNameFigure = findChildFigurebyVisualID(associationEditPart, associationTargetNameVisualID);

		// Test the default visibility of associationTargetName
		Assert.assertNotNull("TargetRole Label must exist", associationTargetNameFigure);
		Assert.assertTrue("TargetRole Label must be visible by default", associationTargetNameFigure.isVisible());

		// Set the visibility to true with CSS selector
		setEmbeddedStyleSheet("Label[kind=TargetRole]", "visible", String.valueOf(false), false);
		associationEditPart.refresh();

		// Test the visibility of floatingLabel with CSS selector Label[kind=FloatingLabel]
		Assert.assertFalse("TargetRole Label must be hidden with CSS selector Label[kind=TargetRole]", associationTargetNameFigure.isVisible());

	}


	/**
	 * Find child figure by visual id.
	 *
	 * @param classEditPart
	 *            the class edit part
	 * @param classFloatingNameVisualID
	 *            the class floating name visual id
	 * @return the figure
	 */
	private IFigure findChildFigurebyVisualID(EditPart classEditPart, String classFloatingNameVisualID) {
		// Find the floatingLabelFigure
		IFigure floatingLabelFigure = null;
		Iterator<?> childsIterator = classEditPart.getChildren().iterator();
		while (null == floatingLabelFigure && childsIterator.hasNext()) {
			Object child = childsIterator.next();

			if (child instanceof IGraphicalEditPart && classFloatingNameVisualID.equals(((IGraphicalEditPart) child).getNotationView().getType())) {
				floatingLabelFigure = (IFigure) ((IGraphicalEditPart) child).getFigure();
			}
		}
		return floatingLabelFigure;
	}


	/**
	 * Sets the embedded style sheet. Element + "{" + attribut + ":" + value + ";}"
	 *
	 * @param Element
	 *            the element
	 * @param attribut
	 *            the attribut
	 * @param value
	 *            the value
	 * @param append
	 *            the append
	 * @throws ExecutionException
	 *             the execution exception
	 * @throws NotDefinedException
	 *             the not defined exception
	 * @throws NotEnabledException
	 *             the not enabled exception
	 * @throws NotHandledException
	 *             the not handled exception
	 */
	private void setEmbeddedStyleSheet(final String Element, final String attribut, final String value, final boolean append) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		final EmbeddedStyleSheet embeddedStyleSheet = getEmbeddedStyleSheet(diagram);
		Assert.assertNotNull(embeddedStyleSheet);

		Command command = new RecordingCommand(modelSetFixture.getEditingDomain()) {

			@Override
			protected void doExecute() {
				String str = append ? embeddedStyleSheet.getContent() + "\n" : "";
				embeddedStyleSheet.setContent(str + Element + "{" + attribut + ":" + value + ";}");
			}
		};
		getCommandStack().execute(command);
		refreshEditor();

	}

	/**
	 * Gets the embedded style sheet.
	 *
	 * @param diagram
	 *            the diagram
	 * @return the embedded style sheet
	 */
	private EmbeddedStyleSheet getEmbeddedStyleSheet(Diagram diagram) {
		Iterator<EObject> iterator = diagram.eResource().getContents().iterator();
		EmbeddedStyleSheet embeddedStyleSheet = null;
		while (null == embeddedStyleSheet && iterator.hasNext()) {
			Object object = iterator.next();
			if (object instanceof EmbeddedStyleSheet) {
				embeddedStyleSheet = (EmbeddedStyleSheet) object;
			}
		}
		Assert.assertNotNull(embeddedStyleSheet);
		return embeddedStyleSheet;
	}

	/**
	 * Gets the command stack.
	 *
	 * @return the command stack
	 */
	private CommandStack getCommandStack() {
		return modelSetFixture.getEditingDomain().getCommandStack();
	}

	/**
	 * Refresh editor.
	 */
	private void refreshEditor() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		String REFRESH_COMMAND_ID = "org.eclipse.ui.file.refresh"; //$NON-NLS-1$
		ICommandService commandService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ICommandService.class);
		org.eclipse.core.commands.Command refreshCommand = commandService.getCommand(REFRESH_COMMAND_ID);
		if (refreshCommand.getHandler().isEnabled()) {
			refreshCommand.executeWithChecks(new ExecutionEvent());
		}
	}

	/**
	 * Gets the edits the part.
	 *
	 * @param semanticElement
	 *            the semantic element
	 * @return the edits the part
	 */
	private EditPart getEditPart(String semanticElement) {

		modelSetFixture.getPageManager().openPage(diagram);

		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (diagramView == null) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (diagramView == null) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (diagramView == null) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		Assert.assertNotNull(diagramView);

		IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		Assert.assertNotNull(semanticEP);

		return semanticEP;
	}
}

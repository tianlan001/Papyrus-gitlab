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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ResizableCompartmentEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStyleValueCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.BorderDisplayEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.LineStyleEnum;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.EmbeddedStyleSheet;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedBorderNamedElementEditPart;
import org.eclipse.papyrus.uml.diagram.common.editparts.RoundedCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.node.NodeNamedElementFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.PapyrusNodeFigure;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * The Class ShapeCustomisationTest.
 */
@PluginResource("/resources/shapeCustomisation/model.di")
public class ShapeCustomisationTest extends AbstractPapyrusTest {

	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();

	/** The diagram. */
	private Diagram diagram = null;

	/** The component edit part. */
	private EditPart componentEditPart = null;

	/** The port edit part. */
	private EditPart portEditPart = null;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {

		diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "ComponentDiagram");

		componentEditPart = getEditPart("Component1");
		Assert.assertTrue("The Edit Part must be a RoundedCompartmentEditPart", componentEditPart instanceof RoundedCompartmentEditPart);

		portEditPart = getEditPart("Port1");
		Assert.assertTrue("The Edit Part must be a RoundedCompartmentEditPart", portEditPart instanceof RoundedBorderNamedElementEditPart);
	}

	/**
	 * Test generic shape for node.
	 */
	@Test
	public void testGenericShapeForNode() {

		// Test isOval with command on notation.
		testIsOval(componentEditPart);

		// Test radius with command on notation.
		testRadius(componentEditPart);

		// Test borderStyle with command on notation.
		testBorderStyle(componentEditPart);

		// Test CustomDash with command on notation.
		testCustomDash(componentEditPart);

		// Test shadowWidth with command on notation.
		// TODO getter on IRoundedRectangleFigure for shadowWidth

		// Test shadowColor with command on notation.
		testShadowColor(componentEditPart);

		// Test the name background color on notation
		testNameBackgroundColor(componentEditPart);

		// Test isPackage with command on notation.
		testIsPackage(componentEditPart);
		// Test hasHeader with command on notation.
		testHasHeader(componentEditPart);

		// Test namePosition with Command
		testNamePosition(componentEditPart);

	}

	/**
	 * Test generic shape for port.
	 */
	@Test
	public void testGenericShapeForPort() {

		// Test isOval with command on notation.
		testIsOval(portEditPart);

		// Test radius with command on notation.
		testRadius(portEditPart);

		// Test shadowColor with command on notation.
		testShadowColor(portEditPart);

		// Test shadowWidth with command on notation.
		// TODO getter on IRoundedRectangleFigure for shadowWidth

		// Test borderStyle with command on notation.
		testBorderStyle(portEditPart);

		// Test CustomDash with command on notation.
		testCustomDash(portEditPart);
	}

	/**
	 * Test generic shape for port with css.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	@Test
	public void testGenericShapeForPortWithCSS() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {

		// Test isOval with CSS
		testIsOvalWithCSS(portEditPart);

		// Test radius with CSS
		testRadiusWithCSS(portEditPart);

		// Test shadowColor with CSS
		testShadowColorWithCSS(portEditPart);

		// Test shadowWidth with CSS
		// TODO getter on IRoundedRectangleFigure for shadowWidth

		// Test borderStyle with CSS
		testBorderStyleWithCSS(portEditPart);

		// Test CustomDash with CSS
		testCustomDashWithCSS(portEditPart);
	}

	/**
	 * Test generic shape for compartment.
	 */
	@Test
	@Ignore("Can't get the setted attribute on compartment figure to test it.")
	public void testGenericShapeForCompartment() {

		List<?> children = componentEditPart.getChildren();
		for (Object child : children) {
			if (child instanceof ResizableCompartmentEditPart) {
				// Test if has the needed edit policy
				EditPolicy borderDisplayeditPolicy = ((ResizableCompartmentEditPart) child).getEditPolicy(BorderDisplayEditPolicy.BORDER_DISPLAY_EDITPOLICY);
				Assert.assertNotNull("Compartment must use BorderDisplayEditPolicy", borderDisplayeditPolicy);

				// Test lineLength with command on notation.
				// TODO: getter on OneTopLineResizableBorder
				// View model = (View) componentEditPart.getModel();
				// int defaultLineLength = NotationUtils.getIntValue(model, NamedStyleProperties.LINE_POSITION, -1);

				// Test lineLengthRatio with command on notation.
				// TODO: getter on OneTopLineResizableBorder
				// String defaultLineLengthRatio = NotationUtils.getStringValue(model, NamedStyleProperties.LINE_POSITION, "1.0");

				// Test linePosition with command on notation.
				// TODO: getter on OneTopLineResizableBorder
				// String defaultLinePosition = NotationUtils.getStringValue(model, NamedStyleProperties.LINE_POSITION, "center");
			}
		}
	}

	/**
	 * Test generic shape for compartment.
	 */
	@Test
	@Ignore("Can't get the setted attribute on compartment figure to test it.")
	public void testGenericShapeForCompartmentWithCSS() {

		List<?> children = componentEditPart.getChildren();
		for (Object child : children) {
			if (child instanceof ResizableCompartmentEditPart) {
				// Test if has the needed edit policy
				EditPolicy borderDisplayeditPolicy = ((ResizableCompartmentEditPart) child).getEditPolicy(BorderDisplayEditPolicy.BORDER_DISPLAY_EDITPOLICY);
				Assert.assertNotNull("Compartment must use BorderDisplayEditPolicy", borderDisplayeditPolicy);

				// Test lineLength with command on notation.
				// TODO: getter on OneTopLineResizableBorder
				// View model = (View) componentEditPart.getModel();
				// int defaultLineLength = NotationUtils.getIntValue(model, NamedStyleProperties.LINE_POSITION, -1);

				// Test lineLengthRatio with command on notation.
				// TODO: getter on OneTopLineResizableBorder
				// String defaultLineLengthRatio = NotationUtils.getStringValue(model, NamedStyleProperties.LINE_POSITION, "1.0");

				// Test linePosition with command on notation.
				// TODO: getter on OneTopLineResizableBorder
				// String defaultLinePosition = NotationUtils.getStringValue(model, NamedStyleProperties.LINE_POSITION, "center");
			}
		}
	}

	/**
	 * Test generic shape for floating label.
	 */
	@Test
	public void testGenericShapeForFloatingLabel() {
		// Test isLabelConstrained with command on notation.
		testIsLabelConstrained(componentEditPart);

		// Test floatingNameOffset with command on notation.
		testFloatingLabelOffset(componentEditPart);
	}

	/**
	 * Test generic shape for floating label with css.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	@Test
	public void testGenericShapeForFloatingLabelWithCSS() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		// Test isLabelConstrained with command on notation.
		testIsLabelConstrainedWithCSS(componentEditPart);

		// Test floatingNameOffset with command on notation.
		testFloatingLabelOffsetWithCSS(componentEditPart);
	}

	/**
	 * Test generic shape node with css.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	@Test
	public void testGenericShapeForNodeWithCSS() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {

		// Test isOval with CSS
		testIsOvalWithCSS(componentEditPart);

		// Test radius with CSS
		testRadiusWithCSS(componentEditPart);

		// Test borderStyle CSS
		testBorderStyleWithCSS(componentEditPart);

		// Test CustomDash CSS
		testCustomDashWithCSS(componentEditPart);

		// Test shadowWidth CSS
		// TODO getter on IRoundedRectangleFigure for shadowWidth

		// Test shadowColor CSS
		testShadowColorWithCSS(componentEditPart);

		// Test name background color with CSS
		testNameBackgroundColorWithCSS(componentEditPart);

		// Test isPackage with CSS
		testIsPackageWithCSS(componentEditPart);

		// Test hasHeader with CSS
		testHasHeaderWithCSS(componentEditPart);

		// Test namePosition CSS
		testNamePositionWithCSS(componentEditPart);

	}

	/**
	 * Test name position.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testNamePosition(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		int namePositionDefault = ((NodeNamedElementFigure) primaryShape).getNameLabel().getAlignment();
		String namePositionForTest = "left";

		getCommandStack().execute(
				new CustomStyleValueCommand((View) editPart.getModel(), namePositionForTest, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(), NamedStyleProperties.TEXT_ALIGNMENT));

		Assert.assertEquals("name Position not well set on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getNameLabel().getAlignment());
		Assert.assertEquals("stereotype Position not well set on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getStereotypesLabel().getAlignment());
		Assert.assertEquals("Tag label Position not well set on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getTaggedLabel().getAlignment());
		getCommandStack().undo();
		Assert.assertEquals("name Position not well undo on IRoundedRectangleFigure", namePositionDefault, ((NodeNamedElementFigure) primaryShape).getNameLabel().getAlignment());
		Assert.assertEquals("stereotype Position not well undo on IRoundedRectangleFigure", namePositionDefault, ((NodeNamedElementFigure) primaryShape).getStereotypesLabel().getAlignment());
		Assert.assertEquals("Tag label Position not well undo on IRoundedRectangleFigure", namePositionDefault, ((NodeNamedElementFigure) primaryShape).getTaggedLabel().getAlignment());
		getCommandStack().redo();
		Assert.assertEquals("name Position not well redo on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getNameLabel().getAlignment());
		Assert.assertEquals("stereotype Position not well redo on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getStereotypesLabel().getAlignment());
		Assert.assertEquals("Tag label Position not well redo on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getTaggedLabel().getAlignment());
		getCommandStack().undo();
	}

	/**
	 * Test name position with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testNamePositionWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		String namePositionForTest = "left";

		getCommandStack().execute(
				new CustomStyleValueCommand((View) editPart.getModel(), namePositionForTest, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(), NamedStyleProperties.TEXT_ALIGNMENT));

		setEmbeddedStyleSheet("*", NamedStyleProperties.TEXT_ALIGNMENT, namePositionForTest, false);
		editPart.refresh();

		Assert.assertEquals("name Position not well set on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getNameLabel().getAlignment());
		Assert.assertEquals("stereotype Position not well set on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getStereotypesLabel().getAlignment());
		Assert.assertEquals("Tag label Position not well set on IRoundedRectangleFigure", PositionConstants.LEFT, ((NodeNamedElementFigure) primaryShape).getTaggedLabel().getAlignment());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Test custom dash.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testCustomDash(EditPart editPart) {

		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		int[] defaultCustomDash = ((PapyrusNodeFigure) primaryShape).getCustomDash();
		List<Integer> defaultCustomDashList = new ArrayList<Integer>();
		for (int index = 0; index < defaultCustomDash.length; index++) {
			defaultCustomDashList.add(defaultCustomDash[index]);
		}

		int[] customDashForTest = { 10, 20, 30 };
		List<String> customDashForTestList = new ArrayList<String>(3);
		customDashForTestList.add(Integer.toString(customDashForTest[0]));
		customDashForTestList.add(Integer.toString(customDashForTest[1]));
		customDashForTestList.add(Integer.toString(customDashForTest[2]));

		getCommandStack()
				.execute(new CustomStyleValueCommand((View) editPart.getModel(), customDashForTestList, NotationPackage.eINSTANCE.getStringListValueStyle(), NotationPackage.eINSTANCE.getStringListValueStyle_StringListValue(),
						NamedStyleProperties.LINE_CUSTOM_VALUE));
		Assert.assertArrayEquals("custom dash not well set on IRoundedRectangleFigure", customDashForTest, ((PapyrusNodeFigure) primaryShape).getCustomDash());
		getCommandStack().undo();
		Assert.assertArrayEquals("custom dash not well undo on IRoundedRectangleFigure", defaultCustomDash, ((PapyrusNodeFigure) primaryShape).getCustomDash());
		getCommandStack().redo();
		Assert.assertArrayEquals("custom dash not well redo on IRoundedRectangleFigure", customDashForTest, ((PapyrusNodeFigure) primaryShape).getCustomDash());
		getCommandStack().undo();
	}

	/**
	 * Test custom dash with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testCustomDashWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {

		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		int[] defaultCustomDash = ((PapyrusNodeFigure) primaryShape).getCustomDash();
		List<Integer> defaultCustomDashList = new ArrayList<Integer>();
		for (int index = 0; index < defaultCustomDash.length; index++) {
			defaultCustomDashList.add(defaultCustomDash[index]);
		}

		int[] customDashForTest = { 10, 20, 30 };

		setEmbeddedStyleSheet("*", NamedStyleProperties.LINE_CUSTOM_VALUE, "10 20 30", false);
		editPart.refresh();

		Assert.assertArrayEquals("custom dash not well set on IRoundedRectangleFigure", customDashForTest, ((PapyrusNodeFigure) primaryShape).getCustomDash());
		resetEmbeddedStyleSheet();

	}

	/**
	 * Test is oval with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testIsOvalWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		setEmbeddedStyleSheet("*", NamedStyleProperties.IS_OVAL, "true", false);
		editPart.refresh();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);
		Assert.assertEquals("Oval not well set on IRoundedRectangleFigure throw CSS", true, ((IRoundedRectangleFigure) primaryShape).isOval());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Sets the embedded style sheet.
	 *
	 * @param Element
	 *            the element
	 * @param attribut
	 *            the attribut
	 * @param value
	 *            the value
	 * @param append
	 *            if append
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
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
		while (iterator.hasNext() && null == embeddedStyleSheet) {
			Object object = iterator.next();
			if (object instanceof EmbeddedStyleSheet) {
				embeddedStyleSheet = (EmbeddedStyleSheet) object;
			}
		}
		Assert.assertNotNull(embeddedStyleSheet);
		return embeddedStyleSheet;
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
	 * Test floating label offset.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testFloatingLabelOffset(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		Dimension labelOffsetDefaultValue = ((IRoundedRectangleFigure) primaryShape).getFloatingNameOffset();
		Dimension labelOffsetForTest = new Dimension(-20, 30);

		CompoundCommand cc = new CompoundCommand();
		cc.append(new CustomStyleValueCommand((View) editPart.getModel(), labelOffsetForTest.width, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
				NamedStyleProperties.FLOATING_LABEL_OFFSET_WIDTH));
		cc.append(new CustomStyleValueCommand((View) editPart.getModel(), labelOffsetForTest.height, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
				NamedStyleProperties.FLOATING_LABEL_OFFSET_HEIGHT));
		getCommandStack().execute(cc);
		Assert.assertEquals("labelOffset not well set on IRoundedRectangleFigure", labelOffsetForTest, ((IRoundedRectangleFigure) primaryShape).getFloatingNameOffset());
		getCommandStack().undo();
		Assert.assertEquals("labelOffset not well undo on IRoundedRectangleFigure", labelOffsetDefaultValue, ((IRoundedRectangleFigure) primaryShape).getFloatingNameOffset());
		getCommandStack().redo();
		Assert.assertEquals("labelOffset not well redo on IRoundedRectangleFigure", labelOffsetForTest, ((IRoundedRectangleFigure) primaryShape).getFloatingNameOffset());
		getCommandStack().undo();
	}

	/**
	 * Test floating label offset with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testFloatingLabelOffsetWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		Dimension labelOffsetForTest = new Dimension(-20, 30);

		setEmbeddedStyleSheet("*", NamedStyleProperties.FLOATING_LABEL_OFFSET_WIDTH, "-20", false);
		setEmbeddedStyleSheet("*", NamedStyleProperties.FLOATING_LABEL_OFFSET_HEIGHT, "30", true);
		editPart.refresh();

		Assert.assertEquals("labelOffset not well set on IRoundedRectangleFigure", labelOffsetForTest, ((IRoundedRectangleFigure) primaryShape).getFloatingNameOffset());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Test is label constrained.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testIsLabelConstrained(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		boolean isLabelConstrainedDefaultValue = ((IRoundedRectangleFigure) primaryShape).isFloatingNameConstrained();

		getCommandStack().execute(
				new CustomStyleValueCommand((View) editPart.getModel(), !isLabelConstrainedDefaultValue, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						NamedStyleProperties.FLOATING_LABEL_CONSTRAINED));
		Assert.assertEquals("LabelConstrained not well set on IRoundedRectangleFigure", !isLabelConstrainedDefaultValue, ((IRoundedRectangleFigure) primaryShape).isFloatingNameConstrained());
		getCommandStack().undo();
		Assert.assertEquals("LabelConstrained not well undo on IRoundedRectangleFigure", isLabelConstrainedDefaultValue, ((IRoundedRectangleFigure) primaryShape).isFloatingNameConstrained());
		getCommandStack().redo();
		Assert.assertEquals("LabelConstrained not well redo on IRoundedRectangleFigure", !isLabelConstrainedDefaultValue, ((IRoundedRectangleFigure) primaryShape).isFloatingNameConstrained());
		getCommandStack().undo();
	}

	/**
	 * Test is label constrained with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testIsLabelConstrainedWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		setEmbeddedStyleSheet("*", NamedStyleProperties.FLOATING_LABEL_CONSTRAINED, "true", false);
		editPart.refresh();

		Assert.assertEquals("LabelConstrained not well set on IRoundedRectangleFigure", true, ((IRoundedRectangleFigure) primaryShape).isFloatingNameConstrained());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Test shadow color.
	 *
	 * @param editPart
	 *            the edit part"true"
	 */
	private void testShadowColor(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		String shadowColorDefaultValue = ((IRoundedRectangleFigure) primaryShape).getShadowColor();
		String shadowColorForTest1 = "red";

		getCommandStack()
				.execute(new CustomStyleValueCommand((View) editPart.getModel(), shadowColorForTest1, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(),
						NamedStyleProperties.SHADOW_COLOR));
		Assert.assertEquals("shadowColor not well set on IRoundedRectangleFigure", shadowColorForTest1, ((IRoundedRectangleFigure) primaryShape).getShadowColor());
		getCommandStack().undo();
		Assert.assertEquals("shadowColor not well undo on IRoundedRectangleFigure", shadowColorDefaultValue, ((IRoundedRectangleFigure) primaryShape).getShadowColor());
		getCommandStack().redo();
		Assert.assertEquals("shadowColor not well redo on IRoundedRectangleFigure", shadowColorForTest1, ((IRoundedRectangleFigure) primaryShape).getShadowColor());
		getCommandStack().undo();

		String shadowColorForTest2 = "#336699";
		getCommandStack()
				.execute(new CustomStyleValueCommand((View) editPart.getModel(), shadowColorForTest2, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(),
						NamedStyleProperties.SHADOW_COLOR));
		Assert.assertEquals("shadowColor not well set on IRoundedRectangleFigure", shadowColorForTest2, ((IRoundedRectangleFigure) primaryShape).getShadowColor());
		getCommandStack().undo();
		Assert.assertEquals("shadowColor not well undo on IRoundedRectangleFigure", shadowColorDefaultValue, ((IRoundedRectangleFigure) primaryShape).getShadowColor());
	}

	/**
	 * Test shadow color with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testShadowColorWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		setEmbeddedStyleSheet("*", NamedStyleProperties.SHADOW_COLOR, "red", false);
		editPart.refresh();

		Assert.assertEquals("shadowColor not well set on IRoundedRectangleFigure", "red", ((IRoundedRectangleFigure) primaryShape).getShadowColor());
		resetEmbeddedStyleSheet();

		setEmbeddedStyleSheet("*", NamedStyleProperties.SHADOW_COLOR, "\"#336699\"", false);// need to ass \" \" because #123456 is reconise as RGBColor whitch is not managed by papyrus.
		editPart.refresh();

		Assert.assertEquals("shadowColor not well set on IRoundedRectangleFigure", "#336699", ((IRoundedRectangleFigure) primaryShape).getShadowColor());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Test background label color.
	 *
	 * @param editPart
	 *            the edit part"true"
	 */
	private void testNameBackgroundColor(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		String labelBackgroundColorDefaultValue = ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor();
		String labelBackgroundColorForTest1 = "red";

		getCommandStack()
				.execute(new CustomStyleValueCommand((View) editPart.getModel(), labelBackgroundColorForTest1, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(),
						NamedStyleProperties.NAME_BACKGROUND_COLOR));
		Assert.assertEquals("nameBackgroundColor not well set on IRoundedRectangleFigure", labelBackgroundColorForTest1, ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
		getCommandStack().undo();
		Assert.assertEquals("nameBackgroundColor not well undo on IRoundedRectangleFigure", labelBackgroundColorDefaultValue, ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
		getCommandStack().redo();
		Assert.assertEquals("nameBackgroundColor not well redo on IRoundedRectangleFigure", labelBackgroundColorForTest1, ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
		getCommandStack().undo();

		String labelBackgroundColorForTest2 = "#336699";
		getCommandStack()
				.execute(new CustomStyleValueCommand((View) editPart.getModel(), labelBackgroundColorForTest2, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(),
						NamedStyleProperties.NAME_BACKGROUND_COLOR));
		Assert.assertEquals("nameBackgroundColor not well set on IRoundedRectangleFigure", labelBackgroundColorForTest2, ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
		getCommandStack().undo();
		Assert.assertEquals("nameBackgroundColor not well undo on IRoundedRectangleFigure", labelBackgroundColorDefaultValue, ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
	}

	/**
	 * Test name Background Color with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testNameBackgroundColorWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		setEmbeddedStyleSheet("*", NamedStyleProperties.NAME_BACKGROUND_COLOR, "red", false);
		editPart.refresh();

		Assert.assertEquals("nameBackgroundColor not well set on IRoundedRectangleFigure", "red", ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
		resetEmbeddedStyleSheet();

		setEmbeddedStyleSheet("*", NamedStyleProperties.NAME_BACKGROUND_COLOR, "\"#336699\"", false);// need to ass \" \" because #123456 is reconise as RGBColor whitch is not managed by papyrus.
		editPart.refresh();

		Assert.assertEquals("nameBackgroundColor not well set on IRoundedRectangleFigure", "#336699", ((IRoundedRectangleFigure) primaryShape).getNameBackgroundColor());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Test has header.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testHasHeader(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		boolean hasHeaderDefaultValue = ((IRoundedRectangleFigure) primaryShape).hasHeader();

		getCommandStack()
				.execute(new CustomStyleValueCommand((View) editPart.getModel(), !hasHeaderDefaultValue, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
						NamedStyleProperties.DISPLAY_HEADER));

		Assert.assertEquals("Oval not well set on IRoundedRectangleFigure", !hasHeaderDefaultValue, ((IRoundedRectangleFigure) primaryShape).hasHeader());
		getCommandStack().undo();
		Assert.assertEquals("Oval not well undo on IRoundedRectangleFigure", hasHeaderDefaultValue, ((IRoundedRectangleFigure) primaryShape).hasHeader());
		getCommandStack().redo();
		Assert.assertEquals("Oval not well redo on IRoundedRectangleFigure", !hasHeaderDefaultValue, ((IRoundedRectangleFigure) primaryShape).hasHeader());
		getCommandStack().undo();
	}

	/**
	 * Test has header with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testHasHeaderWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		boolean hasHeaderDefaultValue = ((IRoundedRectangleFigure) primaryShape).hasHeader();

		setEmbeddedStyleSheet("*", NamedStyleProperties.DISPLAY_HEADER, String.valueOf(!hasHeaderDefaultValue), false);
		editPart.refresh();

		Assert.assertEquals("Oval not well set on IRoundedRectangleFigure", !hasHeaderDefaultValue, ((IRoundedRectangleFigure) primaryShape).hasHeader());
		resetEmbeddedStyleSheet();
	}

	/**
	 * Test is package.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testIsPackage(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		if (!((IRoundedRectangleFigure) primaryShape).getBounds().equals(0, 0, 0, 0)) {
			boolean isPackageDefaultValue = isPackage((IPapyrusNodeFigure) primaryShape);

			getCommandStack()
					.execute(new CustomStyleValueCommand((View) editPart.getModel(), !isPackageDefaultValue, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(),
							NamedStyleProperties.IS_PACKAGE));

			Assert.assertEquals("Package not well set on IRoundedRectangleFigure", !isPackageDefaultValue, isPackage((IPapyrusNodeFigure) primaryShape));
			getCommandStack().undo();
			Assert.assertEquals("Package not well undo on IRoundedRectangleFigure", isPackageDefaultValue, isPackage((IPapyrusNodeFigure) primaryShape));
			getCommandStack().redo();
			Assert.assertEquals("Package not well redo on IRoundedRectangleFigure", !isPackageDefaultValue, isPackage((IPapyrusNodeFigure) primaryShape));
			getCommandStack().undo();
		}
	}

	/**
	 * Test is package with css.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testIsPackageWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		if (!((IRoundedRectangleFigure) primaryShape).getBounds().equals(0, 0, 0, 0)) {
			boolean isPackageDefaultValue = isPackage((IPapyrusNodeFigure) primaryShape);

			setEmbeddedStyleSheet("*", NamedStyleProperties.IS_PACKAGE, String.valueOf(!isPackageDefaultValue), false);
			editPart.refresh();

			Assert.assertEquals("Package not well set on IRoundedRectangleFigure", !isPackageDefaultValue, isPackageDefaultValue ? !isPackage((IPapyrusNodeFigure) primaryShape) : isPackage((IPapyrusNodeFigure) primaryShape));
			resetEmbeddedStyleSheet();
		}
	}

	/**
	 * Checks if is package.
	 *
	 * @param primaryShape
	 *            the primary shape
	 * @return true, if is package
	 */
	private boolean isPackage(IPapyrusNodeFigure primaryShape) {
		return !((IRoundedRectangleFigure) primaryShape).getPackageHeader().isEmpty();
	}

	/**
	 * Test border style.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testBorderStyle(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		Border border = ((IRoundedRectangleFigure) primaryShape).getBorder();
		int borderStyleDefaultValue;
		String borderStyleForTestAsString = "dash";
		if (border instanceof LineBorder) {
			borderStyleDefaultValue = ((LineBorder) border).getStyle();

			int borderStyleForTest = LineStyleEnum.getByLiteral(borderStyleForTestAsString).getLineStyle();

			getCommandStack()
					.execute(new CustomStyleValueCommand((View) editPart.getModel(), borderStyleForTestAsString, NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(),
							NamedStyleProperties.BORDER_STYLE));

			Assert.assertEquals("borderStyle not well set on IRoundedRectangleFigure", borderStyleForTest, ((LineBorder) ((IRoundedRectangleFigure) primaryShape).getBorder()).getStyle());
			getCommandStack().undo();
			Assert.assertEquals("borderStyle not well undo on IRoundedRectangleFigure", borderStyleDefaultValue, ((LineBorder) ((IRoundedRectangleFigure) primaryShape).getBorder()).getStyle());
			getCommandStack().redo();
			Assert.assertEquals("borderStyle not well redo on IRoundedRectangleFigure", borderStyleForTest, ((LineBorder) ((IRoundedRectangleFigure) primaryShape).getBorder()).getStyle());
			getCommandStack().undo();
		}
	}

	/**
	 * Test border style.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testBorderStyleWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		Border border = ((IRoundedRectangleFigure) primaryShape).getBorder();
		String borderStyleForTestAsString = "dash";
		if (border instanceof LineBorder) {

			int borderStyleForTest = LineStyleEnum.getByLiteral(borderStyleForTestAsString).getLineStyle();

			setEmbeddedStyleSheet("*", NamedStyleProperties.BORDER_STYLE, borderStyleForTestAsString, false);
			editPart.refresh();

			Assert.assertEquals("borderStyle not well set on IRoundedRectangleFigure", borderStyleForTest, ((LineBorder) ((IRoundedRectangleFigure) primaryShape).getBorder()).getStyle());
			resetEmbeddedStyleSheet();
		}
	}


	/**
	 * Reset embedded style sheet.
	 * 
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void resetEmbeddedStyleSheet() throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		setEmbeddedStyleSheet("", "", "", false);
	}

	/**
	 * Test radius.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testRadius(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		Dimension radiusDefaultValue = ((IRoundedRectangleFigure) primaryShape).getCornerDimensions();
		Dimension radiusForTest = new Dimension(20, 30);

		CompoundCommand cc = new CompoundCommand();
		cc.append(new CustomStyleValueCommand((View) editPart.getModel(), radiusForTest.width, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
				NamedStyleProperties.RADIUS_WIDTH));
		cc.append(new CustomStyleValueCommand((View) editPart.getModel(), radiusForTest.height, NotationPackage.eINSTANCE.getIntValueStyle(), NotationPackage.eINSTANCE.getIntValueStyle_IntValue(),
				NamedStyleProperties.RADIUS_HEIGHT));
		getCommandStack().execute(cc);

		Assert.assertEquals("Radius not well set on IRoundedRectangleFigure", radiusForTest, ((IRoundedRectangleFigure) primaryShape).getCornerDimensions());
		getCommandStack().undo();
		Assert.assertEquals("Radius not well undo on IRoundedRectangleFigure", radiusDefaultValue, ((IRoundedRectangleFigure) primaryShape).getCornerDimensions());
		getCommandStack().redo();
		Assert.assertEquals("Radius not well redo on IRoundedRectangleFigure", radiusForTest, ((IRoundedRectangleFigure) primaryShape).getCornerDimensions());
		getCommandStack().undo();
	}

	/**
	 * Test radius with CSS.
	 *
	 * @param editPart
	 *            the edit part
	 * @throws NotHandledException
	 * @throws NotEnabledException
	 * @throws NotDefinedException
	 * @throws ExecutionException
	 */
	private void testRadiusWithCSS(EditPart editPart) throws ExecutionException, NotDefinedException, NotEnabledException, NotHandledException {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		Dimension radiusForTest = new Dimension(20, 30);

		setEmbeddedStyleSheet("*", NamedStyleProperties.RADIUS_WIDTH, String.valueOf(radiusForTest.width), false);
		setEmbeddedStyleSheet("*", NamedStyleProperties.RADIUS_HEIGHT, String.valueOf(radiusForTest.height), true);
		editPart.refresh();

		Assert.assertEquals("Radius not well set on IRoundedRectangleFigure", radiusForTest, ((IRoundedRectangleFigure) primaryShape).getCornerDimensions());

		resetEmbeddedStyleSheet();
	}

	/**
	 * Test is oval.
	 *
	 * @param editPart
	 *            the edit part
	 */
	private void testIsOval(EditPart editPart) {
		IFigure primaryShape = ((IPapyrusEditPart) editPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement IRoundedRectangleFigure", primaryShape instanceof IRoundedRectangleFigure);

		boolean isOvalDefaultValue = ((IRoundedRectangleFigure) primaryShape).isOval();

		getCommandStack().execute(
				new CustomStyleValueCommand((View) editPart.getModel(), !isOvalDefaultValue, NotationPackage.eINSTANCE.getBooleanValueStyle(), NotationPackage.eINSTANCE.getBooleanValueStyle_BooleanValue(), NamedStyleProperties.IS_OVAL));

		Assert.assertEquals("Oval not well set on IRoundedRectangleFigure", !isOvalDefaultValue, ((IRoundedRectangleFigure) primaryShape).isOval());
		getCommandStack().undo();
		Assert.assertEquals("Oval not well undo on IRoundedRectangleFigure", isOvalDefaultValue, ((IRoundedRectangleFigure) primaryShape).isOval());
		getCommandStack().redo();
		Assert.assertEquals("Oval not well redo on IRoundedRectangleFigure", !isOvalDefaultValue, ((IRoundedRectangleFigure) primaryShape).isOval());
		getCommandStack().undo();
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

		assertNotNull(diagramView);

		IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(modelSetFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		assertNotNull(semanticEP);

		return semanticEP;
	}

}
/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RotatableDecoration;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.CustomStyleValueCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.decoration.ConnectionDecorationRegistry;
import org.eclipse.papyrus.infra.gmfdiag.common.decoration.OpenArrowConnectionDecoration;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.edge.PapyrusEdgeFigure;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * <pre>
 * Tests for the edge source and target decoration customization.
 * </pre>
 * 
 * @author Mickaël ADAM
 */
@SuppressWarnings("nls")
@PluginResource("/resources/edgeCustomisation/edgeCustomisation.di")
public class EdgeCustomizationTest extends AbstractPapyrusTest {

	/** The model set fixture. */
	@Rule
	public final PapyrusEditorFixture modelSetFixture = new PapyrusEditorFixture();

	/** The diagram. */
	private Diagram diagram = null;

	/** The connector edit part with default style. */
	private EditPart defaultConnectorEditPart = null;

	/** The connector edit part with CSS style. */
	private EditPart cssConnectorEditPart = null;

	/** The connector edit part with style set with properties view. */
	private EditPart propertiesConnectorEditPart = null;

	/**
	 * Initialize tests.
	 */
	@Before
	public void init() {
		diagram = DiagramUtils.getNotationDiagram((ModelSet) modelSetFixture.getResourceSet(), "Composite Structure Diagram");
		assertNotNull(diagram);

		defaultConnectorEditPart = getEditPart("DefaultConnector");
		Assert.assertTrue("The Edit Part must be a ConnectionEditPart", defaultConnectorEditPart instanceof ConnectionEditPart);

		cssConnectorEditPart = getEditPart("CSSConnector");
		Assert.assertTrue("The Edit Part must be a ConnectionEditPart", cssConnectorEditPart instanceof ConnectionEditPart);

		propertiesConnectorEditPart = getEditPart("PropertiesSetConnector");
		Assert.assertTrue("The Edit Part must be a ConnectionEditPart", propertiesConnectorEditPart instanceof ConnectionEditPart);
	}


	/**
	 * Test the edge decoration customization.
	 */
	@Test
	public void testEdgeDecoration() {
		testDefaultEdgeDecoration();
		testPropertiesEdgeDecoration();
		testCSSEdgeDecoration();
	}

	/**
	 * Test the {@link ConnectionDecorationRegistry}.
	 */
	@Test
	public void testConnectionDecorationRegistry() {
		Class<? extends RotatableDecoration> decorationClass = ConnectionDecorationRegistry.getInstance().getDecorationClass("open_arrow");
		assertNotNull("open_arrow decoration must exist", decorationClass);
		if (null != decorationClass) {
			// load the class
			RotatableDecoration decoration = ClassLoaderHelper.newInstance(decorationClass);
			assertTrue("Instanciate decoration must be instance of OpenArrowConnectionDecoration", decoration instanceof OpenArrowConnectionDecoration);
		}
	}

	/**
	 * Test the connector with default edge decoration.
	 */
	private void testDefaultEdgeDecoration() {
		IFigure primaryShape = ((IPapyrusEditPart) defaultConnectorEditPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement PapyrusEdgeFigure", primaryShape instanceof PapyrusEdgeFigure);

		// Default value test:
		String sourceDecoration = extract((StringValueStyle) ((View) defaultConnectorEditPart.getModel()).getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), ConnectionEditPart.SOURCE_DECORATION));
		assertNull("Default source decoration must be null", sourceDecoration);

		// Set Value Style
		getCommandStack().execute(
				new CustomStyleValueCommand((View) defaultConnectorEditPart.getModel(), "open_arrow", NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(), ConnectionEditPart.SOURCE_DECORATION));

		// test of set command
		sourceDecoration = extract((StringValueStyle) ((View) defaultConnectorEditPart.getModel()).getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), ConnectionEditPart.SOURCE_DECORATION));
		assertEquals("SourceDecoration value style must be open_arrow", "open_arrow", sourceDecoration);
	}

	/**
	 * Test the connector with edge decoration set with properties view.
	 */
	private void testPropertiesEdgeDecoration() {
		IFigure primaryShape = ((IPapyrusEditPart) propertiesConnectorEditPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement PapyrusEdgeFigure", primaryShape instanceof PapyrusEdgeFigure);

		// Default value test:
		String sourceDecoration = extract((StringValueStyle) ((View) propertiesConnectorEditPart.getModel()).getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), ConnectionEditPart.SOURCE_DECORATION));
		assertEquals("SourceDecoration value style must be solid_diamond_filled", "solid_diamond_filled", sourceDecoration);

		// Set Value Style
		getCommandStack().execute(
				new CustomStyleValueCommand((View) propertiesConnectorEditPart.getModel(), "open_arrow", NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(), ConnectionEditPart.SOURCE_DECORATION));

		// test of set command
		sourceDecoration = extract((StringValueStyle) ((View) propertiesConnectorEditPart.getModel()).getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), ConnectionEditPart.SOURCE_DECORATION));
		assertEquals("SourceDecoration value style must be open_arrow", "open_arrow", sourceDecoration);
	}

	/**
	 * Test the connector with edge decoration set with CSS.
	 */
	private void testCSSEdgeDecoration() {
		IFigure primaryShape = ((IPapyrusEditPart) cssConnectorEditPart).getPrimaryShape();
		Assert.assertTrue("The Figure must implement PapyrusEdgeFigure", primaryShape instanceof PapyrusEdgeFigure);

		// Default value test:
		String sourceDecoration = extract((StringValueStyle) ((View) cssConnectorEditPart.getModel()).getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), ConnectionEditPart.SOURCE_DECORATION));
		assertEquals("SourceDecoration value style must be open_arrow", "open_arrow", sourceDecoration);

		// Set Value Style
		getCommandStack().execute(
				new CustomStyleValueCommand((View) cssConnectorEditPart.getModel(), "solid_diamond_filled", NotationPackage.eINSTANCE.getStringValueStyle(), NotationPackage.eINSTANCE.getStringValueStyle_StringValue(), ConnectionEditPart.SOURCE_DECORATION));

		// test of set command
		sourceDecoration = extract((StringValueStyle) ((View) cssConnectorEditPart.getModel()).getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), ConnectionEditPart.SOURCE_DECORATION));
		assertEquals("SourceDecoration value style must be solid_diamond_filled", "solid_diamond_filled", sourceDecoration);
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
	 * Gets the edit part.
	 *
	 * @param semanticElement
	 *            the semantic element name
	 * @return the edit part
	 */
	private EditPart getEditPart(final String semanticElement) {
		modelSetFixture.getPageManager().openPage(diagram);

		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (null == diagramView) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (null == diagramView) {
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

	/**
	 * Extracts the string value from the given style
	 *
	 * @param style
	 *            The style
	 * @return The primitive value
	 */
	private String extract(final StringValueStyle style) {
		if (null == style || null == style.getStringValue() || style.getStringValue().isEmpty()) {
			return null;
		}
		return style.getStringValue();
	}

}

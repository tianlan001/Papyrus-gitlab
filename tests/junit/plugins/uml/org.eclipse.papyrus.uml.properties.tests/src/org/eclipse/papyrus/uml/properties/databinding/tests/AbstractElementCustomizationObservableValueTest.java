/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.databinding.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.modelelement.UMLNotationModelElement;
import org.eclipse.papyrus.uml.properties.tests.DiagramViewUtils;
import org.junit.Assert;

/**
 * This allows to test the element customization observable value.
 */
@SuppressWarnings({ "nls" })
@PluginResource("model/ElementCustomizationObservableValue.di")
public abstract class AbstractElementCustomizationObservableValueTest extends AbstractObservableValueTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		Diagram diagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), "Class Diagram");
		assertNotNull(diagram);

		editorFixture.getPageManager().openPage(diagram);
		editorFixture.flushDisplayEvents();

		final View diagramView = DiagramViewUtils.getDiagramView(diagram, "Class1");
		Assert.assertNotNull("The diagram view for 'Class1' cannot be found", diagramView);

		final IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(editorFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		Assert.assertNotNull("The edit part for 'Class1' cannot be found", semanticEP);

		return semanticEP;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new UMLNotationModelElement((EditPart) source);
	}

}

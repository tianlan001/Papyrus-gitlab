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

import java.util.ArrayList;
import java.util.stream.Stream;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.BasicCompartment;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.modelelement.UMLNotationModelElement;
import org.eclipse.papyrus.uml.properties.tests.DiagramViewUtils;
import org.junit.Assert;

/**
 * This allows to test the mask value observable list.
 */
@SuppressWarnings({ "unchecked", "serial", "nls" })
@PluginResource("model/MaskValueObservableList.di")
public class MaskValueObservableListTest extends AbstractUMLObservableListTest {

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

		final Stream<BasicCompartment> compartments = diagramView.getChildren().stream().filter(BasicCompartment.class::isInstance).map(BasicCompartment.class::cast);
		final BasicCompartment attributeCompartment = compartments.filter(child -> "Class_AttributeCompartment".equals(child.getType())).findFirst().get();
		Assert.assertNotNull("The attribute compartment of 'Class1' cannot be found", attributeCompartment);

		final View propertyView = !attributeCompartment.getChildren().isEmpty() ? (View) attributeCompartment.getChildren().get(0) : null;
		Assert.assertNotNull("The view of property 'Property1' cannot be found", propertyView);

		final IGraphicalEditPart propertyEP = DiagramUtils.findEditPartforView(editorFixture.getEditor(), propertyView, IGraphicalEditPart.class);
		Assert.assertNotNull("The edit part for 'Property1' cannot be found", propertyEP);

		return propertyEP;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new UMLNotationModelElement((EditPart) source);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return UMLNotationModelElement.LabelCustomization;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return new ArrayList<String>() {
			{
				add("multiplicity");
				add("visibility");
				add("name");
				add("type");
				add("derived");
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		return new ArrayList<String>() {
			{
				add("multiplicity");
				add("visibility");
				add("name");
				add("type");
				add("derived");
				add("defaultValue");
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		return "defaultValue";
	}

}

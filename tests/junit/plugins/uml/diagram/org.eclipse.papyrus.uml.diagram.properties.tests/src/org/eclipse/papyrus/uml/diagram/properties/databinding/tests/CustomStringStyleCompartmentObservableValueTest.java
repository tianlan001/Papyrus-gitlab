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

package org.eclipse.papyrus.uml.diagram.properties.databinding.tests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.properties.modelelement.AdvanceStyleModelElement;
import org.eclipse.papyrus.infra.properties.contexts.ContextsFactory;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.properties.databinding.tests.AbstractUMLObservableValueTest;
import org.eclipse.papyrus.uml.properties.tests.DiagramViewUtils;
import org.junit.Assert;

/**
 * This allows to test the custom string style compartment observable value.
 */
@SuppressWarnings({ "nls" })
@PluginResource("model/CustomStyleCompartmentObservableValue.di")
public class CustomStringStyleCompartmentObservableValueTest extends AbstractUMLObservableValueTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		Diagram diagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), "ClassDiagram");
		assertNotNull(diagram);

		editorFixture.getPageManager().openPage(diagram);
		editorFixture.flushDisplayEvents();

		final View classView = DiagramViewUtils.getDiagramView(diagram, "Class1");
		Assert.assertNotNull("The diagram view for 'Class1' cannot be found", classView);

		return classView;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		final DataContextElement dataContext = ContextsFactory.eINSTANCE.createDataContextElement();
		final Property createdProperty = ContextsFactory.eINSTANCE.createProperty();
		createdProperty.setName(getPropertyPath());
		dataContext.getProperties().add(createdProperty);
		return new AdvanceStyleModelElement((View) source, (TransactionalEditingDomain) domain, dataContext);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return NamedStyleProperties.LINE_POSITION;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return "center";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		return "left";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		return "left";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableValueTest#expectedValueType()
	 */
	@Override
	protected Object expectedValueType() {
		return String.class;
	}

}

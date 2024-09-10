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
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.activity.modelelement.ActivityNotationModelElement;
import org.eclipse.papyrus.uml.properties.databinding.tests.AbstractUMLObservableValueTest;
import org.eclipse.papyrus.uml.properties.tests.DiagramViewUtils;
import org.junit.Assert;

/**
 * This allows to test the connection decoration style observable value.
 */
@SuppressWarnings({ "nls" })
@PluginResource("model/SwitchOrientationObservableValue.di")
public class SwitchOrientationObservableValueTest extends AbstractUMLObservableValueTest {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		Diagram diagram = DiagramUtils.getNotationDiagram(editorFixture.getModelSet(), "ActivityDiagram");
		assertNotNull(diagram);

		editorFixture.getPageManager().openPage(diagram);
		editorFixture.flushDisplayEvents();

		final View joinNodeView = DiagramViewUtils.getDiagramView(diagram, "JoinNode1");
		Assert.assertNotNull("The diagram view for 'JoinNode1' cannot be found", joinNodeView);

		final IGraphicalEditPart joinNodeEP = DiagramUtils.findEditPartforView(editorFixture.getEditor(), joinNodeView, IGraphicalEditPart.class);
		Assert.assertNotNull("The edit part for 'JoinNode1' cannot be found", joinNodeEP);

		return joinNodeEP;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new ActivityNotationModelElement((TransactionalEditingDomain) domain, (EditPart) source);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return "switchOrientation";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableValueTest#expectedValueType()
	 */
	@Override
	protected Object expectedValueType() {
		return Boolean.class;
	}

}

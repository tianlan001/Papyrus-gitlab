/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.command;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.component.providers.UMLViewProvider;

/**
 * this is the specific command in charge to transform an element editpart into a shape_namedElement Editpart
 *
 * @since 3.0
 *
 */
public class ElementToStereotypedShape extends RecordingCommand {

	protected GraphicalEditPart elementEditPart;

	/**
	 *
	 * Constructor of this command
	 *
	 * @param domain
	 *            the transactional editing domain to execute transaction
	 * @param classView
	 *            the editpart that will be transformed
	 */
	public ElementToStereotypedShape(TransactionalEditingDomain domain, GraphicalEditPart classView) {
		super(domain);
		this.elementEditPart = classView;
	}

	@Override
	protected void doExecute() {

		// creation of the node
		UMLViewProvider umlViewProvider = new UMLViewProvider();
		Node packageview = umlViewProvider.createNamedElement_DefaultShape(elementEditPart.resolveSemanticElement(), (View) elementEditPart.getNotationView().eContainer(), -1, true, elementEditPart.getDiagramPreferencesHint());
		packageview.setLayoutConstraint(((Node) elementEditPart.getNotationView()).getLayoutConstraint());

		// copy of all eannotations
		Iterator<EAnnotation> iter = elementEditPart.getNotationView().getEAnnotations().iterator();
		while (iter.hasNext()) {
			EAnnotation annotation = EcoreUtil.copy(iter.next());
			packageview.getEAnnotations().add(annotation);

		}

	}

}

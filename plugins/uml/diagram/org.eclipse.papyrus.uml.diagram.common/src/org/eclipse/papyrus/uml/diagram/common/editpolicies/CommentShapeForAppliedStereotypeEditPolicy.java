/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - bug 323802
 *  Celine Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 460356 : Refactor Stereotype Display
 *  Christian W. Damus - bug 492482
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This Edit Policy is attached to AppliedStereotypeCommentEdipart, and is in charge to prevent the deletion of the Comment from model
 * and launch command of deletion if it detect that no property of applied stereotype are displayed.
 *
 */
public class CommentShapeForAppliedStereotypeEditPolicy extends AbstractAppliedStereotypeDisplayEditPolicy implements IPapyrusListener {


	/**
	 * Returns the uml element controlled by the host edit part
	 *
	 * @return the uml element controlled by the host edit part
	 */
	@Override
	protected Element getUMLElement() {
		Element element = null;
		if (null != (Element) getView().getElement()) {
			element = (Element) getView().getElement();
		} else {

			EObject object = NotationUtils.getEObjectValue(getView(), StereotypeDisplayConstant.STEREOTYPE_COMMENT_RELATION_NAME, null);
			if (null != object) {
				if (object instanceof Element) {
					element = (Element) object;
				} else {
					if (UMLUtil.getStereotype(object) != null) {
						element = UMLUtil.getStereotype(object);
					}
				}
			}
		}
		return element;
	}

	@Override
	public Command getCommand(Request request) {
		if (request instanceof EditCommandRequestWrapper) {
			if (((EditCommandRequestWrapper) (request)).getEditCommandRequest() instanceof DestroyElementRequest) {
				return UnexecutableCommand.INSTANCE;
			}
		}
		return super.getCommand(request);
	}

	/**
	 * Refreshes the stereotypes properties displayed above name of the element
	 * in this edit part.
	 *
	 * @param stereotypesPropertiesToDisplay
	 */
	protected void refreshAppliedStereotypesPropertiesInBrace(IPapyrusNodeUMLElementFigure figure) {
		String toDisplayInBrace = helper.getStereotypePropertiesInBrace(((GraphicalEditPart) getHost()).getNotationView());
		// if the string is not empty, then, the figure has to display it. Else,
		// it displays nothing
		if (!"".equals(toDisplayInBrace)) {
			// it has to be displayed in braces, so compute the string to
			// display
			figure.setStereotypePropertiesInBrace(toDisplayInBrace);
		} else {
			figure.setStereotypePropertiesInBrace(null);
		}
	}

	/**
	 * Execute Comment Deletion
	 *
	 * @param domain
	 *            TransactionalDomain
	 * @param commentNode
	 *            Node of the Comment to be deleted.
	 */
	protected void executeAppliedStereotypeCommentDeletion(final TransactionalEditingDomain domain, final View commentNode) {
		if (null != commentNode) {
			if (null != domain && TransactionUtil.getEditingDomain(commentNode) == domain) {
				DeleteCommand command = new DeleteCommand(commentNode);
				try {
					GMFUnsafe.write(domain, command);
				} catch (Exception e) {
					Activator.log.error(e);
				}
			}
		}
	}





	@Override
	public void activate() {
		super.activate();
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (view == null) {
			return;
		}

		// adds a listener on the view and the element controlled by the
		// editpartrefr
		subscribe(view);

	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void deactivate() {
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (null == view) {
			return;
		}

		unsubscribe(view);
	}

	/**
	 * Returns the view controlled by the host edit part
	 *
	 * @return the view controlled by the host edit part
	 */
	@Override
	protected View getView() {
		return (View) getHost().getModel();
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy#refreshStereotypeDisplay()
	 *
	 */

	protected void refreshStereotypeDisplay() {


		IFigure figure = ((IPapyrusEditPart) getHost()).getPrimaryShape();

		// refresh Brace display
		if (figure instanceof IPapyrusNodeUMLElementFigure) {
			refreshAppliedStereotypesPropertiesInBrace((IPapyrusNodeUMLElementFigure) figure);
		}

		// Deletion of Comment
		View commentNode = getView();
		if (commentNode != null) {
			final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(commentNode);

			// if Base_Element is deleted, the Comment is deleted as well
			if (null == getUMLElement()) {
				executeAppliedStereotypeCommentDeletion(domain, commentNode);
			}

			if (0 == commentNode.getSourceEdges().size() && 0 == commentNode.getTargetEdges().size()) {
				executeAppliedStereotypeCommentDeletion(domain, commentNode);
			}
		}

	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy#refreshDisplay()
	 *
	 */
	@Override
	public void refreshDisplay() {

		refreshStereotypeDisplay();

	}
}

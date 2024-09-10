/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Initial API and implementation
 *  Christian W. Damus - bugs 459566, 463846, 485220
 *  
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.menu.utils;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;

/**
 * Utility class to manage delete action in GMF Diagram context.
 * 
 * @author Gabriel Pascual
 *
 */
public final class DeleteActionUtil {

	/**
	 * Instantiates a new delete action util.
	 */
	private DeleteActionUtil() {

	}

	/**
	 * Checks if is semantic deletion.
	 *
	 * @param editPart
	 *            the edit part
	 * @return true, if is semantic deletion
	 * 
	 * @deprecated Use the {@link DiagramEditPartsUtil#isSemanticDeletion(IGraphicalEditPart)} API, instead
	 */
	@Deprecated
	public static boolean isSemanticDeletion(IGraphicalEditPart editPart) {
		return DiagramEditPartsUtil.isSemanticDeletion(editPart);
	}

	/**
	 * Checks if this is a read only element from the edit part.
	 *
	 * @param editPart
	 *            the edit part
	 * @return true, if this is a read only element.
	 * 
	 * @deprecated Use the {@link DiagramEditPartsUtil#isReadOnly(IGraphicalEditPart)} API, instead
	 */
	@Deprecated
	public static boolean isReadOnly(final IGraphicalEditPart editPart) {
		return DiagramEditPartsUtil.isReadOnly(editPart);
	}

	/**
	 * Gets the delete from model command.
	 *
	 * @param editPart
	 *            the edit part
	 * @param editingDomain
	 *            the editing domain
	 * @return the delete from model command
	 */
	public static Command getDeleteFromModelCommand(IGraphicalEditPart editPart, TransactionalEditingDomain editingDomain) {
		// Look for the GMF deletion command
		return editPart.getCommand(new EditCommandRequestWrapper(new DestroyElementRequest(false)));

	}

	/**
	 * Gets the delete from diagram command.
	 *
	 * @param editPart
	 *            the edit part
	 * @return the delete from diagram command
	 */
	public static Command getDeleteFromDiagramCommand(IGraphicalEditPart editPart) {
		return editPart.getCommand(new GroupRequest(RequestConstants.REQ_DELETE));
	}

	/**
	 * Copied from <tt>org.eclipse.gmf.runtime.diagram.ui.actions.internal.DeleteFromDiagramAction</tt>.
	 *
	 * @param editParts
	 *            the edit parts
	 * @return true, if is canonical
	 */
	public static boolean isCanonical(List<IGraphicalEditPart> editParts) {

		boolean isCanonical = false;
		if (!editParts.isEmpty()) {

			for (Iterator<IGraphicalEditPart> si = editParts.iterator(); si.hasNext() && !isCanonical;) {
				IGraphicalEditPart child = si.next();

				isCanonical = isCanonicalView(child);
				if (isCanonical) {
					isCanonical = isCanonicalEditPart(child);
				} else {
					// If there is no element or the element is a view (e.g.
					// diagram
					// link) than we want to support delete from diagram. See
					// Bug 148453.
					continue;
				}

			}
		}
		return isCanonical;
	}


	/**
	 * Checks if is canonical edit part.
	 *
	 * @param editPart
	 *            the edit part
	 * @return true, if is canonical edit part
	 */
	public static boolean isCanonicalEditPart(IGraphicalEditPart editPart) {
		boolean isCanonical = false;
		if (editPart instanceof ConnectionEditPart) {
			ConnectionEditPart connection = (ConnectionEditPart) editPart;
			isCanonical = (!connection.isSemanticConnection() || (isCanonical(connection.getSource()) && isCanonical(connection.getTarget())));
		} else {
			isCanonical = isCanonical(editPart);
		}

		return isCanonical;

	}

	/**
	 * Copied from <tt>org.eclipse.gmf.runtime.diagram.ui.actions.internal.DeleteFromDiagramAction</tt>.
	 *
	 * @param editParts
	 *            the edit parts
	 * @return true, if is support view
	 */
	public static boolean isSupportView(List<IGraphicalEditPart> editParts) {
		boolean isSupportted = true;
		for (Iterator<IGraphicalEditPart> iter = editParts.iterator(); isSupportted && iter.hasNext();) {
			IGraphicalEditPart object = iter.next();
			isSupportted = isSupportView(object);
		}
		return isSupportted;
	}

	/**
	 * Checks if is support view.
	 *
	 * @param object
	 *            the object
	 * @return true, if is support view
	 */
	public static boolean isSupportView(IGraphicalEditPart object) {
		return !(object instanceof GraphicalEditPart && !((GraphicalEditPart) object).hasNotationView());

	}

	/**
	 * Checks if is not canonical view.
	 *
	 * @param editpart
	 *            the editpart
	 * @return true, if is not canonical view
	 */
	public static boolean isCanonicalView(IGraphicalEditPart editpart) {
		View view = editpart.getAdapter(View.class);
		boolean canonicalView = true;
		if (view != null) {
			canonicalView = !(view.getElement() instanceof View);
		}
		return canonicalView;

	}

	/**
	 * Copied from <tt>org.eclipse.gmf.runtime.diagram.ui.actions.internal.DeleteFromDiagramAction</tt>.
	 *
	 * @param editPart
	 *            the edit part
	 * @return true, if is canonical
	 */
	public static boolean isCanonical(EditPart editPart) {
		boolean isCanonical = false;
		EObject eObject = EMFHelper.getEObject(editPart);
		EditPart parent = editPart.getParent();
		if (eObject != null && parent != null) { // sanity checks
			CanonicalEditPolicy canonicalEditPolicy = (CanonicalEditPolicy) parent.getEditPolicy(EditPolicyRoles.CANONICAL_ROLE);
			isCanonical = canonicalEditPolicy != null && canonicalEditPolicy.isEnabled() && canonicalEditPolicy.canCreate(eObject);
		}
		return isCanonical;
	}
}

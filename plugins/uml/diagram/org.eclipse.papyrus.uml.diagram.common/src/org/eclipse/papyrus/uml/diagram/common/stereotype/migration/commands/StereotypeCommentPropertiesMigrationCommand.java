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
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Initial API and implementation
 *   Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 : Refactor Stereotype Display
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 493420
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.stereotype.StereotypeLocationEnum;
import org.eclipse.papyrus.uml.diagram.common.util.CommandUtil;

/**
 *
 * Command to Migrate the StereotypeComment from old Structure (with EAnnotation) to the Structure with NamedStyle.
 *
 * @author Céline JANSSENS
 *
 */
public class StereotypeCommentPropertiesMigrationCommand extends StereotypePropertiesMigrationCommand {
	/**
	 * Boolean to delete the comment or not.
	 */
	private boolean deleteOldComment;

	/**
	 * Constructor.
	 *
	 * @param label
	 *            Label of the Command
	 * @param content
	 *            Main view on which the Stereotype is applied
	 */
	public StereotypeCommentPropertiesMigrationCommand(final String label, final View content, final boolean deleteOldComment) {
		super(label, content);
		this.deleteOldComment = deleteOldComment;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.stereotype.migration.commands.StereotypePropertiesMigrationCommand#updateStereotypePropertyDisplay(org.eclipse.gmf.runtime.notation.View, java.lang.String, java.lang.Enum)
	 *
	 */
	@Override
	protected void updateStereotypePropertyDisplay(final View view, final String propertyList, final Enum<?> location) {
		// In case of the Comment, the view will be the comment itself instead of the main View.
		if (StereotypeLocationEnum.IN_COMMENT.equals(location)) {
			Node newComment = helper.getStereotypeComment(view);
			if (null != newComment) {
				super.updateStereotypePropertyDisplay(newComment, propertyList, location);
				setConstraint(view, newComment);

				Edge stereotypeCommentLink = getStereotypeCommentLink(view, newComment);
				if (null != stereotypeCommentLink) {
					setBendpointsAnchors(view, stereotypeCommentLink);
				}
			}
		}

		if (deleteOldComment) {
			deleteOldComment(view);
		}
	}

	/**
	 * Get the comment stereotype link related with the comment
	 *
	 * @param view
	 * @param newComment
	 * @return
	 */
	private Edge getStereotypeCommentLink(final View view, final Node newComment) {
		EList<Edge> targetEdges = newComment.getTargetEdges();
		for (Edge edge : targetEdges) {
			if (helper.isStereotypeCommentLink(edge)) {
				if (edge.getSource().equals(view)) {
					return edge;
				}
			}
		}
		return null;
	}

	/**
	 * @param view
	 * @param newComment
	 */
	private void setConstraint(final View view, final Node newComment) {
		View oldComment = migrationHelper.getOldStereotypeComment(view);
		if (oldComment instanceof Node) {
			newComment.setLayoutConstraint(((Node) oldComment).getLayoutConstraint());
		}
	}

	/**
	 * To set the Bendpoints and the anchors (source and target) to the created comment link
	 *
	 * @param view
	 * @param edge
	 */
	private void setBendpointsAnchors(final View view, final Edge edge) {
		Edge oldStereotypeLinkComment = migrationHelper.getOldStereotypeLinkComment(view);
		if (null != oldStereotypeLinkComment) {
			edge.setBendpoints(oldStereotypeLinkComment.getBendpoints());
			edge.setSourceAnchor(oldStereotypeLinkComment.getSourceAnchor());
			edge.setTargetAnchor(oldStereotypeLinkComment.getTargetAnchor());
		}
	}

	/**
	 * @param view
	 */
	private void deleteOldComment(final View hostView) {
		// Delete Comment from the Old Structure
		View oldComment = migrationHelper.getOldStereotypeComment(hostView);
		if (oldComment != null) {
			DeleteCommand deleteComment = new DeleteCommand(oldComment);
			CommandUtil.executeUnsafeCommand(deleteComment, hostView);
		}
		Edge oldLink = migrationHelper.getOldStereotypeLinkComment(hostView);
		if (oldLink != null) {
			DeleteCommand deleteLink = new DeleteCommand(oldLink);
			CommandUtil.executeUnsafeCommand(deleteLink, hostView);
		}
	}
}

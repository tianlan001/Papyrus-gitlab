/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
package org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Graphics;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.Activator;


/**
 *
 * The editpart AppliedSterotypesCommentLinkEditPart, the link between the stereotyped element and the comment.
 *
 */

public class AppliedStereotypesCommentLinkEditPart extends ConnectionEditPart {

	public static final String ID = "AppliedStereotypesCommentLink";//$NON-NLS-1$

	public AppliedStereotypesCommentLinkEditPart(View view) {
		super(view);
	}

	/**
	 * Creates figure for this edit part.
	 *
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 *
	 * 
	 */
	@Override
	protected Connection createConnectionFigure() {
		return new AppliedStereotypesCommentLink();
	}

	/**
	 * 
	 */
	public AppliedStereotypesCommentLink getPrimaryShape() {
		return (AppliedStereotypesCommentLink) getFigure();
	}

	/**
	 * 
	 */
	public class AppliedStereotypesCommentLink extends PolylineConnectionEx {

		/**
		 * 
		 */
		public AppliedStereotypesCommentLink() {
			this.setLineStyle(Graphics.LINE_DASH);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart#refresh()
	 *
	 */
	@Override
	public void refresh() {

		try {
			getEditingDomain().runExclusive(new Runnable() {

				public void run() {
					refreshVisuals();
					refreshChildren();
					refreshSourceAnchor();
					refreshTargetAnchor();
					refreshSourceConnections();
					refreshTargetConnections();
				}
			});
		} catch (InterruptedException e) {
			Activator.log.error(e);

		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart#refreshVisuals()
	 *
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (this.getTarget() == null || this.getSource() == null) {
			setVisibility(false);
		}
	}

}

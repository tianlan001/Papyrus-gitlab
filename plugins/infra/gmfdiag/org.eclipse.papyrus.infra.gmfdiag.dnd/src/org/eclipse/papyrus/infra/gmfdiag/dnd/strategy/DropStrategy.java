/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.dnd.strategy;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.graphics.Image;

/**
 * A strategy to be applied when dropping elements
 *
 * @author Camille Letavernier
 */
public interface DropStrategy {

	/**
	 * A user-readable label
	 *
	 * @return
	 */
	public String getLabel();

	/**
	 * A user-readable description
	 *
	 * @return
	 */
	public String getDescription();

	/**
	 * An image to associate to this strategy
	 *
	 * @return
	 */
	public Image getImage();

	/**
	 * A unique ID for this strategy
	 *
	 * @return
	 */
	public String getID();

	/**
	 * The command to be executed when the strategy is applied.
	 * Should return null if the strategy cannot handle the request.
	 *
	 * @param request
	 *            The drop request
	 * @param targetEditPart
	 *            The target edit part
	 * @return
	 * 		A command, or null if the strategy cannot handle the request
	 */
	public Command getCommand(Request request, EditPart targetEditPart);

	/**
	 * The default priority for this strategy. Might be overridden by a user
	 * preference.
	 *
	 * @return
	 * @deprecated The priority mechanism isn't used anymore
	 */
	@Deprecated
	public int getPriority();

	/**
	 * When this strategy can handle this request, this method can be implemented
	 * to optionally display a visual feedback on the given edit part for this request.
	 *
	 * By default, compartments already support a feedback. Return true to override
	 * the default feedback, false to keep it.
	 *
	 * The feedback should always be erased in {@link #eraseTargetFeedback(Request, EditPart)}
	 *
	 * @param request
	 * @param targetEditPart
	 *
	 * @return true if the default feedback should be overridden, false if it should be preserved
	 *
	 * @since 1.3
	 */
	public default boolean showTargetFeedback(Request request, EditPart targetEditPart) {
		return false;
	}

	/**
	 * Erase any feedback added via {@link #showTargetFeedback(Request, EditPart)}. Note that this method
	 * may be invoked even if {@link #showTargetFeedback(Request, EditPart)} was never called.
	 *
	 * @param request
	 * @param targetEditPart
	 * @return
	 *
	 * @since 1.3
	 */
	public default boolean eraseTargetFeedback(Request request, EditPart targetEditPart) {
		return false;
	}

}

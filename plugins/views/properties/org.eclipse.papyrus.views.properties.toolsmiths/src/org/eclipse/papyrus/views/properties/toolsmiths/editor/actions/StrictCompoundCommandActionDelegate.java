/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import org.eclipse.emf.common.command.StrictCompoundCommand;
import org.eclipse.emf.edit.command.CommandActionDelegate;
import org.eclipse.swt.graphics.Image;

/**
 * An EMF Compound Command with support for Images, Tooltip text and description
 * (From CommandActionDelegate)
 * The first sub-command should be a CommandActionDelegate
 *
 * @author Camille Letavernier
 */
public class StrictCompoundCommandActionDelegate extends StrictCompoundCommand implements CommandActionDelegate {

	private Image image;

	public StrictCompoundCommandActionDelegate() {
		super.isPessimistic = true;
	}

	/**
	 * Sets the Image for this command
	 *
	 * @param image
	 *            The image to display on the command
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public Object getImage() {
		if (image == null) {
			return ((CommandActionDelegate) getCommandList().get(0)).getImage();
		} else {
			return image;
		}
	}

	@Override
	public String getText() {
		return ((CommandActionDelegate) getCommandList().get(0)).getText();
	}

	@Override
	public String getToolTipText() {
		return ((CommandActionDelegate) getCommandList().get(0)).getToolTipText();
	}

	@Override
	public String getDescription() {
		return ((CommandActionDelegate) getCommandList().get(0)).getDescription();
	}

}

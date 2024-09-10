/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.ui.properties.providers;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.papyrus.uml.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.views.properties.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Label provider for {@link StereotypeToApply} Object.
 */
public class StereotypeToApplyLabelProvider extends EMFLabelProvider implements ILabelProvider {

	/** The stereotype icon path. */
	private static final String ICONS_STEREOTYPE_GIF = "/icons/stereotype.gif";//$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(final Object element) {
		String text = null;
		if (element instanceof StereotypeToApply) {
			StereotypeToApply stereotypeToApply = (StereotypeToApply) element;
			text = stereotypeToApply.getStereotypeQualifiedName();
		} else {
			text = super.getText(element);
		}
		return null != text ? text : Messages.undefined;
	}

	/**
	 * Return the image of stereotype.
	 * 
	 * @see org.eclipse.papyrus.infra.ui.emf.providers.EMFLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object element) {
		Image image = null;
		if (element instanceof StereotypeToApply) {
			image = Activator.getDefault().getImage("org.eclipse.papyrus.uml.diagram.common", ICONS_STEREOTYPE_GIF);//$NON-NLS-1$
		} else {
			image = super.getImage(element);
		}
		return image;
	}

}

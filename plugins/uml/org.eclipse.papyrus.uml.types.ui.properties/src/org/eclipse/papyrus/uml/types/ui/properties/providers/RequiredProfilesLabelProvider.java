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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.ui.properties.providers;

import java.util.Iterator;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.papyrus.views.properties.Activator;
import org.eclipse.swt.graphics.Image;

/**
 * Label Provider to add profile icon to profile name.
 */
public class RequiredProfilesLabelProvider extends LabelProvider {

	/** The default profile icon */
	protected static final String ICONS_PROFILE_GIF = "/icons/profile.gif";//$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(final Object element) {
		Image image = null;

		if (element instanceof String) {

			Iterator<IRegisteredProfile> registeredProfiles = RegisteredProfile.getRegisteredProfiles().iterator();
			while (null == image && registeredProfiles.hasNext()) {
				IRegisteredProfile iRegisteredProfile = (IRegisteredProfile) registeredProfiles.next();
				iRegisteredProfile.getName();
				if (element.equals(iRegisteredProfile.getName())) {
					image = iRegisteredProfile.getImage();
				}
			}

			if (null == image) {
				image = Activator.getDefault().getImage("org.eclipse.papyrus.uml.diagram.common", ICONS_PROFILE_GIF);//$NON-NLS-1$
			}

		}
		return image;
	}


}

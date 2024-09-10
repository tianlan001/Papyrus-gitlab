/*******************************************************************************
 * Copyright (c) 2013, 2014 Atos, CEA, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 *     Christian W. Damus (CEA) - bug 410346
 *
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;

/**
 * General label helper
 *
 * @author adaussy
 *
 */
public class LabelHelper {

	/**
	 * 
	 */
	private static final String LABEL_ERROR = Messages.getString("LabelHelper.label.error"); //$NON-NLS-1$

	/**
	 * Return an user understandable label for an {@link EObject}
	 *
	 * @param eObject
	 * @return
	 */
	public static String getPrettyLabel(EObject eObject) {
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

		try {
			IItemLabelProvider itemLabelProvider = (IItemLabelProvider) adapterFactory.adapt(eObject, IItemLabelProvider.class);
			if (itemLabelProvider != null) {
				return itemLabelProvider.getText(eObject);
			}
		} finally {
			adapterFactory.dispose();
		}

		return LABEL_ERROR;
	}


}

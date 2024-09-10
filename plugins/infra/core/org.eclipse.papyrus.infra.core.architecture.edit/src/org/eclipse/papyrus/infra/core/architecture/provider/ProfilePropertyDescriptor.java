/*****************************************************************************
 * Copyright (c) 2013, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  Christian W. Damus - bug 570486
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.architecture.provider;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * Represents a descriptor for properties of type EList of EPackage
 * This class lists the currently loaded ECore metamodels.
 *
 * @author Laurent Wouters
 */
public class ProfilePropertyDescriptor extends EPackagePropertyDescriptor {
	public ProfilePropertyDescriptor(IItemPropertyDescriptor inner) {
		super(inner);
	}

	@Override
	public IItemLabelProvider getLabelProvider(Object object) {
		return new IItemLabelProvider() {
			@Override
			public String getText(Object object) {
				if (object instanceof EPackage) {
					return ((EPackage) unwrap(object)).getNsURI();
				}
				if (object instanceof EList<?>) {
					EList<?> list = (EList<?>) object;
					if (list.isEmpty()) {
						return "";
					}
					StringBuilder builder = new StringBuilder("{");
					for (int i = 0; i != list.size(); i++) {
						if (i != 0) {
							builder.append(", ");
						}
						builder.append(((EPackage) unwrap(list.get(i))).getNsURI());
					}
					builder.append("}");
					return builder.toString();
				}
				return "";
			}

			@Override
			public Object getImage(Object object) {
				return null;
			}
		};
	}
}

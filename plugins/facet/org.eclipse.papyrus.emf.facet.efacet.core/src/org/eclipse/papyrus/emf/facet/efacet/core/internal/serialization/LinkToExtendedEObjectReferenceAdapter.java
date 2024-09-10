/*******************************************************************************
 * Copyright (c) 2011 Mia-Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Emmanuelle Rouillé (Mia-Software) - Bug 352618 - To be able to use non derived facet structural features and save them values.
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.core.internal.serialization;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.serialization.ExtendedEObjectReference;

public class LinkToExtendedEObjectReferenceAdapter extends AdapterImpl implements
		ILinkToExtendedEObjectReference {

	private ExtendedEObjectReference extendedEObjectReference;

	public ExtendedEObjectReference getExtendedEObjectReference() {
		return this.extendedEObjectReference;
	}

	public void setExtendedEObjectReference(final ExtendedEObjectReference extendedEObjectReference) {
		this.extendedEObjectReference = extendedEObjectReference;
	}

	@Override
	public boolean isAdapterForType(final Object type) {
		return type == ILinkToExtendedEObjectReference.class;
	}

}

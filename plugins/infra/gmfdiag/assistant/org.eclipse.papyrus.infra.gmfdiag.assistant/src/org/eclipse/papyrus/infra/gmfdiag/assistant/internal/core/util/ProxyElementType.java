/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.util;

import java.net.URL;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.emf.type.core.SpecializationType;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.util.IProxyElementType;

import com.google.common.base.Objects;

/**
 * Default implementation of the proxy element type.
 */
public class ProxyElementType extends SpecializationType implements IProxyElementType {
	private final IElementType semanticType;
	private final IHintedType visualType;

	private ProxyElementType(IElementType semanticType, IHintedType visualType, String name) {
		// Spoof the semantic type's ID in order to get the correct editing behaviour from the Element Edit Service
		super(semanticType.getId(), semanticType.getIconURL(), name, new IElementType[] { visualType, semanticType }, null, null, null);

		this.semanticType = semanticType;
		this.visualType = visualType;
	}

	public static ProxyElementType create(IElementType semanticType, IHintedType visualType, boolean distinguishVisualType) {
		String name = semanticType.getDisplayName();

		if (distinguishVisualType) {
			name = NLS.bind("{0} ({1})", name, visualType.getDisplayName());
		}

		return new ProxyElementType(semanticType, visualType, name);
	}

	@Override
	public String getSemanticHint() {
		return visualType.getSemanticHint();
	}

	@Override
	public URL getIconURL() {
		URL result = super.getIconURL();

		if (result == null) {
			// Try the visual type
			result = visualType.getIconURL();

			if (result == null) {
				// Look for the nearest supertype that has an icon
				IElementType[] allSupertypes = getAllSuperTypes();
				for (int i = allSupertypes.length - 1; (result == null) && (i >= 0); i--) {
					result = allSupertypes[i].getIconURL();
				}
			}
		}

		return result;
	}

	@Override
	public IElementType resolveSemanticType() {
		return semanticType;
	}

	@Override
	public IHintedType resolveVisualType() {
		return visualType;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId(), getSemanticHint());
	}

	/**
	 * Because these element type implementations are ephemeral, we create them repeatedly
	 * and in order for some maps used by CreateUnspecifiedConnectionTypeRequest and other
	 * classes to work, we need to implement equality.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = obj instanceof ProxyElementType;
		if (result) {
			ProxyElementType other = (ProxyElementType) obj;
			result = Objects.equal(other.getId(), getId()) && Objects.equal(other.getSemanticHint(), getSemanticHint());
		}
		return result;
	}
}

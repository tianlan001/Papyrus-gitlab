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

package org.eclipse.papyrus.infra.core.language;

import org.eclipse.papyrus.infra.core.resource.ModelSet;

/**
 * Default implementation of a language. This implementation does nothing when {@linkplain #install(ModelSet) installed} on a model-set.
 */
public class Language implements ILanguage {
	private final String id;
	private final Version version;
	private final String name;

	public Language(String id, Version version, String name) {
		super();

		this.id = id;
		this.version = version;
		this.name = name;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public Version getVersion() {
		return version;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void install(ModelSet modelSet) {
		// Pass
	}

	@Override
	public void uninstall(ModelSet modelSet) {
		// Pass
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ILanguage)) {
			return false;
		}
		ILanguage other = (ILanguage) obj;
		if (id == null) {
			if (other.getID() != null) {
				return false;
			}
		} else if (!id.equals(other.getID())) {
			return false;
		}
		if (version == null) {
			if (other.getVersion() != null) {
				return false;
			}
		} else if (!version.equals(other.getVersion())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s version %s", getName(), getVersion()); //$NON-NLS-1$
	}
}

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Strings;

/**
 * A {@linkplain ILanguage language} version.
 */
public final class Version implements Comparable<Version> {
	private final Pattern VERSION_PATTERN = Pattern.compile("^(-?\\d+)(\\.\\d+)?(\\.\\d+)?(\\.[A-Za-z0-9_+\\-]+)?$"); //$NON-NLS-1$

	public static final Version ZERO = new Version(0, 0, 0, null);
	public static final Version ONE = new Version(1, 0, 0, null);

	private final int major;
	private final int minor;
	private final int service;
	private final String qualifier;

	/**
	 * Initializes me.
	 *
	 * @param major
	 *            my major increment. Others are zero
	 */
	public Version(int major) {
		this(major, 0, 0, null);
	}

	/**
	 * Initializes me.
	 *
	 * @param major
	 *            my major increment
	 * @param minor
	 *            my minor increment. Must be a whole number
	 * 
	 * @throws IllegalArgumentException
	 *             if the {@code minor} segment is negative
	 */
	public Version(int major, int minor) {
		this(major, minor, 0, null);
	}

	/**
	 * Initializes me.
	 *
	 * @param major
	 *            my major increment
	 * @param minor
	 *            my minor increment. Must be a whole number
	 * @param service
	 *            my service increment. Must be a whole number
	 * 
	 * @throws IllegalArgumentException
	 *             if any {@code minor}/{@code service} segment is negative
	 */
	public Version(int major, int minor, int service) {
		this(major, minor, service, null);
	}

	/**
	 * Initializes me.
	 *
	 * @param major
	 *            my major increment
	 * @param minor
	 *            my minor increment. Must be a whole number
	 * @param service
	 *            my service increment. Must be a whole number
	 * @param qualifier
	 *            my qualifier (may be {@code null} if not needed, but not empty)
	 * 
	 * @throws IllegalArgumentException
	 *             if any {@code minor}/{@code service} segment is negative or if the qualifier is the empty string
	 */
	public Version(int major, int minor, int service, String qualifier) {
		super();

		if (minor < 0) {
			throw new IllegalArgumentException("Negative minor segment"); //$NON-NLS-1$
		}
		if (service < 0) {
			throw new IllegalArgumentException("Negative service segment"); //$NON-NLS-1$
		}
		if ("".equals(qualifier)) { //$NON-NLS-1$
			throw new IllegalArgumentException("Empty qualifier; should be null"); //$NON-NLS-1$
		}

		this.major = major;
		this.minor = minor;
		this.service = service;
		this.qualifier = qualifier;
	}

	/**
	 * Initializes me from my string representation.
	 *
	 * @param versionSpec
	 *            my string representation
	 * 
	 * @throws IllegalArgumentException
	 *             if the string representation has errors in any segment
	 */
	public Version(String versionSpec) {
		super();

		Matcher m = VERSION_PATTERN.matcher(versionSpec);
		if (!m.matches()) {
			throw new IllegalArgumentException("Invalid version specification: " + versionSpec); //$NON-NLS-1$
		}

		this.major = Integer.parseInt(m.group(1));
		this.minor = m.group(2) == null ? 0 : Integer.parseInt(m.group(2).substring(1));
		this.service = m.group(3) == null ? 0 : Integer.parseInt(m.group(3).substring(1));
		this.qualifier = m.group(3) == null ? null : m.group(3).substring(1);
	}

	public int major() {
		return major;
	}

	public int minor() {
		return minor;
	}

	public int service() {
		return service;
	}

	public String qualifier() {
		return qualifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + major;
		result = prime * result + minor;
		result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
		result = prime * result + service;
		return result;
	}

	@Override
	public String toString() {
		return (qualifier == null)
				? String.format("%s.%s.%s", major, minor, service) //$NON-NLS-1$
				: String.format("%s.%s.%s.%s", major, minor, service, qualifier); //$NON-NLS-1$
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Version)) {
			return false;
		}
		Version other = (Version) obj;
		if (major != other.major) {
			return false;
		}
		if (minor != other.minor) {
			return false;
		}
		if (qualifier == null) {
			if (other.qualifier != null) {
				return false;
			}
		} else if (!qualifier.equals(other.qualifier)) {
			return false;
		}
		if (service != other.service) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Version o) {
		int result = o.major - this.major;
		if (result == 0) {
			result = o.minor - this.minor;
			if (result == 0) {
				result = o.service - this.service;
				if (result == 0) {
					result = Strings.nullToEmpty(o.qualifier).compareTo(Strings.nullToEmpty(this.qualifier));
				}
			}
		}
		return result;
	}

	public static org.osgi.framework.Version toOSGI(Version version) {
		return new org.osgi.framework.Version(version.major, version.minor, version.service, version.qualifier);
	}

	public static Version fromOSGI(org.osgi.framework.Version version) {
		return new Version(version.getMajor(), version.getMinor(), version.getMicro(), Strings.emptyToNull(version.getQualifier()));
	}
}

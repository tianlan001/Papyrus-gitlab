/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.utils;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.Package;

/**
 * Some utils method to manipulate UML Package
 *
 */
public class PackageUtils {

	/**
	 *
	 * @param package_
	 *            a package
	 * @param packageURI
	 *            the expected package URI (can't be <code>null</code> of empty
	 * @param expectedPackageEClass
	 *            the expected EClass (UMLPackage.eInstance.getPackage()/getModel()/getProfile()
	 * @return
	 *         the found {@link Package} or null
	 */
	public static final Package findPackageFromURI(final Package package_, final String packageURI, final EClass expectedPackageEClass) {
		if (null == packageURI || packageURI.isEmpty() || null == package_) {
			return null;
		}
		if (packageURI.equals(package_.getURI())
				&& (null == expectedPackageEClass || expectedPackageEClass == package_.eClass())) {
			return package_;
		}
		final Iterator<Package> packIter = package_.getNestedPackages().iterator();
		Package foundPackage = null;
		while (packIter.hasNext() && null == foundPackage) {
			foundPackage = findPackageFromURI(packIter.next(), packageURI, expectedPackageEClass);
		}
		return foundPackage;
	}

	/**
	 *
	 * @param <T>
	 * @param package_
	 *            a package
	 * @param packageURI
	 *            the expected package URI (can't be <code>null</code> of empty
	 * @param expectedPackageEClass
	 *            the expected type (Profile, Package or Model)
	 * @return
	 *         the found {@link Package} or null
	 */
	public static final <T> T findPackageFromURI(final Package package_, final String packageURI, final Class<T> expectedType) {
		if (null == packageURI || packageURI.isEmpty() || null == package_) {
			return null;
		}
		if (packageURI.equals(package_.getURI()) && expectedType.isInstance(package_)) {
			return expectedType.cast(package_);
		}
		final Iterator<Package> packIter = package_.getNestedPackages().iterator();
		T foundPackage = null;
		while (packIter.hasNext() && null == foundPackage) {
			foundPackage = findPackageFromURI(packIter.next(), packageURI, expectedType);
		}
		return foundPackage;
	}
}

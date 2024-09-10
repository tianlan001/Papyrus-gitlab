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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.edit.internal.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This contents provider allows to navigate until to find Profiles
 */
public class ProfileTreeContentProvider implements ITreeContentProvider {

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(final Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return ((Collection<?>) inputElement).toArray();
		}
		if (inputElement instanceof Object[]) {
			return (Object[]) inputElement;
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(final Object parentElement) {
		if (parentElement instanceof Namespace) {
			final Iterator<NamedElement> namedElementIter = ((Namespace) parentElement).getOwnedMembers().iterator();
			final Collection<Element> children = new ArrayList<>();
			while (namedElementIter.hasNext()) {
				final NamedElement current = namedElementIter.next();
				if (isValid(current)) {
					children.add(current);
				}
			}
			return children.toArray();
		}
		return new Object[0];
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(final Object element) {
		if (element instanceof EObject) {
			return ((EObject) element).eContainer();
		}
		return null;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(final Object element) {
		if (element instanceof Stereotype) {
			return false;
		}
		if (element instanceof EObject) {
			return isValid((EObject) element);
		}
		return false;
	}

	/**
	 *
	 * @param pack
	 *            a UML Package
	 * @return
	 *         <code>true</code> if there is a {@link Stereotype} in the contents/subcontents of the Package
	 */
	protected final boolean hasProfileInHierarchy(final Package pack) {
		final Iterator<NamedElement> namedElementIter = pack.getOwnedMembers().iterator();
		while (namedElementIter.hasNext()) {
			final NamedElement current = namedElementIter.next();
			if (isValid(current)) {
				return true;
			}

		}
		return false;
	}

	/**
	 *
	 * @param eobject
	 *            an eobject
	 * @return
	 *         <code>true</code> if the element has an interesting element to display
	 */
	protected boolean isValid(final EObject eobject) {
		if (eobject instanceof Profile) {
			return true;
		}
		if (eobject instanceof Package) {
			return (hasProfileInHierarchy((Package) eobject));
		}
		return false;
	}
}
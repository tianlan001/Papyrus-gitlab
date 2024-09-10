/*****************************************************************************
 * Copyright (c) 2020 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider;
import org.eclipse.papyrus.uml.tools.util.StereotypeUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 *
 * This content providers is used to get the operations of stereotypes
 *
 * @since 4.3
 *
 */
public class UMLStereotypeOperationContentProvider implements IHierarchicContentProvider, IInheritedElementContentProvider, IOperationContentProvider {

	/**
	 * the profiles
	 */
	protected List<Profile> profiles;

	/**
	 * if <code>true</code> we don't return the inherited properties
	 */
	private boolean ignoreInheritedOperation;

	private boolean ignoreVoidOperations;

	private boolean ignoreOperationsWithParameters;

	/**
	 *
	 * Constructor.
	 *
	 * @param profiles
	 *            the profiles to navigate
	 *
	 *            the boolean fields are initialized to false
	 */
	public UMLStereotypeOperationContentProvider(final List<Profile> profiles) {
		this.profiles = profiles;
		this.ignoreInheritedOperation = false;
		this.ignoreOperationsWithParameters = false;
		this.ignoreVoidOperations = false;
	}

	/**
	 *
	 * Constructor.
	 *
	 */
	public UMLStereotypeOperationContentProvider() {
		this(null);
	}


	/**
	 *
	 * @return
	 */
	public Object[] getElements() {
		return this.profiles.toArray();
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getElements();
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 *
	 * @param parentElement
	 * @return
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> children = new ArrayList<>();
		if (hasChildren(parentElement)) {
			if (parentElement instanceof Package) {
				for (final EObject current : ((Package) parentElement).getOwnedMembers()) {
					if (hasChildren(current)) {
						children.add(current);
					}
				}
			} else if (parentElement instanceof Stereotype) {
				children.addAll(StereotypeUtil.getAllStereotypeOperations((Stereotype) parentElement, this.ignoreVoidOperations, this.ignoreOperationsWithParameters, this.ignoreInheritedOperation));
			}
		}
		return children.toArray();
	}



	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof EObject) {
			return ((EObject) element).eContainer();
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Element) {
			final TreeIterator<EObject> iter = ((EObject) element).eAllContents();
			while (iter.hasNext()) {
				if (isValidValue(iter.next())) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void dispose() {
		profiles.clear();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider#isValidValue(java.lang.Object)
	 *
	 * @param element
	 *            an element
	 * @return
	 *         <code>true</code> if the element is a Property owned by a Stereotype
	 */
	@Override
	public boolean isValidValue(Object element) {
		if (element instanceof Element) {
			return element instanceof Operation && ((Element) element).eContainer() instanceof Stereotype;
		}
		return false;
	}

	/**
	 *
	 * @param profiles
	 *            the list of the profiles to navigate
	 */
	public void setProfiles(final List<Profile> profiles) {
		this.profiles = profiles;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider#setIgnoreInheritedElements(boolean)
	 *
	 * @param ignoreInheritedElements
	 */
	@Override
	public void setIgnoreInheritedElements(final boolean ignoreInheritedElements) {
		this.ignoreInheritedOperation = ignoreInheritedElements;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.IInheritedElementContentProvider#isIgnoringInheritedElements()
	 *
	 * @return
	 */
	@Override
	public boolean isIgnoringInheritedElements() {
		return this.ignoreInheritedOperation;
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.providers.IOperationContentProvider#setIgnoredOperationsWithParameters(boolean)
	 *
	 * @param ignoreOperationsWithParameters
	 */
	@Override
	public void setIgnoredOperationsWithParameters(boolean ignoreOperationsWithParameters) {
		this.ignoreOperationsWithParameters = ignoreOperationsWithParameters;
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.providers.IOperationContentProvider#setIgnoreVoidOperations(boolean)
	 *
	 * @param ignoreVoidOperation
	 */
	@Override
	public void setIgnoreVoidOperations(boolean ignoreVoidOperation) {
		this.ignoreVoidOperations = ignoreVoidOperation;
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.providers.IOperationContentProvider#isIgnoringOperationsWithParameters()
	 *
	 * @return
	 */
	@Override
	public boolean isIgnoringOperationsWithParameters() {
		return this.ignoreOperationsWithParameters;
	}

	/**
	 * @see org.eclipse.papyrus.uml.tools.providers.IOperationContentProvider#isIgnoringVoidOperations()
	 *
	 * @return
	 */
	@Override
	public boolean isIgnoringVoidOperations() {
		return this.ignoreVoidOperations;
	}

}

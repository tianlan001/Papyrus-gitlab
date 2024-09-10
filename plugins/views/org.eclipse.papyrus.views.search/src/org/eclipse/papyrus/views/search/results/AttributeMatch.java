/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST.
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
 *  CEA LIST - Initial API and implementation
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.search.results;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;
import org.eclipse.ui.PartInitException;
import org.eclipse.uml2.uml.Stereotype;

/**
 *
 * A match raised by the value of an attribute
 *
 */
public class AttributeMatch extends ModelMatch {

	/**
	 * The element containing the value of the attribute that matches
	 */



	protected Object attribute;

	private Stereotype stereotype;



	/**
	 * Similar to {@link AbstractResultEntry} but adds an information about which attribute raised the match and which element contains the value of
	 * the attribute that matches
	 *
	 * @param offset
	 * @param lenght
	 * @param target
	 *            the element containing the value of the attribute that matches
	 * @param scopeEntry
	 * @param attribute
	 *            the attribute that raised the match
	 */
	public AttributeMatch(int offset, int lenght, Object target, IScopeEntry scopeEntry, Object attribute, Stereotype stereotype) {
		super(offset, lenght, target, scopeEntry);
		this.attribute = attribute;
		this.stereotype = stereotype;

		// if(target instanceof EObject) {
		// this.uriSource = EcoreUtil.getURI((EObject)target);
		// }
		this.parent = new ResultEntry(target, scopeEntry);
		((ResultEntry) this.parent).setParent(new ResultEntry(scopeEntry.getResourceURI(), scopeEntry));

		// recursiveHierarchy((AbstractResultEntry) parent);
	}

	/**
	 * Create an attribute-match in a parent result entry that already exists.
	 *
	 * @param parent
	 *            the parent result entry
	 * @param offset
	 * @param lengtt
	 * @param target
	 *            the element containing the value of the attribute that matches
	 * @param attribute
	 *            the attribute that raised the match
	 */
	public AttributeMatch(AbstractResultEntry parent, int offset, int length, Object target, Object attribute, Stereotype stereotype) {
		super(offset, length, target, (IScopeEntry) parent.getElement());

		this.attribute = attribute;
		this.stereotype = stereotype;
		this.parent = parent;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#equals(java.lang.Object)
	 *      In addition, it checks if target is the same as well.
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AttributeMatch) {
			if (super.equals(obj)) {
				if (((AttributeMatch) obj).getSource() instanceof EObject && this.getSource() instanceof EObject) {
					if (EcoreUtil.getURI((EObject) ((AttributeMatch) obj).getSource()).equals(EcoreUtil.getURI((EObject) this.getSource()))) {
						if (obj.hashCode() == this.hashCode()) {
							return true;
						}
					}
				} else {
					if (((AttributeMatch) obj).getSource().equals(this.getSource())) {
						if (obj.hashCode() == this.hashCode()) {
							return true;
						}
					}
				}
			}
			return false;

		} else {
			return false;
		}
	}

	/**
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#hashCode()
	 *
	 *      Adds the identity hash since two instances of match with same offset and length will have
	 *      the same hash code, although they should not if they are two different attributes.
	 *
	 */
	@Override
	public int hashCode() {
		return super.hashCode() + System.identityHashCode(this);
	}

	// public Object getTarget() {
	// // ResourceSet resSet = new ResourceSetImpl();
	// if(this.uriSource != null) {
	// ResourceSet resSet = ((ScopeEntry)this.getElement()).getModelSet();
	// return resSet.getEObject(this.uriSource, true);
	// }
	// return null;
	//
	// }
	//
	// public void setTarget(Object target) {
	// if(target instanceof EObject) {
	// this.uriSource = EcoreUtil.getURI((EObject)target);
	// }
	//
	// }

	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#elementToDisplay()
	 *
	 * @return
	 */
	@Override
	public Object elementToDisplay() {
		return this;

	}

	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#elementToCheckFilterFor()
	 *
	 * @return
	 */
	@Override
	public Object elementToCheckFilterFor() {
		return this.getSource();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.views.search.results.AbstractResultEntry#openElement()
	 *
	 * @return
	 */
	@Override
	public Object openElement(OpenElementService service) throws ServiceException, PartInitException {
		if (this.getSource() instanceof EObject) {
			return service.openSemanticElement((EObject) this.getSource());
		}
		return null;
	}

	public Object getMetaAttribute() {

		return this.attribute;
	}

	public Stereotype getStereotype() {
		return stereotype;
	}


	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	public URI getURISource() {

		return uriSource;
	}

}

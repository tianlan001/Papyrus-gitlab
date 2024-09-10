/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.m2m.qvto.tests.tools.tests;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.utils.EqualityHelper;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.constraints.constraints.TrueConstraint;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.ProfileApplication;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.TemplateParameterSubstitution;

import com.google.common.cache.LoadingCache;

/**
 * This allows to manage the ignoring identified objects of UML.
 */
public class UMLIgnoringIdentifiedEqualityHelper extends EqualityHelper {

	/**
	 * Constructor.
	 *
	 * @param uriCache The uris in cache.
	 */
	public UMLIgnoringIdentifiedEqualityHelper(final LoadingCache<EObject, URI> uriCache) {
		super(uriCache);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.utils.EqualityHelper#matchingEObjects(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected boolean matchingEObjects(final EObject object1, final EObject object2) {
		final EClass eClass1 = object1.eClass();
		final EClass eClass2 = object2.eClass();

		boolean result = false;
		if (eClass1 == eClass2) {
			if (object1.eIsProxy() && object2.eIsProxy()) {
				result = super.matchingEObjects(object1, object2);
			} else if (object1 instanceof NamedElement && object2 instanceof NamedElement) {
				result = matchingNamedElement((NamedElement) object1, (NamedElement) object2);
			} else if (object1 instanceof Element && object2 instanceof Element) {
				result = matchingElement((Element) object1, (Element) object2);
			} else if (EcorePackage.eINSTANCE == object1.eClass().getEPackage()) {
				result = super.matchingEObjects(object1, object2);
			} else {
				result = super.matchingEObjects(object1, object2);
			}
		}
		return result;
	}

	/**
	 * This allows to check if two named elements are matching.
	 * 
	 * @param object1
	 *            The first named element.
	 * @param object2
	 *            The second named element.
	 * @return
	 * 		{@link TrueConstraint} if the 2 named element have the same qualified name.
	 */
	protected boolean matchingNamedElement(final NamedElement object1, final NamedElement object2) {
		boolean result = false;
		final String qn1 = ((NamedElement) object1).getQualifiedName();
		final String qn2 = ((NamedElement) object2).getQualifiedName();
		if (null == qn1 && null == qn2) {
			// when there is no name on a named element
			result = matchingValues(object1.eContainer(), object2.eContainer());
		}
		if (null != qn1 && null != qn2) {
			result = qn1.equals(qn2);
		}
		return result;
	}

	/**
	 * This allows to check if two UML elements are matching.
	 * 
	 * @param object1
	 *            The first UML element.
	 * @param object2
	 *            The second UML element.
	 * @return <code>true</code> if the two UML elements are matching, <code>false</code> otherwise.
	 * 
	 */
	protected boolean matchingElement(final Element object1, final Element object2) {
		boolean result = false;
		final EObject parent1 = object1.eContainer();
		final EObject parent2 = object2.eContainer();

		// we check owner
		if (matchingValues(parent1, parent2)) {
			if (object1 instanceof PackageImport && object2 instanceof PackageImport) {
				final Package importedPackage1 = ((PackageImport) object1).getImportedPackage();
				final Package importedPackage2 = ((PackageImport) object2).getImportedPackage();
				result = importedPackage1.getQualifiedName().equals(importedPackage2.getQualifiedName());
			} else if (object1 instanceof ProfileApplication && object2 instanceof ProfileApplication) {
				final ProfileApplication prof1 = (ProfileApplication) object1;
				final ProfileApplication prof2 = (ProfileApplication) object2;
				final Profile p1 = prof1.getAppliedProfile();
				final Profile p2 = prof2.getAppliedProfile();
				final String p1Qname = p1.getQualifiedName();
				final String p2Qname = p2.getQualifiedName();
				if (p1Qname != null) {
					result = p1Qname.equals(p2Qname);
				}
			} else if (object1 instanceof ConnectorEnd && object2 instanceof ConnectorEnd) {
				final ConnectorEnd conn1 = (ConnectorEnd) object1;
				final ConnectorEnd conn2 = (ConnectorEnd) object2;
				final Element role1 = conn1.getRole();
				final Element role2 = conn2.getRole();
				boolean roleMatching = matchingValues(role1, role2);
				if (roleMatching) {
					final Element partWithPort1 = conn1.getPartWithPort();
					final Element partWithPort2 = conn2.getPartWithPort();
					result = matchingValues(partWithPort1, partWithPort2);
				}
				// }
			} else if (object1 instanceof Comment && object2 instanceof Comment) {
				final String body1 = ((Comment) object1).getBody();
				final String body2 = ((Comment) object2).getBody();
				if (body1 != null) {
					result = body1.equals(body2);
				}
			} else if (object1 instanceof TemplateBinding && object2 instanceof TemplateBinding) {
				// owner already checked
				result = true;
			} else if (object1 instanceof TemplateParameterSubstitution && object2 instanceof TemplateParameterSubstitution) {
				// owner already checked
				result = true;
			}
		}
		return result;
	}

}

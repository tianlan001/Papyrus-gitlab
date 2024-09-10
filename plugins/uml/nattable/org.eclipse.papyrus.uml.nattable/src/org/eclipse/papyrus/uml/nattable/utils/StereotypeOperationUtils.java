/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
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

package org.eclipse.papyrus.uml.nattable.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 * This class provides useful methods for operations of stereotypes
 *
 * @since 5.4
 *
 */
public class StereotypeOperationUtils {

	/**
	 * The prefix used to identify the operations of stereotype in the table axis
	 */
	public static final String OPERATION_OF_STEREOTYPE_PREFIX = "operation_of_stereotype:/"; //$NON-NLS-1$


	/**
	 *
	 * @param tableContext
	 *            the context of the table
	 * @param id
	 *            the id of the operation
	 * @return
	 *         the stereotype operation
	 */
	public static Object getRealStereotypeOperation(final EObject tableContext, final String id) {
		Operation result = null;
		Assert.isTrue(id.startsWith(OPERATION_OF_STEREOTYPE_PREFIX));
		if (tableContext instanceof Element) {
			final Element element = (Element) tableContext;
			final String operationQN = id.replace(OPERATION_OF_STEREOTYPE_PREFIX, ""); //$NON-NLS-1$

			final Package nearestPackage = element.getNearestPackage();
			if (null != nearestPackage) {

				result = UMLUtil.<Operation> findNamedElements(nearestPackage.eResource().getResourceSet(), operationQN, false, UMLPackage.eINSTANCE.getOperation())
						.stream()
						.findFirst()
						.orElse(null);


				// TODO should we still keep that? FindNamedElement browse all the resourceSet.
				// 2. if not, the profile could be applied on a sub-package of the nearest package
				/* the table can show element which are not children of its context, so the profile could not be available in its context */
				if (null == result) {
					result = getOperation(element.getNearestPackage().getNestedPackages(), operationQN);
				}
			}

		}
		return result;
	}

	/**
	 *
	 * @param packages
	 *            a collection of packages
	 * @param operationQN
	 *            the qualified name of the operation
	 * @return
	 *         the first found operation with the expected qualified name
	 */
	public static Operation getOperation(final Collection<Package> packages, final String operationQN) {
		final String operationName = NamedElementUtil.getNameFromQualifiedName(operationQN);
		final String stereotypeQN = NamedElementUtil.getParentQualifiedName(operationQN);
		final String stereotypeName = NamedElementUtil.getNameFromQualifiedName(stereotypeQN);
		final String profileQN = NamedElementUtil.getParentQualifiedName(stereotypeQN);
		for (Package package1 : packages) {
			for (Profile prof : package1.getAppliedProfiles()) {
				if (prof.getQualifiedName().equals(profileQN)) {
					NamedElement ste = prof.getMember(stereotypeName);
					if (ste instanceof Stereotype) {
						NamedElement prop = ((Stereotype) ste).getMember(operationName);
						if (prop instanceof Operation && prop.getQualifiedName().equals(operationQN)) {
							return (Operation) prop;
						}
					}
				}
				Operation p = getOperation(package1.getNestedPackages(), operationQN);
				if (p != null) {
					return p;
				}
			}
		}
		return null;
	}


	/**
	 *
	 * @param element
	 *            the UML::Element on which we are looking for applied Stereotype with the operation identified by its id
	 * @param id
	 *            the id used to identify the operation of the stereotype
	 *
	 * @return
	 *         the list of UML::Stereotype which have the operation identified by this id and which are applied on the Element or <code>null</code> if
	 *         we can't resolve it (the required profile is not applied)
	 */
	public static final List<Stereotype> getAppliedStereotypesWithThisOperation(final Element element, final String id) {
		Assert.isTrue(id.startsWith(OPERATION_OF_STEREOTYPE_PREFIX));
		final List<Stereotype> stereotypes = new ArrayList<>();
		if (element.eResource() != null) {
			final Object operation = getRealStereotypeOperation(element, id);
			if (operation instanceof Operation) {
				for (final Stereotype current : element.getAppliedStereotypes()) {
					if (current.getAllOperations().contains(operation)) {
						stereotypes.add(current);
					}
				}
			}
		}
		return stereotypes;
	}


}

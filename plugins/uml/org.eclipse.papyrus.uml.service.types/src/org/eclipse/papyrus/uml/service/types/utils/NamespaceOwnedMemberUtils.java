/**
 * Copyright (c) 2014 CEA LIST.
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
 */
package org.eclipse.papyrus.uml.service.types.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;


public class NamespaceOwnedMemberUtils {


	public static EReference getContainmentFeature(Namespace container, Namespace element) {
		ContainerFeatureSwitch classifierSwitch = new ContainerFeatureSwitch(element);

		return classifierSwitch.doSwitch(container);

	}

	private static class ContainerFeatureSwitch extends UMLSwitch<EReference> {

		private final Namespace element;

		public ContainerFeatureSwitch(Namespace element) {
			super();
			this.element = element;
		}



		@Override
		public EReference casePackage(Package object) {
			if (element instanceof PackageableElement)
			{
				return UMLPackage.eINSTANCE.getPackage_PackagedElement();
			}
			return super.casePackage(object);
		}



		@Override
		public EReference caseClass(Class object) {
			if (element instanceof Classifier)
			{
				return UMLPackage.eINSTANCE.getClass_NestedClassifier();
			}
			return super.caseClass(object);
		}




		@Override
		public EReference defaultCase(EObject object) {
			return null;
		}
	};



	static public boolean isContainedTransitively(EObject owner, EObject owned) {
		List<EObject> owners = new ArrayList<EObject>();
		EObject it = owner;
		while (it != null)
		{
			owners.add(it);
			it = it.eContainer();
		}

		return owners.contains(owned);
	}


	public static boolean canContainTarget(Namespace source, Namespace target) {
		// Must know how to set the containment
		ContainerFeatureSwitch classifierSwitch = new ContainerFeatureSwitch(target);
		if (classifierSwitch.doSwitch(source) == null) {
			return false;
		}

		// No transitive
		if (NamespaceOwnedMemberUtils.isContainedTransitively(source, target))
		{
			return false;
		}
		// No reflexivity
		if (source.equals(target))
		{
			return false;
		}
		return true;
	}

}

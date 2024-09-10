/*****************************************************************************
 * Copyright (c) 2013, 2014 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 399859
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.CacheAdapter;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extension;
import org.eclipse.uml2.uml.util.UMLUtil;

import com.google.common.collect.ImmutableList;

/**
 * Provides methods for stereotypes application outside of a resource
 *
 * @author Vincent Lorenzo
 *
 */
public class CustomUMLUtil extends UMLUtil {

	public static void destroy(EObject eObject) {
		UML2Util.destroy(eObject);
	}

	public static void destroyAll(Collection<? extends EObject> eObjects) {
		// Iterate a copy of the list to avoid concurrent modification
		UML2Util.destroyAll(ImmutableList.copyOf(eObjects));
	}

	/**
	 * The StereotypeApplicationHelper can be overridden to change the default
	 * location of applied stereotypes.
	 * Typically, stereotype applications are placed in the same location as
	 * the element to which the stereotype is applied, however, stereotype
	 * applications may be placed in other resources.
	 *
	 * @since 3.0
	 */
	public static class StereotypeApplicationHelper extends UMLUtil.StereotypeApplicationHelper {

		public static final StereotypeApplicationHelper INSTANCE = createStereotypeApplicationHelper();

		private static StereotypeApplicationHelper createStereotypeApplicationHelper() {
			StereotypeApplicationHelper stereotypeApplicationHelper = UML2Util.loadClassFromSystemProperty("org.eclipse.uml2.uml.util.UMLUtil$StereotypeApplicationHelper.INSTANCE"); //$NON-NLS-1$

			if (stereotypeApplicationHelper != null) {
				return stereotypeApplicationHelper;
			}

			return new StereotypeApplicationHelper();
		}


		@Override
		public EObject applyStereotype(Element element, EClass definition) {
			EObject stereotypeApplication = EcoreUtil.create(definition);

			CacheAdapter.getInstance().adapt(stereotypeApplication);

			// addToContainmentList(element, stereotypeApplication);
			setBaseElement(stereotypeApplication, element);

			return stereotypeApplication;
		}

		/**
		 * Sets the base element for the specified stereotype application to the
		 * specified element.
		 *
		 * @param stereotypeApplication
		 *            The stereotype application.
		 * @param element
		 *            The new base element.
		 */
		public static void setBaseElement(EObject stereotypeApplication, Element element) {

			if (stereotypeApplication != null) {
				EClass eClass = stereotypeApplication.eClass();
				// if(getStereotype(eClass, stereotypeApplication) != null) {

				for (EStructuralFeature eStructuralFeature : eClass.getEAllStructuralFeatures()) {

					if (eStructuralFeature.getName().startsWith(Extension.METACLASS_ROLE_PREFIX) && (element == null || eStructuralFeature.getEType().isInstance(element))) {

						stereotypeApplication.eSet(eStructuralFeature, element);
					}
				}
				// }
			}
		}

	}
}

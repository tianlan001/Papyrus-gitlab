/*****************************************************************************
 *  Copyright (c) 2018 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Patrick Tessier  (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.internal;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.EFacetPackage;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;



public class Activator implements BundleActivator {

	/**
	 * 
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 *
	 * @param arg0
	 * @throws Exception
	 */
	public void start(BundleContext arg0) throws Exception {
		EValidator.Registry.INSTANCE.put
		(EFacetPackage.eINSTANCE, 
		 new EValidator.Descriptor() {
			 public EValidator getEValidator() {
				 return EFacetValidator.eInstance;
			 }
		 });

	}

	public void stop(BundleContext arg0) throws Exception {

	}

}
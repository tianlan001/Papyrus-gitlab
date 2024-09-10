/**
 *  Copyright (c) 2012 Mia-Software.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  	Gregoire Dupe (Mia-Software) - Bug 369987 - [Restructuring][Table] Switch to the new customization and facet framework
 */
package org.eclipse.papyrus.emf.facet.custom.ui.internal.exported;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.EClassCustomization;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.FacetCustomization;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.Facet;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetOperation;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.extensible.Query;
import org.eclipse.papyrus.emf.facet.util.emf.core.command.ICommandFactoryResult;

public interface ICustomizationCommandFactory {

	ICommandFactoryResult<Customization> createCustomization(
			String name);

	ICommandFactoryResult<EClassCustomization> createEClassCustomization(
			Customization customization, EClass customedEClass, Query conformanceQuery);

	ICommandFactoryResult<FacetCustomization> createFacetCustomization(
			Customization customization, Facet customizedFacet);

	ICommandFactoryResult<Facet> setPropertyConfig(Facet typeCustomization,
			ETypedElement customizedTElt, // customized typed element
			FacetOperation customProperty,
			Query query);
}

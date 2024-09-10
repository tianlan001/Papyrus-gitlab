/*****************************************************************************
 * Copyright (c) 2011, 2015 CEA LIST, Christian W. Damus, and others.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yann Tanguy - Initial API and implementation
 *  Christian W. Damus - bug 451230
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.tests.registry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.ISpecializationType;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DefaultEditHelper;
import org.eclipse.papyrus.infra.services.edit.context.TypeContext;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.profile.standard.StandardPackage;
import org.junit.Test;

/**
 *
 */
public class TestElementTypeRegistryContent {

	private IClientContext context;
	
	private static final String PAPYRUS_ELEMENT_TYPE_PREFIX = "org.eclipse.papyrus.uml."; //$NON-NLS-1$

	private static final String PAPYRUS_ST_APPLICATION_TYPE_PREFIX = "org.eclipse.papyrus.uml.stereotype."; //$NON-NLS-1$

	private static final String PAPYRUS_INVALID_PREFIX = "org.eclipse.papyrus.uml.diagram"; //$NON-NLS-1$

	public TestElementTypeRegistryContent() {
		try {
			context = TypeContext.getDefaultContext();
		} catch (ServiceException e) {
			fail("Default client context could not be found.");
			return;
		}
	}
	
	@Test
	public void testRegistryContentForUML() {
		// Iterate over UML2 contents
		Iterator<EObject> it = UMLPackage.eINSTANCE.eAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();
			if (eObject instanceof EClass) {
				EClass eClass = (EClass) eObject;
				IElementType elementType = ElementTypeRegistry.getInstance().getElementType(eClass, context);

				// An IElementType is supposed to be registered for any meta-class in the UML type service.
				assertTrue("No IElementType registered for eClass (" + eClass.getName() + ")", elementType != null);
				// An IElementType is supposed to be registered for any meta-class in the UML type service.
				assertTrue("Unexpected IElementType id (" + elementType.getId() + ")", elementType.getId().startsWith(PAPYRUS_ELEMENT_TYPE_PREFIX));
				assertFalse("Unexpected IElementType id (" + elementType.getId() + ")", elementType.getId().startsWith(PAPYRUS_INVALID_PREFIX));
				// Ensure a correct base is used for Helper
				assertTrue("Incorrect edit helper hierarchy for element type (" + elementType.getId() + ")", elementType.getEditHelper() instanceof DefaultEditHelper);
			}
		}
	}

	@Test
	public void testRegistryContentForUMLStandard() {

		// Iterate over UML2 Standard profile contents
		Iterator<EObject> it = StandardPackage.eINSTANCE.eAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();
			if (eObject instanceof EClass) {
				EClass eClass = (EClass) eObject;

				if (!"Trace".equals(eClass.getName()) && !"Refine".equals(eClass.getName())) {
					// Not implemented
					continue;
				}

				assertTrue("No type found in Papyrus context for " + eClass.getName(), ElementEditServiceUtils.getEditServiceProvider(context).isKnownElementType(PAPYRUS_ELEMENT_TYPE_PREFIX + eClass.getName()));

			}
		}
	}

	@Test
	public void testRegistryContentForUMLStandardStereotypeApplication() {

		// Iterate over UML2 Standard profile contents
		Iterator<EObject> it = StandardPackage.eINSTANCE.eAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();
			if (eObject instanceof EClass) {
				EClass eClass = (EClass) eObject;

				if (!"Trace".equals(eClass.getName()) && !"Refine".equals(eClass.getName())) {
					// Not implemented
					continue;
				}

				assertTrue("No type found in Papyrus context for " + eClass.getName(), ElementEditServiceUtils.getEditServiceProvider(context).isKnownElementType(PAPYRUS_ST_APPLICATION_TYPE_PREFIX + eClass.getName()));

			}
		}
	}

	@Test
	public void testRegistryContentForUMLAssociations() {

		IClientContext context;
		try {
			context = TypeContext.getDefaultContext();
		} catch (ServiceException e) {
			fail("Default client context could not be found.");
			return;
		}

		IElementType associationBaseElementType = ElementTypeRegistry.getInstance().getElementType(UMLPackage.eINSTANCE.getAssociation(), context);
		assertTrue("Incorrect id for base Association element type (" + associationBaseElementType.getId() + ")", associationBaseElementType.getId().equals("org.eclipse.papyrus.uml.AssociationBase"));

		IElementType associationElementType = ElementTypeRegistry.getInstance().getType("org.eclipse.papyrus.uml.Association");
		assertTrue("No ISpecializationType found for UML Association in Papyrus context", ElementEditServiceUtils.getEditServiceProvider(context).isKnownElementType("org.eclipse.papyrus.uml.Association"));
		assertTrue("Incorrect kind of ElementType (ISpecializationType expected for " + associationElementType.getId() + ")", associationElementType instanceof ISpecializationType);
		ISpecializationType associationSpecializationType = (ISpecializationType) associationElementType;
		assertTrue("Incorrect specialization type hierarchy for " + associationElementType.getId(), associationSpecializationType.isSpecializationOf(associationBaseElementType));

	}

}

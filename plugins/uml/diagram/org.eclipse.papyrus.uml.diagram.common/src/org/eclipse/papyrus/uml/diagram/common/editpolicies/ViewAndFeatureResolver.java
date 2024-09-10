/*******************************************************************************
 * Copyright (c) 2008 Conselleria de Infraestructuras y Transporte, Generalitat
 * de la Comunitat Valenciana . All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution, and is
t https://www.eclipse.org/legal/epl-2.0/
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Francisco Javier Cano Muñoz (Prodevelop) – Initial implementation.
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Lets a D&D policy know if an <EObject> is a <Node> or a <Edge> in the host <EditPart>. Can
 * resolve the <EStructuralFeature> where an <EObject> is stored by its <ECLass>.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 */
public interface ViewAndFeatureResolver extends ViewResolver {

	/**
	 * Gets the e structural feature for e class.
	 *
	 * @param eClass
	 *            the e class
	 *
	 * @return the e structural feature for e class
	 */
	EStructuralFeature getEStructuralFeatureForEClass(EClass eClass);

}

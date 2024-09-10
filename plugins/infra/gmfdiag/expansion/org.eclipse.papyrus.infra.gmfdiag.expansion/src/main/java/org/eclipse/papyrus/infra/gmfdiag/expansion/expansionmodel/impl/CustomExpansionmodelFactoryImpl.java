/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl;

import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.GMFT_BasedRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.InducedRepresentation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.Representation;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.RepresentationKind;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.ExpansionModelFactoryImpl;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.GMFT_BasedRepresentationImpl;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.InducedRepresentationImpl;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationImpl;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.impl.RepresentationKindImpl;

/**
 *  this class is overloaded in order to add the implementation of the method validate
 * See Requirement #Req org.eclipse.papyrus.infra.gmfdiag.expansion.Req_061
 *
 */
public class CustomExpansionmodelFactoryImpl extends ExpansionModelFactoryImpl {

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.impl.ExpansionmodelFactoryImpl#createInducedRepresentation()
	 *
	 * @return
	 */
	@Override
	public InducedRepresentation createInducedRepresentation() {
		InducedRepresentationImpl inducedRepresentation = new CustomInducedRepresentationImpl();
		return inducedRepresentation;
	}
	
	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.impl.ExpansionmodelFactoryImpl#createRepresentation()
	 *
	 * @return
	 */
	@Override
	public Representation createRepresentation() {
			RepresentationImpl representation = new CustomRepresentationImpl();
			return representation;
	}
	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.impl.ExpansionmodelFactoryImpl#createGMFT_Based_Representation()
	 *
	 * @return
	 */
	@Override
	public GMFT_BasedRepresentation createGMFT_BasedRepresentation() {
		GMFT_BasedRepresentationImpl gmfT_Based_Representation = new CustomGMFT_BasedRepresentationImpl();
		return gmfT_Based_Representation;
	}
	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.expansion.impl.ExpansionmodelFactoryImpl#createRepresentationKind()
	 *
	 * @return
	 */
	@Override
	public RepresentationKind createRepresentationKind() {
		RepresentationKindImpl representationKind = new CustomRepresentationKindImpl();
		return representationKind;
	}
}

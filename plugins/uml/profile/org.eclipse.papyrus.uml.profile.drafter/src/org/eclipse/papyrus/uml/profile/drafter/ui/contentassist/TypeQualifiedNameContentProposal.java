/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.ui.contentassist;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;


/**
 * Field assist {@link IContentProposal} .
 * This implementation is used to render one proposal of a {@link Stereotype} as a short name:
 * shortName (profil)
 * 
 * @author cedric dumoulin
 *
 */
public class TypeQualifiedNameContentProposal extends TypeContentProposalBase implements IContentProposal, Comparable<IContentProposal> {
	
	/**
	 * Constructor.
	 *
	 * @param stereotype
	 */
	public TypeQualifiedNameContentProposal(Type type) {
		super( type );
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
	 *
	 * @return
	 */
	public String getContent() {
		return type.getQualifiedName() ;
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getCursorPosition()
	 *
	 * @return
	 */
	public int getCursorPosition() {
		return getContent().length();
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
	 *
	 * @return
	 */
	public String getLabel() {
		
		return type.getQualifiedName();		
	}


}

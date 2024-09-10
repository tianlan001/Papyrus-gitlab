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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.ui.contentassist;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Stereotype;


/**
 * Field assist {@link IContentProposal} .
 * This implementation is used to render one proposal of a {@link Stereotype}
 * 
 * @author cedric dumoulin
 *
 */
public class StereotypeContentProvider implements IContentProposal, Comparable<StereotypeContentProvider> {
	
	/**
	 * The stereotype that is render.
	 */
	private Stereotype stereotype;
	
	
	/**
	 * Constructor.
	 *
	 * @param stereotype
	 */
	public StereotypeContentProvider(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
	 *
	 * @return
	 */
	public String getContent() {
		return UMLLabelInternationalization.getInstance().getKeyword(stereotype);
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getCursorPosition()
	 *
	 * @return
	 */
	public int getCursorPosition() {
		return getLabel().length();
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
	 *
	 * @return
	 */
	public String getLabel() {
		return UMLLabelInternationalization.getInstance().getKeyword(stereotype);
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getDescription()
	 *
	 * @return
	 */
	public String getDescription() {
		
		return stereotype.getKeyword();
	}

	public int compareTo(StereotypeContentProvider o) {
		return this.getLabel().compareTo(o.getLabel());
	}

}

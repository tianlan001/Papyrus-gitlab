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

import java.util.List;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Type;


/**
 * Field assist {@link IContentProposal} .
 * Base class for TypeContentProposal.
 * 
 * 
 * @author cedric dumoulin
 *
 */
public abstract class TypeContentProposalBase implements IContentProposal, Comparable<IContentProposal> {
	
	/**
	 * The type that is render.
	 */
	protected Type type;
	
	
	/**
	 * Constructor.
	 *
	 * @param stereotype
	 */
	public TypeContentProposalBase(Type type) {
		this.type = type;
	}

	/**
	 * @see org.eclipse.jface.fieldassist.IContentProposal#getDescription()
	 *
	 * @return
	 */
	public String getDescription() {
		// Try to return the first associated comment
		List<Comment> comments =  type.getOwnedComments();
		if( !comments.isEmpty()) {
			Comment comment = comments.get(0);
			return comment.toString();
		}
		
		return type.getLabel();
	}

	public int compareTo(IContentProposal o) {
		return this.getLabel().compareTo(o.getLabel());
	}

	
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

}

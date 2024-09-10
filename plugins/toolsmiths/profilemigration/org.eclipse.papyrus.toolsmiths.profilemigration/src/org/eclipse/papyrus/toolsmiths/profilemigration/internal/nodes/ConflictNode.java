/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.profilemigration.internal.nodes;

import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.edit.tree.impl.TreeNodeImpl;

import com.google.common.collect.Iterables;

/**
 * This class is wrapper for TreeNode used to represent a conflict TreeNode.
 * 
 */
public class ConflictNode extends TreeNodeImpl {

	/**
	 * Constructor.
	 * 
	 * @param conflict
	 *            The conflict represented by this TreeNode
	 */
	public ConflictNode(Conflict conflict) {
		setData(conflict);
	}

	/**
	 * Add the given MatchNode to the children of this ConflictNode.
	 * 
	 * @param matchNode
	 *            The MatchNode to add
	 * @return <code>true</code> if the MatchNode has been added
	 */
	public boolean addConflictingTree(MatchNode matchNode) {
		return getChildren().add(matchNode);
	}

	/**
	 * Remove the given MatchNode of the children of this ConflictNode.
	 * 
	 * @param matchNode
	 *            The MatchNode to remove
	 * @return <code>true</code> if the MatchNode has been removed
	 */
	public boolean removeConflictingTree(MatchNode matchNode) {
		return getChildren().remove(matchNode);
	}

	/**
	 * Getter for the conflict represented by this TreeNode.
	 * 
	 * @return the conflict
	 */
	public Conflict getConflict() {
		return (Conflict) getData();
	}

	/**
	 * Get all the match trees that are part of the conflict
	 * 
	 * @return an iterable of all MatchNode that are part of the conflict
	 */
	public Iterable<MatchNode> getConflictingTrees() {
		return Iterables.filter(getChildren(), MatchNode.class);
	}

}

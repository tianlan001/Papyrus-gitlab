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

import org.eclipse.emf.compare.Match;
import org.eclipse.emf.edit.tree.impl.TreeNodeImpl;

import com.google.common.collect.Iterables;

/**
 * This class is wrapper for TreeNode used to represent a match TreeNode.
 */
public class MatchNode extends TreeNodeImpl {

	/**
	 * Constructor.
	 * 
	 * @param match
	 *            The match represented by this TreeNode
	 */
	public MatchNode(Match match) {
		setData(match);
	}

	/**
	 * Add the given MatchNode to the children of this MatchNode.
	 * 
	 * @param matchNode
	 *            The MatchNode to add
	 * @return <code>true</code> if the MatchNode has been added
	 */
	public boolean addSubMatchNode(MatchNode matchNode) {
		return getChildren().add(matchNode);
	}

	/**
	 * Add the given DiffNode to the children of this MatchNode.
	 * 
	 * @param diffNode
	 *            The DiffNode to add
	 * @return <code>true</code> if the DiffNode has been added
	 */
	public boolean addDiffNode(DiffNode diffNode) {
		return getChildren().add(diffNode);
	}

	/**
	 * Remove the given MatchNode of the children of this MatchNode.
	 * 
	 * @param matchNode
	 *            The MatchNode to remove
	 * @return <code>true</code> if the MatchNode has been removed
	 */
	public boolean removeSubMatchNode(MatchNode matchNode) {
		return getChildren().remove(matchNode);
	}

	/**
	 * Remove the given DiffNode of the children of this MatchNode.
	 * 
	 * @param diffNode
	 *            The DiffNode to remove
	 * @return <code>true</code> if the DiffNode has been removed
	 */
	public boolean removeDiffNode(DiffNode diffNode) {
		return getChildren().remove(diffNode);
	}

	/**
	 * Getter for the match represented by this TreeNode.
	 * 
	 * @return the match
	 */
	public Match getMatch() {
		return (Match) getData();
	}

	/**
	 * Get all the match trees that are submatches of the match
	 * 
	 * @return an iterable of all submatches
	 */
	public Iterable<MatchNode> getSubMatchNodes() {
		return Iterables.filter(getChildren(), MatchNode.class);
	}

	/**
	 * Get all the diffs that are part of the match
	 * 
	 * @return an iterable of all directly contained diffs
	 */
	public Iterable<DiffNode> getDiffNodes() {
		return Iterables.filter(getChildren(), DiffNode.class);
	}

}

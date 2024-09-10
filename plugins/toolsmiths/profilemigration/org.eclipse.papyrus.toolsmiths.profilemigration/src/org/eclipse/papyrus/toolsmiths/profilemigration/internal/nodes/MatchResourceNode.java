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

import org.eclipse.emf.compare.MatchResource;
import org.eclipse.emf.edit.tree.impl.TreeNodeImpl;

import com.google.common.collect.Iterables;

/**
 * This class is wrapper for TreeNode used to represent a match resource TreeNode.
 */
public class MatchResourceNode extends TreeNodeImpl {

	/**
	 * Constructor.
	 * 
	 * @param matchResource
	 *            The matchResource represented by this TreeNode
	 */
	public MatchResourceNode(MatchResource matchResource) {
		setData(matchResource);
	}

	/**
	 * Add the given DiffNode to the direct children of this MatchResourceNode.
	 * 
	 * @param diffNode
	 *            The DiffNode to add
	 * @return <code>true</code> if the MatchNode has been added
	 */
	public boolean addDiffNode(DiffNode diffNode) {
		return getChildren().add(diffNode);
	}

	/**
	 * Add the given MatchNode to the children of this MatchResourceNode.
	 * 
	 * @param matchNode
	 *            The MatchNode to add
	 * @return <code>true</code> if the MatchNode has been added
	 */
	public boolean addMatchNode(MatchNode matchNode) {
		return getChildren().add(matchNode);
	}

	/**
	 * Remove the given DiffNode of the direct children of this MatchResourceNode.
	 * 
	 * @param diffNode
	 *            The DiffNode to remove
	 * @return <code>true</code> if the DiffNode has been removed
	 */
	public boolean removeDiffNode(DiffNode diffNode) {
		return getChildren().remove(diffNode);
	}

	/**
	 * Remove the given MatchNode of the children of this MatchResourceNode.
	 * 
	 * @param matchNode
	 *            The MatchNode to remove
	 * @return <code>true</code> if the MatchNode has been removed
	 */
	public boolean removeMatchNode(MatchNode matchNode) {
		return getChildren().remove(matchNode);
	}

	/**
	 * Getter for the match resource represented by this TreeNode.
	 * 
	 * @return the matchResource
	 */
	public MatchResource getMatchResource() {
		return (MatchResource) getData();
	}

	/**
	 * Get all the match trees that are part of the MatchResource
	 * 
	 * @return an iterable of all MatchNode that are part of the MatchResource
	 */
	public Iterable<MatchNode> getMatches() {
		return Iterables.filter(getChildren(), MatchNode.class);
	}

}

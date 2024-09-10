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

import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.edit.tree.impl.TreeNodeImpl;

import com.google.common.collect.Iterables;

/**
 * This class is wrapper for TreeNode used to represent a diff TreeNode.
 */
public class DiffNode extends TreeNodeImpl {

	/**
	 * Constructor.
	 * 
	 * @param diff
	 *            The diff represented by this TreeNode
	 */
	public DiffNode(Diff diff) {
		setData(diff);
	}

	/**
	 * Add the given DiffNode to the children of this DiffNode. This is intended to be used only for refined
	 * diffs.
	 * 
	 * @param diffNode
	 *            The DiffNode to add
	 * @return <code>true</code> if the DiffNode has been added
	 */
	public boolean addRefinedDiffNode(DiffNode diffNode) {
		return getChildren().add(diffNode);
	}

	/**
	 * Remove the given DiffNode of the children of this DiffNode. This is intended to be used only for
	 * refined diffs.
	 * 
	 * @param diffNode
	 *            The DiffNode to remove
	 * @return <code>true</code> if the DiffNode has been removed
	 */
	public boolean removeRefinedDiffNode(DiffNode diffNode) {
		return getChildren().remove(diffNode);
	}

	/**
	 * Getter for the diff represented by this TreeNode.
	 * 
	 * @return the diff
	 */
	public Diff getDiff() {
		return (Diff) getData();
	}

	/**
	 * Get all the refined DiffNode that are part of this DiffNode.
	 * 
	 * @return an iterable of all refined DiffNode of this diff
	 */
	public Iterable<DiffNode> getRefinedDiffs() {
		return Iterables.filter(getChildren(), DiffNode.class);
	}

}

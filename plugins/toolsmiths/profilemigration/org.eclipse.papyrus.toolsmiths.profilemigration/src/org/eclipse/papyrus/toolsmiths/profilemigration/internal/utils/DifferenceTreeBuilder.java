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

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils;

import static com.google.common.collect.Collections2.filter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Conflict;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.MatchResource;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.compare.ResourceAttachmentChange;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.nodes.ConflictNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.nodes.DiffNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.nodes.MatchNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.nodes.MatchResourceNode;

import com.google.common.base.Predicates;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

/**
 * This class is uses to get the treeNode from the comparison
 * 
 * This code is mostly take from {@link org.eclipse.emf.compare.rcp.ui.internal.structuremergeviewer.groups.impl.BasicDifferenceGroupImpl}
 */
public class DifferenceTreeBuilder extends AdapterImpl {

	private Comparison comparison;

	/**
	 * Constructor
	 *
	 * @param comparison
	 * @param profile
	 */
	public DifferenceTreeBuilder(Comparison comparison) {
		this.comparison = comparison;
	}

	/**
	 * Get the treeNode
	 *
	 * @return the build treeNode
	 */
	public TreeNode buildMatchTree() {
		List<TreeNode> result = buildMatchTrees();
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}



	/**
	 * Compute a subTree for each root match of the comparison.
	 * 
	 * @return the list of matchSubTrees
	 */
	protected List<TreeNode> buildMatchTrees() {
		final List<TreeNode> matchTrees = new ArrayList<TreeNode>();
		for (Match match : getComparison().getMatches()) {
			MatchNode matchNode = buildTree(match);
			if (matchNode != null) {
				matchTrees.add(matchNode);
			}
		}
		return matchTrees;
	}

	private Comparison getComparison() {
		return comparison;
	}

	/**
	 * Compute a tree for the given match.
	 * 
	 * @param match
	 *            The given match
	 * @return a list of subTree for this match, must not be <code>null</code>
	 */
	protected MatchNode buildTree(Match match) {
		MatchNode result = null;
		MatchNode matchNode = createMatchNode(match);
		populateMatchNode(matchNode);
		if (!matchNode.getChildren().isEmpty()) {
			result = matchNode;
		}
		return result;
	}

	/**
	 * Build the subtree for the given match.
	 * 
	 * @param matchNode
	 *            The root matchNode
	 */
	protected void populateMatchNode(MatchNode matchNode) {
		Match match = matchNode.getMatch();
		Multimap<Match, Diff> diffsBySubMatch = LinkedHashMultimap.create();
		for (Diff diff : filter(match.getDifferences(), Predicates.alwaysTrue())) {
			// If a diff is part of a larger diff (is refined by), we don't want to add it to the tree. It
			// will be added by the algorithm in a second step. This way we avoid duplication and all diffs
			// that are part of a 'master' diff are grouped as children of this 'master' diff
			if (isPartOfLargerDiff(diff)) {
				Match targetMatch = getTargetMatch(diff);
				if (match == targetMatch) {
					addDiffNode(matchNode, diff);
				} else if (match.getSubmatches().contains(targetMatch)) {
					diffsBySubMatch.put(targetMatch, diff);
				} else if (targetMatch != null) {
					MatchNode targetMatchNode = createMatchNode(targetMatch);
					matchNode.addSubMatchNode(targetMatchNode);
					addDiffNode(targetMatchNode, diff);
				}
			}
		}
		for (Match subMatch : match.getSubmatches()) {
			MatchNode subMatchNode = createMatchNode(subMatch);
			for (Diff subMatchDiff : diffsBySubMatch.get(subMatch)) {
				addDiffNode(subMatchNode, subMatchDiff);
			}
			diffsBySubMatch.removeAll(subMatch);
			populateMatchNode(subMatchNode);
			if (!subMatchNode.getChildren().isEmpty()) {
				matchNode.addSubMatchNode(subMatchNode);
			}
		}
	}

	/**
	 * Add the diff in the given match. This method handles refined diffs and allows to customize the result.
	 * 
	 * @param matchNode
	 *            The given match node
	 * @param diff
	 *            The diff to add
	 */
	protected void addDiffNode(MatchNode matchNode, Diff diff) {
		if (!(diff instanceof ResourceAttachmentChange)) {
			DiffNode diffNode = createDiffNode(diff);
			handleRefiningDiffs(diffNode);
			matchNode.addDiffNode(diffNode);
		}
	}

	/**
	 * Handle the diffs that refine the given diff. Refining diffs are added as children of the given diff,
	 * and so on recursively.
	 * 
	 * @param diffNode
	 *            The diff node to handle, which is not necessarily a child of a MatchNode since this method
	 *            is called recursively.
	 */
	protected void handleRefiningDiffs(DiffNode diffNode) {
		Diff diff = diffNode.getDiff();
		for (Diff refiningDiff : diff.getRefinedBy()) {
			DiffNode refinedDiffNode = createDiffNode(refiningDiff);
			diffNode.addRefinedDiffNode(refinedDiffNode);
			handleRefiningDiffs(refinedDiffNode);
		}
	}

	/**
	 * Does the given difference is part of a larger diff, means is refined by an other diff
	 * 
	 * @param diff
	 *            The diff
	 * @return <code>true</code> if the diff's node should be a child of a MatchNode.
	 */
	protected boolean isPartOfLargerDiff(Diff diff) {
		return diff.getRefines().isEmpty();
	}

	/**
	 * Provide the Match that should directly contain the given diff. If the given diff should not be a direct
	 * child of a Match, the method must return <code>null</code>. For a given strategy, a diff should only be
	 * displayed in the same Match (i.e. the {@link DiffNode}s that represent the diff should always be
	 * children of the {@link MatchNode}s that represent the returned Match.
	 * 
	 * @param diff
	 *            The difference
	 * @return The Match that is a direct parent of the given diff, can be <code>null</code>.
	 */
	protected Match getTargetMatch(Diff diff) {
		if (isPartOfLargerDiff(diff)) {
			if (isContainmentRefChange(diff)) {
				Match valueMatch = diff.getMatch().getComparison()
						.getMatch(((ReferenceChange) diff).getValue());
				return valueMatch; // This match may not be a sub-match because the child may have moved
			} else if (isContainmentRefChange(diff.getPrimeRefining())) {
				Match valueMatch = diff.getMatch().getComparison()
						.getMatch(((ReferenceChange) diff.getPrimeRefining()).getValue());
				return valueMatch; // This match may not be a sub-match because the child may have moved
			}
			return diff.getMatch();
		}
		return null;
	}

	/**
	 * Is it a containment reference change?
	 * 
	 * @param diff
	 *            The diff
	 * @return <code>true</code> if the diff is a {@link ReferenceChange} whose {@link EReference} is a
	 *         containment reference.
	 */
	protected boolean isContainmentRefChange(Diff diff) {
		return diff instanceof ReferenceChange && ((ReferenceChange) diff).getReference().isContainment();
	}

	/**
	 * Create a diff node.
	 * 
	 * @param diff
	 *            The given diff
	 * @return the DiffNode
	 */
	protected DiffNode createDiffNode(Diff diff) {
		DiffNode diffNode = new DiffNode(diff);
		diffNode.eAdapters().add(this);
		return diffNode;
	}

	/**
	 * Create a match node.
	 * 
	 * @param match
	 *            The given match
	 * @return the MatchNode
	 */
	protected MatchNode createMatchNode(Match match) {
		MatchNode matchNode = new MatchNode(match);
		matchNode.eAdapters().add(this);
		return matchNode;
	}

	/**
	 * Create a conflict node.
	 * 
	 * @param conflict
	 *            The given conflict
	 * @return the ConflictNode
	 */
	protected ConflictNode createConflictNode(Conflict conflict) {
		ConflictNode conflictNode = new ConflictNode(conflict);
		conflictNode.eAdapters().add(this);
		return conflictNode;
	}

	/**
	 * Create a matchResource node.
	 * 
	 * @param matchResource
	 *            The given matchResource
	 * @return the MatchResourceNode
	 */
	protected MatchResourceNode createMatchResourceNode(MatchResource matchResource) {
		MatchResourceNode matchResourceNode = new MatchResourceNode(matchResource);
		matchResourceNode.eAdapters().add(this);
		return matchResourceNode;
	}

}

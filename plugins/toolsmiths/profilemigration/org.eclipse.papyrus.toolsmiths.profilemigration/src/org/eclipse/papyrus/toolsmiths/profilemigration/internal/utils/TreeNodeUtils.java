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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.DifferenceKind;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.uml2.uml.Profile;

/**
 * Util methods to get information from the TreeNode structure
 */
public class TreeNodeUtils {

	/**
	 * Check if the treeNode is a change of type Add
	 *
	 * @param treeNode
	 * @param profile
	 * @return true if the treeNode reference an add of something, false otherwise
	 */
	public static boolean isAddType(TreeNode treeNode, Profile profile) {
		if (getNearestProfile(treeNode) == profile) {
			if (treeNode.getData() instanceof Match) {
				Match match = (Match) treeNode.getData();
				if (getNewElement(match) != null && getOldElement(match) == null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get the added element, this method return null if the change is not an add
	 *
	 * @param treeNode
	 * @return the added element or null if the treeNode is not an add change
	 */
	public static EObject getAddedElement(TreeNode treeNode) {
		if (treeNode.getData() instanceof Match) {
			Match match = (Match) treeNode.getData();
			if (getNewElement(match) != null && getOldElement(match) == null) {
				return getNewElement(match);
			}
		}
		return null;
	}

	/**
	 * Get the structural feature where the element is added, this method return null if the change is not an add
	 *
	 * @param treeNode
	 * @return the added element or null if the treeNode is not an add change
	 */
	public static EStructuralFeature getAddedStructuralFeature(TreeNode treeNode) {
		if (treeNode.getData() instanceof Match) {
			Match match = (Match) treeNode.getData();
			if (getNewElement(match) != null && getOldElement(match) == null) {
				for (TreeNode child : treeNode.getChildren()) {
					if (child.getData() instanceof AttributeChange) {
						AttributeChange attributeChange = (AttributeChange) child.getData();
						return attributeChange.getAttribute();
					} else if (child.getData() instanceof ReferenceChange) {
						ReferenceChange referenceChange = (ReferenceChange) child.getData();
						return referenceChange.getReference();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Check if the treeNode is a change of type Delete
	 *
	 * @param treeNode
	 * @param profile
	 * @return true if the treeNode reference an delete of something, false otherwise
	 */
	public static boolean isDeleteType(TreeNode treeNode, Profile profile) {
		if (getNearestProfile(treeNode) == profile) {
			if (treeNode.getData() instanceof Match) {
				Match match = (Match) treeNode.getData();
				if (getNewElement(match) == null && getOldElement(match) != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get the deleted element, this method return null if the change is not a delete
	 * (warning: return the object from the oldModel, you can't use == with elements get from the model)
	 *
	 * @param treeNode
	 * @return the deleted element or null if the treeNode is not a delete change
	 */
	public static EObject getDeletedElement(TreeNode treeNode) {
		if (treeNode.getData() instanceof Match) {
			Match match = (Match) treeNode.getData();
			if (getNewElement(match) == null && getOldElement(match) != null) {
				return getOldElement(match);
			}
		}
		return null;
	}

	/**
	 * Get the deleted element's owner, this method return null if the change is not a delete. This method
	 * return the element from the model if it is possible otherwise it return the element from the old model.
	 *
	 * @param treeNode
	 * @return the deleted element or null if the treeNode is not a delete change
	 */
	public static EObject getDeletedElementOwner(TreeNode treeNode) {
		if (treeNode.getData() instanceof Match) {
			Match match = (Match) treeNode.getData();
			if (getNewElement(match) == null && getOldElement(match) != null) {
				if (match.eContainer() instanceof Match) {
					if (getNewElement(((Match) match.eContainer())) != null) {
						return getNewElement(((Match) match.eContainer()));
					} else {
						return getOldElement(((Match) match.eContainer()));
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get the structural feature where the element is deleted, this method return null if the change is not an add
	 *
	 * @param treeNode
	 * @return the added element or null if the treeNode is not an add change
	 */
	public static EStructuralFeature getDeletedStructuralFeature(TreeNode treeNode) {
		if (treeNode.getData() instanceof Match) {
			Match match = (Match) treeNode.getData();
			if (getNewElement(match) == null && getOldElement(match) != null) {
				for (TreeNode child : treeNode.getChildren()) {
					if (child.getData() instanceof AttributeChange) {
						AttributeChange attributeChange = (AttributeChange) child.getData();
						return attributeChange.getAttribute();
					} else if (child.getData() instanceof ReferenceChange) {
						ReferenceChange referenceChange = (ReferenceChange) child.getData();
						return referenceChange.getReference();
					}
				}
			}
		}
		return null;
	}

	/**
	 * Check if the treeNode is a change of type Change
	 *
	 * @param treeNode
	 * @param profile
	 * @return true if the treeNode is a change reference of a feature, false otherwise
	 */
	public static boolean isChangeType(TreeNode treeNode, Profile profile) {
		if (getNearestProfile(treeNode) == profile) {
			if (treeNode.getData() instanceof Diff) {
				Diff diff = (Diff) treeNode.getData();
				if (diff.getKind() == DifferenceKind.CHANGE) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Get the changed element, this method return null if the change is not a change
	 *
	 * @param treeNode
	 * @return the changed element or null if the treeNode is not a change
	 */
	public static EObject getChangedElement(TreeNode treeNode) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.CHANGE) {
				return getNewElement(diff.getMatch());
			}
		}
		return null;
	}

	/**
	 * Get the old version of the changed element, this method return null if the change is not a change
	 *
	 * @param treeNode
	 * @return the old version of the changed element or null if the treeNode is not a change
	 */
	public static EObject getChangedOldElement(TreeNode treeNode) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.CHANGE) {
				return getOldElement(diff.getMatch());
			}
		}
		return null;
	}

	/**
	 * Get the changed attribute, this method return null if the change is not a change
	 *
	 * @param treeNode
	 * @return the changed attribute or null if the treeNode is not a change
	 */
	public static EStructuralFeature getChangedAttribute(TreeNode treeNode) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.CHANGE) {
				if (diff instanceof AttributeChange) {
					AttributeChange attributeChange = (AttributeChange) diff;
					return attributeChange.getAttribute();
				} else if (diff instanceof ReferenceChange) {
					ReferenceChange referenceChange = (ReferenceChange) diff;
					return referenceChange.getReference();
				}
			}
		}
		return null;
	}

	/**
	 * Check if the treeNode is a change of type Move
	 *
	 * @param treeNode
	 * @return true if the treeNode is a move change, false otherwise
	 */
	public static boolean isMoveChange(TreeNode treeNode) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.MOVE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the element which has been moved, this method return null if the change is not a move
	 *
	 * @param treeNode
	 * @return the moved element or null if the treeNode is not a move
	 */
	public static Object getMovedElement(TreeNode treeNode) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.MOVE) {
				if (diff instanceof ReferenceChange) {
					ReferenceChange attributeChange = (ReferenceChange) diff;
					return attributeChange.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Get the new container, this method return null if the change is not a move
	 *
	 * @param treeNode
	 * @return the new container or null if the treeNode is not a move
	 */
	public static EObject getMovedTargetContainer(TreeNode treeNode) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.MOVE) {
				if (diff instanceof ReferenceChange) {
					ReferenceChange attributeChange = (ReferenceChange) diff;
					return attributeChange.getValue().eContainer();
				}
			}
		}
		return null;
	}

	/**
	 * Get the old container, this method return null if the change is not a move
	 *
	 * @param treeNode
	 * @param comparison
	 *            the root treeNode it is used to search the match element in the new model from the old model
	 * @return the new container or null if the treeNode is not a move
	 */
	public static EObject getMovedSourceContainer(TreeNode treeNode, Comparison comparison) {
		if (treeNode.getData() instanceof Diff) {
			Diff diff = (Diff) treeNode.getData();
			if (diff.getKind() == DifferenceKind.MOVE) {
				if (diff instanceof ReferenceChange) {
					ReferenceChange attributeChange = (ReferenceChange) diff;
					EObject newElement = attributeChange.getValue();
					EObject oldElement = getOldElementFromNewElement(newElement, comparison.getMatches());
					if (oldElement != null) {
						EObject oldContainer = oldElement.eContainer();
						return getNewElementFromOldElement(oldContainer, comparison.getMatches());
					}
				}
			}
		}
		return null;
	}

	/**
	 * Get the element corresponding to the element of the old profile
	 *
	 * @param match
	 * @return return the element corresponding to the element of the old profile
	 */
	public static EObject getOldElement(Match match) {
		return match.getRight();
	}

	/**
	 * Get the element corresponding to the element of the new profile
	 *
	 * @param match
	 * @return return the element corresponding to the element of the new profile
	 */
	public static EObject getNewElement(Match match) {
		return match.getLeft();
	}

	/**
	 * This method try to find the math corresponding to the new element to get the associated old element
	 * 
	 * @param element
	 *            the new element we search
	 * @param matches
	 *            the list of match we will run into
	 * @return the old element corresponding to the new one if any, return null if this element doesn't exist
	 */
	private static EObject getOldElementFromNewElement(EObject element, EList<Match> matches) {
		for (Match match : matches) {
			if (getNewElement(match) == element) {
				return getOldElement(match);
			}
			EObject result = getOldElementFromNewElement(element, match.getSubmatches());
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * This method try to find the math corresponding to the old element to get the associated new element
	 * 
	 * @param element
	 *            the old element we search
	 * @param matches
	 *            the list of match we will run into
	 * @return the new element corresponding to the old one if any, return null if this element doesn't exist
	 */
	private static EObject getNewElementFromOldElement(EObject element, EList<Match> matches) {
		for (Match match : matches) {
			if (getOldElement(match) == element) {
				return getNewElement(match);
			}
			EObject result = getNewElementFromOldElement(element, match.getSubmatches());
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Get the nearest profile owning the treeNode
	 * 
	 * @param treeNode
	 * @return the nearest profile owning the treeNode
	 */
	public static Profile getNearestProfile(TreeNode treeNode) {
		if (treeNode.getData() instanceof Match) {
			Match match = (Match) treeNode.getData();
			if (match.getLeft() instanceof Profile) {
				return (Profile) match.getLeft();
			}
		}
		return getNearestProfile(treeNode.getParent());
	}

}

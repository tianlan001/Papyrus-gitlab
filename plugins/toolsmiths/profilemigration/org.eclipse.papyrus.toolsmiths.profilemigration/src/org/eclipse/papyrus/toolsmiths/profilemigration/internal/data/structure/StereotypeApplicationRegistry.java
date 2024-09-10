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

package org.eclipse.papyrus.toolsmiths.profilemigration.internal.data.structure;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.tree.TreeNode;
import org.eclipse.papyrus.toolsmiths.profilemigration.internal.utils.ProfileUtil;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Registry of each StereotypeApplicationDescriptor in the model
 */
public class StereotypeApplicationRegistry {

	/**
	 * This list contain all stereotype application of the profiled model before the migration
	 */
	public static List<StereotypeApplicationDescriptor> stereotypeApplicationList;

	private static Package model;

	/**
	 * Constructor, initialize the list of stereotype application
	 *
	 * @param treeNode
	 * @param profiledModel
	 */
	public StereotypeApplicationRegistry(TreeNode treeNode, Package profiledModel) {
		initList(treeNode, profiledModel);
		StereotypeApplicationRegistry.model = profiledModel;
	}

	/**
	 * Get the list of StereotypeApplicationDescriptor corresponding to the stereotype
	 *
	 * @param stereotype
	 *            a stereotype from the model
	 * @return the list of StereotypeApplicationDescriptor corresponding to the stereotype
	 */
	public static List<StereotypeApplicationDescriptor> getStereotypeApplicationDescriptors(Stereotype stereotype) {
		List<StereotypeApplicationDescriptor> result = new ArrayList<>();
		for (StereotypeApplicationDescriptor descriptor : stereotypeApplicationList) {
			if (descriptor.getStereotype().equals(stereotype)) {
				result.add(descriptor);
			}
		}
		return result;
	}

	/**
	 * Get the list of StereotypeApplicationDescriptor corresponding to the stereotype and sub stereotypes
	 * Warning: this method give only stereotype find is applied profiles
	 *
	 * @param stereotype
	 *            a stereotype from the model
	 * @return the list of StereotypeApplicationDescriptor corresponding to the modelStereotype and sub stereotypes
	 */
	public static List<StereotypeApplicationDescriptor> getAllStereotypeApplicationDescriptors(Stereotype stereotype) {
		List<StereotypeApplicationDescriptor> result = new ArrayList<>();
		for (StereotypeApplicationDescriptor descriptor : stereotypeApplicationList) {
			for (Stereotype subStereotype : ProfileUtil.findAllSubStereotypes(stereotype, model, false)) {
				if (descriptor.getStereotype().equals(subStereotype)) {
					result.add(descriptor);
				}
			}
		}
		return result;
	}

	/**
	 * Get the list of StereotypeApplicationDescriptor corresponding to stereotype application on the element
	 *
	 * @param element
	 *
	 * @return the list of StereotypeApplicationDescriptor corresponding to stereotype application on the element
	 */
	public static List<StereotypeApplicationDescriptor> getAllStereotypeApplicationDescriptorsFromOwner(Element element) {
		List<StereotypeApplicationDescriptor> result = new ArrayList<>();
		for (StereotypeApplicationDescriptor descriptor : stereotypeApplicationList) {
			if (descriptor.getOwner().equals(element)) {
				result.add(descriptor);
			}
		}
		return result;
	}

	/**
	 * Get the StereotypeApplicationDescriptor corresponding to the application of the stereotype on the owner
	 *
	 * @param owner
	 *            the owner of the stereotype
	 * @param stereotype
	 *            a stereotype from the model
	 * @return the StereotypeApplicationDescriptor corresponding to the application of the stereotype on the owner
	 */
	public static StereotypeApplicationDescriptor getStereotypeApplicationDescriptors(Element owner, Stereotype stereotype) {
		StereotypeApplicationDescriptor result = null;
		for (StereotypeApplicationDescriptor descriptor : stereotypeApplicationList) {
			if (descriptor.getOwner().equals(owner) && descriptor.getStereotype().equals(stereotype)) {
				result = descriptor;
			}
		}
		return result;
	}

	/**
	 * Initialize the list of stereotype application in the specified model
	 *
	 * @param treeNode
	 * @param profiledModel
	 */
	private void initList(TreeNode treeNode, Package profiledModel) {
		// get all stereotypes in the treeNode
		Set<Stereotype> treeNodeStereotypes = new HashSet<>();
		getStereotypes(treeNode, treeNodeStereotypes);

		// fill the list
		stereotypeApplicationList = new ArrayList<>();
		TreeIterator<EObject> iter = profiledModel.eAllContents();
		while (iter.hasNext()) {
			EObject object = iter.next();
			if (object instanceof Element) {
				Element element = (Element) object;
				for (Stereotype stereotype : element.getAppliedStereotypes()) {
					stereotypeApplicationList.add(new StereotypeApplicationDescriptor(stereotype, element));
				}
			}
		}
	}

	/**
	 * Get all stereotype find in the treeNode
	 *
	 * @param treeNode
	 * @param result
	 *            the set of finding stereotype
	 */
	private void getStereotypes(TreeNode treeNode, Set<Stereotype> result) {
		if (treeNode.getData() instanceof Match) {
			if (((Match) treeNode.getData()).getRight() instanceof EAnnotation) {
				return; // not interesting, this is the record of different version of the profile
			}
			if (((Match) treeNode.getData()).getLeft() instanceof Stereotype) {
				result.add((Stereotype) ((Match) treeNode.getData()).getLeft());
			} else if (((Match) treeNode.getData()).getRight() instanceof Stereotype) {
				result.add((Stereotype) ((Match) treeNode.getData()).getRight());
			}
		}
		for (TreeNode child : treeNode.getChildren()) {
			getStereotypes(child, result);
		}
	}
}

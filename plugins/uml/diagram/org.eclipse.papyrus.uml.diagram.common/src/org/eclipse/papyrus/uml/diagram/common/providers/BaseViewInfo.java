/*******************************************************************************
 * Copyright (c) 2009 Conselleria de Infraestructuras y Transporte, Generalitat
 * de la Comunitat Valenciana . All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution, and is
t https://www.eclipse.org/legal/epl-2.0/
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Francisco Javier Cano Muñoz (Prodevelop) – Initial implementation
 *
 ******************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A basic implementation of {@link ViewInfo}. This class is used to parse the
 * 'es.cv.gvcase.mdt.common.viewInfo' extension point. It can handle the additions of child nodes
 * that have parents that have not yet been added; these nodes are stores in a temporary storage.
 * Upon a later addition of a node, nodes in the temporary storage are revisited to put them in
 * their correct place.
 *
 * @author <a href="mailto:fjcano@prodevelop.es">Francisco Javier Cano Muñoz</a>
 * @NOT-generated
 */
public class BaseViewInfo implements ViewInfo {

	// // attributes

	/** The parent ViewInfo in the hierarchy. */
	private ViewInfo parentViewInfo = null;

	/** The visual id in String form. */
	public String visualID = "-1";

	/** The type in String form. */
	public String type;

	/** The type in integer form. */
	private int typeViewInfo = ViewInfo.None;

	/** The label that will be shown. */
	public String label = null;

	/** The children of this ViewInfo in the hierarchy. */
	private Collection<ViewInfo> children = null;

	/** Temporary storage of ViewInfo elements to be added. */
	private static Map<String, Collection<ViewInfo>> toAdd = null;

	/** Identifier of the IElementType this ViewInfo represents. */
	// @unused
	public String elementType;

	/** VisualID in String form of this ViewInfo's parent. */
	public String parent;

	/** Pointer to the RootInfo which this ViewInfo belong to. */
	public RootViewInfo rootViewInfo;

	/** Flag that indicates if this ViewInfo can be selected to be filtered. */
	public Boolean selectable = true;

	// // constructors

	/**
	 * Instantiates a new base view info.
	 */
	// @unused
	public BaseViewInfo() {
		// empty constructor
	}

	/**
	 * Instantiates a new base view info.
	 *
	 * @param visualID
	 *            the visual id
	 * @param type
	 *            the type
	 * @param label
	 *            the label
	 */
	public BaseViewInfo(String visualID, int type, String label) {
		this(visualID, type, label, null, null);
	}

	/**
	 * Instantiates a new base view info.
	 *
	 * @param visualID
	 *            the visual id
	 * @param type
	 *            the type
	 * @param label
	 *            the label
	 * @param children
	 *            the children
	 * @param parent
	 *            the parent
	 */
	public BaseViewInfo(String visualID, int type, String label, Collection<ViewInfo> children, ViewInfo parent) {
		this.visualID = visualID;
		this.typeViewInfo = type;
		this.label = label;
		this.children = children;
		this.parentViewInfo = parent;
	}

	// // getters

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#getChildren()
	 */
	@Override
	public Collection<ViewInfo> getChildren() {
		if (children == null) {
			children = new ArrayList<>();
		}
		return children;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#getLabel()
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#getType()
	 */
	@Override
	public int getType() {
		// type by its integer form
		switch (typeViewInfo) {
		case ViewInfo.Head:
		case ViewInfo.Node:
		case ViewInfo.Edge:
		case ViewInfo.Label:
			return typeViewInfo;
		default: {
			// type by its String form
			if (type != null && type.length() > 0) {
				if (ViewInfo.NONE_LITERAL.equals(type)) {
					return ViewInfo.None;
				} else if (ViewInfo.HEAD_LITERAL.equals(type)) {
					return ViewInfo.Head;
				} else if (ViewInfo.NODE_LITERAL.equals(type)) {
					return ViewInfo.Node;
				} else if (ViewInfo.EDGE_LITERAL.equals(type)) {
					return ViewInfo.Edge;
				} else if (ViewInfo.LABEL_LITERAL.equals(type)) {
					return ViewInfo.Label;
				} else {
					return ViewInfo.None;
				}
			} else {
				// unknown
				return -1;
			}
		}
		}
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#getVisualID()
	 */
	@Override
	public String getVisualID() {
		return visualID;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#getParent()
	 */
	@Override
	public ViewInfo getParent() {
		return parentViewInfo;
	}

	/**
	 * Indicates whether this ViewInfo can be selected to be filtered.
	 */
	@Override
	public boolean isSelectable() {
		return selectable;
	}

	/**
	 * Gets the temporary storage for nodes without parents in the hierarchy.
	 *
	 * @return the to add
	 */
	protected static Map<String, Collection<ViewInfo>> getToAdd() {
		if (toAdd == null) {
			toAdd = new HashMap<>();
		}
		return toAdd;
	}

	// // setters

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#setChildren(java.util.Collection )
	 */
	@Override
	public void setChildren(Collection<ViewInfo> children) {
		this.children = children;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#setLabel(java.lang.String)
	 */
	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#setParent(es.cv.gvcase.mdt. common.provider.ViewInfo)
	 */
	@Override
	public void setParent(ViewInfo parent) {
		this.parentViewInfo = parent;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#setType(int)
	 */
	@Override
	public void setType(int type) {
		this.typeViewInfo = type;
	}

	/**
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#setVisualID(String)
	 */
	@Override
	public void setVisualID(String visualID) {
		this.visualID = visualID;
	}

	/**
	 * Gets the root info which this ViewInfo belong to.
	 *
	 * @return the root info
	 */
	protected ViewInfo getRootInfo() {
		ViewInfo parent = null, aux = this;
		while ((parent = aux.getParent()) != null) {
			aux = parent;
		}
		return aux;
	}

	/**
	 * Checks whether ViewInfo is the higher in the hierarchy.
	 *
	 * @return true, if is root
	 */
	protected boolean isRoot() {
		ViewInfo root = getRootInfo();
		if (root != null && root == this) {
			return true;
		}
		return false;
	}

	// // add a node

	/**
	 * Adds a node in its corresponding place in the hierarchy. The parent node
	 * needs not exist yet in the hierarchy. Orphan nodes are stored in a
	 * temporary storage, that is revisited when a new node is added.
	 *
	 * @see es.cv.gvcase.mdt.common.provider.ViewInfo#addNode(String, es.cv.gvcase.mdt.common.provider.ViewInfo)
	 */
	@Override
	public boolean addNode(String parentVisualID, ViewInfo info) {
		if (isAlreadyContained(info)) {
			return true;
		}
		if (internalAddNode(parentVisualID, info)) {
			if (isRoot()) {
				revisePendentNodes(info);
			}
			return true;
		} else {
			if (isRoot()) {
				addPendentNode(parentVisualID, info);
			}
			return false;
		}
	}

	/**
	 * Checks if a ViewInfo is already contained in the hierarchy.
	 *
	 * @param info
	 *            the info
	 *
	 * @return true, if is already contained
	 */
	protected boolean isAlreadyContained(ViewInfo info) {
		if (info == null) {
			return true;
		}
		return lookInChildren(this, info);
	}

	/**
	 * Look in children for a given ViewInfo.
	 *
	 * @param info
	 *            the info
	 * @param lookFor
	 *            the look for
	 *
	 * @return true, if successful
	 */
	protected boolean lookInChildren(ViewInfo info, ViewInfo lookFor) {
		if (info == lookFor) {
			return true;
		}
		for (ViewInfo child : info.getChildren()) {
			if (lookInChildren(child, lookFor)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds a node that was in the temporary storage and its parent node has
	 * just been added to the hierarchy.
	 *
	 * @param parentID
	 *            the parent id
	 * @param info
	 *            the info
	 */
	private void addPendentNode(String parentID, ViewInfo info) {
		Map<String, Collection<ViewInfo>> toAdd = getToAdd();
		if (toAdd.containsKey(parentID) == false) {
			toAdd.put(parentID, new ArrayList<ViewInfo>());
		}
		if (toAdd.get(parentID).contains(info) == false) {
			toAdd.get(parentID).add(info);
		}
	}

	/**
	 * Revise pending nodes in the temporary storage. Nodes whose parent has
	 * been added will be put in their corresponding in the hierarchy.
	 *
	 * @param info
	 *            the info
	 */
	private void revisePendentNodes(ViewInfo info) {
		String parentVisualID = info.getVisualID();
		Map<String, Collection<ViewInfo>> toAdd = getToAdd();
		if (toAdd.containsKey(parentVisualID)) {
			for (ViewInfo viewInfo : toAdd.get(parentVisualID)) {
				if (info.getChildren().contains(viewInfo) == false && info != viewInfo && isAlreadyContained(viewInfo) == false) {
					info.getChildren().add(viewInfo);
					if (viewInfo.getParent() == null) {
						viewInfo.setParent(info);
					}
				}
			}
			toAdd.remove(parentVisualID);
		}
	}

	/**
	 * Internal add node.
	 *
	 * @param parentVisualID
	 *            the parent visual id
	 * @param info
	 *            the info
	 *
	 * @return true, if successful
	 */
	protected boolean internalAddNode(String parentVisualID, ViewInfo info) {
		if (info == null) {
			return true;
		}
		if (getVisualID().equals(parentVisualID)) {
			if (getChildren().contains(info) == false && this != info) {
				getChildren().add(info);
			}
			if (info.getParent() == null) {
				info.setParent(this);
			}
			return true;
		}
		for (ViewInfo viewInfo : getChildren()) {
			if (viewInfo.addNode(parentVisualID, info)) {
				return true;
			}
		}
		return false;
	}

	// // for debugging purposes
	/**
	 * A Debugging method. Shows some info.
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String superString = super.toString();
		String myString = getVisualID() + ", " + getLabel();
		superString = superString != null ? superString + " :: " + myString : myString;
		return superString;
	}

}

/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The Class Node.
 */
public class Node {

	// expected to be unique
	/** The namespace. */
	// required only for diagram
	public String namespace = ""; // used as parameter to call the onclick
	
	/** The text. */
	public String text = ""; // text displayed in the tree

	/** The childs. */
	public List<Node> childs = new ArrayList<>();		
	
	/** The additional informations. */
	private Map<String,String> additionalInformations = new HashMap<>();
	
	/**
	 * Gets the additional informations.
	 *
	 * @return the additional informations
	 */
	public Map<String, String> getAdditionalInformations() {
		return additionalInformations;
	}

	/**
	 * Sets the additional informations.
	 *
	 * @param additionalInformations the additional informations
	 */
	public void setAdditionalInformations(Map<String, String> additionalInformations) {
		this.additionalInformations = additionalInformations;
	}


	/**
	 * Instantiates a new node.
	 *
	 * @param namespace the namespace
	 * @param text the text
	 */
	public Node(String namespace, String text) {
		this.namespace = namespace;
		this.text = text;
	}		
	
	/**
	 * Instantiates a new node.
	 *
	 * @param namespace the namespace
	 * @param text the text
	 * @param childs the childs
	 */
	public Node(String namespace, String text, List<Node> childs) {
		this.namespace = namespace;
		this.text = text;
		this.childs = childs;
	}	
	
	/**
	 * Instantiates a new node.
	 *
	 * @param namespace the namespace
	 * @param text the text
	 * @param child the child
	 */
	public Node(String namespace, String text, Node child) {
		this.namespace = namespace;
		this.text = text;
		this.childs.add(child); 
	}	
	
	/**
	 * Gets the childby name.
	 *
	 * @param name the name
	 * @return the childby name
	 */
	public Node getChildbyName(String name) {
		for (Node node : childs) {
			if (name.equals(node.text)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Adds the child.
	 *
	 * @param node the node
	 */
	public void addChild(Node node) {
		childs.add(node);
	}
	
}

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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;
import org.eclipse.papyrus.uml.export.extension.AdditionalInformations;
import org.eclipse.uml2.uml.NamedElement;


/**
 * A factory for creating UMLTree objects.
 */
public class UMLTreeFactory {

	/** The additionnal datas. */
	private Map<String, AdditionalInformations> additionnalDatas = new HashMap<>();

	/** The tree. */
	private Node tree = null;

	/**
	 * Instantiates a new UML tree factory.
	 *
	 * @param additionnalDatas the additionnal datas
	 */
	public UMLTreeFactory(Map<String, AdditionalInformations> additionnalDatas) {
		this.additionnalDatas = additionnalDatas;
	}

	/**
	 * Adds the diagram.
	 *
	 * @param diagram the diagram
	 * @return the node
	 */
	public Node addDiagram(Diagram diagram) {
		List<NamedElement> pathToRoot = getPathToRoot(diagram);
		if (tree == null) {
			tree = createSubTree(pathToRoot, diagram);
		} else {
			Collections.reverse(pathToRoot);
			addElementToTree(tree, pathToRoot, diagram);
		}
		return tree;
	}

	/**
	 * Creates a new UMLTree object.
	 *
	 * @param pathToRoot the path to root
	 * @param diagram the diagram
	 * @return the node
	 */
	private Node createSubTree(List<NamedElement> pathToRoot, Diagram diagram) {
		Node node = createNode(diagram);
		for (NamedElement namedElement : pathToRoot) {
			node = createNode(namedElement, node);
		}
		return node;
	}

	/**
	 * Adds the element to tree.
	 *
	 * @param parentNode the parent node
	 * @param namespaceList the namespace list
	 * @param diagram the diagram
	 * @return the node
	 */
	private Node addElementToTree(Node parentNode, List<NamedElement> namespaceList, Diagram diagram) {
		if (namespaceList.size() > 1) {
			NamedElement namedElement = namespaceList.get(1);
			namespaceList.remove(0);
			Node childbyName = parentNode.getChildbyName(namedElement.getName());
			if (childbyName != null) {
				addElementToTree(childbyName, namespaceList, diagram);
			} else {
				Node node = createNode(namedElement);
				parentNode.addChild(node);
				addElementToTree(node, namespaceList, diagram);
			}
		} else {
			Node diagramLeaf = createNode(diagram);
			parentNode.childs.add(diagramLeaf);
		}
		return parentNode;
	}

	/**
	 * Gets the additionnal datas.
	 *
	 * @return the additionnal datas
	 */
	public Map<String, AdditionalInformations> getAdditionnalDatas() {
		return additionnalDatas;
	}

	/**
	 * Sets the additionnal datas.
	 *
	 * @param additionnalDatas the additionnal datas
	 */
	public void setAdditionnalDatas(Map<String, AdditionalInformations> additionnalDatas) {
		this.additionnalDatas = additionnalDatas;
	}

	/**
	 * Gets the tree.
	 *
	 * @return the tree
	 */
	public Node getTree() {
		return tree;
	}

	/**
	 * Sets the tree.
	 *
	 * @param tree the new tree
	 */
	public void setTree(Node tree) {
		this.tree = tree;
	}

	/**
	 * Gets the path to root.
	 *
	 * @param diagram the diagram
	 * @return the path to root
	 */
	public List<NamedElement> getPathToRoot(Diagram diagram) {
		List<NamedElement> namespaceList = new ArrayList<>();
		for (Object object : diagram.getStyles()) { 
			if (object instanceof PapyrusDiagramStyle) {
				EObject owner = ((PapyrusDiagramStyle) object).getOwner();
				while (owner instanceof NamedElement) {
					NamedElement namedOwner = (NamedElement) owner;
					namespaceList.add(namedOwner);
					owner = namedOwner.getOwner();
				}
			}
		}
		return namespaceList;
	}

	/**
	 * Creates a new UMLTree object.
	 *
	 * @param diagram the diagram
	 * @return the node
	 */
	public Node createNode(Diagram diagram) {
		String name = diagram.getName();
		Node node = new Node(name, name);
		node.setAdditionalInformations(getAdditionalDatasFor(diagram));
		return node;
	}

	/**
	 * Creates a new UMLTree object.
	 *
	 * @param namedElement the named element
	 * @return the node
	 */
	public Node createNode(NamedElement namedElement) {
		Node node = new Node(utilgetNamespace(namedElement), namedElement.getName());
		node.setAdditionalInformations(getAdditionalDatasFor(namedElement));
		return node;
	}

	/**
	 * Creates a new UMLTree object.
	 *
	 * @param namedElement the named element
	 * @param child the child
	 * @return the node
	 */
	public Node createNode(NamedElement namedElement, Node child) {
		Node node = new Node(utilgetNamespace(namedElement), namedElement.getName(), child);
		node.setAdditionalInformations(getAdditionalDatasFor(namedElement));
		return node;
	}

	/**
	 * Utilget namespace.
	 *
	 * @param namedElement the named element
	 * @return the string
	 */
	private static String utilgetNamespace(NamedElement namedElement) {
		String namespace = namedElement.getName();
		while (namedElement.getOwner() != null) {
			namespace = namedElement.getName() + namespace;
			namedElement = (NamedElement) namedElement.getOwner();

		}
		return namespace;
	}

	/**
	 * Gets the additional datas for.
	 *
	 * @param object the object
	 * @return the additional datas for
	 */
	public Map<String, String> getAdditionalDatasFor(Object object) {
		Map<String, String> res = new HashMap<>();
		for (Entry<String, AdditionalInformations> entry : additionnalDatas.entrySet()) {
			String data = entry.getValue().getData(object);
			if (data != null) {
				res.put(entry.getKey(), data);
			}
		}
		return res;
	}
}

/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.providers;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

/**
 * Example content provider for test purposes.
 */
public class TestContentProvider implements IStaticContentProvider, IHierarchicContentProvider, IAdaptableContentProvider {

	private static final Object[] NONE = {};

	private final Map<Object, Node> nodes;
	private final List<Object> elements;

	private TestContentProvider(Node... roots) {
		super();

		this.elements = Stream.of(roots)
				.map(Node::element)
				.collect(toList());
		this.nodes = Stream.of(roots).reduce(
				new HashMap<Object, Node>(),
				TestContentProvider::map,
				(acc, map) -> acc);
	}

	public static TestContentProvider example1() {
		return new TestContentProvider(
				n("root1",
						n("child1.1"),
						n("child1.2")),
				n("root2",
						n("child2.1"),
						n("child2.2",
								n("child2.2.1"),
								n("child2.2.2")),
						n("child2.3")),
				n("root3",
						n("child3.1"),
						n("child3.2")));
	}

	public static TestContentProvider example2() {
		return new TestContentProvider(
				n("A",
						n("1"),
						n("2")),
				n("B",
						n("3"),
						n("4",
								n("i"),
								n("ii")),
						n("5")),
				n("C",
						n("6"),
						n("7")));
	}

	public static ISemanticContentProviderFactory factory1() {
		return rset -> example1();
	}

	public static ISemanticContentProviderFactory factory2() {
		return rset -> example2();
	}

	private static Node n(Object element, Node... children) {
		Node result = new Node(element);
		Stream.of(children).forEach(child -> result.add(child));
		return result;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return elements.toArray();
	}

	@Override
	public void dispose() {
		nodes.clear();
		elements.clear();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// Pass
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return node(parentElement)
				.map(Node::children)
				.map(Collection::stream)
				.map(s -> s.map(Node::element))
				.map(Stream::toArray)
				.orElse(NONE);
	}

	private Optional<Node> node(Object element) {
		return Optional.ofNullable(nodes.get(element));
	}

	@Override
	public Object getParent(Object element) {
		return node(element)
				.map(Node::parent)
				.map(Node::element)
				.orElse(null);
	}

	@Override
	public boolean hasChildren(Object element) {
		return !node(element)
				.map(Node::children)
				.map(Collection::isEmpty)
				.orElse(true);
	}

	@Override
	public Object getAdaptedValue(Object containerElement) {
		return node(containerElement).orElse(null); // Just for something different but related
	}

	@Override
	public boolean isValidValue(Object element) {
		return node(element).isPresent();
	}

	@Override
	public Object[] getElements() {
		return elements.toArray();
	}

	static Map<Object, Node> map(Map<Object, Node> map, Node node) {
		node.map(map);
		return map;
	}


	//
	// Nested types
	//

	private static class Node {
		private final Object element;
		private final List<Node> children = new ArrayList<>(3);
		private Node parent;

		Node(Object element) {
			this(element, null);
		}

		Node(Object element, Node parent) {
			super();

			this.element = element;
			if (parent != null) {
				parent.add(this);
			}
		}

		void add(Node child) {
			child.parent = this;
			children.add(child);
		}

		Object element() {
			return element;
		}

		Node parent() {
			return parent;
		}

		List<Node> children() {
			return Collections.unmodifiableList(children);
		}

		void map(Map<Object, Node> map) {
			map.put(element, this);
			if (!children.isEmpty()) {
				children.forEach(child -> child.map(map));
			}
		}

	}
}

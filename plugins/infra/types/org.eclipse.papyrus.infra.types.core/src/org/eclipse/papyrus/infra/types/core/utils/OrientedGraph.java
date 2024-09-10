/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.types.core.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OrientedGraph<T extends Object> {
	private Map<T, Set<T>> graph;

	/**
	 * @return the vertices
	 */
	public Set<T> getVertices() {
		return graph.keySet();
	}

	/**
	 * @return the edges
	 */
	public Map<T, Set<T>> getEdges() {
		return graph;
	}

	public Set<T> getAllConnex(T element) {
		Set<T> result = new HashSet<T>();

		Set<T> directChildren = graph.get(element);

		if (directChildren != null) {
			result.addAll(directChildren);
			for (T child : directChildren) {
				Set<T> childChildren = getAllConnex(child);
				result.addAll(childChildren);
			}
		}

		return result;
	}


	public OrientedGraph() {
		graph = new HashMap<T, Set<T>>();
	}

	public OrientedGraph(Map<T, Set<T>> edges) {
		this.graph = edges;
	}

	public void clear() {
		graph.clear();
	}

	public void addEdge(T source, T target) {
		if (!graph.containsKey(source)) {
			addVertex(source);
		}
		if (!graph.containsKey(target)) {
			addVertex(target);
		}

		graph.get(source).add(target);
	}

	public void addVertex(T vertex) {
		if (!graph.containsKey(vertex)) {
			graph.put(vertex, new HashSet<T>());
		}

	}

	public String toString() {
		String result = "";
		for (T element : graph.keySet()) {
			result += "- " + element + "\n";
			for (T target : graph.get(element)) {
				result += "\t-> " + target + "\n";
			}
		}

		return result;

	}

}

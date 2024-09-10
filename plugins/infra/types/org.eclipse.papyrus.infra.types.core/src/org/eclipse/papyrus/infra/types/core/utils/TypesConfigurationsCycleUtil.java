/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST and others.
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
 *   Alain Le Guennec (Esterel Technologies) - bug 519809
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.gmf.runtime.emf.type.core.IAdviceBindingDescriptor;
import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ExternallyRegisteredAdvice;
import org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.registries.AdviceConfigurationTypeRegistry;

//Implements Tarjan's strongly connected components algorithm
public class TypesConfigurationsCycleUtil {


	protected static Collection<Collection<Object>> computeStronglyConnectedComponents(Set<String> vertices, Map<String, Set<String>> edges) {
		int index = 0;
		Map<String, Integer> lowIndex = new HashMap<String, Integer>();
		Collection<String> visitedVertices = new HashSet<String>();
		Stack<String> stack = new Stack<String>();
		Collection<Collection<Object>> stronglyConnnectedComponents = new ArrayList<Collection<Object>>();

		for (String vertex : vertices) {
			if (!visitedVertices.contains(vertex))
				dfs(vertex, vertices, edges, stronglyConnnectedComponents, stack, lowIndex, visitedVertices, index);
		}

		return stronglyConnnectedComponents;
	}


	protected static void dfs(String vertex, Set<String> vertices, Map<String, Set<String>> map, Collection<Collection<Object>> stronglyConnnectedComponents, Stack<String> stack, Map<String, Integer> lowIndex, Collection<String> visitedVertices,
			int index) {
		lowIndex.put(vertex, index++);
		visitedVertices.add(vertex);
		stack.push(vertex);
		int minIndex = lowIndex.get(vertex);
		for (String targetVertex : map.get(vertex)) {
			if (!visitedVertices.contains(targetVertex))
				dfs(targetVertex, vertices, map, stronglyConnnectedComponents, stack, lowIndex, visitedVertices, index);
			if (lowIndex.get(targetVertex) < minIndex)
				minIndex = lowIndex.get(targetVertex);
		}
		if (minIndex < lowIndex.get(vertex)) {
			lowIndex.put(vertex, minIndex);
			return;
		}
		List<Object> component = new ArrayList<Object>();
		String memberVertex;
		do {
			memberVertex = stack.pop();
			component.add(memberVertex);
			lowIndex.put(memberVertex, vertices.size());
		} while (!memberVertex.equals(vertex));

		if (component.size() > 1) {
			stronglyConnnectedComponents.add(component);
		}
	}

	/**
	 * @since 3.0
	 */
	static public Map<String, OrientedGraph<String>> getDependenciesAmongAdvices(Collection<AdviceConfiguration> adviceConfigurations) {
		Map<String, OrientedGraph<String>> adviceDependencies = new HashMap<String, OrientedGraph<String>>();
		for (AdviceConfiguration adviceConfiguration : adviceConfigurations) {
			IAdviceBindingDescriptor descriptor = AdviceConfigurationTypeRegistry.getInstance().getEditHelperAdviceDecriptor(adviceConfiguration);

			String type = descriptor.getTypeId();

			String currentAdviceConfigurationClassName = (adviceConfiguration instanceof ExternallyRegisteredAdvice)
				? ((ExternallyRegisteredAdvice) adviceConfiguration).getEditHelperAdviceClassName()
				: descriptor.getEditHelperAdvice().getClass().getName();
			// Add current to the vertices
			if (!adviceDependencies.containsKey(type)) {
				adviceDependencies.put(type, new OrientedGraph<String>());
			}
			OrientedGraph<String> deps = adviceDependencies.get(type);
			deps.addVertex(currentAdviceConfigurationClassName);

			// Add dependencies vertices
			for (AdviceConfiguration after : adviceConfiguration.getAfter()) {
				IAdviceBindingDescriptor descriptorAfter = AdviceConfigurationTypeRegistry.getInstance().getEditHelperAdviceDecriptor(after);
				deps.addEdge(currentAdviceConfigurationClassName, descriptorAfter.getEditHelperAdvice().getClass().getName());
			}

			for (AdviceConfiguration before : adviceConfiguration.getBefore()) {
				IAdviceBindingDescriptor descriptorBefore = AdviceConfigurationTypeRegistry.getInstance().getEditHelperAdviceDecriptor(before);
				deps.addEdge(descriptorBefore.getEditHelperAdvice().getClass().getName(), currentAdviceConfigurationClassName);
			}
		}

		return adviceDependencies;
	}

	static public Collection<Collection<Object>> getCyclesInAdvices(Set<String> vertices, Map<String, Set<String>> edges) {

		return computeStronglyConnectedComponents(vertices, edges);
	}

	static public OrientedGraph<String> getDependenciesAmongElementTypes(Collection<ElementTypeConfiguration> elementTypesConfigurations) {
		OrientedGraph<String> elementTypeDependencies = new OrientedGraph<String>();

		for (ElementTypeConfiguration elementTypeConfiguration : elementTypesConfigurations) {
			String currentElementTypeID = elementTypeConfiguration.getIdentifier();

			// Add current to the vertices
			elementTypeDependencies.addVertex(currentElementTypeID);

			// Add dependencies vertices
			if (elementTypeConfiguration instanceof SpecializationTypeConfiguration) {
				for (ElementTypeConfiguration specializedType : ((SpecializationTypeConfiguration) elementTypeConfiguration).getSpecializedTypes()) {
					elementTypeDependencies.addEdge(currentElementTypeID, specializedType.getIdentifier());
				}
			}
		}

		return elementTypeDependencies;
	}

	static public Collection<Collection<Object>> getCyclesAmongElementTypes(Set<String> vertices, Map<String, Set<String>> edges) {
		return computeStronglyConnectedComponents(vertices, edges);
	}
}

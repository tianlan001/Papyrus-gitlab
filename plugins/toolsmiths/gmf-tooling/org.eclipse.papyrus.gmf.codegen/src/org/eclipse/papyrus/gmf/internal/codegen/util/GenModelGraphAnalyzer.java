/******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, Artal and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Alexander Shatalin (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContainerBase;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLink;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorChildReference;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigatorReferenceType;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNode;

/**
 * GenNavigatorChildReference may relate parent and child not directly connected to each other (but not completely unrelated, though),  
 * consider child nodes placed into compartments (which we don't want to visualize in the navigator tree).
 * To accomplish that, we build connection paths for those non-directly related elements, and use these paths at code generation time.
 * This class is basically an utility to find connection path for any parent-child pair of navigator elements demanded by user with 
 * GenNavigatorChildReference in its genmodel. 
 */
public class GenModelGraphAnalyzer {

	/**
	 * @deprecated
	 */
	public GenModelGraphAnalyzer(GenDiagram diagram) {
	}

	public static List<List<GenCommonBase>> getConnectionPaths(GenNavigatorChildReference reference) {
		assert reference.getParent() != null;
		if (reference.getReferenceType() == GenNavigatorReferenceType.CHILDREN_LITERAL) {
			return getChildConnectionPaths(reference.getParent(), reference.getChild(), reference.getNavigator());
		} else if (reference.getReferenceType() == GenNavigatorReferenceType.IN_SOURCE_LITERAL) {
			return getInSourceLinkConnectionPaths(reference.getParent(), reference.getChild(), reference.getNavigator());
		} else if (reference.getReferenceType() == GenNavigatorReferenceType.OUT_TARGET_LITERAL) {
			return getOutTargetLinkConnectionPaths(reference.getParent(), reference.getChild(), reference.getNavigator());
		}
		return Collections.emptyList();
	}

	private static List<List<GenCommonBase>> getOutTargetLinkConnectionPaths(GenCommonBase source, GenCommonBase target, GenNavigator genNavigator) {
		return new LinkedConnectionFinder(genNavigator, true).findConnectionPaths(source, target);
	}

	private static List<List<GenCommonBase>> getInSourceLinkConnectionPaths(GenCommonBase source, GenCommonBase target, GenNavigator genNavigator) {
		return new LinkedConnectionFinder(genNavigator, false).findConnectionPaths(source, target);
	}

	private static List<List<GenCommonBase>> getChildConnectionPaths(GenCommonBase source, GenCommonBase target, GenNavigator genNavigator) {
		return new ChildConnectionFinder(genNavigator).findConnectionPaths(source, target);
	}

	private static abstract class AbstractConnectionFinder {

		private Set<GenCommonBase> myVisiting;
		
		private GenNavigator myNavigator;

		public AbstractConnectionFinder(GenNavigator genNavigator) {
			myVisiting = new LinkedHashSet<GenCommonBase>();
			myNavigator = genNavigator;
		}

		protected abstract Collection<GenCommonBase> getConnectedNodes(GenCommonBase source);

		public List<List<GenCommonBase>> findConnectionPaths(GenCommonBase source, GenCommonBase target) {
			if (isConnectionFound(source, target)) {
				// Direct connection found
				List<GenCommonBase> path = new ArrayList<GenCommonBase>();
				path.add(target);
				List<List<GenCommonBase>> connections = new ArrayList<List<GenCommonBase>>();
				connections.add(path);
				return connections;
			}

			if (isVisiting(source) || stopIterating(source)) {
				// Loop found
				return new ArrayList<List<GenCommonBase>>();
			}
			startVisiting(source);
			try {
				// Looking for indirect connection + extending connection with
				// current node
				Collection<GenCommonBase> connectedNodes = getConnectedNodes(source);
				List<List<GenCommonBase>> connections = new ArrayList<List<GenCommonBase>>();

				for (GenCommonBase nextConnectedNode : connectedNodes) {
					connections.addAll(findConnectionPaths(nextConnectedNode, target));
				}

				for (List<GenCommonBase> path : connections) {
					path.add(0, source);
				}
				return connections;
			} finally {
				stopVisiting(source);
			}
		}

		private boolean stopIterating(GenCommonBase source) {
			if (myVisiting.size() > 0) {
				for (GenNavigatorChildReference nextReference : myNavigator.getChildReferences()) {
					if (nextReference.getParent() == source) {
						return true;
					}
				}				
			} 
			return false;
		}

		private boolean isVisiting(GenCommonBase node) {
			return myVisiting.contains(node);
		}

		private void startVisiting(GenCommonBase node) {
			myVisiting.add(node);
		}

		private void stopVisiting(GenCommonBase node) {
			myVisiting.remove(node);
		}

		protected Iterable<GenCommonBase> getPath() {
			return myVisiting;
		}

		/**
		 * myVisiting.size() > 0 checked return non-empty paths if source and
		 * target nodes are the same
		 */
		private boolean isConnectionFound(GenCommonBase source, GenCommonBase target) {
			return myVisiting.size() > 0 && source == target;
		}

	}

	private static class ChildConnectionFinder extends AbstractConnectionFinder {

		public ChildConnectionFinder(GenNavigator genNavigator) {
			super(genNavigator);
		}

		protected Collection<GenCommonBase> getConnectedNodes(GenCommonBase source) {
			Collection<GenCommonBase> children = new ArrayList<GenCommonBase>();
			if (source instanceof GenContainerBase) {
				children.addAll(((GenContainerBase) source).getContainedNodes());
			}
			if (source instanceof GenDiagram) {
				children.addAll(((GenDiagram) source).getLinks());
			}
			if (source instanceof GenNode) {
				children.addAll(((GenNode) source).getCompartments());
				children.addAll(((GenNode) source).getLabels());
			}
			if (source instanceof GenLink) {
				children.addAll(((GenLink) source).getLabels());
			}
			return children;
		}

	}

	private static class LinkedConnectionFinder extends AbstractConnectionFinder {

		private GenDiagram myDiagram;

		/**
		 * true: looking for the connection from source to target using all
		 * links in natural direction (outgoing links -> link target -> outgoing
		 * links...)
		 * 
		 * false: opposite direction (incomming links -> link source -> ..)
		 */
		private boolean myIsInLinkDirection;

		public LinkedConnectionFinder(GenNavigator genNavigator, boolean inLinkDirection) {
			super(genNavigator);
			myDiagram = genNavigator.getEditorGen().getDiagram();
			myIsInLinkDirection = inLinkDirection;
		}

		protected Collection<GenCommonBase> getConnectedNodes(GenCommonBase source) {
			if (source instanceof GenNode) {
				return getPotentiallyConnectedLinks((GenNode) source);
			} else if (source instanceof GenLink) {
				return getPotentiallyConnectedNodes((GenLink) source);
			}
			return Collections.emptyList();
		}

		private Collection<GenCommonBase> getPotentiallyConnectedLinks(GenNode node) {
			for (GenCommonBase nextPathElement : getPath()) {
				if (nextPathElement instanceof GenLink) {
					// Only one link allowed in the path
					return Collections.emptyList();
				}
			}
			// TODO: this method could be moved to GenNode
			Collection<GenCommonBase> potentialLinks = new ArrayList<GenCommonBase>();
			for (GenLink nextLink : myDiagram.getLinks()) {
				if (nextLink.getModelFacet() == null) {
					potentialLinks.add(nextLink);
				} else if (node.getModelFacet() != null) {
					GenClass genClass = myIsInLinkDirection ? nextLink.getModelFacet().getSourceType() : nextLink.getModelFacet().getTargetType();
					if (genClass != null && Extras.isSuperTypeOf(genClass.getEcoreClass(), node.getDomainMetaClass().getEcoreClass())) {
						potentialLinks.add(nextLink);
					}
				}
			}
			return potentialLinks;
		}

		// TODO: this method could be moved to GenLink
		private Collection<GenCommonBase> getPotentiallyConnectedNodes(GenLink link) {
			Collection<GenCommonBase> potentialNodes = new ArrayList<GenCommonBase>();
			if (link.getModelFacet() == null) {
				potentialNodes.addAll(myDiagram.getAllNodes());
			} else {
				GenClass genClass = myIsInLinkDirection ? link.getModelFacet().getTargetType() : link.getModelFacet().getSourceType();
				if (genClass != null) {
					for (GenNode nextNode : myDiagram.getAllNodes()) {
						if (nextNode.getModelFacet() == null) {
							// skipping pure design nodes - cannot be incorrect
							// connection source/target
							continue;
						}
						if (Extras.isSuperTypeOf(genClass.getEcoreClass(), nextNode.getDomainMetaClass().getEcoreClass())) {
							potentialNodes.add(nextNode);
						}
					}
				}
			}
			return potentialNodes;
		}

	}

}

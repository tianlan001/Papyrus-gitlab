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
package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.cbi.targetplatform.model.Location;
import org.eclipse.cbi.targetplatform.model.TargetPlatform;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * @author Camille Letavernier
 *
 */
public class TPDUpdater extends DependencyUpdater<Location> {

	private Resource currentTarget;

	/**
	 * @see org.eclipse.papyrus.releng.tools.internal.popup.actions.DependencyUpdater#canUpdate(org.eclipse.core.resources.IFile)
	 *
	 * @param file
	 * @return
	 */
	@Override
	public boolean canUpdate(IFile file) {
		return "tpd".equals(file.getFileExtension());
	}

	@Override
	protected List<Location> getNodesToUpdate(IFile file) throws CoreException {
		ResourceSet resourceSet = new ResourceSetImpl();

		URI workspaceURI = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

		currentTarget = resourceSet.getResource(workspaceURI, true);

		for (EObject rootElement : currentTarget.getContents()) {
			if (rootElement instanceof TargetPlatform) {
				TargetPlatform tp = (TargetPlatform) rootElement;
				return tp.getLocations();
			}
		}

		return Collections.emptyList();
	}

	@Override
	protected void save(IFile file) throws Exception {
		currentTarget.save(null);
	}

	@Override
	protected String getCurrentLocation(Location uri) {
		return uri.getUri();
	}

	@Override
	protected void updateUri(Location uri, String location) {
		uri.setUri(location);
	}

	@Override
	protected String getComment(Location location) {
		List<String> comments = findCommentsAsString(location);

		for (String comment : comments) {
			if (comment.contains("updateFrom")) {
				return comment;
			}
		}

		return null;
	}

	/**
	 * Expected structure: the Location contains a Multiline or Single line comment before the location keyword
	 *
	 * <pre>
	 * // A Comment
	 * /* Another Comment /
	 * location locID "http://locURL/repo" {
	 * 		installable.unit1.id
	 * 		installable.unit2.id
	 * }
	 * </pre>
	 *
	 * @param location
	 * @return
	 */
	protected List<String> findCommentsAsString(Location location) {
		List<String> comments = new ArrayList<>();

		INode grammarNode = NodeModelUtils.getNode(location);
		if (grammarNode instanceof ICompositeNode) {
			ICompositeNode compositeNode = (ICompositeNode) grammarNode;
			for (INode child : compositeNode.getChildren()) {
				if (child instanceof ILeafNode) {
					ILeafNode leafNode = (ILeafNode) child;
					if (leafNode.isHidden()) {
						if (child.getGrammarElement() instanceof TerminalRule) {
							TerminalRule rule = (TerminalRule) child.getGrammarElement();
							String name = rule.getName();
							if ("SL_COMMENT".equals(name) || "ML_COMMENT".equals(name)) { //$NON-NLS-1$ //$NON-NLS-2$
								String text = leafNode.getText();
								text = text.replaceAll("[\\*/]", "").trim(); // Remove all / and */, as the leafNode is the raw element
								comments.add(text);
							}
						}
					}
				}
			}
		}

		return comments;
	}

}

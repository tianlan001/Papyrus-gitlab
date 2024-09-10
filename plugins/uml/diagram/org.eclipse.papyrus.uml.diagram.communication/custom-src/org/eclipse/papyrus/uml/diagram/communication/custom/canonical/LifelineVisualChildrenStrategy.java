/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

package org.eclipse.papyrus.uml.diagram.communication.custom.canonical;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.IVisualChildrenStrategy;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.LifelineEditPartCN;
import org.eclipse.uml2.uml.Message;

import com.google.common.collect.Lists;

/**
 * Custom visual-children strategy for lifelines in communication diagrams.
 *
 * @since 3.0
 */
public class LifelineVisualChildrenStrategy implements IVisualChildrenStrategy {

	public LifelineVisualChildrenStrategy() {
		super();
	}

	@Override
	public List<? extends Edge> getCanonicalChildren(EditPart editPart, View view) {
		return null;
	}

	@Override
	public List<? extends View> getCanonicalEdges(EditPart editPart, View view) {
		List<View> result = null;

		if (editPart instanceof LifelineEditPartCN) {
			// In the communication diagram, edges are just communication paths.
			// It is labels on these edges that represent the messages sent
			// and received by lifelines
			result = Lists.newArrayList();

			@SuppressWarnings("unchecked")
			List<? extends Edge> sourceEdges = view.getSourceEdges();
			for (Edge next : sourceEdges) {
				collectMessages(next, result);
			}

			@SuppressWarnings("unchecked")
			List<? extends Edge> targetEdges = view.getTargetEdges();
			for (Edge next : targetEdges) {
				// Don't process self-edges twice
				if (next.getSource() != view) {
					collectMessages(next, result);
				}
			}
		}

		return result;
	}

	private static void collectMessages(Edge edge, List<? super View> result) {
		@SuppressWarnings("unchecked")
		List<? extends View> labels = edge.getChildren();
		for (View next : labels) {
			if (next.getElement() instanceof Message) {
				result.add(next);
			}
		}
	}
}

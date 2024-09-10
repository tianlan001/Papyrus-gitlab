/*****************************************************************************
 * Copyright (c) 2017 EclipseSource Services GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Martin Fleck (EclipseSource) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.helper.advice;

import java.util.List;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyDependentsRequest;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Vertex;

import com.google.common.collect.Lists;

/**
 * Edit Helper Advice to delete incoming and outgoing transitions for deleted {@link Vertex} elements.
 * 
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class VertexEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * Default Constructor.
	 */
	public VertexEditHelperAdvice() {
	}

	@Override
	protected ICommand getBeforeDestroyDependentsCommand(DestroyDependentsRequest request) {
		if (!(request.getElementToDestroy() instanceof Vertex)) {
			return super.getBeforeDestroyDependentsCommand(request);
		}

		Vertex vertexToDestroy = (Vertex) request.getElementToDestroy();
		List<Transition> transitionsToDestroy = Lists.newArrayList(vertexToDestroy.getIncomings());
		transitionsToDestroy.addAll(vertexToDestroy.getOutgoings());
		return request.getDestroyDependentsCommand(transitionsToDestroy);
	}
}
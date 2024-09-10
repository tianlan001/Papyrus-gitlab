/*******************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 464647
 *     
 ******************************************************************************/
package org.eclipse.papyrus.mwe2.utils;

import java.util.List;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowComponent;
import org.eclipse.emf.mwe2.runtime.workflow.Workflow;

import com.google.common.collect.Lists;


/**
 * A workflow for Xtend transformations. Primarily, it provides Xtend-compatible access to
 * the collections manipulated by the inherited {@link Workflow#addBean(Object)} and
 * {@link Workflow#addComponent(IWorkflowComponent)} APIs.
 */
public class XtendWorkflow extends Workflow {

	protected List<Object> getBeans() {
		return Lists.newArrayList();
	}

	protected List<IWorkflowComponent> getComponents() {
		return getChildren();
	}

}
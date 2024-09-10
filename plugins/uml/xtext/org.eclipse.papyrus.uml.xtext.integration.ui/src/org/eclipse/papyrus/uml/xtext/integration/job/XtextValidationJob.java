/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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
 *   Jérémie Tatibouet (CEA LIST)
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.xtext.integration.job;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.validation.IValidationIssueProcessor;
import org.eclipse.xtext.ui.editor.validation.ValidationJob;
import org.eclipse.xtext.util.concurrent.IReadAccess;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

public class XtextValidationJob extends ValidationJob {

	private List<Issue> lastIssues;

	/**
	 * Constructor.
	 *
	 * @param xtextResourceChecker
	 * @param xtextDocument
	 * @param validationIssueProcessor
	 * @param checkMode
	 */
	public XtextValidationJob(IResourceValidator xtextResourceChecker, IReadAccess<XtextResource> xtextDocument, IValidationIssueProcessor validationIssueProcessor, CheckMode checkMode) {
		super(xtextResourceChecker, xtextDocument, validationIssueProcessor, checkMode);
		this.lastIssues = new ArrayList<Issue>();
	}

	/**
	 * @see org.eclipse.xtext.ui.editor.validation.ValidationJob#createIssues(org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * @param monitor
	 * @return
	 */
	@Override
	public List<Issue> createIssues(IProgressMonitor monitor) {
		this.lastIssues = super.createIssues(monitor);
		return this.lastIssues;
	}

	public List<Issue> getLastValidationIssues() {
		return this.lastIssues;
	}

	public boolean hasValidationIssues() {
		return !this.lastIssues.isEmpty();
	}
}

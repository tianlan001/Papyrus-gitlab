/*****************************************************************************
 * Copyright (c) 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr -  Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.xtext.integration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.validation.Issue;

/**
 * This class must be implemented to provide Xtext editor configuration
 *
 * @since 2.2
 */
public abstract class AbstractXtextDirectEditorConfiguration extends DefaultXtextDirectEditorConfiguration implements IXtextDirectEditorConfiguration {


	/**
	 * @param newString
	 * @param semanticObject
	 * @param parseResult
	 * @param errors
	 * @param issues
	 * @return
	 */
	@Override
	public ICommand createInvalidStringCommand(final String invalidString, final EObject semanticElement, final IParseResult parseResult, final List<Diagnostic> diagnostics, final List<Issue> issues) {
		return super.createInvalidStringCommand(invalidString, semanticElement, parseResult, diagnostics, issues);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration#getTextToEditInternal(org.eclipse.emf.ecore.EObject)
	 *
	 * @param semanticObject
	 * @return
	 */
	@Override
	public String getTextToEditInternal(final EObject semanticObject) {
		return super.getTextToEditInternal(semanticObject);
	}

	/**
	 * Adapts {@link IDirectEditorConfiguration} to gmfs {@link IParser} interface for reuse in GMF direct editing infrastructure.
	 *
	 * @param semanticEObject
	 *            the edited EObject
	 */
	@Override
	public final IParser createParser(final EObject semanticEObject) {
		if (objectToEdit == null) {
			objectToEdit = semanticEObject;
		}
		return doCreateParser(semanticEObject);
	}

	/**
	 * @param semanticEObject
	 *            the edited EObject
	 *
	 * @return
	 */
	protected IParser doCreateParser(final EObject semanticEObject) {
		return new DefaultXtextParser(this, semanticEObject);
	}
}

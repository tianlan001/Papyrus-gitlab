/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.xtext.integration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.services.validation.commands.AbstractValidateCommand;
import org.eclipse.papyrus.infra.services.validation.commands.AsyncValidateSubtreeCommand;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProviderWithInit;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

/**
 * Default implementation for the parser in a Xtext context.
 * This parser allows to check the string validity before to save the String.
 * When the string is not valid, the string is saved in a UML Comment, stereotype <<TextualRepresentation>> and owned by the edited element.
 * The validation of the string can be ignored setting {@link #ignoreValidationStep} to <code>true</code>.
 * In this case, the {@link IXtextDirectEditorConfiguration} must be able to save the string when it is invalid.
 * A typical usecase is the editing of String feature (like {@link OpaqueBehavior#body}), because with this usecase,
 * it is obvious we can save invalid string directly in the edited field.
 *
 * @since 2.2
 */
public class DefaultXtextParser implements IParser {

	/**
	 * the xtext editor configuration
	 */
	protected final IXtextDirectEditorConfiguration editorConfiguration;

	/**
	 * the edited semantic EObject
	 */
	protected final EObject semanticObject;

	/**
	 * when <code>true</code>, we won't check the validity of the typed string and we save it in the model
	 * mainly use when the edited string is set in the text field
	 */
	protected final boolean ignoreValidationStep;

	/**
	 *
	 * Constructor.
	 *
	 * @param editorConfiguration
	 *            the editor configuration
	 * @param semanticEObject
	 *            the edited semanticEObject
	 */
	public DefaultXtextParser(final IXtextDirectEditorConfiguration editorConfiguration, final EObject semanticEObject) {
		this(editorConfiguration, semanticEObject, false);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param editorConfiguration
	 *            the editor configuration
	 * @param semanticEObject
	 *            the edited semanticEObject
	 * @param ignoreValidationStep
	 *            when <code>true</code>, we won't check the validity of the typed string and we save it in the model
	 *            mainly use when the edited string is set in the text field
	 */
	public DefaultXtextParser(final IXtextDirectEditorConfiguration editorConfiguration, final EObject semanticEObject, final boolean ignoreValidationStep) {
		this.editorConfiguration = editorConfiguration;
		this.semanticObject = semanticEObject;
		this.ignoreValidationStep = ignoreValidationStep;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 *
	 * @param element
	 * @param flags
	 * @return
	 */
	@Override
	public String getEditString(final IAdaptable element, int flags) {
		return this.editorConfiguration.getTextToEditInternal(this.semanticObject);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 *
	 * @param element
	 * @param newString
	 * @param flags
	 * @return
	 */
	@Override
	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		CompositeCommand result = new CompositeCommand("validation"); //$NON-NLS-1$
		IContextElementProvider provider = this.editorConfiguration.getContextProvider();
		XtextFakeResourceContext context = new XtextFakeResourceContext(this.editorConfiguration.getInjector());
		final XtextResource xtextResource = context.getFakeResource();
		xtextResource.eAdapters().add(new ContextElementAdapter(provider));

		try {
			xtextResource.load(new StringInputStream(newString), Collections.EMPTY_MAP);
		} catch (IOException e) {
			org.eclipse.papyrus.uml.xtext.integration.ui.Activator.log.error(e);
		}
		if (provider instanceof IContextElementProviderWithInit) {
			((IContextElementProviderWithInit) provider).initResource(context.getFakeResource());
		}
		EcoreUtil2.resolveLazyCrossReferences(xtextResource, CancelIndicator.NullImpl);
		final IParseResult parseResult = xtextResource.getParseResult();
		if (this.ignoreValidationStep) {
			EObject xtextObject = parseResult.getRootASTElement();
			ICommand cmd = this.editorConfiguration.getParseCommand(this.semanticObject, xtextObject);
			if (cmd != null) {
				result.add(cmd);
			}
		} else {
			final IResourceValidator resVal = xtextResource.getResourceServiceProvider().getResourceValidator();
			final List<Issue> issues = resVal.validate(xtextResource, CheckMode.ALL, CancelIndicator.NullImpl);
			final List<Diagnostic> errors = context.getFakeResource().getErrors();
			if (!parseResult.hasSyntaxErrors() && errors.size() == 0 && issues.size() == 0) {
				EObject xtextObject = parseResult.getRootASTElement();
				ICommand cmd = this.editorConfiguration.getParseCommand(this.semanticObject, xtextObject);
				if (cmd != null) {
					result.add(cmd);
				}
			} else {
				result.add(this.editorConfiguration.createInvalidStringCommand(newString, this.semanticObject, parseResult, errors, issues));
			}
			AbstractValidateCommand validationCommand = new AsyncValidateSubtreeCommand(this.semanticObject);
			validationCommand.disableUIFeedback();
			result.add(validationCommand);
		}
		return result;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 *
	 * @param element
	 * @param flags
	 * @return
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		return this.editorConfiguration.getTextToEdit(this.semanticObject);
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isAffectingEvent(java.lang.Object, int)
	 *
	 * @param event
	 * @param flags
	 * @return
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		return false;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param element
	 * @return
	 */
	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		// Not used
		return null;
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 *
	 * @param element
	 * @param editString
	 * @return
	 */
	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		// Not used
		return null;
	}

}

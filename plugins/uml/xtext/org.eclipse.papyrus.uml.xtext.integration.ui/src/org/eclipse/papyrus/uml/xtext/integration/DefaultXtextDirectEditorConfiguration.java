/*****************************************************************************
 * Copyright (c) 2013, 2018 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Andreas Muelder - muelder@itemis.de -  Initial API and implementation
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - Bug 539293
 *****************************************************************************/
package org.eclipse.papyrus.uml.xtext.integration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.DefaultDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.ICustomDirectEditorConfiguration;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.IDirectEditorConfiguration;
import org.eclipse.papyrus.infra.services.validation.commands.AbstractValidateCommand;
import org.eclipse.papyrus.infra.services.validation.commands.AsyncValidateSubtreeCommand;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProvider;
import org.eclipse.papyrus.uml.xtext.integration.core.ContextElementAdapter.IContextElementProviderWithInit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

/**
 *
 * abstract base implementation of {@link ICustomDirectEditorConfiguration}
 * This class is used to configure and use the XText editors:
 * <ul>
 * <li>usage in Property View, Diagram and Table, as DirectEditor or CellEditor</li>
 * <li>manage the string parsing and provide final edition command</li>
 * <li>create a comment stereotyped in case of parsing error</li>
 * </ul>
 *
 *
 * @author andreas muelder - Initial contribution and API
 *         Ansgar Radermacher - Added possibility to configure context provider
 *
 * @deprecated since 2.2 use {@link AbstractXtextDirectEditorConfiguration} instead
 */
@Deprecated
public abstract class DefaultXtextDirectEditorConfiguration extends DefaultDirectEditorConfiguration implements ICustomDirectEditorConfiguration {

	public static final String ANNOTATION_SOURCE = "expression_source"; //$NON-NLS-1$

	public static final String ANNOTATION_DETAIL = "expression"; //$NON-NLS-1$

	/**
	 * returns the UI Injector for the Xtext language
	 *
	 */
	public abstract Injector getInjector();

	/**
	 * returns the {@link ICommand} used to update the UML Model
	 *
	 * @param umlObject
	 * @param xtextObject
	 * @return
	 */
	protected abstract ICommand getParseCommand(EObject umlObject, EObject xtextObject);

	/**
	 * Clients may override to change style to {@link SWT}.MULTI
	 */
	@Override
	public int getStyle() {
		return SWT.SINGLE;
	}

	/**
	 * Clients may override, if the objectToEdit is not equal to the context element
	 *
	 * @return the context provider
	 */
	public IContextElementProvider getContextProvider() {
		return new IContextElementProvider() {

			@Override
			public EObject getContextObject() {
				if (objectToEdit instanceof EObject) {
					return (EObject) objectToEdit;
				}
				return null;
			}
		};
	}

	@Override
	public DirectEditManager createDirectEditManager(final ITextAwareEditPart host) {
		return new XtextDirectEditManager(host, getInjector(), getStyle(), this);
	}

	/**
	 * Adapts {@link IDirectEditorConfiguration} to gmfs {@link IParser} interface for reuse in GMF direct editing infrastructure.
	 */
	@Override
	public IParser createParser(final EObject semanticObject) {
		if (objectToEdit == null) {
			objectToEdit = semanticObject;
		}
		return new IParser() {

			@Override
			public String getEditString(IAdaptable element, int flags) {
				return DefaultXtextDirectEditorConfiguration.this.getTextToEditInternal(semanticObject);
			}

			@Override
			public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
				CompositeCommand result = new CompositeCommand("validation"); //$NON-NLS-1$
				IContextElementProvider provider = getContextProvider();

				XtextFakeResourceContext context = new XtextFakeResourceContext(getInjector());
				final XtextResource xtextResource = context.getFakeResource();
				xtextResource.eAdapters().add(new ContextElementAdapter(provider));

				try {
					xtextResource.load(new StringInputStream(newString), Collections.EMPTY_MAP);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (provider instanceof IContextElementProviderWithInit) {
					((IContextElementProviderWithInit) provider).initResource(context.getFakeResource());
				}
				EcoreUtil2.resolveLazyCrossReferences(xtextResource, CancelIndicator.NullImpl);

				final IResourceValidator resVal = xtextResource.getResourceServiceProvider().getResourceValidator();
				final List<Issue> issues = resVal.validate(xtextResource, CheckMode.ALL, CancelIndicator.NullImpl);
				final IParseResult parseResult = xtextResource.getParseResult();
				final List<Diagnostic> errors = context.getFakeResource().getErrors();

				if (!parseResult.hasSyntaxErrors() && errors.size() == 0 && issues.size() == 0) {
					EObject xtextObject = xtextResource.getParseResult().getRootASTElement();
					ICommand cmd = DefaultXtextDirectEditorConfiguration.this.getParseCommand(semanticObject, xtextObject);
					if (cmd != null) {
						result.add(cmd);
					}
				} else {
					result.add(createInvalidStringCommand(newString, semanticObject, parseResult, errors, issues));
				}
				AbstractValidateCommand validationCommand = new AsyncValidateSubtreeCommand(semanticObject);
				validationCommand.disableUIFeedback();
				result.add(validationCommand);
				return result;
			}

			@Override
			public String getPrintString(IAdaptable element, int flags) {
				return DefaultXtextDirectEditorConfiguration.this.getTextToEdit(semanticObject);
			}

			@Override
			public boolean isAffectingEvent(Object event, int flags) {
				return false;
			}

			@Override
			public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
				// Not used
				return null;
			}

			@Override
			public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
				// Not used
				return null;
			}
		};
	}

	protected String getTextToEditInternal(EObject semanticObject) {
		if (semanticObject instanceof Element) {
			String textualRepresentation = InvalidStringUtil.getTextualRepresentation((Element) semanticObject);
			if (textualRepresentation != null) {
				// register invalid string adapter. If we edit existing models, the invalid string may exist
				// although we have not created the invalid string in this session.
				registerInvalidStringAdapter(semanticObject);
				return textualRepresentation;
			}
		}
		return getTextToEdit(semanticObject);
	}

	/**
	 *
	 * @param invalidString
	 *            the invalid string we tried to set in the UML model
	 * @param semanticElement
	 *            the edited element
	 * @return
	 *         the command saving the invalid string in a stereotyped comment
	 * @deprecated since 2.1
	 */
	@Deprecated
	protected ICommand createInvalidStringCommand(final String invalidString, final EObject semanticElement) {
		return createInvalidStringCommand(invalidString, semanticElement, null, Collections.<Diagnostic> emptyList(), Collections.<Issue> emptyList());
	}

	/**
	 *
	 * @param invalidString
	 *            the invalid string we tried to set in the UML model
	 * @param semanticElement
	 *            the edited element
	 * @param parseResult
	 *            the parse result of the string
	 * @param diagnostics
	 *            the diagnostic given by the resource
	 * @param issues
	 *            the issues found by the resource validator
	 * @return
	 *         the command saving the invalid string in a stereotyped comment. The command result contains contains a {@link XTextStringValidationResult} owning informations about the invalid string
	 * @since 2.1
	 */
	protected ICommand createInvalidStringCommand(final String invalidString, final EObject semanticElement, final IParseResult parseResult, final List<Diagnostic> diagnostics, final List<Issue> issues) {
		if (semanticElement instanceof Element) {
			registerInvalidStringAdapter(semanticElement);
			final Element element = (Element) semanticElement;
			return new AbstractTransactionalCommand(TransactionUtil.getEditingDomain(semanticElement), "", Collections.emptyList()) { //$NON-NLS-1$

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					String languageName = getInjector().getInstance(Key.get(String.class, Names.named(Constants.LANGUAGE_NAME)));
					Comment comment = InvalidStringUtil.getTextualRepresentationComment(element);
					if (comment == null) {
						comment = InvalidStringUtil.createTextualRepresentationComment(element, languageName);
					}
					comment.setBody(invalidString);
					return CommandResult.newOKCommandResult(new XTextStringValidationResult(comment, invalidString, diagnostics, issues, parseResult));
				}
			};

		}
		return UnexecutableCommand.INSTANCE;
	}

	protected void registerInvalidStringAdapter(EObject semanticElement) {
		Adapter existingAdapter = EcoreUtil.getExistingAdapter(semanticElement, InvalidSyntaxAdapter.class);
		if (existingAdapter == null) {
			semanticElement.eAdapters().add(new InvalidSyntaxAdapter());
		}
	}

	@Override
	public CellEditor createCellEditor(Composite parent, final EObject semanticObject) {
		IContextElementProvider provider = getContextProvider();
		XtextStyledTextCellEditorEx cellEditor = new XtextStyledTextCellEditorEx(SWT.MULTI | SWT.BORDER, getInjector(), provider) {

			// This is a workaround for bug
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=412732
			// We can not create SWT.SINGLE CellEditor here, so we have to hook
			// into the StyledTextListener to disable new line. Delete this when
			// the bug is fixed
			@Override
			protected StyledText createStyledText(Composite parent) {
				StyledText text = super.createStyledText(parent);
				text.addListener(3005, new Listener() {

					@Override
					public void handleEvent(Event event) {
						if (event.character == SWT.CR && !completionProposalAdapter.isProposalPopupOpen()) {
							focusLost();
						}
					}
				});
				return text;
			}
			// Workaround end
		};
		cellEditor.create(parent);
		return cellEditor;
	}
}

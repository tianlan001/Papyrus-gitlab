/*****************************************************************************
 * Copyright (c) 2013, 2014, 2023 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 434681
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ocl.examples.xtext.console.messages.ConsoleMessages;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.evaluation.EvaluationEnvironment;
import org.eclipse.ocl.pivot.evaluation.EvaluationVisitor;
import org.eclipse.ocl.pivot.evaluation.ModelManager;
import org.eclipse.ocl.pivot.resource.CSResource;
import org.eclipse.ocl.pivot.utilities.ClassUtil;
import org.eclipse.ocl.pivot.utilities.EnvironmentFactory;
import org.eclipse.ocl.pivot.utilities.ParserContext;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.ocl.pivot.utilities.PivotUtil;
import org.eclipse.ocl.pivot.utilities.ValueUtil;
import org.eclipse.ocl.pivot.values.CollectionValue;
import org.eclipse.ocl.pivot.values.InvalidValueException;
import org.eclipse.ocl.xtext.base.ui.model.BaseDocument;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.uml.search.ui.Messages;
import org.eclipse.papyrus.uml.search.ui.results.PapyrusSearchResult;
import org.eclipse.papyrus.views.search.results.AbstractResultEntry;
import org.eclipse.papyrus.views.search.results.ModelElementMatch;
import org.eclipse.papyrus.views.search.results.ModelMatch;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

/**
 *
 * Papyrus specific search query
 *
 */
public class PapyrusOCLQuery extends AbstractPapyrusQuery {


	private EObject contextObject;

	private PapyrusSearchResult results;

	private IScopeEntry scopeEntry;

	private BaseDocument queryEditorDocument;

	private ParserContext parserContext;

	private EnvironmentFactory environmentFactory;

	private ModelManager modelManager;

	protected Set<AbstractResultEntry> fResults = null;

	public PapyrusOCLQuery(BaseDocument queryEditorDocument, ParserContext parserContext, EnvironmentFactory environmentFactory, ModelManager modelManager, EObject contextObject, IScopeEntry scopeEntry) {
		this.queryEditorDocument = queryEditorDocument;
		this.contextObject = contextObject;
		this.scopeEntry = scopeEntry;
		this.parserContext = parserContext;
		this.environmentFactory = environmentFactory;
		this.modelManager = modelManager;

		Collection<IScopeEntry> scopeEntries = new ArrayList<>();
		scopeEntries.add(scopeEntry);
		results = new PapyrusSearchResult(this);
		fResults = new HashSet<>();
	}


	protected boolean evaluate(final String expression) {

		if ((expression == null) || (expression.trim().length() <= 0)) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.PapyrusOCLQuery_0, Messages.PapyrusOCLQuery_1);
			return false;
		}

		boolean result = true;
		try {


			@SuppressWarnings("unused")
			IDocument doc = queryEditorDocument;

			final BaseDocument editorDocument = queryEditorDocument;
			Object value = null;
			try {

				value = editorDocument.readOnly(new IUnitOfWork<Object, XtextResource>() {

					@Override
					public Object exec(XtextResource state) throws Exception {
						assert state != null;
						@SuppressWarnings("unused")
						IProgressService progressService = PlatformUI.getWorkbench().getProgressService();
						EvaluationRunnable runnable = new EvaluationRunnable((CSResource) state, expression);
						runnable.run(new NullProgressMonitor());
						// progressService.busyCursorWhile(runnable);
						return runnable.getValue();
					}
				});
			} catch (Exception e) {
				// MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", e.getMessage());
			}
			if (value instanceof InvalidValueException) {
				InvalidValueException exception = (InvalidValueException) value;
				// MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", exception.getMessage());
				Throwable cause = exception.getCause();
				if ((cause != null) && (cause != exception)) {
					// MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", cause.getMessage());
				}
			} else if (value != null) {
				CollectionValue collectionValue = ValueUtil.isCollectionValue(value);
				if (collectionValue != null) {
					for (Object elementValue : collectionValue.iterable()) {
						if (elementValue instanceof EObject) {
							ModelMatch match = new ModelElementMatch(elementValue, scopeEntry);
							fResults.add(match);
						}
						// System.err.println("Found : " + ValuesUtil.stringValueOf(elementValue));
					}
				} else {
					if (value instanceof EObject) {
						ModelMatch match = new ModelElementMatch(value, scopeEntry);
						fResults.add(match);
					}
					// System.err.println("Found : " + ValuesUtil.stringValueOf(value));
				}
			} else {
				// MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", ValuesUtil.stringValueOf(value));

			}

		} catch (Exception e) {
			result = false;

			if (e.getLocalizedMessage() == null) {
				// MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", e.getClass().getName());
			} else {
				// MessageDialog.openError(Display.getCurrent().getActiveShell(), "Error", e.getLocalizedMessage());
			}
		}

		return result;
	}

	private class EvaluationRunnable implements IRunnableWithProgress {

		private final CSResource resource;

		private final String expression;

		private Object value = null;

		public EvaluationRunnable(CSResource resource, String expression) {
			this.resource = resource;
			this.expression = expression;
		}

		public Object getValue() {
			return value;
		}

		@Override
		public void run(final IProgressMonitor monitor) {
			monitor.beginTask(NLS.bind(ConsoleMessages.Progress_Title, expression), 10);
			monitor.subTask(ConsoleMessages.Progress_Synchronising);
			monitor.worked(1);


			ExpressionInOCL expressionInOCL;
			try {
				PivotUtil.checkResourceErrors("", resource); //$NON-NLS-1$
				expressionInOCL = parserContext.getExpression(resource);
			} catch (ParserException e) {
				value = new InvalidValueException(e, ConsoleMessages.Result_ParsingFailure);
				return;
			}
			if (expressionInOCL != null) {
				// monitor.worked(2);
				monitor.subTask(ConsoleMessages.Progress_Extent);
				ModelManager modelManager2 = modelManager;
				if (modelManager2 == null) {
					// let the evaluation environment create one
					modelManager2 = modelManager = environmentFactory.createModelManager(contextObject);
				}
				EvaluationEnvironment evaluationEnvironment = environmentFactory.createEvaluationEnvironment(expressionInOCL, modelManager2);
				Object contextValue = environmentFactory.getIdResolver().boxedValueOf(contextObject);
				evaluationEnvironment.add(ClassUtil.nonNullModel(expressionInOCL.getOwnedContext()), contextValue);
				monitor.worked(2);
				monitor.subTask(ConsoleMessages.Progress_Evaluating);
				try {
					// metamodelManager.setMonitor(monitor);
					EvaluationVisitor evaluationVisitor = environmentFactory.createEvaluationVisitor(evaluationEnvironment);
					evaluationVisitor.setMonitor(BasicMonitor.toMonitor(monitor));
					// evaluationVisitor.setLogger(new DomainLogger() {
					//
					// public void append(final @NonNull String message) {
					// OCLConsolePage.this.getControl().getDisplay().asyncExec(new Runnable() {
					//
					// public void run() {
					// OCLConsolePage.this.append(message, ColorManager.DEFAULT, false);
					// }
					// });
					// }
					// });
					value = evaluationVisitor.visitExpressionInOCL(expressionInOCL);
				} catch (InvalidValueException e) {
					value = e;
				} catch (Exception e) {
					value = new InvalidValueException(e, ConsoleMessages.Result_EvaluationFailure);
				} finally {
					// metamodelManager.setMonitor(null);
				}
			}
			monitor.worked(4);
		}
	}

	@Override
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		results.removeAll();
		fResults.clear();

		evaluate(queryEditorDocument.get().trim());

		monitor.done();

		return Status.OK_STATUS;
	}

	/**
	 * Evaluate if the value matches the pattern
	 *
	 * @param value
	 *            the value to evaluate
	 * @param attribute
	 *            the attribute has the value
	 * @param pattern
	 *            the pattern that is searched
	 * @param participant
	 *            the element that contains the value
	 * @param scopeEntry
	 *            the scopeEntry that contains the participant
	 */
	protected void evaluateAndAddToResult(String value, Object attribute, Pattern pattern, Object participant, IScopeEntry scopeEntry) {

		value = value != null ? value : ""; //$NON-NLS-1$

		@SuppressWarnings("unused")
		Matcher m = pattern.matcher(value);

		// if(isRegularExpression) {
		// if(m.matches()) {
		// int start = m.start();
		// int end = m.end();
		// ModelMatch match = new AttributeMatch(start, end, participant, scopeEntry, attribute);
		//
		// fResults.add(match);
		// }
		// } else {
		// while(m.find()) {
		// int start = m.start();
		// int end = m.end();
		// AttributeMatch match = new AttributeMatch(start, end, participant, scopeEntry, attribute);
		// fResults.add(match);
		// }
		// }


	}

	/**
	 * Try to find elements that match in the participants
	 *
	 * @param participants
	 * @param scopeEntry
	 */
	protected void evaluate(Collection<EObject> participants, IScopeEntry scopeEntry) {

		// for(EObject participant : participants) {
		//
		// String query = searchQueryText;
		// if(searchQueryText.equals("")) { //$NON-NLS-1$
		// query = ".*"; //$NON-NLS-1$
		// }
		//
		// Pattern pattern = PatternHelper.getInstance().createPattern(query, isCaseSensitive, isRegularExpression);
		//
		// if(pattern != null) {
		// if(searchAllStringAttributes) {
		//
		// for(EAttribute attribute : participant.eClass().getEAllAttributes()) {
		// Object value = participant.eGet(attribute);
		//
		// if(value instanceof String) {
		// String stringValue = (String)value;
		// evaluateAndAddToResult(stringValue, attribute, pattern, participant, scopeEntry);
		// }
		// }
		//
		// } else {
		// if(participant instanceof NamedElement) {
		// String umlElementName = ((NamedElement)participant).getName();
		// umlElementName = umlElementName != null ? umlElementName : ""; //$NON-NLS-1$
		//
		// evaluateAndAddToResult(umlElementName, UMLPackage.eINSTANCE.getNamedElement_Name(), pattern, participant, scopeEntry);
		// }
		// }
		// if(searchStereotypeAttributes) {
		// if(participant instanceof Element) {
		// EList<Stereotype> stereotypes = ((Element)participant).getAppliedStereotypes();
		// for(Stereotype stereotype : stereotypes) {
		// for(Property stereotypeProperty : stereotype.getAllAttributes()) {
		// if(!stereotypeProperty.getName().startsWith("base_")) {
		// Object value = ((Element)participant).getValue(stereotype, stereotypeProperty.getName());
		// if(value != null) {
		//
		// if(value instanceof String) {
		// String stringValue = (String)value;
		// evaluateAndAddToResult(stringValue, stereotypeProperty, pattern, participant, scopeEntry);
		// }
		// }
		// }
		// }
		//
		// }
		// }
		// }
		//
		// }
		// }
		//
		// //Now, find in diagram and others the elements we found
		// ViewerSearchService viewerSearcherService = new ViewerSearchService();
		// try {
		// viewerSearcherService.startService();
		//
		// //Get sources elements that matched
		// Set<Object> sources = new HashSet<Object>();
		// for(AbstractResultEntry match : fResults) {
		// if(match instanceof AttributeMatch) {
		// sources.add(((AttributeMatch)match).getTarget());
		// } else {
		// sources.add(match.getSource());
		// }
		// }
		//
		// //Get viewer of these sources
		// Map<Object, Map<Object, Object>> viewersMappings = viewerSearcherService.getViewers(sources, scopeEntry.getModelSet());
		//
		// //Add viewers to results
		// for(Object containingModelSet : viewersMappings.keySet()) {
		// for(Object view : viewersMappings.get(containingModelSet).keySet()) {
		// Object semanticElement = viewersMappings.get(containingModelSet).get(view);
		// ViewerMatch viewMatch = new ViewerMatch(view, scopeEntry, semanticElement);
		// fResults.add(viewMatch);
		// }
		// }
		//
		// } catch (ServiceException e) {
		// Activator.log.error(Messages.PapyrusQuery_5 + scopeEntry.getModelSet(), e);
		// }
	}

	@Override
	public String getLabel() {
		return Messages.PapyrusQuery_6;
	}

	@Override
	public boolean canRerun() {
		return false;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ISearchResult getSearchResult() {
		for (AbstractResultEntry match : fResults) {
			results.addMatch(match);
		}
		return results;
	}

	/**
	 * Getter for the text query
	 *
	 * @return the the query text
	 */
	@Override
	public String getSearchQueryText() {
		if (queryEditorDocument.get().length() > 25) {
			return queryEditorDocument.get().subSequence(0, 25) + "..."; //$NON-NLS-1$
		} else {
			return queryEditorDocument.get();
		}
	}
}

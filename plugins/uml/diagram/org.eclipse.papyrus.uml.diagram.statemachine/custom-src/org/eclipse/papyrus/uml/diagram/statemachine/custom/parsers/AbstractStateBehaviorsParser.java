/**
 * Copyright (c) 2014 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.parsers;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.ui.services.parser.ISemanticParser;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.papyrus.infra.internationalization.common.utils.InternationalizationPreferencesUtils;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.OpaqueBehavior;

/**
 * Abstract Parser for States behavior (Entry , Do , Exit)
 * To use this only implement the abstract methods ...
 *
 * @author arthur daussy
 *
 */
public abstract class AbstractStateBehaviorsParser implements ISemanticParser {
	private static final String ONE_SPACE_STRING = " ";
	private static final String EMPTY_STRING = "";

	/**
	 * Interface to implement in order to extends this abstract class
	 * For example:
	 * In State UML element there is 3 EAttributes which point to behavior entry , exit , do Activity.
	 * An implementation of this interface for the entry attribute could be:
	 * public String getKeyWord(){
	 * return "\entry ";
	 * }
	 * public EStructuralFeature getEStructuralFeature(){
	 * return UMLPackage.Literals.STATE__ENTRY;
	 * }
	 *
	 * @author adaussy
	 *
	 */
	public interface BehaviorType {
		/**
		 * Get the keyword to display in the label name
		 * CAN NOT RETURN NULL
		 *
		 * @return {@link String}
		 */
		public String getKeyWord();

		/**
		 * Get the corresponding {@link EStructuralFeature} CAN NOT RETURN NULL
		 *
		 * @return
		 */
		public EStructuralFeature getEStructuralFeature();
	}

	/***
	 * Get the parser type ( among {@link Behavior_Type})
	 *
	 * @return
	 */
	protected abstract BehaviorType getParserType();

	@Override
	public String getEditString(IAdaptable element, int flags) {
		Object obj = element.getAdapter(Behavior.class);
		if (obj instanceof Behavior) {
			final Behavior behavior = ((Behavior) obj);
			return UMLLabelInternationalization.getInstance().getLabel(behavior);
		}
		return EMPTY_STRING;
	}

	@Override
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return new ParserEditStatus(org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin.ID, IStatus.OK, ""); //$NON-NLS-1$
	}

	/***
	 * Allow to set the name of behavior directly from the direct edit
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
		final Behavior behavior = ((Behavior) ((EObjectAdapter) element).getRealObject());
		final EditingDomain editingDomain = AdapterFactoryEditingDomain.getEditingDomainFor(behavior);
		final String newStringResult = newString;
		if (editingDomain != null && editingDomain instanceof TransactionalEditingDomain) {
			AbstractTransactionalCommand cmd = new AbstractTransactionalCommand((TransactionalEditingDomain) editingDomain, "Set new name in " + behavior.getName(), null) {
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					if (InternationalizationPreferencesUtils.getInternationalizationPreference(behavior) && null != UMLLabelInternationalization.getInstance().getLabelWithoutUML(behavior)) {
						UMLLabelInternationalization.getInstance().setLabel(behavior, newStringResult, null);
					} else {
						behavior.setName(newStringResult);
					}
					return CommandResult.newOKCommandResult();
				}
			};
			if (cmd != null && cmd.canExecute()) {
				return cmd;
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Object obj = element.getAdapter(EObject.class);
		View view = null;
		if (obj instanceof View) {
			// retrieve view from passed adaptable
			view = (View) obj;
			obj = view.getElement();
		}
		if (obj instanceof Behavior) {
			final Behavior behavior = ((Behavior) obj);
			StringBuilder result = new StringBuilder();
			// Append keyword
			String keyWord = getParserType().getKeyWord();
			result.append(keyWord);
			addExtraSpace(result, keyWord);
			String body = null;
			if (behavior instanceof OpaqueBehavior) {
				OpaqueBehavior ob = (OpaqueBehavior) obj;
				if (ob.getBodies().size() > 0) {
					// return body of behavior (only handle case of a single body)
					body = OpaqueBehaviorViewUtil.retrieveBody(view, ob);
				}
			}
			if (body != null && !body.equals(OpaqueBehaviorViewUtil.DOTS)) {
				result.append(body);
			} else {
				// Append kind
				String kind = behavior.eClass().getName();
				result.append(kind);
				addExtraSpace(result, kind);
				// Append behavior name
				result.append(UMLLabelInternationalization.getInstance().getLabel(behavior));
			}
			return result.toString();
		}
		return EMPTY_STRING;
	}

	private void addExtraSpace(StringBuilder result, String keyWord) {
		if (!keyWord.endsWith(ONE_SPACE_STRING)) {
			result.append(ONE_SPACE_STRING);
		}
	}

	@Override
	public boolean isAffectingEvent(Object event, int flags) {
		if (event instanceof Notification) {
			int notificationType = ((Notification) event).getEventType();
			if (Notification.ADD == notificationType || Notification.SET == notificationType || Notification.REMOVE == notificationType) {
				Object feature = ((Notification) event).getFeature();
				if (feature instanceof EStructuralFeature) {
					EStructuralFeature eStrucFeature = (EStructuralFeature) feature;
					if (getParserType().getEStructuralFeature().equals(eStrucFeature)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	@Override
	public List<Behavior> getSemanticElementsBeingParsed(EObject element) {
		if (element instanceof Behavior) {
			final Behavior behavior = ((Behavior) element);
			return Collections.singletonList(behavior);
		}
		return Collections.emptyList();
	}

	@Override
	public boolean areSemanticElementsAffected(EObject listener, Object notification) {
		return true;
	}
}

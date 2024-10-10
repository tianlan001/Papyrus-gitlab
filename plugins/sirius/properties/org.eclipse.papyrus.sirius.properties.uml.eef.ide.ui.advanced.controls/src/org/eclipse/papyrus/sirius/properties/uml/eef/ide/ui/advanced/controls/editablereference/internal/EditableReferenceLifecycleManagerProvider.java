/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.editablereference.internal;

import java.util.Collection;

import org.eclipse.eef.EEFControlDescription;
import org.eclipse.eef.common.api.utils.Util;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.ext.widgets.reference.eefextwidgetsreference.EEFExtReferenceDescription;
import org.eclipse.eef.ide.ui.api.widgets.IEEFLifecycleManager;
import org.eclipse.eef.ide.ui.api.widgets.IEEFLifecycleManagerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription;
import org.eclipse.sirius.common.interpreter.api.IEvaluationResult;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

/**
 * The lifecycle manager provider is used to create a lifecycle manager for the
 * editable reference widget.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class EditableReferenceLifecycleManagerProvider implements IEEFLifecycleManagerProvider {

	@Override
	public boolean canHandle(EEFControlDescription controlDescription) {
		return controlDescription instanceof EEFExtEditableReferenceDescription;
	}

	@Override
	public IEEFLifecycleManager getLifecycleManager(EEFControlDescription controlDescription,
			IVariableManager variableManager, IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		if (!(controlDescription instanceof EEFExtEditableReferenceDescription) || Util
				.isBlank(((EEFExtEditableReferenceDescription) controlDescription).getReferenceNameExpression())) {
			return null;
		}
		IEEFLifecycleManager lifecycleManager = null;
		EEFExtEditableReferenceDescription description = (EEFExtEditableReferenceDescription) controlDescription;

		EObject target = this.getTarget(description, interpreter, variableManager);
		String referenceName = this.getReferenceName(description, interpreter, variableManager);
		if (target != null && !Util.isBlank(referenceName)) {
			EStructuralFeature eStructuralFeature = target.eClass().getEStructuralFeature(referenceName);
			if (eStructuralFeature instanceof EReference) {
				EReference eReference = (EReference) eStructuralFeature;
				if (eReference.isMany()) {
					lifecycleManager = new MultipleEditableReferenceLifecycleManager(description, target, eReference,
							variableManager, interpreter, editingContextAdapter);
				} else {
					lifecycleManager = new SingleEditableReferenceLifecycleManager(description, target, eReference,
							variableManager, interpreter, editingContextAdapter);
				}
			}
		}
		return lifecycleManager;
	}

	/**
	 * Returns the target to use as the current object.
	 *
	 * @param description     the reference description to manage
	 * @param interpreter     the interpreter to evaluate AQL expressions
	 * @param variableManager the variable manager which contain variables used by
	 *                        Interpreter to evaluate AQL expression
	 * @return the owner of the reference
	 */
	private EObject getTarget(EEFExtReferenceDescription description, IInterpreter interpreter,
			IVariableManager variableManager) {
		EObject self = null;
		if (!Util.isBlank(description.getReferenceOwnerExpression())) {
			IEvaluationResult result = interpreter.evaluateExpression(variableManager.getVariables(),
					description.getReferenceOwnerExpression());
			if (result.success()) {
				Collection<EObject> eObjects = result.asEObjects();
				if (eObjects.size() > 0) {
					self = eObjects.iterator().next();
				}
			}
		} else {
			Object selfVariable = variableManager.getVariables().get(EEFExpressionUtils.SELF);
			if (selfVariable instanceof EObject) {
				self = (EObject) selfVariable;
			}
		}
		return self;
	}

	/**
	 * Returns the name of the reference to use.
	 *
	 * @param description     the reference description to manage
	 * @param interpreter     the interpreter to evaluate AQL expressions
	 * @param variableManager the variable manager which contain variables used by
	 *                        Interpreter to evaluate AQL expression
	 * @return the name of the reference
	 */
	private String getReferenceName(EEFExtReferenceDescription description, IInterpreter interpreter,
			IVariableManager variableManager) {
		IEvaluationResult evaluationResult = interpreter.evaluateExpression(variableManager.getVariables(),
				description.getReferenceNameExpression());
		if (evaluationResult.success()) {
			return evaluationResult.asString();
		}
		return ""; //$NON-NLS-1$
	}

}

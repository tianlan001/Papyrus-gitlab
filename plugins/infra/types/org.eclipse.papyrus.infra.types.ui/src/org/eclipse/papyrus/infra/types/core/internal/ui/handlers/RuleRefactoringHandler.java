/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.types.core.internal.ui.handlers;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.types.rulebased.Activator;
import org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedFactory;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;
import org.eclipse.ui.ISources;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handler for the rule refactoring commands.
 */
public class RuleRefactoringHandler extends AbstractHandler implements IExecutableExtension {

	/** The operation initialization parameter in the <tt>plugin.xml</tt>. */
	private static final String PARAM_OPERATION = "operation"; //$NON-NLS-1$

	private BiFunction<EditingDomain, RuleConfiguration, Command> refactoringOperation;

	public RuleRefactoringHandler() {
		super();
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		if (data instanceof Map) {
			Map<?, ?> parameters = (Map<?, ?>) data;
			String operationName = (String) parameters.get(PARAM_OPERATION);
			if (operationName == null || operationName.isBlank()) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"Missing operation parameter in RuleRefactoringHandler initialization")); //$NON-NLS-1$
			} else {
				switch (OperationKind.valueOf(String.valueOf(operationName))) {
				case NEGATE:
					refactoringOperation = this::getNegateCommand;
					break;
				case ADD_TO_AND:
					refactoringOperation = this::getAddToAndCommand;
					break;
				case ADD_TO_OR:
					refactoringOperation = this::getAddToOrCommand;
					break;
				case PULL_UP_OPERAND:
					refactoringOperation = this::getPullUpOperandCommand;
					break;
				}
			}
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RuleConfiguration rule = getRuleConfiguration(event.getApplicationContext());

		if (rule != null) {
			try {
				execute(rule, event.getCommand().getName());
			} catch (NotDefinedException e) {
				throw new ExecutionException("Cannot get command label", e); //$NON-NLS-1$
			}
		}

		return null;
	}

	protected void execute(RuleConfiguration rule, String label) {
		EditingDomain domain = AdapterFactoryEditingDomain.getEditingDomainFor(rule);
		domain.getCommandStack().execute(new CommandWrapper(label, label, null) {
			@Override
			protected Command createCommand() {
				return refactoringOperation.apply(domain, rule);
			}
		});
	}

	private RuleConfiguration getRuleConfiguration(Object evaluationContext) {
		RuleConfiguration result = null;

		Object selection = HandlerUtil.getVariable(evaluationContext, ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (selection instanceof Iterable<?>) {
			for (Iterator<?> iter = ((Iterable<?>) selection).iterator(); result == null && iter.hasNext();) {
				EObject next = EMFHelper.getEObject(iter.next());
				if (next instanceof RuleConfiguration) {
					result = (RuleConfiguration) next;
				}
			}
		}

		return result;
	}

	RuleConfiguration getContainingRule(EditingDomain domain, RuleConfiguration rule) {
		EObject container = rule.eContainer();

		if (container == null) {
			// The rule may be referenced by a NotRuleConfiguration, which is its logical 'container'
			// despite not actually being a container (cf. bug 571560)
			container = EcoreUtil.UsageCrossReferencer.find(rule, domain.getResourceSet()).stream()
					.filter(setting -> setting.getEStructuralFeature() == RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION__COMPOSED_RULE)
					.map(EStructuralFeature.Setting::getEObject)
					.findFirst()
					.orElse(null);
		}

		return (container instanceof RuleConfiguration) ? (RuleConfiguration) container : null;
	}

	protected Command replace(EditingDomain domain, EObject object, EObject replacement) {
		EObject container = object.eContainer();
		EReference containment = object.eContainmentFeature();
		return containment.isMany()
				? RemoveCommand.create(domain, container, containment, Set.of(object))
						.chain(AddCommand.create(domain, container, containment, Set.of(replacement)))
				: SetCommand.create(domain, object.eContainer(), containment, replacement);
	}

	private Command getNegateCommand(EditingDomain domain, RuleConfiguration rule) {
		RuleConfiguration parent = getContainingRule(domain, rule);
		if (parent instanceof NotRuleConfiguration) {
			// Negate the parent, instead (recursively)
			return getNegateCommand(domain, parent);
		} else if (rule instanceof NotRuleConfiguration) {
			NotRuleConfiguration notRule = (NotRuleConfiguration) rule;
			return SetCommand.create(domain, notRule, RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION__COMPOSED_RULE, SetCommand.UNSET_VALUE)
					.chain(replace(domain, notRule, notRule.getComposedRule()));
		} else {
			NotRuleConfiguration notRule = RuleBasedFactory.eINSTANCE.createNotRuleConfiguration();

			return replace(domain, rule, notRule)
					.chain(SetCommand.create(domain, notRule, RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION__COMPOSED_RULE, rule));
		}
	}

	private Command getAddToAndCommand(EditingDomain domain, RuleConfiguration rule) {
		AndRuleConfiguration andRule = RuleBasedFactory.eINSTANCE.createAndRuleConfiguration();
		return replace(domain, rule, andRule)
				.chain(AddCommand.create(domain, andRule, RuleBasedPackage.Literals.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES, Set.of(rule)));
	}

	private Command getAddToOrCommand(EditingDomain domain, RuleConfiguration rule) {
		OrRuleConfiguration orRule = RuleBasedFactory.eINSTANCE.createOrRuleConfiguration();
		return replace(domain, rule, orRule)
				.chain(AddCommand.create(domain, orRule, RuleBasedPackage.Literals.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES, Set.of(rule)));
	}

	private Command getPullUpOperandCommand(EditingDomain domain, RuleConfiguration rule) {
		CompositeRuleConfiguration composite = (CompositeRuleConfiguration) rule;
		RuleConfiguration operand = composite.getComposedRules().get(0);
		return RemoveCommand.create(domain, composite, RuleBasedPackage.Literals.COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES, Set.of(operand))
				.chain(replace(domain, composite, operand));
	}

	//
	// Nested types
	//

	/**
	 * Enumeration of refactoring operations supported.
	 */
	public static enum OperationKind {
		NEGATE, ADD_TO_AND, ADD_TO_OR, PULL_UP_OPERAND;
	}

}

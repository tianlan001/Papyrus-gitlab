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

package org.eclipse.papyrus.infra.types.core.internal.ui.handlers.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.eEqualTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;
import java.util.Map;
import java.util.function.BiFunction;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.core.internal.ui.handlers.RuleRefactoringHandler.OperationKind;
import org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedFactory;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.util.RuleBasedSwitch;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the rule refactoring handlers.
 */
public class RuleRefactoringHandlerTest {

	@Rule
	public final AnnotationRule<OperationKind> refactoringKind = AnnotationRule.create(Refactoring.class, null);

	private String commandID;

	private IEditorPart editor;
	private EditingDomain domain;
	private ElementTypeSetConfiguration fixture;
	private ElementTypeSetConfiguration originalState;
	private ElementTypeSetConfiguration newState;
	private RuleBasedTypeConfiguration type;
	private RuleConfiguration rule;
	private boolean skipUndoRedo;
	private ECrossReferenceAdapter xrefs;

	public RuleRefactoringHandlerTest() {
		super();
	}

	@Refactoring(OperationKind.NEGATE)
	@Test
	public void negate() {
		execute(rule);

		NotRuleConfiguration notRule = getNegatingRule(rule);
		assertThat("Rule not negated", notRule, notNullValue());

		if (RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION__COMPOSED_RULE.isContainment()) {
			assertThat("Rule lost in space", rule.eContainer().eContainer(), sameInstance(type));
		}
	}

	@Refactoring(OperationKind.NEGATE)
	@Test
	public void negate_notRule() {
		NotRuleConfiguration notRule = wrapInNot();

		execute(notRule);

		assertThat("Rule not negated", rule.eContainer(), sameInstance(type));
	}

	@Refactoring(OperationKind.NEGATE)
	@Test
	public void negate_negatedRule() {
		NotRuleConfiguration notRule = wrapInNot();

		if (!RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION__COMPOSED_RULE.isContainment()) {
			// The not rule detached the object, so put it in the resource for the handler to find
			// the editing domain context. cf. bug 571560
			notRule.eResource().getContents().add(rule);
		}

		execute(rule);

		assertThat("Rule not negated", rule.eContainer(), sameInstance(type));
	}

	@Refactoring(OperationKind.ADD_TO_AND)
	@Test
	public void addToAnd() {
		execute(rule);

		assertThat("Rule not wrapped in 'and'", rule.eContainer(), instanceOf(AndRuleConfiguration.class));
		assertThat("Rule lost in space", rule.eContainer().eContainer(), sameInstance(type));
	}

	@Refactoring(OperationKind.ADD_TO_AND)
	@Test
	public void alreadyInAnd() {
		wrapInAnd();

		assertDisabled(rule);
	}

	@Refactoring(OperationKind.ADD_TO_OR)
	@Test
	public void addToOr() {
		execute(rule);

		assertThat("Rule not wrapped in 'or'", rule.eContainer(), instanceOf(OrRuleConfiguration.class));
		assertThat("Rule lost in space", rule.eContainer().eContainer(), sameInstance(type));
	}

	@Refactoring(OperationKind.ADD_TO_OR)
	@Test
	public void alreadyInOr() {
		wrapInOr();

		assertDisabled(rule);
	}

	@Refactoring(OperationKind.PULL_UP_OPERAND)
	@Test
	public void pullUpAnd() {
		AndRuleConfiguration andRule = wrapInAnd();

		execute(andRule);

		assertThat("And rule not elided", rule.eContainer(), sameInstance(type));
	}

	@Refactoring(OperationKind.PULL_UP_OPERAND)
	@Test
	public void pullUpAndMultiple() {
		AndRuleConfiguration andRule = wrapInAnd();
		andRule.getComposedRules().add(RuleBasedFactory.eINSTANCE.createNotRuleConfiguration());

		assertDisabled(andRule);
	}

	@Refactoring(OperationKind.PULL_UP_OPERAND)
	@Test
	public void pullUpOr() {
		OrRuleConfiguration orRule = wrapInOr();

		execute(orRule);

		assertThat("Or", rule.eContainer(), sameInstance(type));
	}

	@Refactoring(OperationKind.PULL_UP_OPERAND)
	@Test
	public void pullUpOrMultiple() {
		OrRuleConfiguration orRule = wrapInOr();
		orRule.getComposedRules().add(RuleBasedFactory.eINSTANCE.createNotRuleConfiguration());

		assertDisabled(orRule);
	}

	@Refactoring(OperationKind.PULL_UP_OPERAND)
	@Test
	public void pullUpNot() {
		NotRuleConfiguration notRule = wrapInNot();

		assertDisabled(notRule);
	}

	//
	// Test framework
	//

	@Before
	public void createHandler() throws CoreException {
		switch (refactoringKind.get()) {
		case NEGATE:
			commandID = "org.eclipse.papyrus.infra.types.ui.commands.negateRule"; //$NON-NLS-1$
			break;
		case ADD_TO_AND:
			commandID = "org.eclipse.papyrus.infra.types.ui.commands.addToAndRule"; //$NON-NLS-1$
			break;
		case ADD_TO_OR:
			commandID = "org.eclipse.papyrus.infra.types.ui.commands.addToOrRule"; //$NON-NLS-1$
			break;
		case PULL_UP_OPERAND:
			commandID = "org.eclipse.papyrus.infra.types.ui.commands.pullUpOperandRule"; //$NON-NLS-1$
			break;
		}
	}

	@Before
	public void loadFixture() throws PartInitException, IOException {
		// Ensure a file: URI so that the editing domain doesn't think it's read-only,
		// which would then result in no commands being executable
		URL url = FileLocator.toFileURL(RuleRefactoringHandlerTest.class.getResource("test.elementtypesconfigurations"));
		URI uri = URI.createURI(url.toExternalForm(), true);

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window == null) {
			window = PlatformUI.getWorkbench().getWorkbenchWindows()[0];
		}

		editor = window.getActivePage().openEditor(new URIEditorInput(uri), "org.eclipse.papyrus.infra.types.presentation.ElementTypesConfigurationsEditorID");
		domain = ((IEditingDomainProvider) editor).getEditingDomain();

		Resource resource = domain.getResourceSet().getResource(uri, true);
		fixture = (ElementTypeSetConfiguration) resource.getContents().get(0);
		type = (RuleBasedTypeConfiguration) fixture.getElementTypeConfigurations().get(0);
		rule = type.getRuleConfiguration();

		originalState = EcoreUtil.copy(fixture);

		xrefs = new ECrossReferenceAdapter();
		domain.getResourceSet().eAdapters().add(xrefs);
	}

	@After
	public void undoAndRedo() {
		try {
			if (!skipUndoRedo) {
				undo();
				redo();
			}
		} finally {
			if (editor != null) {
				editor.getEditorSite().getPage().closeEditor(editor, false);
			}
		}
	}

	<T extends RuleConfiguration> T updateInitialState(BiFunction<? super RuleBasedTypeConfiguration, ? super RuleConfiguration, T> update) {
		T result = update.apply(type, rule);
		originalState = EcoreUtil.copy(fixture);
		return result;
	}

	NotRuleConfiguration wrapInNot() {
		return compose(RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION);
	}

	AndRuleConfiguration wrapInAnd() {
		return compose(RuleBasedPackage.Literals.AND_RULE_CONFIGURATION);
	}

	OrRuleConfiguration wrapInOr() {
		return compose(RuleBasedPackage.Literals.OR_RULE_CONFIGURATION);
	}

	<T extends RuleConfiguration> T compose(EClass compositeType) {
		@SuppressWarnings("unchecked")
		T result = (T) updateInitialState((type, rule) -> {
			return new RuleBasedSwitch<RuleConfiguration>() {
				@Override
				public RuleConfiguration caseNotRuleConfiguration(NotRuleConfiguration object) {
					object.setComposedRule(rule);
					type.setRuleConfiguration(object);
					return object;
				}

				@Override
				public RuleConfiguration caseCompositeRuleConfiguration(CompositeRuleConfiguration object) {
					object.getComposedRules().add(rule);
					type.setRuleConfiguration(object);
					return object;
				}
			}.doSwitch(RuleBasedFactory.eINSTANCE.create(compositeType));
		});
		return result;
	}

	void execute(RuleConfiguration rule) {
		// In case execution fails, there will be no point in testing undo/redo after the fact
		skipUndoRedo = true;

		IStructuredSelection selection = new StructuredSelection(rule);
		((ISelectionProvider) editor).setSelection(selection);

		ICommandService commandService = editor.getSite().getService(ICommandService.class);
		Command command = commandService.getCommand(commandID);

		IEvaluationContext context = new EvaluationContext(null, selection);
		context.addVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME, selection);
		ExecutionEvent event = new ExecutionEvent(command, Map.of(), this, context);

		assertThat("Cannot execute refactoring handler", command.isEnabled(), is(true));
		try {
			command.executeWithChecks(event);
		} catch (NotDefinedException | NotEnabledException | NotHandledException | ExecutionException e) {
			throw new AssertionError("Failed to execute refactoring handler", e);
		}

		// This test will perform the undo/redo assertion after the fact
		skipUndoRedo = false;

		newState = EcoreUtil.copy(fixture);
	}

	void assertDisabled(RuleConfiguration rule) {
		// This test will skip the undo/redo assertion after the fact
		skipUndoRedo = true;

		IStructuredSelection selection = new StructuredSelection(rule);
		((ISelectionProvider) editor).setSelection(selection);

		ICommandService commandService = editor.getSite().getService(ICommandService.class);
		Command command = commandService.getCommand(commandID);

		assertThat("Can execute refactoring handler", command.isEnabled(), is(false));
	}

	void undo() {
		assertThat("Cannot undo refactoring command", domain.getCommandStack().canUndo(), is(true));
		domain.getCommandStack().undo();

		assertThat("Undo did not revert to original state", fixture, eEqualTo(originalState));
	}

	void redo() {
		assertThat("Cannot redo refactoring command", domain.getCommandStack().canRedo(), is(true));
		domain.getCommandStack().redo();

		assertThat("Redo did not restore to new state", fixture, eEqualTo(newState));
	}

	NotRuleConfiguration getNegatingRule(RuleConfiguration rule) {
		return xrefs.getInverseReferences(rule, RuleBasedPackage.Literals.NOT_RULE_CONFIGURATION__COMPOSED_RULE, true).stream()
				.map(EStructuralFeature.Setting::getEObject)
				.map(NotRuleConfiguration.class::cast)
				.findFirst()
				.orElse(null);
	}

	//
	// Nested types
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface Refactoring {
		OperationKind value();
	}

}

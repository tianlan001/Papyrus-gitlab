/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.architecture.internal.operations;

import static java.util.function.Predicate.not;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.impl.ArchitecturePlugin;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureCommandUtils;
import org.eclipse.papyrus.infra.core.architecture.util.ArchitectureValidator;

/**
 * Externalization of custom (non-generatable) {@link ArchitectureContext} method implementations
 * for reuse in the CDO implementation of the model.
 */
public class ArchitectureContextOperations {

	public static boolean isExtension(ArchitectureContext self) {
		// This happens to have the same semantics
		return self.eIsSet(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS);
	}

	public static boolean ceationCommandClassExists(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (self.getCreationCommandClass() != null) {
			boolean exists = false;

			Object javaClass = ArchitectureCommandUtils.getCommandClass(self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS);
			exists = javaClass != null;

			if (!exists) {
				if (diagnostics != null) {
					// Further narrow the problem
					String problem = ArchitectureCommandUtils.getCommandClassUnconstrained(self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS) == null
							? "_UI_creationCommandClassExists_diagnostic" //$NON-NLS-1$
							: "_UI_creationCommandClassConforms_diagnostic"; //$NON-NLS-1$
					String expectedInterface = ArchitectureCommandUtils.getCommandType(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS)
							.map(Class::getSimpleName).orElse(ArchitecturePlugin.INSTANCE.getString("_UI_genericRequiredInterface_name")); //$NON-NLS-1$
					Set<String> requiredBundleDependencies = ArchitectureCommandUtils.getRequiredCommandBundleDependencies(self);
					String depsList = String.join(", ", requiredBundleDependencies); //$NON-NLS-1$

					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
							ArchitectureValidator.DIAGNOSTIC_SOURCE,
							ArchitectureValidator.ARCHITECTURE_CONTEXT__CEATION_COMMAND_CLASS_EXISTS,
							ArchitecturePlugin.INSTANCE.getString(problem, new Object[] {
									EObjectValidator.getObjectLabel(self, context), expectedInterface, requiredBundleDependencies.size(), depsList }),
							new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS }));
				}
				return false;
			}
		}
		return true;
	}

	public static boolean conversionCommandClassExists(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (self.getConversionCommandClass() != null) {
			boolean exists = false;

			Object javaClass = ArchitectureCommandUtils.getCommandClass(self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS);
			exists = javaClass != null;

			if (!exists) {
				if (diagnostics != null) {
					// Further narrow the problem
					String problem = ArchitectureCommandUtils.getCommandClassUnconstrained(self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS) == null
							? "_UI_conversionCommandClassExists_diagnostic" //$NON-NLS-1$
							: "_UI_conversionCommandClassConforms_diagnostic"; //$NON-NLS-1$
					String expectedInterface = ArchitectureCommandUtils.getCommandType(ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS)
							.map(Class::getSimpleName).orElse(ArchitecturePlugin.INSTANCE.getString("_UI_genericRequiredInterface_name")); //$NON-NLS-1$
					Set<String> requiredBundleDependencies = ArchitectureCommandUtils.getRequiredCommandBundleDependencies(self);
					String depsList = String.join(", ", requiredBundleDependencies); //$NON-NLS-1$

					diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
							ArchitectureValidator.DIAGNOSTIC_SOURCE,
							ArchitectureValidator.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS_EXISTS,
							ArchitecturePlugin.INSTANCE.getString(problem, new Object[] {
									EObjectValidator.getObjectLabel(self, context), expectedInterface, requiredBundleDependencies.size(), depsList }),
							new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CONVERSION_COMMAND_CLASS }));
				}
				return false;
			}
		}
		return true;
	}

	public static boolean isConsistentWith(ArchitectureContext self, ArchitectureContext context) {
		return context != null && context.eClass() == self.eClass();
	}

	public static boolean contextExtensionsAreConsistent(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		List<ArchitectureContext> inconsistent = !self.isExtension()
				? List.of()
				: self.allExtendedContexts().stream().filter(other -> !self.isConsistentWith(other)).collect(Collectors.toList());

		if (!inconsistent.isEmpty()) {
			if (diagnostics != null) {
				String us = EObjectValidator.getObjectLabel(self, context);
				String them = inconsistent.stream().map(e -> EObjectValidator.getObjectLabel(e, context)).collect(Collectors.joining(", ")); //$NON-NLS-1$
				List<Object> data = new ArrayList<>(List.of(self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS));
				data.addAll(inconsistent);

				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
						ArchitectureValidator.DIAGNOSTIC_SOURCE,
						ArchitectureValidator.ARCHITECTURE_CONTEXT__CONTEXT_EXTENSIONS_ARE_CONSISTENT,
						ArchitecturePlugin.INSTANCE.getString("_UI_contextExtensionsAreConsistent_diagnostic", //$NON-NLS-1$
								new Object[] { us, them }),
						data.toArray()));
			}
			return false;
		}
		return true;
	}

	public static boolean contextGeneralizationIsConsistent(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Optional<ArchitectureContext> inconsistent = self.getGeneralContext() == null
				? Optional.empty()
				: self.allGeneralContexts().stream().filter(not(self::isConsistentWith)).findAny();
		if (inconsistent.isPresent()) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
						ArchitectureValidator.DIAGNOSTIC_SOURCE,
						ArchitectureValidator.ARCHITECTURE_CONTEXT__CONTEXT_GENERALIZATION_IS_CONSISTENT,
						ArchitecturePlugin.INSTANCE.getString("_UI_contextGeneralizationIsConsistent_diagnostic", //$NON-NLS-1$
								new Object[] { EObjectValidator.getObjectLabel(self, context),
										EObjectValidator.getObjectLabel(inconsistent.get(), context) }),
						new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT, inconsistent.get() }));
			}
			return false;
		}
		return true;
	}

	public static boolean creationCommandClassRequired(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!self.isExtension() && self.getCreationCommandClass() == null) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
						ArchitectureValidator.DIAGNOSTIC_SOURCE,
						ArchitectureValidator.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS_REQUIRED,
						ArchitecturePlugin.INSTANCE.getString("_UI_creationCommandClassRequired_diagnostic", //$NON-NLS-1$
								new Object[] { EObjectValidator.getObjectLabel(self, context) }),
						new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__CREATION_COMMAND_CLASS }));
			}
			return false;
		}
		return true;
	}

	public static EList<ArchitectureContext> allExtendedContexts(ArchitectureContext self) {
		Set<ArchitectureContext> result = new LinkedHashSet<>();
		Queue<ArchitectureContext> queue = new ArrayDeque<>(self.getExtendedContexts());

		for (ArchitectureContext extended = queue.poll(); extended != null; extended = queue.poll()) {
			if (result.add(extended)) {
				queue.addAll(extended.getExtendedContexts());
			} // Cycle detected
		}

		return new BasicEList.UnmodifiableEList.FastCompare<>(result);
	}

	public static EList<ArchitectureContext> allGeneralContexts(ArchitectureContext self) {
		Set<ArchitectureContext> result = new LinkedHashSet<>();

		for (ArchitectureContext general = self.getGeneralContext(); general != null; general = general.getGeneralContext()) {
			if (!result.add(general)) {
				// Cycle detected.
				break;
			}
		}

		return new BasicEList.UnmodifiableEList.FastCompare<>(result);
	}

	public static boolean extensionCycle(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (self.allExtendedContexts().contains(self)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
						ArchitectureValidator.DIAGNOSTIC_SOURCE,
						ArchitectureValidator.ARCHITECTURE_CONTEXT__EXTENSION_CYCLE,
						ArchitecturePlugin.INSTANCE.getString("_UI_extensionCycle_diagnostic", //$NON-NLS-1$
								new Object[] { EObjectValidator.getObjectLabel(self, context) }),
						new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS }));
			}
			return false;
		}
		return true;
	}

	public static boolean generalizationCycle(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (self.allGeneralContexts().contains(self)) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
						ArchitectureValidator.DIAGNOSTIC_SOURCE,
						ArchitectureValidator.ARCHITECTURE_CONTEXT__GENERALIZATION_CYCLE,
						ArchitecturePlugin.INSTANCE.getString("_UI_generalizationCycle_diagnostic", //$NON-NLS-1$
								new Object[] { EObjectValidator.getObjectLabel(self, context) }),
						new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__GENERAL_CONTEXT }));
			}
			return false;
		}
		return true;
	}

	public static boolean generalNotExtended(ArchitectureContext self, DiagnosticChain diagnostics, Map<Object, Object> context) {
		Set<ArchitectureContext> allGenerals = new HashSet<>(self.allGeneralContexts());
		if (allGenerals.removeAll(self.allExtendedContexts())) {
			if (diagnostics != null) {
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR,
						ArchitectureValidator.DIAGNOSTIC_SOURCE,
						ArchitectureValidator.ARCHITECTURE_CONTEXT__GENERAL_NOT_EXTENDED,
						ArchitecturePlugin.INSTANCE.getString("_UI_generalNotExtended_diagnostic", //$NON-NLS-1$
								new Object[] { EObjectValidator.getObjectLabel(self, context) }),
						new Object[] { self, ArchitecturePackage.Literals.ARCHITECTURE_CONTEXT__EXTENDED_CONTEXTS }));
			}
			return false;
		}
		return true;
	}

}

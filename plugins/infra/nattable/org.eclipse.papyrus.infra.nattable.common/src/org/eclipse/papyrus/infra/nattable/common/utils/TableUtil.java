/*****************************************************************************
 * Copyright (c) 2014, 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550568
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 551558
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.nattable.common.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.representation.PapyrusTable;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;


public class TableUtil {

	/**
	 * Gets the tables associated to element.
	 *
	 * @param element
	 * @param resourceSet
	 *            can be null, it will then try to retrieve it from the element.
	 * @return the list of diagrams associated with the given element
	 */
	public static List<Table> getAssociatedTables(EObject element, ResourceSet resourceSet) {
		List<Table> result;

		if (resourceSet == null) {
			resourceSet = EMFHelper.getResourceSet(element);
		}

		if (resourceSet == null) {
			// Deny
			result = Collections.emptyList();
		} else {
			result = new ArrayList<>(3); // Don't anticipate many
			for (EStructuralFeature.Setting setting : EMFHelper.getUsages(element)) {
				if (setting.getEStructuralFeature() == NattablePackage.Literals.TABLE__OWNER) {
					if (EMFHelper.getResourceSet(setting.getEObject()) == resourceSet) {
						result.add((Table) setting.getEObject());
					}
				}
			}
		}

		return result;
	}

	/**
	 * Gets the diagrams associated to element.
	 *
	 * @param element
	 * @param notationResource
	 *            the notation resource where to look for diagrams
	 * @return the list of diagrams associated with the given element
	 */
	public static List<Table> getAssociatedTablesFromNotationResource(EObject element, Resource notationResource) {
		if (notationResource != null) {
			LinkedList<Table> tables = new LinkedList<>();
			for (EObject eObj : notationResource.getContents()) {
				if (eObj instanceof Table) {
					Table table = (Table) eObj;
					if (element.equals(table.getOwner())) {
						tables.add(table);
					}
				}
			}
			return tables;
		}
		return Collections.emptyList();
	}

	/**
	 * Gets the prototype of a table
	 * Check if the selected viewpoint contains
	 * 1. the table model kind
	 * 2. an ancestor of the table model kind
	 * 3. a descendant of the table model kind
	 *
	 * @param table
	 *            A table
	 * @return The table's prototype
	 * @since 5.3
	 */
	public static ViewPrototype getPrototype(final Table table) {
		PolicyChecker checker = PolicyChecker.getFor(table);
		return getPrototype(table, checker);
	}

	/**
	 * Gets the prototype of a table
	 * Check if the selected viewpoint contains
	 * 1. the table model kind
	 * 2. an ancestor of the table model kind
	 * 3. a descendant of the table model kind
	 *
	 * @param table
	 *            A table.
	 * @param checkViewpoint
	 *            Boolean to determinate if we have to check viewpoint consistency before returning the view prototype.
	 * @return The table's prototype
	 * @since 5.3
	 */
	public static ViewPrototype getPrototype(final Table table, final boolean checkViewpoint) {
		PolicyChecker checker = PolicyChecker.getFor(table);
		return getPrototype(table, checker, checkViewpoint);
	}

	/**
	 * Gets the prototype of a {@code table} according to a given policy {@code checker}.
	 *
	 * @param table
	 *            a table
	 * @param checker
	 *            a policy checker
	 * @return the policy {@code checker}'s prototype for the {@code table}
	 * @since 5.3
	 */
	public static ViewPrototype getPrototype(final Table table, final PolicyChecker checker) {
		// By default check the viewpoint consistency
		return getPrototype(table, checker, true);
	}

	/**
	 * Gets the prototype of a {@code table} according to a given policy {@code checker}.
	 *
	 * @param table
	 *            A table.
	 * @param checker
	 *            A policy checker.
	 * @param checkViewpoint
	 *            Boolean to determinate if we have to check viewpoint consistency before returning the view prototype.
	 * @return the policy {@code checker}'s prototype for the {@code table}
	 *
	 * @since 5.3
	 */
	public static ViewPrototype getPrototype(final Table table, final PolicyChecker checker, final boolean checkViewpoint) {
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		PapyrusTable repKind = null;
		String tableKindId = table.getTableKindId();
		if (manager.getRepresentationKindById(tableKindId) != null) {
			if (manager.getRepresentationKindById(tableKindId) instanceof PapyrusTable) {
				repKind = (PapyrusTable) manager.getRepresentationKindById(tableKindId);
			} else {
				Activator.log.info("Unexpected table kind: " + tableKindId + " Your notation file might be broken or created with a previous version of the architecture framework."); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// Check if the selected viewpoint contains the diagram model kind
			if (repKind != null) {

				if (!checkViewpoint || checker.isInViewpoint(repKind)) {
					return ViewPrototype.get(repKind);
				}

				// Check if the selected viewpoint contains an ancestor of the diagram model kind
				PapyrusRepresentationKind tableParentView = repKind.getParent();
				while (tableParentView != null && !checker.isInViewpoint(tableParentView)) {
					tableParentView = tableParentView.getParent();
				}
				if (tableParentView != null) {
					return ViewPrototype.get(tableParentView);
				}

				// Check if the selected viewpoint contains a descendant of the diagram model kind
				String tableConfigName = repKind.getName();
				if (tableConfigName != null) { // the model kind name is used as a "semantic" key to test equality
					for (final MergedArchitectureViewpoint viewpoint : checker.getViewpoints()) {
						for (final RepresentationKind representationKind : viewpoint.getRepresentationKinds()) {
							if (representationKind instanceof PapyrusRepresentationKind) {
								PapyrusRepresentationKind papyrusRepresentationKind = (PapyrusRepresentationKind) representationKind;

								if (tableConfigName.equals(papyrusRepresentationKind.getName())) {
									ViewPrototype.get(papyrusRepresentationKind);
								}

								PapyrusRepresentationKind parentPapyrusRepresentationKind = papyrusRepresentationKind.getParent();
								while (parentPapyrusRepresentationKind != null && !tableConfigName.equals(parentPapyrusRepresentationKind.getName())) {
									parentPapyrusRepresentationKind = parentPapyrusRepresentationKind.getParent();
								}
								if (parentPapyrusRepresentationKind != null) {
									return ViewPrototype.get(papyrusRepresentationKind);
								}
							}
						}
					}
				}

			}
			return ViewPrototype.get(checker, table.getTableKindId(), table.getOwner(), table.getContext());
		}
		return ViewPrototype.get(checker, table.getTableConfiguration().getType(), table.getOwner(), table.getContext());
	}
}

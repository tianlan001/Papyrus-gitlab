/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.facet.architecture.internal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator;
import org.eclipse.papyrus.emf.facet.architecture.internal.customizationconfiguration.comparators.CustomizationReferenceMerger;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration;

/**
 * Custom validator for the CustomizationConfiguration metamamodel
 */
public class CustomCustomizationConfigurationValidator extends CustomizationConfigurationValidator {

	/**
	 * the file extension of architecture file
	 */
	private static final String ARCHITECTURE_FILE_EXTENSION = "architecture"; //$NON-NLS-1$

	/**
	 * This class is a singleton
	 */
	public static final CustomizationConfigurationValidator INSTANCE = new CustomCustomizationConfigurationValidator();

	/**
	 * Constructor.
	 *
	 */
	private CustomCustomizationConfigurationValidator() {
		super();
	}


	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator#validateEMFFacetTreeViewerConfiguration_checkConfiguration(org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @param emfFacetTreeViewerConfiguration
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	@Override
	public boolean validateEMFFacetTreeViewerConfiguration_checkConfiguration(EMFFacetTreeViewerConfiguration emfFacetTreeViewerConfiguration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return checkElement(emfFacetTreeViewerConfiguration, diagnostics, context, getAllConfigurations(emfFacetTreeViewerConfiguration));
	}


	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator#validateAbsoluteOrder_checkAbsoluteOrder(org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @param absoluteOrder
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	@Override
	public boolean validateAbsoluteOrder_checkAbsoluteOrder(AbsoluteOrder absoluteOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return checkApplicationRule(absoluteOrder, diagnostics, context);

	}


	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator#validateRelativeOrder_checkRelativeOrder(org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @param relativeOrder
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	@Override
	public boolean validateRelativeOrder_checkRelativeOrder(RelativeOrder relativeOrder, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return checkApplicationRule(relativeOrder, diagnostics, context);

	}


	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator#validateRedefinition_checkRedefinition(org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @param redefinition
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	@Override
	public boolean validateRedefinition_checkRedefinition(Redefinition redefinition, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return checkApplicationRule(redefinition, diagnostics, context);
	}

	/**
	 *
	 * @param rule
	 *            the rule to check
	 * @param diagnostics
	 *            the diagnotics to contribute
	 * @param context
	 *            the context
	 * @return
	 *         <code>true</code> if the rule is valid, <code>false</code> otherwise
	 */
	private boolean checkApplicationRule(final IApplicationRule rule, final DiagnosticChain diagnostics, final Map<Object, Object> context) {
		final CustomizationReference custoRef = (CustomizationReference) rule.eContainer();
		final EMFFacetTreeViewerConfiguration emfFacetTreeViewerConfiguration = (EMFFacetTreeViewerConfiguration) custoRef.eContainer();
		return checkElement(rule, diagnostics, context, getAllConfigurations(emfFacetTreeViewerConfiguration));
	}


	/**
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator#validateCustomizationReference_checkCustomizationReference(org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference,
	 *      org.eclipse.emf.common.util.DiagnosticChain, java.util.Map)
	 *
	 * @param customizationReference
	 * @param diagnostics
	 * @param context
	 * @return
	 */
	@Override
	public boolean validateCustomizationReference_checkCustomizationReference(CustomizationReference customizationReference, DiagnosticChain diagnostics, Map<Object, Object> context) {
		final EMFFacetTreeViewerConfiguration emfFacetTreeViewerConfiguration = (EMFFacetTreeViewerConfiguration) customizationReference.eContainer();
		return checkElement(customizationReference, diagnostics, context, getAllConfigurations(emfFacetTreeViewerConfiguration));
	}

	/**
	 *
	 * @param toCheck
	 *            the element to validate
	 * @param diagnostics
	 *            the diagnotics to contribute
	 * @param context
	 *            the context
	 * @param allConfigurations
	 *            all {@link EMFFacetTreeViewerConfiguration} to use to do the validation
	 * @return
	 *         <code>true</code> if the element is valid, <code>false</code> otherwise
	 */
	private boolean checkElement(final EObject toCheck, final DiagnosticChain diagnostics, final Map<Object, Object> context, final Collection<EMFFacetTreeViewerConfiguration> allConfigurations) {
		final ICustomizationReferenceMerger merger = new CustomizationReferenceMerger(allConfigurations);
		merger.doValidationAndMerge();
		final IStatus status = merger.getStatus().get(toCheck);
		createDiagnostic(toCheck, diagnostics, context, status);
		return status == null;
	}

	/**
	 *
	 * @param checkedElement
	 *            the checked element
	 * @param diagnostics
	 *            the diagnotics to contribute
	 * @param context
	 *            the context
	 * @param status
	 *            the status representing the validation
	 */
	private void createDiagnostic(final EObject checkedElement, final DiagnosticChain diagnostics, final Map<Object, Object> context, final IStatus status) {
		if (status != null) {
			if (status instanceof MultiStatus) {
				for (IStatus current : ((MultiStatus) status).getChildren()) {
					createDiagnostic(checkedElement, diagnostics, context, current);
				}
			} else {
				Diagnostic d = new BasicDiagnostic(Diagnostic.ERROR, DIAGNOSTIC_SOURCE, status.getCode(), status.getMessage(), new Object[] { checkedElement });
				diagnostics.add(d);
			}
		}
	}

	/**
	 *
	 * @param configuration
	 *            an {@link EMFFacetTreeViewerConfiguration}
	 * @return
	 *         all {@link EMFFacetTreeViewerConfiguration} contributing to the same {@link ArchitectureDescriptionLanguage} (including the parameter itself)
	 */
	private Collection<EMFFacetTreeViewerConfiguration> getAllConfigurations(final EMFFacetTreeViewerConfiguration configuration) {
		// 1. get the current ADL
		final ArchitectureDescriptionLanguage currentLanguage = (ArchitectureDescriptionLanguage) configuration.eContainer();

		// 2. return the EMFFacetTreeViewerConfiguration contributing to the current ArchitectureDescriptionLanguage
		return getAllEMFFacetTreeViewerConfigurations(currentLanguage);
	}

	/**
	 *
	 * @param adl
	 *            an {@link ArchitectureDescriptionLanguage}
	 * @return
	 *         all {@link EMFFacetTreeViewerConfiguration} contributing to this {@link ArchitectureDescriptionLanguage}
	 */
	private Collection<EMFFacetTreeViewerConfiguration> getAllEMFFacetTreeViewerConfigurations(final ArchitectureDescriptionLanguage adl) {
		return getAllTreeViewerConfigurations(adl)
				.stream()
				.filter(EMFFacetTreeViewerConfiguration.class::isInstance)
				.map(EMFFacetTreeViewerConfiguration.class::cast)
				.collect(Collectors.toList());
	}

	/**
	 *
	 * @param adl
	 *            an {@link ArchitectureDescriptionLanguage}
	 * @return
	 *         all {@link TreeViewerConfiguration} contributing to this {@link ArchitectureDescriptionLanguage}
	 */
	private Collection<TreeViewerConfiguration> getAllTreeViewerConfigurations(final ArchitectureDescriptionLanguage adl) {
		final Collection<Resource> allArchitectures = getAllReferencedArchitectureResources(adl.eResource().getResourceSet());
		final Collection<ArchitectureDescriptionLanguage> contributionToCurrentADL = getAllArchitectureDescriptionLanguageContributions(adl.getId(), getAllADLs(allArchitectures));
		final List<TreeViewerConfiguration> treeViewerConfiguration = new ArrayList<>();
		for (final ArchitectureDescriptionLanguage current : contributionToCurrentADL) {
			treeViewerConfiguration.addAll(current.getTreeViewerConfigurations());
		}
		return treeViewerConfiguration;
	}

	/**
	 *
	 * @param resourceSet
	 *            a {@link ResourceSet}
	 * @return
	 *         all architecture {@link Resource} in this {@link ResourceSet}
	 */
	private Collection<Resource> getAllReferencedArchitectureResources(final ResourceSet resourceSet) {
		final Collection<Resource> architectures = new ArrayList<>();
		for (final Resource current : resourceSet.getResources()) {
			if (ARCHITECTURE_FILE_EXTENSION.equals(current.getURI().fileExtension()) && current.getContents().size() > 0) {
				architectures.add(current);
			}
		}
		return architectures;
	}

	/**
	 *
	 * @param resources
	 *            a list of {@link Resource}
	 * @return
	 *         all {@link ArchitectureDescriptionLanguage} owned by these {@link Resource}
	 */
	private Collection<ArchitectureDescriptionLanguage> getAllADLs(final Collection<Resource> resources) {
		final Collection<ArchitectureDescriptionLanguage> allADLs = new ArrayList<>();
		for (final Resource current : resources) {
			if (current.getContents().size() > 0 && current.getContents().get(0) instanceof ArchitectureDomain) {
				final EObject root = current.getContents().get(0);
				for (final ArchitectureContext AFcontext : ((ArchitectureDomain) root).getContexts()) {
					if (AFcontext instanceof ArchitectureDescriptionLanguage) {
						allADLs.add((ArchitectureDescriptionLanguage) AFcontext);
					}
				}
			}
		}
		return allADLs;
	}

	/**
	 *
	 * @param adlID
	 *            the ID of the current {@link ArchitectureDescriptionLanguage}
	 * @param allADLs
	 *            all known {@link ArchitectureDescriptionLanguage}
	 * @return
	 *         all {@link ArchitectureDescriptionLanguage} elements with the same id
	 */
	private Collection<ArchitectureDescriptionLanguage> getAllArchitectureDescriptionLanguageContributions(final String adlID, final Collection<ArchitectureDescriptionLanguage> allADLs) {
		return allADLs.stream().filter(adl -> adlID.equals(adl.getId())).collect(Collectors.toList());
	}
}

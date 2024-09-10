/*****************************************************************************
 * Copyright (c) 2014, 2015, 2020 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   Ansgar Radermacher - Bug 526156, add postfix, if generating DI element types
 *   Camille Letavernier - Bug 569356 Support incremental generation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator.ui.internal.wizards;

import static com.google.common.base.Strings.isNullOrEmpty;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy;
import org.eclipse.uml2.uml.Profile;

/**
 * @author damus
 *
 */
public class GeneratorWizardModel {

	private final IWizard owner;

	private final Profile profile;

	private final IDialogSettings settings;

	private String identifier;

	private String selectedElementTypeSet;

	private IPath containerPath;

	private String fileName;

	private boolean suppressSemanticSuperElementTypes;

	private boolean addDiPostfix;

	private boolean incremental = true;

	private boolean generateExtensionPoint = true;

	private boolean deleteObsoleteTypes = true;

	private DeltaStrategy.Diff diff;

	public GeneratorWizardModel(IWizard owner, Profile profile, IDialogSettings settings) {
		super();

		this.owner = owner;
		this.profile = profile;
		this.settings = settings;
	}

	public Profile getProfile() {
		return profile;
	}

	public IDialogSettings getDialogSettings() {
		return settings;
	}

	private WizardPage currentPage() {
		WizardPage result = (WizardPage) owner.getContainer().getCurrentPage();

		if (result == null) {
			result = (WizardPage) owner.getStartingPage();
		}

		return result;
	}

	public void setErrorMessage(String message) {
		currentPage().setErrorMessage(message);
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getSelectedElementTypeSet() {
		return selectedElementTypeSet;
	}

	public void setSelectedElementTypeSet(String selectedElementTypeSet) {
		this.selectedElementTypeSet = selectedElementTypeSet;
	}

	public void setContainerPath(IPath containerPath) {
		this.containerPath = containerPath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setSuppressSemanticSuperElementTypes(boolean suppressSemanticSuperElementTypes) {
		this.suppressSemanticSuperElementTypes = suppressSemanticSuperElementTypes;
	}

	public boolean isSuppressSemanticSuperElementTypes() {
		return suppressSemanticSuperElementTypes;
	}

	/**
	 * Control whether a DI postfix should be used
	 *
	 * @since 1.3.0
	 */
	public void setAddDiPostfix(boolean addDiPostfix) {
		this.addDiPostfix = addDiPostfix;
	}

	/**
	 * Check whether a DI postfix should be used
	 *
	 * @since 1.3.0
	 */
	public boolean isAddDiPostfix() {
		return addDiPostfix;
	}

	/**
	 * @return isAddDiPostfix configured and DI element type set is selected.
	 * @since 1.3.0
	 */
	public boolean isAddDiPostfixActive() {
		return addDiPostfix && BaseElementTypeSetBlock.UMLDI_ELEMENT_TYPE_SET.equals(getSelectedElementTypeSet());
	}

	public URI getOutputModelURI() {
		return URI.createPlatformResourceURI(containerPath.append(fileName).toString(), true);
	}

	public IFile getOutputModelFile() {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(containerPath.append(fileName));
	}

	public void validatePage() {
		WizardPage current = currentPage();
		if (current instanceof IGeneratorWizardPage) {
			current.setPageComplete(((IGeneratorWizardPage) current).validatePage());
		}
	}

	public boolean validate() {
		boolean result = true;

		setErrorMessage(null);
		if (isNullOrEmpty(getIdentifier())) {
			setErrorMessage("An identifier is required.");//$NON-NLS-1$
			result = false;
		} else if (getSelectedElementTypeSet() == null) {
			setErrorMessage("A base element type set must be selected.");//$NON-NLS-1$
			result = false;
		}

		return result;
	}

	IStructuredSelection getDefaultContainerSelection() {
		IFile file = ResourceUtils.getFile(getProfile().eResource());
		return (file == null) ? StructuredSelection.EMPTY : new StructuredSelection(file.getParent());
	}

	String getDefaultFileName() {
		return trimExtensions(EcoreUtil.getURI(profile).trimFragment()).lastSegment();
	}

	static URI trimExtensions(URI uri) {
		URI result = uri.trimFileExtension();

		while (!result.equals(uri)) {
			uri = result;
			result = result.trimFileExtension();
		}

		return result;
	}

	public DeltaStrategy.Diff getDiff() {
		return diff;
	}

	public void setDiff(DeltaStrategy.Diff diff) {
		this.diff = diff;
	}

	/**
	 * <p>
	 * Whether this generation should be incremental. This value is ignored
	 * when generating to a file that doesn't exist yet.
	 * </p>
	 * <p>
	 * If the file exists and this value is false, the file will be overwritten.
	 * If the file exists and this value is true, the target {@link ElementTypeSetConfiguration}
	 * will be updated, preserving user modifications (as much as possible).
	 * </p>
	 *
	 * @return the incremental value
	 */
	public boolean isIncremental() {
		return incremental;
	}

	/**
	 * @param incremental
	 *            the incremental to set
	 */
	public void setIncremental(boolean incremental) {
		this.incremental = incremental;
	}

	/**
	 * <p>
	 * Whether the generation should populate the org.eclipse.papyrus.infra.types.core.elementTypeSetConfiguration
	 * extension point. This value is ignored if the output file is not located in an Eclipse plug-in.
	 * </p>
	 * <p>
	 * This value should be set to <code>true</code> if the profile is meant to be used with generic Papyrus UML
	 * models, and <code>false</code> if the profile defines its own Architecture Language (i.e. is meant to be
	 * used as a standalone language). If the profile can be used in both cases, then the value should be
	 * <code>true</code>.
	 * </p>
	 *
	 * @return the generateExtensionPoint value
	 */
	public boolean isGenerateExtensionPoint() {
		return generateExtensionPoint;
	}

	/**
	 * @param generateExtensionPoint
	 *            the generateExtensionPoint to set
	 */
	public void setGenerateExtensionPoint(boolean generateExtensionPoint) {
		this.generateExtensionPoint = generateExtensionPoint;
	}

	/**
	 * <p>
	 * Whether ElementTypeConfigurations related to Stereotypes that no longer exist should be removed.
	 * This value is ignored when generating to a file that doesn't exist yet, or when incremental generation
	 * is turned off.
	 * </p>
	 * <p>
	 * Set this value to <code>true</code> to automatically clean-up obsolete ElementTypeConfigurations;
	 * set <code>false</code> if the user should clean-up manually (e.g. to migrate/fix broken ElementTypeConfigurations)
	 * </p>
	 *
	 * @return the deleteObsoleteTypes
	 */
	public boolean isDeleteObsoleteTypes() {
		return deleteObsoleteTypes;
	}

	/**
	 * @param deleteObsoleteTypes
	 *            the removeDeletedTypes to set
	 */
	public void setDeleteObsoleteTypes(boolean deleteObsoleteTypes) {
		this.deleteObsoleteTypes = deleteObsoleteTypes;
	}
}

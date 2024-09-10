/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation (Bug 520936)
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.export.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.export.messages.Messages;

/**
 * <p>
 * Provides the export name for a Diagram. During export, each diagram
 * may produce a file. In order to support cross-referencing, the name
 * must be deterministic, even when exporting a single diagram, and unique.
 * </p>
 *
 * <p>
 * This class will generate a unique and deterministic name for each Diagram
 * in the model, even if only one of these diagrams ends up actually being exported.
 * </p>
 *
 * @since 2.1
 */
public class DiagramNameProvider {

	private Collection<Diagram> exportedDiagrams;
	private boolean useQualifiedName;
	private Set<Diagram> duplicateDiagramNames = new HashSet<>();
	private List<Diagnostic> diagnostic = new ArrayList<>();

	private Map<Diagram, String> diagramNames = null;

	public DiagramNameProvider(Collection<Diagram> diagrams, boolean useQualifiedName) {
		this.exportedDiagrams = diagrams;
		this.useQualifiedName = useQualifiedName;
	}

	/**
	 * Returns a map of name for each diagram in this model set
	 *
	 * @param modelSet
	 *            The model set
	 * @return
	 * 		A map containing a name entry for each diagram in the model set
	 */
	public Map<Diagram, String> getDiagramNames() {
		if (diagramNames == null) {
			Collection<Diagram> allDiagrams = getAllDiagrams();
			diagramNames = getFileNames(allDiagrams);
		}
		return diagramNames;
	}

	/**
	 * Return a list of diagnostics indicating if warnings or errors
	 * occurred while computing diagram name.
	 *
	 * @return
	 */
	public List<Diagnostic> getDiagnostic() {
		return diagnostic;
	}

	public boolean hasDuplicates(Diagram diagram) {
		return duplicateDiagramNames.contains(diagram);
	}

	private Collection<Diagram> getAllDiagrams() {
		List<Diagram> result = new ArrayList<>();

		// TODO browse the model set to (really) include all diagrams
		result.addAll(exportedDiagrams);

		return result;
	}

	private Map<Diagram, String> getFileNames(Collection<Diagram> diagrams) {
		Map<Diagram, String> result = new HashMap<>();
		Collection<String> usedNames = new HashSet<>();
		for (Diagram diagram : diagrams) {
			String name = handleFileName(diagram, usedNames);
			usedNames.add(name);
			result.put(diagram, name);
		}
		return result;
	}

	/**
	 * Handle file name.
	 *
	 * @param diagram
	 *            the diagram
	 * @param diagramNames
	 * @param duplicates
	 * @return the string
	 */
	private String handleFileName(Diagram diagram, Collection<String> diagramNames) {
		boolean nameCut = false;

		// Extract name of diagram
		String label = ""; //$NON-NLS-1$
		if (useQualifiedName) {
			ComposedAdapterFactory composedAdapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			composedAdapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());

			try {
				IItemLabelProvider itemLabelFactory = (IItemLabelProvider) composedAdapterFactory.adapt(diagram.getElement(), IItemLabelProvider.class);
				label = itemLabelFactory.getText(diagram.getElement()).replace(Messages.ExportAllDiagrams_16, "") + "_"; //$NON-NLS-1$//$NON-NLS-2$
			} finally {
				// Don't leak the adapters created by this factory
				composedAdapterFactory.dispose();
			}
		}

		String uniqueFileName = encodeFileName(label + diagram.getName());

		// Verify file name length
		if (uniqueFileName.length() > 150) {
			nameCut = true;
			uniqueFileName = uniqueFileName.substring(0, 150);
		}

		// Detect duplicated diagram name
		if (diagramNames.contains(uniqueFileName)) {
			duplicateDiagramNames.add(diagram);
			uniqueFileName = getFirstAvailableName(uniqueFileName, diagramNames, 1);
		}

		// Add warning about cut name in export diagnostic
		if (nameCut) {
			BasicDiagnostic newDiagnostic = new BasicDiagnostic(Diagnostic.WARNING, "", 0, Messages.ExportAllDiagrams_10 + uniqueFileName, null); //$NON-NLS-1$
			diagnostic.add(newDiagnostic);
		}

		return uniqueFileName;
	}

	/**
	 * Gets the first available name.
	 *
	 * @param commonBasis
	 *            the common basis
	 * @param existingNames
	 *            the existing names
	 * @param cpt
	 *            the cpt
	 * @return the first available name
	 */
	private String getFirstAvailableName(String commonBasis, Collection<String> existingNames, int cpt) {
		if (existingNames.contains(commonBasis + cpt)) {
			return getFirstAvailableName(commonBasis, existingNames, cpt + 1);
		}
		return commonBasis + cpt;
	}


	/**
	 * Escape all characters that may result in a wrong file name.
	 *
	 * @param pathName
	 *            a file name to encode
	 * @return The encoded file name
	 */
	private String encodeFileName(String pathName) {
		pathName = pathName.trim();
		pathName = pathName.replaceAll(Messages.ExportAllDiagrams_14, Messages.ExportAllDiagrams_15);
		pathName = pathName.replaceAll("_-_", "-"); //$NON-NLS-1$ //$NON-NLS-2$
		while (pathName.contains("__")) { //$NON-NLS-1$
			pathName = pathName.replaceAll("__", "_"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (pathName.startsWith("_")) { //$NON-NLS-1$
			pathName = pathName.replaceFirst("_", ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		if (pathName.endsWith("_")) { //$NON-NLS-1$
			pathName = pathName.substring(0, pathName.length() - 1);
		}

		return pathName;
		// return URLEncoder.encode(pathName, "UTF-8").replaceAll("[*]", "_");
	}
}


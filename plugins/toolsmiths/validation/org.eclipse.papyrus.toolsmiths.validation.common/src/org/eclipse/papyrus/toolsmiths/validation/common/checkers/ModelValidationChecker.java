/*****************************************************************************
 * Copyright (c) 2019, 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 569357
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.checkers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

/**
 * This class allows to check the element types file.
 */
public class ModelValidationChecker extends AbstractPluginChecker {

	/**
	 * The EMF model resource to validate.
	 */
	private final Resource resource;

	/**
	 * Constructor.
	 *
	 * @param modelFile
	 *            The model file to validate.
	 * @param resource
	 *            The EMF resource to validate.
	 * @param markerType
	 *            The problem marker type to create.
	 */
	public ModelValidationChecker(final IFile modelFile, final Resource resource, String markerType) {
		super(modelFile.getProject(), modelFile, markerType);

		this.resource = resource;
	}

	@Override
	public void check(final DiagnosticChain diagnostics, final IProgressMonitor monitor) {
		SubMonitor subMonitor = SubMonitor.convert(monitor, "Validate file '" + getModelFile().getName() + "'.", 1); //$NON-NLS-1$ //$NON-NLS-2$

		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		final EValidator.SubstitutionLabelProvider labels = createSubstitutionLabelProvider(adapterFactory);

		Map<Object, Object> context = new HashMap<>();
		context.put(EValidator.SubstitutionLabelProvider.class, labels);

		// Get the resource and validate it
		try {
			BasicDiagnostic validationResults = new BasicDiagnostic();
			Diagnostician.INSTANCE.validate(resource.getContents().get(0), validationResults, context);
			if (validationResults.getSeverity() > Diagnostic.OK) {
				diagnostics.merge(wrap(validationResults));
			}
		} finally {
			adapterFactory.dispose();
		}

		subMonitor.worked(1);
		SubMonitor.done(monitor);
	}

	/**
	 * Create a substitution label provider using item providers from the given factory.
	 *
	 * @param adapterFactory
	 *            an item-provider adapter factory
	 * @return a substitution label provider that gets labels from item providers
	 */
	public static EValidator.SubstitutionLabelProvider createSubstitutionLabelProvider(AdapterFactory adapterFactory) {
		return new EValidator.SubstitutionLabelProvider() {
			@Override
			public String getObjectLabel(EObject eObject) {
				String result = null;

				if (eObject.eIsProxy()) {
					result = EcoreUtil.getURI(eObject).toString();
				} else {
					IItemLabelProvider labels = (IItemLabelProvider) adapterFactory.adapt(eObject, IItemLabelProvider.class);
					if (labels != null) {
						result = labels.getText(eObject);
					}
				}

				if (result == null) {
					result = EcoreUtil.getIdentification(eObject);
				}

				return result;
			}

			@Override
			public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
				return eStructuralFeature.getName();
			}

			@Override
			public String getValueLabel(EDataType eDataType, Object value) {
				return EcoreUtil.convertToString(eDataType, value);
			}
		};
	}

}

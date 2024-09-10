/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.semantic.service.impl;

import static java.util.stream.StreamSupport.stream;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.IEMFModel;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.services.semantic.Activator;
import org.eclipse.papyrus.infra.services.semantic.service.SemanticService;

/**
 * Default implementation of the SemanticService. The result is composed of the root elements
 * of each resource of the current model set
 *
 * @author Camille Letavernier
 *
 */
public class SemanticServiceImpl implements SemanticService {

	private ServicesRegistry registry;

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.registry = servicesRegistry;
	}

	@Override
	public void startService() throws ServiceException {
		// Nothing
	}

	@Override
	public void disposeService() throws ServiceException {
		registry = null;
	}

	/**
	 * The default implements returns all root EObjects
	 */
	@Override
	public EObject[] getSemanticRoots() {
		EObject[] result;

		try {
			ModelSet modelSet = ServiceUtils.getInstance().getModelSet(registry);
			Collection<IEMFModel> emfModels = getEMFModels();
			if (emfModels.isEmpty()) {
				List<EObject> rootElements = new LinkedList<EObject>();
				for (Resource resource : modelSet.getResources()) {
					rootElements.addAll(resource.getContents());
				}
				result = rootElements.toArray(new EObject[rootElements.size()]);
			} else {
				result = emfModels.stream()
						.flatMap(m -> stream(m.getRootElements().spliterator(), false))
						.toArray(EObject[]::new);
			}
		} catch (Exception ex) {
			Activator.log.error(ex);
			result = new EObject[0];
		}

		return result;
	}

	@Override
	public IModel[] getSemanticIModels() {
		IModel[] result;

		try {
			ModelSet modelSet = ServiceUtils.getInstance().getModelSet(registry);
			result = ILanguageService.getLanguageModels(modelSet).stream().toArray(IModel[]::new);
		} catch (Exception e) {
			Activator.log.error(e);
			result = new IModel[0];
		}

		return result;
	}

	protected Collection<IEMFModel> getEMFModels() {
		return Stream.of(getSemanticIModels())
				.filter(IEMFModel.class::isInstance)
				.map(IEMFModel.class::cast)
				.distinct() // Unique models only
				.collect(Collectors.toList());
	}
}

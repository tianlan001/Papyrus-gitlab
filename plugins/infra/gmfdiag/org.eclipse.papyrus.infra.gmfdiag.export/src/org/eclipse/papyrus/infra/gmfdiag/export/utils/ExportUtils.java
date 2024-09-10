/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.export.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.gmfdiag.export.Activator;
import org.eclipse.papyrus.infra.gmfdiag.export.engine.ExportDiagramLocalPageService;
import org.eclipse.papyrus.infra.services.semantic.service.SemanticService;

/**
 * Static utilities in support of the export APIs.
 */
public class ExportUtils {

	private ExportUtils() {
		super();
	}

	/**
	 * Obtains the complete collection of exportable diagrams in a model-set.
	 * 
	 * @param modelSet
	 *            the model-set from which to export diagrams
	 * 
	 * @return the exportable diagrams (which may be empty if there are none)
	 * 
	 * @throws ServiceException
	 *             on failure to get a required Papyrus service
	 */
	public static List<Diagram> getExportableDiagrams(ModelSet modelSet) throws ServiceException {
		return exportableDiagrams(modelSet).collect(Collectors.toList());
	}

	private static Stream<Diagram> exportableDiagrams(ModelSet modelSet) throws ServiceException {
		IPageManager pageManager = ServiceUtilsForResourceSet.getInstance().getService(IPageManager.class, modelSet);
		SemanticService semanticService = ServiceUtilsForResourceSet.getInstance().getService(SemanticService.class, modelSet);

		return Stream.of(semanticService.getSemanticRoots())
				.map(ExportDiagramLocalPageService::new)
				.flatMap(service -> pageManager.allLocalPages(service).stream())
				.filter(Diagram.class::isInstance)
				.map(Diagram.class::cast)
				.distinct();
	}

	/**
	 * Queries whether a model-set has any exportable diagrams.
	 * 
	 * @param modelSet
	 *            the model-set from which to export diagrams
	 * 
	 * @return whether it has any diagrams
	 */
	public static boolean hasExportableDiagrams(ModelSet modelSet) {
		try {
			return exportableDiagrams(modelSet).findAny().isPresent();
		} catch (ServiceException e) {
			Activator.log.error(e);
			return false;
		}
	}
}

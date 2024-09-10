/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.core.language;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.editor.ModelSetServiceFactory;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;

/**
 * <p>
 * The Language Service provides information the {@linkplain ILanguage Modeling Languages} that are
 * instantiated in a Papyrus model.
 * </p>
 * <p>
 * In my capacity as an {@link IAdaptable}, I provide other services from the registry in which I
 * am provided as adapters, in addition to whatever other adapters may be contributed for me.
 * </p>
 * 
 * @see ILanguage
 */
public interface ILanguageService extends IAdaptable, ILanguageChangeListener {
	/**
	 * Queries the languages that are instantiated in the specified model resource.
	 * 
	 * @param modelURI
	 *            the URI of a model resource, such as might be used to load a {@link ModelSet}
	 * @param uriHasFileExtension
	 *            whether the {@code modelURI} has a file extension. For example, if the {@link ModelSet}
	 *            is requesting languages, then the URI typically does not have an extension because a model-set comprising several
	 *            resources that all have the same base file name
	 * 
	 * @return the languages instantiated in the specified resource
	 */
	Set<ILanguage> getLanguages(URI modelURI, boolean uriHasFileExtension);

	/**
	 * Queries the languages that are instantiated in the specified model-set. This has the
	 * advantage over the {@linkplain #getLanguages(URI, boolean) URI-based API} of being able
	 * to inspect resources that don't yet exist in the persistent store or are different from
	 * the latest persisted state.
	 * 
	 * @param modelSet
	 *            a {@link ModelSet} in which to find the instantiated languages
	 * 
	 * @return the languages instantiated in the specified resource
	 * @since 2.0
	 */
	Set<ILanguage> getLanguages(ModelSet modelSet);

	/**
	 * Adds a listener for language change notifications. Has no effect if the {@code listener} is already added.
	 * 
	 * @param listener
	 *            the listener to add
	 */
	void addLanguageChangeListener(ILanguageChangeListener listener);

	/**
	 * Removes a listener that is no longer interested in language change notifications. Has no effect if the {@link listener}
	 * is not currently added.
	 * 
	 * @param listener
	 *            a listener to remove
	 */
	void removeLanguageChangeListener(ILanguageChangeListener listener);

	/**
	 * Registers a language {@code provider}. Has no effect if the given {@code provider} is already registered.
	 * 
	 * @param provider
	 *            a language provider to add (may not be {@code null})
	 */
	void addLanguageProvider(ILanguageProvider provider);

	/**
	 * Unregisters a language {@code provider}. Has no effect if the given {@code provider} is not currently registered.
	 * 
	 * @param provider
	 *            a language provider to remove (may be {@code null}, which has no effect)
	 */
	void removeLanguageProvider(ILanguageProvider provider);

	/**
	 * Obtains all of the {@link IModel}s that are the models for languages instantiated
	 * in a {@code modelSet}.
	 * 
	 * @param modelSet
	 *            a model-set
	 * 
	 * @return its language models, which may be empty if the model-set is not in a
	 *         service registry that has a language service
	 * @since 2.0
	 */
	static Collection<IModel> getLanguageModels(ModelSet modelSet) {
		List<IModel> result;

		ILanguageService service = ServiceUtils.getInstance().getService(ILanguageService.class, ModelSetServiceFactory.getServiceRegistry(modelSet), null);
		if (service == null) {
			// No language service? No language models
			result = Collections.emptyList();
		} else {
			result = service.getLanguages(modelSet).stream()
					.map(l -> l.getModel(modelSet))
					.filter(Objects::nonNull)
					.distinct() // Only unique models
					.collect(Collectors.toList());
		}

		return result;
	}
}

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

package org.eclipse.papyrus.infra.editor.welcome.internal.modelelements;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.ILanguageChangeListener;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.language.LanguageChangeEvent;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.editor.welcome.internal.Activator;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.tools.databinding.WritableListWithIterator;

/**
 * A list property of the {@code LanguageObservable}s encapsulating the
 * {@link ILanguage}s instantiated in the model.
 */
public class LanguagesObservableProperty implements Supplier<IObservableList<LanguageObservable>> {
	private ILanguageService languageService;
	private ILanguageChangeListener languagesListener;

	private IObservableList<LanguageObservable> list;

	public LanguagesObservableProperty(WelcomeModelElement owner) {
		super();

		this.list = new WritableListWithIterator.Containment<>(LanguageObservable.class);

		try {
			this.languageService = ServiceUtilsForResourceSet.getInstance().getService(ILanguageService.class, owner.getDomain().getResourceSet());

			hookLanguagesListener();

			list.addDisposeListener(event -> {
				languageService.removeLanguageChangeListener(languagesListener);
				languagesListener = null;
				languageService = null;
			});
		} catch (ServiceException e) {
			Activator.log.error("Cannot obtain language service. Languages will not be shown.", e); //$NON-NLS-1$
		}
	}

	@Override
	public IObservableList<LanguageObservable> get() {
		return list;
	}

	void hookLanguagesListener() {
		languagesListener = event -> {
			switch (event.getType()) {
			case LanguageChangeEvent.ADDED:
				list.addAll(event.getLanguages().stream().map(LanguageObservable::new).collect(Collectors.toList()));
				break;
			case LanguageChangeEvent.REMOVED:
				list.removeAll(getObservables(event.getLanguages()));
				break;
			}
		};

		ModelSet modelSet = languageService.getAdapter(ModelSet.class);
		list.addAll(languageService.getLanguages(modelSet.getURIWithoutExtension(), false).stream().map(LanguageObservable::new).collect(Collectors.toList()));
		languageService.addLanguageChangeListener(languagesListener);
	}

	Collection<LanguageObservable> getObservables(Collection<? extends ILanguage> languages) {
		return list.stream().filter(o -> languages.contains(o.getLanguage())).collect(Collectors.toList());
	}
}

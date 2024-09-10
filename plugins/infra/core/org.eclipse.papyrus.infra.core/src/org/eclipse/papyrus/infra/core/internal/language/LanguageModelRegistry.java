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

package org.eclipse.papyrus.infra.core.internal.language;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

import com.google.common.base.Strings;

/**
 * A registry of bindings of {@link IModel}s to {@link ILanguage}s, by ID.
 */
public class LanguageModelRegistry {

	private static final String EXT_POINT = "language"; //$NON-NLS-1$

	public static final LanguageModelRegistry INSTANCE = new LanguageModelRegistry();

	private final Map<String, String> modelBindings = new HashMap<>();

	private LanguageModelRegistry() {
		super();

		new MyRegistryReader().readRegistry();
	}

	public String getModelID(String languageID) {
		return modelBindings.get(languageID);
	}

	public String getModelID(ILanguage language) {
		return getModelID(language.getID());
	}

	public IModel getModel(ILanguage language, ModelSet modelSet) {
		String modelID = getModelID(language);
		return (modelID == null) ? null : modelSet.getModel(modelID);
	}

	/**
	 * Bind a model to a language. Has no effect it the language is already bound to a model.
	 * 
	 * @param languageID
	 *            the language to bind
	 * @param modelID
	 *            the unique identifier of the model to bind to the language
	 */
	public void bind(String languageID, String modelID) {
		String current = modelBindings.putIfAbsent(languageID, modelID);
		if (current != null) {
			Activator.log.warn(NLS.bind("Language {0} is already bound to model {1}; ignoring model {2}", new Object[] { languageID, current, modelID }));
		}
	}

	/**
	 * Unbind a model from a language. Has no effect if the specified model is not currently
	 * bound to the language.
	 * 
	 * @param languageID
	 *            the language to unbind
	 * @param modelID
	 *            the unique identifier of the model to unbind from the language
	 */
	public void unbind(String languageID, String modelID) {
		if (!modelBindings.remove(languageID, modelID)) {
			Activator.log.warn(NLS.bind("Attempt to unbind model {0} from language {1} to which it is not bound", modelID, languageID));
		}
	}

	//
	// Nested types
	//

	private class MyRegistryReader extends RegistryReader {

		private static final String E_MODEL_BINDING = "modelBinding"; //$NON-NLS-1$

		private static final String A_LANGUAGE = "language"; //$NON-NLS-1$

		private static final String A_MODEL = "model"; //$NON-NLS-1$


		MyRegistryReader() {
			super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_POINT);
		}

		@Override
		protected boolean readElement(IConfigurationElement element, boolean add) {
			return add ? handleAdd(element) : handleRemove(element);
		}

		private boolean handleAdd(IConfigurationElement element) {
			boolean result = true;

			if (E_MODEL_BINDING.equals(element.getName())) {
				String languageID = element.getAttribute(A_LANGUAGE);
				String modelID = element.getAttribute(A_MODEL);

				if (Strings.isNullOrEmpty(languageID)) {
					logMissingAttribute(element, A_LANGUAGE);
				} else if (Strings.isNullOrEmpty(modelID)) {
					logMissingAttribute(element, A_MODEL);
				} else {
					bind(languageID, modelID);
				}
			}

			return result;
		}

		private boolean handleRemove(IConfigurationElement element) {
			boolean result = true;

			if (E_MODEL_BINDING.equals(element.getName())) {
				String languageID = element.getAttribute(A_LANGUAGE);
				String modelID = element.getAttribute(A_MODEL);

				if (!Strings.isNullOrEmpty(languageID) && !Strings.isNullOrEmpty(modelID)) {
					unbind(languageID, modelID);
				}
			}

			return result;
		}
	}
}

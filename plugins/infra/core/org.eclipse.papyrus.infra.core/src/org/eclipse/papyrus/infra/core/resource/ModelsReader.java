/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 429242
 *  Christian W. Damus - bug 496299
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.resource;

import static org.eclipse.papyrus.infra.core.Activator.log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.extension.ExtensionException;
import org.eclipse.papyrus.infra.core.extension.ExtensionUtils;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;

import com.google.common.collect.Sets;

/**
 * A reader to read model from Eclipse extension and register them to the
 * specified ModelManager.
 *
 * @author cedric dumoulin
 *
 */
public class ModelsReader extends ExtensionUtils {

	/** name for the element "loadAfter" */
	public static final String LOAD_AFTER_ELEMENT_NAME = "loadAfter";

	/** name for the element "unloadBefore" */
	public static final String UNLOAD_BEFORE_ELEMENT_NAME = "unloadBefore";

	/** name for the element "dependency" */
	public static final String DEPENDENCY_ELEMENT_NAME = "dependency";

	/**
	 * Name of the extension (as declared in 'plugin.xml->extension
	 * point->xxx->ID')
	 */
	public static final String EXTENSION_POINT_NAME = "model";

	/** Name for the element "model" */
	public static final String MODEL_ELEMENT_NAME = "model";

	/** Name for the element "modelSnippet" */
	public static final String MODEL_SNIPPET_ELEMENT_NAME = "modelSnippet";

	/** Name for the element "modelSetSnippet" */
	public static final String MODEL_SET_SNIPPET_ELEMENT_NAME = "modelSetSnippet";

	/**
	 * Name of attribute used to set class name.
	 */
	private static final String CLASSNAME_ATTRIBUTE = "classname";

	/** name of the attribute "identifier" */
	public static final String IDENTIFIER_ATTRIBUTE_NAME = "identifier";

	/** Name of the attribute indicating the model's canonical file extension. */
	private static final String EXTENSION_ATTRIBUTE = "fileExtension"; //$NON-NLS-1$

	/** Name of the attribute indicating whether the model is required to be available. */
	private static final String REQUIRED_ATTRIBUTE = "required"; //$NON-NLS-1$

	/** Namespace where to look for the extension points. */
	protected String extensionPointNamespace;

	/**
	 * Create a ModelReader reading extension from the core namespace.
	 *
	 * @param extensionPointNamespace
	 */
	public ModelsReader() {
		// Use this plugin namespace.
		this(Activator.PLUGIN_ID);
	}

	/**
	 * Create a ModelReader reading extension from the specified namespace. The
	 * namespace is usually the name of the plugin owning the {@link ModelSet}.
	 *
	 * @param extensionPointNamespace
	 */
	public ModelsReader(String extensionPointNamespace) {
		super();
		this.extensionPointNamespace = extensionPointNamespace;
	}

	/**
	 * Populate the manager with models found in Eclipse extensions.
	 *
	 * @param modelSet
	 */
	public void readModel(ModelSet modelSet) {
		// Actually, we register model manually.
		// TODO: read from Eclipse extension.
		// modelSet.registerModel( new SashModel() );
		// modelSet.registerModel( new NotationModel() );
		// // uml model
		// UmlModel umlModel = new ExtendedUmlModel();
		// umlModel.addModelSnippet(new UmlSnippet());
		// modelSet.registerModel(umlModel);
		// // global snippets
		// modelSet.addModelSetSnippet(new TypeCacheInitializer());
		// Reading data from plugins
		IConfigurationElement[] configElements = getExtensions();
		addDeclaredModels(configElements, modelSet);
		addDeclaredModelSetSnippets(configElements, modelSet);
	}

	private IConfigurationElement[] getExtensions() {
		return Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointNamespace, EXTENSION_POINT_NAME);
	}

	/**
	 * Queries whether there is a registered model correlating to the specified URI.
	 *
	 * @param uri
	 *            a resource URI (including the file extension, if there is one)
	 *
	 * @return {@code true} if the URI has a file extension and that extension is associated with any registered {@link IModel}; {@code false},
	 *         otherwise
	 */
	public boolean hasAssociatedModel(URI uri) {
		boolean result = false;

		String extension = uri.fileExtension();
		if (extension != null) {
			IConfigurationElement[] configs = getExtensions();
			for (int i = 0; !result && (i < configs.length); i++) {
				String modelExtension = configs[i].getAttribute(EXTENSION_ATTRIBUTE);
				result = (modelExtension != null) && modelExtension.equals(extension);
			}
		}

		return result;
	}

	/**
	 * Queries the collection of distinct resource URIs that are recognized by Papyrus as model resources, based on the specified prototype.
	 *
	 * @param prototypeURI
	 *            an example of a URI of a component resource of a Papyrus model; it may be but is not required to be a *.di URI, but it must have a file
	 *            extension
	 *
	 * @return the collection of known model resource URIs that are related to the given prototype
	 */
	public Collection<URI> getKnownModelURIs(URI prototypeURI) {
		Set<URI> result = Sets.newHashSet();

		final URI uriWithoutExtension = prototypeURI.trimFileExtension();

		IConfigurationElement[] configs = getExtensions();
		for (int i = 0; i < configs.length; i++) {
			String modelExtension = configs[i].getAttribute(EXTENSION_ATTRIBUTE);
			if (modelExtension != null) {
				result.add(uriWithoutExtension.appendFileExtension(modelExtension));
			}
		}

		return result;
	}

	/**
	 * Read and instanciate declared models
	 *
	 * @param modelSet
	 */
	private void addDeclaredModels(IConfigurationElement[] configElements, ModelSet modelSet) {
		for (IConfigurationElement ele : configElements) {
			// Check if it is a Model
			try {
				if (MODEL_ELEMENT_NAME.equals(ele.getName())) {
					IModel model = instanciateModel(ele);

					// Register the model
					AbstractModel previous = TypeUtils.as(modelSet.getModel(model.getIdentifier()), AbstractModel.class);
					modelSet.registerModel(model);

					// We may be contributing to another model already registered
					// under the same ID
					model = modelSet.getModel(model.getIdentifier());
					if ((previous != null) && (previous != model)) {
						inherit(model, previous);
					}

					addDeclaredModelSnippet(ele, model);
					addDeclaredDependencies(ele, model);
					log.debug("model loaded: '" + model.getClass().getName() + "'");
				}
			} catch (ExtensionException e) {
				log.error("Problems occur while instanciating model", e);
			}
		}
	}

	/**
	 * Let a {@code special} model inherit the snippets and dependencies from a
	 * more {@code general} model that it replaces in the model-set.
	 * 
	 * @param special
	 *            a specializing model
	 * @param general
	 *            a more generalized model that it replaces
	 */
	private void inherit(IModel special, AbstractModel general) {
		general.snippets.forEach(special::addModelSnippet);
		special.setAfterLoadModelDependencies(general.getAfterLoadModelIdentifiers());
		special.setBeforeUnloadDependencies(general.getUnloadBeforeModelIdentifiers());
	}

	/**
	 * Add ModelSet snippet
	 *
	 * @param modelSet
	 */
	private void addDeclaredModelSetSnippets(IConfigurationElement[] configElements, ModelSet modelSet) {
		for (IConfigurationElement ele : configElements) {
			// Check if it is a Model
			try {
				if (MODEL_SET_SNIPPET_ELEMENT_NAME.equals(ele.getName())) {
					IModelSetSnippet snippet = instanciateModelSetSnippet(ele);
					modelSet.addModelSetSnippet(snippet);
					log.debug("modelSet snippet added: '" + modelSet.getClass().getName() + "().add(" + snippet.getClass().getName() + ")'");
				}
			} catch (ExtensionException e) {
				log.error("Problems occur while instanciating snippet", e);
			}
		}
	}

	/**
	 * Instanciate model declared in the configuration element.
	 *
	 * @param ele
	 * @return
	 * @throws ExtensionException
	 */
	private IModel instanciateModel(IConfigurationElement ele) throws ExtensionException {
		@SuppressWarnings("unchecked")
		Class<IModel> modelInstance = (Class<IModel>) parseClass(ele, CLASSNAME_ATTRIBUTE, MODEL_ELEMENT_NAME);
		IModel model;
		try {
			model = modelInstance.newInstance();
		} catch (InstantiationException e) {
			throw new ExtensionException(e);
		} catch (IllegalAccessException e) {
			throw new ExtensionException(e);
		}
		return model;
	}

	/**
	 * Instanciate model snippet declared in the configuration element.
	 *
	 * @param ele
	 * @return
	 * @throws ExtensionException
	 */
	private IModelSnippet instanciateModelSnippet(IConfigurationElement ele) throws ExtensionException {
		@SuppressWarnings("unchecked")
		Class<IModelSnippet> modelInstance = (Class<IModelSnippet>) parseClass(ele, CLASSNAME_ATTRIBUTE, MODEL_SNIPPET_ELEMENT_NAME);
		IModelSnippet snippet;
		try {
			snippet = modelInstance.newInstance();
		} catch (InstantiationException e) {
			throw new ExtensionException(e);
		} catch (IllegalAccessException e) {
			throw new ExtensionException(e);
		}
		return snippet;
	}

	/**
	 * Instanciate modelSet snippet declared in the configuration element.
	 *
	 * @param ele
	 * @return
	 * @throws ExtensionException
	 */
	private IModelSetSnippet instanciateModelSetSnippet(IConfigurationElement ele) throws ExtensionException {
		@SuppressWarnings("unchecked")
		Class<IModelSetSnippet> modelInstance = (Class<IModelSetSnippet>) parseClass(ele, CLASSNAME_ATTRIBUTE, MODEL_SET_SNIPPET_ELEMENT_NAME);
		IModelSetSnippet snippet;
		try {
			snippet = modelInstance.newInstance();
		} catch (InstantiationException e) {
			throw new ExtensionException(e);
		} catch (IllegalAccessException e) {
			throw new ExtensionException(e);
		}
		return snippet;
	}

	/**
	 * Add associated snippets to the model.
	 *
	 * @param parentElement
	 * @param model
	 */
	private void addDeclaredModelSnippet(IConfigurationElement parentElement, IModel model) {
		// Get children
		IConfigurationElement[] configElements = parentElement.getChildren(MODEL_SNIPPET_ELEMENT_NAME);
		for (IConfigurationElement ele : configElements) {
			try {
				IModelSnippet snippet = instanciateModelSnippet(ele);
				model.addModelSnippet(snippet);
				log.debug("model snippet added: '" + model.getClass().getName() + "().add(" + snippet.getClass().getName() + ")'");
			} catch (ExtensionException e) {
				log.error("Problems occur while instanciating model snippet", e);
			}
		}
	}

	/**
	 * Sets the declared dependencies on the Model
	 *
	 * @param modelConfigurationElement
	 *            the configuration element of the model
	 * @param model
	 *            the Model to configure
	 */
	protected void addDeclaredDependencies(IConfigurationElement modelConfigurationElement, IModel model) {
		// Get children
		IConfigurationElement[] dependencyElements = modelConfigurationElement.getChildren(DEPENDENCY_ELEMENT_NAME);

		// Ordering is important, obviously, but we mustn't have duplicates
		LinkedHashSet<String> afterLoadModelIdentifiers = null;
		LinkedHashSet<String> unloadBeforeModelIdentifiers = null;

		for (IConfigurationElement dependencyElement : dependencyElements) {
			// init load after and unloadBefore
			IConfigurationElement[] loadAfterElements = dependencyElement.getChildren(LOAD_AFTER_ELEMENT_NAME);
			IConfigurationElement[] unloadBeforeElements = dependencyElement.getChildren(UNLOAD_BEFORE_ELEMENT_NAME);

			for (IConfigurationElement loadAfterElement : loadAfterElements) {
				String identifier = loadAfterElement.getAttribute(IDENTIFIER_ATTRIBUTE_NAME);
				if (identifier != null && identifier.length() > 0) {
					if (afterLoadModelIdentifiers == null) {
						afterLoadModelIdentifiers = new LinkedHashSet<>();
						List<String> existing = model.getAfterLoadModelIdentifiers();
						if (existing != null) {
							afterLoadModelIdentifiers.addAll(existing);
						}
					}
					afterLoadModelIdentifiers.add(identifier);
				}
			}

			for (IConfigurationElement unloadBeforeElement : unloadBeforeElements) {
				String identifier = unloadBeforeElement.getAttribute(IDENTIFIER_ATTRIBUTE_NAME);
				if (identifier != null && identifier.length() > 0) {
					if (unloadBeforeModelIdentifiers == null) {
						unloadBeforeModelIdentifiers = new LinkedHashSet<>();
						List<String> existing = model.getUnloadBeforeModelIdentifiers();
						if (existing != null) {
							unloadBeforeModelIdentifiers.addAll(existing);
						}
					}
					unloadBeforeModelIdentifiers.add(identifier);
				}
			}
		}

		// all config elements have been parsed. sets the dependencies in the model
		if (afterLoadModelIdentifiers != null) {
			model.setAfterLoadModelDependencies(new ArrayList<>(afterLoadModelIdentifiers));
		}
		if (unloadBeforeModelIdentifiers != null) {
			model.setBeforeUnloadDependencies(new ArrayList<>(unloadBeforeModelIdentifiers));
		}
	}

	/**
	 * Queries the models that are required in their model-set.
	 * 
	 * @param modelSet
	 *            a model-set
	 * @return ones that are required
	 * @since 2.0
	 */
	public Set<IModel> getRequiredModels(ModelSet modelSet) {
		return getRequiredModels(modelSet, IModel.class);
	}

	/**
	 * Queries the models that are required in their model-set.
	 * 
	 * @param modelSet
	 *            a model-set
	 * @param modelType
	 *            the specific type of models to request
	 * @return ones that are required
	 * @since 2.0
	 */
	public <M extends IModel> Set<M> getRequiredModels(ModelSet modelSet, Class<M> modelType) {
		Set<String> requiredModelClasses = Stream.of(getExtensions())
				.filter(c -> Boolean.parseBoolean(c.getAttribute(REQUIRED_ATTRIBUTE)))
				.map(c -> c.getAttribute(CLASSNAME_ATTRIBUTE))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		return modelSet.models.values().stream()
				.filter(modelType::isInstance)
				.map(modelType::cast)
				.filter(instanceOfAny(requiredModelClasses))
				.collect(Collectors.toSet());
	}

	private static Predicate<IModel> instanceOfAny(Set<String> classNames) {
		return model -> {
			boolean result = false;

			// We don't have to worry about interfaces because the extension point
			// identifies instantiable classes only
			for (Class<?> type = model.getClass(); !result && (type != null); type = type.getSuperclass()) {
				result = classNames.contains(type.getName());
			}

			return result;
		};
	}
}

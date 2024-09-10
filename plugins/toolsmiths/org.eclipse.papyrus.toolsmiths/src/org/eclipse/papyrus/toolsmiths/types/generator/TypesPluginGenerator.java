/*****************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Camille Letavernier - Bug 569354
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.types.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.eclipse.project.editors.project.PluginEditor;
import org.eclipse.papyrus.toolsmiths.Activator;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Version;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>
 * Generates the plug-in metadata from an *.elementtypesconfiguration model.
 * </p>
 *
 * <p>
 * Supported artifacts:
 * <ul>
 * <li>build.properties</li>
 * <li>plugin.xml</li>
 * <li>MANIFEST.MF</li>
 * </ul>
 * </p>
 */
public class TypesPluginGenerator {

	public static final String TYPES_CORE_PLUGIN = "org.eclipse.papyrus.infra.types.core";

	// Element Type Extension Point
	private static final String EXTENSION_POINT = "org.eclipse.papyrus.infra.types.core.elementTypeSetConfiguration";
	private static final String ELEMENT_TYPE_SET = "elementTypeSet";
	private static final String CLIENT_CONTEXT_ID = "clientContextID";
	private static final String PATH = "path";

	/**
	 * <p>
	 * Configure the plug-ins owning the target models (e.g. Add necessary dependencies
	 * to Papyrus ElementTypes framework, as well a required ElementType plug-in dependencies,
	 * ensure build.properties contains all the necessary resources...).
	 * </p>
	 *
	 * <p>
	 * For target projects that are not an Eclipse plug-in project, this method does nothing and returns a warning.
	 * </p>
	 *
	 * @param modelPaths
	 *            The collection of paths. Each path represents an elementtypeconfiguration model.
	 * @param contextId
	 *            The clientContextID for which the type models should be registered. For Papyrus, this is usually
	 *            <code>org.eclipse.papyrus.infra.services.edit.TypeContext</code>, but this may vary in some
	 *            specific applications / customization scenarios.
	 * @param generateExtensionPoint
	 *            Whether the element types configurations extension points should be created (or updated) to reference the model.
	 *            This is optional, as element types configurations models may be contributed via the architecture model instead.
	 * @return
	 *         The status representing the result of this configuration operation.
	 */
	public IStatus generate(Collection<IPath> elementTypesConfigurationModels, String contextId, boolean generateExtensionPoint) {
		Map<IProject, Collection<IPath>> paths = groupByProject(elementTypesConfigurationModels);
		for (var entry : paths.entrySet()) {
			IProject project = entry.getKey();
			IStatus configurePlugin = configurePlugin(project, entry.getValue(), contextId, generateExtensionPoint);
		}
		return Status.OK_STATUS;
	}

	/**
	 * <p>
	 * Configure the plug-in owning the target model (e.g. Add necessary dependencies
	 * to Papyrus ElementTypes framework, as well a required ElementType plug-in dependencies,
	 * ensure build.properties contains all the necessary resources...).
	 * </p>
	 *
	 * <p>
	 * If the target project is not an Eclipse plug-in project, this method does nothing and returns a warning.
	 * </p>
	 *
	 * @param project
	 *            The project containing the models
	 * @param modelPaths
	 *            The collection of paths. Each path represents an elementtypeconfiguration model
	 * @param contextId
	 *            The clientContextID for which the type models should be registered. For Papyrus, this is usually
	 *            <code>org.eclipse.papyrus.infra.services.edit.TypeContext</code>, but this may vary in some
	 *            specific applications / customization scenarios.
	 * @param generateExtensionPoint
	 *            Whether the element types configurations extension points should be created (or updated) to reference the model.
	 *            This is optional, as element types configurations models may be contributed via the architecture model instead.
	 * @return
	 *         The status representing the result of this configuration operation.
	 */
	protected IStatus configurePlugin(IProject project, Collection<IPath> modelPaths, String contextId, boolean generateExtensionPoint) {
		// Should always be a workspace project; but let's make sure
		if (!project.exists()) {
			return new Status(IStatus.WARNING, getClass(), "The target model is not located in a workspace project; cannot configure the plug-in.");
		}

		if (!project.isOpen()) {
			try {
				project.open(null);
			} catch (CoreException e) {
				Activator.log.error(e);
				return new Status(IStatus.WARNING, getClass(), "The target model is located in a workspace project that couldn't be opened; impossible to configure the plug-in.");
			}
		}

		try {
			PluginEditor editor = new PluginEditor(project);
			editor.init();

			if (!editor.exists()) {
				return new Status(IStatus.WARNING, getClass(), "The target model is not located in an Eclipse Plug-in Project; impossible to configure the plug-in.");
			}

			addDependencies(editor);
			if (generateExtensionPoint) {
				addExtensions(editor, modelPaths, contextId);
			}
			for (IPath path : modelPaths) {
				addBuildProperties(editor, path);
			}

			editor.save();
		} catch (Exception ex) {
			return new Status(IStatus.WARNING, getClass(), "The target model is not located in an Eclipse Plug-in Project; impossible to configure the plug-in.", ex);
		}

		return Status.OK_STATUS;
	}

	/**
	 * Add required plug-in dependencies to the target plug-in
	 *
	 * @param editor
	 *            The plug-in to configure
	 */
	protected void addDependencies(PluginEditor editor) {
		if (!editor.hasDependency(TYPES_CORE_PLUGIN)) {
			Version currentVersion = getTypesCorePluginVersion();
			editor.addDependency(TYPES_CORE_PLUGIN, String.format("[%s, %s)", getTypesCorePluginMin(currentVersion), getTypesCorePluginMax(currentVersion)));
		}
		// TODO Find and Add dependencies to plug-ins contributing the referenced models (e.g. UML, extended profiles)
	}

	private Version getTypesCorePluginVersion() {
		IPluginModelBase[] pluginModels = PluginRegistry.findModels(TYPES_CORE_PLUGIN, null, null);
		for (IPluginModelBase next : pluginModels) {
			if (next.getBundleDescription() != null) {
				return next.getBundleDescription().getVersion();
			}
		}
		return null;
	}

	private String getTypesCorePluginMin(Version currentVersion) {
		return new Version(currentVersion.getMajor(), currentVersion.getMinor(), 0).toString();
	}

	private String getTypesCorePluginMax(Version currentVersion) {
		return new Version(currentVersion.getMajor() + 1, 0, 0).toString();
	}

	/**
	 * Add required contributions to extension points in the target plug-in,
	 * for the given element types model.
	 *
	 * @param editor
	 *            The plug-in to configure.
	 * @param modelPaths
	 *            The models to add to the extensions.
	 * @param contextId
	 *            The clientContextID for which the type model should be registered.
	 */
	protected void addExtensions(PluginEditor editor, Collection<IPath> modelPaths, String contextId) {
		if (!editor.pluginManifestExists()) {
			editor.getPluginEditor().create();
		}
		editor.addToBuild("plugin.xml");

		// Identify the missing paths. Convert the IPath to the String-format used in the extension point
		Set<String> missingPaths = modelPaths.stream() //
				.map(this::toLocalProjectPath) //
				.map(IPath::toString) //
				.collect(Collectors.toSet());

		// Check if the extension(s) already exist(s).
		List<Node> extensions = editor.getExtensions(EXTENSION_POINT);
		for (Node extension : extensions) {
			NodeList childNodes = extension.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node childNode = childNodes.item(i);
				if (childNode.getNodeType() == Node.ELEMENT_NODE && ELEMENT_TYPE_SET.equals(childNode.getNodeName())) {
					Element childElement = (Element) childNode;
					if (contextId.equals(childElement.getAttribute(CLIENT_CONTEXT_ID)) && childElement.hasAttribute(PATH)) {
						missingPaths.remove(childElement.getAttribute(PATH));
					}
				}
			}
		}

		for (String modelPath : missingPaths) {
			Element ext = editor.addExtension(EXTENSION_POINT);
			Element elementTypeSet = editor.addChild(ext, ELEMENT_TYPE_SET);
			elementTypeSet.setAttribute(PATH, modelPath);
			elementTypeSet.setAttribute(CLIENT_CONTEXT_ID, contextId);
		}
	}

	/**
	 * Add required build.properties entries in the target plug-in, for the
	 * model being generated.
	 *
	 * @param editor
	 *            The plug-in to configure
	 * @param modelPath
	 *            The element types model path
	 */
	protected void addBuildProperties(PluginEditor editor, IPath modelPath) {
		IPath path = toLocalProjectPath(modelPath);

		// Trim one segment to exclude the file name (we always export the parent folder),
		// and convert to a workspace-relative path
		path = path.removeLastSegments(1);

		// Add trailing / because we export a folder
		editor.addToBuild(path.toString() + "/");
	}


	/**
	 * <p>
	 * Converts a workspace {@link IPath} to a path relative
	 * to the current project.
	 * </p>
	 * <p>
	 * The <code>/projectName/folderName/fileName.fileExtension</code> {@link IPath}
	 * becomes <code>folderName/fileName.fileExtension</code>.
	 * </p>
	 *
	 * @param modelPath
	 *            The IPath to convert to a local project path
	 * @return
	 *         The {@link IPath} relative to the Project Root, corresponding to this URI
	 */
	protected IPath toLocalProjectPath(IPath modelPath) {
		// Remove the project name, as we export paths relative to the project folder
		IPath path = modelPath.removeFirstSegments(1);

		return path;
	}

	private Map<IProject, Collection<IPath>> groupByProject(Collection<IPath> elementTypesConfigurationModels) {
		Map<IProject, Collection<IPath>> result = new HashMap<>();

		for (IPath configPath : elementTypesConfigurationModels) {
			result.computeIfAbsent(getProject(configPath), project -> new ArrayList<>()).add(configPath);
		}

		return result;
	}

	private IProject getProject(IPath path) {
		if (path.segmentCount() == 0) {
			return null;
		}
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.getProject(path.segment(0));
	}
}

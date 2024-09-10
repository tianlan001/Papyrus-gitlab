/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Christian W. Damus (CEA LIST) - Replace workspace IResource dependency with URI for CDO compatibility
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.search.scope;

import static org.eclipse.papyrus.views.search.scope.WorkspaceScopeProvider.findPapyrusModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.emf.utils.BusinessModelResolver;
import org.eclipse.papyrus.infra.onefile.model.IPapyrusFile;
//import org.eclipse.papyrus.uml.stereotypecollector.Messages;
//import org.eclipse.papyrus.uml.stereotypecollector.UMLResourceVisitor;
import org.eclipse.papyrus.views.search.Activator;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.ui.IWorkingSet;

public class ScopeCollector implements IScopeCollector {

	private static ScopeCollector instance = new ScopeCollector();

	private final Iterable<? extends IScopeProvider> scopeProviders;

	private ScopeCollector() {
		super();

		scopeProviders = loadScopeProviders();
	}

	public static final ScopeCollector getInstance() {



		synchronized (ScopeCollector.class) {
			if (ScopeCollector.instance == null) {
				ScopeCollector.instance = new ScopeCollector();

			}
		}
		return ScopeCollector.instance;
	}

	/**
	 * @see org.eclipse.papyrus.views.search.scope.IScopeCollector#computeSearchScope(org.eclipse.search.ui.ISearchPageContainer)
	 *
	 * @param container
	 * @return
	 */
	public Collection<URI> computeSearchScope(ISearchPageContainer container) {

		Set<URI> results = new HashSet<URI>();

		if (container == null) {
			results.addAll(createWorkspaceScope());

		} else {
			switch (container.getSelectedScope()) {
			case ISearchPageContainer.WORKSPACE_SCOPE: {
				results.addAll(createWorkspaceScope());
				break;
			}
			case ISearchPageContainer.SELECTION_SCOPE: {
				ISelection selection = container.getSelection();

				if (!selection.isEmpty()) {
					if (selection instanceof IStructuredSelection) {
						results.addAll(createSelectionScope((IStructuredSelection) selection));
					} else {
						// Do a workspace search instead
						results.addAll(createWorkspaceScope());
					}
				} else {
					// Do a workspace search instead
					results.addAll(createWorkspaceScope());
				}
				break;
			}
			case ISearchPageContainer.SELECTED_PROJECTS_SCOPE: {
				String[] projects = container.getSelectedProjectNames();
				results.addAll(createProjectsScope(projects));
				break;
			}
			case ISearchPageContainer.WORKING_SET_SCOPE: {
				IWorkingSet[] workingSets = container.getSelectedWorkingSets();
				results.addAll(createWorkingSetsScope(workingSets));
				break;
			}
			default: {
				break;
			}
			}
		}

		return results;

	}

	/**
	 * Create a scope when the container is ISearchPageContainer.SELECTION_SCOPE
	 *
	 * @param selection
	 *            the selection of the container
	 * @return
	 *         the scope
	 */
	// Old version, to keep?
	protected List<URI> createSelectionScope(IStructuredSelection selection) {
		List<URI> results = new ArrayList<URI>();

		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object next = it.next();

			if (!(next instanceof IPapyrusFile)) {
				if (next instanceof IContainer) { // Folder, project, etc...
					IContainer project = (IContainer) next;
					ArrayList<URI> diFiles = new ArrayList<URI>();
					IPath path = project.getLocation();
					IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

					recursiveFindDiFiles(diFiles, path, workspaceRoot);
					results.addAll(diFiles);
				} else { // Not a container, so it is some other kind of resource (e.g. file, graphical element)
					Object element = BusinessModelResolver.getInstance().getBusinessModel(next);
					if (element instanceof EObject) {
						// CDO resource *are* EObjects
						Resource eResource = (element instanceof Resource) ? (Resource) element : ((EObject) element).eResource();
						if (eResource != null) {
							// TODO This is a quick fix. Find a more robust solution.
							URI theURI = null;
							if (eResource.getURI().fileExtension().equalsIgnoreCase("uml")) {
								theURI = eResource.getURI().trimFileExtension();
								theURI = theURI.appendFileExtension("di");
							} else {
								theURI = eResource.getURI();
							}
							
							results.add(theURI);
						} else {
							// Do a workspace search instead
							results.addAll(createWorkspaceScope());
						}

					} else {
						// Do a workspace search instead
						results.addAll(createWorkspaceScope());
					}
				}
			} else {
				for (IScopeProvider provider : getScopeProviders()) {
					Collection<URI> scope = provider.getScope(next);
					if (!scope.isEmpty()) {
						results.addAll(scope);

						// don't consult the next provider
						break;
					}
				}
			}
		}

		if (results.isEmpty()) {
			// search the workspace instead, then
			results.addAll(createWorkspaceScope());
		}

		return results;
	}
	
	protected void recursiveFindDiFiles(ArrayList<URI> diFiles, IPath path, IWorkspaceRoot workspaceRoot) {
		IContainer container =  workspaceRoot.getContainerForLocation(path);

		try {
			IResource[] iResources;
			iResources = container.members();
			
			for (IResource iResource : iResources) {
				IFile iFile = (IFile) iResource.getAdapter(IFile.class);

				if (iFile != null) {
					if ("di".equalsIgnoreCase(iResource.getFileExtension())) {
						URI theURI = URI.createPlatformResourceURI(iResource.getFullPath().toString(), true);
						diFiles.add(theURI);
					}
				} else {
					IPath tmpPath = iResource.getLocation();
					recursiveFindDiFiles(diFiles, tmpPath, workspaceRoot);
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a scope when the container is ISearchPageContainer.SELECTED_PROJECTS_SCOPE
	 *
	 * @param projects
	 *            the selected scope
	 * @return
	 *         the scope
	 */
	protected List<URI> createProjectsScope(String[] projects) {
		List<URI> results = new ArrayList<URI>();

		for (String projectName : projects) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if (project.isOpen()) {
				results.addAll(findPapyrusModels(project));
			}
		}
		return results;
	}

	/**
	 * Create a scope when the container is ISearchPageContainer.WORKING_SET_SCOPE
	 *
	 * @param workingSets
	 *            the selected workingSets
	 * @return
	 *         the scope
	 */
	protected List<URI> createWorkingSetsScope(IWorkingSet[] workingSets) {
		List<URI> results = new ArrayList<URI>();

		if (workingSets != null && workingSets.length > 0) {
			for (IWorkingSet iWorkingSet : workingSets) {
				for (IAdaptable element : iWorkingSet.getElements()) {
					Object resource = element.getAdapter(IResource.class);
					if (resource instanceof IResource) {
						results.addAll(findPapyrusModels((IResource) resource));
					}
				}
			}
		}

		return results;
	}

	/**
	 * Create a scope when the container is ISearchPageContainer.WORKSPACE_SCOPE
	 *
	 * @return
	 *         the scope
	 */
	protected Collection<URI> createWorkspaceScope() {
		Collection<URI> result = new ArrayList<URI>();

		for (IScopeProvider next : getScopeProviders()) {
			result.addAll(next.getScope());
		}

		return result;
	}

	private Iterable<? extends IScopeProvider> loadScopeProviders() {
		return new ProvidersReader().load();
	}

	final Iterable<? extends IScopeProvider> getScopeProviders() {
		List<IScopeProvider> result = new ArrayList<IScopeProvider>();

		synchronized (scopeProviders) {
			for (IScopeProvider next : scopeProviders) {
				result.add(next);
			}
		}

		return result;
	}

	//
	// Nested types
	//

	private static class PriorityScopeProvider implements IScopeProvider, Comparable<PriorityScopeProvider> {
		private final IScopeProvider delegate;

		private final int priority;

		public PriorityScopeProvider(IScopeProvider delegate, int priority) {
			this.delegate = delegate;
			this.priority = priority;
		}

		public int compareTo(PriorityScopeProvider o) {
			// sort by descending priority
			return o.priority - this.priority;
		}

		@Override
		public int hashCode() {
			return delegate.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return (obj instanceof PriorityScopeProvider) && ((PriorityScopeProvider) obj).delegate.equals(delegate);
		}

		//
		// API delegation
		//

		public Collection<URI> getScope() {
			return delegate.getScope();
		}

		public Collection<URI> getScope(Object selected) {
			return delegate.getScope(selected);
		}
	}

	private class ProvidersReader extends RegistryReader {
		private static final String EXT_PT = "scopeProviders"; //$NON-NLS-1$

		private static final String TAG_PROVIDER = "scopeProvider"; //$NON-NLS-1$

		private static final String ATTR_CLASS = "class"; //$NON-NLS-1$

		private static final String ATTR_PRIORITY = "priority"; //$NON-NLS-1$

		private final SortedSet<PriorityScopeProvider> providers = new java.util.TreeSet<PriorityScopeProvider>();

		ProvidersReader() {
			super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_PT);
		}

		Iterable<? extends IScopeProvider> load() {
			synchronized (providers) {
				providers.clear();
				readRegistry();
			}

			return providers;
		}

		@Override
		protected boolean readElement(IConfigurationElement element, boolean add) {
			boolean result = false;

			if (TAG_PROVIDER.equals(element.getName())) {
				result = true;

				String className = element.getAttribute(ATTR_CLASS);
				if ((className == null) || (className.length() == 0)) {
					logMissingAttribute(element, ATTR_CLASS);
				} else if (add) {
					addProvider(element, className);
				} else {
					removeProvider(element, className);
				}
			}

			return result;
		}

		private void addProvider(IConfigurationElement element, String className) {
			try {
				Object provider = element.createExecutableExtension(ATTR_CLASS);

				if (!(provider instanceof IScopeProvider)) {
					Activator.log.error("Scope provider extension does not implement IScopeProvider interface: " + className, null); //$NON-NLS-1$
				} else {
					String priorityString = element.getAttribute(ATTR_PRIORITY);
					int priority = 0;

					try {
						if ((priorityString) != null && (priorityString.length() > 0)) {
							priority = Integer.parseInt(priorityString);
							if (priority < 0) {
								Activator.log.warn("Negative priority in scope provider " + className); //$NON-NLS-1$
								priority = 0;
							}
						}
					} catch (NumberFormatException e) {
						Activator.log.warn("Not an integer priority in scope provider " + className); //$NON-NLS-1$
					}

					synchronized (providers) {
						providers.add(new PriorityScopeProvider((IScopeProvider) provider, priority));
					}
				}
			} catch (CoreException e) {
				Activator.getDefault().getLog().log(e.getStatus());
			}
		}

		private void removeProvider(IConfigurationElement element, String className) {
			synchronized (providers) {
				for (Iterator<PriorityScopeProvider> iter = providers.iterator(); iter.hasNext();) {
					if (iter.next().delegate.getClass().getName().equals(className)) {
						iter.remove();
					}
				}
			}
		}
	}
}

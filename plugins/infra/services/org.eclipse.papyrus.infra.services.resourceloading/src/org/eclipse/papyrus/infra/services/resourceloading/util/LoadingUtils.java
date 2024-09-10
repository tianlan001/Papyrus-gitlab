/*****************************************************************************
 * Copyright (c) 2011, 2016 Atos Origin, CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Hemery (Atos) vincent.hemery@atos.net - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 415639
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.util;

import static org.eclipse.papyrus.infra.core.utils.TransactionHelper.createPrivilegedRunnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.resource.AbstractBaseModel;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.IPapyrusRunnable;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.infra.services.resourceloading.Activator;
import org.eclipse.papyrus.infra.services.resourceloading.Messages;
import org.eclipse.papyrus.infra.tools.notify.NotificationBuilder;
import org.eclipse.papyrus.infra.tools.notify.Type;
import org.eclipse.papyrus.infra.tools.util.CoreExecutors;
import org.eclipse.papyrus.infra.tools.util.IProgressRunnable;

/**
 * This class provides utility methods for model loading
 *
 * @author vhemery
 */
public class LoadingUtils {

	/**
	 * Load corresponding resources in model set for all its existing models.
	 *
	 * @param modelSet
	 *            the model set
	 * @param uriWithoutFileExtension
	 *            path of resources to load without file extension
	 */
	public static void loadResourcesInModelSet(final ModelSet modelSet, final URI uriWithoutFileExtension) {
		// This must be created on the UI thread
		final NotificationBuilder error = NotificationBuilder
				.createAsyncPopup(Messages.LoadingUtils_ErrorTitle, String.format(Messages.LoadingUtils_ErrorMessage, uriWithoutFileExtension.toString()))
				.setType(Type.ERROR).setDelay(2000);

		runInEditingDomain(modelSet, monitor -> {
			try {
				try {
					IPageManager pageMngr = ServiceUtilsForResourceSet.getInstance().getIPageManager(modelSet);
					List<Object> allPages = pageMngr.allPages();
					// mark progress
					monitor.beginTask(Messages.LoadingUtils_RefreshPagesTask, allPages.size());
					// the uri is added after getting all the pages. If it is done before, the eobjects are resolved
					for (Object o : allPages) {
						// refresh pages to display proxy diagrams
						if (o instanceof EObject) {
							EObject eobject = (EObject) o;
							if (eobject.eIsProxy()) {
								InternalEObject internal = (InternalEObject) eobject;
								URI uriProxy = internal.eProxyURI();
								URI trimFragment = uriProxy.trimFragment();
								if (uriWithoutFileExtension.equals(trimFragment.trimFileExtension())) {
									try {
										Resource r = modelSet.getResource(trimFragment, true);
										if (r != null) {
											EObject newEObject = r.getEObject(uriProxy.fragment());
											if (pageMngr.isOpen(newEObject)) {
												pageMngr.selectPage(newEObject);
											}
										} else {
											error.run();
										}
									} catch (Exception e) {
										error.run();
										Activator.logError(e);
									}
								}
							}
						}
						// mark progress
						monitor.worked(1);
					}
					Set<String> extensions = getExtensions(modelSet);
					// mark progress
					monitor.beginTask(Messages.LoadingUtils_LoadModelsTask, extensions.size());
					for (String s : extensions) {
						try {
							URI uriToLoad = uriWithoutFileExtension.appendFileExtension(s);
							Resource r = modelSet.getResource(uriToLoad, true);
							if (r == null) {
								error.run();
							}
						} catch (Exception re) {
							error.run();
							Activator.logError(re);
						}
						// mark progress
						monitor.worked(1);
					}
				} catch (ServiceException e) {
					Activator.logError(e);
				}
			} finally {
				// mark progress
				monitor.done();
			}
		});
	}

	/**
	 * Unload corresponding resources from model set for all its existing models.
	 *
	 * @param modelSet
	 *            the model set
	 * @param uriWithoutFileExtension
	 *            path of resources to unload without file extension
	 */
	public static void unloadResourcesFromModelSet(ModelSet modelSet, URI uriWithoutFileExtension) {
		runInEditingDomain(modelSet, monitor -> {

			try {
				// mark progress
				monitor.beginTask(Messages.LoadingUtils_UnloadModelsTask, modelSet.getResources().size());

				// Use the platform string of a normalized URI for comparison below, see bug 372326
				// (registered libraries in the model set have different URIs - e.g. due to a pathmap -
				// although they point to the same location).
				// TODO: Use a single detection mechanism in ResourceUpdateService and here
				String unloadPlatformString;
				if (uriWithoutFileExtension.isPlatform()) {
					unloadPlatformString = uriWithoutFileExtension.toPlatformString(true);
				} else {
					unloadPlatformString = URI.decode(uriWithoutFileExtension.toString());
				}
				// URIConverter uriConverter = modelSet.getURIConverter();
				// unload resource
				for (Resource res : new ArrayList<Resource>(modelSet.getResources())) {
					URI normalizedURI = res.getURI();
					String platformString;
					if (normalizedURI.isPlatform()) {
						platformString = normalizedURI.trimFileExtension().toPlatformString(true);
					} else {
						platformString = URI.decode(normalizedURI.trimFileExtension().toString());
					}

					if ((platformString != null) && platformString.equals(unloadPlatformString)) {
						// unload this resource
						res.unload();
						// there is no need to remove it from the resource set (which inevitably
						// causes ConcurrentModificationExceptions!), especially as we may be
						// loading it again (the editor is still open)
					}
					// mark progress
					monitor.worked(1);
				}
				// // mark progress
				// monitor.beginTask("Resolve", 1);
				// EcoreUtil.resolveAll(modelSet);
				// monitor.worked(1);
			} finally {
				// mark progress
				monitor.done();
			}
		});
	}

	static void runInEditingDomain(ModelSet modelSet, IPapyrusRunnable runnable) {
		try {
			// Created the privileged progress-runnable that borrows the editing domain
			IProgressRunnable privileged = createPrivilegedRunnable(
					modelSet.getTransactionalEditingDomain(),
					runnable);

			// And wrap it in the service-registry context for best possible UI feed-back
			privileged = ServiceUtilsForResourceSet.getInstance().runnable(privileged, modelSet);

			// Go
			CoreExecutors.getUIExecutorService().syncExec(privileged);
		} catch (Exception e) {
			Activator.log.error(e);
		}
	}

	/**
	 * Get list of file extensions existing for this model set
	 *
	 * @param modelSet
	 *            model set to find common extensions for
	 * @return extensions list to explore
	 */
	private static Set<String> getExtensions(ModelSet modelSet) {
		Set<String> result = new HashSet<String>();
		// FIXME: Also need to generalize the notation and DI models
		result.add("notation"); //$NON-NLS-1$
		result.add(DiModel.DI_FILE_EXTENSION);

		// Get the semantic model extensions
		Collection<IModel> languageModels = ILanguageService.getLanguageModels(modelSet);
		if (languageModels.isEmpty()) {
			// No semantic models? Force UML for compatibility
			Activator.log.warn("Semantic service unavailable. Assuming a UML model."); //$NON-NLS-1$
			result.add("uml"); //$NON-NLS-1$
		} else {
			for (IModel model : languageModels) {
				if (model instanceof AbstractBaseModel) {
					URI uri = ((AbstractBaseModel) model).getResourceURI();
					if ((uri != null) && (uri.fileExtension() != null)) {
						result.add(uri.fileExtension());
					}
				}
			}
		}

		return result;
	}

	/**
	 * Get File from a URI
	 *
	 * @param uri
	 *            the URI to transform
	 * @return the corresponding file
	 */
	public static IFile getFile(URI uri) {
		IPath path = getPath(uri);
		if (path != null) {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		}
		return null;
	}

	/**
	 * Get Path from a URI
	 *
	 * @param uri
	 *            the URI to transform
	 * @return the corresponding path
	 */
	public static IPath getPath(URI uri) {
		String scheme = uri.scheme();
		IPath path = null;
		if ("platform".equals(scheme)) { //$NON-NLS-1$
			path = Path.fromPortableString(uri.toPlatformString(true));
		} else if ("file".equals(scheme)) { //$NON-NLS-1$
			path = Path.fromPortableString(uri.toFileString());
		}
		return path;
	}
}

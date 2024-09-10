/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references.actions;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelsReader;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceMultiException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.infra.services.openelement.service.OpenElementService;
import org.eclipse.papyrus.infra.services.openelement.service.impl.OpenElementServiceImpl;
import org.eclipse.papyrus.infra.ui.util.EditorUtils;
import org.eclipse.papyrus.views.references.Activator;
import org.eclipse.papyrus.views.references.Messages;
import org.eclipse.papyrus.views.references.constants.ReferencesViewConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * The action used to access to the references on the Model Explorer view or the
 * Diagram.
 */
public class GoToAction extends BaseSelectionListenerAction {

	/**
	 * Id of the view 'Model Explorer'.
	 */
	private static final String ID_VIEW_MODELEXPLORER = "org.eclipse.papyrus.views.modelexplorer.navigation.target"; //$NON-NLS-1$

	/**
	 * The Go To image descriptor.
	 */
	private static final ImageDescriptor GO_TO_IMAGE_DESCRIPTOR = Activator
			.imageDescriptorFromPlugin(ReferencesViewConstants.PLUGIN_ORG_ECLIPSE_SEARCH, ReferencesViewConstants.ICON_GO_TO);

	/**
	 * URI of the resource of the model of the reference.
	 */
	private URI diResourceUri;

	/**
	 * Registry of the services.
	 */
	private ServicesRegistry servicesRegistry;

	/**
	 * The ModelSet.
	 */
	private ModelSet modelSet;

	/**
	 * Constructor.
	 */
	public GoToAction() {
		super(Messages.ReferencesView_GoTo);
		setImageDescriptor(GO_TO_IMAGE_DESCRIPTOR);
	}

	/**
	 *
	 * @return
	 */
	private Collection<IEditorPart> getEditors() {
		final Collection<IEditorPart> results = new HashSet<>();
		final IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
		for (final IWorkbenchWindow iWorkbenchWindow : windows) {
			final IWorkbenchPage[] pages = iWorkbenchWindow.getPages();
			for (final IWorkbenchPage iWorkbenchPage : pages) {
				final IEditorReference[] references = iWorkbenchPage.getEditorReferences();
				for (final IEditorReference ref : references) {
					final IEditorPart editor = ref.getEditor(true);
					results.add(editor);
				}
			}
		}

		return results;
	}

	/**
	 *
	 * @return
	 */
	private IEditorPart editorOnResource() {
		final Collection<IEditorPart> editors = getEditors();
		for (final IEditorPart editor : editors) {
			if (null != editor) {
				if (diResourceUri.equals(EditorUtils.getResourceURI(editor))) {
					return editor;
				}
			}
		}

		return null;
	}

	/**
	 *
	 * @return
	 */
	private ServicesRegistry getUpdatedServiceRegistry() {
		if (null != diResourceUri) {
			final IEditorPart editor = editorOnResource();
			if (null != editor) {
				return editor.getAdapter(ServicesRegistry.class);
			}
		}

		return servicesRegistry;
	}

	/**
	 * Get the Services Registry.
	 *
	 * @return the ServicesRegistry.
	 */
	private ServicesRegistry getServicesRegistry() {
		if (null == servicesRegistry) {
			// Try to find existing
			final ServicesRegistry registry = getUpdatedServiceRegistry();
			if (null != registry) {
				// If the OpenElementService is no yet available, we must start
				// it
				try {
					registry.getService(OpenElementService.class);
				} catch (final ServiceException e) {
					registry.add(OpenElementService.class, 10, new OpenElementServiceImpl());
				}
				servicesRegistry = registry;
			} else {
				servicesRegistry = createServicesRegistry();
			}
		}

		return servicesRegistry;
	}

	/**
	 * Create the Services Registry and add new Services.
	 *
	 * @return The new Services Registry.
	 */
	private ServicesRegistry createServicesRegistry() {
		try {
			final ServicesRegistry serviceRegistry = new ServicesRegistry();
			serviceRegistry.add(OpenElementService.class, 10, new OpenElementServiceImpl());
			serviceRegistry.startRegistry();
			return serviceRegistry;
		} catch (final ServiceException e) {
			Activator.logError(e.getMessage());
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run() {
		final IStructuredSelection structuredSelection = getStructuredSelection();

		if (structuredSelection instanceof ITreeSelection) {
			final Object firstElement = ((ITreeSelection) structuredSelection).getFirstElement();
			if (firstElement instanceof Setting) {
				final EObject source = ((Setting) firstElement).getEObject();
				final String nameEStructuralFeature = ((Setting) firstElement).getEStructuralFeature().getName();

				if (ReferencesViewConstants.NAME_ELEMENT.equals(nameEStructuralFeature)) {
					try {
						diResourceUri = getDiResourceURI(source.eResource().getURI());
						final ResourceSet modelSet = getModelSet(diResourceUri);
						final EObject eObject = modelSet.getEObject(EcoreUtil.getURI(source), true);
						final OpenElementService service = getServicesRegistry().getService(OpenElementService.class);
						service.openElement(eObject);
					} catch (final ServiceException e) {
						Activator.logError(e.getMessage());
					} catch (final PartInitException e) {
						Activator.logError(e.getMessage());
					}

				} else {
					// Use NavigationService to navigate to ModelExplorer
					try {
						final NavigationService navigationService = ServiceUtilsForEObject.getInstance()
								.getService(NavigationService.class, source);
						navigationService.navigate(source, ID_VIEW_MODELEXPLORER);
					} catch (final ServiceException e) {
						Activator.logError(e.getMessage());
					}
				}
			}
		}
	}

	/**
	 * Modify the extension of the Uri.
	 *
	 * @param uri
	 *            The Uri to modify.
	 * @return The new Uri. Null if the file doesn't exists.
	 */
	private URI getDiResourceURI(final URI uri) {
		URI fileURI = uri.trimFileExtension().trimFragment();
		fileURI = fileURI.appendFileExtension(DiModel.MODEL_FILE_EXTENSION);
		return fileURI;
	}

	/**
	 * Get the ModelSet of the Services Registry.
	 *
	 * @param resourceUri
	 *            The resource to open.
	 * @return The ModelSet.
	 */
	private ModelSet getModelSet(final URI resourceUri) {
		// If null, try to find one or create one
		if (null == modelSet) {
			try {
				modelSet = ServiceUtils.getInstance().getModelSet(getServicesRegistry());
			} catch (final ServiceException e) {
				// Create one
				try {
					modelSet = openResource(resourceUri);
					getServicesRegistry().add(ModelSet.class, 10, modelSet);
					getServicesRegistry().startServicesByClassKeys(ModelSet.class);
				} catch (final ModelMultiException modelMultiException) {
					Activator.logError(Messages.ReferencesView_FailedToOpenModelSet + resourceUri);
				} catch (final ServiceMultiException e1) {
					Activator.logError(e1.getMessage());
				} catch (final ServiceNotFoundException e1) {
					Activator.logError(e1.getMessage());
				}
			}
		}

		return modelSet;
	}

	/**
	 * Open the resource in parameter.
	 *
	 * @param resourceURI
	 *            The resource to open.
	 * @return The modelSet of the resource.
	 * @throws ModelMultiException
	 */
	private final static ModelSet openResource(final URI resourceURI) throws ModelMultiException {
		if (null != resourceURI) {
			ModelSet modelSet = new ModelSet();
			ModelsReader reader = new ModelsReader();
			reader.readModel(modelSet);
			modelSet.loadModels(resourceURI);
			return modelSet;
		} else {
			return null;
		}
	}

}

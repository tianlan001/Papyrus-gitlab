/*****************************************************************************
 * Copyright (c) 2021, 2022 CEA LIST, Obeo and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *  Jessy MALLET (OBEO) <jessy.mallet@obeo.fr> - Bug 579694, 579782
 *
 *****************************************************************************/
package org.eclipse.papyrus.sirius.editor.internal.sessions;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.architecture.listeners.ArchitectureDescriptionAdapterUtils;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.sirius.editor.Activator;
import org.eclipse.papyrus.sirius.editor.internal.listeners.SiriusArchitectureDescriptionAdapter;
import org.eclipse.papyrus.sirius.editor.internal.runnables.RegisterSemanticResourceRunnable;
import org.eclipse.papyrus.sirius.editor.internal.runnables.UpdateSiriusViewpointRunnable;
import org.eclipse.papyrus.sirius.editor.modelresource.SiriusDiagramModel;
import org.eclipse.papyrus.sirius.editor.representation.SiriusDiagramPrototype;
import org.eclipse.papyrus.sirius.editor.sirius.ISiriusSessionService;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.sirius.business.api.dialect.DialectManager;
import org.eclipse.sirius.business.api.session.DefaultLocalSessionCreationOperation;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.business.internal.session.SessionTransientAttachment;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.ui.business.api.session.IEditingSession;
import org.eclipse.sirius.ui.business.api.session.SessionUIManager;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;

/**
 * This service is in charge to create and initialize the Sirius Session when a Papyrus model is loaded.
 * The Sirius Session must be created and fully initialized just after the creation of a Papyrus model, even when the model doesn't contain a Sirius Diagram
 * 
 * The created Sirius Session is opened at the end of the process
 */
@SuppressWarnings("restriction") // suppress warning for SessionTransientAttachment
public class SessionService implements ISiriusSessionService, ISiriusSessionViewpointUpdater {

	/**
	 * The service registry associated to this current SessionService
	 */
	private ServicesRegistry servicesRegistry;

	/**
	 * the current ModelSet
	 */
	private ModelSet modelSet;

	/**
	 * the current editing domain;
	 */
	private TransactionalEditingDomain editingDomain;

	/**
	 * The created session
	 */
	private Session createdSession = null;

	/**
	 * this adapter listen the switch of Papyrus architecture Framework in order to update the available Sirius Viewpoint
	 */
	private SiriusArchitectureDescriptionAdapter architectureListener;

	/**
	 * this adapter listen the add of EObject inside the UML Resource
	 */
	private Adapter semanticResourceListener;

	/**
	 * 
	 * Constructor.
	 *
	 */
	public SessionService() {
		// nothing to do
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.services.IService#init(org.eclipse.papyrus.infra.core.services.ServicesRegistry)
	 *
	 * @param servicesRegistry
	 *            the service registry associated to the current model
	 * @throws ServiceException
	 */
	@Override
	public void init(final ServicesRegistry servicesRegistry) throws ServiceException {
		this.servicesRegistry = servicesRegistry;
		if (this.servicesRegistry == null) {
			throw new ServiceException(NLS.bind("The service {0} can't be initialized because the ServicesRegistry is not found", ISiriusSessionService.SERVICE_ID)); //$NON-NLS-1$
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.services.IService#startService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void startService() throws ServiceException {
		this.modelSet = getModelSet();
		this.editingDomain = getEditingDomain();
		if (this.modelSet == null || this.editingDomain == null) {
			throw new ServiceException(NLS.bind("The service {0} can't start.", ISiriusSessionService.SERVICE_ID)); //$NON-NLS-1$
		}

		// we need to create the session before registering adapter
		// the session must be created when Papyrus model is opened (even when there is not a Sirius Diagram inside the model
		createdSession();

		this.architectureListener = new SiriusArchitectureDescriptionAdapter(this);
		ArchitectureDescriptionAdapterUtils.registerListener(this.modelSet, this.architectureListener);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.services.IService#disposeService()
	 *
	 * @throws ServiceException
	 */
	@Override
	public void disposeService() throws ServiceException {
		if (this.architectureListener != null) {
			ArchitectureDescriptionAdapterUtils.unregisterListener(this.modelSet, this.architectureListener);
		}
		this.servicesRegistry = null;
		this.editingDomain = null;
		this.modelSet = null;
		this.createdSession = null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.sirius.editor.sirius.ISiriusSessionService#getSiriusSession()
	 *
	 * @return
	 *         the Sirius Session associated to the {@link ServicesRegistry}. This method creates it if is doesn't yet exists
	 */
	@Override
	public Session getSiriusSession() {
		if (this.createdSession == null) {
			createdSession();
		}
		return this.createdSession;
	}

	/**
	 * This method creates the Sirius session
	 */
	private void createdSession() {
		if (this.servicesRegistry == null || this.modelSet == null || this.editingDomain == null) {
			return;
		}

		// 1. create the Sirius file resource URI
		URI siriusFileResource = modelSet.getURIWithoutExtension();
		siriusFileResource = siriusFileResource.appendFileExtension(SiriusConstants.SIRIUS_DIAGRAM_MODEL_FILE_EXTENSION);

		// 2. save the notation file
		saveNotationFile(modelSet);

		// 3. create the session
		this.createdSession = createSiriusSession(siriusFileResource);

		// 4 . associate the resource to the session
		SiriusDiagramModel siriusModel = (SiriusDiagramModel) modelSet.getModel(SiriusDiagramModel.SIRIUS_DIAGRAM_MODEL_ID);
		Assert.isNotNull(siriusModel, NLS.bind("We can't find the '{0}' class.", SiriusDiagramModel.class.getName())); //$NON-NLS-1$
		siriusModel.setSiriusSession(this.createdSession);

		// 5. associate the session to the resource
		// this point requires that the resource.getContents() is not empty!
		final Resource semanticResource = this.modelSet.getResource(getSemanticResourceURI(), false);
		if (semanticResource.getContents().size() != 0) {
			//1. attach the semantic resource
			setSemanticResource();
			//2. update the Sirius viewpoint
			updateAppliedSiriusViewpoints();
			//3. open the session
			openSessions();
		} else {
			// we are creating a new UML Model, and the root element of the semantic resource has not yet been created
			// this adapter is in charge to register the semantic resource inside the session when the root element would have been added into the resource
			this.semanticResourceListener = new AdapterImpl() {

				public void notifyChanged(final Notification msg) {
					// EObject to avoid dependency on UML Element{
					if (Notification.ADD == msg.getEventType() && msg.getNewValue() instanceof EObject) {

						//1. attach the semantic resource
						setSemanticResource();
						//2. update the Sirius viewpoint
						updateAppliedSiriusViewpoints();
						//3. open the session
						openSessions();
						//4. we can remove the listener after the first notification
						semanticResource.eAdapters().remove(semanticResourceListener);
					}
				}
			};
			semanticResource.eAdapters().add(this.semanticResourceListener);
		}
	}

	/**
	 *
	 * @return
	 *         the editing domain or <code>null</code> if not found
	 */
	private final TransactionalEditingDomain getEditingDomain() {
		try {
			return ServiceUtils.getInstance().getTransactionalEditingDomain(this.servicesRegistry);
		} catch (ServiceException e) {
			Activator.log.error("EditingDomain not found", e); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the {@link ModelSet} or <code>null</code> if not found
	 */
	private final ModelSet getModelSet() {
		try {
			return this.servicesRegistry.getService(ModelSet.class);
		} catch (ServiceException e) {
			Activator.log.error("ModelSet not found", e); //$NON-NLS-1$
		}
		return null;
	}

	/**
	 * This method calls ModelSet.save() to save the notation file if it doesn't yet exist
	 *
	 * @param modelSet
	 *            the current modelSet
	 */
	private final void saveNotationFile(final ModelSet modelSet) {
		// this action is required to avoid a crash when we create a new sirius diagram from the Papyrus "create model wizard"
		// TODO to avoid this bug, we should propose a patch to Sirius to check the file exists before trying to load it? (or a try catch)
		// bug is in org.eclipse.sirius.business.api.helper.SiriusResourceHelper.getCorrespondingViewpoint(Session, Viewpoint), line 136
		// so at this line : editingDomainResource.load(Collections.EMPTY_MAP);
		URI notationURi = modelSet.getURIWithoutExtension();
		notationURi = notationURi.appendFileExtension("notation");////$NON-NLS-1$
		boolean exists = modelSet.getURIConverter().exists(notationURi, null);
		if (!exists) {
			try {
				modelSet.save(new NullProgressMonitor());
			} catch (IOException e) {
				Activator.log.error("ModelSet can't be saved", e); //$NON-NLS-1$
			}
		}
	}

	/**
	 *
	 * @param siriusResourceURI
	 *            the resource uri to use to create the Session
	 * @return
	 *         the Session or <code>null</code>
	 */
	private Session createSiriusSession(final URI siriusResourceURI) {
		final DefaultLocalSessionCreationOperation operation = new PapyrusLocalSessionCreationOperation(siriusResourceURI, new NullProgressMonitor(), this.editingDomain);
		try {
			operation.execute();
			return operation.getCreatedSession();
		} catch (CoreException e) {
			Activator.log.error(NLS.bind("The resource {0} can't be created", siriusResourceURI), e); //$NON-NLS-1$
		}
		return null;
	}



	/**
	 * @see org.eclipse.papyrus.sirius.editor.sirius.ISiriusSessionViewpointUpdater#updateAppliedViewpoint()
	 */
	@Override
	public void updateAppliedSiriusViewpoints() {
		if (getEditingDomain() == null) {
			// we are disposing the service
			return;
		}
		try {
			// required to allow to open a sirius Diagram from the ModelExplorer on double click as first action!
			// if not we get this kind of exception : java.lang.IllegalStateException: Cannot modify resource set without a write transaction
			GMFUnsafe.write(getEditingDomain(), new UpdateSiriusViewpointRunnable(this.createdSession, this.modelSet));
		} catch (InterruptedException | RollbackException e) {
			Activator.log.error("Unexpected Error",e); //$NON-NLS-1$
		}

	}


	/**
	 *
	 * @see org.eclipse.papyrus.sirius.editor.sirius.ISiriusSessionService#getSiriusDiagramDescriptionFromPapyrusPrototype(org.eclipse.papyrus.sirius.editor.representation.SiriusDiagramPrototype, org.eclipse.emf.ecore.EObject)
	 *
	 * @param siriusDiagramPrototype
	 * @param context
	 * @return
	 *         the DiagramDescription corresponding to the {@link SiriusDiagramPrototype} and registered in the current session, or <code>null</code> if not found
	 */
	@Override
	public DiagramDescription getSiriusDiagramDescriptionFromPapyrusPrototype(final SiriusDiagramPrototype siriusDiagramPrototype, final EObject context) {
		if (getSiriusSession() == null || siriusDiagramPrototype == null || siriusDiagramPrototype.getDiagramDescription() == null) {
			return null;
		}
		final URI descriptionURI = siriusDiagramPrototype.getDiagramDescription().eResource().getURI();
		if (descriptionURI == null) {
			return null;
		}
		Collection<RepresentationDescription> desc = DialectManager.INSTANCE.getAvailableRepresentationDescriptions(this.createdSession.getSelectedViewpoints(false), context);
		if (desc.isEmpty()) { // TODO required?
			updateAppliedSiriusViewpoints();
			desc = DialectManager.INSTANCE.getAvailableRepresentationDescriptions(this.createdSession.getSelectedViewpoints(false), context);
		}
		final java.util.Iterator<RepresentationDescription> iter = desc.iterator();
		while (iter.hasNext()) {
			final RepresentationDescription current = iter.next();
			if (current instanceof DiagramDescription && current.eResource() != null) {
				final URI uri = current.eResource().getURI();
				if (descriptionURI.equals(uri)) {
					return (DiagramDescription) current;
				}
			}
		}

		return null;
	}

	/**
	 * This method is used to register the semantic resource into the created Sirius Session
	 */
	private void setSemanticResource() {
		final URI semanticResourceURI = getSemanticResourceURI();
		try {
			// GMFUnsafe to avoid read-only exception (silent exception catched by Papyrus)
			GMFUnsafe.write(this.editingDomain, new RegisterSemanticResourceRunnable(this.createdSession, semanticResourceURI));
		} catch (InterruptedException | RollbackException e) {
			Activator.log.error("We are not able to register the semantic resource into the Sirius Session", e); //$NON-NLS-1$
		}
	}

	/**
	 * 
	 * @return
	 *         the URI of the semanticResource
	 */
	private URI getSemanticResourceURI() {
		// TODO : dependency on UML is not very nice...
		final UmlModel umlModel = (UmlModel) modelSet.getModel(UmlModel.MODEL_ID);
		return umlModel.getResourceURI();
	}

	/**
	 * open the Sirius Session and the Sirius UI Session
	 */
	public void openSessions() {
		if (!this.createdSession.isOpen()) {
			this.createdSession.open(new NullProgressMonitor());
		}
		if (this.createdSession != null) {
			final IEditingSession uiSession = SessionUIManager.INSTANCE.getOrCreateUISession(this.createdSession);
			if (!uiSession.isOpen()) {
				uiSession.open();
			}
		}
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.sirius.editor.sirius.ISiriusSessionService#attachSession(org.eclipse.emf.ecore.EObject)
	 *
	 * @param eobject
	 */
	public void attachSession(final EObject eobject) {
		boolean needToBeAttached = true;
		final Iterator<Adapter> iter = eobject.eAdapters().iterator();
		while (needToBeAttached && iter.hasNext()) {
			final Adapter tmp = iter.next();
			if (tmp instanceof SessionTransientAttachment && ((SessionTransientAttachment) tmp).getSession() == this.createdSession) {
				needToBeAttached = false;
			}
		}
		if (needToBeAttached) {
			eobject.eAdapters().add(new SessionTransientAttachment(this.createdSession));
		}
	}

}

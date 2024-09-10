/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr
 *  Christian W. Damus (CEA) - bug 392301
 *  Christian W. Damus - bug 474467
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.factory;

import java.lang.reflect.Constructor;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.papyrus.infra.core.editor.BackboneException;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.AbstractPageModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.common.editor.NatTableEditor;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.provider.TableLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.ui.extension.diagrameditor.AbstractEditorFactory;
import org.eclipse.papyrus.infra.ui.multidiagram.actionbarcontributor.ActionBarContributorRegistry;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * Abstract factory for the NattableEditor
 *
 *
 *
 */
public class NattableEditorFactory extends AbstractEditorFactory {

	/**
	 *
	 * Constructor.
	 *
	 * @param editorClass
	 *            the editor class
	 * @param editorType
	 *            the type of editor
	 */
	public NattableEditorFactory() {
		super(NatTableEditor.class, "");// we don't use the type for the Nattable models
	}

	/**
	 * Create the IPageModel that is used by the SashWindows to manage the editor.
	 *
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorFactory#createIPageModel(java.lang.Object)
	 *
	 * @param pageIdentifier
	 *            The model pushed in the sashmodel by the creation command
	 * @return A model implementing the IPageModel
	 */
	@Override
	public IPageModel createIPageModel(Object pageIdentifier) {
		ServicesRegistry services = getServiceRegistry();
		ILabelProvider labels = ServiceUtils.getInstance().tryService(services, LabelProviderService.class)
				.map(lps -> lps.getLabelProvider(pageIdentifier))
				.orElseGet(TableLabelProvider::new);

		return new NattableEditorModel(pageIdentifier, services, labels);
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.editorsfactory.IEditorFactory#isPageModelFactoryFor(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 */
	@Override
	public boolean isPageModelFactoryFor(Object pageIdentifier) {
		return pageIdentifier instanceof Table;
	}

	/**
	 * IEditorModel used internally by the SashContainer. This model know how to handle IEditor creation.
	 *
	 * @author cedric dumoulin
	 *
	 */
	class NattableEditorModel extends AbstractPageModel implements IEditorModel {


		/**
		 * The servicesRegistry provided at creation.
		 */
		private ServicesRegistry servicesRegistry;

		/**
		 * The created editor.
		 */
		private IEditorPart editor;

		/**
		 * The raw model stored in the SashProvider.
		 */
		private Table rawModel;

		/**
		 *
		 * Constructor.
		 */
		public NattableEditorModel(Object pageIdentifier, ServicesRegistry servicesRegistry, ILabelProvider labels) {
			super(labels);

			this.rawModel = (Table) pageIdentifier;
			this.servicesRegistry = servicesRegistry;
		}

		/**
		 * Create the IEditor for the diagram.
		 *
		 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel#createIEditorPart()
		 * @return
		 * @throws PartInitException
		 *
		 */
		@Override
		public IEditorPart createIEditorPart() throws PartInitException {
			try {

				Constructor<?> c = getDiagramClass().getConstructor(ServicesRegistry.class, Table.class);
				IEditorPart newEditor = (IEditorPart) c.newInstance(this.servicesRegistry, this.rawModel);
				// IEditorPart newEditor = new DefaultNattableEditor(getServiceRegistry(), rawModel);
				this.editor = newEditor;
				return this.editor;

			} catch (Exception e) {
				// Lets propagate. This is an implementation problem that should be solved by
				// programmer.
				throw new PartInitException("Can't create Nattable", e); //$NON-NLS-1$
			}

		}

		/**
		 * Get the action bar requested by the Editor.
		 *
		 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IEditorModel#getActionBarContributor()
		 * @return
		 *
		 */
		@Override
		public EditorActionBarContributor getActionBarContributor() {

			String actionBarId = NattableEditorFactory.this.editorDescriptor.getActionBarContributorId();

			// Do nothing if no EditorActionBarContributor is specify.
			if (actionBarId == null || actionBarId.length() == 0) {
				return null;
			}

			// Try to get it.

			// Get ServiceRegistry
			// ServicesRegistry serviceRegistry = getServicesRegistry();
			ActionBarContributorRegistry registry;
			try {
				registry = this.servicesRegistry.getService(ActionBarContributorRegistry.class);
			} catch (ServiceException e) {
				// Service not found
				Activator.log.error(e);
				return null;
			}

			try {
				return registry.getActionBarContributor(actionBarId);
			} catch (BackboneException e) {
				Activator.log.error(e);
				return null;
			}
		}

		/**
		 * Get the underlying RawModel. Return the Diagram.
		 *
		 * @see org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel#getRawModel()
		 * @return
		 *
		 */
		@Override
		public Object getRawModel() {
			return this.rawModel;
		}
	}
}

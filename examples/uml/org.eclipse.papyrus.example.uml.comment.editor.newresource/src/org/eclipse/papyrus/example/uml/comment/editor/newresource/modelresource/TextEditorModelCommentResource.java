/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.example.uml.comment.editor.newresource.modelresource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.example.text.instance.papyrustextinstance.PapyrusTextInstance;
import org.eclipse.papyrus.infra.core.resource.AbstractDynamicModel;
import org.eclipse.papyrus.infra.core.resource.IModel;

public class TextEditorModelCommentResource extends AbstractDynamicModel<PapyrusTextInstance> implements IModel {

	/**
	 * File extension used for comment.
	 */
	public static final String COMMENT_FILE_EXTENSION = "comment"; //$NON-NLS-1$

	/**
	 * Model ID.
	 */
	public static final String MODEL_ID = "org.eclipse.papyrus.example.text.TextEditorModelCommentResource"; //$NON-NLS-1$

	/**
	 * 
	 * Constructor.
	 * 
	 */
	public TextEditorModelCommentResource() {

	}

	/**
	 * Get the file extension used for this model.
	 * 
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getModelFileExtension()
	 * 
	 * @return
	 */
	@Override
	protected String getModelFileExtension() {
		return COMMENT_FILE_EXTENSION;
	}

	/**
	 * Get the identifier used to register this model.
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getIdentifier()
	 *
	 * @return
	 */
	@Override
	public String getIdentifier() {
		return MODEL_ID;
	}

	/**
	 * Add a new initialized {@link PapyrusEMFCompareInstance} to the model.
	 *
	 * @param compareInstance
	 *            The compareInstance to add.
	 */
	public void addPapyrusTextInstance(PapyrusTextInstance compareInstance) {
		addModelRoot(compareInstance);
	}

	/**
	 * Add a new initialized {@link PapyrusEMFCompareInstance} to the model.
	 *
	 * @param compareInstance
	 *            The compareInstance to add.
	 */
	public void removePapyrusTextInstance(PapyrusTextInstance compareInstance) {
		getResource().getContents().remove(compareInstance);
	}

	@Override
	public void loadModel(URI uriWithoutExtension) {
		// It is a common use case that this resource does not (and will not)
		// exist
		if (exists(uriWithoutExtension)) {
			try {
				super.loadModel(uriWithoutExtension);
			} catch (Exception ex) {
				createModel(uriWithoutExtension);
			}
		}

		if (resource == null) {
			createModel(uriWithoutExtension);
		}
	}

	public boolean canPersist(EObject object) {
		return false;
	}

	public void persist(EObject object) {
	}

}

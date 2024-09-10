/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.ui.editor.factories;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * An abstract class used to select EObject from an Ecore model
 */
public abstract class AbstractEcoreEReferenceDialogEditorFactory extends AbstractEStructuralFeatureDialogEditorFactory {

	/**
	 * Constructor.
	 *
	 * @param propertyEditorFactoryURI
	 * @param editedFeature
	 */
	public AbstractEcoreEReferenceDialogEditorFactory(final URI propertyEditorFactoryURI, final EStructuralFeature editedFeature) {
		super(propertyEditorFactoryURI, editedFeature);
	}


	/**
	 * @see org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.AbstractEStructuralFeatureDialogEditorFactory#getDialogInput()
	 *
	 * @return
	 */
	@Override
	protected Collection<?> getDialogInput(final EObject editedObject) {
		Collection<EPackage> input = getEditedMetamodelEPackage(editedObject);

		if (null == input || input.isEmpty()) {
			input = new HashSet<EPackage>();
			// in this case we cross the loaded resource to find EPackage in others resource
			final ResourceSet set = editedObject.eResource().getResourceSet();
			Iterator<Resource> iter = set.getResources().iterator();
			while (iter.hasNext()) {
				final Resource res = iter.next();
				for (EObject eobject : res.getContents()) {
					if (eobject instanceof EPackage) {
						input.add((EPackage) eobject);
					} else {
						final EClass eClass = eobject.eClass();
						if (null != eClass && null != eClass.getEPackage()) {
							input.add(eClass.getEPackage());
						}
					}
				}
			}
		}
		return input;
	}

	/**
	 * 
	 * @param editedObject
	 *            the edited object
	 * @return
	 *         the EPackage representing the metamodels we are editing. For example, if we are editing a PapyrusExpression in a Table context, we probably returns UMLPackage
	 */
	protected abstract Collection<EPackage> getEditedMetamodelEPackage(final EObject editedObject);

}

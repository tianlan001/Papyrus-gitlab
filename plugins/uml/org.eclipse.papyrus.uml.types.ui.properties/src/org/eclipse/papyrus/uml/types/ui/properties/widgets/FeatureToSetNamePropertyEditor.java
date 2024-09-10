/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *  Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 517679
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.ui.properties.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;
import org.eclipse.papyrus.uml.profile.index.ProfileWorkspaceModelIndex;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * The Stereotype qualify name property Editor.
 */
public class FeatureToSetNamePropertyEditor extends AbstractPropertyEditor {

	/** The stereotype qualify name value editor. */
	private FeatureToSetNameValueEditor editor;

	/** The editing domain. */
	private AdapterFactoryEditingDomain domain;

	/**
	 * Constructor.
	 *
	 * @param parent
	 *            The composite in which the widget will be displayed
	 * @param style
	 *            The style for the widget
	 */
	public FeatureToSetNamePropertyEditor(final Composite parent, final int style) {
		editor = new FeatureToSetNameValueEditor(parent, style);
		setEditor(editor);
	}

	/**
	 * Set the Uml Element to apply stereotype to the editor. Used to filter applicable stereotypes.
	 */
	protected void setObjectToApply() {
		if (null != domain) {

			// get the selection
			Object firstElement = input.getSelection().getFirstElement();
			if (firstElement instanceof EObject) {

				List<Profile> profiles = new ArrayList<Profile>();
				// Gets profiles from workspace
				profiles.addAll(getWorkspaceProfiles());

				// Gets profiles from plugins
				List<IRegisteredProfile> registeredProfiles = RegisteredProfile.getRegisteredProfiles();

				for (IRegisteredProfile iRegisteredProfile : registeredProfiles) {
					Profile profile = getProfile(iRegisteredProfile);
					if (null != profile) {
						profiles.add(profile);
					}
				}

				// Gets all stereotypes
				List<Stereotype> allStereotypes = new ArrayList<Stereotype>();
				for (Profile profile : profiles) {
					allStereotypes.addAll(StereotypeUtil.getAllStereotypes(profile));
				}


				// Set the editor with find stereotypes
				boolean editorSet = false;
				Iterator<Stereotype> stereotypes = allStereotypes.iterator();
				while (!editorSet && stereotypes.hasNext()) {
					Stereotype stereotype = (Stereotype) stereotypes.next();

					// get the stereotype qualify name
					EObject eContainer = ((EObject) firstElement).eContainer();
					String stereotypeQualifiedName = null;
					if (eContainer instanceof StereotypeToApply) {
						stereotypeQualifiedName = ((StereotypeToApply) eContainer).getStereotypeQualifiedName();
					} else if (firstElement instanceof StereotypePropertyReferenceEdgeAdviceConfiguration) {
						stereotypeQualifiedName = ((StereotypePropertyReferenceEdgeAdviceConfiguration) firstElement).getStereotypeQualifiedName();
					}

					// Set the editor
					if (null != stereotypeQualifiedName && stereotypeQualifiedName.equals(stereotype.getQualifiedName())) {
						// Set the source stereotype to the editor.
						editor.setSourceStereotype(stereotype);
						editorSet = true;
					}

					// unload resources
					profiles.forEach(profile -> {
						Resource eResource = profile.eResource();
						if (null != eResource && eResource.isLoaded()) {
							eResource.unload();
						}
					});
				}
			}
		}
	}

	/**
	 * @return workspace profiles.
	 */
	protected Collection<Profile> getWorkspaceProfiles() {
		Collection<Profile> profiles = new ArrayList<>();
		Collection<URI> workspaceProfilesURIs = ProfileWorkspaceModelIndex.getInstance().getWorkspaceProfilesURIs();

		for (Iterator iterator = workspaceProfilesURIs.iterator(); iterator.hasNext();) {
			URI uri = (URI) iterator.next();

			ResourceSet resourceSet = new ResourceSetImpl();
			Resource createResource = resourceSet.getResource(uri, true);
			if (!createResource.getContents().isEmpty() && createResource.getContents().get(0) instanceof Profile) {
				profiles.add((Profile) createResource.getContents().get(0));
			} else {
				createResource.unload();
			}
		}
		return profiles;
	}

	/**
	 * Gets the {@link Profile} model of an {@link IRegisteredProfile}.
	 */
	protected Profile getProfile(final IRegisteredProfile registeredProfile) {
		URI uri = registeredProfile.getUri();
		Resource resource = domain.getResourceSet().getResource(uri, true);
		Profile profile = null;
		if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof Profile) {
			profile = (Profile) resource.getContents().get(0);
		} else {
			resource.unload();
		}
		return profile;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setProperty(java.lang.String)
	 */
	@Override
	public void setProperty(final String path) {
		super.setProperty(path);
		setDomain();
		setObjectToApply();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#setInput(org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource)
	 */
	@Override
	public void setInput(final DataSource input) {
		super.setInput(input);
		setDomain();
		setObjectToApply();
	}

	/**
	 * Get the Editing domain according to the input and the property path.
	 */
	private void setDomain() {
		if (null != propertyPath && null != input) {
			// Get the semantic uml object selected
			ModelElement modelElement = input.getModelElement(propertyPath);
			EMFHelper.resolveEditingDomain(input.getSelection().getFirstElement());
			if (modelElement instanceof EMFModelElement) {
				domain = (AdapterFactoryEditingDomain) ((EMFModelElement) modelElement).getDomain();
			}
		}
	}


}

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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.properties.databinding.tests;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.extensionpoints.Registry;
import org.eclipse.papyrus.uml.extensionpoints.profile.IRegisteredProfile;
import org.eclipse.papyrus.uml.properties.modelelement.StereotypeApplicationModelElement;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Profile;
import org.junit.Assert;

/**
 * This allows to test the stereotype application observable list.
 */
@SuppressWarnings({ "nls" })
@PluginResource("model/StereotypeApplicationObservableList.di")
public class ProfileApplicationObservableListTest extends AbstractUMLObservableListTest {

	/**
	 * The profile to apply to the model.
	 */
	protected Profile profileToApply = null;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#initializeContextObject()
	 */
	@Override
	protected Object initializeContextObject() {
		return model;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#createModelElement(org.eclipse.emf.edit.domain.EditingDomain, java.lang.Object)
	 */
	@Override
	protected ModelElement createModelElement(final EditingDomain domain, final Object source) {
		return new StereotypeApplicationModelElement((Element) source, domain);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#getPropertyPath()
	 */
	@Override
	protected String getPropertyPath() {
		return "profileApplication";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedBeforeValue()
	 */
	@Override
	protected Object expectedBeforeValue() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedAfterValue()
	 */
	@Override
	protected Object expectedAfterValue() {
		if (null != profileToApply) {
			return Collections.singletonList(profileToApply);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.properties.databinding.tests.AbstractObservableTest#expectedValueToSet()
	 */
	@Override
	protected Object expectedValueToSet() {
		if (null == profileToApply) {
			final IRegisteredProfile registeredProfile = Registry.getRegisteredProfiles().stream().filter(p -> "pathmap://UML_PROFILES/Ecore.profile.uml".equals(p.getUri().toString())).findFirst().get();

			if (null != registeredProfile) {
				final URI profileUri = registeredProfile.getUri();
				final Resource profileResource = editorFixture.getResourceSet().getResource(profileUri, true);

				if ((!profileResource.getContents().isEmpty()) && profileResource.getContents().get(0) instanceof Profile) {
					profileToApply = (Profile) (profileResource.getContents().get(0));
				}
			}

			Assert.assertNotNull("A profile must be available for the application", profileToApply);
		}
		return profileToApply;
	}

}

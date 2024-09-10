/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.tests.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThan;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.language.Language;
import org.eclipse.papyrus.infra.core.language.Version;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForResourceSet;
import org.eclipse.papyrus.junit.utils.rules.ModelSetFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.rules.ServiceRegistryModelSetFixture;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * Tests the UML language providers in the Papyrus Language Service.
 */
@PluginResource("resources/language/My.di")
public class UMLLanguageProvidersTest {
	@ClassRule
	public static final ModelSetFixture modelSet = new ServiceRegistryModelSetFixture();

	public UMLLanguageProvidersTest() {
		super();
	}

	@Test
	public void umlLanguage() throws ServiceException {
		ILanguageService service = ServiceUtilsForResourceSet.getInstance().getService(ILanguageService.class, modelSet.getResourceSet());

		ILanguage uml = null;
		for (ILanguage next : service.getLanguages(modelSet.getProject().getURI("My.uml"), true)) {
			if ("org.eclipse.papyrus.uml.language".equals(next.getID())) {
				uml = next;
				break;
			}
		}

		assertThat(uml, notNullValue());

		// Version is at least 2.5
		assertThat(uml.getVersion().major(), greaterThan(1));
		if (uml.getVersion().major() == 2) {
			assertThat(uml.getVersion().minor(), greaterThan(4));
		}
		assertThat(uml.getName(), is("UML"));
	}

	@Test
	public void profileLanguage() throws ServiceException {
		JunkLanguage junk = AdapterUtils.adapt(modelSet.getResourceSet(), JunkLanguage.class, null);
		assertThat(junk, notNullValue()); // The language was installed
	}

	//
	// Test fixtures
	//

	public static class JunkLanguage extends Language implements Adapter {
		private ModelSet modelSet;

		public JunkLanguage() {
			super("org.eclipse.papyrus.uml.tools.tests.language.junk", new Version("1.1.0"), "Junk");
		}

		@Override
		public void install(ModelSet modelSet) {
			this.modelSet = modelSet;

			modelSet.eAdapters().add(this);
		}

		@Override
		public void uninstall(ModelSet modelSet) {
			modelSet.eAdapters().remove(this);
			this.modelSet = null;
		}

		@Override
		public void notifyChanged(Notification notification) {
			// Pass
		}

		@Override
		public Notifier getTarget() {
			return modelSet;
		}

		@Override
		public void setTarget(Notifier newTarget) {
			if ((newTarget != null) && (newTarget != modelSet)) {
				throw new IllegalArgumentException("bad junk language adapter target");
			}
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == JunkLanguage.class;
		}
	}

}

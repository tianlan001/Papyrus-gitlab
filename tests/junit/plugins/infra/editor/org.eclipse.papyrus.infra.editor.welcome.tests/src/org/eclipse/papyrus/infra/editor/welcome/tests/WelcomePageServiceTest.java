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

package org.eclipse.papyrus.infra.editor.welcome.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.lessThan;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeThat;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.IPageUtils;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.PageRemovalValidator;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.internal.WelcomePage;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Test;

/**
 * Test cases for the default {@link IWelcomePageService} implementation.
 */
@PluginResource("resources/empty_model.di")
public class WelcomePageServiceTest extends AbstractWelcomePageTest {

	public WelcomePageServiceTest() {
		super();
	}

	@Test
	public void getOwner() {
		assertThat(getService().getOwner(), is(editor.getServiceRegistry()));
	}

	@Test
	public void emptyModelShowsWelcomePage() {
		WelcomePage welcome = getWelcomePage();
		assertThat(welcome, notNullValue());

		assertThat(isActivePage(welcome), is(true));
	}

	@Test
	public void emptyModelCannotCloseWelcomePage() {
		IWelcomePageService service = getService();
		assertThat(service.canCloseWelcomePage(), is(false));
	}

	@Test
	public void welcomeModelElement() {
		Welcome welcome = getService().getWelcome();

		assertThat(welcome, notNullValue());
		assertThat(welcome.eResource(), notNullValue());

		IPage activePage = getActivePage();
		assumeThat(activePage, notNullValue());
		assertThat(IPageUtils.getRawModel(activePage), is(welcome));
	}

	@Test
	public void welcomeModelResource() {
		Resource resource = getService().getWelcomeResource();

		assertThat(resource, notNullValue());
		assertThat(resource.getResourceSet(), is(editor.getModelSet()));
	}

	@Test
	@PluginResource("resources/one_diagram.di")
	public void openWelcomePage() {
		// Initially, this page is not open
		assumeThat(getWelcomePage(), nullValue());

		getService().openWelcomePage();
		editor.flushDisplayEvents();

		WelcomePage page = getWelcomePage();
		assertThat(page, notNullValue());
		assertThat(isActivePage(page), is(true));
	}

	@Test
	@PluginResource("resources/one_diagram.di")
	public void closeWelcomePage() {
		// Initially, this page is not open
		assumeThat(getWelcomePage(), nullValue());
		getService().openWelcomePage();
		editor.flushDisplayEvents();

		WelcomePage page = getWelcomePage();
		assumeThat(page, notNullValue());

		assertThat(getService().canCloseWelcomePage(), is(true));

		editor.getPageManager().closePage(getService().getWelcome());
		editor.flushDisplayEvents();

		assertThat(getWelcomePage(), nullValue());
	}

	@Test
	@PluginResource("resources/one_diagram.di")
	public void canCloseWelcomePage() {
		// Initially, this page is not open
		assumeThat(getWelcomePage(), nullValue());

		// Close the active diagram, which is the only page
		editor.closeDiagram();

		// This was opened automatically
		WelcomePage page = getWelcomePage();
		assumeThat(page, notNullValue());

		assertThat(getService().canCloseWelcomePage(), is(false));
	}

	@Test
	@PluginResource("resources/many_diagrams.di")
	public void pageRemovalValidator() {
		SashModel sashModel = SashModelUtils.getSashWindowsMngr(editor.getModelSet()).getSashModel();

		getService().openWelcomePage();
		editor.flushDisplayEvents();

		PageRef welcomePage = sashModel.lookupPage(getWelcome());
		assertNotNull(welcomePage);

		PageRemovalValidator validator = PageRemovalValidator.getInstance(sashModel);
		assertThat(validator.canRemovePage(welcomePage), is(true));
		Collection<PageRef> allPages = getAllPages(sashModel);
		Collection<PageRef> removable = new ArrayList<>(validator.filterRemovablePages(allPages));
		assertThat(removable.size(), lessThan(allPages.size()));
		assertThat(removable, not(hasItem(welcomePage)));

		// Composition of multiple validators
		sashModel.eAdapters().add(new DenyAllRemovals());

		validator = PageRemovalValidator.getInstance(sashModel);
		assertThat(validator.canRemovePage(welcomePage), is(false));
		removable = new ArrayList<>(validator.filterRemovablePages(allPages));
		assertThat(removable.size(), is(0));

		// No validators
		sashModel.eAdapters().removeIf(PageRemovalValidator.class::isInstance);

		validator = PageRemovalValidator.getInstance(sashModel);
		assertThat(validator.canRemovePage(welcomePage), is(true));
		removable = new ArrayList<>(validator.filterRemovablePages(allPages));
		assertThat(removable, is(allPages));

	}

	//
	// Test Framework
	//

	Collection<PageRef> getAllPages(SashModel sash) {
		Collection<PageRef> result = new ArrayList<>();

		sash.eAllContents().forEachRemaining(next -> {
			if (next instanceof PageRef) {
				result.add((PageRef) next);
			}
		});

		return result;
	}

	//
	// Nested types
	//

	static class DenyAllRemovals extends AdapterImpl implements PageRemovalValidator {
		@Override
		public boolean canRemovePage(PageRef page) {
			return false;
		}
	}
}

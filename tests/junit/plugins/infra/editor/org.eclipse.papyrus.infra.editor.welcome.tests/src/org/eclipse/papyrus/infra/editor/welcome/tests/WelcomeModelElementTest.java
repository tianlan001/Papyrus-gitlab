/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.papyrus.infra.core.language.Version;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModel;
import org.eclipse.papyrus.infra.core.resource.sasheditor.SashModelUtils;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.utils.TransactionHelper;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.editor.welcome.internal.modelelements.LanguageObservable;
import org.eclipse.papyrus.infra.editor.welcome.internal.modelelements.WelcomeModelElement;
import org.eclipse.papyrus.infra.editor.welcome.internal.modelelements.WelcomeModelElementFactory;
import org.eclipse.papyrus.infra.ui.internal.commands.SashLayoutCommandFactory;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the {@link WelcomeModelElement} and its properties.
 */
@PluginResource("resources/many_diagrams.di")
public class WelcomeModelElementTest extends AbstractWelcomePageTest {

	private WelcomeModelElement fixture;

	public WelcomeModelElementTest() {
		super();
	}

	@Test
	public void privateLayoutProperty() {
		@SuppressWarnings("unchecked")
		IObservableValue<Boolean> privateLayout = (IObservableValue<Boolean>) fixture.getObservable("privateLayout");

		// The test model's layout is shared
		assertThat(privateLayout.getValue(), is(false));

		// Change it
		privateLayout.setValue(true);

		SashModel sashModel = SashModelUtils.getSashModel(editor.getModelSet());

		Resource di = editor.getModelSet().getResource(sashModel.getSharedResourceURI(), false);
		assertThat(di, notNullValue());
		assertThat(di.isLoaded(), is(true));
		assertThat(di.getContents(), not(hasItem(anything())));

		Resource sash = editor.getModelSet().getResource(sashModel.getPrivateResourceURI(), false);
		assertThat(sash, notNullValue());
		assertThat(sash.isLoaded(), is(true));
		assertThat(sash.getContents(), hasItem(instanceOf(SashWindowsMngr.class)));
	}

	@Test
	public void restoreActivePageProperty() throws InterruptedException, RollbackException {
		@SuppressWarnings("unchecked")
		IObservableValue<Boolean> restoreActivePage = (IObservableValue<Boolean>) fixture.getObservable("restoreActivePage");

		// Initial conditions
		assumeThat(editor.getDiagram("classes").getDiagramView(), not(isPageVisible()));
		assumeThat(editor.getDiagram("use cases").getDiagramView(), not(isPageVisible()));
		assertThat(restoreActivePage.getValue(), is(false));

		// Change it (only supported in private layouts)
		// toggleSashLayoutStorage();
		restoreActivePage.setValue(true);
		editor.flushDisplayEvents();

		// Activate pages in different tab folders, so that both are visible simultaneously
		editor.activateDiagram("classes");
		editor.activateDiagram("use cases");

		editor.close();

		// Open the editor again

		editor.reopen();

		assertThat(editor.getDiagram("classes").getDiagramView(), isPageVisible());
		assertThat(editor.getDiagram("use cases").getDiagramView(), isPageVisible());
	}

	@Test
	public void languagesProperty() throws InterruptedException, RollbackException {
		@SuppressWarnings("unchecked")
		IObservableList<LanguageObservable> languages = (IObservableList<LanguageObservable>) fixture.getObservable("languages");

		assertThat(languages, hasItem(anything()));
		LanguageObservable lang = languages.get(0);
		assertThat(lang.getName().getValue().toUpperCase(), is("UML"));
		assertThat(lang.getVersion().getValue().compareTo(new Version("2.5")), not(lessThan(0)));
	}

	/**
	 * Verifies correct and complete disposal of observables when they are no longer needed.
	 */
	@Test
	public void dispose_bug487027() {
		@SuppressWarnings("unchecked")
		IObservableList<LanguageObservable> languages = (IObservableList<LanguageObservable>) fixture.getObservable("languages");

		assumeThat(languages, hasItem(anything()));
		LanguageObservable lang = languages.get(0);

		// Dispose the model-element
		fixture.dispose();

		// Let the auto-release pool clean up
		editor.flushDisplayEvents();

		assertThat("Language observable not disposed", lang.isDisposed(), is(true));
	}

	@Test
	public void isEditable() {
		assertThat(fixture.isEditable("privateLayout"), is(true));
		assertThat(fixture.isEditable("languages"), is(true));
		assertThat(fixture.isEditable("garbage"), is(false));
	}

	@Test
	public void isEditableRestoreActivePage() {
		// This is not editable while the layout is shared
		assertThat(fixture.isEditable("restoreActivePage"), is(false));

		// So, make the layout private
		toggleSashLayoutStorage();

		assertThat(fixture.isEditable("restoreActivePage"), is(true));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = (WelcomeModelElement) new WelcomeModelElementFactory().createFromSource(
				requireWelcome(), null); // The data-context isn't used by the factory
	}

	void toggleSashLayoutStorage() {
		try {
			TransactionHelper.run(editor.getEditingDomain(), () -> {
				new SashLayoutCommandFactory(editor.getEditor()).createTogglePrivateLayoutCommand().execute();
			});
		} catch (InterruptedException | RollbackException e) {
			e.printStackTrace();

			fail("Could not toggle sash layout storage");
		}
	}
}

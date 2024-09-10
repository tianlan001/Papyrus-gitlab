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

import static org.junit.Assert.fail;

import java.util.Optional;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.papyrus.infra.core.sasheditor.editor.IComponentPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IEditorPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPageVisitor;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.internal.WelcomePage;
import org.eclipse.papyrus.infra.properties.ui.runtime.IConfigurationManager;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.tools.util.PlatformHelper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DisplayUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.swt.widgets.Display;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;
import org.junit.Rule;

/**
 * Abstract test class for common behaviours of welcome-page tests.
 */
public abstract class AbstractWelcomePageTest extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	protected AbstractWelcomePageTest() {
		super();
	}

	@BeforeClass
	public static void loadConfigurationManager() throws InterruptedException, ExecutionException {
		// Preëmptively load the configuration manager to avoid deadlocks in
		// diagram bundle loading and preferences initialization
		ExecutorService exec = Executors.newSingleThreadExecutor();
		CompletionService<IConfigurationManager> completion = new ExecutorCompletionService<IConfigurationManager>(exec);

		Future<IConfigurationManager> waitFor = completion.submit(() -> PropertiesRuntime.getConfigurationManager());

		try {
			// Wait for these bundles to finish activating
			Display.getDefault().syncExec(() -> {
				while (completion.poll() != waitFor) {
					// Process the sync runnables that the diagram bundles are waiting to do during activation
					DisplayUtils.flushEventLoop();
				}
			});

			// Just to be sure that it's done
			waitFor.get();
		} finally {
			exec.shutdown();
		}
	}

	protected IWelcomePageService getService() {
		try {
			return editor.getServiceRegistry().getService(IWelcomePageService.class);
		} catch (ServiceException e) {
			fail("Cannot get welcome-page service: " + e.getMessage());
			return null; // Unreachable
		}
	}

	protected Optional<IWelcomePageService> getServiceOpt() {
		try {
			return Optional.of(editor.getServiceRegistry().getService(IWelcomePageService.class));
		} catch (ServiceException e) {
			fail("Cannot get welcome-page service: " + e.getMessage());
			return Optional.empty(); // Unreachable
		}
	}

	protected WelcomePage getWelcomePage() {
		WelcomePage[] result = { null };

		ISashWindowsContainer sash = PlatformHelper.getAdapter(editor.getEditor(), ISashWindowsContainer.class);
		sash.visit(new IPageVisitor() {

			@Override
			public void accept(IEditorPage page) {
				// Pass
			}

			@Override
			public void accept(IComponentPage page) {
				WelcomePage welcome = PlatformHelper.getAdapter(page, WelcomePage.class);
				if (welcome != null) {
					result[0] = welcome;
				}
			}
		});

		return result[0];
	}

	protected boolean isActivePage(WelcomePage page) {
		IPage activePage = getActivePage();
		return (activePage != null) && (PlatformHelper.getAdapter(activePage, WelcomePage.class) == page);
	}

	protected IPage getActivePage() {
		ISashWindowsContainer sash = PlatformHelper.getAdapter(editor.getEditor(), ISashWindowsContainer.class);
		return sash.getActiveSashWindowsPage();
	}

	protected Optional<Welcome> getWelcomeOpt() {
		return getServiceOpt().map(IWelcomePageService::getWelcome);
	}

	protected Welcome getWelcome() {
		return getWelcomeOpt().orElse(null);
	}

	protected Welcome requireWelcome() {
		return getWelcomeOpt().orElseThrow(AssertionError::new);
	}

	protected Matcher<Object> isPageVisible() {
		ISashWindowsContainer sash = PlatformHelper.getAdapter(editor.getEditor(), ISashWindowsContainer.class);
		return WelcomeMatchers.isPageVisible(sash);
	}
}

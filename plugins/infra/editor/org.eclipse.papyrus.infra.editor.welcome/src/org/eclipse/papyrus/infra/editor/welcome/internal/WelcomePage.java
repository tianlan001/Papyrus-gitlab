/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus and others.
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
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 573429
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintEngine;
import org.eclipse.papyrus.infra.constraints.runtime.ConstraintEngineListener;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ICloseablePart;
import org.eclipse.papyrus.infra.editor.welcome.IWelcomePageService;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.properties.ui.runtime.DefaultDisplayEngine;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.properties.ui.xwt.XWTSection;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.views.properties.tabbed.ISection;

/**
 * The extensible <em>Welcome Page</em> of the Papyrus Editor.
 */
public class WelcomePage implements ICloseablePart {

	private final IWelcomePageService service;

	private final Object model;

	private FormToolkit toolkit;
	private ConstraintEngine<View> constraintEngine;
	private ConstraintEngineListener constraintsListener;
	private DefaultDisplayEngine displayEngine;
	private ScrolledForm form;
	private Collection<ISection> sections;
	private WelcomeLayout welcomeLayout;

	private final CopyOnWriteArrayList<IPropertyListener> propertyListeners = new CopyOnWriteArrayList<>();

	public WelcomePage(IWelcomePageService service, Object model) {
		super();

		this.service = service;
		this.model = model;
	}

	public static WelcomePage getWelcomePage(Control control) {
		WelcomePage result = null;

		for (Control next = control; (result == null) && (next != null); next = next.getParent()) {
			if (next.getData() instanceof WelcomePage) {
				result = (WelcomePage) next.getData();
			}
		}

		return result;
	}

	@Override
	public boolean canClose() {
		return service.canCloseWelcomePage();
	}

	void fireCanCloseChanged() {
		propertyListeners.forEach(l -> {
			try {
				l.propertyChanged(WelcomePage.this, PROP_CAN_CLOSE);
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in property listener", e); //$NON-NLS-1$
			}
		});
	}

	public Composite createControl(Composite parent) {
		toolkit = new FormToolkit(parent.getDisplay());
		constraintEngine = PropertiesRuntime.getConstraintEngine();
		displayEngine = new DefaultDisplayEngine(false);
		attachConstraintEngine(constraintEngine);

		form = toolkit.createScrolledForm(parent);
		form.setData(this);

		FormLayout layout = new FormLayout();
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.spacing = 5;
		form.getBody().setLayout(layout);

		createSections(form.getBody());

		form.addDisposeListener(event -> dispose());

		return form;
	}

	protected void attachConstraintEngine(ConstraintEngine<? extends View> engine) {
		constraintsListener = event -> rebuildSections(form.getBody());
		engine.addConstraintEngineListener(constraintsListener);
	}

	public void dispose() {
		if (constraintsListener != null) {
			constraintEngine.removeConstraintEngineListener(constraintsListener);
			constraintsListener = null;
		}

		constraintEngine = null;
		if (displayEngine != null) {
			displayEngine.dispose();
			displayEngine = null;
		}

		if (toolkit != null) {
			toolkit.dispose();
			toolkit = null;
		}

		sections.forEach(ISection::dispose);
		sections.clear();

		if (welcomeLayout != null) {
			welcomeLayout.dispose();
			welcomeLayout = null;
		}

		propertyListeners.clear();
	}

	protected void createSections(Composite parent) {
		Set<View> views = constraintEngine.getDisplayUnits(model);

		List<Context> welcomeContexts = PropertiesRuntime.getConfigurationManager().getContextsForPreferencePage(WelcomeConstants.WELCOME_PAGE_ID);

		// Get the unique tabs
		Map<String, WelcomeTab> tabProxies = new HashMap<>();
		views.stream()
				.filter(v -> welcomeContexts.contains(v.getContext())) // Remove views that are not welcome content
				.flatMap(v -> v.getSections().stream())
				.map(s -> s.getTab())
				.forEach(tab -> {
					WelcomeTab existing = tabProxies.get(tab.getId());
					if (existing != null) {
						existing.merge(tab);
					} else {
						tabProxies.put(tab.getId(), new WelcomeTab(tab));
					}
				});

		List<WelcomeTab> tabs = new ArrayList<>(tabProxies.values());
		sections = new ArrayList<>();

		welcomeLayout = new WelcomeLayout(parent, toolkit, service);
		welcomeLayout.createTabs(tabs);

		// Now, filter the sections in each tab for only those referenced
		// by the views that we want to present
		for (WelcomeTab next : tabs) {
			next.filterSections(views);
		}

		IStructuredSelection selection = new StructuredSelection(model);
		for (WelcomeTab tab : tabs) {
			for (Section section : tab.getSections()) {
				XWTSection xwtSection = new XWTSection(section, tab.getView(section), displayEngine);
				sections.add(xwtSection);

				xwtSection.createControls(welcomeLayout.getTabControl(section.getTab()), null);
				xwtSection.setInput(null, selection);
				xwtSection.refresh();
			}
		}
	}

	protected void rebuildSections(Composite parent) {
		displayEngine.invalidate();

		welcomeLayout.dispose();

		sections.forEach(ISection::dispose);
		sections.clear();

		createSections(parent);
		parent.layout();
	}

	void reset() {
		welcomeLayout.resetLayoutModel();
	}

	void layout() {
		welcomeLayout.layout();
	}

	@Override
	public void addPropertyListener(IPropertyListener listener) {
		propertyListeners.addIfAbsent(listener);
	}

	@Override
	public void removePropertyListener(IPropertyListener listener) {
		propertyListeners.remove(listener);
	}

}

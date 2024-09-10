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

package org.eclipse.papyrus.infra.editor.welcome.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.papyrus.infra.properties.contexts.Section;
import org.eclipse.papyrus.infra.properties.contexts.Tab;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.swt.widgets.Composite;

class WelcomeTab implements Comparable<WelcomeTab> {
	private String id;
	private Set<String> allTabIDs;
	private String label;

	private Set<Tab> tabs = new HashSet<>();
	private final List<Section> sections;
	private final Map<Section, View> views;

	private int priority;

	WelcomeTab(Tab tab) {
		super();

		this.id = tab.getId();
		this.allTabIDs = new LinkedHashSet<>(Collections.singleton(tab.getId()));
		this.label = tab.getLabel();
		this.sections = new ArrayList<>(tab.getSections());
		this.views = new HashMap<>();

		priority = tab.getPriority();

		tabs.add(tab);
	}

	public String getID() {
		return id;
	}

	public Set<String> getAllTabIDs() {
		return allTabIDs;
	}

	public String getLabel() {
		return label;
	}

	public List<Section> getSections() {
		return sections;
	}

	void filterSections(Set<View> views) {
		for (Iterator<Section> iter = sections.iterator(); iter.hasNext();) {
			Section section = iter.next();
			Optional<View> view = views.stream().filter(v -> v.getSections().contains(section)).findFirst();
			if (view.isPresent()) {
				this.views.put(section, view.get());
			} else {
				iter.remove();
			}
		}
	}

	View getView(Section section) {
		return views.get(section);
	}

	/**
	 * Sort in priority order, from highest to lowest, and with
	 * precendents before antecedents as indicated by the aggregate
	 * 'afterTab' relationships.
	 */
	@Override
	public int compareTo(WelcomeTab o) {
		int result = o.precedes(this) ? +1 : this.precedes(o) ? -1 : 0;

		if (result == 0) {
			result = o.priority - this.priority;
		}

		return result;
	}

	boolean precedes(WelcomeTab other) {
		// I precede another tab-proxy if, in aggregate, more of its tabs are after my
		// tabs that vice versa
		long otherAntecedents = other.tabs.stream().filter(tab -> this.tabs.contains(tab.getAfterTab())).count();
		long myAntecedents = this.tabs.stream().filter(tab -> other.tabs.contains(tab.getAfterTab())).count();
		return otherAntecedents > myAntecedents;
	}

	void merge(Tab tab) {
		if (tabs.add(tab)) {
			allTabIDs.add(tab.getId());

			if (tab.getPriority() > priority) {
				priority = tab.getPriority();
				id = tab.getId();

				// Insert these at the head
				sections.addAll(0, tab.getSections());
			} else {
				// Append these sections
				sections.addAll(tab.getSections());
			}
		}
	}

	void register(Composite composite, Map<? super Tab, ? super Composite> tabControls) {
		tabs.forEach(tab -> tabControls.put(tab, composite));
	}
}

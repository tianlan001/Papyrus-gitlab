/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.emf.common.util.URI;
import org.osgi.service.prefs.BackingStoreException;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

/**
 * Façade for management/interrogation of core Modeling Assistant preferences.
 */
public class ModelingAssistantPreferences implements ISchedulingRule {
	private static final String PREF_WORKSPACE_ASSISTANT_MODELS = "workspaceAssistantModels";

	private final IEclipsePreferences preferences;

	private final Set<URI> workspaceAssistantModels = Sets.newLinkedHashSet();

	private final PreferenceListener listener = new PreferenceListener();

	private final AtomicBoolean savePending = new AtomicBoolean();

	ModelingAssistantPreferences(IEclipsePreferences preferences) {
		super();

		this.preferences = preferences;
		initWorkspaceAssistantModels();

		preferences.addPreferenceChangeListener(listener);
	}

	public void dispose() {
		try {
			savePreferences(true);
		} catch (CoreException e) {
			// Can't help it
			AssistantPlugin.getPlugin().getLog().log(e.getStatus());
		}

		preferences.removePreferenceChangeListener(listener);
	}

	public Iterable<URI> getWorkspaceAssistantModels() {
		synchronized (workspaceAssistantModels) {
			return ImmutableList.copyOf(workspaceAssistantModels);
		}
	}

	public boolean isWorkspaceAssistantModel(URI uri) {
		synchronized (workspaceAssistantModels) {
			return workspaceAssistantModels.contains(uri);
		}
	}

	public void addWorkspaceAssistantModel(URI uri) {
		synchronized (workspaceAssistantModels) {
			if (workspaceAssistantModels.add(uri)) {
				updateWorkspaceAssistantModels();
			}
		}
	}

	public void removeWorkspaceAssistantModel(URI uri) {
		synchronized (workspaceAssistantModels) {
			if (workspaceAssistantModels.remove(uri)) {
				updateWorkspaceAssistantModels();
			}
		}
	}

	void initWorkspaceAssistantModels() {
		workspaceAssistantModels.clear();
		String stringList = preferences.get(PREF_WORKSPACE_ASSISTANT_MODELS, ""); //$NON-NLS-1$
		for (String next : Splitter.on(' ').split(Strings.nullToEmpty(stringList))) {
			next = next.trim();
			if (!next.isEmpty()) {
				workspaceAssistantModels.add(URI.createURI(next, true));
			}
		}
	}

	void updateWorkspaceAssistantModels() {
		listener.write(PREF_WORKSPACE_ASSISTANT_MODELS, new Runnable() {

			@Override
			public void run() {
				preferences.put(PREF_WORKSPACE_ASSISTANT_MODELS, Joiner.on(' ').join(workspaceAssistantModels));
			}
		});
	}

	void savePreferences(boolean now) throws CoreException {
		if (!now) {
			if (savePending.compareAndSet(false, true)) {
				new PreferenceSaveJob().schedule(10000L);
			}
		} else if (savePending.compareAndSet(true, false)) {
			try {
				preferences.flush();
			} catch (BackingStoreException e) {
				throw new CoreException(new Status(IStatus.ERROR, AssistantPlugin.getPlugin().getSymbolicName(), "Failed to save preferences.", e)); //$NON-NLS-1$
			}
		}
	}

	//
	// ISchedulingRule protocol
	//

	@Override
	public boolean contains(ISchedulingRule rule) {
		return rule == this;
	}

	@Override
	public boolean isConflicting(ISchedulingRule rule) {
		return rule == this;
	}

	//
	// Nested types
	//

	private class PreferenceListener implements IPreferenceChangeListener {
		private final Set<String> updating = Sets.newConcurrentHashSet();

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (!updating.contains(event.getKey())) {
				switch (event.getKey()) {
				case PREF_WORKSPACE_ASSISTANT_MODELS:
					initWorkspaceAssistantModels();
					break;
				}
			}
		}

		void write(String key, Runnable writeOperation) {
			updating.add(key);

			try {
				writeOperation.run();
			} finally {
				updating.remove(key);

				try {
					savePreferences(false);
				} catch (CoreException e) {
					// Only happens in 'now=true' case
				}
			}
		}
	}

	private class PreferenceSaveJob extends Job {
		PreferenceSaveJob() {
			super("Saving preferences");

			setRule(ModelingAssistantPreferences.this);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus result = Status.OK_STATUS;

			try {
				savePreferences(true);
			} catch (CoreException e) {
				result = e.getStatus();
			}

			return result;
		}
	}
}

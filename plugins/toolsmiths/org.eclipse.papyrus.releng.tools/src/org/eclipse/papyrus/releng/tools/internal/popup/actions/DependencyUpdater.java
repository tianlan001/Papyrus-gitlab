/*******************************************************************************
 * Copyright (c) 2011, 2020 Mia-Software, CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Nicolas Bros (Mia-Software) - Bug 366567 - [Releng] Tool to update rmaps
 *     Camille Letavernier (CEA LIST) - Generalize to support POMs
 *     Christian W. Damus (CEA) - Add support for updating Oomph setup models
 *     Christian W. Damus - Support updating of multiple selected files
 *     Christian W. Damus - Ignore equivalent URL prefixes in detecting suspicious updates
 *
 *******************************************************************************/
package org.eclipse.papyrus.releng.tools.internal.popup.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.cbi.p2repo.aggregator.Contribution;
import org.eclipse.cbi.p2repo.aggregator.MappedRepository;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.releng.tools.internal.Activator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ListSelectionDialog;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Generic DependencyUpdater
 *
 * This class will read a B3 Aggregator model (e.g. from Simrel) to find up-to-date P2 repositories.
 *
 * Subclasses will then be able to update the relevant <T> element (e.g. XML Dom Node, EMF EObject...)
 * with the new repository location
 *
 * The matching is typically done via comments using the following format: updateFrom(repositoryLabel, index)
 * It is up to subclasses to retrieve these comments in their model (XML Document, EMF Model)
 *
 * @param <T>
 */
public abstract class DependencyUpdater<T> {

	private final Pattern commentPattern = Pattern.compile("updateFrom\\s*\\(\\s*\"(.*?)\"\\s*,\\s*(\\d+)\\s*\\)"); //$NON-NLS-1$

	private final List<LocationUpdateStrategy> locationUpdateStrategies = ImmutableList.of(
			new MilestoneLocationStrategy(),
			new EMFLocationStrategy());

	public DependencyUpdater() {
		super();
	}

	protected static final String PREFIX = "http://download.eclipse.org/"; //$NON-NLS-1$

	public abstract boolean canUpdate(IFile file);

	protected abstract List<T> getNodesToUpdate(IFile file) throws CoreException;

	public void updateDocument(final Shell parentShell, final IFile mapFile, final EList<Contribution> contributions, final Map<Object, Object> context) throws CoreException {
		try {
			List<T> nodesToUpdate = getNodesToUpdate(mapFile);
			List<UpdateItem<T>> updates = Lists.newArrayList();
			for (T uri : nodesToUpdate) {
				String comment = getComment(uri);
				if (comment != null) {
					Matcher matcher = getCommentPattern().matcher(comment);
					if (matcher.find()) {
						String contributionName = matcher.group(1);
						int repositoryIndex = Integer.parseInt(matcher.group(2));
						Contribution contribution = findContribution(contributions, contributionName);
						if (contribution == null) {
							throw new RuntimeException("'updateFrom' failed: cannot find contribution with label \"" + contributionName + "\""); //$NON-NLS-1$ //$NON-NLS-2$
						}
						updates.add(new UpdateItem<>(uri, contribution, repositoryIndex));
					} else if (comment.contains("updateFrom")) { //$NON-NLS-1$
						throw new Exception("Wrong syntax for 'updateFrom' : should be " + getCommentSyntax()); //$NON-NLS-1$
					}
				}
			}

			if (confirmUpdates(parentShell, updates, context)) {
				for (UpdateItem<T> next : updates) {
					updateWithContribution(parentShell, next.uriNode, next.contribution, next.repositoryIndex, context);
				}

				save(mapFile);

				mapFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
			}
		} catch (OperationCanceledException e) {
			throw e;
		} catch (Exception e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error updating map: " + e.getLocalizedMessage(), e)); //$NON-NLS-1$
		}
	}

	protected abstract void save(IFile file) throws Exception;

	protected void updateWithContribution(final Shell parentShell, final T uri, final Contribution contribution, final int repositoryIndex, final Map<Object, Object> context) {
		EList<MappedRepository> repositories = contribution.getRepositories();
		if (repositoryIndex >= repositories.size()) {
			throw new RuntimeException("wrong index in updateFrom(\"" + contribution.getLabel() + "\"" + repositoryIndex //$NON-NLS-1$ //$NON-NLS-2$
					+ ") : there are " + repositories.size() + " contributions"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		String location = repositories.get(repositoryIndex).getLocation();

		updateUri(uri, location);
	}

	protected abstract String getCurrentLocation(T uri);

	protected abstract void updateUri(T uri, String location);

	protected Contribution findContribution(Iterable<? extends Contribution> contributions, final String contributionName) {
		Contribution matchingContribution = null;
		int shortestMatch = Integer.MAX_VALUE;
		for (Contribution contribution : contributions) {
			final String label = contribution.getLabel();
			if (label != null && label.startsWith(contributionName)) {
				int thisMatch = label.length();
				if (thisMatch < shortestMatch) {
					// Prefer a prefix match that matches more of this contribution label
					// than any other (ultimately stopping on complete match)
					matchingContribution = contribution;
					shortestMatch = thisMatch;

					if (shortestMatch <= contributionName.length()) {
						// Complete match (equality). Cannot do better
						break;
					}
				}
			}
		}
		return matchingContribution;
	}

	protected abstract String getComment(T node);

	protected Pattern getCommentPattern() {
		return commentPattern;
	}

	protected String getCommentSyntax() {
		return "updateFrom(\"<contributionName>\",<index>)"; //$NON-NLS-1$
	}

	private boolean promptToReplaceSingle(Shell parentShell, LocationUpdate<T> locationUpdate, Map<Object, Object> context) {
		String message = NLS.bind("{0}\n\nUpdate anyways?", locationUpdate.strategy.getUpdateConfirmationMessage(locationUpdate.update, locationUpdate.oldLocation, locationUpdate.newLocation)); //$NON-NLS-1$
		boolean result = MessageDialog.openQuestion(parentShell, "Confirm Location Update", message);
		setReplace(locationUpdate.update, result, context);

		return result;
	}

	private Boolean getReplace(UpdateItem<T> update, Map<Object, Object> context) {
		return (Boolean) context.get("$replace$::" + update.contribution.getLabel()); //$NON-NLS-1$
	}

	private void setReplace(UpdateItem<T> update, Boolean replace, Map<Object, Object> context) {
		context.put("$replace$::" + update.contribution.getLabel(), replace); //$NON-NLS-1$
	}

	/**
	 * Prompt to confirm multiple suspicious dependency replacements, returning those updates that the
	 * user confirms to perform.
	 */
	private Collection<LocationUpdate<T>> promptToReplaceMultiple(Shell parentShell, Collection<? extends LocationUpdate<T>> locationUpdates, Map<Object, Object> context) {
		final List<LocationUpdate<T>> result = Lists.newArrayList(locationUpdates);

		ILabelProvider labels = new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((LocationUpdate<?>) element).update.contribution.getLabel();
			}
		};

		ListSelectionDialog dialog = new ListSelectionDialog(parentShell, locationUpdates, ArrayContentProvider.getInstance(), labels, "Select dependencies to confirm updating.") {
			@Override
			protected Control createDialogArea(Composite parent) {
				Composite result = (Composite) super.createDialogArea(parent);

				createDetailsArea(result);

				return result;
			}

			void createDetailsArea(Composite parent) {
				Label label = new Label(parent, SWT.NONE);
				label.setText("Details:");

				final Text details = new Text(parent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.READ_ONLY);
				GridData data = new GridData(GridData.FILL_BOTH);
				data.heightHint = details.getLineHeight() * 4;
				details.setLayoutData(data);

				getViewer().addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection sel = (IStructuredSelection) event.getSelection();
						if (sel.isEmpty()) {
							details.setText(""); //$NON-NLS-1$
						} else {
							LocationUpdate<?> update = (LocationUpdate<?>) sel.getFirstElement();
							details.setText(update.strategy.getUpdateConfirmationMessage(update.update, update.oldLocation, update.newLocation));
						}
					}
				});
			}
		};

		dialog.setInitialElementSelections(result);
		dialog.setTitle("Confirm Location Update");
		if (dialog.open() == Window.OK) {
			Set<?> toUpdate = ImmutableSet.copyOf(dialog.getResult());
			for (Iterator<LocationUpdate<T>> iter = result.iterator(); iter.hasNext();) {
				LocationUpdate<T> next = iter.next();

				boolean update = toUpdate.contains(next);
				setReplace(next.update, update, context);
				if (!update) {
					// User elects not to update, so remove from the result
					iter.remove();
				}
			}
		} else {
			throw new OperationCanceledException();
		}

		return result;
	}

	private boolean confirmUpdates(final Shell parentShell, List<UpdateItem<T>> updates, Map<Object, Object> context) {
		Map<UpdateItem<T>, LocationUpdate<T>> toPrompt = Maps.newHashMap();

		for (Iterator<UpdateItem<T>> iter = updates.iterator(); iter.hasNext();) {
			UpdateItem<T> next = iter.next();

			// Check for previous prompt answer
			Boolean previousAnswer = getReplace(next, context);
			if (previousAnswer != null) {
				if (previousAnswer) {
					// Don't prompt this one
				} else {
					iter.remove(); // Don't update this one
				}
			} else {
				EList<MappedRepository> repositories = next.contribution.getRepositories();
				if (next.repositoryIndex >= repositories.size()) {
					throw new RuntimeException("wrong index in updateFrom(\"" + next.contribution.getLabel() + "\"" + next.repositoryIndex //$NON-NLS-1$ //$NON-NLS-2$
							+ ") : there are " + repositories.size() + " contributions"); //$NON-NLS-1$ //$NON-NLS-2$
				}
				String location = repositories.get(next.repositoryIndex).getLocation();
				String current = getCurrentLocation(next.uriNode);

				if ((current == null) || !current.equals(location)) {
					if ((current != null) && !current.isEmpty()) {
						LocationUpdateStrategy strategy = findLocationUpdateStrategy(next, current, location);
						if (strategy != null) {
							toPrompt.put(next, new LocationUpdate<>(next, strategy, current, location));
						}
					}
				}
			}
		}

		if (!toPrompt.isEmpty()) {
			if (toPrompt.size() == 1) {
				// Simple dialog
				UpdateItem<T> update = Iterables.getOnlyElement(toPrompt.keySet());
				LocationUpdate<T> location = toPrompt.get(update);
				if (!promptToReplaceSingle(parentShell, location, context)) {
					updates.remove(update);
				}
			} else {
				// List dialog
				toPrompt.values().removeAll(promptToReplaceMultiple(parentShell, toPrompt.values(), context));
				updates.removeAll(toPrompt.keySet()); // What remains are the update exclusions
			}
		}

		return !updates.isEmpty();
	}

	private LocationUpdateStrategy findLocationUpdateStrategy(UpdateItem<T> update, String oldLocation, String newLocation) {
		LocationUpdateStrategy result = null;

		for (LocationUpdateStrategy next : locationUpdateStrategies) {
			if (!next.shouldAutoUpdate(update, oldLocation, newLocation)) {
				result = next;
				break;
			}
		}

		return result;
	}

	//
	// Nested types
	//

	private static class UpdateItem<T> {
		final T uriNode;
		final Contribution contribution;
		final int repositoryIndex;

		UpdateItem(T uriNode, Contribution contribution, int repositoryIndex) {
			super();

			this.uriNode = uriNode;
			this.contribution = contribution;
			this.repositoryIndex = repositoryIndex;
		}
	}

	private static class LocationUpdate<T> {
		final UpdateItem<T> update;
		final LocationUpdateStrategy strategy;
		final String oldLocation;
		final String newLocation;

		LocationUpdate(UpdateItem<T> update, LocationUpdateStrategy strategy, String oldLocation, String newLocation) {
			super();

			this.update = update;
			this.strategy = strategy;
			this.oldLocation = oldLocation;
			this.newLocation = newLocation;
		}
	}

	private interface LocationUpdateStrategy {
		Pattern URL_PREFIX_PATTERN = Pattern.compile("^(?:\\$\\{[^}]+\\}/|\\Qhttp://download.eclipse.org/\\E)"); //$NON-NLS-1$

		boolean shouldAutoUpdate(UpdateItem<?> update, String oldLocation, String newLocation);

		String getUpdateConfirmationMessage(UpdateItem<?> update, String oldLocation, String newLocation);

		default boolean hasRecognizedURLPrefix(String location) {
			return URL_PREFIX_PATTERN.matcher(location).find();
		}

		default String stripRecognizedURLPrefix(String location) {
			Matcher m = URL_PREFIX_PATTERN.matcher(location);
			return !m.find() ? location : location.substring(m.end());
		}

		default boolean matchURLPattern(Pattern urlPattern, String oldLocation, String newLocation) {
			boolean result = true; // Optimistically assume sameness if we can't find matching URL path segment structures

			Matcher oldMatcher = urlPattern.matcher(oldLocation);
			Matcher newMatcher = urlPattern.matcher(newLocation);
			boolean foundOld = oldMatcher.find();
			boolean foundNew = newMatcher.find();

			if (foundOld != foundNew) {
				// definitely different
				result = false;
			} else if (foundNew) {
				// Compare prefixes
				String oldPrefix = oldLocation.substring(0, oldMatcher.start());
				String newPrefix = newLocation.substring(0, newMatcher.start());
				if (hasRecognizedURLPrefix(oldPrefix) && hasRecognizedURLPrefix(newPrefix)) {
					// Both have equivalent URL prefixes, so remove those for comparison
					oldPrefix = stripRecognizedURLPrefix(oldPrefix);
					newPrefix = stripRecognizedURLPrefix(newPrefix);
				}

				result = newPrefix.equals(oldPrefix);
			}

			return result;
		}
	}

	static class MilestoneLocationStrategy implements LocationUpdateStrategy {
		private final Pattern typicalBuildTimestampPattern = Pattern.compile("[NISMR](?:-\\d+\\.\\d+(?:\\.\\d+)?(?:M|RC)\\d[abcd]-)?20\\d\\d[-0-9]+"); //$NON-NLS-1$

		@Override
		public boolean shouldAutoUpdate(UpdateItem<?> update, String oldLocation, String newLocation) {
			return matchURLPattern(typicalBuildTimestampPattern, oldLocation, newLocation);
		}

		@Override
		public String getUpdateConfirmationMessage(UpdateItem<?> update, String oldLocation, String newLocation) {
			return NLS.bind("The new location \"{0}\" for project \"{1}\" is not like the current location \"{2}\". This could roll back to an older (obsolete) build.", new Object[] { newLocation, update.contribution.getLabel(), oldLocation });
		}
	}

	static class EMFLocationStrategy implements LocationUpdateStrategy {
		private final Pattern typicalMilestonesPattern = Pattern.compile("\\d+\\.\\d+(milestones|interim)$"); //$NON-NLS-1$

		@Override
		public boolean shouldAutoUpdate(UpdateItem<?> update, String oldLocation, String newLocation) {
			boolean result = true; // Optimistically assume sameness if we can't find any milestones/interim segment

			Matcher oldMatcher = typicalMilestonesPattern.matcher(oldLocation);
			Matcher newMatcher = typicalMilestonesPattern.matcher(newLocation);
			boolean foundOld = oldMatcher.find();
			boolean foundNew = newMatcher.find();

			if (foundOld != foundNew) {
				// definitely different
				result = false;
			} else if (foundNew && !(oldMatcher.group(1).equals(newMatcher.group(1)))) {
				result = (oldMatcher.group(1).equals("interim"));
			}

			return result;
		}

		@Override
		public String getUpdateConfirmationMessage(UpdateItem<?> update, String oldLocation, String newLocation) {
			return NLS.bind("The current location \"{2}\" for project \"{1}\" provides interim builds. Updating from \"{0}\" could roll back to a previous milestone build.", new Object[] { newLocation, update.contribution.getLabel(), oldLocation });
		}
	}
}

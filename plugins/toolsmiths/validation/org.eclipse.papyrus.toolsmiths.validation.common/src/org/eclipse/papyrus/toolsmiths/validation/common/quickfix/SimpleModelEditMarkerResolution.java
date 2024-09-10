/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.tools.util.TriFunction;
import org.eclipse.papyrus.infra.tools.util.Try;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.messages.Messages;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

/**
 * <p>
 * A marker resolution that resolves a problem by means of an EMF {@link Command}
 * that edits the model. Two factory methods are provided that use client-provided functions to
 * compute commands:
 * </p>
 * <ul>
 * <li>one (recommended for most cases) that {@linkplain #create(int, String, String, Class, TriFunction) includes the marker being resolved}
 * as an input, to support extraction of arbitrary information from it about what needs fixing</li>
 * <li>a simpler one that {@linkplain #create(int, String, String, Class, BiFunction) uses only the object targeted by the marker}.
 * This is convenient, for example, for a resolution that just deletes the object or that otherwise doesn't need additional information
 * from the marker</li>
 * </ul>
 *
 * @param <T>
 *            the model object class on which I resolve the problem
 */
public class SimpleModelEditMarkerResolution<T extends EObject> extends AbstractPapyrusWorkbenchMarkerResolution {

	private final String label;
	private final String description;

	private final Class<T> type;
	private final TriFunction<? super EditingDomain, ? super T, ? super IMarker, ? extends Command> commandFunction;

	private boolean canMultiFix = true;

	protected SimpleModelEditMarkerResolution(int problemID, String label, String description, Class<T> type, TriFunction<? super EditingDomain, ? super T, ? super IMarker, ? extends Command> commandFunction) {
		super(problemID);

		this.label = label;
		this.description = description;
		this.type = type;
		this.commandFunction = commandFunction;
	}

	/**
	 * Create a new marker resolution that edits the model.
	 *
	 * @param <T>
	 *            the kind of object to edit
	 * @param problemID
	 *            my marker resolution problem ID
	 * @param label
	 *            a label to present to the user for the marker resolution
	 * @param description
	 *            a description of the marker resolution to present to the user
	 * @param type
	 *            the type of object to edit to resolve the marker
	 * @param commandFunction
	 *            a function that creates a command to edit the object. It will receive an editing domain either from
	 *            an existing open editor or created on-the-fly for an off-line edit, plus the object loaded in that domain to edit
	 *            and the problem marker to be resolved
	 *
	 * @return the marker resolution
	 */
	public static <T extends EObject> SimpleModelEditMarkerResolution<T> create(int problemID, String label, String description, Class<T> type, TriFunction<? super EditingDomain, ? super T, ? super IMarker, ? extends Command> commandFunction) {
		return new SimpleModelEditMarkerResolution<>(problemID, label, description, type, commandFunction);
	}

	/**
	 * Create a new marker resolution that edits the model without reference to the specific problem marker being resolved.
	 *
	 * @param <T>
	 *            the kind of object to edit
	 * @param problemID
	 *            my marker resolution problem ID
	 * @param label
	 *            a label to present to the user for the marker resolution
	 * @param description
	 *            a description of the marker resolution to present to the user
	 * @param type
	 *            the type of object to edit to resolve the marker
	 * @param commandFunction
	 *            a function that creates a command to edit the object. It will receive an editing domain either from
	 *            an existing open editor or created on-the-fly for an off-line edit, plus the object loaded in that domain to edit
	 *
	 * @return the marker resolution
	 */
	public static <T extends EObject> SimpleModelEditMarkerResolution<T> create(int problemID, String label, String description, Class<T> type, BiFunction<? super EditingDomain, ? super T, ? extends Command> commandFunction) {
		return new SimpleModelEditMarkerResolution<>(problemID, label, description, type,
				(domain, object, __) -> commandFunction.apply(domain, object));
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void run(IMarker marker) {
		run(new IMarker[] { marker }, new NullProgressMonitor());
	}

	@Override
	public void run(IMarker[] markers, IProgressMonitor monitor) {
		Multimap<Try<EditingDomain>, IMarker> openDomains = getOpenEditingDomains(markers);
		if (openDomains.keySet().stream().anyMatch(Try::isFailure)) {
			// User cancelled prompt to save a dirty editor
			if (monitor != null) {
				monitor.setCanceled(true);
			}
			return;
		}

		// Count the map size once for preparation of commands and once for execution
		SubMonitor sub = SubMonitor.convert(monitor, 2 * openDomains.size());

		try {
			for (Map.Entry<Try<EditingDomain>, Collection<IMarker>> next : openDomains.asMap().entrySet()) {
				Try<EditingDomain> openDomain = next.getKey();
				Collection<? extends IMarker> partition = next.getValue();

				EditingDomain domain = openDomain.toOptional().orElseGet(this::createEditingDomain);
				Adapter loadedResourceAdapter = new QuickFixLoadedResourcesAdapter(domain);

				try {
					CompoundCommand fixCommand = new CompoundCommand();

					for (IMarker marker : partition) {
						CommonMarkerResolutionUtils.getModelObject(marker, type, domain).ifPresent(object -> {
							Command command = commandFunction.apply(domain, object, marker);
							if (command != null) {
								fixCommand.append(new CommandWrapper(command) {
									@Override
									public void execute() {
										super.execute();
										sub.worked(1); // Executed
									}
								});
							}
							sub.worked(1); // Prepared
						});
					}

					Command command = fixCommand.unwrap();
					if (command.canExecute()) {
						domain.getCommandStack().execute(command);
					} else {
						// Skipped this partition for whatever reason
						sub.worked(partition.size());
					}
				} finally {
					// Save and unload any resources that the quick fix caused to be loaded and changed.
					// Note that if we created the editing domain, then this will unload all of the
					// resources in it, so any item-provider adapters will have been cleaned up, too
					loadedResourceAdapter.getTarget().eAdapters().remove(loadedResourceAdapter);

					// And then save the changes we made in the editor
					openDomain.flatMap(CommonMarkerResolutionUtils::getEditor).ifPresent(editor -> editor.getSite().getPage().saveEditor(editor, false));
				}
			}
		} finally {
			sub.done();
		}
	}

	/**
	 * Partition the given {@code markers} by resource and attempt to find an editing domain
	 * in an open editor for each.
	 *
	 * @param markers
	 *            markers to be fixed
	 * @return a partition of markers by resource with attempts to find open editing domains
	 */
	private Multimap<Try<EditingDomain>, IMarker> getOpenEditingDomains(IMarker[] markers) {
		// First, partition the markers by resource
		ListMultimap<IResource, IMarker> partitions = Multimaps.index(Arrays.asList(markers), IMarker::getResource);

		ImmutableListMultimap.Builder<Try<EditingDomain>, IMarker> result = ImmutableListMultimap.builder();
		for (IResource next : partitions.keySet()) {
			List<IMarker> nextPartition = partitions.get(next);

			IMarker marker = nextPartition.get(0);
			result.putAll(CommonMarkerResolutionUtils.getOpenEditingDomain(marker), nextPartition);
		}

		return result.build();
	}

	private EditingDomain createEditingDomain() {
		return new AdapterFactoryEditingDomain(
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
				new BasicCommandStack());
	}

	/**
	 * Disable fixing of multiple markers with this fix. This is usually only appropriate
	 * for a resolution that encodes a static change plan, not determined dynamically
	 * from the marker being fixed.
	 *
	 * @return myself for convenience of fluent configuration
	 */
	public SimpleModelEditMarkerResolution<T> disableMultiFix() {
		this.canMultiFix = false;
		return this;
	}

	/**
	 * Query whether I can fix multiple markers. This is usually only not the case
	 * for a resolution that encodes a static change plan, not determined dynamically
	 * from the marker being fixed.
	 *
	 * @return whether I can fix multiple markers
	 */
	public boolean canMultiFix() {
		return canMultiFix;
	}

	@Override
	protected Stream<IMarker> findSimilarMarkers(IMarker[] markers) {
		return canMultiFix() ? super.findSimilarMarkers(markers) : Stream.empty();
	}

	//
	// Nested types
	//

	/**
	 * An adapter on the resource-set that tracks resources loaded by the quick-fix command,
	 * so that when finished, they may be saved (if changed) and unloaded again to leave
	 * an open editor in ostensibly the same condition as the user last saw it.
	 */
	private final class QuickFixLoadedResourcesAdapter extends AdapterImpl {

		private final List<Resource> loadedByQuickFix = new ArrayList<>();

		QuickFixLoadedResourcesAdapter(EditingDomain domain) {
			super();

			domain.getResourceSet().eAdapters().add(this);
		}

		@Override
		public void notifyChanged(Notification msg) {
			if (msg.getFeatureID(ResourceSet.class) == ResourceSet.RESOURCE_SET__RESOURCES) {
				switch (msg.getEventType()) {
				case Notification.ADD:
					handleAdded((Resource) msg.getNewValue());
					break;
				case Notification.ADD_MANY:
					((Collection<?>) msg.getNewValue()).stream().map(Resource.class::cast).forEach(this::handleAdded);
					break;
				}
			}
		}

		@Override
		public void unsetTarget(Notifier oldTarget) {
			// Save any resources that the quick-fix loaded and changed, then unload them.
			loadedByQuickFix.forEach(this::saveAndUnload);
			loadedByQuickFix.clear();

			super.unsetTarget(oldTarget);
		}

		private void handleAdded(Resource resource) {
			loadedByQuickFix.add(resource);

			// We cannot rely on the "affected objects" of the commands to indicate which resources
			// are modified by it because, e.g., AddCommand's affected objects are the added objects,
			// not the object to which they are added. So, track modifications on every resource
			resource.setTrackingModification(true);
		}

		private void saveAndUnload(Resource resource) {
			// First, remove the resource from the resource set so that the editor doesn't see it
			// when the workspace notifies about save and get confused about conflicting edits
			// from outside the editor context because we are, in fact, doing this in the editor context
			resource.getResourceSet().getResources().remove(resource);

			try {
				if (resource.isModified()) {
					resource.save(Map.of(Resource.OPTION_SAVE_ONLY_IF_CHANGED, Resource.OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER));
				}
			} catch (IOException e) {
				Activator.log.error(NLS.bind(Messages.SimpleModelEditMarkerResolution_0, resource.getURI(), getLabel()), e);
			} finally {
				resource.unload();
			}
		}
	}
}

/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.dev.project.management.internal.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.VersionRange;

/**
 * A context in which bundle dependency analysis is computed. It maintains
 * shared state for analysis of the dependencies of any number of bundles
 * in the workspace and PDE target.
 */
public class DependencyAnalysisContext {

	private final Map<String, BundleAnalysis> bundles = new HashMap<>();
	private final Map<String, BundleAnalysis> packageProviders = new HashMap<>();

	private final VersionRules versionRules = new VersionRules();

	private final Deque<SubMonitor> monitorStack = new LinkedList<>();
	private SubMonitor currentMonitor;

	private final Set<BundleAnalysis> roots;

	public DependencyAnalysisContext(Collection<? extends IFile> bundleManifests) {
		super();

		pushMonitor(new NullProgressMonitor());

		roots = Collections.unmodifiableSet(init(bundleManifests));
	}

	private Set<BundleAnalysis> init(Collection<? extends IFile> bundleManifests) {
		return bundleManifests.stream()
				.map(this::getBundleID)
				.filter(Objects::nonNull)
				.map(this::internalGetBundle)
				.map(BundleAnalysis::checkCycle) // Bombs the analysis if there's any dependency cycle
				.collect(Collectors.toSet());
	}

	private String getBundleID(IFile manifest) {
		IPluginModelBase model = PluginRegistry.findModel(manifest.getProject());
		return (model == null) ? null : model.getPluginBase().getId();
	}

	public Set<BundleAnalysis> getAnalysisRoots() {
		return roots;
	}

	public boolean isAnalysisRoot(String bundleID) {
		return roots.contains(internalGetBundle(bundleID));
	}

	public BundleAnalysis getBundle(String bundleID) {
		return internalGetBundle(bundleID).analyze();
	}

	private BundleAnalysis internalGetBundle(String bundleID) {
		return bundles.computeIfAbsent(bundleID, BundleAnalysis::new);
	}

	public BundleAnalysis getProvidingBundle(String packageName) {

		if (packageProviders.containsKey(packageName)) {
			return packageProviders.get(packageName);
		} else {
			BundleAnalysis bundleAnalysis = findPackageProvider(packageName);
			packageProviders.put(packageName, bundleAnalysis);
			return bundleAnalysis;
		}
		// BundleAnalysis bundleAnalysis = packageProviders.computeIfAbsent(packageName, this::findPackageProvider);




	}

	private BundleAnalysis findPackageProvider(String packageName) {
		// Protect against concurrent modification
		return new ArrayList<>(bundles.values()).stream()
				.map(BundleAnalysis::getAPIExports)
				.filter(api -> api.exports(packageName))
				.findAny().map(APIExports::getBundle).orElse(null);
	}

	public final void pushMonitor(IProgressMonitor monitor) {
		currentMonitor = (monitor instanceof SubMonitor) ? (SubMonitor) monitor : SubMonitor.convert(monitor);
		monitorStack.push(currentMonitor);
	}

	public final void popMonitor() {
		monitorStack.pop();
		currentMonitor = monitorStack.peek();
	}

	//
	// Nested types
	//

	/**
	 * An analysis of the transitive dependencies (by <tt>Require-Bundle</tt>) and public
	 * API exports of a bundle. Bundles sort dependencies before dependents.
	 */
	public class BundleAnalysis {
		private final String bundleID;

		private DependencyGraph dependencyGraph;
		private APIExports apiExports;

		private BundleAnalysis(String bundleID) {
			super();

			this.bundleID = bundleID;
		}

		public String getBundleID() {
			return bundleID;
		}

		public BundleAnalysis checkCycle() throws IllegalStateException {
			getDependencyGraph().checkCycle();
			return this;
		}

		public VersionRange getCompatibleVersionRange() {
			return getAPIExports().getCompatibleVersionRange();
		}

		public DependencyGraph getDependencyGraph() {
			analyze();
			return dependencyGraph;
		}

		public APIExports getAPIExports() {
			analyze();
			return apiExports;
		}

		public BundleAnalysis getDependency(String bundleID) {
			return getDependencyGraph().getDependency(bundleID);
		}

		public boolean isReexported(String bundleID) {
			return getDependencyGraph().isReexported(bundleID);
		}

		/**
		 * Queries whether I express a dependency directly on the given bundle.
		 * Equivalent to {@link #hasDependency(String, boolean) hasDependency(bundleID, false)}.
		 *
		 * @param bundleID
		 *            a bundle identifier
		 * @return whether my bundle directly requires it
		 */
		public boolean hasDependency(String bundleID) {
			return getDependencyGraph().hasDependency(bundleID);
		}

		/**
		 * Queries whether I express a dependency directly or, optionally,
		 * indirectly on the given bundle.
		 *
		 * @param bundleID
		 *            a bundle identifier
		 * @param recursive
		 *            whether to consider transitive (indirect) dependencies
		 *
		 * @return whether my bundle directly requires it
		 */
		public boolean hasDependency(String bundleID, boolean recursive) {
			return getDependencyGraph().hasDependency(bundleID, recursive);
		}

		/**
		 * Obtains the set of bundles that should be re-exported that are not
		 * explicitly required (they mmust be implicitly required because some
		 * other dependency re-exports them).
		 *
		 * @return the missing re-exported dependency declarations
		 */
		public Set<BundleAnalysis> getMissingReexports() {
			return getAPIExports().getMissingReexports();
		}

		private BundleAnalysis analyze() {
			if (dependencyGraph == null) {
				dependencyGraph = new DependencyGraph(bundleID);
			}
			if (apiExports == null) {
				apiExports = new APIExports(bundleID);
			}

			return this;
		}

		public String toReexportDeclaration() {
			return String.format("%s;bundle-version=\"%s\";visibility:=reexport",
					getBundleID(),
					getCompatibleVersionRange());
		}

		/**
		 * For bundles that are workspace projects, gets the manifest file.
		 *
		 * @return the workspace bundle's manifest, or {@code null} if I am
		 *         a target bundle
		 */
		public IFile getManifest() {
			IFile result = null;

			IPluginModelBase model = PluginRegistry.findModel(getBundleID());
			IResource resource = (model == null) ? null : model.getUnderlyingResource();
			if (resource != null) {
				switch (resource.getType()) {
				case IResource.FILE:
					if ("MANIFEST.MF".equals(resource.getName())) {
						result = (IFile) resource;
					}
					break;
				case IResource.PROJECT:
					IFile manifest = ((IProject) resource).getFile("META-INF/MANIFEST.MF"); //$NON-NLS-1$
					if ((manifest != null) && manifest.isAccessible()) {
						result = manifest;
					}
					break;
				}
			}

			return result;
		}

		/**
		 * Queries whether I am an analysis root, which is a bundle selected
		 * by the user for optimization.
		 *
		 * @return whether I am an analysis root
		 */
		public boolean isAnalysisRoot() {
			return DependencyAnalysisContext.this.isAnalysisRoot(getBundleID());
		}

		/**
		 * A partial-ordering analoque of the {@link Comparable#compareTo(Object)} API.
		 * Bundles are only partially orderable by dependency relationships, so
		 * they are not actually {@link Comparable}.
		 *
		 * @param o
		 *            another analysis bundle
		 *
		 * @return my partial ordering relative to {@code o}
		 */
		public int partialCompare(BundleAnalysis o) {
			int result;

			if (o == this) {
				// Trivial case
				result = 0;
			} else if (this.hasDependency(o.getBundleID(), true)) {
				result = +1;
			} else if (o.hasDependency(this.getBundleID(), true)) {
				result = -1;
			} else {
				result = 0;
			}

			return result;
		}

		@Override
		public String toString() {
			return "Analysis of " + bundleID;
		}

		public final void pushMonitor(IProgressMonitor monitor) {
			DependencyAnalysisContext.this.pushMonitor(monitor);
		}

		public final void popMonitor() {
			DependencyAnalysisContext.this.popMonitor();
		}
	}

	/**
	 * An analysis of the dependency graph (by <tt>Require-Bundle</tt>) of a bundle.
	 */
	public class DependencyGraph {
		private final String bundleID;

		private final Map<String, BundleAnalysis> dependencies = new HashMap<>();
		private final Map<String, BundleAnalysis> reexports = new HashMap<>();

		private Set<BundleAnalysis> cycle;
		private Set<String> transitiveDependencies;

		private DependencyGraph(String bundleID) {
			super();

			this.bundleID = bundleID;

			IPluginModelBase model = PluginRegistry.findModel(bundleID);
			if (model == null) {
				throw new IllegalArgumentException("No such bundle: " + bundleID);
			}

			BundleDescription desc = model.getBundleDescription();

			if (desc != null) {
				for (BundleSpecification next : desc.getRequiredBundles()) {
					// Optional dependencies may as well not be defined. If they are
					// exposed by the API, they cannot be optional

					if (next.isResolved() && !next.isOptional()
					// The org.eclipse.utp.upr bundle actually does this!
							&& !bundleID.equals(next.getName())) {
						BundleAnalysis dep = internalGetBundle(next.getSupplier().getName());

						dependencies.put(dep.getBundleID(), dep);
						if (next.isExported()) {
							reexports.put(dep.getBundleID(), dep);
						}
					}
				}
			}
		}

		public BundleAnalysis getBundle() {
			return internalGetBundle(bundleID);
		}

		public BundleAnalysis getDependency(String bundleID) {
			BundleAnalysis result = dependencies.get(bundleID);

			if (result == null) {
				// Maybe it's a transitive dependency
				result = dependencies.values().stream()
						.map(bundle -> bundle.getDependency(bundleID))
						.filter(Objects::nonNull)
						.findAny().orElse(null);
			}

			return result;
		}

		public boolean isReexported(String bundleID) {
			// I trivially "re-export" myself
			boolean result = this.bundleID.equals(bundleID) || reexports.containsKey(bundleID);

			if (!result) {
				// Maybe it's a transitive re-export
				result = reexports.values().stream()
						.anyMatch(bundle -> bundle.isReexported(bundleID));
			}

			return result;
		}

		/**
		 * Queries whether I express a dependency directly on the given bundle.
		 * Equivalent to {@link #hasDependency(String, boolean) hasDependency(bundleID, false)}.
		 *
		 * @param bundleID
		 *            a bundle identifier
		 * @return whether my bundle directly requires it
		 */
		public boolean hasDependency(String bundleID) {
			return dependencies.containsKey(bundleID);
		}

		/**
		 * Queries whether I express a dependency directly or, optionally,
		 * indirectly on the given bundle.
		 *
		 * @param bundleID
		 *            a bundle identifier
		 * @param recursive
		 *            whether to consider transitive (indirect) dependencies
		 *
		 * @return whether my bundle directly requires it
		 */
		public boolean hasDependency(String bundleID, boolean recursive) {
			boolean result = hasDependency(bundleID);

			if (!result && recursive) {
				result = transitiveDependencies.contains(bundleID);
			}

			return result;
		}

		public boolean isRedundant(String bundleID) {
			boolean result;

			if (isReexported(bundleID)) {
				// If it's re-exported, then it's only redundant if it is also re-exported
				// by some other bundle that I re-export. Account for re-exports that we
				// would be adding
				result = Stream.concat(reexports.values().stream(), getBundle().getMissingReexports().stream())
						.filter(bundle -> !bundleID.equals(bundle.getBundleID())) // Excluding the bundle itself, of course!
						.anyMatch(bundle -> bundle.isReexported(bundleID));
			} else {
				// Otherwise, it's redundant if it's re-exported by any of our dependencies
				result = dependencies.values().stream()
						.filter(bundle -> !bundle.getBundleID().equals(bundleID))
						.anyMatch(bundle -> bundle.isReexported(bundleID));
			}

			return result;
		}

		public void checkCycle() throws IllegalStateException {
			if (cycle == null) {
				cycle = computeCycle(new LinkedHashSet<>());
			}

			if (!cycle.isEmpty()) {
				List<String> list = cycle.stream()
						.map(BundleAnalysis::getBundleID)
						.collect(Collectors.toList());
				list.add(cycle.iterator().next().getBundleID()); // Close the cycle
				throw new IllegalStateException("Dependency cycle detected: " + list);
			}
		}

		// use a LinkedHashSet specifically because the cycle has a defined order
		private Set<BundleAnalysis> computeCycle(LinkedHashSet<BundleAnalysis> trace) {
			if (cycle != null) {
				// I've already been validated, so there's no possibility of finding
				// a new cycle in me
				return cycle;
			}

			BundleAnalysis self = getBundle();

			if (!trace.add(self)) {
				// We closed the cycle. Trim up to the first occurrence of myself
				for (Iterator<BundleAnalysis> iter = trace.iterator(); iter.hasNext();) {
					if (iter.next() == self) {
						break;
					} else {
						iter.remove();
					}
				}
				cycle = trace;
			} else {
				// Take this opportunity when we are exhaustively looking for a cycle, anyways,
				// to compute everybody's transitive dependencies
				transitiveDependencies = new HashSet<>();

				// We didn't close the cycle, so keep looking
				for (BundleAnalysis next : dependencies.values()) {
					DependencyGraph child = next.getDependencyGraph();
					cycle = child.computeCycle(trace);
					if (!cycle.isEmpty()) {
						// Got a cycle
						break;
					} else {
						// Collect its dependencies and its transitive dependencies
						transitiveDependencies.addAll(child.dependencies.keySet());
						transitiveDependencies.addAll(child.transitiveDependencies);
					}
				}

				// Cycle could be null if I had no dependencies
				if ((cycle == null) || cycle.isEmpty()) {
					// Some of my direct dependencies could be dependencies of some of
					// my transitive dependencies, but they are direct to me
					transitiveDependencies.removeAll(dependencies.keySet());

					// Backtrack
					trace.remove(self);

					// And mark me cycle-free
					cycle = Collections.emptySet();
				}
			}

			return cycle;
		}

		public void removeDependency(String bundleID) {
			dependencies.remove(bundleID);

			// It's now just a transitive dependency because it was redundant
			transitiveDependencies.add(bundleID);

			// And it obviously can't be reexported
			reexports.remove(bundleID);
		}

		public void reexport(String bundleID) {
			reexports.put(bundleID, internalGetBundle(bundleID));

			// This information is now stale
			getBundle().getAPIExports().recomputeMissingReexports();
		}

		@Override
		public String toString() {
			return "Dependencies of " + bundleID;
		}
	}

	/**
	 * An analysis of the public API exports of a bundle.
	 */
	public class APIExports {
		private final String bundleID;
		private VersionRange compatibleVersionRange;

		private Set<String> exports;
		private Map<String, BundleAnalysis> exposedDependencies;
		private Set<BundleAnalysis> missingReexports;

		private APIExports(String bundleID) {
			super();

			this.bundleID = bundleID;
		}

		public BundleAnalysis getBundle() {
			return internalGetBundle(bundleID);
		}

		public VersionRange getCompatibleVersionRange() {
			return compatibleVersionRange;
		}

		public boolean exports(String packageName) {
			analyze();

			return exports.contains(packageName);
		}

		public boolean isExposed(String bundleID) {
			analyze();

			return exposedDependencies.containsKey(bundleID);
		}

		public Set<BundleAnalysis> getExposedDependencies() {
			analyze();

			return new HashSet<>(exposedDependencies.values());
		}

		/**
		 * Obtains the set of bundles that should be re-exported that are not
		 * explicitly required (they mmust be implicitly required because some
		 * other dependency re-exports them).
		 *
		 * @return the missing re-exported dependency declarations
		 */
		public Set<BundleAnalysis> getMissingReexports() {
			if (missingReexports == null) {
				missingReexports = getExposedDependencies().stream()
						.filter(bundle -> !getBundle().isReexported(bundle.getBundleID()))
						.filter(bundle -> !getBundle().hasDependency(bundle.getBundleID()))
						.filter(bundle -> !isReexportedByExposedDependency(bundle))
						.collect(Collectors.toCollection(
								() -> new TreeSet<>(Comparator.comparing(BundleAnalysis::getBundleID))));
			}

			return missingReexports;
		}

		void recomputeMissingReexports() {
			missingReexports = null;
		}

		private APIExports analyze() {
			if (exposedDependencies == null) {
				exposedDependencies = new HashMap<>();

				IPluginModelBase model = PluginRegistry.findModel(bundleID);
				BundleDescription desc = model.getBundleDescription();
				IProject project = (model.getUnderlyingResource() == null)
						? null
						: model.getUnderlyingResource().getProject();
				BundleAnalysis self = getBundle();

				if (desc == null) {
					// No exports if no bundle description
					exports = Collections.emptySet();
					compatibleVersionRange = VersionRange.valueOf("0.0.0"); //$NON-NLS-1$
				} else {
					compatibleVersionRange = versionRules.getDependencyVersionRange(
							DependencyKind.REQUIRE_BUNDLE, bundleID);

					if ((project == null) || !isAnalysisRoot(bundleID)) {
						// It's a PDE target bundle. We don't need to compute uses constraints
						// for it because we won't be attempting to edit its dependencies
						exports = Stream.of(desc.getExportPackages())
								.filter(this::isPublicExport)
								.map(ExportPackageDescription::getName)
								.collect(Collectors.toSet());
					} else {
						// It's a workspace bundle that is selected for optimization.
						// We need to compute uses constraints for it
						Map<String, ? extends Set<String>> uses = new MyCalculateUsesOperation(project, model).calculate();

						currentMonitor.setTaskName("Computing re-exported dependencies...");
						exports = new HashSet<>(uses.keySet());
						Set<String> allUsedPackages = uses.values().stream()
								.flatMap(Collection::stream)
								.distinct()
								.collect(Collectors.toSet());

						// Don't consider my own exported packages, of course
						allUsedPackages.removeAll(exports);

						for (String next : allUsedPackages) {
							BundleAnalysis provider = getProvidingBundle(next);
							if ((provider != null) && (provider != self) && !exposedDependencies.containsKey(provider.getBundleID())) {
								exposedDependencies.put(provider.getBundleID(), provider);
							}
						}
					}
				}

				exports.forEach(x -> packageProviders.put(x, self));
			}

			return this;
		}

		private boolean isPublicExport(ExportPackageDescription exportPackage) {
			Map<String, String> directives = exportPackage.getDeclaredDirectives();
			return !"true".equals(directives.get("x-internal"))
					&& !directives.containsKey("x-friends");
		}

		/**
		 * Queries whether a {@code bundle} is re-exported by some existing dependency
		 * that is exposed in the API. Such a bundle would not have to be added as
		 * a missing re-export.
		 *
		 * @param bundle
		 *            an exposed bundle
		 *
		 * @return whether it is re-exported by some other bundle that I expose
		 */
		private boolean isReexportedByExposedDependency(BundleAnalysis bundle) {
			return exposedDependencies.values().stream()
					.anyMatch(dep -> dep.isReexported(bundle.getBundleID()));
		}

		@Override
		public String toString() {
			return "API of " + bundleID;
		}

		//
		// Nested types
		//

		@SuppressWarnings("restriction")
		private final class MyCalculateUsesOperation extends org.eclipse.pde.internal.ui.search.dependencies.CalculateUsesOperation {
			MyCalculateUsesOperation(IProject project, IPluginModelBase model) {
				// This cast is safe if the model if the model is a workspace bundle project
				super(project, (org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase) model);
			}

			Map<String, ? extends Set<String>> calculate() {
				Map<String, ? extends Set<String>> result;

				Collection<String> packages = getPublicExportedPackages();
				if (packages.isEmpty()) {
					result = Collections.emptyMap();
				} else {
					result = findPackageReferences(packages, currentMonitor.split(1)); // Split doesn't matter for unknown total work
				}

				return result;
			}
		}
	}
}

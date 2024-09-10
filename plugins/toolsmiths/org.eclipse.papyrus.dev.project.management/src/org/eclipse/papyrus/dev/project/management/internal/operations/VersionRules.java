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

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.ExportPackageDescription;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * Utility for management of bundle dependency version ranges, defining rules
 * governing how various specific dependencies are to be managed.
 */
public class VersionRules {

	private final VersionRule rule = VersionRule.composeAll(
			new CurrentVersionRule(), // The default behaviour
			new FixedRangeRule(dep -> dep.startsWith("org.apache.batik"), "[1.6.0,1.7.0)"), //$NON-NLS-1$//$NON-NLS-2$
			new SkipRule("com.ibm.icu") //$NON-NLS-1$
	,new FixedRangeRule("com.google.guava", "32.1.3") //$NON-NLS-1$
	);

	public VersionRules() {
		super();
	}

	/**
	 * Queries the version range that should constraint a dependency of the given {@code kind}
	 * on a bundle or package.
	 *
	 * @param kind
	 *            the dependency kind
	 * @param dependency
	 *            the bundle or package dependency
	 *
	 * @return the appropriate version range, or {@code null} if unknown or the range
	 *         currently specified in the manfiest can be retained as is
	 */
	public VersionRange getDependencyVersionRange(DependencyKind kind, String dependency) {
		return rule.apply(kind, dependency);
	}

	static Version getCurrentMinorVersion(String dependencyName, boolean isPackageImport) {
		Version result = Version.emptyVersion;

		if (!isPackageImport) {
			IPluginModelBase model = PluginRegistry.findModel(dependencyName);
			if (model != null) {
				result = Version.valueOf(model.getPluginBase().getVersion());
			}
		} else {
			// Look for a bundle that exports the package
			result = Stream.of(PluginRegistry.getActiveModels(false))
					.filter(p -> exports(p, dependencyName))
					// TODO: Sort by longest prefix match to the package name
					.map(p -> getExportedVersion(p, dependencyName))
					.findFirst()
					.orElse(Version.emptyVersion);
		}

		if (!result.equals(Version.emptyVersion)) {
			// 'Minorize' it
			result = new Version(result.getMajor(), result.getMinor(), 0);
		}

		return result;
	}

	private static boolean exports(IPluginModelBase pluginModel, String packageName) {
		boolean result = false;

		BundleDescription desc = pluginModel.getBundleDescription();
		if (desc != null) {
			result = Stream.of(desc.getExportPackages())
					.anyMatch(ep -> packageName.equals(ep.getName()));
		}

		return result;
	}

	private static Version getExportedVersion(IPluginModelBase pluginModel, String packageName) {
		// In case there is no bundle description, which would be odd at this point
		Version result = Version.emptyVersion;

		BundleDescription desc = pluginModel.getBundleDescription();
		if (desc != null) {
			result = Stream.of(desc.getExportPackages())
					.filter(ep -> packageName.equals(ep.getName()))
					.map(ExportPackageDescription::getVersion)
					.filter(Objects::nonNull)
					.findAny().orElse(Version.emptyVersion);
		}

		return result;
	}

	//
	// Nested types
	//

	private interface VersionRule extends BiPredicate<DependencyKind, String>, BiFunction<DependencyKind, String, VersionRange> {
		default VersionRule compose(VersionRule other) {
			return new VersionRule() {

				@Override
				public VersionRange apply(DependencyKind kind, String dependencyName) {
					return VersionRule.this.test(kind, dependencyName)
							? VersionRule.this.apply(kind, dependencyName)
							: other.apply(kind, dependencyName);
				}

				@Override
				public boolean test(DependencyKind kind, String dependencyName) {
					return VersionRule.this.test(kind, dependencyName) || other.test(kind, dependencyName);
				}
			};
		}

		static VersionRule composeAll(VersionRule default_, VersionRule... rules) {
			VersionRule result = default_;
			for (VersionRule next : rules) {
				result = next.compose(result);
			}
			return result;
		}
	}

	private static abstract class AbstractRule implements VersionRule {
		private final BiPredicate<DependencyKind, String> predicate;

		AbstractRule(BiPredicate<DependencyKind, String> predicate) {
			super();

			this.predicate = predicate;
		}

		AbstractRule(Predicate<String> predicate) {
			this((kind, dep) -> predicate.test(dep));
		}

		@Override
		public boolean test(DependencyKind kind, String dependencyName) {
			return predicate.test(kind, dependencyName);
		}
	}

	private static final class FixedRangeRule extends AbstractRule {
		private final VersionRange range;

		FixedRangeRule(String bundleID, String range) {
			this(bundleID::equals, VersionRange.valueOf(range));
		}

		FixedRangeRule(Predicate<String> predicate, String range) {
			this(predicate, VersionRange.valueOf(range));
		}

		FixedRangeRule(Predicate<String> predicate, VersionRange range) {
			super(predicate);

			this.range = range;
		}

		@Override
		public VersionRange apply(DependencyKind kind, String dependencyName) {
			return range;
		}
	}

	private static final class SkipRule extends AbstractRule {
		SkipRule(String dependencyName) {
			this(dependencyName::equals);
		}

		SkipRule(Predicate<String> predicate) {
			super(predicate);
		}

		@Override
		public VersionRange apply(DependencyKind kind, String dependencyName) {
			return null;
		}
	}

	private static class CurrentVersionRule extends AbstractRule {
		private CurrentVersionRule() {
			super((kind, dep) -> true);
		}

		@Override
		public VersionRange apply(DependencyKind kind, String dependencyName) {
			Version current = getCurrentMinorVersion(dependencyName, kind == DependencyKind.IMPORT_PACKAGE);
			Version next = ((current == null) || current.equals(Version.emptyVersion))
					? null
					: new Version(current.getMajor() + 1, 0, 0);

			return (next == null)
					? null
					: new VersionRange(VersionRange.LEFT_CLOSED, current, next, VersionRange.RIGHT_OPEN);
		}
	}
}

/*****************************************************************************
 * Copyright (c) 2012, 2017, 2021, 2023 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - Skip the feature-version test when running in development mode
 *  Christian W. Damus - bugs 433206, 485220
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 513963
 *  Quentin Le Menez (CEA LIST) quentin.lemenez@cea.fr - Bug 570716
 *  Vincent Lorenzo (CEA LIST) vincent.Lorenzo@cea.fr - Bug 582667
 *****************************************************************************/
package org.eclipse.papyrus.bundles.tests;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.internal.bundles.tests.PapyrusBundleDescription;
import org.eclipse.papyrus.internal.bundles.tests.PapyrusBundleDescriptionRegistry;
import org.eclipse.papyrus.junit.framework.classification.InvalidTest;
import org.eclipse.papyrus.junit.framework.classification.NotImplemented;
import org.eclipse.papyrus.junit.framework.classification.rules.Condition;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.Bundle;

/**
 * Suppress restriction for org.eclipse.pde.internal.core.feature.Feature.
 *
 * @author Vincent Lorenzo
 *
 * @noreference This class is not intended to be referenced by clients.
 */
public class BundlesTests extends AbstractPapyrusTest {

	// Indicates that the bundle name must contain the (Incubation) string
	// private static final String REGEX_INCUBATION = ".*\\(Incubation\\)"; //$NON-NLS-1$

	private static final String INCUBATION_KEYWORD = "(Incubation)"; //$NON-NLS-1$

	private static final String BATIK_VERSION = "\"[1.17.0,1.18.0)\""; //$NON-NLS-1$

	private static final String INJECT_VERSION = "\"[7.0.0,8.0.0)\""; //$NON-NLS-1$

	private static final String NATTABLE_VERSION = "1.5.0"; //$NON-NLS-1$

	private static final String GLAZED_LIST_VERSION = "1.9.0";//$NON-NLS-1$

	private static final String GUAVA_VERSION = "33.1.0"; //$NON-NLS-1$

	private static final String GSON_VERSION = "2.9.1"; //$NON-NLS-1$

	private static final String UML2_UML_VERSION_RANGE = "bundle-version=\"[5.3.0,6.0.0)\"";//$NON-NLS-1$

	@Condition
	public final boolean isAutomatedBuild = JUnitUtils.isAutomatedBuildExecution();

	/**
	 * Tests that all Papyrus Bundle name are finished by {@link #INCUBATION}
	 */
	@InvalidTest("Some Papyrus bundles are still incubating (Extra...)")
	@Test
	public void incubationTest() {
		org.hamcrest.Matcher<String> matcher = new BaseMatcher<>() {

			@Override
			public boolean matches(Object item) {
				return item instanceof String && !((String) item).contains(INCUBATION_KEYWORD);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Does not contain "); //$NON-NLS-1$
				description.appendText(INCUBATION_KEYWORD);
			}
		};
		testManifestProperty(BundleTestsUtils.BUNDLE_NAME, matcher, false, false);
	}

	/**
	 * Tests the provider name (should be EL
	 */
	@Test
	public void vendorTest() {
		testManifestProperty(BundleTestsUtils.BUNDLE_VENDOR, BundleTestsUtils.VENDOR_NAME, false, false);
	}

	/**
	 * Tests if the file about.html is included to the plugin
	 */
	@Test
	public void aboutTest() {
		fileTest("/about.html"); //$NON-NLS-1$
	}

	/**
	 * Tests the java version
	 */
	@Test
	public void javaVersionTest() {
		boolean onlyOnJavaProject = true;
		String message = null;
		int nb = 0;
		Pattern bundleRequirementPattern = Pattern.compile(BundleTestsUtils.JAVA_VERSION_17);
		Pattern capabilityPattern = Pattern.compile(BundleTestsUtils.JAVA_17_CAPABILITY);


		for (final Bundle current : BundleTestsUtils.getPapyrusBundles()) {

			if (onlyOnJavaProject && !BundleTestsUtils.isJavaProject(current)) {
				continue; // useful for oep.infra.gmfdiag.css.theme for example
			}
			boolean result = false;

			String value = current.getHeaders().get(BundleTestsUtils.BUNDLE_REQUIREDEXECUTIONENVIRONMENT);
			if (value != null) {
				result = bundleRequirementPattern.matcher(value).matches(); // Don't fail yet if invalid
			} else {
				value = current.getHeaders().get(BundleTestsUtils.REQUIRE_CAPABILITY);
				result = capabilityPattern.matcher(value).matches(); // Don't fail yet if invalid
			}
			if (!result) {
				if (message == null) {
					message = "Wrong java version for :"; //$NON-NLS-1$
				}
				message += "\n "; //$NON-NLS-1$
				message += current.getSymbolicName();
				nb++;
			}
		}
		Assert.assertNull(nb + " problems!", message); //$NON-NLS-1$
	}

	/**
	 * Tests that we don't use import package
	 */
	@NotImplemented("Usage of importPackage is discouraged")
	@Test
	public void importPackage() {
		testManifestProperty(BundleTestsUtils.BUNDLE_IMPORT_PACKAGE, "", true, false); //$NON-NLS-1$
	}

	/**
	 * Tests if a the value of a property in the Manifest is correct
	 *
	 * @param property
	 *            the property to test
	 * @param regex
	 *            the regular expression to test the property
	 * @param mustBeNull
	 *            indicates that the value for the property must be <code>null</code>
	 * @param onlyOnJavaProject
	 *            boolean indicating if the tests should only be done on
	 *            JavaProject
	 */
	private void testManifestProperty(final String property, final String regex, final boolean mustBeNull, final boolean onlyOnJavaProject) {
		org.hamcrest.Matcher<String> regexMatcher = new org.hamcrest.BaseMatcher<>() {

			@Override
			public boolean matches(Object item) {
				return item instanceof String && ((String) item).matches(regex);
			}

			@Override
			public void describeTo(Description description) {
				description.appendText("Matching regex("); //$NON-NLS-1$
				description.appendValue(regex);
				description.appendText(")"); //$NON-NLS-1$
			}

		};

		testManifestProperty(property, regexMatcher, mustBeNull, onlyOnJavaProject);
	}

	private void testManifestProperty(final String property, final org.hamcrest.Matcher<String> matcher, final boolean mustBeNull, final boolean onlyOnJavaProject) {
		String message = null;
		int nb = 0;
		for (final Bundle current : BundleTestsUtils.getPapyrusBundles()) {
			if (onlyOnJavaProject && !BundleTestsUtils.isJavaProject(current)) {
				continue; // useful for oep.infra.gmfdiag.css.theme for example
			}
			final String value = current.getHeaders().get(property);
			boolean result = false;
			if (mustBeNull) {
				result = (value == null);
			} else if (value != null) {
				result = matcher.matches(value); // Don't fail yet if invalid
			}
			if (!result) {
				if (message == null) {
					message = "Wrong " + property + " for :"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				message += "\n "; //$NON-NLS-1$
				message += current.getSymbolicName();
				nb++;
			}
		}
		Assert.assertNull(nb + " problems!", message); //$NON-NLS-1$
	}

	/**
	 * Tests if the file is owned by the bundle
	 *
	 * @param filepath
	 *            the file path
	 */
	private void fileTest(final String filepath) {
		StringBuffer buffer = new StringBuffer();
		int nb = 0;
		for (final Bundle current : BundleTestsUtils.getPapyrusBundles()) {
			URL url = current.getEntry(filepath);
			if (url == null) {
				if (buffer.length() == 0) {
					buffer.append(NLS.bind("The following bundles don't have the file {0}.", filepath)); //$NON-NLS-1$
				}
				buffer.append("\n");//$NON-NLS-1$
				buffer.append(current.getSymbolicName());
				nb++;
			}
		}
		StringBuffer errorMessage = new StringBuffer();
		errorMessage.append(nb);
		errorMessage.append(" problems!\n"); //$NON-NLS-1$
		errorMessage.append(buffer.toString());
		Assert.assertTrue(errorMessage.toString(), buffer.toString().isEmpty());
	}

	/**
	 * We want that all Papyrus batik dependencies will be defines
	 */
	@Test
	public void batikDependencyVersionTest() {
		testPapyrusDependencies("org.apache.batik", BATIK_VERSION, Collections.emptyList());//$NON-NLS-1$
	}

	@Test
	public void natTableDependencyVersionTest() {
		testPapyrusDependencies("org.eclipse.nebula.widgets.nattable", NATTABLE_VERSION, Collections.singletonList("org.eclipse.nebula.widgets.nattable.extension.nebula"));//$NON-NLS-1$ //$NON-NLS-2$
	}

	@Test
	public void glazedListDependencyVersionTest() {
		testPapyrusDependencies("ca.odell.glazedlists", GLAZED_LIST_VERSION, Collections.emptyList());//$NON-NLS-1$
	}

	@Test
	public void guavaDependencyVersionTest() {
		testPapyrusDependencies("com.google.guava", GUAVA_VERSION, Collections.emptyList());//$NON-NLS-1$
	}

	@Test
	public void injectDependencyVersionTest() {
		testPapyrusDependencies("com.google.inject", INJECT_VERSION, Collections.emptyList());//$NON-NLS-1$
	}

	@Test
	public void gsonDependencyVersionTest() {
		testPapyrusDependencies("com.google.gson", GSON_VERSION, Collections.emptyList());//$NON-NLS-1$
	}

	@Test
	public void uml2umlDependencyVersionTest() {
		testPapyrusDependencies("org.eclipse.uml2.uml", UML2_UML_VERSION_RANGE, //$NON-NLS-1$
				new ArrayList<>(Arrays.asList("org.eclipse.uml2.uml.profile.standard", //$NON-NLS-1$
						"org.eclipse.uml2.uml.validation", "org.eclipse.uml2.uml.editor"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public static final String REGEX_PACKAGE_WORD = "\\w(?:\\w|\\d)*";// match a //$NON-NLS-1$
																		// package
																		// name

	public static final String REGEX_PLUGIN = "(?:\\." + REGEX_PACKAGE_WORD + ")*";// match plugin name //$NON-NLS-1$ //$NON-NLS-2$

	public static final String REGEX_BUNDLE = "(?:;bundle-version=\"([^\"]*)\")?"; //$NON-NLS-1$

	public static final String REGEX_REEXPORT = "(?:;\\w*:=\\w*;\\w*-\\w*=\"([^\"]*)\")|"; //$NON-NLS-1$

	public static class Version {

		private boolean minIncluding;

		private boolean maxIncluding;

		private int[] min = null;

		private int[] max = null;

		public Version(final String versionAsString) {
			this.minIncluding = true;
			this.maxIncluding = true;
			if (versionAsString != null) {
				this.minIncluding = !versionAsString.startsWith("("); //$NON-NLS-1$
				this.maxIncluding = !versionAsString.endsWith(")"); //$NON-NLS-1$
				final Pattern versionNumber = Pattern.compile("\\d+(\\.\\d+)*"); //$NON-NLS-1$
				final Matcher matcher = versionNumber.matcher(versionAsString);
				while (matcher.find()) {
					final String grp = matcher.group();
					final String[] versions = grp.split("\\."); //$NON-NLS-1$
					int[] vers = new int[versions.length];
					for (int i = 0; i < versions.length; i++) {
						vers[i] = Integer.parseInt(versions[i]);
					}
					if (min == null) {
						min = vers;
					} else {
						max = vers;
					}
				}
			}
			if (min == null) {
				min = new int[] { 0, 0, 0 };
			}
			if (max == null) {
				max = new int[] { 99, 99, 99 };
			}
		}

		public boolean inIncludedIn(final Version version) {
			// verifying intersection between versions!
			if (compare(this.max, version.min) < 0) {
				return false;
			}
			if (compare(version.max, this.min) < 0) {
				return false;
			}
			if (compare(this.max, version.min) == 0 && (!this.maxIncluding || !version.minIncluding)) {
				return false;
			}
			if (compare(version.max, this.min) == 0 && (!version.maxIncluding || !this.minIncluding)) {
				return false;
			}

			// verifying inclusion
			if (compare(this.min, version.min) < 0) {
				return false;
			}

			if (compare(this.min, version.min) == 0 && (this.minIncluding != version.minIncluding)) {
				return false;
			}

			if (compare(this.max, version.max) > 0) {
				return false;
			}

			if (compare(this.max, version.max) == 0 && (this.maxIncluding != version.maxIncluding)) {
				return false;
			}
			return true;
		}

		/**
		 *
		 * @param first
		 * @param second
		 * @return
		 *         <ul>
		 *         <li>0 when they are equal</li>
		 *         <li>1 if first is greater than second</li>
		 *         <li>-1 if first is smaller than second</li>
		 *         </ul>
		 */
		protected int compare(int[] first, int[] second) {
			int min = Math.min(first.length, second.length);
			for (int i = 0; i < min; i++) {
				if (first[i] < second[i]) {
					return -1;
				} else if (first[i] > second[i]) {
					return 1;
				}
			}
			if (first.length == second.length) {
				return 0;
			} else if (first.length > second.length) {
				return 1;
			}
			return -1;
		}
	}

	/**
	 *
	 * @param partialDependencyName
	 *            the fullName or a part of the name of the plugin
	 * @param wantedBundleVersionRegex
	 *            a string like this : "bundle-version=\"[1.6.0,1.7.0)\""
	 * @deprecated since 2.0, use the method {@link BundlesTests#testPapyrusDependencies(String, String, List)} instead
	 */
	@Deprecated
	protected void testPapyrusDependencies2(final String partialDependencyName, final String wantedVersion) {
		testPapyrusDependencies(partialDependencyName, wantedVersion, Collections.emptyList());
	}

	/**
	 *
	 * @param partialDependencyName
	 *            the fullName or a part of the name of the plugin
	 * @param wantedBundleVersionRegex
	 *            a string like this : "bundle-version=\"[1.6.0,1.7.0)\""
	 * @param list
	 *            of dependencies to ignore
	 * @since 2.0
	 */
	protected void testPapyrusDependencies(final String partialDependencyName, final String wantedVersion, List<String> exceptions) {
		final StringBuilder builder = new StringBuilder();
		int nb = 0;
		final Version wanted = new Version(wantedVersion);
		for (final Bundle current : BundleTestsUtils.getPapyrusBundles()) {
			final String value = current.getHeaders().get(BundleTestsUtils.REQUIRE_BUNDLE);
			if (value == null) {
				continue;
			}

			// Pattern pattern = Pattern.compile("(" + partialDependencyName + REGEX_PLUGIN + ")" + REGEX_DEPENDENCY); //$NON-NLS-1$ //$NON-NLS-2$
			Pattern pattern = Pattern.compile("(" + partialDependencyName + REGEX_PLUGIN + ")" + "(" + REGEX_REEXPORT + REGEX_BUNDLE + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			Matcher matcher = pattern.matcher(value);
			final StringBuilder localBuilder = new StringBuilder();
			while (matcher.find()) {
				final String pluginName = matcher.group(1);
				if (exceptions.contains(pluginName)) {
					continue;
				}
				String versionString = null;
				if (matcher.groupCount() > 1) {
					versionString = matcher.group(2);
				}
				if (versionString == null) {
					if (localBuilder.length() == 0) {
						localBuilder.append(NLS.bind("Incorrect version for {0}, got {1} and not {2} \n", new String[] { pluginName, current.getSymbolicName(), wantedVersion })); //$NON-NLS-1$
					}
					localBuilder.append(NLS.bind("No Version number for {0}\n", pluginName)); //$NON-NLS-1$
					nb++;
				} else {
					Version version = new Version(versionString);
					if (!version.inIncludedIn(wanted)) {
						if (localBuilder.length() == 0) {
							localBuilder.append(NLS.bind("{0} incorrect required bundle-version:\n", current.getSymbolicName())); //$NON-NLS-1$
						}
						localBuilder.append(NLS.bind("Bad version for {0}, got {1} and not {2} \n", new String[] { pluginName, versionString, wantedVersion })); //$NON-NLS-1$
						nb++;
					}
				}
			}
			if (localBuilder.length() != 0) {
				builder.append(localBuilder.toString());
				builder.append("\n");//$NON-NLS-1$
			}
		}
		if (builder.length() != 0)

		{
			builder.insert(0, NLS.bind("{0} problems. We want this version : {1} for the plugin {2}\n", new String[] { Integer.toString(nb), wantedVersion, partialDependencyName })); //$NON-NLS-1$
		}
		Assert.assertTrue(builder.toString(), builder.length() == 0);
	}

	/**
	 * verify that the field PLUGIN_ID is equals to the plugin name
	 */
	@Test
	public void pluginIDTest() {
		StringBuffer errorMessage = new StringBuffer();
		StringBuffer warningMessage = new StringBuffer();
		final Collection<String> possibleIds = new ArrayList<>();
		possibleIds.add("ID");//$NON-NLS-1$
		possibleIds.add("PLUGIN_ID");//$NON-NLS-1$
		int nbError = 0;
		for (final Bundle current : BundleTestsUtils.getPapyrusBundles()) {
			if (!BundleTestsUtils.isJavaProject(current)) {
				continue; // useful for oep.infra.gmfdiag.css.theme for example
			}
			final String activator = current.getHeaders().get("Bundle-Activator"); //$NON-NLS-1$
			if (activator != null) {
				try {
					final Class<?> activatorClass = current.loadClass(activator);
					Field plugin_id_field = null;
					for (final Field currentField : activatorClass.getFields()) {
						final String fieldName = currentField.getName();
						if (possibleIds.contains(fieldName)) {
							plugin_id_field = currentField;
							break;
						}
					}
					if (plugin_id_field != null) {
						final String plugin_id = (String) plugin_id_field.get(activatorClass);
						if (!plugin_id.equals(current.getSymbolicName())) {
							nbError++;
							errorMessage.append(NLS.bind("The field PLUGIN_ID of the plugin {0} is not equals to the plugin name.\n", current.getSymbolicName())); //$NON-NLS-1$
						}
					} else {
						// Never happens. An exception is thrown.
						warningMessage.append(NLS.bind("The activator of {0} has no field named PLUGIN_ID.\n", current.getSymbolicName())); //$NON-NLS-1$
					}
				} catch (final Exception e) {
					errorMessage.append(NLS.bind("Exception occured with the plugin {0} \n {1} \n", new Object[] { current.getSymbolicName(), e })); //$NON-NLS-1$
				}
			}

		}
		StringBuffer finalErrorMessage = new StringBuffer();
		finalErrorMessage.append(nbError);
		finalErrorMessage.append(" problems! ");//$NON-NLS-1$
		finalErrorMessage.append(errorMessage);
		Assert.assertTrue(finalErrorMessage.toString(), nbError == 0);

		// Do not fail on warnings
		// Assert.assertTrue(nbWarning + "warning!" + warningMessage, nbWarning == 0);//$NON-NLS-1$
	}

	/**
	 * This test checks that nobody adds an unexpected dependency to the plugin org.eclipse.papyrus.emf
	 *
	 * @since 1.3.0
	 */
	@Test
	public void checkPapyrusEMFPluginDependency() {
		final Version undefinedVersion = new Version(null);
		final String bundleIDToCheck = "org.eclipse.papyrus.emf"; //$NON-NLS-1$
		final Map<String, Version> dependencies = new HashMap<>();
		dependencies.put("org.eclipse.core.resources", undefinedVersion); //$NON-NLS-1$
		dependencies.put("org.eclipse.core.runtime", undefinedVersion); //$NON-NLS-1$
		dependencies.put("org.eclipse.emf.ecore.xmi", undefinedVersion); //$NON-NLS-1$
		dependencies.put("org.eclipse.papyrus.infra.core.log", undefinedVersion); //$NON-NLS-1$
		dependencies.put("org.eclipse.pde.core", undefinedVersion); //$NON-NLS-1$
		strictCheckOfDependenciesList(bundleIDToCheck, dependencies, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
	}

	/**
	 * This test checks that nobody adds an unexpected dependency to the plugin org.eclipse.papyrus.emf.ui
	 *
	 * @since 2.0.0
	 */
	@Test
	public void checkPapyrusEMFUIPluginDependency() {
		final Version undefinedVersion = new Version(null);
		final String bundleIDToCheck = "org.eclipse.papyrus.emf.ui"; //$NON-NLS-1$
		final Map<String, Version> dependencies = new HashMap<>();
		dependencies.put("org.eclipse.core.runtime", undefinedVersion); //$NON-NLS-1$
		dependencies.put("org.eclipse.emf.edit.ui", undefinedVersion); //$NON-NLS-1$
		dependencies.put("org.eclipse.ui.views.properties.tabbed", undefinedVersion); //$NON-NLS-1$
		strictCheckOfDependenciesList(bundleIDToCheck, dependencies, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
	}

	/**
	 *
	 * This method check all dependencies on the bundle. The version are checked using the method {@link Version#inIncludedIn(Version)}
	 * If there are more or less dependencies in the tested bundle, the test will fails
	 *
	 * @param bundleSymbolicNameToCheck
	 *            the name of the bundle to check
	 * @param bundleDependenciesWithVersion
	 *            the complete list of the dependencies to have for this bundle
	 * @param reexportedDependencies
	 *            the completed list of re-exported dependencies for this bundle
	 * @param greedyDependencies
	 *            the complete list of greedy dependencies for this bundle
	 * @param optionalDependencies
	 *            the complete list of optional dependencies for this bundle
	 *
	 * @since 1.3
	 */
	private void strictCheckOfDependenciesList(final String bundleSymbolicNameToCheck, final Map<String, Version> bundleDependenciesWithVersion, final Set<String> reexportedDependencies, final Set<String> greedyDependencies,
			final Set<String> optionalDependencies) {

		Assert.assertNotNull(bundleSymbolicNameToCheck);
		Assert.assertNotNull(bundleDependenciesWithVersion);
		Assert.assertNotNull(reexportedDependencies);
		Assert.assertNotNull(greedyDependencies);
		Assert.assertNotNull(optionalDependencies);

		final PapyrusBundleDescription bundleToCheck = PapyrusBundleDescriptionRegistry.INSTANCE.getPapyrusBundleDescription(bundleSymbolicNameToCheck);
		Assert.assertNotNull(NLS.bind("The bundle {0} has not been found.", bundleSymbolicNameToCheck), bundleToCheck); //$NON-NLS-1$

		final Set<String> expectedDependencies = bundleDependenciesWithVersion.keySet();
		Assert.assertEquals("The current dependencies are not the expected ones.", expectedDependencies, PapyrusBundleDescription.asSymbolicNameSet(bundleToCheck.getDependencies())); //$NON-NLS-1$

		Assert.assertEquals("The current reexported dependencies are not the expected ones.", reexportedDependencies, PapyrusBundleDescription.asSymbolicNameSet(bundleToCheck.getReexportedDependencies())); //$NON-NLS-1$

		Assert.assertEquals("The current greedy dependencies are not the expected ones.", greedyDependencies, PapyrusBundleDescription.asSymbolicNameSet(bundleToCheck.getGreedyDependencies())); //$NON-NLS-1$

		Assert.assertEquals("The current optional dependencies are not the expected ones.", optionalDependencies, PapyrusBundleDescription.asSymbolicNameSet(bundleToCheck.getOptionalDependencies())); //$NON-NLS-1$


		// we check the dependency range
		for (final Entry<String, Version> current : bundleDependenciesWithVersion.entrySet()) {
			Assert.assertTrue(NLS.bind("The dependency {0} is not registered with compatible version range", current.getKey()), bundleToCheck.getRegisteredDependencyVersion(current.getKey()).inIncludedIn(current.getValue())); //$NON-NLS-1$
		}

	}



}

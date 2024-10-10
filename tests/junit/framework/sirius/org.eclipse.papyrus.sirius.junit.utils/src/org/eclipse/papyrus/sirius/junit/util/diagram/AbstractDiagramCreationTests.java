/******************************************************************************
 * Copyright (c) 2021, 2023 CEA LIST, Artal Technologies
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rengin Battal (ARTAL) - rengin.battal@artal.fr - Initial API and implementation
 *  Obeo - Various fixes.
 *****************************************************************************/
package org.eclipse.papyrus.sirius.junit.util.diagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramPrototype;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.sirius.editor.internal.sessions.SiriusConstants;
import org.eclipse.papyrus.sirius.editor.representation.SiriusDiagramConstants;
import org.eclipse.papyrus.sirius.editor.representation.SiriusDiagramPrototype;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.tools.api.DiagramPlugin;
import org.eclipse.sirius.tests.support.api.SiriusAssert;
import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.description.DAnnotation;
import org.eclipse.uml2.uml.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

import junit.framework.TestCase;

/**
 * Abstract class to test the Sirius Diagram creation in Papyrus
 */
public abstract class AbstractDiagramCreationTests extends AbstractSiriusDiagramTests {

	protected Package rootModel;
	/** HashMaps to store the initial values of preferences before changes. */
	private final HashMap<String, Object> oldValueDiagramPreferences = new HashMap<String, Object>();

	/**
	 * The editor fixture
	 */
	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	@SuppressWarnings("restriction")
	@Before
	public void setUp() {
		this.rootModel = this.fixture.getModel();
		this.changeDiagramPreference(org.eclipse.sirius.diagram.tools.internal.preferences.SiriusDiagramInternalPreferencesKeys.PREF_SYNCHRONIZE_DIAGRAM_ON_CREATION.name(), Boolean.FALSE);
	}

	/**
	 *
	 * @return
	 *         the *.aird resource associated to the current model
	 */
	protected final Resource getAIRDResourceForCurrentModel() {
		for (final Resource current : this.fixture.getResourceSet().getResources()) {
			if (SiriusConstants.SIRIUS_DIAGRAM_MODEL_FILE_EXTENSION.equals(current.getURI().fileExtension())) {
				if (this.fixture.getModelResourceURI().trimFileExtension().equals(current.getURI().trimFileExtension())) {
					return current;
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @param context
	 *            a semantic element
	 * @return
	 *         the {@link ViewPrototype} found for this context
	 */
	protected Collection<ViewPrototype> getCreatableDiagramPrototype(final EObject context) {
		final Collection<ViewPrototype> viewPrototype = new ArrayList<>();
		for (final ViewPrototype proto : PolicyChecker.getFor(context).getPrototypesFor(context)) {
			viewPrototype.add(proto);
		}
		return viewPrototype;
	}


	/**
	 *
	 * @param context
	 *            a semantic element
	 * @param type
	 *            the type of the wanted {@link ViewPrototype}
	 * @return
	 *         the {@link DiagramPrototype} or <code>null</code> if not found
	 */
	protected final ViewPrototype getDiagramPrototype(final EObject context, final String type) {
		for (final ViewPrototype current : this.getCreatableDiagramPrototype(context)) {
			if (current.getRepresentationKind() instanceof SiriusDiagramPrototype) {
				final SiriusDiagramPrototype pdp = (SiriusDiagramPrototype) current.getRepresentationKind();
				if (type.equals(((PapyrusRepresentationKind) pdp).getId())) {
					return current;
				}

			}
		}
		return null;
	}

	/**
	 * Check diagram creation launch on a given diagramInstantiaterOwner which is the semanticOwner of the diagram.
	 *
	 * @param diagramInstantiaterOwner
	 *            the semantic owner for the diagram to create
	 * @param diagramName
	 *            the name to set for the new diagram
	 * @param type
	 *            the type of the diagram to create
	 */
	protected void checkDiagramCreationFromSiriusDiagramPrototype(final EObject diagramInstantiaterOwner, final String diagramName, final String type) {
		this.checkDiagramCreationFromSiriusDiagramPrototypeWithRootOrNot(diagramInstantiaterOwner, diagramName, type, null);
	}

	/**
	 * Check diagram creation launch on a given diagramInstantiaterOwner.
	 * A new element of semanticOwnerType will be created (under diagramInstantiaterOwner except if diagramInstantiaterOwner match with semanticOwnerType) to contain the diagram.
	 * A root element is always initialized on the diagram in this case.
	 *
	 * @param diagramInstantiaterOwner
	 *            the semantic owner for the diagram to create
	 * @param diagramName
	 *            the name to set for the new diagram
	 * @param type
	 *            the type of the diagram to create
	 * @param semanticOwnerType
	 *            the type of the semantic owner that will be created to contain the diagram
	 */
	protected void checkDiagramCreationFromSiriusDiagramPrototypeWithRootCreation(final EObject diagramInstantiaterOwner, final String diagramName, final String type, final Class<?> semanticOwnerType) {
		this.checkDiagramCreationFromSiriusDiagramPrototypeWithRootOrNot(diagramInstantiaterOwner, diagramName, type, semanticOwnerType);
	}

	/**
	 * Check diagram creation launch on a given diagramInstantiaterOwner.
	 * If semanticOwnerType is null, the diagramInstantiaterOwner will be the semanticOwner of the diagram.
	 * Otherwise a new element of semanticOwnerType will be created to contain the diagram. In this case, a root element is always initialized on the diagram.
	 *
	 * @param diagramInstantiaterOwner
	 *            the semantic owner for the diagram to create
	 * @param diagramName
	 *            the name to set for the new diagram
	 * @param type
	 *            the type of the diagram to create
	 * @param semanticOwnerType
	 *            the type of the semantic owner that will be created to contain the diagram
	 */
	private void checkDiagramCreationFromSiriusDiagramPrototypeWithRootOrNot(final EObject diagramInstantiaterOwner, final String diagramName, final String type, final Class<?> semanticOwnerType) {
		// 1. we look for the view prototype required to create the document
		final ViewPrototype diagramPrototype = this.getDiagramPrototype(this.fixture.getRoot(), type);
		Assert.assertNotNull(NLS.bind("The DiagramPrototype to create a diagram of type {0} is not found.", type), diagramPrototype); //$NON-NLS-1$

		// 2. check if the aird resource exists in the ModelSet
		final Resource airdResource = this.getAIRDResourceForCurrentModel();
		Assert.assertNotNull("The aird resource is not in the ModelSet.", airdResource); //$NON-NLS-1$

		// 3. check aird resource only contains the DAnalysis element
		final List<EObject> initialAirdContents = airdResource.getContents();
		Assert.assertEquals("The aird resource must contains only one element", 1, initialAirdContents.size()); //$NON-NLS-1$
		Assert.assertTrue(initialAirdContents.get(0) instanceof DAnalysis);

		// 4. check the aird file is created
		boolean airdFileExists = airdResource.getResourceSet().getURIConverter().exists(airdResource.getURI(), null);
		Assert.assertTrue("The aird file doesn't exist, but it should already be created.", airdFileExists); //$NON-NLS-1$

		boolean succeed = diagramPrototype.instantiateOn(diagramInstantiaterOwner, diagramName, true);
		this.fixture.flushDisplayEvents();
		Assert.assertTrue("The instantiation of the diagram failed", succeed); //$NON-NLS-1$

		final List<EObject> airdContents = airdResource.getContents();
		Assert.assertEquals(2, airdContents.size());
		Assert.assertTrue(airdContents.get(0) instanceof DAnalysis);
		Assert.assertTrue(airdContents.get(1) instanceof DSemanticDiagram);

		final DSemanticDiagram dDiagram = (DSemanticDiagram) airdContents.get(1);
		EObject semanticOwner = null;
		if (semanticOwnerType == null) {
			Assert.assertEquals("The created diagram must be empty", 0, dDiagram.getDiagramElements().size()); //$NON-NLS-1$
			semanticOwner = diagramInstantiaterOwner;
		} else {
			Assert.assertEquals("The created diagram must contain the root (and its compartment)", 2, dDiagram.getDiagramElements().size()); //$NON-NLS-1$
			semanticOwner = this.getSemanticOwner(diagramInstantiaterOwner, semanticOwnerType);
		}
		Assert.assertEquals("The created diagram hasn't the expected semanticOwner", semanticOwner, dDiagram.getTarget()); //$NON-NLS-1$
		Assert.assertEquals("The created diagram hasn't the expected name", diagramName, dDiagram.getName()); //$NON-NLS-1$
		final DAnnotation annotation = dDiagram.getDAnnotation(SiriusDiagramConstants.PAPYRUS_SIRIUS_DIAGRAM_IMPLEMENTATION_DANNOTATION_SOURCE);
		final String implementationId = annotation.getDetails().get(SiriusDiagramConstants.PAPYRUS_SIRIUS_DIAGRAM_IMPLEMENTATION_DANNOTATION_KEY);
		Assert.assertEquals("The created diagram hasn't the expected implementationId", type, implementationId); //$NON-NLS-1$

		Diagram diagram = this.fixture.getActiveDiagram().getDiagramView();
		Assert.assertNotNull("The created GMF Diagram must not be null", diagram); //$NON-NLS-1$
		Assert.assertEquals("The element associated to the GMF Diagram must be the DSemanticDiagram", dDiagram, diagram.getElement()); //$NON-NLS-1$
	}

	private EObject getSemanticOwner(EObject parentSemanticOwner, Class<?> class1) {
		if (class1.isAssignableFrom(parentSemanticOwner.getClass())) {
			return parentSemanticOwner;
		} else {
			List<?> potentialSemanticOwner = parentSemanticOwner.eContents().stream().filter(class1::isInstance).map(class1::cast).collect(Collectors.toList());
			return (EObject) potentialSemanticOwner.get(potentialSemanticOwner.size() - 1);
		}
	}

	/**
	 * Change a boolean preference and store the old value. It will be automatically reset during tear down.
	 *
	 * TO CALL ONLY ONCE PER TEST (set up + test)
	 *
	 * @param preferenceKey
	 *            The key of the preference.
	 * @param newValue
	 *            The new value.
	 */
	protected void changeDiagramPreference(String preferenceKey, Boolean newValue) {
		SiriusAssert.assertNoDiagramUIPreferenceChangedinDiagramCoreStore(preferenceKey);

		boolean oldValue = Platform.getPreferencesService().getBoolean(DiagramPlugin.ID, preferenceKey, false, null);
		this.oldValueDiagramPreferences.put(preferenceKey, Boolean.valueOf(oldValue));

		IEclipsePreferences diagramCorePreferences = InstanceScope.INSTANCE.getNode(DiagramPlugin.ID);
		diagramCorePreferences.putBoolean(preferenceKey, newValue.booleanValue());

		boolean valueToCheck = Platform.getPreferencesService().getBoolean(DiagramPlugin.ID, preferenceKey, false, null);
		TestCase.assertEquals(this.getErrorMessage(preferenceKey, DiagramPlugin.ID), newValue.booleanValue(), valueToCheck);
	}

	private String getErrorMessage(String preferenceKey, String pluginId) {
		return "The " + preferenceKey + " preference value was not changed for plugin " + pluginId; //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Restore this preference to its initial value. Should be called after
	 * {@link #changeDiagramPreference(String, Boolean)} of {@link #changeDiagramPreference(String, Integer)} to have
	 * effect.
	 *
	 * @param preferenceKey
	 *            The key of the preference.
	 * @param diagramCorePreferences
	 *            The {@link IEclipsePreferences} to use.
	 */
	private void resetDiagramPreference(String preferenceKey, IEclipsePreferences diagramCorePreferences) {
		Object initialValue = this.oldValueDiagramPreferences.get(preferenceKey);
		if (initialValue instanceof Boolean) {
			diagramCorePreferences.putBoolean(preferenceKey, ((Boolean) initialValue).booleanValue());
		} else if (initialValue instanceof Integer) {
			diagramCorePreferences.putInt(preferenceKey, ((Integer) initialValue).intValue());
		}
	}

	/**
	 * dispose the resource
	 */
	@After
	public void tearDown() {
		this.rootModel = null;
		// Reset the preferences changed during the test with the method changePreference
		IEclipsePreferences diagamCorePreferences = InstanceScope.INSTANCE.getNode(DiagramPlugin.ID);
		for (String key : this.oldValueDiagramPreferences.keySet()) {
			this.resetDiagramPreference(key, diagamCorePreferences);
		}
	}
}

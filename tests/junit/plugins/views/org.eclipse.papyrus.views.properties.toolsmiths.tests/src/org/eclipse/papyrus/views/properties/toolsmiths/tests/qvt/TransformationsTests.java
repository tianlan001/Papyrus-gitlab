/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 422257
 *  Slimane El Ouadi - elouadi.slimane@yahoo.fr - Bug 507633
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.tests.qvt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.papyrus.customization.properties.generation.generators.EcoreGenerator;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.customization.properties.generation.generators.ProfileGenerator;
import org.eclipse.papyrus.views.properties.model.xwt.resource.XWTResource;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.ProjectUtils;
import org.eclipse.papyrus.views.properties.root.PropertiesRoot;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.papyrus.views.properties.toolsmiths.tests.Activator;
import org.eclipse.uml2.uml.Profile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


//Bug 383748: [Properties View] Customization & Generation do not work with a default Papyrus installation
//https://bugs.eclipse.org/bugs/show_bug.cgi?id=383748
/**
 * Test the QVT transformations associated to the properties view generation/customization
 *
 * @author Camille Letavernier
 */
public class TransformationsTests extends AbstractPapyrusTest {

	private static URI xwtModelUri;

	private static IProject targetProject;

	private IGenerator generator;

	@BeforeClass
	public static void init() throws CoreException {
		targetProject = ProjectUtils.createProject(Activator.PLUGIN_ID + ".testProject");
		xwtModelUri = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/SingleClass.xwt", false);
	}

	@AfterClass
	public static void dispose() throws CoreException {
		targetProject.delete(true, new NullProgressMonitor());
	}

	@After
	public void disposeFixture() {
		if (generator != null) {
			generator.dispose();
			generator = null;
		}
	}

	@Test
	public void handleXWTFileFromResource() {
		// From an XWT Resource
		Resource xwtResource = new XWTResource(xwtModelUri);
		try {
			xwtResource.load(null);
		} catch (IOException ex) {
			Activator.log.error(ex);
			Assert.fail("Cannot load the XWT Resource");
		}
		checkContents(xwtResource);
	}

	@Test
	public void handleXWTFileFromResourceSet() {
		// From a generic ResourceSet
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource xwtResource = resourceSet.getResource(xwtModelUri, true);
		checkContents(xwtResource);
	}

	// Briefly check the resource contents
	private void checkContents(Resource xwtResource) {
		Assert.assertNotNull(xwtResource);

		Assert.assertEquals(1, xwtResource.getContents().size()); // Exactly one root

		EObject root = xwtResource.getContents().get(0);
		Assert.assertTrue(root instanceof CompositeWidget); // The root is a Composite

		CompositeWidget widget = (CompositeWidget) root;
		Assert.assertEquals("Composite", widget.getWidgetType().getWidgetClass()); //$NON-NLS-1$ //The root is a SWT Composite
		Assert.assertEquals("PropertiesLayout", widget.getLayout().getLayoutType().getWidgetClass()); //$NON-NLS-1$ //The layout is a Papyrus Properties Layout
		Assert.assertEquals(3, widget.getWidgets().size()); // There are exactly three widgets under the root composite

		//First child
        EObject firstWidgetInRoot = widget.getWidgets().get(0);
        CompositeWidget widgetFirst = (CompositeWidget) firstWidgetInRoot;
        Assert.assertTrue(widgetFirst instanceof CompositeWidget); 
        Assert.assertEquals("Composite",widgetFirst.getWidgetType().getWidgetClass());
        Assert.assertEquals("PropertiesLayout", widget.getLayout().getLayoutType().getWidgetClass());
        Assert.assertEquals(2, widgetFirst.getWidgets().size());
        
        //Second child
        EObject secondWidgetInRoot = widget.getWidgets().get(1);
        CompositeWidget widgetSecond= (CompositeWidget) secondWidgetInRoot;
        Assert.assertTrue(widgetSecond instanceof CompositeWidget); 
        Assert.assertEquals("Composite",widgetSecond.getWidgetType().getWidgetClass());
        Assert.assertEquals("PropertiesLayout", widgetSecond.getLayout().getLayoutType().getWidgetClass());
        Assert.assertEquals(3, widgetSecond.getWidgets().size());

        //third child
        EObject thirdWidgetInRoot = widget.getWidgets().get(2);
        CompositeWidget widgetThird= (CompositeWidget) thirdWidgetInRoot;
        Assert.assertTrue(widgetThird instanceof CompositeWidget); 
        Assert.assertEquals("Composite",widgetThird.getWidgetType().getWidgetClass());
        Assert.assertEquals("PropertiesLayout", widgetThird.getLayout().getLayoutType().getWidgetClass());
        Assert.assertEquals(2, widgetThird.getWidgets().size());
	}

	@Test
	public void generateEcoreContext() {
		final URI packageURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/Sample.ecore", false);

		generator = new EcoreGenerator() {

			// FIXME: The EcoreGenerator is currently not built to be used programmatically; we need to override it to test it.

			@Override
			protected List<ModelExtent> getModelExtents() {
				try {
					ecorePackage = (EPackage) loadEMFModel(packageURI);
				} catch (IOException e) {
					// nothing
				}
				LinkedList<ModelExtent> result = new LinkedList<ModelExtent>();
				ModelExtent inPackage = new BasicModelExtent(Collections.singletonList(ecorePackage));

				PropertiesRoot root = ConfigurationManager.getInstance().getPropertiesRoot();
				ModelExtent inRoot = new BasicModelExtent(Collections.singletonList(root));

				// Basic Method
				result.add(inPackage);
				result.add(inPackage);


				result.add(inRoot);
				result.add(getOutContextExtent());
				return result;
			}
		};

		URI targetURI = URI.createPlatformResourceURI(targetProject.getName() + "/properties/Ecore/EcoreProperties.ctx", false);

		ArrayList<URI> listURI = new ArrayList<URI>();
		listURI.add(targetURI);
		// List<Object> listObject = generator.getExternalReference();
		// for(Object p: listObject){
		// generator.addCheckElement(p);
		// }
		List<Context> generatedContexts = generator.generate(listURI);

		Assert.assertNotNull(generatedContexts);
		Assert.assertEquals(1, generatedContexts.size());

		Context context = generatedContexts.get(0);

		Assert.assertEquals(1, context.getDataContexts().size()); // Only one DataContextRoot
		Assert.assertEquals(3, context.getDataContexts().get(0).getElements().size()); // 3 DataContextElements

		Assert.assertEquals(6, context.getViews().size());

		// int numberOfSections = checkGeneratedContents(context);
		// Assert.assertEquals(6, numberOfSections);
	}

	@Test
	public void generateProfileContext() {
		final URI profileURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/resources/profile/model3.profile.uml", true);

		// FIXME: The ProfileGenerator is currently not built to be used programmatically; we need to override it to test it.

		generator = new ProfileGenerator() {

			@Override
			protected List<ModelExtent> getModelExtents() {
				try {
					Profile umlProfile = (Profile) loadEMFModel(profileURI);
					ModelExtent inProfile = new BasicModelExtent(Collections.singletonList(umlProfile));

					URI umlURI = URI.createURI("ppe:/context/org.eclipse.papyrus.uml.properties/Model/UML/UML.ctx", true);
					Context umlContext = (Context) loadEMFModel(umlURI);
					if (umlContext == null) {
						Activator.log.warn("Cannot find the UML Property View configuration");
					}
					ModelExtent inUml = new BasicModelExtent(Collections.singletonList(umlContext));

					PropertiesRoot root = ConfigurationManager.getInstance().getPropertiesRoot();
					ModelExtent inRoot = new BasicModelExtent(Collections.singletonList(root));

					LinkedList<ModelExtent> result = new LinkedList<ModelExtent>();
					result.add(inProfile);
					result.add(getOutContextExtent());
					result.add(inUml);
					result.add(inRoot);

					return result;
				} catch (Exception ex) {
					Activator.log.error(ex);
				}

				return null;
			}
		};

		URI targetURI = URI.createPlatformResourceURI(targetProject.getName() + "/properties/Profile/ProfileProperties.ctx", false);
		generator.setStrategy(0);
		ArrayList<URI> listURI = new ArrayList<URI>();
		listURI.add(targetURI);
		List<Context> generatedContexts = generator.generate(listURI);

		Assert.assertNotNull(generatedContexts);
		Assert.assertEquals(1, generatedContexts.size());

		Context context = generatedContexts.get(0);
		Assert.assertEquals(1, context.getDependencies().size());
		Assert.assertEquals("UML", context.getDependencies().get(0).getName());
		Assert.assertEquals(1, context.getDataContexts().size()); // Only one DataContextRoot
		Assert.assertEquals(12, context.getViews().size()); // 12 view for 6 elements
		Assert.assertEquals(6, context.getDataContexts().get(0).getElements().size()); // 6 DataContextElements

		// Test stereotype inheritance (Bug 480969)
		DataContextElement st5Element = findContextElement(context, "Stereotype5");
		Assert.assertEquals("Stereotype5 should have exactly two supertypes", 2, st5Element.getSupertypes().size());

		List<String> names = new LinkedList<String>();
		for (DataContextElement superElement : st5Element.getSupertypes()) {
			names.add(superElement.getName());
		}
		Assert.assertTrue("Stereotype5 should extend Stereotype3", names.contains("Stereotype3"));
		Assert.assertTrue("Stereotype5 should extend Stereotype6", names.contains("Stereotype6"));


		// Test metaclass extension
		DataContextElement st3Element = findContextElement(context, "Stereotype3");
		//TODO reactivate
		//Assert.assertEquals("Stereotype3 should have exactly one supertype", 1, st3Element.getSupertypes().size());
		//Assert.assertEquals("Stereotype3 should extend Activity", "Activity", st3Element.getSupertypes().get(0).getName());

		// Check that all 12 sections have an associated CompositeWidget (xwt file)
		// int numberOfSections = checkGeneratedContents(context);


		// Assert.assertEquals(12, numberOfSections);
	}

	private DataContextElement findContextElement(Context context, String name) {
		TreeIterator<EObject> iterator = context.getDataContexts().get(0).eAllContents();
		while (iterator.hasNext()) {
			EObject next = iterator.next();
			if (next instanceof DataContextElement) {
				DataContextElement element = (DataContextElement) next;
				if (name.equals(element.getName())) {
					return element;
				}
			} else {
				iterator.prune();
			}
		}
		return null;
	}

	// FIXME: This test is disabled, because we haven't generated the views yet.
	// We need to run the full wizard ; not only the IGenerator (Which only generates the Context model)
	// The wizard isn't built to be used programmatically
	//
	// private int checkGeneratedContents(Context context) {
	// int numberOfSections = 0;
	//
	// ResourceSet loadingResourceSet = new ResourceSetImpl();
	// for(Tab tab : context.getTabs()) {
	// for(Section section : tab.getSections()) {
	// //There is a CompositeWidget
	// Assert.assertNotNull(section.getWidget());
	//
	// Resource widgetResource = section.getWidget().eResource();
	// URI widgetURI = widgetResource.getURI();
	//
	// //The CompositeWidget is located in its own *.xwt resource
	// Assert.assertTrue(widgetURI.lastSegment().endsWith(".xwt"));
	//
	// //The Resource is serialized to the XWT Format (Not XMI)
	// Assert.assertTrue(widgetResource instanceof XWTResource);
	//
	// //The XWT Resource can be unserialized
	// Resource xwtResource = loadingResourceSet.getResource(widgetURI, true);
	// Assert.assertTrue(xwtResource instanceof XWTResource);
	// Assert.assertEquals(1, xwtResource.getContents().size());
	// Assert.assertTrue(xwtResource.getContents().get(0) instanceof CompositeWidget);
	//
	// numberOfSections++;
	// }
	// }
	//
	// return numberOfSections;
	// }
}

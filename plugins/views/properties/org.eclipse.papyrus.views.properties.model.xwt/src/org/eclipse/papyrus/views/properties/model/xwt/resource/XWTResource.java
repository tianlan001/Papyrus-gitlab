/*****************************************************************************
 * Copyright (c) 2010, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 402049
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.model.xwt.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.m2m.qvt.oml.util.Log;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.ui.CompositeWidget;
import org.eclipse.papyrus.infra.properties.ui.UiPackage;
import org.eclipse.papyrus.infra.properties.ui.ValueAttribute;
import org.eclipse.papyrus.infra.properties.ui.WidgetAttribute;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesUtil;
import org.eclipse.papyrus.views.properties.model.xwt.Activator;
import org.eclipse.papyrus.views.properties.model.xwt.format.XMLFormatter;
import org.eclipse.papyrus.views.properties.model.xwt.modisco.GenericXMLResourceImpl;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Namespace;
import org.eclipse.papyrus.views.properties.model.xwt.xwtxml.Root;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;

/**
 * A Resource for representing XWT (XML Widget Toolkit) files
 * as EObjects.
 * Resulting EObjects are conform to the Papyrus property view UI Metamodel :
 * http://www.eclipse.org/papyrus/properties/ui/0.9
 *
 * The resource is based on MoDisco for reading and writing XML,
 * and on QVTO to go from XML to EMF and vice-versa.
 *
 * @author Camille Letavernier
 *
 * @see UiPackage
 */
public class XWTResource extends ResourceImpl {

	private GenericXMLResourceImpl xmlResource;

	/**
	 * The "format" option.
	 *
	 * This option is a boolean, which default value is true
	 */
	public static final String OPTION_FORMAT = "format"; //$NON-NLS-1$

	/**
	 * Comparators created to sort the namespaces and fix the bug 402049
	 */
	private final NamespaceComparator comparator = new NamespaceComparator();

	private final WidgetAttributeComparator widgetAttributeComparator = new WidgetAttributeComparator();

	/**
	 *
	 * Constructs a new XWTResource with the given URI
	 *
	 * @param uri
	 *            The resource's URI
	 */
	public XWTResource(URI uri) {
		super(uri);
		xmlResource = new GenericXMLResourceImpl(uri);
	}

	@Override
	protected void doLoad(InputStream inputStream, Map<?, ?> options) throws IOException {
		xmlResource.load(inputStream, options);
		Root root = (Root) xmlResource.getContents().get(0);
		try {
			CompositeWidget widget = xmlToUISection(root);
			if (widget == null) {
				Activator.log.warn("Cannot load the XWT Widget"); //$NON-NLS-1$
			} else {
				getContents().add(widget);
			}
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		if (options == null || options.isEmpty()) {
			Map<String, String> optionsMap = new HashMap<>();
			optionsMap.put(OPTION_SAVE_ONLY_IF_CHANGED, OPTION_SAVE_ONLY_IF_CHANGED_MEMORY_BUFFER);
			super.save(optionsMap);
		} else {
			super.save(options);
		}

		Object formatValue = options == null ? null : options.get(OPTION_FORMAT);
		if (formatValue == null || formatValue == Boolean.TRUE) {
			if (uri.isPlatform()) {
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
				XMLFormatter.format(file);
			}
		}
	}

	@Override
	protected void doSave(OutputStream outputStream, Map<?, ?> options) throws IOException {
		try {
			if (getContents().isEmpty()) {
				Activator.log.warn("Cannot save an Empty XWT resource : " + getURI()); //$NON-NLS-1$
				return;
			}
			Root root = uiSectionToXML((CompositeWidget) getContents().get(0));

			xmlResource.getContents().clear();
			xmlResource.getContents().add(root);
			xmlResource.save(outputStream, options);
		} catch (IOException ex) {
			Activator.log.error(ex);
			throw ex;
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}

	private Collection<Context> findContexts() {
		Set<Context> rootContexts = new HashSet<>();
		if (resourceSet == null) {
			return Collections.emptyList();
		}

		for (Resource resource : resourceSet.getResources()) {
			if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof Context) {
				Context context = (Context) resource.getContents().get(0);
				rootContexts.add(context);
			}
		}

		Set<Context> allContexts = new HashSet<>();

		for (Context context : rootContexts) {
			allContexts.addAll(PropertiesUtil.getDependencies(context));
		}

		return allContexts;
	}

	private CompositeWidget xmlToUISection(Root genericXMLRoot) {
		URI transformationURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/Transformation/XMLToUI.qvto", true); //$NON-NLS-1$
		TransformationExecutor executor = new TransformationExecutor(transformationURI);

		List<Context> contexts = new LinkedList<>(findContexts());

		ModelExtent inXml = getModelExtent(genericXMLRoot);
		ModelExtent inRoot = getModelExtent(ConfigurationManager.getInstance().getPropertiesRoot());
		ModelExtent inContexts = new BasicModelExtent(contexts);
		ModelExtent outUI = new BasicModelExtent();

		ExecutionContextImpl context = new ExecutionContextImpl();
		context.setLog(getLogger());
		context.setConfigProperty("keepModeling", true); //$NON-NLS-1$

		ExecutionDiagnostic result = executor.execute(context, inXml, inRoot, inContexts, outUI);

		if (result.getSeverity() == org.eclipse.emf.common.util.Diagnostic.OK) {
			List<EObject> outObjects = outUI.getContents();
			Object objectResult = outObjects.get(0);
			if (!(objectResult instanceof CompositeWidget)) {
				return null;
			}

			// we sort the attribute to be sure to display them in the same order than the serialization, done to fix the bug 402049
			CompositeWidget widget = (CompositeWidget) outObjects.get(0);
			ECollections.sort(widget.getAttributes(), this.widgetAttributeComparator);

			return widget;
		} else {
			IStatus status = BasicDiagnostic.toIStatus(result);
			Activator.getDefault().getLog().log(status);
			Activator.log.error(status.getException());
		}

		return null;
	}

	private Log getLogger() {
		return new Log() {

			@Override
			public void log(int level, String message) {
				Activator.getDefault().getLog().log(new Status(level, Activator.PLUGIN_ID, message));
			}

			@Override
			public void log(int level, String message, Object param) {
				log(level, message);
			}


			@Override
			public void log(String message) {
				log(IStatus.INFO, message);
			}

			@Override
			public void log(String message, Object param) {
				log(message);
			}
		};
	}

	private Root uiSectionToXML(CompositeWidget widget) {
		URI transformationURI = URI.createPlatformPluginURI(Activator.PLUGIN_ID + "/Transformation/UIToXML.qvto", true); //$NON-NLS-1$
		TransformationExecutor executor = new TransformationExecutor(transformationURI);

		ModelExtent inWidget = getModelExtent(widget);
		ModelExtent outXML = new BasicModelExtent();

		ExecutionContextImpl context = new ExecutionContextImpl();
		context.setConfigProperty("keepModeling", true); //$NON-NLS-1$
		context.setLog(getLogger());

		ExecutionDiagnostic result = executor.execute(context, inWidget, outXML);

		if (result.getSeverity() == org.eclipse.emf.common.util.Diagnostic.OK) {
			List<EObject> outObjects = outXML.getContents();
			Root root = (Root) outObjects.get(0);

			// we sort the namespaces to fix the bug 402049.
			ECollections.sort(root.getNamespaces(), this.comparator);

			return root;
		} else {
			IStatus status = BasicDiagnostic.toIStatus(result);
			Activator.getDefault().getLog().log(status);
		}
		return null;
	}

	private ModelExtent getModelExtent(EObject source) {
		if (source == null) {
			return new BasicModelExtent();
		}

		EList<EObject> objects = new BasicEList<>();
		objects.add(source);
		ModelExtent extent = new BasicModelExtent(objects);
		return extent;
	}

	/**
	 *
	 * @author Vincent Lorenzo
	 *         This comparator has been created to fix the bug 402049. This comparator is used during the save of the model.
	 */
	private static class NamespaceComparator implements Comparator<Namespace> {

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 *
		 * @param o1
		 * @param o2
		 * @return
		 */
		@Override
		public int compare(Namespace o1, Namespace o2) {
			final String o1Name = o1.getName() != null ? o1.getName() : "";
			final String o2Name = o2.getName() != null ? o2.getName() : "";
			int res = o1Name.compareTo(o2Name);
			if (res == 0) {
				final String o1Value = o1.getValue() != null ? o1.getValue() : "";
				final String o2Value = o2.getValue() != null ? o2.getValue() : "";
				res = o1Value.compareTo(o2Value);
			}
			return res;
		}
	}

	/**
	 *
	 * @author Vincent Lorenzo
	 *         This comparator has been created to fix the bug 402049. This comparator is used when we load the model,
	 *         to be sure to display attribute in the same order than the saved one!
	 */
	private static class WidgetAttributeComparator implements Comparator<WidgetAttribute> {

		/**
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 *
		 * @param o1
		 * @param o2
		 * @return
		 */
		@Override
		public int compare(WidgetAttribute o1, WidgetAttribute o2) {
			final String o1Name = o1.getName() != null ? o1.getName() : "";
			final String o2Name = o2.getName() != null ? o2.getName() : "";
			int res = o1Name.compareTo(o2Name);
			if (res == 0) {
				if (o1 instanceof ValueAttribute && o2 instanceof ValueAttribute) {
					final String o1Value = ((ValueAttribute) o1).getValue() != null ? ((ValueAttribute) o1).getValue() : "";
					final String o2Value = ((ValueAttribute) o2).getValue() != null ? ((ValueAttribute) o2).getValue() : "";
					res = o1Value.compareTo(o2Value);
				}
			}
			return res;
		}
	}
}

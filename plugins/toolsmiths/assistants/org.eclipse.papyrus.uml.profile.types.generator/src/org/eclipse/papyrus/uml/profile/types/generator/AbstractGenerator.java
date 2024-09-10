/*****************************************************************************
 * Copyright (c) 2014, 2015, 2018, 2020 Christian W. Damus and others.
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
 *   Ansgar Radermacher - Bug 526155, re-generation from profile: use ID as XML-ID
 *   Camille Letavernier - Bug 569356: support incremental generation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.types.generator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.uml.profile.types.generator.DeltaStrategy.Diff;
import org.eclipse.papyrus.uml.profile.types.generator.internal.Activator;
import org.eclipse.uml2.common.util.UML2Util;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Scaffolding for an Xtend model-to-model transformation.
 *
 * @param <I>
 *            the kind of model element input
 * @param <O>
 *            the kind of model element that I generate from &lt;I&gt;
 */
public abstract class AbstractGenerator<I extends EObject, O extends EObject> {

	private final Injector injector;

	@Inject
	@InputModel
	private EClass inputType;

	@Inject
	@OutputModel
	private EClass outputType;

	@Inject
	private Identifiers identifiers;

	@Inject
	private Optional<Diff> diff;

	public AbstractGenerator(Identifiers identifiers, DeltaStrategy.Diff diff) {
		this(new GeneratorModule(identifiers, diff));
	}

	public AbstractGenerator(GeneratorModule module) {
		super();

		this.injector = Guice.createInjector(module);
		injector.injectMembers(this);
	}

	public String getLabel() {
		String result = identifiers.getLabel(outputType);

		return Strings.isNullOrEmpty(result) ? "output model" : result;
	}

	public IStatus generate(URI inputURI, URI outputURI) {
		IStatus result;
		ResourceSet resourceSet = new ResourceSetImpl();

		try {
			I input = UML2Util.load(resourceSet, inputURI, inputType);
			result = generate(input, outputURI);
		} finally {
			EMFHelper.unload(resourceSet);
		}

		return result;
	}

	public IStatus generate(I input, URI outputURI) {
		IStatus result;
		final ResourceSet resourceSet = new ResourceSetImpl();

		try {
			final Resource output;
			if (diff.isEmpty()) {
				output = resourceSet.createResource(outputURI);
			} else {
				// TODO If Diff is set, we can probably successfully load this resource.
				// However, adding a few checks wouldn't hurt...
				output = resourceSet.getResource(outputURI, true);
			}
			result = generate(input, output.getContents());

			// use Identifier as XML-ID. This implies that the same XML-ID is used when re-generating
			EObject set = output.getContents().size() > 0 ? output.getContents().get(0) : null;
			if (set instanceof ElementTypeSetConfiguration) {
				ElementTypeSetConfiguration elementTypeSet = (ElementTypeSetConfiguration) set;
				String elementTypeSetId = elementTypeSet.getIdentifier();
				if (elementTypeSetId != null && elementTypeSetId.length() > 0) {
					((XMLResource) output).setID(elementTypeSet, escapeID(elementTypeSetId));
				}
				for (ElementTypeConfiguration elemTypeConfig : elementTypeSet.getElementTypeConfigurations()) {
					String id = elemTypeConfig.getIdentifier();
					if (id != null && id.length() > 0) {
						((XMLResource) output).setID(elemTypeConfig, escapeID(id));
					}
				}
			}

			try {
				output.save(null);
			} catch (IOException e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to save output", e));//$NON-NLS-1$
			}
			refreshContainer(outputURI);
		} catch (CoreException e) {
			result = e.getStatus();
		} finally {
			EMFHelper.unload(resourceSet);
		}

		return result;
	}

	/**
	 * Replace problematic characters in identifier with "_", before using it as XML-id
	 * in particular, the :: can be used by the generator.
	 *
	 * @param id
	 *            an ID
	 * @return
	 * @since 2.1
	 */
	public String escapeID(String id) {
		id = id.replaceAll(" ", "_");
		id = id.replaceAll(":", "_");
		return id;
	}

	public IStatus generate(I input, EList<? super EObject> output) {
		IStatus result = Status.OK_STATUS;

		if (output.isEmpty()) {
			output.add(generate(input));
		} else {
			regenerate(input, output);
		}

		return result;
	}

	protected abstract O generate(I input);

	protected O regenerate(I input, EList<? super EObject> originalOutput) {
		// Don't support incremental generation by default.
		return generate(input);
	}

	protected void refreshContainer(URI resourceURI) throws CoreException {
		if (resourceURI.isPlatformResource()) {
			IContainer container = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(resourceURI.toPlatformString(true))).getParent();
			if ((container != null) && container.isAccessible()) {
				container.refreshLocal(IResource.DEPTH_ONE, null);
			}
		} else if (resourceURI.isFile()) {
			try {
				IFile[] files = ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI(resourceURI.toString()));
				Set<IContainer> containers = Sets.newHashSet();
				for (IFile next : files) {
					containers.add(next.getParent());
				}

				for (IContainer next : containers) {
					next.refreshLocal(IResource.DEPTH_ONE, null);
				}
			} catch (URISyntaxException e) {
				throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Bad resource URI", e));//$NON-NLS-1$
			}
		}
	}
}

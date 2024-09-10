/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ed Seidewitz
 * 
 *****************************************************************************/

package org.eclipse.papyrus.uml.alf.tests.mapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.ocl.ecore.delegate.OCLDelegateDomain;
import org.eclipse.ocl.pivot.model.OCLstdlib;
import org.eclipse.ocl.uml.OCL;
import org.eclipse.papyrus.uml.alf.AlfStandaloneSetup;
import org.eclipse.papyrus.uml.alf.MappingError;
import org.eclipse.papyrus.uml.alf.tests.Activator;
import org.eclipse.papyrus.uml.alf.tests.mapper.AlfMapper;
import org.eclipse.papyrus.uml.alf.tests.utils.RegisteredItemLoader;
import org.eclipse.papyrus.uml.alf.tests.utils.RegisteredItemLoader.RequiredElementsNames;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.xtext.resource.XtextResourceSet;

public class AlfMapper extends org.eclipse.papyrus.uml.alf.AlfMapper {

	public AlfMapper() throws Exception {
		String base = CommonPlugin.getPlugin() == null ? System.getProperty("qvt.base") + "/" : "platform:/plugin/";
		this.executor = new TransformationExecutor(
				URIConverter.INSTANCE.normalize(URI.createURI(base + QVT_PATH)));

		this.context = new ExecutionContextImpl();
		this.context.setConfigProperty("keepModeling", true);

		this.injector = new AlfStandaloneSetup().createInjectorAndDoEMFRegistration();
		this.setResourceSet(new XtextResourceSet());

		UMLResourcesUtil.init(this.resourceSet);
		OCL.initialize(this.resourceSet);
		OCLstdlib.install();
		OCLDelegateDomain.initialize(this.resourceSet);

		this.setStandardProfile(
				RegisteredItemLoader.getInstance().loadProfile(this.resourceSet, RequiredElementsNames.STANDARD_PROFILE));
		this.setActionLanguageProfile(
				RegisteredItemLoader.getInstance().loadProfile(this.resourceSet, RequiredElementsNames.ACTION_LANGUAGE_PROFILE));
	}

	public Resource createResource(String path) {
		return this.resourceSet.createResource(URI.createFileURI(path));
	}

	public Resource getResource(String path) {
		return this.resourceSet.getResource(URI.createFileURI(path), true);
	}

	public List<EObject> map(String path) throws MappingError {
		Resource resource = this.getResource(path);
		List<EObject> result = this.map(resource.getContents());
		resource.unload();
		return result;
	}

	public void save(String path, List<EObject> uml) throws IOException {
		Resource resource = this.resourceSet.createResource(
				URI.createFileURI(path));
		resource.getContents().addAll(uml);
		resource.save(Collections.emptyMap());
		resource.unload();
	}

	public static String addSlash(String directory) {
		if (directory.charAt(directory.length() - 1) != '/') {
			return directory + "/";
		} else {
			return directory;
		}
	}

	public static String removeExtension(String fileName) {
		final int l = fileName.length();
		if (l > 4 && fileName.substring(l - 4, l).equals(".alf")) {
			return fileName.substring(0, l - 4);
		} else {
			return fileName;
		}
	}

	public static void main(String args[]) {
		if (args.length >= 4) {
			final String inputDirectory = addSlash(args[0]);
			final String outputDirectory = addSlash(args[1]);
			final String fileName = removeExtension(args[3]);

			Resource inputResource = null;
			Resource outputResource = null;

			try {
				AlfMapper mapper = new AlfMapper();

				String path = inputDirectory + fileName + ".alf";
				Activator.log.trace(Activator.ALF_MAPPER_TRACE, "Reading from " + path + "...");
				inputResource = mapper.getResource(path);

				path = outputDirectory + fileName + ".uml";
				if (args.length > 4 && args[4].equals("-merge")) {
					Activator.log.trace(Activator.ALF_MAPPER_TRACE, "Reading from " + path + "...");
					outputResource = mapper.getResource(path);

					Package model = (Package) outputResource.getContents().get(0);
					for (PackageableElement member : model.getPackagedElements()) {
						String name = member.getName();
						if (name.length() < 3 || !name.substring(0, 2).equals("$$")) {
							mapper.map(member, inputResource.getContents());
							break;
						}
					}
				} else {
					List<EObject> uml = mapper.map(inputResource.getContents());

					outputResource = mapper.createResource(path);
					outputResource.getContents().addAll(uml);
				}

				Activator.log.trace(Activator.ALF_MAPPER_TRACE, "Writting to " + path + "...");
				outputResource.save(null);


			} catch (MappingError e) {
				ExecutionDiagnostic diagnostic = e.getDiagnostic();
				Activator.log.trace(Activator.ALF_MAPPER_TRACE, BasicDiagnostic.toIStatus(diagnostic).toString());
				diagnostic.printStackTrace(new PrintWriter(System.out));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputResource != null) {
					inputResource.unload();
				}
				if (outputResource != null) {
					outputResource.unload();
				}
			}
		}
	}

}

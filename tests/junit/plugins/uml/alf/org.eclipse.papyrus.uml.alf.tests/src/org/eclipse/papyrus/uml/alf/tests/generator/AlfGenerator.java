/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Ed Seidewitz (MDS) - Initial implementation
 * 
 *****************************************************************************/

package org.eclipse.papyrus.uml.alf.tests.generator;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;
import org.eclipse.papyrus.uml.alf.AlfStandaloneSetup;
import org.eclipse.papyrus.uml.alf.MappingError;
import org.eclipse.papyrus.uml.alf.tests.Activator;
import org.eclipse.papyrus.uml.alf.tests.generator.AlfGenerator;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.serializer.ISerializer;

import com.google.inject.Injector;

public class AlfGenerator {

	static final public String QVT_PATH = "org.eclipse.papyrus.uml.alf.to.fuml/transformation/UML2Alf.qvto";
	static final private SaveOptions options = SaveOptions.newBuilder().format().noValidation().getOptions();


	protected TransformationExecutor executor;
	protected ExecutionContextImpl context;
	protected ISerializer serializer;

	protected ResourceSet resourceSet;

	public AlfGenerator() {
		String base = CommonPlugin.getPlugin() == null ? System.getProperty("qvt.base") + "/" : "platform:/plugin/";
		this.executor = new TransformationExecutor(
				URIConverter.INSTANCE.normalize(URI.createURI(base + QVT_PATH)));

		this.context = new ExecutionContextImpl();
		this.context.setConfigProperty("keepModeling", true);

		Injector injector = new AlfStandaloneSetup().createInjectorAndDoEMFRegistration();
		this.serializer = injector.getInstance(ISerializer.class);

		this.resourceSet = new ResourceSetImpl();
		UMLResourcesUtil.init(this.resourceSet);
	}

	public List<EObject> transform(List<? extends EObject> uml) throws MappingError {
		ModelExtent input = new BasicModelExtent(uml);
		ModelExtent output = new BasicModelExtent();

		ExecutionDiagnostic diagnostic = this.executor.execute(this.context, input, output);

		if (diagnostic.getSeverity() == Diagnostic.OK) {
			return output.getContents();
		} else {
			throw new MappingError(diagnostic);
		}
	}

	public String generate(List<? extends EObject> uml) throws MappingError {
		List<EObject> alf = this.transform(uml);
		if (alf.isEmpty()) {
			return "";
		} else {
			try {
				return serializer.serialize(alf.get(0), options);
			} catch (RuntimeException e) {
				throw new MappingError(e);
			}
		}
	}

	public Resource getResource(String path) {
		return this.resourceSet.getResource(URI.createFileURI(path), true);
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
		if (l > 4 && fileName.substring(l - 4, l).equals(".uml")) {
			return fileName.substring(0, l - 4);
		} else {
			return fileName;
		}
	}

	public static void main(String[] args) {
		if (args.length >= 3) {
			final String inputDirectory = addSlash(args[0]);
			final String outputDirectory = addSlash(args[1]);
			final String fileName = removeExtension(args[2]);

			Resource inputResource = null;
			PrintWriter output = null;

			try {
				AlfGenerator generator = new AlfGenerator();

				String path = inputDirectory + fileName + ".uml";
				Activator.log.trace(Activator.ALF_GENERATOR_TRACE, "Reading from " + path + "...");
				inputResource = generator.getResource(path);

				path = outputDirectory + fileName + ".alf";
				String text = generator.generate(inputResource.getContents());

				Activator.log.trace(Activator.ALF_GENERATOR_TRACE, "Writing to " + path + "...");
				output = new PrintWriter(new FileWriter(path));
				output.print(text);

			} catch (MappingError e) {
				ExecutionDiagnostic diagnostic = e.getDiagnostic();
				if (diagnostic != null) {
					Activator.log.trace(Activator.ALF_GENERATOR_TRACE, BasicDiagnostic.toIStatus(diagnostic).toString());
					diagnostic.printStackTrace(new PrintWriter(System.out));
				} else {
					e.getCause().printStackTrace();
					;
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputResource != null) {
					inputResource.unload();
				}
				if (output != null) {
					output.close();
				}
			}
		}
	}

}

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
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.dev.types.handlers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.dev.types.Activator;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.IConfiguredHintedElementType;
import org.eclipse.ui.handlers.HandlerUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MigrateSpecializations extends AbstractHandler {
	final String FILE_EXTENSION = "elementtypesconfigurations";

	final String TYPE_ATTRIBUTE = "xsi:type";
	final String NAME_ATTRIBUTE = "name";
	final String IDENTIFIER_ATTRIBUTE = "identifier";
	final String ELEMENTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:elementtypesconfigurations";
	final String ELEMENTTYPECONFIGURATION_NAMESPACE_OLD = "http://www.eclipse.org/papyrus/infra/elementtypesconfigurations/1.1";
	final String ELEMENTTYPECONFIGURATION_NAMESPACE_NEW = "http://www.eclipse.org/papyrus/infra/elementtypesconfigurations/1.2";

	final String ELEMENTTYPECONFIGURATIONS = "elementTypeConfigurations";
	final String ELEMENTTYPESCONFIGURATIONS_SPECIALIZATIONTYPECONFIGURATION = "elementtypesconfigurations:SpecializationTypeConfiguration";
	final String SPECIALIZEDTYPESID_ATTRIBUTE = "specializedTypesID";
	final String SPECIALIZEDTYPES_ATTRIBUTE = "specializedTypes";
	final String HREF_ATTRIBUTE = "href";

	final String ELEMENTTYPESCONFIGURATIONS_METAMODELTYPECONFIGURATION = "elementtypesconfigurations:MetamodelTypeConfiguration";


	public Object execute(ExecutionEvent event) throws ExecutionException {




		Map<String, String> mapSpecializationMigration = new HashMap<>();
		Map<String, String> mapMetamodelMigration = new HashMap<>();


		ISelection currentSelection = HandlerUtil.getCurrentSelection(event);
		if (!(currentSelection instanceof IStructuredSelection) || currentSelection.isEmpty()) {
			return null;
		}

		final IStructuredSelection selection = (IStructuredSelection) currentSelection;

		Iterator<?> it = selection.iterator();

		while (it.hasNext()) {
			Object selectedElement = (Object) it.next();

			if (selectedElement instanceof IFile) {
				if (FILE_EXTENSION.equals(((IFile) selectedElement).getFileExtension())) {
					IFile selectedFile = ((IFile) selectedElement);
					URI uri = selectedFile.getLocationURI();


					if (selectedFile.isLinked()) {
						uri = selectedFile.getRawLocationURI();
					}

					try {
						File file = EFS.getStore(uri).toLocalFile(0, new NullProgressMonitor());
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();
						Document doc = builder.parse(file);
						Element root = doc.getDocumentElement();

						NodeList elementTypeConfigurations = root.getElementsByTagName(ELEMENTTYPECONFIGURATIONS);

						for (int i = 0; i < elementTypeConfigurations.getLength(); i++) {
							Element elementTypeConfiguration = (Element) elementTypeConfigurations.item(i);

							Node identifierAttrib = elementTypeConfiguration.getAttributes().getNamedItem(IDENTIFIER_ATTRIBUTE);

							Node xmiid = elementTypeConfiguration.getAttributes().getNamedItem("xmi:id");

							Node type = elementTypeConfiguration.getAttributes().getNamedItem(TYPE_ATTRIBUTE);

							if (type.getNodeValue().equals(ELEMENTTYPESCONFIGURATIONS_SPECIALIZATIONTYPECONFIGURATION)) {
								mapSpecializationMigration.put(identifierAttrib.getNodeValue(), "platform:/plugin" + selectedFile.getFullPath().toString() + "#" + xmiid.getNodeValue());
							} else {
								mapMetamodelMigration.put(identifierAttrib.getNodeValue(), "platform:/plugin" + selectedFile.getFullPath().toString() + "#" + xmiid.getNodeValue());
							}

						}
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

		}

		it = selection.iterator();

		while (it.hasNext()) {
			Object selectedElement = (Object) it.next();

			if (selectedElement instanceof IFile) {
				if (FILE_EXTENSION.equals(((IFile) selectedElement).getFileExtension())) {
					IFile selectedFile = ((IFile) selectedElement);
					URI uri = selectedFile.getLocationURI();

					if (selectedFile.isLinked()) {
						uri = selectedFile.getRawLocationURI();
					}

					try {
						File file = EFS.getStore(uri).toLocalFile(0, new NullProgressMonitor());
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();
						Document doc = builder.parse(file);
						Element root = doc.getDocumentElement();

						// update namespaces
						root.setAttribute(ELEMENTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE, ELEMENTTYPECONFIGURATION_NAMESPACE_NEW);

						NodeList elementTypeConfigurations = root.getElementsByTagName(ELEMENTTYPECONFIGURATIONS);

						for (int i = 0; i < elementTypeConfigurations.getLength(); i++) {
							Element elementTypeConfiguration = (Element) elementTypeConfigurations.item(i);

							// Node type = elementTypeConfiguration.getAttributes().getNamedItem(TYPE_ATTRIBUTE);

							// if (type.getNodeValue().equals(ELEMENTTYPESCONFIGURATIONS_SPECIALIZATIONTYPECONFIGURATION)) {
							NodeList children = elementTypeConfiguration.getChildNodes();

							ArrayList<String> specializedIDs = new ArrayList<String>();
							ArrayList<Node> childToRemove = new ArrayList<Node>();
							for (int j = 0; j < children.getLength(); j++) {

								Node child = children.item(j);
								if (child instanceof Element) {
									if (child.getNodeName().equals(SPECIALIZEDTYPESID_ATTRIBUTE)) {
										String specialized = ((Element) child).getFirstChild().getNodeValue();
										specializedIDs.add(specialized);
										childToRemove.add(child);
									}
								}
							}

							for (Node node : childToRemove) {
								elementTypeConfiguration.removeChild(node);
							}

							for (String specialized : specializedIDs) {
								Element specializedTypes = doc.createElement(SPECIALIZEDTYPES_ATTRIBUTE);

								if (mapMetamodelMigration.containsKey(specialized)) {
									specializedTypes.setAttribute(TYPE_ATTRIBUTE, ELEMENTTYPESCONFIGURATIONS_METAMODELTYPECONFIGURATION);
									specializedTypes.setAttribute(HREF_ATTRIBUTE, mapMetamodelMigration.get(specialized));
									elementTypeConfiguration.appendChild(specializedTypes);
								} else if (mapSpecializationMigration.containsKey(specialized)) {
									specializedTypes.setAttribute(TYPE_ATTRIBUTE, ELEMENTTYPESCONFIGURATIONS_SPECIALIZATIONTYPECONFIGURATION);
									specializedTypes.setAttribute(HREF_ATTRIBUTE, mapSpecializationMigration.get(specialized));
									elementTypeConfiguration.appendChild(specializedTypes);
								} else {
									// handle case that the referenced element-type ID is not in this file, but defined
									// in the registry
									IElementType elementType = ElementTypeRegistry.getInstance().getType(specialized);
									if (elementType instanceof IConfiguredHintedElementType) {
										ElementTypeConfiguration etc = ((IConfiguredHintedElementType) elementType).getConfiguration();
										if (etc != null) {
											specializedTypes.setAttribute(TYPE_ATTRIBUTE, ELEMENTTYPESCONFIGURATIONS_SPECIALIZATIONTYPECONFIGURATION);
											specializedTypes.setAttribute(HREF_ATTRIBUTE,
													etc.eResource().getURI().toString() + "#" + etc.eResource().getURIFragment(etc)); //$NON-NLS-1$
											elementTypeConfiguration.appendChild(specializedTypes);
										}
									}
									else {
										Activator.log.debug("Couldn't find : " + specialized); //$NON-NLS-1$
									}
								}
							}
						}

						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
						Result output = new StreamResult(file);
						Source input = new DOMSource(doc);

						transformer.transform(input, output);

						selectedFile.touch(new NullProgressMonitor());


					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (CoreException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransformerConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransformerFactoryConfigurationError e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TransformerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}


}

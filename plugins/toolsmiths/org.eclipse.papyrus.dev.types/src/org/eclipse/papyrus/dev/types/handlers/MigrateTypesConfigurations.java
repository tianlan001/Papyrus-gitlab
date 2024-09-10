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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MigrateTypesConfigurations extends AbstractHandler {
	final String FILE_EXTENSION = "elementtypesconfigurations";

	final String ELEMENTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:elementtypesconfigurations";
	final String ELEMENTTYPECONFIGURATION_NAMESPACE_OLD = "http://www.eclipse.org/papyrus/infra/elementtypesconfigurations/1.0";
	final String ELEMENTTYPECONFIGURATION_NAMESPACE_NEW = "http://www.eclipse.org/papyrus/infra/elementtypesconfigurations/1.1";

	final String APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE = "applystereotypeadviceconfiguration";
	final String APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACENEW = "applystereotypeadvice";
	final String APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE;
	final String APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACENEW;
	final String APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/uml/types/applystereotypeadvice/1.1";

	final String INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE = "invariantstereotyperuleconfiguration";
	final String INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACENEW = "invariantstereotyperule";
	final String INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE;
	final String INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACENEW;
	final String INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/uml/types/invariantstereotyperule/1.1";

	final String SETTYPEADVICECONFIGURATION_NAMESPACE = "settypeadviceconfiguration";
	final String SETTYPEADVICECONFIGURATION_NAMESPACENEW = "settypeadvice";
	final String SETTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + SETTYPEADVICECONFIGURATION_NAMESPACE;
	final String SETTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + SETTYPEADVICECONFIGURATION_NAMESPACENEW;
	final String SETTYPEADVICECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/uml/types/settypeadvice/1.1";

	final String STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE = "stereotypeapplicationmatcherconfiguration";
	final String STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACENEW = "stereotypematcher";
	final String STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE;
	final String STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACENEW;
	final String STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/uml/types/stereotypematcher/1.1";

	final String INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE = "invariantcontainerruleconfiguration";
	final String INVARIANTCONTAINERRULECONFIGURATION_NAMESPACENEW = "invariantcontainerrule";
	final String INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE;
	final String INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + INVARIANTCONTAINERRULECONFIGURATION_NAMESPACENEW;
	final String INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/emf/types/invariantcontainerrule/1.1";

	final String RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE = "runtimevalueseditionadviceconfiguration";
	final String RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACENEW = "runtimevaluesadvice";
	final String RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE;
	final String RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACENEW;
	final String RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/emf/types/runtimevaluesadvice/1.1";

	final String SETVALUESADVICECONFIGURATION_NAMESPACE = "setvaluesadviceconfiguration";
	final String SETVALUESADVICECONFIGURATION_NAMESPACENEW = "setvaluesadvice";
	final String SETVALUESADVICECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + SETVALUESADVICECONFIGURATION_NAMESPACE;
	final String SETVALUESADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + SETVALUESADVICECONFIGURATION_NAMESPACENEW;
	final String SETVALUESADVICECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/emf/types/setvaluesadvice/1.1";

	final String INVARIANTTYPECONFIGURATION_NAMESPACE = "invarianttypeconfiguration";
	final String INVARIANTTYPECONFIGURATION_NAMESPACENEW = "rulebased";
	final String INVARIANTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE = "xmlns:" + INVARIANTTYPECONFIGURATION_NAMESPACE;
	final String INVARIANTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW = "xmlns:" + INVARIANTTYPECONFIGURATION_NAMESPACENEW;
	final String INVARIANTTYPECONFIGURATION_NAMESPACE_NEW_URL = "http://www.eclipse.org/papyrus/infra/types/rulebased/1.1";

	final String INVARIANTTYPECONFIGURATION = "InvariantTypeConfiguration";
	final String INVARIANTTYPECONFIGURATION_NEW = "RuleBasedTypeConfiguration";

	final String RUNTIMEVALUESEDITIONADVICECONFIGURATION = "RuntimeValuesEditionAdviceConfiguration";
	final String RUNTIMEVALUESADVICECONFIGURATION = "RuntimeValuesAdviceConfiguration";

	final String INHERITANCE_ATTRIBUTE = "inheritance";
	final String TYPE_ATTRIBUTE = "xsi:type";
	final String EDITHELPERADVICECONFIGURATION_TAG = "editHelperAdviceConfiguration";
	final String MATCHERCONFIGURATION_TAG = "matcherConfiguration";
	final String INVARIANTRULECONFIGURATION_TAG = "invariantRuleConfiguration";
	final String RULECONFIGURATION_TAG = "ruleConfiguration";
	final String NAME_ATTRIBUTE = "name";
	final String IDENTIFIER_ATTRIBUTE = "identifier";
	final String EDITHELPERADVICECLASSNAME_ATTRIBUTE = "editHelperAdviceClassName";
	final String ADVICEBINDINGSCONFIGURATIONS_TAG = "adviceBindingsConfigurations";
	final String MATCHER_TYPE = "elementtypesconfigurations:MatcherConfiguration";
	final String ADVICEBINDINGCONFIGURATION_TYPE = "elementtypesconfigurations:AdviceBindingConfiguration";
	final String EDITHELPERADVICECONFIGURATION_TYPE = "elementtypesconfigurations:EditHelperAdviceConfiguration";
	final String MATCHERCLASSNAME_ATTRIBUTE = "matcherClassName";

	public Object execute(ExecutionEvent event) throws ExecutionException {




		Map<String, String> mapMigration = new HashMap<>();

		mapMigration.put(APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE, APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACENEW);
		mapMigration.put(INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE, INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACENEW);
		mapMigration.put(SETTYPEADVICECONFIGURATION_NAMESPACE, SETTYPEADVICECONFIGURATION_NAMESPACENEW);
		mapMigration.put(STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE, STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACENEW);
		mapMigration.put(INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE, INVARIANTCONTAINERRULECONFIGURATION_NAMESPACENEW);
		mapMigration.put(RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE, RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACENEW);
		mapMigration.put(SETVALUESADVICECONFIGURATION_NAMESPACE, SETVALUESADVICECONFIGURATION_NAMESPACENEW);
		mapMigration.put(INVARIANTTYPECONFIGURATION_NAMESPACE, INVARIANTTYPECONFIGURATION_NAMESPACENEW);
		mapMigration.put(INVARIANTTYPECONFIGURATION, INVARIANTTYPECONFIGURATION_NEW);
		mapMigration.put(RUNTIMEVALUESEDITIONADVICECONFIGURATION, RUNTIMEVALUESADVICECONFIGURATION);


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



						// Update namespaces
						root.setAttribute(ELEMENTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE, ELEMENTTYPECONFIGURATION_NAMESPACE_NEW);

						if (!root.getAttribute(APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, APPLYSTEREOTYPEADVICECONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, INVARIANTSTEREOTYPERULECONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(SETTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(SETTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(SETTYPEADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, SETTYPEADVICECONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, STEREOTYPEAPPLICATIONMATCHERCONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, INVARIANTCONTAINERRULECONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, RUNTIMEVALUESEDITIONADVICECONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(SETVALUESADVICECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(SETVALUESADVICECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(SETVALUESADVICECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, SETVALUESADVICECONFIGURATION_NAMESPACE_NEW_URL);
						}

						if (!root.getAttribute(INVARIANTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE).isEmpty()) {
							root.removeAttribute(INVARIANTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE);
							root.setAttribute(INVARIANTTYPECONFIGURATION_NAMESPACE_ATTRIBUTE_NEW, INVARIANTTYPECONFIGURATION_NAMESPACE_NEW_URL);
						}

						NodeList editHelperAdviceConfigurations = root.getElementsByTagName(EDITHELPERADVICECONFIGURATION_TAG);


						for (int i = 0; i < editHelperAdviceConfigurations.getLength(); i++) {
							Element editHelperAdvice = (Element) editHelperAdviceConfigurations.item(i);

							Node type = editHelperAdvice.getAttributes().getNamedItem(TYPE_ATTRIBUTE);

							if (type == null) {
								editHelperAdvice.setAttribute(TYPE_ATTRIBUTE, EDITHELPERADVICECONFIGURATION_TYPE);
							} else if (!type.getNodeValue().equals(EDITHELPERADVICECONFIGURATION_TYPE)) {
								editHelperAdvice.removeAttribute(EDITHELPERADVICECLASSNAME_ATTRIBUTE);
							}

							editHelperAdvice.removeAttribute(NAME_ATTRIBUTE);
							editHelperAdvice.removeAttribute(IDENTIFIER_ATTRIBUTE);
							editHelperAdvice.removeAttribute(INHERITANCE_ATTRIBUTE);
						}

						NodeList adviceBindingsConfigurations = root.getElementsByTagName(ADVICEBINDINGSCONFIGURATIONS_TAG);

						for (int i = 0; i < adviceBindingsConfigurations.getLength(); i++) {
							Element adviceBinding = (Element) adviceBindingsConfigurations.item(i);

							Node type = adviceBinding.getAttributes().getNamedItem(TYPE_ATTRIBUTE);

							if (type == null) {
								adviceBinding.setAttribute(TYPE_ATTRIBUTE, ADVICEBINDINGCONFIGURATION_TYPE);
							} else if (!type.getNodeValue().equals(ADVICEBINDINGCONFIGURATION_TYPE)) {
								adviceBinding.removeAttribute(EDITHELPERADVICECLASSNAME_ATTRIBUTE);
							}

							adviceBinding.removeAttribute(NAME_ATTRIBUTE);
						}

						NodeList matcherConfigurations = root.getElementsByTagName(MATCHERCONFIGURATION_TAG);

						for (int i = 0; i < matcherConfigurations.getLength(); i++) {
							Element matcher = (Element) matcherConfigurations.item(i);

							Node type = matcher.getAttributes().getNamedItem(TYPE_ATTRIBUTE);

							if (type == null) {
								matcher.setAttribute(TYPE_ATTRIBUTE, MATCHER_TYPE);
							} else if (!type.getNodeValue().equals(MATCHER_TYPE)) {
								matcher.removeAttribute(MATCHERCLASSNAME_ATTRIBUTE);
							}
						}

						NodeList invariantRuleConfigurations = root.getElementsByTagName(INVARIANTRULECONFIGURATION_TAG);


						for (int i = 0; i < invariantRuleConfigurations.getLength(); i++) {
							Element invariantRule = (Element) invariantRuleConfigurations.item(i);

							doc.renameNode(invariantRule, null, RULECONFIGURATION_TAG);


						}

						migrate(root.getChildNodes(), mapMigration);

						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
						Result output = new StreamResult(file);
						Source input = new DOMSource(doc);

						transformer.transform(input, output);


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



	void migrate(NodeList nodeList, Map<String, String> mapMigration) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node childNode = nodeList.item(i);

			NamedNodeMap attributes = childNode.getAttributes();
			if (attributes != null) {
				for (int j = 0; j < attributes.getLength(); j++) {
					Node childAttr = attributes.item(j);

					if (childAttr.getNodeName().equals(TYPE_ATTRIBUTE)) {

						for (String old : mapMigration.keySet()) {
							if (childAttr.getNodeValue().contains(old)) {
								String newNS = mapMigration.get(old);
								String newValue = childAttr.getNodeValue().replace(old, newNS);
								childAttr.setNodeValue(newValue);
							}
						}

					}
				}
			}

			NodeList children = childNode.getChildNodes();
			if (children != null) {
				migrate(children, mapMigration);
			}
		}
	}



}

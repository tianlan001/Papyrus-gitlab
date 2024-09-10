/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen;

import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.gmf.codegen.gmfgen.DynamicModelAccess;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContextMenu;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenParsers;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertySheet;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Editor Generator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getAudits <em>Audits</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getMetrics <em>Metrics</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagram <em>Diagram</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPlugin <em>Plugin</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getEditor <em>Editor</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getNavigator <em>Navigator</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagramUpdater <em>Diagram Updater</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPropertySheet <em>Property Sheet</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getApplication <em>Application</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDomainGenModel <em>Domain Gen Model</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPackageNamePrefix <em>Package Name Prefix</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getModelID <em>Model ID</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#isSameFileForDiagramAndModel <em>Same File For Diagram And Model</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagramFileExtension <em>Diagram File Extension</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDomainFileExtension <em>Domain File Extension</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#isDynamicTemplates <em>Dynamic Templates</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getTemplateDirectory <em>Template Directory</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getCopyrightText <em>Copyright Text</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getExpressionProviders <em>Expression Providers</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getModelAccess <em>Model Access</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getLabelParsers <em>Label Parsers</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getContextMenus <em>Context Menus</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPluginDirectory <em>Plugin Directory</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getJdkComplianceLevel <em>Jdk Compliance Level</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator()
 * @model
 * @generated
 */
public interface GenEditorGenerator extends EObject {
	/**
	 * Returns the value of the '<em><b>Audits</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Audits</em>' containment reference.
	 * @see #setAudits(GenAuditRoot)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Audits()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	GenAuditRoot getAudits();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getAudits <em>Audits</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Audits</em>' containment reference.
	 * @see #getAudits()
	 * @generated
	 */
	void setAudits(GenAuditRoot value);

	/**
	 * Returns the value of the '<em><b>Metrics</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricContainer#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metrics</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metrics</em>' containment reference.
	 * @see #setMetrics(GenMetricContainer)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Metrics()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricContainer#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	GenMetricContainer getMetrics();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getMetrics <em>Metrics</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metrics</em>' containment reference.
	 * @see #getMetrics()
	 * @generated
	 */
	void setMetrics(GenMetricContainer value);

	/**
	 * Returns the value of the '<em><b>Diagram</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram</em>' containment reference.
	 * @see #setDiagram(GenDiagram)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Diagram()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram#getEditorGen
	 * @model opposite="editorGen" containment="true" required="true"
	 * @generated
	 */
	GenDiagram getDiagram();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagram <em>Diagram</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram</em>' containment reference.
	 * @see #getDiagram()
	 * @generated
	 */
	void setDiagram(GenDiagram value);

	/**
	 * Returns the value of the '<em><b>Plugin</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin</em>' containment reference.
	 * @see #setPlugin(GenPlugin)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Plugin()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenPlugin#getEditorGen
	 * @model opposite="editorGen" containment="true" required="true"
	 * @generated
	 */
	GenPlugin getPlugin();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPlugin <em>Plugin</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin</em>' containment reference.
	 * @see #getPlugin()
	 * @generated
	 */
	void setPlugin(GenPlugin value);

	/**
	 * Returns the value of the '<em><b>Editor</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Editor</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Editor</em>' containment reference.
	 * @see #setEditor(GenEditorView)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Editor()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorView#getEditorGen
	 * @model opposite="editorGen" containment="true" required="true"
	 * @generated
	 */
	GenEditorView getEditor();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getEditor <em>Editor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Editor</em>' containment reference.
	 * @see #getEditor()
	 * @generated
	 */
	void setEditor(GenEditorView value);

	/**
	 * Returns the value of the '<em><b>Navigator</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Navigator</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Navigator</em>' containment reference.
	 * @see #setNavigator(GenNavigator)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Navigator()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenNavigator#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	GenNavigator getNavigator();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getNavigator <em>Navigator</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Navigator</em>' containment reference.
	 * @see #getNavigator()
	 * @generated
	 */
	void setNavigator(GenNavigator value);

	/**
	 * Returns the value of the '<em><b>Diagram Updater</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Updater</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Updater</em>' containment reference.
	 * @see #setDiagramUpdater(GenDiagramUpdater)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_DiagramUpdater()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagramUpdater#getEditorGen
	 * @model opposite="editorGen" containment="true" required="true"
	 * @generated
	 */
	GenDiagramUpdater getDiagramUpdater();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagramUpdater <em>Diagram Updater</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Updater</em>' containment reference.
	 * @see #getDiagramUpdater()
	 * @generated
	 */
	void setDiagramUpdater(GenDiagramUpdater value);

	/**
	 * Returns the value of the '<em><b>Property Sheet</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertySheet#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Sheet</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Sheet</em>' containment reference.
	 * @see #setPropertySheet(GenPropertySheet)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_PropertySheet()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenPropertySheet#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	GenPropertySheet getPropertySheet();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPropertySheet <em>Property Sheet</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Sheet</em>' containment reference.
	 * @see #getPropertySheet()
	 * @generated
	 */
	void setPropertySheet(GenPropertySheet value);

	/**
	 * Returns the value of the '<em><b>Application</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If application is defined within the model then generator should target RCP
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Application</em>' containment reference.
	 * @see #setApplication(GenApplication)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Application()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenApplication#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	GenApplication getApplication();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getApplication <em>Application</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application</em>' containment reference.
	 * @see #getApplication()
	 * @generated
	 */
	void setApplication(GenApplication value);

	/**
	 * Returns the value of the '<em><b>Domain Gen Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This is primary EMF genmodel for user's domain model
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Domain Gen Model</em>' reference.
	 * @see #setDomainGenModel(GenModel)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_DomainGenModel()
	 * @model
	 * @generated
	 */
	GenModel getDomainGenModel();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDomainGenModel <em>Domain Gen Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Gen Model</em>' reference.
	 * @see #getDomainGenModel()
	 * @generated
	 */
	void setDomainGenModel(GenModel value);

	/**
	 * Returns the value of the '<em><b>Package Name Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Derived from genModel.genPackage[0].getBasePackage() by default
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Package Name Prefix</em>' attribute.
	 * @see #setPackageNamePrefix(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_PackageNamePrefix()
	 * @model
	 * @generated
	 */
	String getPackageNamePrefix();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPackageNamePrefix <em>Package Name Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name Prefix</em>' attribute.
	 * @see #getPackageNamePrefix()
	 * @generated
	 */
	void setPackageNamePrefix(String value);

	/**
	 * Returns the value of the '<em><b>Model ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model ID</em>' attribute.
	 * @see #setModelID(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_ModelID()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getModelID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getModelID <em>Model ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model ID</em>' attribute.
	 * @see #getModelID()
	 * @generated
	 */
	void setModelID(String value);

	/**
	 * Returns the value of the '<em><b>Same File For Diagram And Model</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Specified whether editor should create separate file for domain elements if diagram file references no domain model
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Same File For Diagram And Model</em>' attribute.
	 * @see #setSameFileForDiagramAndModel(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_SameFileForDiagramAndModel()
	 * @model
	 * @generated
	 */
	boolean isSameFileForDiagramAndModel();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#isSameFileForDiagramAndModel <em>Same File For Diagram And Model</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Same File For Diagram And Model</em>' attribute.
	 * @see #isSameFileForDiagramAndModel()
	 * @generated
	 */
	void setSameFileForDiagramAndModel(boolean value);

	/**
	 * Returns the value of the '<em><b>Diagram File Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram File Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Distinct even when sameFileForDiagramAndModel is true. On init diagram, we just copy domain model element to new file with that extension. I won't try to set this equal to domainFileExtension
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Diagram File Extension</em>' attribute.
	 * @see #setDiagramFileExtension(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_DiagramFileExtension()
	 * @model
	 * @generated
	 */
	String getDiagramFileExtension();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDiagramFileExtension <em>Diagram File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram File Extension</em>' attribute.
	 * @see #getDiagramFileExtension()
	 * @generated
	 */
	void setDiagramFileExtension(String value);

	/**
	 * Returns the value of the '<em><b>Domain File Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * By default, genModel.genPackage[0].getPrefix().toLowerCase(), as in emf/templates/editor/pluign.xmljet
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Domain File Extension</em>' attribute.
	 * @see #setDomainFileExtension(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_DomainFileExtension()
	 * @model
	 * @generated
	 */
	String getDomainFileExtension();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getDomainFileExtension <em>Domain File Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain File Extension</em>' attribute.
	 * @see #getDomainFileExtension()
	 * @generated
	 */
	void setDomainFileExtension(String value);

	/**
	 * Returns the value of the '<em><b>Dynamic Templates</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dynamic Templates</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dynamic Templates</em>' attribute.
	 * @see #setDynamicTemplates(boolean)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_DynamicTemplates()
	 * @model default="false"
	 * @generated
	 */
	boolean isDynamicTemplates();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#isDynamicTemplates <em>Dynamic Templates</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dynamic Templates</em>' attribute.
	 * @see #isDynamicTemplates()
	 * @generated
	 */
	void setDynamicTemplates(boolean value);

	/**
	 * Returns the value of the '<em><b>Template Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Directory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Directory</em>' attribute.
	 * @see #setTemplateDirectory(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_TemplateDirectory()
	 * @model
	 * @generated
	 */
	String getTemplateDirectory();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getTemplateDirectory <em>Template Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Directory</em>' attribute.
	 * @see #getTemplateDirectory()
	 * @generated
	 */
	void setTemplateDirectory(String value);

	/**
	 * Returns the value of the '<em><b>Copyright Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Copyright Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Copyright Text</em>' attribute.
	 * @see #setCopyrightText(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_CopyrightText()
	 * @model
	 * @generated
	 */
	String getCopyrightText();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getCopyrightText <em>Copyright Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Copyright Text</em>' attribute.
	 * @see #getCopyrightText()
	 * @generated
	 */
	void setCopyrightText(String value);

	/**
	 * Returns the value of the '<em><b>Expression Providers</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderContainer#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression Providers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression Providers</em>' containment reference.
	 * @see #setExpressionProviders(GenExpressionProviderContainer)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_ExpressionProviders()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenExpressionProviderContainer#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	GenExpressionProviderContainer getExpressionProviders();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getExpressionProviders <em>Expression Providers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression Providers</em>' containment reference.
	 * @see #getExpressionProviders()
	 * @generated
	 */
	void setExpressionProviders(GenExpressionProviderContainer value);

	/**
	 * Returns the value of the '<em><b>Model Access</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.DynamicModelAccess#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If present, specifies dynamic access to domain model(s), without using generated Java code
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Model Access</em>' containment reference.
	 * @see #setModelAccess(DynamicModelAccess)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_ModelAccess()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.DynamicModelAccess#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 * @generated
	 */
	DynamicModelAccess getModelAccess();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getModelAccess <em>Model Access</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Access</em>' containment reference.
	 * @see #getModelAccess()
	 * @generated
	 */
	void setModelAccess(DynamicModelAccess value);

	/**
	 * Returns the value of the '<em><b>Label Parsers</b></em>' containment reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenParsers#getEditorGen <em>Editor Gen</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Parsers</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * If present, describes access to and types of parser implementations
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Label Parsers</em>' containment reference.
	 * @see #setLabelParsers(GenParsers)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_LabelParsers()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenParsers#getEditorGen
	 * @model opposite="editorGen" containment="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='labelParsers.oclIsUndefined() implies (diagram.getAllNodes()-&gt;forAll(labels-&gt;size()=0) and diagram.links-&gt;forAll(labels-&gt;size()=0) and not diagram.childNodes-&gt;exists(oclIsKindOf(GenChildLabelNode)))' description='No label may be declared without a parser'"
	 * @generated
	 */
	GenParsers getLabelParsers();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getLabelParsers <em>Label Parsers</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Parsers</em>' containment reference.
	 * @see #getLabelParsers()
	 * @generated
	 */
	void setLabelParsers(GenParsers value);

	/**
	 * Returns the value of the '<em><b>Context Menus</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenContextMenu}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context Menus</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context Menus</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_ContextMenus()
	 * @model containment="true"
	 * @generated
	 */
	EList<GenContextMenu> getContextMenus();

	/**
	 * Returns the value of the '<em><b>Plugin Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target directory for generated editor code. Similar to EMF's GenModel#editorDirectory
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Plugin Directory</em>' attribute.
	 * @see #setPluginDirectory(String)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_PluginDirectory()
	 * @model
	 * @generated
	 */
	String getPluginDirectory();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getPluginDirectory <em>Plugin Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin Directory</em>' attribute.
	 * @see #getPluginDirectory()
	 * @generated
	 */
	void setPluginDirectory(String value);

	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension#getGenerator <em>Generator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Custom extensions for this generator
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Extensions</em>' containment reference list.
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_Extensions()
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomGeneratorExtension#getGenerator
	 * @model opposite="generator" containment="true"
	 * @generated
	 */
	EList<GenCustomGeneratorExtension> getExtensions();

	/**
	 * Returns the value of the '<em><b>Jdk Compliance Level</b></em>' attribute.
	 * The default value is <code>"11.0"</code>.
	 * The literals are from the enumeration {@link org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jdk Compliance Level</em>' attribute.
	 * @see org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
	 * @see #setJdkComplianceLevel(GenJDKLevel)
	 * @see org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage#getGenEditorGenerator_JdkComplianceLevel()
	 * @model default="11.0"
	 * @generated
	 */
	GenJDKLevel getJdkComplianceLevel();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator#getJdkComplianceLevel <em>Jdk Compliance Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jdk Compliance Level</em>' attribute.
	 * @see org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
	 * @see #getJdkComplianceLevel()
	 * @generated
	 */
	void setJdkComplianceLevel(GenJDKLevel value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Returns all genpackages from domain genmodel
	 * <!-- end-model-doc -->
	 * @model
	 * @generated
	 */
	EList<GenPackage> getAllDomainGenPackages(boolean withUsed);

	boolean hasAudits(); // Indicates whether this generator defines at least one AuditRule
} // GenEditorGenerator

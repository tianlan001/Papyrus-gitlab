/*****************************************************************************
 * Copyright (c) 2013, 2014, 2023 CEA LIST and others.
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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus (CEA LIST) - Fix leaking of all UML models in search results
 *  Christian W. Damus (CEA LIST) - Replace workspace IResource dependency with URI for CDO compatibility
 *  Christian W. Damus (CEA) - bug 434681
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.pages;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.ocl.examples.xtext.console.xtfo.EmbeddedXtextEditor;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.internal.context.EObjectContext;
import org.eclipse.ocl.pivot.resource.CSResource;
import org.eclipse.ocl.pivot.utilities.EnvironmentFactory;
import org.eclipse.ocl.pivot.utilities.ParserContext;
import org.eclipse.ocl.pivot.utilities.ParserException;
import org.eclipse.ocl.pivot.utilities.PivotUtil;
import org.eclipse.ocl.pivot.values.InvalidValueException;
import org.eclipse.ocl.pivot.values.Value;
import org.eclipse.ocl.xtext.base.ui.model.BaseDocument;
import org.eclipse.ocl.xtext.essentialocl.ui.internal.EssentialOCLActivator;
import org.eclipse.ocl.xtext.essentialocl.utilities.EssentialOCLCSResource;
import org.eclipse.ocl.xtext.essentialocl.utilities.EssentialOCLPlugin;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.labelprovider.service.impl.LabelProviderServiceImpl;
import org.eclipse.papyrus.uml.search.ui.Activator;
import org.eclipse.papyrus.uml.search.ui.CheckBoxFilteredTree;
import org.eclipse.papyrus.uml.search.ui.Messages;
import org.eclipse.papyrus.uml.search.ui.actions.ReplaceAction;
import org.eclipse.papyrus.uml.search.ui.listeners.ParticipantTypesTreeViewerCheckStateListener;
import org.eclipse.papyrus.uml.search.ui.providers.OCLContextContentProvider;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeAttribute;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeContentProvider;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeElement;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeLabelProvider;
import org.eclipse.papyrus.uml.search.ui.query.AbstractPapyrusQuery;
import org.eclipse.papyrus.uml.search.ui.query.CompositePapyrusQuery;
import org.eclipse.papyrus.uml.search.ui.query.CompositePapyrusQueryProvider;
import org.eclipse.papyrus.uml.search.ui.query.PapyrusOCLQuery;
import org.eclipse.papyrus.uml.search.ui.query.QueryInfo;
import org.eclipse.papyrus.uml.search.ui.query.WorkspaceQueryProvider;
import org.eclipse.papyrus.uml.stereotypecollector.StereotypeCollector;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.views.search.regex.PatternHelper;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;
import org.eclipse.papyrus.views.search.scope.ScopeCollector;
import org.eclipse.papyrus.views.search.scope.ScopeEntry;
import org.eclipse.search.ui.IReplacePage;
import org.eclipse.search.ui.ISearchPage;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResultPage;
import org.eclipse.search.ui.ISearchResultViewPart;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLUtil;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.EObjectNode;
import org.eclipse.xtext.ui.editor.outline.impl.EStructuralFeatureNode;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Injector;

/**
 *
 * Papyrus specific search page
 *
 * @since 2.0
 *
 */
public class PapyrusSearchPage extends DialogPage implements ISearchPage, IReplacePage {

	private int currentScope = -1;

	private HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> umlTypeParticipantsList = new HashMap<>();

	private HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> stereotypeParticipantsList = new HashMap<>();

	private LinkedList<Object> profiles = new LinkedList<>();

	private String allProfiles = "*"; //$NON-NLS-1$

	private Collection<Stereotype> availableStereotypes;

	private Collection<Stereotype> appliedStereotypes;

	private boolean profilesComputed = false;

	private boolean availableStereotypesComputed = false;

	private boolean appliedStereotypesComputed = false;

	private static final String REGULAR_EXPRESSION_ILLFORMED = Messages.PapyrusSearchPage_0;

	private static final String OCL_QUERY_ILLFORMED = Messages.PapyrusSearchPage_0;

	private static final String SEARCH_ISSUE = Messages.PapyrusSearchPage_1;

	private Text searchQueryText;

	private Text oclContext;

	private ISearchPageContainer container;

	private CheckBoxFilteredTree participantUMLTypesTree;

	private CheckBoxFilteredTree participantStereotypesTree;

	private CheckboxTreeViewer participantUMLTypesTreeViewer;

	private CheckboxTreeViewer participantStereotypesTreeViewer;

	private ComboViewer participantProfilesComboViewer;

	private Label searchQueryExplanatoryLabel;

	private Button btnRegularExpression;

	private Button btnCaseSensitive;

	private Button btnSearchAllStringAttributes;

	private Button btnSearchInName;

	private Combo queryKind;

	private Combo searchKind;

	private EmbeddedXtextEditor oclEditor;

	private Composite queryComposite;

	private EObject contextObject;

	private static final int TEXT_QUERY_KIND = 0;

	private static final int OCL_QUERY_KIND = 1;

	private static final int SIMPLE_SEARCH = 0;

	private static final int ADVANCED_SEARCH = 1;

	private int currentSearchKind = SIMPLE_SEARCH;

	private int currentQueryKind = TEXT_QUERY_KIND;

	private boolean onlyAppliedStereotypes = false;

	private boolean onlyAppliedStereotypesStateChanged = true;

	private Profile selectedProfile = null;

	private ParserContext parserContext;

	protected Composite textQueryComposite;

	private Composite advancedSearchComposite;

	private Composite textQueryFieldsComposite;

	private Button fBtnOnlyAppliedStereotypes;

	private Button fBtnSearchForAllSelected;

	private Button fBtnSearchForAnySelected;

	private Label umlTypesLabel;

	private Label stereotypesLabel;

	private Label profilesLabel;

	private Label emptyLabel;

	protected void createTextSearch() {
		textQueryComposite = new Composite(queryComposite, SWT.NONE);
		textQueryComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		textQueryComposite.setLayout(new GridLayout(2, false));

		textQueryFieldsComposite = new Composite(textQueryComposite, SWT.NONE);
		textQueryFieldsComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		textQueryFieldsComposite.setLayout(new GridLayout(1, false));

		searchQueryExplanatoryLabel = new Label(textQueryFieldsComposite, SWT.NONE);
		searchQueryExplanatoryLabel.setText(Messages.PapyrusSearchPage_48);
		searchQueryExplanatoryLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		searchQueryText = new Text(textQueryFieldsComposite, SWT.BORDER);
		searchQueryText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		searchQueryText.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent evt) {
				validateRegex();
			}
		});
		searchQueryText.setFocus();

		Composite compositeParameters = new Composite(textQueryComposite, SWT.NONE);
		compositeParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		compositeParameters.setLayout(new GridLayout(1, false));

		btnCaseSensitive = new Button(compositeParameters, SWT.CHECK);
		btnCaseSensitive.setText(Messages.PapyrusSearchPage_5);

		btnRegularExpression = new Button(compositeParameters, SWT.CHECK);
		btnRegularExpression.setText(Messages.PapyrusSearchPage_6);
		btnRegularExpression.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				btnCaseSensitive.setEnabled(!btnRegularExpression.getSelection());
				validateRegex();
				searchQueryText.forceFocus();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		Group grpSearchFor = new Group(textQueryComposite, SWT.NONE);
		grpSearchFor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		grpSearchFor.setLayout(new GridLayout(1, false));
		grpSearchFor.setText(Messages.PapyrusSearchPage_8);

		Composite groupComposite = new Composite(grpSearchFor, SWT.NONE);
		groupComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		groupComposite.setLayout(new GridLayout(2, false));

		Label lblSearchKind = new Label(groupComposite, SWT.NONE);
		lblSearchKind.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblSearchKind.setText(Messages.PapyrusSearchPage_7);

		searchKind = new Combo(groupComposite, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		searchKind.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		searchKind.add(Messages.PapyrusSearchPage_11);
		searchKind.add(Messages.PapyrusSearchPage_12);
		searchKind.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (searchKind.getSelectionIndex() != currentSearchKind) {
					for (Control childControl : advancedSearchComposite.getChildren()) {
						childControl.dispose();
					}

					createSpecificTextSearch();

					advancedSearchComposite.layout();
				}
			}
		});

		advancedSearchComposite = new Composite(groupComposite, SWT.NONE);
		// gd_advancedSearchComposite.widthHint = 479;
		advancedSearchComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		advancedSearchComposite.setLayout(new GridLayout(3, false));

		if (currentSearchKind == ADVANCED_SEARCH) {
			searchKind.select(ADVANCED_SEARCH);
		} else {
			searchKind.select(SIMPLE_SEARCH);
		}
		createSpecificTextSearch();
	}

	protected void createSpecificTextSearch() {
		if (searchKind.getSelectionIndex() == ADVANCED_SEARCH) {

			if (container.getSelectedScope() == currentScope || currentScope == -1) { // if scope not changed or first time
				if (umlTypeParticipantsList.isEmpty()) {
					createUMLTypesList();
				}

				createProfilesList(false);
				createStereotypesList(false);

				createAdvancedSearch(); // Don't call any methods of UI entities before this!
				participantProfilesComboViewer.refresh();
				participantUMLTypesTreeViewer.refresh();
				filterParticipantStereotypesByProfile(); // This refreshes the participantStereotypesTreeViewer

				currentScope = container.getSelectedScope();
			} else { // else: same code as refresh button except we need to recreate the UI
				// Refresh UML types
				if (umlTypeParticipantsList.isEmpty()) {
					createUMLTypesList();
				}

				// Refresh profiles
				selectedProfile = null;
				profilesComputed = false;
				createProfilesList(true);

				// Refresh stereotypes
				availableStereotypesComputed = false;
				appliedStereotypesComputed = false;
				createStereotypesList(true);

				// Refresh UI
				createAdvancedSearch(); // Don't call any methods of UI entities before this!
				participantProfilesComboViewer.refresh();
				participantUMLTypesTreeViewer.refresh();
				filterParticipantStereotypesByProfile(); // This refreshes the participantStereotypesTreeViewer

				currentScope = container.getSelectedScope();
			}


		} else {
			createSimpleSearch();
		}
	}

	protected void createUMLTypesList() {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		IRunnableWithProgress computeAvailableTypes = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor thePM) throws InterruptedException {

				// UML metaclasses
				for (EClassifier eClassifier : UMLPackage.eINSTANCE.getEClassifiers()) {
					if (eClassifier instanceof EClass) {
						ParticipantTypeElement parentElement = new ParticipantTypeElement(eClassifier);
						if (parentElement.getElement() instanceof EClass) {
							List<ParticipantTypeAttribute> attributeList = new ArrayList<>();
							for (EObject eAttribute : ((EClass) (parentElement).getElement()).getEAllAttributes()) {
								ParticipantTypeAttribute attribute = new ParticipantTypeAttribute(eAttribute, (parentElement));
								attributeList.add(attribute);
							}
							umlTypeParticipantsList.put(parentElement, attributeList);
						}
					}
				}
			}

		};

		try {
			dialog.run(true, true, computeAvailableTypes);
		} catch (InvocationTargetException e1) {
			Activator.log.error(e1);
		} catch (InterruptedException e1) {
			Activator.log.error(e1);
		}

	}

	protected void createProfilesList(boolean forceRefresh) {
		if (forceRefresh || !profilesComputed) {
			profiles.clear();

			ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
			IRunnableWithProgress computeAvailableTypes = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor thePM) throws InterruptedException {
					Collection<Profile> appliedProfiles = StereotypeCollector.getInstance().computeAppliedProfiles(container);
					profiles.add(allProfiles);
					profiles.addAll(appliedProfiles);
					profilesComputed = true;
				}
			};

			try {
				dialog.run(true, true, computeAvailableTypes);
			} catch (InvocationTargetException e1) {
				Activator.log.error(e1);
			} catch (InterruptedException e1) {
				Activator.log.error(e1);
			}
		}
	}

	protected void createStereotypesList(final boolean forceRefresh) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		IRunnableWithProgress computeAvailableTypes = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor thePM) throws InterruptedException {
				if (forceRefresh || onlyAppliedStereotypesStateChanged) {
					stereotypeParticipantsList.clear();

					Collection<Stereotype> computedStereotypes;

					// Find available stereotypes
					if (onlyAppliedStereotypes) {
						if (forceRefresh || !appliedStereotypesComputed) {
							if (appliedStereotypes != null) {
								appliedStereotypes.clear();
							}

							appliedStereotypes = StereotypeCollector.getInstance().computeAppliedStereotypes(container);
							appliedStereotypesComputed = true;
						}

						computedStereotypes = appliedStereotypes;
					} else { // Find applied stereotypes
						if (forceRefresh || !availableStereotypesComputed) {
							if (availableStereotypes != null) {
								availableStereotypes.clear();
							}

							availableStereotypes = StereotypeCollector.getInstance().computeAvailableStereotypes(container);
							availableStereotypesComputed = true;
						}

						computedStereotypes = availableStereotypes;
					}

					// Fill the hash map for the treeviewer
					for (Stereotype stereotype : computedStereotypes) {
						ParticipantTypeElement parentElement = new ParticipantTypeElement(stereotype);
						List<ParticipantTypeAttribute> attributeList = new ArrayList<>();
						for (Property property : ((Stereotype) parentElement.getElement()).getAllAttributes()) {
							if (!property.getName().startsWith("base_")) { //$NON-NLS-1$
								if (property.getType() instanceof Element) {
									ParticipantTypeAttribute attribute = new ParticipantTypeAttribute(property, parentElement);
									attributeList.add(attribute);
								}
							}
						}

						stereotypeParticipantsList.put(parentElement, attributeList);
					}

					onlyAppliedStereotypesStateChanged = false;
				}
			}
		};

		try {
			dialog.run(true, true, computeAvailableTypes);
		} catch (InvocationTargetException e1) {
			Activator.log.error(e1);
		} catch (InterruptedException e1) {
			Activator.log.error(e1);
		}
	}

	protected void filterParticipantStereotypesByProfile() {
		if (selectedProfile == null) {
			participantStereotypesTreeViewer.setInput(stereotypeParticipantsList);
			participantStereotypesTreeViewer.refresh();
			return;
		}

		HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> filteredStereotypeParticipantsList = new HashMap<>();

		Iterator<Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>>> it = stereotypeParticipantsList.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>> pair = it.next();
			Stereotype stereotype = (Stereotype) pair.getKey().getElement();
			if (EcoreUtil.getURI(stereotype.getProfile()).equals(EcoreUtil.getURI(selectedProfile))) {
				filteredStereotypeParticipantsList.put(pair.getKey(), pair.getValue());
			}
		}

		participantStereotypesTreeViewer.setInput(filteredStereotypeParticipantsList);
		participantStereotypesTreeViewer.refresh();
	}

	protected void createAdvancedSearch() {
		profilesLabel = new Label(advancedSearchComposite, SWT.NONE);
		profilesLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		profilesLabel.setText(Messages.PapyrusSearchPage_51);

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		// Newline

		participantProfilesComboViewer = new ComboViewer(advancedSearchComposite, SWT.READ_ONLY);
		participantProfilesComboViewer.setContentProvider(ArrayContentProvider.getInstance());
		participantProfilesComboViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Profile) {
					return ((Profile) element).getName();
				}
				return super.getText(element);
			}
		});
		participantProfilesComboViewer.setSorter(new ViewerSorter());
		participantProfilesComboViewer.setInput(profiles);
		if (selectedProfile == null) {
			participantProfilesComboViewer.setSelection(new StructuredSelection(allProfiles));
		} else {
			participantProfilesComboViewer.setSelection(new StructuredSelection(selectedProfile));
		}
		participantProfilesComboViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection().isEmpty()) {
					return;
				}

				if (participantProfilesComboViewer.getStructuredSelection().getFirstElement() instanceof Profile) {
					selectedProfile = (Profile) participantProfilesComboViewer.getStructuredSelection().getFirstElement();
				} else {
					selectedProfile = null;
				}

				filterParticipantStereotypesByProfile();
			}
		});

		fBtnOnlyAppliedStereotypes = new Button(advancedSearchComposite, SWT.CHECK);
		fBtnOnlyAppliedStereotypes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fBtnOnlyAppliedStereotypes.setText(Messages.PapyrusSearchPage_50);
		fBtnOnlyAppliedStereotypes.setSelection(onlyAppliedStereotypes);
		fBtnOnlyAppliedStereotypes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onlyAppliedStereotypes = fBtnOnlyAppliedStereotypes.getSelection();

				// Memorize previous stereotypes attributes that have been checked
				HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> oldStereotypeParticipantsList = new HashMap<>();
				Iterator<Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>>> it = stereotypeParticipantsList.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>> pair = it.next();
					boolean toAdd = false;
					if (pair.getKey().isChecked()) {
						toAdd = true;
					}

					if (!toAdd) {
						for (ParticipantTypeAttribute attribute : pair.getValue()) {
							if (attribute.isChecked()) {
								toAdd = true;
								break;
							}
						}
					}

					if (toAdd) {
						oldStereotypeParticipantsList.put(pair.getKey(), pair.getValue());
					}
				}

				onlyAppliedStereotypesStateChanged = true;
				createStereotypesList(false);

				Iterator<Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>>> it2 = oldStereotypeParticipantsList.entrySet().iterator();
				while (it2.hasNext()) {
					Map.Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>> oldPair = it2.next();
					Stereotype oldStereotype = (Stereotype) oldPair.getKey().getElement();

					Iterator<Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>>> it3 = stereotypeParticipantsList.entrySet().iterator();
					while (it3.hasNext()) {
						Map.Entry<ParticipantTypeElement, List<ParticipantTypeAttribute>> newPair = it3.next();
						Stereotype newStereotype = (Stereotype) newPair.getKey().getElement();

						// Lazy second condition because otherwise performance is impacted too much
						if (EcoreUtil.getURI(newStereotype).equals(EcoreUtil.getURI(oldStereotype))
								&& newPair.getValue().size() == oldPair.getValue().size()) {
							newPair.getKey().setChecked(oldPair.getKey().isChecked());
							for (int i = 0; i < oldPair.getValue().size(); i++) {
								newPair.getValue().get(i).setChecked(oldPair.getValue().get(i).isChecked());
							}
						}
					}
				}

				filterParticipantStereotypesByProfile(); // This refreshes the list too
			}
		});

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		// Newline

		umlTypesLabel = new Label(advancedSearchComposite, SWT.NONE);
		umlTypesLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		umlTypesLabel.setText(Messages.PapyrusSearchPage_44);

		stereotypesLabel = new Label(advancedSearchComposite, SWT.NONE);
		stereotypesLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		stereotypesLabel.setText(Messages.PapyrusSearchPage_45);

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		// New line

		participantUMLTypesTree = new CheckBoxFilteredTree(advancedSearchComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE, new PatternFilter(), true);
		participantUMLTypesTree.setLayout(new GridLayout());
		GridData typesChechboxTreeViewerGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		typesChechboxTreeViewerGridData.heightHint = 150;
		participantUMLTypesTree.setLayoutData(typesChechboxTreeViewerGridData);

		participantStereotypesTree = new CheckBoxFilteredTree(advancedSearchComposite, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE, new PatternFilter(), true);
		participantStereotypesTree.setLayout(new GridLayout());
		GridData stereotypesChechboxTreeViewerGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		stereotypesChechboxTreeViewerGridData.heightHint = 150;
		participantStereotypesTree.setLayoutData(stereotypesChechboxTreeViewerGridData);

		participantUMLTypesTreeViewer = (CheckboxTreeViewer) participantUMLTypesTree.getViewer();
		participantUMLTypesTreeViewer.setContentProvider(new ParticipantTypeContentProvider());
		participantUMLTypesTreeViewer.setLabelProvider(new ParticipantTypeLabelProvider());
		participantUMLTypesTreeViewer.setSorter(new ViewerSorter());
		participantUMLTypesTreeViewer.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof ParticipantTypeElement) {

					return ((ParticipantTypeElement) element).isChecked();

				}
				return false;
			}
		});

		participantStereotypesTreeViewer = (CheckboxTreeViewer) participantStereotypesTree.getViewer();
		// TODO ParticipantStereotypeContentProvider
		participantStereotypesTreeViewer.setContentProvider(new ParticipantTypeContentProvider());
		// TODO ParticipantStereotypeLabelProvider
		participantStereotypesTreeViewer.setLabelProvider(new ParticipantTypeLabelProvider());
		participantStereotypesTreeViewer.setSorter(new ViewerSorter());
		participantStereotypesTreeViewer.setCheckStateProvider(new ICheckStateProvider() {

			@Override
			public boolean isGrayed(Object element) {
				return false;
			}

			@Override
			public boolean isChecked(Object element) {
				if (element instanceof ParticipantTypeElement) {

					return ((ParticipantTypeElement) element).isChecked();

				}
				return false;
			}
		});

		Composite participantManipualtionComposite = new Composite(advancedSearchComposite, SWT.NONE);
		participantManipualtionComposite.setLayout(new GridLayout(1, false));
		participantManipualtionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, true, 1, 1));

		Button btnSelectSub = new Button(participantManipualtionComposite, SWT.PUSH);
		btnSelectSub.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSelectSub.setText(Messages.PapyrusSearchPage_14);
		btnSelectSub.setToolTipText(Messages.PapyrusSearchPageTooltip_1);
		btnSelectSub.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				ISelection selection = participantUMLTypesTreeViewer.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object selectedElement = ((IStructuredSelection) selection).getFirstElement();

					if (selectedElement instanceof ParticipantTypeElement) {
						List<ParticipantTypeAttribute> attributeParentList = new ArrayList<>();

						for (Object attribute : umlTypeParticipantsList.get(selectedElement)) {
							if (attribute instanceof ParticipantTypeAttribute) {
								if (((ParticipantTypeAttribute) attribute).isChecked()) {
									attributeParentList.add(((ParticipantTypeAttribute) attribute));
								}

							}
						}
						if (((ParticipantTypeElement) selectedElement).getElement() instanceof EClass) {

							selectAllSubUML((ParticipantTypeElement) selectedElement, attributeParentList);

						} else if (((ParticipantTypeElement) selectedElement).getElement() instanceof Stereotype) {

							selectAllSubSter((ParticipantTypeElement) selectedElement, attributeParentList);

						}
					}
				}

				participantUMLTypesTreeViewer.refresh();
			}
		});

		Button btnSelectAll = new Button(participantManipualtionComposite, SWT.PUSH);
		btnSelectAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSelectAll.setText(Messages.PapyrusSearchPage_9);

		btnSelectAll.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				for (ParticipantTypeElement element : umlTypeParticipantsList.keySet()) {
					if (!element.isChecked()) {
						element.setChecked(true);

						for (ParticipantTypeAttribute attribute : umlTypeParticipantsList.get(element)) {
							attribute.setChecked(true);

						}
					}
				}
				participantUMLTypesTreeViewer.refresh();

			}
		});

		Button btnDeselectAll = new Button(participantManipualtionComposite, SWT.PUSH);
		btnDeselectAll.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDeselectAll.setText(Messages.PapyrusSearchPage_10);
		btnDeselectAll.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				for (ParticipantTypeElement element : umlTypeParticipantsList.keySet()) {
					if (element.isChecked()) {
						element.setChecked(false);

						for (ParticipantTypeAttribute attribute : umlTypeParticipantsList.get(element)) {
							attribute.setChecked(false);


						}
					}
				}
				participantUMLTypesTreeViewer.refresh();
			}
		});

		Button btnSelectAllSt = new Button(participantManipualtionComposite, SWT.PUSH);
		btnSelectAllSt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnSelectAllSt.setText(Messages.PapyrusSearchPage_46);

		btnSelectAllSt.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				for (ParticipantTypeElement element : stereotypeParticipantsList.keySet()) {
					if (!element.isChecked()) {
						element.setChecked(true);

						for (ParticipantTypeAttribute attribute : stereotypeParticipantsList.get(element)) {
							attribute.setChecked(true);
						}
					}
				}
				participantStereotypesTreeViewer.refresh();

			}
		});

		Button btnDeselectAllSt = new Button(participantManipualtionComposite, SWT.PUSH);
		btnDeselectAllSt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnDeselectAllSt.setText(Messages.PapyrusSearchPage_47);
		btnDeselectAllSt.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				for (ParticipantTypeElement element : stereotypeParticipantsList.keySet()) {
					if (element.isChecked()) {
						element.setChecked(false);

						for (ParticipantTypeAttribute attribute : stereotypeParticipantsList.get(element)) {
							attribute.setChecked(false);
						}
					}
				}
				participantStereotypesTreeViewer.refresh();
			}
		});

		Button btnRefreshTypes = new Button(participantManipualtionComposite, SWT.PUSH);
		btnRefreshTypes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRefreshTypes.setText(Messages.PapyrusSearchPage_15);
		btnRefreshTypes.setToolTipText(Messages.PapyrusSearchPageTooltip_2);
		btnRefreshTypes.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {
				refreshByScope();
			}
		});

		participantUMLTypesTreeViewer.setInput(umlTypeParticipantsList);
		((ICheckable) participantUMLTypesTreeViewer).addCheckStateListener(new ParticipantTypesTreeViewerCheckStateListener(participantUMLTypesTreeViewer, umlTypeParticipantsList));

		participantStereotypesTreeViewer.setInput(stereotypeParticipantsList);
		((ICheckable) participantStereotypesTreeViewer).addCheckStateListener(new ParticipantTypesTreeViewerCheckStateListener(participantStereotypesTreeViewer, stereotypeParticipantsList));

		// New line

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		fBtnSearchForAllSelected = new Button(advancedSearchComposite, SWT.CHECK);
		fBtnSearchForAllSelected.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fBtnSearchForAllSelected.setText(Messages.PapyrusSearchPage_13);

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		// New line

		// TODO Better solution than this empty label
		emptyLabel = new Label(advancedSearchComposite, SWT.NONE);
		emptyLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		emptyLabel.setText(""); //$NON-NLS-1$

		fBtnSearchForAnySelected = new Button(advancedSearchComposite, SWT.CHECK);
		fBtnSearchForAnySelected.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		fBtnSearchForAnySelected.setText(Messages.PapyrusSearchPage_49);

		currentSearchKind = ADVANCED_SEARCH;
		currentQueryKind = TEXT_QUERY_KIND;
	}

	protected void refreshByScope() {
		// Refresh UML types
		if (umlTypeParticipantsList.isEmpty()) {
			createUMLTypesList();
		}

		// Refresh profiles
		selectedProfile = null;
		profilesComputed = false;
		createProfilesList(true);

		// Refresh stereotypes
		availableStereotypesComputed = false;
		appliedStereotypesComputed = false;
		createStereotypesList(true);


		// Refresh UI
		participantProfilesComboViewer.setSelection(new StructuredSelection(allProfiles));
		participantProfilesComboViewer.refresh();
		participantUMLTypesTreeViewer.refresh();
		filterParticipantStereotypesByProfile(); // This refreshes the participantStereotypesTreeViewer
	}

	protected void selectAllSubSter(final ParticipantTypeElement elementParent, final List<ParticipantTypeAttribute> attributeParentList) {

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		IRunnableWithProgress computeAvailableTypes = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor thePM) throws InterruptedException {
				for (Object element : umlTypeParticipantsList.keySet()) {
					if (element instanceof ParticipantTypeElement) {
						checkAllSubSter((ParticipantTypeElement) element, elementParent, attributeParentList);

					}
				}


			}

		};


		try {
			dialog.run(true, true, computeAvailableTypes);
		} catch (InvocationTargetException e) {
			Activator.log.error(e);
		} catch (InterruptedException e) {
			Activator.log.error(e);
		}


	}

	protected void checkAllSubSter(ParticipantTypeElement element, ParticipantTypeElement elementParent, List<ParticipantTypeAttribute> attributeParentList) {
		if (element.getElement() instanceof Stereotype) {
			List<Class> superTypes = ((Class) element.getElement()).getSuperClasses();

			if (superTypes.contains(elementParent.getElement())) {
				element.setChecked(true);


				// Proceed with attributes
				for (ParticipantTypeAttribute attributeParent : attributeParentList) {
					for (ParticipantTypeAttribute attributeToEvaluate : umlTypeParticipantsList.get(element)) {
						if (attributeParent.getElement() == attributeToEvaluate.getElement()) {

							attributeToEvaluate.setChecked(true);


						}

					}
				}
			}
		}
	}

	protected void selectAllSubUML(final ParticipantTypeElement elementParent, final List<ParticipantTypeAttribute> attributeParentList) {

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		IRunnableWithProgress computeAvailableTypes = new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor thePM) throws InterruptedException {
				for (Object element : umlTypeParticipantsList.keySet()) {
					if (element instanceof ParticipantTypeElement) {
						checkAllSubUML((ParticipantTypeElement) element, elementParent, attributeParentList);

					}
				}
			}

		};

		try {
			dialog.run(true, true, computeAvailableTypes);
		} catch (InvocationTargetException e1) {
			Activator.log.error(e1);
		} catch (InterruptedException e1) {
			Activator.log.error(e1);
		}

	}


	protected void checkAllSubUML(ParticipantTypeElement element, ParticipantTypeElement elementParent, List<ParticipantTypeAttribute> attributeParentList) {
		if (element.getElement() instanceof EClass) {
			List<EClass> superTypes = ((EClass) element.getElement()).getEAllSuperTypes();

			if (superTypes.contains(elementParent.getElement())) {
				// participantTypesTreeViewer.setChecked(element, true);
				element.setChecked(true);


				// Proceed with attributes
				for (ParticipantTypeAttribute attributeParent : attributeParentList) {
					for (ParticipantTypeAttribute attributeToEvaluate : umlTypeParticipantsList.get(element)) {
						if (attributeParent.getElement() == attributeToEvaluate.getElement()) {
							attributeToEvaluate.setChecked(true);
						}
					}
				}
			}
		}
	}


	protected void createSimpleSearch() {
		Composite participantManipualtionComposite = new Composite(advancedSearchComposite, SWT.NONE);
		participantManipualtionComposite.setLayout(new GridLayout(1, false));
		participantManipualtionComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true, 1, 1));


		btnSearchInName = new Button(participantManipualtionComposite, SWT.RADIO);
		btnSearchInName.setText(Messages.PapyrusSearchPage_16);
		btnSearchInName.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 0, 1));
		btnSearchInName.setSelection(true);


		btnSearchAllStringAttributes = new Button(participantManipualtionComposite, SWT.RADIO);
		btnSearchAllStringAttributes.setText(Messages.PapyrusSearchPage_17);
		btnSearchAllStringAttributes.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));

		currentSearchKind = SIMPLE_SEARCH;
		currentQueryKind = TEXT_QUERY_KIND;
	}


	public Object[] getMetaClassesList() {
		Set<EObject> umlMetaClasses = new HashSet<>();

		for (EClassifier eClassifier : UMLPackage.eINSTANCE.getEClassifiers()) {
			if (eClassifier instanceof EClass) {
				umlMetaClasses.add(eClassifier);
			}
		}
		return umlMetaClasses.toArray();

	}


	protected void createOCLSearch(EObject root) {

		Composite client = queryComposite;

		Composite oclContextComposite = new Composite(client, SWT.NONE);
		oclContextComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		oclContextComposite.setLayout(new GridLayout(2, false));

		oclContext = new Text(oclContextComposite, SWT.BORDER);
		oclContext.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		oclContext.setEnabled(false);
		oclContext.setFocus();

		Button btnSelectContext = new Button(oclContextComposite, SWT.PUSH);
		btnSelectContext.setText(Messages.PapyrusSearchPage_18);
		btnSelectContext.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent e) {

				LabelProviderService labelProviderService = new LabelProviderServiceImpl();
				ILabelProvider labelProvider = labelProviderService.getLabelProvider();

				IScopeEntry currentScope = getCurrentScopeEntry();

				if (currentScope != null) {

					try {
						((UmlModel) currentScope.getModelSet().getModel(UmlModel.MODEL_ID)).lookupRoot();

						ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getCurrent().getActiveShell(), labelProvider, new OCLContextContentProvider());
						dialog.setTitle(Messages.PapyrusSearchPage_19);
						dialog.setMessage(Messages.PapyrusSearchPage_20);
						dialog.setInput(currentScope.getModelSet());
						dialog.setAllowMultiple(false);
						dialog.open();
						Object selection = dialog.getFirstResult();

						if (selection instanceof EObject) {

							refreshSelection(selection);

							if (contextObject instanceof NamedElement) {
								oclContext.setText(((NamedElement) contextObject).getQualifiedName());
							} else {
								oclContext.setText(labelProvider.getText(contextObject));
							}
						}

					} catch (NotFoundException notFoundException) {
						Activator.log.error(Messages.PapyrusQuery_0 + currentScope.getModelSet(), notFoundException);
					}
				}

			}
		});
		btnSelectContext.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		Injector injector = EssentialOCLActivator.getInstance().getInjector(EssentialOCLPlugin.LANGUAGE_ID);
		Composite editorComposite = client;
		oclEditor = new EmbeddedXtextEditor(editorComposite, injector, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		oclEditor.getViewer().getTextWidget().addVerifyKeyListener(new VerifyKeyListener() {

			@Override
			public void verifyKey(VerifyEvent e) {
				// System.out.println("verifyKey: " + e.keyCode);
				if (e.keyCode == SWT.KEYPAD_CR || e.keyCode == SWT.CR) {
					if ((e.stateMask & (SWT.CTRL | SWT.SHIFT)) == 0) {
						e.doit = false;
					}
				}
			}
		});

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		data.heightHint = convertHeightInCharsToPixels(3);
		editorComposite.setLayoutData(data);

		refreshSelection(root);

		currentQueryKind = OCL_QUERY_KIND;
	}

	protected IScopeEntry getCurrentScopeEntry() {
		if (container.getSelectedScope() == ISearchPageContainer.SELECTION_SCOPE) {
			Collection<URI> scope = ScopeCollector.getInstance().computeSearchScope(container);

			// this is only used for OCL queries, which currently assume workspace-like availability of the model content
			Collection<IScopeEntry> scopeEntries = WorkspaceQueryProvider.createScopeEntries(scope);

			if (scopeEntries.size() == 1) {
				Object[] entries = scopeEntries.toArray();
				IScopeEntry selectedResource = (IScopeEntry) entries[0];

				return selectedResource;
			}
		}
		return null;
	}

	@Override
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);
		Composite searchComposite = new Composite(parent, SWT.NONE);
		searchComposite.setFont(parent.getFont());
		searchComposite.setLayout(new GridLayout(2, false));
		searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		Label lblSearchString = new Label(searchComposite, SWT.NONE);
		lblSearchString.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		lblSearchString.setText(Messages.PapyrusSearchPage_4);

		queryKind = new Combo(searchComposite, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		queryKind.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		queryKind.add(Messages.PapyrusSearchPage_21);
		queryKind.add(Messages.PapyrusSearchPage_22);

		queryKind.select(TEXT_QUERY_KIND);

		queryKind.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (queryKind.getSelectionIndex() != currentQueryKind) {
					for (Control childControl : queryComposite.getChildren()) {
						childControl.dispose();
					}

					if (queryKind.getSelectionIndex() == TEXT_QUERY_KIND) {
						createTextSearch();
					} else {
						if (container.getSelectedScope() == ISearchPageContainer.SELECTION_SCOPE) {

							IScopeEntry currentScope = getCurrentScopeEntry();
							if (currentScope != null) {
								if (currentScope.getModelSet() != null) {

									try {
										EObject root = ((UmlModel) currentScope.getModelSet().getModel(UmlModel.MODEL_ID)).lookupRoot();
										createOCLSearch(root);

										if (contextObject instanceof NamedElement) {
											oclContext.setText(((NamedElement) contextObject).getQualifiedName());
										} else {
											LabelProviderService labelProviderService = new LabelProviderServiceImpl();
											ILabelProvider labelProvider = labelProviderService.getLabelProvider();
											oclContext.setText(labelProvider.getText(contextObject));
										}
									} catch (NotFoundException notFoundException) {

										Activator.log.error(Messages.PapyrusQuery_0 + currentScope.getModelSet(), notFoundException);
									}
								} else {
									MessageDialog.openWarning(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_23, Messages.PapyrusSearchPage_24);
									createTextSearch();
									queryKind.select(TEXT_QUERY_KIND);
								}
							} else {
								MessageDialog.openWarning(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_25, Messages.PapyrusSearchPage_26);
								createTextSearch();
								queryKind.select(TEXT_QUERY_KIND);
							}
						} else {
							MessageDialog.openWarning(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_27, Messages.PapyrusSearchPage_28);
							createTextSearch();
							queryKind.select(TEXT_QUERY_KIND);
						}
					}
					queryComposite.layout();
				}
			}
		});


		queryComposite = new Composite(searchComposite, SWT.NONE);
		queryComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		queryComposite.setLayout(new GridLayout(1, false));

		createTextSearch();

		setControl(parent);
	}

	public boolean getSearchAllStringAttributes() {
		if (btnSearchAllStringAttributes != null) {
			return btnSearchAllStringAttributes.getSelection();
		} else {
			return false;
		}
	}

	/**
	 * Validate syntax of the regular expression of the search query text.
	 *
	 * @return true, if successful
	 */
	private boolean validateRegex() {

		try {
			PatternHelper.getInstance().createPattern(searchQueryText.getText(), btnCaseSensitive.getSelection(), btnRegularExpression.getSelection());
			searchQueryExplanatoryLabel.setForeground(getControl().getForeground());
			searchQueryExplanatoryLabel.setText(""); //$NON-NLS-1$
			return true;

		} catch (PatternSyntaxException e) {
			searchQueryExplanatoryLabel.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
			searchQueryExplanatoryLabel.setText(e.getDescription());

			return false;
		}
	}

	protected void refreshSelection(final Object selected) {
		final BaseDocument editorDocument = (BaseDocument) oclEditor.getDocument();
		editorDocument.modify(new IUnitOfWork<Object, XtextResource>() {

			@Override
			public Value exec(XtextResource resource) throws Exception {
				Object selectedObject = selected;
				if (selectedObject instanceof IOutlineNode) {
					if (selectedObject instanceof EObjectNode) {
						EObjectNode selectedObjectNode = (EObjectNode) selectedObject;
						selectedObjectNode.getEObjectURI();
						contextObject = null; // FIXME
												// metamodelManager.loadResource(eObjectURI,
												// null, null);
					} else if (selectedObject instanceof EStructuralFeatureNode) {
						contextObject = null;
					} else {
						contextObject = null;
					}
				} else {
					if (selectedObject instanceof IAdaptable) {
						selectedObject = ((IAdaptable) selectedObject).getAdapter(EObject.class);
					}
					if (selectedObject instanceof EObject) {
						contextObject = (EObject) selectedObject;
					} else {
						contextObject = null;
					}
				}
				EnvironmentFactory environmentFactory = getEnvironmentFactory();
				parserContext = new EObjectContext(environmentFactory, null, contextObject);
				EssentialOCLCSResource csResource = (EssentialOCLCSResource) resource;
				if (csResource != null) {
					if (contextObject != null) {
						csResource.getCS2AS(); // FIXME redundant ??
					}
					ResourceSet resourceSet = oclEditor.getResourceSet();
					if (resourceSet != null) {
						environmentFactory.adapt(resourceSet); // FIXME redundant ??
					}
					csResource.setParserContext(parserContext);
				}

				return null;
			}
		});
	}


	@SuppressWarnings("unused")
	private Collection<IScopeEntry> createScopeEntries(Collection<URI> scope) {
		Collection<IScopeEntry> results = new HashSet<>();

		for (URI resource : scope) {

			IScopeEntry scopeEntry = new ScopeEntry(resource);

			results.add(scopeEntry);

		}

		return results;
	}

	@SuppressWarnings("unused")
	private List<ParticipantTypeElement> getParticipantsToEvaluate(HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> participantsList) {
		List<ParticipantTypeElement> participantsToEvaluate = new ArrayList<>();

		for (ParticipantTypeElement element : participantsList.keySet()) {
			if (element.isChecked()) {
				participantsToEvaluate.add(element);
				for (ParticipantTypeAttribute attributesToEvaluate : participantsList.get(element)) {
					if (attributesToEvaluate.isChecked()) {
						participantsToEvaluate.add(attributesToEvaluate);
					}
				}
			}
		}
		return participantsToEvaluate;
	}

	@Override
	public boolean performAction() {

		if (queryKind.getSelectionIndex() == TEXT_QUERY_KIND) {
			if (validateRegex()) {
				Collection<URI> scope = ScopeCollector.getInstance().computeSearchScope(container);
				AbstractPapyrusQuery compositeQuery;
				if (searchKind.getSelectionIndex() == SIMPLE_SEARCH) {
					if (searchQueryText.getText().length() == 0) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_29, Messages.PapyrusSearchPage_30);
						return false;
					} else {
						// One query per di file to avoid one single slow query on many files
						ArrayList<AbstractPapyrusQuery> queries = new ArrayList<>(scope.size());

						for (URI uri : scope) {
							Collection<URI> singleScope = new HashSet<>();
							singleScope.add(uri);

							QueryInfo info = new QueryInfo(searchQueryText.getText(), btnCaseSensitive.getSelection(), btnRegularExpression.getSelection(), btnSearchAllStringAttributes.getSelection(), singleScope);
							ISearchQuery query = CompositePapyrusQueryProvider.getInstance().createSimpleSearchQuery(info);

							queries.add((AbstractPapyrusQuery) query);
						}

						compositeQuery = CompositePapyrusQuery.compose(queries);
					}
				} else {


					List<ParticipantTypeElement> participantsToEvaluate = new ArrayList<>();

					for (ParticipantTypeElement element : this.umlTypeParticipantsList.keySet()) {
						if (element.isChecked()) {
							participantsToEvaluate.add(element);

							if (searchQueryText.getText().length() > 0) {
								for (ParticipantTypeAttribute attributesToEvaluate : umlTypeParticipantsList.get(element)) {
									if (attributesToEvaluate.isChecked()) {
										participantsToEvaluate.add(attributesToEvaluate);
									}
								}
							}
						}
					}

					for (ParticipantTypeElement element : this.stereotypeParticipantsList.keySet()) {
						if (element.isChecked()) {
							participantsToEvaluate.add(element);

							if (searchQueryText.getText().length() > 0) {
								for (ParticipantTypeAttribute attributesToEvaluate : stereotypeParticipantsList.get(element)) {
									if (attributesToEvaluate.isChecked()) {
										participantsToEvaluate.add(attributesToEvaluate);
									}
								}
							}
						}
					}

					if (participantsToEvaluate.size() == 0) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_31, Messages.PapyrusSearchPage_32);
						return false;
					} else {
						// One query per di file to avoid one single slow query on many files
						ArrayList<AbstractPapyrusQuery> queries = new ArrayList<>(scope.size());

						for (URI uri : scope) {
							Collection<URI> singleScope = new HashSet<>();
							singleScope.add(uri);

							QueryInfo info = new QueryInfo(searchQueryText.getText(), btnCaseSensitive.getSelection(), btnRegularExpression.getSelection(), participantsToEvaluate, singleScope, fBtnSearchForAllSelected.getSelection(),
									fBtnSearchForAnySelected.getSelection());
							ISearchQuery query = CompositePapyrusQueryProvider.getInstance().createAdvancedSearchQuery(info);

							queries.add((AbstractPapyrusQuery) query);
						}

						compositeQuery = CompositePapyrusQuery.compose(queries);
					}

				}
				if (compositeQuery.canRunInBackground()) {
					NewSearchUI.runQueryInBackground(compositeQuery);
				}

				return true;
			} else {
				MessageDialog.openError(Display.getCurrent().getActiveShell(), SEARCH_ISSUE, REGULAR_EXPRESSION_ILLFORMED);
				return false;
			}
		} else {

			IScopeEntry scopeEntry = getCurrentScopeEntry();

			if (scopeEntry != null) {

				try {

					PivotUtil.checkResourceErrors("", oclEditor.getResource()); //$NON-NLS-1$
					@SuppressWarnings("unused")
					ExpressionInOCL expressionInOCL = parserContext.getExpression((CSResource) oclEditor.getResource());
					ISearchQuery query = new PapyrusOCLQuery((BaseDocument) oclEditor.getDocument(), parserContext, getEnvironmentFactory(), null, contextObject, scopeEntry);


					if (query.canRunInBackground()) {
						NewSearchUI.runQueryInBackground(query);
					}
				} catch (ParserException e) {
					@SuppressWarnings("unused")
					Object value = new InvalidValueException(e, Messages.PapyrusSearchPage_35);
					MessageDialog.openError(Display.getCurrent().getActiveShell(), SEARCH_ISSUE, OCL_QUERY_ILLFORMED);
					return false;
				}

				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void setContainer(ISearchPageContainer container) {
		this.container = container;
		this.container.setPerformActionEnabled(true);
	}

	@Override
	public boolean performReplace() {
		if (queryKind.getSelectionIndex() == TEXT_QUERY_KIND) {
			if (container.getSelectedScope() == ISearchPageContainer.SELECTION_SCOPE) {
				if (validateRegex()) {
					if (searchQueryText.getText().length() == 0) {
						MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_36, Messages.PapyrusSearchPage_37);
						return false;
					}

					Collection<URI> scope = ScopeCollector.getInstance().computeSearchScope(container);

					AbstractPapyrusQuery query;
					if (searchKind.getSelectionIndex() == SIMPLE_SEARCH) {
						QueryInfo info = new QueryInfo(searchQueryText.getText(), btnCaseSensitive.getSelection(), btnRegularExpression.getSelection(), btnSearchAllStringAttributes.getSelection(), scope);
						query = CompositePapyrusQueryProvider.getInstance().createSimpleSearchQuery(info);
					} else {
						List<ParticipantTypeElement> participantsToEvaluate = new ArrayList<>();
						for (ParticipantTypeElement element : this.umlTypeParticipantsList.keySet()) {
							if (element.isChecked()) {
								participantsToEvaluate.add(element);
								if (umlTypeParticipantsList.get(element).size() == 0) {
									MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_38, Messages.PapyrusSearchPage_39);
									return false;

								} else {
									for (ParticipantTypeAttribute attributesToEvaluate : umlTypeParticipantsList.get(element)) {
										if (attributesToEvaluate.isChecked()) {
											participantsToEvaluate.add(attributesToEvaluate);
											boolean canDoReplace = false;
											if (attributesToEvaluate.getElement() instanceof EAttribute) {
												Object value = element.getElement().eGet((EAttribute) attributesToEvaluate.getElement());
												if (value instanceof String) {
													canDoReplace = true;
												}
											} else if (attributesToEvaluate.getElement() instanceof Property) {
												Property property = (Property) attributesToEvaluate.getElement();
												if (UMLUtil.isString(property.getType())) {
													canDoReplace = true;
												}
											}
											if (!canDoReplace) {
												MessageDialog.openError(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_40, Messages.PapyrusSearchPage_41);
												return false;
											}
										}
									}
								}
							}
						}
						QueryInfo info = new QueryInfo(searchQueryText.getText(), btnCaseSensitive.getSelection(), btnRegularExpression.getSelection(), participantsToEvaluate, scope, fBtnSearchForAllSelected.getSelection(),
								fBtnSearchForAnySelected.getSelection());
						query = CompositePapyrusQueryProvider.getInstance().createAdvancedSearchQuery(info);

					}

					NewSearchUI.runQueryInForeground(container.getRunnableContext(), query);


					Display.getCurrent().syncExec(new Runnable() {

						@Override
						public void run() {
							ISearchResultViewPart view = NewSearchUI.activateSearchResultView();
							if (view != null) {
								ISearchResultPage page = view.getActivePage();

								if (page instanceof PapyrusSearchResultPage) {
									PapyrusSearchResultPage resultPage = (PapyrusSearchResultPage) page;
									ReplaceAction replaceAction = new ReplaceAction(resultPage.getSite().getShell(), resultPage, null);
									replaceAction.run();
								}
							}
						}
					});

					NewSearchUI.removeQuery(query);
					return true;
				} else {
					MessageDialog.openError(Display.getCurrent().getActiveShell(), SEARCH_ISSUE, REGULAR_EXPRESSION_ILLFORMED);
					return false;
				}
			} else {
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_27, Messages.PapyrusSearchPage_28);
				return false;
			}
		} else {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(), Messages.PapyrusSearchPage_42, Messages.PapyrusSearchPage_43);
			return false;
		}

	}

	protected EnvironmentFactory getEnvironmentFactory() {
		return oclEditor.getOCL().getEnvironmentFactory();
	}

	protected void flushEvents() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		while (workbench.getDisplay().readAndDispatch()) {
			;
		}
	}

	public void reset() {
		if (oclEditor != null) {
			flushEvents();
			// editor.close(false);
			flushEvents();
		}
		parserContext = null;
		contextObject = null;
	}
}

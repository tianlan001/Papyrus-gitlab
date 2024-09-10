/*****************************************************************************
 * Copyright (c) 2013, 2023 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Bug 581217
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.search.ui.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.uml.search.ui.Activator;
import org.eclipse.papyrus.uml.search.ui.Messages;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeAttribute;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeElement;
import org.eclipse.papyrus.uml.search.ui.results.PapyrusSearchResult;
import org.eclipse.papyrus.uml.search.ui.validator.ParticipantValidator;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.utils.StereotypeUtil;
import org.eclipse.papyrus.views.search.regex.PatternHelper;
import org.eclipse.papyrus.views.search.results.AbstractResultEntry;
import org.eclipse.papyrus.views.search.results.AttributeMatch;
import org.eclipse.papyrus.views.search.results.ModelElementMatch;
import org.eclipse.papyrus.views.search.results.ModelMatch;
import org.eclipse.papyrus.views.search.results.ViewerMatch;
import org.eclipse.papyrus.views.search.scope.IScopeEntry;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.util.UMLUtil;

/**
 *
 * Papyrus specific search query
 *
 */
public class PapyrusAdvancedQuery extends AbstractPapyrusQuery {

	@Override
	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	@Override
	public boolean isRegularExpression() {
		return isRegularExpression;
	}

	private String searchQueryText;

	private boolean isCaseSensitive;

	private boolean isRegularExpression;

	private Collection<IScopeEntry> scopeEntries;

	private Object[] participantsChecked;

	private PapyrusSearchResult results;

	private HashMap<EObject, List<EAttribute>> participantsList;

	protected Set<AbstractResultEntry> fResults = null;

	private HashMap<Stereotype, ArrayList<Property>> stereotypeList;

	private List<EAttribute> attributesList;

	private List<Property> propertyList;

	private boolean searchForAllSter;

	private boolean searchForAnySter;

	public PapyrusAdvancedQuery(String searchQueryText, boolean isCaseSensitive, boolean isRegularExpression, Collection<IScopeEntry> scopeEntries, Object[] participantsChecked, boolean searchForAllSter, boolean searchForAnySter) {
		this.propertyList = new ArrayList<>();
		this.searchQueryText = searchQueryText;
		this.isCaseSensitive = isCaseSensitive;
		this.isRegularExpression = isRegularExpression;
		this.scopeEntries = scopeEntries;
		this.participantsChecked = participantsChecked;
		this.searchForAllSter = searchForAllSter;
		this.searchForAnySter = searchForAnySter;
		results = new PapyrusSearchResult(this);

		participantsList = new HashMap<>();
		stereotypeList = new HashMap<>();
		for (Object participant : this.participantsChecked) {
			if (participant instanceof ParticipantTypeElement) {
				if (((ParticipantTypeElement) participant).getElement() instanceof ENamedElement) {
					List<EAttribute> attributesChecked = new ArrayList<>();
					for (Object attributesFound : this.participantsChecked) {
						if (attributesFound instanceof ParticipantTypeAttribute) {
							if (((ParticipantTypeAttribute) attributesFound).getParent() == participant) {
								attributesChecked.add((EAttribute) ((ParticipantTypeAttribute) attributesFound).getElement());
							}
						}
					}
					participantsList.put(((ParticipantTypeElement) participant).getElement(), attributesChecked);

				} else if (((ParticipantTypeElement) participant).getElement() instanceof Stereotype) {

					ArrayList<Property> attributesChecked = new ArrayList<>();
					for (Object attributesFound : this.participantsChecked) {
						if (attributesFound instanceof ParticipantTypeAttribute) {
							if (((ParticipantTypeAttribute) attributesFound).getParent() == participant) {
								attributesChecked.add((Property) ((ParticipantTypeAttribute) attributesFound).getElement());
							}
						}
					}
					stereotypeList.put((Stereotype) ((ParticipantTypeElement) participant).getElement(), attributesChecked);
				}
			}
		}

		fResults = new HashSet<>();
	}

	@Override
	public IStatus run(IProgressMonitor monitor) throws OperationCanceledException {
		progressMonitor = SubMonitor.convert(monitor, scopeEntries.size() * 4);
		progressMonitor.subTask("Searching"); //$NON-NLS-1$

		results.removeAll();
		fResults.clear();

		for (IScopeEntry scopeEntry : scopeEntries) {
			try {
				if (scopeEntry.getModelSet() != null) {
					UmlModel umlModel = (UmlModel) scopeEntry.getModelSet().getModelChecked(UmlModel.MODEL_ID);

					EObject root = umlModel.lookupRoot();
					EList<EObject> contents = umlModel.getResource().getContents();

					Collection<EObject> participants = ParticipantValidator.getInstance().getParticipants(root, participantsList.keySet().toArray());

					progressMonitor.worked(1);

					Collection<EObject> stereotypedParticipants = ParticipantValidator.getInstance().getParticipantsStereotype(contents, stereotypeList.keySet().toArray());

					progressMonitor.worked(1);

					if (searchForAllSter) {
						if (participantsList.keySet().size() == 0) {
							stereotypedParticipants = getElementsWithAllSter(stereotypedParticipants);
							evaluate(stereotypedParticipants, scopeEntry);
							if (!searchQueryText.equals("")) { //$NON-NLS-1$
								evaluateStereotypes(stereotypedParticipants, scopeEntry);
							}
						} else {
							participants = getElementsWithAllSter(participants);
							evaluate(participants, scopeEntry);
							if (!searchQueryText.equals("")) { //$NON-NLS-1$
								evaluateStereotypes(participants, scopeEntry);
							}
						}
					} else if (searchForAnySter) {
						if (participantsList.keySet().size() == 0) {
							stereotypedParticipants = getElementsWithAnySter(stereotypedParticipants);
							evaluate(stereotypedParticipants, scopeEntry);
							if (!searchQueryText.equals("")) { //$NON-NLS-1$
								evaluateStereotypes(stereotypedParticipants, scopeEntry);
							}
						} else {
							participants = getElementsWithAnySter(participants);
							evaluate(participants, scopeEntry);
							if (!searchQueryText.equals("")) { //$NON-NLS-1$
								evaluateStereotypes(participants, scopeEntry);
							}
						}
					} else {
						evaluate(participants, scopeEntry);
						evaluateStereotypes(stereotypedParticipants, scopeEntry);
					}

					progressMonitor.worked(1);
				}
			} catch (NotFoundException e) {
				Activator.log.error(Messages.PapyrusQuery_0 + scopeEntry.getModelSet(), e);
			}
		}

		// progressMonitor.done();

		return Status.OK_STATUS;
	}

	private Collection<EObject> getElementsWithAllSter(Collection<EObject> initialParticipants) {
		Collection<EObject> participantsToKeep = new ArrayList<>();
		for (EObject participants : initialParticipants) {
			if (participants instanceof Element) {
				int numberOfStereotypeToBeApplied = stereotypeList.size();
				int numberOfStereotypeMatching = 0;
				if (numberOfStereotypeToBeApplied == ((Element) participants).getAppliedStereotypes().size()) {
					for (Stereotype stereotypeToBeApplied : stereotypeList.keySet()) {
						for (Stereotype stereotypeApplied : ((Element) participants).getAppliedStereotypes()) {
							if (EcoreUtil.getURI(stereotypeToBeApplied).equals(EcoreUtil.getURI(stereotypeApplied))) {
								numberOfStereotypeMatching++;
							}
						}
					}

					if (numberOfStereotypeMatching == numberOfStereotypeToBeApplied) {
						participantsToKeep.add(participants);
					}
				}
			}
		}
		return participantsToKeep;
	}

	private Collection<EObject> getElementsWithAnySter(Collection<EObject> initialParticipants) {
		Collection<EObject> participantsToKeep = new ArrayList<>();
		for (EObject participants : initialParticipants) {
			if (participants instanceof Element) {
				boolean added = false;

				for (Stereotype stereotypeToBeApplied : stereotypeList.keySet()) {
					for (Stereotype stereotypeApplied : ((Element) participants).getAppliedStereotypes()) {
						if (EcoreUtil.getURI(stereotypeToBeApplied).equals(EcoreUtil.getURI(stereotypeApplied))) {
							participantsToKeep.add(participants);
							added = true;
							break;
						}
					}

					if (added) {
						break;
					}
				}
			}
		}

		return participantsToKeep;
	}

	/**
	 * Evaluate if the value matches the pattern
	 *
	 * @param value
	 *            the value to evaluate
	 * @param attribute
	 *            the attribute has the value
	 * @param pattern
	 *            the pattern that is searched
	 * @param participant
	 *            the element that contains the value
	 * @param scopeEntry
	 *            the scopeEntry that contains the participant
	 */
	protected void evaluateAndAddToResult(String value, Object attribute, Pattern pattern, Object participant, IScopeEntry scopeEntry, Stereotype stereotype) {

		value = value != null ? value : ""; //$NON-NLS-1$

		Matcher m = pattern.matcher(value);

		if (isRegularExpression) {
			if (m.matches()) {
				int offset = m.start();
				int length = m.end() - m.start();

				ModelMatch match = new AttributeMatch(offset, length, participant, scopeEntry, attribute, stereotype);

				fResults.add(match);
			}
		} else {
			while (m.find()) {
				int offset = m.start();
				int length = m.end() - m.start();

				AttributeMatch match = new AttributeMatch(offset, length, participant, scopeEntry, attribute, stereotype);

				fResults.add(match);
			}
		}

		// if(PatternHelper.getInstance().evaluate(m, isRegularExpression)) {
		// int start = m.start();
		// int end = m.end();
		// ModelMatch match = new AttributeMatch(start, end, participant, scopeEntry, attribute);
		//
		// fResults.add(match);
		// }
	}

	/**
	 * Try to find elements that match in the participants
	 *
	 * @param participants
	 * @param scopeEntry
	 */


	protected void evaluate(Collection<EObject> participants, IScopeEntry scopeEntry) {
		for (EObject participant : participants) {

			if (searchQueryText.equals("")) { //$NON-NLS-1$
				fResults.add(new ModelElementMatch(participant, scopeEntry));
			} else {
				String query = searchQueryText;
				if (searchQueryText.equals("")) { //$NON-NLS-1$
					query = ".*"; //$NON-NLS-1$
				}

				Pattern pattern = PatternHelper.getInstance().createPattern(query, isCaseSensitive, isRegularExpression);

				if (pattern != null) {
					if (participantsList.get(participant.eClass()).size() == 0) {
						attributesList = participant.eClass().getEAllAttributes();
					} else {
						attributesList = participantsList.get(participant.eClass());
					}

					for (EAttribute attribute : attributesList) {

						Object value = participant.eGet(attribute);

						if (value instanceof String) {
							String stringValue = (String) value;
							evaluateAndAddToResult(stringValue, attribute, pattern, participant, scopeEntry, null);
						} else {
							String stringValue = String.valueOf(value);
							evaluateAndAddToResult(stringValue, attribute, pattern, participant, scopeEntry, null);
						}
					}
				}


			}
		}

		findInDiagram(scopeEntry);
	}


	protected void evaluateStereotypes(Collection<EObject> participants, IScopeEntry scopeEntry) {

		for (EObject participant : participants) {

			if (searchQueryText.equals("")) { //$NON-NLS-1$
				fResults.add(new ModelElementMatch(participant, scopeEntry));
			} else {
				String query = searchQueryText;
				if (searchQueryText.equals("")) { //$NON-NLS-1$
					query = ".*"; //$NON-NLS-1$
				}

				Pattern pattern = PatternHelper.getInstance().createPattern(query, isCaseSensitive, isRegularExpression);

				if (pattern != null) {
					EList<Stereotype> stereotypesApplied = ((Element) participant).getAppliedStereotypes();
					for (Stereotype stereotype : stereotypesApplied) {
						for (Stereotype stereotypeSelected : stereotypeList.keySet()) {
							if (EcoreUtil.getURI(stereotype).equals(EcoreUtil.getURI(stereotypeSelected))) {
								propertyList = this.getStereotypesAttributes(stereotype);

								for (Property property : propertyList) {
									if (stereotypeList.get(stereotypeSelected).size() == 0) {
										String value = "[" + StereotypeUtil.displayPropertyValue(stereotype, property, (Element) participant, ";") + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
										evaluateAndAddToResult(value, property, pattern, participant, scopeEntry, stereotype);
									} else {
										for (Property property2 : (stereotypeList.get(stereotypeSelected))) {
											if (EcoreUtil.getURI(property).equals(EcoreUtil.getURI(property2))) { // We loop through all selected attributes of all stereotypes, therefore this test is necessary to compare to currently searched stereotype's
																													// attributes
												String value = StereotypeUtil.displayPropertyValueOnly(stereotype, property, (Element) participant, ""); //$NON-NLS-1$
												evaluateAndAddToResult(value, property, pattern, participant, scopeEntry, stereotype);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		findInDiagram(scopeEntry);

	}

	private String getStringValue(Object value) {
		if (value == null) {
			return ""; //$NON-NLS-1$
		}

		if (value instanceof String) { // Primitive types will hit this case
			return (String) value;
		} else if (value instanceof EnumerationLiteral) {
			return ((EnumerationLiteral) value).getName();
		} else if (value instanceof NamedElement) {
			return ((NamedElement) value).getName();
		} else if (value instanceof EObject) { // Ref to an element in a model
			Element baseElement = UMLUtil.getBaseElement((EObject) value);
			return getStringValue(baseElement);
		} else {
			return String.valueOf(value);
		}
	}

	private EList<String> getStringValuesOfProperty(Element element, Stereotype stereotype, Property property) {
		BasicEList<String> results = new BasicEList<>();

		Object values = element.getValue(stereotype, property.getName());
		if (values instanceof EList) {
			for (Object val : (EList) values) {
				results.add(getStringValue(val));
			}
		} else {
			results.add(getStringValue(values));
		}

		return results;
	}

	public List<Property> getStereotypesAttributes(Object parentElement) {
		List<Property> result = new ArrayList<>();
		if (parentElement instanceof Stereotype) {

			for (Property property : ((Stereotype) parentElement).getAllAttributes()) {

				if (!property.getName().startsWith("base_")) { //$NON-NLS-1$
					if (property.getType() instanceof Element) {
						result.add(property);
					}
				}
			}

		}

		return result;
	}

	protected void findInDiagram(IScopeEntry scopeEntry) {
		// Find diagrams that contain the elements that were found
		Set<AbstractResultEntry> viewResults = new HashSet<>();
		for (AbstractResultEntry match : fResults) {
			Object source = match.getSource();

			if (source instanceof Element) {
				List<View> views = getViews((Element) source);

				if (views != null && !views.isEmpty()) {
					for (View view : views) {
						ViewerMatch viewMatch = new ViewerMatch(view, scopeEntry, source);
						viewResults.add(viewMatch);
					}
				}
			}
		}

		fResults.addAll(viewResults);
	}

	@Override
	public String getLabel() {
		return Messages.PapyrusQuery_6;
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public ISearchResult getSearchResult() {
		if (progressMonitor != null) {
			progressMonitor.setWorkRemaining(fResults.size());
			progressMonitor.subTask("Displaying Results"); //$NON-NLS-1$
		}


		for (AbstractResultEntry match : fResults) {
			results.addMatch(match);

			if (progressMonitor != null) {
				progressMonitor.worked(1);
			}
		}

		if (progressMonitor != null) {
			progressMonitor.done();
		}

		return results;
	}

	/**
	 * Getter for the text query
	 *
	 * @return the the query text
	 */
	@Override
	public String getSearchQueryText() {
		return searchQueryText;
	}

}

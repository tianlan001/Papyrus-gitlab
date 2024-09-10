/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Shuai Li (CEA LIST)
 *****************************************************************************/

package org.eclipse.papyrus.uml.search.ui.listeners;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeAttribute;
import org.eclipse.papyrus.uml.search.ui.providers.ParticipantTypeElement;
import org.eclipse.uml2.uml.Stereotype;

/**
 * CheckStateListener for checkbox selections in types and stereotypes treeviewers
 *
 */
public class ParticipantTypesTreeViewerCheckStateListener implements ICheckStateListener {

	private CheckboxTreeViewer participantTypesTreeViewer;
	private HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> participantsList;
	
	public ParticipantTypesTreeViewerCheckStateListener(CheckboxTreeViewer participantTypesTreeViewer, HashMap<ParticipantTypeElement, List<ParticipantTypeAttribute>> participantsList) {
		this.participantTypesTreeViewer = participantTypesTreeViewer;
		this.participantsList = participantsList;
	}
	
	public void checkStateChanged(CheckStateChangedEvent event) {
		if (participantTypesTreeViewer != null && participantsList != null) {
			if (event.getElement() instanceof ParticipantTypeElement) {
				// If the item is checked . . .
				if (event.getChecked()) {
					Object selectedElement = event.getElement();

					((ParticipantTypeElement) selectedElement).setChecked(true);
					participantTypesTreeViewer.refresh(selectedElement);

					if (selectedElement instanceof ParticipantTypeAttribute) {
						ParticipantTypeElement parent = ((ParticipantTypeAttribute) selectedElement).getParent();
						if (parent != null && !parent.isChecked()) {
							parent.setChecked(true);
							participantTypesTreeViewer.refresh(parent);
						}
					} else if (((ParticipantTypeElement) selectedElement).getElement() instanceof Stereotype || ((ParticipantTypeElement) selectedElement).getElement() instanceof EClassImpl) {
						for (ParticipantTypeAttribute attribute : participantsList.get(selectedElement)) {
							attribute.setChecked(true);
							participantTypesTreeViewer.refresh(attribute);
						}
					}
				} else {
					Object selectedElement = event.getElement();
					((ParticipantTypeElement) selectedElement).setChecked(false);
					participantTypesTreeViewer.refresh(selectedElement);

					if (selectedElement instanceof ParticipantTypeAttribute) {
						ParticipantTypeElement parent = ((ParticipantTypeAttribute) selectedElement).getParent();
						
						if (parent != null && parent.isChecked()) {
							boolean hasCheckedChildren = false;
							
							for (ParticipantTypeAttribute attribute : participantsList.get(parent)) {
								if (attribute.isChecked()) {
									hasCheckedChildren = true;
									break;
								}
							}
							
							if (!hasCheckedChildren) {
								parent.setChecked(false);
								participantTypesTreeViewer.refresh(parent);
							}
						}
					} else if (((ParticipantTypeElement) selectedElement).getElement() instanceof Stereotype || ((ParticipantTypeElement) selectedElement).getElement() instanceof EClassImpl) {
						for (ParticipantTypeAttribute attribute : participantsList.get(selectedElement)) {
							attribute.setChecked(false);
							participantTypesTreeViewer.refresh(attribute);
						}
					}
				}
			}
		}
	}

}

/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.hyperlink.commands.CreateHyperLinkWebCommand;
import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkWeb;
import org.eclipse.papyrus.infra.hyperlink.ui.EditorHyperLinkWebShell;
import org.eclipse.papyrus.infra.hyperlink.util.HyperLinkConstants;
import org.eclipse.swt.widgets.Shell;

/**
 * this is an helper to manager hyperlink web
 *
 * @author Patrick Tessier
 *
 */
public class WebHyperLinkHelper extends AbstractHyperLinkHelper {


	@Override
	public HyperLinkObject getHyperLinkObject(EAnnotation eAnnotation) {
		if (eAnnotation.getSource().equals(HyperLinkConstants.HYPERLINK_WEB)) {
			HyperLinkWeb hyperLinkWeb = new HyperLinkWeb();
			hyperLinkWeb.setHyperLinkWeb(eAnnotation.getDetails().get(HyperLinkConstants.HYPERLINK_WEB_LINK));
			hyperLinkWeb.setTooltipText(eAnnotation.getDetails().get(HyperLinkConstants.HYPERLINK_TOOLTYPE_TEXT));
			if (eAnnotation.getDetails().get(HyperLinkConstants.HYPERLINK_IS_DEFAULT_NAVIGATION) != null) {
				boolean isDefaultNaviagation = Boolean.parseBoolean(eAnnotation.getDetails().get(HyperLinkConstants.HYPERLINK_IS_DEFAULT_NAVIGATION));
				hyperLinkWeb.setIsDefault(isDefaultNaviagation);
			} else {
				hyperLinkWeb.setIsDefault(false);
			}

			return hyperLinkWeb;
		}

		return null;
	}

	@Override
	public RecordingCommand getAddHyperLinkCommand(TransactionalEditingDomain domain, EModelElement object, HyperLinkObject HyperLinkObject) {
		if (HyperLinkObject instanceof HyperLinkWeb) {
			HyperLinkWeb hyperLinkWeb = (HyperLinkWeb) HyperLinkObject;
			return new CreateHyperLinkWebCommand(domain, object, hyperLinkWeb.getTooltipText(), hyperLinkWeb.getHyperLinkWeb(), HyperLinkObject.getIsDefault());
		} else {
			return null;
		}
	}

	@Override
	public String getNameofManagedHyperLink() {
		return Messages.WebHyperLinkHelper_Web;
	}

	@Override
	public void executeNewMousePressed(Shell parentShell, List<HyperLinkObject> list, EObject aModel) {
		EditorHyperLinkWebShell editor = new EditorHyperLinkWebShell(parentShell);
		editor.open();
		if (editor.getHyperLinkWeb() != null) {
			list.add(editor.getHyperLinkWeb());
		}

	}

	@Override
	public ArrayList<HyperLinkObject> getFilteredObject(List<HyperLinkObject> HyperLinkObjects) {
		ArrayList<HyperLinkObject> result = new ArrayList<HyperLinkObject>();
		Iterator<HyperLinkObject> iterator = HyperLinkObjects.iterator();
		while (iterator.hasNext()) {
			HyperLinkObject HyperLinkObject = iterator.next();
			if (HyperLinkObject instanceof HyperLinkWeb) {
				result.add(HyperLinkObject);
			}
		}
		return result;
	}

}

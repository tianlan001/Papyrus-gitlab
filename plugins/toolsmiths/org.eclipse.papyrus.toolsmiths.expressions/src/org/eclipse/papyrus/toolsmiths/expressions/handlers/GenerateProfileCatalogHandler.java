/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.expressions.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog;
import org.eclipse.papyrus.infra.emf.expressions.ExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.IExpression;
import org.eclipse.papyrus.infra.emf.expressions.util.custom.ExpressionsResource;
import org.eclipse.papyrus.infra.emf.expressions.util.custom.ExpressionsResourceFactory;
import org.eclipse.papyrus.infra.emf.expressions.utils.ExpressionNameComparator;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.util.SelectionHelper;
import org.eclipse.papyrus.toolsmiths.expressions.Activator;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.papyrus.uml.expressions.umlexpressions.utils.UMLExpressionsUtils;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;

/**
 * This handlers allow to create a new Catalog providing the IsStereotypedWith expressions required to manipulate a given Profile
 * 
 * The generation is always done from the top profile.
 * 
 * The field {@link IsStereotypedWithExpression#getProfileURI()} is always set with the URI of the top profile
 *
 */
public class GenerateProfileCatalogHandler extends AbstractHandler {

	/**
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final Profile profile = getSelectedProfile();
		if (null != profile) {
			ExpressionCatalog catalog = createExpressionCatalog(profile);
			URI profileURI = profile.eResource().getURI();
			profileURI = profileURI.trimFileExtension();
			profileURI = profileURI.trimFileExtension();

			profileURI = profileURI.appendFileExtension(ExpressionsResource.EXPRESSIONS_RESOURCE_FILE_EXTENSION);
			Resource res = new ExpressionsResourceFactory().createResource(profileURI);
			res.getContents().add(catalog);
			try {
				res.save(null);
			} catch (IOException e) {
				Activator.log.error(e);
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#setEnabled(java.lang.Object)
	 *
	 * @param evaluationContext
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		setBaseEnabled(null != getSelectedProfile());
	}

	/**
	 * 
	 * @return
	 * 		the selected profile or <code>null</code> if the current selection is not a profile
	 */
	private Profile getSelectedProfile() {
		final IStructuredSelection selection = SelectionHelper.getCurrentStructuredSelection();
		if (null != selection) {
			Object selectedObject = selection.getFirstElement();
			selectedObject = EMFHelper.getEObject(selectedObject);
			if (selectedObject instanceof Profile) {
				return (Profile) selectedObject;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param profile
	 *            the selected profile
	 * @return
	 * 		the expression catalog for the given profile. This catalog will contains one {@link IsStereotypedWithExpression} for each stereotype owned in this profile or one of its subprofile
	 */
	private ExpressionCatalog createExpressionCatalog(final Profile profile) {
		final ExpressionCatalog catalog = ExpressionsFactory.eINSTANCE.createExpressionCatalog();
		final String profileName = profile.getName();

		final StringBuilder catalogNameBuilder = new StringBuilder(profileName);
		catalogNameBuilder.append("ExpressionCatalog"); //$NON-NLS-1$
		catalog.setName(catalogNameBuilder.toString());

		final String description = NLS.bind("This catalog provides expressions for the profile {0}.", profileName);//$NON-NLS-1$
		catalog.setDescription(description);
		fillExpressionExpressionToCatalog(profile, catalog);
		return catalog;
	}

	/**
	 * 
	 * @param profile
	 *            a profile
	 * @param catalog
	 *            the catalog to fill with the expressions created for each stereotype of this profile
	 */
	private void fillExpressionExpressionToCatalog(final Profile profile, final ExpressionCatalog catalog) {
		final List<IExpression<?, ?>> list = new ArrayList<IExpression<?, ?>>();
		final Iterator<EObject> iter = profile.eAllContents();
		while (iter.hasNext()) {
			final EObject current = iter.next();
			if (current instanceof Stereotype) {
				final Stereotype stereotype = (Stereotype) current;
				final String stereotypeQN = stereotype.getQualifiedName();
				final IsStereotypedWithExpression exp = UMLExpressionsFactory.eINSTANCE.createIsStereotypedWithExpression();

				final String expressionDescription = NLS.bind("This expression returns TRUE if the Element is stereotyped with {0} and FALSE otherwise.", stereotypeQN); //$NON-NLS-1$
				exp.setName(new StringBuilder("IsStereotypedWith_").append(stereotypeQN).toString());
				exp.setDescription(expressionDescription);
				exp.setStereotypeQualifiedName(stereotypeQN);

				// it is not a mistake, we always take the uri of the top profile, because often only this one has a defined uri
				exp.setProfileURI(UMLExpressionsUtils.getTopProfileURI(stereotype));
				list.add(exp);
			}
		}
		list.sort(new ExpressionNameComparator());
		catalog.getExpressions().addAll(list);
	}
}

/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.textedit.ui.internal.viewpoint;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.textedit.representation.TextDocumentRepresentation;
import org.eclipse.papyrus.infra.textedit.representation.command.ICreateTextDocumentEditorCommand;
import org.eclipse.papyrus.infra.textedit.textdocument.TextDocument;
import org.eclipse.papyrus.infra.textedit.ui.Activator;
import org.eclipse.papyrus.infra.tools.util.ClassLoaderHelper;
import org.eclipse.papyrus.infra.viewpoints.policy.IViewTypeHelper;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * Represents a helper for the handling of Papyrus TextDocument View Type creation commands.
 *
 */
public class CreateTextDocumentViewTypeCommandHelper implements IViewTypeHelper {

	/**
	 * The cache of prototypes
	 */
	private Map<PapyrusRepresentationKind, TextDocumentViewPrototype> cache;

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.IViewTypeHelper#isSupported(org.eclipse.emf.ecore.EClass)
	 *
	 * @param type
	 * @return
	 */
	@Override
	public boolean isSupported(EClass type) {
		return EcoreUtil.equals(type, org.eclipse.papyrus.infra.textedit.representation.RepresentationPackage.eINSTANCE.getTextDocumentRepresentation());
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.IViewTypeHelper#isSupported(org.eclipse.emf.ecore.EObject)
	 *
	 * @param view
	 * @return
	 */
	@Override
	public boolean isSupported(EObject view) {
		return (view instanceof TextDocument);
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.IViewTypeHelper#getPrototypeFor(org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind)
	 *
	 * @param kind
	 * @return
	 */
	@Override
	public ViewPrototype getPrototypeFor(PapyrusRepresentationKind kind) {
		if (!(kind instanceof TextDocumentRepresentation)) {
			return null;
		}

		final TextDocumentRepresentation textDocumentRepresentation = (TextDocumentRepresentation) kind;
		if (cache == null) {
			cache = new HashMap<>();
		}
		if (cache.containsKey(textDocumentRepresentation)) {
			return cache.get(textDocumentRepresentation);
		}
		String creationCommandClassName = textDocumentRepresentation.getCreationCommandClass();
		if (creationCommandClassName == null || creationCommandClassName.isEmpty()) {
			return null;
		}

		Class<?> creationCommandClass = ClassLoaderHelper.loadClass(creationCommandClassName);
		if (creationCommandClass != null) {
			try {
				final Constructor<?> constructor = creationCommandClass.getDeclaredConstructor(new Class[0]);
				Object newInstance = constructor.newInstance();
				if (newInstance instanceof ICreateTextDocumentEditorCommand) {
					ICreateTextDocumentEditorCommand command = (ICreateTextDocumentEditorCommand) newInstance;
					TextDocumentViewPrototype proto = new TextDocumentViewPrototype(textDocumentRepresentation, command);
					cache.put(textDocumentRepresentation, proto);
					return proto;
				}
			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
				Activator.log.error(e);
			} catch (IllegalArgumentException e) {
				Activator.log.error(e);
			} catch (InvocationTargetException e) {
				Activator.log.error(e);
			}
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.viewpoints.policy.IViewTypeHelper#getPrototypeOf(org.eclipse.emf.ecore.EObject)
	 *
	 * @param view
	 * @return
	 */
	@Override
	public ViewPrototype getPrototypeOf(EObject view) {
		if (!isSupported(view)) {
			return null;
		}
		PolicyChecker checker = PolicyChecker.getFor(view);
		ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();
		TextDocumentRepresentation repKind = (TextDocumentRepresentation) manager.getRepresentationKindById(((TextDocument) view).getKindId());// getRepresentationKindById(((Document) view)
		if (null != repKind && checker.isInViewpoint(repKind)) {
			return getPrototypeFor(repKind);
		}
		return ViewPrototype.UNAVAILABLE_VIEW;
	}

}

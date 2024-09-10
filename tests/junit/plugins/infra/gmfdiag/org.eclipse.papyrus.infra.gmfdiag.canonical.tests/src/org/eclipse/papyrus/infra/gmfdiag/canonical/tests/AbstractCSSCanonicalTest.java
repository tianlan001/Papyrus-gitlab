/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.tests;

import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.databinding.custom.AddCustomStyleListValueCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSStyles;
import org.eclipse.papyrus.infra.gmfdiag.css.properties.databinding.AddCSSStyleSheetCommand;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.EmbeddedStyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StylesheetsFactory;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.osgi.framework.FrameworkUtil;

import com.google.common.collect.Iterables;

/**
 * Common implementation of canonical test cases for CSS.
 */
public class AbstractCSSCanonicalTest extends AbstractCanonicalTest {
	@Rule
	public final TestRule stylesheetRule = new StylesheetRule();

	protected IFile cssFile;

	public AbstractCSSCanonicalTest() {
		super();
	}

	protected EmbeddedStyleSheet getStylesheet(String name) {
		EmbeddedStyleSheet result = null;

		for (EmbeddedStyleSheet next : Iterables.filter(getDiagramEditPart().getNotationView().eResource().getContents(), EmbeddedStyleSheet.class)) {
			if (name.equals(next.getLabel())) {
				result = next;
				break;
			}
		}

		return result;
	}

	protected void referenceEmbeddedStylesheet(String name) {
		referenceEmbeddedStylesheet(name, false);
	}

	private void referenceEmbeddedStylesheet(String name, boolean unsafe) {
		TransactionalEditingDomain domain = editor.getEditingDomain();
		View targetView = getDiagramEditPart().getNotationView();
		EmbeddedStyleSheet css = getStylesheet(name);

		Command command = new AddCSSStyleSheetCommand(domain, targetView,
				CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY,
				NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE__EOBJECT_LIST_VALUE,
				css);

		if (unsafe) {
			command = GMFUnsafe.wrap(editor.getEditingDomain(), command);
			command.execute();
			domain.getCommandStack().flush(); // Just in case
		} else {
			domain.getCommandStack().execute(command);
		}

		waitForUIEvents();
	}

	protected void referenceExternalStylesheet(String path) {
		referenceExternalStylesheet(path, false);
	}

	private void referenceExternalStylesheet(String path, boolean unsafe) {
		TransactionalEditingDomain domain = editor.getEditingDomain();
		View targetView = getDiagramEditPart().getNotationView();
		StyleSheetReference css = StylesheetsFactory.eINSTANCE.createStyleSheetReference();
		css.setPath(path);

		Command command = new AddCSSStyleSheetCommand(domain, targetView,
				CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY,
				NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE__EOBJECT_LIST_VALUE,
				css);

		if (unsafe) {
			command = GMFUnsafe.wrap(editor.getEditingDomain(), command);
			command.execute();
			domain.getCommandStack().flush(); // Just in case
		} else {
			domain.getCommandStack().execute(command);
		}

		waitForUIEvents();
	}

	protected void addStyleClass(View view, String name) {
		TransactionalEditingDomain domain = editor.getEditingDomain();

		Command command = new AddCustomStyleListValueCommand(domain, view,
				CSSStyles.CSS_GMF_CLASS_KEY,
				NotationPackage.Literals.STRING_LIST_VALUE_STYLE, NotationPackage.Literals.STRING_LIST_VALUE_STYLE__STRING_LIST_VALUE,
				name);

		domain.getCommandStack().execute(command);

		waitForUIEvents();
	}

	protected void refreshDiagram() {
		Diagram diagram = editor.getActiveDiagramEditor().getDiagram();
		((CSSDiagram) diagram).getEngine().reset();
		DiagramHelper.forceRefresh(editor.getActiveDiagramEditor().getDiagramEditPart());
	}

	//
	// Nested types
	//

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	protected @interface StylesheetRef {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	protected @interface CSSResource {
		String value();
	}

	private class StylesheetRule extends TestWatcher {
		@Override
		protected void starting(Description description) {
			StylesheetRef ref = JUnitUtils.getAnnotation(description, StylesheetRef.class);
			if (ref != null) {
				referenceEmbeddedStylesheet(ref.value(), true);
			}

			CSSResource css = JUnitUtils.getAnnotation(description, CSSResource.class);
			if (css != null) {
				URL url = FrameworkUtil.getBundle(AbstractCSSCanonicalTest.this.getClass()).getEntry(css.value());
				try (InputStream contents = url.openStream()) {
					cssFile = editor.getProject().getProject().getFile(URI.createURI(url.toExternalForm()).lastSegment());
					cssFile.create(contents, false, null);
				} catch (Exception e) {
					throw new WrappedException(e);
				}
				referenceExternalStylesheet(cssFile.getFullPath().toString(), true);
			}
		}
	}

}

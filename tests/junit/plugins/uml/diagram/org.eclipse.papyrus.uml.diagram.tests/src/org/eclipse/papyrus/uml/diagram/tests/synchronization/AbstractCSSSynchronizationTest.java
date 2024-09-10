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

package org.eclipse.papyrus.uml.diagram.tests.synchronization;

import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URL;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.edit.command.AddCommand;
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
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.osgi.framework.FrameworkUtil;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Common implementation of synchronization test cases for CSS.
 */
@PluginResource("resources/synch-test-model.di")
public abstract class AbstractCSSSynchronizationTest extends AbstractSynchronizationTest {
	@Rule
	public final TestRule stylesheetRule = new StylesheetRule();

	protected IFile cssFile;

	public AbstractCSSSynchronizationTest() {
		super();
	}

	@Before
	@Override
	public void makeDiagramCanonical() {
		super.makeDiagramCanonical();

		((StylesheetRule) stylesheetRule).apply();
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
		TransactionalEditingDomain domain = getEditingDomain();
		View targetView = getDiagramEditPart().getNotationView();
		EmbeddedStyleSheet css = getStylesheet(name);

		Command command = new AddCSSStyleSheetCommand(domain, targetView,
				CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY,
				NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE__EOBJECT_LIST_VALUE,
				css);

		if (unsafe) {
			command = GMFUnsafe.wrap(getEditingDomain(), command);
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
		TransactionalEditingDomain domain = getEditingDomain();
		View targetView = getDiagramEditPart().getNotationView();
		StyleSheetReference css = StylesheetsFactory.eINSTANCE.createStyleSheetReference();
		css.setPath(path);

		Command command = new AddCSSStyleSheetCommand(domain, targetView,
				CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY,
				NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE__EOBJECT_LIST_VALUE,
				css);

		if (unsafe) {
			command = GMFUnsafe.wrap(getEditingDomain(), command);
			command.execute();
			domain.getCommandStack().flush(); // Just in case
		} else {
			domain.getCommandStack().execute(command);
		}

		waitForUIEvents();
	}

	protected EmbeddedStyleSheet createEmbeddedStylesheet(String css, boolean unsafe) {
		EmbeddedStyleSheet result = StylesheetsFactory.eINSTANCE.createEmbeddedStyleSheet();
		result.setContent(css);

		final TransactionalEditingDomain domain = getEditingDomain();
		final View diagram = getDiagramEditPart().getNotationView();
		Command command = new AddCommand(domain, diagram.eResource().getContents(), result);
		command = command.chain(new AddCSSStyleSheetCommand(domain, diagram,
				CSSStyles.CSS_DIAGRAM_STYLESHEETS_KEY,
				NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE, NotationPackage.Literals.EOBJECT_LIST_VALUE_STYLE__EOBJECT_LIST_VALUE,
				result));

		if (unsafe) {
			command = GMFUnsafe.wrap(domain, command);
			command.execute();
			domain.getCommandStack().flush(); // Just in case
		} else {
			domain.getCommandStack().execute(command);
		}

		waitForUIEvents();

		return result;
	}

	protected void addStyleClass(View view, String name) {
		TransactionalEditingDomain domain = getEditingDomain();

		Command command = new AddCustomStyleListValueCommand(domain, view,
				CSSStyles.CSS_GMF_CLASS_KEY,
				NotationPackage.Literals.STRING_LIST_VALUE_STYLE, NotationPackage.Literals.STRING_LIST_VALUE_STYLE__STRING_LIST_VALUE,
				name);

		domain.getCommandStack().execute(command);

		waitForUIEvents();
	}

	protected void refreshDiagram() {
		Diagram diagram = getDiagramEditPart().getDiagramView();
		((CSSDiagram) diagram).getEngine().reset();
		DiagramHelper.forceRefresh(getDiagramEditPart());
	}

	//
	// Nested types
	//

	/**
	 * An annotation naming a CSS stylesheet embedded in the model that should be referenced
	 * as the stylesheet of the diagram in which the test is performed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	public @interface StylesheetRef {
		String value();
	}

	/**
	 * An annotation naming an external CSS stylesheet resource in the model that should be referenced
	 * as the stylesheet of the diagram in which the test is performed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	public @interface CSSResource {
		String value();
	}

	/**
	 * An annotation defining a CSS stylesheet (literally) that should be attached
	 * as the stylesheet of the diagram in which the test is performed.
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE, ElementType.METHOD })
	public @interface CSS {
		String value();
	}

	private class StylesheetRule extends TestWatcher {
		private List<StylesheetRef> refs = Lists.newArrayListWithExpectedSize(1);
		private List<CSSResource> cssResources = Lists.newArrayListWithExpectedSize(1);
		private List<CSS> csses = Lists.newArrayListWithExpectedSize(1);

		@Override
		protected void starting(Description description) {
			StylesheetRef ref = JUnitUtils.getAnnotation(description, StylesheetRef.class);
			if (ref != null) {
				refs.add(ref);
			}

			CSSResource cssRes = JUnitUtils.getAnnotation(description, CSSResource.class);
			if (cssRes != null) {
				cssResources.add(cssRes);
			}

			CSS css = JUnitUtils.getAnnotation(description, CSS.class);
			if (css != null) {
				csses.add(css);
			}
		}

		void apply() {
			for (StylesheetRef ref : refs) {
				referenceEmbeddedStylesheet(ref.value(), true);
			}

			for (CSSResource cssRes : cssResources) {
				URL url = FrameworkUtil.getBundle(AbstractCSSSynchronizationTest.this.getClass()).getEntry(cssRes.value());
				try (InputStream contents = url.openStream()) {
					cssFile = suiteState.getProject().getFile(URI.createURI(url.toExternalForm()).lastSegment());
					cssFile.create(contents, false, null);
				} catch (Exception e) {
					throw new WrappedException(e);
				}
				referenceExternalStylesheet(cssFile.getFullPath().toString(), true);
			}

			for (CSS css : csses) {
				createEmbeddedStylesheet(css.value(), true);
			}
		}
	}

}

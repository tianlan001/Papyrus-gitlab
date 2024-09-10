/*
 * Copyright (c) 2014, 2018 CEA, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bugs 533673, 533676, 507479
 *
 */
package org.eclipse.papyrus.junit.matchers;

import static org.hamcrest.CoreMatchers.hasItem;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.ServiceUtilsForEditPart;
import org.eclipse.papyrus.infra.services.decoration.DecorationService;
import org.eclipse.papyrus.infra.services.decoration.IDecorationService;
import org.eclipse.papyrus.infra.services.decoration.util.IPapyrusDecoration;
import org.eclipse.papyrus.infra.services.markerlistener.IPapyrusMarker;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;


/**
 * Hamcrest matchers for assertions on GMF diagrams.
 */
public class DiagramMatchers {

	private DiagramMatchers() {
		super();
	}

	/**
	 * Match an edit part that is selected in its viewer.
	 */
	public static Matcher<EditPart> editPartSelected() {
		return EditPartSelected.INSTANCE;
	}

	/**
	 * Match a drawer that is expanded in the specified {@code viewer}.
	 */
	public static Matcher<PaletteDrawer> expandedIn(PaletteViewer viewer) {
		return new DrawerExpansion(viewer, true);
	}

	/**
	 * Match a drawer that is collapsed (closed) in the specified {@code viewer}.
	 */
	public static Matcher<PaletteDrawer> collapsedIn(PaletteViewer viewer) {
		return new DrawerExpansion(viewer, false);
	}

	/**
	 * Match an edit-part by some condition of its notation view.
	 * 
	 * @param viewMatcher
	 *            view matcher condition
	 * 
	 * @since 2.2
	 */
	public static Matcher<EditPart> viewThat(Matcher<? super View> viewMatcher) {
		return new TypeSafeDiagnosingMatcher<EditPart>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("view that ").appendDescriptionOf(viewMatcher);
			}

			@Override
			protected boolean matchesSafely(EditPart item, Description mismatchDescription) {
				boolean result = false;

				if (!(item.getModel() instanceof View)) {
					mismatchDescription.appendText("edit-part model is not a notation view");
				} else {
					View view = (View) item.getModel();
					result = viewMatcher.matches(view);
					if (!result) {
						viewMatcher.describeMismatch(view, mismatchDescription);
					}
				}

				return result;
			}
		};
	}

	/**
	 * Match a notation view by some condition of its semantic element.
	 * 
	 * @param elementMatcher
	 *            element matcher condition
	 * 
	 * @since 2.2
	 */
	public static Matcher<View> elementThat(Matcher<? super EObject> elementMatcher) {
		return new TypeSafeDiagnosingMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("element that ").appendDescriptionOf(elementMatcher);
			}

			@Override
			protected boolean matchesSafely(View item, Description mismatchDescription) {
				boolean result = false;

				EObject element = item.getElement();
				if (element == null) {
					mismatchDescription.appendText("view has no semantic element");
				} else {
					result = elementMatcher.matches(element);
					if (!result) {
						elementMatcher.describeMismatch(element, mismatchDescription);
					}
				}

				return result;
			}
		};
	}

	/**
	 * Match an edit-part by some condition of its semantic element.
	 * 
	 * @param elementMatcher
	 *            element matcher condition
	 * 
	 * @since 2.2
	 */
	public static Matcher<EditPart> semanticThat(Matcher<? super EObject> elementMatcher) {
		return viewThat(elementThat(elementMatcher));
	}

	/**
	 * Match an edit-part that has an {@linkplain IPapyrusMarker#SEVERITY_ERROR error}
	 * decoration having a message matching a given matcher.
	 * 
	 * @param messageMatcher
	 *            matcher for the decoration message
	 * 
	 * @return the decoration matcher
	 * 
	 * @since 2.2
	 * 
	 * @see #hasDecorationThat(int, Matcher)
	 * @see IPapyrusMarker#SEVERITY_ERROR
	 */
	public static Matcher<EditPart> hasErrorDecorationThat(Matcher<? super String> messageMatcher) {
		return hasDecorationThat(IPapyrusMarker.SEVERITY_ERROR, messageMatcher);
	}

	/**
	 * Match an edit-part that has a {@linkplain IPapyrusMarker#SEVERITY_WARNING warning}
	 * decoration having a message matching a given matcher.
	 * 
	 * @param messageMatcher
	 *            matcher for the decoration message
	 * 
	 * @return the decoration matcher
	 * 
	 * @since 2.2
	 * 
	 * @see #hasDecorationThat(int, Matcher)
	 * @see IPapyrusMarker#SEVERITY_WARNING
	 */
	public static Matcher<EditPart> hasWarningDecorationThat(Matcher<? super String> messageMatcher) {
		return hasDecorationThat(IPapyrusMarker.SEVERITY_WARNING, messageMatcher);
	}

	/**
	 * Match an edit-part that has a decoration of a given severity having a message matching
	 * a given matcher.
	 * 
	 * @param severity
	 *            severity, as per the {@link IPapyrusMarker#SEVERITY} marker attribute values
	 * @param messageMatcher
	 *            matcher for the decoration message
	 * 
	 * @return the decoration matcher
	 * 
	 * @since 2.2
	 */
	public static Matcher<EditPart> hasDecorationThat(int severity, Matcher<? super String> messageMatcher) {

		return new TypeSafeDiagnosingMatcher<EditPart>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("has error decoration that ").appendDescriptionOf(messageMatcher);
			}

			@Override
			protected boolean matchesSafely(EditPart item, Description mismatchDescription) {
				IDecorationService service = null;
				try {
					// For some reason, the service is registered under the implementation class instead
					// of the interface. For maximal compatibility, we try both
					service = ServiceUtilsForEditPart.getInstance().getService(IDecorationService.class, item);
				} catch (Exception e) {
					try {
						service = ServiceUtilsForEditPart.getInstance().getService(DecorationService.class, item);
					} catch (Exception e2) {
						// Will fail, below
					}
				}

				if (service == null) {
					mismatchDescription.appendText("no decoration service");
					return false;
				}

				List<IPapyrusDecoration> decorations = service.getDecorations(item, false);
				List<String> messages = decorations.stream()
						.filter(d -> d.getPriority() == severity)
						.map(IPapyrusDecoration::getMessage)
						.collect(Collectors.toList());

				Matcher<Iterable<? super String>> delegate = hasItem(messageMatcher);
				boolean result = delegate.matches(messages);
				if (!result) {
					delegate.describeMismatch(messages, mismatchDescription);
				}
				return result;
			}
		};
	}

	//
	// Nested types
	//

	static class EditPartSelected extends BaseMatcher<EditPart> {

		private static final EditPartSelected INSTANCE = new EditPartSelected();

		private EditPartSelected() {
			super();
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("edit-part is selected");
		}

		@Override
		public boolean matches(Object item) {
			return (item instanceof EditPart) && isSelected((EditPart) item);
		}

		boolean isSelected(EditPart editPart) {
			EditPartViewer viewer = editPart.getViewer();
			return (viewer != null) && viewer.getSelectedEditParts().contains(editPart);
		}
	}

	static class DrawerExpansion extends BaseMatcher<PaletteDrawer> {

		private final PaletteViewer viewer;

		private final boolean expanded;

		DrawerExpansion(PaletteViewer viewer, boolean expanded) {
			this.viewer = viewer;
			this.expanded = expanded;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("drawer is ");
			description.appendText(expanded ? "expanded" : "collapsed");
		}

		@Override
		public boolean matches(Object item) {
			return (item instanceof PaletteDrawer) && (isExpanded((PaletteDrawer) item) == expanded);
		}

		boolean isExpanded(PaletteDrawer drawer) {
			return viewer.isExpanded(drawer);
		}
	}
}

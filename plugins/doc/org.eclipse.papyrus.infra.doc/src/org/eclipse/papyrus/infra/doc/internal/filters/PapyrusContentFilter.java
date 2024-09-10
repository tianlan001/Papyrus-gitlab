package org.eclipse.papyrus.infra.doc.internal.filters;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.help.webapp.IFilter;

/**
 * A dynamic help content filter that injects into Papyrus help pages the following elements.
 * <ul>
 * <li>the stylesheet defined in this bundle, in the head section</li>
 * <li>the Papyrus Logo banner image in this bundle, at the top of the body section</li>
 * </ul>
 */
public class PapyrusContentFilter implements IFilter {

	@SuppressWarnings("nls")
	private static final String[] STYLESHEET_PATH = { "help", "topic", "org.eclipse.papyrus.infra.doc", "resource", "stylesheet.css" };

	@SuppressWarnings("nls")
	private static final String[] BANNER_PATH = { "help", "topic", "org.eclipse.papyrus.infra.doc", "resource", "PapyrusLeftBanner.gif" };

	@Override
	public OutputStream filter(HttpServletRequest req, OutputStream out) {
		if (req.getRequestURI().startsWith("/help/topic/org.eclipse.papyrus.")) { //$NON-NLS-1$
			String[] reqPath = req.getRequestURI().substring(1).split("/"); //$NON-NLS-1$

			String cssPath = String.join("/", relativize(STYLESHEET_PATH, reqPath)); //$NON-NLS-1$
			String headContent = String.format("<link href=\"%s\" rel=\"stylesheet\" type=\"text/css\"/>", cssPath); //$NON-NLS-1$

			String bannerPath = String.join("/", relativize(BANNER_PATH, reqPath)); //$NON-NLS-1$
			String bodyContent = String.format("<img src=\"%s\" alt=\"Papyrus Banner\" class=\"papyrus-banner\"/>", bannerPath); //$NON-NLS-1$

			return new HTMLHeadAndBodyInjectionStream(out, req.getCharacterEncoding(), headContent, bodyContent);
		}

		return out;
	}

	static String[] relativize(String[] path, String[] base) {
		int i, j;
		for (i = 0, j = 0; i < path.length && j < base.length; i++, j++) {
			if (!path[i].equals(base[j])) {
				break;
			}
		}

		List<String> result = new ArrayList<>(base.length);
		for (j++; j < base.length; j++) {
			result.add(".."); //$NON-NLS-1$
		}
		for (; i < path.length; i++) {
			result.add(path[i]);
		}

		return result.toArray(String[]::new);
	}

}

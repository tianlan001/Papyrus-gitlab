package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

/**
 * A command to be used with the Eclipse Commands Framework.
 * This command allows to close all diagrams in the folder, except the currently openened one.
 *
 * @author cedric dumoulin
 *
 */
public class CloseOtherDiagramsCommand extends AbstractHandler {

	/**
	 * Check if the Command is enabled.
	 */
	@Override
	public void setEnabled(Object evaluationContext) {
		setBaseEnabled(new PageContext(evaluationContext).getOpenPageCount() > 1);
	}

	/**
	 * Execute the command. This method is called when the action is triggered.
	 *
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		PageContext context = new PageContext(event);

		if (context.isValid() && (context.getOpenPageCount() > 1)) {
			context.pageManager.closeOtherPages(context.currentPageIdentifier);
		}

		return null;
	}

}

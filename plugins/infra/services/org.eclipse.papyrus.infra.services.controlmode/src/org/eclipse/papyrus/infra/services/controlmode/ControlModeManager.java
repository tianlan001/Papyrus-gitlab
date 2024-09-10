/*******************************************************************************
 * Copyright (c) 2013, 2016 Atos, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Arthur Daussy <a href="mailto:arthur.daussy@atos.net"> - initial API and implementation
 *     Christian W. Damus - bug 497865
 ******************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.papyrus.infra.services.controlmode.commands.BasicControlCommand;
import org.eclipse.papyrus.infra.services.controlmode.commands.BasicUncontrolCommand;
import org.eclipse.papyrus.infra.services.controlmode.commands.CreateControlResource;
import org.eclipse.papyrus.infra.services.controlmode.commands.RemoveControlResourceCommand;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlCommandApprover;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlCommandParticipant;
import org.eclipse.papyrus.infra.services.controlmode.participants.IControlModeParticipant;
import org.eclipse.papyrus.infra.services.controlmode.participants.IShardModeCommandParticipant;
import org.eclipse.papyrus.infra.services.controlmode.participants.IUncontrolCommandParticipant;

/**
 * Implementation of {@link IControlModeManager} This manager used register participants to compute a control command
 *
 * @author adaussy
 *
 */
public class ControlModeManager implements IControlModeManager {

	/** The Constant CONTROL_COMMAND_POST_COMMANDS. */
	private static final String CONTROL_COMMAND_POST_COMMANDS = Messages.getString("ControlModeManager.post.commands.label"); //$NON-NLS-1$

	/** The Constant CONTROL_COMMAND_PRE_COMMAND_TITLE. */
	private static final String CONTROL_COMMAND_PRE_COMMAND_TITLE = Messages.getString("ControlModeManager.pre.commands.label"); //$NON-NLS-1$

	/** The Constant CONTROL_COMMAND_TITLE. */
	private static final String CONTROL_COMMAND_TITLE = Messages.getString("ControlModeManager.control.command.parent.title"); //$NON-NLS-1$

	/** The Constant UNCONTROL_COMMAND_PRE_COMMAND_TITLE. */
	private static final String UNCONTROL_COMMAND_PRE_COMMAND_TITLE = Messages.getString("ControlModeManager.uncontrol.command.pre.title"); //$NON-NLS-1$

	/** The Constant UNCONTROL_COMMAND_POST_COMMANDS. */
	private static final String UNCONTROL_COMMAND_POST_COMMANDS = Messages.getString("ControlModeManager.uncontrol.command.post.title"); //$NON-NLS-1$

	/** The Constant UNCONTROL_COMMAND_PARENT_TITLE. */
	private static final String UNCONTROL_COMMAND_PARENT_TITLE = Messages.getString("ControlModeManager.uncontrol.command.parent.title"); //$NON-NLS-1$

	/**
	 * Comparator that will sort the participant by priority
	 *
	 */
	protected final class PartipantComparator implements Comparator<IControlModeParticipant> {

		@Override
		public int compare(IControlModeParticipant arg0, IControlModeParticipant arg1) {
			int i = arg1.getPriority();
			int j = arg0.getPriority();
			return i >= j ? ((int) (i != j ? 1 : 0)) : -1;
		}
	}

	/**
	 * Singleton holder
	 *
	 * @author adaussy
	 *
	 */
	private static class SingletonHolder {

		private static ControlModeManager INSTANCE = new ControlModeManager();
	}

	/**
	 * Extension if for registering participants
	 */
	protected static String EXTENSION_ID = "org.eclipse.papyrus.infra.services.controlmode.participant"; //$NON-NLS-1$

	/**
	 * Extension propertyy refering to the participant implementaion class
	 */
	protected static String PARTICPANT_ATTRIBUTE = "class"; //$NON-NLS-1$

	/**
	 * Hold all the {@link IControlCommandParticipant}
	 */
	protected ArrayList<IControlCommandParticipant> controlCommandParticipants = new ArrayList<>();

	/**
	 * Hold all the {@link IUncontrolCommandParticipant}
	 */
	protected ArrayList<IUncontrolCommandParticipant> uncontrolCommandParticipants = new ArrayList<>();

	/**
	 * Hold all the {@link IShardModeCommandParticipant}s.
	 * 
	 * @since 1.3
	 */
	protected List<IShardModeCommandParticipant> shardModeCommandParticipants = new ArrayList<>();

	/**
	 * Hold all the {@link IControlCommandApprover}s.
	 * 
	 * @since 1.3
	 */
	protected List<IControlCommandApprover> controlCommandApprovers = new ArrayList<>();

	/**
	 * @return the unique instance of the manager
	 */
	public static IControlModeManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 *
	 */
	public ControlModeManager() {
		initParticipants();
	}

	@Override
	public ICommand getControlCommand(ControlModeRequest request) {
		boolean isOK = verifCorrectCommand(request);
		if (!isOK) {
			return UnexecutableCommand.INSTANCE;
		}
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(request.getEditingDomain(), CONTROL_COMMAND_TITLE);
		ICompositeCommand preCommand = getPreCommand(request);
		if (preCommand != null && !preCommand.isEmpty()) {
			cc.compose(preCommand);
		}
		cc.compose(new CreateControlResource(request));
		cc.compose(new BasicControlCommand(request));
		ICompositeCommand postCommand = getPostCommand(request);
		if (postCommand != null && !postCommand.isEmpty()) {
			cc.compose(postCommand);
		}
		return cc;
	}

	/**
	 * Check that the request is well formed
	 *
	 * @param request
	 * @return
	 */
	protected boolean verifCorrectCommand(ControlModeRequest request) {
		EObject objectToControl = request.getTargetObject();
		if (objectToControl != null) {
			EObject container = objectToControl.eContainer();
			if (container != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return {@link ControlModeManager#controlCommandParticipants}
	 */
	protected List<IControlCommandParticipant> getControlCommandParticipants() {
		return controlCommandParticipants;
	}

	/**
	 * Compute the post command for the request
	 *
	 * @param request
	 * @return
	 */
	protected ICompositeCommand getPostCommand(ControlModeRequest request) {
		boolean isControlRequest = request.isControlRequest();
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(request.getEditingDomain(), isControlRequest ? CONTROL_COMMAND_POST_COMMANDS : UNCONTROL_COMMAND_POST_COMMANDS);////// $NON-NLS-1$ //$NON-NLS-2$
		if (isControlRequest) {
			getPostControlCommand(request, cc);
		} else {
			getPostUncontrolMethod(request, cc);
		}
		return cc;
	}

	/**
	 * Compute the post control command
	 *
	 * @param request
	 * @param cc
	 *            new command will be composoed in it
	 */
	protected void getPostControlCommand(ControlModeRequest request, CompositeTransactionalCommand cc) {
		ListIterator<IControlCommandParticipant> participantIterator = getControlCommandParticipants().listIterator(getControlCommandParticipants().size());
		while (participantIterator.hasPrevious()) {
			IControlCommandParticipant iControlCommandParticipant = participantIterator.previous();
			if (iControlCommandParticipant.provideControlCommand(request)) {
				ICommand cmd = iControlCommandParticipant.getPostControlCommand(request);
				if (cmd != null) {
					cc.compose(cmd);
				}
			}
		}
	}

	/**
	 * Compute the post Uncontrol command
	 *
	 * @param request
	 * @param cc
	 *            new command will be composoed in it
	 */
	protected void getPostUncontrolMethod(ControlModeRequest request, CompositeTransactionalCommand cc) {
		ListIterator<IUncontrolCommandParticipant> participantIterator = getUncontrolCommandParticipants().listIterator(getUncontrolCommandParticipants().size());
		while (participantIterator.hasPrevious()) {
			IUncontrolCommandParticipant iControlCommandParticipant = participantIterator.previous();
			if (iControlCommandParticipant.provideUnControlCommand(request)) {
				ICommand cmd = iControlCommandParticipant.getPostUncontrolCommand(request);
				if (cmd != null) {
					cc.compose(cmd);
				}
			}
		}
	}

	/**
	 * Compute the Pre command for this {@link ControlModeRequest}
	 *
	 * @param request
	 * @return
	 */
	protected ICompositeCommand getPreCommand(ControlModeRequest request) {
		boolean isControlRequest = request.isControlRequest();
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(request.getEditingDomain(), isControlRequest ? CONTROL_COMMAND_PRE_COMMAND_TITLE : UNCONTROL_COMMAND_PRE_COMMAND_TITLE);
		if (isControlRequest) {
			getPreControlCommand(request, cc);
		} else {
			getPreUncontrolCommand(request, cc);
		}
		return cc;
	}

	/**
	 * Comute the command for pre control command
	 *
	 * @param request
	 * @param cc
	 *            new command will be composoed in it
	 */
	protected void getPreControlCommand(ControlModeRequest request, CompositeTransactionalCommand cc) {
		Iterator<IControlCommandParticipant> participantIterator = getControlCommandParticipants().iterator();
		while (participantIterator.hasNext()) {
			IControlCommandParticipant iControlCommandParticipant = participantIterator.next();
			if (iControlCommandParticipant.provideControlCommand(request)) {
				ICommand cmd = iControlCommandParticipant.getPreControlCommand(request);
				if (cmd != null) {
					cc.compose(cmd);
				}
			}
		}
	}

	/**
	 * Compute the command to pre uncontrol command
	 *
	 * @param request
	 * @param cc
	 *            new command will be composoed in it
	 */
	protected void getPreUncontrolCommand(ControlModeRequest request, CompositeTransactionalCommand cc) {
		Iterator<IUncontrolCommandParticipant> participantIterator = getUncontrolCommandParticipants().iterator();
		while (participantIterator.hasNext()) {
			IUncontrolCommandParticipant iControlCommandParticipant = participantIterator.next();
			if (iControlCommandParticipant.provideUnControlCommand(request)) {
				ICommand cmd = iControlCommandParticipant.getPreUncontrolCommand(request);
				if (cmd != null) {
					cc.compose(cmd);
				}
			}
		}
	}

	@Override
	public ICommand getUncontrolCommand(ControlModeRequest request) {
		boolean isOK = verifCorrectCommand(request);
		if (!isOK) {
			return UnexecutableCommand.INSTANCE;
		}
		CompositeTransactionalCommand cc = new CompositeTransactionalCommand(request.getEditingDomain(), UNCONTROL_COMMAND_PARENT_TITLE);
		ICompositeCommand preCommand = getPreCommand(request);
		if (preCommand != null && !preCommand.isEmpty()) {
			cc.compose(preCommand);
		}
		cc.compose(new BasicUncontrolCommand(request));
		cc.compose(new RemoveControlResourceCommand(request));
		ICompositeCommand postCommand = getPostCommand(request);
		if (postCommand != null && !postCommand.isEmpty()) {
			cc.compose(postCommand);
		}
		return cc;
	}

	/**
	 * @return {@link ControlModeManager#uncontrolCommandParticipants}
	 */
	protected List<IUncontrolCommandParticipant> getUncontrolCommandParticipants() {
		return uncontrolCommandParticipants;
	}

	/**
	 * @since 1.3
	 */
	protected List<IShardModeCommandParticipant> getShardModeCommandParticipants() {
		return shardModeCommandParticipants;
	}

	/**
	 * @since 1.3
	 */
	@Override
	public ICommand getShardModeCommand(ControlModeRequest request) {
		// This does validation of the request, too
		ICommand baseCommand = IControlModeManager.super.getShardModeCommand(request);

		// Include participants

		ICommand result = new CompositeTransactionalCommand(request.getEditingDomain(), Messages.getString("ControlModeManager.changeControlModeCommand.label")); //$NON-NLS-1$
		ICommand preCommand = getPreShardModeCommand(request);
		if (preCommand != null) {
			result = result.compose(preCommand);
		}

		result = result.compose(baseCommand);

		ICommand postCommand = getPostShardModeCommand(request);
		if (postCommand != null) {
			result = result.compose(postCommand);
		}

		return result.reduce();
	}

	/**
	 * @since 1.3
	 */
	protected ICommand getPreShardModeCommand(ControlModeRequest request) {
		return getParticipantCommand(request,
				getShardModeCommandParticipants(),
				IShardModeCommandParticipant::providesShardModeCommand,
				IShardModeCommandParticipant::getPreShardModeCommand);
	}

	/**
	 * @since 1.3
	 */
	protected ICommand getPostShardModeCommand(ControlModeRequest request) {
		return getParticipantCommand(request,
				getShardModeCommandParticipants(),
				IShardModeCommandParticipant::providesShardModeCommand,
				IShardModeCommandParticipant::getPostShardModeCommand);
	}

	private <P extends IControlModeParticipant> ICommand getParticipantCommand(ControlModeRequest request,
			Collection<? extends P> participants,
			BiPredicate<? super P, ? super ControlModeRequest> filter,
			BiFunction<? super P, ? super ControlModeRequest, ICommand> commandFunction) {

		return participants.stream()
				.filter(p -> filter.test(p, request))
				.map(p -> commandFunction.apply(p, request))
				.filter(Objects::nonNull)
				.reduce(ICommand::compose)
				.orElse(null);
	}

	/**
	 * @since 1.3
	 */
	@Override
	public Diagnostic approveRequest(ControlModeRequest request) {
		Diagnostic result;

		Collection<? extends IControlCommandApprover> approvers = getControlCommandApprovers();
		if (approvers.isEmpty()) {
			result = Diagnostic.OK_INSTANCE;
		} else {
			result = approvers.stream()
					.map(a -> a.approveRequest(request))
					.filter(Objects::nonNull)
					.reduce(Diagnostic.OK_INSTANCE, this::merge);
		}

		return result;
	}

	private Diagnostic merge(Diagnostic a, Diagnostic b) {
		return (a.getSeverity() == Diagnostic.OK)
				? b
				: (b.getSeverity() == Diagnostic.OK)
						? a
						: merge(new BasicDiagnostic(a.getSource(), a.getCode(), a.getMessage(), null), a, b);
	}

	private <D extends DiagnosticChain> D merge(D chain, Diagnostic a, Diagnostic b) {
		chain.merge(a);
		chain.merge(b);
		return chain;
	}

	/**
	 * @since 1.3
	 */
	@Override
	public boolean canCreateSubmodel(EObject objectToControl) {
		Collection<? extends IControlCommandApprover> approvers = getControlCommandApprovers();
		return approvers.isEmpty() || approvers.stream().allMatch(a -> a.canCreateSubModel(objectToControl));
	}

	/**
	 * @since 1.3
	 */
	protected List<IControlCommandApprover> getControlCommandApprovers() {
		return controlCommandApprovers;
	}

	/**
	 * Init the manager with all particpants
	 */
	protected void initParticipants() {
		IConfigurationElement[] extensions = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);
		for (IConfigurationElement e : extensions) {
			try {
				Object particpant = e.createExecutableExtension(PARTICPANT_ATTRIBUTE);
				if (particpant instanceof IControlCommandParticipant) {
					getControlCommandParticipants().add((IControlCommandParticipant) particpant);
				}
				if (particpant instanceof IUncontrolCommandParticipant) {
					getUncontrolCommandParticipants().add((IUncontrolCommandParticipant) particpant);
				}
				if (particpant instanceof IShardModeCommandParticipant) {
					getShardModeCommandParticipants().add((IShardModeCommandParticipant) particpant);
				}
				if (particpant instanceof IControlCommandApprover) {
					getControlCommandApprovers().add((IControlCommandApprover) particpant);
				}
			} catch (CoreException exception) {
				exception.printStackTrace();
			}
		}
		PartipantComparator comparator = new PartipantComparator();
		Collections.sort(uncontrolCommandParticipants, comparator);
		Collections.sort(controlCommandParticipants, comparator);
		Collections.sort(shardModeCommandParticipants, comparator);
		Collections.sort(controlCommandApprovers, comparator);
	}
}

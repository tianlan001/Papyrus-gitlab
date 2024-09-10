/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.notification;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelper;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.types.core.notification.events.AbstractTypesEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.AdviceApprovedEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.AdviceDisapprovedEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.AdviceRequestEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.EditHelperApprovedEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.EditHelperDisapprovedEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.ExecutableAdviceEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.ExecutableEditHelperEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.IdentityAdviceEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.IdentityEditHelperEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.UnexecutableAdviceEvent;
import org.eclipse.papyrus.infra.types.core.notification.events.UnexecutableEditHelperEvent;

public class TypesEventsChain {

	/**
	 * Timestamp of the creation of the chain
	 */
	private long timestamp;

	/**
	 * The {@link IEditHelper} that triggered the {@link TypesEventsChain}
	 */
	private IEditHelper editHelper;

	/**
	 * The {@link IEditCommandRequest} that triggered the {@link TypesEventsChain}
	 */
	private IEditCommandRequest req;

	TypesEventsChain(IEditHelper editHelper, IEditCommandRequest req) {
		timestamp = System.currentTimeMillis();
		this.editHelper = editHelper;
		this.req = req;
	}

	/**
	 * The various categories of events that occur during the execution of the {@link IEditHelper}
	 */
	private List<AbstractTypesEvent> adviceRequestConfigurationEvents = new ArrayList<AbstractTypesEvent>();

	private AbstractTypesEvent editHelperRequestConfigurationEvent = null;

	private List<AbstractTypesEvent> adviceApprovalEvents = new ArrayList<AbstractTypesEvent>();

	private AbstractTypesEvent editHelperApprovalEvent = null;

	private List<AbstractTypesEvent> beforeAdvicesCommandsEvents = new ArrayList<AbstractTypesEvent>();

	private List<AbstractTypesEvent> afterAdvicesCommandsEvents = new ArrayList<AbstractTypesEvent>();

	private AbstractTypesEvent editHelperCommandEvent = null;

	/**
	 * @return the adviceApprovalEvents
	 */
	public List<AbstractTypesEvent> getAdviceApprovalEvents() {
		return adviceApprovalEvents;
	}

	/**
	 * Add a {@link AdviceDisapprovedEvent} or {@link AdviceApprovedEvent} event
	 * 
	 * @param adviceApprovalEvent
	 */
	public void addAdviceApprovalEvent(AbstractTypesEvent adviceApprovalEvent) {
		if (adviceApprovalEvent instanceof AdviceDisapprovedEvent || adviceApprovalEvent instanceof AdviceApprovedEvent) {
			adviceApprovalEvents.add(adviceApprovalEvent);
		}
	}

	/**
	 * @return the editHelperAprrovalEvent
	 */
	public AbstractTypesEvent getEditHelperApprovalEvent() {
		return editHelperApprovalEvent;
	}

	/**
	 * @param editHelperAprrovalEvent
	 *            the editHelperAprrovalEvent to set
	 */
	public void setEditHelperApprovalEvent(AbstractTypesEvent editHelperApprovalEvent) {
		if (editHelperApprovalEvent instanceof EditHelperDisapprovedEvent || editHelperApprovalEvent instanceof EditHelperApprovedEvent) {
			this.editHelperApprovalEvent = editHelperApprovalEvent;
		}
	}

	/**
	 * @return the beforeAdvicesCommandsEvents
	 */
	public List<AbstractTypesEvent> getBeforeAdvicesCommandsEvents() {
		return beforeAdvicesCommandsEvents;
	}

	/**
	 * Add a {@link ExecutableAdviceEvent}, {@link UnexecutableAdviceEvent} or {@link IdentityAdviceEvent} event
	 * 
	 * @param beforeAdvicesCommandsEvents
	 *            the beforeAdvicesCommandsEvents to set
	 */
	public void addBeforeAdvicesCommandsEvent(AbstractTypesEvent beforeAdvicesCommandEvent) {
		if (beforeAdvicesCommandEvent instanceof ExecutableAdviceEvent || beforeAdvicesCommandEvent instanceof UnexecutableAdviceEvent || beforeAdvicesCommandEvent instanceof IdentityAdviceEvent) {
			beforeAdvicesCommandsEvents.add(beforeAdvicesCommandEvent);
		}
	}

	/**
	 * @return the afterAdvicesCommandsEvents
	 */
	public List<AbstractTypesEvent> getAfterAdvicesCommandsEvents() {
		return afterAdvicesCommandsEvents;
	}

	/**
	 * Add a {@link ExecutableAdviceEvent}, {@link UnexecutableAdviceEvent} or {@link IdentityAdviceEvent} event
	 * 
	 * @param afterAdvicesCommandEvent
	 */
	public void addAfterAdvicesCommandsEvent(AbstractTypesEvent afterAdvicesCommandEvent) {
		if (afterAdvicesCommandEvent instanceof ExecutableAdviceEvent || afterAdvicesCommandEvent instanceof UnexecutableAdviceEvent || afterAdvicesCommandEvent instanceof IdentityAdviceEvent) {
			afterAdvicesCommandsEvents.add(afterAdvicesCommandEvent);
		}
	}

	/**
	 * @return the editHelperCommandEvent
	 */
	public AbstractTypesEvent getEditHelperCommandEvent() {
		return editHelperCommandEvent;
	}

	/**
	 * @param editHelperCommandEvent
	 *            the editHelperCommandEvent to set ({@link ExecutableEditHelperEvent}, {@link UnexecutableEditHelperEvent} or
	 *            {@link IdentityEditHelperEvent})
	 */
	public void setEditHelperCommandEvent(AbstractTypesEvent editHelperCommandEvent) {
		if (editHelperCommandEvent instanceof ExecutableEditHelperEvent || editHelperCommandEvent instanceof UnexecutableEditHelperEvent || editHelperCommandEvent instanceof IdentityEditHelperEvent) {
			this.editHelperCommandEvent = editHelperCommandEvent;
		}
	}

	/**
	 * Returns all the events that occurred during the execution of the {@link IEditHelper} (i.e. all events categories)
	 * 
	 * @return
	 */
	public List<AbstractTypesEvent> getAllEvents() {
		List<AbstractTypesEvent> result = new ArrayList<AbstractTypesEvent>();
		if (!adviceRequestConfigurationEvents.isEmpty()) {
			result.addAll(adviceRequestConfigurationEvents);
		}
		if (editHelperRequestConfigurationEvent != null) {
			result.add(editHelperRequestConfigurationEvent);
		}
		if (!adviceApprovalEvents.isEmpty()) {
			result.addAll(adviceApprovalEvents);
		}
		if (editHelperApprovalEvent != null) {
			result.add(editHelperApprovalEvent);
		}
		if (!beforeAdvicesCommandsEvents.isEmpty()) {
			result.addAll(beforeAdvicesCommandsEvents);
		}
		if (editHelperCommandEvent != null) {
			result.add(editHelperCommandEvent);
		}
		if (!afterAdvicesCommandsEvents.isEmpty()) {
			result.addAll(afterAdvicesCommandsEvents);
		}
		return result;
	}

	/**
	 * @return the timestamp creation of this {@link TypesEventsChain}
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the req
	 */
	public IEditCommandRequest getRequest() {
		return req;
	}

	/**
	 * The Simple Name of the class of the {@link IEditHelper} that triggered this {@link TypesEventsChain}
	 * 
	 * @return
	 */
	public String getName() {
		return editHelper.getClass().getSimpleName();
	}

	/**
	 * @return the adviceRequestConfigurationEvents
	 */
	public List<AbstractTypesEvent> getAdviceRequestConfigurationEvents() {
		return adviceRequestConfigurationEvents;
	}

	/**
	 * @param adviceConfigureEvent
	 */
	public void addAdviceRequestConfigurationEvent(AdviceRequestEvent adviceConfigureEvent) {
		adviceRequestConfigurationEvents.add(adviceConfigureEvent);
	}

	/**
	 * @return the editHelperRequestConfigurationEvent
	 */
	public AbstractTypesEvent getEditHelperRequestConfigurationEvent() {
		return editHelperRequestConfigurationEvent;
	}

	/**
	 * @param editHelperRequestConfigurationEvent
	 *            the editHelperRequestConfigurationEvent to set
	 */
	public void setEditHelperRequestConfigurationEvent(AbstractTypesEvent editHelperRequestConfigurationEvent) {
		this.editHelperRequestConfigurationEvent = editHelperRequestConfigurationEvent;
	}
}

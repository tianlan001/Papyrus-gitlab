/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Ansgar Radermacher  ansgar.radermacher@cea.fr
 *  Shuai Li (CEA LIST) <shuai.li@cea.fr> - Bug 530073 - 2018
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;

public class ConnectorUtil {

	/**
	 * Returns true, is a port of a composite has an internal delegation to
	 * another port
	 *
	 * @param composite
	 * @param port
	 * @return
	 */
	public static boolean isDelegation(Class composite, Port port) {
		return (getDelegation(composite, port) != null);
	}

	/**
	 * Return the connector end of a delegation originating from a given port
	 * or null, if not such delegation exists
	 *
	 * @param composite
	 *            the composite component
	 * @param port
	 *            a port (may be inherited) of that implementation
	 * @return a connector end to which the port delegates or null
	 */
	public static ConnectorEnd getDelegation(Class composite, Port port) {
		for (Connector connector : composite.getOwnedConnectors()) {
			if (connectsPort(connector, port)) {
				ConnectorEnd otherEnd = connEndNotPart(connector, null);
				if (otherEnd != null) {
					return otherEnd;
				}
			}
		}
		return null;
	}

	/**
	 * Return the connector end of a delegation originating from a given port
	 * or null, if not such delegation exists
	 * 
	 * @since 3.3.0
	 * @param composite
	 *            the composite component
	 * @param port
	 *            a port (may be inherited) of that implementation
	 * @return a connector end to which the port delegates or null
	 */
	public static List<ConnectorEnd> getDelegations(Class composite, Port port) {
		EList<ConnectorEnd> conectorEnds = new UniqueEList<ConnectorEnd>();
		for (Connector connector : composite.getOwnedConnectors()) {
			if (connectsPort(connector, port)) {
				ConnectorEnd otherEnd = connEndNotPart(connector, null);
				if (otherEnd != null) {
					conectorEnds.add(otherEnd);
				}
			}
		}
		return conectorEnds;
	}

	/**
	 * check if a connector connects the port that is passed as parameter
	 */
	public static boolean connectsPort(Connector connection, Port port) {
		// check roles of end points
		// String qPortName = port.getQualifiedName();
		for (ConnectorEnd end : connection.getEnds()) {
			if (end.getRole() == port) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Simple helper function: return a connector end that is referencing
	 * the passed parameter "part" either directly (role) or via a port
	 *
	 * @param part
	 *            a property within a composite
	 * @param connection
	 * @return the first connector end that is referencing a certain part
	 *         passed as parameter
	 */
	public static ConnectorEnd connEndForPart(Connector connection, Property part) {
		// look for the end with same part (connectedPart == part)
		for (ConnectorEnd end : connection.getEnds()) {
			ConnectableElement role = end.getRole();
			Property connectedPart = null;
			if (role instanceof Port) {
				connectedPart = end.getPartWithPort();
			} else if (role instanceof Property) {
				// role is not a Port but also not null => connected directly to a part
				connectedPart = (Property) role;
			}

			if (connectedPart == part) {
				return end;
			}
		}
		return null;
	}

	/**
	 * Simple helper function: return a connector end that is *not* referencing
	 * with the passed parameter "part" either directly or via a port
	 * (useful, to get the "other" end of a connector).
	 *
	 * @param part
	 *            a property within a composite
	 * @param connection
	 * @return the first connector end that is not referencing to the
	 *         part passed as parameter.
	 */
	public static ConnectorEnd connEndNotPart(Connector connection, Property part) {
		// look for the other end (connectedPart != part)
		for (ConnectorEnd end : connection.getEnds()) {
			ConnectableElement role = end.getRole();
			Property connectedPart = null;

			if (role instanceof Port) {
				connectedPart = end.getPartWithPort();
			} else if (role instanceof Property) {
				// role is not a Port but also not null => connected directly to a part
				connectedPart = (Property) role;
			}

			if (connectedPart != part) {
				return end;
			}

		}
		return null;
	}

	/**
	 * check if a connector connects the part that is passed
	 * as parameter
	 */
	public static boolean connectsPart(Connector connection, Property part) {
		return (connEndForPart(connection, part) != null);
	}

	/**
	 * Return true, if passed connector is an assembly connector
	 *
	 * @param connection
	 * @return true, if assembly connection
	 */
	public static boolean isAssembly(Connector connection) {
		// no "null" part found => all ends are parts
		return (connEndForPart(connection, null) == null);
	}

	/**
	 * @param composite
	 *            A composite class
	 * @param partA
	 *            A part within the composite
	 * @param partB
	 *            Another part within the composite
	 * @return a connector, if it connects the parts A and B could within the passed composite, or null if no
	 *         such connector exists
	 */
	public static Connector getConnector(Class composite, Property partA, Property partB) {
		for (Connector connector : composite.getOwnedConnectors()) {
			if (ConnectorUtil.connectsPart(connector, partA) && ConnectorUtil.connectsPart(connector, partB)) {
				return connector;
			}
		}
		return null;
	}

	/**
	 * @param composite
	 *            A composite class
	 * @param partA
	 *            A part within the composite
	 * @param partB
	 *            Another part within the composite
	 * @return true, if a connector between the parts A and B could be
	 *         found within the passed composite
	 */
	public static boolean existsConnector(Class composite, Property partA, Property partB) {
		return getConnector(composite, partA, partB) != null;
	} 
}

/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/*
 * Copyright (c) 2005, 2018, Oracle and/or its affiliates. All rights reserved.
 */
/*
 * $Id: ApacheNodeSetData.java 1496478 2013-06-25 14:01:16Z mullan $
 */
package org.jcp.xml.dsig.internal.dom;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.crypto.NodeSetData;
import org.w3c.dom.Node;
import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;

public class ApacheNodeSetData implements ApacheData, NodeSetData<Node> {

    private XMLSignatureInput xi;

    public ApacheNodeSetData(XMLSignatureInput xi) {
        this.xi = xi;
    }

    public Iterator<Node> iterator() {
        // If nodefilters are set, must execute them first to create node-set
        if (xi.getNodeFilters() != null && !xi.getNodeFilters().isEmpty()) {
            return Collections.unmodifiableSet
                (getNodeSet(xi.getNodeFilters())).iterator();
        }
        try {
            return Collections.unmodifiableSet(xi.getNodeSet()).iterator();
        } catch (Exception e) {
            // should not occur
            throw new RuntimeException
                ("unrecoverable error retrieving nodeset", e);
        }
    }

    public XMLSignatureInput getXMLSignatureInput() {
        return xi;
    }

    private Set<Node> getNodeSet(List<NodeFilter> nodeFilters) {
        if (xi.isNeedsToBeExpanded()) {
            XMLUtils.circumventBug2650
                (XMLUtils.getOwnerDocument(xi.getSubNode()));
        }

        Set<Node> inputSet = new LinkedHashSet<Node>();
        XMLUtils.getSet(xi.getSubNode(), inputSet,
                        null, !xi.isExcludeComments());
        Set<Node> nodeSet = new LinkedHashSet<Node>();
        for (Node currentNode : inputSet) {
            Iterator<NodeFilter> it = nodeFilters.iterator();
            boolean skipNode = false;
            while (it.hasNext() && !skipNode) {
                NodeFilter nf = it.next();
                if (nf.isNodeInclude(currentNode) != 1) {
                    skipNode = true;
                }
            }
            if (!skipNode) {
                nodeSet.add(currentNode);
            }
        }
        return nodeSet;
    }
}

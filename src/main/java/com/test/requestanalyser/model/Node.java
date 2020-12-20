package com.test.requestanalyser.model;

import java.util.List;

/**
 * Author : Paritosh
 * Date : 20/12/20
 */
public class Node {

    public enum TYPE {COUNTRY, DEVICE, NONE}

    public long webReq;
    public long timeSpent;

    public TYPE type;
    public String typeValue;

    public List<Node> child;
    public Node parent;

    public List<Dimension> childData;

    public static void insert(Node node, Data data) {

    }

    Node getChild(TYPE type) {
        for (Node n : this.child) {
            if (n.type != null && n.type == type) return n;
        }
        return null;
    }
}

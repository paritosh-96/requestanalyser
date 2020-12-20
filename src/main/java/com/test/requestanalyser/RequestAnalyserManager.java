package com.test.requestanalyser;

import com.test.requestanalyser.model.Data;
import com.test.requestanalyser.model.Dimension;
import com.test.requestanalyser.model.Node;
import com.test.requestanalyser.model.Query;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Author : Paritosh
 * Date : 20/12/20
 */
public class RequestAnalyserManager {
    static Node root;

    public static void insert(Data data) {
        String country = null, device = null;
        for (Dimension dimension : data.dim) {
            if (dimension.key.equals("country")) country = dimension.value;
            if (dimension.key.equals("device")) device = dimension.value;
        }
        if (country == null || country.isEmpty() || device == null || device.isEmpty())
            throw new IllegalArgumentException("insert parameters wrong");
        Node countryNode = null;
        for (Node child : root.child) {
            for (Dimension dimension : child.childData) {
                if (dimension.value.equals(country)) {
                    countryNode = child;
                }
            }
        }

        assert countryNode != null;
        Node deviceNode = new Node();
        deviceNode.type = Node.TYPE.DEVICE;
        deviceNode.parent = countryNode;
        for (Dimension dimension : data.metrics) {
            switch (dimension.key) {
                case "webreq":
                    deviceNode.webReq = Long.parseLong(dimension.value);
                    break;
                case "timespent":
                    deviceNode.timeSpent = Long.parseLong(dimension.value);
            }
        }
        countryNode.child.add(deviceNode);
    }

    public static Data getData(Query query) {
        String country = query.dim.get(0).value;
        Node countryNode = null;
        for (Node child : root.child) {
            for (Dimension dimension : child.childData) {
                if (dimension.value.equals(country)) {
                    countryNode = child;
                }
            }
        }
        Data data = new Data();
        data.dim = query.dim;
        data.metrics = new ArrayList<>();
        assert countryNode != null;
        long webreqs = 0L;
        long timeSpent = 0L;
        for(Node child: countryNode.child) {
            webreqs += child.webReq;
            timeSpent += child.timeSpent;
        }
        Dimension webreqD = new Dimension("webreq", String.valueOf(webreqs));
        Dimension timespentD = new Dimension("timespent", String.valueOf(timeSpent));
        data.metrics.add(webreqD);
        data.metrics.add(timespentD);

        return data;
    }
}

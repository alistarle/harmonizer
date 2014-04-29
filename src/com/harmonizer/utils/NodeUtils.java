package com.harmonizer.utils;

import java.util.ArrayList;

import com.harmonizer.core.Song;
import com.harmonizer.graph.Node;

public class NodeUtils {
	private static ArrayList<Node> nodeList = new ArrayList<Node>();

	public static Node getNode(Node node) {
		Song.node++;
		if (!nodeList.contains(node)) {
			nodeList.add(node);
		}
		return nodeList.get(nodeList.indexOf(node));
	}
}

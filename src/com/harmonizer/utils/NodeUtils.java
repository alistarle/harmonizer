package com.harmonizer.utils;

import java.util.ArrayList;

import com.harmonizer.core.Song;
import com.harmonizer.graph.Node;
/**
 * Table d'unicité des noeuds du graphe
 * @author alistarle
 *
 */
public class NodeUtils {
	/**
	 * Liste des noeuds present dans le graphe
	 */
	public static ArrayList<Node> nodeList = new ArrayList<Node>();

	/**
	 * Retourne l'objet node de la liste si le node est déjà present, ou sinon l'ajoute a la liste et le retourne
	 * @param node Le noeud a traiter
	 * @return
	 */
	public static Node getNode(Node node) {
		if (!nodeList.contains(node)) {
			nodeList.add(node);
		}
		return nodeList.get(nodeList.indexOf(node));
	}
}

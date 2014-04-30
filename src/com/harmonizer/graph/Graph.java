package com.harmonizer.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import com.harmonizer.core.Chord;
import com.harmonizer.core.Note;
import com.harmonizer.core.NoteSet;
import com.harmonizer.core.NoteSetManager;
import com.harmonizer.core.Song;
import com.harmonizer.rules.LinkRule;
import com.harmonizer.rules.LocalRule;
import com.harmonizer.utils.ChordUtils;
import com.harmonizer.utils.RuleUtils;
import com.harmonizer.writer.LilyWriter;
import com.harmonizer.writer.MidiWriter;
/**
 * Class representant le graphe des harmonisation possible, elle calcule également les harmonisation et leur nombre
 * @author alistarle
 *
 */
public class Graph {
	/**
	 * Noeud racine du graphe crée artificielement
	 */
	private Node root;
	
	/**
	 * Liste de notes des differentes voix, qui seront écrites dans les fichier .mid et .ly
	 * @see MidiWriter
	 * @see LilyWriter
	 */
	private ArrayList<ArrayList<Note>> tracklist;
	
	/**
	 * Liste qui a chaque temps associe une note de soprano prise pour reference
	 */
	private ArrayList<Integer> timeline;
	
	/**
	 * Liste des jeux de notes possibles pour chaque temps du chant
	 */
	private ArrayList<ArrayList<NoteSet>> noteSetsList;
	
	/**
	 * Liste des jeux de notes suivant possible pour chaque jeu de notes a chaque temps
	 */
	private ArrayList<ArrayList<ArrayList<Integer>>> linkNoteSets;
		
	/**
	 * Crée un nouveau graphe, calcule ses harmonisation et leur nombres et écris une harmonisation dans les listes de notes des voix
	 * @param trackList La liste des notes des voix
	 * @param timeline La liste qui a chaque temps associe une note de soprano prise pour reference
	 */
	public Graph(ArrayList<ArrayList<Note>> trackList, ArrayList<Integer> timeline) {
		this.tracklist = trackList;
		this.timeline = timeline;
		this.noteSetsList = new ArrayList<ArrayList<NoteSet>>();
		this.linkNoteSets = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		initNoteSets();
		localSimplify();
		initLinking();
		Song.harmonisation = calcHarmonisation();
		initGraph();
		writeTrack((Node) root.getNext().toArray()[0],0);
	}
	
	/**
	 * Genère la liste des jeux de notes possible pour chaque temps
	 */
	private void initNoteSets() {
		noteSetsList.add(new NoteSetManager(tracklist.get(0).get(timeline.get(0))).getNoteSets());
		for(int i = 1; i < timeline.size(); i++) {
			noteSetsList.add(new NoteSetManager(tracklist.get(0).get(timeline.get(i))).getNoteSets());
		}
	}

	/**
	 * Initialise le premier noeud du graphe crée artificielement afin de lancer la construction recursive
	 * @see Graph#genGraph(HashSet, int)
	 */
	private void initGraph() {
		HashSet<NoteSet> next = new HashSet<NoteSet>();
		for(NoteSet ns : noteSetsList.get(0)) {
			next.add(ns);
		}
		root = new Node(null, -1,next);
		genGraph(root.getNext(), 0);
	}
	
	/**
	 * Genère récursivement le graphe des harmonisation possible a partir de la liste des jeux de notes et du tableau des jeux de notes suivant
	 * @param nodeList La liste de noeuds courante
	 * @param time Le temp auquel se rapporte ces noeuds
	 */
	public void genGraph(HashSet<Node> nodeList, int time) {
		for(Node node : nodeList) {
			HashSet<NoteSet> next = new HashSet<NoteSet>();
			for(Integer i : linkNoteSets.get(time).get(noteSetsList.get(time).indexOf(node.getNoteSet()))) {
				next.add(noteSetsList.get(time+1).get(i));
			}
			node.genNext(next);
		}		
		if(time < Song.duration-2) {
			HashSet<Node> nextNode = new HashSet<Node>();
			for(Node node : nodeList) {
				nextNode.addAll(node.getNext());
			}
			genGraph(nextNode, time+1);
		}
	}
	
	/**
	 * Genère le tableau des jeux de notes suivant pour chaque jeu de notes a chaque temps via les règles d'enchainement
	 */
	public void initLinking() {
		for(int i = 0; i < noteSetsList.size()-1; i++) {
			ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();
			for(NoteSet ns : noteSetsList.get(i)) {
				ArrayList<ArrayList<Integer>> byRuleList = new ArrayList<ArrayList<Integer>>();
				for(LinkRule rule : RuleUtils.getLinkRules()) {
					byRuleList.add(rule.simplify(ns, noteSetsList.get(i+1)));
				}
				
				ArrayList<Integer> tempList = new ArrayList<Integer>();
				for(Integer j : byRuleList.get(0)) {
					if(RuleUtils.containsAll(j, byRuleList)) tempList.add(j);
				}
				temp.add(tempList);
			}
			linkNoteSets.add(temp);
		}
	}
	
	/**
	 * Calcul le nombre d'harmonisation du chant en comptant les nombres de suivant de chaque jeu de notes possible a chaque temps
	 * @return Le nombre d'harmonisation
	 */
	public int calcHarmonisation() {
		int harmonisation = 0;
		for(ArrayList<ArrayList<Integer>> list : linkNoteSets) {
			for(ArrayList<Integer> intList : list) {
				harmonisation+=intList.size();
			}
		}
		return harmonisation;
	}
	
	public void simplifyLinking() {
		for(int i = linkNoteSets.size()-1; i >= 0; i--) {
			for(int j = linkNoteSets.get(i).size()-1; j >= 0; j--) {
				if(linkNoteSets.get(i).get(j).size() == 0) {
					linkNoteSets.get(i).remove(j);
				}
			}
		}
	}
	
	/**
	 * Simplifie les jeux de note possible de chaque temps via les règles locales
	 */
	public void localSimplify() {
		for(LocalRule rule : RuleUtils.getLocalRules()) {
			for(ArrayList<NoteSet> ns : noteSetsList) {
				rule.simplify(ns);
			}
		}
	}
	
	/**
	 * Ecris recursivement une harmonisation dans les liste de notes des voix
	 * @param node Le noeud courant
	 * @param time Le temps auquel le noeud se rapporte
	 */
	public void writeTrack(Node node, int time) {
		tracklist.get(1).add(node.getNoteSet().getAlto());
		tracklist.get(2).add(node.getNoteSet().getTenor());
		tracklist.get(3).add(node.getNoteSet().getBasse());
		if(node.getNext().size() != 0) {
			boolean passed = false;
			for(Node n : node.getNext()){
				if((n.getNext().size() != 0 || n.getTime() == Song.duration-1) && time < Song.duration && !passed) {
					passed = true;
					writeTrack(n,time+1);
				}
			}
		}
	}
	
	/**
	 * Affiche la taille des jeu de notes possible a chaque temps
	 */
	public void displayNoteSetStats() {
		System.out.println("Taille des noteSetList :");
		for(int i = 0; i < noteSetsList.size(); i++) {
			System.out.println("\t"+i+" : "+noteSetsList.get(i).size());
		}
	}
	
	/**
	 * Affiche la taille des jeux de notes suivant chaque jeu de note a chaque temps
	 */
	public void displayLinkingStats() {
		System.out.println("Affichage des lien entre noteSet :");
		for(int c = 0; c < linkNoteSets.size(); c++) {
			System.out.print(c+" : ");
			for(ArrayList<Integer> i : linkNoteSets.get(c)) {
				System.out.print("\t"+i.size());
			}
			System.out.println();
		}
	}
}

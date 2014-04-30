package com.harmonizer.utils;

import java.util.ArrayList;
import java.util.Arrays;

import com.harmonizer.rules.RuleII;
import com.harmonizer.rules.RuleIII;
import com.harmonizer.rules.RuleIV;
import com.harmonizer.rules.RuleV;
import com.harmonizer.rules.RuleVI;
import com.harmonizer.rules.LocalRule;
import com.harmonizer.rules.LinkRule;

public class RuleUtils {
	private static ArrayList<LocalRule> localRules = new ArrayList<LocalRule>(Arrays.asList(new RuleII(),new RuleIII()));
	private static ArrayList<LinkRule> linkRules = new ArrayList<LinkRule>(Arrays.asList(new RuleV(), new RuleVI()));
	
	public static ArrayList<LocalRule> getLocalRules() {
		return localRules;
	}
	
	public static ArrayList<LinkRule> getLinkRules() {
		return linkRules;
	}
	
	public static boolean containsAll(int i, ArrayList<ArrayList<Integer>> list) {
		for(ArrayList<Integer> intList : list) {
			if(!intList.contains(i)) {
				return false;
			}
		}
		return true;
	}
}

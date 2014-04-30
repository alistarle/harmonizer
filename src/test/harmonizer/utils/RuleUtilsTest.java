package test.harmonizer.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.harmonizer.utils.RuleUtils;

public class RuleUtilsTest {

	@Test
	public void testContainsAll() {
		int i;
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> intList = new ArrayList<>(Arrays.asList(3,5,6,9));
		ArrayList<Integer> intList2 = new ArrayList<>(Arrays.asList(2,3,5,5,9));
		i = 5;
		assertTrue("list vide",RuleUtils.containsAll(i, list));
		
		list.add(intList); list.add(intList2);
		assertTrue("5 present partout",RuleUtils.containsAll(i, list));
		
		i=2;
		assertFalse("2 present 1 liste",RuleUtils.containsAll(i, list));
		
		i=52;
		assertFalse("present nul part",RuleUtils.containsAll(i, list));
	}

}

package com.gdl.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaijialeGen2 {
	private final static BaijialeGen2 instance = new BaijialeGen2();
	public static BaijialeGen2 getInstance() { return instance; }
	
	private final Logger logger = LoggerFactory.getLogger(BaijialeGen2.class);
	
	public static class CardInfo {
		int[] cards = new int[5];
	}
	
	FastMap<Integer, FastList<CardInfo>> _list = new FastMap<Integer, FastList<CardInfo>>();
	
	List<int[]> _all_com = new ArrayList<int[]>();
	
	public void setupAllCom() {
		int[] _com1 = {1,2,3};
		_all_com.add(_com1);
		int[] _com2 = {1,2,4};
		_all_com.add(_com2);
		int[] _com3 = {1,2,5};
		_all_com.add(_com3);
		int[] _com4 = {1,3,4};
		_all_com.add(_com4);
		int[] _com5 = {1,3,5};
		_all_com.add(_com5);
		int[] _com6 = {1,4,5};
		_all_com.add(_com6);
		int[] _com7 = {2,3,4};
		_all_com.add(_com7);
		int[] _com8 = {2,3,5};
		_all_com.add(_com8);
		int[] _com9 = {2,4,5};
		_all_com.add(_com9);
		int[] _com10 = {3,4,5};
		_all_com.add(_com10);
	}
	
	FastMap<Integer, Integer> _rec = new FastMap<Integer, Integer>();
	private void recd(int num) {
		int ddd = 0;
		if (_rec.containsKey(num))
			ddd = _rec.get(num);
		ddd += 1;
		_rec.put(num, ddd);
	}
	private void print(int all) {
		for (int k : _rec.keySet()) {
			int a = _rec.get(k);
			System.err.println(k + " : " + a + ", rate : " + ((double)a / all));
		}
	}
	
	public static int processid(int id) {
		if (id == 0)
			return 0;
		
		id = id % 100;
		
		if (id >= 1 && id <= 13)
			return id;
		else if (id >= 14 && id <= 26)
			return id - 13;
		else if (id >= 27 && id <= 39)
			return id - 26;
		else
			return id - 39;
	}
	
	public static int getPoint(int id) {
		if (id >= 10)
			return 0;
		
		return id;
	}
	
	public static boolean hasDouble(int i1, int i2) {
		int j1 = processid(i1);
		int j2 = processid(i2);
		
		return j1 == j2;
	}
	
	public static int calcPoint(int i1, int i2) {
		int j1 = processid(i1);
		int j2 = processid(i2);
		
		int p1 = getPoint(j1);
		int p2 = getPoint(j2);
		
		return (p1 + p2) % 10;
	}
	
	public static int calcPoint(int i1, int i2, int i3) {
		int j1 = processid(i1);
		int j2 = processid(i2);
		int j3 = processid(i3);
		
		int p1 = getPoint(j1);
		int p2 = getPoint(j2);
		int p3 = getPoint(j3);
		
		return (p1 + p2 + p3) % 10;
	}
	
	private static int maskid(int id) {
		if (id >= 1 && id <= 13)
			return 1;
		else if (id >= 14 && id <= 26)
			return 2;
		else if (id >= 27 && id <= 39)
			return 3;
		else
			return 4;
	}
	public List<Integer> proceli(List<Integer> ol, ResList res) {
		List<Integer> cards1 = new ArrayList<>();
		int max_point = -1;
		int mask = 0;
		for (int kk : ol) {
			int pid = processid(kk);
			cards1.add(pid);
			
			if (pid > max_point) {
				max_point = pid;
				mask = maskid(kk);
			}
		}
		
		res.max_point = max_point;
		res.mask = mask;
		
		return cards1;
	}
	boolean is_debug = false;
	private void genWuniu() {
		List<Integer> _all_suffle_car = new ArrayList<Integer>();
		_all_suffle_car.clear();
		for (int i = 1; i <= 52; ++i) {
			_all_suffle_car.add(i);
		}
		
		
		for (int k = 0; k <=10; ++k) {
			Collections.shuffle(_all_suffle_car);
			genWuniu(_all_suffle_car.subList(0, 5));
			
			ArrayList<ResList> reslist = new ArrayList<BaijialeGen2.ResList>();
			for (int i = 0; i < 5; ++i) {
				ResList res1 = BaijialeGen2.getInstance().genWuniu(_all_suffle_car.subList(i * 5, (i + 1) *5));
				reslist.add(res1);
			}
			
			// 2. 判定牌型大小
			Collections.sort(reslist, new Comparator<BaijialeGen2.ResList>() {
				@Override
				public int compare(ResList o1, ResList o2) {
					if (o1.type > o2.type)
						return -1;
					else if (o1.type < o2.type)
						return 1;
					else {
						if (o1.max_point > o2.max_point)
							return -1;
						else if (o1.max_point < o2.max_point)
							return 1;
						else {
							if (o1.mask > o2.mask)
								return -1;
							else
								return 1;
						}
					}
				}
			});
			
			for (ResList rl : reslist) {
				System.err.println("type:"+rl.type+",max_p:"+rl.max_point+",mask:"+rl.mask);
			}
			
			System.err.println();
		}
		
		print(10000000);
	}
	
	public static class ResList {
		List<Integer> res_list;
		int type;
		int max_point;
		int mask;
		int id;
		int order;
	}
	
	public ResList genWuniu(List<Integer> org_cards) {
		ResList res = new ResList();
		List<Integer> cards = proceli(org_cards, res);
		
		boolean all_small = true;
		int sum111 = 0;
		for (int ddd : cards) {
			if (ddd <= 4) {
				sum111 += ddd;
			} else {
				all_small = false;
				break;
			}
		}
		
		if (all_small && sum111<= 10) {
			if (is_debug)
			recd(-1);
			res.res_list = org_cards;
			res.type = 13;
			return res;
		}
		
		boolean is_all = true;
		for (int ddd : cards) {
			if (ddd <= 10) {
				is_all = false;
				break;
			}
		}
		
		if (is_all) {
			if (is_debug)
			recd(-2);
			res.res_list = org_cards;
			res.type = 12;
			return res;
		}
		
		// 五个数里面是否有三个数相加为10，且剩余数相加最大
		int max_point = -1;
		boolean niu = false;
		int target_idx = -1;
		int idx = 0;
		
		List<Integer> cardsv = new ArrayList<Integer>();
		for (int u : cards) {
			if (u > 10)
				cardsv.add(10);
			else
				cardsv.add(u);
		}
		
		boolean[] maskssd = new boolean[5];
		for (int[] arr : _all_com) {
			niu = false;
			for (int i = 0; i < maskssd.length; ++i) {
				maskssd[i] = false;
			}
			
			int idx0 = arr[0] - 1;
			int idx1 = arr[1] - 1;
			int idx2 = arr[2] - 1;
			
			maskssd[idx0] = true;
			maskssd[idx1] = true;
			maskssd[idx2] = true;
			int sum = cardsv.get(idx0) + cardsv.get(idx1) + cardsv.get(idx2);
			
			if (sum % 10 == 0) {
				if (is_debug)
				System.err.println("--------d1:" + cards.get(idx0) 
						+ ",d2:"+ cards.get(idx1) + ",d3:" + cards.get(idx2));
				niu = true;
			}
			
			if (niu) {
				int odd = 0;
				for (int i = 0; i < maskssd.length; ++i) {
					if (!maskssd[i])
						odd += cardsv.get(i);
				}
				
				odd = odd % 10;
				
				if (max_point == 0) {
					max_point = 0;
					target_idx = idx;
					if (is_debug)
					System.err.println("max_num: " + max_point);
					break;
				}
				
				if (odd > max_point) {
					max_point = odd;
					target_idx = idx;
					if (is_debug)
					System.err.println("max_num: " + odd);
				}
			}
			
			idx++;
		}
		
		if (target_idx > -1) {
			if (is_debug)
			System.err.println("target_idx = " + target_idx);
			int[] poss = _all_com.get(target_idx);
			
			for (int i = 0; i < maskssd.length; ++i) {
				maskssd[i] = false;
			}
			
			int idx0 = poss[0] - 1;
			int idx1 = poss[1] - 1;
			int idx2 = poss[2] - 1;
			
			maskssd[idx0] = true;
			maskssd[idx1] = true;
			maskssd[idx2] = true;
			
			List<Integer> i1 = new ArrayList<Integer>();
			i1.add( org_cards.get(idx0) );
			i1.add( org_cards.get(idx1) );
			i1.add( org_cards.get(idx2) );
			
			if (is_debug)
			System.err.println("max_point=" + max_point + 
					",d1:" + cards.get(idx0) 
						+ ",d2:"+ cards.get(idx1) + ",d3:" + cards.get(idx2));
			
			for (int i = 0; i < maskssd.length; ++i) {
				if (!maskssd[i]) {
					if (is_debug)
					System.err.println("dd:" + cards.get(i));
					i1.add( org_cards.get(i) );
				}
			}
			
			if (is_debug)
			recd(max_point);
			
			res.res_list = i1;
			if (max_point == 0)
				res.type = 11;
			else
				res.type = max_point + 1;
			return res;
		}
		
		res.res_list = org_cards;
		res.type = 1;
		return res;
	}
	
	public static void main(String[] args) {
		BaijialeGen2.getInstance().init();
	}
	
	public void init() {
		setupAllCom();
		genWuniu();
	}
}

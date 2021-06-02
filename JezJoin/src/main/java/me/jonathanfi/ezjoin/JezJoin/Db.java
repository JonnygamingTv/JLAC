package me.jonathanfi.ezjoin.JezJoin;

import java.util.HashMap;
import java.util.Map;

public class Db {
	private static Map<String,String> ppl = new HashMap<String,String>();
	private static Map<String,Boolean> lppl = new HashMap<String,Boolean>();
	private static Map<String,Boolean> exist = new HashMap<String,Boolean>();
	private static Map<String,String> serv = new HashMap<String,String>();
	private static Map<String,Boolean> pison = new HashMap<String,Boolean>();
	public static void ison(String n,Boolean is) {
		if(is) {
			pison.put(n, true);
			return;
		}
		pison.remove(n);
	}
	public static boolean on(String n) {
		if(pison.containsKey(n)) {
			return true;
		}
		return false;
	}
	public static void setsrv(String n, String s) {
		if(s != "") {
			serv.put(n, s);
		}else {
			serv.remove(n);
		}
	}
	public static String getsrv(String n) {
		if(serv.containsKey(n)) {
			return serv.get(n);
		}
		return "";
	}
	public static boolean doexist(String name) {
		if(exist.containsKey(name))return true;
		return false;
	}
	public static void exists(String name) {
		exist.put(name, true);
	}
	public static void setLogp(String name) {
		lppl.put(name,true);
	}
	public static boolean needLogp(String name) {
		if(lppl.containsKey(name))return true;
		return false;
	}
	public static void unsetLogp(String name) {
		lppl.remove(name);
	}
	public static boolean getPpl(String name, String pw) {
		if(ppl.containsKey(name)) {
		if(pw != "") {
			if(ppl.get(name).contentEquals(pw)) {return true;}
		}else{
			return true;
		}
		}
		return false;
	}

	public static void setPpl(String name, String pw) {
		ppl.put(name, pw);
	}
	public static void savePpl() {
		// coming soon
	}
}

package me.jonathanfi.ezjoin.JezJoin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import net.md_5.bungee.api.ServerPing;

public class Db {
	private static Map<String,String> ppl = new HashMap<String,String>();
	private static Map<String,Boolean> lppl = new HashMap<String,Boolean>();
	private static Map<String,Boolean> exist = new HashMap<String,Boolean>();
	private static Map<String,String> serv = new HashMap<String,String>();
	private static Map<String,Boolean> pison = new HashMap<String,Boolean>();
	private static Map<String,Integer> tries=new HashMap<String,Integer>();
	public static Map<String,String> alias=new HashMap<String,String>();
	public static Map<String,Boolean> pingMotD=new HashMap<String,Boolean>();
	public static Map<String,Boolean> pingFavi=new HashMap<String,Boolean>();
	public static Map<String,Boolean> pingP=new HashMap<String,Boolean>();
	public static Map<String,String> pingF=new HashMap<String,String>();
	public static Map<String,Boolean> ipforce=new HashMap<String,Boolean>();
	public static Map<String,ServerPing> ipPing=new HashMap<String,ServerPing>();
	private static File Directory;
	public static int tri(String n, Boolean g) {
		if(g) {
			int num=0;
			if(tries.containsKey(n)) {
				num=tries.get(n);
			}
			tries.put(n,num+1);
			return num;
		}else if(tries.containsKey(n)){
			return tries.get(n);
		}
		return 0;
	}
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
		if(App.uF) {
			File fil = new File(Directory,name);
			if(pw!="") {
			if(fil.canRead()) {
			try {
        		Scanner myReader = new Scanner(fil);
        		String dat = "";
        		while (myReader.hasNextLine()) {
        			dat += myReader.nextLine();
        		}
        		myReader.close();
        		return dat.contentEquals(pw);
			} catch (FileNotFoundException e) {}
			}
			}else {
				return fil.canRead();
			}
		}else
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
		if(App.save) {
			try {
			File fil = new File(Directory,name);
			BufferedWriter writer = new BufferedWriter(new FileWriter(fil));
			writer.write(pw);writer.close();
			} catch (IOException e) {}
		}else
		ppl.put(name, pw);
	}
	public static void sDir(File file) {
		Directory = file;
	}
}

package gameserver.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WordFilter {
	static WordFilter _inst = new WordFilter("data/config/filterword_chat.txt");
	public static WordFilter getIns() {return _inst;}
    private Map<String, Pattern> blockedWords = new HashMap<String, Pattern>();
    private String[] badnum = new String[]{"64", "722", "89"};

    public WordFilter(String filePath) {
        this.loadFromFile(filePath);
    }

    private void loadFromFile(String string) {
        ArrayList<String> list = readFile(string);
        if (list == null || list.isEmpty()) {
            return;
        }
        HashMap<String, Pattern> newMap = new HashMap<String, Pattern>();
        for (String line : list) {
            newMap.put(line, Pattern.compile(line, Pattern.CASE_INSENSITIVE));
        }
        this.blockedWords = newMap;
    }

    /**
     * 过滤聊天信息
     *
     * @param str
     * @return
     */
    public String filter(String str) {
        if (containWord(str)) {
            str = filterWord(str, "***");
        }

        return str;
    }

    /**
     * 检查是否含有非法的词汇
     *
     * @param normalized
     * @return
     */
    public boolean containWord(String normalized) {
        if (blockedWords == null || blockedWords.isEmpty()) {
            return false;
        }
        Set<String> keys = blockedWords.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Pattern pattern = blockedWords.get(key);
            if (pattern.matcher(normalized).find()) {
                return true;
            }
        }
        // check bad num
        for (int i = 0; i < badnum.length; i++) {
            if (normalized.contains(badnum[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将非法字符替换为指定符号
     *
     * @param replaced
     * @param mask
     * @return
     */
    private String filterWord(String replaced, String mask) {
        if (blockedWords == null || blockedWords.isEmpty()) {
            return replaced;
        }
        Set<String> keys = blockedWords.keySet();
        for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Pattern pattern = blockedWords.get(key);
            Matcher matcher = pattern.matcher(replaced);
            if (matcher.find()) {
                // replaced = matcher.replaceAll(makeChar(mask,key.length()));
                replaced = matcher.replaceAll(mask);
            }
        }
        for (int i = 0; i < badnum.length; i++) {
            replaced = replaced.replace(badnum[i], "*");
        }
        return replaced;
    }

    /**
     * 读取屏蔽词文件
     *
     * @param fileName
     * @return
     */
    private ArrayList<String> readFile(String fileName) {
        ArrayList<String> list = new ArrayList<String>();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;
        try {
            inputStream = new FileInputStream(fileName);
            inputStreamReader = new InputStreamReader(inputStream, "UTF8");
            br = new BufferedReader(inputStreamReader);
            String temp = null;
            while ((temp = br.readLine()) != null) {
                list.add(temp.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
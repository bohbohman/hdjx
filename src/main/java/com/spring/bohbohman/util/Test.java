package com.spring.bohbohman.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: xueyb
 * @Date: 2018/12/18 16:35
 * @Description:
 */
public class Test {

    public static int recursive(int n) {
        if (n < 3) {
            return 1;
        }
        return recursive(n - 1) + recursive(n - 2);
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
        /*-----------------------------------
        笨方法：String s = "你要去除的字符串";
                1.去除空格：s = s.replace('\\s','');
                2.去除回车：s = s.replace('\n','');
        这样也可以把空格和回车去掉，其他也可以照这样做。
        注：\n 回车(\u000a)
        \t 水平制表符(\u0009)
        \s 空格(\u0008)
        \r 换行(\u000d)*/
    }

    public static void main(String[] args) {
        //System.out.println("recursive=" + recursive(4));
        String str = "北　京";
        String str1 = str.replace(" ", "");
        String str2 = str.replaceAll(" ", "");
        String str3 = str.replaceAll("\\s*", "");
        String str4 = str.replaceAll(" +", "");
        String str5 = str.replaceAll("\\u0009", "");
        String str7 = str.replace("　", "");
        String str8 = str.replaceAll("\\s+", "");
        System.out.println("str1=" + str1);
        System.out.println("str2=" + str2);
        System.out.println("str3=" + str3);
        System.out.println("str4=" + str4);
        System.out.println("str5=" + str5);
        System.out.println("str6=" + replaceBlank(str));
        System.out.println("str7=" + str7);
        System.out.println("str8=" + str8);
    }
}

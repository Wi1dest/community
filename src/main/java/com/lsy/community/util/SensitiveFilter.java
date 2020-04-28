package com.lsy.community.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Lo Shu-ngan
 * @Classname SensitiveFilter
 * @Description 敏感词过滤
 * @Date 2020/04/28 13:33
 */
@Slf4j
@Component
public class SensitiveFilter{
    /**
     * 替换符号
     */
    private static final String REPLACEMENT = "***";

    /**
     * 根节点
     */
    private TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init(){
        // 写在try(){}的()中,运行完会自动关闭
        try (
            // sensitive-words.txt放在resources下,编译后会自动去到classes中
            /*EG:
                赌博
                嫖娼
                吸毒
                开票
             */
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
            // 字节流转为字符流再转为缓冲流
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
           ){
            String keyword;
            while ((keyword = reader.readLine()) != null){
                //添加到前缀树
                this.addKeyword(keyword);
            }
        } catch (IOException e) {
            log.error("加载敏感词文件是啊比"+e.getMessage());
        }
    }

    /**
     * 将一个敏感词添加到前缀树中
     * @param keyword
     */
    private void addKeyword(String keyword){
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length() ; i++){
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null){
                // 初始化子节点
                subNode = new TrieNode();
                tempNode.addSubNode(c,subNode);
            }

            // 指向子节点,进入下一轮循环
            tempNode = subNode;

            // 设置结束标识
            if (i == keyword.length() -1 ){
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param text 待过滤文本
     * @return 过滤后文本
     */
    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return null;
        }

        // 指针1
        TrieNode tempNode = rootNode;
        // 指针2
        int bengin = 0;
        // 指针3
        int position = 0;
        // 结果
        StringBuilder stringBuilder = new StringBuilder();

        while (position < text.length()){
            char c = text.charAt(position);

            // 跳过符号
            if (isSymbol(c)){
                // 若指针1处于根节点,将此符号计入结果,让指针2向下走一步
                if (tempNode == rootNode){
                    stringBuilder.append(c);
                    bengin++;
                }
                // 无论符号在开头或中间,指针3都向下走一步
                position++;
                continue;
            }

            // 检查下级节点
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                // 以begin为开头的字符串不是敏感词
                stringBuilder.append(text.charAt(bengin));
                // 进入下一个位置
                position = ++bengin;
                // 重新指向根节点
                tempNode = rootNode;
            }else if (tempNode.isKeywordEnd()){
                // 发现了敏感词,将begin~position字符串替换掉
                stringBuilder.append(REPLACEMENT);
                // 进入下一个位置
                bengin = ++position;
                // 重新指向根节点
                tempNode = rootNode;
            } else {
                // 检查下一个字符
                position++;
            }
        }

        // 将最后一批字符计入结果
        stringBuilder.append(text.substring(bengin));

        return stringBuilder.toString();
    }

    /**
     * 判断是否为符号
     * @param c
     * @return
     */
    private boolean isSymbol(Character c){
        // 东亚文字范围 : 0x2E80 ~ 0x9FFF
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    /**
     * 前缀树
     */
    @Data
    private class TrieNode {
        // 关键词结束的标识
        private boolean isKeywordEnd = false;

        // 子节点(key是下级字符,value是下级节点)
        private Map<Character,TrieNode>  subNodes = new HashMap<>();

        // 添加子节点
        public void addSubNode(Character c,TrieNode node){
            subNodes.put(c,node);
        }

        // 获取子节点
        public TrieNode getSubNode(Character c){
            return subNodes.get(c);
        }
    }
}

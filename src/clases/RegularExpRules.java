package clases;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kleinz
 */
public class RegularExpRules {
    /* Regular Expressions Constants Declaration */
    protected static final String[] keywords = {"class","main","if","else","while","int","break"};
    protected static final String[] functions = {"pinWriter","printN","printS","timer"};
    protected static final Pattern Operators = Pattern.compile("\\+|\\*|\\/|\\-|\\>|\\<|\\(|\\)|\\[|\\]|\\{|\\}|\\=|\\!=");
    protected static final Pattern Digits = Pattern.compile("\\d+");
    protected static final Pattern Strings = Pattern.compile("\"([^\\\\\"\\n]|\\\\.)*\"");
    protected static final Pattern Comments = Pattern.compile("\\/\\*([^\\$][\\s\\S]*?)?\\*\\/");
    /* Styles Attributes to paint words */
    public static final StyleContext styleContext = StyleContext.getDefaultStyleContext();
    protected static final AttributeSet keywordsAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.decode("#7B68EE"));
    protected static final AttributeSet functionsAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(204,0,255));
    protected static final AttributeSet defaultAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.decode("#00FF00"));
    protected static final AttributeSet operatorsAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(255,51,153));
    protected static final AttributeSet commentAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(153,153,153));
    protected static final AttributeSet digitAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, new Color(255,255,255));        
    protected static final AttributeSet stringAttributeSet = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Foreground, Color.decode("#FF4500"));        
    protected static final AttributeSet attrBold = styleContext.addAttribute(styleContext.getEmptySet(), StyleConstants.Bold, true);
    /* Methods that return the Style Color to the Words*/
    public StyledDocument styleKeywords(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, keywordsAttributeSet, true);
        return styledDoc;
    }
    public StyledDocument styleFunctions(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, functionsAttributeSet, true);
        return styledDoc;
    }
    public StyledDocument styleOperators(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, operatorsAttributeSet, true);
        return styledDoc;
    }
    public StyledDocument styleDefault(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, defaultAttributeSet, true);
        return styledDoc;
    }
    public StyledDocument styleStrings(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, stringAttributeSet, true);
        return styledDoc;
    }
    public StyledDocument styleComments(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, commentAttributeSet, true);
        return styledDoc;
    }
    public StyledDocument styleDigits(JTextPane textpane, int start, int end){
        StyledDocument styledDoc = textpane.getStyledDocument();
        styledDoc.setCharacterAttributes(start, end, digitAttributeSet, true);
        return styledDoc;
    }

    /* Method to save in ArrayList type Pattern all Patterns (Regular Expressions) 
       Only the two arrays type String require separate each reserved word or function with "\\b"
       to avoid that two words are together and painted. */
    protected List<Pattern> buildPatterns(){
    StringBuilder keys = new StringBuilder();
    StringBuilder funcs = new StringBuilder();
    Pattern keywordPattern, functionPattern, operatorPattern,commentPattern,digitPattern,stringPattern;
    for (String token : keywords) {
         
            keys.append("\\b"); // Start of word boundary
            keys.append(token);
            keys.append("\\b|"); // End of word boundary and an or for the next word
           
        }

    if (keys.length() > 0) {
            
            keys.deleteCharAt(keys.length() - 1); // Remove the trailing "|"
            
    }
    
    
    for (String token : functions) {     
            funcs.append("\\b"); // Start of word boundary
            funcs.append(token);
            funcs.append("\\b|"); // End of word boundary and an or for the next word       
    }
    if (funcs.length() > 0) {           
            funcs.deleteCharAt(funcs.length() - 1); // Remove the trailing "|"     
    }   
    keywordPattern = Pattern.compile(keys.toString());
    functionPattern = Pattern.compile(funcs.toString());
    operatorPattern = Operators;
    commentPattern = Comments;
    digitPattern = Digits;
    stringPattern = Strings;
    List<Pattern> patterns = new ArrayList<Pattern>();
    patterns.add(keywordPattern); //[0] keywordPattern
    patterns.add(functionPattern);//[1] functionPattern
    patterns.add(operatorPattern); //[2] operatorPattern
    patterns.add(commentPattern);//[3] commentPattern
    patterns.add(digitPattern);//[4] digitPattern
    patterns.add(stringPattern);//[5] stringPattern
    return patterns;
       
    }  




}


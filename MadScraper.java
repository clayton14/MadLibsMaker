/**
 * @MadLibMaker
 *
 * @author Clayton Easley
 *
 * This is the class responsable for scraping text from the webpage
 * Then randmoly removes words from the text and allowes new words to
 * fill in
 *
 *
 * */


package MadLibsMaker;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.Random;

public class MadScraper {

      private String URl = "";
      private  Document MadDoc;
      private String[] words = {};
      private int TotalWords;


//constructors
    public MadScraper(String URl) {
        this.URl = URl;
    }
    public MadScraper() {
        this.URl = URl;
    }

//encapsulation
    public void setURl(String URl) {
        this.URl = URl;
    }
    public String[] getWords() {
        return words;
    }
   
    //Methods

    /**
     * the  getWords() method has one purpose and it is to extract all the text from a
     * website (all text in the <p> tags )
     */
    public String getText() {

        Elements pGraph = null;
        try {
            MadDoc = Jsoup.connect(URl).get();
            pGraph = MadDoc.getElementsByTag("p");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return pGraph.text();
    }

    /**
     * Based off of the tag properties entered as the parameter it will remover them from the list
     * aka tries to filter out links
     * */
    public String getText(String tagType) {
        Elements p = null;
        try {
            MadDoc = Jsoup.connect(URl).get();
            p = MadDoc.getElementsByTag("p");

    if (tagType.equalsIgnoreCase("href")) {
        MadDoc.select("a[href]").remove();

    }else if(tagType.equalsIgnoreCase("class")) {
        MadDoc.select("a[class]").remove();

    }else if(tagType.equalsIgnoreCase("src")) {
        MadDoc.select("a[src]").remove();

    }else if(tagType.equalsIgnoreCase("all"))
            {
                MadDoc.select("a[src]").remove();
                MadDoc.select("a[class]").remove();
                MadDoc.select("a[href]").remove();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    return p.text();
    }

    /**
     * puts all the words into an array and gets the length
     * */


    public void mySplit(String text) {
        text.trim();
        ArrayList<String> array = new ArrayList<String>();

        int x=0;
        while(x != -1)
        {
            x = text.indexOf(" ");
            if(x != -1) //check if space is found
            {
                String s = text.substring(0,x);
                array.add(s);                //add the word preceding the space
                text = text.substring(x+1); //reset text to be equal to after the word just found
            }

        }
        array.add(text);
        //copy into words array, just because that's what we were using in the split
        words = new String[array.size()];
        for(int i=0; i<words.length; i++)
        {
            words[i]= (String)array.get(i);
        }
        //return array; don't need to return
    }


    public int wordCount(){
        // looks for whitespace and a lot simpler than a tokenizer
         mySplit(getText().trim());
         int count = words.length;
         this.TotalWords = count;
       // System.out.println(TotalWords);
         return count;

    }


    public void userInterface(int Rate){
        Scanner reader = new Scanner(System.in);
        String[] newWords = new String[TotalWords/6];

        for(int i=0; i<TotalWords/Rate; i++)
        {
            System.out.println("Enter a word");
            newWords[i] = reader.nextLine();
        }
        int cnt=0;
        String madLib = "";
        for(int i=0; i<TotalWords; i++)
        {
            if(words[i] != null)
            {
                madLib += words[i] + " ";
            }
            else
            {
                madLib += newWords[cnt] + " ";
                cnt++;

            }
        }
        System.out.println(madLib.replaceAll("null", ""));
    }


    public ArrayList randRemove(){

        ArrayList removedWords = new ArrayList();
        Random rand = new Random();
        for (int i = 0; i <TotalWords/6 ; i++) {

            int x = rand.nextInt(TotalWords);
            removedWords.add(x);
            words[x]=null; //set word at index x to null

        }
        return removedWords;
    }


   public void run() {
       getText("all");
       wordCount();
       randRemove();
   }

    public static void main(String[] args) throws ClassNotFoundException {

//        Scanner s = new Scanner(System.in);
//        MadScraper m = new MadScraper();
//        System.out.println("Welcome to Mad Lib Maker \enenter a valid URL to start");
//        String URL = s.nextLine();
//        m.setURl(URL);
//
//        m.run();
//
//        System.out.println("The word count is: " + m.wordCount());
//        System.out.print("IF the website contains a large amount of text you might want to increase the removal division rate\n\t --> enter rate");
//        int rate = s.nextInt();
//        m.userInterface(rate);

        MadScraper mad = new MadScraper();
        mad.setURl("https://en.wikipedia.org/wiki/Barack_Obama");
        System.out.println("\nThe word count is: " + mad.wordCount()) ;
        mad.run();
        mad.userInterface(2000);

    }
}
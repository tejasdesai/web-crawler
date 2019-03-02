
package webcrawler;

/**
 *
 * @author tejasdesai
 * Name: Tejas Desai
 * Student ID: 700669284
 */
import java.io.*;
import java.util.*;
import java.net.*;

class EncapCrawler{
    private String url;
    private String fname;
    private String dirname;
    
    EncapCrawler(){
        System.out.println("Enter Directory Path: ");
        Scanner sc = new Scanner(System.in);
        dirname = sc.nextLine();
        System.out.println("Enter File Name: ");
        fname = sc.nextLine();
    }
    
    public String getStartingURL(){
        
        File f = new File(dirname, fname);
        if(f.exists()){
            try{
                Scanner fileread = new Scanner(f);
                if(fileread.hasNextLine()){
                    url = fileread.nextLine();
                }else{
                    System.out.println("Empty file.");
                }
            }catch(FileNotFoundException e){
                System.out.println("No such file found.");
            }
        }else{
            System.out.println("File does not exists");
        }
        return url;
    }
}

public class WebCrawler {
    static int visitedurls = 0;
    static ArrayList <String> visited = new ArrayList <String>();
    static String inputLine;
    static ArrayList <String> urls = new ArrayList <String>();
    
    static int VisitURL(String url){
        try{
            java.net.URL conn = new java.net.URL(url);
            Scanner sc1 = new Scanner(conn.openStream());
            System.out.println("Visited website: "+url);
            visited.add(url);
            visitedurls++;
            System.out.println("Visited website count is= "+visitedurls);
            while (sc1.hasNextLine()){
                inputLine = sc1.nextLine();
                if(inputLine.length() > 0 && inputLine.contains("http:")){
                    inputLine = inputLine.trim();
                    inputLine = inputLine.substring(inputLine.indexOf("http:"));
                    if(inputLine.indexOf("\"") >= 0){
                        inputLine = inputLine.substring(0, inputLine.indexOf("\""));
                        urls.add(inputLine);
                    }
                }
            }
        } catch(MalformedURLException e){
            System.out.println("Unable to locate URL.");
        } catch(IOException e){
            System.out.println(e);
        }
        return visitedurls;
    }
    
    public static void main(String[] args) {
        int i=0;
        EncapCrawler ec = new EncapCrawler();
        visitedurls = VisitURL(ec.getStartingURL());
        
        do{
            if(visited.contains(urls.get(i)) || !urls.get(i).contains("http://")){
                i++;
                continue;
            } else {
                VisitURL(urls.get(i));
                i++;
            }
        } while(visitedurls < 100);
    }
    
}

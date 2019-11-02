package com.DBLP_Explorer.DBLP_Explorer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DblpExplorerApplication {

	public static void main(String[] args) throws IOException {


		SpringApplication.run(DblpExplorerApplication.class, args);

		System.out.println("\nStarting file reading...\n");

		//speed testing
		long start = Instant.now().toEpochMilli();

		//json parser
		JSONParser parser = new JSONParser();
		//Map to hold tiers of all articles, each tier will have a list of the articles that articles in that tier reference
		HashMap<Integer, HashSet<String>> tiers = new HashMap<>();

		//list of the articles as json objects
		List<JSONObject> articles = null;

		//create a list of the articles as json objects
		try(Stream<String> lines = Files.lines(Paths.get(args[0]), Charset.defaultCharset())){
            articles = lines
					//map lines to json objects
					.map(line -> {
				try {
					return (JSONObject) parser.parse(line);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			})
					//collect json objects into a list
					.collect(Collectors.toList());
		}
		catch(IOException e){
			e.printStackTrace();
		}

		System.out.println("\nReading File Finished.\n");
        long readTime = Instant.now().toEpochMilli()-start;
        System.out.println("\nStarting article finding...\n");

		//find articles whose title contains the keyword
		System.out.println("\nKeyword Articles: ");
		//to hold the ids of the articles referenced by keyword articles, and later to hold the ids of the articles referenced by tiers
		HashSet<String> ids = new HashSet<>();
		//find papers whose title contains the keyword, order them by the year they were published
		articles.stream().filter(art -> art.get("title").toString().toUpperCase().contains(args[1].toUpperCase())).sorted(Comparator.comparing(art -> (Long)((JSONObject) art).get("year")).reversed()).forEach(article -> printTierArticles(article, ids));
		//add the referenced papers to the HashMap
		tiers.put(0, (HashSet)ids.clone());
		ids.clear();

		//find articles that are cited by keyword and tier articles
        int n = Integer.parseInt(args[2]);
        for(int i = 0; i < n; i++){
            int currTier = i+1;
            System.out.println("\n\nTier-"+currTier+" Articles: ");
            int finalI = i;
            //order the articles by the year they were published
            articles.stream().filter(art -> tiers.get(finalI).contains(art.get("id"))).sorted(Comparator.comparing(art -> (Long)((JSONObject) art).get("year")).reversed()).forEach(article -> printTierArticles(article, ids));
            tiers.put(currTier, (HashSet)ids.clone());
            ids.clear();
        }

        System.out.println("\nFinding articles finished.\n");

        long tierTime = Instant.now().toEpochMilli()-start-readTime;
		long totalTime = Instant.now().toEpochMilli()-start;

		//memory testing
		Runtime rt = Runtime.getRuntime();
		rt.gc();
		long _totalMem = rt.totalMemory();
		long _freeMem = rt.freeMemory();
		long _usedMem = (_totalMem-_freeMem)/(1024*1024);

        System.out.println("\n\nTime consumption in Milliseconds: ");
        System.out.println("Read Time: "+readTime);
        System.out.println("Tiering Time: "+tierTime);
        System.out.println("Total time: "+totalTime);
		System.out.println("\n\nMemory consumption in MB: "+_usedMem);
	}

	//method to print the articles and find the papers they cite
    public static void printTierArticles(JSONObject obj, HashSet<String> ids){
		JSONParser parser = new JSONParser();
		System.out.println("\nID: "+obj.get("id"));
        System.out.println("Title: "+obj.get("title"));

        /*JSONArray authors = (JSONArray) obj.get("authors");
		final String[] authorString = {""};
        authors.stream().map(author -> (JSONObject) author).map(author -> ((JSONObject) author).get("name").toString()).forEach(author -> authorString[0] = authorString[0]+author.toString()+", ");
        System.out.println("Author(s): "+authorString[0].substring(0, authorString[0].length()-2));*/

        System.out.println("Year: "+obj.get("year"));
        JSONArray references = (JSONArray) obj.get("references");
            if(references!= null) {
                references.parallelStream().forEach(id -> ids.add((String)id));
        }
    }
}

package com.google.code.donkirkby;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import twitter4j.RateLimitStatus;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterFetchApp {
	private static Log log = LogFactory.getLog(TwitterFetchApp.class);
	private static final String END_OF_INPUT = "\\Z";

	private String searchUrl;
	private RankFinder rankFinder;
	private RankReader characterReader;

    public static void main( String[] args )
    {
		log.info("Starting.");
		TwitterFetchApp app = null;

		try {
			ClassPathXmlApplicationContext springContext = 
				new ClassPathXmlApplicationContext("spring.xml");
			
			app = (TwitterFetchApp) springContext.getBean(
					"twitterFetchApp", 
					TwitterFetchApp.class);

			app.fetchTweets();
			log.info("Success");
		} catch (Exception e) {
			String msg = "Failure";
			log.error(msg, e);
			System.exit(-1);
		}
    }
	
    public void fetchTweets() throws IOException, TwitterException
    {
    	String tweetIdsFilename = "output/tweets/tweetIds.txt";
    	HashSet<Long> fetchedTweetIds = readOldTweetIds(tweetIdsFilename);
    	
    	fetchTweets(fetchedTweetIds);
		
		writeTweetIds(fetchedTweetIds, tweetIdsFilename);
    }

	private HashSet<Long> readOldTweetIds(String tweetIdsFilename)
			throws FileNotFoundException, IOException {
		HashSet<Long> fetchedTweetIds = new HashSet<Long>();
    	FileInputStream stream = new FileInputStream(tweetIdsFilename);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    	try
    	{
	    	String line;
	    	do
	    	{
	    		line = reader.readLine();
	    		if (line != null)
	    		{
	    			fetchedTweetIds.add(Long.decode(line));
	    		}
	    	}while (line != null);
    	}
    	finally
    	{
    		reader.close();
    	}
		return fetchedTweetIds;
	}

	private void writeTweetIds(HashSet<Long> fetchedTweetIds,
			String tweetIdsFilename) throws FileNotFoundException {
		FileOutputStream outStream = new FileOutputStream(tweetIdsFilename);
    	PrintWriter printWriter = new PrintWriter(outStream);
		try 
		{
			for (Long tweetId: fetchedTweetIds)
			{
				printWriter.println(tweetId);
			}
		}
		finally
		{
			printWriter.close();
		}
	}

	private void fetchTweets(HashSet<Long> fetchedTweetIds)
			throws FactoryConfigurationError {
		OutputStreamWriter fileWriter;
    	XMLStreamWriter xmlWriter;
    	String encoding = "UTF-8";
		try {
			FileOutputStream outStream = new FileOutputStream(chooseFilename());
			fileWriter = new OutputStreamWriter(outStream, encoding);
			XMLOutputFactory factory = XMLOutputFactory.newInstance();
			xmlWriter = factory.createXMLStreamWriter(fileWriter);
			try
			{
				xmlWriter.writeStartDocument(encoding, "1.0");
				xmlWriter.writeStartElement("tweets");
		    	//"url":"http://twitter.com/kaifulee/status/4695878440"
		    	Pattern pattern = Pattern.compile("\"url\":\"[^\"]*/status/(\\d*)\"");
		    	Twitter twitter = new TwitterFactory().getInstance();
				characterReader.open();
				try
				{
					boolean isLimitReached = false;
					while ( ! isLimitReached)
					{
						String target = characterReader.nextItem();
						{
							isLimitReached = fetchTweetsForTarget(
									fetchedTweetIds, 
									xmlWriter,
									pattern, 
									twitter, 
									target);
						}
					}
				}finally
				{
					characterReader.close();
				}
		    	xmlWriter.writeEndElement();
			}
			finally
			{
				xmlWriter.close();
				fileWriter.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String chooseFilename() 
	{
		int i = 0;
		while (true)
		{
			String filename = "output/tweets/tweets" + i + ".xml";
			if ( ! new File(filename).exists())
			{
				return filename;
			}
			i++;
		}
	}

    // return true if the Twitter limit has been reached.
	private boolean fetchTweetsForTarget(
			HashSet<Long> fetchedTweetIds,
			XMLStreamWriter xmlWriter, 
			Pattern pattern, 
			Twitter twitter,
			String target) throws IOException, TwitterException,
			XMLStreamException 
	{
		RateLimitStatus limitStatus = twitter.getRateLimitStatus();
		int remainingHits = limitStatus.getRemainingHits();
		int totalResultCount = 0;
		for (int i = 0; i < 8 && remainingHits > 0; i++)
		{
			String result = getSearchPage(totalResultCount, target.charAt(0));
			Matcher matcher = pattern.matcher(result);
			int resultCount = 0;
			
			while (matcher.find() && remainingHits > 0) 
			{
				long statusId = Long.decode(matcher.group(1));
				if ( ! fetchedTweetIds.contains(statusId))
				{
					fetchedTweetIds.add(statusId);
					String text = twitter.showStatus(statusId).getText();
					xmlWriter.writeStartElement("tweet");
					xmlWriter.writeAttribute("id", Long.toString(statusId));
					//write id and maybe url
					xmlWriter.writeCharacters(text);
					xmlWriter.writeEndElement();
					//log.info("found status with rank " + rank + ": " + statusId + ": " + text);
					remainingHits--;
				}
				resultCount++;
			}
			totalResultCount += resultCount;
			if (resultCount == 0)
			{
				log.error("After totalResultCount " + totalResultCount + result);
			}
		}
		if (remainingHits == 0)
		{
			log.error(
					"Limit reached. Resets in " + 
					limitStatus.getSecondsUntilReset() + "s");
		}
		return remainingHits == 0;
	}

	private String getSearchPage(int start, char target) throws IOException
    {
		//http://www.javapractices.com/topic/TopicAction.do?Id=147
		URL url = new URL(
				searchUrl + "&start=" + start + 
				"&q=site:twitter.com%20inurl:status%20" + target);
		String result = null;
		URLConnection connection = null;
		connection = url.openConnection();
		Scanner scanner = new Scanner(connection.getInputStream());
		scanner.useDelimiter(END_OF_INPUT);
		result = scanner.next();
		return result;
    }
	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public void setRankFinder(RankFinder rankFinder) {
		this.rankFinder = rankFinder;
	}

	public RankFinder getRankFinder() {
		return rankFinder;
	}

	public RankReader getCharacterReader() {
		return characterReader;
	}

	public void setCharacterReader(RankReader characterReader) {
		this.characterReader = characterReader;
	}
	
}
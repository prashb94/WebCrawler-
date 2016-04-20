package edu.uci.ics.crawler4j.examples.basic;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Scanner;

public class BasicCrawlController
	{
  private static Logger logger = LoggerFactory.getLogger(BasicCrawlController.class);
  public static void main(String[] args) throws Exception
  	{
    if (args.length != 2) {
      logger.info("Needed parameters: ");
      logger.info("\t rootFolder (it will contain intermediate crawl data)");
      logger.info("\t numberOfCralwers (number of concurrent threads)");
      return;
    }
    String crawlStorageFolder = args[0];
    int numberOfCrawlers = Integer.parseInt(args[1]);
    CrawlConfig config = new CrawlConfig();
    config.setCrawlStorageFolder(crawlStorageFolder);
    config.setPolitenessDelay(10);
    config.setMaxDepthOfCrawling(1);
    config.setMaxPagesToFetch(5);
    config.setIncludeBinaryContentInCrawling(false);
    config.setResumableCrawling(false);
    PageFetcher pageFetcher = new PageFetcher(config);
    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
    CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
    controller.addSeed("https://www.l3s.de/");
   // controller.addSeed("http://www.ics.uci.edu/~lopes/");
   // controller.addSeed("http://www.ics.uci.edu/~welling/");
    controller.start(BasicCrawler.class, numberOfCrawlers);
    DBconnect connect = new DBconnect();
    connect.getData();
    Scanner scanInput = new Scanner(System.in);
    System.out.println("Enter the Word you want to search the Anchor Text for : \n");
    String searchWord = scanInput.nextLine();
    scanInput.close();
    connect.searchData(searchWord);

  }
}
package edu.uci.ics.crawler4j.examples.basic;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.Set;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class BasicCrawler extends WebCrawler
{
	DBconnect connect = new DBconnect();
  @Override
  public boolean shouldVisit(Page page, WebURL url)
  {
    String href = url.getURL().toLowerCase();
    return href.startsWith("https://www.l3s.de/");
  }
  @Override
  public void visit(Page page)
  {
	   String url = page.getWebURL().getURL();
	   System.out.println("URL: " + url);
      if (page.getParseData() instanceof HtmlParseData) {
              HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
               String text = htmlParseData.getText();
               String html = htmlParseData.getHtml();
              // String anchor = page.getWebURL().getAnchor();`
               String finalanchor = "";
              Set<WebURL> links = htmlParseData.getOutgoingUrls();
            Document doc;
            try {
				doc = Jsoup.connect(url).get();
				 Elements anchor = doc.select("a[href]");
		      		for (Element link : anchor) 
		      		{
		      			finalanchor += "\nhref : " + link.attr("href") + "   Text :" + link.text();
		      		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
              System.out.println("Text length: " + text.length());
              System.out.println("Html length: " + html.length());
             // System.out.println("Anchor Text: " + anchor);
              System.out.println("Number of outgoing links: " + links.size());
              connect.putData(url,finalanchor,html);	
      }  
  }
}


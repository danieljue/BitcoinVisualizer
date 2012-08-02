package bitcoinvisualizer;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main
{
	private static final java.util.logging.Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args)
	{		
		boolean hasError = false;
		
		while (!hasError)
		{
			// Print options
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar blockchainneo4j.jar", getOptions());
	
			// Parse options
			CommandLineParser parser = new GnuParser();
			try
			{
				// Get values
				CommandLine line = parser.parse(getOptions(), args);
				boolean validate = true;
				if (line.hasOption("validate"))
					validate = Boolean.parseBoolean(line.getOptionValue("validate"));
				if (!GraphBuilder.IsStarted())
				{
					GraphBuilder.StartDatabase(line.getOptionValue("path"));
				}
				
				// GraphBuilder.DownloadAndSaveBlockChain(validate);
				
				if (line.hasOption("high"))
				{
					//GraphBuilder.BuildHighLevelGraph();
				}
				// GraphBuilder.StopDatabase();
				LOG.info("Completed.");
			
				// If the user activated the scraper
				if (line.hasOption("scraper"))
				{
					//GraphBuilder.Scrape();
				}
			}				
	
			catch (ParseException e)
			{
				LOG.log(Level.SEVERE, "Parsing failed.  Reason: " + e.getMessage(), e);
				hasError = true;
			}
			
			// Sleep for 6 hours
			try
			{
				Thread.sleep(21600000);
			} 
			
			catch (InterruptedException e)
			{
				LOG.log(Level.SEVERE, "Thread sleep failed.  Reason: " + e.getMessage(), e);
			}
			
		}
	}
	

	/**
	 * Creates the options menu
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static Options getOptions()
	{
		// Define options
		Option dbPath = OptionBuilder.hasArg().withArgName("path").withDescription("The path to the neo4j graph.db directory. Ex: /home/user/Documents/neo4j/graph.db").isRequired().create("path");
		Option validate = OptionBuilder.hasArg().withArgName("true/false")
				.withDescription("Toggle the verifier which checks if the local json files form a complete blockchain.  Default: true.  Recommended.").create("validate");
		Option scraper = OptionBuilder.withArgName("scraper").withDescription("Runs the scraper which attempts to associate bitcoin addresses to real world entities.").create("scraper");
		Option high = OptionBuilder.withArgName("high").withDescription("Builds the high level data structure.").create("high");
		Options options = new Options();
		options.addOption(dbPath);
		options.addOption(validate);
		options.addOption(scraper);
		options.addOption(high);
		return options;
	}
}

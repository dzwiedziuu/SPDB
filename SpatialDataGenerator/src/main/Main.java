package main;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main
{
	private static final String X_MAX = "xm";
	private static final String Y_MAX = "ym";
	private static final String N = "n";
	private static final String OUTPUT_FILE = "of";

	public static void main(String[] args) throws IOException
	{
		Options options = createOptions();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter helpFormatter = new HelpFormatter();
		try
		{
			CommandLine commandLine = parser.parse(options, args);
			int n = Integer.parseInt(commandLine.getOptionValue(N));
			int xmax = Integer.parseInt(commandLine.getOptionValue(X_MAX));
			int ymax = Integer.parseInt(commandLine.getOptionValue(Y_MAX));
			String oFile = commandLine.getOptionValue(OUTPUT_FILE);
			new Generator().generate(oFile, n, xmax, ymax);
		} catch (ParseException e)
		{
			System.out.println(e.getMessage());
			helpFormatter.printHelp("GraphGenerator", options);
		}
	}

	private static Options createOptions()
	{
		Options result = new Options();
		result.addOption(createOption(OUTPUT_FILE, "vertex_number", true, "File where to store ponts"));
		result.addOption(createOption(N, "probability", true, "Number of points"));
		result.addOption(createOption(Y_MAX, "output_direcroty", true, "maximum Y dimension"));
		result.addOption(createOption(X_MAX, "output_file", true, "maximum X dimension"));
		return result;
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description)
	{
		return createOption(opt, longOpt, hasArg, description, true);
	}

	private static Option createOption(String opt, String longOpt, boolean hasArg, String description, boolean isRequired)
	{
		Option option = new Option(opt, longOpt, hasArg, description);
		option.setRequired(isRequired);
		return option;
	}
}

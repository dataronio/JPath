package to.us.harha.jpath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import to.us.harha.jpath.util.Logger;

public class Config
{

	public static final String CONFIG_PATH = "./res/jpath.cfg";
	public static final String MODEL_PATH  = "./res/models/";

	// Display width
	public static int          window_width;
	// Display height
	public static int          window_height;
	// Display pixel scale
	public static int          window_scale;
	// Show debug information or not at startup?
	public static boolean      debug_enabled;
	// Save the rendered image and close the program after max_samples_per_pixel has been reached?
	public static boolean      saving_enabled;
	// Set maximum recursion limit for the path tracing function
	public static int          max_recursion;
	// If this limit is exceeded and saving_enabled is true, then the program is closed and display's image buffer is saved
	public static int          max_samples_per_pixel;
	// Multithreading enabled?
	public static boolean      mt_enabled;
	// Thread amount if mt_enabled is true
	public static int          mt_amount;
	// Supersampling per pixel enabled?
	public static boolean      ss_enabled;
	// How many samples per pixel?
	public static int          ss_amount;
	// Amplitued of the random jitter per supersampled ray
	public static float        ss_jitter;

	// Config's global Logger object
	public static final Logger LOG         = new Logger(Config.class.getName());

	private Config()
	{

	}

	public static void initConfig()
	{
		File f = new File(CONFIG_PATH);
		if (f.exists() && !f.isDirectory())
		{
			loadConfig();
		} else
		{
			createConfig();
			initConfig();
		}
	}

	public static void createConfig()
	{
		Properties p = new Properties();
		OutputStream o = null;
		try
		{
			o = new FileOutputStream(CONFIG_PATH);

			// Set each variable
			p.setProperty("window_width", "128");
			p.setProperty("window_height", "128");
			p.setProperty("window_scale", "4");
			p.setProperty("debug_enabled", "true");
			p.setProperty("saving_enabled", "false");
			p.setProperty("max_recursion", "4");
			p.setProperty("max_samples_per_pixel", "1000");
			p.setProperty("mt_enabled", "true");
			p.setProperty("mt_amount", "-1");
			p.setProperty("ss_enabled", "true");
			p.setProperty("ss_amount", "4");
			p.setProperty("ss_jitter", "0.0025f");

			// Store the variables
			p.store(o, null);

			// Close the outputstream object
			o.close();

			LOG.printMsg(CONFIG_PATH + " Created succesfully!");
		} catch (IOException e)
		{
			LOG.printErr("Couldn't create the main configuration file, closing program...");
			System.exit(1);
		}
	}

	public static void loadConfig()
	{
		Properties p = new Properties();
		InputStream i = null;
		try
		{
			i = new FileInputStream(CONFIG_PATH);

			// Load the file
			p.load(i);

			// Get the properties and set the config variables
			window_width = Integer.valueOf(p.getProperty("window_width"));
			window_height = Integer.valueOf(p.getProperty("window_height"));
			window_scale = Integer.valueOf(p.getProperty("window_scale"));
			debug_enabled = Boolean.valueOf(p.getProperty("debug_enabled"));
			saving_enabled = Boolean.valueOf(p.getProperty("saving_enabled"));
			max_recursion = Integer.valueOf(p.getProperty("max_recursion"));
			max_samples_per_pixel = Integer.valueOf(p.getProperty("max_samples_per_pixel"));
			mt_enabled = Boolean.valueOf(p.getProperty("mt_enabled"));
			mt_amount = Integer.valueOf(p.getProperty("mt_amount"));
			ss_enabled = Boolean.valueOf(p.getProperty("ss_enabled"));
			ss_amount = Integer.valueOf(p.getProperty("ss_amount"));
			ss_jitter = Float.valueOf(p.getProperty("ss_jitter"));

			// Close the inputstream object
			i.close();

			LOG.printMsg(CONFIG_PATH + " loaded succesfully!");
		} catch (IOException e)
		{
			LOG.printErr("Couldn't load the main configuration file, closing program...");
			System.exit(1);
		}
	}

}

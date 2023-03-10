package pgDev.bukkit.CloneCover;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import pgDev.bukkit.DisguiseCraft.DisguiseCraft;
import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;

public class CloneCover extends JavaPlugin {
	// File Locations
    static String pluginMainDir = "./plugins/CloneCover";
    static String pluginConfigLocation = pluginMainDir + "/CloneCover.cfg";
    
    // DisguiseCraft API
    DisguiseCraftAPI dcAPI;
    
    // Listeners
    CCMainListener mainListener = new CCMainListener(this);
    
    // Plugin Configuration
    CCConfig pluginSettings;
    
    public void onEnable() {
    	// Check for the plugin directory (create if it does not exist)
    	File pluginDir = new File(pluginMainDir);
		if(!pluginDir.exists()) {
			boolean dirCreation = pluginDir.mkdirs();
			if (dirCreation) {
				System.out.println("New CloneCover directory created!");
			}
		}
		
		// Load the Configuration
    	try {
        	Properties preSettings = new Properties();
        	if ((new File(pluginConfigLocation)).exists()) {
        		preSettings.load(new FileInputStream(new File(pluginConfigLocation)));
        		pluginSettings = new CCConfig(preSettings, this);
        		if (!pluginSettings.upToDate) {
        			pluginSettings.createConfig();
        			System.out.println("CloneCover Configuration updated!");
        		}
        	} else {
        		pluginSettings = new CCConfig(preSettings, this);
        		pluginSettings.createConfig();
        		System.out.println("CloneCover Configuration created!");
        	}
        } catch (Exception e) {
        	System.out.println("Could not load CloneCover configuration! " + e);
        }
		
        // Register our events
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(mainListener, this);
		
		// Integrations!
        setupDisguiseCraft();
		
		// Output to console
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }
    
    public void onDisable() {
    	System.out.println("CloneCover disabled!");
    }
    
    // DisguiseCraft API Setup
    private void setupDisguiseCraft() {
    	dcAPI = DisguiseCraft.getAPI();
    }
}

package pgDev.bukkit.CloneCover;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Properties;

public class CCConfig {
	private Properties properties;
	private final CloneCover plugin;
	public boolean upToDate = true;
	
	// List of Config Options
	String disguiseTo;
	String cancelCommands;
	String disguiseNotification;
	
	public CCConfig(Properties p, final CloneCover plugin) {
		properties = p;
		this.plugin = plugin;
		
		// Grab values here
		disguiseTo = getString("disguiseTo", "Herobrine");
		cancelCommands = getString("cancelCommands","/done,/lol");
		disguiseNotification = getString("disguiseNotification", "none");
	}
	
	// Value obtaining functions down below
	public int getInt(String label, int thedefault) {
		String value;
        try {
        	value = getString(label);
        	return Integer.parseInt(value);
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public double getDouble(String label) throws NoSuchElementException {
        String value = getString(label);
        return Double.parseDouble(value);
    }
    
    public File getFile(String label) throws NoSuchElementException {
        String value = getString(label);
        return new File(value);
    }

    public boolean getBoolean(String label, boolean thedefault) {
    	String values;
        try {
        	values = getString(label);
        	return Boolean.valueOf(values).booleanValue();
        } catch (NoSuchElementException e) {
        	return thedefault;
        }
    }
    
    public Color getColor(String label) {
        String value = getString(label);
        Color color = Color.decode(value);
        return color;
    }
    
    public HashSet<String> getSet(String label, String thedefault) {
        String values;
        try {
        	values = getString(label);
        } catch (NoSuchElementException e) {
        	values = thedefault;
        }
        String[] tokens = values.split(",");
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < tokens.length; i++) {
            set.add(tokens[i].trim().toLowerCase());
        }
        return set;
    }
    
    public LinkedList<String> getList(String label, String thedefault) {
    	String values;
        try {
        	values = getString(label);
        } catch (NoSuchElementException e) {
        	values = thedefault;
        }
        if(!values.equals("")) {
            String[] tokens = values.split(",");
            LinkedList<String> set = new LinkedList<String>();
            for (int i = 0; i < tokens.length; i++) {
                set.add(tokens[i].trim().toLowerCase());
            }
            return set;
        }else {
        	return new LinkedList<String>();
        }
    }
    
    public String getString(String label) throws NoSuchElementException {
        String value = properties.getProperty(label);
        if (value == null) {
        	upToDate = false;
            throw new NoSuchElementException("Config did not contain: " + label);
        }
        return value;
    }
    
    public String getString(String label, String thedefault) {
    	String value;
    	try {
        	value = getString(label);
        } catch (NoSuchElementException e) {
        	value = thedefault;
        }
        return value;
    }
    
    public String linkedListToString(LinkedList<String> list) {
    	if(list.size() > 0) {
    		String compounded = "";
    		boolean first = true;
        	for (String value : list) {
        		if (first) {
        			compounded = value;
        			first = false;
        		} else {
        			compounded = compounded + "," + value;
        		}
        	}
        	return compounded;
    	}
    	return "";
    }
    
    
    // Config creation method
    public void createConfig() {
    	try {
    		@SuppressWarnings("static-access")
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(plugin.pluginConfigLocation)));
    		out.write("#\r\n");
    		out.write("# CloneCover Configuration\r\n");
    		out.write("#\r\n");
    		out.write("\r\n");
    		out.write("# Universal Disguise\r\n");
    		out.write("#	This is the player name of the disguise\r\n");
    		out.write("#	you wish every player to take up upon\r\n");
    		out.write("#	joining the server.\r\n");
    		out.write("disguiseTo=" + disguiseTo + "\r\n");
    		out.write("\r\n");
    		out.write("# Disguise Turn-Off\r\n");
    		out.write("#	Here you list the chat sequence (separated\r\n");
    		out.write("#	by comma) through which players can\r\n");
    		out.write("#	undisguise themselves.\r\n");
    		out.write("cancelCommands=" + cancelCommands + "\r\n");
    		out.write("\r\n");
    		out.write("# Disguise Notification\r\n");
    		out.write("#	This is the output players will see if\r\n");
    		out.write("#	they are disguised upon joining.\r\n");
    		out.write("#	To disable this, use: none\r\n");
    		out.write("disguiseNotification=" + disguiseNotification + "\r\n");
    		out.close();
    	} catch (Exception e) {
    		System.out.println(e);
    		// Not sure what to do? O.o
    	}
    }
}

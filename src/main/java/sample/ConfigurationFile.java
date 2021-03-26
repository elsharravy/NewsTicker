package sample;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationFile {

    private Map<String , Integer> configuration = new HashMap<String, Integer>();
    private String filePath;

    public ConfigurationFile( String filePath )
    {
        this.filePath = filePath;
  //      loadSettingsFromFile(  );
    }

    public void loadSettingsFromFile(  )
    {
            String[] keyValuePair;
            String line = "";
        clearConfiguration();
        try {
  //          System.out.println(filePath);
            BufferedReader br = new BufferedReader( new FileReader(filePath) );
            line = br.readLine();
            while(line != null) {
                keyValuePair = line.split("=");
                if (keyValuePair.length != 2) {
                    System.out.println("Incorrect configuration file format: " + filePath);
                }
                configuration.put( keyValuePair[0] ,  Integer.valueOf( keyValuePair[1] ) );
                line = br.readLine();
            }
            br.close();                                                                  // TODO FINALLY BLOCK
            System.out.println("Configuration file successful initialization: " + filePath);
        }
        catch ( FileNotFoundException e)
        {
            System.out.println("Configuration file not found: " + filePath);
        } catch (IOException e) {
            System.out.println("IOException in configuration file while loading settings: " + filePath);
        }
    }

    public void saveSettingsToFile()
    {
        try {
            BufferedWriter bw = new BufferedWriter( new FileWriter(filePath) );
            String line;
            for( Map.Entry<String, Integer> config : configuration.entrySet()  )
            {
                line = "";
                line += (config.getKey() + "=" + config.getValue().toString() + System.lineSeparator());
                bw.write( line );
            }
            bw.close();
        }
        catch ( IOException e )
        {
            System.out.println("IOException in configuration file while saving settings: " + filePath);
        }

    }

    // return old value
    public Integer loadKeyValue(String key, boolean newValue)
    {
        if(newValue)
        {
            return configuration.put(key, 1);
        }
        else
        {
            return configuration.put(key, 0);
        }
    }

    // return old value
    public Integer loadKeyValue(String key, Integer newValue)
    {
            return configuration.put(key, newValue);
    }

    private void clearConfiguration()
    {
        configuration.clear();
    }

    public Map<String, Integer> getConfiguration() {
        return configuration;
    }
}




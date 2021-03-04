package utils;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;


public class ConfigProperties {
	
	private static final String fs = File.separator;
	private static final String CONFIG_FILE =  "."+fs+"src"+fs+"test"+fs+"resources"+fs+"config.txt";
	private static final String DATA_FOLDER =  "."+fs+"src"+fs+"test"+fs+"resources"+fs+"dataFiles"+fs;
	private static final String DEFAULT_DATA_TABLE_FOLDER =  "."+fs+"src"+fs+"test"+fs+"resources"+fs+"defaultData"+fs;
	private static final String DEFAULT_CONFIG_FILE =  "."+fs+"src"+fs+"test"+fs+"resources"+fs+"defaultData"+fs+"config.txt";
	
	/**
	 * Obtiene la ruta del data table a usar en TEST_A, TEST_B, etc. 
	 * @param Nombre de la propiedad que contiene el data table deseado en el archivo config.txt. 
	 * @return path value 
	 * @throws IOException 
	 */
	public static String getPathofFile(String dataFilePropertyName) throws IOException {
		String propertyValue = getPropertyValue(dataFilePropertyName);
		String file_path = DATA_FOLDER + propertyValue;
		
		File data_file = new File(file_path);
		boolean exists = data_file.exists();
		 if (!exists) 
		    {
		    /*
		     * 
		     * Copia los data tables demos del deafultData folder (test_file_A.xlsx y test_file_B.xlsx) 
		     * en el path de resources/dataFiles, donde el config path busca el data table por default.
		     * 
		     */
			 
		 	 String test_file_a_path = DATA_FOLDER + "test_file_A.xlsx";
			 String test_file_b_path = DATA_FOLDER + "test_file_B.xlsx";
			 String test_file_c_path = DATA_FOLDER + "test_file_C.xlsx";
			 
			 System.out.println("No se encuentra el data table seleccionado.");
			 System.out.println("Validar el data table solicitado en el config file");
			 System.out.println("Copiando data tables originales al directorio -> " + DATA_FOLDER );			 
		
			 File data_table_a = new File(test_file_a_path);
			 File datatable_default_A = new File (DEFAULT_DATA_TABLE_FOLDER + "test_file_A.xlsx");
			 FileUtils.copyFile(datatable_default_A, data_table_a);
			 
			 
			 File data_table_b = new File(test_file_b_path);
			 File datatable_default_B = new File (DEFAULT_DATA_TABLE_FOLDER + "test_file_B.xlsx");
			 FileUtils.copyFile(datatable_default_B, data_table_b);
			 
			 File data_table_C = new File(test_file_c_path);
			 File datatable_default_C = new File (DEFAULT_DATA_TABLE_FOLDER + "test_file_C.xlsx");
			 FileUtils.copyFile(datatable_default_C, data_table_C);
			 
			 /*Copia el deafult data table A en la carpeta con el nombre del data table solicitado que no existe*/
			 FileUtils.copyFile(datatable_default_A, data_file);
			
		    }
		return file_path;
	}
	
	/***
	 * Get value parameter from property name
	 * @param propertyName
	 * @return Property Value
	 * @throws IOException
	 */

	public static String  getPropertyValue(String vpPropertyName) throws IOException 
	{
		String strPath = CONFIG_FILE;
		File tmpDir = new File(strPath);
	    boolean exists = tmpDir.exists();
	    //si el config no existe, copiar el config default
	    if (!exists) 
	    {
	    	System.out.println("No existe el el archivo config.txt en " + strPath );
	    	System.out.println("Creando nuevo config.txt");
	    	File default_config_file = new File(DEFAULT_CONFIG_FILE);
	    	FileUtils.copyFile(default_config_file, tmpDir);
	    }
		
		String strPropertyValue = null;
		FileReader inputStream = new FileReader(new java.io.File(strPath));
		
		try {
			//Se verifica que existe el archivo en la ruta
			if (inputStream != null)
			{
				//Se cargan las propiedades
	        	Properties properties = new Properties();
	        	properties.load(inputStream);
	        	// Obtiene el valor de la propiedad
		        strPropertyValue = properties.getProperty(vpPropertyName);
		        }
		 }
		 finally{
			 inputStream.close();
		 }
	        //retorno del valor
			//System.out.println("Get():" + strPropertyValue);
	        return strPropertyValue;
	    }
		
}

/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Use SimpleMasContextFile if you don't have the context file as an Eclipse IResource.
 * If you do have it, use MasContextFile.
 * 
 */
public class SimpleMasContextFile implements IMasContextFile {

    private final Properties properties;
    private final String syntaxFile;
    private final String semanticFile;
    private final String contextFileLocation;

    public SimpleMasContextFile(String contextFileLocation) throws FileNotFoundException, IOException {
        this.contextFileLocation = contextFileLocation;
    	File file = new File(contextFileLocation);
    	String pathToContextFile = file.getParent();    	
    	
    	properties = new Properties();
    	properties.load(new FileInputStream(file));
    	
    	syntaxFile = pathToContextFile.concat(File.separator + (String) properties.get("syntax") );
        semanticFile = pathToContextFile.concat(File.separator + (String) properties.get("semantic") );
    }
    
    public SimpleMasContextFile(String pathToContextFile, String contextFile) throws FileNotFoundException, IOException {
    	this(pathToContextFile.concat(contextFile));        
    }

    public String getJavaPackagePrefixOfSyntaxModel() {
        return (String) properties.getProperty("syntax_package_prefix");
    }

    public String getNsPrefixOfSyntaxXmi() {
        return (String) properties.getProperty("syntax_namespace_prefix");
    }

    public String getPackageOfSyntaxModel() {
        return (String) properties.getProperty("syntax_package");
    }
    
    public String getMasFile() {
        return semanticFile;
    }

    public String getSyntaxFile() {
        return syntaxFile;
    }

    public String getLocation() {
        return contextFileLocation;
    }

}
/*
 * www.javagl.de - ND - Multidimensional primitive data structures
 *
 * Copyright (c) 2013-2015 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.nd.gen;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STRawGroupDir;
import org.stringtemplate.v4.misc.ErrorManager;

/**
 * The generator class for the ND classes, using StringTemplate
 */
@SuppressWarnings("javadoc")
public class NDGen
{
    private static STRawGroupDir rawGroupDir;

    private static final Type doubleType = new Type("d", "double", "Double",
        "Double.NEGATIVE_INFINITY", "0.0", "Double.POSITIVE_INFINITY", "%f");
    private static final Type intType = new Type("i", "int", "Integer",
        "Integer.MIN_VALUE", "0", "Integer.MAX_VALUE", "%d");
    private static final Type longType = new Type("j", "long", "Long",
        "Long.MIN_VALUE", "0", "Long.MAX_VALUE", "%d");
    
    public static void main(String[] args) throws IOException
    {
        rawGroupDir = new STRawGroupDir("src/main/resources");
        rawGroupDir.delimiterStartChar = '$';
        rawGroupDir.delimiterStopChar = '$';
        
        generateTuples();
        generateArrays();
    }

    private static void generateTuples() throws IOException
    {
        generateTuples(doubleType);
        generateTuples(longType);
        generateTuples(intType);
    }
    
    private static void generateTuples(Type type) throws IOException
    {
        generate("tuples", "Abstract", type, "Tuple");
        generate("tuples", "AbstractMutable", type, "Tuple");
        generate("tuples", "Array", type, "Tuple");
        generate("tuples", "Buffer", type, "Tuple");
        generate("tuples", "Constant", type, "Tuple");
        generate("tuples", "Default", type, "Tuple");
        generate("tuples", "", type, "Tuple");
        generate("tuples", "Mutable", type, "Tuple");
        generate("tuples", "MutableSub", type, "Tuple");
        generate("tuples", "Sub", type, "Tuple");
        
        generate("tuples", "", type, "TupleCollections");
        generate("tuples", "", type, "TupleFunctions");
        generate("tuples", "", type, "Tuples");
        generate("tuples", "", type, "TupleSpliterator");
        generate("tuples", "", type, "TupleStreams");
        
    }
    
    private static void generateArrays() throws IOException
    {
        generateArrays(doubleType);
        generateArrays(longType);
        generateArrays(intType);
    }
    
    private static void generateArrays(Type type) throws IOException
    {
        generate("arrays", "Abstract", type, "ArrayND");
        generate("arrays", "AbstractMutable", type, "ArrayND");
        generate("arrays", "Array", type, "Array2D");
        generate("arrays", "Default", type, "ArrayND");
        generate("arrays", "", type, "ArrayND");
        generate("arrays", "Mutable", type, "ArrayND");
        generate("arrays", "MutableSub", type, "ArrayND");
        generate("arrays", "Sub", type, "ArrayND");

        generate("arrays", "MutableTuple", type, "ArrayND");
        generate("arrays", "Tuple", type, "ArrayND");
        
        generate("arrays", "", type, "ArraysND");
        generate("arrays", "", type, "ArrayFunctionsND");
    }
    

    private static void generate(String subdir, 
        String prefix, Type type, String suffix) throws IOException
    {
        File outputDirectory = new File(
            "src/generated/de/javagl/nd/"+subdir+"/"+type.getLetter());
        outputDirectory.mkdirs();

        ST st = rawGroupDir.getInstanceOf(subdir+"/"+prefix+"Type"+suffix);
        st.add("type", type);
        
        String outputFileName = 
            prefix + type.getUppercaseName() + suffix + ".java";
        
        // Apologies, Terence... 
        String customMethods = 
            readFile("./src/main/resources/customMethods_"+outputFileName);
        st.add("customMethods", customMethods);

        String customImports = 
            readFile("./src/main/resources/customImports_"+outputFileName);
        st.add("customImports", customImports);
        
        File outputFile = 
            new File(outputDirectory, outputFileName);
        st.write(outputFile, ErrorManager.DEFAULT_ERROR_LISTENER);

        System.out.println("Created " + outputFile);
    }
    
    private static String readFile(String fileName)
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            return "";
        }
        try (Scanner scanner = new Scanner(file))
        {
            String content = scanner.useDelimiter("\\Z").next();
            return content;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    
}

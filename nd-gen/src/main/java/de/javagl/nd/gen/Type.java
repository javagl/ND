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

/**
 * Class representing a "type" in the code generation using StringTemplate
 */
@SuppressWarnings("javadoc")
class Type 
{
    private final String letter;
    private final String name;
    private final String uppercaseName;
    private final String min;
    private final String zero;
    private final String max;
    private final String boxedName;
    private final String defaultFormatString;
    
    Type(String letter, String name, String boxedName, 
        String min, String zero, String max, String defaultFormatString) 
    {
        this.letter = letter;
        this.name = name;
        this.uppercaseName =
            Character.toUpperCase(name.charAt(0)) + name.substring(1);
        this.boxedName = boxedName;
        this.min = min;
        this.zero = zero;
        this.max = max;
        this.defaultFormatString = defaultFormatString;
    }

    public String getLetter()
    {
        return letter;
    }

    public String getName()
    {
        return name;
    }

    public String getUppercaseName()
    {
        return uppercaseName;
    }
    
    public String getBoxedName()
    {
        return boxedName;
    }
    
    public String getMin()
    {
        return min;
    }
    
    public String getZero()
    {
        return zero;
    }
    
    public String getMax()
    {
        return max;
    }
    
    public String getDefaultFormatString()
    {
        return defaultFormatString;
    }
}
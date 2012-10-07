/*******************************************************************************
 * Copyright 2011 Patrick McMorran
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package datagenerator.gui;
// I got this class off of the Internet at the address:
// http://stackoverflow.com/questions/342990/create-java-console-inside-the-panel/343007#343007
// I customized it to work as a console output window.
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 * This class I got off of a website called stack overflow, so that the program can give more feedback to
 * the user as generation progresses. It has been customized some from the original on the website.
 * http://stackoverflow.com/questions/342990/create-java-console-inside-the-panel/343007#343007
 * I did write the JavaDocs for the class explaining
 * @author Software Monkey
 * softwaremonkey.org
 * @author Patrick McMorran - Author of JavaDoc.
 */
public class TextAreaOutputStream extends OutputStream
{

// *****************************************************************************
// INSTANCE PROPERTIES
// *****************************************************************************

private JTextArea                       textArea;                               // target text area
private int                             maxLines;                               // maximum lines allowed in text area
@SuppressWarnings("unchecked")
private LinkedList                      lineLengths;                            // length of lines within text area
private int                             curLength;                              // length of current line
private byte[]                          oneByte;                                // array for write(int val);

// *****************************************************************************
// INSTANCE CONSTRUCTORS/INIT/CLOSE/FINALIZE
// *****************************************************************************
/**
 * This is the default constructor for creating the TextAreaOutputStream.
 * @param ta This is the JTextArea the the output stream should write to.
 */
public TextAreaOutputStream(JTextArea ta) {
    this(ta,1000);
    }

/**
 * This is a constructor for creating the TextAreaOutputStream.
 * @param ta This is the JTextArea the the output stream should write to.
 * @param ml This is an integer representing the maximum number of lines kept in the textarea.
 * This should be be over 0 lines to function correctly.
 */
@SuppressWarnings("unchecked")
public TextAreaOutputStream(JTextArea ta, int ml) {
    //if(ml<1) { throw new IoEscape(IoEscape.GENERAL,"Maximum lines of "+ml+" in TextAreaOutputStream constructor is not permitted"); }
    textArea=ta;
    maxLines=ml;
    lineLengths=new LinkedList();
    curLength=0;
    oneByte=new byte[1];
    }

// *****************************************************************************
// INSTANCE METHODS - ACCESSORS
// *****************************************************************************

/**
 * This method is used to empty the textarea.
 */
@SuppressWarnings("unchecked")
public synchronized void clear() {
    lineLengths=new LinkedList();
    curLength=0;
    textArea.setText("");
    }

/** Get the number of lines this TextArea will hold. */
public synchronized int getMaximumLines() { return maxLines; }

/** Set the number of lines this TextArea will hold. */
public synchronized void setMaximumLines(int val) { maxLines=val; }

// *****************************************************************************
// INSTANCE METHODS
// *****************************************************************************

/**
 * This method cleans up the resources used by the TextAreaOutputStream
 */
public void close() {
    if(textArea!=null) {
        textArea=null;
        lineLengths=null;
        oneByte=null;
        }
    }

/**
 * This method is unused.
 */
public void flush() {
    }

/**
 * This method writes an integer value to the TextArea.
 */
public void write(int val) {
    oneByte[0]=(byte)val;
    write(oneByte,0,1);
    }

/**
 * This method writes a byte array to the TextArea
 */
public void write(byte[] ba) {
    write(ba,0,ba.length);
    }


@SuppressWarnings("unchecked")
public synchronized void write(byte[] ba,int str,int len) {
    try {
        curLength+=len;
        if(bytesEndWith(ba,str,len,LINE_SEP)) {
            lineLengths.addLast(new Integer(curLength));
            curLength=0;
            if(lineLengths.size()>maxLines) {
                textArea.replaceRange(null,0,((Integer)lineLengths.removeFirst()).intValue());
                }
            }
        for(int xa=0; xa<10; xa++) {
            try { textArea.append(new String(ba,str,len)); break; }
            catch(Throwable thr) {                                                 // sometimes throws a java.lang.Error: Interrupted attempt to aquire write lock
                if(xa==9) { thr.printStackTrace(); }
                else      { Thread.sleep(200);    }
                }
            }
        }
    catch(Throwable thr) {
        CharArrayWriter caw=new CharArrayWriter();
        thr.printStackTrace(new PrintWriter(caw,true));
        textArea.append(System.getProperty("line.separator","\n"));
        textArea.append(caw.toString());
        }
    textArea.setCaretPosition(textArea.getDocument().getLength());
    }

private boolean bytesEndWith(byte[] ba, int str, int len, byte[] ew) {
    if(len<LINE_SEP.length) { return false; }
    for(int xa=0,xb=(str+len-LINE_SEP.length); xa<LINE_SEP.length; xa++,xb++) {
        if(LINE_SEP[xa]!=ba[xb]) { return false; }
        }
    return true;
    }

// *****************************************************************************
// STATIC PROPERTIES
// *****************************************************************************

static private byte[]                   LINE_SEP=System.getProperty("line.separator","\n").getBytes();

} /* END PUBLIC CLASS */

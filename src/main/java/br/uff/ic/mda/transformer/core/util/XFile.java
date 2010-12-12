package br.uff.ic.mda.transformer.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 *
 * @author Daniel
 */
public class XFile {



    private FileInputStream inputStream;
    private InputStreamReader streamReader;
    private BufferedReader reader;
    private String name;
    private FileOutputStream fileOut;
    private OutputStreamWriter output;
    private BufferedWriter writer;
    private boolean open = false;
    private boolean closed = false;

    /**
     *
     * @param name
     */
    public XFile(String name) {
        this.name = name;
    }

    /**
     *
     * @param persistPath
     * @param sb
     */
    public static void writeFile(String persistPath, StringBuilder sb) {
        XFile xf = new XFile(persistPath);
        xf.substituteFor(sb);
    }
    
    private void openFile() {
        try {
            inputStream = new FileInputStream(this.name);
            streamReader = new InputStreamReader(inputStream, Charset.forName("ISO-8859-1"));
            reader = new BufferedReader(streamReader);
            open = true;
            closed = false;
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }
    }

    private void closeFile() {
        try {
            reader.close();
            streamReader.close();
            inputStream.close();
            open = false;
            closed = true;
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }
    }

    private BufferedWriter createOutput(boolean append) {
        File file = new File(name);
        if (this.name.contains("/")) {
            File f = new File(this.name.substring(0, this.name.lastIndexOf("/")));
            f.mkdirs();
        }
        try {
            fileOut = new FileOutputStream(file, append);
            output = new OutputStreamWriter(fileOut);
            writer = new BufferedWriter(output);
            open = true;
            append = true;
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }
        return writer;

    }

    private BufferedWriter createOutputFile() {
        return createOutput(false);
    }

    /**
     *
     */
    public void closeOutputFile() {
        try {
            if (!open) {
                return;
            }
            writer.close();
            open = false;
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }
    }

    /**
     *
     * @param string
     */
    public void append(String string) {
        try {
            this.createOutput(true);
            writer.write(string);
            writer.newLine();
            this.closeOutputFile();
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }

    }

    /**
     *
     * @param sb
     */
    public void substituteFor(StringBuffer sb) {
        try {
            this.createOutput(false);
            writer.write(sb.toString());
            writer.newLine();
            this.closeOutputFile();
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }

    }
    
    /**
     *
     * @param sb
     */
    public void substituteFor(StringBuilder sb) {
        try {
            this.createOutput(false);
            writer.write(sb.toString());
            writer.newLine();
            this.closeOutputFile();
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }

    }

    /**
     *
     * @param linha
     */
    public void writeLine(String linha) {
        try {
            if (!open) {
                this.createOutputFile();
            }
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }
    }

    /**
     *
     */
    public void deleteFile() {
        if (!new File(name).delete()) {
            System.err.println("[ERROR] 'Impossible delete file \"" + name + "\"' in class '" + this.getClass().getName() + "'");
        }
    }

    /**
     *
     * @return
     */
    public String readLine() {
        if (!open) {
            openFile();
        }
        String temp = null;
        try {
            temp = reader.readLine();
        } catch (IOException e) {
            System.err.println("[ERROR] '" + e.getMessage() + "' in class '" + this.getClass().getName() + "'");
        }
        if ((temp == null) && !closed) {
            closeFile();
        }
        return temp;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }
}

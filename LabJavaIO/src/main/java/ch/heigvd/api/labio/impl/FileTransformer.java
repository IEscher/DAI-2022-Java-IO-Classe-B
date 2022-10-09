package ch.heigvd.api.labio.impl;

import ch.heigvd.api.labio.impl.transformers.CombinedTransformer;
import ch.heigvd.api.labio.impl.transformers.LineNumberingCharTransformer;
import ch.heigvd.api.labio.impl.transformers.NoOpCharTransformer;
import ch.heigvd.api.labio.impl.transformers.UpperCaseCharTransformer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class transforms files. The transform method receives an inputFile.
 * It writes a copy of the input file to an output file, but applies a
 * character transformer before writing each character.
 *
 * @author Juergen Ehrensberger
 */
public class FileTransformer {
  private static final Logger LOG = Logger.getLogger(FileTransformer.class.getName());

  public void transform(File inputFile) {
    /*
     * This method opens the given inputFile and copies the
     * content to an output file.
     * The output file has a file name <inputFile-Name>.out, for example:
     *   quote-2.utf --> quote-2.utf.out
     * Both files must be opened (read or write) with encoding "UTF-8".
     * Before writing each character to the output file, the transformer calls
     * a character transformer to transform the character before writing it to the output.
     */

    /* TODO: first start with the NoOpCharTransformer which does nothing.
     *  Later, replace it by a combination of the UpperCaseCharTransformer
     *  and the LineNumberCharTransformer.
     */
    //NoOpCharTransformer transformer = new NoOpCharTransformer();
    UpperCaseCharTransformer transformer1 = new UpperCaseCharTransformer();
    LineNumberingCharTransformer transformer2 = new LineNumberingCharTransformer();
    CombinedTransformer transformer = new CombinedTransformer();

    /* TODO: implement the following logic here:
     *  - open the inputFile and an outputFile
     *    Use UTF-8 encoding for both.
     *    Filename of the output file: <inputFile-Name>.out (that is add ".out" at the end)
     *  - Copy all characters from the input file to the output file.
     *  - For each character, apply a transformation: start with NoOpCharTransformer,
     *    then later replace it with a combination of UpperCaseFCharTransformer and LineNumberCharTransformer.
     */
    try {
      java.nio.charset.Charset encoding = java.nio.charset.StandardCharsets.UTF_8;
      FileReader fileReader = new FileReader(inputFile, encoding);
      FileWriter fileWriter = new FileWriter(inputFile + ".out", encoding);

      String contentTransformed = "";

      /* COPYING CONTENT */
      int c;
      while ((c = fileReader.read()) != -1) {
        //fileWriter.write((char)c);
        contentTransformed += transformer.transform(Character.toString(c));
        //contentTransformed += transformer1.transform(transformer2.transform(Character.toString(c)));
        //fileWriter.write(transformer.transform(Character.toString(c)));

      }

      FileOutputStream fos = new FileOutputStream(inputFile + ".out");
      fos.write(contentTransformed.getBytes(encoding));
      fos.flush();
      fos.close();
      /* APPLYING TRANSFORMATION */

    } catch (Exception ex) {
      LOG.log(Level.SEVERE, "Error while reading, writing or transforming file.", ex);
    }
  }
}
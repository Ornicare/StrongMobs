package cconsole;
import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;


/**
 * Used to intercept System.out
 * 
 * @author Ornicare
 *
 */
public class CConsoleOut extends CConsoleInterceptor{
	
	public CConsoleOut(PrintStream originalPrintStream, CConsoleGUI console, Color color,Font font) {
		super(originalPrintStream,console,color,font);
		System.setOut(this);
	}
	
	public CConsoleOut(PrintStream originalPrintStream, CConsoleGUI console) {
		super(originalPrintStream,console,console.getDefaultColor(),console.getDefaultFont());
		System.setOut(this);
	}
	
	public CConsoleOut(PrintStream originalPrintStream, CConsoleGUI console, Color color) {
		super(originalPrintStream,console,color,console.getDefaultFont());
		System.setOut(this);
	}
	
	public CConsoleOut(PrintStream originalPrintStream, CConsoleGUI console, Font font) {
		super(originalPrintStream,console,Color.RED,font);
		System.setOut(this);
	}
	
	//Restore the old PrintStream.
	public void finalize()
    {
         System.setOut(originalPrintStream); 
    }

}

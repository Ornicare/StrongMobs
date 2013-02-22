package cconsole;
import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;

/**
 * Used to intercept System.err
 * 
 * @author Ornicare
 *
 */
public class CConsoleErr extends CConsoleInterceptor{
	
	public CConsoleErr(PrintStream originalPrintStream, CConsoleGUI console, Color color,Font font) {
		super(originalPrintStream,console,color,font);
		System.setErr(this);
	}
	
	public CConsoleErr(PrintStream originalPrintStream, CConsoleGUI console) {
		super(originalPrintStream,console,Color.RED,console.getDefaultFont());
		System.setErr(this);
	}
	
	public CConsoleErr(PrintStream originalPrintStream, CConsoleGUI console, Color color) {
		super(originalPrintStream,console,color,console.getDefaultFont());
		System.setErr(this);
	}
	
	public CConsoleErr(PrintStream originalPrintStream, CConsoleGUI console, Font font) {
		super(originalPrintStream,console,Color.RED,font);
		System.setErr(this);
	}
	
	//Restore the old PrintStream.
	public void finalize()
    {
         System.setErr(originalPrintStream);
    }
}
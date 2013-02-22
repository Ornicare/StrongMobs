package cconsole;
import java.awt.Color;
import java.awt.Font;

/**
 * Static class to use {@link CConsoleGUI}
 * Implements print, warn and debug to display text. 
 * 
 * @author Ornicare
 *
 */
public abstract class CConsole {
	
	private static CConsoleGUI console;
	@SuppressWarnings("unused")
	private static CConsoleOut outInterceptor;
	@SuppressWarnings("unused")
	private static CConsoleErr errInterceptor;

	/**
	 * Text's color for the debug mode. Modifiable.
	 */
	private static Color debugColor;
	
	/**
	 * Text's color for the warning mode. Modifiable.
	 */
	private static Color warnColor;
	
	/**
	 * Text's font for the debug mode. Modifiable.
	 */
	private static Font debugFont;
	
	/**
	 * Text's font for the warning mode. Modifiable.
	 */
	private static Font warnFont;
	
	/**
	 * If true, display debug text. Modifiable.
	 */
	private static Boolean debug = true;
	
	public static void setDebug(Boolean debug) {
		CConsole.debug = debug;
	}

	public static Color getInfoColor() {
		return debugColor;
	}

	public static void setDebugColor(Color debugColor) {
		CConsole.debugColor = debugColor;
	}

	public static void setWarnColor(Color warnColor) {
		CConsole.warnColor = warnColor;
	}

	public static void setDebugFont(Font debugFont) {
		CConsole.debugFont = debugFont;
	}


	public static void setWarnFont(Font warnFont) {
		CConsole.warnFont = warnFont;
	}

	/**
	 * If @see {@link CConsole#debug} is true, call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values for the debug mode.
	 * 
	 * @param o the object to print
	 */
	public static void debug(Object o) {
		if(debug) print(o,debugFont,debugColor);
	}
	
	/**
	 * If @see {@link CConsole#debug} is true, call {@link CConsole#println(Object, Font, Color)} with all non-given args to their defaults values for the debug mode.
	 * 
	 * @param o the object to print
	 */
	public static void debugln(Object o) {
		if(debug) println(o,debugFont,debugColor);
	}

	/**
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values for the warning mode.
	 * 
	 * @param o the object to print
	 */
	public static void warn(Object o) {
		print(o,warnFont,warnColor);
	}
	
	/**
	 * Call {@link CConsole#println(Object, Font, Color)} with all non-given args to their defaults values for the warning mode.
	 * 
	 * @param o the object to print
	 */
	public static void warnln(Object o) {
		println(o,warnFont,warnColor);
	}
	
	/**
	 * Call a @see {@link CConsoleGUI#addText(String, Font, Color)} to print <code>o+append</code>
	 * 
	 * @param o the object to print
	 * @param append the string to add after o (use to differentiate @see {@link CConsole#println(Object, Font, Color)} from @see {@link CConsole#print(Object, String, Font, Color)}
	 * @param font <code>o</code>'s font
	 * @param color <code>o</code>'s color
	 */
	private static void print(Object o, String append, Font font, Color color) {
		String sortie = "";
		if(o.getClass()==Character.class || o.getClass()==Double.class || o.getClass()==Float.class || o.getClass()==Long.class || o.getClass()==Integer.class || o.getClass()==Boolean.class) {
			sortie = String.valueOf(o);
		}
		else {
			sortie = o.toString();
		}
		
		sortie+=append;
		console.addText(sortie,font,color);
			
	}

	/**
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void print(Object o) {
		print(o,"", console.getDefaultFont(), console.getDefaultColor());
	}
	
	/**
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void print(Object o, Color color) {
		print(o,"", console.getDefaultFont(), color);
	}
	
	/**
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void print(Object o, Font font) {
		print(o,"", font, console.getDefaultColor());
	}
	
	/**
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void print(Object o, Font font, Color color) {
		print(o,"",font, color);
	}
	

	/**
	 * Use to automatically a '\n' to the string version of o
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void println(Object o) {
		print(o,"\n", console.getDefaultFont(), console.getDefaultColor());
	}
	
	/**
	 * Use to automatically a '\n' to the string version of o
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void println(Object o, Color color) {
		print(o,"\n", console.getDefaultFont(), color);
	}
	
	/**
	 * Use to automatically a '\n' to the string version of o
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void println(Object o, Font font) {
		print(o,"\n", font, console.getDefaultColor());
	}
	
	/**
	 * Use to automatically a '\n' to the string version of o
	 * Call {@link CConsole#print(Object, Font, Color)} with all non-given args to their defaults values.
	 * 
	 * @param o the object to print
	 */
	public static void println(Object o, Font font, Color color) {
		print(o,"\n",font, color);
	}
	
	
	/**
	 * Set all variables to their defaults values.
	 */
	private static void loadFontProperties() {
		debugColor = Color.CYAN;
		warnColor = Color.YELLOW;
		debugFont = console.getDefaultFont();
		warnFont = console.getDefaultFont();
		outInterceptor = new CConsoleOut(System.out,console);
		errInterceptor = new CConsoleErr(System.err,console);
		
	}

	/**
	 * Initialize the console. Create a gui and set all variables.
	 */
	public static void load() {
		console = new CConsoleGUI();
		loadFontProperties();
	}

	/**
	 * Close the current GUI.
	 */
	public static void close() {
		if(console != null) console.close();
		console = null;
	}
}

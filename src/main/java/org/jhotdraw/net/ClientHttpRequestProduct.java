/**
 * <p>Title: MyJavaTools: Client HTTP Request class</p> <p>Description: this class helps to send POST HTTP requests with various form data, including files. Cookies can be added to be included in the request.</p> <p>Copyright: This is public domain; The right of people to use, distribute, copy or improve the contents of the following may not be restricted.</p>
 * @author  Vlad Patryshev, Alexei Trebounskikh
 * @version  $Id$
 */
package org.jhotdraw.net;


import java.net.URLConnection;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.OutputStream;
import java.io.IOException;

public class ClientHttpRequestProduct {
	private URLConnection connection;
	private OutputStream os = null;

	public URLConnection getConnection() {
		return connection;
	}

	public void setConnection(URLConnection connection) {
		this.connection = connection;
	}

	public OutputStream getOs() {
		return os;
	}

	public void write(char c) throws IOException {
		connect();
		os.write(c);
	}

	public void connect() throws IOException {
		if (os == null) {
			os = connection.getOutputStream();
		}
	}

	public void write(String s) throws IOException {
		connect();
		os.write(s.getBytes("UTF-8"));
	}

	public void newline() throws IOException {
		connect();
		write("\r\n");
	}

	public void writeln(String s) throws IOException {
		connect();
		write(s);
		newline();
	}
}
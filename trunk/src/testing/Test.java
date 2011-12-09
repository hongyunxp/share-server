package testing;

import java.sql.Connection;
import java.util.ArrayList;

public class Test {

	ThreadLocal<Connection> t = new ThreadLocal<Connection>();
	ArrayList<String> s = new ArrayList<String>();
}

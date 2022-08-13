package utility;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;

public class SqlScriptRunner{
    /**
     * Utility to empty all tables in the database before each test.
     * This makes sure that tests should not interfere with each other.
     */
    public static void emptyTables(Connection conn) throws FileNotFoundException{
        ScriptRunner runner = new ScriptRunner(conn);
        // Disable log writer, we don't want to see console full of sql scripts.
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("TableScripts/sql_script_for_testing.sql"));
        // Run the script!
        runner.runScript(reader);
    }
}

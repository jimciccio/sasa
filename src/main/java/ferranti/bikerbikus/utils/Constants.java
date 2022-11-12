package ferranti.bikerbikus.utils;

import static ferranti.bikerbikus.utils.GetPsw.getPsw;
import static ferranti.bikerbikus.utils.GetPsw1.getPsw1;

public final class Constants {
	/*public static final String URL = "jdbc:mysql://remotemysql.com:3306/UUS95gGFLJ";
    public static final String USERNAME = "UUS95gGFLJ";
    public static final String PASSWORD = getPsw();

	 */
    //public static final String URL = "jdbc:mysql://freemysqlhosting.net:3306/sql11528813";
    public static final String URL = "jdbc:mysql://localhost:3306/db1";


    public static final String USERNAME = "root";
    public static final String PASSWORD = getPsw1();
    public static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";

	private Constants(){
	}
}

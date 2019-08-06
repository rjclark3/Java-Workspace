// --------------------------------------------------------------------------------
// Name: <Rodney Clark>
// Class: IT-161 Java #1
// Abstract: This homework ...
// --------------------------------------------------------------------------------
package Utilities;
// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------

import java.math.BigDecimal;

// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------


public class CUserDataTypes
{
	//Declare and make instance example
	//udtTeamType udtNewTeam = new CUserDataTypes().new udtTeamType();
	
	//Team Strucutre
	public class udtTeamType
	{
		public int intTeamID = 0;
		public String strTeam = "";
		public String strMascot = "";
	}
	
	//Team Strucutre
	public class udtPlayerType
	{
		public int intPlayerID = 0;
		public String strFirstName = null;
		public String strMiddleName = null;
		public String strLastName = null;
		public String strAddress = null;
		public String strCity = null;
		public int intStateID = 0;
		public String strZipCode = null;
		public String strHomePhoneNumber = null;
		public BigDecimal bdecSalary = null;
		public java.sql.Date sdtmDateOfBirth = null;
		public int intSexID = 0;
		public boolean blnMostValuablePlayer = false;
		public String strEmailAddress = null;



	}
}

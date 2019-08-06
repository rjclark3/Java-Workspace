// --------------------------------------------------------------------------------
// Name: <Rodney Clark>

// Class: IT-161 Java #1
// Abstract: This homework ...
// --------------------------------------------------------------------------------
package Utilities;
// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.sql.*;
import Default.FMain;
import Utilities.CUserDataTypes.udtPlayerType;
import Utilities.CUserDataTypes.udtTeamType;

// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------



public class CDatabaseUtilities
{
	// ------------------------------------------------------------
	//Properties
	//-------------------------------------------------------------
	private static Connection m_conAdministrator = null;
	
	// ------------------------------------------------------------
	//Methods
	//-------------------------------------------------------------
	
	// --------------------------------------------------------------------------------
	// Name: OpenDatabaseConnection
	// Abstract: Handle button 1 click event
	// -------------------------------------------------------------
	public static boolean OpenDatabaseConnection()
	{
		boolean blnResult = false;
		
		try
		{
			String strConnectionString = "";
			
			Class.forName( "net.ucanaccess.jdbc.UcanaccessDriver" ); 
			
			//Server name/port, IP address/port or path for file based databases like MS access
			//System.getProperty("user.dir") => current working directory from application was started
			strConnectionString = "jdbc:ucanaccess://" + System.getProperty("user.dir")
								+ "\\Database\\TeamsAndPlayers4.accdb";
			
			strConnectionString = strConnectionString.replace( '\\', '/' );
			
			//Open a connection to the database
			m_conAdministrator = DriverManager.getConnection( strConnectionString );
			
			//Success
			blnResult = true;
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	//--------------------------------------------------------------------------------
	//Name: btnLoadTeamsList_Click
	//Abstract: Handle button 2 click event
	//-------------------------------------------------------------	
		public static boolean CloseDatabaseConnection()
		{
			boolean blnResult = false;
			
			try
			{
				// IS there an active connection?
				if ( m_conAdministrator != null)
				{
					//Yes, close the connection if not already closed
					if ( m_conAdministrator.isClosed( ) == false )
					{
						m_conAdministrator.close( );
						
						//Prevent JVM from crashing
						m_conAdministrator = null;
					}

				}
				
				blnResult = true;
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
			return blnResult;
		}

	// --------------------------------------------------------------------------------
	// Name: LoadListBoxFromDatabase
	// Abstract: Handle button 2 click event
	// -------------------------------------------------------------	
		public static boolean LoadListBoxFromDatabase(String strTable, String strID, String strNameColumn, CListBox lstTarget)
		{
			boolean blnResult = false;
			
			try
			{
				String strSelect = "";
				Statement sqlCommand = null;
				ResultSet rstTSource = null;
				String strName = "";
				int intID = 0;
				CListItem liNewItem = null;
				String strFirstName = "";
				String strLastName = "";
				
				//Clear List
				lstTarget.Clear( );
				
				//Build the SQL String
				strSelect = "SELECT "+ strID + ", " + strNameColumn

						   + " FROM " + strTable 

						   + " ORDER BY " + strID;
				
				//Retrieve all of the records
				sqlCommand = m_conAdministrator.createStatement();
				rstTSource = sqlCommand.executeQuery( strSelect );
						
				//Loop Through all of the records
				while ( rstTSource.next() == true )
				{
					//Get Name from current row, which should be the first, and only, column
					// Get ID and Name from current row
					intID = rstTSource.getInt( 1 );
					strName = rstTSource.getString( 2 );
					
					//Add the item to the list ( 0 = if, strTeam = text to display, false = don't select)
					lstTarget.AddItemToList( intID, strName, false );
				}
				
				// Clean up
				rstTSource.close( );
				sqlCommand.close( );
				
				//Success
				blnResult = true;
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
			return blnResult;
		}


		// --------------------------------------------------------------------------------
		// Name: LoadPlayerListBoxFromDatabase
		// Abstract: Handle button 2 click event
		// -------------------------------------------------------------	
			public static boolean LoadPlayerListBoxFromDatabase(String strTable, String strID, String strNameColumn, CListBox lstTarget)
			{
				boolean blnResult = false;
				
				try
				{
					String strSelect = "";
					Statement sqlCommand = null;
					ResultSet rstTSource = null;
					String strName = "";
					int intID = 0;
					CListItem liNewItem = null;
					String strFirstName = "";
					String strLastName = "";
					
					//Clear List
					lstTarget.Clear( );
					
					//Build the SQL String
					strSelect = "SELECT "+ strID + ", " + strNameColumn

							   + " FROM " + strTable 

							   + " ORDER BY " + strID;
					
					//Retrieve all of the records
					sqlCommand = m_conAdministrator.createStatement();
					rstTSource = sqlCommand.executeQuery( strSelect );
							
					//Loop Through all of the records
					while ( rstTSource.next() == true )
					{
						//Get Name from current row, which should be the first, and only, column
						// Get ID and Name from current row
						intID = rstTSource.getInt( 1 );
						strLastName = rstTSource.getString( 2 );
						strFirstName = rstTSource.getString( 3 );
						strName = strLastName + ", " + strFirstName;
						
						//Add the item to the list ( 0 = if, strTeam = text to display, false = don't select)
						lstTarget.AddItemToList( intID, strName, false );
					}
					
					// Clean up
					rstTSource.close( );
					sqlCommand.close( );
					
					//Success
					blnResult = true;
				}
				catch(Exception excError)
				{
					CUtilities.WriteLog( excError );
				}
				return blnResult;
			}
			
			
	// --------------------------------------------------------------------------------
	// Name: GetNextHighestRecordID
	// Abstract: Handle getting the highest record in DB
	// -------------------------------------------------------------	
		public static int GetNextHighestRecordID ( String strPrimaryKey, String strTable)
		{
			int intNextHighestRecordID = 0;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTable = null;
				
				//Build Command
				strSQL = "Select MAX( " + strPrimaryKey + " ) + 1 AS intHighestRecordID" + " FROM " + strTable;
				
				//Execute command
				sqlCommand = m_conAdministrator.createStatement( );
				rstTable = sqlCommand.executeQuery( strSQL );
				
				//Read Result ( next highest ID)
				if (rstTable.next( ) == true )
				{
					//Next Highest ID
					intNextHighestRecordID = rstTable.getInt( "intHighestRecordID" );
				}
				else
				{
					//Table Empty. Start at 1
					intNextHighestRecordID = 1;
				}
				
				//Clean up
				rstTable.close( );
				sqlCommand.close( );
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
			
			return intNextHighestRecordID;
		}
		
//--------------------------------------------------------------------------------
//Name: AddTeamToDatabase
//Abstract: Handle adding new team to database
//-------------------------------------------------------------	
		public static boolean AddTeamToDatabase( udtTeamType udtTeam )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeams = null;
				
				//Get the next highest team ID and save in suitcase so its return to Add form
				udtTeam.intTeamID = GetNextHighestRecordID ( "intTeamID", "TTeams");
				
				//Race condition. MS Access Doesn't support locking or stored procedures
				
				//build the select string (select no records)
				strSQL = "SELECT * FROM TTeams WHERE intTeamID = -1";
				
				//Retrieve the all the records 
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
																 ResultSet.CONCUR_UPDATABLE);
				rstTTeams = sqlCommand.executeQuery (strSQL);
				
				//New row using table structure returned from empty select
				rstTTeams.moveToInsertRow( );
				rstTTeams.updateInt( "intTeamID", udtTeam.intTeamID );
				rstTTeams.updateString( "strTeam", udtTeam.strTeam );
				rstTTeams.updateString( "strMascot", udtTeam.strMascot );
				rstTTeams.updateInt( "intTeamStatusID", CConstants.intTEAM_STATUS_ACTIVE );
				
				//Make the changes stick
				rstTTeams.insertRow();
				
				//Clean up
				rstTTeams.close( );
				sqlCommand.close( );
				
				//Success
				blnResult = true;

			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
			
			return blnResult;
		}

		// -------------------------------------------------------------------------
		// Name: DeleteTeamFromDatabase
		// Abstract: Mark the specified team as inactive.
		// -------------------------------------------------------------------------
		public static boolean DeleteTeamFromDatabase( int intTeamID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetTeamStatusInDatabase( intTeamID, CConstants.intPLAYER_STATUS_INACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	
				

		
		// -------------------------------------------------------------------------
		// Name: DeleteRecordsFromTable
		// Abstract: Delete all records from table that match the ID.
		// -------------------------------------------------------------------------
		private static boolean DeleteRecordsFromTable( int intRecordID,
													   String strPrimaryKeyColumn,
													   String strTable )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;

				// Build the SQL String
				strSQL = "DELETE FROM " + strTable 
					   + " WHERE " + strPrimaryKeyColumn + " = " + intRecordID;

				// Do it
				sqlCommand = m_conAdministrator.createStatement( );
				sqlCommand.execute( strSQL );
				
				// Clean up
				sqlCommand.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}
		
		// --------------------------------------------------------------------------------
		// Name:EditTeamDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean EditTeamDatabase( udtTeamType udtTeam )
		{
			boolean blnResult = false;
			 try
			 {
			
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeams = null;
						
				//Build the Select String
				strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + udtTeam.intTeamID; 
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
				rstTTeams = sqlCommand.executeQuery( strSQL );
				
				//Edit the teams information
				rstTTeams.next( );
				rstTTeams.updateString( "strTeam", udtTeam.strTeam );
				rstTTeams.updateString( "strMascot", udtTeam.strMascot );
				
				//Make the changes stick
				rstTTeams.updateRow( );
				
				//Clean up
				rstTTeams.close( );
				sqlCommand.close();
				
				//Success
				blnResult = true;
			
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}	
				
				return blnResult;
		}	
		
		
		// --------------------------------------------------------------------------------
		// GetTeamInformationFromDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean GetTeamInformationFromDatabase( udtTeamType udtTeam )
		{
			boolean blnResult = false;
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeams = null;
				
				//Build the select String
				strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + udtTeam.intTeamID;
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( );
				rstTTeams = sqlCommand.executeQuery( strSQL );
				
				//Get the team's information (Should be 1 and only 1 row)
				rstTTeams.next( );
				udtTeam.strTeam = rstTTeams.getString( "strTeam" );
				udtTeam.strMascot = rstTTeams.getString( "strMascot" );
				
				//Clean up
				rstTTeams.close( );
				sqlCommand.close( );
				
				//Success
				blnResult = true;
				
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}	
				
				return blnResult;
		}
		





//--------------------------------------------------------------------------------
//Name: AddPlayerToDatabase
//Abstract: Handle adding new Player to database
//-------------------------------------------------------------	
		public static boolean AddPlayerToDatabase( udtPlayerType udtPlayer )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTPlayers = null;
				
				//Get the next highest Player ID and save in suitcase so its return to Add form
				udtPlayer.intPlayerID = GetNextHighestRecordID ( "intPlayerID", "TPlayers");
				
				//Race condition. MS Access Doesn't support locking or stored procedures
				
				//build the select string (select no records)
				strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = -1";
				
				//Retrieve the all the records 
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
																 ResultSet.CONCUR_UPDATABLE);
				rstTPlayers = sqlCommand.executeQuery (strSQL);
				
				//New row using table structure returned from empty select
				rstTPlayers.moveToInsertRow( );
				rstTPlayers.updateInt(    	  "intPlayerID", 			udtPlayer.intPlayerID );
				rstTPlayers.updateString( 	  "strFirstName", 			udtPlayer.strFirstName );
				rstTPlayers.updateString( 	  "strMiddleName", 			udtPlayer.strMiddleName );
				rstTPlayers.updateString( 	  "strLastName", 			udtPlayer.strLastName );
				rstTPlayers.updateString( 	  "strAddress", 			udtPlayer.strAddress );
				rstTPlayers.updateString( 	  "strCity", 				udtPlayer.strCity );
				rstTPlayers.updateInt( 	  	  "intStateID", 			udtPlayer.intStateID );
				rstTPlayers.updateString( 	  "strZipCode", 			udtPlayer.strZipCode );
				rstTPlayers.updateString( 	  "strHomePhoneNumber", 	udtPlayer.strHomePhoneNumber );
				rstTPlayers.updateBigDecimal( "curSalary", 				udtPlayer.bdecSalary );
				rstTPlayers.updateDate(		  "dtmDateOfBirth", 		udtPlayer.sdtmDateOfBirth );
				rstTPlayers.updateInt( 	  	  "intSexID", 				udtPlayer.intSexID );
				rstTPlayers.updateBoolean( 	  "blnMostValuablePlayer", 	udtPlayer.blnMostValuablePlayer );
				rstTPlayers.updateString(	  "strEmailAddress", 		udtPlayer.strEmailAddress );
				rstTPlayers.updateInt(	  	  "intPlayerStatusID", 		CConstants.intPLAYER_STATUS_ACTIVE );
				
				//Make the changes stick
				rstTPlayers.insertRow();
				
				//Clean up
				rstTPlayers.close( );
				sqlCommand.close( );
				
				//Success
				blnResult = true;

			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
			
			return blnResult;
		}

		// --------------------------------------------------------------------------------
		// Name:
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean DeletePlayerFromDatabase( int intPlayerID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetPlayerStatusInDatabase( intPlayerID, CConstants.intPLAYER_STATUS_INACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		// --------------------------------------------------------------------------------
		// Name:EditPlayerDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean EditPlayerDatabase( udtPlayerType udtPlayer )
		{
			boolean blnResult = false;
			 try
			 {
			
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTPlayers = null;
						
				//Build the Select String
				strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + udtPlayer.intPlayerID; 
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
				rstTPlayers = sqlCommand.executeQuery( strSQL );
				
				//Edit the Players information
				rstTPlayers.next( );
				rstTPlayers.updateString(	  "strFirstName", 			udtPlayer.strFirstName );
				rstTPlayers.updateString(	  "strMiddleName", 			udtPlayer.strMiddleName );
				rstTPlayers.updateString(	  "strLastName", 			udtPlayer.strLastName );
				rstTPlayers.updateString(	  "strAddress", 			udtPlayer.strAddress );
				rstTPlayers.updateString(	  "strCity", 				udtPlayer.strCity );
				rstTPlayers.updateInt( 	 	  "intStateID", 			udtPlayer.intStateID );
				rstTPlayers.updateString( 	  "strZipCode", 			udtPlayer.strZipCode );
				rstTPlayers.updateString( 	  "strHomePhoneNumber",		udtPlayer.strHomePhoneNumber );
				rstTPlayers.updateBigDecimal( "curSalary",				udtPlayer.bdecSalary );
				rstTPlayers.updateDate( 	  "dtmDateOfBirth", 		udtPlayer.sdtmDateOfBirth );
				rstTPlayers.updateInt( 	  	  "intSexID", 				udtPlayer.intSexID );
				rstTPlayers.updateBoolean( 	  "blnMostValuablePlayer", 	udtPlayer.blnMostValuablePlayer );
				rstTPlayers.updateString(	  "strEmailAddress", 		udtPlayer.strEmailAddress );
				
				//Make the changes stick
				rstTPlayers.updateRow( );
				
				//Clean up
				rstTPlayers.close( );
				sqlCommand.close();
				
				//Success
				blnResult = true;
			
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}	
				
				return blnResult;
		}	
		
		
		// --------------------------------------------------------------------------------
		// GetPlayerInformationFromDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean GetPlayerInformationFromDatabase( udtPlayerType udtPlayer )
		{
			boolean blnResult = false;
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTPlayers = null;
				
				//Build the select String
				strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + udtPlayer.intPlayerID;
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( );
				rstTPlayers = sqlCommand.executeQuery( strSQL );
				
				//Get the Player's information (Should be 1 and only 1 row)
				rstTPlayers.next( );
				udtPlayer.strFirstName			= rstTPlayers.getString( 		"strFirstName" );
				udtPlayer.strMiddleName			= rstTPlayers.getString( 		"strMiddleName" );
				udtPlayer.strLastName			= rstTPlayers.getString( 		"strLastName" );
				udtPlayer.strAddress			= rstTPlayers.getString( 		"strAddress" );
				udtPlayer.strCity				= rstTPlayers.getString( 		"strCity" );
				udtPlayer.intStateID			= rstTPlayers.getInt( 	 		"intStateID" );
				udtPlayer.strZipCode			= rstTPlayers.getString( 		"strZipCode" );
				udtPlayer.strHomePhoneNumber	= rstTPlayers.getString( 		"strHomePhoneNumber" );
				udtPlayer.bdecSalary			= rstTPlayers.getBigDecimal( 	"curSalary" );
				udtPlayer.sdtmDateOfBirth 		= rstTPlayers.getDate( 			"dtmDateOfBirth" );
				udtPlayer.intSexID				= rstTPlayers.getInt( 	 		"intSexID" );
				udtPlayer.blnMostValuablePlayer = rstTPlayers.getBoolean( 		"blnMostValuablePlayer" );
				udtPlayer.strEmailAddress		= rstTPlayers.getString( 		"strEmailAddress" );;
				
				//Clean up
				rstTPlayers.close( );
				sqlCommand.close( );
				
				//Success
				blnResult = true;
				
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}	
				
				return blnResult;
		}
		
		// ----------------------------------------------------------------------
		// Name: LoadComboBoxFromDatabase
		// Abstract: Load the target list with the values from the specified table
		// ----------------------------------------------------------------------
		public static boolean LoadComboBoxFromDatabase( String strTable, String strPrimaryKeyColumn, 
													    String strNameColumn, CComboBox cmbTarget )
		{
			boolean blnResult = false;
			
			try
			{
				String strSelect = "";
				Statement sqlCommand = null;
				ResultSet rstTSource = null;
				int intID = 0;
				String strName = "";
				
				// Clear list
				cmbTarget.Clear( );
				
				// Build the SQL string
				strSelect = "SELECT " + strPrimaryKeyColumn + ", " + strNameColumn 
						  + " FROM " + strTable 
						  + " ORDER BY " + strNameColumn;

				// Retrieve the all the records
				sqlCommand = m_conAdministrator.createStatement( );
				rstTSource = sqlCommand.executeQuery( strSelect );

				// Loop through all the records
				while( rstTSource.next( ) == true )
				{
					// Get ID and Name from current row
					intID = rstTSource.getInt( 1 );
					strName = rstTSource.getString( 2 );

					// Add to listbox (automatically makes a CListItem instance)
					cmbTarget.AddItemToList( intID, strName, false );
				}
				
				// Select first item in list by default
				if( cmbTarget.Length( ) > 0 ) cmbTarget.SetSelectedIndex( 0 );
				
				// Clean up
				rstTSource.close( );
				sqlCommand.close( );
				
				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
			
			return blnResult;
		}
		
		
		// -------------------------------------------------------------------------
		// Name: UndeleteTeamFromDatabase
		// Abstract: Mark the specified team as active.
		// -------------------------------------------------------------------------
		public static boolean UndeleteTeamFromDatabase( int intTeamID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetTeamStatusInDatabase( intTeamID, CConstants.intPLAYER_STATUS_ACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	

		
		// -------------------------------------------------------------------------
		// Name: SetTeamStatusInDatabase
		// Abstract: Mark the specified team as inactive.
		// -------------------------------------------------------------------------
		private static boolean SetTeamStatusInDatabase( int intTeamID, int intTeamStatusID )
		{
			boolean blnResult = false;

			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeams = null;

				// Build the select string
				strSQL = "SELECT * FROM TTeams WHERE intTeamID = " + intTeamID;

				// Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
																 ResultSet.CONCUR_UPDATABLE );
				rstTTeams = sqlCommand.executeQuery( strSQL );

				// Edit the Team's information (should be 1 and only 1 row) 
				rstTTeams.next( );
				rstTTeams.updateInt( "intTeamStatusID", intTeamStatusID );
				
				// Make the changes stick
				rstTTeams.updateRow( );

				// Clean up
				rstTTeams.close( );
				sqlCommand.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	


		
	    // region "Team Players - Load, All, Add, Remove, None"
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		// Team Players
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------

		// ----------------------------------------------------------------------
	    // Name: LoadListWithPlayersFromDatabase
	    // Abstract: Load all the players on/not on the specified team.
		// ----------------------------------------------------------------------
	    public static boolean LoadTeamPlayersFromDatabase( int intTeamID,
	                                               		   CListBox lstTarget,
	                                               		   boolean blnPlayersOnTeam )
		{
	    	boolean blnResult = false;
	    	
			try
			{
				String strSelect = "";
				Statement sqlCommand = null;
				ResultSet rstTTeamPlayers = null;
	            String strNot = "NOT";
	            int intID = 0;
	            String strName = "";

	            // Clear list
	            lstTarget.Clear( );

	            // Selected players? Yes
	            if( blnPlayersOnTeam == true ) strNot = "";

	            // Build the SQL string.  See chapter on subqueries in SQL Server for Programmers.
	            		  // Load all players ...
	            strSelect = "SELECT intPlayerID, strLastName + ', ' + strFirstName" 
	            	      + " FROM VActivePlayers" 
	            	      // ... that are/are not ... 
	            	      + " WHERE intPlayerID " + strNot + " IN "
	            	      // ... on the team
	            	      + "	( SELECT intPlayerID FROM TTeamPlayers WHERE intTeamID = " + intTeamID + " ) "
	            	      + " ORDER BY strLastName, strFirstName";

				// Retrieve the all the records
				sqlCommand = m_conAdministrator.createStatement( );
				rstTTeamPlayers = sqlCommand.executeQuery( strSelect );

				// Loop through all the records
				while( rstTTeamPlayers.next( ) == true )
				{
					// Get the ID and Name from the current row
					intID = rstTTeamPlayers.getInt( 1 );
					strName = rstTTeamPlayers.getString( 2 );

					// Add the item to the list
					lstTarget.AddItemToList( intID, strName, false );
				}
								
				// Select first item in list by default
				if( lstTarget.Length( ) > 0 ) lstTarget.SetSelectedIndex( 0 );

				// Clean up
				rstTTeamPlayers.close( );
				sqlCommand.close( );
				
				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
			
			return blnResult;
		}
	    
	    
		// ----------------------------------------------------------------------
		// Name: AddAllPlayersToTeamInDatabase
		// Abstract: Add all the players to the specified team
		// ----------------------------------------------------------------------
		public static boolean AddAllPlayersToTeamInDatabase( int intTeamID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;

				// Build the SQL String
				// Add all players to the team that are not already on the team
				strSQL = "INSERT INTO TTeamPlayers "
					   + "  SELECT " + intTeamID + ", intPlayerID "
					   + "  FROM TPlayers"
					   + "  WHERE intPlayerID NOT IN"
					   + "  ( SELECT intPlayerID "
					   + "     FROM TTeamPlayers "
					   + "     WHERE intTeamID = " + intTeamID 
					   + "  )";

				// Do it
				sqlCommand = m_conAdministrator.createStatement( );
				sqlCommand.execute( strSQL );
				
				// Clean up
				sqlCommand.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}


		// ----------------------------------------------------------------------
		// Name: AddAllPlayersToTeamInDatabase
		// Abstract: Add all the players to the specified team with a stored procedure
		// ----------------------------------------------------------------------
		public static boolean AddAllPlayersToTeamInDatabase2( int intTeamID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstAddAllPlayersToTeam = null; 

				// Prepare stored procedure call
				cstAddAllPlayersToTeam = m_conAdministrator.prepareCall( "{ Call uspAddAllPlayersToTeam( ? ) }" ); 
				cstAddAllPlayersToTeam.setInt( 1, intTeamID );
				
				// Execute command
				cstAddAllPlayersToTeam.execute( );

				// Clean up
				cstAddAllPlayersToTeam.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		
		// ----------------------------------------------------------------------
		// Name: AddPlayerToTeamInDatabase
		// Abstract: Add the player to the specified team
		// ----------------------------------------------------------------------
		public static boolean AddPlayerToTeamInDatabase( int intTeamID, int intPlayerID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeamPlayers = null;
				int intTeamPlayerID = 0;
				
				//Get the next highest Player ID and save in suitcase so its return to Add form
				intTeamPlayerID = GetNextHighestRecordID ( "intTeamPlayerID", "TTeamPlayers");
				
				// Build the select string (select no records)
				strSQL = "SELECT * FROM TTeamPlayers WHERE intTeamID = -1";
		
				// Retrieve the all the records (should be none but we get table structure back)
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
															     ResultSet.CONCUR_UPDATABLE );
				rstTTeamPlayers = sqlCommand.executeQuery( strSQL );
		
				// New row using the table structure returned from the empty select
				rstTTeamPlayers.moveToInsertRow( );
				rstTTeamPlayers.updateInt( "intTeamPlayerID", intTeamPlayerID );
				rstTTeamPlayers.updateInt( "intTeamID", intTeamID );
				rstTTeamPlayers.updateInt( "intPlayerID", intPlayerID );
				
				// Make the changes stick
				rstTTeamPlayers.insertRow( );
				
				// Clean up
				rstTTeamPlayers.close( );
				sqlCommand.close( );
				
				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
		
			return blnResult;
		}


		// ----------------------------------------------------------------------
		// Name: AddPlayerToTeamInDatabase2
		// Abstract: Add a player to the specified team using a stored procedure.
		// ----------------------------------------------------------------------
		public static boolean AddPlayerToTeamInDatabase2( int intTeamID, int intPlayerID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstAddPlayerToTeam = null; 

				// Prepare stored procedure call
				cstAddPlayerToTeam = m_conAdministrator.prepareCall( "{ Call uspAddTeamPlayer( ?,? ) }" ); 
				cstAddPlayerToTeam.setInt( 1, intTeamID );
				cstAddPlayerToTeam.setInt( 2, intPlayerID );
				
				// Execute command
				cstAddPlayerToTeam.execute( );

				// Clean up
				cstAddPlayerToTeam.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
		
			return blnResult;
		}


		// ----------------------------------------------------------------------
		// Name: RemovePlayerFromTeamInDatabase
		// Abstract: Remove the player from the specified team
		// ----------------------------------------------------------------------
		public static boolean RemovePlayerFromTeamInDatabase( int intTeamID, int intPlayerID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTTeamPlayers = null;

				// Build the select string
				strSQL = "SELECT * FROM TTeamPlayers" +
						 " WHERE intTeamID = " + intTeamID +
						 " AND intPlayerID = " + intPlayerID;

				// Retrieve the all the records
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
															     ResultSet.CONCUR_UPDATABLE );
				rstTTeamPlayers = sqlCommand.executeQuery( strSQL );

				// Should be 1 and only 1 row
				rstTTeamPlayers.next( );
				rstTTeamPlayers.deleteRow( );
				
				// Clean up
				rstTTeamPlayers.close( );
				sqlCommand.close( );
				
				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		
		// ----------------------------------------------------------------------
		// Name: RemovePlayerFromTeamInDatabase2
		// Abstract: Remove the player from the specified team using a stored procedure.
		// ----------------------------------------------------------------------
		public static boolean RemovePlayerFromTeamInDatabase2( int intTeamID, int intPlayerID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstRemovePlayerFromTeam = null; 

				// Prepare stored procedure call
				cstRemovePlayerFromTeam = m_conAdministrator.prepareCall( "{ Call uspRemoveTeamPlayer( ?,? ) }" ); 
				cstRemovePlayerFromTeam.setInt( 1, intTeamID );
				cstRemovePlayerFromTeam.setInt( 2, intPlayerID );
				
				// Execute command
				cstRemovePlayerFromTeam.execute( );

				// Clean up
				cstRemovePlayerFromTeam.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
		
			return blnResult;
		}


		// ----------------------------------------------------------------------
		// Name: RemoveAllPlayersFromTeamInDatabase
		// Abstract: Remove all the players from the specified team
		// ----------------------------------------------------------------------
		public static boolean RemoveAllPlayersFromTeamInDatabase( int intTeamID )
		{
			boolean blnResult = false;
			
			try
			{
				blnResult = DeleteRecordsFromTable( intTeamID, "intTeamID", "TTeamPlayers" );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		
		// ----------------------------------------------------------------------
		// Name: RemoveAllPlayersFromTeamInDatabase
		// Abstract: Remove all the players from the specified team with a stored procedure
		// ----------------------------------------------------------------------
		public static boolean RemoveAllPlayersFromTeamInDatabase2( int intTeamID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstRemoveAllPlayersFromTeam = null; 

				// Prepare stored procedure call
				cstRemoveAllPlayersFromTeam = m_conAdministrator.prepareCall( "{ Call uspRemoveAllPlayersFromTeam( ? ) }" ); 
				cstRemoveAllPlayersFromTeam.setInt( 1, intTeamID );
				
				// Execute command
				cstRemoveAllPlayersFromTeam.execute( );

				// Clean up
				cstRemoveAllPlayersFromTeam.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		// -------------------------------------------------------------------------
		// Name: UndeletePlayerFromDatabase
		// Abstract: Mark the specified Player as active.
		// -------------------------------------------------------------------------
		public static boolean UndeletePlayerFromDatabase( int intPlayerID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetPlayerStatusInDatabase( intPlayerID, CConstants.intPLAYER_STATUS_ACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	

		
		// -------------------------------------------------------------------------
		// Name: SetPlayerStatusInDatabase
		// Abstract: Mark the specified Player as inactive.
		// -------------------------------------------------------------------------
		private static boolean SetPlayerStatusInDatabase( int intPlayerID, int intPlayerStatusID )
		{
			boolean blnResult = false;

			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTPlayers = null;

				// Build the select string
				strSQL = "SELECT * FROM TPlayers WHERE intPlayerID = " + intPlayerID;

				// Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
																 ResultSet.CONCUR_UPDATABLE );
				rstTPlayers = sqlCommand.executeQuery( strSQL );

				// Edit the Player's information (should be 1 and only 1 row) 
				rstTPlayers.next( );
				rstTPlayers.updateInt( "intPlayerStatusID", intPlayerStatusID );
				
				// Make the changes stick
				rstTPlayers.updateRow( );

				// Clean up
				rstTPlayers.close( );
				sqlCommand.close( );

				// Success
				blnResult = true;
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	

		

		
}

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
import Utilities.CUserDataTypes.udtSodaType;
import Utilities.CUserDataTypes.udtSodaCompanyType;

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
								+ "\\Database\\SodaAndSodaCompanies.accdb";
			
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
	//Name: SodaAndSodaCompanies
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
					
					//Add the item to the list ( 0 = if, strSodaCompany = text to display, false = don't select)
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
		// Name: LoadSodaListBoxFromDatabase
		// Abstract: Handle button 2 click event
		// -------------------------------------------------------------	
			public static boolean LoadSodaListBoxFromDatabase(String strTable, String strID, String strNameColumn, CListBox lstTarget)
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
						
						//Add the item to the list ( 0 = if, strSodaCompany = text to display, false = don't select)
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
//Name: AddSodaCompanyToDatabase
//Abstract: Handle adding new SodaCompany to database
//-------------------------------------------------------------	
		public static boolean AddSodaCompanyToDatabase( udtSodaCompanyType udtSodaCompany )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanies = null;
				
				//Get the next highest SodaCompany ID and save in suitcase so its return to Add form
				udtSodaCompany.intSodaCompanyID = GetNextHighestRecordID ( "intSodaCompanyID", "TSodaCompanies");
				
				//Race condition. MS Access Doesn't support locking or stored procedures
				
				//build the select string (select no records)
				strSQL = "SELECT * FROM TSodaCompanies WHERE intSodaCompanyID = -1";
				
				//Retrieve the all the records 
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
																 ResultSet.CONCUR_UPDATABLE);
				rstTSodaCompanies = sqlCommand.executeQuery (strSQL);
				
				//New row using table structure returned from empty select
				rstTSodaCompanies.updateInt(    	  "intSodaCompanyID", 			udtSodaCompany.intSodaCompanyID );
				rstTSodaCompanies.updateString( 	  "strName", 					udtSodaCompany.strName );
				rstTSodaCompanies.updateString( 	  "strAddress", 				udtSodaCompany.strAddress );
				rstTSodaCompanies.updateString( 	  "strCity", 					udtSodaCompany.strCity );
				rstTSodaCompanies.updateInt( 	  	  "intStateID", 				udtSodaCompany.intStateID );
				rstTSodaCompanies.updateString( 	  "strZipCode", 				udtSodaCompany.strZipCode );
				rstTSodaCompanies.updateString( 	  "strPhoneNumber", 			udtSodaCompany.strPhoneNumber );
				rstTSodaCompanies.updateString(	 	  "strEmailAddress", 			udtSodaCompany.strEmailAddress );
				rstTSodaCompanies.updateInt(	  	  "intSodaCompanyStatus", 		CConstants.intSodaCompany_STATUS_ACTIVE );
				
				//Make the changes stick
				rstTSodaCompanies.insertRow();
				
				//Clean up
				rstTSodaCompanies.close( );
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
		// Name: DeleteSodaCompanyFromDatabase
		// Abstract: Mark the specified SodaCompany as inactive.
		// -------------------------------------------------------------------------
		public static boolean DeleteSodaCompanyFromDatabase( int intSodaCompanyID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetSodaCompanyStatusInDatabase( intSodaCompanyID, CConstants.intSoda_STATUS_INACTIVE );
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
		// Name:EditSodaCompanyDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean EditSodaCompanyDatabase( udtSodaCompanyType udtSodaCompany )
		{
			boolean blnResult = false;
			 try
			 {
			
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanies = null;
						
				//Build the Select String
				strSQL = "SELECT * FROM TSodaCompanies WHERE intSodaCompanyID = " + udtSodaCompany.intSodaCompanyID; 
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
				rstTSodaCompanies = sqlCommand.executeQuery( strSQL );
				
				//Edit the SodaCompanys information
				rstTSodaCompanies.next( );
				rstTSodaCompanies.updateString(    		"strName", 				udtSodaCompany.strName );
				rstTSodaCompanies.updateString(	  		"strAddress", 			udtSodaCompany.strAddress );
				rstTSodaCompanies.updateString(	  		"strCity", 				udtSodaCompany.strCity );
				rstTSodaCompanies.updateInt( 	  		"intStateID", 			udtSodaCompany.intStateID );
				rstTSodaCompanies.updateString( 	  	"strZipCode", 			udtSodaCompany.strZipCode );
				rstTSodaCompanies.updateString( 	  	"strPhoneNumber",		udtSodaCompany.strPhoneNumber );
				rstTSodaCompanies.updateString( 	  	"strEmailAddress",		udtSodaCompany.strEmailAddress );
				//Make the changes stick
				rstTSodaCompanies.updateRow( );
				
				//Clean up
				rstTSodaCompanies.close( );
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
		// GetSodaCompanyInformationFromDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean GetSodaCompanyInformationFromDatabase( udtSodaCompanyType udtSodaCompany )
		{
			boolean blnResult = false;
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanies = null;
				
				//Build the select String
				strSQL = "SELECT * FROM TSodaCompanies WHERE intSodaCompanyID = " + udtSodaCompany.intSodaCompanyID;
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( );
				rstTSodaCompanies = sqlCommand.executeQuery( strSQL );
				
				//Get the SodaCompany's information (Should be 1 and only 1 row)
				rstTSodaCompanies.next( );
				udtSodaCompany.strName = rstTSodaCompanies.getString( "strName" );
				udtSodaCompany.strAddress = rstTSodaCompanies.getString( "strAddress" );
				udtSodaCompany.strCity = rstTSodaCompanies.getString( "strCity" );
				udtSodaCompany.intStateID = rstTSodaCompanies.getInt( "intStateID" );
				udtSodaCompany.strZipCode = rstTSodaCompanies.getString( "strZipCode" );
				udtSodaCompany.strPhoneNumber = rstTSodaCompanies.getString( "strPhoneNumber" );
				udtSodaCompany.strEmailAddress = rstTSodaCompanies.getString( "strEmailAddress" );
				
				//Clean up
				rstTSodaCompanies.close( );
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
//Name: AddSodaToDatabase
//Abstract: Handle adding new Soda to database
//-------------------------------------------------------------	
		public static boolean AddSodaToDatabase( udtSodaType udtSoda )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodas = null;
				
				//Get the next highest Soda ID and save in suitcase so its return to Add form
				udtSoda.intSodaID = GetNextHighestRecordID ( "intSodaID", "TSodas");
				
				//Race condition. MS Access Doesn't support locking or stored procedures
				
				//build the select string (select no records)
				strSQL = "SELECT * FROM TSodas WHERE intSodaID = -1";
				
				//Retrieve the all the records 
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE,
																 ResultSet.CONCUR_UPDATABLE);
				rstTSodas = sqlCommand.executeQuery (strSQL);
				
				//New row using table structure returned from empty select
				rstTSodas.moveToInsertRow( );
				rstTSodas.updateInt(    	  "intSodaID", 				udtSoda.intSodaID );
				rstTSodas.updateString( 	  "strSoda", 				udtSoda.strSoda );
				rstTSodas.updateInt(	  	  "intSodaStatus", 		CConstants.intSoda_STATUS_ACTIVE );
				
				//Make the changes stick
				rstTSodas.insertRow();
				
				//Clean up
				rstTSodas.close( );
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
		public static boolean DeleteSodaFromDatabase( int intSodaID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetSodaStatusInDatabase( intSodaID, CConstants.intSoda_STATUS_INACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		// --------------------------------------------------------------------------------
		// Name:EditSodaDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean EditSodaDatabase( udtSodaType udtSoda )
		{
			boolean blnResult = false;
			 try
			 {
			
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodas = null;
						
				//Build the Select String
				strSQL = "SELECT * FROM TSodas WHERE intSodaID = " + udtSoda.intSodaID; 
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
				rstTSodas = sqlCommand.executeQuery( strSQL );
				
				//Edit the Sodas information
				rstTSodas.next( );
				rstTSodas.updateInt(	  "intSodaID", 			udtSoda.intSodaID );
				rstTSodas.updateString(	  "strSoda", 			udtSoda.strSoda );
				
				//Make the changes stick
				rstTSodas.updateRow( );
				
				//Clean up
				rstTSodas.close( );
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
		// GetSodaInformationFromDatabase
		// Abstract:
		// --------------------------------------------------------------------------------
		public static boolean GetSodaInformationFromDatabase( udtSodaType udtSoda )
		{
			boolean blnResult = false;
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodas = null;
				
				//Build the select String
				strSQL = "SELECT * FROM TSodas WHERE intSodaID = " + udtSoda.intSodaID;
				
				//Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( );
				rstTSodas = sqlCommand.executeQuery( strSQL );
				
				//Get the Soda's information (Should be 1 and only 1 row)
				rstTSodas.next( );
				udtSoda.strSoda			= rstTSodas.getString( 		"strSoda" );

				
				//Clean up
				rstTSodas.close( );
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
		// Name: UndeleteSodaCompanyFromDatabase
		// Abstract: Mark the specified SodaCompany as active.
		// -------------------------------------------------------------------------
		public static boolean UndeleteSodaCompanyFromDatabase( int intSodaCompanyID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetSodaCompanyStatusInDatabase( intSodaCompanyID, CConstants.intSoda_STATUS_ACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	

		
		// region "SodaCompany Sodas - Load, All, Add, Remove, None"
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------
		// SodaCompany Sodas
		// ----------------------------------------------------------------------
		// ----------------------------------------------------------------------

		// ----------------------------------------------------------------------
	    // Name: LoadListWithSodasFromDatabase
	    // Abstract: Load all the Sodas on/not on the specified SodaCompany.
		// ----------------------------------------------------------------------
	    public static boolean LoadSodaCompanySodasFromDatabase( int intSodaCompanyID,
	                                               		   CListBox lstTarget,
	                                               		   boolean blnSodasOnSodaCompany )
		{
	    	boolean blnResult = false;
	    	
			try
			{
				String strSelect = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanySodas = null;
	            String strNot = "NOT";
	            int intID = 0;
	            String strName = "";

	            // Clear list
	            lstTarget.Clear( );

	            // Selected Sodas? Yes
	            if( blnSodasOnSodaCompany == true ) strNot = "";

	            // Build the SQL string.  See chapter on subqueries in SQL Server for Programmers.
	            		  // Load all Sodas ...
	            strSelect = "SELECT intSodaID, strSoda" 
	            	      + " FROM VActiveSodas" 
	            	      // ... that are/are not ... 
	            	      + " WHERE intSodaID " + strNot + " IN "
	            	      // ... on the SodaCompany
	            	      + "	( SELECT intSodaID FROM TSodaCompanySodas WHERE intSodaCompanyID = " + intSodaCompanyID + " ) "
	            	      + " ORDER BY strSoda";

				// Retrieve the all the records
				sqlCommand = m_conAdministrator.createStatement( );
				rstTSodaCompanySodas = sqlCommand.executeQuery( strSelect );

				// Loop through all the records
				while( rstTSodaCompanySodas.next( ) == true )
				{
					// Get the ID and Name from the current row
					intID = rstTSodaCompanySodas.getInt( 1 );
					strName = rstTSodaCompanySodas.getString( 2 );

					// Add the item to the list
					lstTarget.AddItemToList( intID, strName, false );
				}
								
				// Select first item in list by default
				if( lstTarget.Length( ) > 0 ) lstTarget.SetSelectedIndex( 0 );

				// Clean up
				rstTSodaCompanySodas.close( );
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
		// Name: AddAllSodasToSodaCompanyInDatabase
		// Abstract: Add all the Sodas to the specified SodaCompany
		// ----------------------------------------------------------------------
		public static boolean AddAllSodasToSodaCompanyInDatabase( int intSodaCompanyID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;

				// Build the SQL String
				// Add all Sodas to the SodaCompany that are not already on the SodaCompany
				strSQL = "INSERT INTO TSodaCompanySodas "
					   + "  SELECT " + intSodaCompanyID + ", intSodaID "
					   + "  FROM TSodas"
					   + "  WHERE intSodaID NOT IN"
					   + "  ( SELECT intSodaID "
					   + "     FROM TSodaCompanySodas "
					   + "     WHERE intSodaCompanyID = " + intSodaCompanyID 
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
		// Name: AddAllSodasToSodaCompanyInDatabase
		// Abstract: Add all the Sodas to the specified SodaCompany with a stored procedure
		// ----------------------------------------------------------------------
		public static boolean AddAllSodasToSodaCompanyInDatabase2( int intSodaCompanyID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstAddAllSodasToSodaCompany = null; 

				// Prepare stored procedure call
				cstAddAllSodasToSodaCompany = m_conAdministrator.prepareCall( "{ Call uspAddAllSodasToSodaCompany( ? ) }" ); 
				cstAddAllSodasToSodaCompany.setInt( 1, intSodaCompanyID );
				
				// Execute command
				cstAddAllSodasToSodaCompany.execute( );

				// Clean up
				cstAddAllSodasToSodaCompany.close( );

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
		// Name: AddSodaToSodaCompanyInDatabase
		// Abstract: Add the Soda to the specified SodaCompany
		// ----------------------------------------------------------------------
		public static boolean AddSodaToSodaCompanyInDatabase( int intSodaCompanyID, int intSodaID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanySodas = null;
				int intSodaCompanySodaID = 0;
				
				//Get the next highest Soda ID and save in suitcase so its return to Add form
				intSodaCompanySodaID = GetNextHighestRecordID ( "intSodaCompanySodaID", "TSodaCompanySodas");
				
				// Build the select string (select no records)
				strSQL = "SELECT * FROM TSodaCompanySodas WHERE intSodaCompanyID = -1";
		
				// Retrieve the all the records (should be none but we get table structure back)
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
															     ResultSet.CONCUR_UPDATABLE );
				rstTSodaCompanySodas = sqlCommand.executeQuery( strSQL );
		
				// New row using the table structure returned from the empty select
				rstTSodaCompanySodas.moveToInsertRow( );
				rstTSodaCompanySodas.updateInt( "intSodaCompanySodaID", intSodaCompanySodaID );
				rstTSodaCompanySodas.updateInt( "intSodaCompanyID", intSodaCompanyID );
				rstTSodaCompanySodas.updateInt( "intSodaID", intSodaID );
				
				// Make the changes stick
				rstTSodaCompanySodas.insertRow( );
				
				// Clean up
				rstTSodaCompanySodas.close( );
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
		// Name: AddSodaToSodaCompanyInDatabase2
		// Abstract: Add a Soda to the specified SodaCompany using a stored procedure.
		// ----------------------------------------------------------------------
		public static boolean AddSodaToSodaCompanyInDatabase2( int intSodaCompanyID, int intSodaID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstAddSodaToSodaCompany = null; 

				// Prepare stored procedure call
				cstAddSodaToSodaCompany = m_conAdministrator.prepareCall( "{ Call uspAddSodaCompanySoda( ?,? ) }" ); 
				cstAddSodaToSodaCompany.setInt( 1, intSodaCompanyID );
				cstAddSodaToSodaCompany.setInt( 2, intSodaID );
				
				// Execute command
				cstAddSodaToSodaCompany.execute( );

				// Clean up
				cstAddSodaToSodaCompany.close( );

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
		// Name: RemoveSodaFromSodaCompanyInDatabase
		// Abstract: Remove the Soda from the specified SodaCompany
		// ----------------------------------------------------------------------
		public static boolean RemoveSodaFromSodaCompanyInDatabase( int intSodaCompanyID, int intSodaID )
		{
			boolean blnResult = false;
			
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanySodas = null;

				// Build the select string
				strSQL = "SELECT * FROM TSodaCompanySodas" +
						 " WHERE intSodaCompanyID = " + intSodaCompanyID +
						 " AND intSodaID = " + intSodaID;

				// Retrieve the all the records
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
															     ResultSet.CONCUR_UPDATABLE );
				rstTSodaCompanySodas = sqlCommand.executeQuery( strSQL );

				// Should be 1 and only 1 row
				rstTSodaCompanySodas.next( );
				rstTSodaCompanySodas.deleteRow( );
				
				// Clean up
				rstTSodaCompanySodas.close( );
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
		// Name: RemoveSodaFromSodaCompanyInDatabase2
		// Abstract: Remove the Soda from the specified SodaCompany using a stored procedure.
		// ----------------------------------------------------------------------
		public static boolean RemoveSodaFromSodaCompanyInDatabase2( int intSodaCompanyID, int intSodaID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstRemoveSodaFromSodaCompany = null; 

				// Prepare stored procedure call
				cstRemoveSodaFromSodaCompany = m_conAdministrator.prepareCall( "{ Call uspRemoveSodaCompanySoda( ?,? ) }" ); 
				cstRemoveSodaFromSodaCompany.setInt( 1, intSodaCompanyID );
				cstRemoveSodaFromSodaCompany.setInt( 2, intSodaID );
				
				// Execute command
				cstRemoveSodaFromSodaCompany.execute( );

				// Clean up
				cstRemoveSodaFromSodaCompany.close( );

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
		// Name: RemoveAllSodasFromSodaCompanyInDatabase
		// Abstract: Remove all the Sodas from the specified SodaCompany
		// ----------------------------------------------------------------------
		public static boolean RemoveAllSodasFromSodaCompanyInDatabase( int intSodaCompanyID )
		{
			boolean blnResult = false;
			
			try
			{
				blnResult = DeleteRecordsFromTable( intSodaCompanyID, "intSodaCompanyID", "TSodaCompanySodas" );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}

		
		// ----------------------------------------------------------------------
		// Name: RemoveAllSodasFromSodaCompanyInDatabase
		// Abstract: Remove all the Sodas from the specified SodaCompany with a stored procedure
		// ----------------------------------------------------------------------
		public static boolean RemoveAllSodasFromSodaCompanyInDatabase2( int intSodaCompanyID )
		{
			boolean blnResult = false;
			
			try
			{
				CallableStatement cstRemoveAllSodasFromSodaCompany = null; 

				// Prepare stored procedure call
				cstRemoveAllSodasFromSodaCompany = m_conAdministrator.prepareCall( "{ Call uspRemoveAllSodasFromSodaCompany( ? ) }" ); 
				cstRemoveAllSodasFromSodaCompany.setInt( 1, intSodaCompanyID );
				
				// Execute command
				cstRemoveAllSodasFromSodaCompany.execute( );

				// Clean up
				cstRemoveAllSodasFromSodaCompany.close( );

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
		// Name: UndeleteSodaFromDatabase
		// Abstract: Mark the specified Soda as active.
		// -------------------------------------------------------------------------
		public static boolean UndeleteSodaFromDatabase( int intSodaID )
		{
			boolean blnResult = false;

			try
			{
				blnResult = SetSodaStatusInDatabase( intSodaID, CConstants.intSoda_STATUS_ACTIVE );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}

			return blnResult;
		}	

		
		// -------------------------------------------------------------------------
		// Name: SetSodaCompanyStatusInDatabase
		// Abstract: Mark the specified SodaCompany as inactive.
		// -------------------------------------------------------------------------
		private static boolean SetSodaCompanyStatusInDatabase( int intSodaCompanyID, int intSodaCompanyStatus )
		{
			boolean blnResult = false;
		
			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodaCompanies = null;
		
				// Build the select string
				strSQL = "SELECT * FROM TSodaCompanies WHERE intSodaCompanyID = " + intSodaCompanyID;
		
				// Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
																 ResultSet.CONCUR_UPDATABLE );
				rstTSodaCompanies = sqlCommand.executeQuery( strSQL );
		
				// Edit the SodaCompany's information (should be 1 and only 1 row) 
				rstTSodaCompanies.next( );
				rstTSodaCompanies.updateInt( "intSodaCompanyStatus", intSodaCompanyStatus );
				
				// Make the changes stick
				rstTSodaCompanies.updateRow( );
		
				// Clean up
				rstTSodaCompanies.close( );
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
		// Name: SetSodaStatusInDatabase
		// Abstract: Mark the specified Soda as inactive.
		// -------------------------------------------------------------------------
		private static boolean SetSodaStatusInDatabase( int intSodaID, int intSodaStatus )
		{
			boolean blnResult = false;

			try
			{
				String strSQL = "";
				Statement sqlCommand = null;
				ResultSet rstTSodas = null;

				// Build the select string
				strSQL = "SELECT * FROM TSodas WHERE intSodaID = " + intSodaID;

				// Retrieve the record
				sqlCommand = m_conAdministrator.createStatement( ResultSet.TYPE_SCROLL_SENSITIVE, 
																 ResultSet.CONCUR_UPDATABLE );
				rstTSodas = sqlCommand.executeQuery( strSQL );

				// Edit the Soda's information (should be 1 and only 1 row) 
				rstTSodas.next( );
				rstTSodas.updateInt( "intSodaStatus", intSodaStatus );
				
				// Make the changes stick
				rstTSodas.updateRow( );

				// Clean up
				rstTSodas.close( );
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

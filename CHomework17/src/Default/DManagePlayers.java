package Default;
	// --------------------------------------------------------------------------------
	// Name: <Rodney Clark>
	// Class: IT-161 Java #1
	// Abstract: This homework handles the event listener for the calculator buttons
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Import
	// --------------------------------------------------------------------------------
	import java.awt.*;


	import java.awt.event.*;
	import javax.swing.*;
	import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtPlayerType;
	// --------------------------------------------------------------------------------
	// Name: CHomework?
	// Abstract: This class ...
	// --------------------------------------------------------------------------------

	@SuppressWarnings("serial")
public class DManagePlayers extends JDialog implements ActionListener, WindowListener
{
		// --------------------------------------------------------------------------------
		// Name: Controls
		// Abstract: This class ...
		// --------------------------------------------------------------------------------
		
		//Declare buttons
		private JLabel m_lblPlayers = null;
		private CListBox m_lstPlayers = null;
		private JButton m_btnAdd = null;
		private JButton m_btnEdit = null;
		private JButton m_btnDelete = null;
		private JButton m_btnUndelete = null;
		private JCheckBox m_chkShowDeleted = null;
		private JButton m_btnClose = null;

		
		public DManagePlayers(JFrame frmParent)
		{
			
			super (frmParent, true);
			
			try
			{
				Initialize();
			
				AddControls();
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
		}
		
	// --------------------------------------------------------------------------------
	// Name: Initialize?
	// Abstract: Initialize form
	// -------------------------------------------------------------
		public void Initialize()
		{
			try
			{
				int intHeight = 450;
				int intWidth = 550;
				
				//Title
				setTitle ("Manage Players");
				
				//Size
				setSize( intWidth, intHeight);
				
				//Center Screen
				CUtilities.CenterScreen( this );
				
				//No Resize
				setResizable(false);
				
				//Close Application
				//setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				
				//Listen for window events
				this.addWindowListener( this );
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
		}
	// --------------------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Add controls on form
	// -------------------------------------------------------------
		
		public void AddControls()

		{
			try
			{
				// Clear layout manager so we can manually size and position controls
				CUtilities.ClearLayoutManager( this );
		
				// lstPlayers
				m_lblPlayers = CUtilities.AddLabel( this, "Players:", 14, 20 );
				m_lstPlayers = CUtilities.AddListBox( this, 30, 20, 250, 350 );
				
				// m_btnAdd
				m_btnAdd = CUtilities.AddButton( this, this, "Add", 'A', 55, 390, 30, 100 );
		
				// m_btnEdit
				m_btnEdit = CUtilities.AddButton( this, this, "Edit", 'E', 130, 390, 30, 100 );
		
				// m_btnDelete
				m_btnDelete = CUtilities.AddButton( this, this, "Delete", 'D', 205, 390, 30, 100 );
		
				// m_btnUndelete
				m_btnUndelete = CUtilities.AddButton( this, this, "Undelete", 'U', 205, 390, 30, 100 );
				m_btnUndelete.setVisible( false );
		
				// m_chkShowDeleted
				m_chkShowDeleted = CUtilities.AddCheckBox( this, this, "Show Deleted", 280, 16 );
				
				// m_btnClose
				m_btnClose = CUtilities.AddButton( this, this, "Close", 'C', 300, 145, 30, 200 );					
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
		}

		

	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Even handler for control click events
	// -------------------------------------------------------------
		@Override
		public void actionPerformed(ActionEvent aeSource)
		{
			try
			{
				 if( aeSource.getSource( ) == m_btnAdd )	    	btnAdd_Click( );
			else if( aeSource.getSource( ) == m_btnEdit )	    	btnEdit_Click( );
			else if( aeSource.getSource( ) == m_btnDelete )	    	btnDelete_Click( );
			else if( aeSource.getSource( ) == m_btnUndelete )	    btnUndelete_Click( );
			else if( aeSource.getSource( ) == m_chkShowDeleted )   	chkShowDeleted_Click( );
			else if( aeSource.getSource( ) == m_btnClose )			btnClose_Click( );
		
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}	
		}
			
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// WindowListener
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
			
	//--------------------------------------------------------------------------------
	//Name: windowOpened
	//Abstract: Handles the event of the window opening
	//--------------------------------------------------------------------------------	
		public void windowOpened( WindowEvent weSource ) 
			{
				try
				{
					boolean blnResult = false;
					
					// Load the Players list
					blnResult = LoadPlayersList( true );
					
					// Did it work?
					if( blnResult == false )
					{
						// No, warn the user ...
						CMessageBox.Show( this, "Unable to load the Players list.\n"
											  + "The form will now close.\n", 
												getTitle( ) + " Error",
												enuIconType.Error );

						// and close the form
						setVisible( false );
					}
					
					
				}
				catch(Exception excError)
				{
					CUtilities.WriteLog( excError );
				}	
				
				finally 
				{
					//We are not busy
					CUtilities.SetBusyCursor( this, false );
				}
	}


		// ----------------------------------------------------------------------
		// Name: LoadPlayersList
		// Abstract: Load the Players list from the database.
		// ----------------------------------------------------------------------
		private boolean LoadPlayersList( boolean blnActive )
		{
			boolean blnResult = false;
			
			try
			{
				String strTable = "VActivePlayers";
				
				// Deleted Players?
				if( blnActive == false ) 
				{
					// Yes
					strTable = "VInactivePlayers";
				}
				
				// We are busy
				CUtilities.SetBusyCursor( this, true );

				blnResult = CDatabaseUtilities.LoadListBoxFromDatabase( strTable, 
																		"intPlayerID", 
																		"strLastName + ', ' + strFirstName", 
																		m_lstPlayers );
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
			finally
			{
				// We are NOT busy
				CUtilities.SetBusyCursor( this, false );
			}
			
			return blnResult;
		}

	//Don't Care
		public void windowClosing( WindowEvent weSource ){ }
		public void windowClosed( WindowEvent weSource ){ }
		public void windowActivated( WindowEvent weSource ) { }
		public void windowDeactivated( WindowEvent weSource ) { }
		public void windowDeiconified( WindowEvent weSource ) { }
		public void windowIconified( WindowEvent weSource ) { }
		
	// --------------------------------------------------------------------------------
	// Name: btnAdd_Click
	// Abstract: Handle Add click event
	// -------------------------------------------------------------	
		public void btnAdd_Click()
			{
				try
				{
					DAddPlayer dlgAddPlayer = null;
					udtPlayerType udtNewPlayer = null;
					
					
					dlgAddPlayer = new DAddPlayer( this );
					
					dlgAddPlayer.setVisible( true );
					
					//Did it work
					if (dlgAddPlayer.GetResult() == true)
					{
						//Yes, get new info
						udtNewPlayer = dlgAddPlayer.GetNewPlayerInformation( );
						
						//Add new record to the listbox (true = select)
						m_lstPlayers.AddItemToList( udtNewPlayer.intPlayerID, udtNewPlayer.strLastName + ", " + udtNewPlayer.strFirstName, true );
					}
				}
				catch(Exception excError)
				{
					CUtilities.WriteLog( excError );
				}
			}

	// --------------------------------------------------------------------------------
	// Name: btnEdit_Click
	// Abstract: Handle Edit click event
	// -------------------------------------------------------------	
		public void btnEdit_Click()
			{
				try
				{
					int intSelectedListIndex = 0;
					String strSelectedPlayer = "";
					CListItem liSelectedPlayer = null;
					int intSelectedPlayerID = 0;
					
					DEditPlayer dlgEditPlayer = null;
					udtPlayerType udtNewPlayer = null;
					
					//Get the selected index from the list
					intSelectedListIndex = m_lstPlayers.GetSelectedIndex();
					
					//Is Something Selected
					if( intSelectedListIndex < 0)
					{
						//No, Warn User
						CMessageBox.Show( "You must select a Player to Edit.", "Edit Player Error" );
					}
					else
					{

						
						//Yes, so get the selected list item name
						liSelectedPlayer = m_lstPlayers.GetSelectedItem( );
						intSelectedPlayerID = liSelectedPlayer.GetValue( );
						strSelectedPlayer = liSelectedPlayer.GetName( );
						
						//Make instance
						dlgEditPlayer = new DEditPlayer (this, intSelectedPlayerID);
						
						//Display Modally
						dlgEditPlayer.setVisible(true);
						
						//Did it work
						if (dlgEditPlayer.GetResult( ) == true)
						{
							//Yes, get the new Player info
							udtNewPlayer = dlgEditPlayer.GetNewPlayerInformation( );
							
							CDatabaseUtilities.LoadPlayerListBoxFromDatabase( "TPlayers", "intPlayerID",
									"strLastName, " + "strFirstName", m_lstPlayers );
						}
					}
				}
				catch(Exception excError)
				{
					CUtilities.WriteLog( excError );
				}
			}
			
	// --------------------------------------------------------------------------------
	// Name: btnDelete_Click
	// Abstract: Handle Delete click event
	// -------------------------------------------------------------	
		public void btnDelete_Click()
			{
			try
			{
				int intSelectedListIndex = 0;
				CListItem liSelectedPlayer = null;
				int intSelectedPlayerID = 0;
				String strSelectedPlayer = "";
				int intConfirm = 0;
				boolean blnResult = false;
				
				// Get the selected index from the list
				intSelectedListIndex = m_lstPlayers.GetSelectedIndex( );

				// Is something selected?
				if( intSelectedListIndex < 0 )
				{
					// No, warn the user
					CMessageBox.Show( this, "You must select a Player to delete.", 
									  		"Delete Player Error"
											,enuIconType.Warning );
				}
				else
				{
					// Yes, so get the selected list item ID and name
					liSelectedPlayer = m_lstPlayers.GetSelectedItem( );
					intSelectedPlayerID = liSelectedPlayer.GetValue( );
					strSelectedPlayer = liSelectedPlayer.GetName( );
					
					// Confirm delete
					intConfirm = CMessageBox.Confirm( this, "Are you sure?", 
													  "Delete Player: " + strSelectedPlayer );
		
					// Delete?
					if( intConfirm == CMessageBox.intRESULT_YES )
					{
						// Yes, we are busy
						CUtilities.SetBusyCursor( this, true );
						
						// Attempt to delete
						blnResult = CDatabaseUtilities.DeletePlayerFromDatabase( intSelectedPlayerID );
						
						// Did it work?
						if( blnResult == true )
						{
							// Yes, remove record.  Next closest record automatically selected.
							m_lstPlayers.RemoveAt( intSelectedListIndex );
						}
					}
				}	
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
			finally
			{
				// We are NOT busy
				CUtilities.SetBusyCursor( this, false );
			}
		}
			
			
	// --------------------------------------------------------------------------------
	// Name: btnClose_Click
	// Abstract: Handle Close click event
	// -------------------------------------------------------------	
		public void btnClose_Click()
			{
				try
				{
					this.dispose( );
				}
				catch(Exception excError)
				{
					CUtilities.WriteLog( excError );
				}
			}		



		
		
		// ----------------------------------------------------------------------
		// Name: btnUndelete_Click
		// Abstract: Lazarus, come forth!
		// ----------------------------------------------------------------------
		private void btnUndelete_Click( )
		{
			try
			{
				int intSelectedListIndex = 0;
				CListItem liSelectedPlayer = null;
				int intSelectedPlayerID = 0;
				boolean blnResult = false;
				
				// Get the selected index from the list
				intSelectedListIndex = m_lstPlayers.GetSelectedIndex( );

				// Is something selected?
				if( intSelectedListIndex < 0 )
				{
					// No, warn the user
					CMessageBox.Show( this, "You must select a Player to undelete.", 
									  		"Undelete Player Error"
											,enuIconType.Warning );
				}
				else
				{
					// Yes, so get the selected list item ID
					liSelectedPlayer = m_lstPlayers.GetSelectedItem( );
					intSelectedPlayerID = liSelectedPlayer.GetValue( );
					
					// We are busy
					CUtilities.SetBusyCursor( this, true );
					
					// Attempt to delete
					blnResult = CDatabaseUtilities.UndeletePlayerFromDatabase( intSelectedPlayerID );
					
					// Did it work?
					if( blnResult == true )
					{
						// Yes, remove record.  Next closest record automatically selected.
						m_lstPlayers.RemoveAt( intSelectedListIndex );
					}
				}	
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
			finally
			{
				// We are NOT busy
				CUtilities.SetBusyCursor( this, false );
			}
		}

		
		// ----------------------------------------------------------------------
		// Name: chkShowDeleted_Click
		// Abstract: Toggle between active and inactive Players.
		// ----------------------------------------------------------------------
		private void chkShowDeleted_Click( )
		{
			try
			{
				// Show Deleted?
				if( m_chkShowDeleted.isSelected( ) == false )
				{
					// No
					LoadPlayersList( true );
					m_btnAdd.setEnabled( true );
					m_btnEdit.setEnabled( true );
					m_btnDelete.setVisible( true );
					m_btnUndelete.setVisible( false );
				}
				else
				{
					// Yes
					LoadPlayersList( false );
					m_btnAdd.setEnabled( false );
					m_btnEdit.setEnabled( false );
					m_btnDelete.setVisible( false );
					m_btnUndelete.setVisible( true );
				}
			}
			catch( Exception excError )
			{
				// Display Error Message
				CUtilities.WriteLog( excError );
			}
		}
}




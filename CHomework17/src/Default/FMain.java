// --------------------------------------------------------------------------------
// Name: <Rodney Clark>
// Class: IT-161 Java #1
// Abstract: This homework handles the event listener for the calculator buttons
// --------------------------------------------------------------------------------
package Default;
// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*;						// Basic windows functionality
import java.awt.event.*;				// Event processing
import javax.swing.*;					// Supplemental windows functionality
import TeamPlayers.DAssignTeamPlayers;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------

@SuppressWarnings("serial")
public class FMain extends JFrame implements ActionListener, WindowListener
{
	// --------------------------------------------------------------------------------
	// Name: Controls
	// Abstract: This class ...
	// ----------------------------------------------------------z----------------------

	// Main Menu
	private JMenuBar m_mbMainMenu = null;
	// File
	private JMenu m_mnuFile = null;
		private JMenuItem m_mniFileExit = null;
	// Manage
	private JMenu m_mnuTools = null;
		private JMenuItem m_mniToolsManageTeams = null;
		private JMenuItem m_mniToolsAssignTeamPlayers = null;
		private JMenuItem m_mniToolsManagePlayers = null;
	// Help
	private JMenu m_mnuHelp = null;
		private JMenuItem m_mniHelpAbout = null;
		
	private JPanel m_panManage = null;
	private JButton m_btnManageTeams = null;
	private JButton m_btnAssignTeamPlayers = null;
	private JButton m_btnManagePlayers = null;

	
	public static void main( String[ ] args )
	{
		try
		{
			// FMain
			FMain frmMain = new FMain( );

			frmMain.setVisible( true );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}

	public FMain()
	
	{
		try
		{
			Initialize();
			
			BuildMenu();
		
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
			int intHeight = 280;
			int intWidth = 310;
			
			//Title
			setTitle ("Homework 17 - Team Players");
			
			//Size
			setSize( intWidth, intHeight);
			
			//Center Screen
			CUtilities.CenterScreen( this );
			
			//No Resize
			setResizable(false);
			
			//Close Application
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
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
	
			// Group Box (titled panel)
			m_panManage = CUtilities.AddPanel( this, 20, 20, 185, 250, "Manage/Assign" );
			
			// m_btnManageTeams
			m_btnManageTeams = CUtilities.AddButton( m_panManage, this, "Manage Teams", 'T', 30, 25, 30, 200 );
	
			// m_btnAssignTeamPlayers
			m_btnAssignTeamPlayers = CUtilities.AddButton( m_panManage, this, "Assign Team Players", 'A', 80, 25, 30, 200 );
	
			// m_btnManagePlayers
			m_btnManagePlayers = CUtilities.AddButton( m_panManage, this, "Manage Players", 'P', 130, 25, 30, 200 );	
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
				
				//We are Busy
				CUtilities.SetBusyCursor( this, true );;

				//Can we connect to the database??
				if ( CDatabaseUtilities.OpenDatabaseConnection() == false )
				{
					//No, send error warning
					CMessageBox.Show( "Database connection error.\n" 
									+ "The Application will close.\n", getTitle() );
					
					this.dispose( );	
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


//--------------------------------------------------------------------------------
//Name: windowClosing
//Abstract: Handles when the program begins to close event
//--------------------------------------------------------------------------------	
	public void windowClosing( WindowEvent weSource )
{
	try
	{
		CleanUp();
	}
	catch(Exception excError)
	{
		CUtilities.WriteLog( excError );
	}	
}


//--------------------------------------------------------------------------------
//Name: windowClosed
//Abstract: handles the event once the window is closed
//--------------------------------------------------------------------------------	
	public void windowClosed( WindowEvent weSource )
{
	try
	{
		CleanUp();
	}
	catch(Exception excError)
	{
		CUtilities.WriteLog( excError );
	}	
}

// --------------------------------------------------------------------------------
// Name: CleanUp()
// Abstract: calls close database connection
// --------------------------------------------------------------------------------
	private void CleanUp()
{
	try
	{
		CDatabaseUtilities.CloseDatabaseConnection();
	}
	catch(Exception excError)
	{
		CUtilities.WriteLog( excError );
	}	
}

//Don't Care
	public void windowActivated( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }
	
// --------------------------------------------------------------------------------
// Name: btnManageTeams_Click
// Abstract: Handle btnManageTeams click event
// -------------------------------------------------------------	
	public void btnManageTeams_Click()
		{
			try
			{

					DManageTeams dlgManageTeams= null;

					dlgManageTeams = new DManageTeams( this );
					
					dlgManageTeams.setVisible( true );
		
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
		}
// --------------------------------------------------------------------------------
// Name: btnManageTeams_Click
// Abstract: Handle btnManageTeams click event
// -------------------------------------------------------------	
	
	public void btnManagePlayers_Click()
	{
		try
		{						
			
					DManagePlayers dlgManagePlayers= null;
		
					dlgManagePlayers = new DManagePlayers( this );
					
					dlgManagePlayers.setVisible( true );
			
			
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
// --------------------------------------------------------------------------------
// Name: btnManageTeams_Click
// Abstract: Handle btnManageTeams click event
// -------------------------------------------------------------	
	
	public void mniFileExit_Click()
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
// --------------------------------------------------------------------------------
// Name: btnManageTeams_Click
// Abstract: Handle btnManageTeams click event
// -------------------------------------------------------------	
	
	public void mniManageTeams_Click()
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
	
// --------------------------------------------------------------------------------
// Name: btnManageTeams_Click
// Abstract: Handle btnManageTeams click event
// -------------------------------------------------------------	
	public void mniHelpAbout_Click()
	{
		try
		{						
			CMessageBox.Show( "1.0 CHomework15", "CHomework15" );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: mniToolsAssignTeamPlayers_Click
	// Abstract: Manage Teams
	// ----------------------------------------------------------------------
	private void mniToolsAssignTeamPlayers_Click( )
	{
		try
		{
			AssignTeamPlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	// ----------------------------------------------------------------------
	// Name: mniToolsManageTeams_Click
	// Abstract: Manage Teams
	// ----------------------------------------------------------------------
	private void mniToolsManageTeams_Click( )
	{
		try
		{
			ManageTeams( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	// ----------------------------------------------------------------------
	// Name: ManageTeams
	// Abstract: Manage Teams
	// ----------------------------------------------------------------------
	private void ManageTeams( )
	{
		try
		{
			DManageTeams dlgManageTeams = null;
			
			// Make instance
			dlgManageTeams = new DManageTeams( this );
			
			// Show modally
			dlgManageTeams.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}	
	
	// ----------------------------------------------------------------------
	// Name: btnAssignTeamPlayers_Click
	// Abstract: Manage Teams
	// ----------------------------------------------------------------------
	private void btnAssignTeamPlayers_Click( )
	{
		try
		{
			AssignTeamPlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	// ----------------------------------------------------------------------
	// Name: AssignTeamPlayers
	// Abstract: Manage Teams
	// ----------------------------------------------------------------------
	private void AssignTeamPlayers( )
	{
		try
		{
			DAssignTeamPlayers dlgAssignTeamPlayers = null;
			
			// Make instance
			dlgAssignTeamPlayers = new DAssignTeamPlayers( this );
			
			// Show modally
			dlgAssignTeamPlayers.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
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
			 if( aeSource.getSource( ) == m_mniFileExit )	   			mniFileExit_Click( );
		else if( aeSource.getSource( ) == m_mniToolsManageTeams )	   	mniToolsManageTeams_Click( );
		else if( aeSource.getSource( ) == m_btnManageTeams )	   		btnManageTeams_Click( );
		else if( aeSource.getSource( ) == m_mniToolsAssignTeamPlayers ) mniToolsAssignTeamPlayers_Click( );
		else if( aeSource.getSource( ) == m_btnAssignTeamPlayers ) 		btnAssignTeamPlayers_Click( );
		else if( aeSource.getSource( ) == m_mniToolsManagePlayers )	   	mniToolsManagePlayers_Click( );
		else if( aeSource.getSource( ) == m_btnManagePlayers )	   		btnManagePlayers_Click( );
		else if( aeSource.getSource( ) == m_mniHelpAbout )	   			mniHelpAbout_Click( );
	
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
	}
	
	// ----------------------------------------------------------------------
	// Name: mniToolsManagePlayers_Click
	// Abstract: Manage Players
	// ----------------------------------------------------------------------
	private void mniToolsManagePlayers_Click( )
	{
		try
		{
			ManagePlayers( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	// Name: BuildMenu
	public void BuildMenu()
		{
			try
			{
				
				// Main menu
				m_mbMainMenu = CUtilities.AddMenuBar( this );
				
				// File
				m_mnuFile = CUtilities.AddMenu( m_mbMainMenu, "File", 'F' );
				
					// Exit
					m_mniFileExit = CUtilities.AddMenuItem( m_mnuFile, this, "Exit", 'X', 'X' );
		
				// Tools
				m_mnuTools = CUtilities.AddMenu( m_mbMainMenu, "Tools", 'T' );
				
					// Manage Teams
					m_mniToolsManageTeams = CUtilities.AddMenuItem( m_mnuTools, this, "Manage Teams", 'T' );
		
					// Assign Team Players
					m_mniToolsAssignTeamPlayers = CUtilities.AddMenuItem( m_mnuTools, this, "Assign Teams Players", 'A' );

					// Manage Players
						m_mniToolsManagePlayers = CUtilities.AddMenuItem( m_mnuTools, this, "Manage Players", 'P' );
		
				// Help
				m_mnuHelp = CUtilities.AddMenu( m_mbMainMenu, "Help", 'H' );
				
					// About
					m_mniHelpAbout = CUtilities.AddMenuItem( m_mnuHelp, this, "About", 'A' );
			
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}	
		}	

	// ----------------------------------------------------------------------
	// Name: ManagePlayers
	// Abstract: Manage Players
	// ----------------------------------------------------------------------
	private void ManagePlayers( )
	{
		try
		{
			DManagePlayers dlgManagePlayers = null;
			
			// Make instance
			dlgManagePlayers = new DManagePlayers( this );
			
			// Show modally
			dlgManagePlayers.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
}
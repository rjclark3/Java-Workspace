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
import SodaCompanySodas.DAssignSodaCompanySodas;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Sodas.*;
import SodaCompanies.*;
import SodaCompanySodas.*;

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
		private JMenuItem m_mniToolsManageSodaCompanies = null;
		private JMenuItem m_mniToolsAssignSodaCompanySodas = null;
		private JMenuItem m_mniToolsManageSodas = null;
	// Help
	private JMenu m_mnuHelp = null;
		private JMenuItem m_mniHelpAbout = null;
		
	private JPanel m_panManage = null;
	private JButton m_btnManageSodaCompanies = null;
	private JButton m_btnAssignSodaCompanySodas = null;
	private JButton m_btnManageSodas = null;

	
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
			int intWidth = 350;
			
			//Title
			setTitle ("Homework 18 - Final Project");
			
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
			m_panManage = CUtilities.AddPanel( this, 20, 20, 185, 275, "Manage/Assign" );
			
			// m_btnManageSodaCompanies
			m_btnManageSodaCompanies = CUtilities.AddButton( m_panManage, this, "Manage Soda Companies", 'T', 30, 25, 30, 225 );
	
			// m_btnAssignSodaCompanySodas
			m_btnAssignSodaCompanySodas = CUtilities.AddButton( m_panManage, this, "Assign Soda to Soda-Company", 'A', 80, 25, 30, 225 );
	
			// m_btnManageSodas
			m_btnManageSodas = CUtilities.AddButton( m_panManage, this, "Manage Sodas", 'P', 130, 25, 30, 225 );	
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
// Name: btnManageSodaCompanies_Click
// Abstract: Handle btnManageSodaCompanies click event
// -------------------------------------------------------------	
	public void btnManageSodaCompanies_Click()
		{
			try
			{

					DManageSodaCompanies dlgManageSodaCompanies= null;

					dlgManageSodaCompanies = new DManageSodaCompanies( this );
					
					dlgManageSodaCompanies.setVisible( true );
		
			}
			catch(Exception excError)
			{
				CUtilities.WriteLog( excError );
			}
		}
// --------------------------------------------------------------------------------
// Name: btnManageSodaCompanies_Click
// Abstract: Handle btnManageSodaCompanies click event
// -------------------------------------------------------------	
	
	public void btnManageSodas_Click()
	{
		try
		{						
			
					DManageSodas dlgManageSodas= null;
		
					dlgManageSodas = new DManageSodas( this );
					
					dlgManageSodas.setVisible( true );
			
			
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
// --------------------------------------------------------------------------------
// Name: btnManageSodaCompanies_Click
// Abstract: Handle btnManageSodaCompanies click event
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
// Name: btnManageSodaCompanies_Click
// Abstract: Handle btnManageSodaCompanies click event
// -------------------------------------------------------------	
	
	public void mniManageSodaCompanies_Click()
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
// Name: btnManageSodaCompanies_Click
// Abstract: Handle btnManageSodaCompanies click event
// -------------------------------------------------------------	
	public void mniHelpAbout_Click()
	{
		try
		{						
			CMessageBox.Show( "1.0 CHomework18", "CHomework18-Final Project" );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
	// ----------------------------------------------------------------------
	// Name: mniToolsAssignSodaCompanySodas_Click
	// Abstract: Manage SodaCompanies
	// ----------------------------------------------------------------------
	private void mniToolsAssignSodaCompanySodas_Click( )
	{
		try
		{
			AssignSodaCompanySodas( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	// ----------------------------------------------------------------------
	// Name: mniToolsManageSodaCompanies_Click
	// Abstract: Manage SodaCompanies
	// ----------------------------------------------------------------------
	private void mniToolsManageSodaCompanies_Click( )
	{
		try
		{
			ManageSodaCompanies( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	// ----------------------------------------------------------------------
	// Name: ManageSodaCompanies
	// Abstract: Manage SodaCompanies
	// ----------------------------------------------------------------------
	private void ManageSodaCompanies( )
	{
		try
		{
			DManageSodaCompanies dlgManageSodaCompanies = null;
			
			// Make instance
			dlgManageSodaCompanies = new DManageSodaCompanies( this );
			
			// Show modally
			dlgManageSodaCompanies.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}	
	
	// ----------------------------------------------------------------------
	// Name: btnAssignSodaCompanySodas_Click
	// Abstract: Manage SodaCompanies
	// ----------------------------------------------------------------------
	private void btnAssignSodaCompanySodas_Click( )
	{
		try
		{
			AssignSodaCompanySodas( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	// ----------------------------------------------------------------------
	// Name: AssignSodaCompanySodas
	// Abstract: Manage SodaCompanies
	// ----------------------------------------------------------------------
	private void AssignSodaCompanySodas( )
	{
		try
		{
			DAssignSodaCompanySodas dlgAssignSodaCompanySodas = null;
			
			// Make instance
			dlgAssignSodaCompanySodas = new DAssignSodaCompanySodas( this );
			
			// Show modally
			dlgAssignSodaCompanySodas.setVisible( true );
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
		else if( aeSource.getSource( ) == m_mniToolsManageSodaCompanies )	   	mniToolsManageSodaCompanies_Click( );
		else if( aeSource.getSource( ) == m_btnManageSodaCompanies )	   		btnManageSodaCompanies_Click( );
		else if( aeSource.getSource( ) == m_mniToolsAssignSodaCompanySodas ) mniToolsAssignSodaCompanySodas_Click( );
		else if( aeSource.getSource( ) == m_btnAssignSodaCompanySodas ) 		btnAssignSodaCompanySodas_Click( );
		else if( aeSource.getSource( ) == m_mniToolsManageSodas )	   	mniToolsManageSodas_Click( );
		else if( aeSource.getSource( ) == m_btnManageSodas )	   		btnManageSodas_Click( );
		else if( aeSource.getSource( ) == m_mniHelpAbout )	   			mniHelpAbout_Click( );
	
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
	}
	
	// ----------------------------------------------------------------------
	// Name: mniToolsManageSodas_Click
	// Abstract: Manage Sodas
	// ----------------------------------------------------------------------
	private void mniToolsManageSodas_Click( )
	{
		try
		{
			ManageSodas( );
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
				
					// Manage SodaCompanies
					m_mniToolsManageSodaCompanies = CUtilities.AddMenuItem( m_mnuTools, this, "Manage Soda Companies", 'T' );
		
					// Assign SodaCompany Sodas
					m_mniToolsAssignSodaCompanySodas = CUtilities.AddMenuItem( m_mnuTools, this, "Assign Soda to Soda-Companies", 'A' );

					// Manage Sodas
						m_mniToolsManageSodas = CUtilities.AddMenuItem( m_mnuTools, this, "Manage Sodas", 'P' );
		
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
	// Name: ManageSodas
	// Abstract: Manage Sodas
	// ----------------------------------------------------------------------
	private void ManageSodas( )
	{
		try
		{
			DManageSodas dlgManageSodas = null;
			
			// Make instance
			dlgManageSodas = new DManageSodas( this );
			
			// Show modally
			dlgManageSodas.setVisible( true );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
}
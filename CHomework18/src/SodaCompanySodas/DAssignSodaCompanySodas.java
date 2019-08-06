// --------------------------------------------------------------------------------
// Name: <Rodney Clark>
//
// Abstract: DAddingSodaCompaniesodas
// --------------------------------------------------------------------------------

// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------
package SodaCompanySodas;


// ----------------------------------------------------------------------
// Imports
// ----------------------------------------------------------------------
import java.awt.Graphics;
import java.awt.event.*;		// Listeners
import javax.swing.*;			// JEverything
import Utilities.*;
import Utilities.CMessageBox.enuIconType;


@SuppressWarnings("serial")
public class DAssignSodaCompanySodas extends JDialog implements ActionListener,
														   ItemListener,
														   MouseListener,
											               WindowListener
{
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Constants
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Controls( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	@SuppressWarnings("unused")	private JLabel m_lblSodaCompany = null;
	private CComboBox m_cmbSodaCompany = null;
	private JPanel m_panSodas = null;
	@SuppressWarnings("unused")	private JLabel m_lblSelectedSodas = null;
	private CListBox m_lstSelectedSodas = null;
	@SuppressWarnings("unused")	private JLabel m_lblAvailableSodas = null;
	private CListBox m_lstAvailableSodas = null;
	private JButton m_btnAll = null;
	private JButton m_btnAdd = null;
	private JButton m_btnRemove = null;
	private JButton m_btnNone = null;
	private JButton m_btnClose = null;

	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties( never make public )
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------



	// ----------------------------------------------------------------------
	// Name: DAssignSodaCompaniesodas
	// Abstract: default constructor
	// ----------------------------------------------------------------------
	public DAssignSodaCompanySodas( JFrame fraParent )
	{
		super( fraParent, true );	// true = modal
		
		try
		{
			Initialize(  );
			
			AddControls( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}



	// ----------------------------------------------------------------------
	// Name: Initialize
	// Abstract: Set the form properties
	// ----------------------------------------------------------------------
	public void Initialize( )
	{
		try
		{	
			int intHeight = 420;
			int intWidth = 650;
	
			// Title
			setTitle( "Assign SodaCompany Sodas" );
			
			// Size
			setSize( intWidth, intHeight );
			
			// Center screen
			CUtilities.CenterOwner( this );
			
			// No resize
			setResizable( false );

			// Listen for window events
			this.addWindowListener( this );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}


	// ----------------------------------------------------------------------
	// Name: paint
	// Abstract: Override the paint event to draw grid marks.
	// ----------------------------------------------------------------------
	public void paint( Graphics g )
	{
		super.paint( g );
		
		try
		{
			//CUtilities.DrawGridMarks( this, g );
		}
		catch( Exception expError )
		{
			// Display Error Message
			CUtilities.WriteLog( expError );
		}
	}
	
	
	// ----------------------------------------------------------------------
	// Name: AddControls
	// Abstract: Add all the controls to the frame
	// ----------------------------------------------------------------------
	private void AddControls( )
	{
		try
		{				
			// Clear layout manager so we can manually size and position controls
			CUtilities.ClearLayoutManager( this );
	
			// SodaCompany
			m_lblSodaCompany = CUtilities.AddLabel( this, "Soda Company:", 19, 50 );
			m_cmbSodaCompany = CUtilities.AddComboBox( this, this, 18, 225, 20, 200 );

			// Group Box (titled panel)
			m_panSodas = CUtilities.AddPanel( this, 50, 20, 275, 590, "Sodas" );

			// Selected Sodas
			m_lblSelectedSodas = CUtilities.AddLabel( m_panSodas, "Selected:", 25, 25 );
			m_lstSelectedSodas = CUtilities.AddListBox( m_panSodas, this, 42, 25, 205, 200 );
			
			// m_btnAll
			m_btnAll = CUtilities.AddButton( m_panSodas, this, "<< All", 'A', 65, 245, 25, 100 );

			// m_btnAdd
			m_btnAdd = CUtilities.AddButton( m_panSodas, this, "< Add", 'D', 110, 245, 25, 100 );

			// m_btnRemove
			m_btnRemove = CUtilities.AddButton( m_panSodas, this, "Remove >", 'R', 155, 245, 25, 100 );

			// m_btnNone
			m_btnNone = CUtilities.AddButton( m_panSodas, this, "None >>", 'N', 200, 245, 25, 100 );

			// Available Sodas
			m_lblAvailableSodas = CUtilities.AddLabel( m_panSodas, "Available:", 25, 365 );
			m_lstAvailableSodas = CUtilities.AddListBox( m_panSodas, this, 42, 365, 205, 200 );
			
			// m_btnClose
			m_btnClose = CUtilities.AddButton( this, this, "Close", 'C', 345, 217, 30, 200 );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Window Listener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	// Name: windowOpened
	// Abstract: The window is opened.  Triggered by setVisible( true ).
	// ----------------------------------------------------------------------
	public void windowOpened( WindowEvent weSource )
	{
		try
		{
			boolean blnResult = false;
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );

			// Load the SodaCompanies list
			blnResult = CDatabaseUtilities.LoadComboBoxFromDatabase( "VActiveSodaCompanies", 
																	 "intSodaCompanyID", 
																	 "strName", 
																	 m_cmbSodaCompany );
			
			// Did it work?
			if( blnResult == false )
			{
				// No, warn the user ...
				CMessageBox.Show( this, "Unable to load the SodaCompanies list.\n"
									  + "The form will close.\n", 
										getTitle( ) + " Error",
										enuIconType.Error );

				// and close the form/application
				this.dispose( );
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
	

	// Don't care
	public void windowClosing( WindowEvent weSource ) { }
	public void windowClosed( WindowEvent weSource ) { }
	public void windowActivated( WindowEvent weSource ) { }
	public void windowDeactivated( WindowEvent weSource ) { }
	public void windowIconified( WindowEvent weSource ) { }
	public void windowDeiconified( WindowEvent weSource ) { }	
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// ItemListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	// Name: itemStateChanged
	// Abstract: Selected item index in combo box changed.
	// ----------------------------------------------------------------------
	public void itemStateChanged( ItemEvent ieSource )
	{
		try
		{		
			// cmbSodaCompanies?
			if( ieSource.getSource( ) == m_cmbSodaCompany )
			{
				// Selected event?
				if( ieSource.getStateChange( ) == ItemEvent.SELECTED )
				{
					LoadSodaCompaniesodas( );
				}
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	
	// ----------------------------------------------------------------------
	// Name:LoadSodaCompaniesodas
	// Abstract: The selected index for the SodaCompany combo box changed.
	// ----------------------------------------------------------------------
	private void LoadSodaCompaniesodas( )
	{
		try
		{
			int intSodaCompanyID = 0;
			
			// Get the selected SodaCompany ID
            intSodaCompanyID = m_cmbSodaCompany.GetSelectedItemValue( );

            // Is a SodaCompany selected (list could be empty)?
            if( intSodaCompanyID > 0 )
            {
            	// Yes, we are busy
    			CUtilities.SetBusyCursor( this, true );

    			// Selected Sodas
                CDatabaseUtilities.LoadSodaCompanySodasFromDatabase( intSodaCompanyID, 
                												m_lstSelectedSodas, 
                												true );

    			// Available Sodas
                CDatabaseUtilities.LoadSodaCompanySodasFromDatabase( intSodaCompanyID, 
                												m_lstAvailableSodas, 
                												false );

	            // Enable/disable buttons
	            EnableButtons( );
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
	// Name:EnableButtons
	// Abstract: Enable/disable the add/remove buttons.
	// ----------------------------------------------------------------------
	private void EnableButtons( )
	{
		try
		{
	        // All
	        if( m_lstAvailableSodas.Length( ) > 0 ) m_btnAll.setEnabled( true );
	        else									  m_btnAll.setEnabled( false );
	
	        // Add
	        if( m_lstAvailableSodas.Length( ) > 0 ) m_btnAdd.setEnabled( true );
	        else									  m_btnAdd.setEnabled( false );
	
	        // Remove
	        if( m_lstSelectedSodas.Length( ) > 0 )  m_btnRemove.setEnabled( true );
	        else									  m_btnRemove.setEnabled( false );

	        // None
	        if( m_lstSelectedSodas.Length( ) > 0 )  m_btnNone.setEnabled( true );
	        else									  m_btnNone.setEnabled( false );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}

	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// MouseListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	// Name: mouseClicked
	// Abstract: Handle mouseListener events
	// ----------------------------------------------------------------------
	public void mouseClicked( MouseEvent meSource )
	{
		try
		{		
			// Double click?
			if( meSource.getClickCount( ) == 2 )
			{
				// Yes
				     if( meSource.getSource( ) == m_lstAvailableSodas ) btnAdd_Click( );
				else if( meSource.getSource( ) == m_lstSelectedSodas )  btnRemove_Click( );
			}
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	// Don't care
	public void mousePressed( MouseEvent meSource ) { }
	public void mouseReleased( MouseEvent meSource ) { }
	public void mouseEntered( MouseEvent meSource ) { }
	public void mouseExited( MouseEvent meSource ) { }
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// ActionListener
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------

	// ----------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Event handler for controls
	// ----------------------------------------------------------------------
	public void actionPerformed( ActionEvent aeSource )
	{
		try
		{		
			// What control?
			     if( aeSource.getSource( ) == m_btnAll )			btnAll_Click( );
			else if( aeSource.getSource( ) == m_btnAdd )			btnAdd_Click( );
			else if( aeSource.getSource( ) == m_btnRemove )			btnRemove_Click( );
			else if( aeSource.getSource( ) == m_btnNone )			btnNone_Click( );
			else if( aeSource.getSource( ) == m_btnClose )			btnClose_Click( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}


	// ----------------------------------------------------------------------
	// Name: btnAll_Click
	// Abstract: Add all Sodas to the SodaCompany
	// ----------------------------------------------------------------------
	private void btnAll_Click( )
	{
		try
		{
            int intSodaCompanyID = 0;
            int intIndex = 0;
            CListItem liCurrentSoda = null;

            // We are busy
			CUtilities.SetBusyCursor( this, true );

            // Get SodaCompany ID
            intSodaCompanyID = m_cmbSodaCompany.GetSelectedItemValue( );

            // Add all the Sodas
            if( CDatabaseUtilities.AddAllSodasToSodaCompanyInDatabase( intSodaCompanyID ) == true )
			{
            	// Move all the Sodas from available to selected
            	for( intIndex = 0; intIndex < m_lstAvailableSodas.Length( ); intIndex += 1 )
            	{
            		// Get next Soda
            		liCurrentSoda = m_lstAvailableSodas.GetItem( intIndex );
            	
            		// Copy
            		m_lstSelectedSodas.AddItemToList( liCurrentSoda );
            	}
            	
            	// Clear available
            	m_lstAvailableSodas.Clear( );

            	// Select first item in list
            	m_lstSelectedSodas.SetSelectedIndex( 0 );
            }

            // Enable/disable  add/remove buttons
            EnableButtons( );
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
	// Name: btnAdd_Click
	// Abstract: Add a Soda to the SodaCompany
	// ----------------------------------------------------------------------
	private void btnAdd_Click( )
	{
		try
		{
            int intSodaCompanyID = 0;
            int intSodaID = 0;
            boolean blnResult = false;

            // We are busy
			CUtilities.SetBusyCursor( this, true );

            // Get SodaCompany and Soda IDs
            intSodaCompanyID = m_cmbSodaCompany.GetSelectedItemValue( );
            intSodaID = m_lstAvailableSodas.GetSelectedItemValue( );

			// SodaCompany and Soda selected?
			if( intSodaCompanyID > 0 && intSodaID > 0 )
			{
	            // Yes, add the Soda to the SodaCompany
	            blnResult = CDatabaseUtilities.AddSodaToSodaCompanyInDatabase( intSodaCompanyID, intSodaID );
	            
	            // Did it work?
	            if( blnResult == true )
				{
	                // Yes, add Soda to selected list
	                m_lstSelectedSodas.AddItemToList( m_lstAvailableSodas.GetSelectedItem( ) );
	
	                // Remove from available list
	                m_lstAvailableSodas.RemoveAt( m_lstAvailableSodas.GetSelectedIndex( ) );
	            }
	
	            // Enable/disable buttons
	            EnableButtons( );
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
	// Name: btnRemove_Click
	// Abstract: Remove a Soda from the SodaCompany
	// ----------------------------------------------------------------------
	private void btnRemove_Click( )
	{
		try
		{
            int intSodaCompanyID = 0;
            int intSodaID = 0;
            boolean blnResult = false;

            // We are busy
			CUtilities.SetBusyCursor( this, true );

            // Get SodaCompany and Soda IDs
            intSodaCompanyID = m_cmbSodaCompany.GetSelectedItemValue( );
            intSodaID = m_lstSelectedSodas.GetSelectedItemValue( );

			// SodaCompany and Soda selected?
			if( intSodaCompanyID > 0 && intSodaID > 0 )
			{
	            // Yes, remove the Soda from the SodaCompany
	            blnResult = CDatabaseUtilities.RemoveSodaFromSodaCompanyInDatabase( intSodaCompanyID, intSodaID );
	            
	            // Did it work?
	            if( blnResult == true )
				{
	                // Yes, add Soda to available list
	                m_lstAvailableSodas.AddItemToList( m_lstSelectedSodas.GetSelectedItem( ) );
	
	                // Remove from selected list
	                m_lstSelectedSodas.RemoveAt( m_lstSelectedSodas.GetSelectedIndex( ) );
	            }

	            // Enable/disable buttons
	            EnableButtons( );
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
	// Name: btnNone_Click
	// Abstract: Remove all Sodas from the SodaCompany
	// ----------------------------------------------------------------------
	private void btnNone_Click( )
	{
		try
		{
            int intSodaCompanyID = 0;
            int intIndex = 0;
            CListItem liCurrentSoda = null;

            // We are busy
			CUtilities.SetBusyCursor( this, true );

            // Get SodaCompany ID
            intSodaCompanyID = m_cmbSodaCompany.GetSelectedItemValue( );

            // Add all the Sodas
            if( CDatabaseUtilities.RemoveAllSodasFromSodaCompanyInDatabase( intSodaCompanyID ) == true )
			{
            	// Move all the Sodas from selected to selected
            	for( intIndex = 0; intIndex < m_lstSelectedSodas.Length( ); intIndex += 1 )
            	{
            		// Get next Soda
            		liCurrentSoda = m_lstSelectedSodas.GetItem( intIndex );
            	
            		// Copy
            		m_lstAvailableSodas.AddItemToList( liCurrentSoda );
            	}
            	
            	// Clear available
            	m_lstSelectedSodas.Clear( );
            	
            	// Select first item in list
            	m_lstAvailableSodas.SetSelectedIndex( 0 );
            }

            // Enable/disable  add/remove buttons
            EnableButtons( );
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
	// Name: btnClose_Click
	// Abstract: Close the form
	// ----------------------------------------------------------------------
	private void btnClose_Click( )
	{
		try
		{
			// Close the form
			setVisible( false );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
}

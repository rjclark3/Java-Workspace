// --------------------------------------------------------------------------------
// Name: <Rodney Clark>
// Class: IT-161 Java #1
// Abstract: This homework handles the event listener for the calculator buttons
// --------------------------------------------------------------------------------
package Sodas;
// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*;

import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtSodaType;
// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------



@SuppressWarnings("serial")
public class DEditSoda extends JDialog implements ActionListener, WindowListener
{
	// --------------------------------------------------------------------------------
	// Name: Controls
	// Abstract: This class ...
	// --------------------------------------------------------------------------------
	
	//Declare Controls
	@SuppressWarnings("unused")
	private JLabel   m_lblSoda = null;
	private CTextBox m_txtSoda = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblRequiredField = null;

	private JButton  m_btnOK = null;
	private JButton  m_btnCancel = null;
	
	private int m_intSodaToEditID = 0;
	private boolean m_blnResult = false;
// --------------------------------------------------------------------------------
// Name: Controls
// Abstract: This class ...
// --------------------------------------------------------------------------------
	public DEditSoda( JDialog dlgParent, int intSodaToEditID)
	{
		super( dlgParent, true ); //Modal
		
		try
		{
			//Save ID for loading and saving
			m_intSodaToEditID = intSodaToEditID;
			
			//initialize frame
			Initialize();
			
			//Add the controls
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
			int intHeight = 510;
			int intWidth = 370;
			
			//Title
			setTitle ("Edit Soda");
			
			//Size
			setSize( intWidth, intHeight);
			
			//Center Screen
			CUtilities.CenterOwner( this );
			
			//No Resize
			setResizable(false);
			
			// Listen for window events
			this.addWindowListener( this );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}
// --------------------------------------------------------------------------------
// Name: EditControls
// Abstract: Edit controls on form
// -------------------------------------------------------------
	
	public void AddControls()
	{
		try
		{
			//Clear Layout manager
			CUtilities.ClearLayoutManager( this );
			
			// First Name
			m_lblSoda = CUtilities.AddLabel( this, "First Name:*", 25, 20 );
			m_txtSoda = CUtilities.AddTextBox( this, "", 21, 160, 25, 185, 50 );

			// Required
			m_lblRequiredField = CUtilities.AddRequiredFieldLabel( this, 409, 155 );

			// m_btnOK
			m_btnOK = CUtilities.AddButton( this, this, "OK", 'O', 430, 70, 30, 100 );
	
			// m_btnCancel
			m_btnCancel = CUtilities.AddButton( this, this, "Cancel", 'C', 430, 200, 30, 100 );
						
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
	}	

	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Action Listener
	// --------------------------------------------------------------------------------	
	// --------------------------------------------------------------------------------	
		
	// --------------------------------------------------------------------------------
	// Name: actionPerformed
	// Abstract: Even handler for control click events
	// -------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent aeSource)
	{
		try
		{
				 if (aeSource.getSource( ) == m_btnOK ) 							btnOK_Click();
			else if (aeSource.getSource( ) == m_btnCancel ) 						btnCancel_Click();

		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
	}
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	private void btnCancel_Click()
	{
		try
		{
			setVisible ( false );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
	}

	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	private void btnOK_Click( )
	{
		try
		{
			if ( isValidData() == true )
			{
				//Did it save to the database
				if (SaveData() == true)
				{
					
					//Yes
					m_blnResult = true;
					
					setVisible ( false );
				}
			}
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
	}

	
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	private boolean SaveData( )
	{
		
		boolean blnResult = false;
		
		try
		{
			// Make a suitcase for moving data
			udtSodaType udtNewSoda = null;
			
			// Get the values from the form
			udtNewSoda = GetValuesFromForm( );
			
			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Try to save the data
			blnResult = CDatabaseUtilities.EditSodaDatabase( udtNewSoda );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		finally 
		{
			CUtilities.SetBusyCursor( this, false );
		}
		
		return blnResult;
	}

	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	private boolean isValidData()
	{
		
		//Assume data is good.
		boolean blnIsValidData = true;
		
		try
		{
			String strErrorMessage = "Please correct the following error(s): \n";
			
			//Trim Textboxes
			CUtilities.TrimAllFormTextBoxes( this );
			
			//Last NAme
			if ( m_txtSoda.getText().equals( "" ) == true )
			{
				strErrorMessage += "-Soda cannot be blank\n";
				blnIsValidData = false;
			}
			
			
			if ( blnIsValidData == false)
			{
				//Yes, warn the user
				CMessageBox.Show( this, strErrorMessage, getTitle() + " Error", enuIconType.Warning );
			}
			
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
		
		return blnIsValidData;
	}
	
	
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	public boolean GetResult()
	{
		
		boolean blnResult = true;
		
		try
		{
			
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		
		return blnResult;
	}
	
	
	// ----------------------------------------------------------------------
	// Name: GetValuesFromForm
	// Abstract: Load a structure suitcase with all the values from the form.
	// ----------------------------------------------------------------------
	private udtSodaType GetValuesFromForm( )
	{
		udtSodaType udtSoda = null;
		
		try
		{
			// Make a suitcase instance
			udtSoda = new CUserDataTypes( ).new udtSodaType( );
			
			// Load suitcase with values from form
			udtSoda.intSodaID 			= m_intSodaToEditID;
			udtSoda.strSoda			= m_txtSoda.getText( );

		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtSoda;
	}
	
	// --------------------------------------------------------------------------------
	// GetNewSodaInformation
	// Abstract:
	// --------------------------------------------------------------------------------
	public udtSodaType GetNewSodaInformation(  )
	{
		udtSodaType udtSoda = null;

		try
		{		
			udtSoda = GetValuesFromForm( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtSoda;
	}	
	
	
	// ----------------------------------------------------------------------
	// Name: PutValuesOnForm
	// Abstract: Load the form controls with values for record from database.
	// ----------------------------------------------------------------------
	public void PutValuesOnForm( udtSodaType udtSoda )
	{
		try
		{
			String strSoda = "";

			m_txtSoda.setText( 			udtSoda.strSoda );

		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	
	
	
	

	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	@Override
	public void windowOpened( WindowEvent weSource )
	{
		try
		{
			udtSodaType udtSoda = new CUserDataTypes().new udtSodaType();
			boolean blnResult = false;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Which Soda to load
			udtSoda.intSodaID = m_intSodaToEditID;

			
			//Get Values
			blnResult = CDatabaseUtilities.GetSodaInformationFromDatabase(udtSoda);
			
			//Did it work?
			if (blnResult == true)
			{

					// Yes, load form with values
					PutValuesOnForm( udtSoda );

			}
			else
			{
			//No, warn user 
			CMessageBox.Show( this, "Unable to load Soda Information. \n The form will now close", "Edit Soda Error", enuIconType.Error );
		
			//Close
			setVisible(false);
			}
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		finally
		{
			// We are NOT busy
			CUtilities.SetBusyCursor( this, false );
		}
		
	}

	public void windowActivated( WindowEvent arg0 ){}
	public void windowClosed( WindowEvent arg0 ){}
	public void windowClosing( WindowEvent arg0 ){}
	public void windowDeactivated( WindowEvent arg0 ){}
	public void windowDeiconified( WindowEvent arg0 ){}
	public void windowIconified( WindowEvent arg0 ){}




}
// --------------------------------------------------------------------------------
// Name: <Rodney Clark>
// Class: IT-161 Java #1
// Abstract: This homework handles the event listener for the calculator buttons
// --------------------------------------------------------------------------------
package SodaCompanies;
// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*;

import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtSodaCompanyType;
// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------
import Utilities.CUserDataTypes.udtSodaType;



@SuppressWarnings("serial")
public class DEditSodaCompany extends JDialog implements ActionListener
{
	// --------------------------------------------------------------------------------
	// Name: Controls
	// Abstract: This class ...
	// --------------------------------------------------------------------------------
	
	//Declare Controls
		@SuppressWarnings("unused")
		private JLabel   m_lblName = null;
		private CTextBox m_txtName = null;
		
		@SuppressWarnings("unused")
		private JLabel   m_lblAddress = null;
		private CTextBox m_txtAddress = null;
		
		@SuppressWarnings("unused")
		private JLabel   m_lblCity = null;
		private CTextBox m_txtCity = null;
		
		@SuppressWarnings("unused")
		private JLabel   m_lblState = null;
		CComboBox m_cmbState  = null;
		
		@SuppressWarnings("unused")
		private JLabel   m_lblZipCode = null;
		private CTextBox m_txtZipCode = null;
		
		// Phone Number
		@SuppressWarnings("unused")
		private JLabel m_lblPhoneNumber = null;
		private CTextBox m_txtPhoneNumber = null;
		
		// Email
		@SuppressWarnings("unused")
		private JLabel m_lblEmailAddress = null;
		private CTextBox m_txtEmailAddress = null;
		
		@SuppressWarnings("unused")
		private JLabel   m_lblRequiredField = null;

		private JButton  m_btnOK = null;
		private JButton  m_btnCancel = null;

		private int m_intNewSodaCompanyID = 0;
		private boolean m_blnResult = false;
			
// --------------------------------------------------------------------------------
// Name: Controls
// Abstract: This class ...
// --------------------------------------------------------------------------------
	public DEditSodaCompany( JDialog dlgParent, int intSodaCompanyToEditID)
	{
		super( dlgParent, true ); //Modal
		
		try
		{
			//Save ID for loading and saving
			m_intNewSodaCompanyID = intSodaCompanyToEditID;
			
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
			int intHeight = 400;
			int intWidth = 650;
			
			//Title
			setTitle ("Edit Soda Company");
			
			//Size
			setSize( intWidth, intHeight);
			
			//Center Screen
			CUtilities.CenterOwner( this );
			
			//No Resize
			setResizable(false);
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
			m_lblName = CUtilities.AddLabel( this, "Name:*", 20, 20 );
			m_txtName = CUtilities.AddTextBox( this, "", 20, 160, 25, 185, 50 );

	
			// Street Address
			m_lblAddress = CUtilities.AddLabel( this, "Address:", 55, 20 );
			m_txtAddress = CUtilities.AddTextBox( this, "", 55, 160, 25, 185, 50 );

			// City
			m_lblCity = CUtilities.AddLabel( this, "City:", 90, 20 );
			m_txtCity = CUtilities.AddTextBox( this, "", 90, 160, 25, 185, 50 );
			
			// State
			m_lblState = CUtilities.AddLabel( this, "State:", 125, 20 );
			m_cmbState = CUtilities.AddComboBox( this, 125, 160, 25, 184 );

			// Zip Code
			m_lblZipCode = CUtilities.AddLabel( this, "Zip Code:", 160, 20 );
			m_txtZipCode = CUtilities.AddTextBox( this, "", 160, 160, 25, 185, 50 );

			// Phone Number
			m_lblPhoneNumber = CUtilities.AddLabel( this, "Phone Number:", 195, 20 );
			m_txtPhoneNumber = CUtilities.AddTextBox( this, "", 195, 160, 25, 185, 50 );
			
			// Email Address
			m_lblEmailAddress = CUtilities.AddLabel( this, "Email Address:", 230, 20 );
			m_txtEmailAddress = CUtilities.AddTextBox( this, "", 230, 160, 25, 185, 50 );

			//Required
			m_lblRequiredField = CUtilities.AddRequiredFieldLabel( this, 260, 65 );
			
			//Load OK Button
			m_btnOK = CUtilities.AddButton( this, this, "OK", 285, 75, 30, 100 );	
			
			//Load Cancel Button
			m_btnCancel = CUtilities.AddButton( this, this, "Cancel", 285, 225, 30, 100 );									
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
			//Make a suitcase for moving data
			udtSodaCompanyType udtNewSodaCompany = new CUserDataTypes().new udtSodaCompanyType();
			
			//Load suitcase with data from form
			udtNewSodaCompany.intSodaCompanyID = m_cmbState.GetSelectedItemValue();
			udtNewSodaCompany.strName = m_txtName.getText();
			udtNewSodaCompany.strAddress = m_txtAddress.getText();
			udtNewSodaCompany.strCity = m_txtCity.getText();
			udtNewSodaCompany.intStateID = m_cmbState.GetSelectedItemValue( );
			udtNewSodaCompany.strZipCode = m_txtZipCode.getText();
			udtNewSodaCompany.strPhoneNumber = m_txtPhoneNumber.getText();
			udtNewSodaCompany.strEmailAddress = m_txtEmailAddress.getText();

			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Try to save the data
			blnResult = CDatabaseUtilities.EditSodaCompanyDatabase( udtNewSodaCompany );
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
				
				//Name
				if ( m_txtName.getText().equals( "" ) == true )
				{
					strErrorMessage += "-Soda Company Name cannot be blank\n";
					blnIsValidData = false;
				}
				
				//Address
				if ( m_txtAddress.getText( ).equals( "" ) == true )
				{
					strErrorMessage += "-Address cannot be blank\n";
					blnIsValidData = false;
				}
				
				//City
				if ( m_txtCity.getText( ).equals( "" ) == true )
				{
					strErrorMessage += "-City cannot be blank\n";
					blnIsValidData = false;
				}
				
				//State
				//if ( m_txtMiddleName.getText( ).equals( "" ) == true )
				//{
				//	strErrorMessage += "-State cannot be blank\n";
				//	blnIsValidData = false;
				//}
				
				//ZipCode
				if ( m_txtZipCode.getText( ).equals( "" ) == true )
				{
					strErrorMessage += "-Zip Code cannot be blank\n";
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
	

	// ----------------------------------------------------------------------
	// Name: GetValuesFromForm
	// Abstract: Load a structure suitcase with all the values from the form.
	// ----------------------------------------------------------------------
	private udtSodaCompanyType GetValuesFromForm(  udtSodaCompanyType udtSodaCompany)
	{
		
		try
		{
			// Make a suitcase instance
			udtSodaCompany = new CUserDataTypes( ).new udtSodaCompanyType( );
			
			// Load suitcase with values from form
			udtSodaCompany.intSodaCompanyID 			= m_intNewSodaCompanyID;
			udtSodaCompany.strName = m_txtName.getText();
			udtSodaCompany.strAddress =  m_txtAddress.getText();
			udtSodaCompany.strCity =  m_txtCity.getText();
			udtSodaCompany.intStateID 			= m_cmbState.GetSelectedItemValue( );
			udtSodaCompany.strZipCode =  m_txtZipCode.getText();
			udtSodaCompany.strPhoneNumber =  m_txtPhoneNumber.getText();
			udtSodaCompany.strEmailAddress =  m_txtEmailAddress.getText();
			

		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtSodaCompany;
	}
	
	// ----------------------------------------------------------------------
	// Name: PutValuesOnForm
	// Abstract: Load the form controls with values for record from database.
	// ----------------------------------------------------------------------
	public void PutValuesOnForm( udtSodaCompanyType udtSodaCompany )
	{
		try
		{


			m_txtName.setText( 				udtSodaCompany.strName );
			m_txtAddress.setText( 			udtSodaCompany.strAddress );
			m_txtCity.setText( 				udtSodaCompany.strCity );
			m_cmbState.SetSelectedIndexByValue( udtSodaCompany.intStateID );
			m_txtZipCode.setText( 			udtSodaCompany.strZipCode );
			m_txtPhoneNumber.setText( 		udtSodaCompany.strPhoneNumber );
			m_txtEmailAddress.setText( 		udtSodaCompany.strEmailAddress );

			
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
	}
	
	// --------------------------------------------------------------------------------
	// GetNewSodaInformation
	// Abstract:
	// --------------------------------------------------------------------------------
	public udtSodaCompanyType GetNewSodaCompanyInformation(  )
	{
		udtSodaCompanyType udtSodaCompany = null;

		try
		{		
			udtSodaCompany = GetValuesFromForm( udtSodaCompany );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtSodaCompany;
	}	
	
	

	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	public void windowOpened( )
	{
		try
		{
			udtSodaCompanyType udtSodaCompany = new CUserDataTypes().new udtSodaCompanyType();
			boolean blnResult = false;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Which SodaCompany to load
			udtSodaCompany.intSodaCompanyID = m_intNewSodaCompanyID;
			
			//Get Values
			blnResult = CDatabaseUtilities.GetSodaCompanyInformationFromDatabase(udtSodaCompany);
			

			//Did it work?
			if ( blnResult == true )
			{
			
				PutValuesOnForm( udtSodaCompany );
				
			}
			else
			{
				//No, warn user 
				CMessageBox.Show( this, "Unable to load Soda Company Information. \n The form will now close", "Edit Soda Company Error", enuIconType.Error );
			
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

	// --------------------------------------------------------------------------------
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


}
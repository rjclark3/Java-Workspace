// --------------------------------------------------------------------------------
// Name: <Rodney Clark>
// Class: IT-161 Java #1
// Abstract: This homework handles the event listener for the calculator buttons
// --------------------------------------------------------------------------------
package Default;
// --------------------------------------------------------------------------------
// Import
// --------------------------------------------------------------------------------
import java.awt.*;

import java.awt.event.*;
import java.text.NumberFormat;
import javax.swing.*;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtPlayerType;
// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------


@SuppressWarnings("serial")
public class DAddPlayer extends JDialog implements ActionListener, WindowListener
{
	// --------------------------------------------------------------------------------
	// Name: Controls
	// Abstract: This class ...
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Name: Controls
	// Abstract: This class ...
	// --------------------------------------------------------------------------------
	
	//Declare Controls
	@SuppressWarnings("unused")
	private JLabel   m_lblLastName = null;
	private CTextBox m_txtLastName = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblMiddleName = null;
	private CTextBox m_txtMiddleName = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblFirstName = null;
	private CTextBox m_txtFirstName = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblAddress = null;
	private CTextBox m_txtAddress = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblCity = null;
	private CTextBox m_txtCity = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblState = null;
	private CComboBox m_cmbState  = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblZipCode = null;
	private CTextBox m_txtZipCode = null;
	
	// Home Phone Number
	@SuppressWarnings("unused")
	private JLabel m_lblHomePhoneNumber = null;
	private CTextBox m_txtHomePhoneNumber = null;

	// Salary
	@SuppressWarnings("unused")
	private JLabel m_lblSalary = null;
	private CTextBox m_txtSalary = null;

	// Date of Birth
	@SuppressWarnings("unused")
	private JLabel m_lblDateOfBirth = null;
	private CTextBox m_txtDateOfBirth = null;

	// Sex
	@SuppressWarnings("unused")
	private JLabel m_lblSex = null;
	private ButtonGroup m_bgpSex = null;
	private JRadioButton m_radSexFemale = null;
	private JRadioButton m_radSexMale = null;
	
	// Most Valuable Player
	@SuppressWarnings("unused")
	private JLabel m_lblMostValuablePlayer = null;
	private JCheckBox m_chkMostValuablePlayer = null;

	// Email Address
	@SuppressWarnings("unused")
	private JLabel m_lblEmailAddress = null;
	private CTextBox m_txtEmailAddress = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblRequiredField = null;

	private JButton  m_btnOK = null;
	private JButton  m_btnCancel = null;
	
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Properties
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	// Never make public properties.  
	// Make protected or private with public get/set methods
	
	private int m_intNewPlayerID = 0;
	private boolean m_blnResult = false;
	public DAddPlayer( JDialog dlgParent)
	{
		super( dlgParent, true );
		
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
			int intHeight = 510;
			int intWidth = 370;
			
			//Title
			setTitle ("Add Player");
			
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
// Name: AddControls
// Abstract: Add controls on form
// -------------------------------------------------------------
	
	public void AddControls()
	{
		try
		{
			//Clear Layout manager
			CUtilities.ClearLayoutManager( this );
			
			// First Name
			m_lblFirstName = CUtilities.AddLabel( this, "First Name:*", 25, 20 );
			m_txtFirstName = CUtilities.AddTextBox( this, "", 21, 160, 25, 185, 50 );
	
			// Middle Name
			m_lblMiddleName = CUtilities.AddLabel( this, "Middle Name:", 55, 20 );
			m_txtMiddleName = CUtilities.AddTextBox( this, "", 51, 160, 25, 185, 50 );
	
			// Last Name
			m_lblLastName = CUtilities.AddLabel( this, "Last Name:*", 85, 20 );
			m_txtLastName = CUtilities.AddTextBox( this, "", 81, 160, 25, 185, 50 );
	
			// Street Address
			m_lblAddress = CUtilities.AddLabel( this, "Address:", 115, 20 );
			m_txtAddress = CUtilities.AddTextBox( this, "", 111, 160, 25, 185, 50 );

			// City
			m_lblCity = CUtilities.AddLabel( this, "City:", 145, 20 );
			m_txtCity = CUtilities.AddTextBox( this, "", 141, 160, 25, 185, 50 );
			
			// State
			m_lblState = CUtilities.AddLabel( this, "State:", 175, 20 );
			m_cmbState = CUtilities.AddComboBox( this, 171, 160, 25, 184 );

			// Zip Code
			m_lblZipCode = CUtilities.AddLabel( this, "Zip Code:", 205, 20 );
			m_txtZipCode = CUtilities.AddTextBox( this, "", 201, 160, 25, 185, 50 );

			// Home Phone Number
			m_lblHomePhoneNumber = CUtilities.AddLabel( this, "Home Phone Number:", 235, 20 );
			CUtilities.AddColoredSizedLabel( this, 250, 25, "###-#### or ###-###-####", "999999", 0.8f );
			m_txtHomePhoneNumber = CUtilities.AddTextBox( this, "", 231, 160, 25, 185, 50 );

			// Salary
			m_lblSalary = CUtilities.AddLabel( this, "Salary:", 265, 20 );
			m_txtSalary = CUtilities.AddTextBox( this, "", 261, 160, 25, 185, 50 );
			m_txtSalary.setHorizontalAlignment( JTextField.RIGHT );	// right align numbers

			// Date Of Birth
			m_lblDateOfBirth = CUtilities.AddLabel( this, "Date of Birth:", 295, 20 );
				CUtilities.AddColoredSizedLabel( this, 310, 25, "yyyy/mm/dd", "999999", 0.8f );
			m_txtDateOfBirth = CUtilities.AddTextBox( this, "", 291, 160, 25, 185, 50 );

			// Sex
			m_lblSex = CUtilities.AddLabel( this, "Sex:", 325, 20 );
			m_bgpSex = new ButtonGroup( );
			m_radSexFemale = CUtilities.AddRadioButton( this, this, m_bgpSex, "Female", 321, 156, true );
			m_radSexMale = CUtilities.AddRadioButton( this, this, m_bgpSex, "Male", 321, 225 );

			// Most Valuable Player
			m_lblMostValuablePlayer = CUtilities.AddLabel( this, "Most Valuable Player:", 355, 20 );
			m_chkMostValuablePlayer = CUtilities.AddCheckBox( this, "", 353, 156 );

			// Email Address
			m_lblEmailAddress = CUtilities.AddLabel( this, "Email Address:", 385, 20 );
			m_txtEmailAddress = CUtilities.AddTextBox( this, "", 381, 160, 25, 185, 50 );

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
			this.dispose( );
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
	private void btnOK_Click()
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

	
	// ----------------------------------------------------------------------
	// Name: GetValuesFromForm
	// Abstract: Load a structure suitcase with all the values from the form.
	// ----------------------------------------------------------------------
	private udtPlayerType GetValuesFromForm( )
	{
		udtPlayerType udtPlayer = null;
		
		try
		{
			// Make a suitcase instance
			udtPlayer = new CUserDataTypes( ).new udtPlayerType( );
			
			// Load suitcase with values from form
			udtPlayer.intPlayerID 			= m_intNewPlayerID;
			udtPlayer.strFirstName 			= m_txtFirstName.getText( );
			udtPlayer.strMiddleName 		= m_txtMiddleName.getText( );
			udtPlayer.strLastName 			= m_txtLastName.getText( );
			udtPlayer.strAddress 			= m_txtAddress.getText( );
			udtPlayer.strCity 				= m_txtCity.getText( );
			udtPlayer.intStateID 			= m_cmbState.GetSelectedItemValue( );
			udtPlayer.strZipCode			= m_txtZipCode.getText( );
			udtPlayer.strHomePhoneNumber 	= m_txtHomePhoneNumber.getText( );
			udtPlayer.bdecSalary			= CUtilities.ConvertStringToBigDecimal( m_txtSalary.getText( ) );

			// Date of Birth
			if( m_txtDateOfBirth.getText().equals( "" ) == false )
			{
				udtPlayer.sdtmDateOfBirth = CUtilities.ConvertStringToSQLDate( m_txtDateOfBirth.getText( ) );
			}
			else
			{
				// Can't have null so substitute a "zeroth" date
				udtPlayer.sdtmDateOfBirth = CUtilities.ConvertStringToSQLDate( "1800/01/01" );	
			}
			
			// Sex
			if( m_radSexFemale.isSelected( ) == true ) udtPlayer.intSexID = CConstants.intSEX_FEMALE;
			if( m_radSexMale.isSelected( )   == true ) udtPlayer.intSexID = CConstants.intSEX_MALE;
			
			// Most Valuable Player
			if( m_chkMostValuablePlayer.isSelected( ) == true ) udtPlayer.blnMostValuablePlayer = true;
			
			// Email Address
			udtPlayer.strEmailAddress 		= m_txtEmailAddress.getText( );
		}
		catch( Exception excError )
		{
			// Display Error Message
			CUtilities.WriteLog( excError );
		}
		
		return udtPlayer;
	}
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	private boolean SaveData()
	{
		
		boolean blnResult = false;
		
		try
		{
			// Make a suitcase for moving data
			udtPlayerType udtNewPlayer = null;
			
			// Get the values from the form
			udtNewPlayer = GetValuesFromForm( );

			// We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Try to save the data
			blnResult = CDatabaseUtilities.AddPlayerToDatabase( udtNewPlayer );
			
			// Did it work?
			if( blnResult == true )
			{
				// Yes, save the new Player ID
				m_intNewPlayerID = udtNewPlayer.intPlayerID;
			}
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
			if ( m_txtLastName.getText().equals( "" ) == true )
			{
				strErrorMessage += "-Last Name cannot be blank\n";
				blnIsValidData = false;
			}
			
			//First Name
			if ( m_txtFirstName.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-First Name cannot be blank\n";
				blnIsValidData = false;
			}
			
			//Middle Name
			if ( m_txtMiddleName.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-Middle Name cannot be blank\n";
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
			if ( m_txtMiddleName.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-State cannot be blank\n";
				blnIsValidData = false;
			}
			
			//ZipCode
			if ( m_txtZipCode.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-Zip Code cannot be blank\n";
				blnIsValidData = false;
			}
			
//			// Home Phone Number blank?
//			if( m_txtHomePhoneNumber.getText( ).equals( "" ) == false )
//			{
//				// No, is it a valid format?
//				if( CUtilities.IsValidPhoneNumber( m_txtHomePhoneNumber.getText( ) ) == false )
//				{
//					strErrorMessage += "-Home Phone Number is invalid\n";
//					blnIsValidData = false;
//				}
//			}
			
			// Salary blank?
			if( m_txtSalary.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsCurrency( m_txtSalary.getText( ) ) == false )
				{
					strErrorMessage += "-Salary is invalid\n";
					blnIsValidData = false;
				}
			}
			
			// Date of Birth blank?
			if( m_txtDateOfBirth.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsValidDate( m_txtDateOfBirth.getText( ) ) == false )
				{
					strErrorMessage += "-Date of Birth is invalid\n";
					blnIsValidData = false;
				}
			}
			
			// EmailAddress blank?
			if( m_txtEmailAddress.getText( ).equals( "" ) == false )
			{
				// No, is it a valid format?
				if( CUtilities.IsValidEmailAddress( m_txtEmailAddress.getText( ) ) == false )
				{
					strErrorMessage += "-Email Address is invalid\n";
					blnIsValidData = false;
				}
			}

			// Bad data?
			if( blnIsValidData == false )
			{
				// Yes, warn the user
				CMessageBox.Show( this, strErrorMessage, 
								  getTitle( ) + " Error", enuIconType.Warning );
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
	// GetNewPlayerInformation
	// Abstract:
	// --------------------------------------------------------------------------------
	public udtPlayerType GetNewPlayerInformation(  )
	{
		udtPlayerType udtPlayer = null;

		try
		{
			udtPlayer = GetValuesFromForm( );
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
		
		return udtPlayer;
	}	
	
	
	// --------------------------------------------------------------------------------
	// --------------------------------------------------------------------------------
	// Name:
	// Abstract:
	// --------------------------------------------------------------------------------
	public boolean GetResult()
	{
		try
		{
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}
		
		return m_blnResult;
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
			boolean blnResult = false;
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			// Load the states list
			CDatabaseUtilities.LoadComboBoxFromDatabase( "TStates", 
																	 "intStateID", 
																	 "strState", 
																	 m_cmbState );
			
			//Did it work?
			if (blnResult == false)
			{

			//No, warn user 
			CMessageBox.Show( this, "Unable to load Player Information. \n The form will now close", "Edit Player Error", enuIconType.Error );
		
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
	
	

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
import javax.swing.*;
import Utilities.*;
import Utilities.CMessageBox.enuIconType;
import Utilities.CUserDataTypes.udtTeamType;
// --------------------------------------------------------------------------------
// Name: CHomework?
// Abstract: This class ...
// --------------------------------------------------------------------------------


@SuppressWarnings("serial")
public class DAddTeam extends JDialog implements ActionListener
{
	// --------------------------------------------------------------------------------
	// Name: Controls
	// Abstract: This class ...
	// --------------------------------------------------------------------------------
	
	//Declare Controls
	@SuppressWarnings("unused")
	private JLabel   m_lblTeam = null;
	private CTextBox m_txtTeam = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblMascot = null;
	private CTextBox m_txtMascot = null;
	
	@SuppressWarnings("unused")
	private JLabel   m_lblRequiredField = null;

	private JButton  m_btnOK = null;
	private JButton  m_btnCancel = null;

	private int m_intNewTeamID = 0;
	private boolean m_blnResult = false;
		
	
	public DAddTeam( JDialog dlgParent)
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
			int intHeight = 175;
			int intWidth = 285;
			
			//Title
			setTitle ("Add Team");
			
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
// Name: AddControls
// Abstract: Add controls on form
// -------------------------------------------------------------
	
	public void AddControls()
	{
		try
		{
			//Clear Layout manager
			CUtilities.ClearLayoutManager( this );
			
			//Team
			m_lblTeam = CUtilities.AddLabel( this, "Team:*", 25, 20 );	
			m_txtTeam = CUtilities.AddTextBox( this, 21, 75, 25, 185, 50 );
					
			//Mascot
			m_lblMascot = CUtilities.AddLabel( this, "Mascot:*", 55, 20 );	
			m_txtMascot = CUtilities.AddTextBox( this, 51, 75, 25, 185, 50 );

			//Required
			m_lblRequiredField = CUtilities.AddRequiredFieldLabel( this, 77, 70 );
			
			//Load OK Button
			m_btnOK = CUtilities.AddButton( this, this, "OK", 100, 30, 30, 100 );	
			
			//Load Cancel Button
			m_btnCancel = CUtilities.AddButton( this, this, "Cancel", 100, 155, 30, 100 );							
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
			//Make a suitcase for moving data
			udtTeamType udtNewTeam = new CUserDataTypes().new udtTeamType();
			
			//Load suitcase with data from form
			udtNewTeam.intTeamID = 0; 
			udtNewTeam.strTeam = m_txtTeam.getText( );
			udtNewTeam.strMascot = m_txtMascot.getText( );
			
			//We are busy
			CUtilities.SetBusyCursor( this, true );
			
			//Try to save the data
			blnResult = CDatabaseUtilities.AddTeamToDatabase( udtNewTeam );
			
			//Did it work?
			if(blnResult == true)
			{
				//Yes, save the new if
				m_intNewTeamID = udtNewTeam.intTeamID;
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
			
			//Team
			if ( m_txtTeam.getText().equals( "" ) == true )
			{
				strErrorMessage += "-Team cannot be blank\n";
				blnIsValidData = false;
			}
			
			//Mascot
			if ( m_txtMascot.getText( ).equals( "" ) == true )
			{
				strErrorMessage += "-Mascot cannot be blank\n";
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
	// GetNewTeamInformation
	// Abstract:
	// --------------------------------------------------------------------------------
	public udtTeamType GetNewTeamInformation(  )
	{
		udtTeamType udtTeam = null;

		try
		{
			udtTeam = new CUserDataTypes().new udtTeamType();
			
			udtTeam.intTeamID = 0;
			udtTeam.strTeam = m_txtTeam.getText();
			udtTeam.strMascot = m_txtMascot.getText();
		}
		catch(Exception excError)
		{
			CUtilities.WriteLog( excError );
		}	
		
		return udtTeam;
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
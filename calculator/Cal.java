/**Catherine Rodriguez
 * Calculator Application
 * Uses StackClass.java to store and perform operations on user input values
 * **/
package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel; 

import net.miginfocom.swing.MigLayout; 
import javax.swing.JButton;
 
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter; 

public class Cal
{

	private JFrame frmCalculator;
	
	private final int MAX_LEN = 16; // max length of user input/result
	
	// instance variables
	private StackClass stack; // list to store operands

	private char operator;	  // current operation type for calculation
	private int length;		  // current length of input/result 
	private int decLen;       // current number of values after decimal point
	
	// indicators
	private boolean reqInput; // check if digit input is required
	private boolean swapSign; // check if sign changed (false is positive)
	private boolean hasDecPt; // check if lblResult has a decimal point
	
	// labels
	private JLabel lblResult;  // display inputs & reduced stack operands
	private JLabel lblInput;   // display history of inputs & operations
	
	// buttons for input
	private JButton btnDecPt;  // input decimal point
	private JButton btnSign;   // input positive/negative sign
	private JButton btn9;	   // buttons to input a digit [0-9]
	private JButton btn8;
	private JButton btn7;
	private JButton btn6;
	private JButton btn5;
	private JButton btn4;
	private JButton btn3;
	private JButton btn2;
	private JButton btn1;
	private JButton btn0;

	// buttons for clearing
	private JButton btnC;	  // clear all user input
	private JButton btnCE;	  // clear current entry only
	private JButton btnDel;	  // delete last value input

	// buttons for setting operator & performing calculation on operands
	private JButton btnEql;   // perform current operation
	private JButton btnAdd;   // adds operands together
	private JButton btnSub;   // subtracts 2nd operand from 1st
	private JButton btnMul;   // multiplies operands
	private JButton btnDiv;   // divides 2nd operand from 1st
	private JButton btnMod;   // takes modulus 2nd operand from 1st
	private JButton btnSqR;   // takes square root of operand
	private JButton btnPow;   // takes operand to input power
	private JButton btnRec;   // takes reciprocal of operand

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Cal window = new Cal();
					window.frmCalculator.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Cal()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{ 
		Font btnFont = new Font("Segoe UI Historic", Font.PLAIN, 28);

		stack = new StackClass(); // create list to hold operands

		reqInput = false; // does not initially require user input of digits
		swapSign = false; // input initially positive 
		hasDecPt = false; // doesn't initially have a decimal point
		
		length = 0;		  // length of input/result is initially zero
		decLen = 0;		  // number of decimal nums is initially zero 
		
		btnMod = new JButton("%");
		btnMod.setEnabled(false);
		btnMod.setForeground(new Color(204, 204, 204));
		btnMod.setBackground(new Color(0, 0, 102));
		btnMod.setBorder(null);
		btnMod.setFont(btnFont);
		btnMod.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				inputNum(""); // call inputNum to update main display
			}
		}); // end btnMod actionListener

		btnSqR = new JButton("SR");
		btnSqR.setEnabled(false);
		 
		btnSqR.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				inputNum(""); // call inputNum to update main display
			}
		}); // end btnSqR actionListener

		btnPow = new JButton("PW");
		btnPow.setEnabled(false);
		btnPow.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				inputNum(""); // call inputNum to update main display
			}
		}); // end btnPow actionListener

		btnRec = new JButton("RC");
		btnRec.setEnabled(false);
		 
		btnRec.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				inputNum(""); // call inputNum to update main display
			}
		}); // end btnRec actionListener

		btnC = new JButton("C");
		btnC.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				lblInput.setText("");   // clear history of input
				lblResult.setText("0"); // reset main label to 0
				setTextFit();
				
				length = 0;				// reset length of input/result to zero
				decLen = 0;
				
				reqInput = false;		// reset more input required indicator
				swapSign = false;		// reset sign indicator to initially positive
				hasDecPt = false;

				stack.clear();			// remove all items from the stack

			}
		}); // end btnC actionListener

		btnCE = new JButton("CE");
		btnCE.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				lblResult.setText("0"); // reset main label to 0
				setTextFit();
				
				length = 0;				// reset length of input/result to zero
				decLen = 0;
				
				reqInput = false;		// reset more input required indicator
				swapSign = false;		// reset sign indicator to initially positive
				hasDecPt = false;
				 
			}
		}); // end btnCE actionListener

		btnDel = new JButton("Del");
		btnDel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String strResult = lblResult.getText();
				
				// if user input, delete remove last char from main label
				if (!reqInput)
				{
					if(hasDecPt && strResult.endsWith("."))
					{
						hasDecPt = false;
						decLen = 0;
					}
					else if(hasDecPt)
						decLen--; 
					
					length--; 
					
					strResult = strResult.substring(0, strResult.length() - 1);
					lblResult.setText(strResult);
				}
				// if all values removed, reset main label to 0
				if (strResult.equals(""))
					lblResult.setText("0");
				
				setTextFit();
			}
		}); // end btnDel actionListener

		btnEql = new JButton("=");
		btnEql.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//operator = '=';     // set operator for addition
				operationOnClick(); // call operationOnClick to determine next
									 // step
			}
		}); // end btnEql actionListener

		btnAdd = new JButton("+");
		btnAdd.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				operator = '+';     // set operator for addition
				operationOnClick(); // call operationOnClick to determine next
									 // step

			}
		}); // end btnAdd actionListener

		btnSub = new JButton("-");
		btnSub.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				operator = '-';     // set operator for subtraction
				operationOnClick(); // call operationOnClick to determine next
									 // step

			}
		}); // end btnSub actionListener

		btnMul = new JButton("x"); 
		btnMul.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				operator = '*';     // set operator for multiplication
				operationOnClick(); // call operationOnClick to determine next
									 // step
			}
		}); // end btnMul actionListener

		btnDiv = new JButton("/");
		btnDiv.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				operator = '/';     // set operator for division
				operationOnClick(); // call operationOnClick to determine next
									 // step

			}
		}); // end btnDiv actionListener

		btnSign = new JButton("+-");
		btnSign.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				// if swapSign is false, make value negative
				if (!swapSign && !reqInput)
				{
					lblResult.setText("-" + lblResult.getText());
					setTextFit();
					swapSign = true; // toggle value of sign indicator

				}
				// else remove negative symbol
				else if (!reqInput)
				{
					lblResult.setText(lblResult.getText().substring(1,
							lblResult.getText().length()));
					setTextFit();
					swapSign = false; // toggle value of sign indicator
				}
			}
		}); // end btnSign actionListener

		btnDecPt = new JButton("."); 
		btnDecPt.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				if (!hasDecPt)
				{
					inputNum(".");   // call inputNum to update main display
					hasDecPt = true; // result now has a decimal point
				}
			}
		}); // end btn actionListener

		// create buttons for numbers
		btn9 = new JButton("9"); 
		btn8 = new JButton("8"); 
		btn7 = new JButton("7"); 
		btn6 = new JButton("6");
		btn5 = new JButton("5");
		btn4 = new JButton("4");
		btn3 = new JButton("3");
		btn2 = new JButton("2");
		btn1 = new JButton("1");
		btn0 = new JButton("0");
		 
		
		frmCalculator = new JFrame();
		
		frmCalculator.getContentPane().setForeground(new Color(255, 255, 255));
		frmCalculator.getContentPane().setBackground(new Color(0, 0, 102));
		frmCalculator.setBackground(new Color(0, 0, 102));
		frmCalculator.setForeground(new Color(255, 255, 255));
		frmCalculator.setTitle("Calculator");
		frmCalculator.setFont(new Font("Segoe UI Historic", Font.PLAIN, 28));
		frmCalculator.setBounds(100, 100, 480, 838);
		frmCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCalculator.getContentPane().setLayout(
				new MigLayout("wrap 1", "[grow, fill]", "[grow, fill]"));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 102));
		panel.setForeground(new Color(255, 255, 255));
		panel.setBorder(null);
		panel.setLayout(new MigLayout("wrap 1", "[grow,fill]",	"[grow,fill][grow,fill]"));
		//panel.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill][grow,fill]"));

		lblInput = new JLabel("");
		lblInput.setForeground(new Color(0, 102, 204));
		lblInput.setBackground(Color.WHITE);
		lblInput.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInput.setFont(new Font("Segoe UI Historic", Font.PLAIN, 18));

		lblResult = new JLabel("0");
		lblResult.setForeground(new Color(51, 102, 204));
		lblResult.setBackground(Color.WHITE);
		lblResult.setHorizontalAlignment(SwingConstants.RIGHT);
		lblResult.setFont(new Font("Segoe UI Historic", Font.PLAIN, 52));

		// display panel to hold labels for input history and current
		// result/input
		JPanel display = new JPanel();
		display.setForeground(new Color(255, 255, 255));
		display.setBackground(new Color(0, 51, 153));
		display.setFont(new Font("Segoe UI Historic", Font.PLAIN, 36));
		display.setBorder(null);
		display.setLayout(new MigLayout("", "[400px:n,grow,fill]", "[30px:n,grow,fill][60px:n,grow,fill]"));

		// add labels to the display panel
		display.add(lblInput, "cell 0 0,alignx right,aligny center");
		display.add(lblResult, "cell 0 1,alignx right,aligny center");

		// button panel to hold buttons
		JPanel buttons = new JPanel();
		buttons.setBackground(new Color(0, 0, 102));
		buttons.setFont(new Font("Segoe UI Historic", Font.PLAIN, 28));
		buttons.setForeground(new Color(204, 204, 204));
		buttons.setBorder(new EmptyBorder(0, 0, 0, 0));
		buttons.setLayout(new MigLayout("wrap 4",
				"[100px,sizegroup grow,fill][100px,sizegroup grow,fill][100px,sizegroup grow,fill][100px,sizegroup grow,fill]",
				"[100px:100px,sizegroup grow,fill][100px,sizegroup grow,fill][100px,sizegroup grow,fill][100px,sizegroup grow,fill][100px,sizegroup grow,fill][100px,sizegroup grow,fill]"));

		// add buttons to the button panel
		buttons.add(btnMod, "grow");
		buttons.add(btnSqR, "grow");
		buttons.add(btnPow, "grow");
		buttons.add(btnRec, "grow");
		buttons.add(btnC, "grow");
		buttons.add(btnCE, "grow");
		buttons.add(btnDel, "grow");
		buttons.add(btnDiv, "grow");
		buttons.add(btn7, "grow");
		buttons.add(btn8, "grow");
		buttons.add(btn9, "grow");
		buttons.add(btnMul, "grow");
		buttons.add(btn4, "grow");
		buttons.add(btn5, "grow");
		buttons.add(btn6, "grow");
		buttons.add(btnSub, "grow");
		buttons.add(btn1, "grow");
		buttons.add(btn2, "grow");
		buttons.add(btn3, "grow");
		buttons.add(btnAdd, "grow");
		buttons.add(btnSign, "grow");
		buttons.add(btn0, "grow");
		buttons.add(btnDecPt, "grow");
		buttons.add(btnEql, "grow");

		// loop through buttons in Buttons JPanel
		for (Component comp : buttons.getComponents()) 
		{
			// remove text focus border and add hover effect
		    if (comp instanceof JButton) 
		    {  
		    	((JButton)comp).setFocusPainted(false);
		    	createNewButton((JButton)comp);
		    } 
		} // end for each button in buttons
		 
		// add JPanels to the main window
		panel.add(display, "alignx center");
		panel.add(buttons, "cell 0 1,growx,sizegroupx ,sizegroupy ,aligny top");

		// add panel to frame
		frmCalculator.getContentPane().add(panel, "alignx center,aligny center");

	} // end initialize

	  
	public void createNewButton(JButton btn)
	{ 
		Font btnFont = new Font("Segoe UI Historic", Font.PLAIN, 28);
		
		btn.setBorder(null);
		btn.setForeground(new Color(204, 204, 204));
		btn.setBackground(new Color(0, 0, 102));
		btn.setFont(btnFont);
		
		String btnText = btn.getText();
		
		
		btn.addMouseListener(new MouseAdapter() 
		{
			public void mouseEntered(java.awt.event.MouseEvent e) 
			{
			 JButton button = (JButton)e.getComponent();
		         button.setBackground(new Color(0, 102, 204));
		    }
			public void mouseExited(java.awt.event.MouseEvent e) 
			{
			 JButton button = (JButton)e.getComponent();
		         button.setBackground(new Color(0, 0, 102));
		    }
		});
		
		if(btnText.matches("[0-9]"))
		{
			btn.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					if(btnText.matches("[1-9]"))
						length++;
					
					inputNum(btnText); // call inputNum to update main display
				}
			}); // end btn actionListener

		}
	} // end createNewButton
	 
	protected void operationOnClick()
	{
		String strInput = lblInput.getText();
		
		if (!reqInput)	 // check if required operand values have been input
			calculate(); // call calculate to perform operation on operands in stack
		else 			 // update last operator to reflect current button selection in display
			lblInput.setText(strInput.substring(0, strInput.length() - 1) + " " + operator);

	} // end operationOnClick

	protected void calculate()
	{
		NumberFormat myFormat = NumberFormat.getInstance();
		//myFormat.setMaximumFractionDigits(decLen);
		myFormat.setMaximumFractionDigits(17);
		double result;  // number calculated after performing operation on 2
						  // operands in stack
		char op;		// operator type from selected button (to update
						// display)
		String strNum;	// number input by user

		swapSign = false; // reset sign indicator to positive
		decLen = 0;
		hasDecPt = false;
		
		strNum = lblResult.getText(); // get value from main display label
		String strInput = lblInput.getText();
		
		try
		{
			strNum = String.valueOf(myFormat.parse(strNum));
			double num = Double.parseDouble(strNum); // convert value to double
			
			// if a whole number, remove decimal point
			if (strNum.indexOf('.') != -1 && Double.isFinite(num) 
					&& Double.compare(num, (int) num) == 0)
				strNum = strNum.substring(0, strNum.indexOf('.'));
			  
			num = Double.parseDouble(strNum); // convert value to double
			
			if (!stack.isFull())		// if room in stack
			{
				stack.push(num);	// push the user's number to the stack

				reqInput = true;    // user must enter digits to call
								    // calculate() again

				op = operator;		// store operator type from selected

				if (stack.isFull())  // if no room in stack, num is 2nd operand
									  // - get operation needed to perform
					operator = strInput.charAt(strInput.length() - 1);
				 
					 
					// double numParsed = Double.parseDouble(strNum);
					 lblResult.setText(myFormat.format(num)); // append input to label
					 setTextFit();
				// update history of user calculations with new values & stored
				// operation type
				lblInput.setText(strInput + " " + strNum + " " + op);

			} // end if not full

			if (stack.isFull()) // if no room in stack, perform calculation to
								 // reduce
			{
				// check type of operation
				switch (operator)
				{
					// call corresponding method on stack of operands
					case '+':
						result = stack.add();
						break;
					case '-':
						result = stack.subtract();
						break;
					case '*':
						result = stack.multiply();
						break;
					case '/':
						result = stack.divide();
						break;
					case '=': 
						 
					default:
						result = 0;
						break;

				} // end switch operator type

				strNum = String.valueOf(result);
 
				if (strNum.indexOf('.') != -1 && Double.isFinite(result)
						&& Double.compare(result, (int) result) == 0)
					strNum = strNum.substring(0, strNum.indexOf('.'));
				 
				//myFormat.setMaximumFractionDigits(decLen); 
				lblResult.setText(myFormat.format(result)); // update display
															 // with calculated
															 // result
				setTextFit();
				stack.push(result); // push resulting number to stack as new 1st
									 // operand

				length = 0;				// reset length of input/result to zero
				
				
			} // end full stack

		} // end try
		catch (NumberFormatException ex)
		{

		} // end catch
		catch (ParseException e)
		{
			System.out.println("Error parsing number");
			e.printStackTrace();
		}

	} // end calculate

	protected void inputNum(String strNum)
	{
		NumberFormat myFormat = NumberFormat.getInstance(); 
		myFormat.setMaximumFractionDigits(17);
		String strResult = lblResult.getText(); 

		// if number is 0 or requires input, replace label with input
		if (strResult.equals("0") || reqInput)
		{
			if (strNum.equals(".") && !reqInput)
				lblResult.setText(strResult + strNum); // append input to label
			else if(strNum.equals(".") && reqInput)
				lblResult.setText("0" + strNum); // update main label with input
			else
				lblResult.setText(strNum); // update main label with input

			setTextFit();
			reqInput = false; 		// reset more input required indicator
		}
		else if(length < MAX_LEN|| strNum.equals("."))
		{

			if(hasDecPt)
			{
				decLen++;
				myFormat.setMinimumFractionDigits(decLen);
			}
			else
				myFormat.setMinimumFractionDigits(0);
			
			try
			{
				double numParsed;
				strResult = strResult + strNum;
								 
				strResult = String.valueOf(myFormat.parse(strResult)) ;
				 
				numParsed = Double.parseDouble(strResult);
				
				String strNumParsed = myFormat.format(numParsed);
				
				if(strNum.equals("."))
					lblResult.setText(strNumParsed + strNum); // append input to label
				else 
					lblResult.setText(strNumParsed); 
				setTextFit();
			}
			catch (ParseException e)
			{
				System.out.println("Error parsing number");
				e.printStackTrace();
			}
			 
		} // end else length room to input digits
		
	} // end inputNum

	void setTextFit() 
	{ 
		String text = lblResult.getText();
		
		// Get the original Font from client properties
	    Font originalFont = (Font)lblResult.getClientProperty("originalfont"); 
	    
	    if (originalFont == null) 
	    { // First time we call it: add it
	        originalFont = lblResult.getFont();
	        lblResult.putClientProperty("originalfont", originalFont);
	    }

	    int stringWidth = lblResult.getFontMetrics(originalFont).stringWidth(text);
	    int componentWidth = lblResult.getWidth();

	    if (stringWidth > componentWidth) // Resize only if needed
	    { 
	        // Find out how much the font can shrink in width.
	        double widthRatio = (double)componentWidth / (double)stringWidth;

	        int newFontSize = (int)Math.floor(originalFont.getSize() * widthRatio); // Keep the minimum size

	        // Set the label's font size to the newly determined size.
	        lblResult.setFont(new Font(originalFont.getName(), originalFont.getStyle(), newFontSize));
	    } 
	    else
	    	lblResult.setFont(originalFont); // Text fits, do not change font size

	    lblResult.setText(text);
	    
	} // end setTextFit
	
} // end Calculator class

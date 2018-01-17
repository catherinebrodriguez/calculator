/**
 * Catherine Rodriguez
 * StackClass for Stack-Based Calculator
 * 11/7/2017
 * **/

package calculator;

/**
 * @author cathe
 *
 */
public class StackClass
{
	// constants
	private static final int MAX_SIZE = 2; // max number of items in the list

	// instance variables
	private int top; 		// the top of the list
	private double[] stack; // list of items
	
	// public static void main(String[] args)
	// {
	// StackClass calculator = new StackClass();

	// calculator.push(3);
	// calculator.push(2);

	// } // end main

	/**
	 * Default Constructor
	 * creates initially empty stack 
	 * */
	public StackClass()
	{
		top = -1;						// refers to empty stack
		stack = new double[MAX_SIZE];	// list to hold stack

	} // end default constructor

	/**
	 * purpose: pops the top number off the stack and returns value,
	 * pre: item exists on top of stack 
	 * post: the item on top of stack is returned and popped from the stack
	 * 		 and top, the index of the top item, is decremented 1
	 * prints error if attempt to pop from empty stack
	 * Mutator and Accessor method  
	 * */
	public double pop()
	{
		double num;
		if (isEmpty())
		{
			System.out.println("Error - The stack is empty.");
			num = -1;
		}
		else
		{
			num = stack[top];// top--; // decrement top to item on top of stack
			top--;
		}

		return num;
	} // end pop

	/**
	 * purpose: pushes an item onto the top of stack and returns value
	 * pre: there is room for an item to be added to the stack 
	 * post: the item is pushed on top of stack and returned
	 * 		 and top, the index of the top item, is incremented 1
	 * prints error if attempt to push to full stack
	 * Mutator and Accessor method  
	 * */
	public double push(double item)
	{
		if (isFull())
			System.out.println("Error - The stack is full.");
		else
		{
			top++; // increment top to next empty space
			stack[top] = item;
		}

		return stack[top];

	} // end push

	/**
	 * purpose: returns the item from the top of stack, but doesn't remove it
	 * pre: item exists on top of stack 
	 * post: the item on top of stack is returned
	 * Accessor method  
	 * */
	public double peek()
	{
		return stack[top];

	} // end peek

	/**
	 * purpose: returns if the stack is full
	 * pre: stack is created
	 * post: returns true if stack is full, else returns false
	 * Accessor method  
	 * */
	public boolean isFull()
	{
		return (top == MAX_SIZE - 1);

	} // end isFull

	/**
	 * purpose: returns if the stack is empty
	 * pre: stack is created
	 * post: returns true if stack is empty, else returns false
	 * Accessor method  
	 * */
	public boolean isEmpty()
	{
		return (top == -1);

	} // end isEmpty

	/**
	 * purpose: returns the number of items in the stack
	 * pre: stack is created
	 * post: returns how many items in stack, -1 if empty
	 * Accessor method  
	 * */
	public int size()
	{
		return (top + 1);

	} // end size
	
	/**
	 * purpose: empties the stack
	 * pre: stack is created
	 * post: pops all items off stack until empty
	 * Mutator method  
	 * */
	public void clear()
	{
		while(!isEmpty())
			pop();

	} // end clear
 
	/**
	 * purpose: attempts to find an item in the stack
	 * pre: stack is created
	 * post: returns true if item in stack, else returns false
	 * Accessor method  
	 * */
	public int search(double item)
	{
		int offset = 0; // offset from top of stack

		// loop thru stack to find item
		while (offset < stack.length && item != stack[offset])
		{
			if (item == stack[offset])
				return offset;

			offset++;
		}

		// if not found, -1 indicates item not in stack
		if (item != stack[offset])
			offset = -1;

		return offset;

	} // end search

	/**
	 * purpose: returns a copy of the stack
	 * pre: stack is created 
	 * post: returns a copy of the stack array
	 * Accessor/Mutator method  
	 * */
	public double[] copy()
	{

		double[] newStack = new double[MAX_SIZE]; // list to hold copy

		// iterate through original list to copy each item into new list
		for (int i = 0; i < stack.length; i++)
			newStack[i] = stack[i];

		return newStack;

	} // end copy

	/**
	 * purpose: adds the two items in the stack
	 * pre: stack is created, two items in stack
	 * post: returns the result of addition of the items in stack
	 * Mutator method  
	 * */
	public double add()
	{
		double numTwo = pop(); // top item in the stack
		double numOne = pop(); // 2nd item in the stack

		return (numOne + numTwo);

	} // end add

	/**
	 * purpose: subtracts the two items in the stack
	 * pre: stack is created, two items in stack
	 * post: returns the result of subtraction of the items in stack
	 * Mutator method  
	 * */
	public double subtract()
	{
		double numTwo = pop();
		double numOne = pop();

		return (numOne - numTwo);

	} // end subtract

	/**
	 * purpose: multiplies the two items in the stack
	 * pre: stack is created, two items in stack
	 * post: returns the result of multiplication of the items in stack
	 * Mutator method  
	 * */
	public double multiply()
	{
		double numTwo = pop();
		double numOne = pop();

		return (numOne * numTwo);

	} // end multiply
 

	// NaN > infinity
	// infinity > -infinity
	// 1.95 < NaN
	// 0.0 < NaN
	// 0.0 > -infinity 
	
	/**
	 * purpose: divides the two items in the stack
	 * pre: stack is created, two items in stack
	 * post: returns the result of division of the items in stack
	 * Mutator method  
	 * */
	public double divide()
	{
		double numTwo = pop(); // divisor from top of stack
		double numOne = pop(); // dividend next on stack
		  
		if (Double.compare(numTwo, Double.NaN) == 0) // if divisor not a number
			return Double.NaN;						 	// return Not a Number
		
		if (Double.compare(numOne, Double.NaN) == 0) // if dividend not a number
			return Double.NaN;						 	// return Not a Number

		if (Double.compare(numTwo, 0.0) == 0)		 // if divisor zero
		{
			if (Double.compare(numOne, 0.0) == -1)	 // if dividend < zero,
				return Double.NEGATIVE_INFINITY;		 // return negative infinity
			
			return Double.POSITIVE_INFINITY;		 // else return positive infinity
		}
		if (Double.compare(numTwo, -0.0) == 0)		 // if divisor negative zero,
		{ 
			if (Double.compare(numOne, -0.0) == 1)	 // if dividend > negative zero,
				return Double.NEGATIVE_INFINITY;		 // return negative infinity
			
			return Double.POSITIVE_INFINITY;		 // else return positive infinity
		}
		
		return (numOne / numTwo);				     // return normal division result

	} // end divide

} // end StackClass

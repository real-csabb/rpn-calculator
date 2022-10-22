
import java.util.Scanner;
import java.util.Stack;

/**
 * This RPNCalculator uses stack implementations to evaluate postfix expressions. Using
 * reverse Polish notation, the expression "(7 x (8 - 2) + (5 + 3) / 6) x 4" becomes
 * "7 8 2 - x 5 3 + 6 / + 4 x". 
 * 
 * In order to do this conversion and arithmetic, this program scans left-to-right over the 
 * input expression, pushing each number onto the stack as the program sees it. When the 
 * program sees an operator it pops the operands from the stack, applies the operation to them, 
 * and pushes the result back on the stack. 
 * 
 * @author Chris Sabb
 * @version 1.0
 * @since 2022-09-18
 */
public class RPNCalculator {
	
	private Scanner s;
	public RPNCalculator() {
		s = new Scanner(System.in);
	}
	
	/**
	 * This is the main function of the RPNCalculator class. In it, an instance of the
	 * RPNCalculator class is instantiated and its function method .run is called.
	 * @param args Unused
	 * @return Nothing
	 */
	public static void main(String[] args) {
		RPNCalculator calculator = new RPNCalculator();
		calculator.run();
	}

	/**
	 * This is the run method of the RPNCalculator program. Prior it, an operands stack
	 * of type double is declared and in the method, has content pushed onto it after
	 * the userPrompt method is parsed for doubles. Following, arithmetic occurs and
	 * the operands making up the equation are popped from the stack and replaced with
	 * the sum thereof.
	 * @param Args Unused
	 * @return Returns operations conducted on the stack and prints them out to the console.
	 */
	private Stack<Double> operands;
	public void run() {
		operands = new Stack<Double>();
		
		while (true) {
			operands.clear();
			
			String userInput = this.userPrompt();
			if (userInput.equals("q")) {
				break;
			}
			
			userInput = userInput.trim();
			if (!userInput.equals("")) {
				
				parse(userInput);
					
				double result = operands.pop();
				
				if (!(Double.isNaN(result) || operands.isEmpty())) {
					System.out.println("Invalid input: too many operands");
					result = Double.NaN;
				}
				
				System.out.println(result);
			}
		}
	}
	
	/**
	 * This parse method is a helper function that parses the postfix notation
	 * the user passes to the program and converts it into an operable expression
	 * to which the program can calculate.
	 * @param userInput An postfix expression the user enters into the computer.
	 * @return Nothing
	 */
	private void parse(String userInput) {
		String[] userInputList = userInput.split(" ");
		
		for (int i = 0; i < userInputList.length; i++) {
			try {	
				operands.push(Double.parseDouble(userInputList[i]));
			} catch (NumberFormatException e) {
				String operator = userInputList[i];
				
				if (operator.equals("")) {
					continue;
				}
				
				if (!(operator.equals("+") || operator.equals("-") || 
					  operator.equals("/") || operator.equals("*"))) {
					System.out.println("Invalid input: unrecognized character");
					operands.push(Double.NaN);
					break;
				} else if (operands.size() < 2) {
					System.out.println("Invalid input: not enough operands");
					operands.push(Double.NaN);
					break;
				}
				
				calculate(operator);
			}
		}
	}
	
	/**
	 * This calculate method is a helper function performs that arithmetic based on the given
	 * operator and the two elements of the stack before it.
	 * @param operator An operator to which the two prior stack elements will be operated by.
	 * @return Nothing
	 */
	private void calculate(String operator) {
		double op1 = operands.pop();
		double op2 = operands.pop();
		
		switch (operator) {
			case "+": 
				operands.push(op2 + op1);
				break;
				
			case "-":
				operands.push(op2 - op1);
				break;
			
			case "/":
				operands.push(op2 / op1);
				break;
			
			case "*":
				operands.push(op2 * op1);
		}
	}
	
	/**
	 * The .userPrompt method is a helper function that presents the user with a text
	 * prompt and awaits their input. It then returns this input as a string.
	 * @param args Unused
	 * @return userInput This userInput string is a postfix expression the user inputs.
	 */
	private String userPrompt() {
		System.out.print(":::> ");
		String userInput = s.nextLine();
		
		return userInput;
	}

}
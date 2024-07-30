package program;

public class NumGuess_MainLine {

	public static void main(String[] args) {

		// set up the appropriate resolution for the window on all devices
		System.setProperty("sun.java2d.uiScale", "1.00");

		NumGuess_Logic ngLogic = new NumGuess_Logic();
		ngLogic.generateNumber();

		NumGuess_UI ngFrame = new NumGuess_UI(ngLogic);
		ngFrame.createDisplayGUI();

	}


}

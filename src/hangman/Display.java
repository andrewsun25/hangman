package hangman;
import java.awt.Color;
import java.awt.Font;

import hangman.StdDraw;

public class Display {
	public Display() {
		
	}
	public void update() {
		StdDraw.setPenRadius(0.07);
		StdDraw.setPenColor(new Color(234,245,249));
		StdDraw.filledSquare(0.5, 0.5, 2);
		Color yellow = new Color(186,158,6);
		StdDraw.setPenColor(yellow);
		double headXPos = 0.5;
		double headYPos = 0.75;
		StdDraw.filledCircle(headXPos, headYPos, 0.1);
		// body
		StdDraw.line(headXPos, headYPos, headXPos, headYPos - 0.5);
		double bodyYPos = 0.5;
		// right arm
		StdDraw.line(headXPos, bodyYPos, headXPos + 0.2, bodyYPos);
		// left arm
		StdDraw.line(headXPos, bodyYPos, headXPos - 0.2, bodyYPos);
		double crotchYPos = 0.25;
		StdDraw.line(headXPos, crotchYPos, headXPos + 0.2, crotchYPos - 0.2);
		StdDraw.line(headXPos, crotchYPos, headXPos - 0.2, crotchYPos - 0.2);
		StdDraw.show(10);
	}
	public static void main(String[] args) {
		Display display = new Display();
		display.update();
    }
}

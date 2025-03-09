package ua.ithillel.javapro;

import ua.ithillel.javapro.order.CoffeeOrderBoard;

public class Application {
    public static void main(String[] args) {

        CoffeeOrderBoard coffeeOrderBoard = new CoffeeOrderBoard();

        coffeeOrderBoard.add("Alen");
        coffeeOrderBoard.add("Yoda");
        coffeeOrderBoard.add("Obi-van");

        coffeeOrderBoard.draw();

        coffeeOrderBoard.deliver();
        coffeeOrderBoard.deliver(27);

        coffeeOrderBoard.draw();
    }
}
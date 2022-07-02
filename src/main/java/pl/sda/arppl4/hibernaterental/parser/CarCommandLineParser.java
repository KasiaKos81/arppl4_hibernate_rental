package pl.sda.arppl4.hibernaterental.parser;

import pl.sda.arppl4.hibernaterental.dao.CarDao;

import java.util.Scanner;

public class CarCommandLineParser {

    private Scanner scanner;
    private CarDao dao;

    public CarCommandLineParser(Scanner scanner, CarDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void ogarnij() {
        String command;

        do{
            System.out.println("Please, give command: add/remove/getOne/getAll/update or 'quit' to exit");
            command = scanner.next();

            switch (command){


            }
        }

         while (!command.equals("quit"));
}



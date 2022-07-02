package pl.sda.arppl4.hibernaterental;

import pl.sda.arppl4.hibernaterental.dao.CarDao;
import pl.sda.arppl4.hibernaterental.parser.CarCommandLineParser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CarDao carDao = new CarDao();

        CarCommandLineParser parser = new CarCommandLineParser(scanner, carDao);
        parser.ogarnij();


    }
}

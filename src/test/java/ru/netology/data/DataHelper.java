package ru.netology.data;


import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.Year;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    public static Faker faker = new Faker(new Locale("en"));

    @Value
    @RequiredArgsConstructor
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cardCVC;


        //    Заполнение поля Номер карты
        public static String getApprovedCardNumber() {
            return ("1111 2222 3333 4444");
        }

        public static String getDeclinedCardNumber() {
            return ("5555 6666 7777 8888");
        }

        public static String getUnknownCardNumber() {
            return ("5555 6666 7777 9999");
        }

        public static String getShortCardNumber() {
            return ("5555 6666 77");
        }

        public static String getCardNumberWithSymbols() {
            return ("!!!! ???? **** ()()");
        }

        public static String getCardNumberWithLettered() {
            return ("qwer tyui asdf ghjk");
        }

        //    Заполнение поля Месяц
        public static String getValidMonth() {
            LocalDate localDate = LocalDate.now();
            return String.format("%02d", localDate.getMonthValue());
        }

        public static String getMonthOver12() {
            return ("13");
        }

        public static String getMonthWithLettered() {
            return ("Hello");
        }

        public static String getMonthWithSymbols() {
            return ("#*");
        }

        public static String getMonthWithNulls() {
            return ("00");
        }

        //    Заполнение поля Год
        public static String getValidYear() {
            return String.format("%ty", Year.now());
        }

        public static String getPastYear() {
            LocalDate localDate = LocalDate.now();
            return String.format("20");
        }

        public static String getMoreThan5Years() {
            LocalDate localDate = LocalDate.now();
            return String.format("29");
        }

        public static String getYearWithLettered() {
            return ("aa");
        }

        public static String getYearWithSymbols() {
            return ("!!");
        }

        public static String getYearWithOneDigit() {
            return ("9");
        }

        //    Заполнение поля Владелец
        public static String getOwnerName() {
            return faker.name().fullName();
        }

        public static String getOwnerFirstName() {
            return faker.name().firstName();
        }

        public static String getOwnerNameInRussia() {
            Faker faker = new Faker(new Locale("ru"));
            return faker.name().fullName();
        }

        public static String getOwnerNameWithDigits() {
            return "12345";
        }

        public static String getOwnerNameWithSymbols() {
            return "*****";
        }

        public static String getOwnerNameWithDoubleName() {
            return "Anna-Mariya Moscowskaya";
        }

        //    Заполнение поля CVC
        public static String getCVC() {
            return "234";
        }

        public static String getCVCwithLettered() {
            return "qwe";
        }

        public static String getCVCwithSymbols() {
            return "***";
        }

        public static String getCVCshort() {
            return "55";
        }
    }
}
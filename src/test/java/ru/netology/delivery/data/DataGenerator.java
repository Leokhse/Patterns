package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("ru"));

    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate currentDate = LocalDate.now();
        LocalDate meetingDate = currentDate.plusDays(shift);
        return meetingDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] validCities = {"Москва", "Санкт-Петербург", "Екатеринбург", "Новосибирск", "Казань"};
        return validCities[new Random().nextInt(validCities.length)];
    }

    public static String generateName() {
        return faker.name().fullName();
    }

    public static String generatePhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser() {
            String city = generateCity();
            String name = generateName();
            String phone = generatePhone();
            return new UserInfo(city, name, phone);
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}
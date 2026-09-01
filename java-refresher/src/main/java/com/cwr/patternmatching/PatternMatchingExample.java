package com.cwr.patternmatching;

import java.util.List;

class Item {
    String name;
    double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String toString() {
        return name + " " + price;
    }

    String getName() {
        return name;
    }

    double getPrice() {
        return price;
    }

    void setPrice(double price) {
        this.price = price;
    }
}

class Electronics extends Item {
    String brand;
    String model;

    public Electronics(String name, String brand, String model, double price) {
        super(name, price);
        this.brand = brand;
        this.model = model;
    }

    public String toString() {
        return super.toString() + " " + brand + " " + model;
    }

    String getBrand() {
        return brand;
    }

    String getModel() {
        return model;
    }

}

class Clothing extends Item {
    String color;
    String size;

    public Clothing(String name, String color, String size, double price) {
        super(name, price);
        this.color = color;
        this.size = size;
    }

    public String toString() {
        return super.toString() + " " + color + " " + size;
    }

    String getColor() {
        return color;
    }

}

class Grocery extends Item {
    String category;

    public Grocery(String name, String category, double price) {
        super(name, price);
        this.category = category;
    }

    public String toString() {
        return super.toString() + " " + category;
    }

    String getCategory() {
        return category;
    }
}


public class PatternMatchingExample {

    public static void main(String[] args) {

        Item item1 = new Electronics("Samsung TV", "Samsung", "40 inch", 100000);
        Item item2 = new Clothing("T-shirt", "Red", "M", 100);
        Item item3 = new Grocery("Milk", "Dairy", 100);
        List<Item> items = List.of(item1, item2, item3);

        // electronics items discounted by 20%
        // clothing items discounted by 30%
        // grocery items discounted by 10%


        items.forEach(item -> {
                    switch (item) {
                        case Electronics e when e.brand.equals("Samsung") -> e.setPrice(item.getPrice() * 0.8);
                        case Clothing c -> c.setPrice(c.getPrice() * 0.7);
                        case Grocery g -> g.setPrice(g.getPrice() * 0.9);
                        default -> System.out.println("Unknown item type");
                    }
                }
        );

        items.forEach(System.out::println);

    }

}

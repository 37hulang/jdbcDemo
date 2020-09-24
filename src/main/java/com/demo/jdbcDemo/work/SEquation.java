package com.demo.jdbcDemo.work;

import java.util.Scanner;

public class SEquation {
    private double a,b,c;
    public SEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public void answer() {
        double _answer = Math.pow(this.b, 2) - 4 * this.a * this.c;
        if (_answer < 0)
            return;
        double d1 = -this.b / (a * this.a) + Math.sqrt(_answer);
        double d2 = -this.b / (a * this.a) - Math.sqrt(_answer);
        System.out.println("解1=" + d1 + ";" + "解2=" + d2);
    }

    public static void main(String[] args) {
        double a,b,c;
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入a:");
        a = scanner.nextDouble();
        System.out.println("输入b:");
        b = scanner.nextDouble();
        System.out.println("输入c:");
        c = scanner.nextDouble();
        SEquation instance = new SEquation(a, b, c);
        instance.answer();
    }
}



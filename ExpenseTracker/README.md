# Beginner Java Expense Tracker

A console-based personal finance project built with core Java concepts.

## Features

- Add, view, and delete expenses
- Validate dates and amounts
- Calculate total spending
- Summarize spending by category
- Set and view monthly budgets
- Save expenses and budgets between runs
- Export expenses to a CSV file

## Concepts practiced

- Classes, objects, constructors, and getters
- `ArrayList`, `Map`, and loops
- Methods and `switch` statements
- File input/output and exception handling
- CSV parsing and exporting
- Basic input validation

## Requirements

Install a Java JDK (Java 8 or newer is recommended).

## Run the project

From the directory containing the `Expense_Tracker` folder, compile:

```bash
javac Expense_Tracker/*.java
```

Run:

```bash
java Expense_Tracker.ExpenseTracker
```

The program creates `expenses.csv` and `budgets.csv` as needed in the directory where you run it. Option 7 exports data to `expenses_export.csv`.

## Notes

This is a learning project, not a banking application. Use fictional/sample transactions when demonstrating it publicly. The app stores data locally and does not connect to a bank.

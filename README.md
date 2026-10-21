# Personal Expense Tracker

A Java console application for recording expenses, tracking spending by category, and managing a monthly budget. Records are stored in CSV files so they persist between sessions.

## Features

- **Expense management:** add, view, and delete expense records
- **Spending summaries:** total spending and a breakdown by category
- **Monthly budget:** set a budget and compare spending against it
- **CSV storage:** records are saved locally and reloaded on startup
- **Data export:** export transactions as CSV for use in a spreadsheet
- **Input validation:** invalid amounts and entries are handled with clear errors

## Requirements

- JDK 17 or later [or the version you actually tested with]

## Getting Started

```bash
git clone https://github.com/piyushbhalla2006-dev/Expense-Tracker.git
cd Expense-Tracker
javac *.java
java ExpenseTracker
```

Follow the on-screen menu to add expenses and set a budget.

## Example

```
[paste a real session here, e.g. adding an expense and viewing the category summary or a budget warning]
```

## How It Works

- `[Expense]`: [what it represents]
- `[Class that manages the budget]`: [what it does]
- `[Class that reads and writes CSV]`: [what it does]

[Use your real class names. Naming them shows the structure better than a general description.]

## Design Notes

[One concrete decision, e.g. how you format the CSV, how you group by category, or how you handle a malformed row.]

## Future Improvements

- Spending charts and visualizations
- Filter expenses by date range
- Recurring expense tracking
- Automated unit tests

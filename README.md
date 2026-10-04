# DAA Assignment 2 – Data Structures

## Description

This project was created for Assignment 2 of the Design and Analysis of Algorithms course.

It implements three data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

The project also includes operation metrics, JUnit 5 tests, benchmark workloads, CSV results, and performance plots.

## Requirements

- Java 25
- Maven
- JUnit 5
- Python 3 with pandas and matplotlib for generating plots

## Project Structure

```text
src/main/java/
    DynamicArray.java
    MyLinkedList.java
    MinHeap.java
    Metrics.java
    Benchmark.java
    Main.java

src/test/java/
    DynamicArrayTest.java
    MyLinkedListTest.java
    MinHeapTest.java

results/
    results.csv
    plots/
    plot_results.py

REPORT.md
README.md
pom.xml
```

## Run Tests

Run the tests with Maven:

```bash
mvn test
```

The project contains JUnit 5 tests for correctness and edge cases.

## Run Benchmark

Run `Benchmark.java` from IntelliJ IDEA.

The benchmark executes workloads W1–W4 for:

- 100
- 1,000
- 10,000
- 100,000 elements

The results are saved to:

```text
results/results.csv
```

## Generate Plots

Open the terminal and run:

```bash
cd results
python3 plot_results.py
```

The generated PNG charts are saved in:

```text
results/plots/
```

## Benchmark Workloads

- W1 – Random Access
- W2 – Search
- W3 – Insert & Remove
- W4 – Priority Processing

Random benchmark data is generated using `Random(42)` for reproducibility.

## Author

Amina Satybaldy
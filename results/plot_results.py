import pandas as pd
import matplotlib.pyplot as plt
import os

# Read benchmark results
df = pd.read_csv("results.csv")

# Folder for plots
os.makedirs("plots", exist_ok=True)


def save_time_plot(workload, variant=None):
    data = df[df["workload"] == workload]

    if variant is not None:
        data = data[data["variant"] == variant]

    plt.figure()

    for structure in data["structure"].unique():
        part = data[data["structure"] == structure]

        plt.plot(
            part["n"],
            part["time_ms"],
            marker="o",
            label=structure
        )

    plt.xlabel("n")
    plt.ylabel("Time (ms)")

    title = workload + " - Time vs n"

    if variant is not None:
        title += " (" + variant + ")"

    plt.title(title)
    plt.legend()
    plt.grid(True)

    name = workload.lower() + "_time"

    if variant is not None:
        name += "_" + variant

    plt.savefig("plots/" + name + ".png")
    plt.close()


def save_metrics_plot(workload, variant=None):
    data = df[df["workload"] == workload]

    if variant is not None:
        data = data[data["variant"] == variant]

    for metric in ["steps", "moves", "comparisons"]:

        plt.figure()

        for structure in data["structure"].unique():
            part = data[data["structure"] == structure]

            plt.plot(
                part["n"],
                part[metric],
                marker="o",
                label=structure
            )

        plt.xlabel("n")
        plt.ylabel(metric.capitalize())

        title = workload + " - " + metric.capitalize() + " vs n"

        if variant is not None:
            title += " (" + variant + ")"

        plt.title(title)
        plt.legend()
        plt.grid(True)

        name = workload.lower() + "_" + metric

        if variant is not None:
            name += "_" + variant

        plt.savefig("plots/" + name + ".png")
        plt.close()


# W1 - Random Access
save_time_plot("W1")
save_metrics_plot("W1")

# W2 - Search
save_time_plot("W2")
save_metrics_plot("W2")

# W3 - Head
save_time_plot("W3", "head")
save_metrics_plot("W3", "head")

# W3 - Middle
save_time_plot("W3", "middle")
save_metrics_plot("W3", "middle")

# W4 - Priority Processing
save_time_plot("W4")
save_metrics_plot("W4")

print("Plots created successfully.")
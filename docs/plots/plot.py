import pandas as pd, matplotlib.pyplot as plt
df = pd.read_csv("results/results.csv")
r = df[df.type == "random"]
for col, name in [("time_ms", "time"), ("max_depth", "depth")]:
    plt.figure()
    for alg, g in r.groupby("algorithm"):
        plt.plot(g.n, g[col], marker="o", label=alg)
    plt.xscale("log"); plt.xlabel("n"); plt.ylabel(col); plt.legend(); plt.grid(True)
    plt.savefig(f"docs/plots/{name}_vs_n.png", dpi=150)
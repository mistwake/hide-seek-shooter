# 🚀 Hide and Seek Shooter

Welcome to the **Hide and Seek Shooter** repository! 

This is a 2D desktop survival game developed using **Java**. It was created as a final project (*Tugas Masa Depan*) for an Object-Oriented Programming (OOP) course, strictly implementing the Model-View-Presenter (MVP) architecture.

---

## 🎮 What is this game?

In this game, you play as a space pilot trying to survive endless waves of incoming aliens. You must navigate the map, use giant asteroids for cover, and shoot down the enemies.

One of the unique mechanics in this game is **ammo management**: you start with limited ammo, but you can scavenge more bullets whenever the aliens miss their shots or when their projectiles hit the asteroids. 

Your highest scores, missed shots, and remaining ammo are continuously saved to a local database, creating a competitive leaderboard right on the main menu!

---

## ✨ Key Features

* **Endless Alien Waves:** The enemies increase in number as you progress through the game.
* **Strategic Combat:** Use randomly spawned asteroids as shields against enemy projectiles.
* **Ammo Scavenging:** Gain ammo when enemies miss you or hit obstacles.
* **Persistent Leaderboard:** Player stats are permanently saved in a MySQL database.
* **Custom Audio & Graphics:** Features space-themed sprites and looping background music.

---

## ⌨️ Controls

* **W, A, S, D / Arrow Keys** : Move your ship.
* **F** : Shoot.
* **SPACE** : Pause/Abort back to the main menu.

---

## 🛠️ Tech Stack

* **Language:** Java
* **UI & Rendering:** Java Swing & AWT (`Graphics2D`)
* **Database:** MySQL (JDBC Driver)
* **Architecture:** Model-View-Presenter (MVP)

---

## 🚀 Quick Start

To run this game locally on your machine, follow these steps:

1. **Database Setup:** 
   * Make sure you have a local MySQL server running (e.g., via XAMPP).
   * Create a database named `db_dpbo`.
   * Create a table named `tbenefit` containing columns for `username`, `skor`, `peluru_meleset`, and `sisa_peluru`.
2. **Assets:** Ensure the `assets/` folder (containing your sprites and `.wav` files) is placed in your root directory so the game can load the resources.
3. **Dependencies:** Add the MySQL JDBC Driver (`mysql-connector-java`) to your project's build path/dependencies.
4. **Run:** Compile and run `Main.java` located in the `tmd.main` package.
5. **Play:** Enter your Pilot Name on the menu screen and click **LAUNCH**!

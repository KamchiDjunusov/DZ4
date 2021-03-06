package com.company;

import java.util.Random;

public class Main {
    public static Random random = new Random();
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int[] heroesHealth = {270, 280, 250, 230, 530, 240, 270, 260};
    public static int[] heroesDamage = {20, 15, 25, 0, 5, 15, 15, 20};
    public static int bossDamage = 50;
    public static int bossHealth = 700;
    public static String bossDefenceType = "";
    public static int roundNumber = 1;
    public static int backDamage = 0;
    public static int golemIndex;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinish()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose: " + bossDefenceType);
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHits();
        printStatistics();
        bossDamage = 50;
    }

    public static void heroesHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    int coef = random.nextInt(8) + 2;
                    if (bossHealth <= (heroesDamage[i] * coef)) {
                        bossHealth = 0;
                    } else if (heroesAttackType[i] == "Medic") {
                        for (int j = 0; j < 1000; j++) {
                            int g = random.nextInt(heroesAttackType.length);
                            if (heroesHealth[g] > 100) {
                                int randomHealth = random.nextInt(40) * coef;
                                heroesHealth[g] += randomHealth;
                                break;
                            }
                        }
                    } else if (heroesAttackType[i] == "Thor") {
                        int freezeChance = random.nextInt(4);
                        if (freezeChance == 1) {
                            bossDamage = 0;
                        }
                    } else {
                        bossHealth -= (heroesDamage[i] * coef);
                    }
                } else {
                    if (bossHealth <= heroesDamage[i]) {
                        bossHealth = 0;
                    } else if (heroesAttackType[i] == "Medic") {
                        for (int o = 0; o < 999; o++) {
                            if (heroesHealth[o] > 100) {
                                int randomHealth = random.nextInt(40)+1;
                                heroesHealth[o] += randomHealth;
                                break;
                            }
                        }
                    } else if (heroesAttackType[i] == "Thor") {
                        int freezeChance = random.nextInt(4);
                        if (freezeChance == 1) {
                            bossDamage = 0;
                        }
                    } else {
                        bossHealth -= heroesDamage[i];
                    }
                }
            } else if (heroesHealth[i] < 0) {
                heroesHealth[i] = 0;
            } else if (bossHealth < 0) {
                bossHealth = 0;
            }
        }
    }

    public static void bossHits() {
        for (int h = 0; h < heroesAttackType.length; h++) {
            if (heroesAttackType[h] == "Golem") {
                golemIndex = h;
            }
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[golemIndex] > bossDamage - (bossDamage / 5) && heroesHealth[i] > bossDamage - (bossDamage / 5)) {
                if (heroesAttackType[i] == "Lucky") {
                    int chanceForward = random.nextInt(2);
                    if (chanceForward == 1) {
                    } else {
                        heroesHealth[i] -= (bossDamage - (bossDamage / 5));
                        heroesHealth[golemIndex] -= bossDamage / 5;
                    }
                } else if (heroesHealth[i] > (bossDamage / 2) && heroesAttackType[i] == "Berserk") {
                    bossHealth -= backDamage;
                    heroesHealth[i] -= (bossDamage / 2);
                    backDamage = (bossDamage / 2);
                } else if (heroesHealth[i]> bossDamage-(bossDamage/5)){
                        heroesHealth[i] -= (bossDamage - (bossDamage / 5));
                        heroesHealth[golemIndex] -= bossDamage / 5;
                } else {
                    heroesHealth[i] = 0;
                }
            }else{
                if (heroesAttackType[i] == "Lucky") {
                    int chanceForward = random.nextInt(2);
                    if (chanceForward == 1) {
                    } else {
                        heroesHealth[i] -= bossDamage;
                    }
                } else if (heroesAttackType[i] == "Berserk") {
                    bossHealth -= backDamage;
                    heroesHealth[i] -= (bossDamage / 2);
                    backDamage = (bossDamage / 2);
                } else if (heroesHealth[i] < bossDamage){
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND----------------");
        System.out.println("Boss health " + bossHealth + " [" + bossDamage + "]");
        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i] + " health " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
        }
        System.out.println("----------------");
    }

    public static boolean isGameFinish() {
        if (bossHealth <= 0) {
            System.out.println("HEROES WON!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("BOSS WON!!!");
        }
        return allHeroesDead;
    }
}
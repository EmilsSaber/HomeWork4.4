package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 4000;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {260, 250, 270, 250, 400, 200, 250, 200};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 5, 90, 0};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "medic", "Golem", "luccyMan", "Berserker", "Thor"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefenceType);
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medic();
        Golem();
        Luccy();
        Berserker();
        Thor();
        printStatistics();
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        Random random = new Random();
        int lech = random.nextInt(100);
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
//                if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
//                    heroesHealth[i] = lech + heroesHealth[i];
                if (bossDefenceType == heroesAttackType[i]) {
                    int coeff = random.nextInt(12); //0,1,2,3,4,5,6,7,8,9,10,11
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                }
            }
        }
    }


    public static void medic() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                Random random = new Random();
                int lech = random.nextInt(100);
                heroesHealth[i] = lech + heroesHealth[i];
                System.out.println("Медик вылечил -" + heroesAttackType[i] + " - " + lech);
                break;

            }
        }

    }

    public static void Golem() {
        int getDamege = bossDamage / 5;

        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] > 0 && heroesHealth[i] > 0 && heroesHealth[4] != heroesHealth[i]) {
                heroesHealth[i] += getDamege;
                heroesHealth[4] -= getDamege;
            }

        }
        System.out.println("Gilem get 1/5 damege for - " + getDamege);
    }

    public static void Luccy() {
        Random random = new Random();
        boolean rand = random.nextBoolean();
        if (heroesHealth[5] > 0 && rand == true) {
            heroesHealth[5] += 40;
            System.out.println("Dodged damage - " + rand);
        }
    }

    public static void Berserker() {
        int getDamageBerserker = bossDamage / 2;
        if (heroesHealth[6] > 0) {
            heroesDamage[6] = heroesDamage[6] + getDamageBerserker;
            System.out.println("Berserk Atakuet - " + getDamageBerserker);
        }

    }

    public static void Thor(){
        Random random = new Random();
        boolean thors = random.nextBoolean();
        if (heroesHealth[7] > 0 && thors == true) {
            bossDamage = 0;
            System.out.println("Оглушил на 1 раунд - " + thors);
        }else {
            bossDamage = 50;
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND ------------------- ");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] +
                    " health: " + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }
}

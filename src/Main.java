import java.util.Random;

public class Main {

    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
//    public static int medicHealth = 240;

    public static int[] heroesHealth = {280, 270, 250, 240};
    public static int[] heroesDamage = {20, 15, 10, 0};
    public static int medicHeal = 30;
    public static int medicInd = 3;
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttacks();
        heroesAttack();
        medicHeal();
        printStatistics();
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
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
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void medicHeal() {

        if (heroesHealth[medicInd] > 0) {
            int teamToHeal = -1;
            for (int i = 0; i < heroesHealth.length; i++) {
                if (i != medicInd && heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                    if (teamToHeal == -1 || heroesHealth[i] < heroesHealth[teamToHeal]) {
                        teamToHeal = i;
                    }
                }
            }
            if (teamToHeal != -1) {
                heroesHealth[teamToHeal] += medicHeal;
                if (heroesHealth[teamToHeal] > 100) {
                    heroesHealth[teamToHeal] = 100;
                }
                System.out.println("Медик вылечил игрока: " + teamToHeal + " на " + medicHeal + " единиц здоровья.");
            }else{
                System.out.println("Медик не нашел, кого лечить");
            }
        }else {
            System.out.println("Медик мертв и не может лечить");
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomInd = random.nextInt(2); // 0,1,2
        bossDefence = heroesAttackType[randomInd];
    }


    public static void heroesAttack() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage *= coefficient;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static void bossAttacks() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("Round " + roundNumber + " --------");

        System.out.println("BOSS health: " + bossHealth + " damage: " + bossDamage +
                " defence: " + (bossDefence == null ? "No Defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + " damage: " + heroesDamage[i]);
        }
    }
}
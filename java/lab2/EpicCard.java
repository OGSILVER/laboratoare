public class EpicCard extends RareCard implements FlyingTroop {
    int spawnDamage;


    public EpicCard() {
        super();
        this.spawnDamage = (int) (Math.random() * 20) + 1;
        this.rarity = Rarity.EPIC;
    }

    public EpicCard(int health, int damage, String name, int jumpRange) {
        super(health, damage, name, jumpRange);
        this.spawnDamage = (int) (Math.random() * 20) + 1;
        this.rarity = Rarity.EPIC;
    }


    @Override
    public void fly() {
        System.out.println(this.getName() + " is flying!");
    }

    @Override
    void saveObjectToFile(String fileName) {
        FileUtils.writefile(fileName, this.toString());
    }

    @Override
    public String toString() {
        return "EpicCard{" +
                "spawnDamage=" + spawnDamage +
                ", range=" + jumpRange +
                ", health=" + health +
                ", damage=" + damage +
                ", speed=" + speed +
                ", name='" + name + '\'' +
                ", rarity=" + rarity +
                ", isDeployed=" + isDeployed +
                '}';
    }

    @Override
    public void readData() {
        super.readData();
        while (true) {
            try {
                String input;
                System.out.println("Enter spawn damage (1-20): ");
                input = System.console().readLine();
                int spawnDamageInput = Integer.parseInt(input);
                if (spawnDamageInput < 1 || spawnDamageInput > 20) {
                    System.out.println("Spawn damage must be between 1 and 20");
                    continue;
                }
                this.spawnDamage = spawnDamageInput;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }





    public int getSpawnDamage() {
        return spawnDamage;
    }

    public void setSpawnDamage(int spawnDamage) {
        this.spawnDamage = spawnDamage;
    }

    public final void dodgeAbility()  {
        if (Math.random() < 0.5) {
            System.out.println(this.name + " dodged the attack!");
        } else {
            System.out.println(this.name + " failed to dodge the attack.");
        }

    }
}





public final class LegendaryCard extends EpicCard implements FlyingTroop {
    int ultimateDamage;


    public LegendaryCard() {
        super();
        this.ultimateDamage = (int) (Math.random() * 50) + 1;
        this.rarity = Rarity.LEGENDARY;
    }


    public LegendaryCard(int health, int damage, String name, int jumpRange, int ultimateDamage) {
        super(health, damage, name, jumpRange);
        this.ultimateDamage = ultimateDamage;
        this.rarity = Rarity.LEGENDARY;
    }


    @Override
    public void fly() {
        System.out.println(this.getName() + " is flying!");
    }

    void useUltimate() {
        System.out.println(this.getName() + " uses ultimate ability, dealing " + this.ultimateDamage + " damage!");
    }

    @Override
    void saveObjectToFile(String fileName) {
        FileUtils.writefile(fileName, this.toString());
    }

    @Override
    public String toString() {
        return "LegendaryCard{" +
                "ultimateDamage=" + ultimateDamage +
                ", spawnDamage=" + spawnDamage +
                ", range=" + jumpRange +
                ", health=" + health +
                ", damage=" + damage +
                ", speed=" + speed +
                ", name='" + name + '\'' +
                ", rarity=" + rarity +
                ", isDeployed=" + isDeployed +
                '}';
    }

    public void ultimateAttack(int damage) {
        System.out.println(this.name + " performs an ultimate attack dealing " + damage + " damage!");
    }
}

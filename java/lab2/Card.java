public abstract class Card{
    int health;
    float damage;
    int speed;
    String name;
    Rarity rarity;
    boolean isDeployed = false;
    private final static String[] names = {"Warrior", "Mage", "Archer", "Rogue", "Paladin", "Necromancer", "Druid", "Berserker", "Assassin", "Monk"};



    //default random constructor

    public Card()
    {
        this.health = (int)(Math.random() * 100) + 1;
        this.damage = (int)(Math.random() * 50) + 1;
        this.name = names[(int)(Math.random() * names.length)];
        this.rarity = Rarity.values()[(int)(Math.random() * Rarity.values().length)];
    }

    public Card(int health, float damage, String name) {
        this.health = health;
        this.damage = damage;
        this.name = name;
        this.rarity = Rarity.COMMON;
    }

    public Card(int health, float damage) {
        this.health = health;
        this.rarity = Rarity.COMMON;

    }



    @Override
    public String toString() {
        return "Card{" +
                "health=" + health +
                ", damage=" + damage +
                ", name='" + name + '\'' +
                ", rarity=" + rarity +
                ", isDeployed=" + isDeployed +
                '}';
    }

    public void readData(){
        while(true){
            try{
                String input;
                System.out.println("Enter card name: ");
                input = System.console().readLine();
                if(input.isEmpty()){
                    System.out.println("Name cannot be empty");
                    continue;
                }
                this.name = input;

                System.out.println("Enter card health (1-100): ");
                input = System.console().readLine();
                int healthInput = Integer.parseInt(input);
                if(healthInput < 1 || healthInput > 100){
                    System.out.println("Health must be between 1 and 100");
                    continue;
                }
                this.health = healthInput;

                System.out.println("Enter card damage (1-50): ");
                input = System.console().readLine();
                float damageInput = Float.parseFloat(input);
                if(damageInput < 1 || damageInput > 50){
                    System.out.println("Damage must be between 1 and 50");
                    continue;
                }
                this.damage = damageInput;

                break; // exit loop if all inputs are valid
            }catch(Exception e){
                System.out.println("An error occurred. Please try again.");
                continue;
            }
        }
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }

    public void printInfo() {
        System.out.println(this);
    }

    abstract void saveObjectToFile(String fileName);

}

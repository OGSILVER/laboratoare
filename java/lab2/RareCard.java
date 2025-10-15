public class RareCard extends Card implements WalkingTroop {
    int jumpRange;

    //constructor
    public RareCard() {
        super();
        this.jumpRange = (int) (Math.random() * 10) + 1;
        this.rarity = Rarity.RARE;

    }


    public RareCard(int health, int damage, String name, int jumpRange) {
        super(health, damage, name);
        this.jumpRange = jumpRange;
        this.rarity = Rarity.RARE;
    }

    @Override
    void saveObjectToFile(String fileName) {
        FileUtils.writefile(fileName, this.toString());
    }

    @Override
    public void walk() {
        System.out.println(this.name + " is walking");
    }


    @Override
    public void readData() {
        super.readData();
        while (true) {
            try {
                String input;
                System.out.println("Enter jump range (1-10): ");
                input = System.console().readLine();
                int jumpRangeInput = Integer.parseInt(input);
                if (jumpRangeInput < 1 || jumpRangeInput > 10) {
                    System.out.println("Jump range must be between 1 and 10");
                    continue;
                }
                this.jumpRange = jumpRangeInput;
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public void jumpBridge() {
        System.out.println(this.name + " is jumping over a bridge");
    }
}




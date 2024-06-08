package target;

public class Label extends Instruction {

    public Label(String label) {
        op = label + ":";
        args = null; 
    }

}

package target;

public class Label extends Instruction {

    public Label(String label, String instruction) {
        op = label + ": ";
        args = new String[]{ instruction } ; 
    }

}

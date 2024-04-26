package values;

import java.util.List;

public class ValueList implements Value {
    private List<Value> values;

    public ValueList(List<Value> values) {
        this.values = values;
    }

    // Add any necessary methods for working with a list of values

    @Override
    public String toString() {
        return values.toString();
    }
}

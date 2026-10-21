import java.util.ArrayList;
import java.util.List;

public class Inventario<T> {

    private List<T> items = new ArrayList<>();

    public void agregar(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public T obtener(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.get(index);
    }

    public T remover(int index) {
        if (index < 0 || index >= items.size()) {
            return null;
        }
        return items.remove(index);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<T> getItems() {
        return items;
    }
}

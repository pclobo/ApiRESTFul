import java.util.Collection;


public interface ItemService {
    public void addItem(Item item);
    public Collection<Item> getItems();
    public Item getItem(String id);
    public Item editItem(Item item) throws ItemException;
    public void deleteItem(String id);
}

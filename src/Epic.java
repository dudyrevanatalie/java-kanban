import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds;

    public Epic(String name, String description) {
        super(name, description);
        this.subtaskIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + getName() + '\'' +
                ", id=" + getId() +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtaskIds.size()=" + subtaskIds.size() + '\'' +
                '}';
    }
}

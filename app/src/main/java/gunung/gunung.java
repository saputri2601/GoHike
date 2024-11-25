package gunung;

public class gunung {
    private int resourceId;
    private String name;


    public gunung(int resourceId, String name) {
        this.resourceId = resourceId;
        this.name = name;
    }

    // Getter and setter methods
    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

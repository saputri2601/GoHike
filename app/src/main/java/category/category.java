package category;

import java.util.List;
import gunung.gunung;

public class category {
    private String titleGunung;
    private List<gunung> gunungList;

    public category(String titleGunung, List<gunung> gunungList) {
        this.titleGunung = titleGunung;
        this.gunungList = gunungList;
    }

    public String getTitleGunung() {
        return titleGunung;
    }

    public void setTitleGunung(String titleGunung) {
        this.titleGunung = titleGunung;
    }

    public List<gunung> getGunungList() {
        return gunungList;
    }

    public void setGunungList(List<gunung> gunungList) {
        this.gunungList = gunungList;
    }
}

package client.view;

import javafx.scene.control.Button;

public class DeleteInfoView {
    private SearchView view;

    public DeleteInfoView() {
        Button search = new Button("Delete");
        this.view = new SearchView(search);
    }

    public SearchView getView() {
        return view;
    }
}

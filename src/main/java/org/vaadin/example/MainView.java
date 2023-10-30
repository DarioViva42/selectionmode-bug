package org.vaadin.example;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSingleSelectionModel;
import com.vaadin.flow.component.html.Pre;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.stream.Stream;

@Route
public class MainView extends SplitLayout {

    public MainView() {
        Grid<String> componentA = new Grid<>();
        Grid<String> componentB = new Grid<>();

        componentA.setSizeFull();
        componentB.setSizeFull();

        componentA.addComponentColumn(Span::new);
        componentB.addComponentColumn(Pre::new);

        componentA.setItems("A", "B");
        componentA.addSelectionListener(event -> {
            addToSecondary(componentB);
            componentB.scrollToStart();
            componentB.setItems(query -> Stream.iterate(0, e -> e + 1)
                    .skip(query.getOffset())
                    .limit(query.getLimit())
                    .map("%s:\nImportant information!"::formatted));

            componentB.setVisible(true);
        });

        GridSingleSelectionModel<String> selectionModel =
                (GridSingleSelectionModel<String>) componentA.setSelectionMode(Grid.SelectionMode.SINGLE);
        selectionModel.setDeselectAllowed(false);

        componentB.setSelectionMode(Grid.SelectionMode.NONE);

        componentB.setVisible(false);

        addToPrimary(componentA);
        addToSecondary(componentB);

        setSizeFull();
    }
}

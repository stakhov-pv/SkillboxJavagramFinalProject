package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class PanelClickListener implements MouseListener {
    public interface Listener {
        void onPanelClicked();
    }

    Listener listener;

    public PanelClickListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (listener!=null) listener.onPanelClicked();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) { }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) { }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) { }

    @Override
    public void mouseExited(MouseEvent mouseEvent) { }


}
package dke.extension.gui.panel.view;


import dke.extension.gui.panel.SecureDWPanel;

import dke.extension.mvc.SecureDWModel;

import javax.swing.JComponent;

import oracle.javatools.ui.Header;
import oracle.javatools.ui.HeaderPanel;
import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.VerticalFlowLayout;


public class ViewPage extends SecureDWPanel {
    public ViewPage(SecureDWModel model) {
        super(model);
        layoutComponents();
    }

  private void layoutComponents() {
      setLayout(new VerticalFlowLayout());

      addPageHeader("General");

      addSubPanel("Dimension Tree",
                  "Dimension Tree",
                  new TestPanel(this.getModel()));
  }

  private void addSubPanel(String title, String hint, JComponent component) {
      HeaderPanel stuff = new HeaderPanel();
      stuff.getHeader().setText(title);
      stuff.getHeader().setLevel(Header.Level.SUB);
      stuff.setStaticHelpText(hint);
      stuff.setHostedComponent(component);

      add(stuff);
  }

  private void addPageHeader(String pageTitle) {
      Header pageHeader = new Header();
      pageHeader.setText(pageTitle);
      pageHeader.setLevel(Header.Level.PAGE);

      add(pageHeader);
  }
}

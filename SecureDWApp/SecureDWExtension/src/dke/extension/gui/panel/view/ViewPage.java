package dke.extension.gui.panel.view;

import dke.extension.gui.panel.config.ConfigPage;
import dke.extension.gui.panel.config.ConnectionPanel;

import javax.swing.JComponent;

import oracle.javatools.ui.Header;
import oracle.javatools.ui.HeaderPanel;
import oracle.javatools.ui.TransparentPanel;
import oracle.javatools.ui.layout.VerticalFlowLayout;

public class ViewPage extends TransparentPanel {
    public ViewPage() {
        layoutComponents();
    }

  private void layoutComponents() {
      setLayout(new VerticalFlowLayout());

      addPageHeader("General");

      addSubPanel("Test",
                  "Test DB",
                  new TestPanel());
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

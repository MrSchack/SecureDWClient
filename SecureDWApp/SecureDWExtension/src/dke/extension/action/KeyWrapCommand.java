package dke.extension.action;

import oracle.ide.Ide;
import oracle.ide.controller.Command;
import oracle.ide.extension.RegisteredByExtension;

@RegisteredByExtension("dke.extension")
public final class KeyWrapCommand extends Command {
    
    public KeyWrapCommand() {
        super(actionId());
    }
    
    public KeyWrapCommand(int i, int i1, String string) {
        super(i, i1, string);
    }

    public KeyWrapCommand(int i, int i1) {
        super(i, i1);
    }

    public KeyWrapCommand(int i) {
        super(i);
    }

    public int doit() throws Exception {
        return OK;
    }

    private static int actionId() {
      final Integer cmdId = Ide.findCmdID("dke.extension.keywrap");
      if (cmdId == null)
          throw new IllegalStateException("Action dke.extension.keywrap not found.");
      return cmdId;
    }
}

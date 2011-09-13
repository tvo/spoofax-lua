package lua;

import static org.spoofax.jsglr.client.imploder.ImploderAttachment.getSort;
import static org.spoofax.terms.Term.tryGetName;

import org.eclipse.imp.services.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.spoofax.interpreter.terms.IStrategoList;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.strategoxt.imp.runtime.Environment;
import org.strategoxt.imp.runtime.parser.ast.AstNodeLocator;

/**
 * Copied parts of org.strategoxt.imp.runtime.services.LabelProvider
 *
 * @author Tobi Vollebregt
 */
public class LuaLabelProvider implements ILabelProvider {

  public Image getImage(Object element) {
    return null;
  }

  public String getText(Object element) {
    IStrategoTerm node = AstNodeLocator.impObjectToAstNode(element);
    String caption = getCaption(node);

    if (caption == null) {
      Environment.logException("Unable to infer the caption of this AST node: "
          + getSort(node) + "." + tryGetName(node));
      caption = tryGetName(node);
    }
    return caption;

  }

  private String getCaption(IStrategoTerm node) {
    // FIXME: Get rid of this once Spoofax supports specifying caption...
    String constructor = node == null ? null : tryGetName(node);

    if ("MethodName".equals(constructor)) {
      StringBuilder sb = new StringBuilder();
      appendStrategoList(sb, (IStrategoList) node.getSubterm(0));
      sb.append(":");
      sb.append(((IStrategoString) node.getSubterm(1)).stringValue());
      return sb.toString();
    }
    else if ("FunctionName".equals(constructor)) {
      StringBuilder sb = new StringBuilder();
      appendStrategoList(sb, (IStrategoList) node.getSubterm(0));
      return sb.toString();
    }
    else if ("FunctionDecl".equals(constructor)) {
      return getCaption(node.getSubterm(0));
    }
    else if ("LocalFunctionDecl".equals(constructor)) {
      return ((IStrategoString) node.getSubterm(0)).stringValue();
    }
    else {
      return null;
    }
  }

  private void appendStrategoList(StringBuilder sb, IStrategoList list) {
    while (!list.isEmpty()) {
      IStrategoString name = (IStrategoString) list.head();
      sb.append(name.stringValue());
      list = list.tail();
      if (!list.isEmpty()) {
        sb.append(".");
      }
    }
  }

  public boolean isLabelProperty(Object element, String property) {
    return true; // TODO: Optimize LabelProvider.isLabelProperty?
  }

  public void addListener(ILabelProviderListener listener) {
    // Do nothing
  }

  public void removeListener(ILabelProviderListener listener) {
    // Do nothing
  }

  public void dispose() {
    // Do nothing
  }

}

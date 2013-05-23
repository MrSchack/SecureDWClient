package oracle.jdeveloper.extsamples.custommetric;

import oracle.javatools.parser.java.v2.model.SourceVariable;
import oracle.jdeveloper.audit.analyzer.Analyzer;
import oracle.jdeveloper.audit.analyzer.AuditContext;
import oracle.jdeveloper.audit.analyzer.Category;
import oracle.jdeveloper.audit.analyzer.Metric;
import oracle.jdeveloper.audit.service.Localizer;

public class FirstTest extends Analyzer
{
  private final Localizer LOCALIZER = Localizer.instance("jdeveloper.custom.metrics.metrics");
  private final Category  CUSTOM    = new Category("custom", LOCALIZER);
  private final Metric    SAMPLE    = new Metric("sample", CUSTOM, Integer.class, new Integer(6), LOCALIZER);

  private int stuff2count = 0;

  public Metric[] getMetrics()
  {
    return new Metric[] { SAMPLE };
  }

  public void enter(AuditContext context, SourceVariable obj)
  {
    System.out.println("Metrics applied on :" + obj.getName());
    if (obj.getName().startsWith("_"))
    {
      System.out.println("\tok");
      stuff2count++;
    }
  }

  public void exit(AuditContext context, SourceVariable obj)
  {
    context.report(SAMPLE, stuff2count);
  }
}
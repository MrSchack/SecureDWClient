package oracle.jdeveloper.extsamples.custommetric;

import oracle.jdeveloper.audit.AbstractAuditAddin;

public class JDevMetrics extends AbstractAuditAddin
{
  private static final Class[] ANALYZERS = new Class[] { FirstTest.class };

  public Class[] getAnalyzers()
{
    return ANALYZERS;
  }
}

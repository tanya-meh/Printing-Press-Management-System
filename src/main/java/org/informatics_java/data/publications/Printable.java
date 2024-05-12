package org.informatics_java.data.publications;

import org.informatics_java.data.publications.enums.PaperType;

import java.math.BigDecimal;

public interface Printable {
    public BigDecimal print(PaperType paperType);
}

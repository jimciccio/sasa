package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Escursione;
import ferranti.bikerbikus.queries.EscursioniQuery;
import ferranti.bikerbikus.queries.PrenotaEscursioneQuery;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class EscursioniController1 {

    protected EscursioniController1() {}

    YearMonth currentYearMonth = YearMonth.now();
    protected static final List<Escursione> escursioni = new ArrayList<>();

    public YearMonth onActionPrevMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        loadEscursioni();
        return currentYearMonth;
    }

    public YearMonth onActionNextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        loadEscursioni();
        return currentYearMonth;
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void loadEscursioni() {
        escursioni.clear();
        escursioni.addAll(EscursioniQuery.execute(currentYearMonth, UserData.getInstance().getUser().getId()));
    }

    public  boolean prenotaEscursione(int idEscursione) {
        if (PrenotaEscursioneQuery.execute(UserData.getInstance().getUser().getId(), idEscursione)) {
            loadEscursioni();
            return true;
        }else{
            return false;
        }
    }

    public  boolean eliminaEscursione(int idEscursione) {
        if (EscursioniQuery.eliminaEscursione(idEscursione)) {
            loadEscursioni();
            return true;
        }else{
            return false;
        }
    }

    public YearMonth onActionSpecificMonth(int mese, int anno) {
        currentYearMonth= currentYearMonth.withMonth(mese);
        currentYearMonth= currentYearMonth.withYear(anno);
            loadEscursioni();

        return currentYearMonth;
    }
}

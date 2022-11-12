package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Lezione;
import ferranti.bikerbikus.queries.LezioniQuery;
import ferranti.bikerbikus.queries.PrenotaLezioneQuery;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class LezioniController1 {

    protected LezioniController1() {}

    protected static final List<Lezione> lezioniController = new ArrayList<>();
    YearMonth currentYearMonth = YearMonth.now();

    public YearMonth onActionPrevMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            loadLezioniMaestro();
        } else {
            loadLezioni();
        }
        return currentYearMonth;
    }

    public YearMonth onActionNextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            loadLezioniMaestro();
        } else {
            loadLezioni();
        }
        return currentYearMonth;
    }

    public YearMonth onActionSpecificMonth(int mese, int anno) {
        currentYearMonth= currentYearMonth.withMonth(mese);
        currentYearMonth= currentYearMonth.withYear(anno);
        if (UserData.getInstance().isMaestro() || UserData.getInstance().isMaestroAvanzato()) {
            loadLezioniMaestro();
        } else {
            loadLezioni();
        }
        return currentYearMonth;
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public void loadLezioni() {
        lezioniController.clear();
        lezioniController.addAll(LezioniQuery.execute(currentYearMonth, UserData.getInstance().getUser().getId()));
    }

    public void loadLezioniMaestro() {
        lezioniController.clear();
        lezioniController.addAll(LezioniQuery.lezioniMaestro(currentYearMonth, UserData.getInstance().getUser().getId()));

    }

    public boolean prenotaLezione(int idLezione) {
        if (PrenotaLezioneQuery.execute(UserData.getInstance().getUser().getId(), idLezione)) {
            loadLezioni();
            return true;
        }else{
            return false;
        }
    }

    public boolean eliminaLezione(int idLezione) {
        if (LezioniQuery.eliminaLezione(idLezione)) {
            loadLezioniMaestro();
            return true;
        }else{
            return false;
        }
    }
}

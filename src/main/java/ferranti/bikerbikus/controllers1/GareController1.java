package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.data.UserData;
import ferranti.bikerbikus.models.Gara;
import ferranti.bikerbikus.queries.GareQuery;
import ferranti.bikerbikus.queries.PrenotaGaraQuery;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class GareController1 {

    protected GareController1() {}

    protected static final List<Gara> gare = new ArrayList<>();
    YearMonth currentYearMonth = YearMonth.now();

    public  YearMonth onActionPrevMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        loadGare();
        return currentYearMonth;
    }

    public  YearMonth onActionNextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        loadGare();
        return currentYearMonth;
    }

    public YearMonth onActionSpecificMonth(int mese, int anno) {
        currentYearMonth= currentYearMonth.withMonth(mese);
        currentYearMonth= currentYearMonth.withYear(anno);
        System.out.println("in controller"+ currentYearMonth.getMonth()+currentYearMonth.getYear());
        loadGare();

        return currentYearMonth;
    }

    public YearMonth getCurrentYearMonth() {
        return currentYearMonth;
    }

    public  void loadGare() {
        gare.clear();
        gare.addAll(GareQuery.execute(currentYearMonth, UserData.getInstance().getUser().getId()));
    }

    public  boolean prenotaGara(int idGara) {
        if (PrenotaGaraQuery.execute(UserData.getInstance().getUser().getId(), idGara)) {
            loadGare();
            return true;
        }else{
            return false;
        }
    }
}
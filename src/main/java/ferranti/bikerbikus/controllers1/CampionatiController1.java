package ferranti.bikerbikus.controllers1;

import ferranti.bikerbikus.models.Stagione;
import ferranti.bikerbikus.queries.CampionatiQuery;

import java.util.ArrayList;
import java.util.List;

public class CampionatiController1 {

    protected CampionatiController1() {}

    protected static final List<Stagione> stagioni = new ArrayList<>();

    public static List<Stagione> loadStagioni() {
        stagioni.clear();
        stagioni.addAll(CampionatiQuery.findAll());
        return stagioni;
    }
}